////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.datastructure.list.primitive.adaptor;

import ltd.qubit.commons.datastructure.list.primitive.LongListIterator;

import static ltd.qubit.commons.lang.Argument.requireNonNull;

/**
 * {@link LongListIterator} 的不可修改版本。
 *
 * @author 胡海星
 */
public class UnmodifiableLongListIterator implements LongListIterator {

  /**
   * 将 {@link LongListIterator} 包装为 {@link UnmodifiableLongListIterator}。
   *
   * @param iterator
   *     要包装的 {@link LongListIterator}。
   * @return 包装指定迭代器的 {@link UnmodifiableLongListIterator}。
   */
  public static UnmodifiableLongListIterator wrap(final LongListIterator iterator) {
    if (iterator instanceof UnmodifiableLongListIterator) {
      return (UnmodifiableLongListIterator) iterator;
    } else {
      return new UnmodifiableLongListIterator(iterator);
    }
  }

  /**
   * 被包装的迭代器。
   */
  private final LongListIterator iterator;

  /**
   * 构造一个 {@link UnmodifiableLongListIterator}。
   *
   * @param iterator
   *     要包装的 {@link LongListIterator}。
   */
  protected UnmodifiableLongListIterator(final LongListIterator iterator) {
    this.iterator = requireNonNull("iterator", iterator);
  }

  /**
   * 不支持此操作。
   *
   * @throws UnsupportedOperationException
   *     总是抛出。
   */
  @Override
  public void add(final long element) {
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
  public long next() {
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
  public long previous() {
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
  public void set(final long element) {
    throw new UnsupportedOperationException();
  }

}
