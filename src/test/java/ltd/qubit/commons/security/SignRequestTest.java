////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.security;

import ltd.qubit.commons.text.jackson.CustomizedJsonMapper;
import ltd.qubit.commons.text.jackson.CustomizedXmlMapper;
import ltd.qubit.commons.util.pair.KeyValuePairList;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SignRequestTest {

  private SignRequest prepareSignRequest() {
    final SignRequest request = new SignRequest();
    request.setSignerType("employee");
    request.setSignerId(12345L);
    request.setSignerCode("admin");
    request.setKeyVersion("v1.0.0");
    request.setPayload(KeyValuePairList.of("key1", "value1", "key2", "value2"));
    request.setMessage("Sign the document.");
    return request;
  }

  @Test
  public void testJsonSerialization() throws JsonProcessingException {
    final SignRequest request = prepareSignRequest();
    final JsonMapper mapper = new CustomizedJsonMapper();
    final String json = mapper.writeValueAsString(request);
    System.out.println(json);
    final SignRequest actual = mapper.readValue(json, SignRequest.class);
    assertEquals(request, actual);
  }

  @Test
  public void testXmlSerialization() throws JsonProcessingException {
    final SignRequest request = prepareSignRequest();
    final XmlMapper mapper = new CustomizedXmlMapper();
    final String xml = mapper.writeValueAsString(request);
    System.out.println(xml);
    final SignRequest actual = mapper.readValue(xml, SignRequest.class);
    assertEquals(request, actual);
  }
}
