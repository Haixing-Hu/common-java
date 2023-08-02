////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.datastructure.list.primitive.adaptor;

import ltd.qubit.commons.datastructure.list.primitive.BooleanIterator;
import ltd.qubit.commons.datastructure.list.primitive.BooleanListIterator;
import ltd.qubit.commons.lang.Argument;

/**
 * An unmodifiable version of {@link BooleanListIterator}.
 *
 * @author Haixing Hu
 */
public final class UnmodifiableBooleanListIterator implements BooleanListIterator {

  /**
   * Wraps a {@link BooleanIterator} as an {@link UnmodifiableBooleanIterator}.
   *
   * @param iterator
   *     the {@link BooleanIterator} to be wrap.
   * @return an {@link UnmodifiableBooleanIterator} wrapping the specified iterator.
   */
  public static UnmodifiableBooleanListIterator wrap(final BooleanListIterator iterator) {
    if (iterator instanceof UnmodifiableBooleanListIterator) {
      return (UnmodifiableBooleanListIterator) iterator;
    } else {
      return new UnmodifiableBooleanListIterator(iterator);
    }
  }

  private final BooleanListIterator iterator;

  protected UnmodifiableBooleanListIterator(final BooleanListIterator iterator) {
    this.iterator = Argument.requireNonNull("iterator", iterator);
  }

  @Override
  public void add(final boolean element) {
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
  public boolean next() {
    return iterator.next();
  }

  @Override
  public int nextIndex() {
    return iterator.nextIndex();
  }

  @Override
  public boolean previous() {
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
  public void set(final boolean element) {
    throw new UnsupportedOperationException();
  }

}
