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

import ltd.qubit.commons.datastructure.list.primitive.BooleanCollection;
import ltd.qubit.commons.datastructure.list.primitive.BooleanIterator;
import ltd.qubit.commons.datastructure.list.primitive.BooleanList;
import ltd.qubit.commons.datastructure.list.primitive.RandomAccessBooleanList;

import static java.lang.System.arraycopy;

import static ltd.qubit.commons.lang.Argument.requireIndexInCloseRange;
import static ltd.qubit.commons.lang.Argument.requireIndexInRightOpenRange;

/**
 * 由 {@code boolean} 数组支持的 {@link BooleanList} 实现。此实现支持所有可选方法。
 *
 * @author 胡海星
 */
public class BooleanArrayList extends RandomAccessBooleanList {

  @Serial
  private static final long serialVersionUID = 4991972645899316018L;

  private transient boolean[] data;
  private int size;

  /**
   * 构造一个具有默认初始容量的空列表。
   */
  public BooleanArrayList() {
    this(DEFAULT_INITIAL_CAPACITY);
  }

  /**
   * 构造一个具有指定初始容量的空列表。
   *
   * @param initialCapacity
   *     初始容量。
   * @throws IllegalArgumentException
   *     当 <i>initialCapacity</i> 为负数时
   */
  public BooleanArrayList(final int initialCapacity) {
    if (initialCapacity < 0) {
      throw new IllegalArgumentException("capacity " + initialCapacity);
    }
    data = new boolean[initialCapacity];
    size = 0;
  }

  /**
   * 构造一个包含指定集合元素的列表，元素顺序与该集合的迭代器返回的顺序相同。
   *
   * @param that
   *     要添加的非 {@code null} 的 {@code boolean} 集合
   * @throws NullPointerException
   *     如果 <i>that</i> 为 {@code null}
   * @see BooleanArrayList#addAll(BooleanCollection)
   */
  public BooleanArrayList(final BooleanCollection that) {
    this(that.size());
    addAll(that);
  }

  /**
   * 通过复制指定的数组构造一个列表。
   *
   * @param array
   *          用于初始化集合的数组
   * @throws NullPointerException
   *           如果数组为 {@code null}
   */
  public BooleanArrayList(final boolean[] array) {
    this(array.length);
    arraycopy(array, 0, data, 0, array.length);
    size = array.length;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean get(final int index) {
    requireIndexInRightOpenRange(index, 0, size);
    return data[index];
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int size() {
    return size;
  }

  /**
   * 移除指定位置的元素（可选操作）。将任何后续元素向左移动，从它们的索引中减去一。
   * 返回被移除的元素。
   *
   * @param index
   *     要移除的元素的索引
   * @return 被移除元素的值
   * @throws UnsupportedOperationException
   *     当不支持此操作时
   * @throws IndexOutOfBoundsException
   *     如果指定的索引超出范围
   */
  @Override
  public boolean removeAt(final int index) {
    requireIndexInRightOpenRange(index, 0, size);
    ++modifyCount;
    final boolean oldval = data[index];
    final int numtomove = size - index - 1;
    if (numtomove > 0) {
      arraycopy(data, index + 1, data, index, numtomove);
    }
    size--;
    return oldval;
  }

  /**
   * 用指定元素替换指定位置的元素（可选操作）。
   *
   * @param index
   *     要更改的元素的索引
   * @param element
   *     要存储在指定位置的值
   * @return 之前存储在指定位置的值
   * @throws UnsupportedOperationException
   *     当不支持此操作时
   * @throws IndexOutOfBoundsException
   *     如果指定的索引超出范围
   */
  @Override
  public boolean set(final int index, final boolean element) {
    requireIndexInRightOpenRange(index, 0, size);
    ++modifyCount;
    final boolean oldval = data[index];
    data[index] = element;
    return oldval;
  }

  /**
   * 在指定位置插入指定元素（可选操作）。将当前位于该位置的元素（如果有）和任何后续元素
   * 向右移动，增加它们的索引。
   *
   * @param index
   *     要插入元素的索引
   * @param element
   *     要插入的值
   * @throws UnsupportedOperationException
   *     当不支持此操作时
   * @throws IllegalArgumentException
   *     如果指定元素的某些方面阻止它被添加到此列表中
   * @throws IndexOutOfBoundsException
   *     如果指定的索引超出范围
   */
  @Override
  public void add(final int index, final boolean element) {
    requireIndexInCloseRange(index, 0, size);
    ++modifyCount;
    ensureCapacity(size + 1);
    final int numtomove = size - index;
    arraycopy(data, index, data, index + 1, numtomove);
    data[index] = element;
    size++;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void clear() {
    ++modifyCount;
    size = 0;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean addAll(final BooleanCollection collection) {
    return addAll(size(), collection);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean addAll(final int index, final BooleanCollection collection) {
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
    for (final BooleanIterator it = collection.iterator(); it.hasNext(); ++i) {
      data[i] = it.next();
    }
    size += collection.size();
    return true;
  }

  /**
   * 必要时增加容量，以确保此列表可以容纳至少由最小容量参数指定的元素数量而不需要增长。
   *
   * @param mincap
   *     最小容量。
   */
  public void ensureCapacity(final int mincap) {
    ++modifyCount;
    if (mincap > data.length) {
      final int newcap = ((data.length * 3) / 2) + 1;
      final boolean[] olddata = data;
      data = new boolean[Math.max(newcap, mincap)];
      arraycopy(olddata, 0, data, 0, size);
    }
  }

  /**
   * 必要时减少容量，以匹配当前的 {@link #size size}。
   */
  public void trimToSize() {
    ++modifyCount;
    if (size < data.length) {
      final boolean[] olddata = data;
      data = new boolean[size];
      arraycopy(olddata, 0, data, 0, size);
    }
  }

  private void writeObject(final ObjectOutputStream out) throws IOException {
    out.defaultWriteObject();
    out.writeInt(data.length);
    for (int i = 0; i < size; i++) {
      out.writeBoolean(data[i]);
    }
  }

  private void readObject(final ObjectInputStream in) throws IOException,
      ClassNotFoundException {
    in.defaultReadObject();
    data = new boolean[in.readInt()];
    for (int i = 0; i < size; i++) {
      data[i] = in.readBoolean();
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void sort(final int fromIndex, final int toIndex) {
    int falseCount = 0;
    for (int i = fromIndex; i < toIndex; ++i) {
      if (!data[i]) {
        ++falseCount;
      }
    }
    final int middle = fromIndex + falseCount;
    for (int i = fromIndex; i < middle; ++i) {
      data[i] = false;
    }
    for (int i = middle; i < toIndex; ++i) {
      data[i] = true;
    }
  }
}
