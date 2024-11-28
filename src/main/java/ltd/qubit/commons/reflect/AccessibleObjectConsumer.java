////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.reflect;

import java.lang.reflect.AccessibleObject;

@FunctionalInterface
public interface AccessibleObjectConsumer<T extends AccessibleObject> {

  void access(T object) throws IllegalArgumentException, IllegalAccessException;

}
