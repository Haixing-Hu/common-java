////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.datastructure.list.primitive.adaptor;

import ltd.qubit.commons.datastructure.list.primitive.CharIterator;
import ltd.qubit.commons.datastructure.list.primitive.CharListIterator;

import static ltd.qubit.commons.lang.Argument.requireNonNull;

/**
 * An unmodifiable version of {@link CharListIterator}.
 *
 * @author Haixing Hu
 */
public class UnmodifiableCharListIterator implements CharListIterator {

  /**
   * Wraps a {@link CharIterator} as an {@link UnmodifiableCharIterator}.
   *
   * @param iterator
   *     the {@link CharIterator} to be wrap.
   * @return an {@link UnmodifiableCharIterator} wrapping the specified iterator.
   */
  public static UnmodifiableCharListIterator wrap(final CharListIterator iterator) {
    if (iterator instanceof UnmodifiableCharListIterator) {
      return (UnmodifiableCharListIterator) iterator;
    } else {
      return new UnmodifiableCharListIterator(iterator);
    }
  }

  private final CharListIterator iterator;

  protected UnmodifiableCharListIterator(final CharListIterator iterator) {
    this.iterator = requireNonNull("iterator", iterator);
  }

  @Override
  public void add(final char element) {
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
  public char next() {
    return iterator.next();
  }

  @Override
  public int nextIndex() {
    return iterator.nextIndex();
  }

  @Override
  public char previous() {
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
  public void set(final char element) {
    throw new UnsupportedOperationException();
  }

}
