////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.testbed.model;

/**
 * 此枚举表示年收入档次。
 *
 * @author 胡海星
 */
public enum Incoming {

  /**
   * 年收入 2万5千元以下。
   */
  ANNUAL_25K_BELOW,

  /**
   * 年收入 2万5千元 ～ 5万元。
   */
  ANNUAL_25K_50K,

  /**
   * 年收入 5万元 ～ 10万元。
   */
  ANNUAL_50K_100K,

  /**
   * 年收入 10万元 ～ 15万元。
   */
  ANNUAL_100K_150K,

  /**
   * 年收入 15万元 ～ 20万元。
   */
  ANNUAL_150K_200K,

  /**
   * 年收入 20万元 ～ 30万元。
   */
  ANNUAL_200K_300K,

  /**
   * 年收入 30万元 ～ 40万元。
   */
  ANNUAL_300K_400K,

  /**
   * 年收入 40万元 ～ 50万元。
   */
  ANNUAL_400K_500K,

  /**
   * 年收入 50万元 ～ 80万元。
   */
  ANNUAL_500K_800K,

  /**
   * 年收入 80万元 ～ 100万元。
   */
  ANNUAL_800K_1000K,

  /**
   * 年收入 100万元 ～ 500万元。
   */
  ANNUAL_1000K_5000K,

  /**
   * 年收入 500万元 ～ 1000 万元。
   */
  ANNUAL_5000K_10000K,

  /**
   * 年收入 1000万元 以上。
   */
  ANNUAL_10000K_ABOVE,
}