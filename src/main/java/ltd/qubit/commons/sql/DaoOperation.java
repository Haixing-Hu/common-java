////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.sql;

/**
 * The enumeration of DAO operations.
 *
 * @author Haixing Hu
 */
public enum DaoOperation {

  /**
   * Indicates an add or update operation.
   */
  ADD_OR_UPDATE,

  /**
   * Indicates a deletion operation.
   */
  DELETE,

  /**
   * Indicates an unknown operation.
   */
  UNKNOWN,
}
