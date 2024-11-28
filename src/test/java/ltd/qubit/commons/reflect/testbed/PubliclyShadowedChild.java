////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.reflect.testbed;

// deliberate re-use of variable names
public class PubliclyShadowedChild extends Parent {

  public String ss = "ss";
  public boolean bb = true;
  public int ii = 1;
  public double dd = 1.0;

  public void foo() {
    System.out.println("PubliclyShadowedChild.foo()");
  }
}
