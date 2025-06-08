////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.datastructure.list.primitive.adaptor;

import java.io.Serial;

import ltd.qubit.commons.datastructure.list.primitive.AbstractCharCollection;
import ltd.qubit.commons.datastructure.list.primitive.CharCollection;
import ltd.qubit.commons.datastructure.list.primitive.CharIterator;
import ltd.qubit.commons.lang.Argument;

/**
 * {@link CharCollection} 的一个不可修改版本。
 *
 * @author 胡海星
 */
public class UnmodifiableCharCollection extends AbstractCharCollection {

  @Serial
  private static final long serialVersionUID = -1935352954402707861L;

  /**
   * 将一个 {@link CharCollection} 包装成一个
   * {@link UnmodifiableCharCollection}。
   *
   * @param collection
   *     要包装的 {@link CharCollection}。
   * @return 包装了指定集合的 {@link UnmodifiableCharCollection}。
   */
  public static UnmodifiableCharCollection wrap(final CharCollection collection) {
    if (collection instanceof UnmodifiableCharCollection) {
      return (UnmodifiableCharCollection) collection;
    } else {
      return new UnmodifiableCharCollection(collection);
    }
  }

  /**
   * 被包装的集合。
   */
  private final CharCollection collection;

  /**
   * 构造一个 {@link UnmodifiableCharCollection}。
   *
   * @param collection
   *     要包装的 {@link CharCollection}。
   */
  protected UnmodifiableCharCollection(final CharCollection collection) {
    this.collection = Argument.requireNonNull("collection", collection);
  }

  /**
   * 不支持此操作。
   *
   * @param element
   *     要添加的元素。
   * @return 此调用总会抛出 {@link UnsupportedOperationException}。
   * @throws UnsupportedOperationException
   *     总是抛出。
   */
  @Override
  public boolean add(final char element) {
    throw new UnsupportedOperationException();
  }

  /**
   * 不支持此操作。
   *
   * @param c
   *     要添加的元素集合。
   * @return 此调用总会抛出 {@link UnsupportedOperationException}。
   * @throws UnsupportedOperationException
   *     总是抛出。
   */
  @Override
  public boolean addAll(final CharCollection c) {
    throw new UnsupportedOperationException();
  }

  /**
   * 不支持此操作。
   *
   * @throws UnsupportedOperationException
   *     总是抛出。
   */
  @Override
  public void clear() {
    throw new UnsupportedOperationException();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean contains(final char element) {
    return collection.contains(element);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean containsAll(final CharCollection c) {
    return collection.containsAll(c);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isEmpty() {
    return collection.isEmpty();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public CharIterator iterator() {
    return new UnmodifiableCharIterator(collection.iterator());
  }

  /**
   * 不支持此操作。
   *
   * @param c
   *     要移除的元素集合。
   * @return 此调用总会抛出 {@link UnsupportedOperationException}。
   * @throws UnsupportedOperationException
   *     总是抛出。
   */
  @Override
  public boolean removeAll(final CharCollection c) {
    throw new UnsupportedOperationException();
  }

  /**
   * 不支持此操作。
   *
   * @param element
   *     要移除的元素。
   * @return 此调用总会抛出 {@link UnsupportedOperationException}。
   * @throws UnsupportedOperationException
   *     总是抛出。
   */
  @Override
  public boolean remove(final char element) {
    throw new UnsupportedOperationException();
  }

  /**
   * 不支持此操作。
   *
   * @param c
   *     要保留的元素集合。
   * @return 此调用总会抛出 {@link UnsupportedOperationException}。
   * @throws UnsupportedOperationException
   *     总是抛出。
   */
  @Override
  public boolean retainAll(final CharCollection c) {
    throw new UnsupportedOperationException();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int size() {
    return collection.size();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public char[] toArray() {
    return collection.toArray();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public char[] toArray(final char[] a) {
    return collection.toArray(a);
  }
}