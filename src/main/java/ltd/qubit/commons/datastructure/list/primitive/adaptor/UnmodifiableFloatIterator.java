////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.datastructure.list.primitive.adaptor;

import ltd.qubit.commons.datastructure.list.primitive.FloatIterator;
import ltd.qubit.commons.lang.Argument;

/**
 * An unmodifiable version of {@link FloatIterator}.
 *
 * @author Haixing Hu
 */
public class UnmodifiableFloatIterator implements FloatIterator {

  /**
   * Wraps a {@link FloatIterator} as an {@link UnmodifiableFloatIterator}.
   *
   * @param iterator
   *     the {@link FloatIterator} to be wrap.
   * @return an {@link UnmodifiableFloatIterator} wrapping the specified iterator.
   */
  public static UnmodifiableFloatIterator wrap(final FloatIterator iterator) {
    if (iterator instanceof UnmodifiableFloatIterator) {
      return (UnmodifiableFloatIterator) iterator;
    } else {
      return new UnmodifiableFloatIterator(iterator);
    }
  }

  private final FloatIterator iterator;

  protected UnmodifiableFloatIterator(final FloatIterator iterator) {
    this.iterator = Argument.requireNonNull("iterator", iterator);
  }

  @Override
  public boolean hasNext() {
    return iterator.hasNext();
  }

  @Override
  public float next() {
    return iterator.next();
  }

  @Override
  public void remove() {
    throw new UnsupportedOperationException();
  }

}
