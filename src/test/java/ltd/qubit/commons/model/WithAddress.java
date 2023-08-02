////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.model;

import javax.annotation.Nullable;

/**
 * 此接口表示实体类具有地址属性。
 *
 * @author 胡海星
 */
public interface WithAddress {

  /**
   * 获取当前对象的地址。
   *
   * @return
   *     当前对象的地址，或{@code null}如果当前对象没有设置地址。
   */
  @Nullable
  Address getAddress();

  /**
   * 设置当前对象的地址。
   *
   * @param contact
   *     新的地址，可以为{@code null}。
   */
  void setAddress(@Nullable Address contact);
}
