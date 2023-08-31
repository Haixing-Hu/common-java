////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.model;

import javax.annotation.Nullable;

/**
 * This interface indicates that an entity class has properties of the owning entity class.
 *
 * @author Haixing Hu
 */
public interface WithEntity {

  /**
   * Get the name of the entity class to which the current object belongs.
   *
   * @return
   *     The name of the entity class to which the current object belongs.
   */
  @Nullable
  String getEntity();

  /**
   * Set the name of the entity class to which the current object belongs.
   *
   * @param entity
   *     The name of the new entity class.
   */
  void setEntity(@Nullable String entity);

}
