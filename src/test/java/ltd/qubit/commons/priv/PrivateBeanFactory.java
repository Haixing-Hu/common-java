////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.priv;

/**
 * Factory class for PrivateBean instances.
 *
 * @author Haixing Hu
 */
public class PrivateBeanFactory {

  /**
   * Factory method to create new beans.
   */
  public static PrivateDirect create() {
    return (new PrivateBean());
  }

  /**
   * Factory method to create new beans.
   */
  public static PrivateDirect createSubclass() {
    return (new PrivateBeanSubclass());
  }

}
