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
 * Thrown to indicate the specified method does not exist.
 *
 * @author Haixing Hu
 */
public class MethodNotExistException extends ReflectionException {

  @Serial
  private static final long serialVersionUID = 7457691421536998975L;

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