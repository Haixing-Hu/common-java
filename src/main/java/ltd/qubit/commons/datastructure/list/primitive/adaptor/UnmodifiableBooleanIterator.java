////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.datastructure.list.primitive.adaptor;

import ltd.qubit.commons.datastructure.list.primitive.BooleanIterator;

import static ltd.qubit.commons.lang.Argument.requireNonNull;

/**
 * An unmodifiable version of {@link BooleanIterator}.
 *
 * @author Haixing Hu
 */
public final class UnmodifiableBooleanIterator implements BooleanIterator {

  /**
   * Wraps a {@link BooleanIterator} as an {@link UnmodifiableBooleanIterator}.
   *
   * @param iterator
   *     the {@link BooleanIterator} to be wrap.
   * @return an {@link UnmodifiableBooleanIterator} wrapping the specified iterator.
   */
  public static UnmodifiableBooleanIterator wrap(final BooleanIterator iterator) {
    if (iterator instanceof UnmodifiableBooleanIterator) {
      return (UnmodifiableBooleanIterator) iterator;
    } else {
      return new UnmodifiableBooleanIterator(iterator);
    }
  }

  private final BooleanIterator iterator;

  protected UnmodifiableBooleanIterator(final BooleanIterator iterator) {
    this.iterator = requireNonNull("iterator", iterator);
  }

  @Override
  public boolean hasNext() {
    return iterator.hasNext();
  }

  @Override
  public boolean next() {
    return iterator.next();
  }

  @Override
  public void remove() {
    throw new UnsupportedOperationException();
  }

}
