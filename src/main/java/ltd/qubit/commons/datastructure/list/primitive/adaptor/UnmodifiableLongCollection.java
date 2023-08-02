////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.datastructure.list.primitive.adaptor;

import ltd.qubit.commons.datastructure.list.primitive.AbstractLongCollection;
import ltd.qubit.commons.datastructure.list.primitive.LongCollection;
import ltd.qubit.commons.datastructure.list.primitive.LongIterator;
import ltd.qubit.commons.lang.Argument;

/**
 * An unmodifiable version of {@link LongCollection}.
 *
 * @author Haixing Hu
 */
public class UnmodifiableLongCollection extends AbstractLongCollection {

  private static final long serialVersionUID = -4735199080766940445L;

  /**
   * Wraps a {@link LongCollection} as an {@link UnmodifiableLongCollection}.
   *
   * @param collection
   *     the {@link LongCollection} to be wrap.
   * @return an {@link UnmodifiableLongCollection} wrapping the specified collection.
   */
  public static UnmodifiableLongCollection wrap(final LongCollection collection) {
    if (collection instanceof UnmodifiableLongCollection) {
      return (UnmodifiableLongCollection) collection;
    } else {
      return new UnmodifiableLongCollection(collection);
    }
  }

  private final LongCollection collection;

  protected UnmodifiableLongCollection(final LongCollection collection) {
    this.collection = Argument.requireNonNull("collection", collection);
  }

  @Override
  public boolean add(final long element) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean addAll(final LongCollection c) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void clear() {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean contains(final long element) {
    return collection.contains(element);
  }

  @Override
  public boolean containsAll(final LongCollection c) {
    return collection.containsAll(c);
  }

  @Override
  public boolean isEmpty() {
    return collection.isEmpty();
  }

  @Override
  public LongIterator iterator() {
    return new UnmodifiableLongIterator(collection.iterator());
  }

  @Override
  public boolean removeAll(final LongCollection c) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean remove(final long element) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean retainAll(final LongCollection c) {
    throw new UnsupportedOperationException();
  }

  @Override
  public int size() {
    return collection.size();
  }

  @Override
  public long[] toArray() {
    return collection.toArray();
  }

  @Override
  public long[] toArray(final long[] a) {
    return collection.toArray(a);
  }
}
