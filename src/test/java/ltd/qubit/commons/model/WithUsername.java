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
 * 此接口表示实体类具有{@code username}属性。
 *
 * @author 胡海星
 */
public interface WithUsername {

  /**
   * 获取当前对象的用户名。
   *
   * @return
   *     当前对象的用户名。
   */
  String getUsername();

  /**
   * 设置当前对象的用户名。
   *
   * @param username
   *     待设置的新的用户名。
   */
  void setUsername(String username);
}
