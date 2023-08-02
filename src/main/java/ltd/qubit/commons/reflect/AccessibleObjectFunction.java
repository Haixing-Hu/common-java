////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
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
