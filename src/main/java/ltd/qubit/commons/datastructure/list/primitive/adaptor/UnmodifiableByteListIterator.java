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
import ltd.qubit.commons.datastructure.list.primitive.ByteListIterator;

import static ltd.qubit.commons.lang.Argument.requireNonNull;

/**
 * An unmodifiable version of {@link ByteListIterator}.
 *
 * @author Haixing Hu
 */
public class UnmodifiableByteListIterator implements ByteListIterator {

  /**
   * Wraps a {@link ByteIterator} as an {@link UnmodifiableByteIterator}.
   *
   * @param iterator
   *     the {@link ByteIterator} to be wrap.
   * @return an {@link UnmodifiableByteIterator} wrapping the specified iterator.
   */
  public static UnmodifiableByteListIterator wrap(final ByteListIterator iterator) {
    if (iterator instanceof UnmodifiableByteListIterator) {
      return (UnmodifiableByteListIterator) iterator;
    } else {
      return new UnmodifiableByteListIterator(iterator);
    }
  }

  private final ByteListIterator iterator;

  protected UnmodifiableByteListIterator(final ByteListIterator iterator) {
    this.iterator = requireNonNull("iterator", iterator);
  }

  @Override
  public void add(final byte element) {
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
  public byte next() {
    return iterator.next();
  }

  @Override
  public int nextIndex() {
    return iterator.nextIndex();
  }

  @Override
  public byte previous() {
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
  public void set(final byte element) {
    throw new UnsupportedOperationException();
  }

}
