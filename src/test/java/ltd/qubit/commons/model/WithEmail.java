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
 * 此接口表示实体类具有电子邮件地址属性。
 *
 * @author 胡海星
 */
public interface WithEmail {

  /**
   * 获取当前对象的电子邮件地址。
   *
   * @return
   *     当前对象的电子邮件地址。
   */
  String getEmail();

  /**
   * 设置当前对象的电子邮件地址。
   *
   * @param email
   *     待设置的新的电子邮件地址。
   */
  void setEmail(String email);
}
