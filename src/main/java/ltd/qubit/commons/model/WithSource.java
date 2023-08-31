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
 * This interface indicates that an entity class whose instance belongs to a
 * certain source.
 *
 * @author Haixing Hu
 */
public interface WithSource {

  /**
   * Get basic information about the source to which this object belongs.
   *
   * @return
   *     Basic information about the source to which this object belongs.
   */
  @Nullable
  Info getSource();

  /**
   * Set the basic information of the source to which this object belongs.
   *
   * @param source
   *     Basic information about the new source to which this object belongs.
   */
  void setSource(@Nullable Info source);
}
