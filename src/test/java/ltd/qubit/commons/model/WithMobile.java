////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.model;

/**
 * 此接口表示实体类具有手机号码属性。
 *
 * @author 胡海星
 */
public interface WithMobile {

  /**
   * 获取当前对象的手机号码。
   *
   * @return
   *     当前对象的手机号码。
   */
  Phone getMobile();

  /**
   * 设置当前对象的手机号码。
   *
   * @param mobile
   *     待设置的新的手机号码。
   */
  void setMobile(Phone mobile);
}
