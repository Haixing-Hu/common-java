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
 * This interface indicates that an entity class has an owner (another entity object) property.
 *
 * @author Haixing Hu
 */
public interface WithOwner {

  /**
   * Get basic information about the owner of the current object.
   *
   * @return
   *     The basic information of the owner of the current object, including
   *     entity type and entity ID.
   */
  Owner getOwner();

  /**
   * Set the owner of the current object.
   *
   * @param owner
   *     Basic information about the new owner, including entity type and entity
   *     ID.
   */
  void setOwner(Owner owner);
}
