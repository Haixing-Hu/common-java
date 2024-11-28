////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.priv;

/**
 * This class is designed to test the default access jvm problem workaround. The
 * issue is that public methods of a public subclass contained in a default
 * access superclass are returned by reflection but an IllegalAccessException is
 * thrown when they are invoked.
 *
 * <p>This is the default access superclass
 *
 * @author Haixing Hu
 */
class PackageBean {

  /**
   * Package private constructor - can only use factory method to create beans.
   */
  PackageBean() {
  }

  private String bar = "This is bar";

  public String getBar() {
    return (this.bar);
  }

  public void setBar(final String bar) {
    this.bar = bar;
  }
}
