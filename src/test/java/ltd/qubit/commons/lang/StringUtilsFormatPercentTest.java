////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.lang;

import java.util.Locale;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit tests for the {@link StringUtils#formatPercent()} methods.
 *
 * @author Haixing Hu
 */
public class StringUtilsFormatPercentTest {

  @Test
  public void testFormatPercent() {
    // Test with default locale and default fraction digits (2)
    assertEquals("50.00%", StringUtils.formatPercent(0.5));
    assertEquals("100.00%", StringUtils.formatPercent(1.0));
    assertEquals("0.00%", StringUtils.formatPercent(0.0));
    assertEquals("25.00%", StringUtils.formatPercent(0.25));
    assertEquals("75.00%", StringUtils.formatPercent(0.75));
    assertEquals("33.33%", StringUtils.formatPercent(0.3333));
    assertEquals("66.67%", StringUtils.formatPercent(0.6667));
    assertEquals("200.00%", StringUtils.formatPercent(2.0));
    assertEquals("-50.00%", StringUtils.formatPercent(-0.5));
  }

  @Test
  public void testFormatPercentWithFractionDigits() {
    // Test with default locale but different fraction digits
    assertEquals("50%", StringUtils.formatPercent(0.5, 0));
    assertEquals("50.0%", StringUtils.formatPercent(0.5, 1));
    assertEquals("50.00%", StringUtils.formatPercent(0.5, 2));
    assertEquals("50.000%", StringUtils.formatPercent(0.5, 3));

    assertEquals("33%", StringUtils.formatPercent(0.333, 0));
    assertEquals("33.3%", StringUtils.formatPercent(0.333, 1));
    assertEquals("33.30%", StringUtils.formatPercent(0.333, 2));
    assertEquals("33.300%", StringUtils.formatPercent(0.333, 3));
    assertEquals("33.3333%", StringUtils.formatPercent(0.333333, 4));

    assertEquals("66.7%", StringUtils.formatPercent(0.667, 1));
    assertEquals("66.67%", StringUtils.formatPercent(0.6667, 2));
    assertEquals("66.667%", StringUtils.formatPercent(0.66667, 3));
  }

  @Test
  public void testFormatPercentWithLocale() {
    // Test with different locales
    // US locale uses period as decimal separator
    assertEquals("50.00%", StringUtils.formatPercent(0.5, 2, Locale.US));
    assertEquals("33.33%", StringUtils.formatPercent(0.3333, 2, Locale.US));

    // German locale uses comma as decimal separator
    assertEquals("50,00 %", StringUtils.formatPercent(0.5, 2, Locale.GERMANY));
    assertEquals("33,33 %", StringUtils.formatPercent(0.3333, 2, Locale.GERMANY));

    // French locale uses comma as decimal separator and space before %
    assertEquals("50,00 %", StringUtils.formatPercent(0.5, 2, Locale.FRANCE));
    assertEquals("33,33 %", StringUtils.formatPercent(0.3333, 2, Locale.FRANCE));
  }

  @Test
  public void testFormatPercentEdgeCases() {
    // Test edge cases
    assertEquals("0%", StringUtils.formatPercent(0.0, 0));
    assertEquals("0.0%", StringUtils.formatPercent(0.0, 1));

    // Very small values
    assertEquals("0.01%", StringUtils.formatPercent(0.0001, 2));
    assertEquals("0.001%", StringUtils.formatPercent(0.00001, 3));

    // Very large values
    assertEquals("10,000%", StringUtils.formatPercent(100.0, 0));
    assertEquals("1,000,000%", StringUtils.formatPercent(10000.0, 0));

    // Negative values
    assertEquals("-50.00%", StringUtils.formatPercent(-0.5, 2));
    assertEquals("-33.33%", StringUtils.formatPercent(-0.3333, 2));
  }
}