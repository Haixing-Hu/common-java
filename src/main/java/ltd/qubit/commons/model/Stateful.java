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
 * This interface indicates that entity classes have state properties.
 *
 * @author Haixing Hu
 */
public interface Stateful {

  /**
   * Get the state of this object.
   *
   * @return
   *     The current state of the object.
   */
  State getState();

  /**
   * Sets the state of this object.
   *
   * @param state
   *     The new state to be set.
   */
  void setState(State state);
}
