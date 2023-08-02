////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.datastructure.list.primitive.adaptor;

import ltd.qubit.commons.datastructure.list.primitive.CharIterator;
import ltd.qubit.commons.lang.Argument;

/**
 * An unmodifiable version of {@link CharIterator}.
 *
 * @author Haixing Hu
 */
public class UnmodifiableCharIterator implements CharIterator {

  /**
   * Wraps a {@link CharIterator} as an {@link UnmodifiableCharIterator}.
   *
   * @param iterator
   *     the {@link CharIterator} to be wrap.
   * @return an {@link UnmodifiableCharIterator} wrapping the specified iterator.
   */
  public static UnmodifiableCharIterator wrap(final CharIterator iterator) {
    if (iterator instanceof UnmodifiableCharIterator) {
      return (UnmodifiableCharIterator) iterator;
    } else {
      return new UnmodifiableCharIterator(iterator);
    }
  }

  private final CharIterator iterator;

  protected UnmodifiableCharIterator(final CharIterator iterator) {
    this.iterator = Argument.requireNonNull("iterator", iterator);
  }

  @Override
  public boolean hasNext() {
    return iterator.hasNext();
  }

  @Override
  public char next() {
    return iterator.next();
  }

  @Override
  public void remove() {
    throw new UnsupportedOperationException();
  }

}
