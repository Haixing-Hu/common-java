////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit tests of the Readable class.
 *
 * @author Haixing Hu
 */
public class HumanReadableTest {

  @Test
  public void testFormatDuration() {
    String str = null;

    str = HumanReadable.formatDuration(0, TimeUnit.NANOSECONDS);
    assertEquals("0 nanosecond", str);
    str = HumanReadable.formatDuration(0, TimeUnit.MICROSECONDS);
    assertEquals("0 microsecond", str);
    str = HumanReadable.formatDuration(0, TimeUnit.MILLISECONDS);
    assertEquals("0 millisecond", str);
    str = HumanReadable.formatDuration(0, TimeUnit.SECONDS);
    assertEquals("0 second", str);
    str = HumanReadable.formatDuration(0, TimeUnit.MINUTES);
    assertEquals("0 minute", str);
    str = HumanReadable.formatDuration(0, TimeUnit.HOURS);
    assertEquals("0 hour", str);
    str = HumanReadable.formatDuration(0, TimeUnit.DAYS);
    assertEquals("0 day", str);

    str = HumanReadable.formatDuration(1, TimeUnit.NANOSECONDS);
    assertEquals("1 nanosecond", str);
    str = HumanReadable.formatDuration(1, TimeUnit.MICROSECONDS);
    assertEquals("1 microsecond", str);
    str = HumanReadable.formatDuration(1, TimeUnit.MILLISECONDS);
    assertEquals("1 millisecond", str);
    str = HumanReadable.formatDuration(1, TimeUnit.SECONDS);
    assertEquals("1 second", str);
    str = HumanReadable.formatDuration(1, TimeUnit.MINUTES);
    assertEquals("1 minute", str);
    str = HumanReadable.formatDuration(1, TimeUnit.HOURS);
    assertEquals("1 hour", str);
    str = HumanReadable.formatDuration(1, TimeUnit.DAYS);
    assertEquals("1 day", str);

    str = HumanReadable.formatDuration(2, TimeUnit.NANOSECONDS);
    assertEquals("2 nanoseconds", str);
    str = HumanReadable.formatDuration(2, TimeUnit.MICROSECONDS);
    assertEquals("2 microseconds", str);
    str = HumanReadable.formatDuration(2, TimeUnit.MILLISECONDS);
    assertEquals("2 milliseconds", str);
    str = HumanReadable.formatDuration(2, TimeUnit.SECONDS);
    assertEquals("2 seconds", str);
    str = HumanReadable.formatDuration(2, TimeUnit.MINUTES);
    assertEquals("2 minutes", str);
    str = HumanReadable.formatDuration(2, TimeUnit.HOURS);
    assertEquals("2 hours", str);
    str = HumanReadable.formatDuration(2, TimeUnit.DAYS);
    assertEquals("2 days", str);

    str = HumanReadable.formatDuration(999, TimeUnit.NANOSECONDS);
    assertEquals("999 nanoseconds", str);
    str = HumanReadable.formatDuration(1000, TimeUnit.NANOSECONDS);
    assertEquals("1 microsecond", str);
    str = HumanReadable.formatDuration(1001, TimeUnit.NANOSECONDS);
    assertEquals("1 microsecond 1 nanosecond", str);
    str = HumanReadable.formatDuration(1002, TimeUnit.NANOSECONDS);
    assertEquals("1 microsecond 2 nanoseconds", str);
    str = HumanReadable.formatDuration(2002, TimeUnit.NANOSECONDS);
    assertEquals("2 microseconds 2 nanoseconds", str);
    str = HumanReadable.formatDuration(2002002, TimeUnit.NANOSECONDS);
    assertEquals("2 milliseconds 2 microseconds 2 nanoseconds", str);

    str = HumanReadable.formatDuration(999, TimeUnit.MICROSECONDS);
    assertEquals("999 microseconds", str);
    str = HumanReadable.formatDuration(1000, TimeUnit.MICROSECONDS);
    assertEquals("1 millisecond", str);
    str = HumanReadable.formatDuration(1001, TimeUnit.MICROSECONDS);
    assertEquals("1 millisecond 1 microsecond", str);
    str = HumanReadable.formatDuration(1002, TimeUnit.MICROSECONDS);
    assertEquals("1 millisecond 2 microseconds", str);
    str = HumanReadable.formatDuration(2002, TimeUnit.MICROSECONDS);
    assertEquals("2 milliseconds 2 microseconds", str);
    str = HumanReadable.formatDuration(2002002, TimeUnit.MICROSECONDS);
    assertEquals("2 seconds 2 milliseconds 2 microseconds", str);

    //  TODO

  }

  @Test
  public void testFormatBytes() {
    String str = null;

    str = HumanReadable.formatBytesShort(0L);
    assertEquals("0 B", str);
    str = HumanReadable.formatBytesShort(1L);
    assertEquals("1 B", str);
    str = HumanReadable.formatBytesShort(1024L);
    assertEquals("1 KB", str);
    str = HumanReadable.formatBytesShort(1025L);
    assertEquals("1.001 KB", str);
    str = HumanReadable.formatBytesShort(1025000L);
    assertEquals("1000.977 KB", str);
    str = HumanReadable.formatBytesShort(1125000L);
    assertEquals("1.073 MB", str);

    //  TODO

  }
}