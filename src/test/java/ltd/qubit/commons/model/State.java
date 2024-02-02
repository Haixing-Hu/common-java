////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.model;

import jakarta.xml.bind.annotation.XmlRootElement;

/**
 * 此枚举表示实体类型的状态。
 *
 * @author Haixing Hu
 */
@XmlRootElement(name = "state")
public enum State {

  /**
   * 未激活。
   */
  INACTIVE(ErrorCode.INACTIVE),

  /**
   * 正常。
   */
  NORMAL(ErrorCode.NONE),

  /**
   * 锁定/冻结。
   */
  LOCKED(ErrorCode.LOCKED),

  /**
   * 屏蔽/封杀。
   */
  BLOCKED(ErrorCode.BLOCKED),

  /**
   * 已废弃。
   */
  OBSOLETED(ErrorCode.DISABLED),    // FIXME: 是否需要单独的ErrorCode?

  /**
   * 禁用。
   */
  DISABLED(ErrorCode.DISABLED);

  private final ErrorCode errorCode;

  State(final ErrorCode errorCode) {
    this.errorCode = errorCode;
  }

  public ErrorCode getErrorCode() {
    return this.errorCode;
  }
}
