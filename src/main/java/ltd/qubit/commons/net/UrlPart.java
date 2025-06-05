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
 * URL各部分的枚举。
 *
 * @author 胡海星
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
