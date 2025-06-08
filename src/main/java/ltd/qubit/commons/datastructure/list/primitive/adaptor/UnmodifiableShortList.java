////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.datastructure.list.primitive.adaptor;

import java.io.Serial;

import ltd.qubit.commons.datastructure.list.primitive.AbstractShortCollection;
import ltd.qubit.commons.datastructure.list.primitive.ShortCollection;
import ltd.qubit.commons.datastructure.list.primitive.ShortIterator;
import ltd.qubit.commons.datastructure.list.primitive.ShortList;
import ltd.qubit.commons.datastructure.list.primitive.ShortListIterator;

import static ltd.qubit.commons.lang.Argument.requireNonNull;

/**
 * {@link ShortList} 的不可修改版本。
 *
 * @author 胡海星
 */
public final class UnmodifiableShortList extends AbstractShortCollection
    implements ShortList {

  @Serial
  private static final long serialVersionUID = -1920811357371072622L;

  /**
   * 将 {@link ShortList} 包装为 {@link UnmodifiableShortList}。
   *
   * @param list
   *     要包装的 {@link ShortList}。
   * @return 包装指定集合的 {@link UnmodifiableShortList}。
   */
  public static UnmodifiableShortList wrap(final ShortList list) {
    if (list instanceof UnmodifiableShortList) {
      return (UnmodifiableShortList) list;
    } else {
      return new UnmodifiableShortList(list);
    }
  }

  /**
   * 被包装的列表。
   */
  private final ShortList list;

  /**
   * 构造一个 {@link UnmodifiableShortList}。
   *
   * @param list
   *     要包装的 {@link ShortList}。
   */
  protected UnmodifiableShortList(final ShortList list) {
    this.list = requireNonNull("list", list);
  }

  /**
   * 不支持此操作。
   *
   * @throws UnsupportedOperationException
   *     总是抛出。
   */
  @Override
  public void clear() {
    throw new UnsupportedOperationException();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean contains(final short element) {
    return list.contains(element);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean containsAll(final ShortCollection c) {
    return list.containsAll(c);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isEmpty() {
    return list.isEmpty();
  }

  /**
   * 不支持此操作。
   *
   * @throws UnsupportedOperationException
   *     总是抛出。
   */
  @Override
  public boolean removeAll(final ShortCollection c) {
    throw new UnsupportedOperationException();
  }

  /**
   * 不支持此操作。
   *
   * @throws UnsupportedOperationException
   *     总是抛出。
   */
  @Override
  public boolean remove(final short element) {
    throw new UnsupportedOperationException();
  }

  /**
   * 不支持此操作。
   *
   * @throws UnsupportedOperationException
   *     总是抛出。
   */
  @Override
  public boolean retainAll(final ShortCollection c) {
    throw new UnsupportedOperationException();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int size() {
    return list.size();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public short[] toArray() {
    return list.toArray();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public short[] toArray(final short[] a) {
    return list.toArray(a);
  }

  /**
   * 不支持此操作。
   *
   * @throws UnsupportedOperationException
   *     总是抛出。
   */
  @Override
  public boolean add(final short element) {
    throw new UnsupportedOperationException();
  }

  /**
   * 不支持此操作。
   *
   * @throws UnsupportedOperationException
   *     总是抛出。
   */
  @Override
  public void add(final int index, final short element) {
    throw new UnsupportedOperationException();
  }

  /**
   * 不支持此操作。
   *
   * @throws UnsupportedOperationException
   *     总是抛出。
   */
  @Override
  public boolean addAll(final ShortCollection c) {
    throw new UnsupportedOperationException();
  }

  /**
   * 不支持此操作。
   *
   * @throws UnsupportedOperationException
   *     总是抛出。
   */
  @Override
  public boolean addAll(final int index, final ShortCollection collection) {
    throw new UnsupportedOperationException();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public short get(final int index) {
    return list.get(index);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int indexOf(final short element) {
    return list.indexOf(element);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ShortIterator iterator() {
    return new UnmodifiableShortIterator(list.iterator());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int lastIndexOf(final short element) {
    return list.lastIndexOf(element);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ShortListIterator listIterator() {
    return new UnmodifiableShortListIterator(list.listIterator());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ShortListIterator listIterator(final int index) {
    return new UnmodifiableShortListIterator(list.listIterator(index));
  }

  /**
   * 不支持此操作。
   *
   * @throws UnsupportedOperationException
   *     总是抛出。
   */
  @Override
  public short removeAt(final int index) {
    throw new UnsupportedOperationException();
  }

  /**
   * 不支持此操作。
   *
   * @throws UnsupportedOperationException
   *     总是抛出。
   */
  @Override
  public short set(final int index, final short element) {
    throw new UnsupportedOperationException();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ShortList subList(final int fromIndex, final int toIndex) {
    return new UnmodifiableShortList(list.subList(fromIndex, toIndex));
  }

  /**
   * 不支持此操作。
   *
   * @throws UnsupportedOperationException
   *     总是抛出。
   */
  @Override
  public void sort() {
    throw new UnsupportedOperationException();
  }

  /**
   * 不支持此操作。
   *
   * @throws UnsupportedOperationException
   *     总是抛出。
   */
  @Override
  public void unique() {
    throw new UnsupportedOperationException();
  }
}
