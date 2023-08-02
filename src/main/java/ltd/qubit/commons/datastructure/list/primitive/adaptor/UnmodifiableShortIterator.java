////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.datastructure.list.primitive.adaptor;

import ltd.qubit.commons.datastructure.list.primitive.ShortIterator;
import ltd.qubit.commons.lang.Argument;

/**
 * An unmodifiable version of {@link ShortIterator}.
 *
 * @author Haixing Hu
 */
public class UnmodifiableShortIterator implements ShortIterator {

  /**
   * Wraps a {@link ShortIterator} as an {@link UnmodifiableShortIterator}.
   *
   * @param iterator
   *     the {@link ShortIterator} to be wrap.
   * @return an {@link UnmodifiableShortIterator} wrapping the specified iterator.
   */
  public static UnmodifiableShortIterator wrap(final ShortIterator iterator) {
    if (iterator instanceof UnmodifiableShortIterator) {
      return (UnmodifiableShortIterator) iterator;
    } else {
      return new UnmodifiableShortIterator(iterator);
    }
  }

  private final ShortIterator iterator;

  protected UnmodifiableShortIterator(final ShortIterator iterator) {
    this.iterator = Argument.requireNonNull("iterator", iterator);
  }

  @Override
  public boolean hasNext() {
    return iterator.hasNext();
  }

  @Override
  public short next() {
    return iterator.next();
  }

  @Override
  public void remove() {
    throw new UnsupportedOperationException();
  }

}
