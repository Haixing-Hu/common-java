////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.datastructure.list.primitive.adaptor;

import ltd.qubit.commons.datastructure.list.primitive.AbstractBooleanCollection;
import ltd.qubit.commons.datastructure.list.primitive.BooleanCollection;
import ltd.qubit.commons.datastructure.list.primitive.BooleanIterator;

import static ltd.qubit.commons.lang.Argument.requireNonNull;

/**
 * An unmodifiable version of {@link BooleanCollection}.
 *
 * @author Haixing Hu
 */
public final class UnmodifiableBooleanCollection extends AbstractBooleanCollection {

  private static final long serialVersionUID = -6830844026182836661L;

  /**
   * Wraps a {@link BooleanCollection} as an {@link UnmodifiableBooleanCollection}.
   *
   * @param collection
   *     the {@link BooleanCollection} to be wrap.
   * @return an {@link UnmodifiableBooleanCollection} wrapping the specified collection.
   */
  public static UnmodifiableBooleanCollection wrap(final BooleanCollection collection) {
    if (collection instanceof UnmodifiableBooleanCollection) {
      return (UnmodifiableBooleanCollection) collection;
    } else {
      return new UnmodifiableBooleanCollection(collection);
    }
  }

  private final BooleanCollection collection;

  protected UnmodifiableBooleanCollection(final BooleanCollection collection) {
    this.collection = requireNonNull("collection", collection);
  }

  @Override
  public boolean add(final boolean element) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean addAll(final BooleanCollection c) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void clear() {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean contains(final boolean element) {
    return collection.contains(element);
  }

  @Override
  public boolean containsAll(final BooleanCollection c) {
    return collection.containsAll(c);
  }

  @Override
  public boolean isEmpty() {
    return collection.isEmpty();
  }

  @Override
  public BooleanIterator iterator() {
    return new UnmodifiableBooleanIterator(collection.iterator());
  }

  @Override
  public boolean removeAll(final BooleanCollection c) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean remove(final boolean element) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean retainAll(final BooleanCollection c) {
    throw new UnsupportedOperationException();
  }

  @Override
  public int size() {
    return collection.size();
  }

  @Override
  public boolean[] toArray() {
    return collection.toArray();
  }

  @Override
  public boolean[] toArray(final boolean[] a) {
    return collection.toArray(a);
  }
}
