////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.reflect.impl;

import java.io.Serial;
import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 方法引用到方法对象的缓存类。
 *
 * <p>该类继承自 {@link ConcurrentHashMap}，用于缓存方法引用与对应的 {@link Method} 对象之间的映射关系，
 * 以提高方法查找的性能。</p>
 *
 * @param <T> 方法所属的类的类型
 * @author 胡海星
 */
public class ReferenceToMethodCache<T> extends ConcurrentHashMap<MethodReference<T>, Method> {

  @Serial
  private static final long serialVersionUID = 8639151465115965085L;

  //  empty
}