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
 * 此接口表示实体类具有所有者（另一个实体对象）属性。
 *
 * @author 胡海星
 */
public interface WithOwner {

  /**
   * 获取当前对象的所有者的基本信息。
   *
   * @return
   *     当前对象的所有者的基本信息，包括实体类型和实体ID。
   */
  Owner getOwner();

  /**
   * 设置当前对象的所有者。
   *
   * @param owner
   *     新的所有者的基本信息，包括实体类型和实体ID。
   */
  void setOwner(Owner owner);
}