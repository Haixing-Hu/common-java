////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.datastructure.list.primitive.adaptor;

import ltd.qubit.commons.datastructure.list.primitive.FloatIterator;
import ltd.qubit.commons.lang.Argument;

/**
 * {@link FloatIterator} 的不可修改版本。
 *
 * @author 胡海星
 */
public class UnmodifiableFloatIterator implements FloatIterator {

  /**
   * 将 {@link FloatIterator} 包装为 {@link UnmodifiableFloatIterator}。
   *
   * @param iterator
   *     要包装的 {@link FloatIterator}。
   * @return 包装指定迭代器的 {@link UnmodifiableFloatIterator}。
   */
  public static UnmodifiableFloatIterator wrap(final FloatIterator iterator) {
    if (iterator instanceof UnmodifiableFloatIterator) {
      return (UnmodifiableFloatIterator) iterator;
    } else {
      return new UnmodifiableFloatIterator(iterator);
    }
  }

  /**
   * 被包装的 {@link FloatIterator}。
   */
  private final FloatIterator iterator;

  /**
   * 构造一个 {@link UnmodifiableFloatIterator}。
   *
   * @param iterator
   *     要包装的 {@link FloatIterator}。
   */
  protected UnmodifiableFloatIterator(final FloatIterator iterator) {
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
  public float next() {
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
