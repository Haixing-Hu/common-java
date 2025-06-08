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
 * 一个表示 double 数组变量引用的简单结构体，可在函数调用中用作引用参数。
 *
 * @author 胡海星
 */
public final class DoubleArrayRef {
  /**
   * 被引用的 double 数组值。
   */
  public double[] value;

  /**
   * 构造一个值为 {@code null} 的 {@code DoubleArrayRef}。
   */
  public DoubleArrayRef() {
    this.value = null;
  }

  /**
   * 构造一个具有指定值的 {@code DoubleArrayRef}。
   *
   * @param value
   *     指定的初始值。
   */
  public DoubleArrayRef(final double[] value) {
    this.value = value;
  }
}