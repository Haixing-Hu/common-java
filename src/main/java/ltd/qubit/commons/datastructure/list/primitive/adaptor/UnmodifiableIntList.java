////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.datastructure.list.primitive.adaptor;

import ltd.qubit.commons.datastructure.list.primitive.AbstractIntCollection;
import ltd.qubit.commons.datastructure.list.primitive.IntCollection;
import ltd.qubit.commons.datastructure.list.primitive.IntIterator;
import ltd.qubit.commons.datastructure.list.primitive.IntList;
import ltd.qubit.commons.datastructure.list.primitive.IntListIterator;
import ltd.qubit.commons.lang.Argument;

/**
 * An unmodifiable version of {@link IntList}.
 *
 * @author Haixing Hu
 */
public final class UnmodifiableIntList extends AbstractIntCollection
    implements IntList {

  private static final long serialVersionUID = -3644054160668454040L;

  /**
   * Wraps a {@link IntList} as an {@link UnmodifiableIntList}.
   *
   * @param list
   *     the {@link IntList} to be wrap.
   * @return an {@link UnmodifiableIntList} wrapping the specified collection.
   */
  public static UnmodifiableIntList wrap(final IntList list) {
    if (list instanceof UnmodifiableIntList) {
      return (UnmodifiableIntList) list;
    } else {
      return new UnmodifiableIntList(list);
    }
  }

  private final IntList list;

  protected UnmodifiableIntList(final IntList list) {
    this.list = Argument.requireNonNull("list", list);
  }

  @Override
  public void clear() {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean contains(final int element) {
    return list.contains(element);
  }

  @Override
  public boolean containsAll(final IntCollection c) {
    return list.containsAll(c);
  }

  @Override
  public boolean isEmpty() {
    return list.isEmpty();
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
    return list.size();
  }

  @Override
  public int[] toArray() {
    return list.toArray();
  }

  @Override
  public int[] toArray(final int[] a) {
    return list.toArray(a);
  }

  @Override
  public boolean add(final int element) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void add(final int index, final int element) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean addAll(final IntCollection c) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean addAll(final int index, final IntCollection collection) {
    throw new UnsupportedOperationException();
  }

  @Override
  public int get(final int index) {
    return list.get(index);
  }

  @Override
  public int indexOf(final int element) {
    return list.indexOf(element);
  }

  @Override
  public IntIterator iterator() {
    return new UnmodifiableIntIterator(list.iterator());
  }

  @Override
  public int lastIndexOf(final int element) {
    return list.lastIndexOf(element);
  }

  @Override
  public IntListIterator listIterator() {
    return new UnmodifiableIntListIterator(list.listIterator());
  }

  @Override
  public IntListIterator listIterator(final int index) {
    return new UnmodifiableIntListIterator(list.listIterator(index));
  }

  @Override
  public int removeAt(final int index) {
    throw new UnsupportedOperationException();
  }

  @Override
  public int set(final int index, final int element) {
    throw new UnsupportedOperationException();
  }

  @Override
  public IntList subList(final int fromIndex, final int toIndex) {
    return new UnmodifiableIntList(list.subList(fromIndex, toIndex));
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
