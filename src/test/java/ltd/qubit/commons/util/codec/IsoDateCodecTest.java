////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.codec;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import org.junit.jupiter.api.Test;

import static ltd.qubit.commons.lang.DateUtils.getUtcDate;
import static ltd.qubit.commons.lang.DateUtils.getUtcDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit test of the {@link IsoDateCodec}.
 *
 * @author Haixing Hu
 */
public class IsoDateCodecTest {

  @Test
  public void testEncode() throws Exception {
    final IsoDateCodec codec = new IsoDateCodec();

    final Date value = getUtcDate(2018, 9, 21);
    assertEquals("2018-09-21T00:00:00.000Z", codec.encode(value));

    codec.setZoneId(ZoneId.of("UTC+8"));
    assertEquals("2018-09-21T08:00:00.000Z", codec.encode(value));
  }

  @Test
  public void testDecode() throws Exception {
    final IsoDateCodec codec = new IsoDateCodec();

    Date value = getUtcDate(2018, 9, 21);
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

    value = getUtcDateTime(2018, 9, 21, 1, 2, 31, 123);
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
    final IsoDateCodec codec = new IsoDateCodec();
    final Date value = Date.from(LocalDate.of(0000, 9, 21)
                                          .atStartOfDay(ZoneId.of("UTC")).toInstant());
    assertEquals("0000-09-21T00:00:00.000Z", codec.encode(value));
  }
}
