////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.datastructure.list.primitive.adaptor;

import ltd.qubit.commons.datastructure.list.primitive.ShortListIterator;
import ltd.qubit.commons.lang.Argument;

/**
 * {@link ShortListIterator} 的不可修改版本。
 *
 * @author 胡海星
 */
public class UnmodifiableShortListIterator implements ShortListIterator {

  /**
   * 将 {@link ShortListIterator} 包装为 {@link UnmodifiableShortListIterator}。
   *
   * @param iterator
   *     要包装的 {@link ShortListIterator}。
   * @return 包装指定迭代器的 {@link UnmodifiableShortListIterator}。
   */
  public static UnmodifiableShortListIterator wrap(final ShortListIterator iterator) {
    if (iterator instanceof UnmodifiableShortListIterator) {
      return (UnmodifiableShortListIterator) iterator;
    } else {
      return new UnmodifiableShortListIterator(iterator);
    }
  }

  /**
   * 被包装的迭代器。
   */
  private final ShortListIterator iterator;

  /**
   * 构造一个 {@link UnmodifiableShortListIterator}。
   *
   * @param iterator
   *     要包装的 {@link ShortListIterator}。
   */
  protected UnmodifiableShortListIterator(final ShortListIterator iterator) {
    this.iterator = Argument.requireNonNull("iterator", iterator);
  }

  /**
   * 不支持此操作。
   *
   * @throws UnsupportedOperationException
   *     总是抛出。
   */
  @Override
  public void add(final short element) {
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
  public short next() {
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
  public short previous() {
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
  public void set(final short element) {
    throw new UnsupportedOperationException();
  }

}
