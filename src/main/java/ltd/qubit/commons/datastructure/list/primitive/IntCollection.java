////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.datastructure.list.primitive;

import java.io.Serializable;

/**
 * {@code int} 值的集合。
 *
 * @author 胡海星
 */
public interface IntCollection extends Comparable<IntCollection>, Serializable {

  /**
   * Ensures that I contain the specified element (optional operation). Returns
   * {@code true} iff I changed as a result of this call.
   *
   * <p>If a collection refuses to add the specified element for any reason
   * other than that it already contains the element, it <i>must</i> throw an
   * exception (rather than simply returning {@code false}). This preserves the
   * invariant that a collection always contains the specified element after
   * this call returns.
   *
   * @param element
   *     the value whose presence within me is to be ensured
   * @return {@code true} iff I changed as a result of this call
   * @throws UnsupportedOperationException
   *     when this operation is not supported
   * @throws IllegalArgumentException
   *     may be thrown if some aspect of the specified element prevents it from
   *     being added to me
   */
  boolean add(int element);

  /**
   * {@link #add Adds} all of the elements in the specified collection to me
   * (optional operation).
   *
   * @param c
   *     the collection of elements whose presence within me is to be ensured
   * @return {@code true} iff I changed as a result of this call
   * @throws UnsupportedOperationException
   *     when this operation is not supported
   * @throws IllegalArgumentException
   *     may be thrown if some aspect of some specified element prevents it from
   *     being added to me
   */
  boolean addAll(IntCollection c);

  /**
   * Removes all my elements (optional operation). I will be {@link #isEmpty
   * empty} after this method successfully returns.
   *
   * @throws UnsupportedOperationException
   *     when this operation is not supported
   */
  void clear();

  /**
   * Returns {@code true} iff I contain the specified element.
   *
   * @param element
   *     the value whose presence within me is to be tested
   * @return {@code true} iff I contain the specified element
   */
  boolean contains(int element);

  /**
   * Returns {@code true} iff I {@link #contains contain} all of the elements in
   * the given collection.
   *
   * @param c
   *     the collection of elements whose presence within me is to be tested
   * @return {@code true} iff I contain the all the specified elements
   */
  boolean containsAll(IntCollection c);

  /**
   * Returns {@code true} iff I contain no elements.
   *
   * @return {@code true} iff I contain no elements.
   */
  boolean isEmpty();

  /**
   * Returns an {@link IntIterator iterator} over all my elements. This base
   * interface places no constraints on the order in which the elements are
   * returned by the returned iterator.
   *
   * @return an {@link IntIterator iterator} over all my elements.
   */
  IntIterator iterator();

  /**
   * Removes all of my elements that are contained in the specified collection
   * (optional operation). The behavior of this method is unspecified if the
   * given collection is modified while this method is executing. Note that this
   * includes the case in which the given collection is this collection, and it
   * is not empty.
   *
   * @param c
   *     the collection of elements to remove
   * @return {@code true} iff I contained the at least one of the specified
   *     elements, in other words, returns {@code true} iff I changed as a
   *     result of this call
   * @throws UnsupportedOperationException
   *     when this operation is not supported
   */
  boolean removeAll(IntCollection c);

  /**
   * Removes a single occurrence of the specified element (optional operation).
   *
   * @param element
   *     the element to remove, if present
   * @return {@code true} iff I contained the specified element, in other words,
   *     iff I changed as a result of this call
   * @throws UnsupportedOperationException
   *     when this operation is not supported
   */
  boolean remove(int element);

  /**
   * Removes all of my elements that are <i>not</i> contained in the specified
   * collection (optional operation). (In other words, retains <i>only</i> my
   * elements that are contained in the specified collection.) The behavior of
   * this method is unspecified if the given collection is modified while this
   * method is executing.
   *
   * @param c
   *     the collection of elements to retain
   * @return {@code true} iff I changed as a result of this call
   * @throws UnsupportedOperationException
   *     when this operation is not supported
   */
  boolean retainAll(IntCollection c);

  /**
   * Returns the number of elements I contain.
   *
   * @return the number of elements I contain
   */
  int size();

  /**
   * Returns an array containing all of my elements. The length of the returned
   * array will be equal to my {@link #size size}.
   *
   * <p>The returned array will be independent of me, so that callers may modify
   * that returned array without modifying this collection.
   *
   * <p>When I guarantee the order in which elements are returned by an {@link
   * #iterator iterator}, the returned array will contain elements in the same
   * order.
   *
   * @return an array containing all my elements
   */
  int[] toArray();

  /**
   * Returns an array containing all of my elements, using the given array if it
   * is large enough. When the length of the given array is larger than the
   * number of elements I contain, values outside of my range will be
   * unchanged.
   *
   * <p>The returned array will be independent of me, so that callers may modify
   * that returned array without modifying this collection.
   *
   * <p>When I guarantee the order in which elements are returned by an {@link
   * #iterator iterator}, the returned array will contain elements in the same
   * order.
   *
   * @param a
   *     an array that may be used to contain the elements
   * @return an array containing all my elements
   */
  int[] toArray(int[] a);
}