////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.error;

import ltd.qubit.commons.text.jackson.CustomizedXmlMapper;
import ltd.qubit.commons.util.pair.KeyValuePairList;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.junit.jupiter.api.Test;
import org.xmlunit.assertj.XmlAssert;

public class ErrorInfoTest {

  @Test
  public void testXmlSerialize() throws JsonProcessingException {
    final XmlMapper mapper = new CustomizedXmlMapper();
    final ErrorInfo e1 = new ErrorInfo();
    e1.setType(ErrorType.IO_ERROR);
    e1.setCode("no_such_file");
    e1.setMessage("The specified file was not found.");
    e1.setParams(KeyValuePairList.of("k1", "v1", "k2", "v2"));
    final String x1 = mapper.writeValueAsString(e1);
    System.out.println(x1);
    final String expected1 = "<error-info>\n"
        + "  <type>IO_ERROR</type>\n"
        + "  <code>no_such_file</code>\n"
        + "  <message>The specified file was not found.</message>\n"
        + "  <params>\n"
        + "    <param><key>k1</key><value>v1</value></param>\n"
        + "    <param><key>k2</key><value>v2</value></param>\n"
        + "  </params>\n"
        + "</error-info>";
    XmlAssert.assertThat(x1).and(expected1)
             .ignoreComments()
             .ignoreChildNodesOrder()
             .ignoreWhitespace()
             .ignoreElementContentWhitespace()
             .areIdentical();
  }

  @Test
  public void testXmlDeserialize() {

  }
}
