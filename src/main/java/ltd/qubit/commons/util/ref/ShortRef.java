////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.ref;

import java.util.Objects;

import javax.annotation.Nullable;

/**
 * 表示一个 short 变量的引用。
 *
 * <p>这个一个简单的辅助类，用于在函数调用中传递引用类型的参数。
 *
 * @author 胡海星
 */
public final class ShortRef {

  /**
   * 被引用的 short 变量.
   */
  public short value;

  /**
   * 构造一个值为0的 {@link ShortRef} 对象.
   */
  public ShortRef() {
    value = 0;
  }

  /**
   * 构造一个具有指定初始值的 {@link ShortRef} 对象.
   *
   * @param value
   *     指定的初始值.
   */
  public ShortRef(final short value) {
    this.value = value;
  }

  @Override
  public boolean equals(@Nullable final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    final ShortRef other = (ShortRef) o;
    return value == other.value;
  }

  @Override
  public int hashCode() {
    return Objects.hash(value);
  }

  @Override
  public String toString() {
    return String.valueOf(value);
  }
}