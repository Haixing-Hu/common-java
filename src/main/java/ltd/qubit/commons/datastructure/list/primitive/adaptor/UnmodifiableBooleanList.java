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
import ltd.qubit.commons.datastructure.list.primitive.BooleanList;
import ltd.qubit.commons.datastructure.list.primitive.BooleanListIterator;

import static ltd.qubit.commons.lang.Argument.requireNonNull;

/**
 * An unmodifiable version of {@link BooleanList}.
 *
 * @author Haixing Hu
 */
public final class UnmodifiableBooleanList extends AbstractBooleanCollection
    implements BooleanList {

  private static final long serialVersionUID = -6395625209080471117L;

  /**
   * Wraps a {@link BooleanList} as an {@link UnmodifiableBooleanList}.
   *
   * @param list
   *     the {@link BooleanList} to be wrap.
   * @return an {@link UnmodifiableBooleanList} wrapping the specified collection.
   */
  public static UnmodifiableBooleanList wrap(final BooleanList list) {
    if (list instanceof UnmodifiableBooleanList) {
      return (UnmodifiableBooleanList) list;
    } else {
      return new UnmodifiableBooleanList(list);
    }
  }

  private final BooleanList list;

  protected UnmodifiableBooleanList(final BooleanList list) {
    this.list = requireNonNull("list", list);
  }

  @Override
  public void clear() {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean contains(final boolean element) {
    return list.contains(element);
  }

  @Override
  public boolean containsAll(final BooleanCollection c) {
    return list.containsAll(c);
  }

  @Override
  public boolean isEmpty() {
    return list.isEmpty();
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
    return list.size();
  }

  @Override
  public boolean[] toArray() {
    return list.toArray();
  }

  @Override
  public boolean[] toArray(final boolean[] a) {
    return list.toArray(a);
  }

  @Override
  public boolean add(final boolean element) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void add(final int index, final boolean element) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean addAll(final BooleanCollection c) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean addAll(final int index, final BooleanCollection collection) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean get(final int index) {
    return list.get(index);
  }

  @Override
  public int indexOf(final boolean element) {
    return list.indexOf(element);
  }

  @Override
  public BooleanIterator iterator() {
    return new UnmodifiableBooleanIterator(list.iterator());
  }

  @Override
  public int lastIndexOf(final boolean element) {
    return list.lastIndexOf(element);
  }

  @Override
  public BooleanListIterator listIterator() {
    return new UnmodifiableBooleanListIterator(list.listIterator());
  }

  @Override
  public BooleanListIterator listIterator(final int index) {
    return new UnmodifiableBooleanListIterator(list.listIterator(index));
  }

  @Override
  public boolean removeAt(final int index) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean set(final int index, final boolean element) {
    throw new UnsupportedOperationException();
  }

  @Override
  public BooleanList subList(final int fromIndex, final int toIndex) {
    return new UnmodifiableBooleanList(list.subList(fromIndex, toIndex));
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
