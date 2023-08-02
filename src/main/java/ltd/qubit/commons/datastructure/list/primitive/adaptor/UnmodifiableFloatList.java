////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.datastructure.list.primitive.adaptor;

import ltd.qubit.commons.datastructure.list.primitive.AbstractFloatCollection;
import ltd.qubit.commons.datastructure.list.primitive.FloatCollection;
import ltd.qubit.commons.datastructure.list.primitive.FloatIterator;
import ltd.qubit.commons.datastructure.list.primitive.FloatList;
import ltd.qubit.commons.datastructure.list.primitive.FloatListIterator;
import ltd.qubit.commons.lang.Argument;

/**
 * An unmodifiable version of {@link FloatList}.
 *
 * @author Haixing Hu
 */
public final class UnmodifiableFloatList extends AbstractFloatCollection
    implements FloatList {

  private static final long serialVersionUID = 5981841099976104230L;

  /**
   * Wraps a {@link FloatList} as an {@link UnmodifiableFloatList}.
   *
   * @param list
   *     the {@link FloatList} to be wrap.
   * @return an {@link UnmodifiableFloatList} wrapping the specified collection.
   */
  public static UnmodifiableFloatList wrap(final FloatList list) {
    if (list instanceof UnmodifiableFloatList) {
      return (UnmodifiableFloatList) list;
    } else {
      return new UnmodifiableFloatList(list);
    }
  }

  private final FloatList list;

  protected UnmodifiableFloatList(final FloatList list) {
    this.list = Argument.requireNonNull("list", list);
  }

  @Override
  public void clear() {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean contains(final float element) {
    return list.contains(element);
  }

  @Override
  public boolean containsAll(final FloatCollection c) {
    return list.containsAll(c);
  }

  @Override
  public boolean isEmpty() {
    return list.isEmpty();
  }

  @Override
  public boolean removeAll(final FloatCollection c) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean remove(final float element) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean retainAll(final FloatCollection c) {
    throw new UnsupportedOperationException();
  }

  @Override
  public int size() {
    return list.size();
  }

  @Override
  public float[] toArray() {
    return list.toArray();
  }

  @Override
  public float[] toArray(final float[] a) {
    return list.toArray(a);
  }

  @Override
  public boolean add(final float element) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void add(final int index, final float element) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean addAll(final FloatCollection c) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean addAll(final int index, final FloatCollection collection) {
    throw new UnsupportedOperationException();
  }

  @Override
  public float get(final int index) {
    return list.get(index);
  }

  @Override
  public int indexOf(final float element) {
    return list.indexOf(element);
  }

  @Override
  public FloatIterator iterator() {
    return new UnmodifiableFloatIterator(list.iterator());
  }

  @Override
  public int lastIndexOf(final float element) {
    return list.lastIndexOf(element);
  }

  @Override
  public FloatListIterator listIterator() {
    return new UnmodifiableFloatListIterator(list.listIterator());
  }

  @Override
  public FloatListIterator listIterator(final int index) {
    return new UnmodifiableFloatListIterator(list.listIterator(index));
  }

  @Override
  public float removeAt(final int index) {
    throw new UnsupportedOperationException();
  }

  @Override
  public float set(final int index, final float element) {
    throw new UnsupportedOperationException();
  }

  @Override
  public FloatList subList(final int fromIndex, final int toIndex) {
    return new UnmodifiableFloatList(list.subList(fromIndex, toIndex));
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
