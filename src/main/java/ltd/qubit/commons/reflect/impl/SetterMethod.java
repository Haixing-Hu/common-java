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
 * 此接口表示 Java Bean 的 Setter 方法的引用。
 *
 * @param <T>
 *     方法所属的类的类型。
 * @param <P>
 *     方法的参数的类型。
 * @author 胡海星
 */
@FunctionalInterface
public interface SetterMethod<T, P> extends VoidMethod1<T, P> {
  //  empty
}
