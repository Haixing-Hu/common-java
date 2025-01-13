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
 * 此枚举表示实体类型的状态。
 *
 * @author 胡海星
 */
@XmlRootElement(name = "state")
public enum State {

  /**
   * 未激活。
   */
  INACTIVE("INACTIVE"),

  /**
   * 正常。
   */
  NORMAL("NONE"),

  /**
   * 锁定/冻结。
   */
  LOCKED("LOCKED"),

  /**
   * 屏蔽/封杀。
   */
  BLOCKED("BLOCKED"),

  /**
   * 已废弃。
   */
  OBSOLETED("DISABLED"),    // FIXME: 是否需要单独的ErrorCode?

  /**
   * 禁用。
   */
  DISABLED("DISABLED");

  private final String errorCode;

  State(final String errorCode) {
    this.errorCode = errorCode;
  }

  public String getErrorCode() {
    return this.errorCode;
  }
}
