////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.datastructure.list.primitive;

/**
 * An ordered collection of {@code byte} values.
 *
 * @author Haixing Hu
 */
public interface ByteList extends ByteCollection {

  /**
   * Appends the specified element to the end of me (optional operation).
   * Returns {@code true} iff I changed as a result of this call.
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
  @Override
  boolean add(byte element);

  /**
   * Inserts the specified element at the specified position (optional
   * operation). Shifts the element currently at that position (if any) and any
   * subsequent elements to the right, increasing their indices.
   *
   * @param index
   *     the index at which to insert the element
   * @param element
   *     the value to insert
   * @throws UnsupportedOperationException
   *     when this operation is not supported
   * @throws IllegalArgumentException
   *     if some aspect of the specified element prevents it from being added to
   *     me
   * @throws IndexOutOfBoundsException
   *     if the specified index is out of range
   */
  void add(int index, byte element);

  /**
   * Inserts all of the elements in the specified collection into me, at the
   * specified position (optional operation).
   *
   * <p>Shifts the element currently at that position (if any) and any
   * subsequent elements to the right, increasing their indices. The new
   * elements will appear in the order that they are returned by the given
   * collection's {@link ByteCollection#iterator iterator}.
   *
   * @param index
   *     the index at which to insert the first element from the specified
   *     collection
   * @param collection
   *     the {@link ByteCollection ByteCollection} of elements to add
   * @return {@code true} iff I changed as a result of this call
   * @throws UnsupportedOperationException
   *     when this operation is not supported
   * @throws IndexOutOfBoundsException
   *     if the specified index is out of range
   */
  boolean addAll(int index, ByteCollection collection);

  /**
   * Returns {@code true} iff <i>that</i> is an {@link ByteList} that contains
   * the same elements in the same order as me.
   *
   * <p>In other words, returns {@code true} iff <i>that</i> is a
   * {@code ByteList} that has the same {@link #size() size} as me, and for
   * which the elements returned by its {@link ByteList#iterator iterator} are
   * equal ({@code ==}) to the corresponding elements within me. (This contract
   * ensures that this method works properly across different implementations of
   * the {@code ByteList} interface.)
   *
   * @param that
   *     the object to compare to me
   * @return {@code true} iff <i>that</i> is an {@code ByteList} that contains
   *     the same elements in the same order as me
   */
  @Override
  boolean equals(Object that);

  /**
   * Returns the value of the element at the specified position within me.
   *
   * @param index
   *     the index of the element to return
   * @return the value of the element at the specified position
   * @throws IndexOutOfBoundsException
   *     if the specified index is out of range
   */
  byte get(int index);

  /**
   * Returns my hash code.
   *
   * <p>The hash code of an {@code ByteList} is defined to be the result of the
   * following calculation:
   *
   * <pre>
   * int hash = 1;
   * for (ByteIterator iter = iterator(); iter.hasNext();) {
   *   byte value = iter.next();
   *   hash = 31 * hash + (int) (value &circ; (value &gt;&gt;&gt; 32));
   * }
   * </pre>
   *
   * <p>This contract ensures that this method is consistent with {@link #equals
   * equals} and with the {@link java.util.List#hashCode hashCode} method of a
   * {@link java.util.List List} of {@link Byte}s.
   *
   * @return my hash code
   */
  @Override
  int hashCode();

  /**
   * Returns the current of the first occurrence of the specified element within
   * me, or {@code -1} if I do not contain the element.
   *
   * @param element
   *     the element to search for
   * @return the smallest current of an element matching the specified value, or
   *     {@code -1} if no such matching element can be found
   */
  int indexOf(byte element);

  /**
   * Returns an {@link ByteIterator iterator} over all elements, in the
   * appropriate sequence.
   *
   * @return an {@link ByteIterator iterator} over all elements.
   */
  @Override
  ByteIterator iterator();

  /**
   * Returns the current of the last occurrence of the specified element within
   * me, or -1 if I do not contain the element.
   *
   * @param element
   *     the element to search for
   * @return the largest current of an element matching the specified value, or
   *     {@code -1} if no such matching element can be found
   */
  int lastIndexOf(byte element);

  /**
   * Returns a {@link ByteListIterator bidirectional iterator} over all my
   * elements, in the appropriate sequence.
   *
   * @return an {@link ByteListIterator iterator} over all elements.
   */
  ByteListIterator listIterator();

  /**
   * Returns a {@link ByteListIterator bidirectional iterator} over all my
   * elements, in the appropriate sequence, starting at the specified position.
   * The specified <i>current</i> indicates the first element that would be
   * returned by an initial call to the {@link ByteListIterator#next next}
   * method. An initial call to the {@link ByteListIterator#previous previous}
   * method would return the element with the specified <i>current</i> minus
   * one.
   *
   * @param index
   *     the specified index.
   * @return the specified {@link ByteListIterator iterator}.
   * @throws IndexOutOfBoundsException
   *     if the specified index is out of range
   */
  ByteListIterator listIterator(int index);

  /**
   * Removes the element at the specified position in (optional operation). Any
   * subsequent elements are shifted to the left, subtracting one from their
   * indices. Returns the element that was removed.
   *
   * @param index
   *     the index of the element to remove
   * @return the value of the element that was removed
   * @throws UnsupportedOperationException
   *     when this operation is not supported
   * @throws IndexOutOfBoundsException
   *     if the specified index is out of range
   */
  byte removeAt(int index);

  /**
   * Replaces the element at the specified position in me with the specified
   * element (optional operation).
   *
   * @param index
   *     the index of the element to change
   * @param element
   *     the value to be stored at the specified position
   * @return the value previously stored at the specified position
   * @throws UnsupportedOperationException
   *     when this operation is not supported
   * @throws IndexOutOfBoundsException
   *     if the specified index is out of range
   */
  byte set(int index, byte element);

  /**
   * Returns a view of the elements within me between the specified
   * <i>fromIndex</i>, inclusive, and <i>toIndex</i>, exclusive. The returned
   * {@code ByteList} is backed by me, so that any changes in the returned list
   * are reflected in me, and vice-versa. The returned list supports all of the
   * optional operations that I support.
   *
   * <p>Note that when <code><i>fromIndex</i> == <i>toIndex</i></code>, the
   * returned list is initially empty, and when{@code fromIndex == 0} &amp;&amp;
   * {@code toIndex} == {@link #size() size()} the returned list is my
   * "improper" sublist, containing all my elements.
   *
   * <p>The semantics of the returned list become undefined if I am structurally
   * modified in any way other than via the returned list.
   *
   * @param fromIndex
   *     the smallest current (inclusive) in me that appears in the returned
   *     list
   * @param toIndex
   *     the largest current (exclusive) in me that appears in the returned
   *     list
   * @return a view of this list from <i>fromIndex</i> (inclusive) to
   *     <i>toIndex</i> (exclusive)
   * @throws IndexOutOfBoundsException
   *     if either specified current is out of range
   */
  ByteList subList(int fromIndex, int toIndex);

  /**
   * Sorts the list into ascending numerical order.
   */
  void sort();

  /**
   * Unique the values in this sorted list.
   *
   * <p>The list is assumed to be sorted, i.e., the same values should be
   * successively placed.</p>
   */
  void unique();
}
