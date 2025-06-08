////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.datastructure.list.primitive.adaptor;

import ltd.qubit.commons.datastructure.list.primitive.ByteIterator;
import ltd.qubit.commons.lang.Argument;

/**
 * {@link ByteIterator} 的一个不可修改版本。
 *
 * @author 胡海星
 */
public class UnmodifiableByteIterator implements ByteIterator {

  /**
   * 将一个 {@link ByteIterator} 包装成一个
   * {@link UnmodifiableByteIterator}。
   *
   * @param iterator
   *     要包装的 {@link ByteIterator}。
   * @return 包装了指定迭代器的 {@link UnmodifiableByteIterator}。
   */
  public static UnmodifiableByteIterator wrap(final ByteIterator iterator) {
    if (iterator instanceof UnmodifiableByteIterator) {
      return (UnmodifiableByteIterator) iterator;
    } else {
      return new UnmodifiableByteIterator(iterator);
    }
  }

  /**
   * 被包装的迭代器。
   */
  private final ByteIterator iterator;

  /**
   * 构造一个 {@link UnmodifiableByteIterator}。
   *
   * @param iterator
   *     要包装的 {@link ByteIterator}。
   */
  protected UnmodifiableByteIterator(final ByteIterator iterator) {
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
  public byte next() {
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