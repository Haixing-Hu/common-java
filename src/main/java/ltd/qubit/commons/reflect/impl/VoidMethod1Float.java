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
 * 此函数接口表示返回值是void，且只有1个`float`参数的方法的引用。
 *
 * @param <T>
 *     方法所属的类的类型。
 * @author 胡海星
 */
@FunctionalInterface
public interface VoidMethod1Float<T> extends MethodReference<T>  {

  void invoke(T bean, Float p1);
}