////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.reflect.testbed;

public abstract class AbstractParent {

  private Child child;

  public Child getChild() {
    return child;
  }

  /**
   * Method which matches signature but which has wrong parameters.
   */
  public String testAddChild(final String badParameter) {
    return null;
  }

  public String testAddChild(final Child child) {
    this.child = child;
    return child.getName();
  }

  /**
   * Method which matches signature but which has wrong parameters.
   */
  public String testAddChild2(final String ignore, final String badParameter) {
    return null;
  }

  public String testAddChild2(final String ignore, final Child child) {
    this.child = child;
    return child.getName();
  }

}
