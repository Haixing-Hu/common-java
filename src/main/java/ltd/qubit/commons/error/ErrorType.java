////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.error;

/**
 * 此枚举表示错误类型。
 *
 * @author 胡海星
 */
public enum ErrorType {

  /**
   * 请求错误。
   */
  REQUEST_ERROR,

  /**
   * 参数错误。
   */
  PARAMETER_ERROR,

  /**
   * 数据库错误。
   */
  DATABASE_ERROR,

  /**
   * I/O 错误。
   */
  IO_ERROR,

  /**
   * 网络错误。
   */
  NETWORK_ERROR,

  /**
   * 服务器内部错误。
   */
  SERVER_ERROR,

  /**
   * 业务逻辑错误。
   */
  LOGIC_ERROR,

  /**
   * 认证错误。
   */
  AUTHENTICATION_ERROR,

  /**
   * 鉴权错误。
   */
  AUTHORIZATION_ERROR,

  /**
   * 支付错误。
   */
  PAYMENT_ERROR,

  /**
   * 其他第三方错误。
   */
  THIRD_PART_ERROR,
}
