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

import javax.annotation.Nullable;

import ltd.qubit.commons.annotation.Computed;

/**
 * This interface indicates that the entity class records the mark deletion time.
 *
 * @author Haixing Hu
 */
public interface Deletable {

  /**
   * Get the mark deletion time of the current object.
   *
   * @return
   *     The mark deletion time of the current object; if it is {@code null}, it
   *     means that the current object has not been marked for deletion.
   */
  @Nullable
  Instant getDeleteTime();

  /**
   * Sets the marker deletion time for the current object.
   *
   * @param deleteTime
   *     The new mark deletion time to be set; if it is {@code null}, it means
   *     that the current object has not been marked for deletion.
   */
  void setDeleteTime(@Nullable Instant deleteTime);

  /**
   * Determines whether this object has been marked for deletion.
   *
   * @return
   *     Returns {@code true} if this object has been marked for deletion;
   *     otherwise returns {@code false}.
   */
  @Computed("deleteTime")
  default boolean isDeleted() {
    return getDeleteTime() != null;
  }
}
