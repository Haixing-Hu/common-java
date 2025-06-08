////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.datastructure.list.primitive.adaptor;

import ltd.qubit.commons.datastructure.list.primitive.CharIterator;
import ltd.qubit.commons.lang.Argument;

/**
 * {@link CharIterator} 的一个不可修改版本。
 *
 * @author 胡海星
 */
public class UnmodifiableCharIterator implements CharIterator {

  /**
   * 将一个 {@link CharIterator} 包装成一个
   * {@link UnmodifiableCharIterator}。
   *
   * @param iterator
   *     要包装的 {@link CharIterator}。
   * @return 包装了指定迭代器的 {@link UnmodifiableCharIterator}。
   */
  public static UnmodifiableCharIterator wrap(final CharIterator iterator) {
    if (iterator instanceof UnmodifiableCharIterator) {
      return (UnmodifiableCharIterator) iterator;
    } else {
      return new UnmodifiableCharIterator(iterator);
    }
  }

  /**
   * 被包装的迭代器。
   */
  private final CharIterator iterator;

  /**
   * 构造一个 {@link UnmodifiableCharIterator}。
   *
   * @param iterator
   *     要包装的 {@link CharIterator}。
   */
  protected UnmodifiableCharIterator(final CharIterator iterator) {
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
  public char next() {
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