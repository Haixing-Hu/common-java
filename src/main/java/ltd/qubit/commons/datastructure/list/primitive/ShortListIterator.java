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
 * {@code short} 值的双向迭代器。
 *
 * @author 胡海星
 */
public interface ShortListIterator extends ShortIterator {

  /**
   * 将指定的元素插入我的底层集合中（可选操作）。
   *
   * <p>该元素被插入到紧接在 {@link #next} 将返回的下一个元素（如有）之前，
   * 并紧接在 {@link #previous} 将返回的下一个元素（如有）之后。
   *
   * <p>新元素被插入到隐含光标之前。随后调用 {@link #previous} 将返回添加的元素，
   * 随后调用 {@link #next} 将不受影响。此调用将使调用 {@link #nextIndex} 或
   * {@link #previousIndex} 返回的值增加一。
   *
   * @param element
   *     要插入的值。
   * @throws UnsupportedOperationException
   *     当不支持此操作时。
   * @throws IllegalArgumentException
   *     如果指定元素的某些方面阻止它被添加。
   */
  void add(short element);

  /**
   * 当我按正向遍历时，当且仅当我还有更多元素时返回 {@code true}。
   *
   * <p>换句话说，当且仅当调用 {@link #next} 将返回一个元素而不是抛出异常时返回 {@code true}。
   *
   * @return 当我按正向遍历时，当且仅当我还有更多元素时返回 {@code true}。
   */
  @Override
  boolean hasNext();

  /**
   * 当我按反向遍历时，当且仅当我还有更多元素时返回 {@code true}。
   *
   * <p>换句话说，当且仅当调用 {@link #previous} 将返回一个元素而不是抛出异常时返回 {@code true}。
   *
   * @return 当我按反向遍历时，当且仅当我还有更多元素时返回 {@code true}。
   */
  boolean hasPrevious();

  /**
   * 当我按正向遍历时，返回我的下一个元素。
   *
   * @return 我的下一个元素。
   * @throws java.util.NoSuchElementException
   *     如果没有下一个元素。
   */
  @Override
  short next();

  /**
   * 返回后续调用 {@link #next} 将返回的元素的当前值，如果我没有下一个元素，则返回我迭代中的元素数。
   *
   * @return 我的下一个元素的当前值。
   */
  int nextIndex();

  /**
   * 当我按反向遍历时，返回我的下一个元素。
   *
   * @return 我的上一个元素。
   * @throws java.util.NoSuchElementException
   *     如果没有上一个元素。
   */
  short previous();

  /**
   * 返回后续调用 {@link #previous} 将返回的元素的当前值，如果我没有上一个元素，则返回 {@code -1}。
   *
   * @return 我的上一个元素的当前值。
   */
  int previousIndex();

  /**
   * 从我的底层集合中移除最后一次由 {@link #next} 或 {@link #previous} 返回的元素（可选操作）。
   *
   * @throws UnsupportedOperationException
   *     如果不支持此操作。
   * @throws IllegalStateException
   *     如果尚未调用 {@link #next} 或 {@link #previous}，或者自上次调用
   *     {@link #next} 或 {@link #previous} 以来已经调用了 {@link #remove} 或 {@link #add}。
   */
  @Override
  void remove();

  /**
   * 用指定的值替换我的底层集合中最后一次由 {@link #next} 或 {@link #previous} 返回的元素（可选操作）。
   *
   * @param element
   *     用来替换最后返回的元素的值。
   * @throws UnsupportedOperationException
   *     如果不支持此操作。
   * @throws IllegalStateException
   *     如果尚未调用 {@link #next} 或 {@link #previous}，或者自上次调用
   *     {@link #next} 或 {@link #previous} 以来已经调用了 {@link #remove} 或 {@link #add}。
   * @throws IllegalArgumentException
   *     如果指定元素的某些方面阻止它被添加。
   */
  void set(short element);
}