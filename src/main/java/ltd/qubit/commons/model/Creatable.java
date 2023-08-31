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

/**
 * This interface indicates that the entity class records the creation time.
 *
 * @author Haixing Hu
 */
public interface Creatable {
  /**
   * Get the creation time of the current object.
   *
   * @return
   *     The creation time of the previous object, which should never be {@code null}.
   */
  Instant getCreateTime();

  /**
   * Sets the creation time of the current object.
   *
   * @param createTime
   *     The new creation time to be set, cannot be {@code null}.
   */
  void setCreateTime(Instant createTime);
}
