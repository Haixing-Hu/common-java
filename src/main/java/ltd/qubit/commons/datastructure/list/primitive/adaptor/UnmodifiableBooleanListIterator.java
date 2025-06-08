////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.datastructure.list.primitive.adaptor;

import ltd.qubit.commons.datastructure.list.primitive.BooleanListIterator;
import ltd.qubit.commons.lang.Argument;

/**
 * {@link BooleanListIterator}的一个不可修改的版本。
 *
 * @author 胡海星
 */
public final class UnmodifiableBooleanListIterator implements BooleanListIterator {

  /**
   * 将一个{@link BooleanListIterator}包装为{@link UnmodifiableBooleanListIterator}.
   *
   * @param iterator
   *     要包装的{@link BooleanListIterator}。
   * @return 一个包装了指定迭代器的{@link UnmodifiableBooleanListIterator}。
   */
  public static UnmodifiableBooleanListIterator wrap(final BooleanListIterator iterator) {
    if (iterator instanceof UnmodifiableBooleanListIterator) {
      return (UnmodifiableBooleanListIterator) iterator;
    } else {
      return new UnmodifiableBooleanListIterator(iterator);
    }
  }

  /**
   * 被包装的{@link BooleanListIterator}。
   */
  private final BooleanListIterator iterator;

  /**
   * 构造一个{@link UnmodifiableBooleanListIterator}。
   *
   * @param iterator
   *     要包装的{@link BooleanListIterator}。
   */
  UnmodifiableBooleanListIterator(final BooleanListIterator iterator) {
    this.iterator = Argument.requireNonNull("iterator", iterator);
  }

  /**
   * 不支持此操作。
   *
   * @throws UnsupportedOperationException
   *     总是抛出。
   */
  @Override
  public void add(final boolean element) {
    throw new UnsupportedOperationException();
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
  public boolean hasPrevious() {
    return iterator.hasPrevious();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean next() {
    return iterator.next();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int nextIndex() {
    return iterator.nextIndex();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean previous() {
    return iterator.previous();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int previousIndex() {
    return iterator.previousIndex();
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

  /**
   * 不支持此操作。
   *
   * @throws UnsupportedOperationException
   *     总是抛出。
   */
  @Override
  public void set(final boolean element) {
    throw new UnsupportedOperationException();
  }

}
