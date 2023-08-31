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
 * This interface represents the entity class with basic information.
 *
 * @author Haixing Hu
 */
public interface HasInfo extends Identifiable, WithCode, WithName {

  /**
   * Get the basic information of this object.
   *
   * @return
   *     Basic information about this object.
   */
  @Computed({"id", "code", "name"})
  default Info getInfo() {
    return new Info(getId(), getCode(), getName());
  }
}
