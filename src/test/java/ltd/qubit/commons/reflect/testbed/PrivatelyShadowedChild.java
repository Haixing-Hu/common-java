////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.reflect.testbed;

@SuppressWarnings("unused")
public class PrivatelyShadowedChild extends Parent {
  private final String ss = "ss";
  private final boolean bb = true;
  private final int ii = 1;
  private final double dd = 1.0;

  public void foo() {
    System.out.println("PrivatelyShadowedChild.foo()");
  }
}
