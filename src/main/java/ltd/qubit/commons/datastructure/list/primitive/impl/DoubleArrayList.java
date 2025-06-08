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

import ltd.qubit.commons.datastructure.list.primitive.DoubleCollection;
import ltd.qubit.commons.datastructure.list.primitive.DoubleIterator;
import ltd.qubit.commons.datastructure.list.primitive.DoubleList;
import ltd.qubit.commons.datastructure.list.primitive.RandomAccessDoubleList;

import static java.lang.System.arraycopy;

import static ltd.qubit.commons.lang.Argument.requireIndexInCloseRange;
import static ltd.qubit.commons.lang.Argument.requireIndexInRightOpenRange;

/**
 * 一个由 {@code double} 数组支持的 {@link DoubleList}。
 *
 * <p>此实现支持所有可选方法。
 *
 * @author 胡海星
 */
public class DoubleArrayList extends RandomAccessDoubleList {

  @Serial
  private static final long serialVersionUID = -4266768737771011697L;

  private transient double[] data;
  private int size;

  /**
   * 构造一个具有默认初始容量的空列表。
   */
  public DoubleArrayList() {
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
  public DoubleArrayList(final int initialCapacity) {
    if (initialCapacity < 0) {
      throw new IllegalArgumentException("capacity " + initialCapacity);
    }
    data = new double[initialCapacity];
    size = 0;
  }

  /**
   * 构造一个列表，其中包含给定集合的元素，其顺序由该集合的迭代器返回。
   *
   * @param that
   *     要添加的非{@code null}的 {@code double} 集合。
   * @throws NullPointerException
   *     如果 <i>that</i> 是 {@code null}。
   * @see DoubleArrayList#addAll(DoubleCollection)
   */
  public DoubleArrayList(final DoubleCollection that) {
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
  public DoubleArrayList(final double[] array) {
    this(array.length);
    arraycopy(array, 0, data, 0, array.length);
    size = array.length;
  }

  @Override
  public double get(final int index) {
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
  public double removeAt(final int index) {
    requireIndexInRightOpenRange(index, 0, size);
    ++modifyCount;
    final double oldval = data[index];
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
  public double set(final int index, final double element) {
    requireIndexInRightOpenRange(index, 0, size);
    ++modifyCount;
    final double oldval = data[index];
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
  public void add(final int index, final double element) {
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
  public boolean addAll(final DoubleCollection collection) {
    return addAll(size(), collection);
  }

  @Override
  public boolean addAll(final int index, final DoubleCollection collection) {
    if (collection.size() == 0) {
      return false;
    }
    requireIndexInCloseRange(index, 0, size);
    ++modifyCount;
    ensureCapacity(size + collection.size());
    if (index != size) {
      // Need to move some elements
      arraycopy(data, index, data, index + collection.size(), size
          - index);
    }
    int i = index;
    for (final DoubleIterator it = collection.iterator(); it.hasNext(); ++i) {
      data[i] = it.next();
    }
    size += collection.size();
    return true;
  }

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
      final double[] olddata = data;
      data = new double[newcap < mincap ? mincap : newcap];
      arraycopy(olddata, 0, data, 0, size);
    }
  }

  /**
   * 如有必要，减小此列表的容量以匹配其当前大小 ({@link #size})。
   */
  public void trimToSize() {
    ++modifyCount;
    if (size < data.length) {
      final double[] olddata = data;
      data = new double[size];
      arraycopy(olddata, 0, data, 0, size);
    }
  }

  private void writeObject(final ObjectOutputStream out) throws IOException {
    out.defaultWriteObject();
    out.writeInt(data.length);
    for (int i = 0; i < size; i++) {
      out.writeDouble(data[i]);
    }
  }

  private void readObject(final ObjectInputStream in) throws IOException,
      ClassNotFoundException {
    in.defaultReadObject();
    data = new double[in.readInt()];
    for (int i = 0; i < size; i++) {
      data[i] = in.readDouble();
    }
  }

  @Override
  protected void sort(final int fromIndex, final int toIndex) {
    Arrays.sort(data, fromIndex, toIndex);
  }
}
