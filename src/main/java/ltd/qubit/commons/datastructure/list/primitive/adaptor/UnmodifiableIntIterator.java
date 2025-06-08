////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.datastructure.list.primitive.adaptor;

import ltd.qubit.commons.datastructure.list.primitive.IntIterator;

import static ltd.qubit.commons.lang.Argument.requireNonNull;

/**
 * {@link IntIterator} 的不可修改版本。
 *
 * @author 胡海星
 */
public class UnmodifiableIntIterator implements IntIterator {

  /**
   * 将 {@link IntIterator} 包装为 {@link UnmodifiableIntIterator}。
   *
   * @param iterator
   *     要包装的 {@link IntIterator}。
   * @return 包装指定迭代器的 {@link UnmodifiableIntIterator}。
   */
  public static UnmodifiableIntIterator wrap(final IntIterator iterator) {
    if (iterator instanceof UnmodifiableIntIterator) {
      return (UnmodifiableIntIterator) iterator;
    } else {
      return new UnmodifiableIntIterator(iterator);
    }
  }

  /**
   * 被包装的 {@link IntIterator}。
   */
  private final IntIterator iterator;

  /**
   * 构造一个{@link UnmodifiableIntIterator}。
   *
   * @param iterator
   *     要包装的{@link IntIterator}。
   */
  protected UnmodifiableIntIterator(final IntIterator iterator) {
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
  public int next() {
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