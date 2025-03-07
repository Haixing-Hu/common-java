////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.codec;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LocalDateTimeCodecTest {

  @Test
  public void testDecode() throws Exception {
    LocalDateTimeCodec codec = new LocalDateTimeCodec();

    assertEquals(LocalDateTime.of(2018, 1, 10, 23, 18, 45), codec.decode("2018-01-10 23:18:45"));
    assertEquals(LocalDateTime.of(2018, 1, 10, 23, 18, 0), codec.decode("2018-1-10 23:18"));
    assertEquals(LocalDateTime.of(2018, 1, 10, 23, 8, 45), codec.decode("2018-01-10 23:8:45"));
    assertEquals(LocalDateTime.of(2018, 1, 10, 3, 8, 3), codec.decode("2018-1-10 3:8:3"));
    assertEquals(LocalDateTime.of(2018, 1, 10, 0, 0, 0), codec.decode("2018-01-10"));
    assertEquals(LocalDateTime.of(2018, 1, 10, 23, 18, 45, 123 * 1000000),
        codec.decode("2018-01-10 23:18:45.123"));
    assertEquals(LocalDateTime.of(2018, 1, 10, 23, 18, 45, 120 * 1000000),
        codec.decode("2018-01-10 23:18:45.12"));
    assertEquals(LocalDateTime.of(2018, 1, 10, 23, 18, 45, 100 * 1000000),
        codec.decode("2018-01-10 23:18:45.1"));
    assertEquals(LocalDateTime.of(1936, 8, 5, 0, 0, 0), codec.decode("1936-08-5 00:00:00.0"));


    codec = new LocalDateTimeCodec("yyyyMMdd",
        new String[]{"yyyyMMdd", "yyyyMMddHHmm", "yyyyMMddHHmmss"}, true, true);
    assertEquals(LocalDateTime.of(2018, 1, 10, 0, 0, 0), codec.decode("20180110"));
    assertEquals(LocalDateTime.of(2018, 1, 10, 23, 18, 45), codec.decode("20180110231845"));
    assertEquals(LocalDateTime.of(2018, 1, 10, 23, 18, 0), codec.decode("201801102318"));
  }

  @Test
  public void testDecode_slashStyle() throws Exception {
    final LocalDateTimeCodec codec = new LocalDateTimeCodec();

    assertEquals(LocalDateTime.of(2018, 1, 10, 23, 18, 45), codec.decode("2018/01/10 23:18:45"));
    assertEquals(LocalDateTime.of(2018, 1, 10, 23, 18, 0), codec.decode("2018/01/10 23:18"));
    assertEquals(LocalDateTime.of(2018, 1, 10, 23, 18, 5), codec.decode("2018/1/10 23:18:5"));
    assertEquals(LocalDateTime.of(2018, 1, 10, 3, 8, 0), codec.decode("2018/01/10 3:8"));
    assertEquals(LocalDateTime.of(2018, 1, 10, 0, 0, 0), codec.decode("2018/1/10"));
    assertEquals(LocalDateTime.of(2018, 1, 10, 23, 18, 45, 123 * 1000000),
        codec.decode("2018/1/10 23:18:45.123"));
    assertEquals(LocalDateTime.of(2018, 1, 10, 23, 18, 45, 120 * 1000000),
        codec.decode("2018/01/10 23:18:45.12"));
    assertEquals(LocalDateTime.of(2018, 1, 10, 23, 18, 45, 100 * 1000000),
        codec.decode("2018/1/10 23:18:45.1"));
    assertEquals(LocalDateTime.of(1936, 8, 5, 0, 0, 0), codec.decode("1936/8/5 00:00:00.0"));
  }

  @Test
  public void testYearOfZero() {
    final LocalDateTimeCodec codec = new LocalDateTimeCodec();
    final LocalDateTime value = LocalDateTime.of(0000, 9, 21, 1, 21, 32);
    assertEquals("0000-09-21 01:21:32", codec.encode(value));
  }

  @Test
  public void testParse() throws DecodingException {
    final String s1 = "1952-10-22 00:00:00.0";
    final LocalDateTimeCodec codec = new LocalDateTimeCodec();
    final LocalDateTime value = codec.decode(s1);
    assertEquals(LocalDateTime.of(1952, 10, 22, 0, 0, 0), value);
    assertEquals("1952-10-22 00:00:00", codec.encode(value));
  }

  @Test
  public void testDataTimeFormatPattern() {
    final String[] dateStrings = {
        "2018-01-10",
        "2018-01-10 23:18:45",
        "2018-01-10T23:18:45",
        "2018-01-10T23:18:45.1",
        "2018-01-10T23:18:45.12",
        "2018-01-10T23:18:45.123",
        "2018-01-10 23:18:45.1",
        "2018-01-10 23:18:45.12",
        "2018-01-10 23:18:45.123"
    };
    final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu-MM-dd[[' ']['T']HH:mm[:ss[.SSS]]]");
    for (final String dateString : dateStrings) {
      try {
        final LocalDateTime dateTime = LocalDateTime.parse(dateString, formatter);
        System.out.println("Parsed date: " + dateTime);
      } catch (final DateTimeParseException e) {
        System.out.println("Error parsing date: " + dateString + " -> " + e.getMessage());
      }
    }
  }
  @Test
  public void testDataTimeFormatPattern2() {
    final String[] dateStrings = {
        "2018-01-10",
        "2018-01-10 23:18:45",
        "2018-01-10 23:18:45.1",
        "2018-01-10 23:18:45.12",
        "2018-01-10 23:18:45.123",
        "2018-01-10T23:18:45",
        "2018-01-10T23:18:45.1",
        "2018-01-10T23:18:45.12",
        "2018-01-10T23:18:45.123",
    };

    final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("uuuu-MM-dd");
    final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("uuuu-MM-dd[[' ']['T']HH:mm[:ss[.SSS]]]");

    for (final String dateString : dateStrings) {
      try {
        final LocalDateTime dateTime = LocalDateTime.parse(dateString, dateTimeFormatter);
        System.out.println("Parsed date time: " + dateTime);
      } catch (final DateTimeParseException e) {
        System.out.println("Error parsing date time: " + dateString + " -> " + e.getMessage());
      }
    }
  }
}