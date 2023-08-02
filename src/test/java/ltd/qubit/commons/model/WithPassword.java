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
 * 此接口表示实体类具有密码属性。
 *
 * @author 胡海星
 */
public interface WithPassword {

  /**
   * 获取当前对象的密码。
   *
   * @return
   *     当前对象的密码。
   */
  String getPassword();

  /**
   * 设置当前对象的密码。
   *
   * @param password
   *     待设置的新的密码。
   */
  void setPassword(String password);
}
