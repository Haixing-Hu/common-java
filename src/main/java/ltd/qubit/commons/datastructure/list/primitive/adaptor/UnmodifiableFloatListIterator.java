////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.datastructure.list.primitive.adaptor;

import ltd.qubit.commons.datastructure.list.primitive.FloatIterator;
import ltd.qubit.commons.datastructure.list.primitive.FloatListIterator;
import ltd.qubit.commons.lang.Argument;

/**
 * An unmodifiable version of {@link FloatListIterator}.
 *
 * @author Haixing Hu
 */
public class UnmodifiableFloatListIterator implements FloatListIterator {

  /**
   * Wraps a {@link FloatIterator} as an {@link UnmodifiableFloatIterator}.
   *
   * @param iterator
   *     the {@link FloatIterator} to be wrap.
   * @return an {@link UnmodifiableFloatIterator} wrapping the specified iterator.
   */
  public static UnmodifiableFloatListIterator wrap(final FloatListIterator iterator) {
    if (iterator instanceof UnmodifiableFloatListIterator) {
      return (UnmodifiableFloatListIterator) iterator;
    } else {
      return new UnmodifiableFloatListIterator(iterator);
    }
  }

  private final FloatListIterator iterator;

  protected UnmodifiableFloatListIterator(final FloatListIterator iterator) {
    this.iterator = Argument.requireNonNull("iterator", iterator);
  }

  @Override
  public void add(final float element) {
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
  public float next() {
    return iterator.next();
  }

  @Override
  public int nextIndex() {
    return iterator.nextIndex();
  }

  @Override
  public float previous() {
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
  public void set(final float element) {
    throw new UnsupportedOperationException();
  }

}
