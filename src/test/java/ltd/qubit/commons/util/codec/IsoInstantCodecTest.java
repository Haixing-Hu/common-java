////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.codec;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

import org.junit.jupiter.api.Test;

import static ltd.qubit.commons.lang.DateUtils.getUtcDate;
import static ltd.qubit.commons.lang.DateUtils.getUtcDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit test of the {@link IsoInstantCodec}.
 *
 * @author Haixing Hu
 */
public class IsoInstantCodecTest {

  private static final String TRAILING_ZERO_MICROSECONDS = ".000Z";

  private String fixTrailingZeroMicroseconds(final String str) {
    String result = str;
    if (result.endsWith(TRAILING_ZERO_MICROSECONDS)) {
      result = result.substring(0, result.length() - TRAILING_ZERO_MICROSECONDS.length());
      result = result + "Z";
    }
    return result;
  }

  @Test
  public void testEncode() throws Exception {
    final IsoInstantCodec codec = new IsoInstantCodec();
    //    DateTimeFormatter formatter = DateTimeFormatter.ISO_INSTANT;
    final DateTimeFormatter formatter = new DateTimeFormatterBuilder()
        .appendInstant(3).toFormatter();
    final Instant value = Instant.ofEpochMilli(getUtcDate(2018, 9, 21).getTime());
    assertEquals("2018-09-21T00:00:00Z", codec.encode(value));
    assertEquals("2018-09-21T00:00:00.000Z", formatter.format(value));

    codec.setZoneId(ZoneId.of("UTC+8"));
    assertEquals("2018-09-21T08:00:00Z", codec.encode(value));

    final Instant value2 = Instant.ofEpochMilli(value.toEpochMilli() + 123L);
    assertEquals("2018-09-21T08:00:00.123Z", codec.encode(value2));

    codec.setZoneId(ZoneId.of("UTC"));
    final Instant now = Instant.now();
    final String actual = codec.encode(now);
    final String expected = formatter.format(now);
    System.out.println(expected);
    assertEquals(expected, fixTrailingZeroMicroseconds(actual));
  }

  @Test
  public void testDecode() throws Exception {
    final IsoInstantCodec codec = new IsoInstantCodec();

    Instant value = Instant.ofEpochMilli(getUtcDate(2018, 9, 21).getTime());
    assertEquals(codec.encode(value), codec.encode(codec.decode("2018-09-21T00:00:00Z")));
    assertEquals(value, codec.decode("2018-09-21"));
    assertEquals(value, codec.decode("2018-09-21 T00:00"));
    assertEquals(value, codec.decode("2018-09-21 00:00:00"));
    assertEquals(value, codec.decode("2018-09-21 00:00:00Z"));
    assertEquals(value, codec.decode("2018-09-21 00:00:00UTC"));
    assertEquals(value, codec.decode("2018-09-21 00:00:00 UTC"));
    assertEquals(value, codec.decode("2018-09-21 00:00:00 GMT"));
    assertEquals(value, codec.decode("2018-09-21 00:00:00+0000"));
    assertEquals(value, codec.decode("2018-09-21 00:00:00 +0000"));
    assertEquals(value, codec.decode("2018-09-21 00:00:00.000 +0000"));

    value = Instant.ofEpochMilli(getUtcDateTime(2018, 9, 21, 1, 2, 31, 123).getTime());
    assertEquals(codec.encode(value), codec.encode(codec.decode("2018-09-21T01:02:31.123Z")));
    assertEquals(value, codec.decode("2018-09-21 T01:02:31.123"));
    assertEquals(value, codec.decode("2018-09-21 01:02:31.123"));
    assertEquals(value, codec.decode("2018-09-21 01:02:31.123Z"));
    assertEquals(value, codec.decode("2018-09-21 01:02:31.123UTC"));
    assertEquals(value, codec.decode("2018-09-21 01:02:31.123 UTC"));
    assertEquals(value, codec.decode("2018-09-21 01:02:31.123 GMT"));
    assertEquals(value, codec.decode("2018-09-21 01:02:31.123+0000"));
    assertEquals(value, codec.decode("2018-09-21 01:02:31.123 +0000"));
    assertEquals(value, codec.decode("2018-09-21 01:02:31.123 +0000"));
  }

  @Test
  public void testYearOfZero() {
    final IsoInstantCodec codec = new IsoInstantCodec();
    final Instant value = LocalDate.of(0000, 9, 21).atStartOfDay(ZoneId.of("UTC")).toInstant();
    assertEquals("0000-09-21T00:00:00Z", codec.encode(value));
  }
}
