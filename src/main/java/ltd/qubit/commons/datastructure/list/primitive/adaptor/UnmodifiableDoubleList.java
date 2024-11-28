////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.datastructure.list.primitive.adaptor;

import ltd.qubit.commons.datastructure.list.primitive.AbstractDoubleCollection;
import ltd.qubit.commons.datastructure.list.primitive.DoubleCollection;
import ltd.qubit.commons.datastructure.list.primitive.DoubleIterator;
import ltd.qubit.commons.datastructure.list.primitive.DoubleList;
import ltd.qubit.commons.datastructure.list.primitive.DoubleListIterator;
import ltd.qubit.commons.lang.Argument;

/**
 * An unmodifiable version of {@link DoubleList}.
 *
 * @author Haixing Hu
 */
public final class UnmodifiableDoubleList extends AbstractDoubleCollection
    implements DoubleList {

  private static final long serialVersionUID = -7550004170539165174L;

  /**
   * Wraps a {@link DoubleList} as an {@link UnmodifiableDoubleList}.
   *
   * @param list
   *     the {@link DoubleList} to be wrap.
   * @return an {@link UnmodifiableDoubleList} wrapping the specified collection.
   */
  public static UnmodifiableDoubleList wrap(final DoubleList list) {
    if (list instanceof UnmodifiableDoubleList) {
      return (UnmodifiableDoubleList) list;
    } else {
      return new UnmodifiableDoubleList(list);
    }
  }

  private final DoubleList list;

  protected UnmodifiableDoubleList(final DoubleList list) {
    this.list = Argument.requireNonNull("list", list);
  }

  @Override
  public void clear() {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean contains(final double element) {
    return list.contains(element);
  }

  @Override
  public boolean containsAll(final DoubleCollection c) {
    return list.containsAll(c);
  }

  @Override
  public boolean isEmpty() {
    return list.isEmpty();
  }

  @Override
  public boolean removeAll(final DoubleCollection c) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean remove(final double element) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean retainAll(final DoubleCollection c) {
    throw new UnsupportedOperationException();
  }

  @Override
  public int size() {
    return list.size();
  }

  @Override
  public double[] toArray() {
    return list.toArray();
  }

  @Override
  public double[] toArray(final double[] a) {
    return list.toArray(a);
  }

  @Override
  public boolean add(final double element) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void add(final int index, final double element) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean addAll(final DoubleCollection c) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean addAll(final int index, final DoubleCollection collection) {
    throw new UnsupportedOperationException();
  }

  @Override
  public double get(final int index) {
    return list.get(index);
  }

  @Override
  public int indexOf(final double element) {
    return list.indexOf(element);
  }

  @Override
  public DoubleIterator iterator() {
    return new UnmodifiableDoubleIterator(list.iterator());
  }

  @Override
  public int lastIndexOf(final double element) {
    return list.lastIndexOf(element);
  }

  @Override
  public DoubleListIterator listIterator() {
    return new UnmodifiableDoubleListIterator(list.listIterator());
  }

  @Override
  public DoubleListIterator listIterator(final int index) {
    return new UnmodifiableDoubleListIterator(list.listIterator(index));
  }

  @Override
  public double removeAt(final int index) {
    throw new UnsupportedOperationException();
  }

  @Override
  public double set(final int index, final double element) {
    throw new UnsupportedOperationException();
  }

  @Override
  public DoubleList subList(final int fromIndex, final int toIndex) {
    return new UnmodifiableDoubleList(list.subList(fromIndex, toIndex));
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
