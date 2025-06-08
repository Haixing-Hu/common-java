////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.datastructure.list.primitive.impl;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serial;
import java.util.Arrays;

import ltd.qubit.commons.datastructure.list.primitive.RandomAccessShortList;
import ltd.qubit.commons.datastructure.list.primitive.ShortCollection;
import ltd.qubit.commons.datastructure.list.primitive.ShortIterator;
import ltd.qubit.commons.datastructure.list.primitive.ShortList;

import static java.lang.System.arraycopy;

import static ltd.qubit.commons.lang.Argument.requireIndexInCloseRange;
import static ltd.qubit.commons.lang.Argument.requireIndexInRightOpenRange;

/**
 * 一个由 {@code short} 数组支持的 {@link ShortList}。
 *
 * <p>此实现支持所有可选方法。
 *
 * @author 胡海星
 */
public class ShortArrayList extends RandomAccessShortList {

  @Serial
  private static final long serialVersionUID = -2604509586550158455L;

  private transient short[] data;
  private int size;

  /**
   * 构造一个具有默认初始容量的空列表。
   */
  public ShortArrayList() {
    this(DEFAULT_INITIAL_CAPACITY);
  }

  /**
   * 构造一个具有给定初始容量的空列表。
   *
   * @param initialCapacity
   *     初始容量。
   * @throws IllegalArgumentException
   *     如果 <i>initialCapacity</i> 为负。
   */
  public ShortArrayList(final int initialCapacity) {
    if (initialCapacity < 0) {
      throw new IllegalArgumentException("capacity " + initialCapacity);
    }
    data = new short[initialCapacity];
    size = 0;
  }

  /**
   * 构造一个列表，其中包含给定集合的元素，其顺序由该集合的迭代器返回。
   *
   * @param that
   *     要添加的非{@code null}的 {@code short} 集合。
   * @throws NullPointerException
   *     如果 <i>that</i> 是 {@code null}。
   * @see ShortArrayList#addAll(ShortCollection)
   */
  public ShortArrayList(final ShortCollection that) {
    this(that.size());
    addAll(that);
  }

  /**
   * 通过复制指定的数组构造一个列表。
   *
   * @param array
   *     用于初始化集合的数组。
   * @throws NullPointerException
   *     如果数组为 {@code null}。
   */
  public ShortArrayList(final short[] array) {
    this(array.length);
    arraycopy(array, 0, data, 0, array.length);
    size = array.length;
  }

  // ShortList methods
  // -------------------------------------------------------------------------

  @Override
  public short get(final int index) {
    requireIndexInRightOpenRange(index, 0, size);
    return data[index];
  }

  @Override
  public int size() {
    return size;
  }

  /**
   * 删除指定位置的元素（可选操作）。
   *
   * <p>所有后续元素都向左移动，其索引减一。返回被删除的元素。
   *
   * @param index
   *     要删除的元素的索引。
   * @return
   *     被删除的元素的值。
   * @throws UnsupportedOperationException
   *     当不支持此操作时。
   * @throws IndexOutOfBoundsException
   *     如果指定的索引超出范围。
   */
  @Override
  public short removeAt(final int index) {
    requireIndexInRightOpenRange(index, 0, size);
    ++modifyCount;
    final short oldval = data[index];
    final int numtomove = size - index - 1;
    if (numtomove > 0) {
      arraycopy(data, index + 1, data, index, numtomove);
    }
    size--;
    return oldval;
  }

  /**
   * 用指定的元素替换指定位置的元素（可选操作）。
   *
   * @param index
   *     要更改的元素的索引。
   * @param element
   *     要存储在指定位置的值。
   * @return
   *     先前存储在指定位置的值。
   * @throws UnsupportedOperationException
   *     当不支持此操作时。
   * @throws IndexOutOfBoundsException
   *     如果指定的索引超出范围。
   */
  @Override
  public short set(final int index, final short element) {
    requireIndexInRightOpenRange(index, 0, size);
    ++modifyCount;
    final short oldval = data[index];
    data[index] = element;
    return oldval;
  }

  /**
   * 在指定位置插入指定元素（可选操作）。
   *
   * <p>将当前位于该位置的元素（如有）和任何后续元素向右移动，使其索引增加。
   *
   * @param index
   *     要插入元素的索引。
   * @param element
   *     要插入的值。
   * @throws UnsupportedOperationException
   *     当不支持此操作时。
   * @throws IllegalArgumentException
   *     如果指定元素的某些方面阻止其被添加到此列表中。
   * @throws IndexOutOfBoundsException
   *     如果指定的索引超出范围。
   */
  @Override
  public void add(final int index, final short element) {
    requireIndexInCloseRange(index, 0, size);
    ++modifyCount;
    ensureCapacity(size + 1);
    final int numtomove = size - index;
    arraycopy(data, index, data, index + 1, numtomove);
    data[index] = element;
    size++;
  }

  @Override
  public void clear() {
    ++modifyCount;
    size = 0;
  }

  @Override
  public boolean addAll(final ShortCollection collection) {
    return addAll(size(), collection);
  }

  @Override
  public boolean addAll(final int index, final ShortCollection collection) {
    if (collection.size() == 0) {
      return false;
    }
    requireIndexInCloseRange(index, 0, size);
    ++modifyCount;
    ensureCapacity(size + collection.size());
    if (index != size) {
      // Need to move some elements
      arraycopy(data, index, data, index + collection.size(), size - index);
    }
    int i = index;
    for (final ShortIterator it = collection.iterator(); it.hasNext(); ++i) {
      data[i] = it.next();
    }
    size += collection.size();
    return true;
  }

  // capacity methods
  // -------------------------------------------------------------------------

  /**
   * 如有必要，增加此列表的容量，以确保它至少可以容纳由最小容量参数指定的元素数量而无需增长。
   *
   * @param mincap
   *     最小容量。
   */
  public void ensureCapacity(final int mincap) {
    ++modifyCount;
    if (mincap > data.length) {
      final int newcap = ((data.length * 3) / 2) + 1;
      final short[] olddata = data;
      data = new short[newcap < mincap ? mincap : newcap];
      arraycopy(olddata, 0, data, 0, size);
    }
  }

  /**
   * 如有必要，减小此列表的容量以匹配其当前大小 ({@link #size})。
   */
  public void trimToSize() {
    ++modifyCount;
    if (size < data.length) {
      final short[] olddata = data;
      data = new short[size];
      arraycopy(olddata, 0, data, 0, size);
    }
  }

  // private methods
  // -------------------------------------------------------------------------

  private void writeObject(final ObjectOutputStream out) throws IOException {
    out.defaultWriteObject();
    out.writeInt(data.length);
    for (int i = 0; i < size; i++) {
      out.writeShort(data[i]);
    }
  }

  private void readObject(final ObjectInputStream in) throws IOException,
      ClassNotFoundException {
    in.defaultReadObject();
    final int cap = in.readInt();
    data = new short[cap];
    for (int i = 0; i < size; i++) {
      data[i] = in.readShort();
    }
  }

  @Override
  protected void sort(final int fromIndex, final int toIndex) {
    Arrays.sort(data, fromIndex, toIndex);
  }
}
