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

/**
 * This interface indicates that the entity class records the last modification
 * time.
 *
 * @author Haixing Hu
 */
public interface Modifiable {

  /**
   * Get the last modification time of this object.
   *
   * @return
   *     The last modification time of this object; a {@code null} value means
   *     that this object has not been modified.
   */
  @Nullable
  Instant getModifyTime();

  /**
   * Sets the last modification time of this object.
   *
   * @param modifyTime
   *     The new last modification time to be set; a {@code null} value means
   *     that this object has not been modified.
   */
  void setModifyTime(@Nullable Instant modifyTime);

}
