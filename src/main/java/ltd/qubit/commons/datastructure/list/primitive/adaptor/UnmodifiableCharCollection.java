////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.datastructure.list.primitive.adaptor;

import ltd.qubit.commons.datastructure.list.primitive.AbstractCharCollection;
import ltd.qubit.commons.datastructure.list.primitive.CharCollection;
import ltd.qubit.commons.datastructure.list.primitive.CharIterator;
import ltd.qubit.commons.lang.Argument;

/**
 * An unmodifiable version of {@link CharCollection}.
 *
 * @author Haixing Hu
 */
public class UnmodifiableCharCollection extends AbstractCharCollection {

  private static final long serialVersionUID = -1935352954402707861L;

  /**
   * Wraps a {@link CharCollection} as an {@link UnmodifiableCharCollection}.
   *
   * @param collection
   *     the {@link CharCollection} to be wrap.
   * @return an {@link UnmodifiableCharCollection} wrapping the specified
   *     collection.
   */
  public static UnmodifiableCharCollection wrap(final CharCollection collection) {
    if (collection instanceof UnmodifiableCharCollection) {
      return (UnmodifiableCharCollection) collection;
    } else {
      return new UnmodifiableCharCollection(collection);
    }
  }

  private final CharCollection collection;

  protected UnmodifiableCharCollection(final CharCollection collection) {
    this.collection = Argument.requireNonNull("collection", collection);
  }

  @Override
  public boolean add(final char element) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean addAll(final CharCollection c) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void clear() {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean contains(final char element) {
    return collection.contains(element);
  }

  @Override
  public boolean containsAll(final CharCollection c) {
    return collection.containsAll(c);
  }

  @Override
  public boolean isEmpty() {
    return collection.isEmpty();
  }

  @Override
  public CharIterator iterator() {
    return new UnmodifiableCharIterator(collection.iterator());
  }

  @Override
  public boolean removeAll(final CharCollection c) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean remove(final char element) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean retainAll(final CharCollection c) {
    throw new UnsupportedOperationException();
  }

  @Override
  public int size() {
    return collection.size();
  }

  @Override
  public char[] toArray() {
    return collection.toArray();
  }

  @Override
  public char[] toArray(final char[] a) {
    return collection.toArray(a);
  }
}
