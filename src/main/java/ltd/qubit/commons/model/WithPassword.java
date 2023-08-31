////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.model;

/**
 * This interface indicates that an entity class has a password property.
 *
 * @author Haixing Hu
 */
public interface WithPassword {

  /**
   * Get the password for the current object.
   *
   * @return
   *     The password for the current object.
   */
  String getPassword();

  /**
   * Sets the password for the current object.
   *
   * @param password
   *     The new password to be set.
   */
  void setPassword(String password);
}
