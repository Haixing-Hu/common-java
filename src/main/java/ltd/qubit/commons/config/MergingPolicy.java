////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.config;

/**
 * The enumeration of the policies used when merging configurations.
 *
 * @author Haixing Hu
 */
public enum MergingPolicy {

  /**
   * Skip all the existing property, no matter whether it is final or not.
   */
  SKIP,

  /**
   * Union the values of the existing property, or skip it if it is final. If
   * the existing property has a different type and it is not final, overwrite
   * it with new value.
   */
  UNION,

  /**
   * Overwrite the existing property, or skip it if it is final.
   */
  OVERWRITE,
}
