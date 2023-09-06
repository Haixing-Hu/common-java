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
 * This interface represents the entity class with basic information.
 *
 * @author Haixing Hu
 */
public interface HasInfo extends Identifiable, WithInfo<Info> {

  /**
   * Get the basic information of this object.
   *
   * @return
   *     Basic information about this object.
   */
  @Computed({"id", "code", "name"})
  @Override
  default Info getInfo() {
    final Long id = this.getId();
    final String code = ((this instanceof WithCode) ? ((WithCode) this).getCode() : null);
    final String name = ((this instanceof WithName) ? ((WithName) this).getName() : null);
    final Instant deleteTime = ((this instanceof Deletable) ? ((Deletable) this).getDeleteTime() : null);
    return new Info(id, code, name, deleteTime);
  }
}
