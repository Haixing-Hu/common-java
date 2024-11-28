////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.datastructure.list.primitive.adaptor;

import ltd.qubit.commons.datastructure.list.primitive.AbstractShortCollection;
import ltd.qubit.commons.datastructure.list.primitive.ShortCollection;
import ltd.qubit.commons.datastructure.list.primitive.ShortIterator;
import ltd.qubit.commons.datastructure.list.primitive.ShortList;
import ltd.qubit.commons.datastructure.list.primitive.ShortListIterator;

import static ltd.qubit.commons.lang.Argument.requireNonNull;

/**
 * An unmodifiable version of {@link ShortList}.
 *
 * @author Haixing Hu
 */
public final class UnmodifiableShortList extends AbstractShortCollection
    implements ShortList {

  private static final long serialVersionUID = -1920811357371072622L;

  /**
   * Wraps a {@link ShortList} as an {@link UnmodifiableShortList}.
   *
   * @param list
   *     the {@link ShortList} to be wrap.
   * @return an {@link UnmodifiableShortList} wrapping the specified collection.
   */
  public static UnmodifiableShortList wrap(final ShortList list) {
    if (list instanceof UnmodifiableShortList) {
      return (UnmodifiableShortList) list;
    } else {
      return new UnmodifiableShortList(list);
    }
  }

  private final ShortList list;

  protected UnmodifiableShortList(final ShortList list) {
    this.list = requireNonNull("list", list);
  }

  @Override
  public void clear() {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean contains(final short element) {
    return list.contains(element);
  }

  @Override
  public boolean containsAll(final ShortCollection c) {
    return list.containsAll(c);
  }

  @Override
  public boolean isEmpty() {
    return list.isEmpty();
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
    return list.size();
  }

  @Override
  public short[] toArray() {
    return list.toArray();
  }

  @Override
  public short[] toArray(final short[] a) {
    return list.toArray(a);
  }

  @Override
  public boolean add(final short element) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void add(final int index, final short element) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean addAll(final ShortCollection c) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean addAll(final int index, final ShortCollection collection) {
    throw new UnsupportedOperationException();
  }

  @Override
  public short get(final int index) {
    return list.get(index);
  }

  @Override
  public int indexOf(final short element) {
    return list.indexOf(element);
  }

  @Override
  public ShortIterator iterator() {
    return new UnmodifiableShortIterator(list.iterator());
  }

  @Override
  public int lastIndexOf(final short element) {
    return list.lastIndexOf(element);
  }

  @Override
  public ShortListIterator listIterator() {
    return new UnmodifiableShortListIterator(list.listIterator());
  }

  @Override
  public ShortListIterator listIterator(final int index) {
    return new UnmodifiableShortListIterator(list.listIterator(index));
  }

  @Override
  public short removeAt(final int index) {
    throw new UnsupportedOperationException();
  }

  @Override
  public short set(final int index, final short element) {
    throw new UnsupportedOperationException();
  }

  @Override
  public ShortList subList(final int fromIndex, final int toIndex) {
    return new UnmodifiableShortList(list.subList(fromIndex, toIndex));
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
