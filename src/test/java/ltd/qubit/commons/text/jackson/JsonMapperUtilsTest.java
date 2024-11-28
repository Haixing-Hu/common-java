////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.json.JsonMapper;

import ltd.qubit.commons.text.testbed.Organization;
import ltd.qubit.commons.text.testbed.Person;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonMapperUtilsTest {

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
}
