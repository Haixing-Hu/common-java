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
 * 此结构表示对{@code double}变量的引用，可在函数调用中用作引用参数。
 *
 * @author 胡海星
 */
public final class DoubleRef {

  /**
   * 此引用指向的{@code double}变量。
   */
  public double value;

  /**
   * 构造一个{@code DoubleRef}对象。
   */
  public DoubleRef() {
    value = 0;
  }

  /**
   * 构造一个{@code DoubleRef}对象。
   *
   * @param value
   *     指定的初始值。
   */
  public DoubleRef(final double value) {
    this.value = value;
  }
}