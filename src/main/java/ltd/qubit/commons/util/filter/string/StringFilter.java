////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.filter.string;

import ltd.qubit.commons.util.filter.Filter;

/**
 * The interface of a string filter.
 *
 * @author Haixing Hu
 */
@FunctionalInterface
public interface StringFilter extends Filter<String> {

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
  static StringFilter not(final StringFilter filter) {
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
  static StringFilter and(final StringFilter filter1, final StringFilter filter2) {
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
  static StringFilter or(final StringFilter filter1, final StringFilter filter2) {
    return (f) -> (filter1.accept(f) || filter2.accept(f));
  }

  /**
   * Tests whether to accept the specified object.
   *
   * @param str
   *     the string to be tested.
   * @return
   *     {@code true} if the String {@code str} is accepted by this filter;
   *     {@code false} otherwise.
   */
  boolean accept(String str);
}
