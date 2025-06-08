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
 * {@code int} 值的有序集合。
 *
 * @author 胡海星
 */
public interface IntList extends IntCollection {

  /**
   * 将指定的元素追加到我的末尾（可选操作）。
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
  @Override
  boolean add(int element);

  /**
   * 在指定位置插入指定的元素（可选操作）。
   *
   * <p>将当前在该位置的元素（如果有）和任何后续元素向右移动，增加它们的索引。
   *
   * @param index
   *     要插入元素的索引。
   * @param element
   *     要插入的值。
   * @throws UnsupportedOperationException
   *     当不支持此操作时。
   * @throws IllegalArgumentException
   *     如果指定元素的某些方面阻止它被添加到我这里。
   * @throws IndexOutOfBoundsException
   *     如果指定的索引超出范围。
   */
  void add(int index, int element);

  /**
   * 将指定集合中的所有元素插入到我的指定位置（可选操作）。
   *
   * <p>将当前在该位置的元素（如果有）和任何后续元素向右移动，增加它们的索引。
   * 新元素将按照给定集合的 {@link IntCollection#iterator iterator} 返回的顺序出现。
   *
   * @param index
   *     要插入指定集合中第一个元素的索引。
   * @param collection
   *     要添加的元素的 {@link IntCollection IntCollection}。
   * @return 当且仅当我因此调用而更改时，才返回 {@code true}。
   * @throws UnsupportedOperationException
   *     当不支持此操作时。
   * @throws IndexOutOfBoundsException
   *     如果指定的索引超出范围。
   */
  boolean addAll(int index, IntCollection collection);

  /**
   * 当且仅当 <i>that</i> 是一个 {@code IntList}，其中包含与我相同顺序的相同元素时，
   * 才返回 {@code true}。
   *
   * <p>换句话说，当且仅当 <i>that</i> 是一个 {@code IntList}，其 {@link #size()} 与我相同，
   * 并且其 {@link #iterator} 返回的元素等于（{@code ==}）我内部的相应元素时，
   * 才返回 {@code true}。此约定确保此方法在 {@code IntList} 接口的不同实现中正常工作。
   *
   * @param that
   *     要与我比较的对象。
   * @return 当且仅当 <i>that</i> 是一个 {@code IntList}，其中包含与我相同顺序的相同元素时，
   *     才返回 {@code true}。
   */
  @Override
  boolean equals(Object that);

  /**
   * 返回我内部指定位置元素的值。
   *
   * @param index
   *     要返回的元素的索引。
   * @return 指定位置元素的值。
   * @throws IndexOutOfBoundsException
   *     如果指定的索引超出范围。
   */
  int get(int index);

  /**
   * 返回我的哈希码。
   *
   * <p>一个 {@code IntList} 的哈希码定义为以下计算的结果：
   *
   * <pre>
   * int hash = 1;
   * for (IntIterator iter = iterator(); iter.hasNext();) {
   *   hash = 31 * hash + iter.next();
   * }
   * </pre>
   *
   * <p>此约定确保此方法与 {@link #equals equals} 以及
   * {@link java.util.List} of {@link Integer}s 的
   * {@link java.util.List#hashCode hashCode} 方法一致。
   *
   * @return 我的哈希码。
   */
  @Override
  int hashCode();

  /**
   * 返回指定元素在我内部首次出现的当前值，如果我不包含该元素，则返回 {@code -1}。
   *
   * @param element
   *     要搜索的元素。
   * @return 匹配指定值的元素的最小当前值，如果找不到这样的匹配元素，则返回 {@code -1}。
   */
  int indexOf(int element);

  /**
   * 返回一个覆盖所有元素的 {@link IntIterator iterator}，按适当的顺序。
   *
   * @return 一个覆盖所有元素的 {@link IntIterator iterator}。
   */
  @Override
  IntIterator iterator();

  /**
   * 返回指定元素在我内部最后一次出现的当前值，如果我不包含该元素，则返回 -1。
   *
   * @param element
   *     要搜索的元素。
   * @return 匹配指定值的元素的最大当前值，如果找不到这样的匹配元素，则返回 {@code -1}。
   */
  int lastIndexOf(int element);

  /**
   * 返回一个覆盖我所有元素的 {@link IntListIterator 双向迭代器}，按适当的顺序。
   *
   * @return 一个覆盖所有元素的 {@link IntListIterator iterator}。
   */
  IntListIterator listIterator();

  /**
   * 返回一个覆盖我所有元素的 {@link IntListIterator 双向迭代器}，按适当的顺序，从指定位置开始。
   *
   * <p>指定的<i>当前</i>位置表示初始调用 {@link IntListIterator#next next} 方法将返回的第一个元素。
   * 初始调用 {@link IntListIterator#previous previous} 方法将返回具有指定<i>当前</i>位置减一的元素。
   *
   * @param index
   *     指定的索引。
   * @return 指定的 {@link IntListIterator iterator}。
   * @throws IndexOutOfBoundsException
   *     如果指定的索引超出范围。
   */
  IntListIterator listIterator(int index);

  /**
   * 移除指定位置的元素（可选操作）。
   *
   * <p>任何后续元素都向左移动，其索引减一。返回被移除的元素。
   *
   * @param index
   *     要移除的元素的索引。
   * @return 被移除的元素的值。
   * @throws UnsupportedOperationException
   *     当不支持此操作时。
   * @throws IndexOutOfBoundsException
   *     如果指定的索引超出范围。
   */
  int removeAt(int index);

  /**
   * 用指定的元素替换我内部指定位置的元素（可选操作）。
   *
   * @param index
   *     要更改的元素的索引。
   * @param element
   *     要存储在指定位置的值。
   * @return 先前存储在指定位置的值。
   * @throws UnsupportedOperationException
   *     当不支持此操作时。
   * @throws IndexOutOfBoundsException
   *     如果指定的索引超出范围。
   */
  int set(int index, int element);

  /**
   * 返回我内部介于指定的 <i>fromIndex</i>（含）和 <i>toIndex</i>（不含）之间的元素视图。
   *
   * <p>返回的 {@code IntList} 由我支持，因此返回列表中的任何更改都会反映在我身上，反之亦然。
   * 返回的列表支持我支持的所有可选操作。
   *
   * <p>请注意，当 <code><i>fromIndex</i> == <i>toIndex</i></code> 时，
   * 返回的列表最初为空；当
   * <code><i>fromIndex</i> == 0 &amp;&amp; <i>toIndex</i> == {@link #size()}</code>
   * 时，返回的列表是我的"不当"子列表，包含我的所有元素。
   *
   * <p>如果我除了通过返回的列表之外以任何方式被结构性修改，返回列表的语义将变得未定义。
   *
   * @param fromIndex
   *     出现在返回列表中的我的最小当前值（含）。
   * @param toIndex
   *     出现在返回列表中的我的最大当前值（不含）。
   * @return 从 <i>fromIndex</i>（含）到 <i>toIndex</i>（不含）的此列表的视图。
   * @throws IndexOutOfBoundsException
   *     如果任一指定的当前值超出范围。
   */
  IntList subList(int fromIndex, int toIndex);

  /**
   * 将列表按升序数字顺序排序。
   */
  void sort();

  /**
   * 对此排序列表中的值进行唯一化处理。
   *
   * <p>假定该列表已排序，即相同的值应连续放置。</p>
   */
  void unique();
}