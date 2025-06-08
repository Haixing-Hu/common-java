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
 * {@code double} 值的有序集合。
 *
 * @author 胡海星
 */
public interface DoubleList extends DoubleCollection {

  /**
   * 将指定元素追加到我的末尾（可选操作）。
   * 当且仅当因为此调用而改变了我时返回 {@code true}。
   *
   * <p>如果集合因为除了已经包含该元素之外的任何原因拒绝添加指定的元素，
   * 它<i>必须</i>抛出异常（而不是简单地返回 {@code false}）。这保持了
   * 此调用返回后集合总是包含指定元素的不变性。
   *
   * @param element
   *     要确保在我中存在的值。
   * @return 当且仅当因为此调用而改变了我时返回 {@code true}。
   * @throws UnsupportedOperationException
   *     当不支持此操作时。
   * @throws IllegalArgumentException
   *     如果指定元素的某些方面阻止其被添加到我中，可能会抛出。
   */
  @Override
  boolean add(double element);

  /**
   * 在指定位置插入指定元素（可选操作）。将当前位于该位置的元素（如果有）
   * 以及任何后续元素向右移动，增加它们的索引。
   *
   * @param index
   *     要插入元素的索引。
   * @param element
   *     要插入的值
   * @throws UnsupportedOperationException
   *     当不支持此操作时。
   * @throws IllegalArgumentException
   *     如果指定元素的某些方面阻止其被添加到我中。
   * @throws IndexOutOfBoundsException
   *     如果指定的索引超出范围。
   */
  void add(int index, double element);

  /**
   * 将指定集合中的所有元素插入到我的指定位置（可选操作）。
   *
   * <p>将当前位于该位置的元素（如果有）以及任何后续元素向右移动，
   * 增加它们的索引。新元素将按照指定集合的 {@link #iterator} 返回的顺序出现。
   *
   * @param index
   *     要插入指定集合中第一个元素的索引
   * @param collection
   *     要添加的元素的 {@link DoubleCollection}。
   * @return 当且仅当因为此调用而改变了我时返回 {@code true}。
   * @throws UnsupportedOperationException
   *     当不支持此操作时。
   * @throws IndexOutOfBoundsException
   *     如果指定的索引超出范围。
   */
  boolean addAll(int index, DoubleCollection collection);

  /**
   * 当且仅当 <i>that</i> 是一个 {@code DoubleList} 且以相同的顺序包含相同的元素时返回 {@code true}。
   *
   * <p>换句话说，当且仅当 <i>that</i> 是一个 {@code DoubleList} 且具有与我相同的 {@link #size()}，
   * 并且其 {@link #iterator} 返回的元素与我的相应元素相等（{@code ==}）时返回 {@code true}。
   *
   * <p>此契约确保此方法在 {@code DoubleList} 接口的不同实现间正常工作。
   *
   * @param that
   *     要与我比较的对象。
   * @return 当且仅当 <i>that</i> 是一个 {@code DoubleList} 且以相同的顺序包含相同的元素时返回 {@code true}。
   */
  @Override
  boolean equals(Object that);

  /**
   * 返回我中指定位置的元素值。
   *
   * @param index
   *     要返回的元素的索引
   * @return 指定位置元素的值
   * @throws IndexOutOfBoundsException
   *     如果指定的索引超出范围
   */
  double get(int index);

  /**
   * 返回我的哈希码。
   *
   * <p>{@code DoubleList} 的哈希码定义为以下计算的结果：
   *
   * <pre>
   * int hash = 1;
   * for (DoubleIterator iter = iterator(); iter.hasNext();) {
   *   double value = iter.next();
   *   hash = 31 * hash + (int) (value &circ; (value &gt;&gt;&gt; 32));
   * }
   * </pre>
   *
   * <p>此契约确保此方法与 {@link #equals equals} 以及 {@link Double} 的 {@link java.util.List List} 
   * 的 {@link java.util.List#hashCode hashCode} 方法一致。
   *
   * @return 我的哈希码
   */
  @Override
  int hashCode();

  /**
   * 返回我中指定元素第一次出现的索引，如果我不包含该元素则返回 {@code -1}。
   *
   * @param element
   *     要搜索的元素
   * @return 匹配指定值的元素的最小索引，如果找不到匹配的元素则返回 {@code -1}
   */
  int indexOf(double element);

  /**
   * 返回所有元素的 {@link DoubleIterator iterator}，按适当的顺序。
   *
   * @return 所有元素的 {@link DoubleIterator iterator}。
   */
  @Override
  DoubleIterator iterator();

  /**
   * 返回我中指定元素最后一次出现的索引，如果我不包含该元素则返回 -1。
   *
   * @param element
   *     要搜索的元素
   * @return 匹配指定值的元素的最大索引，如果找不到匹配的元素则返回 {@code -1}
   */
  int lastIndexOf(double element);

  /**
   * 返回我所有元素的 {@link DoubleListIterator 双向迭代器}，按适当的顺序。
   *
   * @return 所有元素的 {@link DoubleListIterator iterator}。
   */
  DoubleListIterator listIterator();

  /**
   * 返回我所有元素的 {@link DoubleListIterator 双向迭代器}，按适当的顺序，从指定位置开始。
   *
   * <p>指定的 <i>索引</i> 指示初始调用 {@link DoubleListIterator#next next} 
   * 方法将返回的第一个元素。初始调用 {@link DoubleListIterator#previous previous} 
   * 方法将返回指定 <i>索引</i> 减一的元素。
   *
   * @param index
   *     指定的索引。
   * @return 指定的 {@link DoubleListIterator iterator}。
   * @throws IndexOutOfBoundsException
   *     如果指定的索引超出范围
   */
  DoubleListIterator listIterator(int index);

  /**
   * 移除指定位置的元素（可选操作）。任何后续元素都向左移动，
   * 从它们的索引中减一。返回被移除的元素。
   *
   * @param index
   *     要移除的元素的索引
   * @return 被移除元素的值
   * @throws UnsupportedOperationException
   *     当不支持此操作时。
   * @throws IndexOutOfBoundsException
   *     如果指定的索引超出范围
   */
  double removeAt(int index);

  /**
   * 用指定元素替换我中指定位置的元素（可选操作）。
   *
   * @param index
   *     要更改的元素的索引
   * @param element
   *     要存储在指定位置的值
   * @return 先前存储在指定位置的值
   * @throws UnsupportedOperationException
   *     当不支持此操作时
   * @throws IndexOutOfBoundsException
   *     如果指定的索引超出范围
   */
  double set(int index, double element);

  /**
   * 返回我中指定 <i>fromIndex</i>（包含）和 <i>toIndex</i>（不包含）之间元素的视图。
   *
   * <p>返回的 {@code DoubleList} 由我支持，因此返回列表中的任何更改都会反映在我中，反之亦然。
   * 返回的列表支持我支持的所有可选操作。
   *
   * <p>注意，当 <code><i>fromIndex</i> == <i>toIndex</i></code> 时，
   * 返回的列表最初为空，当 <code><i>fromIndex</i> == 0 
   * &amp;&amp; <i>toIndex</i> == {@link #size()}</code> 时，
   * 返回的列表是我的"不当"子列表，包含我的所有元素。
   *
   * <p>如果除了通过返回的列表之外以任何方式在结构上修改我，
   * 返回列表的语义将变得未定义。
   *
   * @param fromIndex
   *     出现在返回列表中的我中最小的索引（包含）。
   * @param toIndex
   *     出现在返回列表中的我中最大的索引（不包含）。
   * @return 从 <i>fromIndex</i>（包含）到 <i>toIndex</i>（不包含）的此列表的视图。
   * @throws IndexOutOfBoundsException
   *     如果任一指定索引超出范围。
   */
  DoubleList subList(int fromIndex, int toIndex);

  /**
   * 将列表按升序数值排序。
   */
  void sort();

  /**
   * 去除此已排序列表中的重复值。
   *
   * <p>假设列表已排序，即相同的值应连续放置。</p>
   */
  void unique();
}