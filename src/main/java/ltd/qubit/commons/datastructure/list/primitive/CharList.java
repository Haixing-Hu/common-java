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
 * {@code char} 值的有序集合。
 *
 * @author 胡海星
 */
public interface CharList extends CharCollection {

  /**
   * 将指定元素添加到列表末尾（可选操作）。
   * 如果我因此调用而发生变化，则返回 {@code true}。
   *
   * <p>如果集合因除了已包含该元素之外的任何原因拒绝添加指定元素，
   * 它 <i>必须</i> 抛出异常（而不是简单地返回 {@code false}）。
   * 这保留了集合在此调用返回后始终包含指定元素的不变性。
   *
   * @param element
   *     要确保在我中存在的值。
   * @return 如果我因此调用而发生变化，则返回 {@code true}。
   * @throws UnsupportedOperationException
   *     当不支持此操作时。
   * @throws IllegalArgumentException
   *     如果指定元素的某些方面阻止将其添加到我中，可能会抛出此异常。
   */
  @Override
  boolean add(char element);

  /**
   * 在指定位置插入指定元素（可选操作）。
   * 将当前在该位置的元素（如果有）和任何后续元素向右移动，增加它们的索引。
   *
   * @param index
   *     要插入元素的索引。
   * @param element
   *     要插入的值。
   * @throws UnsupportedOperationException
   *     当不支持此操作时。
   * @throws IllegalArgumentException
   *     如果指定元素的某些方面阻止将其添加到我中。
   * @throws IndexOutOfBoundsException
   *     如果指定的索引超出范围。
   */
  void add(int index, char element);

  /**
   * 将指定集合中的所有元素插入到我中的指定位置（可选操作）。
   *
   * <p>将当前在该位置的元素（如果有）和任何后续元素向右移动，增加它们的索引。
   * 新元素将按照给定集合的 {@link CharCollection#iterator 迭代器} 返回的顺序出现。
   *
   * @param index
   *     要插入指定集合中第一个元素的索引。
   * @param collection
   *     要添加的 {@link CharCollection CharCollection} 元素。
   * @return 如果我因此调用而发生变化，则返回 {@code true}。
   * @throws UnsupportedOperationException
   *     当不支持此操作时。
   * @throws IndexOutOfBoundsException
   *     如果指定的索引超出范围。
   */
  boolean addAll(int index, CharCollection collection);

  /**
   * 当且仅当 <i>that</i> 是一个 {@code CharList}，
   * 并且包含与我相同的元素且顺序相同时，返回 {@code true}。
   *
   * <p>换句话说，当且仅当 <i>that</i> 是一个 {@code CharList}，
   * 具有与我相同的 {@link #size() 大小}，并且其 {@link CharList#iterator 迭代器}
   * 返回的元素与我中的相应元素相等（{@code ==}）时，返回 {@code true}。
   * （此约定确保此方法在 {@code CharList} 接口的不同实现中正常工作。）
   *
   * @param that
   *     要与我比较的对象。
   * @return 当且仅当 <i>that</i> 是一个 {@code CharList}，
   *     并且包含与我相同的元素且顺序相同时，返回 {@code true}。
   */
  @Override
  boolean equals(Object that);

  /**
   * 返回我中指定位置的元素值。
   *
   * @param index
   *     要返回的元素的索引。
   * @return 指定位置的元素值。
   * @throws IndexOutOfBoundsException
   *     如果指定的索引超出范围。
   */
  char get(int index);

  /**
   * 返回我的哈希码。
   *
   * <p>{@code CharList} 的哈希码定义为以下计算的结果：
   *
   * <pre>
   * int hash = 1;
   * for (CharIterator iter = iterator(); iter.hasNext();) {
   *   char value = iter.next();
   *   hash = 31 * hash + (int) (value &circ; (value &gt;&gt;&gt; 32));
   * }
   * </pre>
   *
   * <p>此约定确保此方法与 {@link #equals equals} 以及
   * {@link Character} 的 {@link java.util.List List} 的 {@link java.util.List#hashCode hashCode} 方法一致。
   *
   * @return 我的哈希码。
   */
  @Override
  int hashCode();

  /**
   * 返回我中指定元素第一次出现的索引，如果我不包含该元素，则返回 {@code -1}。
   *
   * @param element
   *     要搜索的元素。
   * @return 匹配指定值的元素的最小索引，如果找不到这样的匹配元素，则返回 {@code -1}。
   */
  int indexOf(char element);

  /**
   * 返回按适当顺序遍历所有元素的 {@link CharIterator 迭代器}。
   *
   * @return 遍历所有元素的 {@link CharIterator 迭代器}。
   */
  @Override
  CharIterator iterator();

  /**
   * 返回我中指定元素最后一次出现的索引，如果我不包含该元素，则返回 -1。
   *
   * @param element
   *     要搜索的元素。
   * @return 匹配指定值的元素的最大索引，如果找不到这样的匹配元素，则返回 {@code -1}。
   */
  int lastIndexOf(char element);

  /**
   * 返回按适当顺序遍历我的所有元素的 {@link CharListIterator 双向迭代器}。
   *
   * @return 遍历所有元素的 {@link CharListIterator 迭代器}。
   */
  CharListIterator listIterator();

  /**
   * 返回从指定位置开始，按适当顺序遍历我的所有元素的 {@link CharListIterator 双向迭代器}。
   *
   * <p>指定的 <i>索引</i> 表示初次调用 {@link CharListIterator#next next} 方法将返回的第一个元素。
   * 初次调用 {@link CharListIterator#previous previous} 方法将返回指定 <i>索引</i> 减一的元素。
   *
   * @param index
   *     指定的索引。
   * @return 指定的 {@link CharListIterator 迭代器}。
   * @throws IndexOutOfBoundsException
   *     如果指定的索引超出范围。
   */
  CharListIterator listIterator(int index);

  /**
   * 移除指定位置的元素（可选操作）。
   * 任何后续元素都向左移动，从它们的索引中减一。返回被移除的元素。
   *
   * @param index
   *     要移除的元素的索引。
   * @return 被移除的元素的值。
   * @throws UnsupportedOperationException
   *     当不支持此操作时。
   * @throws IndexOutOfBoundsException
   *     如果指定的索引超出范围。
   */
  char removeAt(int index);

  /**
   * 用指定元素替换我中指定位置的元素（可选操作）。
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
  char set(int index, char element);

  /**
   * 返回我中指定 <i>fromIndex</i>（包含）和 <i>toIndex</i>（不包含）之间元素的视图。
   *
   * <p>返回的 {@code CharList} 由我支持，因此返回列表中的任何更改都会反映在我中，反之亦然。
   * 返回的列表支持我支持的所有可选操作。
   *
   * <p>注意当 <code><i>fromIndex</i> == <i>toIndex</i></code> 时，
   * 返回的列表最初为空，当 <code><i>fromIndex</i> == 0 &amp;&amp;<i>toIndex</i> == {@link #size() size()}</code>
   * 时，返回的列表是我的"不当"子列表，包含我的所有元素。
   *
   * <p>如果我以除通过返回列表之外的任何方式进行结构修改，返回列表的语义将变得未定义。
   *
   * @param fromIndex
   *     出现在返回列表中的我中的最小索引（包含）。
   * @param toIndex
   *     出现在返回列表中的我中的最大索引（不包含）。
   * @return 从 <i>fromIndex</i>（包含）到 <i>toIndex</i>（不包含）的此列表的视图。
   * @throws IndexOutOfBoundsException
   *     如果任一指定索引超出范围。
   */
  CharList subList(int fromIndex, int toIndex);

  /**
   * 将列表按升序数值顺序排序。
   */
  void sort();

  /**
   * 对此已排序列表中的值进行去重。
   *
   * <p>假设列表已排序，即相同的值应连续放置。</p>
   */
  void unique();
}