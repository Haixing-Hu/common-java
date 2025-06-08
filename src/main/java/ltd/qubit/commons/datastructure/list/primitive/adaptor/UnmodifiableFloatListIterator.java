////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.datastructure.list.primitive.adaptor;

import ltd.qubit.commons.datastructure.list.primitive.FloatListIterator;
import ltd.qubit.commons.lang.Argument;

/**
 * {@link FloatListIterator} 的不可修改版本。
 *
 * @author 胡海星
 */
public class UnmodifiableFloatListIterator implements FloatListIterator {

  /**
   * 将 {@link FloatListIterator} 包装为 {@link UnmodifiableFloatListIterator}。
   *
   * @param iterator
   *     要包装的 {@link FloatListIterator}。
   * @return 包装指定迭代器的 {@link UnmodifiableFloatListIterator}。
   */
  public static UnmodifiableFloatListIterator wrap(final FloatListIterator iterator) {
    if (iterator instanceof UnmodifiableFloatListIterator) {
      return (UnmodifiableFloatListIterator) iterator;
    } else {
      return new UnmodifiableFloatListIterator(iterator);
    }
  }

  /**
   * 被包装的{@link FloatListIterator}。
   */
  private final FloatListIterator iterator;

  /**
   * 构造一个{@link UnmodifiableFloatListIterator}。
   *
   * @param iterator
   *     要包装的{@link FloatListIterator}。
   */
  protected UnmodifiableFloatListIterator(final FloatListIterator iterator) {
    this.iterator = Argument.requireNonNull("iterator", iterator);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void add(final float element) {
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
  public float next() {
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
  public float previous() {
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
  public void set(final float element) {
    throw new UnsupportedOperationException();
  }

}