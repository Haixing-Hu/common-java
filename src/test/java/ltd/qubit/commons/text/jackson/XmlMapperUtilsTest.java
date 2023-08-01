////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson;

import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ltd.qubit.commons.io.IoUtils;
import ltd.qubit.commons.lang.SystemUtils;
import ltd.qubit.commons.net.UrlPattern;
import ltd.qubit.commons.text.testbed.Organization;
import ltd.qubit.commons.text.testbed.Person;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import static ltd.qubit.commons.test.XmlUnitUtils.assertXmlEqual;
import static ltd.qubit.commons.text.jackson.XmlMapperUtils.formatList;
import static ltd.qubit.commons.text.jackson.XmlMapperUtils.parseList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class XmlMapperUtilsTest {

  @Test
  public void testFormatNormalized() {
    final XmlMapper mapper = new CustomizedXmlMapper();
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
    final String j1 = XmlMapperUtils.formatNoThrow(person, mapper);
    System.out.println(j1);
    final String j2 = XmlMapperUtils.formatNormalizedNoThrow(person, mapper);
    System.out.println(j2);
    assertEquals("<person>"
        + "<code>abc</code>"
        + "<company>"
        + "<address>江苏省南京市秦淮区XX路32号</address>"
        + "<code>xx-tech</code>"
        + "<create-time>2022-09-28T09:38:45.752Z</create-time>"
        + "<id>547362</id>"
        + "<name>XX科技有限公司</name>"
        + "</company>"
        + "<create-time>2022-09-28T09:38:45.752Z</create-time>"
        + "<id>12345</id>"
        + "<name>张三</name>"
        + "<payload>"
        + "<age>32</age>"
        + "<job-title>engineer</job-title>"
        + "</payload>"
        + "</person>", j2);
  }

  @Test
  public void testParseList() throws Exception {
    final String XML_FILE = "url-patterns.xml";
    final URL url = SystemUtils.getResource(XML_FILE, UrlPattern.class);
    final String originalXml = IoUtils.toString(url, StandardCharsets.UTF_8);
    System.out.println("original XML :\n" + originalXml);
    final XmlMapper xmlMapper = new CustomizedXmlMapper();
    final List<UrlPattern> patterns = parseList(originalXml, UrlPattern.class, xmlMapper);
    assertNotNull(patterns);
    assertTrue(patterns.size() > 0);
    for (final UrlPattern pattern : patterns) {
      assertNotNull(pattern);
      System.out.println(pattern);
    }
    final String actualXml = formatList(patterns, UrlPattern.class, xmlMapper);
    System.out.println("Serialized XML is:\n" + actualXml);
    assertXmlEqual(patterns, originalXml, actualXml);
  }
}
