////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.model;

import ltd.qubit.commons.error.ErrorCode;

/**
 * The enumeration of state of entities.
 *
 * @author Haixing Hu
 */
public enum State {

  /**
   * Indicates that the entity is inactive.
   */
  INACTIVE(ErrorCode.INACTIVE),

  /**
   * Indicates that the entity is in the normal state.
   */
  NORMAL(ErrorCode.NONE),

  /**
   * Indicates that the entity is temporarily locked or frozen.
   */
  LOCKED(ErrorCode.LOCKED),

  /**
   * Indicates that the entity is permanently locked, frozen or blocked.
   */
  BLOCKED(ErrorCode.BLOCKED),

  /**
   * Indicates that the entity is obsoleted.
   */
  OBSOLETED(ErrorCode.DISABLED),

  /**
   * Indicates that the entity is disabled.
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
