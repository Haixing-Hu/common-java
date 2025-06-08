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

import ltd.qubit.commons.datastructure.list.primitive.AbstractIntCollection;
import ltd.qubit.commons.datastructure.list.primitive.IntCollection;
import ltd.qubit.commons.datastructure.list.primitive.IntIterator;

import static ltd.qubit.commons.lang.Argument.requireNonNull;

/**
 * {@link IntCollection} 的不可修改版本。
 *
 * @author 胡海星
 */
public class UnmodifiableIntCollection extends AbstractIntCollection {

  @Serial
  private static final long serialVersionUID = 6766166988906612645L;

  /**
   * 将 {@link IntCollection} 包装为 {@link UnmodifiableIntCollection}。
   *
   * @param collection
   *     要包装的 {@link IntCollection}。
   * @return 包装指定集合的 {@link UnmodifiableIntCollection}。
   */
  public static UnmodifiableIntCollection wrap(final IntCollection collection) {
    if (collection instanceof UnmodifiableIntCollection) {
      return (UnmodifiableIntCollection) collection;
    } else {
      return new UnmodifiableIntCollection(collection);
    }
  }

  /**
   * 被包装的{@link IntCollection}。
   */
  private final IntCollection collection;

  /**
   * 构造一个{@link UnmodifiableIntCollection}。
   *
   * @param collection
   *     要包装的{@link IntCollection}。
   */
  protected UnmodifiableIntCollection(final IntCollection collection) {
    this.collection = requireNonNull("collection", collection);
  }

  /**
   * 不支持此操作。
   *
   * @throws UnsupportedOperationException
   *     总是抛出。
   */
  @Override
  public boolean add(final int element) {
    throw new UnsupportedOperationException();
  }

  /**
   * 不支持此操作。
   *
   * @throws UnsupportedOperationException
   *     总是抛出。
   */
  @Override
  public boolean addAll(final IntCollection c) {
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
  public boolean contains(final int element) {
    return collection.contains(element);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean containsAll(final IntCollection c) {
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
   * 不支持此操作。
   *
   * @throws UnsupportedOperationException
   *     总是抛出。
   */
  @Override
  public IntIterator iterator() {
    return new UnmodifiableIntIterator(collection.iterator());
  }

  /**
   * 不支持此操作。
   *
   * @throws UnsupportedOperationException
   *     总是抛出。
   */
  @Override
  public boolean removeAll(final IntCollection c) {
    throw new UnsupportedOperationException();
  }

  /**
   * 不支持此操作。
   *
   * @throws UnsupportedOperationException
   *     总是抛出。
   */
  @Override
  public boolean remove(final int element) {
    throw new UnsupportedOperationException();
  }

  /**
   * 不支持此操作。
   *
   * @throws UnsupportedOperationException
   *     总是抛出。
   */
  @Override
  public boolean retainAll(final IntCollection c) {
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
  public int[] toArray() {
    return collection.toArray();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int[] toArray(final int[] a) {
    return collection.toArray(a);
  }
}
