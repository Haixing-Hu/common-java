////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.datastructure.list.primitive.adaptor;

import ltd.qubit.commons.datastructure.list.primitive.AbstractDoubleCollection;
import ltd.qubit.commons.datastructure.list.primitive.DoubleCollection;
import ltd.qubit.commons.datastructure.list.primitive.DoubleIterator;
import ltd.qubit.commons.lang.Argument;

/**
 * An unmodifiable version of {@link DoubleCollection}.
 *
 * @author Haixing Hu
 */
public class UnmodifiableDoubleCollection extends AbstractDoubleCollection {

  private static final long serialVersionUID = 8664528441655027186L;

  /**
   * Wraps a {@link DoubleCollection} as an {@link UnmodifiableDoubleCollection}.
   *
   * @param collection
   *     the {@link DoubleCollection} to be wrap.
   * @return an {@link UnmodifiableDoubleCollection} wrapping the specified collection.
   */
  public static UnmodifiableDoubleCollection wrap(final DoubleCollection collection) {
    if (collection instanceof UnmodifiableDoubleCollection) {
      return (UnmodifiableDoubleCollection) collection;
    } else {
      return new UnmodifiableDoubleCollection(collection);
    }
  }

  private final DoubleCollection collection;

  protected UnmodifiableDoubleCollection(final DoubleCollection collection) {
    this.collection = Argument.requireNonNull("collection", collection);
  }

  @Override
  public boolean add(final double element) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean addAll(final DoubleCollection c) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void clear() {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean contains(final double element) {
    return collection.contains(element);
  }

  @Override
  public boolean containsAll(final DoubleCollection c) {
    return collection.containsAll(c);
  }

  @Override
  public boolean isEmpty() {
    return collection.isEmpty();
  }

  @Override
  public DoubleIterator iterator() {
    return new UnmodifiableDoubleIterator(collection.iterator());
  }

  @Override
  public boolean removeAll(final DoubleCollection c) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean remove(final double element) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean retainAll(final DoubleCollection c) {
    throw new UnsupportedOperationException();
  }

  @Override
  public int size() {
    return collection.size();
  }

  @Override
  public double[] toArray() {
    return collection.toArray();
  }

  @Override
  public double[] toArray(final double[] a) {
    return collection.toArray(a);
  }
}
