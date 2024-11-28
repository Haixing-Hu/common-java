////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.datastructure.list.primitive.adaptor;

import ltd.qubit.commons.datastructure.list.primitive.IntIterator;
import ltd.qubit.commons.datastructure.list.primitive.IntListIterator;
import ltd.qubit.commons.lang.Argument;

/**
 * An unmodifiable version of {@link IntListIterator}.
 *
 * @author Haixing Hu
 */
public class UnmodifiableIntListIterator implements IntListIterator {

  /**
   * Wraps a {@link IntIterator} as an {@link UnmodifiableIntIterator}.
   *
   * @param iterator
   *     the {@link IntIterator} to be wrap.
   * @return an {@link UnmodifiableIntIterator} wrapping the specified iterator.
   */
  public static UnmodifiableIntListIterator wrap(final IntListIterator iterator) {
    if (iterator instanceof UnmodifiableIntListIterator) {
      return (UnmodifiableIntListIterator) iterator;
    } else {
      return new UnmodifiableIntListIterator(iterator);
    }
  }

  private final IntListIterator iterator;

  protected UnmodifiableIntListIterator(final IntListIterator iterator) {
    this.iterator = Argument.requireNonNull("iterator", iterator);
  }

  @Override
  public void add(final int element) {
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
  public int next() {
    return iterator.next();
  }

  @Override
  public int nextIndex() {
    return iterator.nextIndex();
  }

  @Override
  public int previous() {
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
  public void set(final int element) {
    throw new UnsupportedOperationException();
  }

}
