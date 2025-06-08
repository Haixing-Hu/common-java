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

import ltd.qubit.commons.datastructure.list.primitive.AbstractLongCollection;
import ltd.qubit.commons.datastructure.list.primitive.LongCollection;
import ltd.qubit.commons.datastructure.list.primitive.LongIterator;
import ltd.qubit.commons.datastructure.list.primitive.LongList;
import ltd.qubit.commons.datastructure.list.primitive.LongListIterator;

import static ltd.qubit.commons.lang.Argument.requireNonNull;

/**
 * {@link LongList} 的不可修改版本。
 *
 * @author 胡海星
 */
public final class UnmodifiableLongList extends AbstractLongCollection
    implements LongList {

  @Serial
  private static final long serialVersionUID = -7024238193987882356L;

  /**
   * 将 {@link LongList} 包装为 {@link UnmodifiableLongList}。
   *
   * @param list
   *     要包装的 {@link LongList}。
   * @return 包装指定集合的 {@link UnmodifiableLongList}。
   */
  public static UnmodifiableLongList wrap(final LongList list) {
    if (list instanceof UnmodifiableLongList) {
      return (UnmodifiableLongList) list;
    } else {
      return new UnmodifiableLongList(list);
    }
  }

  /**
   * 被包装的列表。
   */
  private final LongList list;

  /**
   * 构造一个 {@link UnmodifiableLongList}。
   *
   * @param list
   *     要包装的 {@link LongList}。
   */
  protected UnmodifiableLongList(final LongList list) {
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
  public boolean contains(final long element) {
    return list.contains(element);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean containsAll(final LongCollection c) {
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
  public boolean removeAll(final LongCollection c) {
    throw new UnsupportedOperationException();
  }

  /**
   * 不支持此操作。
   *
   * @throws UnsupportedOperationException
   *     总是抛出。
   */
  @Override
  public boolean remove(final long element) {
    throw new UnsupportedOperationException();
  }

  /**
   * 不支持此操作。
   *
   * @throws UnsupportedOperationException
   *     总是抛出。
   */
    @Override
  public boolean retainAll(final LongCollection c) {
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
  public long[] toArray() {
    return list.toArray();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public long[] toArray(final long[] a) {
    return list.toArray(a);
  }

  /**
   * 不支持此操作。
   *
   * @throws UnsupportedOperationException
   *     总是抛出。
   */
  @Override
  public boolean add(final long element) {
    throw new UnsupportedOperationException();
  }

  /**
   * 不支持此操作。
   *
   * @throws UnsupportedOperationException
   *     总是抛出。
   */
  @Override
  public void add(final int index, final long element) {
    throw new UnsupportedOperationException();
  }

  /**
   * 不支持此操作。
   *
   * @throws UnsupportedOperationException
   *     总是抛出。
   */
  @Override
  public boolean addAll(final LongCollection c) {
    throw new UnsupportedOperationException();
  }

  /**
   * 不支持此操作。
   *
   * @throws UnsupportedOperationException
   *     总是抛出。
   */
  @Override
  public boolean addAll(final int index, final LongCollection collection) {
    throw new UnsupportedOperationException();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public long get(final int index) {
    return list.get(index);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int indexOf(final long element) {
    return list.indexOf(element);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public LongIterator iterator() {
    return new UnmodifiableLongIterator(list.iterator());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int lastIndexOf(final long element) {
    return list.lastIndexOf(element);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public LongListIterator listIterator() {
    return new UnmodifiableLongListIterator(list.listIterator());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public LongListIterator listIterator(final int index) {
    return new UnmodifiableLongListIterator(list.listIterator(index));
  }

  /**
   * 不支持此操作。
   *
   * @throws UnsupportedOperationException
   *     总是抛出。
   */
  @Override
  public long removeAt(final int index) {
    throw new UnsupportedOperationException();
  }

  /**
   * 不支持此操作。
   *
   * @throws UnsupportedOperationException
   *     总是抛出。
   */
  @Override
  public long set(final int index, final long element) {
    throw new UnsupportedOperationException();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public LongList subList(final int fromIndex, final int toIndex) {
    return new UnmodifiableLongList(list.subList(fromIndex, toIndex));
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
