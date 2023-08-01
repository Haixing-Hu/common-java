////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.lang;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.GregorianCalendar;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class StringUtilsTypeTest {

  @Test
  public void testTruncateUtf16() {
    assertEquals("hello", StringUtils.truncateUtf16("hello", 20));
    assertEquals("hell", StringUtils.truncateUtf16("hello", 4));
    assertEquals(null, StringUtils.truncateUtf16(null, 10));
    assertEquals("\\uabcd", StringUtils.truncateUtf16("\\uabcd", 10));
    assertEquals("abcdefghijk", StringUtils
        .truncateUtf16("abcdefghijk\\uabcd", 11));

    try {
      StringUtils.truncateUtf16("hello", -10);
      fail("should throw");
    } catch (final IllegalArgumentException e) {
      // pass
    }
  }

  @Test
  public void testTruncateUtf8() {
    assertEquals(null, StringUtils.truncateUtf8(null, 20));
    assertEquals(null, StringUtils.truncateUtf8(null, -10));
    assertEquals("hello", StringUtils.truncateUtf8("hello", 20));
    assertEquals("hell", StringUtils.truncateUtf8("hello", 4));
    assertEquals("hello", StringUtils.truncateUtf8("hello我", 6));
  }

  @Test
  public void testToCharArrayString_String_StringBuilder() {
    final StringBuilder builderin = new StringBuilder();
    String str = "";
    String strout = "";

    final Object[][] Cases = {
        {"0", "[\\u0030]"},
        {"0x00", "[\\u0030,\\u0078,\\u0030,\\u0030]"},
        {"0xAA", "[\\u0030,\\u0078,\\u0041,\\u0041]"},
        {"0xFF", "[\\u0030,\\u0078,\\u0046,\\u0046]"}
    };

    for (final Object[] ele : Cases) {
      builderin.setLength(0);
      str = (String) ele[0];
      strout = (String) ele[1];
      StringUtils.toCharArrayString(str, builderin);
      assertEquals(strout, builderin.toString());
    }
  }

  @Test
  public void testToCharArrayString_String() {
    String str = "";
    String strout = "";

    final Object[][] Cases = {
        {"0", "[\\u0030]"},
        {"0x00", "[\\u0030,\\u0078,\\u0030,\\u0030]"},
        {"0xAA", "[\\u0030,\\u0078,\\u0041,\\u0041]"},
        {"0xFF", "[\\u0030,\\u0078,\\u0046,\\u0046]"}
    };

    for (final Object[] ele : Cases) {
      str = (String) ele[0];
      strout = (String) ele[1];
      assertEquals(strout, StringUtils.toCharArrayString(str));
    }
  }

  @Test
  public void testToBoolean_String() {
    String str = "";
    boolean booleanout;

    final Object[][] Cases = {
        {null, false},
        {"true", true},
        {"false", false},
        {"  true", true},
        {"false  ", false},
        {"abc", false},
        {"  abc", false}
    };

    for (final Object[] ele : Cases) {
      str = (String) ele[0];
      booleanout = (Boolean) ele[1];
      assertEquals(booleanout, StringUtils.toBoolean(str));
    }
  }

  @Test
  public void testToBoolean_String_Boolean() {
    String str = "";
    boolean booleanin;
    boolean booleanout;

    final Object[][] Cases = {
        {null, true, true},
        {null, false, false},
        {"true", true, true},
        {"true", false, true},
        {"false", true, false},
        {"false", false, false},
        {"  true", true, true},
        {"  true", false, true},
        {"false  ", true, false},
        {"false  ", false, false},
        {"abc", true, true},
        {"  abc", false, false}
    };

    for (final Object[] ele : Cases) {
      str = (String) ele[0];
      booleanin = (Boolean) ele[1];
      booleanout = (Boolean) ele[2];
      assertEquals(booleanout, StringUtils.toBoolean(str, booleanin));
    }
  }

  @Test
  public void testToBooleanObject_String() {
    String str = "";
    Boolean booleanout = null;

    final Object[][] Cases = {
        {null, null},
        {"true", true},
        {"false", false},
        {"  true", true},
        {"false  ", false},
        {"abc", null},
        {"  abc", null}
    };

    for (final Object[] ele : Cases) {
      str = (String) ele[0];
      booleanout = (Boolean) ele[1];
      assertEquals(booleanout, StringUtils.toBooleanObject(str));
    }
  }

  @Test
  public void testToBooleanObject_String_Boolean() {
    String str = "";
    Boolean booleanin;
    Boolean booleanout;

    final Object[][] Cases = {
        {"true", true, true},
        {"true", false, true},
        {"true", null, true},

        {"false", true, false},
        {"false", false, false},
        {"false", null, false},

        {"  true", true, true},
        {"  true", false, true},
        {"  true", null, true},

        {"false  ", true, false},
        {"false  ", false, false},
        {"false  ", null, false},

        {"abc", true, true},
        {"  abc", false, false},
        {"abc", null, null},

        {null, true, true},
        {null, false, false},
        {null, null, null}
    };

    for (final Object[] ele : Cases) {
      str = (String) ele[0];
      booleanin = (Boolean) ele[1];
      booleanout = (Boolean) ele[2];
      assertEquals(booleanout, StringUtils.toBooleanObject(str, booleanin));
    }
  }

  @Test
  public void testToChar_String() {
    String str = "";
    char charout = (char) 0;

    final Object[][] Cases = {
        {null, (char) 0},
        {"", (char) 0},
        {"abc", 'a'}
    };

    for (final Object[] ele : Cases) {
      str = (String) ele[0];
      charout = (Character) ele[1];
      assertEquals(charout, StringUtils.toChar(str));
    }
  }

  @Test
  public void testToChar_String_Char() {
    String str = "";
    char charin = 0;
    char charout = 0;

    final Object[][] Cases = {
        {null, (char) 0, (char) 0},
        {"", (char) 0, (char) 0},
        {null, 'a', 'a'},
        {"", 'b', 'b'},
        {"abc", 'a', 'a'},
        {"abc", 'f', 'a'}
    };

    for (final Object[] ele : Cases) {
      str = (String) ele[0];
      charin = (Character) ele[1];
      charout = (Character) ele[2];
      assertEquals(charout, StringUtils.toChar(str, charin));
    }
  }

  @Test
  public void testToCharObject_String() {
    String str = "";
    Character charout = (char) 0;

    final Object[][] Cases = {
        {null, null},
        {"", null},
        {"abc", 'a'}
    };

    for (final Object[] ele : Cases) {
      str = (String) ele[0];
      charout = (Character) ele[1];
      assertEquals(charout, StringUtils.toCharObject(str));
    }
  }

  @Test
  public void testToCharObject_String_Character() {
    String str = "";
    Character charin = (char) 0;
    Character charout = (char) 0;

    final Object[][] Cases = {
        {null, (char) 0, (char) 0},
        {null, 'a', 'a'},
        {null, null, null},
        {"", (char) 0, (char) 0},
        {"", 'b', 'b'},
        {"", null, null},
        {"abc", 'a', 'a'},
        {"abc", 'f', 'a'},
        {"abc", null, 'a'}
    };

    for (final Object[] ele : Cases) {
      str = (String) ele[0];
      charin = (Character) ele[1];
      charout = (Character) ele[2];
      assertEquals(charout, StringUtils.toCharObject(str, charin));
    }
  }

  @Test
  public void testToByte_String() {
    String str;
    byte byteout = 0;

    final Object[][] Cases = {
        {null, (byte) 0},
        {"0", (byte) 0},
        {"abc", (byte) 0},
        {"123", (byte) 123}
    };

    for (final Object[] ele : Cases) {
      str = (String) ele[0];
      byteout = (Byte) ele[1];
      assertEquals(byteout, StringUtils.toByte(str));
    }
  }

  @Test
  public void testToByte_String_Byte() {
    String str = "";
    byte bytein = (byte) 0;
    byte byteout = (byte) 0;

    final Object[][] Cases = {
        {null, (byte) 0, (byte) 0},
        {"0", (byte) 0, (byte) 0},
        {"abc", (byte) 20, (byte) 20},
        {"123", (byte) 0, (byte) 123},
        {"12f", (byte) 0, (byte) 12f},
        {"0x12", (byte) 0, (byte) 0x12}
    };

    for (final Object[] ele : Cases) {
      str = (String) ele[0];
      bytein = (Byte) ele[1];
      byteout = (Byte) ele[2];
      assertEquals(byteout, StringUtils.toByte(str, bytein));
    }
  }

  @Test
  public void testToByteObject_String() {
    String str = "";
    Byte byteout = (byte) 0;

    final Object[][] Cases = {
        {null, null},
        {"0", (byte) 0},
        {"abc", null},
        {"123", (byte) 123},
        {"12f", (byte) 12f},
        {"0x12", (byte) 0x12}
    };

    for (final Object[] ele : Cases) {
      str = (String) ele[0];
      byteout = (Byte) ele[1];
      assertEquals(byteout, StringUtils.toByteObject(str));
    }
  }

  @Test
  public void testToByteObject_String_Byte() {
    String str = "";
    Byte bytein = (byte) 0;
    Byte byteout = (byte) 0;

    final Object[][] Cases = {
        {null, null, null},
        {null, (byte) 0, (byte) 0},
        {"0", (byte) 0, (byte) 0},
        {"abc", (byte) 20, (byte) 20},
        {"abc", null, null},
        {"123", (byte) 0, (byte) 123},
        {"12f", (byte) 0, (byte) 12f},
        {"0x12", (byte) 0, (byte) 0x12}
    };

    for (final Object[] ele : Cases) {
      str = (String) ele[0];
      bytein = (Byte) ele[1];
      byteout = (Byte) ele[2];
      assertEquals(byteout, StringUtils.toByteObject(str, bytein));
    }
  }

  @Test
  public void testToShort_String() {
    String str = "";
    short shortout = (short) 0;

    final Object[][] Cases = {
        {null, (short) 0},
        {"0", (short) 0},
        {"abc", (short) 0},
        {"123", (short) 123},
        {"12f", (short) 12f},
        {"0x12", (short) 0x12}
    };

    for (final Object[] ele : Cases) {
      str = (String) ele[0];
      shortout = (Short) ele[1];
      assertEquals(shortout, StringUtils.toShort(str));
    }
  }

  @Test
  public void testToShort_String_Short() {
    String str = "";
    short shortin = (short) 0;
    short shortout = (short) 0;

    final Object[][] Cases = {
        {null, (short) 0, (short) 0},
        {"0", (short) 0, (short) 0},
        {"abc", (short) 20, (short) 20},
        {"123", (short) 0, (short) 123},
        {"12f", (short) 0, (short) 12f},
        {"0x12", (short) 0, (short) 0x12}
    };

    for (final Object[] ele : Cases) {
      str = (String) ele[0];
      shortin = (Short) ele[1];
      shortout = (Short) ele[2];
      assertEquals(shortout, StringUtils.toShort(str, shortin));
    }
  }

  @Test
  public void testToShortObject_String() {
    String str = "";
    Short shortout = (short) 0;

    final Object[][] Cases = {
        {null, null},
        {"0", (short) 0},
        {"abc", null},
        {"123", (short) 123},
        {"12f", (short) 12f},
        {"0x12", (short) 0x12}
    };

    for (final Object[] ele : Cases) {
      str = (String) ele[0];
      shortout = (Short) ele[1];
      assertEquals(shortout, StringUtils.toShortObject(str));
    }
  }

  @Test
  public void testToShortObjectStringShort() {
    String str = "";
    Short shortin = (short) 0;
    Short shortout = (short) 0;

    final Object[][] Cases = {
        {null, null, null},
        {null, (short) 0, (short) 0},
        {"0", (short) 0, (short) 0},
        {"abc", (short) 20, (short) 20},
        {"abc", null, null},
        {"123", (short) 0, (short) 123},
        {"12f", (short) 0, (short) 12f},
        {"0x12", (short) 0, (short) 0x12}
    };

    for (final Object[] ele : Cases) {
      str = (String) ele[0];
      shortin = (Short) ele[1];
      shortout = (Short) ele[2];
      assertEquals(shortout, StringUtils.toShortObject(str, shortin));
    }
  }

  @Test
  public void testToInt_String() {
    String str = "";
    int intout = 0;

    final Object[][] Cases = {
        {null, 0},
        {"0123", 83},
        {"abc", 0},
        {"23f", (int) 23f},
        {"0x23", 0x23}
    };

    for (final Object[] ele : Cases) {
      str = (String) ele[0];
      intout = (Integer) ele[1];
      assertEquals(intout, StringUtils.toInt(str));
    }
  }

  @Test
  public void testToInt_String_Int() {
    String str = "";
    int intin = 0;
    int intout = 0;

    final Object[][] Cases = {
        {null, 20, 20},
        {"abc", 20, 20},
        {"123", 12, 123},
        {"23f", 23, (int) 23f},
        {"0x23", 34, 35}
    };

    for (final Object[] ele : Cases) {
      str = (String) ele[0];
      intin = (Integer) ele[1];
      intout = (Integer) ele[2];
      assertEquals(intout, StringUtils.toInt(str, intin));
    }
  }

  @Test
  public void testToIntObject_String() {
    String str = "";
    Integer intout = 0;

    final Object[][] Cases = {
        {null, null},
        {"0123", 83},
        {"abc", null},
        {"23f", (int) 23f},
        {"0x23", 0x23}
    };

    for (final Object[] ele : Cases) {
      str = (String) ele[0];
      intout = (Integer) ele[1];
      assertEquals(intout, StringUtils.toIntObject(str));
    }
  }

  @Test
  public void testToIntObject_String_Integer() {
    String str = "";
    Integer intin = 0;
    Integer intout = 0;

    final Object[][] Cases = {
        {null, 20, 20},
        {null, null, null},
        {"abc", 20, 20},
        {"abc", null, null},
        {"123", 12, 123},
        {"23f", 23, (int) 23f},
        {"0x23", 34, 35}
    };

    for (final Object[] ele : Cases) {
      str = (String) ele[0];
      intin = (Integer) ele[1];
      intout = (Integer) ele[2];
      assertEquals(intout, StringUtils.toIntObject(str, intin));
    }
  }

  @Test
  public void testToLong_String() {
    String str = "";
    long longout = 0;

    final Object[][] Cases = {
        {null, (long) 0},
        {"12", (long) 12},
        {"abc", (long) 0},
        {"23f", (long) 23f},
        {"0x23", (long) 35}
    };

    for (final Object[] ele : Cases) {
      str = (String) ele[0];
      longout = (Long) ele[1];
      assertEquals(longout, StringUtils.toLong(str));
    }
  }

  @Test
  public void testToLongStringLong() {
    String str = "";
    long longin = 0;
    long longout = 0;

    final Object[][] Cases = {
        {null, (long) 20, (long) 20},
        {"12", (long) 20, (long) 12},
        {"abc", (long) 0, (long) 0},
        {"23f", (long) 3, (long) 23f},
        {"0x23", (long) 3, (long) 35}
    };

    for (final Object[] ele : Cases) {
      str = (String) ele[0];
      longin = (Long) ele[1];
      longout = (Long) ele[2];
      assertEquals(longout, StringUtils.toLong(str, longin));
    }
  }

  @Test
  public void testToLongObject_String() {
    String str = "";
    Long longout = (long) 0;

    final Object[][] Cases = {
        {null, null},
        {"12", (long) 12},
        {"abc", null},
        {"23f", (long) 23f},
        {"0x23", (long) 35}
    };

    for (final Object[] ele : Cases) {
      str = (String) ele[0];
      longout = (Long) ele[1];
      assertEquals(longout, StringUtils.toLongObject(str));
    }
  }

  @Test
  public void testToLongObjectStringLong() {
    String str = "";
    Long longin = (long) 0;
    Long longout = (long) 0;

    final Object[][] Cases = {
        {null, null, null},
        {null, (long) 20, (long) 20},
        {"12", (long) 20, (long) 12},
        {"abc", null, null},
        {"abc", (long) 0, (long) 0},
        {"23f", (long) 3, (long) 23f},
        {"0x23", (long) 3, (long) 35}
    };

    for (final Object[] ele : Cases) {
      str = (String) ele[0];
      longin = (Long) ele[1];
      longout = (Long) ele[2];
      assertEquals(longout, StringUtils.toLongObject(str, longin));
    }
  }

  @Test
  public void testToFloat_String() {
    String str = "";
    float floatout = (float) 0;

    final Object[][] Cases = {
        {null, (float) 0},
        {"abc", (float) 0},
        {"23", (float) 23},
        {"23f", 23f},
        {"2.3f", 2.3f},
        {"3e-2", 0.03f}
    };

    for (final Object[] ele : Cases) {
      str = (String) ele[0];
      floatout = (Float) ele[1];
      assertEquals(floatout, StringUtils.toFloat(str), FloatUtils.EPSILON);
    }
  }

  @Test
  public void testToFloat_String_Float() {
    String str = "";
    float floatin = (float) 0;
    float floatout = (float) 0;

    final Object[][] Cases = {
        {null, (float) 0, (float) 0},
        {"abc", (float) 0, (float) 0},
        {"123", (float) 20, (float) 123},
        {"23f", (float) 20, 23f}
    };

    for (final Object[] ele : Cases) {
      str = (String) ele[0];
      floatin = (Float) ele[1];
      floatout = (Float) ele[2];
      assertEquals(floatout, StringUtils.toFloat(str, floatin),
          FloatUtils.EPSILON);
    }
  }

  @Test
  public void testToFloatObject_String() {
    String str = "";
    Float floatout = (float) 0;

    final Object[][] Cases = {
        {null, null},
        {"abc", null},
        {"23", (float) 23},
        {"23f", 23f}
    };

    for (final Object[] ele : Cases) {
      str = (String) ele[0];
      floatout = (Float) ele[1];
      assertEquals(floatout, StringUtils.toFloatObject(str));
    }
  }

  @Test
  public void testToFloatObject_String_Float() {
    String str = "";
    Float floatin = (float) 0;
    Float floatout = (float) 0;

    final Object[][] Cases = {
        {null, null, null},
        {null, (float) 0, (float) 0},
        {"abc", null, null},
        {"abc", (float) 0, (float) 0},
        {"123", (float) 20, (float) 123},
        {"23f", (float) 20, 23f}
    };

    for (final Object[] ele : Cases) {
      str = (String) ele[0];
      floatin = (Float) ele[1];
      floatout = (Float) ele[2];
      assertEquals(floatout, StringUtils.toFloatObject(str, floatin));
    }
  }

  @Test
  public void testToDouble_String() {
    String str = "";
    double doubleout = 0;

    final Object[][] Cases = {
        {null, (double) 0},
        {"abc", (double) 0},
        {"123", (double) 123},
        {"23f", (double) 23f}
    };

    for (final Object[] ele : Cases) {
      str = (String) ele[0];
      doubleout = (Double) ele[1];
      assertEquals(doubleout, StringUtils.toDouble(str), DoubleUtils.EPSILON);
    }
  }

  @Test
  public void testToDouble_String_Double() {
    String str = "";
    double doublein = 0;
    double doubleout = 0;

    final Object[][] Cases = {
        {null, (double) 123, (double) 123},
        {"abc", (double) 123, (double) 123},
        {"123", (double) 23, (double) 123},
        {"23f", (double) 23, (double) 23f}
    };

    for (final Object[] ele : Cases) {
      str = (String) ele[0];
      doublein = (Double) ele[1];
      doubleout = (Double) ele[2];
      assertEquals(doubleout, StringUtils.toDouble(str, doublein),
          DoubleUtils.EPSILON);
    }
  }

  @Test
  public void testToDoubleObject_String() {
    String str = "";
    Double doubleout = (double) 0;

    final Object[][] Cases = {
        {null, null},
        {"abc", null},
        {"123", (double) 123},
        {"23f", (double) 23f}
    };

    for (final Object[] ele : Cases) {
      str = (String) ele[0];
      doubleout = (Double) ele[1];
      assertEquals(doubleout, StringUtils.toDoubleObject(str));
    }
  }

  @Test
  public void testToDoubleObject_String_Double() {
    String str = "";
    Double doublein = (double) 0;
    Double doubleout = (double) 0;

    final Object[][] Cases = {
        {null, null, null},
        {null, (double) 23, (double) 23},
        {"abc", null, null},
        {"abc", (double) 23, (double) 23},
        {"123", (double) 23, (double) 123},
        {"123f", (double) 23, (double) 123f}
    };

    for (final Object[] ele : Cases) {
      str = (String) ele[0];
      doublein = (Double) ele[1];
      doubleout = (Double) ele[2];
      assertEquals(doubleout, StringUtils.toDoubleObject(str, doublein));
    }
  }

  @Test
  public void testToDate_String() {
    String str = "";
    Date dateout = new Date();

    str = null;
    dateout = null;
    assertEquals(dateout, StringUtils.toDate(str));

    str = "";
    dateout = null;
    assertEquals(dateout, StringUtils.toDate(str));

    str = "123";
    dateout = null;
    assertEquals(dateout, StringUtils.toDate(str));

    final GregorianCalendar cal = new GregorianCalendar();
    cal.setTimeInMillis(0);
    cal.set(2012, 0, 12, 0, 0, 0);
    str = "2012-01-12 00:00:00";
    dateout = cal.getTime();
    assertEquals(dateout, StringUtils.toDate(str));
  }

  @Test
  public void testToDateStringDate() {
    String str = null;
    final Date defaultValue = null;

    assertEquals(null, StringUtils.toDate(str, defaultValue));

    final GregorianCalendar cal = new GregorianCalendar();
    cal.setTimeInMillis(0);
    cal.set(2012, 0, 12, 0, 0, 0);
    Date expected = cal.getTime();
    assertEquals(expected, StringUtils.toDate(str, expected));

    str = "2012-01-12 00:00:00";
    assertEquals(expected, StringUtils.toDate(str, defaultValue));

    cal.setTimeZone(DateUtils.UTC);
    cal.setTimeInMillis(0);
    cal.set(2012, 0, 12, 0, 0, 0);
    expected = cal.getTime();
    str = "2012-01-12 00:00:00 UTC";
    assertEquals(expected, StringUtils.toDate(str, defaultValue));

  }

  @Test
  public void testToClass_String() {
    assertEquals(null, StringUtils.toClass(null));
    assertEquals(null, StringUtils.toClass(""));

    String str = "";
    Class<?> classout = null;
    assertEquals(classout, StringUtils.toClass(str));

    str = "Integer";
    classout = null;
    assertEquals(classout, StringUtils.toClass(str));

    str = "java.lang.Integer";
    final Integer x = 2;
    classout = x.getClass();
    assertEquals(classout, StringUtils.toClass(str));
  }

  @Test
  public void testToClassStringClassOfQ() {
    String str = null;
    Class<?> classin = null;
    assertEquals(null, StringUtils.toClass(str, classin));

    str = null;
    final Integer x = 2;
    classin = x.getClass();
    assertEquals(classin, StringUtils.toClass(str, classin));

    str = "Integer";
    classin = null;
    assertEquals(null, StringUtils.toClass(str, classin));

    str = "java.lang.Float";
    final Float y = 0.2f;
    final Class<?> classout = y.getClass();
    assertEquals(classout, StringUtils.toClass(str, classin));
  }

  @Test
  public void testToByteArray_String() {
    assertArrayEquals(null, StringUtils.toByteArray(null));
    assertArrayEquals(null, StringUtils.toByteArray(""));

    String str = "0x00AA";
    final byte[] xa = {(byte) 0x00, (byte) 0xAA};
    assertArrayEquals(xa, StringUtils.toByteArray(str));

    str = "0x00FF";
    final byte[] xb = {(byte) 0x00, (byte) 0xFF};
    assertArrayEquals(xb, StringUtils.toByteArray(str));

    str = "0xAAAA";
    final byte[] xc = {(byte) 0xAA, (byte) 0xAA};
    assertArrayEquals(xc, StringUtils.toByteArray(str));

    str = "0xFFFF";
    final byte[] xd = {(byte) 0xFF, (byte) 0xFF};
    assertArrayEquals(xd, StringUtils.toByteArray(str));
  }

  @Test
  public void testToByteArray_String_ByteArray() {
    assertArrayEquals(null, StringUtils.toByteArray(null, null));
    assertArrayEquals(null, StringUtils.toByteArray("", null));

    final byte[] bain = {(byte) 0xff};
    final byte[] baout = {(byte) 0xff};
    assertArrayEquals(baout, StringUtils.toByteArray(null, bain));
    assertArrayEquals(baout, StringUtils.toByteArray("", bain));

    String str = "0x00AA";
    final byte[] xa = {(byte) 0x00, (byte) 0xAA};
    final byte[] ya = {(byte) 0xff};
    assertArrayEquals(xa, StringUtils.toByteArray(str, ya));

    str = "0x00FF";
    final byte[] xb = {(byte) 0x00, (byte) 0xFF};
    final byte[] yb = {(byte) 0xff};
    assertArrayEquals(xb, StringUtils.toByteArray(str, yb));

    str = "0xAAAA";
    final byte[] xc = {(byte) 0xAA, (byte) 0xAA};
    final byte[] yc = {(byte) 0xff};
    assertArrayEquals(xc, StringUtils.toByteArray(str, yc));

    str = "0xFFFF";
    final byte[] xd = {(byte) 0xFF, (byte) 0xFF};
    final byte[] yd = {(byte) 0xff};
    assertArrayEquals(xd, StringUtils.toByteArray(str, yd));
  }

  @Test
  public void testToBigInteger_String() {
    String str = "";
    BigInteger biout;

    str = null;
    biout = null;
    assertEquals(biout, StringUtils.toBigInteger(str));

    str = "";
    biout = null;
    assertEquals(biout, StringUtils.toBigInteger(str));

    str = "20";
    biout = BigInteger.valueOf(20);
    assertEquals(biout, StringUtils.toBigInteger(str));
  }

  @Test
  public void testToBigIntegerStringBigInteger() {
    String str = "";
    BigInteger biin;
    BigInteger biout;

    str = null;
    biin = null;
    biout = biin;
    assertEquals(biout, StringUtils.toBigInteger(str, biin));

    str = "";
    biin = null;
    biout = biin;
    assertEquals(biout, StringUtils.toBigInteger(str, biin));

    str = null;
    biin = BigInteger.valueOf(20);
    biout = biin;
    assertEquals(biout, StringUtils.toBigInteger(str, biin));

    str = "";
    biin = BigInteger.valueOf(20);
    biout = biin;
    assertEquals(biout, StringUtils.toBigInteger(str, biin));

    str = "20";
    biin = null;
    biout = BigInteger.valueOf(20);
    assertEquals(biout, StringUtils.toBigInteger(str, biin));

    str = "20";
    biin = BigInteger.valueOf(10);
    biout = BigInteger.valueOf(20);
    assertEquals(biout, StringUtils.toBigInteger(str, biin));
  }

  @Test
  public void testToBigDecimal_String() {
    String str = "";
    BigDecimal bdout;

    str = null;
    bdout = null;
    assertEquals(bdout, StringUtils.toBigDecimal(str));

    str = "";
    bdout = null;
    assertEquals(bdout, StringUtils.toBigDecimal(str));

    str = "20";
    bdout = BigDecimal.valueOf(20);
    assertEquals(bdout, StringUtils.toBigDecimal(str));
  }

  @Test
  public void testToBigDecimal_String_BigDecimal() {
    String str = "";
    BigDecimal bdin;
    BigDecimal bdout;

    str = null;
    bdin = null;
    bdout = null;
    assertEquals(bdout, StringUtils.toBigDecimal(str, bdin));

    str = null;
    bdin = BigDecimal.valueOf(20);
    bdout = bdin;
    assertEquals(bdout, StringUtils.toBigDecimal(str, bdin));

    str = "";
    bdin = null;
    bdout = null;
    assertEquals(bdout, StringUtils.toBigDecimal(str, bdin));

    str = "";
    bdin = BigDecimal.valueOf(20);
    bdout = bdin;
    assertEquals(bdout, StringUtils.toBigDecimal(str, bdin));

    str = "20";
    bdin = null;
    bdout = BigDecimal.valueOf(20);
    assertEquals(bdout, StringUtils.toBigDecimal(str, bdin));

    str = "20";
    bdin = BigDecimal.valueOf(10);
    bdout = BigDecimal.valueOf(20);
    assertEquals(bdout, StringUtils.toBigDecimal(str, bdin));
  }

  @Test
  public void testToXmlNode() {
    //TODO
  }

}
