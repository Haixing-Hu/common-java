////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson;

import java.io.IOException;
import java.time.Instant;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.http.RequestEntity;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.json.JsonMapper;

import ltd.qubit.commons.text.testbed.Gender;
import ltd.qubit.commons.text.testbed.OrganizationNoAnnotation;
import ltd.qubit.commons.text.testbed.PersonNoAnnotation;
import ltd.qubit.commons.text.testbed.ValueEnum;
import ltd.qubit.commons.text.testbed.WithEnum;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CustomizedJsonMapperTest {

  @Test
  public void testJsonSerializationWithoutAnnotation()
      throws JsonProcessingException {
    final JsonMapper mapper = new CustomizedJsonMapper();
    final OrganizationNoAnnotation o1 = new OrganizationNoAnnotation();
    o1.setId(547362L);
    o1.setCode("xx-tech");
    o1.setName("XX科技有限公司");
    o1.setAddress("江苏省南京市秦淮区XX路32号");
    o1.setCreateTime(Instant.parse("2022-09-28T09:38:45.752Z"));
    o1.setModifyTime(Instant.parse("2022-09-28T10:40:32Z"));
    o1.setDeleteTime(null);

    final String json1 = mapper.writeValueAsString(o1);
    System.out.println(json1);
    final OrganizationNoAnnotation o2 = mapper.readValue(json1, OrganizationNoAnnotation.class);
    assertEquals(o1, o2);

    final PersonNoAnnotation p1 = new PersonNoAnnotation();
    p1.setId(12345L);
    p1.setCode("abc");
    p1.setName("张三");
    p1.setGender(Gender.MALE);
    p1.setCompany(o1);
    p1.setCreateTime(Instant.parse("2022-09-28T09:38:45.752Z"));
    p1.setModifyTime(Instant.parse("2022-09-28T10:40:32Z"));
    final Map<String, String> payload = new HashMap<>();
    payload.put("jobTitle", "engineer");
    payload.put("age", "32");
    p1.setPayload(payload);

    p1.setJobTags(Arrays.asList("t1", "t2", "t3"));

    final String json2 = mapper.writeValueAsString(p1);
    System.out.println(json2);
    final PersonNoAnnotation p2 = mapper.readValue(json2, PersonNoAnnotation.class);
    assertEquals(p1, p2);
  }

  @Test
  public void testEnumSerialize() throws JsonProcessingException {
    final JsonMapper mapper = new CustomizedJsonMapper();
    final WithEnum foo = new WithEnum();
    foo.setValue(ValueEnum.VALUE_1);
    final String json = mapper.writeValueAsString(foo);
    System.out.println(json);
    assertEquals("{\"value\":\"VALUE_1\"}", json);
  }

  @Test
  public void testEnumDeserialize() throws JsonProcessingException {
    final JsonMapper mapper = new CustomizedJsonMapper();
    final WithEnum foo = new WithEnum();
    foo.setValue(ValueEnum.VALUE_1);
    String json = mapper.writeValueAsString(foo);
    System.out.println(json);
    assertEquals("{\"value\":\"VALUE_1\"}", json);
    json = "{\"value\":\"VALUE_1\"}";
    final WithEnum f1 = mapper.readValue(json, WithEnum.class);
    assertEquals(foo, f1);
    json = "{\"value\":\"value_1\"}";
    final WithEnum f2 = mapper.readValue(json, WithEnum.class);
    assertEquals(foo, f2);
    json = "{\"value\":\"value-1\"}";
    final WithEnum f3 = mapper.readValue(json, WithEnum.class);
    assertEquals(foo, f3);
    json = "{\"value\":\"vaLue-1\"}";
    final WithEnum f4 = mapper.readValue(json, WithEnum.class);
    assertEquals(foo, f4);

    json = "{\"value\":\"\"}";
    final WithEnum f5 = mapper.readValue(json, WithEnum.class);
    foo.setValue(null);
    assertEquals(foo, f5);

    json = "{\"value\":\"  \"}";
    final WithEnum f6 = mapper.readValue(json, WithEnum.class);
    foo.setValue(null);
    assertEquals(foo, f6);

    json = "{}";
    final WithEnum f7 = mapper.readValue(json, WithEnum.class);
    foo.setValue(null);
    assertEquals(foo, f7);

    json = "{\"value\":\"the-value-3\"}";
    final WithEnum f8 = mapper.readValue(json, WithEnum.class);
    foo.setValue(ValueEnum.the_Value_3);
    assertEquals(foo, f8);

    final String errorJson = "{\"value\":\"value-3\"}";
    assertThrows(InvalidFormatException.class,
        () -> mapper.readValue(errorJson, WithEnum.class));
  }


  public static class Foo {
    private final String name;
    private final String value;

    public Foo(final String name, final String value) {
      this.name = name;
      this.value = value;
    }

    public String getName() {
      return name;
    }

    public String getValue() {
      return value;
    }
  }

  @Test
  public void testMapperForClassWithoutDefaultConstructor() throws JsonProcessingException {
    final JsonMapper mapper = new CustomizedJsonMapper();
    final Foo foo = new Foo("foo", "bar");
    final String json = "{\"name\":\"foo\",\"value\":\"bar\"}";
    final Foo f1 = mapper.readValue(json, Foo.class);
    assertEquals(foo.getName(), f1.getName());
    assertEquals(foo.getValue(), f1.getValue());
  }


  private static class ImmutableClass {
    private String str;

    public ImmutableClass(final String someStr) {
      this.str = someStr;
    }

    public String getStr() {
      return str;
    }
  }

  private static class ImmutableMap {
    private final Map<String, Object> params;

    public ImmutableMap(final Map<String, Object> params) {
      this.params = params;
    }

    public Map<String, Object> getParams() {
      return params;
    }
  }

  @Test
  public void shouldSuccessDeserialize() throws IOException {
    final JsonMapper objectMapper = new CustomizedJsonMapper();
    final String ser = objectMapper.writeValueAsString(new ImmutableClass("Hi")); // => { "str": "Hi" }
    final ImmutableClass deser = objectMapper.readValue(ser, ImmutableClass.class);
    assertEquals(deser.getStr(), "Hi");
  }

  @Test
  public void shouldSuccessDeserializeImmutableMap() throws IOException {
    final JsonMapper objectMapper = new CustomizedJsonMapper();
    final String ser = objectMapper.writeValueAsString(new ImmutableMap(Map.of("key", "value"))); // => { "params": { "key": "value" } }
    final ImmutableMap deser = objectMapper.readValue(ser, ImmutableMap.class);
    assertEquals(deser.getParams(), Map.of("key", "value"));
  }

  @Disabled
  @Test
  public void shouldSuccessDeserializeCustomImmutableMap() throws IOException {
    // RequestEntity class have ReadOnlyHttpHeaders extending HttpHeaders implementing Map.
    final String json = "{\"@class\":\"org.springframework.http.RequestEntity\","
        + "\"headers\":{\"@class\":\"org.springframework.http.ReadOnlyHttpHeaders\","
        + "\"host\":[\"java.util.LinkedList\",[\"localhost:8080\"]],"
        + "\"user-agent\":[\"java.util.LinkedList\",[\"insomnia/7.0.1\"]],"
        + "\"cookie\":[\"java.util.LinkedList\",[\"SESSIONID=abcdefg\"]],"
        + "\"accept\":[\"java.util.LinkedList\",[\"*/*\"]],"
        + "\"content-length\":[\"java.util.LinkedList\",[\"100\"]],"
        + "\"Content-Type\":[\"java.util.LinkedList\",[\"application/json;charset=UTF-8\"]]},"
        + "\"body\":{\"@class\":\"java.util.LinkedHashMap\","
        + "\"params\":[\"java.util.ArrayList\",[{\"@class\":\"java.util.LinkedHashMap\","
        + "\"email\":\"wndtn853@gmail.com\"}]]},\"method\":\"POST\",\"url\":\"http://localhost:8080/\",\"type\":null}";

    final ObjectMapper objectMapper = new ObjectMapper()
        .setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE)
        .setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY)
        .enableDefaultTypingAsProperty(ObjectMapper.DefaultTyping.NON_FINAL, "@class")
        .registerModule(new ForceCreatorDeserializerModule());

    final RequestEntity deser = objectMapper.readValue(json, RequestEntity.class);

    assertEquals(objectMapper.writeValueAsString(deser), json);
  }
}
