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
 * 此接口表示实体类具有基本信息。
 *
 * @author Haixing Hu
 */
public interface HasInfo extends Identifiable, WithCode, WithName {

  /**
   * 获取当前对象的基本信息。
   *
   * @return
   *     当前对象的基本信息。
   */
  @Computed({"id", "code", "name"})
  default Info getInfo() {
    return new Info(getId(), getCode(), getName());
  }
}
