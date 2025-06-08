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
 * {@code boolean} 值的双向迭代器。
 *
 * @author 胡海星
 */
public interface BooleanListIterator extends BooleanIterator {

  /**
   * 将指定元素插入到底层集合中（可选操作）。
   * 该元素紧接在{@link #next}将返回的下一个元素之前插入（如果有的话），
   * 并紧接在{@link #previous}将返回的下一个元素之后插入（如果有的话）。
   *
   * <p>新元素紧接在隐含游标之前插入。后续调用{@link #previous}将返回添加的元素，
   * 后续调用{@link #next}不受影响。此调用将{@link #nextIndex}或
   * {@link #previousIndex}调用返回的值增加一。
   *
   * @param element
   *     要插入的值
   * @throws UnsupportedOperationException
   *     当不支持此操作时
   * @throws IllegalArgumentException
   *     如果指定元素的某些方面阻止其被添加
   */
  void add(boolean element);

  /**
   * 当且仅当向前遍历时还有更多元素时返回 {@code true}。
   * （换句话说，当且仅当调用{@link #next}将返回元素而不是抛出异常时返回 {@code true}。）
   *
   * @return 当且仅当向前遍历时还有更多元素时返回 {@code true}
   */
  @Override
  boolean hasNext();

  /**
   * 当且仅当向后遍历时还有更多元素时返回 {@code true}。
   * （换句话说，当且仅当调用{@link #previous}将返回元素而不是抛出异常时返回 {@code true}。）
   *
   * @return 当且仅当向后遍历时还有更多元素时返回 {@code true}
   */
  boolean hasPrevious();

  /**
   * 返回向前遍历时的下一个元素。
   *
   * @return 下一个元素
   * @throws java.util.NoSuchElementException
   *     如果没有下一个元素
   */
  @Override
  boolean next();

  /**
   * 返回后续调用{@link #next}将返回的元素的索引，
   * 如果没有下一个元素，则返回迭代中元素的数量。
   *
   * @return 下一个元素的索引
   */
  int nextIndex();

  /**
   * 返回向后遍历时的下一个元素。
   *
   * @return 前一个元素
   * @throws java.util.NoSuchElementException
   *     如果没有前一个元素
   */
  boolean previous();

  /**
   * 返回后续调用{@link #previous}将返回的元素的索引，
   * 如果没有前一个元素，则返回 {@code -1}。
   *
   * @return 前一个元素的索引
   */
  int previousIndex();

  /**
   * 从底层集合中移除{@link #next}或{@link #previous}返回的最后一个元素（可选操作）。
   *
   * @throws UnsupportedOperationException
   *     如果不支持此操作
   * @throws IllegalStateException
   *     如果尚未调用{@link #next}或{@link #previous}，
   *     或者自上次调用{@link #next}或{@link #previous}以来已经调用了{@link #remove}或{@link #add}
   */
  @Override
  void remove();

  /**
   * 用指定值替换底层集合中{@link #next}或{@link #previous}返回的最后一个元素（可选操作）。
   *
   * @param element
   *     用于替换最后返回元素的值
   * @throws UnsupportedOperationException
   *     如果不支持此操作
   * @throws IllegalStateException
   *     如果尚未调用{@link #next}或{@link #previous}，
   *     或者自上次调用{@link #next}或{@link #previous}以来已经调用了{@link #remove}或{@link #add}
   * @throws IllegalArgumentException
   *     如果指定元素的某些方面阻止其被添加
   */
  void set(boolean element);
}