////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.datastructure.list.primitive.adaptor;

import ltd.qubit.commons.datastructure.list.primitive.IntIterator;

import static ltd.qubit.commons.lang.Argument.requireNonNull;

/**
 * An unmodifiable version of {@link IntIterator}.
 *
 * @author Haixing Hu
 */
public class UnmodifiableIntIterator implements IntIterator {

  /**
   * Wraps a {@link IntIterator} as an {@link UnmodifiableIntIterator}.
   *
   * @param iterator
   *     the {@link IntIterator} to be wrap.
   * @return an {@link UnmodifiableIntIterator} wrapping the specified iterator.
   */
  public static UnmodifiableIntIterator wrap(final IntIterator iterator) {
    if (iterator instanceof UnmodifiableIntIterator) {
      return (UnmodifiableIntIterator) iterator;
    } else {
      return new UnmodifiableIntIterator(iterator);
    }
  }

  private final IntIterator iterator;

  protected UnmodifiableIntIterator(final IntIterator iterator) {
    this.iterator = requireNonNull("iterator", iterator);
  }

  @Override
  public boolean hasNext() {
    return iterator.hasNext();
  }

  @Override
  public int next() {
    return iterator.next();
  }

  @Override
  public void remove() {
    throw new UnsupportedOperationException();
  }

}
