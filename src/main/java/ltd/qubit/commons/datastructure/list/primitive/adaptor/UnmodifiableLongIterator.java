////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.datastructure.list.primitive.adaptor;

import ltd.qubit.commons.datastructure.list.primitive.LongIterator;
import ltd.qubit.commons.lang.Argument;

/**
 * {@link LongIterator} 的不可修改版本。
 *
 * @author 胡海星
 */
public class UnmodifiableLongIterator implements LongIterator {

  /**
   * 将 {@link LongIterator} 包装为 {@link UnmodifiableLongIterator}。
   *
   * @param iterator
   *     要包装的 {@link LongIterator}。
   * @return 包装指定迭代器的 {@link UnmodifiableLongIterator}。
   */
  public static UnmodifiableLongIterator wrap(final LongIterator iterator) {
    if (iterator instanceof UnmodifiableLongIterator) {
      return (UnmodifiableLongIterator) iterator;
    } else {
      return new UnmodifiableLongIterator(iterator);
    }
  }

  /**
   * 被包装的迭代器。
   */
  private final LongIterator iterator;

  /**
   * 构造一个 {@link UnmodifiableLongIterator}。
   *
   * @param iterator
   *     要包装的 {@link LongIterator}。
   */
  protected UnmodifiableLongIterator(final LongIterator iterator) {
    this.iterator = Argument.requireNonNull("iterator", iterator);
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
  public long next() {
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