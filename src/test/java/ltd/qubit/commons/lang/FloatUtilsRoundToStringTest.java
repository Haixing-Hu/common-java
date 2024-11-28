////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.lang;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import static ltd.qubit.commons.lang.FloatUtils.roundToString;

public class FloatUtilsRoundToStringTest {

  @Test
  public void testRoundToString_NoDecimal() {
    assertEquals("123", roundToString(123.456f, 0));
    assertEquals("124", roundToString(123.789f, 0));
  }

  @Test
  public void testRoundToString_OneDecimal() {
    assertEquals("123.5", roundToString(123.456f, 1));
    assertEquals("123.8", roundToString(123.789f, 1));
  }

  @Test
  public void testRoundToString_TwoDecimals() {
    assertEquals("123.46", roundToString(123.456f, 2));
    assertEquals("123.79", roundToString(123.789f, 2));
  }

  @Test
  public void testRoundToString_ThreeDecimals() {
    assertEquals("123.456", roundToString(123.456f, 3));
    assertEquals("123.789", roundToString(123.789f, 3));
  }

  @Test
  public void testRoundToString_NegativeValues() {
    assertEquals("-123.46", roundToString(-123.456f, 2));
    assertEquals("-123.79", roundToString(-123.789f, 2));
  }

  @Test
  public void testRoundToString_ZeroValue() {
    assertEquals("0", roundToString(0.0f, 0));
    assertEquals("0.0", roundToString(0.0f, 1));
    assertEquals("0.00", roundToString(0.0f, 2));
  }

  @Test
  public void testRoundToString_LargeValues() {
    assertEquals("12345.46", roundToString(12345.456f, 2));
    assertEquals("12345.79", roundToString(12345.789f, 2));
  }

  @Test
  public void testRoundToString_SmallValues() {
    assertEquals("0.46", roundToString(0.456f, 2));
    assertEquals("0.79", roundToString(0.789f, 2));
  }


  @Test
  public void testRoundToString_NaN() {
    assertEquals("NaN", roundToString(Float.NaN, 2));
  }

  @Test
  public void testRoundToString_Infinity() {
    assertEquals("Infinity", roundToString(Float.POSITIVE_INFINITY, 2));
    assertEquals("-Infinity", roundToString(Float.NEGATIVE_INFINITY, 2));
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
    assertEquals("123", roundToString(123.456f, "default", 0));
    assertEquals("124", roundToString(123.789f, "default", 0));
  }

  @Test
  public void testRoundToString2_OneDecimal() {
    assertEquals("123.5", roundToString(123.456f, "default", 1));
    assertEquals("123.8", roundToString(123.789f, "default", 1));
  }

  @Test
  public void testRoundToString2_TwoDecimals() {
    assertEquals("123.46", roundToString(123.456f, "default", 2));
    assertEquals("123.79", roundToString(123.789f, "default", 2));
  }

  @Test
  public void testRoundToString2_ThreeDecimals() {
    assertEquals("123.456", roundToString(123.456f, "default", 3));
    assertEquals("123.789", roundToString(123.789f, "default", 3));
  }

  @Test
  public void testRoundToString2_NegativeValues() {
    assertEquals("-123.46", roundToString(-123.456f, "default", 2));
    assertEquals("-123.79", roundToString(-123.789f, "default", 2));
  }

  @Test
  public void testRoundToString2_ZeroValue() {
    assertEquals("0", roundToString(0.0f, "default", 0));
    assertEquals("0.0", roundToString(0.0f, "default", 1));
    assertEquals("0.00", roundToString(0.0f, "default", 2));
  }

  @Test
  public void testRoundToString2_LargeValues() {
    assertEquals("12345.46", roundToString(12345.456f, "default", 2));
    assertEquals("12345.79", roundToString(12345.789f, "default", 2));
  }

  @Test
  public void testRoundToString2_SmallValues() {
    assertEquals("0.46", roundToString(0.456f, "default", 2));
    assertEquals("0.79", roundToString(0.789f, "default", 2));
  }

  @Test
  public void testRoundToString2_NaN() {
    assertEquals("NaN", roundToString(Float.NaN, "default", 2));
  }

  @Test
  public void testRoundToString2_Infinity() {
    assertEquals("Infinity", roundToString(Float.POSITIVE_INFINITY, "default", 2));
    assertEquals("-Infinity", roundToString(Float.NEGATIVE_INFINITY, "default", 2));
  }
}
