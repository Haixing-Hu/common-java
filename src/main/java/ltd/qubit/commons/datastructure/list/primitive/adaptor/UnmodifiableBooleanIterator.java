////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.datastructure.list.primitive.adaptor;

import ltd.qubit.commons.datastructure.list.primitive.BooleanIterator;

import static ltd.qubit.commons.lang.Argument.requireNonNull;

/**
 * {@link BooleanIterator}的一个不可修改的版本。
 *
 * @author 胡海星
 */
public final class UnmodifiableBooleanIterator implements BooleanIterator {

  /**
   * 将一个{@link BooleanIterator}包装为{@link UnmodifiableBooleanIterator}。
   *
   * @param iterator
   *     要包装的{@link BooleanIterator}。
   * @return 一个包装了指定迭代器的{@link UnmodifiableBooleanIterator}。
   */
  public static UnmodifiableBooleanIterator wrap(final BooleanIterator iterator) {
    if (iterator instanceof UnmodifiableBooleanIterator) {
      return (UnmodifiableBooleanIterator) iterator;
    } else {
      return new UnmodifiableBooleanIterator(iterator);
    }
  }

  /**
   * 被包装的{@link BooleanIterator}。
   */
  private final BooleanIterator iterator;

  /**
   * 构造一个包装了指定迭代器的{@link UnmodifiableBooleanIterator}。
   *
   * @param iterator
   *     要包装的{@link BooleanIterator}。
   */
  UnmodifiableBooleanIterator(final BooleanIterator iterator) {
    this.iterator = requireNonNull("iterator", iterator);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean hasNext() {
    return iterator.hasNext();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean next() {
    return iterator.next();
  }

  /**
   * 不支持此操作。
   *
   * @throws UnsupportedOperationException
   *     总是抛出。
   */
  @Override
  public void remove() {
    throw new UnsupportedOperationException();
  }

}
