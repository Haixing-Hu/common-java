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
 * 此接口表示实体类具有名称属性。
 *
 * @author 胡海星
 */
public interface WithName {

  /**
   * 获取当前对象的名称。
   *
   * @return
   *     当前对象的名称。
   */
  String getName();

  /**
   * 设置当前对象的名称。
   *
   * @param name
   *     待设置的新的名称。
   */
  void setName(String name);
}
