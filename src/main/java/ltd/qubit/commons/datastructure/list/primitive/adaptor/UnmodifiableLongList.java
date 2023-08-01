////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.datastructure.list.primitive.adaptor;

import ltd.qubit.commons.datastructure.list.primitive.AbstractLongCollection;
import ltd.qubit.commons.datastructure.list.primitive.LongCollection;
import ltd.qubit.commons.datastructure.list.primitive.LongIterator;
import ltd.qubit.commons.datastructure.list.primitive.LongList;
import ltd.qubit.commons.datastructure.list.primitive.LongListIterator;

import static ltd.qubit.commons.lang.Argument.requireNonNull;

/**
 * An unmodifiable version of {@link LongList}.
 *
 * @author Haixing Hu
 */
public final class UnmodifiableLongList extends AbstractLongCollection
    implements LongList {

  private static final long serialVersionUID = -7024238193987882356L;

  /**
   * Wraps a {@link LongList} as an {@link UnmodifiableLongList}.
   *
   * @param list
   *     the {@link LongList} to be wrap.
   * @return an {@link UnmodifiableLongList} wrapping the specified collection.
   */
  public static UnmodifiableLongList wrap(final LongList list) {
    if (list instanceof UnmodifiableLongList) {
      return (UnmodifiableLongList) list;
    } else {
      return new UnmodifiableLongList(list);
    }
  }

  private final LongList list;

  protected UnmodifiableLongList(final LongList list) {
    this.list = requireNonNull("list", list);
  }

  @Override
  public void clear() {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean contains(final long element) {
    return list.contains(element);
  }

  @Override
  public boolean containsAll(final LongCollection c) {
    return list.containsAll(c);
  }

  @Override
  public boolean isEmpty() {
    return list.isEmpty();
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
    return list.size();
  }

  @Override
  public long[] toArray() {
    return list.toArray();
  }

  @Override
  public long[] toArray(final long[] a) {
    return list.toArray(a);
  }

  @Override
  public boolean add(final long element) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void add(final int index, final long element) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean addAll(final LongCollection c) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean addAll(final int index, final LongCollection collection) {
    throw new UnsupportedOperationException();
  }

  @Override
  public long get(final int index) {
    return list.get(index);
  }

  @Override
  public int indexOf(final long element) {
    return list.indexOf(element);
  }

  @Override
  public LongIterator iterator() {
    return new UnmodifiableLongIterator(list.iterator());
  }

  @Override
  public int lastIndexOf(final long element) {
    return list.lastIndexOf(element);
  }

  @Override
  public LongListIterator listIterator() {
    return new UnmodifiableLongListIterator(list.listIterator());
  }

  @Override
  public LongListIterator listIterator(final int index) {
    return new UnmodifiableLongListIterator(list.listIterator(index));
  }

  @Override
  public long removeAt(final int index) {
    throw new UnsupportedOperationException();
  }

  @Override
  public long set(final int index, final long element) {
    throw new UnsupportedOperationException();
  }

  @Override
  public LongList subList(final int fromIndex, final int toIndex) {
    return new UnmodifiableLongList(list.subList(fromIndex, toIndex));
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
