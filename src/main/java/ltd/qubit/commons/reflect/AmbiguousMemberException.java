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
 * Thrown to indicate the specified member is ambiguous.
 *
 * @author Haixing Hu
 */
public class AmbiguousMemberException extends ReflectionException {

  @Serial
  private static final long serialVersionUID = - 7385799996559327654L;

  public AmbiguousMemberException(final Class<?> cls, final String name) {
    super("The specified member '" + name
        + "' for the class " + cls.getName() + " is ambiguous.");
  }
}