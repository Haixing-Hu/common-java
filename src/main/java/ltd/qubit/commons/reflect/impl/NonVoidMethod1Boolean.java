////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.reflect.impl;

/**
 * 此函数接口表示返回值不是void，且只有1个Boolean或boolean类型参数的方法的引用。
 *
 * @param <T>
 *     方法所属的类的类型。
 * @param <R>
 *     方法的返回值的类型。
 * @author Haixing Hu
 */
@FunctionalInterface
public interface NonVoidMethod1Boolean<T, R> extends MethodReference<T> {

  R invoke(T bean, Boolean p1);
}
