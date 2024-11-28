////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.reflect.testbed;

import java.lang.reflect.Array;

public class GenericMethod extends GenericMethodParent {

  @SuppressWarnings("unchecked")
  public <T> T[] createArray(final Class<T> componentType, final int size) {
    return (T[]) Array.newInstance(componentType, size);
  }

  public <E> E foo(final E value) {
    System.out.println("GenericMethod::foo - " + value.toString());
    return value;
  }

  public <E extends Enum<E>> E foo(final E value) {
    System.out.println("GenericMethod::foo [enum] - " + value.toString());
    return value;
  }
}
