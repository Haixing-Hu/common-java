////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
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
