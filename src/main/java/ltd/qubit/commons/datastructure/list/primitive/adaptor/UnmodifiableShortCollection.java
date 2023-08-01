////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.datastructure.list.primitive.adaptor;

import ltd.qubit.commons.datastructure.list.primitive.AbstractShortCollection;
import ltd.qubit.commons.datastructure.list.primitive.ShortCollection;
import ltd.qubit.commons.datastructure.list.primitive.ShortIterator;

import static ltd.qubit.commons.lang.Argument.requireNonNull;

/**
 * An unmodifiable version of {@link ShortCollection}.
 *
 * @author Haixing Hu
 */
public class UnmodifiableShortCollection extends AbstractShortCollection {

  private static final long serialVersionUID = -824703401053770770L;

  /**
   * Wraps a {@link ShortCollection} as an {@link UnmodifiableShortCollection}.
   *
   * @param collection
   *     the {@link ShortCollection} to be wrap.
   * @return an {@link UnmodifiableShortCollection} wrapping the specified collection.
   */
  public static UnmodifiableShortCollection wrap(final ShortCollection collection) {
    if (collection instanceof UnmodifiableShortCollection) {
      return (UnmodifiableShortCollection) collection;
    } else {
      return new UnmodifiableShortCollection(collection);
    }
  }

  private final ShortCollection collection;

  protected UnmodifiableShortCollection(final ShortCollection collection) {
    this.collection = requireNonNull("collection", collection);
  }

  @Override
  public boolean add(final short element) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean addAll(final ShortCollection c) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void clear() {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean contains(final short element) {
    return collection.contains(element);
  }

  @Override
  public boolean containsAll(final ShortCollection c) {
    return collection.containsAll(c);
  }

  @Override
  public boolean isEmpty() {
    return collection.isEmpty();
  }

  @Override
  public ShortIterator iterator() {
    return new UnmodifiableShortIterator(collection.iterator());
  }

  @Override
  public boolean removeAll(final ShortCollection c) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean remove(final short element) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean retainAll(final ShortCollection c) {
    throw new UnsupportedOperationException();
  }

  @Override
  public int size() {
    return collection.size();
  }

  @Override
  public short[] toArray() {
    return collection.toArray();
  }

  @Override
  public short[] toArray(final short[] a) {
    return collection.toArray(a);
  }
}
