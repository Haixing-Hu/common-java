////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.reflect.impl;

import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;

public class ReferenceToMethodCache<T> extends ConcurrentHashMap<MethodReference<T>, Method> {

  private static final long serialVersionUID = 8639151465115965085L;

  //  empty
}
