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
 * 此接口表示实体类具有带状态的基本信息。
 *
 * @author Haixing Hu
 */
@SuppressWarnings("serial")
public interface HasStatefulInfo extends HasInfo, Stateful {

  /**
   * 获取当前对象的基本信息。
   *
   * @return
   *     当前对象的基本信息。
   */
  @Computed({"id", "code", "name", "state"})
  @Override
  default StatefulInfo getInfo() {
    return new StatefulInfo(getId(), getCode(), getName(), getState());
  }
}
