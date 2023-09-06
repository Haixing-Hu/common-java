////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.model;

import java.time.Instant;

import ltd.qubit.commons.annotation.Computed;

/**
 * This interface indicates that entity classes have basic information with
 * entity categories.
 *
 * @author Haixing Hu
 */
public interface HasInfoWithEntity extends Identifiable, WithEntity, WithInfo<InfoWithEntity> {

  /**
   * Get the basic information of the current object.
   *
   * @return
   *     Basic information of the current object with entity category.
   */
  @Computed({"id", "code", "name", "entity"})
  @Override
  default InfoWithEntity getInfo() {
    final Long id = this.getId();
    final String code = ((this instanceof WithCode) ? ((WithCode) this).getCode() : null);
    final String name = ((this instanceof WithName) ? ((WithName) this).getName() : null);
    final Instant deleteTime = ((this instanceof Deletable) ? ((Deletable) this).getDeleteTime() : null);
    final String entity = this.getEntity();
    return new InfoWithEntity(id, code, name, entity);
  }
}
