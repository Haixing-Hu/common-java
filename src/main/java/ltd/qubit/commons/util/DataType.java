////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util;

/**
 * 此枚举表示常见的数据类型。
 *
 * @author 胡海星
 */
public enum DataType {
  /**
   * 整数
   */
  INTEGER,

  /**
   * 实数
   */
  DECIMAL,

  /**
   * 字符串
   */
  STRING,

  /**
   * 布尔值
   */
  BOOLEAN,

  /**
   * 日期
   */
  DATE,

  /**
   * 时间
   */
  TIME,

  /**
   * 日期时间，精确到秒
   */
  DATETIME,

  /**
   * UTC 时间戳，精确到毫秒
   */
  TIMESTAMP,

  /**
   * 枚举值
   */
  ENUM,
}