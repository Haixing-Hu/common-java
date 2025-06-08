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

import ltd.qubit.commons.datastructure.list.primitive.AbstractLongCollection;
import ltd.qubit.commons.datastructure.list.primitive.LongCollection;
import ltd.qubit.commons.datastructure.list.primitive.LongIterator;
import ltd.qubit.commons.lang.Argument;

/**
 * {@link LongCollection} 的不可修改版本。
 *
 * @author 胡海星
 */
public class UnmodifiableLongCollection extends AbstractLongCollection {

  @Serial
  private static final long serialVersionUID = -4735199080766940445L;

  /**
   * 将 {@link LongCollection} 包装为 {@link UnmodifiableLongCollection}。
   *
   * @param collection
   *     要包装的 {@link LongCollection}。
   * @return 包装指定集合的 {@link UnmodifiableLongCollection}。
   */
  public static UnmodifiableLongCollection wrap(final LongCollection collection) {
    if (collection instanceof UnmodifiableLongCollection) {
      return (UnmodifiableLongCollection) collection;
    } else {
      return new UnmodifiableLongCollection(collection);
    }
  }

  /**
   * 被包装的集合。
   */
  private final LongCollection collection;

  /**
   * 构造一个 {@link UnmodifiableLongCollection}。
   *
   * @param collection
   *     要包装的 {@link LongCollection}。
   */
  protected UnmodifiableLongCollection(final LongCollection collection) {
    this.collection = Argument.requireNonNull("collection", collection);
  }

  /**
   * 不支持此操作。
   *
   * @throws UnsupportedOperationException
   *     总是抛出。
   */
  @Override
  public boolean add(final long element) {
    throw new UnsupportedOperationException();
  }

  /**
   * 不支持此操作。
   *
   * @throws UnsupportedOperationException
   *     总是抛出。
   */
  @Override
  public boolean addAll(final LongCollection c) {
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
  public boolean contains(final long element) {
    return collection.contains(element);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean containsAll(final LongCollection c) {
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
  public LongIterator iterator() {
    return new UnmodifiableLongIterator(collection.iterator());
  }

  /**
   * 不支持此操作。
   *
   * @throws UnsupportedOperationException
   *     总是抛出。
   */
  @Override
  public boolean removeAll(final LongCollection c) {
    throw new UnsupportedOperationException();
  }

  /**
   * 不支持此操作。
   *
   * @throws UnsupportedOperationException
   *     总是抛出。
   */
  @Override
  public boolean remove(final long element) {
    throw new UnsupportedOperationException();
  }

  /**
   * 不支持此操作。
   *
   * @throws UnsupportedOperationException
   *     总是抛出。
   */
  @Override
  public boolean retainAll(final LongCollection c) {
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
  public long[] toArray() {
    return collection.toArray();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public long[] toArray(final long[] a) {
    return collection.toArray(a);
  }
}
