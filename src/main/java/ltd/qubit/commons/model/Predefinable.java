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
 * This interface indicates that models can be predefined.
 *
 * @author Haixing Hu
 */
public interface Predefinable {

  /**
   * Determine whether this object is predefined.
   *
   * @return
   *     Whether this object is predefined.
   */
  Boolean isPredefined();

  /**
   * Set whether this object is predefined.
   *
   * @param predefined
   *     Whether this object is predefined.
   */
  void setPredefined(final Boolean predefined);
}
