////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.datastructure.list.primitive.adaptor;

import ltd.qubit.commons.datastructure.list.primitive.IntListIterator;
import ltd.qubit.commons.lang.Argument;

/**
 * {@link IntListIterator} 的不可修改版本。
 *
 * @author 胡海星
 */
public class UnmodifiableIntListIterator implements IntListIterator {

  /**
   * 将 {@link IntListIterator} 包装为 {@link UnmodifiableIntListIterator}。
   *
   * @param iterator
   *     要包装的 {@link IntListIterator}。
   * @return 包装指定迭代器的 {@link UnmodifiableIntListIterator}。
   */
  public static UnmodifiableIntListIterator wrap(final IntListIterator iterator) {
    if (iterator instanceof UnmodifiableIntListIterator) {
      return (UnmodifiableIntListIterator) iterator;
    } else {
      return new UnmodifiableIntListIterator(iterator);
    }
  }

  /**
   * 被包装的迭代器。
   */
  private final IntListIterator iterator;

  /**
   * 构造一个 {@link UnmodifiableIntListIterator}。
   *
   * @param iterator
   *     要包装的 {@link IntListIterator}。
   */
  protected UnmodifiableIntListIterator(final IntListIterator iterator) {
    this.iterator = Argument.requireNonNull("iterator", iterator);
  }

  /**
   * 不支持此操作。
   *
   * @throws UnsupportedOperationException
   *     总是抛出。
   */
  @Override
  public void add(final int element) {
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
  public int next() {
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
  public int previous() {
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
  public void set(final int element) {
    throw new UnsupportedOperationException();
  }

}