////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.datastructure.list.primitive.adaptor;
import ltd.qubit.commons.datastructure.list.primitive.AbstractByteCollection;
import ltd.qubit.commons.datastructure.list.primitive.ByteCollection;
import ltd.qubit.commons.datastructure.list.primitive.ByteIterator;

import static ltd.qubit.commons.lang.Argument.requireNonNull;

/**
 * An unmodifiable version of {@link ByteCollection}.
 *
 * @author Haixing Hu
 */
public class UnmodifiableByteCollection extends AbstractByteCollection {

  private static final long serialVersionUID = -4825833429742656519L;

  /**
   * Wraps a {@link ByteCollection} as an {@link UnmodifiableByteCollection}.
   *
   * @param collection
   *     the {@link ByteCollection} to be wrap.
   * @return an {@link UnmodifiableByteCollection} wrapping the specified collection.
   */
  public static UnmodifiableByteCollection wrap(final ByteCollection collection) {
    if (collection instanceof UnmodifiableByteCollection) {
      return (UnmodifiableByteCollection) collection;
    } else {
      return new UnmodifiableByteCollection(collection);
    }
  }

  private final ByteCollection collection;

  protected UnmodifiableByteCollection(final ByteCollection collection) {
    this.collection = requireNonNull("collection", collection);
  }

  @Override
  public boolean add(final byte element) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean addAll(final ByteCollection c) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void clear() {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean contains(final byte element) {
    return collection.contains(element);
  }

  @Override
  public boolean containsAll(final ByteCollection c) {
    return collection.containsAll(c);
  }

  @Override
  public boolean isEmpty() {
    return collection.isEmpty();
  }

  @Override
  public ByteIterator iterator() {
    return new UnmodifiableByteIterator(collection.iterator());
  }

  @Override
  public boolean removeAll(final ByteCollection c) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean remove(final byte element) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean retainAll(final ByteCollection c) {
    throw new UnsupportedOperationException();
  }

  @Override
  public int size() {
    return collection.size();
  }

  @Override
  public byte[] toArray() {
    return collection.toArray();
  }

  @Override
  public byte[] toArray(final byte[] a) {
    return collection.toArray(a);
  }
}
