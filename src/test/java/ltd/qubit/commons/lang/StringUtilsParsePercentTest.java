////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.lang;

import java.text.ParseException;
import java.util.Locale;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit tests for the {@link StringUtils#parsePercent()} methods.
 *
 * @author Haixing Hu
 */
public class StringUtilsParsePercentTest {

  private static final double DELTA = 0.0000001;

  @Test
  public void testParsePercent() throws ParseException {
    // Test with default locale and default fraction digits (2)
    assertEquals(0.5, StringUtils.parsePercent("50%"), DELTA);
    assertEquals(1.0, StringUtils.parsePercent("100%"), DELTA);
    assertEquals(0.0, StringUtils.parsePercent("0%"), DELTA);
    assertEquals(0.25, StringUtils.parsePercent("25%"), DELTA);
    assertEquals(0.75, StringUtils.parsePercent("75%"), DELTA);
    assertEquals(0.3333, StringUtils.parsePercent("33.33%"), DELTA);
    assertEquals(0.6667, StringUtils.parsePercent("66.67%"), DELTA);
    assertEquals(2.0, StringUtils.parsePercent("200%"), DELTA);
    assertEquals(-0.5, StringUtils.parsePercent("-50%"), DELTA);
  }

  @Test
  public void testParsePercentWithFractionDigits() throws ParseException {
    // Test with default locale but different fraction digits
    assertEquals(0.5, StringUtils.parsePercent("50%", 0), DELTA);
    assertEquals(0.5, StringUtils.parsePercent("50.0%", 1), DELTA);
    assertEquals(0.5, StringUtils.parsePercent("50.00%", 2), DELTA);
    assertEquals(0.5, StringUtils.parsePercent("50.000%", 3), DELTA);

    assertEquals(0.333, StringUtils.parsePercent("33.3%", 1), DELTA);
    assertEquals(0.333, StringUtils.parsePercent("33.30%", 2), DELTA);
    assertEquals(0.333, StringUtils.parsePercent("33.300%", 3), DELTA);
    assertEquals(0.33333, StringUtils.parsePercent("33.333%", 3), DELTA);

    assertEquals(0.667, StringUtils.parsePercent("66.7%", 1), DELTA);
    assertEquals(0.6667, StringUtils.parsePercent("66.67%", 2), DELTA);
    assertEquals(0.66667, StringUtils.parsePercent("66.667%", 3), DELTA);
  }

  @Test
  public void testParsePercentWithLocale() throws ParseException {
    // Test with different locales
    // US locale uses period as decimal separator
    assertEquals(0.5, StringUtils.parsePercent("50.00%", 2, Locale.US), DELTA);
    assertEquals(0.3333, StringUtils.parsePercent("33.33%", 2, Locale.US), DELTA);

    // German locale uses comma as decimal separator
    assertEquals(0.5, StringUtils.parsePercent("50,00 %", 2, Locale.GERMANY), DELTA);
    assertEquals(0.3333, StringUtils.parsePercent("33,33 %", 2, Locale.GERMANY), DELTA);

    // French locale uses comma as decimal separator and space before %
    assertEquals(0.5, StringUtils.parsePercent("50,00 %", 2, Locale.FRANCE), DELTA);
    assertEquals(0.3333, StringUtils.parsePercent("33,33 %", 2, Locale.FRANCE), DELTA);
  }

  @Test
  public void testParsePercentEdgeCases() throws ParseException {
    // Test edge cases
    assertEquals(0.0, StringUtils.parsePercent("0%", 0), DELTA);
    assertEquals(0.0, StringUtils.parsePercent("0.0%", 1), DELTA);

    // Very small values
    assertEquals(0.0001, StringUtils.parsePercent("0.01%", 2), DELTA);
    assertEquals(0.00001, StringUtils.parsePercent("0.001%", 3), DELTA);

    // Very large values
    assertEquals(100.0, StringUtils.parsePercent("10,000%", 0), DELTA);
    assertEquals(10000.0, StringUtils.parsePercent("1,000,000%", 0), DELTA);

    // Negative values
    assertEquals(-0.5, StringUtils.parsePercent("-50.00%", 2), DELTA);
    assertEquals(-0.3333, StringUtils.parsePercent("-33.33%", 2), DELTA);
  }

  @Test
  public void testParsePercentInvalidInput() {
    // Test invalid inputs
    assertThrows(ParseException.class, () -> StringUtils.parsePercent("abc"));
    assertThrows(ParseException.class, () -> StringUtils.parsePercent("50"));
    assertThrows(ParseException.class, () -> StringUtils.parsePercent(""));
    // assertThrows(ParseException.class, () -> StringUtils.parsePercent("50%%"));
    assertThrows(ParseException.class, () -> StringUtils.parsePercent("50 percent"));
    assertThrows(ParseException.class, () -> StringUtils.parsePercent("percent"));
    assertThrows(ParseException.class, () -> StringUtils.parsePercent("50.00.00%"));
  }
}