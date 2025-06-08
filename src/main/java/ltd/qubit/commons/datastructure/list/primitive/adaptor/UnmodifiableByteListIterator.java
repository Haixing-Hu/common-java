////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.datastructure.list.primitive.adaptor;

import ltd.qubit.commons.datastructure.list.primitive.ByteListIterator;

import static ltd.qubit.commons.lang.Argument.requireNonNull;

/**
 * {@link ByteListIterator} 的一个不可修改版本。
 *
 * @author 胡海星
 */
public class UnmodifiableByteListIterator implements ByteListIterator {

  /**
   * 将一个 {@link ByteListIterator} 包装成一个
   * {@link UnmodifiableByteListIterator}。
   *
   * @param iterator
   *     要包装的 {@link ByteListIterator}。
   * @return 包装了指定迭代器的 {@link UnmodifiableByteListIterator}。
   */
  public static UnmodifiableByteListIterator wrap(final ByteListIterator iterator) {
    if (iterator instanceof UnmodifiableByteListIterator) {
      return (UnmodifiableByteListIterator) iterator;
    } else {
      return new UnmodifiableByteListIterator(iterator);
    }
  }

  /**
   * 被包装的迭代器。
   */
  private final ByteListIterator iterator;

  /**
   * 构造一个 {@link UnmodifiableByteListIterator}。
   *
   * @param iterator
   *     要包装的 {@link ByteListIterator}。
   */
  protected UnmodifiableByteListIterator(final ByteListIterator iterator) {
    this.iterator = requireNonNull("iterator", iterator);
  }

  /**
   * 不支持此操作。
   *
   * @param element
   *     要添加的元素。
   * @throws UnsupportedOperationException
   *     总是抛出。
   */
  @Override
  public void add(final byte element) {
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
  public byte next() {
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
  public byte previous() {
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
   * @param element
   *     要设置的元素。
   * @throws UnsupportedOperationException
   *     总是抛出。
   */
  @Override
  public void set(final byte element) {
    throw new UnsupportedOperationException();
  }

}