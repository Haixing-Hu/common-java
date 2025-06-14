////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.reflect.testbed;

import java.io.OutputStream;

/**
 * Class used in MethodUtils test.
 *
 * @author Haixing Hu
 */
public class A {

  boolean called = false;

  public void foo(final OutputStream os) {
    called = true;
  }

  public void sayHello() {
    System.out.println("Hello from A");
  }
}