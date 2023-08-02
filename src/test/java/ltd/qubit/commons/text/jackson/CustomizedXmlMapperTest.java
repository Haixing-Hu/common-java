////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson;

import java.time.Instant;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import ltd.qubit.commons.text.testbed.Gender;
import ltd.qubit.commons.text.testbed.MixJsonXmlAnnotation;
import ltd.qubit.commons.text.testbed.OrganizationNoAnnotation;
import ltd.qubit.commons.text.testbed.PersonNoAnnotation;
import ltd.qubit.commons.text.testbed.ValueEnum;
import ltd.qubit.commons.text.testbed.WithEnum;
import ltd.qubit.commons.util.pair.KeyValuePair;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import static ltd.qubit.commons.test.XmlUnitUtils.assertXPathEquals;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CustomizedXmlMapperTest {

  @Test
  public void testXmlSerializationWithoutAnnotation()
      throws JsonProcessingException {
    final XmlMapper mapper = new CustomizedXmlMapper();
    final OrganizationNoAnnotation o1 = new OrganizationNoAnnotation();
    o1.setId(547362L);
    o1.setCode("xx-tech");
    o1.setName("XX科技有限公司");
    o1.setAddress("江苏省南京市秦淮区XX路32号");
    o1.setCreateTime(Instant.parse("2022-09-28T09:38:45.752Z"));
    o1.setModifyTime(Instant.parse("2022-09-28T10:40:32Z"));
    o1.setDeleteTime(null);

    final String xml1 = mapper.writeValueAsString(o1);
    System.out.println(xml1);
    final OrganizationNoAnnotation o2 = mapper.readValue(xml1, OrganizationNoAnnotation.class);
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
    payload.put("job-title", "engineer");
    payload.put("age", "32");
    p1.setPayload(payload);

    p1.setJobTags(Arrays.asList("t1", "t2", "t3"));

    final String xml2 = mapper.writeValueAsString(p1);
    System.out.println(xml2);
    final PersonNoAnnotation p2 = mapper.readValue(xml2, PersonNoAnnotation.class);
    assertEquals(p1, p2);
  }

  @Test
  public void testMixJsonXmlAnnotation() throws JsonProcessingException {
    final XmlMapper mapper = new CustomizedXmlMapper();
    final MixJsonXmlAnnotation m1 = new MixJsonXmlAnnotation();
    m1.setCreateTime(Instant.parse("2022-09-28T09:38:45.752Z"));
    m1.setDeleteTime(Instant.parse("2022-09-28T09:38:45.752Z"));

    final String xml = mapper.writeValueAsString(m1);
    System.out.println(xml);
    final MixJsonXmlAnnotation m2 = mapper.readValue(xml, MixJsonXmlAnnotation.class);
    assertEquals(m1, m2);
  }

  @Test
  public void testStringTrim() throws JsonProcessingException {
    final XmlMapper mapper = new CustomizedXmlMapper();
    final OrganizationNoAnnotation o1 = new OrganizationNoAnnotation();
    o1.setId(547362L);
    o1.setCode("xx-tech");
    o1.setName("XX科技有限公司");
    o1.setAddress("江苏省南京市秦淮区XX路32号");
    o1.setCreateTime(Instant.parse("2022-09-28T09:38:45.752Z"));
    o1.setModifyTime(Instant.parse("2022-09-28T10:40:32Z"));
    o1.setDeleteTime(null);

    final PersonNoAnnotation p1 = new PersonNoAnnotation();
    p1.setId(12345L);
    p1.setCode("abc");
    p1.setName("张三");
    p1.setGender(Gender.MALE);
    p1.setCompany(o1);
    p1.setCreateTime(Instant.parse("2022-09-28T09:38:45.752Z"));
    p1.setModifyTime(Instant.parse("2022-09-28T10:40:32Z"));
    final Map<String, String> payload = new HashMap<>();
    payload.put("job-title", "engineer");
    payload.put("age", "32");
    p1.setPayload(payload);
    p1.setJobTags(Arrays.asList("t1", "t2", "t3"));

    final String xml =
        "<person-no-annotation>\n"
      + "    <id>\n"
      + "        12345 \n"
      + "    </id>\n"
      + "    <code>abc \n"
      + "    </code> \n"
      + "    <name> 张三\n"
      + "    </name> \n"
      + "    <company>\n"
      + "        <id>547362</id> \n"
      + "        <code>xx-tech </code> \n"
      + "        <name>  XX科技有限公司 \n  </name> \n"
      + "        <address>江苏省南京市秦淮区XX路32号 </address> \n"
      + "        <create-time> 2022-09-28T09:38:45.752Z </create-time> \n"
      + "        <modify-time> 2022-09-28T10:40:32Z \n </modify-time> \n"
      + "    </company>\n"
      + "    <gender>\n"
      + "        MALE \n"
      + "    </gender>\n"
      + "    <create-time>\n"
      + "        2022-09-28T09:38:45.752Z \n"
      + "    </create-time>\n"
      + "    <modify-time>\n"
      + "        2022-09-28T10:40:32Z \n"
      + "    </modify-time>\n"
      + "    <payload>\n"
      + "        <job-title>  engineer </job-title> \n"
      + "        <age>  32 \n</age> \n"
      + "    </payload>\n"
      + "    <job-tags>\n"
      + "        <job-tag> t1 </job-tag> \n"
      + "        <job-tag> t2 </job-tag> \n"
      + "        <job-tag>    t3\t\n </job-tag> \n"
      + "    </job-tags>\n"
      + "</person-no-annotation>\n";

    System.out.println(xml);
    final PersonNoAnnotation p2 = mapper.readValue(xml, PersonNoAnnotation.class);
    assertEquals(p1, p2);
  }

  @Test
  public void testXmlOuputEscapeCharacter() throws JsonProcessingException {
    final XmlMapper mapper = new CustomizedXmlMapper();
    final KeyValuePair k = new KeyValuePair();
    k.setKey("$<_/;2");
    k.setValue("_!GcpaYO(k]YTO*5\nTLhui?:E~)v'AN<I");
    final String xml = mapper.writeValueAsString(k);
    System.out.println(xml);
    final KeyValuePair k2 = mapper.readValue(xml, KeyValuePair.class);
    assertEquals(k, k2);
  }

  @Disabled
  @Test
  public void testXmlSerializationWithLeadingTrainingSpace()
      throws JsonProcessingException {
    final XmlMapper mapper = new CustomizedXmlMapper();
    final KeyValuePair obj = new KeyValuePair();
    obj.setKey("key");
    obj.setValue(" value ");
    String xml = mapper.writeValueAsString(obj);
    System.out.println(xml);
    assertXPathEquals(xml, "key-value-pair/key", obj.getKey());
    assertXPathEquals(xml, "key-value-pair/value", obj.getValue());
    KeyValuePair other = mapper.readValue(xml, KeyValuePair.class);
    assertEquals(obj, other);

    obj.setKey("key");
    obj.setValue("value");
    xml = mapper.writeValueAsString(obj);
    System.out.println(xml);
    assertXPathEquals(xml, "key-value-pair/key", obj.getKey());
    assertXPathEquals(xml, "key-value-pair/value", obj.getValue());
    other = mapper.readValue(xml, KeyValuePair.class);
    assertEquals(obj, other);
  }

  @Test
  public void testEnumSerialize() throws JsonProcessingException {
    final XmlMapper mapper = new CustomizedXmlMapper();
    final WithEnum foo = new WithEnum();
    foo.setValue(ValueEnum.VALUE_1);
    final String json = mapper.writeValueAsString(foo);
    System.out.println(json);
    assertEquals("<with-enum><value>VALUE_1</value></with-enum>", json);
  }

  @Test
  public void testEnumDeserialize() throws JsonProcessingException {
    final XmlMapper mapper = new CustomizedXmlMapper();
    final WithEnum foo = new WithEnum();
    foo.setValue(ValueEnum.VALUE_1);
    String json = mapper.writeValueAsString(foo);
    System.out.println(json);
    assertEquals("<with-enum><value>VALUE_1</value></with-enum>", json);
    json = "<with-enum><value>VALUE_1</value></with-enum>";
    final WithEnum f1 = mapper.readValue(json, WithEnum.class);
    assertEquals(foo, f1);
    json = "<with-enum><value>value_1</value></with-enum>";
    final WithEnum f2 = mapper.readValue(json, WithEnum.class);
    assertEquals(foo, f2);
    json = "<with-enum><value>value-1</value></with-enum>";
    final WithEnum f3 = mapper.readValue(json, WithEnum.class);
    assertEquals(foo, f3);
    json = "<with-enum><value>vaLue-1</value></with-enum>";
    final WithEnum f4 = mapper.readValue(json, WithEnum.class);
    assertEquals(foo, f4);

    json = "<with-enum><value></value></with-enum>";
    final WithEnum f5 = mapper.readValue(json, WithEnum.class);
    foo.setValue(null);
    assertEquals(foo, f5);

    json = "<with-enum><value>  </value></with-enum>";
    final WithEnum f6 = mapper.readValue(json, WithEnum.class);
    foo.setValue(null);
    assertEquals(foo, f6);

    json = "<with-enum></with-enum>";
    final WithEnum f7 = mapper.readValue(json, WithEnum.class);
    foo.setValue(null);
    assertEquals(foo, f7);

    json = "<with-enum><value>the-value-3</value></with-enum>";
    final WithEnum f8 = mapper.readValue(json, WithEnum.class);
    foo.setValue(ValueEnum.the_Value_3);
    assertEquals(foo, f8);

    final String errorJson = "<with-enum><value>value-3</value></with-enum>";
    assertThrows(InvalidFormatException.class,
        () -> mapper.readValue(errorJson, WithEnum.class));
  }
}
