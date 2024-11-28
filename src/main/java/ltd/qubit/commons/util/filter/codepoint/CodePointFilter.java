////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.filter.codepoint;

import ltd.qubit.commons.util.filter.Filter;
import ltd.qubit.commons.util.filter.character.CharFilter;

/**
 * A filter interface provides a function to filter Unicode code points.
 *
 * @author Haixing Hu
 * @see CharFilter
 */
@FunctionalInterface
public interface CodePointFilter extends Filter<Integer> {

  /**
   * Constructs a new filter which accepts all objects that rejected by the
   * specified filter.
   *
   * @param filter
   *     the specified filter.
   * @return
   *     a new filter which accepts all objects that rejected by the specified
   *     filter.
   */
  static CodePointFilter not(final CodePointFilter filter) {
    return (f) -> (!filter.accept(f));
  }

  /**
   * Constructs a new filter which accepts all objects that accepted by both of
   * the specified filters.
   *
   * @param filter1
   *     the first specified filter.
   * @param filter2
   *     the second specified filter.
   * @return
   *     a new filter which accepts all objects that accepted by both of the
   *     specified filters.
   */
  static CodePointFilter and(final CodePointFilter filter1, final CodePointFilter filter2) {
    return (f) -> (filter1.accept(f) && filter2.accept(f));
  }

  /**
   * Constructs a new filter which accepts all objects that accepted by any of
   * the specified filters.
   *
   * @param filter1
   *     the first specified filter.
   * @param filter2
   *     the second specified filter.
   * @return
   *     a new filter which accepts all objects that accepted by any of the
   *     specified filters.
   */
  static CodePointFilter or(final CodePointFilter filter1, final CodePointFilter filter2) {
    return (f) -> (filter1.accept(f) || filter2.accept(f));
  }

  /**
   * Tests if a specified Unicode code point should be accepted.
   *
   * @param codePoint
   *     the Unicode code point to be tested.
   * @return
   *     {@code true} if the specified Unicode code point is accepted by this
   *     filter; {@code false} otherwise.
   */
  boolean accept(Integer codePoint);
}
