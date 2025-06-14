////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.lang;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nullable;
import javax.annotation.concurrent.ThreadSafe;

import ltd.qubit.commons.datastructure.list.primitive.BooleanCollection;
import ltd.qubit.commons.datastructure.list.primitive.BooleanIterator;
import ltd.qubit.commons.datastructure.list.primitive.ByteCollection;
import ltd.qubit.commons.datastructure.list.primitive.ByteIterator;
import ltd.qubit.commons.datastructure.list.primitive.CharCollection;
import ltd.qubit.commons.datastructure.list.primitive.CharIterator;
import ltd.qubit.commons.datastructure.list.primitive.DoubleCollection;
import ltd.qubit.commons.datastructure.list.primitive.DoubleIterator;
import ltd.qubit.commons.datastructure.list.primitive.FloatCollection;
import ltd.qubit.commons.datastructure.list.primitive.FloatIterator;
import ltd.qubit.commons.datastructure.list.primitive.IntCollection;
import ltd.qubit.commons.datastructure.list.primitive.IntIterator;
import ltd.qubit.commons.datastructure.list.primitive.LongCollection;
import ltd.qubit.commons.datastructure.list.primitive.LongIterator;
import ltd.qubit.commons.datastructure.list.primitive.ShortCollection;
import ltd.qubit.commons.datastructure.list.primitive.ShortIterator;

/**
 * This class provides functions to test the equality between objects.
 *
 * @author Haixing Hu
 */
@ThreadSafe
public final class Equality {

  /**
   * Tests the equality of two {@code boolean} values.
   *
   * <p>This function is presented to make the {@link Equality} class complete
   * for implementing the {@link Object#equals(Object)} method.
   *
   * @param value1
   *     the first value.
   * @param value2
   *     the second value.
   * @return true if the two value are equal or both null; false otherwise.
   */
  public static boolean equals(final boolean value1, final boolean value2) {
    return value1 == value2;
  }

  /**
   * Tests the equality of two {@code boolean} arrays.
   *
   * @param array1
   *     the first array, which could be null.
   * @param array2
   *     the second array, which could be null.
   * @return true if two arrays are equal or both null; false otherwise.
   */
  public static boolean equals(@Nullable final boolean[] array1,
      @Nullable final boolean[] array2) {
    if (array1 == array2) {
      return true;
    } else if ((array1 == null) || (array2 == null)) {
      return false;
    } else if (array1.length != array2.length) {
      return false;
    } else {
      return equals(array1, 0, array2, 0, array1.length);
    }
  }

  /**
   * Tests the equality of segments of two {@code boolean} arrays.
   *
   * @param array1
   *     the first array, which can't be null.
   * @param startIndex1
   *     the starting index of the first segment in the first array.
   * @param array2
   *     the second array, which can't be null.
   * @param startIndex2
   *     the starting index of the second segment in the second array.
   * @param length
   *     the length of the two segment to be compared.
   * @return true if two segments are equal; false otherwise.
   * @throws NullPointerException
   *     if any of the arrays is null.
   * @throws IndexOutOfBoundsException
   *     if the index out of bounds during the iteration of two arrays.
   */
  public static boolean equals(final boolean[] array1, final int startIndex1,
      final boolean[] array2, final int startIndex2, final int length) {
    for (int i = 0; i < length; ++i) {
      final boolean x = array1[startIndex1 + i];
      final boolean y = array2[startIndex2 + i];
      if (x != y) {
        return false;
      }
    }
    return true;
  }

  /**
   * Tests the equality of two {@link Boolean} objects.
   *
   * @param value1
   *     the first object, which may be null.
   * @param value2
   *     the second object, which may be null.
   * @return true if the two objects are equal or both null; false otherwise.
   */
  public static boolean equals(@Nullable final Boolean value1,
      @Nullable final Boolean value2) {
    if (value1 == value2) {
      return true;
    } else if ((value1 == null) || (value2 == null)) {
      return false;
    } else {
      return value1.booleanValue() == value2.booleanValue();
    }
  }

  /**
   * Tests the equality of two {@link Boolean} arrays.
   *
   * @param array1
   *     the first array, which could be null.
   * @param array2
   *     the second array, which could be null.
   * @return true if two arrays are equal or both null; false otherwise.
   */
  public static boolean equals(@Nullable final Boolean[] array1,
      @Nullable final Boolean[] array2) {
    if (array1 == array2) {
      return true;
    } else if ((array1 == null) || (array2 == null)) {
      return false;
    } else if (array1.length != array2.length) {
      return false;
    } else {
      return equals(array1, 0, array2, 0, array1.length);
    }
  }

  /**
   * Tests the equality of segments of two {@link Boolean} arrays.
   *
   * @param array1
   *     the first array, which can't be null.
   * @param startIndex1
   *     the starting index of the first segment in the first array.
   * @param array2
   *     the second array, which can't be null.
   * @param startIndex2
   *     the starting index of the second segment in the second array.
   * @param length
   *     the length of the two segment to be compared.
   * @return true if two segments are equal; false otherwise.
   * @throws NullPointerException
   *     if any of the arrays is null.
   * @throws IndexOutOfBoundsException
   *     if the index out of bounds during the iteration of two arrays.
   */
  public static boolean equals(final Boolean[] array1, final int startIndex1,
      final Boolean[] array2, final int startIndex2, final int length) {
    for (int i = 0; i < length; ++i) {
      final Boolean x = array1[startIndex1 + i];
      final Boolean y = array2[startIndex2 + i];
      if (!equals(x, y)) {
        return false;
      }
    }
    return true;
  }

  /**
   * Tests the equality of two {@code char} values.
   *
   * <p>This function is presented to make the {@link Equality} class complete
   * for implementing the {@link Object#equals(Object)} method.
   *
   * @param value1
   *     the first value.
   * @param value2
   *     the second value.
   * @return true if the two value are equal or both null; false otherwise.
   */
  public static boolean equals(final char value1, final char value2) {
    return value1 == value2;
  }

  /**
   * Tests the equality of two {@code char} arrays.
   *
   * @param array1
   *     the first array, which could be null.
   * @param array2
   *     the second array, which could be null.
   * @return true if two arrays are equal or both null; false otherwise.
   */
  public static boolean equals(@Nullable final char[] array1,
      @Nullable final char[] array2) {
    if (array1 == array2) {
      return true;
    } else if ((array1 == null) || (array2 == null)) {
      return false;
    } else if (array1.length != array2.length) {
      return false;
    } else {
      return equals(array1, 0, array2, 0, array1.length);
    }
  }

  /**
   * Tests the equality of segments of two {@code char} arrays.
   *
   * @param array1
   *     the first array, which can't be null.
   * @param startIndex1
   *     the starting index of the first segment in the first array.
   * @param array2
   *     the second array, which can't be null.
   * @param startIndex2
   *     the starting index of the second segment in the second array.
   * @param length
   *     the length of the two segment to be compared.
   * @return true if two segments are equal; false otherwise.
   * @throws NullPointerException
   *     if any of the arrays is null.
   * @throws IndexOutOfBoundsException
   *     if the index out of bounds during the iteration of two arrays.
   */
  public static boolean equals(final char[] array1, final int startIndex1,
      final char[] array2, final int startIndex2, final int length) {
    for (int i = 0; i < length; ++i) {
      final char x = array1[startIndex1 + i];
      final char y = array2[startIndex2 + i];
      if (x != y) {
        return false;
      }
    }
    return true;
  }

  /**
   * Tests the equality of two {@link Character} objects.
   *
   * @param value1
   *     the first object, which may be null.
   * @param value2
   *     the second object, which may be null.
   * @return true if the two objects are equal or both null; false otherwise.
   */
  public static boolean equals(@Nullable final Character value1,
      @Nullable final Character value2) {
    if (value1 == null) {
      return (value2 == null);
    } else if (value2 == null) {
      return false;
    } else {
      return value1.charValue() == value2.charValue();
    }
  }

  /**
   * Tests the equality of two {@link Character} arrays.
   *
   * @param array1
   *     the first array, which could be null.
   * @param array2
   *     the second array, which could be null.
   * @return true if two arrays are equal or both null; false otherwise.
   */
  public static boolean equals(@Nullable final Character[] array1,
      @Nullable final Character[] array2) {
    if (array1 == array2) {
      return true;
    } else if ((array1 == null) || (array2 == null)) {
      return false;
    } else if (array1.length != array2.length) {
      return false;
    } else {
      return equals(array1, 0, array2, 0, array1.length);
    }
  }

  /**
   * Tests the equality of segments of two {@link Character} arrays.
   *
   * @param array1
   *     the first array, which can't be null.
   * @param startIndex1
   *     the starting index of the first segment in the first array.
   * @param array2
   *     the second array, which can't be null.
   * @param startIndex2
   *     the starting index of the second segment in the second array.
   * @param length
   *     the length of the two segment to be compared.
   * @return true if two segments are equal; false otherwise.
   * @throws NullPointerException
   *     if any of the arrays is null.
   * @throws IndexOutOfBoundsException
   *     if the index out of bounds during the iteration of two arrays.
   */
  public static boolean equals(final Character[] array1, final int startIndex1,
      final Character[] array2, final int startIndex2, final int length) {
    for (int i = 0; i < length; ++i) {
      final Character x = array1[startIndex1 + i];
      final Character y = array2[startIndex2 + i];
      if (!equals(x, y)) {
        return false;
      }
    }
    return true;
  }

  /**
   * Tests the equality of two {@code byte} values.
   *
   * <p>This function is presented to make the {@link Equality} class complete
   * for implementing the {@link Object#equals(Object)} method.
   *
   * @param value1
   *     the first value.
   * @param value2
   *     the second value.
   * @return true if the two value are equal or both null; false otherwise.
   */
  public static boolean equals(final byte value1, final byte value2) {
    return value1 == value2;
  }

  /**
   * Tests the equality of two {@code byte} arrays.
   *
   * @param array1
   *     the first array, which could be null.
   * @param array2
   *     the second array, which could be null.
   * @return true if two arrays are equal or both null; false otherwise.
   */
  public static boolean equals(@Nullable final byte[] array1,
      @Nullable final byte[] array2) {
    if (array1 == array2) {
      return true;
    } else if ((array1 == null) || (array2 == null)) {
      return false;
    } else if (array1.length != array2.length) {
      return false;
    } else {
      return equals(array1, 0, array2, 0, array1.length);
    }
  }

  /**
   * Tests the equality of segments of two {@code byte} arrays.
   *
   * @param array1
   *     the first array, which can't be null.
   * @param startIndex1
   *     the starting index of the first segment in the first array.
   * @param array2
   *     the second array, which can't be null.
   * @param startIndex2
   *     the starting index of the second segment in the second array.
   * @param length
   *     the length of the two segment to be compared.
   * @return true if two segments are equal; false otherwise.
   * @throws NullPointerException
   *     if any of the arrays is null.
   * @throws IndexOutOfBoundsException
   *     if the index out of bounds during the iteration of two arrays.
   */
  public static boolean equals(final byte[] array1, final int startIndex1,
      final byte[] array2, final int startIndex2, final int length) {
    for (int i = 0; i < length; ++i) {
      final byte x = array1[startIndex1 + i];
      final byte y = array2[startIndex2 + i];
      if (x != y) {
        return false;
      }
    }
    return true;
  }

  /**
   * Tests the equality of two {@link Byte} objects.
   *
   * @param value1
   *     the first object, which may be null.
   * @param value2
   *     the second object, which may be null.
   * @return true if the two objects are equal or both null; false otherwise.
   */
  public static boolean equals(@Nullable final Byte value1,
      @Nullable final Byte value2) {
    if (value1 == null) {
      return (value2 == null);
    } else if (value2 == null) {
      return false;
    } else {
      return value1.byteValue() == value2.byteValue();
    }
  }

  /**
   * Tests the equality of two {@link Byte} arrays.
   *
   * @param array1
   *     the first array, which could be null.
   * @param array2
   *     the second array, which could be null.
   * @return true if two arrays are equal or both null; false otherwise.
   */
  public static boolean equals(@Nullable final Byte[] array1,
      @Nullable final Byte[] array2) {
    if (array1 == array2) {
      return true;
    } else if ((array1 == null) || (array2 == null)) {
      return false;
    } else if (array1.length != array2.length) {
      return false;
    } else {
      return equals(array1, 0, array2, 0, array1.length);
    }
  }

  /**
   * Tests the equality of segments of two {@link Byte} arrays.
   *
   * @param array1
   *     the first array, which can't be null.
   * @param startIndex1
   *     the starting index of the first segment in the first array.
   * @param array2
   *     the second array, which can't be null.
   * @param startIndex2
   *     the starting index of the second segment in the second array.
   * @param length
   *     the length of the two segment to be compared.
   * @return true if two segments are equal; false otherwise.
   * @throws NullPointerException
   *     if any of the arrays is null.
   * @throws IndexOutOfBoundsException
   *     if the index out of bounds during the iteration of two arrays.
   */
  public static boolean equals(final Byte[] array1, final int startIndex1,
      final Byte[] array2, final int startIndex2, final int length) {
    for (int i = 0; i < length; ++i) {
      final Byte x = array1[startIndex1 + i];
      final Byte y = array2[startIndex2 + i];
      if (!equals(x, y)) {
        return false;
      }
    }
    return true;
  }

  /**
   * Tests the equality of two {@code short} values.
   *
   * <p>This function is presented to make the {@link Equality} class complete
   * for implementing the {@link Object#equals(Object)} method.
   *
   * @param value1
   *     the first value.
   * @param value2
   *     the second value.
   * @return true if the two value are equal or both null; false otherwise.
   */
  public static boolean equals(final short value1, final short value2) {
    return value1 == value2;
  }

  /**
   * Tests the equality of two {@code short} arrays.
   *
   * @param array1
   *     the first array, which could be null.
   * @param array2
   *     the second array, which could be null.
   * @return true if two arrays are equal or both null; false otherwise.
   */
  public static boolean equals(@Nullable final short[] array1,
      @Nullable final short[] array2) {
    if (array1 == array2) {
      return true;
    } else if ((array1 == null) || (array2 == null)) {
      return false;
    } else if (array1.length != array2.length) {
      return false;
    } else {
      return equals(array1, 0, array2, 0, array1.length);
    }
  }

  /**
   * Tests the equality of segments of two {@code short} arrays.
   *
   * @param array1
   *     the first array, which can't be null.
   * @param startIndex1
   *     the starting index of the first segment in the first array.
   * @param array2
   *     the second array, which can't be null.
   * @param startIndex2
   *     the starting index of the second segment in the second array.
   * @param length
   *     the length of the two segment to be compared.
   * @return true if two segments are equal; false otherwise.
   * @throws NullPointerException
   *     if any of the arrays is null.
   * @throws IndexOutOfBoundsException
   *     if the index out of bounds during the iteration of two arrays.
   */
  public static boolean equals(final short[] array1, final int startIndex1,
      final short[] array2, final int startIndex2, final int length) {
    for (int i = 0; i < length; ++i) {
      final short x = array1[startIndex1 + i];
      final short y = array2[startIndex2 + i];
      if (x != y) {
        return false;
      }
    }
    return true;
  }

  /**
   * Tests the equality of two {@link Short} objects.
   *
   * @param value1
   *     the first object, which may be null.
   * @param value2
   *     the second object, which may be null.
   * @return true if the two objects are equal or both null; false otherwise.
   */
  public static boolean equals(@Nullable final Short value1,
      @Nullable final Short value2) {
    if (value1 == null) {
      return (value2 == null);
    } else if (value2 == null) {
      return false;
    } else {
      return value1.shortValue() == value2.shortValue();
    }
  }

  /**
   * Tests the equality of two {@link Short} arrays.
   *
   * @param array1
   *     the first array, which could be null.
   * @param array2
   *     the second array, which could be null.
   * @return true if two arrays are equal or both null; false otherwise.
   */
  public static boolean equals(@Nullable final Short[] array1,
      @Nullable final Short[] array2) {
    if (array1 == array2) {
      return true;
    } else if ((array1 == null) || (array2 == null)) {
      return false;
    } else if (array1.length != array2.length) {
      return false;
    } else {
      return equals(array1, 0, array2, 0, array1.length);
    }
  }

  /**
   * Tests the equality of segments of two {@link Short} arrays.
   *
   * @param array1
   *     the first array, which can't be null.
   * @param startIndex1
   *     the starting index of the first segment in the first array.
   * @param array2
   *     the second array, which can't be null.
   * @param startIndex2
   *     the starting index of the second segment in the second array.
   * @param length
   *     the length of the two segment to be compared.
   * @return true if two segments are equal; false otherwise.
   * @throws NullPointerException
   *     if any of the arrays is null.
   * @throws IndexOutOfBoundsException
   *     if the index out of bounds during the iteration of two arrays.
   */
  public static boolean equals(final Short[] array1, final int startIndex1,
      final Short[] array2, final int startIndex2, final int length) {
    for (int i = 0; i < length; ++i) {
      final Short x = array1[startIndex1 + i];
      final Short y = array2[startIndex2 + i];
      if (!equals(x, y)) {
        return false;
      }
    }
    return true;
  }

  /**
   * Tests the equality of two {@code int} values.
   *
   * <p>This function is presented to make the {@link Equality} class complete
   * for implementing the {@link Object#equals(Object)} method.
   *
   * @param value1
   *     the first value.
   * @param value2
   *     the second value.
   * @return true if the two value are equal or both null; false otherwise.
   */
  public static boolean equals(final int value1, final int value2) {
    return value1 == value2;
  }

  /**
   * Tests the equality of two {@code int} arrays.
   *
   * @param array1
   *     the first array, which could be null.
   * @param array2
   *     the second array, which could be null.
   * @return true if two arrays are equal or both null; false otherwise.
   */
  public static boolean equals(@Nullable final int[] array1,
      @Nullable final int[] array2) {
    if (array1 == array2) {
      return true;
    } else if ((array1 == null) || (array2 == null)) {
      return false;
    } else if (array1.length != array2.length) {
      return false;
    } else {
      return equals(array1, 0, array2, 0, array1.length);
    }
  }

  /**
   * Tests the equality of segments of two {@code int} arrays.
   *
   * @param array1
   *     the first array, which can't be null.
   * @param startIndex1
   *     the starting index of the first segment in the first array.
   * @param array2
   *     the second array, which can't be null.
   * @param startIndex2
   *     the starting index of the second segment in the second array.
   * @param length
   *     the length of the two segment to be compared.
   * @return true if two segments are equal; false otherwise.
   * @throws NullPointerException
   *     if any of the arrays is null.
   * @throws IndexOutOfBoundsException
   *     if the index out of bounds during the iteration of two arrays.
   */
  public static boolean equals(final int[] array1, final int startIndex1,
      final int[] array2, final int startIndex2, final int length) {
    for (int i = 0; i < length; ++i) {
      final int x = array1[startIndex1 + i];
      final int y = array2[startIndex2 + i];
      if (x != y) {
        return false;
      }
    }
    return true;
  }

  /**
   * Tests the equality of two {@link Integer} objects.
   *
   * @param value1
   *     the first object, which may be null.
   * @param value2
   *     the second object, which may be null.
   * @return true if the two objects are equal or both null; false otherwise.
   */
  public static boolean equals(@Nullable final Integer value1,
      @Nullable final Integer value2) {
    if (value1 == null) {
      return (value2 == null);
    } else if (value2 == null) {
      return false;
    } else {
      return value1.intValue() == value2.intValue();
    }
  }

  /**
   * Tests the equality of two {@link Integer} arrays.
   *
   * @param array1
   *     the first array, which could be null.
   * @param array2
   *     the second array, which could be null.
   * @return true if two arrays are equal or both null; false otherwise.
   */
  public static boolean equals(@Nullable final Integer[] array1,
      @Nullable final Integer[] array2) {
    if (array1 == array2) {
      return true;
    } else if ((array1 == null) || (array2 == null)) {
      return false;
    } else if (array1.length != array2.length) {
      return false;
    } else {
      return equals(array1, 0, array2, 0, array1.length);
    }
  }

  /**
   * Tests the equality of segments of two {@link Integer} arrays.
   *
   * @param array1
   *     the first array, which can't be null.
   * @param startIndex1
   *     the starting index of the first segment in the first array.
   * @param array2
   *     the second array, which can't be null.
   * @param startIndex2
   *     the starting index of the second segment in the second array.
   * @param length
   *     the length of the two segment to be compared.
   * @return true if two segments are equal; false otherwise.
   * @throws NullPointerException
   *     if any of the arrays is null.
   * @throws IndexOutOfBoundsException
   *     if the index out of bounds during the iteration of two arrays.
   */
  public static boolean equals(final Integer[] array1, final int startIndex1,
      final Integer[] array2, final int startIndex2, final int length) {
    for (int i = 0; i < length; ++i) {
      final Integer x = array1[startIndex1 + i];
      final Integer y = array2[startIndex2 + i];
      if (!equals(x, y)) {
        return false;
      }
    }
    return true;
  }

  /**
   * Tests the equality of two {@code long} values.
   *
   * <p>This function is presented to make the {@link Equality} class complete
   * for implementing the {@link Object#equals(Object)} method.
   *
   * @param value1
   *     the first value.
   * @param value2
   *     the second value.
   * @return true if the two value are equal or both null; false otherwise.
   */
  public static boolean equals(final long value1, final long value2) {
    return value1 == value2;
  }

  /**
   * Tests the equality of two {@code long} arrays.
   *
   * @param array1
   *     the first array, which could be null.
   * @param array2
   *     the second array, which could be null.
   * @return true if two arrays are equal or both null; false otherwise.
   */
  public static boolean equals(@Nullable final long[] array1,
      @Nullable final long[] array2) {
    if (array1 == array2) {
      return true;
    } else if ((array1 == null) || (array2 == null)) {
      return false;
    } else if (array1.length != array2.length) {
      return false;
    } else {
      return equals(array1, 0, array2, 0, array1.length);
    }
  }

  /**
   * Tests the equality of segments of two {@code long} arrays.
   *
   * @param array1
   *     the first array, which can't be null.
   * @param startIndex1
   *     the starting index of the first segment in the first array.
   * @param array2
   *     the second array, which can't be null.
   * @param startIndex2
   *     the starting index of the second segment in the second array.
   * @param length
   *     the length of the two segment to be compared.
   * @return true if two segments are equal; false otherwise.
   * @throws NullPointerException
   *     if any of the arrays is null.
   * @throws IndexOutOfBoundsException
   *     if the index out of bounds during the iteration of two arrays.
   */
  public static boolean equals(final long[] array1, final int startIndex1,
      final long[] array2, final int startIndex2, final int length) {
    for (int i = 0; i < length; ++i) {
      final long x = array1[startIndex1 + i];
      final long y = array2[startIndex2 + i];
      if (x != y) {
        return false;
      }
    }
    return true;
  }

  /**
   * Tests the equality of two {@link Long} objects.
   *
   * @param value1
   *     the first object, which may be null.
   * @param value2
   *     the second object, which may be null.
   * @return true if the two objects are equal or both null; false otherwise.
   */
  public static boolean equals(@Nullable final Long value1,
      @Nullable final Long value2) {
    if (value1 == null) {
      return (value2 == null);
    } else if (value2 == null) {
      return false;
    } else {
      return value1.longValue() == value2.longValue();
    }
  }

  /**
   * Tests the equality of two {@link Long} arrays.
   *
   * @param array1
   *     the first array, which could be null.
   * @param array2
   *     the second array, which could be null.
   * @return true if two arrays are equal or both null; false otherwise.
   */
  public static boolean equals(@Nullable final Long[] array1,
      @Nullable final Long[] array2) {
    if (array1 == array2) {
      return true;
    } else if ((array1 == null) || (array2 == null)) {
      return false;
    } else if (array1.length != array2.length) {
      return false;
    } else {
      return equals(array1, 0, array2, 0, array1.length);
    }
  }

  /**
   * Tests the equality of segments of two {@link Long} arrays.
   *
   * @param array1
   *     the first array, which can't be null.
   * @param startIndex1
   *     the starting index of the first segment in the first array.
   * @param array2
   *     the second array, which can't be null.
   * @param startIndex2
   *     the starting index of the second segment in the second array.
   * @param length
   *     the length of the two segment to be compared.
   * @return true if two segments are equal; false otherwise.
   * @throws NullPointerException
   *     if any of the arrays is null.
   * @throws IndexOutOfBoundsException
   *     if the index out of bounds during the iteration of two arrays.
   */
  public static boolean equals(final Long[] array1, final int startIndex1,
      final Long[] array2, final int startIndex2, final int length) {
    for (int i = 0; i < length; ++i) {
      final Long x = array1[startIndex1 + i];
      final Long y = array2[startIndex2 + i];
      if (!equals(x, y)) {
        return false;
      }
    }
    return true;
  }

  /**
   * Tests the equality of two {@code float} values exactly.
   *
   * <p>This function compare the float numbers with their bits
   * representations,
   * therefore this function should be used to implement the {@link
   * Object#equals(Object)} function.
   *
   * @param value1
   *     the first value.
   * @param value2
   *     the second value.
   * @return true if the bits representation of two float values are equal;
   *     false otherwise.
   */
  public static boolean equals(final float value1, final float value2) {
    return Float.floatToIntBits(value1) == Float.floatToIntBits(value2);
  }

  /**
   * Tests the equality of two {@code float} arrays exactly.
   *
   * <p>This function compare the float numbers with their bits
   * representations,
   * therefore this function should be used to implement the {@link
   * Object#equals(Object)} function.
   *
   * @param array1
   *     the first array, which could be null.
   * @param array2
   *     the second array, which could be null.
   * @return true if two arrays are equal exactly or both null; false otherwise.
   */
  public static boolean equals(@Nullable final float[] array1,
      @Nullable final float[] array2) {
    if (array1 == array2) {
      return true;
    } else if ((array1 == null) || (array2 == null)) {
      return false;
    } else if (array1.length != array2.length) {
      return false;
    } else {
      return equals(array1, 0, array2, 0, array1.length);
    }
  }

  /**
   * Tests the equality of segments of two {@code float} arrays exactly.
   *
   * <p>This function compare the float numbers with their bits
   * representations,
   * therefore this function should be used to implement the {@link
   * Object#equals(Object)} function.
   *
   * @param array1
   *     the first array, which can't be null.
   * @param startIndex1
   *     the starting index of the first segment in the first array.
   * @param array2
   *     the second array, which can't be null.
   * @param startIndex2
   *     the starting index of the second segment in the second array.
   * @param length
   *     the length of the two segment to be compared.
   * @return true if two segments are equal exactly; false otherwise.
   * @throws NullPointerException
   *     if any of the arrays is null.
   * @throws IndexOutOfBoundsException
   *     if the index out of bounds during the iteration of two arrays.
   */
  public static boolean equals(final float[] array1, final int startIndex1,
      final float[] array2, final int startIndex2, final int length) {
    for (int i = 0; i < length; ++i) {
      final float x = array1[startIndex1 + i];
      final float y = array2[startIndex2 + i];
      if (!equals(x, y)) {
        return false;
      }
    }
    return true;
  }

  /**
   * Tests the equality of two {@link Float} objects exactly.
   *
   * <p>This function compare the float numbers with their bits
   * representations,
   * therefore this function should be used to implement the {@link
   * Object#equals(Object)} function.
   *
   * @param value1
   *     the first object, which may be null.
   * @param value2
   *     the second object, which may be null.
   * @return true if the two objects are equal exactly or both null; false
   *     otherwise.
   */
  public static boolean equals(@Nullable final Float value1,
      @Nullable final Float value2) {
    if (value1 == null) {
      return (value2 == null);
    } else if (value2 == null) {
      return false;
    } else {
      final float x = value1.floatValue();
      final float y = value2.floatValue();
      return equals(x, y);
    }
  }

  /**
   * Tests the equality of two {@link Float} arrays exactly.
   *
   * <p>This function compare the float numbers with their bits
   * representations,
   * therefore this function should be used to implement the {@link
   * Object#equals(Object)} function.
   *
   * @param array1
   *     the first array, which could be null.
   * @param array2
   *     the second array, which could be null.
   * @return true if two arrays are equal exactly or both null; false otherwise.
   */
  public static boolean equals(@Nullable final Float[] array1,
      @Nullable final Float[] array2) {
    if (array1 == array2) {
      return true;
    } else if ((array1 == null) || (array2 == null)) {
      return false;
    } else if (array1.length != array2.length) {
      return false;
    } else {
      return equals(array1, 0, array2, 0, array1.length);
    }
  }

  /**
   * Tests the equality of segments of two {@link Float} arrays exactly.
   *
   * <p>This function compare the float numbers with their bits
   * representations,
   * therefore this function should be used to implement the {@link
   * Object#equals(Object)} function.
   *
   * @param array1
   *     the first array, which can't be null.
   * @param startIndex1
   *     the starting index of the first segment in the first array.
   * @param array2
   *     the second array, which can't be null.
   * @param startIndex2
   *     the starting index of the second segment in the second array.
   * @param length
   *     the length of the two segment to be compared.
   * @return true if two segments are equal; false otherwise.
   * @throws NullPointerException
   *     if any of the arrays is null.
   * @throws IndexOutOfBoundsException
   *     if the index out of bounds during the iteration of two arrays.
   */
  public static boolean equals(final Float[] array1, final int startIndex1,
      final Float[] array2, final int startIndex2, final int length) {
    for (int i = 0; i < length; ++i) {
      final Float x = array1[startIndex1 + i];
      final Float y = array2[startIndex2 + i];
      if (!equals(x, y)) {
        return false;
      }
    }
    return true;
  }

  /**
   * Tests the equality of two {@code double} values exactly.
   *
   * <p>This function compare the double numbers with their bits
   * representations,
   * therefore this function should be used to implement the {@link
   * Object#equals(Object)} function.
   *
   * @param value1
   *     the first value.
   * @param value2
   *     the second value.
   * @return true if the bits representation of two double values are equal;
   *     false otherwise.
   */
  public static boolean equals(final double value1, final double value2) {
    return Double.doubleToLongBits(value1) == Double.doubleToLongBits(value2);
  }

  /**
   * Tests the equality of two {@code double} arrays exactly.
   *
   * <p>This function compare the double numbers with their bits
   * representations,
   * therefore this function should be used to implement the {@link
   * Object#equals(Object)} function.
   *
   * @param array1
   *     the first array, which could be null.
   * @param array2
   *     the second array, which could be null.
   * @return true if two arrays are equal exactly or both null; false otherwise.
   */
  public static boolean equals(@Nullable final double[] array1,
      @Nullable final double[] array2) {
    if (array1 == array2) {
      return true;
    } else if ((array1 == null) || (array2 == null)) {
      return false;
    } else if (array1.length != array2.length) {
      return false;
    } else {
      return equals(array1, 0, array2, 0, array1.length);
    }
  }

  /**
   * Tests the equality of segments of two {@code double} arrays exactly.
   *
   * <p>This function compare the double numbers with their bits
   * representations,
   * therefore this function should be used to implement the {@link
   * Object#equals(Object)} function.
   *
   * @param array1
   *     the first array, which can't be null.
   * @param startIndex1
   *     the starting index of the first segment in the first array.
   * @param array2
   *     the second array, which can't be null.
   * @param startIndex2
   *     the starting index of the second segment in the second array.
   * @param length
   *     the length of the two segment to be compared.
   * @return true if two segments are equal exactly; false otherwise.
   * @throws NullPointerException
   *     if any of the arrays is null.
   * @throws IndexOutOfBoundsException
   *     if the index out of bounds during the iteration of two arrays.
   */
  public static boolean equals(final double[] array1, final int startIndex1,
      final double[] array2, final int startIndex2, final int length) {
    for (int i = 0; i < length; ++i) {
      final double x = array1[startIndex1 + i];
      final double y = array2[startIndex2 + i];
      if (!equals(x, y)) {
        return false;
      }
    }
    return true;
  }

  /**
   * Tests the equality of two {@link Double} objects exactly.
   *
   * <p>This function compare the double numbers with their bits
   * representations,
   * therefore this function should be used to implement the {@link
   * Object#equals(Object)} function.
   *
   * @param value1
   *     the first object, which may be null.
   * @param value2
   *     the second object, which may be null.
   * @return true if the two objects are equal exactly or both null; false
   *     otherwise.
   */
  public static boolean equals(@Nullable final Double value1,
      @Nullable final Double value2) {
    if (value1 == null) {
      return (value2 == null);
    } else if (value2 == null) {
      return false;
    } else {
      final double x = value1.doubleValue();
      final double y = value2.doubleValue();
      return equals(x, y);
    }
  }

  /**
   * Tests the equality of two {@link Double} arrays exactly.
   *
   * <p>This function compare the double numbers with their bits
   * representations,
   * therefore this function should be used to implement the {@link
   * Object#equals(Object)} function.
   *
   * @param array1
   *     the first array, which could be null.
   * @param array2
   *     the second array, which could be null.
   * @return true if two arrays are equal exactly or both null; false otherwise.
   */
  public static boolean equals(@Nullable final Double[] array1,
      @Nullable final Double[] array2) {
    if (array1 == array2) {
      return true;
    } else if ((array1 == null) || (array2 == null)) {
      return false;
    } else if (array1.length != array2.length) {
      return false;
    } else {
      return equals(array1, 0, array2, 0, array1.length);
    }
  }

  /**
   * Tests the equality of segments of two {@link Double} arrays exactly.
   *
   * <p>This function compare the double numbers with their bits
   * representations,
   * therefore this function should be used to implement the {@link
   * Object#equals(Object)} function.
   *
   * @param array1
   *     the first array, which can't be null.
   * @param startIndex1
   *     the starting index of the first segment in the first array.
   * @param array2
   *     the second array, which can't be null.
   * @param startIndex2
   *     the starting index of the second segment in the second array.
   * @param length
   *     the length of the two segment to be compared.
   * @return true if two segments are equal; false otherwise.
   * @throws NullPointerException
   *     if any of the arrays is null.
   * @throws IndexOutOfBoundsException
   *     if the index out of bounds during the iteration of two arrays.
   */
  public static boolean equals(final Double[] array1, final int startIndex1,
      final Double[] array2, final int startIndex2, final int length) {
    for (int i = 0; i < length; ++i) {
      final Double x = array1[startIndex1 + i];
      final Double y = array2[startIndex2 + i];
      if (!equals(x, y)) {
        return false;
      }
    }
    return true;
  }

  /**
   * Tests the equality of two {@link CharSequence} objects.
   *
   * @param value1
   *     the first object, which may be null.
   * @param value2
   *     the second object, which may be null.
   * @return true if the two objects are equal or both null; false otherwise.
   */
  public static boolean equals(@Nullable final CharSequence value1,
      @Nullable final CharSequence value2) {
    if (value1 == null) {
      return (value2 == null);
    } else if (value2 == null) {
      return false;
    } else {
      return value1.equals(value2);
    }
  }

  /**
   * Tests the equality of two {@link Long} arrays.
   *
   * @param array1
   *     the first array, which could be null.
   * @param array2
   *     the second array, which could be null.
   * @return true if two arrays are equal or both null; false otherwise.
   */
  public static boolean equals(@Nullable final CharSequence[] array1,
      @Nullable final CharSequence[] array2) {
    if (array1 == array2) {
      return true;
    } else if ((array1 == null) || (array2 == null)) {
      return false;
    } else if (array1.length != array2.length) {
      return false;
    } else {
      return equals(array1, 0, array2, 0, array1.length);
    }
  }

  /**
   * Tests the equality of two {@link Enum} objects.
   *
   * @param <E>
   *     an enumeration type.
   * @param value1
   *     the first object, which may be null.
   * @param value2
   *     the second object, which may be null.
   * @return true if the two objects are equal or both null; false otherwise.
   */
  public static <E extends Enum<E>> boolean equals(@Nullable final E value1,
      @Nullable final E value2) {
    return (value1 == value2);
  }

  /**
   * Tests the equality of two {@link Enum} arrays.
   *
   * @param <E>
   *     an enumeration type.
   * @param array1
   *     the first array, which could be null.
   * @param array2
   *     the second array, which could be null.
   * @return true if two arrays are equal or both null; false otherwise.
   */
  public static <E extends Enum<E>> boolean equals(@Nullable final E[] array1,
      @Nullable final E[] array2) {
    if (array1 == array2) {
      return true;
    } else if ((array1 == null) || (array2 == null)) {
      return false;
    } else if (array1.length != array2.length) {
      return false;
    } else {
      return equals(array1, 0, array2, 0, array1.length);
    }
  }

  /**
   * Tests the equality of segments of two {@link Enum} arrays.
   *
   * @param <E>
   *     an enumeration type.
   * @param array1
   *     the first array, which can't be null.
   * @param startIndex1
   *     the starting index of the first segment in the first array.
   * @param array2
   *     the second array, which can't be null.
   * @param startIndex2
   *     the starting index of the second segment in the second array.
   * @param length
   *     the length of the two segment to be compared.
   * @return true if two segments are equal; false otherwise.
   * @throws NullPointerException
   *     if any of the arrays is null.
   * @throws IndexOutOfBoundsException
   *     if the index out of bounds during the iteration of two arrays.
   */
  public static <E extends Enum<E>> boolean equals(final E[] array1,
      final int startIndex1, final E[] array2, final int startIndex2,
      final int length) {
    for (int i = 0; i < length; ++i) {
      final E x = array1[startIndex1 + i];
      final E y = array2[startIndex2 + i];
      if (x != y) {
        return false;
      }
    }
    return true;
  }

  /**
   * Tests the equality of two {@link String} objects.
   *
   * @param value1
   *     the first object, which may be null.
   * @param value2
   *     the second object, which may be null.
   * @return true if the two objects are equal or both null; false otherwise.
   */
  public static boolean equals(@Nullable final String value1,
      @Nullable final String value2) {
    if (value1 == value2) {
      return true;
    } else if ((value1 == null) || (value2 == null)) {
      return false;
    } else {
      return value1.equals(value2);
    }
  }

  /**
   * Tests the equality of two {@link String} arrays.
   *
   * @param array1
   *     the first array, which could be null.
   * @param array2
   *     the second array, which could be null.
   * @return true if two arrays are equal or both null; false otherwise.
   */
  public static boolean equals(@Nullable final String[] array1,
      @Nullable final String[] array2) {
    if (array1 == array2) {
      return true;
    } else if ((array1 == null) || (array2 == null)) {
      return false;
    } else if (array1.length != array2.length) {
      return false;
    } else {
      return equals(array1, 0, array2, 0, array1.length);
    }
  }

  /**
   * Tests the equality of segments of two {@link String} arrays.
   *
   * @param array1
   *     the first array, which can't be null.
   * @param startIndex1
   *     the starting index of the first segment in the first array.
   * @param array2
   *     the second array, which can't be null.
   * @param startIndex2
   *     the starting index of the second segment in the second array.
   * @param length
   *     the length of the two segment to be compared.
   * @return true if two segments are equal; false otherwise.
   * @throws NullPointerException
   *     if any of the arrays is null.
   * @throws IndexOutOfBoundsException
   *     if the index out of bounds during the iteration of two arrays.
   */
  public static boolean equals(final String[] array1, final int startIndex1,
      final String[] array2, final int startIndex2, final int length) {
    for (int i = 0; i < length; ++i) {
      final String x = array1[startIndex1 + i];
      final String y = array2[startIndex2 + i];
      if (!equals(x, y)) {
        return false;
      }
    }
    return true;
  }

  /**
   * Tests the equality of two {@link String} objects.
   *
   * @param value1
   *     the first object, which may be null.
   * @param value2
   *     the second object, which may be null.
   * @param ignoreCase
   *     indicate whether to ignore the case while comparing the strings.
   * @return true if the two objects are equal or both null; false otherwise.
   */
  public static boolean equals(@Nullable final String value1,
      @Nullable final String value2, final boolean ignoreCase) {
    if (value1 == value2) {
      return true;
    } else if ((value1 == null) || (value2 == null)) {
      return false;
    } else if (ignoreCase) {
      return value1.equalsIgnoreCase(value2);
    } else {
      return value1.equals(value2);
    }
  }

  /**
   * Tests the equality of two {@link String} arrays.
   *
   * @param array1
   *     the first array, which could be null.
   * @param array2
   *     the second array, which could be null.
   * @param ignoreCase
   *     indicate whether to ignore the case while comparing the strings.
   * @return true if two arrays are equal or both null; false otherwise.
   */
  public static boolean equals(@Nullable final String[] array1,
      @Nullable final String[] array2, final boolean ignoreCase) {
    if (array1 == array2) {
      return true;
    } else if ((array1 == null) || (array2 == null)) {
      return false;
    } else if (array1.length != array2.length) {
      return false;
    } else if (ignoreCase) {
      return equalsIgnoreCase(array1, 0, array2, 0, array1.length);
    } else {
      return equals(array1, 0, array2, 0, array1.length);
    }
  }

  /**
   * Tests the equality of segments of two {@link String} arrays.
   *
   * @param array1
   *     the first array, which can't be null.
   * @param startIndex1
   *     the starting index of the first segment in the first array.
   * @param array2
   *     the second array, which can't be null.
   * @param startIndex2
   *     the starting index of the second segment in the second array.
   * @param length
   *     the length of the two segment to be compared.
   * @param ignoreCase
   *     indicate whether to ignore the case while comparing the strings.
   * @return true if two segments are equal; false otherwise.
   * @throws NullPointerException
   *     if any of the arrays is null.
   * @throws IndexOutOfBoundsException
   *     if the index out of bounds during the iteration of two arrays.
   */
  public static boolean equals(final String[] array1, final int startIndex1,
      final String[] array2, final int startIndex2, final int length,
      final boolean ignoreCase) {
    if (ignoreCase) {
      return equalsIgnoreCase(array1, startIndex1, array2, startIndex2, length);
    } else {
      return equals(array1, startIndex1, array2, startIndex2, length);
    }
  }

  /**
   * Tests the equality of two {@link Class} objects.
   *
   * @param value1
   *     the first object, which may be null.
   * @param value2
   *     the second object, which may be null.
   * @return true if the two objects are equal or both null; false otherwise.
   */
  public static boolean equals(@Nullable final Class<?> value1,
      @Nullable final Class<?> value2) {
    return (value1 == value2);
  }

  /**
   * Tests the equality of two {@link Class} arrays.
   *
   * @param array1
   *     the first array, which could be null.
   * @param array2
   *     the second array, which could be null.
   * @return true if two arrays are equal or both null; false otherwise.
   */
  public static boolean equals(@Nullable final Class<?>[] array1,
      @Nullable final Class<?>[] array2) {
    if (array1 == array2) {
      return true;
    } else if ((array1 == null) || (array2 == null)) {
      return false;
    } else if (array1.length != array2.length) {
      return false;
    } else {
      return equals(array1, 0, array2, 0, array1.length);
    }
  }

  /**
   * Tests the equality of segments of two {@link Class} arrays.
   *
   * @param array1
   *     the first array, which can't be null.
   * @param startIndex1
   *     the starting index of the first segment in the first array.
   * @param array2
   *     the second array, which can't be null.
   * @param startIndex2
   *     the starting index of the second segment in the second array.
   * @param length
   *     the length of the two segment to be compared.
   * @return true if two segments are equal; false otherwise.
   * @throws NullPointerException
   *     if any of the arrays is null.
   * @throws IndexOutOfBoundsException
   *     if the index out of bounds during the iteration of two arrays.
   */
  public static boolean equals(final Class<?>[] array1, final int startIndex1,
      final Class<?>[] array2, final int startIndex2, final int length) {
    for (int i = 0; i < length; ++i) {
      final Class<?> x = array1[startIndex1 + i];
      final Class<?> y = array2[startIndex2 + i];
      if (x != y) {
        return false;
      }
    }
    return true;
  }

  /**
   * Tests the equality of two {@link Date} objects.
   *
   * @param value1
   *     the first object, which may be null.
   * @param value2
   *     the second object, which may be null.
   * @return true if the two objects are equal or both null; false otherwise.
   */
  public static boolean equals(@Nullable final Date value1,
      @Nullable final Date value2) {
    if (value1 == value2) {
      return true;
    } else if ((value1 == null) || (value2 == null)) {
      return false;
    } else {
      return value1.getTime() == value2.getTime();
    }
  }

  /**
   * Tests the equality of two {@link Date} arrays.
   *
   * @param array1
   *     the first array, which could be null.
   * @param array2
   *     the second array, which could be null.
   * @return true if two arrays are equal or both null; false otherwise.
   */
  public static boolean equals(@Nullable final Date[] array1,
      @Nullable final Date[] array2) {
    if (array1 == array2) {
      return true;
    } else if ((array1 == null) || (array2 == null)) {
      return false;
    } else if (array1.length != array2.length) {
      return false;
    } else {
      return equals(array1, 0, array2, 0, array1.length);
    }
  }

  /**
   * Tests the equality of segments of two {@link Date} arrays.
   *
   * @param array1
   *     the first array, which can't be null.
   * @param startIndex1
   *     the starting index of the first segment in the first array.
   * @param array2
   *     the second array, which can't be null.
   * @param startIndex2
   *     the starting index of the second segment in the second array.
   * @param length
   *     the length of the two segment to be compared.
   * @return true if two segments are equal; false otherwise.
   * @throws NullPointerException
   *     if any of the arrays is null.
   * @throws IndexOutOfBoundsException
   *     if the index out of bounds during the iteration of two arrays.
   */
  public static boolean equals(final Date[] array1, final int startIndex1,
      final Date[] array2, final int startIndex2, final int length) {
    for (int i = 0; i < length; ++i) {
      final Date x = array1[startIndex1 + i];
      final Date y = array2[startIndex2 + i];
      if (!equals(x, y)) {
        return false;
      }
    }
    return true;
  }

  /**
   * Tests the equality of two {@link BigInteger} objects.
   *
   * @param value1
   *     the first object, which may be null.
   * @param value2
   *     the second object, which may be null.
   * @return true if the two objects are equal or both null; false otherwise.
   */
  public static boolean equals(@Nullable final BigInteger value1,
      @Nullable final BigInteger value2) {
    if (value1 == value2) {
      return true;
    } else if ((value1 == null) || (value2 == null)) {
      return false;
    } else {
      return value1.equals(value2);
    }
  }

  /**
   * Tests the equality of two {@link BigInteger} arrays.
   *
   * @param array1
   *     the first array, which could be null.
   * @param array2
   *     the second array, which could be null.
   * @return true if two arrays are equal or both null; false otherwise.
   */
  public static boolean equals(@Nullable final BigInteger[] array1,
      @Nullable final BigInteger[] array2) {
    if (array1 == array2) {
      return true;
    } else if ((array1 == null) || (array2 == null)) {
      return false;
    } else if (array1.length != array2.length) {
      return false;
    } else {
      return equals(array1, 0, array2, 0, array1.length);
    }
  }

  /**
   * Tests the equality of segments of two {@link BigInteger} arrays.
   *
   * @param array1
   *     the first array, which can't be null.
   * @param startIndex1
   *     the starting index of the first segment in the first array.
   * @param array2
   *     the second array, which can't be null.
   * @param startIndex2
   *     the starting index of the second segment in the second array.
   * @param length
   *     the length of the two segment to be compared.
   * @return true if two segments are equal; false otherwise.
   * @throws NullPointerException
   *     if any of the arrays is null.
   * @throws IndexOutOfBoundsException
   *     if the index out of bounds during the iteration of two arrays.
   */
  public static boolean equals(final BigInteger[] array1,
      final int startIndex1, final BigInteger[] array2, final int startIndex2,
      final int length) {
    for (int i = 0; i < length; ++i) {
      final BigInteger x = array1[startIndex1 + i];
      final BigInteger y = array2[startIndex2 + i];
      if (!equals(x, y)) {
        return false;
      }
    }
    return true;
  }

  /**
   * Tests the equality of two {@link BigDecimal} objects.
   *
   * <p>Note that this function compares the {@link BigDecimal}'s internal
   * fields,
   * which is consistent with the {@link BigDecimal#hashCode()} method, but
   * inconsistent with the {@link BigDecimal#compareTo(BigDecimal)} method.
   * Therefore, this function must be used to implements the {@link
   * Object#equals(Object)} method.
   *
   * <p>For example,
   * <pre><code>
   *   Equality.equals(new BigDecimal("3"), new BigDecimal("3.000")) == false
   * </code></pre>
   *
   * @param value1
   *     the first object, which may be null.
   * @param value2
   *     the second object, which may be null.
   * @return true if the two objects are equal or both null; false otherwise.
   */
  public static boolean equals(@Nullable final BigDecimal value1,
      @Nullable final BigDecimal value2) {
    if (value1 == value2) {
      return true;
    } else if ((value1 == null) || (value2 == null)) {
      return false;
    } else {
      return value1.equals(value2);
    }
  }

  /**
   * Tests the equality of two {@link BigDecimal} arrays.
   *
   * <p>Note that this function compares the {@link BigDecimal}'s internal
   * fields,
   * which is consistent with the {@link BigDecimal#hashCode()} method, but
   * inconsistent with the {@link BigDecimal#compareTo(BigDecimal)} method.
   * Therefore, this function must be used to implements the {@link
   * Object#equals(Object)} method.
   *
   * @param array1
   *     the first array, which could be null.
   * @param array2
   *     the second array, which could be null.
   * @return true if two arrays are equal or both null; false otherwise.
   */
  public static boolean equals(@Nullable final BigDecimal[] array1,
      @Nullable final BigDecimal[] array2) {
    if (array1 == array2) {
      return true;
    } else if ((array1 == null) || (array2 == null)) {
      return false;
    } else if (array1.length != array2.length) {
      return false;
    } else {
      return equals(array1, 0, array2, 0, array1.length);
    }
  }

  /**
   * Tests the equality of segments of two {@link BigDecimal} arrays.
   *
   * <p>Note that this function compares the {@link BigDecimal}'s internal
   * fields,
   * which is consistent with the {@link BigDecimal#hashCode()} method, but
   * inconsistent with the {@link BigDecimal#compareTo(BigDecimal)} method.
   * Therefore, this function must be used to implements the {@link
   * Object#equals(Object)} method.
   *
   * @param array1
   *     the first array, which can't be null.
   * @param startIndex1
   *     the starting index of the first segment in the first array.
   * @param array2
   *     the second array, which can't be null.
   * @param startIndex2
   *     the starting index of the second segment in the second array.
   * @param length
   *     the length of the two segment to be compared.
   * @return true if two segments are equal; false otherwise.
   * @throws NullPointerException
   *     if any of the arrays is null.
   * @throws IndexOutOfBoundsException
   *     if the index out of bounds during the iteration of two arrays.
   */
  public static boolean equals(final BigDecimal[] array1,
      final int startIndex1, final BigDecimal[] array2, final int startIndex2,
      final int length) {
    for (int i = 0; i < length; ++i) {
      final BigDecimal x = array1[startIndex1 + i];
      final BigDecimal y = array2[startIndex2 + i];
      if (!equals(x, y)) {
        return false;
      }
    }
    return true;
  }

  /**
   * Tests the equality of two {@link Object}s.
   *
   * <p>Note that the implementation of this function is non-trivial, since the
   * multi-dimensional array in Java is treated as an {@link Object}.
   *
   * <p>Note also that this function test the equality of two float or double
   * numbers using their bit representation, therefore this function should be
   * used to implement the {@link Object#equals(Object)} function.
   *
   * @param value1
   *     the first object, which may be null.
   * @param value2
   *     the second object, which may be null.
   * @return true if the two objects are equal or both null; false otherwise.
   */
  public static boolean equals(@Nullable final Object value1,
      @Nullable final Object value2) {
    if (value1 == value2) {
      return true;
    } else if ((value1 == null) || (value2 == null)) {
      return false;
    } else {
      final Class<?> class1 = value1.getClass();
      final Class<?> class2 = value2.getClass();
      // either class1 should be a subclass of class2, or class2 should be a subclass of class1
      if ((!class1.isAssignableFrom(class2)) && (!class2.isAssignableFrom(class1))) {
        return false;
      } else if (class1.isArray()) {
        // 'Switch' on type of array, to dispatch to the correct handler
        // This handles multi-dimensional arrays
        if (value1 instanceof final boolean[] array1) {
          final boolean[] array2 = (boolean[]) value2;
          return (array1.length == array2.length)
              && equals(array1, 0, array2, 0, array1.length);
        } else if (value1 instanceof final char[] array1) {
          final char[] array2 = (char[]) value2;
          return (array1.length == array2.length)
              && equals(array1, 0, array2, 0, array1.length);
        } else if (value1 instanceof final byte[] array1) {
          final byte[] array2 = (byte[]) value2;
          return (array1.length == array2.length)
              && equals(array1, 0, array2, 0, array1.length);
        } else if (value1 instanceof final short[] array1) {
          final short[] array2 = (short[]) value2;
          return (array1.length == array2.length)
              && equals(array1, 0, array2, 0, array1.length);
        } else if (value1 instanceof final int[] array1) {
          final int[] array2 = (int[]) value2;
          return (array1.length == array2.length)
              && equals(array1, 0, array2, 0, array1.length);
        } else if (value1 instanceof final long[] array1) {
          final long[] array2 = (long[]) value2;
          return (array1.length == array2.length)
              && equals(array1, 0, array2, 0, array1.length);
        } else if (value1 instanceof final float[] array1) {
          final float[] array2 = (float[]) value2;
          return (array1.length == array2.length)
              && equals(array1, 0, array2, 0, array1.length);
        } else if (value1 instanceof final double[] array1) {
          final double[] array2 = (double[]) value2;
          return (array1.length == array2.length)
              && equals(array1, 0, array2, 0, array1.length);
        } else if (value1 instanceof final String[] array1) {
          final String[] array2 = (String[]) value2;
          return (array1.length == array2.length)
              && equals(array1, 0, array2, 0, array1.length);
        } else if (value1 instanceof final Boolean[] array1) {
          final Boolean[] array2 = (Boolean[]) value2;
          return (array1.length == array2.length)
              && equals(array1, 0, array2, 0, array1.length);
        } else if (value1 instanceof final Character[] array1) {
          final Character[] array2 = (Character[]) value2;
          return (array1.length == array2.length)
              && equals(array1, 0, array2, 0, array1.length);
        } else if (value1 instanceof final Byte[] array1) {
          final Byte[] array2 = (Byte[]) value2;
          return (array1.length == array2.length)
              && equals(array1, 0, array2, 0, array1.length);
        } else if (value1 instanceof final Short[] array1) {
          final Short[] array2 = (Short[]) value2;
          return (array1.length == array2.length)
              && equals(array1, 0, array2, 0, array1.length);
        } else if (value1 instanceof final Integer[] array1) {
          final Integer[] array2 = (Integer[]) value2;
          return (array1.length == array2.length)
              && equals(array1, 0, array2, 0, array1.length);
        } else if (value1 instanceof final Long[] array1) {
          final Long[] array2 = (Long[]) value2;
          return (array1.length == array2.length)
              && equals(array1, 0, array2, 0, array1.length);
        } else if (value1 instanceof final Float[] array1) {
          final Float[] array2 = (Float[]) value2;
          return (array1.length == array2.length)
              && equals(array1, 0, array2, 0, array1.length);
        } else if (value1 instanceof final Double[] array1) {
          final Double[] array2 = (Double[]) value2;
          return (array1.length == array2.length)
              && equals(array1, 0, array2, 0, array1.length);
        } else if (value1 instanceof final Class<?>[] array1) {
          final Class<?>[] array2 = (Class<?>[]) value2;
          return (array1.length == array2.length)
              && equals(array1, 0, array2, 0, array1.length);
        } else if (value1 instanceof final Date[] array1) {
          final Date[] array2 = (Date[]) value2;
          return (array1.length == array2.length)
              && equals(array1, 0, array2, 0, array1.length);
        } else if (value1 instanceof final BigInteger[] array1) {
          final BigInteger[] array2 = (BigInteger[]) value2;
          return (array1.length == array2.length)
              && equals(array1, 0, array2, 0, array1.length);
        } else if (value1 instanceof final BigDecimal[] array1) {
          final BigDecimal[] array2 = (BigDecimal[]) value2;
          return (array1.length == array2.length)
              && equals(array1, 0, array2, 0, array1.length);
        } else { // an array of general objects
          final Object[] array1 = (Object[]) value1;
          final Object[] array2 = (Object[]) value2;
          return (array1.length == array2.length)
              && equals(array1, 0, array2, 0, array1.length);
        }
      } else if (Set.class.isAssignableFrom(class1)) {
        final Set<?> col1 = (Set<?>) value1;
        final Set<?> col2 = (Set<?>) value2;
        return equals(col1, col2);
      } else if (Collection.class.isAssignableFrom(class1)) {
        final Collection<?> col1 = (Collection<?>) value1;
        final Collection<?> col2 = (Collection<?>) value2;
        return equals(col1, col2);
      } else {
        // The simple case, just test the element
        return value1.equals(value2);
      }
    }
  }

  /**
   * Tests the equality of two {@link Object} arrays.
   *
   * <p>Note that the implementation of this function is non-trivial, since the
   * multi-dimensional array in Java is treated as an {@link Object}.
   *
   * <p>Note also that this function test the equality of two float or double
   * numbers using their bit representation, therefore this function should be
   * used to implement the {@link Object#equals(Object)} function.
   *
   * @param array1
   *     the first array, which may be null.
   * @param array2
   *     the second array, which may be null.
   * @return true if the two arrays are equal or both null; false otherwise.
   */
  public static boolean equals(@Nullable final Object[] array1,
      @Nullable final Object[] array2) {
    if (array1 == array2) {
      return true;
    } else if ((array1 == null) || (array2 == null)) {
      return false;
    } else if (array1.length != array2.length) {
      return false;
    } else {
      return equals(array1, 0, array2, 0, array1.length);
    }
  }

  /**
   * Tests the equality of segments of two {@link Object} arrays.
   *
   * <p>Note that the implementation of this function is non-trivial, since the
   * multi-dimensional array in Java is treated as an {@link Object}.
   *
   * <p>Note also that this function test the equality of two float or double
   * numbers using their bit representation, therefore this function should be
   * used to implement the {@link Object#equals(Object)} function.
   *
   * @param array1
   *     the first array, which can't be null.
   * @param startIndex1
   *     the starting index of the first segment in the first array.
   * @param array2
   *     the second array, which can't be null.
   * @param startIndex2
   *     the starting index of the second segment in the second array.
   * @param length
   *     the length of the two segment to be compared.
   * @return true if two segments are equal; false otherwise.
   * @throws NullPointerException
   *     if any of the arrays is null.
   * @throws IndexOutOfBoundsException
   *     if the index out of bounds during the iteration of two arrays.
   */
  public static boolean equals(final Object[] array1, final int startIndex1,
      final Object[] array2, final int startIndex2, final int length) {
    for (int i = 0; i < length; ++i) {
      final Object x = array1[startIndex1 + i];
      final Object y = array2[startIndex2 + i];
      // It's very IMPORTANT to call the equals(Object, Object) to test
      // the x and y, since they may also be arrays.
      if (!equals(x, y)) {
        return false;
      }
    }
    return true;
  }

  /**
   * Tests the equality of two {@code boolean} collections.
   *
   * @param col1
   *     the first collection, which may be null.
   * @param col2
   *     the second collection, which may be null.
   * @return true if two collections are equal, or both are null; false
   *     otherwise.
   */
  public static boolean equals(@Nullable final BooleanCollection col1,
      @Nullable final BooleanCollection col2) {
    if (col1 == col2) {
      return true;
    }
    if ((col1 == null) || (col2 == null)) {
      return false;
    }
    if (col1.size() != col2.size()) {
      return false;
    }
    final BooleanIterator lhsIter = col1.iterator();
    final BooleanIterator rhsIter = col2.iterator();
    while (lhsIter.hasNext()) {
      final boolean lhsValue = lhsIter.next();
      final boolean rhsValue = rhsIter.next();
      if (lhsValue != rhsValue) {
        return false;
      }
    }
    return true;
  }

  /**
   * Tests the equality of two {@code char} collections.
   *
   * @param col1
   *     the first collection, which may be null.
   * @param col2
   *     the second collection, which may be null.
   * @return true if two collections are equal, or both are null; false
   *     otherwise.
   */
  public static boolean equals(@Nullable final CharCollection col1,
      @Nullable final CharCollection col2) {
    if (col1 == col2) {
      return true;
    }
    if ((col1 == null) || (col2 == null)) {
      return false;
    }
    if (col1.size() != col2.size()) {
      return false;
    }
    final CharIterator lhsIter = col1.iterator();
    final CharIterator rhsIter = col2.iterator();
    while (lhsIter.hasNext()) {
      final char lhsValue = lhsIter.next();
      final char rhsValue = rhsIter.next();
      if (lhsValue != rhsValue) {
        return false;
      }
    }
    return true;
  }

  /**
   * Tests the equality of two {@code byte} collections.
   *
   * @param col1
   *     the first collection, which may be null.
   * @param col2
   *     the second collection, which may be null.
   * @return true if two collections are equal, or both are null; false
   *     otherwise.
   */
  public static boolean equals(@Nullable final ByteCollection col1,
      @Nullable final ByteCollection col2) {
    if (col1 == col2) {
      return true;
    }
    if ((col1 == null) || (col2 == null)) {
      return false;
    }
    if (col1.size() != col2.size()) {
      return false;
    }
    final ByteIterator lhsIter = col1.iterator();
    final ByteIterator rhsIter = col2.iterator();
    while (lhsIter.hasNext()) {
      final byte lhsValue = lhsIter.next();
      final byte rhsValue = rhsIter.next();
      if (lhsValue != rhsValue) {
        return false;
      }
    }
    return true;
  }

  /**
   * Tests the equality of two {@code short} collections.
   *
   * @param col1
   *     the first collection, which may be null.
   * @param col2
   *     the second collection, which may be null.
   * @return true if two collections are equal, or both are null; false
   *     otherwise.
   */
  public static boolean equals(@Nullable final ShortCollection col1,
      @Nullable final ShortCollection col2) {
    if (col1 == col2) {
      return true;
    }
    if ((col1 == null) || (col2 == null)) {
      return false;
    }
    if (col1.size() != col2.size()) {
      return false;
    }
    final ShortIterator lhsIter = col1.iterator();
    final ShortIterator rhsIter = col2.iterator();
    while (lhsIter.hasNext()) {
      final short lhsValue = lhsIter.next();
      final short rhsValue = rhsIter.next();
      if (lhsValue != rhsValue) {
        return false;
      }
    }
    return true;
  }

  /**
   * Tests the equality of two {@code int} collections.
   *
   * @param col1
   *     the first collection, which may be null.
   * @param col2
   *     the second collection, which may be null.
   * @return true if two collections are equal, or both are null; false
   *     otherwise.
   */
  public static boolean equals(@Nullable final IntCollection col1,
      @Nullable final IntCollection col2) {
    if (col1 == col2) {
      return true;
    }
    if ((col1 == null) || (col2 == null)) {
      return false;
    }
    if (col1.size() != col2.size()) {
      return false;
    }
    final IntIterator lhsIter = col1.iterator();
    final IntIterator rhsIter = col2.iterator();
    while (lhsIter.hasNext()) {
      final int lhsValue = lhsIter.next();
      final int rhsValue = rhsIter.next();
      if (lhsValue != rhsValue) {
        return false;
      }
    }
    return true;
  }

  /**
   * Tests the equality of two {@code long} collections.
   *
   * @param col1
   *     the first collection, which may be null.
   * @param col2
   *     the second collection, which may be null.
   * @return true if two collections are equal, or both are null; false
   *     otherwise.
   */
  public static boolean equals(@Nullable final LongCollection col1,
      @Nullable final LongCollection col2) {
    if (col1 == col2) {
      return true;
    }
    if ((col1 == null) || (col2 == null)) {
      return false;
    }
    if (col1.size() != col2.size()) {
      return false;
    }
    final LongIterator lhsIter = col1.iterator();
    final LongIterator rhsIter = col2.iterator();
    while (lhsIter.hasNext()) {
      final long lhsValue = lhsIter.next();
      final long rhsValue = rhsIter.next();
      if (lhsValue != rhsValue) {
        return false;
      }
    }
    return true;
  }

  /**
   * Tests the equality of two {@code float} collections.
   *
   * <p>This function compare the float numbers with their bits
   * representations,
   * therefore this function should be used to implement the {@link
   * Object#equals(Object)} function.
   *
   * @param col1
   *     the first collection, which may be null.
   * @param col2
   *     the second collection, which may be null.
   * @return true if two collections are equal, or both are null; false
   *     otherwise.
   */
  public static boolean equals(@Nullable final FloatCollection col1,
      @Nullable final FloatCollection col2) {
    if (col1 == col2) {
      return true;
    }
    if ((col1 == null) || (col2 == null)) {
      return false;
    }
    if (col1.size() != col2.size()) {
      return false;
    }
    final FloatIterator lhsIter = col1.iterator();
    final FloatIterator rhsIter = col2.iterator();
    while (lhsIter.hasNext()) {
      final float lhsValue = lhsIter.next();
      final float rhsValue = rhsIter.next();
      if (!equals(lhsValue, rhsValue)) {
        return false;
      }
    }
    return true;
  }

  /**
   * Tests the equality of two {@code double} collections.
   *
   * <p>This function compare the double numbers with their bits
   * representations,
   * therefore this function should be used to implement the {@link
   * Object#equals(Object)} function.
   *
   * @param col1
   *     the first collection, which may be null.
   * @param col2
   *     the second collection, which may be null.
   * @return true if two collections are equal, or both are null; false
   *     otherwise.
   */
  public static boolean equals(@Nullable final DoubleCollection col1,
      @Nullable final DoubleCollection col2) {
    if (col1 == col2) {
      return true;
    }
    if ((col1 == null) || (col2 == null)) {
      return false;
    }
    if (col1.size() != col2.size()) {
      return false;
    }
    final DoubleIterator lhsIter = col1.iterator();
    final DoubleIterator rhsIter = col2.iterator();
    while (lhsIter.hasNext()) {
      final double lhsValue = lhsIter.next();
      final double rhsValue = rhsIter.next();
      if (!equals(lhsValue, rhsValue)) {
        return false;
      }
    }
    return true;
  }

  /**
   * Tests the equality of two collections.
   *
   * <p>The elements in the collections to be tested may be any objects, or
   * arrays, or another collection of objects.
   *
   * <p>This function compare the float numbers with their bits
   * representations,
   * therefore this function should be used to implement the {@link
   * Object#equals(Object)} function.
   *
   * @param col1
   *     the first collection, which may be null.
   * @param col2
   *     the second collection, which may be null.
   * @return true if two collections are equal, or both are null; false
   *     otherwise.
   */
  public static boolean equals(@Nullable final Collection<?> col1,
      @Nullable final Collection<?> col2) {
    if (col1 == col2) {
      return true;
    }
    if ((col1 == null) || (col2 == null)) {
      return false;
    }
    if (col1.size() != col2.size()) {
      return false;
    }
    // special treatment for Sets
    if (col1 instanceof Set) {
      if (col2 instanceof Set) {
        return setEquals((Set<?>) col1, (Set<?>) col2);
      } else {
        return false;
      }
    }
    final Iterator<?> lhsIter = col1.iterator();
    final Iterator<?> rhsIter = col2.iterator();
    while (lhsIter.hasNext()) {
      final Object lhsValue = lhsIter.next();
      final Object rhsValue = rhsIter.next();
      // note that here we must call the equals(Object, Object), since the
      // lhsValue and rhsValue may also be array or collection.
      if (!equals(lhsValue, rhsValue)) {
        return false;
      }
    }
    return true;
  }

  /**
   * Tests the equality of two sets.
   *
   * <p>The elements in the sets to be tested may be any objects, or
   * arrays, or another collection of objects.
   *
   * @param set1
   *     the first set, which may be null.
   * @param set2
   *     the second set, which may be null.
   * @return true if two sets are equal, or both are null; false otherwise.
   */
  public static boolean equals(@Nullable final Set<?> set1,
      @Nullable final Set<?> set2) {
    return setEquals(set1, set2);
  }

  /**
   * Tests the equality of two sets.
   *
   * <p>The elements in the sets to be tested may be any objects, or
   * arrays, or another collection of objects.
   *
   * @param set1
   *     the first set, which may be null.
   * @param set2
   *     the second set, which may be null.
   * @return true if two sets are equal, or both are null; false otherwise.
   */
  public static boolean setEquals(@Nullable final Set<?> set1,
      @Nullable final Set<?> set2) {
    if (set1 == set2) {
      return true;
    }
    if ((set1 == null) || (set2 == null)) {
      return false;
    }
    if (set1.size() != set2.size()) {
      return false;
    }
    return set2.containsAll(set1);
  }


  /**
   * Tests the equality of two maps.
   *
   * <p>The elements in the maps to be tested may be any objects, or
   * arrays, or another collection of objects.
   *
   * @param map1
   *     the first map, which may be null.
   * @param map2
   *     the second map, which may be null.
   * @return true if two maps are equal, or both are null; false otherwise.
   */
  public static boolean equals(@Nullable final Map<?, ?> map1,
      @Nullable final Map<?, ?> map2) {
    return mapEquals(map1, map2);
  }

  /**
   * Tests the equality of two maps.
   *
   * <p>The elements in the maps to be tested may be any objects, or
   * arrays, or another collection of objects.
   *
   * @param map1
   *     the first map, which may be null.
   * @param map2
   *     the second map, which may be null.
   * @return true if two maps are equal, or both are null; false otherwise.
   */
  public static boolean mapEquals(@Nullable final Map<?, ?> map1,
      @Nullable final Map<?, ?> map2) {
    if (map1 == map2) {
      return true;
    }
    if ((map1 == null) || (map2 == null)) {
      return false;
    }
    if (map1.size() != map2.size()) {
      return false;
    }
    for (final Map.Entry<?, ?> entry : map1.entrySet()) {
      final Object key = entry.getKey();
      final Object value1 = entry.getValue();
      final Object value2 = map2.get(key);
      if (!equals(value1, value2)) {
        return false;
      }
    }
    return true;
  }

  /**
   * Tests the equality of two {@code char} value ignoring the case.
   *
   * @param ch1
   *     the first character.
   * @param ch2
   *     the second character.
   * @return true if the two characters are equal ignoring the case; false
   *     otherwise.
   */
  public static boolean equalsIgnoreCase(final char ch1, final char ch2) {
    return Character.toLowerCase(ch1) == Character.toLowerCase(ch2);
  }

  /**
   * Tests the equality of two {@code char} arrays ignoring the case.
   *
   * @param array1
   *     the first array, which could be null.
   * @param array2
   *     the second array, which could be null.
   * @return true if two arrays are equal ignoring the case or both null; false
   *     otherwise.
   */
  public static boolean equalsIgnoreCase(@Nullable final char[] array1,
      @Nullable final char[] array2) {
    if (array1 == array2) {
      return true;
    } else if ((array1 == null) || (array2 == null)) {
      return false;
    } else if (array1.length != array2.length) {
      return false;
    } else {
      return equalsIgnoreCase(array1, 0, array2, 0, array1.length);
    }
  }

  /**
   * Tests the equality of segments of two {@code char} arrays ignoring the
   * case.
   *
   * @param array1
   *     the first array, which can't be null.
   * @param startIndex1
   *     the starting index of the first segment in the first array.
   * @param array2
   *     the second array, which can't be null.
   * @param startIndex2
   *     the starting index of the second segment in the second array.
   * @param length
   *     the length of the two segment to be compared.
   * @return true if two segments are equal ignoring the case; false otherwise.
   * @throws NullPointerException
   *     if any of the arrays is null.
   * @throws IndexOutOfBoundsException
   *     if the index out of bounds during the iteration of two arrays.
   */
  public static boolean equalsIgnoreCase(final char[] array1,
      final int startIndex1, final char[] array2, final int startIndex2,
      final int length) {
    for (int i = 0; i < length; ++i) {
      final char ch1 = array1[startIndex1 + i];
      final char ch2 = array2[startIndex2 + i];
      if (!equalsIgnoreCase(ch1, ch2)) {
        return false;
      }
    }
    return true;
  }

  /**
   * Tests the equality of two {@link Character} objects ignoring the case.
   *
   * @param value1
   *     the first object, which may be null.
   * @param value2
   *     the second object, which may be null.
   * @return true if the two objects are equal ignoring the case or both null;
   *     false otherwise.
   */
  public static boolean equalsIgnoreCase(@Nullable final Character value1,
      @Nullable final Character value2) {
    if (value1 == null) {
      return (value2 == null);
    } else if (value2 == null) {
      return false;
    } else {
      final char ch1 = value1.charValue();
      final char ch2 = value2.charValue();
      return equalsIgnoreCase(ch1, ch2);
    }
  }

  /**
   * Tests the equality of two {@link Character} arrays ignoring the case.
   *
   * @param array1
   *     the first array, which could be null.
   * @param array2
   *     the second array, which could be null.
   * @return true if two arrays are equal ignoring the case or both null; false
   *     otherwise.
   */
  public static boolean equalsIgnoreCase(@Nullable final Character[] array1,
      @Nullable final Character[] array2) {
    if (array1 == array2) {
      return true;
    } else if ((array1 == null) || (array2 == null)) {
      return false;
    } else if (array1.length != array2.length) {
      return false;
    } else {
      return equalsIgnoreCase(array1, 0, array2, 0, array1.length);
    }
  }

  /**
   * Tests the equality of segments of two {@link Character} arrays ignoring the
   * case.
   *
   * @param array1
   *     the first array, which can't be null.
   * @param startIndex1
   *     the starting index of the first segment in the first array.
   * @param array2
   *     the second array, which can't be null.
   * @param startIndex2
   *     the starting index of the second segment in the second array.
   * @param length
   *     the length of the two segment to be compared.
   * @return true if two segments are equal ignoring the case; false otherwise.
   * @throws NullPointerException
   *     if any of the arrays is null.
   * @throws IndexOutOfBoundsException
   *     if the index out of bounds during the iteration of two arrays.
   */
  public static boolean equalsIgnoreCase(final Character[] array1,
      final int startIndex1, final Character[] array2, final int startIndex2,
      final int length) {
    for (int i = 0; i < length; ++i) {
      final Character x = array1[startIndex1 + i];
      final Character y = array2[startIndex2 + i];
      if (!equalsIgnoreCase(x, y)) {
        return false;
      }
    }
    return true;
  }

  /**
   * Tests the equality of two {@link String} objects ignoring the case.
   *
   * @param value1
   *     the first object, which may be null.
   * @param value2
   *     the second object, which may be null.
   * @return true if the two objects are equal ignoring the case or both null;
   *     false otherwise.
   */
  public static boolean equalsIgnoreCase(@Nullable final CharSequence value1,
      @Nullable final CharSequence value2) {
    if (value1 == value2) {
      return true;
    } else if (value1 == null || value2 == null) {
      return false;
    } else if (value1.length() != value2.length()) {
      return false;
    } else {
      final String str1 = value1.toString();
      final String str2 = value2.toString();
      return str1.regionMatches(true, 0, str2, 0, str1.length());
    }
  }

  /**
   * Tests the equality of two {@link String} arrays ignoring the case.
   *
   * @param array1
   *     the first array, which could be null.
   * @param array2
   *     the second array, which could be null.
   * @return true if two arrays are equal ignoring the case or both null; false
   *     otherwise.
   */
  public static boolean equalsIgnoreCase(@Nullable final String[] array1,
      @Nullable final String[] array2) {
    if (array1 == array2) {
      return true;
    } else if ((array1 == null) || (array2 == null)) {
      return false;
    } else if (array1.length != array2.length) {
      return false;
    } else {
      return equalsIgnoreCase(array1, 0, array2, 0, array1.length);
    }
  }

  /**
   * Tests the equality of segments of two {@link String} arrays ignoring the
   * case.
   *
   * @param array1
   *     the first array, which can't be null.
   * @param startIndex1
   *     the starting index of the first segment in the first array.
   * @param array2
   *     the second array, which can't be null.
   * @param startIndex2
   *     the starting index of the second segment in the second array.
   * @param length
   *     the length of the two segment to be compared.
   * @return true if two segments are equal ignoring the case; false otherwise.
   * @throws NullPointerException
   *     if any of the arrays is null.
   * @throws IndexOutOfBoundsException
   *     if the index out of bounds during the iteration of two arrays.
   */
  public static boolean equalsIgnoreCase(final String[] array1,
      final int startIndex1, final String[] array2, final int startIndex2,
      final int length) {
    for (int i = 0; i < length; ++i) {
      final String x = array1[startIndex1 + i];
      final String y = array2[startIndex2 + i];
      if (!equalsIgnoreCase(x, y)) {
        return false;
      }
    }
    return true;
  }

  /**
   * Tests the equality of the represented values of two {@code float} numbers
   * with regard to a given epsilon.
   *
   * <p>That is, if the distance between the two float numbers is less than or
   * equal to the given epsilon, the two float numbers are considered to be
   * equal.
   *
   * <p>The implementation of this function is non-trivial, since float numbers
   * could be NaN.
   *
   * <p>Note that this function can NOT be used to implement the
   * {@link Object#equals(Object)} function.
   *
   * @param value1
   *     the first value.
   * @param value2
   *     the second value.
   * @param epsilon
   *     the epsilon used to compare two float numbers.
   * @return true if the represented values of two float numbers are equal
   *     approximately with regard to the given epsilon; false otherwise.
   */
  public static boolean valueEquals(final float value1, final float value2,
      final float epsilon) {
    if (Float.isNaN(value1)) {
      return Float.isNaN(value2);
    } else if (Float.isNaN(value2)) {
      return false;
    } else {
      return Math.abs(value1 - value2) <= epsilon;
    }
  }

  /**
   * Tests the equality of the represented values of two {@code float} arrays
   * with regard to a given epsilon.
   *
   * <p>That is, if the distance between the two float numbers is less than or
   * equal to the given epsilon, the two float numbers are considered to be
   * equal.
   *
   * <p>The implementation of this function is non-trivial, since float numbers
   * could be NaN.
   *
   * <p>Note that this function can NOT be used to implement the
   * {@link Object#equals(Object)} function.
   *
   * @param array1
   *     the first array, which may be null.
   * @param array2
   *     the second array, which may be null.
   * @param epsilon
   *     the epsilon used to compare two float numbers.
   * @return true if the represented values of two float arrays are equal
   *     approximately with regard to the given epsilon, or both null; false
   *     otherwise.
   */
  public static boolean valueEquals(@Nullable final float[] array1,
      @Nullable final float[] array2, final float epsilon) {
    if (array1 == array2) {
      return true;
    } else if ((array1 == null) || (array2 == null)) {
      return false;
    } else if (array1.length != array2.length) {
      return false;
    } else {
      return valueEquals(array1, 0, array2, 0, array1.length, epsilon);
    }
  }

  /**
   * Tests the equality of the represented values of segments of two {@code
   * float} arrays with regard to a given epsilon.
   *
   * <p>That is, if the distance between the two float numbers is less than or
   * equal to the given epsilon, the two float numbers are considered to be
   * equal.
   *
   * <p>The implementation of this function is non-trivial, since float numbers
   * could be NaN.
   *
   * <p>Note that this function can NOT be used to implement the
   * {@link Object#equals(Object)} function.
   *
   * @param array1
   *     the first array, which can't be null.
   * @param startIndex1
   *     the starting index of the first segment in the first array.
   * @param array2
   *     the second array, which can't be null.
   * @param startIndex2
   *     the starting index of the second segment in the second array.
   * @param length
   *     the length of the two segment to be compared.
   * @param epsilon
   *     the epsilon used to compare two float numbers.
   * @return true if the represented values of two segments are equal
   *     approximately with regard to the given epsilon; false otherwise.
   * @throws NullPointerException
   *     if any of the arrays is null.
   * @throws IndexOutOfBoundsException
   *     if the index out of bounds during the iteration of two arrays.
   */
  public static boolean valueEquals(final float[] array1,
      final int startIndex1, final float[] array2, final int startIndex2,
      final int length, final float epsilon) {
    for (int i = 0; i < length; ++i) {
      final float x = array1[startIndex1 + i];
      final float y = array2[startIndex2 + i];
      if (!valueEquals(x, y, epsilon)) {
        return false;
      }
    }
    return true;
  }

  /**
   * Tests the equality of the represented values of two {@link Float} objects
   * with regard to a given epsilon.
   *
   * <p>That is, if the distance between the two float numbers is less than or
   * equal to the given epsilon, the two float numbers are considered to be
   * equal.
   *
   * <p>The implementation of this function is non-trivial, since float numbers
   * could be NaN.
   *
   * <p>Note that this function can NOT be used to implement the
   * {@link Object#equals(Object)} function.
   *
   * @param value1
   *     the first object, which may be null.
   * @param value2
   *     the second object, which may be null.
   * @param epsilon
   *     the epsilon used to compare two float numbers.
   * @return true if the represented values of two {@link Float} objects are
   *     equal approximately with regard to the given epsilon, or both null;
   *     false otherwise.
   */
  public static boolean valueEquals(@Nullable final Float value1,
      @Nullable final Float value2, final float epsilon) {
    if (value1 == null) {
      return (value2 == null);
    } else if (value2 == null) {
      return false;
    } else {
      final float x = value1.floatValue();
      final float y = value2.floatValue();
      return valueEquals(x, y, epsilon);
    }
  }

  /**
   * Tests the equality of the represented values of two {@link Float} arrays
   * with regard to a given epsilon.
   *
   * <p>That is, if the distance between the two float numbers is less than or
   * equal to the given epsilon, the two float numbers are considered to be
   * equal.
   *
   * <p>The implementation of this function is non-trivial, since float numbers
   * could be NaN.
   *
   * <p>Note that this function can NOT be used to implement the
   * {@link Object#equals(Object)} function.
   *
   * @param array1
   *     the first array, which may be null.
   * @param array2
   *     the second array, which may be null.
   * @param epsilon
   *     the epsilon used to compare two float numbers.
   * @return true if the represented values of two {@link Float} arrays are
   *     equal approximately with regard to the given epsilon, or both null;
   *     false otherwise.
   */
  public static boolean valueEquals(@Nullable final Float[] array1,
      @Nullable final Float[] array2, final float epsilon) {
    if (array1 == array2) {
      return true;
    } else if ((array1 == null) || (array2 == null)) {
      return false;
    } else if (array1.length != array2.length) {
      return false;
    } else {
      return valueEquals(array1, 0, array2, 0, array1.length, epsilon);
    }
  }

  /**
   * Tests the equality of the represented values of segments of two {@link
   * Float} arrays with regard to a given epsilon.
   *
   * <p>That is, if the distance between the two float numbers is less than or
   * equal to the given epsilon, the two float numbers are considered to be
   * equal.
   *
   * <p>The implementation of this function is non-trivial, since float numbers
   * could be NaN.
   *
   * <p>Note that this function can NOT be used to implement the
   * {@link Object#equals(Object)} function.
   *
   * @param array1
   *     the first array, which can't be null.
   * @param startIndex1
   *     the starting index of the first segment in the first array.
   * @param array2
   *     the second array, which can't be null.
   * @param startIndex2
   *     the starting index of the second segment in the second array.
   * @param length
   *     the length of the two segment to be compared.
   * @param epsilon
   *     the epsilon used to compare two float numbers.
   * @return true if the represented values of two segments are equal
   *     approximately with regard to the given epsilon; false otherwise.
   * @throws NullPointerException
   *     if any of the arrays is null.
   * @throws IndexOutOfBoundsException
   *     if the index out of bounds during the iteration of two arrays.
   */
  public static boolean valueEquals(final Float[] array1,
      final int startIndex1, final Float[] array2, final int startIndex2,
      final int length, final float epsilon) {
    for (int i = 0; i < length; ++i) {
      final Float x = array1[startIndex1 + i];
      final Float y = array2[startIndex2 + i];
      if (!valueEquals(x, y, epsilon)) {
        return false;
      }
    }
    return true;
  }

  /**
   * Tests the equality of the represented values of two {@code double} numbers
   * with regard to a given epsilon.
   *
   * <p>That is, if the distance between the two double numbers is less than or
   * equal to the given epsilon, the two double numbers are considered to be
   * equal.
   *
   * <p>The implementation of this function is non-trivial, since double
   * numbers
   * could be NaN.
   *
   * <p>Note that this function can NOT be used to implement the
   * {@link Object#equals(Object)} function.
   *
   * @param value1
   *     the first value.
   * @param value2
   *     the second value.
   * @param epsilon
   *     the epsilon used to compare two double numbers.
   * @return true if the represented values of two double numbers are equal
   *     approximately with regard to the given epsilon; false otherwise.
   */
  public static boolean valueEquals(final double value1, final double value2,
      final double epsilon) {
    if (Double.isNaN(value1)) {
      return Double.isNaN(value2);
    } else if (Double.isNaN(value2)) {
      return false;
    } else {
      return (Math.abs(value1 - value2) <= epsilon);
    }
  }

  /**
   * Tests the equality of the represented values of two {@code double} arrays
   * with regard to a given epsilon.
   *
   * <p>That is, if the distance between the two double numbers is less than or
   * equal to the given epsilon, the two double numbers are considered to be
   * equal.
   *
   * <p>The implementation of this function is non-trivial, since double
   * numbers
   * could be NaN.
   *
   * <p>Note that this function can NOT be used to implement the
   * {@link Object#equals(Object)} function.
   *
   * @param array1
   *     the first array, which may be null.
   * @param array2
   *     the second array, which may be null.
   * @param epsilon
   *     the epsilon used to compare two double numbers.
   * @return true if the represented values of two double arrays are equal
   *     approximately with regard to the given epsilon, or both null; false
   *     otherwise.
   */
  public static boolean valueEquals(@Nullable final double[] array1,
      @Nullable final double[] array2, final double epsilon) {
    if (array1 == array2) {
      return true;
    } else if ((array1 == null) || (array2 == null)) {
      return false;
    } else if (array1.length != array2.length) {
      return false;
    } else {
      return valueEquals(array1, 0, array2, 0, array1.length, epsilon);
    }
  }

  /**
   * Tests the equality of the represented values of segments of two {@code
   * double} arrays with regard to a given epsilon.
   *
   * <p>That is, if the distance between the two double numbers is less than or
   * equal to the given epsilon, the two double numbers are considered to be
   * equal.
   *
   * <p>The implementation of this function is non-trivial, since double
   * numbers
   * could be NaN.
   *
   * <p>Note that this function can NOT be used to implement the
   * {@link Object#equals(Object)} function.
   *
   * @param array1
   *     the first array, which can't be null.
   * @param startIndex1
   *     the starting index of the first segment in the first array.
   * @param array2
   *     the second array, which can't be null.
   * @param startIndex2
   *     the starting index of the second segment in the second array.
   * @param length
   *     the length of the two segment to be compared.
   * @param epsilon
   *     the epsilon used to compare two double numbers.
   * @return true if the represented values of two segments are equal
   *     approximately with regard to the given epsilon; false otherwise.
   * @throws NullPointerException
   *     if any of the arrays is null.
   * @throws IndexOutOfBoundsException
   *     if the index out of bounds during the iteration of two arrays.
   */
  public static boolean valueEquals(final double[] array1,
      final int startIndex1, final double[] array2, final int startIndex2,
      final int length, final double epsilon) {
    for (int i = 0; i < length; ++i) {
      final double x = array1[startIndex1 + i];
      final double y = array2[startIndex2 + i];
      if (!valueEquals(x, y, epsilon)) {
        return false;
      }
    }
    return true;
  }

  /**
   * Tests the equality of the represented values of two {@link Double} objects
   * with regard to a given epsilon.
   *
   * <p>That is, if the distance between the two double numbers is less than or
   * equal to the given epsilon, the two double numbers are considered to be
   * equal.
   *
   * <p>The implementation of this function is non-trivial, since double
   * numbers
   * could be NaN.
   *
   * <p>Note that this function can NOT be used to implement the
   * {@link Object#equals(Object)} function.
   *
   * @param value1
   *     the first object, which may be null.
   * @param value2
   *     the second object, which may be null.
   * @param epsilon
   *     the epsilon used to compare two double numbers.
   * @return true if the represented values of two {@link Float} objects are
   *     equal approximately with regard to the given epsilon, or both null;
   *     false otherwise.
   */
  public static boolean valueEquals(@Nullable final Double value1,
      @Nullable final Double value2, final double epsilon) {
    if (value1 == null) {
      return (value2 == null);
    } else if (value2 == null) {
      return false;
    } else {
      final double x = value1.doubleValue();
      final double y = value2.doubleValue();
      return valueEquals(x, y, epsilon);
    }
  }

  /**
   * Tests the equality of the represented values of two {@link Double} arrays
   * with regard to a given epsilon.
   *
   * <p>That is, if the distance between the two double numbers is less than or
   * equal to the given epsilon, the two double numbers are considered to be
   * equal.
   *
   * <p>The implementation of this function is non-trivial, since double
   * numbers
   * could be NaN.
   *
   * <p>Note that this function can NOT be used to implement the
   * {@link Object#equals(Object)} function.
   *
   * @param array1
   *     the first array, which may be null.
   * @param array2
   *     the second array, which may be null.
   * @param epsilon
   *     the epsilon used to compare two double numbers.
   * @return true if the represented values of two {@link Double} arrays are
   *     equal approximately with regard to the given epsilon, or both null;
   *     false otherwise.
   */
  public static boolean valueEquals(@Nullable final Double[] array1,
      @Nullable final Double[] array2, final double epsilon) {
    if (array1 == array2) {
      return true;
    } else if ((array1 == null) || (array2 == null)) {
      return false;
    } else if (array1.length != array2.length) {
      return false;
    } else {
      return valueEquals(array1, 0, array2, 0, array1.length, epsilon);
    }
  }

  /**
   * Tests the equality of the represented values of segments of two {@link
   * Double} arrays with regard to a given epsilon.
   *
   * <p>That is, if the distance between the two double numbers is less than or
   * equal to the given epsilon, the two double numbers are considered to be
   * equal.
   *
   * <p>The implementation of this function is non-trivial, since double
   * numbers
   * could be NaN.
   *
   * <p>Note that this function can NOT be used to implement the
   * {@link Object#equals(Object)} function.
   *
   * @param array1
   *     the first array, which can't be null.
   * @param startIndex1
   *     the starting index of the first segment in the first array.
   * @param array2
   *     the second array, which can't be null.
   * @param startIndex2
   *     the starting index of the second segment in the second array.
   * @param length
   *     the length of the two segment to be compared.
   * @param epsilon
   *     the epsilon used to compare two double numbers.
   * @return true if the represented values of two segments are equal
   *     approximately with regard to the given epsilon; false otherwise.
   * @throws NullPointerException
   *     if any of the arrays is null.
   * @throws IndexOutOfBoundsException
   *     if the index out of bounds during the iteration of two arrays.
   */
  public static boolean valueEquals(final Double[] array1,
      final int startIndex1, final Double[] array2, final int startIndex2,
      final int length, final double epsilon) {
    for (int i = 0; i < length; ++i) {
      final Double x = array1[startIndex1 + i];
      final Double y = array2[startIndex2 + i];
      if (!valueEquals(x, y, epsilon)) {
        return false;
      }
    }
    return true;
  }

  /**
   * Tests the equality of represented values of two {@link BigDecimal}
   * objects.
   *
   * <p>Note that this function compares the {@link BigDecimal}'s represented
   * value, which is inconsistent with the {@link BigDecimal#hashCode()} method,
   * but consistent with the {@link BigDecimal#compareTo(BigDecimal)} method.
   * Therefore, this function can NOT be used to implements the {@link
   * Object#equals(Object)} method.
   *
   * @param value1
   *     the first object, which may be null.
   * @param value2
   *     the second object, which may be null.
   * @return true if the two objects are equal or both null; false otherwise.
   */
  public static boolean valueEquals(@Nullable final BigDecimal value1,
      @Nullable final BigDecimal value2) {
    if (value1 == value2) {
      return true;
    } else if ((value1 == null) || (value2 == null)) {
      return false;
    } else {
      return (value1.compareTo(value2) == 0);
    }
  }

  /**
   * Tests the equality of represented values of two {@link BigDecimal} arrays.
   *
   * <p>Note that this function compares the {@link BigDecimal}'s represented
   * value, which is inconsistent with the {@link BigDecimal#hashCode()} method,
   * but consistent with the {@link BigDecimal#compareTo(BigDecimal)} method.
   * Therefore, this function can NOT be used to implements the {@link
   * Object#equals(Object)} method.
   *
   * @param array1
   *     the first array, which could be null.
   * @param array2
   *     the second array, which could be null.
   * @return true if two arrays are equal or both null; false otherwise.
   */
  public static boolean valueEquals(@Nullable final BigDecimal[] array1,
      @Nullable final BigDecimal[] array2) {
    if (array1 == array2) {
      return true;
    } else if ((array1 == null) || (array2 == null)) {
      return false;
    } else if (array1.length != array2.length) {
      return false;
    } else {
      return valueEquals(array1, 0, array2, 0, array1.length);
    }
  }

  /**
   * Tests the equality of represented values of segments of two {@link
   * BigDecimal} arrays.
   *
   * <p>Note that this function compares the {@link BigDecimal}'s represented
   * value, which is inconsistent with the {@link BigDecimal#hashCode()} method,
   * but consistent with the {@link BigDecimal#compareTo(BigDecimal)} method.
   * Therefore, this function can NOT be used to implements the {@link
   * Object#equals(Object)} method.
   *
   * @param array1
   *     the first array, which can't be null.
   * @param startIndex1
   *     the starting index of the first segment in the first array.
   * @param array2
   *     the second array, which can't be null.
   * @param startIndex2
   *     the starting index of the second segment in the second array.
   * @param length
   *     the length of the two segment to be compared.
   * @return true if the represented values of two segments are equal; false
   *     otherwise.
   * @throws NullPointerException
   *     if any of the arrays is null.
   * @throws IndexOutOfBoundsException
   *     if the index out of bounds during the iteration of two arrays.
   */
  public static boolean valueEquals(final BigDecimal[] array1,
      final int startIndex1, final BigDecimal[] array2, final int startIndex2,
      final int length) {
    for (int i = 0; i < length; ++i) {
      final BigDecimal x = array1[startIndex1 + i];
      final BigDecimal y = array2[startIndex2 + i];
      if (!valueEquals(x, y)) {
        return false;
      }
    }
    return true;
  }

  /**
   * Tests the equality of the represented values of two {@link Object}s.
   *
   * <p>The implementation of this function is non-trivial, since the
   * multi-dimensional array in Java is treated as an {@link Object}.
   *
   * <p>Note that this function test the equality of two float or double
   * numbers
   * using their represented values with regard to a given epsilon. Therefore
   * this function can NOT be used to implement the {@link
   * Object#equals(Object)} function.
   *
   * @param value1
   *     the first object, which may be null.
   * @param value2
   *     the second object, which may be null.
   * @param epsilon
   *     the epsilon used to compare two double numbers.
   * @return true if the two objects are equal or both null; false otherwise.
   */
  public static boolean valueEquals(@Nullable final Object value1,
      @Nullable final Object value2, final double epsilon) {
    if (value1 == value2) {
      return true;
    } else if ((value1 == null) || (value2 == null)) {
      return false;
    } else {
      final Class<?> class1 = value1.getClass();
      final Class<?> class2 = value2.getClass();
      if (class1 != class2) {
        return false;
      } else if (class1.isArray()) {
        // 'Switch' on type of array, to dispatch to the correct handler
        // This handles multi-dimensional arrays
        if (value1 instanceof final boolean[] array1) {
          final boolean[] array2 = (boolean[]) value2;
          return (array1.length == array2.length)
              && equals(array1, 0, array2, 0, array1.length);
        } else if (value1 instanceof final char[] array1) {
          final char[] array2 = (char[]) value2;
          return (array1.length == array2.length)
              && equals(array1, 0, array2, 0, array1.length);
        } else if (value1 instanceof final byte[] array1) {
          final byte[] array2 = (byte[]) value2;
          return (array1.length == array2.length)
              && equals(array1, 0, array2, 0, array1.length);
        } else if (value1 instanceof final short[] array1) {
          final short[] array2 = (short[]) value2;
          return (array1.length == array2.length)
              && equals(array1, 0, array2, 0, array1.length);
        } else if (value1 instanceof final int[] array1) {
          final int[] array2 = (int[]) value2;
          return (array1.length == array2.length)
              && equals(array1, 0, array2, 0, array1.length);
        } else if (value1 instanceof final long[] array1) {
          final long[] array2 = (long[]) value2;
          return (array1.length == array2.length)
              && equals(array1, 0, array2, 0, array1.length);
        } else if (value1 instanceof final float[] array1) {
          final float[] array2 = (float[]) value2;
          return (array1.length == array2.length)
              && valueEquals(array1, 0, array2, 0, array1.length,
              (float) epsilon);
        } else if (value1 instanceof final double[] array1) {
          final double[] array2 = (double[]) value2;
          return (array1.length == array2.length)
              && valueEquals(array1, 0, array2, 0, array1.length, epsilon);
        } else if (value1 instanceof final String[] array1) {
          final String[] array2 = (String[]) value2;
          return (array1.length == array2.length)
              && equals(array1, 0, array2, 0, array1.length);
        } else if (value1 instanceof final Boolean[] array1) {
          final Boolean[] array2 = (Boolean[]) value2;
          return (array1.length == array2.length)
              && equals(array1, 0, array2, 0, array1.length);
        } else if (value1 instanceof final Character[] array1) {
          final Character[] array2 = (Character[]) value2;
          return (array1.length == array2.length)
              && equals(array1, 0, array2, 0, array1.length);
        } else if (value1 instanceof final Byte[] array1) {
          final Byte[] array2 = (Byte[]) value2;
          return (array1.length == array2.length)
              && equals(array1, 0, array2, 0, array1.length);
        } else if (value1 instanceof final Short[] array1) {
          final Short[] array2 = (Short[]) value2;
          return (array1.length == array2.length)
              && equals(array1, 0, array2, 0, array1.length);
        } else if (value1 instanceof final Integer[] array1) {
          final Integer[] array2 = (Integer[]) value2;
          return (array1.length == array2.length)
              && equals(array1, 0, array2, 0, array1.length);
        } else if (value1 instanceof final Long[] array1) {
          final Long[] array2 = (Long[]) value2;
          return (array1.length == array2.length)
              && equals(array1, 0, array2, 0, array1.length);
        } else if (value1 instanceof final Float[] array1) {
          final Float[] array2 = (Float[]) value2;
          return (array1.length == array2.length)
              && valueEquals(array1, 0, array2, 0, array1.length,
              (float) epsilon);
        } else if (value1 instanceof final Double[] array1) {
          final Double[] array2 = (Double[]) value2;
          return (array1.length == array2.length)
              && valueEquals(array1, 0, array2, 0, array1.length, epsilon);
        } else if (value1 instanceof final Class<?>[] array1) {
          final Class<?>[] array2 = (Class<?>[]) value2;
          return (array1.length == array2.length)
              && equals(array1, 0, array2, 0, array1.length);
        } else if (value1 instanceof final Date[] array1) {
          final Date[] array2 = (Date[]) value2;
          return (array1.length == array2.length)
              && equals(array1, 0, array2, 0, array1.length);
        } else if (value1 instanceof final BigInteger[] array1) {
          final BigInteger[] array2 = (BigInteger[]) value2;
          return (array1.length == array2.length)
              && equals(array1, 0, array2, 0, array1.length);
        } else if (value1 instanceof final BigDecimal[] array1) {
          final BigDecimal[] array2 = (BigDecimal[]) value2;
          return (array1.length == array2.length)
              && valueEquals(array1, 0, array2, 0, array1.length);
        } else { // an array of general objects
          final Object[] array1 = (Object[]) value1;
          final Object[] array2 = (Object[]) value2;
          return (array1.length == array2.length)
              && valueEquals(array1, 0, array2, 0, array1.length, epsilon);
        }
      } else if (Collection.class.isAssignableFrom(class1)) {
        final Collection<?> col1 = (Collection<?>) value1;
        final Collection<?> col2 = (Collection<?>) value2;
        return valueEquals(col1, col2, epsilon);
      } else if (value1 instanceof final BigDecimal x) {
        // the BigDecimal should be treated specially.
        final BigDecimal y = (BigDecimal) value2;
        return x.compareTo(y) == 0;
      } else {
        // The simple case, just test the element
        return value1.equals(value2);
      }
    }
  }

  /**
   * Tests the equality of represented values of two {@link Object} arrays.
   *
   * <p>The implementation of this function is non-trivial, since the
   * multi-dimensional array in Java is treated as an {@link Object}.
   *
   * <p>Note that this function test the equality of two float or double
   * numbers
   * using their represented values with regard to a given epsilon. Therefore
   * this function can NOT be used to implement the {@link
   * Object#equals(Object)} function.
   *
   * @param array1
   *     the first array, which could be null.
   * @param array2
   *     the second array, which could be null.
   * @param epsilon
   *     the precision.
   * @return true if two arrays are equal or both null; false otherwise.
   */
  public static boolean valueEquals(@Nullable final Object[] array1,
      @Nullable final Object[] array2, final double epsilon) {
    if (array1 == array2) {
      return true;
    } else if ((array1 == null) || (array2 == null)) {
      return false;
    } else if (array1.length != array2.length) {
      return false;
    } else {
      return valueEquals(array1, 0, array2, 0, array1.length, epsilon);
    }
  }

  /**
   * Tests the equality of represented values of segments of two {@link Object}
   * arrays.
   *
   * <p>The implementation of this function is non-trivial, since the
   * multi-dimensional array in Java is treated as an {@link Object}.
   *
   * <p>Note that this function test the equality of two float or double
   * numbers
   * using their represented values with regard to a given epsilon. Therefore
   * this function can NOT be used to implement the {@link
   * Object#equals(Object)} function.
   *
   * @param array1
   *     the first array, which can't be null.
   * @param startIndex1
   *     the starting index of the first segment in the first array.
   * @param array2
   *     the second array, which can't be null.
   * @param startIndex2
   *     the starting index of the second segment in the second array.
   * @param length
   *     the length of the two segment to be compared.
   * @param epsilon
   *     the precision.
   * @return true if the represented values of two segments are equal; false
   *     otherwise.
   * @throws NullPointerException
   *     if any of the arrays is null.
   * @throws IndexOutOfBoundsException
   *     if the index out of bounds during the iteration of two arrays.
   */
  public static boolean valueEquals(final Object[] array1,
      final int startIndex1, final Object[] array2, final int startIndex2,
      final int length, final double epsilon) {
    for (int i = 0; i < length; ++i) {
      final Object x = array1[startIndex1 + i];
      final Object y = array2[startIndex2 + i];
      // It's very IMPORTANT to call the valueEquals(Object, Object, double)
      // to test the x and y, since they may be also arrays.
      if (!valueEquals(x, y, epsilon)) {
        return false;
      }
    }
    return true;
  }

  /**
   * Tests the equality of the represented values of two {@code float}
   * collections with regard to a given epsilon.
   *
   * <p>That is, if the distance between the two float numbers is less than or
   * equal to the given epsilon, the two float numbers are considered to be
   * equal.
   *
   * <p>Note that this function can NOT be used to implement the
   * {@link Object#equals(Object)} function.
   *
   * @param col1
   *     the first collection, which may be null.
   * @param col2
   *     the second collection, which may be null.
   * @param epsilon
   *     the epsilon used to compare two float numbers.
   * @return true if the represented values of two collections are equal, or
   *     both are null; false otherwise.
   */
  public static boolean valueEquals(@Nullable final FloatCollection col1,
      @Nullable final FloatCollection col2, final float epsilon) {
    if (col1 == col2) {
      return true;
    }
    if ((col1 == null) || (col2 == null)) {
      return false;
    }
    if (col1.size() != col2.size()) {
      return false;
    }
    final FloatIterator lhsIter = col1.iterator();
    final FloatIterator rhsIter = col2.iterator();
    while (lhsIter.hasNext()) {
      final float lhsValue = lhsIter.next();
      final float rhsValue = rhsIter.next();
      if (!valueEquals(lhsValue, rhsValue, epsilon)) {
        return false;
      }
    }
    return true;
  }

  /**
   * Tests the equality of the represented values of two {@code double}
   * collections with regard to a given epsilon.
   *
   * <p>That is, if the distance between the two double numbers is less than or
   * equal to the given epsilon, the two double numbers are considered to be
   * equal.
   *
   * <p>Note that this function can NOT be used to implement the
   * {@link Object#equals(Object)} function.
   *
   * @param col1
   *     the first collection, which may be null.
   * @param col2
   *     the second collection, which may be null.
   * @param epsilon
   *     the epsilon used to compare two float numbers.
   * @return true if the represented values of two collections are equal, or
   *     both are null; false otherwise.
   */
  public static boolean valueEquals(@Nullable final DoubleCollection col1,
      @Nullable final DoubleCollection col2, final double epsilon) {
    if (col1 == col2) {
      return true;
    }
    if ((col1 == null) || (col2 == null)) {
      return false;
    }
    if (col1.size() != col2.size()) {
      return false;
    }
    final DoubleIterator lhsIter = col1.iterator();
    final DoubleIterator rhsIter = col2.iterator();
    while (lhsIter.hasNext()) {
      final double lhsValue = lhsIter.next();
      final double rhsValue = rhsIter.next();
      if (!valueEquals(lhsValue, rhsValue, epsilon)) {
        return false;
      }
    }
    return true;
  }

  /**
   * Tests the equality of the represented values of two collections.
   *
   * <p>The elements in the collections to be test may be any objects, or
   * arrays,
   * or another collection of objects.
   *
   * <p>Note that this function test the equality of two float or double
   * numbers
   * using their represented values with regard to a given epsilon. Therefore
   * this function can NOT be used to implement the {@link
   * Object#equals(Object)} function.
   *
   * @param col1
   *     the first collection, which may be null.
   * @param col2
   *     the second collection, which may be null.
   * @param epsilon
   *     the precision.
   * @return true if the represented values of two collections are equal, or
   *     both are null; false otherwise.
   */
  public static boolean valueEquals(@Nullable final Collection<?> col1,
      @Nullable final Collection<?> col2, final double epsilon) {
    if (col1 == col2) {
      return true;
    }
    if ((col1 == null) || (col2 == null)) {
      return false;
    }
    if (col1.size() != col2.size()) {
      return false;
    }
    final Iterator<?> lhsIter = col1.iterator();
    final Iterator<?> rhsIter = col2.iterator();
    while (lhsIter.hasNext()) {
      final Object lhsValue = lhsIter.next();
      final Object rhsValue = rhsIter.next();
      // note that here we must call the valueEquals(Object, Object, double),
      // since the
      // lhsValue and rhsValue may also be array or collection.
      if (!valueEquals(lhsValue, rhsValue, epsilon)) {
        return false;
      }
    }
    return true;
  }

  /**
   * Tests whether the left hand side object is null or equals to the right
   * hand side object.
   *
   * @param lhs
   *     the left hand side object.
   * @param rhs
   *     the right hand side object.
   * @return
   *     {@code true} if the left hand side object is null or equals to the
   *     right hand side object; {@code false} otherwise.
   */
  public static boolean nullOrEquals(@Nullable final Object lhs,
      @Nullable final Object rhs) {
    return (lhs == null) || (lhs.equals(rhs));
  }
}