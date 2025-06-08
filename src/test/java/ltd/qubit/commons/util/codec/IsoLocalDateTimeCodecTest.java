////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.codec;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IsoLocalDateTimeCodecTest {

  @Test
  public void testDecode() throws Exception {
    final IsoLocalDateTimeCodec codec = new IsoLocalDateTimeCodec();

    assertEquals(LocalDateTime.of(2018, 1, 10, 23, 18, 45), codec.decode("2018-01-10T23:18:45"));
    assertEquals(LocalDateTime.of(2018, 1, 10, 23, 18, 0), codec.decode("2018-01-10T23:18:00"));
    assertEquals(LocalDateTime.of(2018, 1, 10, 0, 0, 0), codec.decode("2018-01-10T00:00:00"));
    assertEquals(LocalDateTime.of(2018, 1, 10, 23, 18, 45), codec.decode("2018-01-10T23:18:45"));
    assertEquals(LocalDateTime.of(2018, 1, 10, 23, 18, 45), codec.decode("2018-01-10 23:18:45"));
    assertEquals(LocalDateTime.of(2018, 1, 10, 23, 18, 45, 123000000),
        codec.decode("2018-01-10 23:18:45.123"));

    //    codec = new LocalDateTimeCodec("yyyyMMdd[HHmmss]", true, true);
    //    assertEquals(LocalDateTime.of(2018, 1, 10, 0, 0, 0), codec.decode("20180110"));
    //    assertEquals(LocalDateTime.of(2018, 1, 10, 23, 18, 45), codec.decode("20180110231845"));
    //    assertEquals(LocalDateTime.of(2018, 1, 10, 23, 18, 0), codec.decode("201801102318"));
  }

  @Test
  public void testYearOfZero() {
    final IsoLocalDateTimeCodec codec = new IsoLocalDateTimeCodec();
    final LocalDateTime value = LocalDateTime.of(0000, 9, 21, 1, 21, 32);
    assertEquals("0000-09-21 01:21:32", codec.encode(value));
  }
}