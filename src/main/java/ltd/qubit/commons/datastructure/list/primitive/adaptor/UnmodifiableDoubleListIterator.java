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
import ltd.qubit.commons.datastructure.list.primitive.DoubleListIterator;

import static ltd.qubit.commons.lang.Argument.requireNonNull;

/**
 * An unmodifiable version of {@link DoubleListIterator}.
 *
 * @author Haixing Hu
 */
public class UnmodifiableDoubleListIterator implements DoubleListIterator {

  /**
   * Wraps a {@link DoubleIterator} as an {@link UnmodifiableDoubleIterator}.
   *
   * @param iterator
   *     the {@link DoubleIterator} to be wrap.
   * @return an {@link UnmodifiableDoubleIterator} wrapping the specified iterator.
   */
  public static UnmodifiableDoubleListIterator wrap(final DoubleListIterator iterator) {
    if (iterator instanceof UnmodifiableDoubleListIterator) {
      return (UnmodifiableDoubleListIterator) iterator;
    } else {
      return new UnmodifiableDoubleListIterator(iterator);
    }
  }

  private final DoubleListIterator iterator;

  protected UnmodifiableDoubleListIterator(final DoubleListIterator iterator) {
    this.iterator = requireNonNull("iterator", iterator);
  }

  @Override
  public void add(final double element) {
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
  public double next() {
    return iterator.next();
  }

  @Override
  public int nextIndex() {
    return iterator.nextIndex();
  }

  @Override
  public double previous() {
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
  public void set(final double element) {
    throw new UnsupportedOperationException();
  }

}
