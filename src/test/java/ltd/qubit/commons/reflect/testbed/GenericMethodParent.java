////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.reflect.testbed;

public class GenericMethodParent {

  public <T> T foo(final T value) {
    System.out.println("GenericMethodParent::foo - " + value.toString());
    return value;
  }
}
