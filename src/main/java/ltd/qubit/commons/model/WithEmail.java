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
 * This interface represents an entity class with an email address property.
 *
 * @author Haixing Hu
 */
public interface WithEmail {

  /**
   * Get the email address of this object.
   *
   * @return
   *     The email address of this object.
   */
  String getEmail();

  /**
   * Sets the email address of this object.
   *
   * @param email
   *     The new email address to be set up.
   */
  void setEmail(String email);
}
