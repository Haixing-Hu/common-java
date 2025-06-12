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
import java.lang.reflect.Method;

import ltd.qubit.commons.text.Joiner;

/**
 * 此异常表示调用方法失败。
 *
 * @author 胡海星
 */
public class InvokingMethodFailedException extends ReflectionException {

  @Serial
  private static final long serialVersionUID = 7457691421536998975L;

  /**
   * 构造一个 {@link InvokingMethodFailedException}。
   *
   * @param cls
   *     类
   * @param options
   *     选项
   * @param name
   *     方法名称
   * @param paramTypes
   *     参数类型
   * @param cause
   *     原因
   */
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

  /**
   * 构造一个 {@link InvokingMethodFailedException}。
   *
   * @param object
   *     对象
   * @param method
   *     方法
   * @param arguments
   *     参数
   * @param cause
   *     原因
   */
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