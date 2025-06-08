////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.codec;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LocalDateCodecTest {

  @Test
  public void testEncode() throws Exception {
    final LocalDateCodec codec = new LocalDateCodec();
    assertEquals("2018-01-10", codec.encode(LocalDate.of(2018, 1, 10)));
    assertEquals("0018-12-01", codec.encode(LocalDate.of(18, 12, 1)));
  }

  @Test
  public void testDecode() throws Exception {
    final LocalDateCodec codec = new LocalDateCodec();
    assertEquals(LocalDate.of(2018, 1, 10), codec.decode("2018-01-10"));
    assertEquals(LocalDate.of(2018, 1, 10), codec.decode("2018-1-10"));
    assertEquals(LocalDate.of(18, 12, 1), codec.decode("0018-12-01"));
    assertEquals(LocalDate.of(18, 12, 1), codec.decode("0018-12-1"));
  }

  @Test
  public void testDecode_slashStyle() throws Exception {
    final LocalDateCodec codec = new LocalDateCodec();
    assertEquals(LocalDate.of(2018, 1, 10), codec.decode("2018/01/10"));
    assertEquals(LocalDate.of(2018, 1, 10), codec.decode("2018/1/10"));
    assertEquals(LocalDate.of(18, 12, 1), codec.decode("0018/12/01"));
    assertEquals(LocalDate.of(18, 12, 1), codec.decode("0018/12/1"));
  }

  @Test
  public void testYearOfZero() {
    final LocalDateCodec codec = new LocalDateCodec();
    final LocalDate value = LocalDate.of(0000, 9, 21);
    assertEquals("0000-09-21", codec.encode(value));
  }
}