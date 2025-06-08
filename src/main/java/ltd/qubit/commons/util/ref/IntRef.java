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
 * 一个表示 int 变量引用的简单结构体，可在函数调用中用作引用参数。
 *
 * @author 胡海星
 */
public final class IntRef {

  /**
   * 被引用的 int 值。
   */
  public int value;

  /**
   * 构造一个值为 0 的 {@code IntRef}。
   */
  public IntRef() {
    this.value = 0;
  }

  /**
   * 构造一个具有指定值的 {@code IntRef}。
   *
   * @param value
   *     指定的初始值。
   */
  public IntRef(final int value) {
    this.value = value;
  }
}