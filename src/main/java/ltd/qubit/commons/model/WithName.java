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
 * This interface represents an entity class with a name property.
 *
 * @author Haixing Hu
 */
public interface WithName {

  /**
   * Get the name of the current object.
   *
   * @return
   *     The name of the current object.
   */
  String getName();

  /**
   * Set the name of the current object.
   *
   * @param name
   *     The new name to be set.
   */
  void setName(String name);
}
