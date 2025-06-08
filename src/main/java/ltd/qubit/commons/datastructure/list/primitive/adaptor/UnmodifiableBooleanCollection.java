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

import ltd.qubit.commons.datastructure.list.primitive.AbstractBooleanCollection;
import ltd.qubit.commons.datastructure.list.primitive.BooleanCollection;
import ltd.qubit.commons.datastructure.list.primitive.BooleanIterator;

import static ltd.qubit.commons.lang.Argument.requireNonNull;

/**
 * {@link BooleanCollection}的一个不可修改的版本。
 *
 * @author 胡海星
 */
public final class UnmodifiableBooleanCollection extends AbstractBooleanCollection {

  @Serial
  private static final long serialVersionUID = -6830844026182836661L;

  /**
   * 将一个{@link BooleanCollection}包装为{@link UnmodifiableBooleanCollection}。
   *
   * @param collection
   *     要包装的{@link BooleanCollection}。
   * @return 一个包装了指定集合的{@link UnmodifiableBooleanCollection}。
   */
  public static UnmodifiableBooleanCollection wrap(final BooleanCollection collection) {
    if (collection instanceof UnmodifiableBooleanCollection) {
      return (UnmodifiableBooleanCollection) collection;
    } else {
      return new UnmodifiableBooleanCollection(collection);
    }
  }

  /**
   * 被包装的{@link BooleanCollection}。
   */
  private final BooleanCollection collection;

  /**
   * 构造一个包装了指定集合的{@link UnmodifiableBooleanCollection}。
   *
   * @param collection
   *     要包装的{@link BooleanCollection}。
   */
  UnmodifiableBooleanCollection(final BooleanCollection collection) {
    this.collection = requireNonNull("collection", collection);
  }

  /**
   * 不支持此操作。
   *
   * @throws UnsupportedOperationException
   *     总是抛出。
   */
  @Override
  public boolean add(final boolean element) {
    throw new UnsupportedOperationException();
  }

  /**
   * 不支持此操作。
   *
   * @throws UnsupportedOperationException
   *     总是抛出。
   */
  @Override
  public boolean addAll(final BooleanCollection c) {
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
  public boolean contains(final boolean element) {
    return collection.contains(element);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean containsAll(final BooleanCollection c) {
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
  public BooleanIterator iterator() {
    return new UnmodifiableBooleanIterator(collection.iterator());
  }

  /**
   * 不支持此操作。
   *
   * @throws UnsupportedOperationException
   *     总是抛出。
   */
  @Override
  public boolean removeAll(final BooleanCollection c) {
    throw new UnsupportedOperationException();
  }

  /**
   * 不支持此操作。
   *
   * @throws UnsupportedOperationException
   *     总是抛出。
   */
  @Override
  public boolean remove(final boolean element) {
    throw new UnsupportedOperationException();
  }

  /**
   * 不支持此操作。
   *
   * @throws UnsupportedOperationException
   *     总是抛出。
   */
  @Override
  public boolean retainAll(final BooleanCollection c) {
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
  public boolean[] toArray() {
    return collection.toArray();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean[] toArray(final boolean[] a) {
    return collection.toArray(a);
  }
}
