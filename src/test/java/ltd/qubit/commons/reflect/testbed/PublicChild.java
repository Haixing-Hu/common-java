////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.reflect.testbed;

public class PublicChild extends Parent {

  // deliberate reuse of variable name
  static final String VALUE = "child";

  public void foo() {
    System.out.println("PublicChild.foo()");
  }
}
