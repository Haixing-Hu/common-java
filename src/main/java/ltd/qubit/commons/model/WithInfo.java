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
 * This interface represents the entity class with basic information.
 *
 * @param <T>
 *     the type of the basic information.
 * @author Haixing Hu
 */
public interface WithInfo<T> {

  /**
   * Get the basic information of this object.
   *
   * @return
   *     Basic information about this object.
   */
  T getInfo();
}
