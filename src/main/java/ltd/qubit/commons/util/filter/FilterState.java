////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.filter;

/**
 * The constant values of filter results returned by a filter.
 *
 * @author Haixing Hu
 */
public final class FilterState {

  /**
   * Means whether the object is accepted or rejected is unknown.
   */
  public static final int UNKNOWN = 0x0000;

  /**
   * Means the object is accepted.
   */
  public static final int ACCEPT  = 0x0001;

  /**
   * Means the object is rejected.
   */
  public static final int REJECT  = 0x0002;

  /**
   * The bitwise mask of the filter result values.
   */
  public static final int MASK    = UNKNOWN | ACCEPT | REJECT;

}