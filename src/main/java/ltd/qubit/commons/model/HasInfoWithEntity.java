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
 * This interface indicates that entity classes have basic information with
 * entity categories.
 *
 * @author Haixing Hu
 */
public interface HasInfoWithEntity extends HasInfo, WithEntity {

  /**
   * Get the basic information of the current object.
   *
   * @return
   *     Basic information of the current object with entity category.
   */
  @Computed({"id", "code", "name", "entity"})
  @Override
  default InfoWithEntity getInfo() {
    return new InfoWithEntity(getId(), getCode(), getName(), getEntity());
  }
}
