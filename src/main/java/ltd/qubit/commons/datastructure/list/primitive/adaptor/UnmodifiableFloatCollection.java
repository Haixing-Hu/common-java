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

import ltd.qubit.commons.datastructure.list.primitive.AbstractFloatCollection;
import ltd.qubit.commons.datastructure.list.primitive.FloatCollection;
import ltd.qubit.commons.datastructure.list.primitive.FloatIterator;

import static ltd.qubit.commons.lang.Argument.requireNonNull;

/**
 * {@link FloatCollection} 的不可修改版本。
 *
 * @author 胡海星
 */
public class UnmodifiableFloatCollection extends AbstractFloatCollection {

  @Serial
  private static final long serialVersionUID = -3450029815907229739L;

  /**
   * 将 {@link FloatCollection} 包装为 {@link UnmodifiableFloatCollection}。
   *
   * @param collection
   *     要包装的 {@link FloatCollection}。
   * @return 包装指定集合的 {@link UnmodifiableFloatCollection}。
   */
  public static UnmodifiableFloatCollection wrap(final FloatCollection collection) {
    if (collection instanceof UnmodifiableFloatCollection) {
      return (UnmodifiableFloatCollection) collection;
    } else {
      return new UnmodifiableFloatCollection(collection);
    }
  }

  /**
   * 被包装的集合。
   */
  private final FloatCollection collection;

  /**
   * 构造一个 {@link UnmodifiableFloatCollection}。
   *
   * @param collection
   *     要包装的 {@link FloatCollection}。
   */
  protected UnmodifiableFloatCollection(final FloatCollection collection) {
    this.collection = requireNonNull("collection", collection);
  }

  /**
   * 不支持此操作。
   *
   * @throws UnsupportedOperationException
   *     总是抛出。
   */
  @Override
  public boolean add(final float element) {
    throw new UnsupportedOperationException();
  }

  /**
   * 不支持此操作。
   *
   * @throws UnsupportedOperationException
   *     总是抛出。
   */
  @Override
  public boolean addAll(final FloatCollection c) {
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
  public boolean contains(final float element) {
    return collection.contains(element);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean containsAll(final FloatCollection c) {
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
  public FloatIterator iterator() {
    return new UnmodifiableFloatIterator(collection.iterator());
  }

  /**
   * 不支持此操作。
   *
   * @throws UnsupportedOperationException
   *     总是抛出。
   */
  @Override
  public boolean removeAll(final FloatCollection c) {
    throw new UnsupportedOperationException();
  }

  /**
   * 不支持此操作。
   *
   * @throws UnsupportedOperationException
   *     总是抛出。
   */
  @Override
  public boolean remove(final float element) {
    throw new UnsupportedOperationException();
  }

  /**
   * 不支持此操作。
   *
   * @throws UnsupportedOperationException
   *     总是抛出。
   */
  @Override
  public boolean retainAll(final FloatCollection c) {
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
  public float[] toArray() {
    return collection.toArray();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public float[] toArray(final float[] a) {
    return collection.toArray(a);
  }
}
