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

import ltd.qubit.commons.datastructure.list.primitive.AbstractShortCollection;
import ltd.qubit.commons.datastructure.list.primitive.ShortCollection;
import ltd.qubit.commons.datastructure.list.primitive.ShortIterator;

import static ltd.qubit.commons.lang.Argument.requireNonNull;

/**
 * {@link ShortCollection} 的不可修改版本。
 *
 * @author 胡海星
 */
public class UnmodifiableShortCollection extends AbstractShortCollection {

  @Serial
  private static final long serialVersionUID = -824703401053770770L;

  /**
   * 将 {@link ShortCollection} 包装为 {@link UnmodifiableShortCollection}。
   *
   * @param collection
   *     要包装的 {@link ShortCollection}。
   * @return 包装指定集合的 {@link UnmodifiableShortCollection}。
   */
  public static UnmodifiableShortCollection wrap(final ShortCollection collection) {
    if (collection instanceof UnmodifiableShortCollection) {
      return (UnmodifiableShortCollection) collection;
    } else {
      return new UnmodifiableShortCollection(collection);
    }
  }

  /**
   * 被包装的集合。
   */
  private final ShortCollection collection;

  /**
   * 构造一个 {@link UnmodifiableShortCollection}。
   *
   * @param collection
   *     要包装的 {@link ShortCollection}。
   */
  protected UnmodifiableShortCollection(final ShortCollection collection) {
    this.collection = requireNonNull("collection", collection);
  }

  /**
   * 不支持此操作。
   *
   * @throws UnsupportedOperationException
   *     总是抛出。
   */
  @Override
  public boolean add(final short element) {
    throw new UnsupportedOperationException();
  }

  /**
   * 不支持此操作。
   *
   * @throws UnsupportedOperationException
   *     总是抛出。
   */
  @Override
  public boolean addAll(final ShortCollection c) {
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
  public boolean contains(final short element) {
    return collection.contains(element);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean containsAll(final ShortCollection c) {
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
  public ShortIterator iterator() {
    return new UnmodifiableShortIterator(collection.iterator());
  }

  /**
   * 不支持此操作。
   *
   * @throws UnsupportedOperationException
   *     总是抛出。
   */
  @Override
  public boolean removeAll(final ShortCollection c) {
    throw new UnsupportedOperationException();
  }

  /**
   * 不支持此操作。
   *
   * @throws UnsupportedOperationException
   *     总是抛出。
   */
  @Override
  public boolean remove(final short element) {
    throw new UnsupportedOperationException();
  }

  /**
   * 不支持此操作。
   *
   * @throws UnsupportedOperationException
   *     总是抛出。
   */
  @Override
  public boolean retainAll(final ShortCollection c) {
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
  public short[] toArray() {
    return collection.toArray();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public short[] toArray(final short[] a) {
    return collection.toArray(a);
  }
}
