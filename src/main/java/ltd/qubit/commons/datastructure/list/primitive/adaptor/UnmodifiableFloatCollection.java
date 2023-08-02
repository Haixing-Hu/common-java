////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.datastructure.list.primitive.adaptor;

import ltd.qubit.commons.datastructure.list.primitive.AbstractFloatCollection;
import ltd.qubit.commons.datastructure.list.primitive.FloatCollection;
import ltd.qubit.commons.datastructure.list.primitive.FloatIterator;

import static ltd.qubit.commons.lang.Argument.requireNonNull;

/**
 * An unmodifiable version of {@link FloatCollection}.
 *
 * @author Haixing Hu
 */
public class UnmodifiableFloatCollection extends AbstractFloatCollection {

  private static final long serialVersionUID = -3450029815907229739L;

  /**
   * Wraps a {@link FloatCollection} as an {@link UnmodifiableFloatCollection}.
   *
   * @param collection
   *     the {@link FloatCollection} to be wrap.
   * @return an {@link UnmodifiableFloatCollection} wrapping the specified collection.
   */
  public static UnmodifiableFloatCollection wrap(final FloatCollection collection) {
    if (collection instanceof UnmodifiableFloatCollection) {
      return (UnmodifiableFloatCollection) collection;
    } else {
      return new UnmodifiableFloatCollection(collection);
    }
  }

  private final FloatCollection collection;

  protected UnmodifiableFloatCollection(final FloatCollection collection) {
    this.collection = requireNonNull("collection", collection);
  }

  @Override
  public boolean add(final float element) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean addAll(final FloatCollection c) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void clear() {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean contains(final float element) {
    return collection.contains(element);
  }

  @Override
  public boolean containsAll(final FloatCollection c) {
    return collection.containsAll(c);
  }

  @Override
  public boolean isEmpty() {
    return collection.isEmpty();
  }

  @Override
  public FloatIterator iterator() {
    return new UnmodifiableFloatIterator(collection.iterator());
  }

  @Override
  public boolean removeAll(final FloatCollection c) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean remove(final float element) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean retainAll(final FloatCollection c) {
    throw new UnsupportedOperationException();
  }

  @Override
  public int size() {
    return collection.size();
  }

  @Override
  public float[] toArray() {
    return collection.toArray();
  }

  @Override
  public float[] toArray(final float[] a) {
    return collection.toArray(a);
  }
}
