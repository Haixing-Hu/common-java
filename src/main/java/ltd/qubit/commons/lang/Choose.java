////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.lang;

import javax.annotation.Nullable;

/**
 * 提供选择函数。
 *
 * @author 胡海星
 */
public class Choose {

  /**
   * 从两个对象中选择第一个非 null 的值。
   *
   * @param <T> 对象的类型
   * @param v1 第一个值
   * @param v2 第二个值
   * @return 如果 v1 不为 null，返回 v1；否则返回 v2
   */
  @Nullable
  public static <T> T choose(@Nullable final T v1, @Nullable final T v2) {
    return v1 != null ? v1 : v2;
  }

  /**
   * 从三个对象中选择第一个非 null 的值。
   *
   * @param <T> 对象的类型
   * @param v1 第一个值
   * @param v2 第二个值
   * @param v3 第三个值
   * @return 如果 v1 不为 null，返回 v1；否则如果 v2 不为 null，返回 v2；
   *         否则返回 v3
   */
  @Nullable
  public static <T> T choose(@Nullable final T v1, @Nullable final T v2,
      @Nullable final T v3) {
    if (v1 != null) {
      return v1;
    } else if (v2 != null) {
      return v2;
    } else {
      return v3;
    }
  }

  /**
   * 从多个对象中选择第一个非 null 的值。
   *
   * @param <T> 对象的类型
   * @param values 待选择的值列表
   * @return 第一个非 null 的值；如果所有值都为 null，则返回 null
   */
  @Nullable
  @SafeVarargs
  public static <T> T choose(@Nullable final T... values) {
    if (values == null) {
      return null;
    }
    for (final T value : values) {
      if (value != null) {
        return value;
      }
    }
    return null;
  }
}
