////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.filter.character;

import ltd.qubit.commons.util.filter.Filter;
import ltd.qubit.commons.util.filter.codepoint.CodePointFilter;

/**
 * A interface for filter characters.
 *
 * @author Haixing Hu
 * @see CodePointFilter
 */
@FunctionalInterface
public interface CharFilter extends Filter<Character> {

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
  static CharFilter not(final CharFilter filter) {
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
  static CharFilter and(final CharFilter filter1, final CharFilter filter2) {
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
  static CharFilter or(final CharFilter filter1, final CharFilter filter2) {
    return (f) -> (filter1.accept(f) || filter2.accept(f));
  }

  /**
   * Tests if a specified character should be accepted.
   *
   * @param ch
   *     the character to be tested.
   * @return
   *     {@code true} if the specified character is accepted by this filter;
   *     {@code false} otherwise.
   */
  boolean accept(Character ch);
}
