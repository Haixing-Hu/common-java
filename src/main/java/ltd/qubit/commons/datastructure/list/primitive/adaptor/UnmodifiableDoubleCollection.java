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

import ltd.qubit.commons.datastructure.list.primitive.AbstractDoubleCollection;
import ltd.qubit.commons.datastructure.list.primitive.DoubleCollection;
import ltd.qubit.commons.datastructure.list.primitive.DoubleIterator;
import ltd.qubit.commons.lang.Argument;

/**
 * {@link DoubleCollection} 的不可修改版本。
 *
 * @author 胡海星
 */
public class UnmodifiableDoubleCollection extends AbstractDoubleCollection {

  @Serial
  private static final long serialVersionUID = 8664528441655027186L;

  /**
   * 将 {@link DoubleCollection} 包装为 {@link UnmodifiableDoubleCollection}。
   *
   * @param collection
   *     要包装的 {@link DoubleCollection}。
   * @return 包装指定集合的 {@link UnmodifiableDoubleCollection}。
   */
  public static UnmodifiableDoubleCollection wrap(final DoubleCollection collection) {
    if (collection instanceof UnmodifiableDoubleCollection) {
      return (UnmodifiableDoubleCollection) collection;
    } else {
      return new UnmodifiableDoubleCollection(collection);
    }
  }

  /**
   * 被包装的集合。
   */
  private final DoubleCollection collection;

  /**
   * 构造一个 {@link UnmodifiableDoubleCollection}。
   *
   * @param collection
   *     要包装的 {@link DoubleCollection}。
   */
  protected UnmodifiableDoubleCollection(final DoubleCollection collection) {
    this.collection = Argument.requireNonNull("collection", collection);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean add(final double element) {
    throw new UnsupportedOperationException();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean addAll(final DoubleCollection c) {
    throw new UnsupportedOperationException();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void clear() {
    throw new UnsupportedOperationException();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean contains(final double element) {
    return collection.contains(element);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean containsAll(final DoubleCollection c) {
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
  public DoubleIterator iterator() {
    return new UnmodifiableDoubleIterator(collection.iterator());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean removeAll(final DoubleCollection c) {
    throw new UnsupportedOperationException();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean remove(final double element) {
    throw new UnsupportedOperationException();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean retainAll(final DoubleCollection c) {
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
  public double[] toArray() {
    return collection.toArray();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public double[] toArray(final double[] a) {
    return collection.toArray(a);
  }
}
