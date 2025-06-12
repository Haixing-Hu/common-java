////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.reflect;

import java.io.IOException;

/**
 * 用于写入字符串的方法引用。
 *
 * @param <T>
 *     调用方法的对象类型。
 */
@FunctionalInterface
public interface WriteMethodReference<T> {

  /**
   * 调用此方法。
   *
   * @param obj
   *     调用方法的对象。
   * @param value
   *     要写入的字符串。
   * @throws IOException
   *     如果发生IO异常。
   */
  void invoke(T obj, String value) throws IOException;
}