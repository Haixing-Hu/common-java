////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.datastructure.list.primitive.adaptor;

import ltd.qubit.commons.datastructure.list.primitive.LongIterator;
import ltd.qubit.commons.lang.Argument;

/**
 * An unmodifiable version of {@link LongIterator}.
 *
 * @author Haixing Hu
 */
public class UnmodifiableLongIterator implements LongIterator {

  /**
   * Wraps a {@link LongIterator} as an {@link UnmodifiableLongIterator}.
   *
   * @param iterator
   *     the {@link LongIterator} to be wrap.
   * @return an {@link UnmodifiableLongIterator} wrapping the specified iterator.
   */
  public static UnmodifiableLongIterator wrap(final LongIterator iterator) {
    if (iterator instanceof UnmodifiableLongIterator) {
      return (UnmodifiableLongIterator) iterator;
    } else {
      return new UnmodifiableLongIterator(iterator);
    }
  }

  private final LongIterator iterator;

  protected UnmodifiableLongIterator(final LongIterator iterator) {
    this.iterator = Argument.requireNonNull("iterator", iterator);
  }

  @Override
  public boolean hasNext() {
    return iterator.hasNext();
  }

  @Override
  public long next() {
    return iterator.next();
  }

  @Override
  public void remove() {
    throw new UnsupportedOperationException();
  }

}
