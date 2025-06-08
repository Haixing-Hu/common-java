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
 * 基于原始 boolean 类型的栈。底层支持存储是一个 ArrayBooleanList，
 * 其中列表的前端是栈的底部，列表的尾端是栈的顶部。
 *
 * @author 胡海星
 */
public class BooleanStack {

  /**
   * 底层动态原始类型支持存储。
   */
  private final BooleanArrayList list;

  /**
   * 创建一个空的原始类型栈。
   */
  public BooleanStack() {
    list = new BooleanArrayList();
  }

  /**
   * 创建一个栈并用值预填充它。
   *
   * @param bits
   *          要添加的数组
   */
  public BooleanStack(final boolean[] bits) {
    list = new BooleanArrayList();
    for (final boolean bit : bits) {
      list.add(bit);
    }
  }

  /**
   * 测试此栈是否为空。
   *
   * @return 当且仅当此栈为空时返回 true；否则返回 false
   */
  public boolean empty() {
    return list.isEmpty();
  }

  /**
   * 查看此栈的顶部而不移除它。
   *
   * @return 此栈顶部的值
   * @throws java.util.EmptyStackException
   *           如果此栈为空
   */
  public boolean peek() {
    if (list.isEmpty()) {
      throw new EmptyStackException();
    }

    return list.get(list.size() - 1);
  }

  /**
   * 返回栈中向下第 n 个 boolean 值，其中 0 是顶部元素，
   * [size()-1] 是底部元素。
   *
   * @param n
   *          元素位置
   * @return 指定位置的元素
   * @throws EmptyStackException
   *           如果栈为空
   * @throws IndexOutOfBoundsException
   *           如果位置超出边界
   */
  public boolean peek(final int n) {
    if (list.isEmpty()) {
      throw new EmptyStackException();
    }

    return list.get(list.size() - n - 1);
  }

  /**
   * 移除此栈顶部的值并返回它。
   *
   * @return 栈顶部的值
   * @throws java.util.EmptyStackException
   *           如果此栈为空
   */
  public boolean pop() {
    if (list.isEmpty()) {
      throw new EmptyStackException();
    }

    return list.removeAt(list.size() - 1);
  }

  /**
   * 将一个值推送到此栈的顶部。
   *
   * @param item
   *          要推送到此栈上的值
   * @return 用于调用链的 item 参数
   */
  public boolean push(final boolean item) {
    list.add(item);
    return item;
  }

  /**
   * 返回值在此栈上的基于 1 的位置。如果该值作为项目出现在此栈中，
   * 此方法返回从栈顶到最接近栈顶的出现位置的距离；
   * 栈上最顶部的项目被认为处于距离 1 的位置。
   *
   * @param item
   *          要从顶部向下搜索的值
   * @return 从栈顶开始的基于 1 的位置，其中 int 所在的位置；
   *         返回值 -1 表示 int 不在栈上
   */
  public int search(final boolean item) {
    for (int ii = list.size() - 1; ii >= 0; ii--) {
      if (list.get(ii) == item) {
        return list.size() - ii;
      }
    }

    return - 1;
  }

  /**
   * 从栈中获取项目，其中索引基于零，栈顶位于 size()-1 索引处，
   * 栈底位于索引 0 处。
   *
   * @param index
   *          视为列表的栈中的索引
   * @return 指定索引处的值
   */
  public boolean get(final int index) {
    return list.get(index);
  }

  /**
   * 获取此栈的大小。
   *
   * @return 此栈的大小
   */
  public int size() {
    return list.size();
  }

  /**
   * 清空栈的内容。
   */
  public void clear() {
    list.clear();
  }
}