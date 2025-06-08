////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.testbed.model;

import jakarta.xml.bind.annotation.XmlRootElement;

/**
 * 此枚举表示操作系统平台。
 *
 * @author 胡海星
 */
@XmlRootElement(name = "platform")
public enum Platform {

  /**
   * 苹果 iOS 系统。
   */
  IOS,

  /**
   * 苹果 iPadOS 系统。
   */
  IPAD_OS,

  /**
   * 谷歌 Android 系统。
   */
  ANDROID,

  /**
   * 微软 Windows Phone 系统。
   */
  WINDOWS_PHONE,

  /**
   * 微软 Windows 桌面系统。
   */
  WINDOWS,

  /**
   * Linux 桌面系统。
   */
  LINUX,

  /**
   * Mac 桌面系统。
   */
  MAC,

  /**
   * Web 页面。
   */
  WEB,

  /**
   * 未知操作系统。
   */
  UNKNOWN
}