////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.datastructure.list.primitive.adaptor;

import ltd.qubit.commons.datastructure.list.primitive.DoubleIterator;
import ltd.qubit.commons.lang.Argument;

/**
 * An unmodifiable version of {@link DoubleIterator}.
 *
 * @author Haixing Hu
 */
public class UnmodifiableDoubleIterator implements DoubleIterator {

  /**
   * Wraps a {@link DoubleIterator} as an {@link UnmodifiableDoubleIterator}.
   *
   * @param iterator
   *     the {@link DoubleIterator} to be wrap.
   * @return an {@link UnmodifiableDoubleIterator} wrapping the specified iterator.
   */
  public static UnmodifiableDoubleIterator wrap(final DoubleIterator iterator) {
    if (iterator instanceof UnmodifiableDoubleIterator) {
      return (UnmodifiableDoubleIterator) iterator;
    } else {
      return new UnmodifiableDoubleIterator(iterator);
    }
  }

  private final DoubleIterator iterator;

  protected UnmodifiableDoubleIterator(final DoubleIterator iterator) {
    this.iterator = Argument.requireNonNull("iterator", iterator);
  }

  @Override
  public boolean hasNext() {
    return iterator.hasNext();
  }

  @Override
  public double next() {
    return iterator.next();
  }

  @Override
  public void remove() {
    throw new UnsupportedOperationException();
  }

}
