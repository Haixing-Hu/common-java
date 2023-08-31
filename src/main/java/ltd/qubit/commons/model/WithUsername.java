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
 * This interface indicates that entity classes have a {@code username} attribute.
 *
 * @author Haixing Hu
 */
public interface WithUsername {

  /**
   * Get the username of the current object.
   *
   * @return
   *     The username of the current object.
   */
  String getUsername();

  /**
   * Sets the username of the current object.
   *
   * @param username
   *     The new username to be set.
   */
  void setUsername(String username);
}
