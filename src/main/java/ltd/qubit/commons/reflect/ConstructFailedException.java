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
 * 抛出异常以指示构造失败。
 *
 * @author 胡海星
 */
public class ConstructFailedException extends ReflectionException {

  @Serial
  private static final long serialVersionUID = - 4255851177528512471L;

  /**
   * 构造一个 {@link ConstructFailedException} 实例。
   *
   * @param cls
   *     指定的类。
   */
  public ConstructFailedException(final Class<?> cls) {
    super("Failed to construct " + cls.getName());
  }

  /**
   * 构造一个 {@link ConstructFailedException} 实例。
   *
   * @param cls
   *     指定的类。
   * @param cause
   *     导致构造失败的原因。
   */
  public ConstructFailedException(final Class<?> cls, final Throwable cause) {
    super("Failed to construct " + cls.getName(), cause);
  }

  /**
   * 构造一个 {@link ConstructFailedException} 实例。
   *
   * @param cls
   *     指定的类。
   * @param paramTypes
   *     参数类型。
   */
  public ConstructFailedException(final Class<?> cls, final Class<?>[] paramTypes) {
    super("Failed to construct "
        + cls.getName()
        + " with parameter types ["
        + new Joiner(',').addAll(paramTypes).toString()
        + "].");
  }

  /**
   * 构造一个 {@link ConstructFailedException} 实例。
   *
   * @param cls
   *     指定的类。
   * @param paramTypes
   *     参数类型。
   * @param cause
   *     导致构造失败的原因。
   */
  public ConstructFailedException(final Class<?> cls, final Class<?>[] paramTypes,
      final Throwable cause) {
    super("Failed to construct "
        + cls.getName()
        + " with parameter types ["
        + new Joiner(',').addAll(paramTypes).toString()
        + "].", cause);
  }
}