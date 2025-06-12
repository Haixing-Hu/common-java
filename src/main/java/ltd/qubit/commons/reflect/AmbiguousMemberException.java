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

/**
 * 抛出异常以指示指定的成员是模糊的。
 *
 * @author 胡海星
 */
public class AmbiguousMemberException extends ReflectionException {

  @Serial
  private static final long serialVersionUID = - 7385799996559327654L;

  /**
   * 构造一个 {@link AmbiguousMemberException} 实例。
   *
   * @param cls
   *     指定的类。
   * @param name
   *     指定的成员名称。
   */
  public AmbiguousMemberException(final Class<?> cls, final String name) {
    super("The specified member '" + name
        + "' for the class " + cls.getName() + " is ambiguous.");
  }
}