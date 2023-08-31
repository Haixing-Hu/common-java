////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.model;

import java.io.Serializable;

/**
 * This interface indicates that an entity class has a unique identifier.
 *
 * @author Haixing Hu
 */
public interface Identifiable extends Serializable {

  /**
   * Get the unique identifier of this object.
   *
   * @return
   *     A unique identifier for this object.
   */
  Long getId();

  /**
   * Sets the unique identifier of this object.
   *
   * @param id
   *     The new unique identifier to be set.
   */
  void setId(Long id);
}
