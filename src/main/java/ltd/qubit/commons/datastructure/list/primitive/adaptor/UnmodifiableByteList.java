////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.datastructure.list.primitive.adaptor;

import ltd.qubit.commons.datastructure.list.primitive.AbstractByteCollection;
import ltd.qubit.commons.datastructure.list.primitive.ByteCollection;
import ltd.qubit.commons.datastructure.list.primitive.ByteIterator;
import ltd.qubit.commons.datastructure.list.primitive.ByteList;
import ltd.qubit.commons.datastructure.list.primitive.ByteListIterator;
import ltd.qubit.commons.lang.Argument;

/**
 * An unmodifiable version of {@link ByteList}.
 *
 * @author Haixing Hu
 */
public final class UnmodifiableByteList extends AbstractByteCollection
    implements ByteList {

  private static final long serialVersionUID = -2449447720150271338L;

  /**
   * Wraps a {@link ByteList} as an {@link UnmodifiableByteList}.
   *
   * @param list
   *     the {@link ByteList} to be wrap.
   * @return an {@link UnmodifiableByteList} wrapping the specified collection.
   */
  public static UnmodifiableByteList wrap(final ByteList list) {
    if (list instanceof UnmodifiableByteList) {
      return (UnmodifiableByteList) list;
    } else {
      return new UnmodifiableByteList(list);
    }
  }

  private final ByteList list;

  protected UnmodifiableByteList(final ByteList list) {
    this.list = Argument.requireNonNull("list", list);
  }

  @Override
  public void clear() {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean contains(final byte element) {
    return list.contains(element);
  }

  @Override
  public boolean containsAll(final ByteCollection c) {
    return list.containsAll(c);
  }

  @Override
  public boolean isEmpty() {
    return list.isEmpty();
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
    return list.size();
  }

  @Override
  public byte[] toArray() {
    return list.toArray();
  }

  @Override
  public byte[] toArray(final byte[] a) {
    return list.toArray(a);
  }

  @Override
  public boolean add(final byte element) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void add(final int index, final byte element) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean addAll(final ByteCollection c) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean addAll(final int index, final ByteCollection collection) {
    throw new UnsupportedOperationException();
  }

  @Override
  public byte get(final int index) {
    return list.get(index);
  }

  @Override
  public int indexOf(final byte element) {
    return list.indexOf(element);
  }

  @Override
  public ByteIterator iterator() {
    return new UnmodifiableByteIterator(list.iterator());
  }

  @Override
  public int lastIndexOf(final byte element) {
    return list.lastIndexOf(element);
  }

  @Override
  public ByteListIterator listIterator() {
    return new UnmodifiableByteListIterator(list.listIterator());
  }

  @Override
  public ByteListIterator listIterator(final int index) {
    return new UnmodifiableByteListIterator(list.listIterator(index));
  }

  @Override
  public byte removeAt(final int index) {
    throw new UnsupportedOperationException();
  }

  @Override
  public byte set(final int index, final byte element) {
    throw new UnsupportedOperationException();
  }

  @Override
  public ByteList subList(final int fromIndex, final int toIndex) {
    return new UnmodifiableByteList(list.subList(fromIndex, toIndex));
  }

  @Override
  public void sort() {
    throw new UnsupportedOperationException();
  }

  @Override
  public void unique() {
    throw new UnsupportedOperationException();
  }
}
