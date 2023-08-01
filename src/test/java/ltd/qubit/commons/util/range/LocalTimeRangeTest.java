////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.range;

import ltd.qubit.commons.math.RandomEx;
import ltd.qubit.commons.text.jackson.CustomizedJsonMapper;
import ltd.qubit.commons.text.jackson.CustomizedXmlMapper;
import ltd.qubit.commons.text.xml.jaxb.IsoLocalTimeXmlAdapter;
import ltd.qubit.commons.util.codec.IsoLocalTimeCodec;

import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static ltd.qubit.commons.test.JsonUnitUtils.assertJsonNodeEquals;
import static ltd.qubit.commons.test.XmlUnitUtils.assertXPathEquals;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit test of the {@link LocalTimeRange} class.
 *
 * @author Haixing Hu
 */
public class LocalTimeRangeTest {

  private static final int TEST_COUNT = 20;

  private final RandomEx random = new RandomEx();

  private final JsonMapper jsonMapper = new CustomizedJsonMapper();

  private final XmlMapper xmlMapper = new CustomizedXmlMapper();

  private LocalTimeRange createLocalTimeRange() {
    final LocalTime min = random.nextLocalTime();
    final LocalTime max = random.nextLocalTime();
    if (min.isBefore(max)) {
      return new LocalTimeRange(min, max);
    } else {
      return new LocalTimeRange(max, min);
    }
  }

  @Test
  public void testJsonSerializeDeserialize() throws Exception {
    final IsoLocalTimeCodec codec = new IsoLocalTimeCodec();
    for (int i = 0; i < TEST_COUNT; ++i) {
      final LocalTimeRange obj = createLocalTimeRange();
      final String json = jsonMapper.writerWithDefaultPrettyPrinter()
                                    .writeValueAsString(obj);
      System.out.println(json);
      assertJsonNodeEquals(json, "start", codec.encode(obj.getStart()));
      assertJsonNodeEquals(json, "end", codec.encode(obj.getEnd()));
      final LocalTimeRange actual = jsonMapper.readValue(json, LocalTimeRange.class);
      System.out.println(obj);
      assertEquals(obj, actual);
    }
  }

  @Test
  public void testXmlSerializeDeserialize() throws Exception {
    final IsoLocalTimeXmlAdapter adapter = new IsoLocalTimeXmlAdapter();
    for (int i = 0; i < TEST_COUNT; ++i) {
      final LocalTimeRange obj = createLocalTimeRange();
      final String xml = xmlMapper.writerWithDefaultPrettyPrinter()
                                  .writeValueAsString(obj);
      System.out.println(xml);
      assertXPathEquals(xml, "local-time-range/start", adapter.marshal(obj.getStart()));
      assertXPathEquals(xml, "local-time-range/end", adapter.marshal(obj.getEnd()));
      final LocalTimeRange actual = xmlMapper.readValue(xml, LocalTimeRange.class);
      System.out.println(obj);
      assertEquals(obj, actual);
    }
  }
}
