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
 * Thrown to indicate the specified constructor does not exist.
 *
 * @author Haixing Hu
 */
public class ConstructorNotExistException extends ReflectionException {

  @Serial
  private static final long serialVersionUID = - 4255851177528512471L;

  public ConstructorNotExistException(final Class<?> cls, final int options) {
    super("There is no "
        + Option.toString(options)
        + " default constructor for the class "
        + cls.getName());
  }

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