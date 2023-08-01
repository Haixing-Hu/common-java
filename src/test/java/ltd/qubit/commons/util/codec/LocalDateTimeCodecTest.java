////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.codec;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LocalDateTimeCodecTest {

  @Test
  public void testDecode() throws Exception {
    final LocalDateTimeCodec codec = new LocalDateTimeCodec();

    assertEquals(LocalDateTime.of(2018, 1, 10, 23, 18, 45), codec.decode("2018-01-10 23:18:45"));
    assertEquals(LocalDateTime.of(2018, 1, 10, 23, 18, 0), codec.decode("2018-01-10 23:18"));
    assertEquals(LocalDateTime.of(2018, 1, 10, 0, 0, 0), codec.decode("2018-01-10"));
    assertEquals(LocalDateTime.of(2018, 1, 10, 23, 18, 45, 123 * 1000000),
        codec.decode("2018-01-10 23:18:45.123"));
    //
    //    codec = new LocalDateTimeCodec("yyyyMMdd[HHmmss]", true, true);
    //    assertEquals(LocalDateTime.of(2018, 1, 10, 0, 0, 0), codec.decode("20180110"));
    //    assertEquals(LocalDateTime.of(2018, 1, 10, 23, 18, 45), codec.decode("20180110231845"));
    //    assertEquals(LocalDateTime.of(2018, 1, 10, 23, 18, 0), codec.decode("201801102318"));
  }

  @Test
  public void testYearOfZero() {
    final LocalDateTimeCodec codec = new LocalDateTimeCodec();
    final LocalDateTime value = LocalDateTime.of(0000, 9, 21, 1, 21, 32);
    assertEquals("0000-09-21 01:21:32", codec.encode(value));
  }
}
