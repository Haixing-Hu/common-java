////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.datastructure.list.primitive.adaptor;

import ltd.qubit.commons.datastructure.list.primitive.ByteIterator;
import ltd.qubit.commons.lang.Argument;

/**
 * An unmodifiable version of {@link ByteIterator}.
 *
 * @author Haixing Hu
 */
public class UnmodifiableByteIterator implements ByteIterator {

  /**
   * Wraps a {@link ByteIterator} as an {@link UnmodifiableByteIterator}.
   *
   * @param iterator
   *     the {@link ByteIterator} to be wrap.
   * @return an {@link UnmodifiableByteIterator} wrapping the specified iterator.
   */
  public static UnmodifiableByteIterator wrap(final ByteIterator iterator) {
    if (iterator instanceof UnmodifiableByteIterator) {
      return (UnmodifiableByteIterator) iterator;
    } else {
      return new UnmodifiableByteIterator(iterator);
    }
  }

  private final ByteIterator iterator;

  protected UnmodifiableByteIterator(final ByteIterator iterator) {
    this.iterator = Argument.requireNonNull("iterator", iterator);
  }

  @Override
  public boolean hasNext() {
    return iterator.hasNext();
  }

  @Override
  public byte next() {
    return iterator.next();
  }

  @Override
  public void remove() {
    throw new UnsupportedOperationException();
  }

}
