////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.pair;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import ltd.qubit.commons.math.RandomEx;
import ltd.qubit.commons.text.Stripper;
import ltd.qubit.commons.text.jackson.CustomizedJsonMapper;
import ltd.qubit.commons.text.jackson.CustomizedXmlMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit test of the {@link KeyValuePair} class.
 *
 * @author Haixing Hu
 */
public class KeyValuePairListTest {

  private static final int TEST_COUNT = 20;

  private final RandomEx random = new RandomEx();

  private final JsonMapper jsonMapper = new CustomizedJsonMapper();

  private final XmlMapper xmlMapper = new CustomizedXmlMapper();

  private final Stripper stripper = new Stripper().ofBlank().fromBothSide();

  private KeyValuePairList createKeyValuePairList() {
    final KeyValuePairList result = new KeyValuePairList();
    final int n = random.nextInt(10);
    for (int i = 0; i < n; ++i) {
      final String key = stripper.strip(random.nextString());
      final String value = stripper.strip(random.nextString());
      result.add(new KeyValuePair(key, value));
    }
    return result;
  }

  @Test
  public void testJsonSerializeDeserialize() throws Exception {
    for (int i = 0; i < TEST_COUNT; ++i) {
      final KeyValuePairList obj = createKeyValuePairList();
      System.out.println(obj);
      final String json = jsonMapper.writerWithDefaultPrettyPrinter()
                                .writeValueAsString(obj);
      System.out.println(json);
      final KeyValuePairList actual = jsonMapper.readValue(json, KeyValuePairList.class);
      System.out.println(obj);
      assertEquals(obj, actual);
    }
  }

  @Test
  public void testXmlSerializeDeserialize() throws Exception {
    for (int i = 0; i < TEST_COUNT; ++i) {
      final KeyValuePairList obj = createKeyValuePairList();
      final String xml = xmlMapper.writeValueAsString(obj);
      System.out.println(xml);
      final KeyValuePairList actual = xmlMapper.readValue(xml, KeyValuePairList.class);
      System.out.println(obj);
      assertEquals(obj, actual);
    }
  }
}