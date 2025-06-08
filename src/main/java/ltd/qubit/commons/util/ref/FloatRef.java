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
 * 一个表示 float 变量引用的简单结构体，可在函数调用中用作引用参数。
 *
 * @author 胡海星
 */
public final class FloatRef {

  /**
   * 被引用的 float 值。
   */
  public float value;

  /**
   * 构造一个值为 0.0f 的 {@code FloatRef}。
   */
  public FloatRef() {
    this.value = 0.0f;
  }

  /**
   * 构造一个具有指定值的 {@code FloatRef}。
   *
   * @param value
   *     指定的初始值。
   */
  public FloatRef(final float value) {
    this.value = value;
  }
}