////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.reflect;

import java.lang.reflect.AccessibleObject;

@FunctionalInterface
public interface AccessibleObjectFunction<T extends AccessibleObject, R> {

  R access(T object) throws IllegalArgumentException, IllegalAccessException;

}
