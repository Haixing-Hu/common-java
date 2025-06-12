////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.reflect;

import java.io.Serial;

import ltd.qubit.commons.text.Joiner;

/**
 * 此异常表示指定的方法不存在。
 *
 * @author 胡海星
 */
public class MethodNotExistException extends ReflectionException {

  @Serial
  private static final long serialVersionUID = 7457691421536998975L;

  /**
   * 构造一个 {@link MethodNotExistException}。
   *
   * @param cls
   *     类
   * @param options
   *     选项
   * @param name
   *     方法名称
   * @param paramTypes
   *     参数类型
   */
  public MethodNotExistException(final Class<?> cls, final int options,
      final String name, final Class<?>[] paramTypes) {
    super("There is no "
        + Option.toString(options)
        + " method named with '"
        + name
        + "' for the class "
        + cls.getName()
        + " with the parameter types ["
        + new Joiner(',').addAll(paramTypes).toString()
        + "].");
  }
}