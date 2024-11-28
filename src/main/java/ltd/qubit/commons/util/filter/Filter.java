////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.filter;

/**
 * A filter interface provides a function to filter objects of a specified
 * type.
 *
 * @author Haixing Hu
 */
@FunctionalInterface
public interface Filter<T> {

  /**
   * Constructs a new filter which accepts all objects that rejected by the
   * specified filter.
   *
   * @param <F>
   *     the type of objects.
   * @param filter
   *     the specified filter.
   * @return
   *     a new filter which accepts all objects that rejected by the specified
   *     filter.
   */
  static <F> Filter<F> not(final Filter<F> filter) {
    return (f) -> (!filter.accept(f));
  }

  /**
   * Constructs a new filter which accepts all objects that accepted by both of
   * the specified filters.
   *
   * @param <F>
   *     the type of objects.
   * @param filter1
   *     the first specified filter.
   * @param filter2
   *     the second specified filter.
   * @return
   *     a new filter which accepts all objects that accepted by both of the
   *     specified filters.
   */
  static <F> Filter<F> and(final Filter<F> filter1, final Filter<F> filter2) {
    return (f) -> (filter1.accept(f) && filter2.accept(f));
  }

  /**
   * Constructs a new filter which accepts all objects that accepted by any of
   * the specified filters.
   *
   * @param <F>
   *     the type of objects.
   * @param filter1
   *     the first specified filter.
   * @param filter2
   *     the second specified filter.
   * @return
   *     a new filter which accepts all objects that accepted by any of the
   *     specified filters.
   */
  static <F> Filter<F> or(final Filter<F> filter1, final Filter<F> filter2) {
    return (f) -> (filter1.accept(f) || filter2.accept(f));
  }

  /**
   * Tests whether to accept the specified object.
   *
   * @param t
   *     the object to be test.
   * @return true if the object {@code t} is accepted by this filter; false
   *     otherwise.
   */
  boolean accept(T t);
}
