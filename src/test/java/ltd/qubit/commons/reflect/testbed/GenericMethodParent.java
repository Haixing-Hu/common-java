////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
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
