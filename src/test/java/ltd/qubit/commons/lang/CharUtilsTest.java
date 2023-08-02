////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.lang;

import java.util.HashMap;
import java.util.Map;

import ltd.qubit.commons.datastructure.list.primitive.IntIterator;
import ltd.qubit.commons.datastructure.list.primitive.impl.IntArrayList;

import org.junit.jupiter.api.Test;

import static ltd.qubit.commons.lang.CharUtils.VISIBILITY_GRAPH;
import static ltd.qubit.commons.lang.CharUtils.VISIBILITY_INLINE_BLANK;
import static ltd.qubit.commons.lang.CharUtils.VISIBILITY_LINE_BREAK;
import static ltd.qubit.commons.lang.CharUtils.getVisibility;
import static ltd.qubit.commons.lang.CharUtils.isBlank;
import static ltd.qubit.commons.lang.CharUtils.isGraph;
import static ltd.qubit.commons.lang.CharUtils.isInlineBlank;
import static ltd.qubit.commons.lang.CharUtils.toBoolean;
import static ltd.qubit.commons.lang.CharUtils.toBooleanObject;
import static ltd.qubit.commons.lang.CharUtils.toByte;
import static ltd.qubit.commons.lang.CharUtils.toByteObject;
import static ltd.qubit.commons.lang.CharUtils.toDouble;
import static ltd.qubit.commons.lang.CharUtils.toDoubleObject;
import static ltd.qubit.commons.lang.CharUtils.toFloat;
import static ltd.qubit.commons.lang.CharUtils.toFloatObject;
import static ltd.qubit.commons.lang.CharUtils.toInt;
import static ltd.qubit.commons.lang.CharUtils.toIntObject;
import static ltd.qubit.commons.lang.CharUtils.toLong;
import static ltd.qubit.commons.lang.CharUtils.toLongObject;
import static ltd.qubit.commons.lang.CharUtils.toPrimitive;
import static ltd.qubit.commons.lang.CharUtils.toShort;
import static ltd.qubit.commons.lang.CharUtils.toShortObject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit test of the Chars class.
 *
 * @author Haixing Hu
 */
public class CharUtilsTest {

  // CHAR_CONST[i] is the string consisted of the characters whose type is i
  static final IntArrayList[] CHAR_CONST;

  static {
    final Map<Integer, IntArrayList> map = new HashMap<>();
    int maxType = -1;
    for (int ch = 0; ch <= 0x10FFFF; ++ch) {
      final int type = Character.getType(ch);
      if (type > maxType) {
        maxType = type;
      }
      IntArrayList list = map.get(type);
      if (list == null) {
        list = new IntArrayList();
        map.put(type, list);
      }
      list.add(ch);
    }

    CHAR_CONST = new IntArrayList[maxType + 1];
    for (final Map.Entry<Integer, IntArrayList> entry : map.entrySet()) {
      final Integer type = entry.getKey();
      final IntArrayList list = entry.getValue();
      CHAR_CONST[type] = list;
    }
  }

  @Test
  public void testIsGraph_int() {
    for (int type = 0; type < CHAR_CONST.length; ++type) {
      final IntArrayList char_const = CHAR_CONST[type];
      if (char_const == null) {
        continue;
      }
      final boolean expected;
      switch (type) {
        case Character.CONTROL:
        case Character.FORMAT:
        case Character.SURROGATE:
        case Character.UNASSIGNED:
        case Character.SPACE_SEPARATOR:
        case Character.LINE_SEPARATOR:
        case Character.PARAGRAPH_SEPARATOR:
          expected = false;
          break;
        default:
          expected = true;
          break;
      }
      final IntIterator iter = char_const.iterator();
      while (iter.hasNext()) {
        final int ch = iter.next();
        final boolean actual = isGraph(ch);
        assertEquals(expected, actual);
      }
    }
  }

  @Test
  public void testIsBlank_int() {
    for (int type = 0; type < CHAR_CONST.length; ++type) {
      final IntArrayList char_const = CHAR_CONST[type];
      if (char_const == null) {
        continue;
      }
      final boolean expected;
      switch (type) {
        case Character.CONTROL:
        case Character.FORMAT:
        case Character.SURROGATE:
        case Character.UNASSIGNED:
        case Character.SPACE_SEPARATOR:
        case Character.LINE_SEPARATOR:
        case Character.PARAGRAPH_SEPARATOR:
          expected = true;
          break;
        default:
          expected = false;
          break;
      }
      final IntIterator iter = char_const.iterator();
      while (iter.hasNext()) {
        final int ch = iter.next();
        final boolean actual = isBlank(ch);
        assertEquals(expected, actual);
      }
    }
  }

  @Test
  public void testIsInlineBlank_int() {
    for (int type = 0; type < CHAR_CONST.length; ++type) {
      final IntArrayList char_const = CHAR_CONST[type];
      if (char_const == null) {
        continue;
      }
      final boolean expected;
      switch (type) {
        case Character.CONTROL:
        case Character.FORMAT:
        case Character.SURROGATE:
        case Character.UNASSIGNED:
        case Character.SPACE_SEPARATOR:
          expected = true;
          break;
        default:
          expected = false;
          break;
      }
      final IntIterator iter = char_const.iterator();
      while (iter.hasNext()) {
        final int ch = iter.next();
        final boolean actual = isInlineBlank(ch);
        if ((ch == '\n')
            || (ch == '\r')
            || (ch == '\u001C')
            || (ch == '\u001D')
            || (ch == '\u001E')
            || (ch == '\u0085')
            || (ch == '\u2028')
            || (ch == '\u2029')) {
          assertFalse(actual);
        } else {
          assertEquals(expected, actual);
        }
      }
    }
  }

  @Test
  public void testIsLineBreak_int() {
    assertTrue(CharUtils.isLineBreak('\r'));
    assertTrue(CharUtils.isLineBreak('\n'));
    assertFalse(CharUtils.isLineBreak(0x007F));
    assertFalse(CharUtils.isLineBreak(0x0083));
    assertFalse(CharUtils.isLineBreak(0x000C));
    assertFalse(CharUtils.isLineBreak(0x0020));
  }

  @Test
  public void testGetVisibility_int() {
    for (int type = 0; type < CHAR_CONST.length; ++type) {
      final IntArrayList char_const = CHAR_CONST[type];
      if (char_const == null) {
        continue;
      }
      int expected = -1;
      switch (type) {
        case Character.CONTROL:
        case Character.FORMAT:
        case Character.SURROGATE:
        case Character.UNASSIGNED:
        case Character.SPACE_SEPARATOR:
          expected = VISIBILITY_INLINE_BLANK;
          break;
        case Character.LINE_SEPARATOR:
        case Character.PARAGRAPH_SEPARATOR:
          expected = VISIBILITY_LINE_BREAK;
          break;
        default:
          expected = VISIBILITY_GRAPH;
          break;
      }
      final IntIterator iter = char_const.iterator();
      while (iter.hasNext()) {
        final int ch = iter.next();
        final int actual = getVisibility(ch);
        if ((ch == '\n')
            || (ch == '\r')
            || (ch == '\u001C')
            || (ch == '\u001D')
            || (ch == '\u001E')
            || (ch == '\u0085')
            || (ch == '\u2028')
            || (ch == '\u2029')) {
          assertEquals(VISIBILITY_LINE_BREAK, actual);
        } else {
          assertEquals(expected, actual);
        }
      }
    }
  }

  @Test
  public void testToPrimitive_Character() {
    assertEquals(CharUtils.DEFAULT, toPrimitive(null));
    assertEquals('A', toPrimitive('A'));
    assertSame(toPrimitive('A'), toPrimitive('A'));
    assertEquals('B', toPrimitive('B'));
    assertSame(toPrimitive('B'), toPrimitive('B'));
  }

  @Test
  public void testToPrimitive_Character_char() {
    assertEquals('A', toPrimitive(null, 'A'));
    assertEquals('B', toPrimitive(null, 'B'));
    assertEquals('B', toPrimitive('B', 'A'));
    assertEquals('A', toPrimitive('A', 'B'));
  }

  @Test
  public void testToBoolean_char() {
    assertEquals(true, toBoolean('a'));
    assertEquals(true, toBoolean('0'));
    assertEquals(true, toBoolean('\n'));
    assertEquals(false, toBoolean('\0'));
  }

  @Test
  public void testToBoolean_Character() {
    assertEquals(BooleanUtils.DEFAULT, toBoolean(null));
    assertEquals(true, toBoolean('a'));
    assertEquals(true, toBoolean('0'));
    assertEquals(true, toBoolean('\n'));
    assertEquals(false, toBoolean('\0'));
  }

  @Test
  public void testToBooleanObject_char() {
    assertEquals(true, toBooleanObject('a'));
    assertEquals(true, toBooleanObject('0'));
    assertEquals(true, toBooleanObject('\n'));
    assertEquals(false, toBooleanObject('\0'));
  }

  @Test
  public void testToBooleanObject_Character() {
    assertEquals(null, toBooleanObject(null));
    assertEquals(true, toBooleanObject('a'));
    assertEquals(true, toBooleanObject('0'));
    assertEquals(true, toBooleanObject('\n'));
    assertEquals(false, toBooleanObject('\0'));
  }

  @Test
  public void testToBooleanObject_Character_Boolean() {
    Character charin = '\0';
    Boolean boolin = null;
    Boolean boolout = null;

    final Object[][] Cases = {
        {null, null, null},
        {null, true, true},
        {null, false, false},
        {'a', true, true},
        {'0', false, true},
        {'\n', false, true},
        {'\0', true, false}
    };

    for (final Object[] ele : Cases) {
      charin = (Character) ele[0];
      boolin = (Boolean) ele[1];
      boolout = (Boolean) ele[2];
      assertEquals(boolout, toBooleanObject(charin, boolin));
    }
  }

  @Test
  public void testToByte_Char() {
    assertEquals((byte) 0, toByte('\0'));
    assertEquals((byte) 49, toByte('1'));
    assertEquals((byte) 65, toByte('A'));
    assertEquals((byte) 97, toByte('a'));
  }

  @Test
  public void testToByte_Character() {
    assertEquals(ByteUtils.DEFAULT, toByte(null));
    assertEquals((byte) 0, toByte('\0'));
    assertEquals((byte) 49, toByte('1'));
    assertEquals((byte) 65, toByte('A'));
    assertEquals((byte) 97, toByte('a'));
  }

  @Test
  public void testToByte_Character_byte() {
    assertEquals((byte) 49, toByte(null, (byte) 49));
    assertEquals((byte) 0, toByte('\0', (byte) 49));
    assertEquals((byte) 49, toByte('1', (byte) 30));
    assertEquals((byte) 65, toByte('A', (byte) 49));
    assertEquals((byte) 97, toByte('a', (byte) 49));
  }

  @Test
  public void testToByteObject_char() {
    assertEquals((byte) 0, toByteObject('\0'));
    assertEquals((byte) 49, toByteObject('1'));
    assertEquals((byte) 65, toByteObject('A'));
    assertEquals((byte) 97, toByteObject('a'));
  }

  @Test
  public void testToByteObject_Character() {
    assertEquals(null, toByteObject(null));
    assertEquals((byte) 0, toByteObject('\0'));
    assertEquals((byte) 49, toByteObject('1'));
    assertEquals((byte) 65, toByteObject('A'));
    assertEquals((byte) 97, toByteObject('a'));
  }

  @Test
  public void testToByteObject_Character_Byte() {
    Character charin = '\0';
    Byte bytein = (byte) 0;
    Byte byteout = (byte) 0;

    final Object[][] Cases = {
        {null, null, null},
        {null, (byte) 0, (byte) 0},
        {null, (byte) 20, (byte) 20},
        {'\0', (byte) 20, (byte) 0},
        {'1', (byte) 20, (byte) 49},
        {'A', (byte) 20, (byte) 65},
        {'a', (byte) 20, (byte) 97}
    };

    for (final Object[] ele : Cases) {
      charin = (Character) ele[0];
      bytein = (Byte) ele[1];
      byteout = (Byte) ele[2];
      assertEquals(byteout, toByteObject(charin, bytein));
    }
  }

  @Test
  public void testToShort_char() {
    assertEquals((short) 0, toShort('\0'));
    assertEquals((short) 49, toShort('1'));
    assertEquals((short) 65, toShort('A'));
    assertEquals((short) 97, toShort('a'));
  }

  @Test
  public void testToShort_Character() {
    assertEquals(ShortUtils.DEFAULT, toShort(null));
    assertEquals((short) 0, toShort('\0'));
    assertEquals((short) 49, toShort('1'));
    assertEquals((short) 65, toShort('A'));
    assertEquals((short) 97, toShort('a'));
  }

  @Test
  public void testToShort_Character_short() {
    assertEquals((short) 49, toShort(null, (short) 49));
    assertEquals((short) 0, toShort('\0', (short) 49));
    assertEquals((short) 49, toShort('1', (short) 30));
    assertEquals((short) 65, toShort('A', (short) 49));
    assertEquals((short) 97, toShort('a', (short) 49));
  }

  @Test
  public void testToShortObject_char() {
    assertEquals((short) 0, toShortObject('\0'));
    assertEquals((short) 49, toShortObject('1'));
    assertEquals((short) 65, toShortObject('A'));
    assertEquals((short) 97, toShortObject('a'));
  }

  @Test
  public void testToShortObject_Character() {
    assertEquals(null, toShortObject(null));
    assertEquals((short) 0, toShortObject('\0'));
    assertEquals((short) 49, toShortObject('1'));
    assertEquals((short) 65, toShortObject('A'));
    assertEquals((short) 97, toShortObject('a'));
  }

  @Test
  public void testToShortObject_Character_Short() {
    Character charin = '\0';
    Short shortin = 0;
    Short shortout = 0;

    final Object[][] Cases = {
        {null, null, null},
        {null, (short) 0, (short) 0},
        {null, (short) 20, (short) 20},
        {'\0', (short) 20, (short) 0},
        {'1', (short) 20, (short) 49},
        {'A', (short) 20, (short) 65},
        {'a', (short) 20, (short) 97}
    };

    for (final Object[] ele : Cases) {
      charin = (Character) ele[0];
      shortin = (Short) ele[1];
      shortout = (Short) ele[2];
      assertEquals(shortout, toShortObject(charin, shortin));
    }
  }

  @Test
  public void testToInt_char() {
    assertEquals(0, toInt('\0'));
    assertEquals(49, toInt('1'));
    assertEquals(65, toInt('A'));
    assertEquals(97, toInt('a'));
  }

  @Test
  public void testToInt_Character() {
    assertEquals(IntUtils.DEFAULT, toShort(null));
    assertEquals(0, toInt('\0'));
    assertEquals(49, toInt('1'));
    assertEquals(65, toInt('A'));
    assertEquals(97, toInt('a'));
  }

  @Test
  public void testToInt_Character_Int() {
    assertEquals(49, toInt(null, 49));
    assertEquals(0, toInt('\0', 49));
    assertEquals(49, toInt('1', 30));
    assertEquals(65, toInt('A', 49));
    assertEquals(97, toInt('a', 49));
  }

  @Test
  public void testToIntObject_char() {
    assertEquals(0, toIntObject('\0'));
    assertEquals(49, toIntObject('1'));
    assertEquals(65, toIntObject('A'));
    assertEquals(97, toIntObject('a'));
  }

  @Test
  public void testToIntObject_Character() {
    assertEquals(null, toIntObject(null));
    assertEquals(0, toIntObject('\0'));
    assertEquals(49, toIntObject('1'));
    assertEquals(65, toIntObject('A'));
    assertEquals(97, toIntObject('a'));
  }

  @Test
  public void testToIntObject_Character_Short() {
    Character charin = '\0';
    Integer intin = 0;
    Integer intout = 0;

    final Object[][] Cases = {
        {null, null, null},
        {null, 0, 0},
        {null, 20, 20},
        {'\0', 20, 0},
        {'1', 20, 49},
        {'A', 20, 65},
        {'a', 20, 97}
    };

    for (final Object[] ele : Cases) {
      charin = (Character) ele[0];
      intin = (Integer) ele[1];
      intout = (Integer) ele[2];
      assertEquals(intout, toIntObject(charin, intin));
    }
  }

  @Test
  public void testToLong_char() {
    assertEquals(0, toLong('\0'));
    assertEquals(49, toLong('1'));
    assertEquals(65, toLong('A'));
    assertEquals(97, toLong('a'));
  }

  @Test
  public void testToLong_Character() {
    assertEquals(LongUtils.DEFAULT, toLong(null));
    assertEquals(0, toLong('\0'));
    assertEquals(49, toLong('1'));
    assertEquals(65, toLong('A'));
    assertEquals(97, toLong('a'));
  }

  @Test
  public void testToLong_Character_long() {
    assertEquals(49, toLong(null, 49));
    assertEquals(0, toLong('\0', 49));
    assertEquals(49, toLong('1', 30));
    assertEquals(65, toLong('A', 49));
    assertEquals(97, toLong('a', 49));
  }

  @Test
  public void testToLongObject_char() {
    assertEquals(0L, toLongObject('\0'));
    assertEquals(49L, toLongObject('1'));
    assertEquals(65L, toLongObject('A'));
    assertEquals(97L, toLongObject('a'));
  }

  @Test
  public void testToLongObject_Character() {
    assertEquals(null, toLongObject(null));
    assertEquals(0L, toLongObject('\0'));
    assertEquals(49L, toLongObject('1'));
    assertEquals(65L, toLongObject('A'));
    assertEquals(97L, toLongObject('a'));
  }

  @Test
  public void testToLongObject_Character_Long() {
    Character charin = '\0';
    Long longin = 0L;
    Long longout = 0L;

    final Object[][] Cases = {
        {null, null, null},
        {null, (long) 0, (long) 0},
        {null, (long) 20, (long) 20},
        {'\0', (long) 20, (long) 0},
        {'1', (long) 20, (long) 49},
        {'A', (long) 20, (long) 65},
        {'a', (long) 20, (long) 97}
    };

    for (final Object[] ele : Cases) {
      charin = (Character) ele[0];
      longin = (Long) ele[1];
      longout = (Long) ele[2];
      assertEquals(longout, toLongObject(charin, longin));
    }
  }

  @Test
  public void testToFloat_char() {
    assertEquals(0, toFloat('\0'), FloatUtils.EPSILON);
    assertEquals(49, toFloat('1'), FloatUtils.EPSILON);
    assertEquals(65, toFloat('A'), FloatUtils.EPSILON);
    assertEquals(97, toFloat('a'), FloatUtils.EPSILON);
  }

  @Test
  public void testToFloat_Character() {
    assertEquals(FloatUtils.DEFAULT, toFloat(null), FloatUtils.EPSILON);
    assertEquals(0, toFloat('\0'), FloatUtils.EPSILON);
    assertEquals(49, toFloat('1'), FloatUtils.EPSILON);
    assertEquals(65, toFloat('A'), FloatUtils.EPSILON);
    assertEquals(97, toFloat('a'), FloatUtils.EPSILON);
  }

  @Test
  public void testToFloat_Character_float() {

    assertEquals(49, toFloat(null, 49), FloatUtils.EPSILON);
    assertEquals(0, toFloat('\0', 49),
        FloatUtils.EPSILON);
    assertEquals(49, toFloat('1', 30),
        FloatUtils.EPSILON);
    assertEquals(65, toFloat('A', 49),
        FloatUtils.EPSILON);
    assertEquals(97, toFloat('a', 49),
        FloatUtils.EPSILON);
  }

  @Test
  public void testToFloatObject_char() {
    assertEquals((Float) 0.0f, toFloatObject('\0'));
    assertEquals((Float) 49.0f, toFloatObject('1'));
    assertEquals((Float) 65.0f, toFloatObject('A'));
    assertEquals((Float) 97.0f, toFloatObject('a'));
  }

  @Test
  public void testToFloatObject_Character() {
    assertEquals(null, toFloatObject(null));
    assertEquals((Float) 0.0f, toFloatObject('\0'));
    assertEquals((Float) 49.0f, toFloatObject('1'));
    assertEquals((Float) 65.0f, toFloatObject('A'));
    assertEquals((Float) 97.0f, toFloatObject('a'));
  }

  @Test
  public void testToFloatObject_Character_Float() {
    Character charin = '\0';
    Float floatin = 0.0f;
    Float floatout = 0.0f;

    final Object[][] Cases = {
        {null, null, null},
        {null, (float) 0, (float) 0},
        {null, (float) 20, (float) 20},
        {'\0', (float) 20, (float) 0},
        {'1', (float) 20, (float) 49},
        {'A', (float) 20, (float) 65},
        {'a', (float) 20, (float) 97}
    };

    for (final Object[] ele : Cases) {
      charin = (Character) ele[0];
      floatin = (Float) ele[1];
      floatout = (Float) ele[2];
      assertEquals(floatout, toFloatObject(charin, floatin));
    }
  }

  @Test
  public void testToDouble_char() {
    assertEquals(0, toDouble('\0'), DoubleUtils.EPSILON);
    assertEquals(49, toDouble('1'), DoubleUtils.EPSILON);
    assertEquals(65, toDouble('A'), DoubleUtils.EPSILON);
    assertEquals(97, toDouble('a'), DoubleUtils.EPSILON);
  }

  @Test
  public void testToDouble_Character() {
    assertEquals(DoubleUtils.DEFAULT, toDouble(null), DoubleUtils.EPSILON);
    assertEquals(0, toDouble('\0'),
        DoubleUtils.EPSILON);
    assertEquals(49, toDouble('1'),
        DoubleUtils.EPSILON);
    assertEquals(65, toDouble('A'),
        DoubleUtils.EPSILON);
    assertEquals(97, toDouble('a'),
        DoubleUtils.EPSILON);
  }

  @Test
  public void testToDouble_Character_float() {

    assertEquals(49, toDouble(null, 49),
        DoubleUtils.EPSILON);
    assertEquals(0, toDouble('\0', 49),
        DoubleUtils.EPSILON);
    assertEquals(49, toDouble('1', 30),
        DoubleUtils.EPSILON);
    assertEquals(65, toDouble('A', 49),
        DoubleUtils.EPSILON);
    assertEquals(97, toDouble('a', 49),
        DoubleUtils.EPSILON);
  }

  @Test
  public void testToDoubleObject_char() {
    assertEquals(0D, toDoubleObject('\0'));
    assertEquals(49D, toDoubleObject('1'));
    assertEquals(65D, toDoubleObject('A'));
    assertEquals(97D, toDoubleObject('a'));
  }

  @Test
  public void testToDoubleObject_Character() {
    assertEquals(null, toFloatObject(null));
    assertEquals(0D, toDoubleObject('\0'));
    assertEquals(49D, toDoubleObject('1'));
    assertEquals(65D, toDoubleObject('A'));
    assertEquals(97D, toDoubleObject('a'));
  }

  @Test
  public void testToDoubleObject_Character_Double() {
    Character charin = '\0';
    Double doublein = 0D;
    Double doubleout = 0D;

    final Object[][] Cases = {
        {null, null, null},
        {null, (double) 0, (double) 0},
        {null, (double) 20, (double) 20},
        {'\0', (double) 20, (double) 0},
        {'1', (double) 20, (double) 49},
        {'A', (double) 20, (double) 65},
        {'a', (double) 20, (double) 97}
    };

    for (final Object[] ele : Cases) {
      charin = (Character) ele[0];
      doublein = (Double) ele[1];
      doubleout = (Double) ele[2];
      assertEquals(doubleout, toDoubleObject(charin, doublein));
    }
  }

  @Test
  public void testToString_char() {
    assertEquals("a", CharUtils.toString('a'));
    assertSame(CharUtils.toString('a'), CharUtils.toString('a'));

    for (int i = 0; i < 128; i++) {
      final String str = CharUtils.toString((char) i);
      final String str2 = CharUtils.toString((char) i);
      assertSame(str, str2);
      assertEquals(1, str.length());
      assertEquals(i, str.charAt(0));
    }
    for (int i = 128; i < 196; i++) {
      final String str = CharUtils.toString((char) i);
      final String str2 = CharUtils.toString((char) i);
      assertEquals(str, str2);
      assertTrue(str != str2);
      assertEquals(1, str.length());
      assertEquals(i, str.charAt(0));
      assertEquals(1, str2.length());
      assertEquals(i, str2.charAt(0));
    }
  }

  @Test
  public void testToString_Character() {
    assertEquals(null, CharUtils.toString(null));
    assertEquals("A", CharUtils.toString('A'));
    assertSame(CharUtils.toString('A'),
        CharUtils.toString('A'));
    assertEquals("B", CharUtils.toString('B'));
    assertSame(CharUtils.toString('B'),
        CharUtils.toString('B'));
  }

  @Test
  public void testToUnicodeEscape_int() {
    assertEquals("\\u0041", CharUtils.toUnicodeEscape(65));

    for (int i = 0; i < 196; i++) {
      final String str = CharUtils.toUnicodeEscape((char) i);
      assertEquals(6, str.length());
      final int val = Integer.parseInt(str.substring(2), 16);
      assertEquals(i, val);
    }
    assertEquals("\\u0999", CharUtils.toUnicodeEscape((char) 0x999));
    assertEquals("\\u1001", CharUtils.toUnicodeEscape((char) 0x1001));
  }

  @Test
  public void testToUnicodeEscape_int_StringBuilder() {
    final StringBuilder builder = new StringBuilder();
    CharUtils.toUnicodeEscape(65, builder);
    assertEquals("\\u0041", builder.toString());
  }

  @Test
  public void testToUnicodeEscape_Character() {
    assertEquals(null, CharUtils.toUnicodeEscape(null));
    assertEquals("\\u0041", CharUtils.toUnicodeEscape('A'));
  }

  @Test
  public void testToUnicodeEscape_Character_StringBuilder() {
    final StringBuilder builder = new StringBuilder();
    builder.setLength(0);
    CharUtils.toUnicodeEscape('A', builder);
    assertEquals("\\u0041", builder.toString());

    builder.setLength(0);
    CharUtils.toUnicodeEscape('B', builder);
    assertEquals("\\u0042", builder.toString());

    builder.setLength(0);
    CharUtils.toUnicodeEscape('C', builder);
    assertEquals("\\u0043", builder.toString());
  }

}
