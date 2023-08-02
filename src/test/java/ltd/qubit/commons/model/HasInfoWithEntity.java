////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.model;

import ltd.qubit.commons.annotation.Computed;

/**
 * 此接口表示实体类具有带entity类别的基本信息。
 *
 * @author 胡海星
 */
public interface HasInfoWithEntity extends HasInfo, WithEntity {

  /**
   * 获取当前对象的基本信息。
   *
   * @return
   *     当前对象的带entity类别的基本信息。
   */
  @Computed({"id", "code", "name", "entity"})
  @Override
  default InfoWithEntity getInfo() {
    return new InfoWithEntity(getId(), getCode(), getName(), getEntity());
  }
}
