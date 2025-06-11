////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.lang;

import java.util.LinkedList;

import javax.annotation.Nullable;

/**
 * 本类定义了常见数据类型的近似字节大小。
 *
 * @author 胡海星
 */
public final class Size {

  /**
   * 在此JVM中{@code boolean}变量的字节大小。
   */
  public static final long BOOL = 1L;

  /**
   * 在此JVM中{@code char}变量的字节大小。
   */
  public static final long CHAR = Character.BYTES;

  /**
   * 在此JVM中{@code byte}变量的字节大小。
   */
  public static final long BYTE = Byte.BYTES;

  /**
   * 在此JVM中{@code short}变量的字节大小。
   */
  public static final long SHORT = Short.BYTES;

  /**
   * 在此JVM中{@code int}变量的字节大小。
   */
  public static final long INT = Integer.BYTES;

  /**
   * 在此JVM中{@code long}变量的字节大小。
   */
  public static final long LONG = Long.BYTES;

  /**
   * 在此JVM中{@code float}变量的字节大小。
   */
  public static final long FLOAT = Float.BYTES;

  /**
   * 在此JVM中{@code double}变量的字节大小。
   */
  public static final long DOUBLE = Double.BYTES;

  /**
   * 在此JVM中对象引用变量的字节大小。
   */
  public static final long REFERENCE = (SystemUtils.IS_JAVA_64BIT ? Long.BYTES : Integer.BYTES);

  /**
   * 在此JVM中{@code Date}变量的字节大小。
   */
  public static final long DATE = Long.BYTES;

  /**
   * {@link LinkedList}类的条目对象的字节大小。
   */
  public static final long LINKED_LIST_ENTRY = 3 * REFERENCE;

  /**
   * 空{@link LinkedList}对象的字节大小。
   */
  public static final long LINKED_LIST = REFERENCE + 2 * INT + LINKED_LIST_ENTRY;

  /**
   * 获取{@code String}对象的字节大小。
   *
   * @param str
   *     指向{@code String}对象的引用，可以为null。
   * @return 指定的{@code String}对象的字节大小；如果引用为{@code null}则返回0。
   */
  public static long of(@Nullable final String str) {
    return (str == null ? 0 : str.length() * CHAR);
  }
}