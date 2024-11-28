////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.datastructure.list.primitive.adaptor;

import ltd.qubit.commons.datastructure.list.primitive.ShortIterator;
import ltd.qubit.commons.datastructure.list.primitive.ShortListIterator;
import ltd.qubit.commons.lang.Argument;

/**
 * An unmodifiable version of {@link ShortListIterator}.
 *
 * @author Haixing Hu
 */
public class UnmodifiableShortListIterator implements ShortListIterator {

  /**
   * Wraps a {@link ShortIterator} as an {@link UnmodifiableShortIterator}.
   *
   * @param iterator
   *     the {@link ShortIterator} to be wrap.
   * @return an {@link UnmodifiableShortIterator} wrapping the specified iterator.
   */
  public static UnmodifiableShortListIterator wrap(final ShortListIterator iterator) {
    if (iterator instanceof UnmodifiableShortListIterator) {
      return (UnmodifiableShortListIterator) iterator;
    } else {
      return new UnmodifiableShortListIterator(iterator);
    }
  }

  private final ShortListIterator iterator;

  protected UnmodifiableShortListIterator(final ShortListIterator iterator) {
    this.iterator = Argument.requireNonNull("iterator", iterator);
  }

  @Override
  public void add(final short element) {
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
  public short next() {
    return iterator.next();
  }

  @Override
  public int nextIndex() {
    return iterator.nextIndex();
  }

  @Override
  public short previous() {
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
  public void set(final short element) {
    throw new UnsupportedOperationException();
  }

}
