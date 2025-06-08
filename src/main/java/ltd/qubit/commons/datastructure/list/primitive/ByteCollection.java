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
 * {@code byte} 值的集合。
 *
 * @author 胡海星
 */
public interface ByteCollection extends Comparable<ByteCollection>,
    Serializable {

  /**
   * 确保此集合包含指定元素（可选操作）。
   * 如果此调用导致集合发生变化，则返回 {@code true}。
   *
   * <p>如果集合因为除了已包含该元素之外的任何原因拒绝添加指定元素，
   * 它<i>必须</i>抛出异常（而不是简单地返回 {@code false}）。
   * 这保持了集合在此调用返回后始终包含指定元素的不变性。
   *
   * @param element
   *     要确保存在于此集合中的值
   * @return 如果此调用导致集合发生变化，则返回 {@code true}
   * @throws UnsupportedOperationException
   *     当不支持此操作时
   * @throws IllegalArgumentException
   *     如果指定元素的某些方面阻止其被添加到此集合中，可能抛出此异常
   */
  boolean add(byte element);

  /**
   * 将指定集合中的所有元素{@link #add 添加}到此集合中（可选操作）。
   *
   * @param c
   *     要确保存在于此集合中的元素集合
   * @return 如果此调用导致集合发生变化，则返回 {@code true}
   * @throws UnsupportedOperationException
   *     当不支持此操作时
   * @throws IllegalArgumentException
   *     如果某些指定元素的某些方面阻止其被添加到此集合中，可能抛出此异常
   */
  boolean addAll(ByteCollection c);

  /**
   * 移除此集合中的所有元素（可选操作）。
   * 此方法成功返回后，此集合将为{@link #isEmpty 空}。
   *
   * @throws UnsupportedOperationException
   *     当不支持此操作时
   */
  void clear();

  /**
   * 当且仅当此集合包含指定元素时返回 {@code true}。
   *
   * @param element
   *     要测试其在此集合中存在性的值
   * @return 当且仅当此集合包含指定元素时返回 {@code true}
   */
  boolean contains(byte element);

  /**
   * 当且仅当此集合{@link #contains 包含}给定集合中的所有元素时返回 {@code true}。
   *
   * @param c
   *     要测试其在此集合中存在性的元素集合
   * @return 当且仅当此集合包含所有指定元素时返回 {@code true}
   */
  boolean containsAll(ByteCollection c);

  /**
   * 当且仅当此集合不包含任何元素时返回 {@code true}。
   *
   * @return 当且仅当此集合不包含任何元素时返回 {@code true}
   */
  boolean isEmpty();

  /**
   * 返回遍历此集合所有元素的{@link ByteIterator 迭代器}。
   * 此基础接口对返回的迭代器返回元素的顺序不施加任何约束。
   *
   * @return 遍历此集合所有元素的{@link ByteIterator 迭代器}
   */
  ByteIterator iterator();

  /**
   * 移除此集合中包含在指定集合中的所有元素（可选操作）。
   *
   * <p>如果在执行此方法时修改了给定集合，则此方法的行为是未指定的。
   * 注意，这包括给定集合就是此集合且不为空的情况。
   *
   * @param c
   *     要移除的元素集合
   * @return 当且仅当此集合包含至少一个指定元素时返回 {@code true}，
   *     换句话说，当且仅当此调用导致集合发生变化时返回 {@code true}
   * @throws UnsupportedOperationException
   *     当不支持此操作时
   */
  boolean removeAll(ByteCollection c);

  /**
   * 移除指定元素的单个出现（可选操作）。
   *
   * @param element
   *     要移除的元素（如果存在）
   * @return 当且仅当此集合包含指定元素时返回 {@code true}，
   *     换句话说，当且仅当此调用导致集合发生变化时返回 {@code true}
   * @throws UnsupportedOperationException
   *     当不支持此操作时
   */
  boolean remove(byte element);

  /**
   * 移除此集合中<i>不</i>包含在指定集合中的所有元素（可选操作）。
   *
   * <p>换句话说，<i>仅</i>保留此集合中包含在指定集合中的元素。
   * 如果在执行此方法时修改了给定集合，则此方法的行为是未指定的。
   *
   * @param c
   *     要保留的元素集合
   * @return 如果此调用导致集合发生变化，则返回 {@code true}
   * @throws UnsupportedOperationException
   *     当不支持此操作时
   */
  boolean retainAll(ByteCollection c);

  /**
   * 返回此集合包含的元素数量。
   *
   * @return 此集合包含的元素数量
   */
  int size();

  /**
   * 返回包含此集合所有元素的数组。
   * 返回数组的长度将等于此集合的{@link #size 大小}。
   *
   * <p>返回的数组将独立于此集合，因此调用者可以修改返回的数组而不会修改此集合。
   *
   * <p>当此集合保证{@link #iterator 迭代器}返回元素的顺序时，
   * 返回的数组将以相同的顺序包含元素。
   *
   * @return 包含此集合所有元素的数组
   */
  byte[] toArray();

  /**
   * 返回包含此集合所有元素的数组，如果给定数组足够大，则使用给定数组。
   * 当给定数组的长度大于此集合包含的元素数量时，超出范围的值将保持不变。
   *
   * <p>返回的数组将独立于此集合，因此调用者可以修改返回的数组而不会修改此集合。
   *
   * <p>当此集合保证{@link #iterator 迭代器}返回元素的顺序时，
   * 返回的数组将以相同的顺序包含元素。
   *
   * @param a
   *     可用于包含元素的数组
   * @return 包含此集合所有元素的数组
   */
  byte[] toArray(byte[] a);
}