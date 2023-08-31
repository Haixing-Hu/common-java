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
 * the state and token properties.
 *
 * @author Haixing Hu
 */
public interface HasStatefulInfoWithToken extends HasStatefulInfo, WithToken {

  /**
   * Get the basic information of the current object.
   *
   * @return
   *     Basic information about the current object.
   */
  @Computed({"id", "code", "name", "state", "token"})
  @Override
  default StatefulInfoWithToken getInfo() {
    return new StatefulInfoWithToken(getId(), getCode(), getName(), getState(), getToken());
  }
}
