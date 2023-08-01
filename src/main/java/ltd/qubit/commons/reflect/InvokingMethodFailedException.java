////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.reflect;

import java.lang.reflect.Method;

import ltd.qubit.commons.text.Joiner;

/**
 * Thrown to indicate the invoking of a method failed.
 *
 * @author Haixing Hu
 */
public class InvokingMethodFailedException extends ReflectionException {

  private static final long serialVersionUID = 7457691421536998975L;

  public InvokingMethodFailedException(final Class<?> cls, final int options,
      final String name, final Class<?>[] paramTypes, final Throwable cause) {
    super("Invoking the "
        + Option.toString(options)
        + " method named with '"
        + name
        + "' for the class "
        + cls.getName()
        + " with the parameter types ["
        + new Joiner(',').addAll(paramTypes).toString()
        + "] failed.", cause);
  }

  public InvokingMethodFailedException(final Object object, final Method method,
      final Object[] arguments, final Throwable cause) {
    super("Invoking the method '"
        + method.getName()
        + "' on the object "
        + object.toString()
        + " with the arguments ["
        + new Joiner(',').addAll(arguments).toString()
        + "] failed.", cause);
  }
}
