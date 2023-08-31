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
 * This interface represents that an entity class has the basic information with
 * the state property.
 *
 * @author Haixing Hu
 */
public interface HasStatefulInfo extends HasInfo, Stateful {

  /**
   * Get the basic information of this object.
   *
   * @return
   *     Basic information of this object.
   */
  @Computed({"id", "code", "name", "state"})
  @Override
  default StatefulInfo getInfo() {
    return new StatefulInfo(getId(), getCode(), getName(), getState());
  }
}
