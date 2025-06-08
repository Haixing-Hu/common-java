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
 * {@code long} 值的集合。
 *
 * @author 胡海星
 */
public interface LongCollection extends Comparable<LongCollection>,
    Serializable {

  /**
   * 确保我包含指定的元素（可选操作）。
   *
   * <p>当且仅当我因此调用而更改时，才返回 {@code true}。
   *
   * <p>如果一个集合因为除了它已经包含该元素之外的任何原因拒绝添加指定的元素，
   * 它<i>必须</i>抛出一个异常（而不是简单地返回 {@code false}）。
   * 这保留了在此调用返回后集合始终包含指定元素的不变性。
   *
   * @param element
   *     要确保其在我内部存在的值。
   * @return 当且仅当我因此调用而更改时，才返回 {@code true}。
   * @throws UnsupportedOperationException
   *     当不支持此操作时。
   * @throws IllegalArgumentException
   *     如果指定元素的某些方面阻止它被添加到我这里，则可能会抛出此异常。
   */
  boolean add(long element);

  /**
   * 将指定集合中的所有元素 {@link #add 添加} 到我这里（可选操作）。
   *
   * @param c
   *     要确保其在我内部存在的元素的集合。
   * @return 当且仅当我因此调用而更改时，才返回 {@code true}。
   * @throws UnsupportedOperationException
   *     当不支持此操作时。
   * @throws IllegalArgumentException
   *     如果某些指定元素的某些方面阻止它被添加到我这里，则可能会抛出此异常。
   */
  boolean addAll(LongCollection c);

  /**
   * 移除我的所有元素（可选操作）。
   *
   * <p>此方法成功返回后，我将是 {@link #isEmpty 空}的。
   *
   * @throws UnsupportedOperationException
   *     当不支持此操作时。
   */
  void clear();

  /**
   * 当且仅当我包含指定的元素时，才返回 {@code true}。
   *
   * @param element
   *     要测试其在我内部存在的值。
   * @return 当且仅当我包含指定的元素时，才返回 {@code true}。
   */
  boolean contains(long element);

  /**
   * 当且仅当我 {@link #contains 包含} 给定集合中的所有元素时，才返回 {@code true}。
   *
   * @param c
   *     要测试其在我内部存在的元素的集合。
   * @return 当且仅当我包含所有指定的元素时，才返回 {@code true}。
   */
  boolean containsAll(LongCollection c);

  /**
   * 当且仅当我没有元素时，才返回 {@code true}。
   *
   * @return 当且仅当我没有元素时，才返回 {@code true}。
   */
  boolean isEmpty();

  /**
   * 返回一个覆盖我所有元素的 {@link LongIterator iterator}。
   *
   * <p>此基本接口对返回的迭代器返回元素的顺序没有任何限制。
   *
   * @return 一个覆盖我所有元素的 {@link LongIterator iterator}。
   */
  LongIterator iterator();

  /**
   * 移除所有包含在指定集合中的我的元素（可选操作）。
   *
   * <p>如果在执行此方法时修改了给定的集合，则此方法的行为未指定。
   * 请注意，这包括给定集合是此集合且不为空的情况。
   *
   * @param c
   *     要移除的元素的集合。
   * @return 当且仅当我至少包含一个指定的元素时，才返回 {@code true}，
   *     换句话说，当且仅当我因此调用而更改时，才返回 {@code true}。
   * @throws UnsupportedOperationException
   *     当不支持此操作时。
   */
  boolean removeAll(LongCollection c);

  /**
   * 移除指定元素的单个出现（可选操作）。
   *
   * @param element
   *     要移除的元素（如果存在）。
   * @return 当且仅当我包含指定的元素时，才返回 {@code true}，换句话说，
   *     当且仅当我因此调用而更改时，才返回 {@code true}。
   * @throws UnsupportedOperationException
   *     当不支持此操作时。
   */
  boolean remove(long element);

  /**
   * 移除所有<i>不</i>包含在指定集合中的我的元素（可选操作）。
   *
   * <p>换句话说，<i>仅</i>保留包含在指定集合中的我的元素。
   * 如果在执行此方法时修改了给定的集合，则此方法的行为未指定。
   *
   * @param c
   *     要保留的元素的集合。
   * @return 当且仅当我因此调用而更改时，才返回 {@code true}。
   * @throws UnsupportedOperationException
   *     当不支持此操作时。
   */
  boolean retainAll(LongCollection c);

  /**
   * 返回我包含的元素数量。
   *
   * @return 我包含的元素数量。
   */
  int size();

  /**
   * 返回一个包含我所有元素的数组。
   *
   * <p>返回的数组的长度将等于我的 {@link #size size}。
   *
   * <p>返回的数组将独立于我，因此调用者可以修改返回的数组而无需修改此集合。
   *
   * <p>当我保证 {@link #iterator iterator} 返回元素的顺序时，返回的数组将以相同的顺序包含元素。
   *
   * @return 一个包含我所有元素的数组。
   */
  long[] toArray();

  /**
   * 返回一个包含我所有元素的数组，如果给定的数组足够大，则使用该数组。
   *
   * <p>当给定数组的长度大于我包含的元素数量时，超出我范围的值将保持不变。
   *
   * <p>返回的数组将独立于我，因此调用者可以修改返回的数组而无需修改此集合。
   *
   * <p>当我保证 {@link #iterator iterator} 返回元素的顺序时，返回的数组将以相同的顺序包含元素。
   *
   * @param a
   *     可用于包含元素的数组。
   * @return 一个包含我所有元素的数组。
   */
  long[] toArray(long[] a);
}