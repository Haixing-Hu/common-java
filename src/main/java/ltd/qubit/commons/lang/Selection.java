////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.lang;

import java.util.Iterator;

import javax.annotation.concurrent.ThreadSafe;

import ltd.qubit.commons.error.NullArgumentException;

/**
 * This class provides the selection related algorithms.
 *
 * <p>TODO: 1. add the select median functions; 2. finish the unit tests.
 *
 * @author Haixing Hu
 */
@ThreadSafe
public final class Selection {

  private Selection() {
  }

  /**
   * Returns the minimum of two {@code boolean} values.
   *
   * <p>Assume {@code true &gt; false} for {@code boolean} values.
   *
   * @param value1
   *     a {@code boolean} value;
   * @param value2
   *     a {@code boolean} value;
   * @return the minimum of {@code value1} and {@code value2}. In case of ties,
   *     returns the one with the smallest position.
   */
  public static boolean min(final boolean value1, final boolean value2) {
    if (!value1) {
      return false;
    } else {
      return value2;
    }
  }

  /**
   * Returns the minimum of three {@code boolean} values.
   *
   * <p>Assume {@code true &gt; false} for {@code boolean} values.
   *
   * @param value1
   *     a {@code boolean} value;
   * @param value2
   *     a {@code boolean} value;
   * @param value3
   *     a {@code boolean} value;
   * @return the minimum of {@code value1}, {@code value2} and {@code value3}.
   *     In case of ties, returns the one with the smallest position.
   */
  public static boolean min(
      final boolean value1, final boolean value2, final boolean value3) {
    if ((!value1) || (!value2)) {
      return false;
    } else {
      return value3;
    }
  }

  /**
   * Returns the minimum value in a {@code boolean} array.
   *
   * <p>Assume {@code true &gt; false} for {@code boolean} values.
   *
   * @param array
   *     a {@code boolean} array, must not be {@code null} nor empty.
   * @return the minimum value in the array. In case of ties, returns the one
   *     with the smallest position.
   * @throws NullArgumentException
   *     if {@code array} is {@code null} or empty.
   */
  public static boolean min(final boolean... array) {
    if ((array == null) || (array.length == 0)) {
      throw new NullArgumentException();
    }
    for (final boolean element : array) {
      if (!element) {
        return false;
      }
    }
    return true;
  }

  /**
   * Returns the minimum of two {@code Boolean} objects.
   *
   * <p>Assume {@code Boolean.TRUE &gt; Boolean.FALSE &gt; null} for {@code
   * Boolean} objects.
   *
   * @param value1
   *     a {@code Boolean} object, could be {@code null}.
   * @param value2
   *     a {@code Boolean} object, could be {@code null}.
   * @return the minimum of {@code value1} and {@code value2}. In case of ties,
   *     returns the one with the smallest position. Note that the returned
   *     value is either {@code null} or a reference to one of the arguments.
   */
  public static Boolean min(final Boolean value1, final Boolean value2) {
    if ((value1 == null) || (value2 == null)) {
      return null;
    } else {
      final boolean v1 = value1.booleanValue();
      final boolean v2 = value2.booleanValue();
      if (v1 == v2) {
        return value1;
      } else if (v1) {
        // recall that v1 != v2, therefore v1 == true && v2 == false.
        return value2;
      } else {
        // recall that v1 != v2, therefore v1 == false && v2 == true.
        return value1;
      }
    }
  }

  /**
   * Returns the minimum of three {@code Boolean} objects.
   *
   * <p>Assume {@code Boolean.TRUE &gt; Boolean.FALSE &gt; null} for {@code
   * Boolean} objects.
   *
   * @param value1
   *     a {@code Boolean} object, could be {@code null}.
   * @param value2
   *     a {@code Boolean} object, could be {@code null}.
   * @param value3
   *     a {@code Boolean} object, could be {@code null}.
   * @return the minimum of {@code value1}, {@code value2} and {@code value3}.
   *     In case of ties, returns the one with the smallest position. Note that
   *     the returned value is either {@code null} or a reference to one of the
   *     arguments.
   */
  public static Boolean min(
      final Boolean value1, final Boolean value2, final Boolean value3) {
    if ((value1 == null) || (value2 == null) || (value3 == null)) {
      return null;
    } else {
      if (!value1.booleanValue()) {
        return value1;
      }
      // now value1 == true
      if (!value2.booleanValue()) {
        return value2;
      }
      // now value1 == value2 == true
      if (!value3.booleanValue()) {
        return value3;
      }
      // now value1 == value2 == value3 == true
      return value1;
    }
  }

  /**
   * Returns the minimum object in a {@code Boolean} array.
   *
   * <p>Assume that {@code Boolean.TRUE &gt; Boolean.FALSE &gt; null} for {@code
   * Boolean} objects.
   *
   * @param array
   *     a {@code Boolean} array, must not be {@code null} nor empty.
   * @return the minimum object in the array. In case of ties, returns the one
   *     with the smallest position. Note that the returned value is either
   *     {@code null} or a reference to one of elements in the {@code array} .
   * @throws NullArgumentException
   *     if {@code array} is {@code null} or empty.
   */
  public static Boolean min(final Boolean... array) {
    if ((array == null) || (array.length == 0)) {
      throw new NullArgumentException();
    }
    Boolean min = array[0];
    if (min == null) {
      return null;
    }
    boolean minValue = min.booleanValue();
    for (int i = 1; i < array.length; ++i) {
      final Boolean element = array[i];
      if (element == null) {
        return null;
      }
      if (minValue && (!element.booleanValue())) {
        min = element;
        minValue = false;
      }
    }
    return min;
  }

  /**
   * Returns the minimum of two {@code char} values.
   *
   * @param value1
   *     a {@code char} value;
   * @param value2
   *     a {@code char} value;
   * @return the minimum of {@code value1} and {@code value2}. In case of ties,
   *     returns the one with the smallest position.
   */
  public static char min(final char value1, final char value2) {
    return (value1 <= value2 ? value1 : value2);
  }

  /**
   * Returns the minimum of three {@code char} values.
   *
   * @param value1
   *     a {@code char} value;
   * @param value2
   *     a {@code char} value;
   * @param value3
   *     a {@code char} value;
   * @return the minimum of {@code value1}, {@code value2} and {@code value3}.
   *     In case of ties, returns the one with the smallest position.
   */
  public static char min(final char value1, final char value2,
      final char value3) {
    if (value1 <= value2) {
      if (value1 <= value3) {
        return value1;
      } else {
        return value3;
      }
    } else if (value2 <= value3) {
      return value2;
    } else {
      return value3;
    }
  }

  /**
   * Returns the minimum value in a {@code char} array.
   *
   * @param array
   *     a {@code char} array, must not be {@code null} nor empty.
   * @return the minimum value in the array. In case of ties, returns the one
   *     with the smallest position.
   * @throws NullArgumentException
   *     if {@code array} is {@code null} or empty.
   */
  public static char min(final char... array) {
    if ((array == null) || (array.length == 0)) {
      throw new NullArgumentException();
    }
    char min = array[0];
    for (int i = 1; i < array.length; ++i) {
      final char element = array[i];
      if (element < min) {
        min = element;
      }
    }
    return min;
  }

  /**
   * Returns the minimum of two {@code Character} objects.
   *
   * <p>Assume that null is the minimum value of {@code Character} objects.
   *
   * @param value1
   *     a {@code Character} object, could be {@code null}.
   * @param value2
   *     a {@code Character} object, could be {@code null}.
   * @return the minimum of {@code value1} and {@code value2}. In case of ties,
   *     returns the one with the smallest position. Note that the returned
   *     value is either {@code null} or a reference to one of the arguments.
   */
  public static Character min(final Character value1, final Character value2) {
    if ((value1 == null) || (value2 == null)) {
      return null;
    } else {
      final char v1 = value1.charValue();
      final char v2 = value2.charValue();
      return (v1 <= v2 ? value1 : value2);
    }
  }

  /**
   * Returns the minimum of three {@code Character} objects.
   *
   * <p>Assume that null is the minimum value of {@code Character} objects.
   *
   * @param value1
   *     a {@code Character} object, could be {@code null}.
   * @param value2
   *     a {@code Character} object, could be {@code null}.
   * @param value3
   *     a {@code Character} object, could be {@code null}.
   * @return the minimum of {@code value1}, {@code value2} and {@code value3}.
   *     In case of ties, returns the one with the smallest position. Note that
   *     the returned value is either {@code null} or a reference to one of the
   *     arguments.
   */
  public static Character min(final Character value1, final Character value2,
      final Character value3) {
    if ((value1 == null) || (value2 == null) || (value3 == null)) {
      return null;
    }
    final char v1 = value1.charValue();
    final char v2 = value2.charValue();
    final char v3 = value3.charValue();
    if (v1 <= v2) {
      if (v1 <= v3) {
        return value1;
      } else {
        return value3;
      }
    } else if (v2 <= v3) {
      return value2;
    } else {
      return value3;
    }
  }

  /**
   * Returns the minimum object in a {@code Character} array.
   *
   * <p>Assume that null is the minimum value of {@code Character} objects.
   *
   * @param array
   *     a {@code Character} array, must not be {@code null} nor empty.
   * @return the minimum object in the array. In case of ties, returns the one
   *     with the smallest position. Note that the returned value is either
   *     {@code null} or a reference to one of elements in the {@code array}.
   * @throws NullArgumentException
   *     if {@code array} is {@code null} or empty.
   */
  public static Character min(final Character... array) {
    if ((array == null) || (array.length == 0)) {
      throw new NullArgumentException();
    }
    Character min = array[0];
    if (min == null) {
      return null;
    }
    char minValue = min.charValue();
    for (final Character element : array) {
      if (element == null) {
        return null;
      } else {
        final char value = element.charValue();
        if (value < minValue) {
          minValue = value;
          min = element;
        }
      }
    }
    return min;
  }

  /**
   * Returns the minimum of two {@code byte} values.
   *
   * @param value1
   *     a {@code byte} value;
   * @param value2
   *     a {@code byte} value;
   * @return the minimum of {@code value1} and {@code value2}. In case of ties,
   *     returns the one with the smallest position.
   */
  public static byte min(final byte value1, final byte value2) {
    return (value1 <= value2 ? value1 : value2);
  }

  /**
   * Returns the minimum of three {@code byte} values.
   *
   * @param value1
   *     a {@code byte} value;
   * @param value2
   *     a {@code byte} value;
   * @param value3
   *     a {@code byte} value;
   * @return the minimum of {@code value1}, {@code value2} and {@code value3}.
   *     In case of ties, returns the one with the smallest position.
   */
  public static byte min(final byte value1, final byte value2,
      final byte value3) {
    if (value1 <= value2) {
      if (value1 <= value3) {
        return value1;
      } else {
        return value3;
      }
    } else if (value2 <= value3) {
      return value2;
    } else {
      return value3;
    }
  }

  /**
   * Returns the minimum value in a {@code byte} array.
   *
   * @param array
   *     a {@code byte} array, must not be {@code null} nor empty.
   * @return the minimum value in the array. In case of ties, returns the one
   *     with the smallest position.
   * @throws NullArgumentException
   *     if {@code array} is {@code null} or empty.
   */
  public static byte min(final byte... array) {
    if ((array == null) || (array.length == 0)) {
      throw new NullArgumentException();
    }
    byte min = array[0];
    for (int i = 1; i < array.length; ++i) {
      final byte element = array[i];
      if (element < min) {
        min = element;
      }
    }
    return min;
  }

  /**
   * Returns the minimum of two {@code Byte} objects.
   *
   * <p>Assume that null is the minimum value of {@code Byte} objects.
   *
   * @param value1
   *     a {@code Byte} object, could be {@code null}.
   * @param value2
   *     a {@code Byte} object, could be {@code null}.
   * @return the minimum of {@code value1} and {@code value2}. In case of ties,
   *     returns the one with the smallest position. Note that the returned
   *     value is either {@code null} or a reference to one of the arguments.
   */
  public static Byte min(final Byte value1, final Byte value2) {
    if ((value1 == null) || (value2 == null)) {
      return null;
    } else {
      final byte v1 = value1.byteValue();
      final byte v2 = value2.byteValue();
      return (v1 <= v2 ? value1 : value2);
    }
  }

  /**
   * Returns the minimum of three {@code Byte} objects.
   *
   * <p>Assume that null is the minimum value of {@code Byte} objects.
   *
   * @param value1
   *     a {@code Byte} object, could be {@code null}.
   * @param value2
   *     a {@code Byte} object, could be {@code null}.
   * @param value3
   *     a {@code Byte} object;
   * @return the minimum of {@code value1}, {@code value2} and {@code value3}.
   *     In case of ties, returns the one with the smallest position. Note that
   *     the returned value is either {@code null} or a reference to one of the
   *     arguments.
   */
  public static Byte min(final Byte value1, final Byte value2,
      final Byte value3) {
    if ((value1 == null) || (value2 == null) || (value3 == null)) {
      return null;
    }
    final byte v1 = value1.byteValue();
    final byte v2 = value2.byteValue();
    final byte v3 = value3.byteValue();
    if (v1 <= v2) {
      if (v1 <= v3) {
        return value1;
      } else {
        return value3;
      }
    } else if (v2 <= v3) {
      return value2;
    } else {
      return value3;
    }
  }

  /**
   * Returns the minimum object in a {@code Byte} array.
   *
   * <p>Assume that null is the minimum value of {@code Byte} objects.
   *
   * @param array
   *     a {@code Byte} array, must not be {@code null} nor empty.
   * @return the minimum object in the array. In case of ties, returns the one
   *     with the smallest position. Note that the returned value is either
   *     {@code null} or a reference to one of elements in the {@code array}.
   * @throws NullArgumentException
   *     if {@code array} is {@code null} or empty.
   */
  public static Byte min(final Byte... array) {
    if ((array == null) || (array.length == 0)) {
      throw new NullArgumentException();
    }
    Byte min = array[0];
    if (min == null) {
      return null;
    }
    byte minValue = min.byteValue();
    for (final Byte element : array) {
      if (element == null) {
        return null;
      } else {
        final byte value = element.byteValue();
        if (value < minValue) {
          minValue = value;
          min = element;
        }
      }
    }
    return min;
  }

  /**
   * Returns the minimum of two {@code short} values.
   *
   * @param value1
   *     a {@code short} value;
   * @param value2
   *     a {@code short} value;
   * @return the minimum of {@code value1} and {@code value2}. In case of ties,
   *     returns the one with the smallest position.
   */
  public static short min(final short value1, final short value2) {
    return (value1 <= value2 ? value1 : value2);
  }

  /**
   * Returns the minimum of three {@code short} values.
   *
   * @param value1
   *     a {@code short} value;
   * @param value2
   *     a {@code short} value;
   * @param value3
   *     a {@code short} value;
   * @return the minimum of {@code value1}, {@code value2} and {@code value3}.
   *     In case of ties, returns the one with the smallest position.
   */
  public static short min(final short value1, final short value2,
      final short value3) {
    if (value1 <= value2) {
      if (value1 <= value3) {
        return value1;
      } else {
        return value3;
      }
    } else if (value2 <= value3) {
      return value2;
    } else {
      return value3;
    }
  }

  /**
   * Returns the minimum value in a {@code short} array.
   *
   * @param array
   *     a {@code short} array, must not be {@code null} nor empty.
   * @return the minimum value in the array. In case of ties, returns the one
   *     with the smallest position.
   * @throws NullArgumentException
   *     if {@code array} is {@code null} or empty.
   */
  public static short min(final short... array) {
    if ((array == null) || (array.length == 0)) {
      throw new NullArgumentException();
    }
    short min = array[0];
    for (int i = 1; i < array.length; ++i) {
      final short element = array[i];
      if (element < min) {
        min = element;
      }
    }
    return min;
  }

  /**
   * Returns the minimum of two {@code Short} objects.
   *
   * <p>Assume that null is the minimum value of {@code Short} objects.
   *
   * @param value1
   *     a {@code Short} object, could be {@code null}.
   * @param value2
   *     a {@code Short} object, could be {@code null}.
   * @return the minimum of {@code value1} and {@code value2}. In case of ties,
   *     returns the one with the smallest position. Note that the returned
   *     value is either {@code null} or a reference to one of the arguments.
   */
  public static Short min(final Short value1, final Short value2) {
    if ((value1 == null) || (value2 == null)) {
      return null;
    } else {
      final short v1 = value1.shortValue();
      final short v2 = value2.shortValue();
      return (v1 <= v2 ? value1 : value2);
    }
  }

  /**
   * Returns the minimum of three {@code Short} objects.
   *
   * <p>Assume that null is the minimum value of {@code Short} objects.
   *
   * @param value1
   *     a {@code Short} object, could be {@code null}.
   * @param value2
   *     a {@code Short} object, could be {@code null}.
   * @param value3
   *     a {@code Short} object, could be {@code null}.
   * @return the minimum of {@code value1}, {@code value2} and {@code value3}.
   *     In case of ties, returns the one with the smallest position. Note that
   *     the returned value is either {@code null} or a reference to one of the
   *     arguments.
   */
  public static Short min(final Short value1, final Short value2,
      final Short value3) {
    if ((value1 == null) || (value2 == null) || (value3 == null)) {
      return null;
    }
    final short v1 = value1.shortValue();
    final short v2 = value2.shortValue();
    final short v3 = value3.shortValue();
    if (v1 <= v2) {
      if (v1 <= v3) {
        return value1;
      } else {
        return value3;
      }
    } else if (v2 <= v3) {
      return value2;
    } else {
      return value3;
    }
  }

  /**
   * Returns the minimum object in a {@code Short} array.
   *
   * <p>Assume that null is the minimum value of {@code Short} objects.
   *
   * @param array
   *     a {@code Short} array, must not be {@code null} nor empty.
   * @return the minimum object in the array. In case of ties, returns the one
   *     with the smallest position. Note that the returned value is either
   *     {@code null} or a reference to one of elements in the {@code array}.
   * @throws NullArgumentException
   *     if {@code array} is {@code null} or empty.
   */
  public static Short min(final Short... array) {
    if ((array == null) || (array.length == 0)) {
      throw new NullArgumentException();
    }
    Short min = array[0];
    if (min == null) {
      return null;
    }
    short minValue = min.shortValue();
    for (final Short element : array) {
      if (element == null) {
        return null;
      } else {
        final short value = element.shortValue();
        if (value < minValue) {
          minValue = value;
          min = element;
        }
      }
    }
    return min;
  }

  /**
   * Returns the minimum of two {@code int} values.
   *
   * @param value1
   *     a {@code int} value;
   * @param value2
   *     a {@code int} value;
   * @return the minimum of {@code value1} and {@code value2}. In case of ties,
   *     returns the one with the smallest position.
   */
  public static int min(final int value1, final int value2) {
    return (value1 <= value2 ? value1 : value2);
  }

  /**
   * Returns the minimum of three {@code int} values.
   *
   * @param value1
   *     a {@code int} value;
   * @param value2
   *     a {@code int} value;
   * @param value3
   *     a {@code int} value;
   * @return the minimum of {@code value1}, {@code value2} and {@code value3}.
   *     In case of ties, returns the one with the smallest position.
   */
  public static int min(final int value1, final int value2, final int value3) {
    if (value1 <= value2) {
      if (value1 <= value3) {
        return value1;
      } else {
        return value3;
      }
    } else if (value2 <= value3) {
      return value2;
    } else {
      return value3;
    }
  }

  /**
   * Returns the minimum value in a {@code int} array.
   *
   * @param array
   *     a {@code int} array, must not be {@code null} nor empty.
   * @return the minimum value in the array. In case of ties, returns the one
   *     with the smallest position.
   * @throws NullArgumentException
   *     if {@code array} is {@code null} or empty.
   */
  public static int min(final int... array) {
    if ((array == null) || (array.length == 0)) {
      throw new NullArgumentException();
    }
    int min = array[0];
    for (int i = 1; i < array.length; ++i) {
      final int element = array[i];
      if (element < min) {
        min = element;
      }
    }
    return min;
  }

  /**
   * Returns the minimum of two {@code Integer} objects.
   *
   * <p>Assume that null is the minimum value of {@code Integer} objects.
   *
   * @param value1
   *     a {@code Integer} object, could be {@code null}.
   * @param value2
   *     a {@code Integer} object, could be {@code null}.
   * @return the minimum of {@code value1} and {@code value2}. In case of ties,
   *     returns the one with the smallest position. Note that the returned
   *     value is either {@code null} or a reference to one of the arguments.
   */
  public static Integer min(final Integer value1, final Integer value2) {
    if ((value1 == null) || (value2 == null)) {
      return null;
    } else {
      final int v1 = value1.intValue();
      final int v2 = value2.intValue();
      return (v1 <= v2 ? value1 : value2);
    }
  }

  /**
   * Returns the minimum of three {@code Integer} objects.
   *
   * <P>Assume that null is the minimum value of {@code Integer} objects.
   *
   * @param value1
   *     a {@code Integer} object, could be {@code null}.
   * @param value2
   *     a {@code Integer} object, could be {@code null}.
   * @param value3
   *     a {@code Integer} object, could be {@code null}.
   * @return the minimum of {@code value1}, {@code value2} and {@code value3}.
   *     In case of ties, returns the one with the smallest position. Note that
   *     the returned value is either {@code null} or a reference to one of the
   *     arguments.
   */
  public static Integer min(
      final Integer value1, final Integer value2, final Integer value3) {
    if ((value1 == null) || (value2 == null) || (value3 == null)) {
      return null;
    }
    final int v1 = value1.intValue();
    final int v2 = value2.intValue();
    final int v3 = value3.intValue();
    if (v1 <= v2) {
      if (v1 <= v3) {
        return value1;
      } else {
        return value3;
      }
    } else if (v2 <= v3) {
      return value2;
    } else {
      return value3;
    }
  }

  /**
   * Returns the minimum object in a {@code Integer} array.
   *
   * <P>Assume that null is the minimum value of {@code Integer} objects.
   *
   * @param array
   *     a {@code Integer} array, must not be {@code null} nor empty.
   * @return the minimum object in the array. In case of ties, returns the one
   *     with the smallest position. Note that the returned value is either
   *     {@code null} or a reference to one of elements in the {@code array}.
   * @throws NullArgumentException
   *     if {@code array} is {@code null} or empty.
   */
  public static Integer min(final Integer... array) {
    if ((array == null) || (array.length == 0)) {
      throw new NullArgumentException();
    }
    Integer min = array[0];
    if (min == null) {
      return null;
    }
    int minValue = min.intValue();
    for (final Integer element : array) {
      if (element == null) {
        return null;
      } else {
        final int value = element.intValue();
        if (value < minValue) {
          minValue = value;
          min = element;
        }
      }
    }
    return min;
  }

  /**
   * Returns the minimum of two {@code long} values.
   *
   * @param value1
   *     a {@code long} value;
   * @param value2
   *     a {@code long} value;
   * @return the minimum of {@code value1} and {@code value2}. In case of ties,
   *     returns the one with the smallest position.
   */
  public static long min(final long value1, final long value2) {
    return (value1 <= value2 ? value1 : value2);
  }

  /**
   * Returns the minimum of three {@code long} values.
   *
   * @param value1
   *     a {@code long} value;
   * @param value2
   *     a {@code long} value;
   * @param value3
   *     a {@code long} value;
   * @return the minimum of {@code value1}, {@code value2} and {@code value3}.
   *     In case of ties, returns the one with the smallest position.
   */
  public static long min(final long value1, final long value2,
      final long value3) {
    if (value1 <= value2) {
      if (value1 <= value3) {
        return value1;
      } else {
        return value3;
      }
    } else if (value2 <= value3) {
      return value2;
    } else {
      return value3;
    }
  }

  /**
   * Returns the minimum value in a {@code long} array.
   *
   * @param array
   *     a {@code long} array, must not be {@code null} nor empty.
   * @return the minimum value in the array. In case of ties, returns the one
   *     with the smallest position.
   * @throws NullArgumentException
   *     if {@code array} is {@code null} or empty.
   */
  public static long min(final long... array) {
    if ((array == null) || (array.length == 0)) {
      throw new NullArgumentException();
    }
    long min = array[0];
    for (int i = 1; i < array.length; ++i) {
      final long element = array[i];
      if (element < min) {
        min = element;
      }
    }
    return min;
  }

  /**
   * Returns the minimum of two {@code Long} objects.
   *
   * <P>Assume that null is the minimum value of {@code Long} objects.
   *
   * @param value1
   *     a {@code Long} object, could be {@code null}.
   * @param value2
   *     a {@code Long} object, could be {@code null}.
   * @return the minimum of {@code value1} and {@code value2}. In case of ties,
   *     returns the one with the smallest position. Note that the returned
   *     value is either {@code null} or a reference to one of the arguments.
   */
  public static Long min(final Long value1, final Long value2) {
    if ((value1 == null) || (value2 == null)) {
      return null;
    } else {
      final long v1 = value1.longValue();
      final long v2 = value2.longValue();
      return (v1 <= v2 ? value1 : value2);
    }
  }

  /**
   * Returns the minimum of three {@code Long} objects.
   *
   * <P>Assume that null is the minimum value of {@code Long} objects.
   *
   * @param value1
   *     a {@code Long} object, could be {@code null}.
   * @param value2
   *     a {@code Long} object, could be {@code null}.
   * @param value3
   *     a {@code Long} object, could be {@code null}.
   * @return the minimum of {@code value1}, {@code value2} and {@code value3}.
   *     In case of ties, returns the one with the smallest position. Note that
   *     the returned value is either {@code null} or a reference to one of the
   *     arguments.
   */
  public static Long min(final Long value1, final Long value2,
      final Long value3) {
    if ((value1 == null) || (value2 == null) || (value3 == null)) {
      return null;
    }
    final long v1 = value1.longValue();
    final long v2 = value2.longValue();
    final long v3 = value3.longValue();
    if (v1 <= v2) {
      if (v1 <= v3) {
        return value1;
      } else {
        return value3;
      }
    } else if (v2 <= v3) {
      return value2;
    } else {
      return value3;
    }
  }

  /**
   * Returns the minimum object in a {@code Long} array.
   *
   * <P>Assume that null is the minimum value of {@code Long} objects.
   *
   * @param array
   *     a {@code Long} array, must not be {@code null} nor empty.
   * @return the minimum object in the array. In case of ties, returns the one
   *     with the smallest position. Note that the returned value is either
   *     {@code null} or a reference to one of elements in the {@code array}.
   * @throws NullArgumentException
   *     if {@code array} is {@code null} or empty.
   */
  public static Long min(final Long... array) {
    if ((array == null) || (array.length == 0)) {
      throw new NullArgumentException();
    }
    Long min = array[0];
    if (min == null) {
      return null;
    }
    long minValue = min.longValue();
    for (final Long element : array) {
      if (element == null) {
        return null;
      } else {
        final long value = element.longValue();
        if (value < minValue) {
          minValue = value;
          min = element;
        }
      }
    }
    return min;
  }

  /**
   * Returns the minimum of two {@code float} values.
   *
   * @param value1
   *     a {@code float} value;
   * @param value2
   *     a {@code float} value;
   * @return the minimum of {@code value1} and {@code value2}. In case of ties,
   *     returns the one with the smallest position.
   */
  public static float min(final float value1, final float value2) {
    if (Comparison.compare(value1, value2) <= 0) {
      return value1;
    } else {
      return value2;
    }
  }

  /**
   * Returns the minimum of three {@code float} values.
   *
   * @param value1
   *     a {@code float} value;
   * @param value2
   *     a {@code float} value;
   * @param value3
   *     a {@code float} value;
   * @return the minimum of {@code value1}, {@code value2} and {@code value3}.
   *     In case of ties, returns the one with the smallest position.
   */
  public static float min(final float value1, final float value2,
      final float value3) {
    if (Comparison.compare(value1, value2) <= 0) {
      if (Comparison.compare(value1, value3) <= 0) {
        return value1;
      } else {
        return value3;
      }
    } else if (Comparison.compare(value2, value3) <= 0) {
      return value2;
    } else {
      return value3;
    }
  }

  /**
   * Returns the minimum value in a {@code float} array.
   *
   * @param array
   *     a {@code float} array, must not be {@code null} nor empty.
   * @return the minimum value in the array. In case of ties, returns the one
   *     with the smallest position.
   * @throws NullArgumentException
   *     if {@code array} is {@code null} or empty.
   */
  public static float min(final float... array) {
    if ((array == null) || (array.length == 0)) {
      throw new NullArgumentException();
    }
    float min = array[0];
    for (int i = 1; i < array.length; ++i) {
      final float element = array[i];
      if (Comparison.compare(element, min) < 0) {
        min = element;
      }
    }
    return min;
  }

  /**
   * Returns the minimum of two {@code Float} objects.
   *
   * <P>Assume that null is the minimum value of {@code Float} objects.
   *
   * @param value1
   *     a {@code Float} object, could be {@code null}.
   * @param value2
   *     a {@code Float} object, could be {@code null}.
   * @return the minimum of {@code value1} and {@code value2}. In case of ties,
   *     returns the one with the smallest position. Note that the returned
   *     value is either {@code null} or a reference to one of the arguments.
   */
  public static Float min(final Float value1, final Float value2) {
    if ((value1 == null) || (value2 == null)) {
      return null;
    }
    final float v1 = value1.floatValue();
    final float v2 = value2.floatValue();
    if (Comparison.compare(v1, v2) <= 0) {
      return value1;
    } else {
      return value2;
    }
  }

  /**
   * Returns the minimum of three {@code Float} objects.
   *
   * <P>Assume that null is the minimum value of {@code Float} objects.
   *
   * @param value1
   *     a {@code Float} object, could be {@code null}.
   * @param value2
   *     a {@code Float} object, could be {@code null}.
   * @param value3
   *     a {@code Float} object, could be {@code null}.
   * @return the minimum of {@code value1}, {@code value2} and {@code value3}.
   *     In case of ties, returns the one with the smallest position. Note that
   *     the returned value is either {@code null} or a reference to one of the
   *     arguments.
   */
  public static Float min(final Float value1, final Float value2,
      final Float value3) {
    if ((value1 == null) || (value2 == null) || (value3 == null)) {
      return null;
    }
    final float v1 = value1.floatValue();
    final float v2 = value2.floatValue();
    final float v3 = value3.floatValue();
    if (Comparison.compare(v1, v2) <= 0) {
      if (Comparison.compare(v1, v3) <= 0) {
        return value1;
      } else {
        return value3;
      }
    } else if (Comparison.compare(v2, v3) <= 0) {
      return value2;
    } else {
      return value3;
    }
  }

  /**
   * Returns the minimum object in a {@code Float} array.
   *
   * <P>Assume that null is the minimum value of {@code Float} objects.
   *
   * @param array
   *     a {@code Float} array, must not be {@code null} nor empty.
   * @return the minimum object in the array. In case of ties, returns the one
   *     with the smallest position. Note that the returned value is either
   *     {@code null} or a reference to one of elements in the {@code array}.
   * @throws NullArgumentException
   *     if {@code array} is {@code null} or empty.
   */
  public static Float min(final Float... array) {
    if ((array == null) || (array.length == 0)) {
      throw new NullArgumentException();
    }
    Float min = array[0];
    if (min == null) {
      return null;
    }
    float minValue = min.floatValue();
    for (final Float element : array) {
      if (element == null) {
        return null;
      } else {
        final float value = element.floatValue();
        if (Comparison.compare(value, minValue) < 0) {
          minValue = value;
          min = element;
        }
      }
    }
    return min;
  }

  /**
   * Returns the minimum of two {@code double} values.
   *
   * @param value1
   *     a {@code double} value;
   * @param value2
   *     a {@code double} value;
   * @return the minimum of {@code value1} and {@code value2}. In case of ties,
   *     returns the one with the smallest position.
   */
  public static double min(final double value1, final double value2) {
    if (Comparison.compare(value1, value2) <= 0) {
      return value1;
    } else {
      return value2;
    }
  }

  /**
   * Returns the minimum of three {@code double} values.
   *
   * @param value1
   *     a {@code double} value;
   * @param value2
   *     a {@code double} value;
   * @param value3
   *     a {@code double} value;
   * @return the minimum of {@code value1}, {@code value2} and {@code value3}.
   *     In case of ties, returns the one with the smallest position.
   */
  public static double min(
      final double value1, final double value2, final double value3) {
    if (Comparison.compare(value1, value2) <= 0) {
      if (Comparison.compare(value1, value3) <= 0) {
        return value1;
      } else {
        return value3;
      }
    } else if (Comparison.compare(value2, value3) <= 0) {
      return value2;
    } else {
      return value3;
    }
  }

  /**
   * Returns the minimum value in a {@code double} array.
   *
   * @param array
   *     a {@code double} array, must not be {@code null} nor empty.
   * @return the minimum value in the array. In case of ties, returns the one
   *     with the smallest position.
   * @throws NullArgumentException
   *     if {@code array} is {@code null} or empty.
   */
  public static double min(final double... array) {
    if ((array == null) || (array.length == 0)) {
      throw new NullArgumentException();
    }
    double min = array[0];
    for (int i = 1; i < array.length; ++i) {
      final double element = array[i];
      if (Comparison.compare(element, min) < 0) {
        min = element;
      }
    }
    return min;
  }

  /**
   * Returns the minimum of two {@code Double} objects.
   *
   * <P>Assume that null is the minimum value of {@code Double} objects.
   *
   * @param value1
   *     a {@code Double} object, could be {@code null}.
   * @param value2
   *     a {@code Double} object, could be {@code null}.
   * @return the minimum of {@code value1} and {@code value2}. In case of ties,
   *     returns the one with the smallest position. Note that the returned
   *     value is either {@code null} or a reference to one of the arguments.
   */
  public static Double min(final Double value1, final Double value2) {
    if ((value1 == null) || (value2 == null)) {
      return null;
    }
    final double v1 = value1.doubleValue();
    final double v2 = value2.doubleValue();
    if (Comparison.compare(v1, v2) <= 0) {
      return value1;
    } else {
      return value2;
    }
  }

  /**
   * Returns the minimum of three {@code Double} objects.
   *
   * <P>Assume that null is the minimum value of {@code Double} objects.
   *
   * @param value1
   *     a {@code Double} object, could be {@code null}.
   * @param value2
   *     a {@code Double} object, could be {@code null}.
   * @param value3
   *     a {@code Double} object, could be {@code null}.
   * @return the minimum of {@code value1}, {@code value2} and {@code value3}.
   *     In case of ties, returns the one with the smallest position. Note that
   *     the returned value is either {@code null} or a reference to one of the
   *     arguments.
   */
  public static Double min(
      final Double value1, final Double value2, final Double value3) {
    if ((value1 == null) || (value2 == null) || (value3 == null)) {
      return null;
    }
    final double v1 = value1.doubleValue();
    final double v2 = value2.doubleValue();
    final double v3 = value3.doubleValue();
    if (Comparison.compare(v1, v2) <= 0) {
      if (Comparison.compare(v1, v3) <= 0) {
        return value1;
      } else {
        return value3;
      }
    } else if (Comparison.compare(v2, v3) <= 0) {
      return value2;
    } else {
      return value3;
    }
  }

  /**
   * Returns the minimum object in a {@code Double} array.
   *
   * <P>Assume that null is the minimum value of {@code Double} objects.
   *
   * @param array
   *     a {@code Double} array, must not be {@code null} nor empty.
   * @return the minimum object in the array. In case of ties, returns the one
   *     with the smallest position. Note that the returned value is either
   *     {@code null} or a reference to one of elements in the {@code array}.
   * @throws NullArgumentException
   *     if {@code array} is {@code null} or empty.
   */
  public static Double min(final Double... array) {
    if ((array == null) || (array.length == 0)) {
      throw new NullArgumentException();
    }
    Double min = array[0];
    if (min == null) {
      return null;
    }
    double minValue = min.doubleValue();
    for (final Double element : array) {
      if (element == null) {
        return null;
      } else {
        final double value = element.doubleValue();
        if (Comparison.compare(value, minValue) < 0) {
          minValue = value;
          min = element;
        }
      }
    }
    return min;
  }

  /**
   * Returns the minimum of two {@code String} objects.
   *
   * <P>Assume that {@code null} is the minimum value of {@code String} objects.
   *
   * @param value1
   *     a {@code String} object, could be {@code null}.
   * @param value2
   *     a {@code String} object, could be {@code null}.
   * @return the minimum of {@code value1} and {@code value2}. In case of ties,
   *     returns the one with the smallest position.
   */
  public static String min(final String value1, final String value2) {
    if ((value1 == null) || (value2 == null)) {
      return null;
    } else if (value1.compareTo(value2) <= 0) {
      return value1;
    } else {
      return value2;
    }
  }

  /**
   * Returns the minimum of three {@code String} objects.
   *
   * <P>Assume that {@code null} is the minimum value of {@code String} objects.
   *
   * @param value1
   *     a {@code String} object, could be {@code null}.
   * @param value2
   *     a {@code String} object, could be {@code null}.
   * @param value3
   *     a {@code String} object, could be {@code null}.
   * @return the minimum of {@code value1}, {@code value2} and {@code value3}.
   *     In case of ties, returns the one with the smallest position.
   */
  public static String min(
      final String value1, final String value2, final String value3) {
    if ((value1 == null) || (value2 == null) || (value3 == null)) {
      return null;
    }
    if (value1.compareTo(value2) <= 0) {
      if (value1.compareTo(value3) <= 0) {
        return value1;
      } else {
        return value3;
      }
    } else if (value2.compareTo(value3) <= 0) {
      return value2;
    } else {
      return value3;
    }
  }

  /**
   * Returns the minimum object in a {@code String} array.
   *
   * <P>Assume that null is the minimum value of {@code String} objects.
   *
   * @param array
   *     a {@code String} array, must not be {@code null} nor empty.
   * @return the minimum object in the array. In case of ties, returns the one
   *     with the smallest position. Note that the returned value is either
   *     {@code null} or a reference to one of elements in the {@code array}.
   * @throws NullArgumentException
   *     if {@code array} is {@code null} or empty.
   */
  public static String min(final String... array) {
    if ((array == null) || (array.length == 0)) {
      throw new NullArgumentException();
    }
    String min = array[0];
    if (min == null) {
      return null;
    }
    for (final String element : array) {
      if (element == null) {
        return null;
      } else if (element.compareTo(min) < 0) {
        min = element;
      }
    }
    return min;
  }

  /**
   * Returns the minimum of two {@code Comparable} objects.
   *
   * <P>Assume that {@code null} is the minimum value.
   *
   * @param <T>
   *     any object type. Note that it could be type of arrays or
   *     multi-dimensional arrays.
   * @param value1
   *     an object of class {@code T}, could be {@code null}.
   * @param value2
   *     an object of class {@code T}, could be {@code null}.
   * @return the minimum of {@code value1} and {@code value2}. In case of ties,
   *     returns the one with the smallest position.
   */
  public static <T> T min(final T value1, final T value2) {
    if (Comparison.compare(value1, value2) <= 0) {
      return value1;
    } else {
      return value2;
    }
  }

  /**
   * Returns the minimum of three {@code Comparable} objects.
   *
   * <P>Assume that {@code null} is the minimum value.
   *
   * @param <T>
   *     any object type. Note that it could be type of arrays or
   *     multi-dimensional arrays.
   * @param value1
   *     a object of class {@code T}, could be {@code null}.
   * @param value2
   *     a object of class {@code T}, could be {@code null}.
   * @param value3
   *     a object of class {@code T}, could be {@code null}.
   * @return the minimum of {@code value1}, {@code value2} and {@code value3}.
   *     In case of ties, returns the one with the smallest position.
   */
  public static <T> T min(final T value1, final T value2, final T value3) {
    if (Comparison.compare(value1, value2) <= 0) {
      if (Comparison.compare(value1, value3) <= 0) {
        return value1;
      } else {
        return value3;
      }
    } else if (Comparison.compare(value2, value3) <= 0) {
      return value2;
    } else {
      return value3;
    }
  }

  /**
   * Returns the minimum object in a {@code Comparable} array.
   *
   * <P>Assume that null is the minimum value .
   *
   * @param <T>
   *     any object type. Note that it could be type of arrays or
   *     multi-dimensional arrays.
   * @param array
   *     a array of class {@code T}, must not be {@code null} nor empty.
   * @return the minimum object in the array. In case of ties, returns the one
   *     with the smallest position. Note that the returned value is either
   *     {@code null} or a reference to one of elements in the {@code array}.
   * @throws NullArgumentException
   *     if {@code array} is {@code null} or empty.
   */
  @SafeVarargs
  public static <T> T min(final T... array) {
    if ((array == null) || (array.length == 0)) {
      throw new NullArgumentException();
    }
    T min = array[0];
    if (min == null) {
      return null;
    }
    for (final T element : array) {
      if (element == null) {
        return null;
      } else if (Comparison.compare(element, min) < 0) {
        min = element;
      }
    }
    return min;
  }

  /**
   * Returns the minimum object in a {@code Comparable} {@code Iterable}
   * object.
   *
   * <P>Assume that null is the minimum value.
   *
   * @param <T>
   *     any object type. Note that it could be type of arrays or
   *     multi-dimensional arrays.
   * @param iterable
   *     an {@code Iterable} of values of class {@code T}, must not be {@code
   *     null} nor empty.
   * @return the minimum object in the list.In case of ties, returns the one
   *     with the smallest position. Note that the returned value is either
   *     {@code null} or a reference to one of elements in the {@code
   *     iterable}.
   * @throws NullArgumentException
   *     if {@code iterable} is {@code null} or empty.
   */
  public static <T> T min(final Iterable<T> iterable) {
    if (iterable == null) {
      throw new NullArgumentException();
    }
    final Iterator<T> iter = iterable.iterator();
    if (!iter.hasNext()) {
      throw new NullArgumentException();
    }
    T min = iter.next();
    if (min == null) {
      return null;
    }
    while (iter.hasNext()) {
      final T element = iter.next();
      if (element == null) {
        return null;
      } else if (Comparison.compare(element, min) < 0) {
        min = element;
      }
    }
    return min;
  }

  /**
   * Returns the maximum of two {@code boolean} values.
   *
   * <P>Assume {@code true &gt; false} for {@code boolean} values.
   *
   * @param value1
   *     a {@code boolean} value;
   * @param value2
   *     a {@code boolean} value;
   * @return the maximum of {@code value1} and {@code value2}. In case of ties,
   *     returns the one with the largest position.
   */
  public static boolean max(final boolean value1, final boolean value2) {
    if (value2) {
      return true;
    } else {
      return value1;
    }
  }

  /**
   * Returns the maximum of three {@code boolean} values.
   *
   * <P>Assume {@code true &gt; false} for {@code boolean} values.
   *
   * @param value1
   *     a {@code boolean} value;
   * @param value2
   *     a {@code boolean} value;
   * @param value3
   *     a {@code boolean} value;
   * @return the maximum of {@code value1}, {@code value2} and {@code value3}.
   *     In case of ties, returns the one with the largest position.
   */
  public static boolean max(
      final boolean value1, final boolean value2, final boolean value3) {
    if (value3 || value2) {
      return true;
    } else {
      return value1;
    }
  }

  /**
   * Returns the maximum value in a {@code boolean} array.
   *
   * <P>Assume {@code true &gt; false} for {@code boolean} values.
   *
   * @param array
   *     a {@code boolean} array, must not be {@code null} nor empty.
   * @return the maximum value in the array. In case of ties, returns the one
   *     with the largest position.
   * @throws NullArgumentException
   *     if {@code array} is {@code null} or empty.
   */
  public static boolean max(final boolean... array) {
    if ((array == null) || (array.length == 0)) {
      throw new NullArgumentException();
    }
    for (final boolean element : array) {
      if (element) {
        return true;
      }
    }
    return false;
  }

  /**
   * Returns the maximum of two {@code Boolean} objects.
   *
   * <P>Assume {@code Boolean.TRUE &gt;Boolean.FALSE &gt; null} for {@code Boolean}
   * objects.
   *
   * @param value1
   *     a {@code Boolean} object, could be {@code null}.
   * @param value2
   *     a {@code Boolean} object, could be {@code null}.
   * @return the maximum of {@code value1} and {@code value2}. In case of ties,
   *     returns the one with the largest position. Note that the returned value
   *     is either {@code null} or a reference to one of the arguments.
   */
  public static Boolean max(final Boolean value1, final Boolean value2) {
    if (value1 == null) {
      return value2;
    } else if (value2 == null) {
      return value1;
    } else {    // value1 != null && value2 != null
      final boolean v1 = value1.booleanValue();
      final boolean v2 = value2.booleanValue();
      if (v2 || (v2 == v1)) {  // v2 >= v1
        return value2;
      } else {                 // v2 < v1
        return value1;
      }
    }
  }

  /**
   * Returns the maximum of three {@code Boolean} objects.
   *
   * <P>Assume {@code Boolean.TRUE &gt; Boolean.FALSE &gt; null} for {@code
   * Boolean} objects.
   *
   * @param value1
   *     a {@code Boolean} object, could be {@code null}.
   * @param value2
   *     a {@code Boolean} object, could be {@code null}.
   * @param value3
   *     a {@code Boolean} object, could be {@code null}.
   * @return the maximum of {@code value1}, {@code value2} and {@code value3}.
   *     In case of ties, returns the one with the largest position. Note that
   *     the returned value is either {@code null} or a reference to one of the
   *     arguments.
   */
  public static Boolean max(
      final Boolean value1, final Boolean value2, final Boolean value3) {
    if (value1 == null) {
      // returns the maximum of value2 and value3
      return max(value2, value3);
    } else if (value2 == null) {
      // returns the maximum of value1 and value3
      return max(value1, value3);
    } else if (value3 == null) {
      // returns the maximum of value1 and value2
      return max(value1, value2);
    } else {
      // returns the maximum of non-null value1, value2 and value3
      if (value3.booleanValue()) {
        return value3;
      }
      if (value2.booleanValue()) {
        return value2;
      }
      if (value1.booleanValue()) {
        return value1;
      }
      // value1 == value2 == value3 == false
      return value3;
    }
  }

  /**
   * Returns the maximum object in a {@code Boolean} array.
   *
   * <P>Assume that {@code Boolean.TRUE &gt; Boolean.FALSE &gt; null} for {@code
   * Boolean} objects.
   *
   * @param array
   *     a {@code Boolean} array, must not be {@code null} nor empty.
   * @return the maximum object in the array. In case of ties, returns the one
   *     with the largest position. Note that the returned value is either
   *     {@code null} or a reference to one of elements in the {@code array}.
   * @throws NullArgumentException
   *     if {@code array} is {@code null} or empty.
   */
  public static Boolean max(final Boolean... array) {
    if ((array == null) || (array.length == 0)) {
      throw new NullArgumentException();
    }
    // find the last non-null element in array
    int i = array.length - 1;
    while ((i >= 0) && (array[i] == null)) {
      --i;
    }
    if (i < 0) {
      // all elements in array are null
      return null;
    }
    final Boolean max = array[i];
    if (max.booleanValue()) {
      return max;
    }
    // now max.booleanValue() == false
    for (--i; i >= 0; --i) {
      final Boolean element = array[i];
      if ((element != null) && element.booleanValue()) {
        return element;
      }
    }
    return max;
  }

  /**
   * Returns the maximum of two {@code char} values.
   *
   * @param value1
   *     a {@code char} value;
   * @param value2
   *     a {@code char} value;
   * @return the maximum of {@code value1} and {@code value2}. In case of ties,
   *     returns the one with the largest position.
   */
  public static char max(final char value1, final char value2) {
    return (value1 <= value2 ? value2 : value1);
  }

  /**
   * Returns the maximum of three {@code char} values.
   *
   * @param value1
   *     a {@code char} value;
   * @param value2
   *     a {@code char} value;
   * @param value3
   *     a {@code char} value;
   * @return the maximum of {@code value1}, {@code value2} and {@code value3}.
   *     In case of ties, returns the one with the largest position.
   */
  public static char max(final char value1, final char value2,
      final char value3) {
    if (value3 >= value2) {
      if (value3 >= value1) {
        return value3;
      } else {
        return value1;
      }
    } else if (value2 >= value1) {
      return value2;
    } else {
      return value1;
    }
  }

  /**
   * Returns the maximum value in a {@code char} array.
   *
   * @param array
   *     a {@code char} array, must not be {@code null} nor empty.
   * @return the maximum value in the array. In case of ties, returns the one
   *     with the largest position.
   * @throws NullArgumentException
   *     if {@code array} is {@code null} or empty.
   */
  public static char max(final char... array) {
    if ((array == null) || (array.length == 0)) {
      throw new NullArgumentException();
    }
    int i = array.length - 1;
    char max = array[i];
    for (--i; i >= 0; --i) {
      final char element = array[i];
      if (element > max) {
        max = element;
      }
    }
    return max;
  }

  /**
   * Returns the maximum of two {@code Character} objects.
   *
   * <P>Assume that null is the minimum value of {@code Character} objects.
   *
   * @param value1
   *     a {@code Character} object, could be {@code null}.
   * @param value2
   *     a {@code Character} object, could be {@code null}.
   * @return the maximum of {@code value1} and {@code value2}. In case of ties,
   *     returns the one with the largest position. Note that the returned value
   *     is either {@code null} or a reference to one of the arguments.
   */
  public static Character max(final Character value1, final Character value2) {
    if (value1 == null) {
      return value2;
    } else if (value2 == null) {
      return value1;
    } else {
      final char v1 = value1.charValue();
      final char v2 = value2.charValue();
      return (v1 <= v2 ? value2 : value1);
    }
  }

  /**
   * Returns the maximum of three {@code Character} objects.
   *
   * <P>Assume that null is the minimum value of {@code Character} objects.
   *
   * @param value1
   *     a {@code Character} object, could be {@code null}.
   * @param value2
   *     a {@code Character} object, could be {@code null}.
   * @param value3
   *     a {@code Character} object, could be {@code null}.
   * @return the maximum of {@code value1}, {@code value2} and {@code value3}.
   *     In case of ties, returns the one with the largest position. Note that
   *     the returned value is either {@code null} or a reference to one of the
   *     arguments.
   */
  public static Character max(final Character value1, final Character value2,
      final Character value3) {
    if (value1 == null) {
      return max(value2, value3);
    } else if (value2 == null) {
      return max(value1, value3);
    } else if (value3 == null) {
      return max(value1, value2);
    } else {
      final char v1 = value1.charValue();
      final char v2 = value2.charValue();
      final char v3 = value3.charValue();
      if (v3 >= v2) {
        if (v3 >= v1) {
          return value3;
        } else {
          return value1;
        }
      } else if (v2 >= v1) {
        return value2;
      } else {
        return value1;
      }
    }
  }

  /**
   * Returns the maximum object in a {@code Character} array.
   *
   * <P>Assume that null is the minimum value of {@code Character} objects.
   *
   * @param array
   *     a {@code Character} array, must not be {@code null} nor empty.
   * @return the maximum object in the array. In case of ties, returns the one
   *     with the largest position. Note that the returned value is either
   *     {@code null} or a reference to one of elements in the {@code array}.
   * @throws NullArgumentException
   *     if {@code array} is {@code null} or empty.
   */
  public static Character max(final Character... array) {
    if ((array == null) || (array.length == 0)) {
      throw new NullArgumentException();
    }
    // find the last non-null element
    int i = array.length - 1;
    while ((i >= 0) && (array[i] == null)) {
      --i;
    }
    if (i < 0) {
      // all elements in array are null
      return null;
    }
    Character max = array[i];
    char maxValue = max.charValue();
    // test the rest elements
    for (--i; i >= 0; --i) {
      final Character element = array[i];
      if (element != null) {
        final char value = element.charValue();
        if (value > maxValue) {
          maxValue = value;
          max = element;
        }
      }
    }
    return max;
  }

  /**
   * Returns the maximum of two {@code byte} values.
   *
   * @param value1
   *     a {@code byte} value;
   * @param value2
   *     a {@code byte} value;
   * @return the maximum of {@code value1} and {@code value2}. In case of ties,
   *     returns the one with the largest position.
   */
  public static byte max(final byte value1, final byte value2) {
    return (value1 <= value2 ? value2 : value1);
  }

  /**
   * Returns the maximum of three {@code byte} values.
   *
   * @param value1
   *     a {@code byte} value;
   * @param value2
   *     a {@code byte} value;
   * @param value3
   *     a {@code byte} value;
   * @return the maximum of {@code value1}, {@code value2} and {@code value3}.
   *     In case of ties, returns the one with the largest position.
   */
  public static byte max(final byte value1, final byte value2,
      final byte value3) {
    if (value3 >= value2) {
      if (value3 >= value1) {
        return value3;
      } else {
        return value1;
      }
    } else if (value2 >= value1) {
      return value2;
    } else {
      return value1;
    }
  }

  /**
   * Returns the maximum value in a {@code byte} array.
   *
   * @param array
   *     a {@code byte} array, must not be {@code null} nor empty.
   * @return the maximum value in the array. In case of ties, returns the one
   *     with the largest position.
   * @throws NullArgumentException
   *     if {@code array} is {@code null} or empty.
   */
  public static byte max(final byte... array) {
    if ((array == null) || (array.length == 0)) {
      throw new NullArgumentException();
    }
    int i = array.length - 1;
    byte max = array[i];
    for (--i; i >= 0; --i) {
      final byte element = array[i];
      if (element > max) {
        max = element;
      }
    }
    return max;
  }

  /**
   * Returns the maximum of two {@code Byte} objects.
   *
   * <P>Assume that null is the maximum value of {@code Byte} objects.
   *
   * @param value1
   *     a {@code Byte} object, could be {@code null}.
   * @param value2
   *     a {@code Byte} object, could be {@code null}.
   * @return the maximum of {@code value1} and {@code value2}. In case of ties,
   *     returns the one with the largest position. Note that the returned value
   *     is either {@code null} or a reference to one of the arguments.
   */
  public static Byte max(final Byte value1, final Byte value2) {
    if (value1 == null) {
      return value2;
    } else if (value2 == null) {
      return value1;
    } else {
      final byte v1 = value1.byteValue();
      final byte v2 = value2.byteValue();
      return (v1 <= v2 ? value2 : value1);
    }
  }

  /**
   * Returns the maximum of three {@code Byte} objects.
   *
   * <P>Assume that null is the maximum value of {@code Byte} objects.
   *
   * @param value1
   *     a {@code Byte} object, could be {@code null}.
   * @param value2
   *     a {@code Byte} object, could be {@code null}.
   * @param value3
   *     a {@code Byte} object;
   * @return the maximum of {@code value1}, {@code value2} and {@code value3}.
   *     In case of ties, returns the one with the largest position. Note that
   *     the returned value is either {@code null} or a reference to one of the
   *     arguments.
   */
  public static Byte max(final Byte value1, final Byte value2,
      final Byte value3) {
    if (value1 == null) {
      return max(value2, value3);
    } else if (value2 == null) {
      return max(value1, value3);
    } else if (value3 == null) {
      return max(value1, value2);
    } else {
      final byte v1 = value1.byteValue();
      final byte v2 = value2.byteValue();
      final byte v3 = value3.byteValue();
      if (v3 >= v2) {
        if (v3 >= v1) {
          return value3;
        } else {
          return value1;
        }
      } else if (v2 >= v1) {
        return value2;
      } else {
        return value1;
      }
    }
  }

  /**
   * Returns the maximum object in a {@code Byte} array.
   *
   * <P>Assume that null is the maximum value of {@code Byte} objects.
   *
   * @param array
   *     a {@code Byte} array, must not be {@code null} nor empty.
   * @return the maximum object in the array. In case of ties, returns the one
   *     with the largest position. Note that the returned value is either
   *     {@code null} or a reference to one of elements in the {@code array}.
   * @throws NullArgumentException
   *     if {@code array} is {@code null} or empty.
   */
  public static Byte max(final Byte... array) {
    if ((array == null) || (array.length == 0)) {
      throw new NullArgumentException();
    }
    // find the last non-null element
    int i = array.length - 1;
    while ((i >= 0) && (array[i] == null)) {
      --i;
    }
    if (i < 0) {
      // all elements in array are null
      return null;
    }
    Byte max = array[i];
    byte maxValue = max.byteValue();
    // test the rest elements
    for (--i; i >= 0; --i) {
      final Byte element = array[i];
      if (element != null) {
        final byte value = element.byteValue();
        if (value > maxValue) {
          maxValue = value;
          max = element;
        }
      }
    }
    return max;
  }

  /**
   * Returns the maximum of two {@code short} values.
   *
   * @param value1
   *     a {@code short} value;
   * @param value2
   *     a {@code short} value;
   * @return the maximum of {@code value1} and {@code value2}. In case of ties,
   *     returns the one with the largest position.
   */
  public static short max(final short value1, final short value2) {
    return (value1 <= value2 ? value2 : value1);
  }

  /**
   * Returns the maximum of three {@code short} values.
   *
   * @param value1
   *     a {@code short} value;
   * @param value2
   *     a {@code short} value;
   * @param value3
   *     a {@code short} value;
   * @return the maximum of {@code value1}, {@code value2} and {@code value3}.
   *     In case of ties, returns the one with the largest position.
   */
  public static short max(final short value1, final short value2,
      final short value3) {
    if (value3 >= value2) {
      if (value3 >= value1) {
        return value3;
      } else {
        return value1;
      }
    } else if (value2 >= value1) {
      return value2;
    } else {
      return value1;
    }
  }

  /**
   * Returns the maximum value in a {@code short} array.
   *
   * @param array
   *     a {@code short} array, must not be {@code null} nor empty.
   * @return the maximum value in the array. In case of ties, returns the one
   *     with the largest position.
   * @throws NullArgumentException
   *     if {@code array} is {@code null} or empty.
   */
  public static short max(final short... array) {
    if ((array == null) || (array.length == 0)) {
      throw new NullArgumentException();
    }
    int i = array.length - 1;
    short max = array[i];
    for (--i; i >= 0; --i) {
      final short element = array[i];
      if (element > max) {
        max = element;
      }
    }
    return max;
  }

  /**
   * Returns the maximum of two {@code Short} objects.
   *
   * <P>Assume that null is the maximum value of {@code Short} objects.
   *
   * @param value1
   *     a {@code Short} object, could be {@code null}.
   * @param value2
   *     a {@code Short} object, could be {@code null}.
   * @return the maximum of {@code value1} and {@code value2}. In case of ties,
   *     returns the one with the largest position. Note that the returned value
   *     is either {@code null} or a reference to one of the arguments.
   */
  public static Short max(final Short value1, final Short value2) {
    if (value1 == null) {
      return value2;
    } else if (value2 == null) {
      return value1;
    } else {
      final short v1 = value1.shortValue();
      final short v2 = value2.shortValue();
      return (v1 <= v2 ? value2 : value1);
    }
  }

  /**
   * Returns the maximum of three {@code Short} objects.
   *
   * <P>Assume that null is the maximum value of {@code Short} objects.
   *
   * @param value1
   *     a {@code Short} object, could be {@code null}.
   * @param value2
   *     a {@code Short} object, could be {@code null}.
   * @param value3
   *     a {@code Short} object, could be {@code null}.
   * @return the maximum of {@code value1}, {@code value2} and {@code value3}.
   *     In case of ties, returns the one with the largest position. Note that
   *     the returned value is either {@code null} or a reference to one of the
   *     arguments.
   */
  public static Short max(final Short value1, final Short value2,
      final Short value3) {
    if (value1 == null) {
      return max(value2, value3);
    } else if (value2 == null) {
      return max(value1, value3);
    } else if (value3 == null) {
      return max(value1, value2);
    } else {
      final short v1 = value1.shortValue();
      final short v2 = value2.shortValue();
      final short v3 = value3.shortValue();
      if (v3 >= v2) {
        if (v3 >= v1) {
          return value3;
        } else {
          return value1;
        }
      } else if (v2 >= v1) {
        return value2;
      } else {
        return value1;
      }
    }
  }

  /**
   * Returns the maximum object in a {@code Short} array.
   *
   * <P>Assume that null is the maximum value of {@code Short} objects.
   *
   * @param array
   *     a {@code Short} array, must not be {@code null} nor empty.
   * @return the maximum object in the array. In case of ties, returns the one
   *     with the largest position. Note that the returned value is either
   *     {@code null} or a reference to one of elements in the {@code array}.
   * @throws NullArgumentException
   *     if {@code array} is {@code null} or empty.
   */
  public static Short max(final Short... array) {
    if ((array == null) || (array.length == 0)) {
      throw new NullArgumentException();
    }
    // find the last non-null element
    int i = array.length - 1;
    while ((i >= 0) && (array[i] == null)) {
      --i;
    }
    if (i < 0) {
      // all elements in array are null
      return null;
    }
    Short max = array[i];
    short maxValue = max.shortValue();
    // test the rest elements
    for (--i; i >= 0; --i) {
      final Short element = array[i];
      if (element != null) {
        final short value = element.shortValue();
        if (value > maxValue) {
          maxValue = value;
          max = element;
        }
      }
    }
    return max;
  }

  /**
   * Returns the maximum of two {@code int} values.
   *
   * @param value1
   *     a {@code int} value;
   * @param value2
   *     a {@code int} value;
   * @return the maximum of {@code value1} and {@code value2}. In case of ties,
   *     returns the one with the largest position.
   */
  public static int max(final int value1, final int value2) {
    return (value1 <= value2 ? value2 : value1);
  }

  /**
   * Returns the maximum of three {@code int} values.
   *
   * @param value1
   *     a {@code int} value;
   * @param value2
   *     a {@code int} value;
   * @param value3
   *     a {@code int} value;
   * @return the maximum of {@code value1}, {@code value2} and {@code value3}.
   *     In case of ties, returns the one with the largest position.
   */
  public static int max(final int value1, final int value2, final int value3) {
    if (value3 >= value2) {
      if (value3 >= value1) {
        return value3;
      } else {
        return value1;
      }
    } else if (value2 >= value1) {
      return value2;
    } else {
      return value1;
    }
  }

  /**
   * Returns the maximum value in a {@code int} array.
   *
   * @param array
   *     a {@code int} array, must not be {@code null} nor empty.
   * @return the maximum value in the array. In case of ties, returns the one
   *     with the largest position.
   * @throws NullArgumentException
   *     if {@code array} is {@code null} or empty.
   */
  public static int max(final int... array) {
    if ((array == null) || (array.length == 0)) {
      throw new NullArgumentException();
    }
    int i = array.length - 1;
    int max = array[i];
    for (--i; i >= 0; --i) {
      final int element = array[i];
      if (element > max) {
        max = element;
      }
    }
    return max;
  }

  /**
   * Returns the maximum of two {@code Integer} objects.
   *
   * <P>Assume that null is the maximum value of {@code Integer} objects.
   *
   * @param value1
   *     a {@code Integer} object, could be {@code null}.
   * @param value2
   *     a {@code Integer} object, could be {@code null}.
   * @return the maximum of {@code value1} and {@code value2}. In case of ties,
   *     returns the one with the largest position. Note that the returned value
   *     is either {@code null} or a reference to one of the arguments.
   */
  public static Integer max(final Integer value1, final Integer value2) {
    if (value1 == null) {
      return value2;
    } else if (value2 == null) {
      return value1;
    } else {
      final int v1 = value1.intValue();
      final int v2 = value2.intValue();
      return (v1 <= v2 ? value2 : value1);
    }
  }

  /**
   * Returns the maximum of three {@code Integer} objects.
   *
   * <P>Assume that null is the maximum value of {@code Integer} objects.
   *
   * @param value1
   *     a {@code Integer} object, could be {@code null}.
   * @param value2
   *     a {@code Integer} object, could be {@code null}.
   * @param value3
   *     a {@code Integer} object, could be {@code null}.
   * @return the maximum of {@code value1}, {@code value2} and {@code value3}.
   *     In case of ties, returns the one with the largest position. Note that
   *     the returned value is either {@code null} or a reference to one of the
   *     arguments.
   */
  public static Integer max(
      final Integer value1, final Integer value2, final Integer value3) {
    if (value1 == null) {
      return max(value2, value3);
    } else if (value2 == null) {
      return max(value1, value3);
    } else if (value3 == null) {
      return max(value1, value2);
    } else {
      final int v1 = value1.intValue();
      final int v2 = value2.intValue();
      final int v3 = value3.intValue();
      if (v3 >= v2) {
        if (v3 >= v1) {
          return value3;
        } else {
          return value1;
        }
      } else if (v2 >= v1) {
        return value2;
      } else {
        return value1;
      }
    }
  }

  /**
   * Returns the maximum object in a {@code Integer} array.
   *
   * <P>Assume that null is the maximum value of {@code Integer} objects.
   *
   * @param array
   *     a {@code Integer} array, must not be {@code null} nor empty.
   * @return the maximum object in the array. In case of ties, returns the one
   *     with the largest position. Note that the returned value is either
   *     {@code null} or a reference to one of elements in the {@code array}.
   * @throws NullArgumentException
   *     if {@code array} is {@code null} or empty.
   */
  public static Integer max(final Integer... array) {
    if ((array == null) || (array.length == 0)) {
      throw new NullArgumentException();
    }
    // find the last non-null element
    int i = array.length - 1;
    while ((i >= 0) && (array[i] == null)) {
      --i;
    }
    if (i < 0) {
      // all elements in array are null
      return null;
    }
    Integer max = array[i];
    int maxValue = max.intValue();
    // test the rest elements
    for (--i; i >= 0; --i) {
      final Integer element = array[i];
      if (element != null) {
        final int value = element.intValue();
        if (value > maxValue) {
          maxValue = value;
          max = element;
        }
      }
    }
    return max;
  }

  /**
   * Returns the maximum of two {@code long} values.
   *
   * @param value1
   *     a {@code long} value;
   * @param value2
   *     a {@code long} value;
   * @return the maximum of {@code value1} and {@code value2}. In case of ties,
   *     returns the one with the largest position.
   */
  public static long max(final long value1, final long value2) {
    return (value1 <= value2 ? value2 : value1);
  }

  /**
   * Returns the maximum of three {@code long} values.
   *
   * @param value1
   *     a {@code long} value;
   * @param value2
   *     a {@code long} value;
   * @param value3
   *     a {@code long} value;
   * @return the maximum of {@code value1}, {@code value2} and {@code value3}.
   *     In case of ties, returns the one with the largest position.
   */
  public static long max(final long value1, final long value2,
      final long value3) {
    if (value3 >= value2) {
      if (value3 >= value1) {
        return value3;
      } else {
        return value1;
      }
    } else if (value2 >= value1) {
      return value2;
    } else {
      return value1;
    }
  }

  /**
   * Returns the maximum value in a {@code long} array.
   *
   * @param array
   *     a {@code long} array, must not be {@code null} nor empty.
   * @return the maximum value in the array. In case of ties, returns the one
   *     with the largest position.
   * @throws NullArgumentException
   *     if {@code array} is {@code null} or empty.
   */
  public static long max(final long... array) {
    if ((array == null) || (array.length == 0)) {
      throw new NullArgumentException();
    }
    int i = array.length - 1;
    long max = array[i];
    for (--i; i >= 0; --i) {
      final long element = array[i];
      if (element > max) {
        max = element;
      }
    }
    return max;
  }

  /**
   * Returns the maximum of two {@code Long} objects.
   *
   * <P>Assume that null is the maximum value of {@code Long} objects.
   *
   * @param value1
   *     a {@code Long} object, could be {@code null}.
   * @param value2
   *     a {@code Long} object, could be {@code null}.
   * @return the maximum of {@code value1} and {@code value2}. In case of ties,
   *     returns the one with the largest position. Note that the returned value
   *     is either {@code null} or a reference to one of the arguments.
   */
  public static Long max(final Long value1, final Long value2) {
    if (value1 == null) {
      return value2;
    } else if (value2 == null) {
      return value1;
    } else {
      final long v1 = value1.longValue();
      final long v2 = value2.longValue();
      return (v1 <= v2 ? value2 : value1);
    }
  }

  /**
   * Returns the maximum of three {@code Long} objects.
   *
   * <P>Assume that null is the maximum value of {@code Long} objects.
   *
   * @param value1
   *     a {@code Long} object, could be {@code null}.
   * @param value2
   *     a {@code Long} object, could be {@code null}.
   * @param value3
   *     a {@code Long} object, could be {@code null}.
   * @return the maximum of {@code value1}, {@code value2} and {@code value3}.
   *     In case of ties, returns the one with the largest position. Note that
   *     the returned value is either {@code null} or a reference to one of the
   *     arguments.
   */
  public static Long max(final Long value1, final Long value2,
      final Long value3) {
    if (value1 == null) {
      return max(value2, value3);
    } else if (value2 == null) {
      return max(value1, value3);
    } else if (value3 == null) {
      return max(value1, value2);
    } else {
      final long v1 = value1.longValue();
      final long v2 = value2.longValue();
      final long v3 = value3.longValue();
      if (v3 >= v2) {
        if (v3 >= v1) {
          return value3;
        } else {
          return value1;
        }
      } else if (v2 >= v1) {
        return value2;
      } else {
        return value1;
      }
    }
  }

  /**
   * Returns the maximum object in a {@code Long} array.
   *
   * <P>Assume that null is the maximum value of {@code Long} objects.
   *
   * @param array
   *     a {@code Long} array, must not be {@code null} nor empty.
   * @return the maximum object in the array. In case of ties, returns the one
   *     with the largest position. Note that the returned value is either
   *     {@code null} or a reference to one of elements in the {@code array}.
   * @throws NullArgumentException
   *     if {@code array} is {@code null} or empty.
   */
  public static Long max(final Long... array) {
    if ((array == null) || (array.length == 0)) {
      throw new NullArgumentException();
    }
    // find the last non-null element
    int i = array.length - 1;
    while ((i >= 0) && (array[i] == null)) {
      --i;
    }
    if (i < 0) {
      // all elements in array are null
      return null;
    }
    Long max = array[i];
    long maxValue = max.longValue();
    // test the rest elements
    for (--i; i >= 0; --i) {
      final Long element = array[i];
      if (element != null) {
        final long value = element.longValue();
        if (value > maxValue) {
          maxValue = value;
          max = element;
        }
      }
    }
    return max;
  }

  /**
   * Returns the maximum of two {@code float} values.
   *
   * @param value1
   *     a {@code float} value;
   * @param value2
   *     a {@code float} value;
   * @return the maximum of {@code value1} and {@code value2}. In case of ties,
   *     returns the one with the largest position.
   */
  public static float max(final float value1, final float value2) {
    if (Comparison.compare(value1, value2) <= 0) {
      return value2;
    } else {
      return value1;
    }
  }

  /**
   * Returns the maximum of three {@code float} values.
   *
   * @param value1
   *     a {@code float} value;
   * @param value2
   *     a {@code float} value;
   * @param value3
   *     a {@code float} value;
   * @return the maximum of {@code value1}, {@code value2} and {@code value3}.
   *     In case of ties, returns the one with the largest position.
   */
  public static float max(final float value1, final float value2,
      final float value3) {
    if (Comparison.compare(value3, value2) >= 0) {
      if (Comparison.compare(value3, value1) >= 0) {
        return value3;
      } else {
        return value1;
      }
    } else if (Comparison.compare(value2, value1) >= 0) {
      return value2;
    } else {
      return value1;
    }
  }

  /**
   * Returns the maximum value in a {@code float} array.
   *
   * @param array
   *     a {@code float} array, must not be {@code null} nor empty.
   * @return the maximum value in the array. In case of ties, returns the one
   *     with the largest position.
   * @throws NullArgumentException
   *     if {@code array} is {@code null} or empty.
   */
  public static float max(final float... array) {
    if ((array == null) || (array.length == 0)) {
      throw new NullArgumentException();
    }
    int i = array.length - 1;
    float max = array[i];
    for (--i; i >= 0; --i) {
      final float element = array[i];
      if (Comparison.compare(element, max) > 0) {
        max = element;
      }
    }
    return max;
  }

  /**
   * Returns the maximum of two {@code Float} objects.
   *
   * <P>Assume that null is the maximum value of {@code Float} objects.
   *
   * @param value1
   *     a {@code Float} object, could be {@code null}.
   * @param value2
   *     a {@code Float} object, could be {@code null}.
   * @return the maximum of {@code value1} and {@code value2}. In case of ties,
   *     returns the one with the largest position. Note that the returned value
   *     is either {@code null} or a reference to one of the arguments.
   */
  public static Float max(final Float value1, final Float value2) {
    if (value1 == null) {
      return value2;
    } else if (value2 == null) {
      return value1;
    } else {
      final float v1 = value1.floatValue();
      final float v2 = value2.floatValue();
      return (Comparison.compare(v1, v2) <= 0 ? value2 : value1);
    }
  }

  /**
   * Returns the maximum of three {@code Float} objects.
   *
   * <P>Assume that null is the maximum value of {@code Float} objects.
   *
   * @param value1
   *     a {@code Float} object, could be {@code null}.
   * @param value2
   *     a {@code Float} object, could be {@code null}.
   * @param value3
   *     a {@code Float} object, could be {@code null}.
   * @return the maximum of {@code value1}, {@code value2} and {@code value3}.
   *     In case of ties, returns the one with the largest position. Note that
   *     the returned value is either {@code null} or a reference to one of the
   *     arguments.
   */
  public static Float max(final Float value1, final Float value2,
      final Float value3) {
    if (value1 == null) {
      return max(value2, value3);
    } else if (value2 == null) {
      return max(value1, value3);
    } else if (value3 == null) {
      return max(value1, value2);
    } else {
      final float v1 = value1.floatValue();
      final float v2 = value2.floatValue();
      final float v3 = value3.floatValue();
      if (Comparison.compare(v3, v2) >= 0) {
        if (Comparison.compare(v3, v1) >= 0) {
          return value3;
        } else {
          return value1;
        }
      } else if (Comparison.compare(v2, v1) >= 0) {
        return value2;
      } else {
        return value1;
      }
    }
  }

  /**
   * Returns the maximum object in a {@code Float} array.
   *
   * <P>Assume that null is the maximum value of {@code Float} objects.
   *
   * @param array
   *     a {@code Float} array, must not be {@code null} nor empty.
   * @return the maximum object in the array. In case of ties, returns the one
   *     with the largest position. Note that the returned value is either
   *     {@code null} or a reference to one of elements in the {@code array}.
   * @throws NullArgumentException
   *     if {@code array} is {@code null} or empty.
   */
  public static Float max(final Float... array) {
    if ((array == null) || (array.length == 0)) {
      throw new NullArgumentException();
    }
    // find the last non-null element
    int i = array.length - 1;
    while ((i >= 0) && (array[i] == null)) {
      --i;
    }
    if (i < 0) {
      // all elements in array are null
      return null;
    }
    Float max = array[i];
    float maxValue = max.floatValue();
    // test the rest elements
    for (--i; i >= 0; --i) {
      final Float element = array[i];
      if (element != null) {
        final float value = element.floatValue();
        if (Comparison.compare(value, maxValue) > 0) {
          maxValue = value;
          max = element;
        }
      }
    }
    return max;
  }

  /**
   * Returns the maximum of two {@code double} values.
   *
   * @param value1
   *     a {@code double} value;
   * @param value2
   *     a {@code double} value;
   * @return the maximum of {@code value1} and {@code value2}. In case of ties,
   *     returns the one with the largest position.
   */
  public static double max(final double value1, final double value2) {
    if (Comparison.compare(value1, value2) <= 0) {
      return value2;
    } else {
      return value1;
    }
  }

  /**
   * Returns the maximum of three {@code double} values.
   *
   * @param value1
   *     a {@code double} value;
   * @param value2
   *     a {@code double} value;
   * @param value3
   *     a {@code double} value;
   * @return the maximum of {@code value1}, {@code value2} and {@code value3}.
   *     In case of ties, returns the one with the largest position.
   */
  public static double max(
      final double value1, final double value2, final double value3) {
    if (Comparison.compare(value3, value2) >= 0) {
      if (Comparison.compare(value3, value1) >= 0) {
        return value3;
      } else {
        return value1;
      }
    } else if (Comparison.compare(value2, value1) >= 0) {
      return value2;
    } else {
      return value1;
    }
  }

  /**
   * Returns the maximum value in a {@code double} array.
   *
   * @param array
   *     a {@code double} array, must not be {@code null} nor empty.
   * @return the maximum value in the array. In case of ties, returns the one
   *     with the largest position.
   * @throws NullArgumentException
   *     if {@code array} is {@code null} or empty.
   */
  public static double max(final double... array) {
    if ((array == null) || (array.length == 0)) {
      throw new NullArgumentException();
    }
    int i = array.length - 1;
    double max = array[i];
    for (--i; i >= 0; --i) {
      final double element = array[i];
      if (Comparison.compare(element, max) > 0) {
        max = element;
      }
    }
    return max;
  }

  /**
   * Returns the maximum of two {@code Double} objects.
   *
   * <P>Assume that null is the maximum value of {@code Double} objects.
   *
   * @param value1
   *     a {@code Double} object, could be {@code null}.
   * @param value2
   *     a {@code Double} object, could be {@code null}.
   * @return the maximum of {@code value1} and {@code value2}. In case of ties,
   *     returns the one with the largest position. Note that the returned value
   *     is either {@code null} or a reference to one of the arguments.
   */
  public static Double max(final Double value1, final Double value2) {
    if (value1 == null) {
      return value2;
    } else if (value2 == null) {
      return value1;
    } else {
      final double v1 = value1.doubleValue();
      final double v2 = value2.doubleValue();
      return (Comparison.compare(v1, v2) <= 0 ? value2 : value1);
    }
  }

  /**
   * Returns the maximum of three {@code Double} objects.
   *
   * <P>Assume that null is the maximum value of {@code Double} objects.
   *
   * @param value1
   *     a {@code Double} object, could be {@code null}.
   * @param value2
   *     a {@code Double} object, could be {@code null}.
   * @param value3
   *     a {@code Double} object, could be {@code null}.
   * @return the maximum of {@code value1}, {@code value2} and {@code value3}.
   *     In case of ties, returns the one with the largest position. Note that
   *     the returned value is either {@code null} or a reference to one of the
   *     arguments.
   */
  public static Double max(
      final Double value1, final Double value2, final Double value3) {
    if (value1 == null) {
      return max(value2, value3);
    } else if (value2 == null) {
      return max(value1, value3);
    } else if (value3 == null) {
      return max(value1, value2);
    } else {
      final double v1 = value1.doubleValue();
      final double v2 = value2.doubleValue();
      final double v3 = value3.doubleValue();
      if (Comparison.compare(v3, v2) >= 0) {
        if (Comparison.compare(v3, v1) >= 0) {
          return value3;
        } else {
          return value1;
        }
      } else if (Comparison.compare(v2, v1) >= 0) {
        return value2;
      } else {
        return value1;
      }
    }
  }

  /**
   * Returns the maximum object in a {@code Double} array.
   *
   * <P>Assume that null is the maximum value of {@code Double} objects.
   *
   * @param array
   *     a {@code Double} array, must not be {@code null} nor empty.
   * @return the maximum object in the array. In case of ties, returns the one
   *     with the largest position. Note that the returned value is either
   *     {@code null} or a reference to one of elements in the {@code array}.
   * @throws NullArgumentException
   *     if {@code array} is {@code null} or empty.
   */
  public static Double max(final Double... array) {
    if ((array == null) || (array.length == 0)) {
      throw new NullArgumentException();
    }
    // find the last non-null element
    int i = array.length - 1;
    while ((i >= 0) && (array[i] == null)) {
      --i;
    }
    if (i < 0) {
      // all elements in array are null
      return null;
    }
    Double max = array[i];
    double maxValue = max.doubleValue();
    // test the rest elements
    for (--i; i >= 0; --i) {
      final Double element = array[i];
      if (element != null) {
        final double value = element.doubleValue();
        if (Comparison.compare(value, maxValue) > 0) {
          maxValue = value;
          max = element;
        }
      }
    }
    return max;
  }

  /**
   * Returns the maximum of two {@code String} objects.
   *
   * <P>Assume that {@code null} is the maximum value of {@code String} objects.
   *
   * @param value1
   *     a {@code String} object, could be {@code null}.
   * @param value2
   *     a {@code String} object, could be {@code null}.
   * @return the maximum of {@code value1} and {@code value2}. In case of ties,
   *     returns the one with the largest position.
   */
  public static String max(final String value1, final String value2) {
    if (value1 == null) {
      return value2;
    } else if (value2 == null) {
      return value1;
    } else if (value1.compareTo(value2) <= 0) {
      return value2;
    } else {
      return value1;
    }
  }

  /**
   * Returns the maximum of three {@code String} objects.
   *
   * <P>Assume that {@code null} is the maximum value of {@code String} objects.
   *
   * @param value1
   *     a {@code String} object, could be {@code null}.
   * @param value2
   *     a {@code String} object, could be {@code null}.
   * @param value3
   *     a {@code String} object, could be {@code null}.
   * @return the maximum of {@code value1}, {@code value2} and {@code value3}.
   *     In case of ties, returns the one with the largest position.
   */
  public static String max(
      final String value1, final String value2, final String value3) {
    if (value1 == null) {
      return max(value2, value3);
    } else if (value2 == null) {
      return max(value1, value3);
    } else if (value3 == null) {
      return max(value1, value2);
    } else {
      if (value3.compareTo(value2) >= 0) {
        if (value3.compareTo(value1) >= 0) {
          return value3;
        } else {
          return value1;
        }
      } else if (value2.compareTo(value1) >= 0) {
        return value2;
      } else {
        return value1;
      }
    }
  }

  /**
   * Returns the maximum object in a {@code String} array.
   *
   * <P>Assume that null is the maximum value of {@code String} objects.
   *
   * @param array
   *     a {@code String} array, must not be {@code null} nor empty.
   * @return the maximum object in the array. In case of ties, returns the one
   *     with the largest position. Note that the returned value is either
   *     {@code null} or a reference to one of elements in the {@code array}.
   * @throws NullArgumentException
   *     if {@code array} is {@code null} or empty.
   */
  public static String max(final String... array) {
    if ((array == null) || (array.length == 0)) {
      throw new NullArgumentException();
    }
    // find the last non-null element
    int i = array.length - 1;
    while ((i >= 0) && (array[i] == null)) {
      --i;
    }
    if (i < 0) {
      // all elements in array are null
      return null;
    }
    String max = array[i];
    // test the rest elements
    for (--i; i >= 0; --i) {
      final String element = array[i];
      if (element != null) {
        if (element.compareTo(max) > 0) {
          max = element;
        }
      }
    }
    return max;
  }

  /**
   * Returns the maximum of two {@code Comparable} objects.
   *
   * <P>Assume that {@code null} is the maximum value.
   *
   * @param <T>
   *     any object type. Note that it could be type of arrays or
   *     multi-dimensional arrays.
   * @param value1
   *     an object of class {@code T}, could be {@code null}.
   * @param value2
   *     an object of class {@code T}, could be {@code null}.
   * @return the maximum of {@code value1} and {@code value2}. In case of ties,
   *     returns the one with the largest position.
   */
  public static <T> T max(final T value1, final T value2) {
    if (Comparison.compare(value1, value2) <= 0) {
      return value2;
    } else {
      return value1;
    }
  }

  /**
   * Returns the maximum of three {@code Comparable} objects.
   *
   * <P>Assume that {@code null} is the maximum value.
   *
   * @param <T>
   *     any object type. Note that it could be type of arrays or
   *     multi-dimensional arrays.
   * @param value1
   *     a object of class {@code T}, could be {@code null}.
   * @param value2
   *     a object of class {@code T}, could be {@code null}.
   * @param value3
   *     a object of class {@code T}, could be {@code null}.
   * @return the maximum of {@code value1}, {@code value2} and {@code value3}.
   *     In case of ties, returns the one with the largest position.
   */
  public static <T> T max(final T value1, final T value2, final T value3) {
    if (Comparison.compare(value3, value2) >= 0) {
      if (Comparison.compare(value3, value1) >= 0) {
        return value3;
      } else {
        return value1;
      }
    } else if (Comparison.compare(value2, value1) >= 0) {
      return value2;
    } else {
      return value1;
    }
  }

  /**
   * Returns the maximum object in a {@code Comparable} array.
   *
   * <P>Assume that null is the maximum value .
   *
   * @param <T>
   *     any object type. Note that it could be type of arrays or
   *     multi-dimensional arrays.
   * @param array
   *     a array of class {@code T}, must not be {@code null} nor empty.
   * @return the maximum object in the array. In case of ties, returns the one
   *     with the largest position. Note that the returned value is either
   *     {@code null} or a reference to one of elements in the {@code array}.
   * @throws NullArgumentException
   *     if {@code array} is {@code null} or empty.
   */
  @SafeVarargs
  public static <T> T max(final T... array) {
    if ((array == null) || (array.length == 0)) {
      throw new NullArgumentException();
    }
    // find the last non-null element
    int i = array.length - 1;
    while ((i >= 0) && (array[i] == null)) {
      --i;
    }
    if (i < 0) {
      // all elements in array are null
      return null;
    }
    T max = array[i];
    // test the rest elements
    for (--i; i >= 0; --i) {
      final T element = array[i];
      if (element != null) {
        if (Comparison.compare(element, max) > 0) {
          max = element;
        }
      }
    }
    return max;
  }

  /**
   * Returns the maximum object in a {@code Comparable} {@code Iterable}
   * object.
   *
   * <P>Assume that null is the maximum value.
   *
   * @param <T>
   *     any object type. Note that it could be type of arrays or
   *     multi-dimensional arrays.
   * @param iterable
   *     an {@code Iterable} of values of class {@code T}, must not be {@code
   *     null} nor empty.
   * @return the maximum object in the list.In case of ties, returns the one
   *     with the largest position. Note that the returned value is either
   *     {@code null} or a reference to one of elements in the {@code
   *     iterable}.
   * @throws NullArgumentException
   *     if {@code iterable} is {@code null} or empty.
   */
  public static <T> T max(final Iterable<T> iterable) {
    if (iterable == null) {
      throw new NullArgumentException();
    }
    final Iterator<T> iter = iterable.iterator();
    if (!iter.hasNext()) {
      throw new NullArgumentException();
    }
    // find the first non-null element
    T max = iter.next();
    while ((max == null) && iter.hasNext()) {
      max = iter.next();
    }
    if (max == null) {
      // all elements in the iterable are null
      return null;
    }
    while (iter.hasNext()) {
      final T element = iter.next();
      if (element != null) {
        if (Comparison.compare(element, max) > 0) {
          max = element;
        }
      }
    }
    return max;
  }
}