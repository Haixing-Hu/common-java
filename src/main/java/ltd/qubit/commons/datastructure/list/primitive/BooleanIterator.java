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
 * 遍历 {@code boolean} 值的迭代器。
 *
 * @author 胡海星
 */
public interface BooleanIterator {
  /**
   * 当且仅当我有更多元素时返回 {@code true}。（换句话说，
   * 当且仅当后续调用 {@link #next next} 将返回元素而不是抛出异常时返回 {@code true}。）
   *
   * @return 当且仅当我有更多元素时返回 {@code true}
   */
  boolean hasNext();

  /**
   * 返回我中的下一个元素。
   *
   * @return 我中的下一个元素
   * @throws java.util.NoSuchElementException
   *           如果没有下一个元素
   */
  boolean next();

  /**
   * 从我的底层集合中移除我{@link #next 返回}的最后一个元素（可选操作）。
   *
   * @throws UnsupportedOperationException
   *           如果不支持此操作
   * @throws IllegalStateException
   *           如果尚未调用 {@link #next}，或者自上次调用 {@link #next} 以来已经调用了 {@link #remove}。
   */
  void remove();
}