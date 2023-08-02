////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.pair;

import ltd.qubit.commons.math.RandomEx;
import ltd.qubit.commons.text.jackson.CustomizedJsonMapper;
import ltd.qubit.commons.text.jackson.CustomizedXmlMapper;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import static ltd.qubit.commons.test.JsonUnitUtils.assertJsonNodeEquals;
import static ltd.qubit.commons.test.XmlUnitUtils.assertXPathEquals;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit test of the {@link KeyValuePair} class.
 *
 * @author Haixing Hu
 */
public class KeyValuePairTest {

  private static final int TEST_COUNT = 20;

  private final RandomEx random = new RandomEx();

  private final JsonMapper jsonMapper = new CustomizedJsonMapper();

  private final XmlMapper xmlMapper = new CustomizedXmlMapper();

  private KeyValuePair createKeyValuePair() {
    final String key = random.nextString().strip();
    final String value = random.nextString().strip();
    return new KeyValuePair(key, value);
  }

  @Test
  public void testJsonSerializeDeserialize() throws Exception {
    for (int i = 0; i < TEST_COUNT; ++i) {
      final KeyValuePair obj = createKeyValuePair();
      System.out.println(obj);
      final String json = jsonMapper.writerWithDefaultPrettyPrinter()
                                .writeValueAsString(obj);
      System.out.println(json);
      assertJsonNodeEquals(json, "key", obj.getKey());
      assertJsonNodeEquals(json, "value", obj.getValue());
      final KeyValuePair actual = jsonMapper.readValue(json, KeyValuePair.class);
      System.out.println(obj);
      assertEquals(obj, actual);
    }
  }

  @Test
  public void testXmlSerializeDeserialize() throws Exception {
    for (int i = 0; i < TEST_COUNT; ++i) {
      final KeyValuePair obj = createKeyValuePair();
      final String xml = xmlMapper.writeValueAsString(obj);
      System.out.println(xml);
      assertXPathEquals(xml, "key-value-pair/key", obj.getKey());
      assertXPathEquals(xml, "key-value-pair/value", obj.getValue());
      final KeyValuePair actual = xmlMapper.readValue(xml, KeyValuePair.class);
      System.out.println(obj);
      assertEquals(obj, actual);
    }
  }
}
