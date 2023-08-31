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
 * This interface represents an entity class with a security key.
 *
 * @author Haixing Hu
 */
public interface WithSecurityKey {

  /**
   * Get the security key for the current object.
   *
   * @return
   *     The security key for the current object.
   */
  String getSecurityKey();

  /**
   * Sets the security key for the current object.
   *
   * @param securityKey
   *     The new security key to be provisioned.
   */
  void setSecurityKey(String securityKey);
}
