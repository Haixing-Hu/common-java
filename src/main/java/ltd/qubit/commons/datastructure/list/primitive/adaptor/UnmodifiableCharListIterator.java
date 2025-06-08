////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.datastructure.list.primitive.adaptor;

import ltd.qubit.commons.datastructure.list.primitive.CharListIterator;

import static ltd.qubit.commons.lang.Argument.requireNonNull;

/**
 * {@link CharListIterator} 的一个不可修改版本。
 *
 * @author 胡海星
 */
public class UnmodifiableCharListIterator implements CharListIterator {

  /**
   * 将一个 {@link CharListIterator} 包装成一个
   * {@link UnmodifiableCharListIterator}。
   *
   * @param iterator
   *     要包装的 {@link CharListIterator}。
   * @return 包装了指定迭代器的 {@link UnmodifiableCharListIterator}。
   */
  public static UnmodifiableCharListIterator wrap(final CharListIterator iterator) {
    if (iterator instanceof UnmodifiableCharListIterator) {
      return (UnmodifiableCharListIterator) iterator;
    } else {
      return new UnmodifiableCharListIterator(iterator);
    }
  }

  /**
   * 被包装的{@link CharListIterator}。
   */
  private final CharListIterator iterator;

  /**
   * 构造一个{@link UnmodifiableCharListIterator}。
   *
   * @param iterator
   *     要包装的{@link CharListIterator}。
   */
  protected UnmodifiableCharListIterator(final CharListIterator iterator) {
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
  public void add(final char element) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean hasNext() {
    return iterator.hasNext();
  }

  @Override
  public boolean hasPrevious() {
    return iterator.hasPrevious();
  }

  @Override
  public char next() {
    return iterator.next();
  }

  @Override
  public int nextIndex() {
    return iterator.nextIndex();
  }

  @Override
  public char previous() {
    return iterator.previous();
  }

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
  public void set(final char element) {
    throw new UnsupportedOperationException();
  }

}