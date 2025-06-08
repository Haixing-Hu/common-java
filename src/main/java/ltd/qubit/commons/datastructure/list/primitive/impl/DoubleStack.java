////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.datastructure.list.primitive.impl;

import java.util.EmptyStackException;

/**
 * 基于 {@code double} 实现的栈。
 *
 * <p>其底层存储是一个 {@link DoubleArrayList}，其中列表的前面是栈底，后面是栈顶。
 *
 * @author 胡海星
 */
public class DoubleStack {
  /**
   * The underlying dynamic primitive backing store.
   */
  private final DoubleArrayList list = new DoubleArrayList();

  /**
   * 创建一个空的栈。
   */
  public DoubleStack() {
  }

  /**
   * 创建一个栈，并用数组中的值预先填充它。
   *
   * @param numbas
   *          要添加的数组。
   */
  public DoubleStack(final double[] numbas) {
    for (final double numba : numbas) {
      list.add(numba);
    }
  }

  /**
   * 测试此堆栈是否为空。
   *
   * @return
   *     如果此堆栈为空，则为 {@code true}；否则为 {@code false}。
   */
  public boolean empty() {
    return list.isEmpty();
  }

  /**
   * 查看此堆栈的顶部而不删除它。
   *
   * @return
   *     此堆栈顶部的值。
   * @throws java.util.EmptyStackException
   *     如果此堆栈为空。
   */
  public double peek() {
    if (list.isEmpty()) {
      throw new EmptyStackException();
    }

    return list.get(list.size() - 1);
  }

  /**
   * 返回堆栈中第 n 个 {@code double}，其中 0 是顶部元素，[size()-1] 是底部元素。
   *
   * @param n
   *          元素索引。
   * @return
   *          指定索引处的元素。
   * @throws EmptyStackException
   *     如果堆栈为空。
   * @throws IndexOutOfBoundsException
   *     如果索引越界。
   */
  public double peek(final int n) {
    if (list.isEmpty()) {
      throw new EmptyStackException();
    }

    return list.get(list.size() - n - 1);
  }

  /**
   * 删除此堆栈顶部的值并返回它。
   *
   * @return
   *     此堆栈顶部的值。
   * @throws java.util.EmptyStackException
   *     如果此堆栈为空。
   */
  public double pop() {
    if (list.isEmpty()) {
      throw new EmptyStackException();
    }

    return list.removeAt(list.size() - 1);
  }

  /**
   * 将一个值压入此堆栈的顶部。
   *
   * @param item
   *          要压入此堆栈的值。
   * @return
   *     参数 {@code item}，以用于方法链式调用。
   */
  public double push(final double item) {
    list.add(item);
    return item;
  }

  /**
   * 返回一个值在此堆栈中的从1开始的位置。
   *
   * <p>如果该值作为一项出现在此堆栈中，此方法返回最接近堆栈顶部的出现位置与堆栈顶部的距离；
   * 堆栈最顶部的项被认为在距离1的位置。
   *
   * @param item
   *          要从上到下搜索的值。
   * @return
   *     从堆栈顶部开始的从1开始的位置，其中找到了 {@code int}；返回值-1表示堆栈上没有该
   *     {@code int}。
   */
  public int search(final double item) {
    for (int ii = list.size() - 1; ii >= 0; ii--) {
      if (list.get(ii) == item) {
        return list.size() - ii;
      }
    }

    return - 1;
  }

  /**
   * 从堆栈中获取项目，其中索引是基于零的，堆栈的顶部位于索引 size()-1 处，堆栈的底部位于索引 0 处。
   *
   * @param index
   *          堆栈作为一个列表的索引。
   * @return
   *          指定索引处的值。
   */
  public double get(final int index) {
    return list.get(index);
  }

  /**
   * 获取此堆栈的大小。
   *
   * @return
   *     此堆栈的大小。
   */
  public int size() {
    return list.size();
  }

  /**
   * 清空堆栈的内容。
   */
  public void clear() {
    list.clear();
  }
}