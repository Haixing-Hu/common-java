////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.filter;

/**
 * The constant values of filter actions returned by a filter.
 *
 * @author Haixing Hu
 */
public final class FilterAction {

  /**
   * No action suggestions to the chained filter containing this filter.
   */
  public static final int NONE     = 0x0000;

  /**
   * Suggests the chained filter containing this filter to continue to the next
   * chained filter.
   */
  public static final int CONTINUE = 0x0100;

  /**
   * Suggests the chained filter containing this filter to break the current
   * chained loop and return immediately.
   */
  public static final int BREAK    = 0x0200;

  /**
   * The bitwise mask of the filter action values.
   */
  public static final int MASK     = NONE | CONTINUE | BREAK;
}
