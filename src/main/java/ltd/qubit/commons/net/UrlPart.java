////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.net;

import ltd.qubit.commons.lang.EnumUtils;

/**
 * Enumeration of the parts of an URL.
 *
 * @author Haixing Hu
 */
public enum UrlPart {

  URL,
  SCHEME,
  USER_INFO,
  HOSTNAME,
  DOMAIN,
  PORT,
  PATH,
  QUERY,
  FRAGMENT;

  @Override
  public String toString() {
    return EnumUtils.getShortName(this);
  }
}
