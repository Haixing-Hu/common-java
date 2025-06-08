////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.datastructure.list.primitive.adaptor;

import ltd.qubit.commons.datastructure.list.primitive.DoubleIterator;
import ltd.qubit.commons.lang.Argument;

/**
 * {@link DoubleIterator} 的不可修改版本。
 *
 * @author 胡海星
 */
public class UnmodifiableDoubleIterator implements DoubleIterator {

  /**
   * 将 {@link DoubleIterator} 包装为 {@link UnmodifiableDoubleIterator}。
   *
   * @param iterator
   *     要包装的 {@link DoubleIterator}。
   * @return 包装指定迭代器的 {@link UnmodifiableDoubleIterator}。
   */
  public static UnmodifiableDoubleIterator wrap(final DoubleIterator iterator) {
    if (iterator instanceof UnmodifiableDoubleIterator) {
      return (UnmodifiableDoubleIterator) iterator;
    } else {
      return new UnmodifiableDoubleIterator(iterator);
    }
  }

  /**
   * 被包装的迭代器。
   */
  private final DoubleIterator iterator;

  /**
   * 构造一个 {@link UnmodifiableDoubleIterator}。
   *
   * @param iterator
   *     要包装的 {@link DoubleIterator}。
   */
  protected UnmodifiableDoubleIterator(final DoubleIterator iterator) {
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
  public double next() {
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