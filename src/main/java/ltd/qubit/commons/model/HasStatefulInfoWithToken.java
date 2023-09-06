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
    final Long id = this.getId();
    final String code = ((this instanceof WithCode) ? ((WithCode) this).getCode() : null);
    final String name = ((this instanceof WithName) ? ((WithName) this).getName() : null);
    final Instant deleteTime = ((this instanceof Deletable) ? ((Deletable) this).getDeleteTime() : null);
    final State state = this.getState();
    final Token token = this.getToken();
    return new StatefulInfoWithToken(id, code, name, state, token);
  }
}
