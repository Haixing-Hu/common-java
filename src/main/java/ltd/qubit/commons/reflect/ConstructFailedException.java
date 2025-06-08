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
 * Thrown to indicate the construction failed.
 *
 * @author Haixing Hu
 */
public class ConstructFailedException extends ReflectionException {

  @Serial
  private static final long serialVersionUID = - 4255851177528512471L;

  public ConstructFailedException(final Class<?> cls) {
    super("Failed to construct " + cls.getName());
  }

  public ConstructFailedException(final Class<?> cls, final Throwable cause) {
    super("Failed to construct " + cls.getName(), cause);
  }

  public ConstructFailedException(final Class<?> cls, final Class<?>[] paramTypes) {
    super("Failed to construct "
        + cls.getName()
        + " with parameter types ["
        + new Joiner(',').addAll(paramTypes).toString()
        + "].");
  }

  public ConstructFailedException(final Class<?> cls, final Class<?>[] paramTypes,
      final Throwable cause) {
    super("Failed to construct "
        + cls.getName()
        + " with parameter types ["
        + new Joiner(',').addAll(paramTypes).toString()
        + "].", cause);
  }
}