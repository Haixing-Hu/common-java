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
 * This interface represents an entity class with a UUID attribute.
 *
 * @author Haixing Hu
 */
public interface WithUuid {

  /**
   * Get the UUID of the current object.
   *
   * @return
   *     当前对象的UUID。
   */
  String getUuid();

  /**
   * Set the UUID of the current object.
   *
   * @param uuid
   *     The new UUID to be set.
   */
  void setUuid(String uuid);
}
