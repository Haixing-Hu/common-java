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

  /**
   * 完整的URL。
   */
  URL,

  /**
   * 方案。
   */
  SCHEME,

  /**
   * 用户信息。
   */
  USER_INFO,

  /**
   * 主机名。
   */
  HOSTNAME,

  /**
   * 域名。
   */
  DOMAIN,

  /**
   * 端口。
   */
  PORT,
  
  /**
   * 路径。
   */
  PATH,

  /**
   * 查询。
   */
  QUERY,

  /**
   * 片段。
   */
  FRAGMENT;

  @Override
  public String toString() {
    return EnumUtils.getShortName(this);
  }
}
