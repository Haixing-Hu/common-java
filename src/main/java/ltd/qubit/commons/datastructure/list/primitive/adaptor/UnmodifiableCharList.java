////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.datastructure.list.primitive.adaptor;

import ltd.qubit.commons.datastructure.list.primitive.AbstractCharCollection;
import ltd.qubit.commons.datastructure.list.primitive.CharCollection;
import ltd.qubit.commons.datastructure.list.primitive.CharIterator;
import ltd.qubit.commons.datastructure.list.primitive.CharList;
import ltd.qubit.commons.datastructure.list.primitive.CharListIterator;

import static ltd.qubit.commons.lang.Argument.requireNonNull;

/**
 * An unmodifiable version of {@link CharList}.
 *
 * @author Haixing Hu
 */
public final class UnmodifiableCharList extends AbstractCharCollection
    implements CharList {

  private static final long serialVersionUID = 6845530339493097526L;

  /**
   * Wraps a {@link CharList} as an {@link UnmodifiableCharList}.
   *
   * @param list
   *     the {@link CharList} to be wrap.
   * @return an {@link UnmodifiableCharList} wrapping the specified collection.
   */
  public static UnmodifiableCharList wrap(final CharList list) {
    if (list instanceof UnmodifiableCharList) {
      return (UnmodifiableCharList) list;
    } else {
      return new UnmodifiableCharList(list);
    }
  }

  private final CharList list;

  protected UnmodifiableCharList(final CharList list) {
    this.list = requireNonNull("list", list);
  }

  @Override
  public void clear() {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean contains(final char element) {
    return list.contains(element);
  }

  @Override
  public boolean containsAll(final CharCollection c) {
    return list.containsAll(c);
  }

  @Override
  public boolean isEmpty() {
    return list.isEmpty();
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
    return list.size();
  }

  @Override
  public char[] toArray() {
    return list.toArray();
  }

  @Override
  public char[] toArray(final char[] a) {
    return list.toArray(a);
  }

  @Override
  public boolean add(final char element) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void add(final int index, final char element) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean addAll(final CharCollection c) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean addAll(final int index, final CharCollection collection) {
    throw new UnsupportedOperationException();
  }

  @Override
  public char get(final int index) {
    return list.get(index);
  }

  @Override
  public int indexOf(final char element) {
    return list.indexOf(element);
  }

  @Override
  public CharIterator iterator() {
    return new UnmodifiableCharIterator(list.iterator());
  }

  @Override
  public int lastIndexOf(final char element) {
    return list.lastIndexOf(element);
  }

  @Override
  public CharListIterator listIterator() {
    return new UnmodifiableCharListIterator(list.listIterator());
  }

  @Override
  public CharListIterator listIterator(final int index) {
    return new UnmodifiableCharListIterator(list.listIterator(index));
  }

  @Override
  public char removeAt(final int index) {
    throw new UnsupportedOperationException();
  }

  @Override
  public char set(final int index, final char element) {
    throw new UnsupportedOperationException();
  }

  @Override
  public CharList subList(final int fromIndex, final int toIndex) {
    return new UnmodifiableCharList(list.subList(fromIndex, toIndex));
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
