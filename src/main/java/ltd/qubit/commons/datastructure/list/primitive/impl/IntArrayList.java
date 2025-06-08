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

import ltd.qubit.commons.datastructure.list.primitive.IntCollection;
import ltd.qubit.commons.datastructure.list.primitive.IntIterator;
import ltd.qubit.commons.datastructure.list.primitive.IntList;
import ltd.qubit.commons.datastructure.list.primitive.RandomAccessIntList;

import static java.lang.System.arraycopy;

import static ltd.qubit.commons.lang.Argument.requireIndexInCloseRange;
import static ltd.qubit.commons.lang.Argument.requireIndexInRightOpenRange;

/**
 * 一个由 {@code int} 数组支持的 {@link IntList}。
 *
 * <p>此实现支持所有可选方法。
 *
 * @author 胡海星
 */
public class IntArrayList extends RandomAccessIntList {

  @Serial
  private static final long serialVersionUID = -6531058737268505895L;

  private transient int[] data;
  private int size;

  /**
   * 构造一个具有默认初始容量的空列表。
   */
  public IntArrayList() {
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
  public IntArrayList(final int initialCapacity) {
    if (initialCapacity < 0) {
      throw new IllegalArgumentException("capacity " + initialCapacity);
    }
    data = new int[initialCapacity];
    size = 0;
  }

  /**
   * 构造一个列表，其中包含给定集合的元素，其顺序由该集合的迭代器返回。
   *
   * @param that
   *     要添加的非{@code null}的 {@code int} 集合。
   * @throws NullPointerException
   *     如果 <i>that</i> 是 {@code null}。
   * @see IntArrayList#addAll(IntCollection)
   */
  public IntArrayList(final IntCollection that) {
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
  public IntArrayList(final int[] array) {
    this(array.length);
    arraycopy(array, 0, data, 0, array.length);
    size = array.length;
  }

  @Override
  public int size() {
    return size;
  }

  @Override
  public int get(final int index) {
    requireIndexInRightOpenRange(index, 0, size);
    return data[index];
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
  public int set(final int index, final int element) {
    requireIndexInRightOpenRange(index, 0, size);
    ++modifyCount;
    final int oldVal = data[index];
    data[index] = element;
    return oldVal;
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
  public int removeAt(final int index) {
    requireIndexInRightOpenRange(index, 0, size);
    ++modifyCount;
    final int oldVal = data[index];
    final int numToMove = size - index - 1;
    if (numToMove > 0) {
      arraycopy(data, index + 1, data, index, numToMove);
    }
    size--;
    return oldVal;
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
  public void add(final int index, final int element) {
    requireIndexInCloseRange(index, 0, size);
    ++modifyCount;
    ensureCapacity(size + 1);
    final int numToMove = size - index;
    arraycopy(data, index, data, index + 1, numToMove);
    data[index] = element;
    size++;
  }

  @Override
  public void clear() {
    ++modifyCount;
    size = 0;
  }

  @Override
  public boolean addAll(final IntCollection collection) {
    return addAll(size(), collection);
  }

  @Override
  public boolean addAll(final int index, final IntCollection collection) {
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
    for (final IntIterator it = collection.iterator(); it.hasNext(); ++i) {
      data[i] = it.next();
    }
    size += collection.size();
    return true;
  }

  /**
   * 如有必要，增加此列表的容量，以确保它至少可以容纳由最小容量参数指定的元素数量而无需增长。
   *
   * @param minCap
   *     最小容量。
   */
  public void ensureCapacity(final int minCap) {
    ++modifyCount;
    if (minCap > data.length) {
      final int newCap = ((data.length * 3) / 2) + 1;
      final int[] oldData = data;
      data = new int[Math.max(newCap, minCap)];
      arraycopy(oldData, 0, data, 0, size);
    }
  }

  /**
   * 如有必要，减小此列表的容量以匹配其当前大小 ({@link #size})。
   */
  public void trimToSize() {
    ++modifyCount;
    if (size < data.length) {
      final int[] oldData = data;
      data = new int[size];
      arraycopy(oldData, 0, data, 0, size);
    }
  }

  private void writeObject(final ObjectOutputStream out) throws IOException {
    out.defaultWriteObject();
    out.writeInt(data.length);
    for (int i = 0; i < size; i++) {
      out.writeInt(data[i]);
    }
  }

  private void readObject(final ObjectInputStream in) throws IOException,
      ClassNotFoundException {
    in.defaultReadObject();
    data = new int[in.readInt()];
    for (int i = 0; i < size; i++) {
      data[i] = in.readInt();
    }
  }

  @Override
  protected void sort(final int fromIndex, final int toIndex) {
    Arrays.sort(data, fromIndex, toIndex);
  }
}
