////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.testbed.priv;

/**
 * Bean that exposes methods defined by an interface that is implemented in the
 * superclass.
 *
 * @author Haixing Hu
 */
class PrivateBeanSubclass extends PrivateBean {

  /**
   * Create a new PrivateBeanSubclass instance.
   */
  PrivateBeanSubclass() {
  }

  /**
   * A property accessible via the superclass.
   */
  @Override
  public String getBar() {
    return (super.getBar());
  }
}