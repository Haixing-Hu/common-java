////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.model;

/**
 * This interface indicates that an entity class has a visibility property.
 *
 * @author Haixing Hu
 */
public interface WithVisibility {

  /**
   * Determines whether the object is visible.
   *
   * @return
   *     Whether the object is visible.
   */
  Boolean isVisible();

  /**
   * Sets the visibility of this object.
   *
   * @param visible
   *     Whether the object is visible.
   */
  void setVisible(Boolean visible);
}
