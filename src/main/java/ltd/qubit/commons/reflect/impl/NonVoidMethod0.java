////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.reflect.impl;

/**
 * 此函数接口表示返回值不是void，且没有参数的方法的引用。
 *
 * @param <T>
 *     方法所属的类的类型。
 * @param <R>
 *     方法的返回值的类型。
 * @author 胡海星
 */
@FunctionalInterface
public interface NonVoidMethod0<T, R> extends MethodReference<T> {

  /**
   * 调用指定对象的方法。
   *
   * @param bean 要调用方法的对象
   * @return 方法调用的返回值
   */
  R invoke(T bean);
}