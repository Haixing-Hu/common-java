////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
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
