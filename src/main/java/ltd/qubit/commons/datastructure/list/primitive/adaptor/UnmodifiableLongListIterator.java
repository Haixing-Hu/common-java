////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.datastructure.list.primitive.adaptor;

import ltd.qubit.commons.datastructure.list.primitive.LongIterator;
import ltd.qubit.commons.datastructure.list.primitive.LongListIterator;

import static ltd.qubit.commons.lang.Argument.requireNonNull;

/**
 * An unmodifiable version of {@link LongListIterator}.
 *
 * @author Haixing Hu
 */
public class UnmodifiableLongListIterator implements LongListIterator {

  /**
   * Wraps a {@link LongIterator} as an {@link UnmodifiableLongIterator}.
   *
   * @param iterator
   *     the {@link LongIterator} to be wrap.
   * @return an {@link UnmodifiableLongIterator} wrapping the specified iterator.
   */
  public static UnmodifiableLongListIterator wrap(final LongListIterator iterator) {
    if (iterator instanceof UnmodifiableLongListIterator) {
      return (UnmodifiableLongListIterator) iterator;
    } else {
      return new UnmodifiableLongListIterator(iterator);
    }
  }

  private final LongListIterator iterator;

  protected UnmodifiableLongListIterator(final LongListIterator iterator) {
    this.iterator = requireNonNull("iterator", iterator);
  }

  @Override
  public void add(final long element) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean hasNext() {
    return iterator.hasNext();
  }

  @Override
  public boolean hasPrevious() {
    return iterator.hasPrevious();
  }

  @Override
  public long next() {
    return iterator.next();
  }

  @Override
  public int nextIndex() {
    return iterator.nextIndex();
  }

  @Override
  public long previous() {
    return iterator.previous();
  }

  @Override
  public int previousIndex() {
    return iterator.previousIndex();
  }

  @Override
  public void remove() {
    throw new UnsupportedOperationException();
  }

  @Override
  public void set(final long element) {
    throw new UnsupportedOperationException();
  }

}
