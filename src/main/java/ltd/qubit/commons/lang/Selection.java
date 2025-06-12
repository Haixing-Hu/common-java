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
 * 此类提供选择相关的算法。
 *
 * <p>TODO: 1. 添加选择中位数的函数；2. 完善单元测试。
 *
 * @author 胡海星
 */
@ThreadSafe
public final class Selection {

  private Selection() {
  }

  /**
   * 返回两个{@code boolean}值中的最小值。
   *
   * <p>对于{@code boolean}值，假设{@code true > false}。
   *
   * @param value1
   *     一个{@code boolean}值；
   * @param value2
   *     一个{@code boolean}值；
   * @return {@code value1}和{@code value2}的最小值。在相等的情况下，
   *     返回位置最小的那个。
   */
  public static boolean min(final boolean value1, final boolean value2) {
    if (!value1) {
      return false;
    } else {
      return value2;
    }
  }

  /**
   * 返回三个{@code boolean}值中的最小值。
   *
   * <p>对于{@code boolean}值，假设{@code true &gt; false}。
   *
   * @param value1
   *     一个{@code boolean}值；
   * @param value2
   *     一个{@code boolean}值；
   * @param value3
   *     一个{@code boolean}值；
   * @return {@code value1}、{@code value2}和{@code value3}的最小值。
   *     在相等的情况下，返回位置最小的那个。
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
   * 返回{@code boolean}数组中的最小值。
   *
   * <p>对于{@code boolean}值，假设{@code true &gt; false}。
   *
   * @param array
   *     一个{@code boolean}数组，不能为{@code null}或空。
   * @return 数组中的最小值。在相等的情况下，返回位置最小的那个。
   * @throws NullArgumentException
   *     如果{@code array}为{@code null}或空。
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
   * 返回两个{@code Boolean}对象中的最小值。
   *
   * <p>对于{@code Boolean}对象，假设{@code Boolean.TRUE &gt; Boolean.FALSE &gt; null}。
   *
   * @param value1
   *     一个{@code Boolean}对象，可以为{@code null}。
   * @param value2
   *     一个{@code Boolean}对象，可以为{@code null}。
   * @return {@code value1}和{@code value2}的最小值。在相等的情况下，
   *     返回位置最小的那个。注意返回值要么是{@code null}，要么是参数之一的引用。
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
        // 由于v1 != v2，因此v1 == true && v2 == false
        return value2;
      } else {
        // 由于v1 != v2，因此v1 == false && v2 == true
        return value1;
      }
    }
  }

  /**
   * 返回三个{@code Boolean}对象中的最小值。
   *
   * <p>对于{@code Boolean}对象，假设{@code Boolean.TRUE &gt; Boolean.FALSE &gt; null}。
   *
   * @param value1
   *     一个{@code Boolean}对象，可以为{@code null}。
   * @param value2
   *     一个{@code Boolean}对象，可以为{@code null}。
   * @param value3
   *     一个{@code Boolean}对象，可以为{@code null}。
   * @return {@code value1}、{@code value2}和{@code value3}的最小值。
   *     在相等的情况下，返回位置最小的那个。注意返回值要么是{@code null}，要么是参数之一的引用。
   */
  public static Boolean min(
      final Boolean value1, final Boolean value2, final Boolean value3) {
    if ((value1 == null) || (value2 == null) || (value3 == null)) {
      return null;
    } else {
      if (!value1.booleanValue()) {
        return value1;
      }
      // 现在value1 == true
      if (!value2.booleanValue()) {
        return value2;
      }
      // 现在value1 == value2 == true
      if (!value3.booleanValue()) {
        return value3;
      }
      // 现在value1 == value2 == value3 == true
      return value1;
    }
  }

  /**
   * 返回{@code Boolean}数组中的最小值。
   *
   * <p>对于{@code Boolean}对象，假设{@code Boolean.TRUE &gt; Boolean.FALSE &gt; null}。
   *
   * @param array
   *     一个{@code Boolean}数组，不能为{@code null}或空。
   * @return 数组中的最小对象。在相等的情况下，返回位置最小的那个。注意返回值要么是
   *     {@code null}，要么是{@code array}中某个元素的引用。
   * @throws NullArgumentException
   *     如果{@code array}为{@code null}或空。
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
   * 返回两个{@code char}值中的最小值。
   *
   * @param value1
   *     一个{@code char}值；
   * @param value2
   *     一个{@code char}值；
   * @return {@code value1}和{@code value2}的最小值。在相等的情况下，
   *     返回位置最小的那个。
   */
  public static char min(final char value1, final char value2) {
    return (value1 <= value2 ? value1 : value2);
  }

  /**
   * 返回三个{@code char}值中的最小值。
   *
   * @param value1
   *     一个{@code char}值；
   * @param value2
   *     一个{@code char}值；
   * @param value3
   *     一个{@code char}值；
   * @return {@code value1}、{@code value2}和{@code value3}的最小值。
   *     在相等的情况下，返回位置最小的那个。
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
   * 返回{@code char}数组中的最小值。
   *
   * @param array
   *     一个{@code char}数组，不能为{@code null}或空。
   * @return 数组中的最小值。在相等的情况下，返回位置最小的那个。
   * @throws NullArgumentException
   *     如果{@code array}为{@code null}或空。
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
   * 返回两个{@code Character}对象中的最小值。
   *
   * <p>对于{@code Character}对象，假设null是最小值。
   *
   * @param value1
   *     一个{@code Character}对象，可以为{@code null}。
   * @param value2
   *     一个{@code Character}对象，可以为{@code null}。
   * @return {@code value1}和{@code value2}的最小值。在相等的情况下，
   *     返回位置最小的那个。注意返回值要么是{@code null}，要么是参数之一的引用。
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
   * 返回三个{@code Character}对象中的最小值。
   *
   * <p>对于{@code Character}对象，假设null是最小值。
   *
   * @param value1
   *     一个{@code Character}对象，可以为{@code null}。
   * @param value2
   *     一个{@code Character}对象，可以为{@code null}。
   * @param value3
   *     一个{@code Character}对象，可以为{@code null}。
   * @return {@code value1}、{@code value2}和{@code value3}的最小值。
   *     在相等的情况下，返回位置最小的那个。注意返回值要么是{@code null}，要么是参数之一的引用。
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
   * 返回{@code Character}数组中的最小值。
   *
   * <p>对于{@code Character}对象，假设null是最小值。
   *
   * @param array
   *     一个{@code Character}数组，不能为{@code null}或空。
   * @return 数组中的最小对象。在相等的情况下，返回位置最小的那个。注意返回值要么是
   *     {@code null}，要么是{@code array}中某个元素的引用。
   * @throws NullArgumentException
   *     如果{@code array}为{@code null}或空。
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
   * 返回两个{@code byte}值中的最小值。
   *
   * @param value1
   *     一个{@code byte}值；
   * @param value2
   *     一个{@code byte}值；
   * @return {@code value1}和{@code value2}的最小值。在相等的情况下，
   *     返回位置最小的那个。
   */
  public static byte min(final byte value1, final byte value2) {
    return (value1 <= value2 ? value1 : value2);
  }

  /**
   * 返回三个{@code byte}值中的最小值。
   *
   * @param value1
   *     一个{@code byte}值；
   * @param value2
   *     一个{@code byte}值；
   * @param value3
   *     一个{@code byte}值；
   * @return {@code value1}、{@code value2}和{@code value3}的最小值。
   *     在相等的情况下，返回位置最小的那个。
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
   * 返回{@code byte}数组中的最小值。
   *
   * @param array
   *     一个{@code byte}数组，不能为{@code null}或空。
   * @return 数组中的最小值。在相等的情况下，返回位置最小的那个。
   * @throws NullArgumentException
   *     如果{@code array}为{@code null}或空。
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
   * 返回两个{@code Byte}对象中的最小值。
   *
   * <p>对于{@code Byte}对象，假设null是最小值。
   *
   * @param value1
   *     一个{@code Byte}对象，可以为{@code null}。
   * @param value2
   *     一个{@code Byte}对象，可以为{@code null}。
   * @return {@code value1}和{@code value2}的最小值。在相等的情况下，
   *     返回位置最小的那个。注意返回值要么是{@code null}，要么是参数之一的引用。
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
   * 返回三个{@code Byte}对象中的最小值。
   *
   * <p>对于{@code Byte}对象，假设null是最小值。
   *
   * @param value1
   *     一个{@code Byte}对象，可以为{@code null}。
   * @param value2
   *     一个{@code Byte}对象，可以为{@code null}。
   * @param value3
   *     一个{@code Byte}对象，可以为{@code null}。
   * @return {@code value1}、{@code value2}和{@code value3}的最小值。
   *     在相等的情况下，返回位置最小的那个。注意返回值要么是{@code null}，要么是参数之一的引用。
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
   * 返回{@code Byte}数组中的最小值。
   *
   * <p>对于{@code Byte}对象，假设null是最小值。
   *
   * @param array
   *     一个{@code Byte}数组，不能为{@code null}或空。
   * @return 数组中的最小对象。在相等的情况下，返回位置最小的那个。注意返回值要么是
   *     {@code null}，要么是{@code array}中某个元素的引用。
   * @throws NullArgumentException
   *     如果{@code array}为{@code null}或空。
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
   * 返回两个{@code short}值中的最小值。
   *
   * @param value1
   *     一个{@code short}值；
   * @param value2
   *     一个{@code short}值；
   * @return {@code value1}和{@code value2}的最小值。在相等的情况下，
   *     返回位置最小的那个。
   */
  public static short min(final short value1, final short value2) {
    return (value1 <= value2 ? value1 : value2);
  }

  /**
   * 返回三个{@code short}值中的最小值。
   *
   * @param value1
   *     一个{@code short}值；
   * @param value2
   *     一个{@code short}值；
   * @param value3
   *     一个{@code short}值；
   * @return {@code value1}、{@code value2}和{@code value3}的最小值。
   *     在相等的情况下，返回位置最小的那个。
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
   * 返回{@code short}数组中的最小值。
   *
   * @param array
   *     一个{@code short}数组，不能为{@code null}或空。
   * @return 数组中的最小值。在相等的情况下，返回位置最小的那个。
   * @throws NullArgumentException
   *     如果{@code array}为{@code null}或空。
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
   * 返回两个{@code Short}对象中的最小值。
   *
   * <p>对于{@code Short}对象，假设null是最小值。
   *
   * @param value1
   *     一个{@code Short}对象，可以为{@code null}。
   * @param value2
   *     一个{@code Short}对象，可以为{@code null}。
   * @return {@code value1}和{@code value2}的最小值。在相等的情况下，
   *     返回位置最小的那个。注意返回值要么是{@code null}，要么是参数之一的引用。
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
   * 返回三个{@code Short}对象中的最小值。
   *
   * <p>对于{@code Short}对象，假设null是最小值。
   *
   * @param value1
   *     一个{@code Short}对象，可以为{@code null}。
   * @param value2
   *     一个{@code Short}对象，可以为{@code null}。
   * @param value3
   *     一个{@code Short}对象，可以为{@code null}。
   * @return {@code value1}、{@code value2}和{@code value3}的最小值。
   *     在相等的情况下，返回位置最小的那个。注意返回值要么是{@code null}，要么是参数之一的引用。
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
   * 返回{@code Short}数组中的最小值。
   *
   * <p>对于{@code Short}对象，假设null是最小值。
   *
   * @param array
   *     一个{@code Short}数组，不能为{@code null}或空。
   * @return 数组中的最小对象。在相等的情况下，返回位置最小的那个。注意返回值要么是
   *     {@code null}，要么是{@code array}中某个元素的引用。
   * @throws NullArgumentException
   *     如果{@code array}为{@code null}或空。
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
   * 返回两个{@code int}值中的最小值。
   *
   * @param value1
   *     一个{@code int}值；
   * @param value2
   *     一个{@code int}值；
   * @return {@code value1}和{@code value2}的最小值。在相等的情况下，
   *     返回位置最小的那个。
   */
  public static int min(final int value1, final int value2) {
    return (value1 <= value2 ? value1 : value2);
  }

  /**
   * 返回三个{@code int}值中的最小值。
   *
   * @param value1
   *     一个{@code int}值；
   * @param value2
   *     一个{@code int}值；
   * @param value3
   *     一个{@code int}值；
   * @return {@code value1}、{@code value2}和{@code value3}的最小值。
   *     在相等的情况下，返回位置最小的那个。
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
   * 返回{@code int}数组中的最小值。
   *
   * @param array
   *     一个{@code int}数组，不能为{@code null}或空。
   * @return 数组中的最小值。在相等的情况下，返回位置最小的那个。
   * @throws NullArgumentException
   *     如果{@code array}为{@code null}或空。
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
   * 返回两个{@code Integer}对象中的最小值。
   *
   * <p>对于{@code Integer}对象，假设null是最小值。
   *
   * @param value1
   *     一个{@code Integer}对象，可以为{@code null}。
   * @param value2
   *     一个{@code Integer}对象，可以为{@code null}。
   * @return {@code value1}和{@code value2}的最小值。在相等的情况下，
   *     返回位置最小的那个。注意返回值要么是{@code null}，要么是参数之一的引用。
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
   * 返回三个{@code Integer}对象中的最小值。
   *
   * <p>对于{@code Integer}对象，假设null是最小值。
   *
   * @param value1
   *     一个{@code Integer}对象，可以为{@code null}。
   * @param value2
   *     一个{@code Integer}对象，可以为{@code null}。
   * @param value3
   *     一个{@code Integer}对象，可以为{@code null}。
   * @return {@code value1}、{@code value2}和{@code value3}的最小值。
   *     在相等的情况下，返回位置最小的那个。注意返回值要么是{@code null}，要么是参数之一的引用。
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
   * 返回{@code Integer}数组中的最小值。
   *
   * <p>对于{@code Integer}对象，假设null是最小值。
   *
   * @param array
   *     一个{@code Integer}数组，不能为{@code null}或空。
   * @return 数组中的最小对象。在相等的情况下，返回位置最小的那个。注意返回值要么是
   *     {@code null}，要么是{@code array}中某个元素的引用。
   * @throws NullArgumentException
   *     如果{@code array}为{@code null}或空。
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
   * 返回两个{@code long}值中的最小值。
   *
   * @param value1
   *     一个{@code long}值；
   * @param value2
   *     一个{@code long}值；
   * @return {@code value1}和{@code value2}的最小值。在相等的情况下，
   *     返回位置最小的那个。
   */
  public static long min(final long value1, final long value2) {
    return (value1 <= value2 ? value1 : value2);
  }

  /**
   * 返回三个{@code long}值中的最小值。
   *
   * @param value1
   *     一个{@code long}值；
   * @param value2
   *     一个{@code long}值；
   * @param value3
   *     一个{@code long}值；
   * @return {@code value1}、{@code value2}和{@code value3}的最小值。
   *     在相等的情况下，返回位置最小的那个。
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
   * 返回{@code long}数组中的最小值。
   *
   * @param array
   *     一个{@code long}数组，不能为{@code null}或空。
   * @return 数组中的最小值。在相等的情况下，返回位置最小的那个。
   * @throws NullArgumentException
   *     如果{@code array}为{@code null}或空。
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
   * 返回两个{@code Long}对象中的最小值。
   *
   * <p>对于{@code Long}对象，假设null是最小值。
   *
   * @param value1
   *     一个{@code Long}对象，可以为{@code null}。
   * @param value2
   *     一个{@code Long}对象，可以为{@code null}。
   * @return {@code value1}和{@code value2}的最小值。在相等的情况下，
   *     返回位置最小的那个。注意返回值要么是{@code null}，要么是参数之一的引用。
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
   * 返回三个{@code Long}对象中的最小值。
   *
   * <p>对于{@code Long}对象，假设null是最小值。
   *
   * @param value1
   *     一个{@code Long}对象，可以为{@code null}。
   * @param value2
   *     一个{@code Long}对象，可以为{@code null}。
   * @param value3
   *     一个{@code Long}对象，可以为{@code null}。
   * @return {@code value1}、{@code value2}和{@code value3}的最小值。
   *     在相等的情况下，返回位置最小的那个。注意返回值要么是{@code null}，要么是参数之一的引用。
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
   * 返回{@code Long}数组中的最小值。
   *
   * <p>对于{@code Long}对象，假设null是最小值。
   *
   * @param array
   *     一个{@code Long}数组，不能为{@code null}或空。
   * @return 数组中的最小对象。在相等的情况下，返回位置最小的那个。注意返回值要么是
   *     {@code null}，要么是{@code array}中某个元素的引用。
   * @throws NullArgumentException
   *     如果{@code array}为{@code null}或空。
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
   * 返回两个{@code float}值中的最小值。
   *
   * @param value1
   *     一个{@code float}值；
   * @param value2
   *     一个{@code float}值；
   * @return {@code value1}和{@code value2}的最小值。在相等的情况下，
   *     返回位置最小的那个。
   */
  public static float min(final float value1, final float value2) {
    if (Comparison.compare(value1, value2) <= 0) {
      return value1;
    } else {
      return value2;
    }
  }

  /**
   * 返回三个{@code float}值中的最小值。
   *
   * @param value1
   *     一个{@code float}值；
   * @param value2
   *     一个{@code float}值；
   * @param value3
   *     一个{@code float}值；
   * @return {@code value1}、{@code value2}和{@code value3}的最小值。
   *     在相等的情况下，返回位置最小的那个。
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
   * 返回{@code float}数组中的最小值。
   *
   * @param array
   *     一个{@code float}数组，不能为{@code null}或空。
   * @return 数组中的最小值。在相等的情况下，返回位置最小的那个。
   * @throws NullArgumentException
   *     如果{@code array}为{@code null}或空。
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
   * 返回两个{@code Float}对象中的最小值。
   *
   * <p>对于{@code Float}对象，假设null是最小值。
   *
   * @param value1
   *     一个{@code Float}对象，可以为{@code null}。
   * @param value2
   *     一个{@code Float}对象，可以为{@code null}。
   * @return {@code value1}和{@code value2}的最小值。在相等的情况下，
   *     返回位置最小的那个。注意返回值要么是{@code null}，要么是参数之一的引用。
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
   * 返回三个{@code Float}对象中的最小值。
   *
   * <p>对于{@code Float}对象，假设null是最小值。
   *
   * @param value1
   *     一个{@code Float}对象，可以为{@code null}。
   * @param value2
   *     一个{@code Float}对象，可以为{@code null}。
   * @param value3
   *     一个{@code Float}对象，可以为{@code null}。
   * @return {@code value1}、{@code value2}和{@code value3}的最小值。
   *     在相等的情况下，返回位置最小的那个。注意返回值要么是{@code null}，要么是参数之一的引用。
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
   * 返回{@code Float}数组中的最小值。
   *
   * <p>对于{@code Float}对象，假设null是最小值。
   *
   * @param array
   *     一个{@code Float}数组，不能为{@code null}或空。
   * @return 数组中的最小对象。在相等的情况下，返回位置最小的那个。注意返回值要么是
   *     {@code null}，要么是{@code array}中某个元素的引用。
   * @throws NullArgumentException
   *     如果{@code array}为{@code null}或空。
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
   * 返回两个{@code double}值中的最小值。
   *
   * @param value1
   *     一个{@code double}值；
   * @param value2
   *     一个{@code double}值；
   * @return {@code value1}和{@code value2}的最小值。在相等的情况下，
   *     返回位置最小的那个。
   */
  public static double min(final double value1, final double value2) {
    if (Comparison.compare(value1, value2) <= 0) {
      return value1;
    } else {
      return value2;
    }
  }

  /**
   * 返回三个{@code double}值中的最小值。
   *
   * @param value1
   *     一个{@code double}值；
   * @param value2
   *     一个{@code double}值；
   * @param value3
   *     一个{@code double}值；
   * @return {@code value1}、{@code value2}和{@code value3}的最小值。
   *     在相等的情况下，返回位置最小的那个。
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
   * 返回{@code double}数组中的最小值。
   *
   * @param array
   *     一个{@code double}数组，不能为{@code null}或空。
   * @return 数组中的最小值。在相等的情况下，返回位置最小的那个。
   * @throws NullArgumentException
   *     如果{@code array}为{@code null}或空。
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
   * 返回两个{@code Double}对象中的最小值。
   *
   * <p>对于{@code Double}对象，假设null是最小值。
   *
   * @param value1
   *     一个{@code Double}对象，可以为{@code null}。
   * @param value2
   *     一个{@code Double}对象，可以为{@code null}。
   * @return {@code value1}和{@code value2}的最小值。在相等的情况下，
   *     返回位置最小的那个。注意返回值要么是{@code null}，要么是参数之一的引用。
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
   * 返回三个{@code Double}对象中的最小值。
   *
   * <p>对于{@code Double}对象，假设null是最小值。
   *
   * @param value1
   *     一个{@code Double}对象，可以为{@code null}。
   * @param value2
   *     一个{@code Double}对象，可以为{@code null}。
   * @param value3
   *     一个{@code Double}对象，可以为{@code null}。
   * @return {@code value1}、{@code value2}和{@code value3}的最小值。
   *     在相等的情况下，返回位置最小的那个。注意返回值要么是{@code null}，要么是参数之一的引用。
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
   * 返回{@code Double}数组中的最小值。
   *
   * <p>对于{@code Double}对象，假设null是最小值。
   *
   * @param array
   *     一个{@code Double}数组，不能为{@code null}或空。
   * @return 数组中的最小对象。在相等的情况下，返回位置最小的那个。注意返回值要么是
   *     {@code null}，要么是{@code array}中某个元素的引用。
   * @throws NullArgumentException
   *     如果{@code array}为{@code null}或空。
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
   * 返回两个{@code String}对象中的最小值。
   *
   * <p>对于{@code String}对象，假设{@code null}是最小值。
   *
   * @param value1
   *     一个{@code String}对象，可以为{@code null}。
   * @param value2
   *     一个{@code String}对象，可以为{@code null}。
   * @return {@code value1}和{@code value2}的最小值。在相等的情况下，
   *     返回位置最小的那个。
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
   * 返回三个{@code String}对象中的最小值。
   *
   * <p>对于{@code String}对象，假设{@code null}是最小值。
   *
   * @param value1
   *     一个{@code String}对象，可以为{@code null}。
   * @param value2
   *     一个{@code String}对象，可以为{@code null}。
   * @param value3
   *     一个{@code String}对象，可以为{@code null}。
   * @return {@code value1}、{@code value2}和{@code value3}的最小值。
   *     在相等的情况下，返回位置最小的那个。
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
   * 返回{@code String}数组中的最小值。
   *
   * <p>对于{@code String}对象，假设null是最小值。
   *
   * @param array
   *     一个{@code String}数组，不能为{@code null}或空。
   * @return 数组中的最小对象。在相等的情况下，返回位置最小的那个。注意返回值要么是
   *     {@code null}，要么是{@code array}中某个元素的引用。
   * @throws NullArgumentException
   *     如果{@code array}为{@code null}或空。
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
   * 返回两个{@code Comparable}对象中的最小值。
   *
   * <p>假设{@code null}是最小值。
   *
   * @param <T>
   *     任意对象类型。注意它可以是数组类型或多维数组类型。
   * @param value1
   *     一个类{@code T}的对象，可以为{@code null}。
   * @param value2
   *     一个类{@code T}的对象，可以为{@code null}。
   * @return {@code value1}和{@code value2}的最小值。在相等的情况下，
   *     返回位置最小的那个。
   */
  public static <T> T min(final T value1, final T value2) {
    if (Comparison.compare(value1, value2) <= 0) {
      return value1;
    } else {
      return value2;
    }
  }

  /**
   * 返回三个{@code Comparable}对象中的最小值。
   *
   * <p>假设{@code null}是最小值。
   *
   * @param <T>
   *     任意对象类型。注意它可以是数组类型或多维数组类型。
   * @param value1
   *     一个类{@code T}的对象，可以为{@code null}。
   * @param value2
   *     一个类{@code T}的对象，可以为{@code null}。
   * @param value3
   *     一个类{@code T}的对象，可以为{@code null}。
   * @return {@code value1}、{@code value2}和{@code value3}的最小值。
   *     在相等的情况下，返回位置最小的那个。
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
   * 返回{@code Comparable}数组中的最小值。
   *
   * <p>假设null是最小值。
   *
   * @param <T>
   *     任意对象类型。注意它可以是数组类型或多维数组类型。
   * @param array
   *     一个类{@code T}的数组，不能为{@code null}或空。
   * @return 数组中的最小对象。在相等的情况下，返回位置最小的那个。注意返回值要么是
   *     {@code null}，要么是{@code array}中某个元素的引用。
   * @throws NullArgumentException
   *     如果{@code array}为{@code null}或空。
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
   * 返回{@code Comparable} {@code Iterable}对象中的最小值。
   *
   * <p>假设null是最小值。
   *
   * @param <T>
   *     任意对象类型。注意它可以是数组类型或多维数组类型。
   * @param iterable
   *     一个类{@code T}值的{@code Iterable}，不能为{@code null}或空。
   * @return 列表中的最小对象。在相等的情况下，返回位置最小的那个。注意返回值要么是
   *     {@code null}，要么是{@code iterable}中某个元素的引用。
   * @throws NullArgumentException
   *     如果{@code iterable}为{@code null}或空。
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
   * 返回两个{@code boolean}值中的最大值。
   *
   * <p>对于{@code boolean}值，假设{@code true &gt; false}。
   *
   * @param value1
   *     一个{@code boolean}值；
   * @param value2
   *     一个{@code boolean}值；
   * @return {@code value1}和{@code value2}的最大值。在相等的情况下，
   *     返回位置最大的那个。
   */
  public static boolean max(final boolean value1, final boolean value2) {
    if (value2) {
      return true;
    } else {
      return value1;
    }
  }

  /**
   * 返回三个{@code boolean}值中的最大值。
   *
   * <p>对于{@code boolean}值，假设{@code true &gt; false}。
   *
   * @param value1
   *     一个{@code boolean}值；
   * @param value2
   *     一个{@code boolean}值；
   * @param value3
   *     一个{@code boolean}值；
   * @return {@code value1}、{@code value2}和{@code value3}的最大值。
   *     在相等的情况下，返回位置最大的那个。
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
   * 返回{@code boolean}数组中的最大值。
   *
   * <p>对于{@code boolean}值，假设{@code true &gt; false}。
   *
   * @param array
   *     一个{@code boolean}数组，不能为{@code null}或空。
   * @return 数组中的最大值。在相等的情况下，返回位置最大的那个。
   * @throws NullArgumentException
   *     如果{@code array}为{@code null}或空。
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
   * 返回两个{@code Boolean}对象中的最大值。
   *
   * <p>对于{@code Boolean}对象，假设{@code Boolean.TRUE &gt; Boolean.FALSE &gt; null}。
   *
   * @param value1
   *     一个{@code Boolean}对象，可以为{@code null}。
   * @param value2
   *     一个{@code Boolean}对象，可以为{@code null}。
   * @return {@code value1}和{@code value2}的最大值。在相等的情况下，
   *     返回位置最大的那个。注意返回值要么是{@code null}，要么是参数中的一个引用。
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
   * 返回三个{@code Boolean}对象中的最大值。
   *
   * <p>对于{@code Boolean}对象，假设{@code Boolean.TRUE &gt; Boolean.FALSE &gt; null}。
   *
   * @param value1
   *     一个{@code Boolean}对象，可以为{@code null}。
   * @param value2
   *     一个{@code Boolean}对象，可以为{@code null}。
   * @param value3
   *     一个{@code Boolean}对象，可以为{@code null}。
   * @return {@code value1}、{@code value2}和{@code value3}的最大值。
   *     在相等的情况下，返回位置最大的那个。注意返回值要么是{@code null}，
   *     要么是参数中的一个引用。
   */
  public static Boolean max(
      final Boolean value1, final Boolean value2, final Boolean value3) {
    if (value1 == null) {
      // 返回value2和value3的最大值
      return max(value2, value3);
    } else if (value2 == null) {
      // 返回value1和value3的最大值
      return max(value1, value3);
    } else if (value3 == null) {
      // 返回value1和value2的最大值
      return max(value1, value2);
    } else {
      // 返回非null的value1、value2和value3的最大值
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
   * 返回{@code Boolean}数组中的最大值。
   *
   * <p>对于{@code Boolean}对象，假设{@code Boolean.TRUE &gt; Boolean.FALSE &gt; null}。
   *
   * @param array
   *     一个{@code Boolean}数组，不能为{@code null}或空。
   * @return 数组中的最大对象。在相等的情况下，返回位置最大的那个。
   *     注意返回值要么是{@code null}，要么是{@code array}中某个元素的引用。
   * @throws NullArgumentException
   *     如果{@code array}为{@code null}或空。
   */
  public static Boolean max(final Boolean... array) {
    if ((array == null) || (array.length == 0)) {
      throw new NullArgumentException();
    }
    // 在数组中找到最后一个非null元素
    int i = array.length - 1;
    while ((i >= 0) && (array[i] == null)) {
      --i;
    }
    if (i < 0) {
      // 数组中的所有元素都是null
      return null;
    }
    final Boolean max = array[i];
    if (max.booleanValue()) {
      return max;
    }
    // 现在max.booleanValue() == false
    for (--i; i >= 0; --i) {
      final Boolean element = array[i];
      if ((element != null) && element.booleanValue()) {
        return element;
      }
    }
    return max;
  }

  /**
   * 返回两个{@code char}值中的最大值。
   *
   * @param value1
   *     一个{@code char}值；
   * @param value2
   *     一个{@code char}值；
   * @return {@code value1}和{@code value2}的最大值。在相等的情况下，
   *     返回位置最大的那个。
   */
  public static char max(final char value1, final char value2) {
    return (value1 <= value2 ? value2 : value1);
  }

  /**
   * 返回三个{@code char}值中的最大值。
   *
   * @param value1
   *     一个{@code char}值；
   * @param value2
   *     一个{@code char}值；
   * @param value3
   *     一个{@code char}值；
   * @return {@code value1}、{@code value2}和{@code value3}的最大值。
   *     在相等的情况下，返回位置最大的那个。
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
   * 返回{@code char}数组中的最大值。
   *
   * @param array
   *     一个{@code char}数组，不能为{@code null}或空。
   * @return 数组中的最大值。在相等的情况下，返回位置最大的那个。
   * @throws NullArgumentException
   *     如果{@code array}为{@code null}或空。
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
   * 返回两个{@code Character}对象中的最大值。
   *
   * <p>对于{@code Character}对象，假设null是最大值。
   *
   * @param value1
   *     一个{@code Character}对象，可以为{@code null}。
   * @param value2
   *     一个{@code Character}对象，可以为{@code null}。
   * @return {@code value1}和{@code value2}的最大值。在相等的情况下，
   *     返回位置最大的那个。注意返回值要么是{@code null}，要么是参数中的一个引用。
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
   * 返回三个{@code Character}对象中的最大值。
   *
   * <p>对于{@code Character}对象，假设null是最大值。
   *
   * @param value1
   *     一个{@code Character}对象，可以为{@code null}。
   * @param value2
   *     一个{@code Character}对象，可以为{@code null}。
   * @param value3
   *     一个{@code Character}对象，可以为{@code null}。
   * @return {@code value1}、{@code value2}和{@code value3}的最大值。
   *     在相等的情况下，返回位置最大的那个。注意返回值要么是{@code null}，
   *     要么是参数中的一个引用。
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
   * 返回{@code Character}数组中的最大值。
   *
   * <p>对于{@code Character}对象，假设null是最大值。
   *
   * @param array
   *     一个{@code Character}数组，不能为{@code null}或空。
   * @return 数组中的最大对象。在相等的情况下，返回位置最大的那个。
   *     注意返回值要么是{@code null}，要么是{@code array}中某个元素的引用。
   * @throws NullArgumentException
   *     如果{@code array}为{@code null}或空。
   */
  public static Character max(final Character... array) {
    if ((array == null) || (array.length == 0)) {
      throw new NullArgumentException();
    }
    // 找到最后一个非null元素
    int i = array.length - 1;
    while ((i >= 0) && (array[i] == null)) {
      --i;
    }
    if (i < 0) {
      // 数组中的所有元素都是null
      return null;
    }
    Character max = array[i];
    char maxValue = max.charValue();
    // 测试其余元素
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
   * 返回两个{@code byte}值中的最大值。
   *
   * @param value1
   *     一个{@code byte}值；
   * @param value2
   *     一个{@code byte}值；
   * @return {@code value1}和{@code value2}的最大值。在相等的情况下，
   *     返回位置最大的那个。
   */
  public static byte max(final byte value1, final byte value2) {
    return (value1 <= value2 ? value2 : value1);
  }

  /**
   * 返回三个{@code byte}值中的最大值。
   *
   * @param value1
   *     一个{@code byte}值；
   * @param value2
   *     一个{@code byte}值；
   * @param value3
   *     一个{@code byte}值；
   * @return {@code value1}、{@code value2}和{@code value3}的最大值。
   *     在相等的情况下，返回位置最大的那个。
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
   * 返回{@code byte}数组中的最大值。
   *
   * @param array
   *     一个{@code byte}数组，不能为{@code null}或空。
   * @return 数组中的最大值。在相等的情况下，返回位置最大的那个。
   * @throws NullArgumentException
   *     如果{@code array}为{@code null}或空。
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
   * 返回两个{@code Byte}对象中的最大值。
   *
   * <p>对于{@code Byte}对象，假设null是最大值。
   *
   * @param value1
   *     一个{@code Byte}对象，可以为{@code null}。
   * @param value2
   *     一个{@code Byte}对象，可以为{@code null}。
   * @return {@code value1}和{@code value2}的最大值。在相等的情况下，
   *     返回位置最大的那个。注意返回值要么是{@code null}，要么是参数中的一个引用。
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
   * 返回三个{@code Byte}对象中的最大值。
   *
   * <p>对于{@code Byte}对象，假设null是最大值。
   *
   * @param value1
   *     一个{@code Byte}对象，可以为{@code null}。
   * @param value2
   *     一个{@code Byte}对象，可以为{@code null}。
   * @param value3
   *     一个{@code Byte}对象，可以为{@code null}。
   * @return {@code value1}、{@code value2}和{@code value3}的最大值。
   *     在相等的情况下，返回位置最大的那个。注意返回值要么是{@code null}，
   *     要么是参数中的一个引用。
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
   * 返回{@code Byte}数组中的最大值。
   *
   * <p>对于{@code Byte}对象，假设null是最大值。
   *
   * @param array
   *     一个{@code Byte}数组，不能为{@code null}或空。
   * @return 数组中的最大对象。在相等的情况下，返回位置最大的那个。
   *     注意返回值要么是{@code null}，要么是{@code array}中某个元素的引用。
   * @throws NullArgumentException
   *     如果{@code array}为{@code null}或空。
   */
  public static Byte max(final Byte... array) {
    if ((array == null) || (array.length == 0)) {
      throw new NullArgumentException();
    }
    // 找到最后一个非null元素
    int i = array.length - 1;
    while ((i >= 0) && (array[i] == null)) {
      --i;
    }
    if (i < 0) {
      // 数组中的所有元素都是null
      return null;
    }
    Byte max = array[i];
    byte maxValue = max.byteValue();
    // 测试其余元素
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
   * 返回两个{@code short}值中的最大值。
   *
   * @param value1
   *     一个{@code short}值；
   * @param value2
   *     一个{@code short}值；
   * @return {@code value1}和{@code value2}的最大值。在相等的情况下，
   *     返回位置最大的那个。
   */
  public static short max(final short value1, final short value2) {
    return (value1 <= value2 ? value2 : value1);
  }

  /**
   * 返回三个{@code short}值中的最大值。
   *
   * @param value1
   *     一个{@code short}值；
   * @param value2
   *     一个{@code short}值；
   * @param value3
   *     一个{@code short}值；
   * @return {@code value1}、{@code value2}和{@code value3}的最大值。
   *     在相等的情况下，返回位置最大的那个。
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
   * 返回{@code short}数组中的最大值。
   *
   * @param array
   *     一个{@code short}数组，不能为{@code null}或空。
   * @return 数组中的最大值。在相等的情况下，返回位置最大的那个。
   * @throws NullArgumentException
   *     如果{@code array}为{@code null}或空。
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
   * 返回两个{@code Short}对象中的最大值。
   *
   * <p>对于{@code Short}对象，假设null是最大值。
   *
   * @param value1
   *     一个{@code Short}对象，可以为{@code null}。
   * @param value2
   *     一个{@code Short}对象，可以为{@code null}。
   * @return {@code value1}和{@code value2}的最大值。在相等的情况下，
   *     返回位置最大的那个。注意返回值要么是{@code null}，要么是参数中的一个引用。
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
   * 返回三个{@code Short}对象中的最大值。
   *
   * <p>对于{@code Short}对象，假设null是最大值。
   *
   * @param value1
   *     一个{@code Short}对象，可以为{@code null}。
   * @param value2
   *     一个{@code Short}对象，可以为{@code null}。
   * @param value3
   *     一个{@code Short}对象，可以为{@code null}。
   * @return {@code value1}、{@code value2}和{@code value3}的最大值。
   *     在相等的情况下，返回位置最大的那个。注意返回值要么是{@code null}，
   *     要么是参数中的一个引用。
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
   * 返回{@code Short}数组中的最大值。
   *
   * <p>对于{@code Short}对象，假设null是最大值。
   *
   * @param array
   *     一个{@code Short}数组，不能为{@code null}或空。
   * @return 数组中的最大对象。在相等的情况下，返回位置最大的那个。
   *     注意返回值要么是{@code null}，要么是{@code array}中某个元素的引用。
   * @throws NullArgumentException
   *     如果{@code array}为{@code null}或空。
   */
  public static Short max(final Short... array) {
    if ((array == null) || (array.length == 0)) {
      throw new NullArgumentException();
    }
    // 找到最后一个非null元素
    int i = array.length - 1;
    while ((i >= 0) && (array[i] == null)) {
      --i;
    }
    if (i < 0) {
      // 数组中的所有元素都是null
      return null;
    }
    Short max = array[i];
    short maxValue = max.shortValue();
    // 测试其余元素
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
   * 返回两个{@code int}值中的最大值。
   *
   * @param value1
   *     一个{@code int}值；
   * @param value2
   *     一个{@code int}值；
   * @return {@code value1}和{@code value2}的最大值。在相等的情况下，
   *     返回位置最大的那个。
   */
  public static int max(final int value1, final int value2) {
    return (value1 <= value2 ? value2 : value1);
  }

  /**
   * 返回三个{@code int}值中的最大值。
   *
   * @param value1
   *     一个{@code int}值；
   * @param value2
   *     一个{@code int}值；
   * @param value3
   *     一个{@code int}值；
   * @return {@code value1}、{@code value2}和{@code value3}的最大值。
   *     在相等的情况下，返回位置最大的那个。
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
   * 返回{@code int}数组中的最大值。
   *
   * @param array
   *     一个{@code int}数组，不能为{@code null}或空。
   * @return 数组中的最大值。在相等的情况下，返回位置最大的那个。
   * @throws NullArgumentException
   *     如果{@code array}为{@code null}或空。
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
   * 返回两个{@code Integer}对象中的最大值。
   *
   * <p>对于{@code Integer}对象，假设null是最大值。
   *
   * @param value1
   *     一个{@code Integer}对象，可以为{@code null}。
   * @param value2
   *     一个{@code Integer}对象，可以为{@code null}。
   * @return {@code value1}和{@code value2}的最大值。在相等的情况下，
   *     返回位置最大的那个。注意返回值要么是{@code null}，要么是参数中的一个引用。
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
   * 返回三个{@code Integer}对象中的最大值。
   *
   * <p>对于{@code Integer}对象，假设null是最大值。
   *
   * @param value1
   *     一个{@code Integer}对象，可以为{@code null}。
   * @param value2
   *     一个{@code Integer}对象，可以为{@code null}。
   * @param value3
   *     一个{@code Integer}对象，可以为{@code null}。
   * @return {@code value1}、{@code value2}和{@code value3}的最大值。
   *     在相等的情况下，返回位置最大的那个。注意返回值要么是{@code null}，
   *     要么是参数中的一个引用。
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
   * 返回{@code Integer}数组中的最大值。
   *
   * <p>对于{@code Integer}对象，假设null是最大值。
   *
   * @param array
   *     一个{@code Integer}数组，不能为{@code null}或空。
   * @return 数组中的最大对象。在相等的情况下，返回位置最大的那个。
   *     注意返回值要么是{@code null}，要么是{@code array}中某个元素的引用。
   * @throws NullArgumentException
   *     如果{@code array}为{@code null}或空。
   */
  public static Integer max(final Integer... array) {
    if ((array == null) || (array.length == 0)) {
      throw new NullArgumentException();
    }
    // 找到最后一个非null元素
    int i = array.length - 1;
    while ((i >= 0) && (array[i] == null)) {
      --i;
    }
    if (i < 0) {
      // 数组中的所有元素都是null
      return null;
    }
    Integer max = array[i];
    int maxValue = max.intValue();
    // 测试其余元素
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
   * 返回两个{@code long}值中的最大值。
   *
   * @param value1
   *     一个{@code long}值；
   * @param value2
   *     一个{@code long}值；
   * @return {@code value1}和{@code value2}的最大值。在相等的情况下，
   *     返回位置最大的那个。
   */
  public static long max(final long value1, final long value2) {
    return (value1 <= value2 ? value2 : value1);
  }

  /**
   * 返回三个{@code long}值中的最大值。
   *
   * @param value1
   *     一个{@code long}值；
   * @param value2
   *     一个{@code long}值；
   * @param value3
   *     一个{@code long}值；
   * @return {@code value1}、{@code value2}和{@code value3}的最大值。
   *     在相等的情况下，返回位置最大的那个。
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
   * 返回{@code long}数组中的最大值。
   *
   * @param array
   *     一个{@code long}数组，不能为{@code null}或空。
   * @return 数组中的最大值。在相等的情况下，返回位置最大的那个。
   * @throws NullArgumentException
   *     如果{@code array}为{@code null}或空。
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
   * 返回两个{@code Long}对象中的最大值。
   *
   * <p>对于{@code Long}对象，假设null是最大值。
   *
   * @param value1
   *     一个{@code Long}对象，可以为{@code null}。
   * @param value2
   *     一个{@code Long}对象，可以为{@code null}。
   * @return {@code value1}和{@code value2}的最大值。在相等的情况下，
   *     返回位置最大的那个。注意返回值要么是{@code null}，要么是参数中的一个引用。
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
   * 返回三个{@code Long}对象中的最大值。
   *
   * <p>对于{@code Long}对象，假设null是最大值。
   *
   * @param value1
   *     一个{@code Long}对象，可以为{@code null}。
   * @param value2
   *     一个{@code Long}对象，可以为{@code null}。
   * @param value3
   *     一个{@code Long}对象，可以为{@code null}。
   * @return {@code value1}、{@code value2}和{@code value3}的最大值。
   *     在相等的情况下，返回位置最大的那个。注意返回值要么是{@code null}，
   *     要么是参数中的一个引用。
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
   * 返回{@code Long}数组中的最大值。
   *
   * <p>对于{@code Long}对象，假设null是最大值。
   *
   * @param array
   *     一个{@code Long}数组，不能为{@code null}或空。
   * @return 数组中的最大对象。在相等的情况下，返回位置最大的那个。
   *     注意返回值要么是{@code null}，要么是{@code array}中某个元素的引用。
   * @throws NullArgumentException
   *     如果{@code array}为{@code null}或空。
   */
  public static Long max(final Long... array) {
    if ((array == null) || (array.length == 0)) {
      throw new NullArgumentException();
    }
    // 找到最后一个非null元素
    int i = array.length - 1;
    while ((i >= 0) && (array[i] == null)) {
      --i;
    }
    if (i < 0) {
      // 数组中的所有元素都是null
      return null;
    }
    Long max = array[i];
    long maxValue = max.longValue();
    // 测试其余元素
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
   * 返回两个{@code float}值中的最大值。
   *
   * @param value1
   *     一个{@code float}值；
   * @param value2
   *     一个{@code float}值；
   * @return {@code value1}和{@code value2}的最大值。在相等的情况下，
   *     返回位置最大的那个。
   */
  public static float max(final float value1, final float value2) {
    if (Comparison.compare(value1, value2) <= 0) {
      return value2;
    } else {
      return value1;
    }
  }

  /**
   * 返回三个{@code float}值中的最大值。
   *
   * @param value1
   *     一个{@code float}值；
   * @param value2
   *     一个{@code float}值；
   * @param value3
   *     一个{@code float}值；
   * @return {@code value1}、{@code value2}和{@code value3}的最大值。
   *     在相等的情况下，返回位置最大的那个。
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
   * 返回{@code float}数组中的最大值。
   *
   * @param array
   *     一个{@code float}数组，不能为{@code null}或空。
   * @return 数组中的最大值。在相等的情况下，返回位置最大的那个。
   * @throws NullArgumentException
   *     如果{@code array}为{@code null}或空。
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
   * 返回两个{@code Float}对象中的最大值。
   *
   * <p>对于{@code Float}对象，假设null是最大值。
   *
   * @param value1
   *     一个{@code Float}对象，可以为{@code null}。
   * @param value2
   *     一个{@code Float}对象，可以为{@code null}。
   * @return {@code value1}和{@code value2}的最大值。在相等的情况下，
   *     返回位置最大的那个。注意返回值要么是{@code null}，要么是参数中的一个引用。
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
   * 返回三个{@code Float}对象中的最大值。
   *
   * <p>对于{@code Float}对象，假设null是最大值。
   *
   * @param value1
   *     一个{@code Float}对象，可以为{@code null}。
   * @param value2
   *     一个{@code Float}对象，可以为{@code null}。
   * @param value3
   *     一个{@code Float}对象，可以为{@code null}。
   * @return {@code value1}、{@code value2}和{@code value3}的最大值。
   *     在相等的情况下，返回位置最大的那个。注意返回值要么是{@code null}，
   *     要么是参数中的一个引用。
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
   * 返回{@code Float}数组中的最大值。
   *
   * <p>对于{@code Float}对象，假设null是最大值。
   *
   * @param array
   *     一个{@code Float}数组，不能为{@code null}或空。
   * @return 数组中的最大对象。在相等的情况下，返回位置最大的那个。
   *     注意返回值要么是{@code null}，要么是{@code array}中某个元素的引用。
   * @throws NullArgumentException
   *     如果{@code array}为{@code null}或空。
   */
  public static Float max(final Float... array) {
    if ((array == null) || (array.length == 0)) {
      throw new NullArgumentException();
    }
    // 找到最后一个非null元素
    int i = array.length - 1;
    while ((i >= 0) && (array[i] == null)) {
      --i;
    }
    if (i < 0) {
      // 数组中的所有元素都是null
      return null;
    }
    Float max = array[i];
    float maxValue = max.floatValue();
    // 测试其余元素
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
   * 返回两个{@code double}值中的最大值。
   *
   * @param value1
   *     一个{@code double}值；
   * @param value2
   *     一个{@code double}值；
   * @return {@code value1}和{@code value2}的最大值。在相等的情况下，
   *     返回位置最大的那个。
   */
  public static double max(final double value1, final double value2) {
    if (Comparison.compare(value1, value2) <= 0) {
      return value2;
    } else {
      return value1;
    }
  }

  /**
   * 返回三个{@code double}值中的最大值。
   *
   * @param value1
   *     一个{@code double}值；
   * @param value2
   *     一个{@code double}值；
   * @param value3
   *     一个{@code double}值；
   * @return {@code value1}、{@code value2}和{@code value3}的最大值。
   *     在相等的情况下，返回位置最大的那个。
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
   * 返回{@code double}数组中的最大值。
   *
   * @param array
   *     一个{@code double}数组，不能为{@code null}或空。
   * @return 数组中的最大值。在相等的情况下，返回位置最大的那个。
   * @throws NullArgumentException
   *     如果{@code array}为{@code null}或空。
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
   * 返回两个{@code Double}对象中的最大值。
   *
   * <p>对于{@code Double}对象，假设null是最大值。
   *
   * @param value1
   *     一个{@code Double}对象，可以为{@code null}。
   * @param value2
   *     一个{@code Double}对象，可以为{@code null}。
   * @return {@code value1}和{@code value2}的最大值。在相等的情况下，
   *     返回位置最大的那个。注意返回值要么是{@code null}，要么是参数中的一个引用。
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
   * 返回三个{@code Double}对象中的最大值。
   *
   * <p>对于{@code Double}对象，假设null是最大值。
   *
   * @param value1
   *     一个{@code Double}对象，可以为{@code null}。
   * @param value2
   *     一个{@code Double}对象，可以为{@code null}。
   * @param value3
   *     一个{@code Double}对象，可以为{@code null}。
   * @return {@code value1}、{@code value2}和{@code value3}的最大值。
   *     在相等的情况下，返回位置最大的那个。注意返回值要么是{@code null}，
   *     要么是参数中的一个引用。
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
   * 返回{@code Double}数组中的最大值。
   *
   * <p>对于{@code Double}对象，假设null是最大值。
   *
   * @param array
   *     一个{@code Double}数组，不能为{@code null}或空。
   * @return 数组中的最大对象。在相等的情况下，返回位置最大的那个。
   *     注意返回值要么是{@code null}，要么是{@code array}中某个元素的引用。
   * @throws NullArgumentException
   *     如果{@code array}为{@code null}或空。
   */
  public static Double max(final Double... array) {
    if ((array == null) || (array.length == 0)) {
      throw new NullArgumentException();
    }
    // 找到最后一个非null元素
    int i = array.length - 1;
    while ((i >= 0) && (array[i] == null)) {
      --i;
    }
    if (i < 0) {
      // 数组中的所有元素都是null
      return null;
    }
    Double max = array[i];
    double maxValue = max.doubleValue();
    // 测试其余元素
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
   * 返回两个{@code String}对象中的最大值。
   *
   * <p>对于{@code String}对象，假设{@code null}是最大值。
   *
   * @param value1
   *     一个{@code String}对象，可以为{@code null}。
   * @param value2
   *     一个{@code String}对象，可以为{@code null}。
   * @return {@code value1}和{@code value2}的最大值。在相等的情况下，
   *     返回位置最大的那个。
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
   * 返回三个{@code String}对象中的最大值。
   *
   * <p>对于{@code String}对象，假设{@code null}是最大值。
   *
   * @param value1
   *     一个{@code String}对象，可以为{@code null}。
   * @param value2
   *     一个{@code String}对象，可以为{@code null}。
   * @param value3
   *     一个{@code String}对象，可以为{@code null}。
   * @return {@code value1}、{@code value2}和{@code value3}的最大值。
   *     在相等的情况下，返回位置最大的那个。
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
   * 返回{@code String}数组中的最大值。
   *
   * <p>对于{@code String}对象，假设null是最大值。
   *
   * @param array
   *     一个{@code String}数组，不能为{@code null}或空。
   * @return 数组中的最大对象。在相等的情况下，返回位置最大的那个。
   *     注意返回值要么是{@code null}，要么是{@code array}中某个元素的引用。
   * @throws NullArgumentException
   *     如果{@code array}为{@code null}或空。
   */
  public static String max(final String... array) {
    if ((array == null) || (array.length == 0)) {
      throw new NullArgumentException();
    }
    // 找到最后一个非null元素
    int i = array.length - 1;
    while ((i >= 0) && (array[i] == null)) {
      --i;
    }
    if (i < 0) {
      // 数组中的所有元素都是null
      return null;
    }
    String max = array[i];
    // 测试其余元素
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
   * 返回两个{@code Comparable}对象中的最大值。
   *
   * <p>假设{@code null}是最大值。
   *
   * @param <T>
   *     任意对象类型。注意它可以是数组类型或多维数组类型。
   * @param value1
   *     一个类{@code T}的对象，可以为{@code null}。
   * @param value2
   *     一个类{@code T}的对象，可以为{@code null}。
   * @return {@code value1}和{@code value2}的最大值。在相等的情况下，
   *     返回位置最大的那个。
   */
  public static <T> T max(final T value1, final T value2) {
    if (Comparison.compare(value1, value2) <= 0) {
      return value2;
    } else {
      return value1;
    }
  }

  /**
   * 返回三个{@code Comparable}对象中的最大值。
   *
   * <p>假设{@code null}是最大值。
   *
   * @param <T>
   *     任意对象类型。注意它可以是数组类型或多维数组类型。
   * @param value1
   *     一个类{@code T}的对象，可以为{@code null}。
   * @param value2
   *     一个类{@code T}的对象，可以为{@code null}。
   * @param value3
   *     一个类{@code T}的对象，可以为{@code null}。
   * @return {@code value1}、{@code value2}和{@code value3}的最大值。
   *     在相等的情况下，返回位置最大的那个。
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
   * 返回{@code Comparable}数组中的最大值。
   *
   * <p>假设null是最大值。
   *
   * @param <T>
   *     任意对象类型。注意它可以是数组类型或多维数组类型。
   * @param array
   *     一个类{@code T}的数组，不能为{@code null}或空。
   * @return 数组中的最大对象。在相等的情况下，返回位置最大的那个。注意返回值要么是
   *     {@code null}，要么是{@code array}中某个元素的引用。
   * @throws NullArgumentException
   *     如果{@code array}为{@code null}或空。
   */
  @SafeVarargs
  public static <T> T max(final T... array) {
    if ((array == null) || (array.length == 0)) {
      throw new NullArgumentException();
    }
    // 找到最后一个非null元素
    int i = array.length - 1;
    while ((i >= 0) && (array[i] == null)) {
      --i;
    }
    if (i < 0) {
      // 数组中的所有元素都是null
      return null;
    }
    T max = array[i];
    // 测试其余元素
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
   * 返回{@code Comparable} {@code Iterable}对象中的最大值。
   *
   * <p>假设null是最大值。
   *
   * @param <T>
   *     任意对象类型。注意它可以是数组类型或多维数组类型。
   * @param iterable
   *     一个类{@code T}值的{@code Iterable}，不能为{@code null}或空。
   * @return 列表中的最大对象。在相等的情况下，返回位置最大的那个。注意返回值要么是
   *     {@code null}，要么是{@code iterable}中某个元素的引用。
   * @throws NullArgumentException
   *     如果{@code iterable}为{@code null}或空。
   */
  public static <T> T max(final Iterable<T> iterable) {
    if (iterable == null) {
      throw new NullArgumentException();
    }
    final Iterator<T> iter = iterable.iterator();
    if (!iter.hasNext()) {
      throw new NullArgumentException();
    }
    // 找到第一个非null元素
    T max = iter.next();
    while ((max == null) && iter.hasNext()) {
      max = iter.next();
    }
    if (max == null) {
      // iterable中的所有元素都是null
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