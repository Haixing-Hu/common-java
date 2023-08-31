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
 * 此接口表示实体类具有安全密钥。
 *
 * @author Haixing Hu
 */
public interface WithSecurityKey {

  /**
   * 获取当前对象的安全密钥。
   *
   * @return
   *     当前对象的安全密钥。
   */
  String getSecurityKey();

  /**
   * 设置当前对象的安全密钥。
   *
   * @param securityKey
   *     待设置的新的安全密钥。
   */
  void setSecurityKey(String securityKey);
}
