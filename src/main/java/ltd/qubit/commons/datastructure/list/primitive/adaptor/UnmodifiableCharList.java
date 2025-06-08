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

import ltd.qubit.commons.datastructure.list.primitive.AbstractCharCollection;
import ltd.qubit.commons.datastructure.list.primitive.CharCollection;
import ltd.qubit.commons.datastructure.list.primitive.CharIterator;
import ltd.qubit.commons.datastructure.list.primitive.CharList;
import ltd.qubit.commons.datastructure.list.primitive.CharListIterator;

import static ltd.qubit.commons.lang.Argument.requireNonNull;

/**
 * {@link CharList} 的一个不可修改版本。
 *
 * @author 胡海星
 */
public final class UnmodifiableCharList extends AbstractCharCollection
    implements CharList {

  @Serial
  private static final long serialVersionUID = 6845530339493097526L;

  /**
   * 将一个 {@link CharList} 包装成一个 {@link UnmodifiableCharList}。
   *
   * @param list
   *     要包装的 {@link CharList}。
   * @return 一个包装了指定列表的 {@link UnmodifiableCharList}。
   */
  public static UnmodifiableCharList wrap(final CharList list) {
    if (list instanceof UnmodifiableCharList) {
      return (UnmodifiableCharList) list;
    } else {
      return new UnmodifiableCharList(list);
    }
  }

  /**
   * 被包装的列表。
   */
  private final CharList list;

  /**
   * 构造一个 {@link UnmodifiableCharList}。
   *
   * @param list
   *     要包装的 {@link CharList}。
   */
  protected UnmodifiableCharList(final CharList list) {
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
  public boolean contains(final char element) {
    return list.contains(element);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean containsAll(final CharCollection c) {
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
  public boolean removeAll(final CharCollection c) {
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
  public boolean remove(final char element) {
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
  public boolean retainAll(final CharCollection c) {
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
  public char[] toArray() {
    return list.toArray();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public char[] toArray(final char[] a) {
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
  public boolean add(final char element) {
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
  public void add(final int index, final char element) {
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
  public boolean addAll(final CharCollection c) {
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
  public boolean addAll(final int index, final CharCollection collection) {
    throw new UnsupportedOperationException();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public char get(final int index) {
    return list.get(index);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int indexOf(final char element) {
    return list.indexOf(element);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public CharIterator iterator() {
    return new UnmodifiableCharIterator(list.iterator());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int lastIndexOf(final char element) {
    return list.lastIndexOf(element);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public CharListIterator listIterator() {
    return new UnmodifiableCharListIterator(list.listIterator());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public CharListIterator listIterator(final int index) {
    return new UnmodifiableCharListIterator(list.listIterator(index));
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
  public char removeAt(final int index) {
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
  public char set(final int index, final char element) {
    throw new UnsupportedOperationException();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public CharList subList(final int fromIndex, final int toIndex) {
    return new UnmodifiableCharList(list.subList(fromIndex, toIndex));
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