////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.datastructure.list.primitive.adaptor;

import ltd.qubit.commons.datastructure.list.primitive.AbstractIntCollection;
import ltd.qubit.commons.datastructure.list.primitive.IntCollection;
import ltd.qubit.commons.datastructure.list.primitive.IntIterator;

import static ltd.qubit.commons.lang.Argument.requireNonNull;

/**
 * An unmodifiable version of {@link IntCollection}.
 *
 * @author Haixing Hu
 */
public class UnmodifiableIntCollection extends AbstractIntCollection {

  private static final long serialVersionUID = 6766166988906612645L;

  /**
   * Wraps a {@link IntCollection} as an {@link UnmodifiableIntCollection}.
   *
   * @param collection
   *     the {@link IntCollection} to be wrap.
   * @return an {@link UnmodifiableIntCollection} wrapping the specified collection.
   */
  public static UnmodifiableIntCollection wrap(final IntCollection collection) {
    if (collection instanceof UnmodifiableIntCollection) {
      return (UnmodifiableIntCollection) collection;
    } else {
      return new UnmodifiableIntCollection(collection);
    }
  }

  private final IntCollection collection;

  protected UnmodifiableIntCollection(final IntCollection collection) {
    this.collection = requireNonNull("collection", collection);
  }

  @Override
  public boolean add(final int element) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean addAll(final IntCollection c) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void clear() {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean contains(final int element) {
    return collection.contains(element);
  }

  @Override
  public boolean containsAll(final IntCollection c) {
    return collection.containsAll(c);
  }

  @Override
  public boolean isEmpty() {
    return collection.isEmpty();
  }

  @Override
  public IntIterator iterator() {
    return new UnmodifiableIntIterator(collection.iterator());
  }

  @Override
  public boolean removeAll(final IntCollection c) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean remove(final int element) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean retainAll(final IntCollection c) {
    throw new UnsupportedOperationException();
  }

  @Override
  public int size() {
    return collection.size();
  }

  @Override
  public int[] toArray() {
    return collection.toArray();
  }

  @Override
  public int[] toArray(final int[] a) {
    return collection.toArray(a);
  }
}
