////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.lang;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InstantUtilsTest {

  @Test
  public void safelyPlusPositiveAmountWithinRange() {
    final Instant instant = Instant.parse("2023-01-01T00:00:00Z");
    final Instant result = InstantUtils.safePlus(instant, 1, ChronoUnit.DAYS);
    assertEquals(Instant.parse("2023-01-02T00:00:00Z"), result);
  }

  @Test
  public void safelyPlusNegativeAmountWithinRange() {
    final Instant instant = Instant.parse("2023-01-01T00:00:00Z");
    final Instant result = InstantUtils.safePlus(instant, -1, ChronoUnit.DAYS);
    assertEquals(Instant.parse("2022-12-31T00:00:00Z"), result);
  }

  @Test
  public void safelyPlusPositiveAmountExceedingRange() {
    final Instant instant = Instant.parse("2023-01-01T00:00:00Z");
    final Instant result = InstantUtils.safePlus(instant, Long.MAX_VALUE, ChronoUnit.DAYS);
    assertEquals(Instant.MAX, result);
  }

  @Test
  public void safelyPlusNegativeAmountExceedingRange() {
    final Instant instant = Instant.parse("2023-01-01T00:00:00Z");
    final Instant result = InstantUtils.safePlus(instant, Long.MIN_VALUE, ChronoUnit.DAYS);
    assertEquals(Instant.MIN, result);
  }

  @Test
  public void safelyPlusZeroAmount() {
    final Instant instant = Instant.parse("2023-01-01T00:00:00Z");
    final Instant result = InstantUtils.safePlus(instant, 0, ChronoUnit.DAYS);
    assertEquals(instant, result);
  }
  @Test
  public void safelyMinusPositiveAmountWithinRange() {
    final Instant instant = Instant.parse("2023-01-01T00:00:00Z");
    final Instant result = InstantUtils.safeMinus(instant, 1, ChronoUnit.DAYS);
    assertEquals(Instant.parse("2022-12-31T00:00:00Z"), result);
  }

  @Test
  public void safelyMinusNegativeAmountWithinRange() {
    final Instant instant = Instant.parse("2023-01-01T00:00:00Z");
    final Instant result = InstantUtils.safeMinus(instant, -1, ChronoUnit.DAYS);
    assertEquals(Instant.parse("2023-01-02T00:00:00Z"), result);
  }

  @Test
  public void safelyMinusPositiveAmountExceedingRange() {
    final Instant instant = Instant.parse("2023-01-01T00:00:00Z");
    final Instant result = InstantUtils.safeMinus(instant, Long.MAX_VALUE, ChronoUnit.DAYS);
    assertEquals(Instant.MIN, result);
  }

  @Test
  public void safelyMinusNegativeAmountExceedingRange() {
    final Instant instant = Instant.parse("2023-01-01T00:00:00Z");
    final Instant result = InstantUtils.safeMinus(instant, Long.MIN_VALUE, ChronoUnit.DAYS);
    assertEquals(Instant.MAX, result);
  }

  @Test
  public void safelyMinusZeroAmount() {
    final Instant instant = Instant.parse("2023-01-01T00:00:00Z");
    final Instant result = InstantUtils.safeMinus(instant, 0, ChronoUnit.DAYS);
    assertEquals(instant, result);
  }
}
