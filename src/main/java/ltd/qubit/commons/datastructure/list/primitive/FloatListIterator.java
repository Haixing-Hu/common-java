////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.datastructure.list.primitive;

/**
 * {@code float} 值的双向迭代器。
 *
 * @author 胡海星
 */
public interface FloatListIterator extends FloatIterator {

  /**
   * 将指定元素插入到我的底层集合中（可选操作）。元素被插入到 {@link #next} 将要返回的下一个元素之前
   *（如果有的话），以及 {@link #previous} 将要返回的下一个元素之后（如果有的话）。
   *
   * <p>新元素被插入到隐含游标之前。后续调用 {@link #previous} 将返回添加的元素，
   * 后续调用 {@link #next} 不会受到影响。此调用会使 {@link #nextIndex} 或 
   * {@link #previousIndex} 的返回值增加一。
   *
   * @param element
   *     要插入的值
   * @throws UnsupportedOperationException
   *     当不支持此操作时
   * @throws IllegalArgumentException
   *     如果指定元素的某些方面阻止其被添加
   */
  void add(float element);

  /**
   * 当向前遍历时，如果我还有更多元素则返回 {@code true}。
   * （换句话说，如果调用 {@link #next} 将返回元素而不是抛出异常，则返回 {@code true}。
   *
   * @return 当向前遍历时，如果我还有更多元素则返回 {@code true}
   */
  @Override
  boolean hasNext();

  /**
   * 当向后遍历时，如果我还有更多元素则返回 {@code true}。
   * （换句话说，如果调用 {@link #previous} 将返回元素而不是抛出异常，则返回 {@code true}。
   *
   * @return 当向后遍历时，如果我还有更多元素则返回 {@code true}
   */
  boolean hasPrevious();

  /**
   * 当向前遍历时返回我的下一个元素。
   *
   * @return 我的下一个元素
   * @throws java.util.NoSuchElementException
   *     如果没有下一个元素
   */
  @Override
  float next();

  /**
   * 返回后续调用 {@link #next} 将要返回的元素的索引，
   * 或者如果我没有下一个元素则返回我的迭代中元素的数量。
   *
   * @return 我的下一个元素的索引
   */
  int nextIndex();

  /**
   * 当向后遍历时返回我的下一个元素。
   *
   * @return 我的前一个元素
   * @throws java.util.NoSuchElementException
   *     如果没有前一个元素
   */
  float previous();

  /**
   * 返回后续调用 {@link #previous} 将要返回的元素的索引，
   * 或者如果我没有前一个元素则返回 {@code -1}。
   *
   * @return 我的前一个元素的索引
   */
  int previousIndex();

  /**
   * 从我的底层集合中移除 {@link #next} 或 {@link #previous} 最后返回的元素（可选操作）。
   *
   * @throws UnsupportedOperationException
   *     如果不支持此操作
   * @throws IllegalStateException
   *     如果既没有调用 {@link #next} 也没有调用 {@link #previous}，
   *     或者自上次调用 {@link #next} 或 {@link #previous} 以来已经调用了 {@link #remove} 或 {@link #add}。
   */
  @Override
  void remove();

  /**
   * 用指定值替换我的底层集合中 {@link #next} 或 {@link #previous} 最后返回的元素（可选操作）。
   *
   * @param element
   *     用来替换最后返回元素的值
   * @throws UnsupportedOperationException
   *     如果不支持此操作
   * @throws IllegalStateException
   *     如果既没有调用 {@link #next} 也没有调用 {@link #previous}，
   *     或者自上次调用 {@link #next} 或 {@link #previous} 以来已经调用了 {@link #remove} 或 {@link #add}。
   * @throws IllegalArgumentException
   *     如果指定元素的某些方面阻止其被添加
   */
  void set(float element);
}