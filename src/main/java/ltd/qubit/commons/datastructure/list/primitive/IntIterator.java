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
 * An iterator over {@code int} values.
 *
 * @author Haixing Hu
 */
public interface IntIterator {

  /**
   * Returns {@code true} iff I have more elements.
   *
   * <p>In other words, returns {@code true} iff a subsequent call to
   * {@link #next} will return an element rather than throwing an exception.
   *
   * @return {@code true} iff I have more elements
   */
  boolean hasNext();

  /**
   * Returns the next element in me.
   *
   * @return the next element in me
   * @throws java.util.NoSuchElementException
   *     if there is no next element
   */
  int next();

  /**
   * Removes from my underlying collection the last element {@link #next
   * returned} by me (optional operation).
   *
   * @throws UnsupportedOperationException
   *     if this operation is not supported
   * @throws IllegalStateException
   *     if {@link #next} has not yet been called, or {@link #remove} has
   *     already been called since the last call to {@link #next}.
   */
  void remove();
}
