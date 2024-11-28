////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.error;

import org.junit.jupiter.api.Test;
import org.xmlunit.assertj.XmlAssert;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import ltd.qubit.commons.text.jackson.CustomizedJsonMapper;
import ltd.qubit.commons.text.jackson.CustomizedXmlMapper;
import ltd.qubit.commons.util.pair.KeyValuePairList;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;

public class ErrorInfoTest {

  @Test
  public void testXmlSerialize() throws JsonProcessingException {
    final XmlMapper mapper = new CustomizedXmlMapper();
    final ErrorInfo e1 = new ErrorInfo();
    e1.setType("IO_ERROR");
    e1.setCode("no_such_file");
    e1.setMessage("The specified file was not found.");
    e1.setParams(KeyValuePairList.of("k1", "v1", "k2", "v2"));
    final String x1 = mapper.writeValueAsString(e1);
    System.out.println(x1);
    final String xml = "<error-info>\n"
        + "  <type>IO_ERROR</type>\n"
        + "  <code>no_such_file</code>\n"
        + "  <message>The specified file was not found.</message>\n"
        + "  <params>\n"
        + "    <param><key>k1</key><value>v1</value></param>\n"
        + "    <param><key>k2</key><value>v2</value></param>\n"
        + "  </params>\n"
        + "</error-info>";
    XmlAssert.assertThat(x1).and(xml)
             .ignoreComments()
             .ignoreChildNodesOrder()
             .ignoreWhitespace()
             .ignoreElementContentWhitespace()
             .areIdentical();
  }

  @Test
  public void testXmlDeserialize() throws JsonProcessingException {
    final String xml = "<error-info>\n"
        + "  <type>IO_ERROR</type>\n"
        + "  <code>no_such_file</code>\n"
        + "  <message>The specified file was not found.</message>\n"
        + "  <params>\n"
        + "    <param><key>k1</key><value>v1</value></param>\n"
        + "    <param><key>k2</key><value>v2</value></param>\n"
        + "  </params>\n"
        + "</error-info>";
    final XmlMapper mapper = new CustomizedXmlMapper();
    final ErrorInfo e1 = mapper.readValue(xml, ErrorInfo.class);
    assertEquals("IO_ERROR", e1.getType(), "wrong type");
    assertEquals("no_such_file", e1.getCode(), "wrong code");
    assertEquals("The specified file was not found.", e1.getMessage(), "wrong message");
    assertEquals(KeyValuePairList.of("k1", "v1", "k2", "v2"), e1.getParams(), "wrong params");
  }

  @Test
  public void testJsonSerialize() throws JsonProcessingException {
    final JsonMapper mapper = new CustomizedJsonMapper();
    final ErrorInfo e1 = new ErrorInfo();
    e1.setType("IO_ERROR");
    e1.setCode("no_such_file");
    e1.setMessage("The specified file was not found.");
    e1.setParams(KeyValuePairList.of("k1", "v1", "k2", "v2"));
    final String actual = mapper.writeValueAsString(e1);
    System.out.println(actual);
    final String expected = "{"
        + "\"type\":\"IO_ERROR\","
        + "\"code\":\"no_such_file\","
        + "\"message\":\"The specified file was not found.\","
        + "\"params\":[{"
        + "\"key\":\"k1\","
        + "\"value\":\"v1"
        + "\"},{"
        + "\"key\":\"k2\","
        + "\"value\":\"v2\""
        + "}]"
        + "}";
    assertThatJson(actual).isEqualTo(expected);
  }

  @Test
  public void testJsonDeserialize() throws JsonProcessingException {
    final String json = "{"
        + "\"type\":\"IO_ERROR\","
        + "\"code\":\"no_such_file\","
        + "\"message\":\"The specified file was not found.\","
        + "\"params\":[{"
        + "\"key\":\"k1\","
        + "\"value\":\"v1"
        + "\"},{"
        + "\"key\":\"k2\","
        + "\"value\":\"v2\""
        + "}]"
        + "}";
    final JsonMapper mapper = new CustomizedJsonMapper();
    final ErrorInfo e1 = mapper.readValue(json, ErrorInfo.class);
    assertEquals("IO_ERROR", e1.getType(), "wrong type");
    assertEquals("no_such_file", e1.getCode(), "wrong code");
    assertEquals("The specified file was not found.", e1.getMessage(), "wrong message");
    assertEquals(KeyValuePairList.of("k1", "v1", "k2", "v2"), e1.getParams(), "wrong params");
  }
}
