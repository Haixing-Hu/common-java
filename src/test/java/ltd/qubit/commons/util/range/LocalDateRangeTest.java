////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.range;

import java.time.LocalDate;
import java.time.temporal.ChronoField;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import ltd.qubit.commons.math.RandomEx;
import ltd.qubit.commons.text.jackson.CustomizedJsonMapper;
import ltd.qubit.commons.text.jackson.CustomizedXmlMapper;
import ltd.qubit.commons.text.xml.jaxb.IsoLocalDateXmlAdapter;
import ltd.qubit.commons.util.codec.IsoLocalDateCodec;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static ltd.qubit.commons.test.JsonUnitUtils.assertJsonNodeEquals;
import static ltd.qubit.commons.test.XmlUnitUtils.assertXPathEquals;

/**
 * Unit test of the {@link LocalDateRange} class.
 *
 * @author Haixing Hu
 */
public class LocalDateRangeTest {

  private static final int TEST_COUNT = 20;

  private final RandomEx random = new RandomEx();

  private final JsonMapper jsonMapper = new CustomizedJsonMapper();

  private final XmlMapper xmlMapper = new CustomizedXmlMapper();

  private LocalDateRange createLocalDateRange() {
    final LocalDate min = random.nextLocalDate();
    final LocalDate max = random.nextLocalDate();
    if (min.isBefore(max)) {
      return new LocalDateRange(min, max);
    } else {
      return new LocalDateRange(max, min);
    }
  }

  @Test
  public void testJsonSerializeDeserialize() throws Exception {
    final IsoLocalDateCodec codec = new IsoLocalDateCodec();
    for (int i = 0; i < TEST_COUNT; ++i) {
      final LocalDateRange obj = createLocalDateRange();
      final String json = jsonMapper.writerWithDefaultPrettyPrinter()
          .writeValueAsString(obj);
      System.out.println(json);
      assertJsonNodeEquals(json, "start", codec.encode(obj.getStart()));
      assertJsonNodeEquals(json, "end", codec.encode(obj.getEnd()));
      final LocalDateRange actual = jsonMapper.readValue(json, LocalDateRange.class);
      System.out.println(obj);
      assertEquals(obj, actual);
    }
  }

  @Test
  public void testXmlSerializeDeserialize() throws Exception {
    final IsoLocalDateXmlAdapter adapter = new IsoLocalDateXmlAdapter();
    for (int i = 0; i < TEST_COUNT; ++i) {
      final LocalDateRange obj = createLocalDateRange();
      final String xml = xmlMapper.writeValueAsString(obj);
      System.out.println(xml);
      assertXPathEquals(xml, "local-date-range/start", adapter.marshal(obj.getStart()));
      assertXPathEquals(xml, "local-date-range/end", adapter.marshal(obj.getEnd()));
      final LocalDateRange actual = xmlMapper.readValue(xml, LocalDateRange.class);
      System.out.println(obj);
      assertEquals(obj, actual);
    }
  }

  @Test
  public void testJsonSerializationZeroYear() throws Exception {
    final IsoLocalDateCodec codec = new IsoLocalDateCodec();
    final LocalDate start = LocalDate.of(0, 6, 14);
    final LocalDate end = LocalDate.of(2401, 10, 8);
    final LocalDateRange obj = new LocalDateRange(start, end);
    final String json = jsonMapper.writerWithDefaultPrettyPrinter()
        .writeValueAsString(obj);
    System.out.println(json);
    assertJsonNodeEquals(json, "start", codec.encode(obj.getStart()));
    assertJsonNodeEquals(json, "end", codec.encode(obj.getEnd()));
    final LocalDateRange actual = jsonMapper.readValue(json, LocalDateRange.class);
    System.out.println(obj);
    assertEquals(obj, actual);
  }

  @Test
  public void testCodecZeroYear() {
    final IsoLocalDateCodec codec = new IsoLocalDateCodec();
    final LocalDate start = LocalDate.of(0, 6, 14);
    assertEquals("0000-06-14", codec.encode(start));
  }

  @Disabled("not a bug: https://stackoverflow.com/questions/29014225/what-is-the-difference-between-year-and-year-of-era")
  @Test
  public void testLocalDateBug() {
    final LocalDate start = LocalDate.of(0, 6, 14);
    assertEquals(0, start.getLong(ChronoField.YEAR_OF_ERA));
  }
}
