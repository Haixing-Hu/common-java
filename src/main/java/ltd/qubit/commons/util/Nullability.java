////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util;

/**
 * The enumeration indicates whether a value is nullable.
 *
 * @author Haixing Hu
 */
public enum Nullability {
  /**
   * Indicates the value is nullable.
   */
  NULLABLE,

  /**
   * Indicates the value cannot be null.
   */
  NOT_NULL,
}