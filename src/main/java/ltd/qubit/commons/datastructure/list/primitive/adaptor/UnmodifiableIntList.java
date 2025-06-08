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

import ltd.qubit.commons.datastructure.list.primitive.AbstractIntCollection;
import ltd.qubit.commons.datastructure.list.primitive.IntCollection;
import ltd.qubit.commons.datastructure.list.primitive.IntIterator;
import ltd.qubit.commons.datastructure.list.primitive.IntList;
import ltd.qubit.commons.datastructure.list.primitive.IntListIterator;
import ltd.qubit.commons.lang.Argument;

/**
 * {@link IntList} 的不可修改版本。
 *
 * @author 胡海星
 */
public final class UnmodifiableIntList extends AbstractIntCollection
    implements IntList {

  @Serial
  private static final long serialVersionUID = -3644054160668454040L;

  /**
   * 将 {@link IntList} 包装为 {@link UnmodifiableIntList}。
   *
   * @param list
   *     要包装的 {@link IntList}。
   * @return 包装指定集合的 {@link UnmodifiableIntList}。
   */
  public static UnmodifiableIntList wrap(final IntList list) {
    if (list instanceof UnmodifiableIntList) {
      return (UnmodifiableIntList) list;
    } else {
      return new UnmodifiableIntList(list);
    }
  }

  /**
   * 被包装的列表。
   */
  private final IntList list;

  /**
   * 构造一个 {@link UnmodifiableIntList}。
   *
   * @param list
   *     要包装的 {@link IntList}。
   */
  protected UnmodifiableIntList(final IntList list) {
    this.list = Argument.requireNonNull("list", list);
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
  public boolean contains(final int element) {
    return list.contains(element);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean containsAll(final IntCollection c) {
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
  public boolean removeAll(final IntCollection c) {
    throw new UnsupportedOperationException();
  }

  /**
   * 不支持此操作。
   *
   * @throws UnsupportedOperationException
   *     总是抛出。
   */
  @Override
  public boolean remove(final int element) {
    throw new UnsupportedOperationException();
  }

  /**
   * 不支持此操作。
   *
   * @throws UnsupportedOperationException
   *     总是抛出。
   */
  @Override
  public boolean retainAll(final IntCollection c) {
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
  public int[] toArray() {
    return list.toArray();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int[] toArray(final int[] a) {
    return list.toArray(a);
  }

  /**
   * 不支持此操作。
   *
   * @throws UnsupportedOperationException
   *     总是抛出。
   */
  @Override
  public boolean add(final int element) {
    throw new UnsupportedOperationException();
  }

  /**
   * 不支持此操作。
   *
   * @throws UnsupportedOperationException
   *     总是抛出。
   */
  @Override
  public void add(final int index, final int element) {
    throw new UnsupportedOperationException();
  }

  /**
   * 不支持此操作。
   *
   * @throws UnsupportedOperationException
   *     总是抛出。
   */
  @Override
  public boolean addAll(final IntCollection c) {
    throw new UnsupportedOperationException();
  }

  /**
   * 不支持此操作。
   *
   * @throws UnsupportedOperationException
   *     总是抛出。
   */
  @Override
  public boolean addAll(final int index, final IntCollection collection) {
    throw new UnsupportedOperationException();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int get(final int index) {
    return list.get(index);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int indexOf(final int element) {
    return list.indexOf(element);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IntIterator iterator() {
    return new UnmodifiableIntIterator(list.iterator());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int lastIndexOf(final int element) {
    return list.lastIndexOf(element);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IntListIterator listIterator() {
    return new UnmodifiableIntListIterator(list.listIterator());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IntListIterator listIterator(final int index) {
    return new UnmodifiableIntListIterator(list.listIterator(index));
  }

  /**
   * 不支持此操作。
   *
   * @throws UnsupportedOperationException
   *     总是抛出。
   */
  @Override
  public int removeAt(final int index) {
    throw new UnsupportedOperationException();
  }

  /**
   * 不支持此操作。
   *
   * @throws UnsupportedOperationException
   *     总是抛出。
   */
  @Override
  public int set(final int index, final int element) {
    throw new UnsupportedOperationException();
  }

  /**
   * 不支持此操作。
   *
   * @throws UnmodifiableIntListIterator
   *     总是抛出。
   */
  @Override
  public IntList subList(final int fromIndex, final int toIndex) {
    return new UnmodifiableIntList(list.subList(fromIndex, toIndex));
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
