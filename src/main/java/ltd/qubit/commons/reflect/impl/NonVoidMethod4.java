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
 * 此函数接口表示返回值不是void，且只有4个参数的方法的引用。
 *
 * @param <T>
 *     方法所属的类的类型。
 * @param <R>
 *     方法的返回值的类型。
 * @param <P1>
 *     方法的第1个参数的类型。
 * @param <P2>
 *     方法的第2个参数的类型。
 * @param <P3>
 *     方法的第3个参数的类型。
 * @param <P4>
 *     方法的第4个参数的类型。
 * @author 胡海星
 */
@FunctionalInterface
public interface NonVoidMethod4<T, R, P1, P2, P3, P4> extends MethodReference<T> {

  /**
   * 调用指定对象的方法。
   *
   * @param bean 要调用方法的对象
   * @param p1 方法的第1个参数
   * @param p2 方法的第2个参数
   * @param p3 方法的第3个参数
   * @param p4 方法的第4个参数
   * @return 方法调用的返回值
   */
  R invoke(T bean, P1 p1, P2 p2, P3 p3, P4 p4);
}