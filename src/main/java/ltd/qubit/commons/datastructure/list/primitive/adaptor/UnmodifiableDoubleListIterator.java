////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.datastructure.list.primitive.adaptor;

import ltd.qubit.commons.datastructure.list.primitive.DoubleListIterator;

import static ltd.qubit.commons.lang.Argument.requireNonNull;

/**
 * {@link DoubleListIterator} 的不可修改版本。
 *
 * @author 胡海星
 */
public class UnmodifiableDoubleListIterator implements DoubleListIterator {

  /**
   * 将 {@link DoubleListIterator} 包装为 {@link UnmodifiableDoubleListIterator}。
   *
   * @param iterator
   *     要包装的 {@link DoubleListIterator}。
   * @return 包装指定迭代器的 {@link UnmodifiableDoubleListIterator}。
   */
  public static UnmodifiableDoubleListIterator wrap(final DoubleListIterator iterator) {
    if (iterator instanceof UnmodifiableDoubleListIterator) {
      return (UnmodifiableDoubleListIterator) iterator;
    } else {
      return new UnmodifiableDoubleListIterator(iterator);
    }
  }

  /**
   * 被包装的迭代器。
   */
  private final DoubleListIterator iterator;

  /**
   * 构造一个 {@link UnmodifiableDoubleListIterator}。
   *
   * @param iterator
   *     要包装的 {@link DoubleListIterator}。
   */
  protected UnmodifiableDoubleListIterator(final DoubleListIterator iterator) {
    this.iterator = requireNonNull("iterator", iterator);
  }

  /**
   * 不支持此操作。
   *
   * @throws UnsupportedOperationException
   *     总是抛出。
   */
  @Override
  public void add(final double element) {
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
  public double next() {
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
  public double previous() {
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
  public void set(final double element) {
    throw new UnsupportedOperationException();
  }

}