////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.ref;

/**
 * 此结构表示对{@code char[]}变量的引用，可在函数调用中用作引用参数。
 *
 * @author 胡海星
 */
public final class CharArrayRef {

  /**
   * 此引用指向的{@code char[]}变量。
   */
  public char[] value;

  /**
   * 构造一个{@code CharArrayRef}对象。
   */
  public CharArrayRef() {
    value = null;
  }

  /**
   * 构造一个{@code CharArrayRef}对象。
   *
   * @param value
   *     指定的初始值。
   */
  public CharArrayRef(final char[] value) {
    this.value = value;
  }
}