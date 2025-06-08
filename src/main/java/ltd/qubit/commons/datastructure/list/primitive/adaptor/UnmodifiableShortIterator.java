////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.datastructure.list.primitive.adaptor;

import ltd.qubit.commons.datastructure.list.primitive.ShortIterator;
import ltd.qubit.commons.lang.Argument;

/**
 * {@link ShortIterator} 的不可修改版本。
 *
 * @author 胡海星
 */
public class UnmodifiableShortIterator implements ShortIterator {

  /**
   * 将 {@link ShortIterator} 包装为 {@link UnmodifiableShortIterator}。
   *
   * @param iterator
   *     要包装的 {@link ShortIterator}。
   * @return 包装指定迭代器的 {@link UnmodifiableShortIterator}。
   */
  public static UnmodifiableShortIterator wrap(final ShortIterator iterator) {
    if (iterator instanceof UnmodifiableShortIterator) {
      return (UnmodifiableShortIterator) iterator;
    } else {
      return new UnmodifiableShortIterator(iterator);
    }
  }

  /**
   * 被包装的迭代器。
   */
  private final ShortIterator iterator;

  /**
   * 构造一个 {@link UnmodifiableShortIterator}。
   *
   * @param iterator
   *     要包装的 {@link ShortIterator}。
   */
  protected UnmodifiableShortIterator(final ShortIterator iterator) {
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
  public short next() {
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
