////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.range;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import ltd.qubit.commons.math.RandomEx;
import ltd.qubit.commons.text.jackson.CustomizedJsonMapper;
import ltd.qubit.commons.text.jackson.CustomizedXmlMapper;
import ltd.qubit.commons.text.xml.jaxb.IsoLocalDateTimeXmlAdapter;
import ltd.qubit.commons.util.codec.IsoLocalDateTimeCodec;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static ltd.qubit.commons.test.JsonUnitUtils.assertJsonNodeEquals;
import static ltd.qubit.commons.test.XmlUnitUtils.assertXPathEquals;

/**
 * Unit test of the {@link LocalDateTimeRange} class.
 *
 * @author Haixing Hu
 */
public class LocalDateTimeRangeTest {

  private static final int TEST_COUNT = 20;

  private final RandomEx random = new RandomEx();

  private final JsonMapper jsonMapper = new CustomizedJsonMapper();

  private final XmlMapper xmlMapper = new CustomizedXmlMapper();

  private LocalDateTimeRange createLocalDateTimeRange() {
    final LocalDateTime min = random.nextLocalDateTime();
    final LocalDateTime max = random.nextLocalDateTime();
    if (min.isBefore(max)) {
      return new LocalDateTimeRange(min, max);
    } else {
      return new LocalDateTimeRange(max, min);
    }
  }

  @Test
  public void testJsonSerializeDeserialize() throws Exception {
    final IsoLocalDateTimeCodec codec = new IsoLocalDateTimeCodec();
    for (int i = 0; i < TEST_COUNT; ++i) {
      final LocalDateTimeRange obj = createLocalDateTimeRange();
      obj.truncatedToSeconds();
      final String json = jsonMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
      System.out.println(json);
      assertJsonNodeEquals(json, "start", codec.encode(obj.getStart()));
      assertJsonNodeEquals(json, "end", codec.encode(obj.getEnd()));
      final LocalDateTimeRange actual = jsonMapper.readValue(json, LocalDateTimeRange.class);
      System.out.println(obj);
      assertEquals(obj, actual);
    }
  }

  @Test
  public void testXmlSerializeDeserialize() throws Exception {
    final IsoLocalDateTimeXmlAdapter adapter = new IsoLocalDateTimeXmlAdapter();
    for (int i = 0; i < TEST_COUNT; ++i) {
      final LocalDateTimeRange obj = createLocalDateTimeRange();
      obj.truncatedToSeconds();
      final String xml = xmlMapper.writerWithDefaultPrettyPrinter()
          .writeValueAsString(obj);
      System.out.println(xml);
      assertXPathEquals(xml, "local-date-time-range/start", adapter.marshal(obj.getStart()));
      assertXPathEquals(xml, "local-date-time-range/end", adapter.marshal(obj.getEnd()));
      final LocalDateTimeRange actual = xmlMapper.readValue(xml, LocalDateTimeRange.class);
      System.out.println(obj);
      assertEquals(obj, actual);
    }
  }

  @Test
  public void testJsonSerializationZeroYear() throws Exception {
    final IsoLocalDateTimeCodec codec = new IsoLocalDateTimeCodec();
    final LocalDateTime start = LocalDateTime.of(0, 6, 14, 13, 21, 2);
    final LocalDateTime end = LocalDateTime.of(2401, 10, 8, 13, 21, 2);
    final LocalDateTimeRange obj = new LocalDateTimeRange(start, end);
    final String json = jsonMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
    System.out.println(json);
    assertJsonNodeEquals(json, "start", codec.encode(obj.getStart()));
    assertJsonNodeEquals(json, "end", codec.encode(obj.getEnd()));
    final LocalDateTimeRange actual = jsonMapper.readValue(json, LocalDateTimeRange.class);
    System.out.println(obj);
    assertEquals(obj, actual);
  }
}
