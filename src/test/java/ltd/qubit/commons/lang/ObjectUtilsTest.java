////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.lang;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Unit test or the Objects class.
 *
 * @author Haixing Hu
 */
public class ObjectUtilsTest {

  private static final String FOO = "foo";
  private static final String BAR = "bar";

  @Test
  public void testIsNull() {
    final Object o = FOO;
    final Object dflt = BAR;
    assertSame(dflt, ObjectUtils.defaultIfNull(null, dflt),
        "dflt was not returned when o was null");
    assertSame(o, ObjectUtils.defaultIfNull(o, dflt),
        "dflt was returned when o was not null");
  }

  @Test
  public void testHashCode() {
    assertEquals(0, ObjectUtils.hashCode(null));
    assertEquals("a".hashCode(), ObjectUtils.hashCode("a"));
  }

  ///**
  // * Show that java.util.Date and java.sql.Timestamp are apples and oranges.
  // * Prompted by an email discussion.
  // *
  // * The behavior is different b/w Sun Java 1.3.1_10 and 1.4.2_03.
  // */
  //public void testDateEqualsJava() {
  //    long now = 1076957313284L; // Feb 16, 2004 10:49... PST
  //    java.util.Date date = new java.util.Date(now);
  //    java.sql.Timestamp realTimestamp = new java.sql.Timestamp(now);
  //    java.util.Date timestamp = realTimestamp;
  //    // sanity check 1:
  //    assertEquals(284000000, realTimestamp.getNanos());
  //    assertEquals(1076957313284L, date.getTime());
  //    //
  //    // On Sun 1.3.1_10:
  //    //org.junit.AssertionFailedError: expected:<1076957313284> but was:<1076957313000>
  //    //
  //    //assertEquals(1076957313284L, timestamp.getTime());
  //    //
  //    //org.junit.AssertionFailedError: expected:<1076957313284> but was:<1076957313000>
  //    //
  //    //assertEquals(1076957313284L, realTimestamp.getTime());
  //    // sanity check 2:
  //    assertEquals(date.getDay(), realTimestamp.getDay());
  //    assertEquals(date.getHours(), realTimestamp.getHours());
  //    assertEquals(date.getMinutes(), realTimestamp.getMinutes());
  //    assertEquals(date.getMonth(), realTimestamp.getMonth());
  //    assertEquals(date.getSeconds(), realTimestamp.getSeconds());
  //    assertEquals(date.getTimezoneOffset(), realTimestamp.getTimezoneOffset());
  //    assertEquals(date.getYear(), realTimestamp.getYear());
  //    //
  //    // Time values are == and equals() on Sun 1.4.2_03 but NOT on Sun 1.3.1_10:
  //    //
  //    //assertFalse("Sanity check failed: date.getTime() == timestamp.getTime()",
  //    date.getTime() == timestamp.getTime());
  //    //assertFalse("Sanity check failed: timestamp.equals(date)", timestamp.equals(date));
  //    //assertFalse("Sanity check failed: date.equals(timestamp)", date.equals(timestamp));
  //    // real test:
  //    //assertFalse("java.util.Date and java.sql.Timestamp should be equal",
  //    Objects.equals(date, timestamp));
  //}

  @Test
  public void testIdentityToString() {
    assertEquals(null, ObjectUtils.identityToString(null));
    assertEquals(
        "java.lang.String@" + Integer.toHexString(System.identityHashCode(FOO)),
        ObjectUtils.identityToString(FOO));
    final Integer i = 90;
    final String expected = "java.lang.Integer@" + Integer
        .toHexString(System.identityHashCode(i));
    assertEquals(expected, ObjectUtils.identityToString(i));
    final StringBuilder builder = new StringBuilder();
    ObjectUtils.identityToString(builder, i);
    assertEquals(expected, builder.toString());

    try {
      ObjectUtils.identityToString(null, "tmp");
      fail("NullPointerException expected");
    } catch (final NullPointerException npe) {
      //  pass
    }
    try {
      ObjectUtils.identityToString(new StringBuilder(), null);
      fail("NullPointerException expected");
    } catch (final NullPointerException npe) {
      //  pass
    }
  }

  @Test
  public void testToString_Object() {
    assertNull(ObjectUtils.toString(null));
    assertEquals(Boolean.TRUE.toString(), ObjectUtils.toString(Boolean.TRUE));
  }

  @Test
  public void testToString_ObjectString() {
    assertEquals(BAR, ObjectUtils.toString(null, BAR));
    assertEquals(Boolean.TRUE.toString(),
        ObjectUtils.toString(Boolean.TRUE, BAR));
  }

  @Test
  public void testToString_array() {
    final String[] a1 = {};
    assertEquals("{}", ObjectUtils.toString(a1));

    final String[] a2 = {"a", "b", "c"};
    assertEquals("{\"a\",\"b\",\"c\"}", ObjectUtils.toString(a2));

    final int[] a3 = {1, 2, 3};
    assertEquals("{1,2,3}", ObjectUtils.toString(a3));
  }

  @Test
  public void testNull() {
    assertTrue(ObjectUtils.NULL != null);
    assertTrue(ObjectUtils.NULL instanceof ObjectUtils.Null);
    //assertSame(Objects.NULL, SerializationUtils.clone(Objects.NULL));
  }

  //  @Test
  //  public void testMax() {
  //    Calendar calendar = Calendar.getInstance();
  //    Date nonNullComparable1 = calendar.getTime();
  //    Date nonNullComparable2 = calendar.getTime();
  //
  //    calendar.set( Calendar.YEAR, calendar.get( Calendar.YEAR ) -1 );
  //    Date minComparable = calendar.getTime();
  //
  //    assertNotSame( nonNullComparable1, nonNullComparable2 );
  //    assertSame( nonNullComparable1, Objects.max( null, nonNullComparable1 ) );
  //    assertSame( nonNullComparable1, Objects.max( nonNullComparable1, null ) );
  //    assertSame( nonNullComparable1, Objects.max( nonNullComparable1, nonNullComparable2 ) );
  //    assertSame( nonNullComparable1, Objects.max( nonNullComparable1, minComparable ) );
  //    assertSame( nonNullComparable1, Objects.max( minComparable, nonNullComparable1 ) );
  //  }
  //
  //  @Test
  //  public void testMin() {
  //    Calendar calendar = Calendar.getInstance();
  //    Date nonNullComparable1 = calendar.getTime();
  //    Date nonNullComparable2 = calendar.getTime();
  //
  //    calendar.set( Calendar.YEAR, calendar.get( Calendar.YEAR ) -1 );
  //    Date minComparable = calendar.getTime();
  //
  //    assertNotSame( nonNullComparable1, nonNullComparable2 );
  //
  //    assertSame( nonNullComparable1, Objects.min( null, nonNullComparable1 ) );
  //    assertSame( nonNullComparable1, Objects.min( nonNullComparable1, null ) );
  //    assertSame( nonNullComparable1, Objects.min( nonNullComparable1, nonNullComparable2 ) );
  //    assertSame( minComparable, Objects.min( nonNullComparable1, minComparable ) );
  //    assertSame( minComparable, Objects.min( minComparable, nonNullComparable1 ) );
  //  }
}
