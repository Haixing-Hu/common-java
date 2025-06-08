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

import ltd.qubit.commons.datastructure.list.primitive.AbstractByteCollection;
import ltd.qubit.commons.datastructure.list.primitive.ByteCollection;
import ltd.qubit.commons.datastructure.list.primitive.ByteIterator;
import ltd.qubit.commons.datastructure.list.primitive.ByteList;
import ltd.qubit.commons.datastructure.list.primitive.ByteListIterator;
import ltd.qubit.commons.lang.Argument;

/**
 * {@link ByteList} 的一个不可修改版本。
 *
 * @author 胡海星
 */
public final class UnmodifiableByteList extends AbstractByteCollection
    implements ByteList {

  @Serial
  private static final long serialVersionUID = -2449447720150271338L;

  /**
   * 将一个 {@link ByteList} 包装成一个 {@link UnmodifiableByteList}。
   *
   * @param list
   *     要包装的 {@link ByteList}。
   * @return 一个包装了指定列表的 {@link UnmodifiableByteList}。
   */
  public static UnmodifiableByteList wrap(final ByteList list) {
    if (list instanceof UnmodifiableByteList) {
      return (UnmodifiableByteList) list;
    } else {
      return new UnmodifiableByteList(list);
    }
  }

  /**
   * 被包装的列表。
   */
  private final ByteList list;

  /**
   * 构造一个 {@link UnmodifiableByteList}。
   *
   * @param list
   *     要包装的 {@link ByteList}。
   */
  protected UnmodifiableByteList(final ByteList list) {
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
  public boolean contains(final byte element) {
    return list.contains(element);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean containsAll(final ByteCollection c) {
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
   * @param c
   *     要移除的元素集合。
   * @return 此调用总会抛出 {@link UnsupportedOperationException}。
   * @throws UnsupportedOperationException
   *     总是抛出。
   */
  @Override
  public boolean removeAll(final ByteCollection c) {
    throw new UnsupportedOperationException();
  }

  /**
   * 不支持此操作。
   *
   * @param element
   *     要移除的元素。
   * @return 此调用总会抛出 {@link UnsupportedOperationException}。
   * @throws UnsupportedOperationException
   *     总是抛出。
   */
  @Override
  public boolean remove(final byte element) {
    throw new UnsupportedOperationException();
  }

  /**
   * 不支持此操作。
   *
   * @param c
   *     要保留的元素集合。
   * @return 此调用总会抛出 {@link UnsupportedOperationException}。
   * @throws UnsupportedOperationException
   *     总是抛出。
   */
  @Override
  public boolean retainAll(final ByteCollection c) {
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
  public byte[] toArray() {
    return list.toArray();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public byte[] toArray(final byte[] a) {
    return list.toArray(a);
  }

  /**
   * 不支持此操作。
   *
   * @param element
   *     要添加的元素。
   * @return 此调用总会抛出 {@link UnsupportedOperationException}。
   * @throws UnsupportedOperationException
   *     总是抛出。
   */
  @Override
  public boolean add(final byte element) {
    throw new UnsupportedOperationException();
  }

  /**
   * 不支持此操作。
   *
   * @param index
   *     要插入元素的位置索引。
   * @param element
   *     要插入的元素。
   * @throws UnsupportedOperationException
   *     总是抛出。
   */
  @Override
  public void add(final int index, final byte element) {
    throw new UnsupportedOperationException();
  }

  /**
   * 不支持此操作。
   *
   * @param c
   *     要添加的元素集合。
   * @return 此调用总会抛出 {@link UnsupportedOperationException}。
   * @throws UnsupportedOperationException
   *     总是抛出。
   */
  @Override
  public boolean addAll(final ByteCollection c) {
    throw new UnsupportedOperationException();
  }

  /**
   * 不支持此操作。
   *
   * @param index
   *     要插入集合中第一个元素的位置索引。
   * @param collection
   *     要插入的元素集合。
   * @return 此调用总会抛出 {@link UnsupportedOperationException}。
   * @throws UnsupportedOperationException
   *     总是抛出。
   */
  @Override
  public boolean addAll(final int index, final ByteCollection collection) {
    throw new UnsupportedOperationException();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public byte get(final int index) {
    return list.get(index);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int indexOf(final byte element) {
    return list.indexOf(element);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ByteIterator iterator() {
    return new UnmodifiableByteIterator(list.iterator());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int lastIndexOf(final byte element) {
    return list.lastIndexOf(element);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ByteListIterator listIterator() {
    return new UnmodifiableByteListIterator(list.listIterator());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ByteListIterator listIterator(final int index) {
    return new UnmodifiableByteListIterator(list.listIterator(index));
  }

  /**
   * 不支持此操作。
   *
   * @param index
   *     要移除的元素的位置索引。
   * @return 此调用总会抛出 {@link UnsupportedOperationException}。
   * @throws UnsupportedOperationException
   *     总是抛出。
   */
  @Override
  public byte removeAt(final int index) {
    throw new UnsupportedOperationException();
  }

  /**
   * 不支持此操作。
   *
   * @param index
   *     要替换的元素的位置索引。
   * @param element
   *     要存储在指定位置的元素。
   * @return 此调用总会抛出 {@link UnsupportedOperationException}。
   * @throws UnsupportedOperationException
   *     总是抛出。
   */
  @Override
  public byte set(final int index, final byte element) {
    throw new UnsupportedOperationException();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ByteList subList(final int fromIndex, final int toIndex) {
    return new UnmodifiableByteList(list.subList(fromIndex, toIndex));
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
