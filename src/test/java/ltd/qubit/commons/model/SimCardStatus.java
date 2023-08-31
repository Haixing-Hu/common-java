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
 * 此枚举表示SIM卡的状态。
 *
 * @author Haixing Hu
 */
public enum SimCardStatus {

  UNKNOWN,
  ABSENT,
  PIN_REQUIRED,
  PUK_REQUIRED,
  NETWORK_LOCKED,
  READY,
  NOT_READY,
  PERM_DISABLED,
  CARD_IO_ERROR,
  CARD_RESTRICTED,
}
