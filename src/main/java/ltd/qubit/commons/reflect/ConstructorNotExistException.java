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
 * 抛出异常以指示指定的构造函数不存在。
 *
 * @author 胡海星
 */
public class ConstructorNotExistException extends ReflectionException {

  @Serial
  private static final long serialVersionUID = - 4255851177528512471L;

  /**
   * 构造一个 {@link ConstructorNotExistException} 实例。
   *
   * @param cls
   *     指定的类。
   * @param options
   *     选项。
   */
  public ConstructorNotExistException(final Class<?> cls, final int options) {
    super("There is no "
        + Option.toString(options)
        + " default constructor for the class "
        + cls.getName());
  }

  /**
   * 构造一个 {@link ConstructorNotExistException} 实例。
   *
   * @param cls
   *     指定的类。
   * @param options
   *     选项。
   * @param paramTypes
   *     参数类型。
   */
  public ConstructorNotExistException(final Class<?> cls, final int options,
      final Class<?>[] paramTypes) {
    super("There is no "
        + Option.toString(options)
        + " constructor for the class "
        + cls.getName()
        + " with the parameter types ["
        + new Joiner(',').addAll(paramTypes).toString()
        + "].");
  }

}