////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.datastructure.list.primitive;

import java.io.Serializable;

/**
 * {@code boolean} 值的集合。
 *
 * @author 胡海星
 */
public interface BooleanCollection extends Comparable<BooleanCollection>,
    Serializable {

  /**
   * 确保我包含指定的元素（可选操作）。当且仅当我因此调用而改变时返回 {@code true}。
   *
   * <p>如果集合因为除了已经包含该元素之外的任何原因拒绝添加指定元素，它<i>必须</i>抛出异常
   * （而不是简单地返回 {@code false}）。这保持了集合在此调用返回后始终包含指定元素的不变性。
   *
   * @param element
   *     要确保在我内部存在的值
   * @return 当且仅当我因此调用而改变时返回 {@code true}
   * @throws UnsupportedOperationException
   *     当不支持此操作时
   * @throws IllegalArgumentException
   *     如果指定元素的某些方面阻止它被添加到我中，可能会抛出此异常
   */
  boolean add(boolean element);

  /**
   * 将指定集合中的所有元素{@link #add 添加}到我中（可选操作）。
   *
   * @param c
   *     要确保在我内部存在的元素集合
   * @return 当且仅当我因此调用而改变时返回 {@code true}
   * @throws UnsupportedOperationException
   *     当不支持此操作时
   * @throws IllegalArgumentException
   *     如果某些指定元素的某些方面阻止它被添加到我中，可能会抛出此异常
   */
  boolean addAll(BooleanCollection c);

  /**
   * 移除我的所有元素（可选操作）。此方法成功返回后，我将是{@link #isEmpty 空的}。
   *
   * @throws UnsupportedOperationException
   *     当不支持此操作时
   */
  void clear();

  /**
   * 当且仅当我包含指定元素时返回 {@code true}。
   *
   * @param element
   *     要测试其在我内部存在性的值
   * @return 当且仅当我包含指定元素时返回 {@code true}
   */
  boolean contains(boolean element);

  /**
   * 当且仅当我{@link #contains 包含}给定集合中的所有元素时返回 {@code true}。
   *
   * @param c
   *     要测试其在我内部存在性的元素集合
   * @return 当且仅当我包含所有指定元素时返回 {@code true}
   */
  boolean containsAll(BooleanCollection c);

  /**
   * 当且仅当我不包含任何元素时返回 {@code true}。
   *
   * @return 当且仅当我不包含任何元素时返回 {@code true}。
   */
  boolean isEmpty();

  /**
   * 返回遍历我所有元素的{@link BooleanIterator 迭代器}。此基础接口对返回的迭代器
   * 返回元素的顺序不施加任何约束。
   *
   * @return 遍历我所有元素的{@link BooleanIterator 迭代器}。
   */
  BooleanIterator iterator();

  /**
   * 移除我的所有包含在指定集合中的元素（可选操作）。如果在执行此方法时修改了给定集合，
   * 则此方法的行为是未指定的。注意这包括给定集合就是此集合且它不为空的情况。
   *
   * @param c
   *     要移除的元素集合
   * @return 当且仅当我包含至少一个指定元素时返回 {@code true}，换句话说，
   *     当且仅当我因此调用而改变时返回 {@code true}。
   * @throws UnsupportedOperationException
   *     当不支持此操作时
   */
  boolean removeAll(BooleanCollection c);

  /**
   * 移除指定元素的单个出现（可选操作）。
   *
   * @param element
   *     要移除的元素（如果存在）
   * @return 当且仅当我包含指定元素时返回 {@code true}，换句话说，
   *     当且仅当我因此调用而改变时返回 {@code true}。
   * @throws UnsupportedOperationException
   *     当不支持此操作时
   */
  boolean remove(boolean element);

  /**
   * 移除我的所有<i>不</i>包含在指定集合中的元素（可选操作）。（换句话说，<i>仅</i>保留
   * 我的包含在指定集合中的元素。）如果在执行此方法时修改了给定集合，则此方法的行为是未指定的。
   *
   * @param c
   *     要保留的元素集合
   * @return 当且仅当我因此调用而改变时返回 {@code true}
   * @throws UnsupportedOperationException
   *     当不支持此操作时
   */
  boolean retainAll(BooleanCollection c);

  /**
   * 返回我包含的元素数量。
   *
   * @return 我包含的元素数量
   */
  int size();

  /**
   * 返回包含我所有元素的数组。返回数组的长度将等于我的{@link #size 大小}。
   *
   * <p>返回的数组将独立于我，因此调用者可以修改返回的数组而不会修改此集合。
   *
   * <p>当我保证{@link #iterator 迭代器}返回元素的顺序时，返回的数组将以相同顺序包含元素。
   *
   * @return 包含我所有元素的数组
   */
  boolean[] toArray();

  /**
   * 返回包含我所有元素的数组，如果给定数组足够大则使用给定数组。当给定数组的长度大于
   * 我包含的元素数量时，我的范围之外的值将保持不变。
   *
   * <p>返回的数组将独立于我，因此调用者可以修改返回的数组而不会修改此集合。
   *
   * <p>当我保证{@link #iterator 迭代器}返回元素的顺序时，返回的数组将以相同顺序包含元素。
   *
   * @param a
   *     可能用于包含元素的数组
   * @return 包含我所有元素的数组
   */
  boolean[] toArray(boolean[] a);
}