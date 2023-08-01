////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.range;

import java.time.Instant;

import ltd.qubit.commons.math.RandomEx;
import ltd.qubit.commons.text.jackson.CustomizedJsonMapper;
import ltd.qubit.commons.text.jackson.CustomizedXmlMapper;
import ltd.qubit.commons.text.xml.jaxb.IsoInstantXmlAdapter;
import ltd.qubit.commons.util.codec.IsoInstantCodec;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import static ltd.qubit.commons.test.JsonUnitUtils.assertJsonNodeEquals;
import static ltd.qubit.commons.test.XmlUnitUtils.assertXPathEquals;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit test of the {@link InstantRange} class.
 *
 * @author Haixing Hu
 */
public class InstantRangeTest {

  private static final int TEST_COUNT = 20;

  private final RandomEx random = new RandomEx();

  private final JsonMapper jsonMapper = new CustomizedJsonMapper();

  private final XmlMapper xmlMapper = new CustomizedXmlMapper();

  private InstantRange createInstantRange() {
    final Instant min = random.nextInstant();
    final Instant max = random.nextInstant();
    if (min.isBefore(max)) {
      return new InstantRange(min, max);
    } else {
      return new InstantRange(max, min);
    }
  }

  @Test
  public void testJsonSerializeDeserialize() throws Exception {
    final IsoInstantCodec codec = new IsoInstantCodec();
    for (int i = 0; i < TEST_COUNT; ++i) {
      final InstantRange obj = createInstantRange();
      final String json = jsonMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
      System.out.println(json);
      assertJsonNodeEquals(json, "start", codec.encode(obj.getStart()));
      assertJsonNodeEquals(json, "end", codec.encode(obj.getEnd()));
      final InstantRange actual = jsonMapper.readValue(json, InstantRange.class);
      System.out.println(obj);
      assertEquals(obj, actual);
    }
  }

  @Test
  public void testXmlSerializeDeserialize() throws Exception {
    final IsoInstantXmlAdapter adapter = new IsoInstantXmlAdapter();
    for (int i = 0; i < TEST_COUNT; ++i) {
      final InstantRange obj = createInstantRange();
      final String xml = xmlMapper.writerWithDefaultPrettyPrinter()
          .writeValueAsString(obj);
      System.out.println(xml);
      assertXPathEquals(xml, "instant-range/start", adapter.marshal(obj.getStart()));
      assertXPathEquals(xml, "instant-range/end", adapter.marshal(obj.getEnd()));
      final InstantRange actual = xmlMapper.readValue(xml, InstantRange.class);
      System.out.println(obj);
      assertEquals(obj, actual);
    }
  }
}
