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

public class ReferenceToMethodCache<T> extends ConcurrentHashMap<MethodReference<T>, Method> {

  @Serial
  private static final long serialVersionUID = 8639151465115965085L;

  //  empty
}