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

import ltd.qubit.commons.annotation.Computed;

/**
 * This interface represents a model with expirable info properties.
 *
 * @author Haixing Hu
 */
public interface Expirable {

  /**
   * Get expiration information for this object.
   *
   * @return
   *     Expiration information for this object, or {@code null} if it has not
   *     expired.
   */
  @Nullable
  Expired getExpired();

  /**
   * Sets the expiration information for this object.
   *
   * @param expired
   *     Expiration information for this object, or {@code null} if it has not
   *     expired.
   */
  void setExpired(@Nullable Expired expired);

  /**
   * Determines whether this object has expired.
   *
   * @return
   *     Whether this object has expired.
   */
  @Computed("expiredTime")
  default boolean hasExpired() {
    return getExpired() != null;
  }
}
