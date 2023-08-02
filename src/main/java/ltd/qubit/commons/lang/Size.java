////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.lang;

import java.util.LinkedList;

import javax.annotation.Nullable;

/**
 * This class defines the approximate size in bytes of common data types.
 *
 * @author Haixing Hu
 */
public final class Size {

  /**
   * The size in bytes of a {@code boolean} variable in this JVM.
   */
  public static final long BOOL = 1L;

  /**
   * The size in bytes of a {@code char} variable in this JVM.
   */
  public static final long CHAR = Character.BYTES;

  /**
   * The size in bytes of a {@code byte} variable in this JVM.
   */
  public static final long BYTE = Byte.BYTES;

  /**
   * The size in bytes of a {@code short} variable in this JVM.
   */
  public static final long SHORT = Short.BYTES;

  /**
   * The size in bytes of a {@code int} variable in this JVM.
   */
  public static final long INT = Integer.BYTES;

  /**
   * The size in bytes of a {@code long} variable in this JVM.
   */
  public static final long LONG = Long.BYTES;

  /**
   * The size in bytes of a {@code float} variable in this JVM.
   */
  public static final long FLOAT = Float.BYTES;

  /**
   * The size in bytes of a {@code double} variable in this JVM.
   */
  public static final long DOUBLE = Double.BYTES;

  /**
   * The size in bytes of an object reference variable in this JVM.
   */
  public static final long REFERENCE = (SystemUtils.IS_JAVA_64BIT ? Long.BYTES : Integer.BYTES);

  /**
   * The size in bytes of a {@code Date} variable in this JVM.
   */
  public static final long DATE = Long.BYTES;

  /**
   * The size in bytes of a entry object of the {@link LinkedList} class.
   */
  public static final long LINKED_LIST_ENTRY = 3 * REFERENCE;

  /**
   * The size in bytes of an empty {@link LinkedList} object.
   */
  public static final long LINKED_LIST = REFERENCE + 2 * INT + LINKED_LIST_ENTRY;

  /**
   * Gets the size in bytes of a {@code String} object.
   *
   * @param str
   *     a reference to a {@code String} object, which could be null.
   * @return the size in bytes of the specified {@code String} object; or 0 if
   *     the reference is {@code null}.
   */
  public static long of(@Nullable final String str) {
    return (str == null ? 0 : str.length() * CHAR);
  }
}
