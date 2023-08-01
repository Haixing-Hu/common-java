////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.reflect;

/**
 * Thrown to indicate the specified member is ambiguous.
 *
 * @author Haixing Hu
 */
public class AmbiguousMemberException extends ReflectionException {

  private static final long serialVersionUID = - 7385799996559327654L;

  public AmbiguousMemberException(final Class<?> cls, final String name) {
    super("The specified member '" + name
        + "' for the class " + cls.getName() + " is ambiguous.");
  }
}
