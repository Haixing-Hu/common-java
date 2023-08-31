////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.reflect.testbed;

import ltd.qubit.commons.annotation.Computed;

/**
 * 此接口表示实体类具有基本信息。
 *
 * @author Haixing Hu
 */
public interface WithInfo extends Identifiable, WithCode, WithName {

  @Computed({"id", "code", "name"})
  default Info getInfo() {
    return new Info(getId(), getCode(), getName());
  }
}
