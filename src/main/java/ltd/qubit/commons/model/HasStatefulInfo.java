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
 * This interface represents that an entity class has the basic information with
 * the state property.
 *
 * @author Haixing Hu
 */
public interface HasStatefulInfo extends Identifiable, Stateful, WithInfo<StatefulInfo> {

  /**
   * Get the basic information of this object.
   *
   * @return
   *     Basic information of this object.
   */
  @Computed({"id", "code", "name", "state"})
  @Override
  default StatefulInfo getInfo() {
    final Long id = this.getId();
    final String code = ((this instanceof WithCode) ? ((WithCode) this).getCode() : null);
    final String name = ((this instanceof WithName) ? ((WithName) this).getName() : null);
    final Instant deleteTime = ((this instanceof Deletable) ? ((Deletable) this).getDeleteTime() : null);
    final State state = this.getState();
    return new StatefulInfo(id, code, name, state);
  }
}
