////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.testbed;

import ltd.qubit.commons.annotation.Computed;

/**
 * 此接口表示实体类具有带状态的基本信息。
 *
 * @author 胡海星
 */
public interface WithStatefulInfo extends WithInfo, Stateful {

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