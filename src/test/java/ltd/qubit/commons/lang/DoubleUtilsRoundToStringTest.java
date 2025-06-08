////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.lang;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import static ltd.qubit.commons.lang.DoubleUtils.roundToString;

public class DoubleUtilsRoundToStringTest {

  @Test
  public void testRoundToString_NoDecimal() {
    assertEquals("123", roundToString(123.456, 0));
    assertEquals("124", roundToString(123.789, 0));
  }

  @Test
  public void testRoundToString_OneDecimal() {
    assertEquals("123.5", roundToString(123.456, 1));
    assertEquals("123.8", roundToString(123.789, 1));
  }

  @Test
  public void testRoundToString_TwoDecimals() {
    assertEquals("123.46", roundToString(123.456, 2));
    assertEquals("123.79", roundToString(123.789, 2));
  }

  @Test
  public void testRoundToString_ThreeDecimals() {
    assertEquals("123.456", roundToString(123.456, 3));
    assertEquals("123.789", roundToString(123.789, 3));
  }

  @Test
  public void testRoundToString_NegativeValues() {
    assertEquals("-123.46", roundToString(-123.456, 2));
    assertEquals("-123.79", roundToString(-123.789, 2));
  }

  @Test
  public void testRoundToString_ZeroValue() {
    assertEquals("0", roundToString(0.0, 0));
    assertEquals("0.0", roundToString(0.0, 1));
    assertEquals("0.00", roundToString(0.0, 2));
  }

  @Test
  public void testRoundToString_LargeValues() {
    assertEquals("123456789.46", roundToString(123456789.456, 2));
    assertEquals("123456789.79", roundToString(123456789.789, 2));
  }

  @Test
  public void testRoundToString_SmallValues() {
    assertEquals("0.46", roundToString(0.456, 2));
    assertEquals("0.79", roundToString(0.789, 2));
  }

  @Test
  public void testRoundToString_NaN() {
    assertEquals("NaN", roundToString(Double.NaN, 2));
  }

  @Test
  public void testRoundToString_Infinity() {
    assertEquals("Infinity", roundToString(Double.POSITIVE_INFINITY, 2));
    assertEquals("-Infinity", roundToString(Double.NEGATIVE_INFINITY, 2));
  }

  @Test
  public void testRoundToString2_ValueIsNull() {
    assertEquals("default", roundToString(null, "default", 2));
    assertEquals("anotherDefault", roundToString(null, "anotherDefault", 3));
  }

  @Test
  public void testRoundToString2_DefaultValueIsNull() {
    assertNull(roundToString(null, null, 2));
  }

  @Test
  public void testRoundToString2_NoDecimal() {
    assertEquals("123", roundToString(123.456, "default", 0));
    assertEquals("124", roundToString(123.789, "default", 0));
  }

  @Test
  public void testRoundToString2_OneDecimal() {
    assertEquals("123.5", roundToString(123.456, "default", 1));
    assertEquals("123.8", roundToString(123.789, "default", 1));
  }

  @Test
  public void testRoundToString2_TwoDecimals() {
    assertEquals("123.46", roundToString(123.456, "default", 2));
    assertEquals("123.79", roundToString(123.789, "default", 2));
  }

  @Test
  public void testRoundToString2_ThreeDecimals() {
    assertEquals("123.456", roundToString(123.456, "default", 3));
    assertEquals("123.789", roundToString(123.789, "default", 3));
  }

  @Test
  public void testRoundToString2_NegativeValues() {
    assertEquals("-123.46", roundToString(-123.456, "default", 2));
    assertEquals("-123.79", roundToString(-123.789, "default", 2));
  }

  @Test
  public void testRoundToString2_ZeroValue() {
    assertEquals("0", roundToString(0.0, "default", 0));
    assertEquals("0.0", roundToString(0.0, "default", 1));
    assertEquals("0.00", roundToString(0.0, "default", 2));
  }

  @Test
  public void testRoundToString2_LargeValues() {
    assertEquals("123456789.46", roundToString(123456789.456, "default", 2));
    assertEquals("123456789.79", roundToString(123456789.789, "default", 2));
  }

  @Test
  public void testRoundToString2_SmallValues() {
    assertEquals("0.46", roundToString(0.456, "default", 2));
    assertEquals("0.79", roundToString(0.789, "default", 2));
  }

  @Test
  public void testRoundToString2_NaN() {
    assertEquals("NaN", roundToString(Double.NaN, "default", 2));
  }

  @Test
  public void testRoundToString2_Infinity() {
    assertEquals("Infinity", roundToString(Double.POSITIVE_INFINITY, "default", 2));
    assertEquals("-Infinity", roundToString(Double.NEGATIVE_INFINITY, "default", 2));
  }
}