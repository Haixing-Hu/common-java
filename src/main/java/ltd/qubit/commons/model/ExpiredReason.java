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
 * This enumeration represents the expiration reason.
 *
 * @author Haixing Hu
 */
public enum ExpiredReason {

  /**
   * User logged out.
   */
  LOGOUT,

  /**
   * time out.
   */
  TIMEOUT,

  /**
   * Single-session login, that is, the user logs in from another location and
   * the current session ends.
   */
  SINGLE_SESSION,

  /**
   * system maintenance.
   */
  MAINTENANCE,

  /**
   * None, that is, the session is not expired.
   */
  NONE;

  public String id() {
    return name().toLowerCase();
  }
}
