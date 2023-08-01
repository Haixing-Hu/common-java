////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.reflect.impl;

/**
 * 此函数接口表示返回值是void，且只有2个参数的方法的引用。
 *
 * @param <T>
 *     方法所属的类的类型。
 * @param <P1>
 *     方法的第1个参数的类型。
 * @param <P2>
 *     方法的第2个参数的类型。
 * @param <P3>
 *     方法的第3个参数的类型。
 * @param <P4>
 *     方法的第4个参数的类型。
 * @param <P5>
 *     方法的第5个参数的类型。
 * @param <P6>
 *     方法的第6个参数的类型。
 * @author 胡海星
 */
@FunctionalInterface
public interface VoidMethod6<T, P1, P2, P3, P4, P5, P6> extends MethodReference<T>  {

  void invoke(T bean, P1 p1, P2 p2, P3 p3, P4 p4, P5 p5, P6 p6);
}
