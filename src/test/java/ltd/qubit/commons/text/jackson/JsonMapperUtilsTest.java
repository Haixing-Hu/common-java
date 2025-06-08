////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;

import ltd.qubit.commons.text.testbed.Organization;
import ltd.qubit.commons.text.testbed.Person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * {@link JsonMapperUtils} 的单元测试。
 *
 * @author Haixing Hu
 */
public class JsonMapperUtilsTest {

  /**
   * 测试用的简单对象。
   */
  private static class SimpleObject {
    @JsonProperty
    private Long id;

    @JsonProperty
    private String name;

    @JsonProperty
    private Integer age;

    @JsonProperty
    private Boolean active;

    public SimpleObject() {}

    public SimpleObject(final Long id, final String name, final Integer age,
        final Boolean active) {
      this.id = id;
      this.name = name;
      this.age = age;
      this.active = active;
    }
  }

  /**
   * 测试用的嵌套对象。
   */
  private static class NestedObject {
    @JsonProperty
    private Long id;

    @JsonProperty
    private String name;

    @JsonProperty
    private SimpleObject child;

    @JsonProperty
    private List<String> tags;

    @JsonProperty
    private Map<String, String> attributes;

    @JsonProperty("create_time")
    private Instant createTime;

    public NestedObject() {}

    public NestedObject(final Long id, final String name, final SimpleObject child,
        final List<String> tags, final Map<String, String> attributes,
        final Instant createTime) {
      this.id = id;
      this.name = name;
      this.child = child;
      this.tags = tags;
      this.attributes = attributes;
      this.createTime = createTime;
    }
  }

  @Test
  public void testFormatNormalized() {
    final JsonMapper mapper = new CustomizedJsonMapper();
    final Organization company = new Organization();
    company.setId(547362L);
    company.setCode("xx-tech");
    company.setName("XX科技有限公司");
    company.setAddress("江苏省南京市秦淮区XX路32号");
    company.setCreateTime(Instant.parse("2022-09-28T09:38:45.752Z"));
    final Person person = new Person();
    person.setId(12345L);
    person.setCode("abc");
    person.setName("张三");
    person.setCompany(company);
    person.setCreateTime(Instant.parse("2022-09-28T09:38:45.752Z"));
    final Map<String, String> payload = new HashMap<>();
    payload.put("job-title", "engineer");
    payload.put("age", "32");
    person.setPayload(payload);
    //
    final String j1 = JsonMapperUtils.formatNoThrow(person, mapper);
    System.out.println(j1);
    final String j2 = JsonMapperUtils.formatNormalizedNoThrow(person, mapper);
    System.out.println(j2);
    assertEquals("{\"code\":\"abc\","
        + "\"company\":{"
        + "\"address\":\"江苏省南京市秦淮区XX路32号\","
        + "\"code\":\"xx-tech\","
        + "\"create_time\":\"2022-09-28T09:38:45.752Z\","
        + "\"id\":547362,"
        + "\"name\":\"XX科技有限公司"
        + "\"},"
        + "\"create_time\":\"2022-09-28T09:38:45.752Z\","
        + "\"id\":12345,"
        + "\"name\":\"张三\","
        + "\"payload\":{\"age\":\"32\",\"job-title\":\"engineer\"}}", j2);
  }

  @Test
  public void testToPathValueMap_SimpleObject() throws JsonProcessingException {
    final SimpleObject obj = new SimpleObject(1L, "张三", 30, true);
    final Map<String, String> result = JsonMapperUtils.toPathValueMap(obj);
    assertNotNull(result);
    assertEquals("1", result.get("id"));
    assertEquals("张三", result.get("name"));
    assertEquals("30", result.get("age"));
    assertEquals("true", result.get("active"));
    assertEquals(4, result.size());
  }

  @Test
  public void testToPathValueMap_NestedObject() throws JsonProcessingException {
    final SimpleObject child = new SimpleObject(2L, "李四", 25, false);
    final List<String> tags = Arrays.asList("developer", "manager");
    final Map<String, String> attributes = new HashMap<>();
    attributes.put("department", "技术部");
    attributes.put("title", "高级工程师");
    final Instant createTime = Instant.parse("2024-02-27T12:34:56Z");
    final NestedObject obj = new NestedObject(1L, "张三", child, tags, attributes,
        createTime);

    final Map<String, String> result = JsonMapperUtils.toPathValueMap(obj);
    assertNotNull(result);
    assertEquals("1", result.get("id"));
    assertEquals("张三", result.get("name"));
    assertEquals("2", result.get("child.id"));
    assertEquals("李四", result.get("child.name"));
    assertEquals("25", result.get("child.age"));
    assertEquals("false", result.get("child.active"));
    assertEquals("developer", result.get("tags[0]"));
    assertEquals("manager", result.get("tags[1]"));
    assertEquals("技术部", result.get("attributes.department"));
    assertEquals("高级工程师", result.get("attributes.title"));
    assertEquals("2024-02-27T12:34:56Z", result.get("create_time"));
    assertEquals(11, result.size());
  }

  @Test
  public void testToPathValueMap_Null() throws JsonProcessingException {
    final Map<String, String> result = JsonMapperUtils.toPathValueMap(null);
    assertNotNull(result);
    assertEquals(0, result.size());
  }

  @Test
  public void testToPathValueMap_EmptyObjectFields() throws JsonProcessingException {
    final SimpleObject obj = new SimpleObject();
    final Map<String, String> result = JsonMapperUtils.toPathValueMap(obj);
    assertNotNull(result);
    assertEquals(0, result.size());
  }

  @Test
  public void testToPathValueMap_EmptyList() throws JsonProcessingException {
    final List<String> list = new ArrayList<>();
    final Map<String, String> result = JsonMapperUtils.toPathValueMap(list);
    assertNotNull(result);
    assertEquals(0, result.size());
  }

  @Test
  public void testToPathValueMap_CustomMapper() throws JsonProcessingException {
    final SimpleObject obj = new SimpleObject(1L, "张三", 30, true);
    final JsonMapper mapper = new CustomizedJsonMapper();
    final Map<String, String> result = JsonMapperUtils.toPathValueMap(obj, mapper);
    assertNotNull(result);
    assertEquals("1", result.get("id"));
    assertEquals("张三", result.get("name"));
    assertEquals("30", result.get("age"));
    assertEquals("true", result.get("active"));
    assertEquals(4, result.size());
  }

  @Test
  public void testToPathValueMapNoThrow_Success() {
    final SimpleObject obj = new SimpleObject(1L, "张三", 30, true);
    final Map<String, String> result = JsonMapperUtils.toPathValueMapNoThrow(obj);
    assertNotNull(result);
    assertEquals("1", result.get("id"));
    assertEquals("张三", result.get("name"));
    assertEquals("30", result.get("age"));
    assertEquals("true", result.get("active"));
    assertEquals(4, result.size());
  }

  @Test
  public void testToPathValueMapNoThrow_EmptyObject() {
    final Object obj = new Object() {
      @Override
      public String toString() {
        throw new RuntimeException("Simulated error");
      }
    };
    final Map<String, String> result = JsonMapperUtils.toPathValueMapNoThrow(obj);
    assertEquals(Collections.emptyMap(), result);
  }
}