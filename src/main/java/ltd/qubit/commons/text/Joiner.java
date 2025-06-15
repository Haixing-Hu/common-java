////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import javax.annotation.Nullable;

import ltd.qubit.commons.datastructure.list.primitive.BooleanList;
import ltd.qubit.commons.datastructure.list.primitive.BooleanListIterator;
import ltd.qubit.commons.datastructure.list.primitive.ByteList;
import ltd.qubit.commons.datastructure.list.primitive.ByteListIterator;
import ltd.qubit.commons.datastructure.list.primitive.CharList;
import ltd.qubit.commons.datastructure.list.primitive.CharListIterator;
import ltd.qubit.commons.datastructure.list.primitive.DoubleList;
import ltd.qubit.commons.datastructure.list.primitive.DoubleListIterator;
import ltd.qubit.commons.datastructure.list.primitive.FloatList;
import ltd.qubit.commons.datastructure.list.primitive.FloatListIterator;
import ltd.qubit.commons.datastructure.list.primitive.IntList;
import ltd.qubit.commons.datastructure.list.primitive.IntListIterator;
import ltd.qubit.commons.datastructure.list.primitive.LongList;
import ltd.qubit.commons.datastructure.list.primitive.LongListIterator;
import ltd.qubit.commons.datastructure.list.primitive.ShortList;
import ltd.qubit.commons.datastructure.list.primitive.ShortListIterator;
import ltd.qubit.commons.lang.BooleanUtils;
import ltd.qubit.commons.lang.ByteUtils;
import ltd.qubit.commons.lang.CharUtils;
import ltd.qubit.commons.lang.DoubleUtils;
import ltd.qubit.commons.lang.FloatUtils;
import ltd.qubit.commons.lang.IntUtils;
import ltd.qubit.commons.lang.LongUtils;
import ltd.qubit.commons.lang.ObjectUtils;
import ltd.qubit.commons.lang.ShortUtils;

import static ltd.qubit.commons.lang.Argument.requireNonNull;
import static ltd.qubit.commons.lang.StringUtils.EMPTY;
import static ltd.qubit.commons.lang.StringUtils.nullToEmpty;

/**
 * 用于将值连接为字符串的类。
 *
 * <p>此类提供了一种链式调用的方式来构建由分隔符连接的字符串。支持各种基本数据类型、
 * 包装类型、集合、数组以及自定义对象的连接。</p>
 *
 * <h3>基本用法</h3>
 * <pre>{@code
 * // 使用逗号分隔符
 * String result = new Joiner(",")
 *     .add("apple")
 *     .add("banana")
 *     .add("cherry")
 *     .toString();
 * // 结果: "apple,banana,cherry"
 * }</pre>
 *
 * <h3>带前缀和后缀</h3>
 * <pre>{@code
 * // 创建带括号的列表格式
 * String result = new Joiner(", ", "[", "]")
 *     .add("item1")
 *     .add("item2")
 *     .add("item3")
 *     .toString();
 * // 结果: "[item1, item2, item3]"
 * }</pre>
 *
 * <h3>处理null值</h3>
 * <pre>{@code
 * // 忽略null值
 * String result = new Joiner(",")
 *     .ignoreNull(true)
 *     .add("value1")
 *     .add(null)
 *     .add("value2")
 *     .toString();
 * // 结果: "value1,value2"
 *
 * // 为null值指定替代文本
 * String result2 = new Joiner(",")
 *     .nullText("N/A")
 *     .add("value1")
 *     .add(null)
 *     .add("value2")
 *     .toString();
 * // 结果: "value1,N/A,value2"
 * }</pre>
 *
 * <h3>添加多个值</h3>
 * <pre>{@code
 * // 添加数组
 * String result = new Joiner("|")
 *     .addAll(new String[]{"a", "b", "c"})
 *     .toString();
 * // 结果: "a|b|c"
 *
 * // 添加集合
 * List<Integer> numbers = Arrays.asList(1, 2, 3, 4);
 * String result2 = new Joiner("-")
 *     .addAll(numbers)
 *     .toString();
 * // 结果: "1-2-3-4"
 *
 * // 添加原始类型数组
 * String result3 = new Joiner(" ")
 *     .addAll(new int[]{10, 20, 30})
 *     .toString();
 * // 结果: "10 20 30"
 * }</pre>
 *
 * <h3>支持的数据类型</h3>
 * <ul>
 *   <li>基本数据类型：boolean, char, byte, short, int, long, float, double</li>
 *   <li>包装类型：Boolean, Character, Byte, Short, Integer, Long, Float, Double</li>
 *   <li>字符序列：String, CharSequence及其子类</li>
 *   <li>任意对象：通过toString()方法转换</li>
 *   <li>集合类型：Collection, Iterable, Iterator</li>
 *   <li>数组类型：包括基本类型数组和对象数组</li>
 *   <li>原始类型列表：BooleanList, CharList, ByteList等</li>
 * </ul>
 *
 * <h3>链式调用</h3>
 * <pre>{@code
 * // 所有add和配置方法都返回Joiner实例，支持链式调用
 * String result = new Joiner(", ")
 *     .ignoreNull(true)
 *     .add("first")
 *     .add(123)
 *     .add(null)
 *     .addAll(Arrays.asList("second", "third"))
 *     .add(true)
 *     .toString();
 * // 结果: "first, 123, second, third, true"
 * }</pre>
 *
 * <h3>输出到Appendable</h3>
 * <pre>{@code
 * // 直接输出到StringBuilder或其他Appendable
 * StringBuilder sb = new StringBuilder();
 * new Joiner(" | ")
 *     .add("A")
 *     .add("B")
 *     .add("C")
 *     .toString(sb);
 * // sb内容: "A | B | C"
 * }</pre>
 *
 * <h3>注意事项</h3>
 * <ul>
 *   <li>如果没有添加任何值，{@code toString()}方法将返回{@code null}</li>
 *   <li>分隔符、前缀、后缀为{@code null}时会被转换为空字符串</li>
 *   <li>对象值通过相应的工具类（如IntUtils.toString）或ObjectUtils.toString转换为字符串</li>
 *   <li>支持添加指定范围内的数组元素</li>
 * </ul>
 *
 * @author 胡海星
 * @see ltd.qubit.commons.lang.ObjectUtils#toString(Object)
 * @see ltd.qubit.commons.lang.StringUtils
 */
public class Joiner {

  /**
   * 分隔符。
   */
  private final CharSequence separator;

  /**
   * 前缀。
   */
  private final CharSequence prefix;

  /**
   * 后缀。
   */
  private final CharSequence suffix;

  /**
   * 是否忽略null值。
   */
  private boolean ignoreNull = false;

  /**
   * null值的文本表示。
   */
  private CharSequence nullText = EMPTY;

  /**
   * 待连接的值列表。
   */
  private List<CharSequence> values;

  /**
   * 使用指定分隔符构造一个Joiner实例。
   *
   * @param separator
   *     分隔符字符。
   */
  public Joiner(final char separator) {
    this.separator = CharUtils.toString(separator);
    this.prefix = EMPTY;
    this.suffix = EMPTY;
  }

  /**
   * 使用指定分隔符、前缀和后缀构造一个Joiner实例。
   *
   * @param separator
   *     分隔符字符。
   * @param prefix
   *     前缀，可为null。
   * @param suffix
   *     后缀，可为null。
   */
  public Joiner(final char separator, @Nullable final CharSequence prefix,
      @Nullable final CharSequence suffix) {
    this.separator = CharUtils.toString(separator);
    this.prefix = nullToEmpty(prefix);
    this.suffix = nullToEmpty(suffix);
  }

  /**
   * 使用指定分隔符构造一个Joiner实例。
   *
   * @param separator
   *     分隔符，可为null。
   */
  public Joiner(@Nullable final CharSequence separator) {
    this.separator = nullToEmpty(separator);
    this.prefix = EMPTY;
    this.suffix = EMPTY;
  }

  /**
   * 使用指定分隔符、前缀和后缀构造一个Joiner实例。
   *
   * @param separator
   *     分隔符，可为null。
   * @param prefix
   *     前缀，可为null。
   * @param suffix
   *     后缀，可为null。
   */
  public Joiner(@Nullable final CharSequence separator,
      @Nullable final CharSequence prefix,
      @Nullable final CharSequence suffix) {
    this.separator = nullToEmpty(separator);
    this.prefix = nullToEmpty(prefix);
    this.suffix = nullToEmpty(suffix);
  }

  /**
   * 设置是否忽略null值。
   *
   * @param ignoreNull
   *     是否忽略null值。
   * @return
   *     此Joiner实例，用于链式调用。
   */
  public Joiner ignoreNull(final boolean ignoreNull) {
    this.ignoreNull = ignoreNull;
    return this;
  }

  /**
   * 设置null值的文本表示。
   *
   * @param nullText
   *     null值的文本表示，不能为null。
   * @return
   *     此Joiner实例，用于链式调用。
   */
  public Joiner nullText(final CharSequence nullText) {
    this.nullText = requireNonNull("nullText", nullText);
    return this;
  }

  private void ensureListExist() {
    if (values == null) {
      values = new ArrayList<>();
    }
  }

  private void addToList(@Nullable final CharSequence str) {
    if (str == null) {
      if (!ignoreNull) {
        ensureListExist();
        values.add(nullText);
      }
    } else {
      ensureListExist();
      values.add(str);
    }
  }

  /**
   * 添加一个字符序列值。
   *
   * @param value
   *     要添加的字符序列值，可为null。
   * @return
   *     此Joiner实例，用于链式调用。
   */
  public Joiner add(@Nullable final CharSequence value) {
    addToList(value);
    return this;
  }

  /**
   * 添加一个对象值。
   *
   * @param <T>
   *     对象的类型。
   * @param value
   *     要添加的对象值，可为null。
   * @return
   *     此Joiner实例，用于链式调用。
   */
  public <T> Joiner add(@Nullable final T value) {
    addToList(ObjectUtils.toString(value));
    return this;
  }

  /**
   * 添加一个boolean值。
   *
   * @param value
   *     要添加的boolean值。
   * @return
   *     此Joiner实例，用于链式调用。
   */
  public Joiner add(final boolean value) {
    addToList(BooleanUtils.toString(value));
    return this;
  }

  /**
   * 添加一个Boolean值。
   *
   * @param value
   *     要添加的Boolean值，可为null。
   * @return
   *     此Joiner实例，用于链式调用。
   */
  public Joiner add(@Nullable final Boolean value) {
    addToList(BooleanUtils.toString(value));
    return this;
  }

  /**
   * 添加一个char值。
   *
   * @param value
   *     要添加的char值。
   * @return
   *     此Joiner实例，用于链式调用。
   */
  public Joiner add(final char value) {
    addToList(CharUtils.toString(value));
    return this;
  }

  /**
   * 添加一个Character值。
   *
   * @param value
   *     要添加的Character值，可为null。
   * @return
   *     此Joiner实例，用于链式调用。
   */
  public Joiner add(@Nullable final Character value) {
    addToList(CharUtils.toString(value));
    return this;
  }

  /**
   * 添加一个byte值。
   *
   * @param value
   *     要添加的byte值。
   * @return
   *     此Joiner实例，用于链式调用。
   */
  public Joiner add(final byte value) {
    addToList(ByteUtils.toString(value));
    return this;
  }

  /**
   * 添加一个Byte值。
   *
   * @param value
   *     要添加的Byte值，可为null。
   * @return
   *     此Joiner实例，用于链式调用。
   */
  public Joiner add(@Nullable final Byte value) {
    addToList(ByteUtils.toString(value));
    return this;
  }

  /**
   * 添加一个short值。
   *
   * @param value
   *     要添加的short值。
   * @return
   *     此Joiner实例，用于链式调用。
   */
  public Joiner add(final short value) {
    addToList(ShortUtils.toString(value));
    return this;
  }

  /**
   * 添加一个Short值。
   *
   * @param value
   *     要添加的Short值，可为null。
   * @return
   *     此Joiner实例，用于链式调用。
   */
  public Joiner add(@Nullable final Short value) {
    addToList(ShortUtils.toString(value));
    return this;
  }

  /**
   * 添加一个int值。
   *
   * @param value
   *     要添加的int值。
   * @return
   *     此Joiner实例，用于链式调用。
   */
  public Joiner add(final int value) {
    addToList(IntUtils.toString(value));
    return this;
  }

  /**
   * 添加一个Integer值。
   *
   * @param value
   *     要添加的Integer值，可为null。
   * @return
   *     此Joiner实例，用于链式调用。
   */
  public Joiner add(@Nullable final Integer value) {
    addToList(IntUtils.toString(value));
    return this;
  }

  /**
   * 添加一个long值。
   *
   * @param value
   *     要添加的long值。
   * @return
   *     此Joiner实例，用于链式调用。
   */
  public Joiner add(final long value) {
    addToList(LongUtils.toString(value));
    return this;
  }

  /**
   * 添加一个Long值。
   *
   * @param value
   *     要添加的Long值，可为null。
   * @return
   *     此Joiner实例，用于链式调用。
   */
  public Joiner add(@Nullable final Long value) {
    addToList(LongUtils.toString(value));
    return this;
  }

  /**
   * 添加一个float值。
   *
   * @param value
   *     要添加的float值。
   * @return
   *     此Joiner实例，用于链式调用。
   */
  public Joiner add(final float value) {
    addToList(FloatUtils.toString(value));
    return this;
  }

  /**
   * 添加一个Float值。
   *
   * @param value
   *     要添加的Float值，可为null。
   * @return
   *     此Joiner实例，用于链式调用。
   */
  public Joiner add(@Nullable final Float value) {
    addToList(FloatUtils.toString(value));
    return this;
  }

  /**
   * 添加一个double值。
   *
   * @param value
   *     要添加的double值。
   * @return
   *     此Joiner实例，用于链式调用。
   */
  public Joiner add(final double value) {
    addToList(DoubleUtils.toString(value));
    return this;
  }

  /**
   * 添加一个Double值。
   *
   * @param value
   *     要添加的Double值，可为null。
   * @return
   *     此Joiner实例，用于链式调用。
   */
  public Joiner add(@Nullable final Double value) {
    addToList(DoubleUtils.toString(value));
    return this;
  }

  /**
   * 添加多个字符序列值。
   *
   * @param values
   *     要添加的字符序列值数组，可为null。
   * @return
   *     此Joiner实例，用于链式调用。
   */
  public Joiner addAll(final CharSequence ... values) {
    if (values == null) {
      return this;
    }
    ensureListExist();
    for (final CharSequence value : values) {
      addToList(value);
    }
    return this;
  }

  /**
   * 添加多个对象值。
   *
   * @param <T>
   *     对象的类型。
   * @param values
   *     要添加的对象值数组，可为null。
   * @return
   *     此Joiner实例，用于链式调用。
   */
  @SafeVarargs
  public final <T> Joiner addAll(final T... values) {
    if (values == null) {
      return this;
    }
    ensureListExist();
    for (final T value : values) {
      addToList(ObjectUtils.toString(value));
    }
    return this;
  }

  /**
   * 添加指定范围内的对象值。
   *
   * @param <T>
   *     对象的类型。
   * @param values
   *     要添加的对象值数组，可为null。
   * @param startIndex
   *     开始索引（包含）。
   * @param endIndex
   *     结束索引（不包含）。
   * @return
   *     此Joiner实例，用于链式调用。
   */
  public <T> Joiner addAll(final T[] values, final int startIndex,
      final int endIndex) {
    if (values == null) {
      return this;
    }
    ensureListExist();
    final int start = Math.max(0, startIndex);
    final int end = Math.min(values.length, endIndex);
    for (int i = start; i < end; ++i) {
      final T value = values[i];
      addToList(ObjectUtils.toString(value));
    }
    return this;
  }

  /**
   * 添加多个boolean值。
   *
   * @param <T>
   *     类型参数（未使用）。
   * @param values
   *     要添加的boolean值数组，可为null。
   * @return
   *     此Joiner实例，用于链式调用。
   */
  public <T> Joiner addAll(final boolean ... values) {
    if (values == null) {
      return this;
    }
    ensureListExist();
    for (final boolean value : values) {
      addToList(BooleanUtils.toString(value));
    }
    return this;
  }

  /**
   * 添加指定范围内的boolean值。
   *
   * @param <T>
   *     类型参数（未使用）。
   * @param values
   *     要添加的boolean值数组，可为null。
   * @param startIndex
   *     开始索引（包含）。
   * @param endIndex
   *     结束索引（不包含）。
   * @return
   *     此Joiner实例，用于链式调用。
   */
  public <T> Joiner addAll(final boolean[] values, final int startIndex,
      final int endIndex) {
    if (values == null) {
      return this;
    }
    ensureListExist();
    final int start = Math.max(0, startIndex);
    final int end = Math.min(values.length, endIndex);
    for (int i = start; i < end; ++i) {
      final boolean value = values[i];
      addToList(BooleanUtils.toString(value));
    }
    return this;
  }

  /**
   * 添加多个char值。
   *
   * @param <T>
   *     类型参数（未使用）。
   * @param values
   *     要添加的char值数组，可为null。
   * @return
   *     此Joiner实例，用于链式调用。
   */
  public <T> Joiner addAll(final char ... values) {
    if (values == null) {
      return this;
    }
    ensureListExist();
    for (final char value : values) {
      addToList(CharUtils.toString(value));
    }
    return this;
  }

  /**
   * 添加指定范围内的char值。
   *
   * @param <T>
   *     类型参数（未使用）。
   * @param values
   *     要添加的char值数组，可为null。
   * @param startIndex
   *     开始索引（包含）。
   * @param endIndex
   *     结束索引（不包含）。
   * @return
   *     此Joiner实例，用于链式调用。
   */
  public <T> Joiner addAll(final char[] values, final int startIndex,
      final int endIndex) {
    if (values == null) {
      return this;
    }
    ensureListExist();
    final int start = Math.max(0, startIndex);
    final int end = Math.min(values.length, endIndex);
    for (int i = start; i < end; ++i) {
      final char value = values[i];
      addToList(CharUtils.toString(value));
    }
    return this;
  }

  /**
   * 添加多个byte值。
   *
   * @param <T>
   *     类型参数（未使用）。
   * @param values
   *     要添加的byte值数组，可为null。
   * @return
   *     此Joiner实例，用于链式调用。
   */
  public <T> Joiner addAll(final byte ... values) {
    if (values == null) {
      return this;
    }
    ensureListExist();
    for (final byte value : values) {
      addToList(ByteUtils.toString(value));
    }
    return this;
  }

  /**
   * 添加指定范围内的byte值。
   *
   * @param <T>
   *     类型参数（未使用）。
   * @param values
   *     要添加的byte值数组，可为null。
   * @param startIndex
   *     开始索引（包含）。
   * @param endIndex
   *     结束索引（不包含）。
   * @return
   *     此Joiner实例，用于链式调用。
   */
  public <T> Joiner addAll(final byte[] values, final int startIndex,
      final int endIndex) {
    if (values == null) {
      return this;
    }
    ensureListExist();
    final int start = Math.max(0, startIndex);
    final int end = Math.min(values.length, endIndex);
    for (int i = start; i < end; ++i) {
      final byte value = values[i];
      addToList(ByteUtils.toString(value));
    }
    return this;
  }

  /**
   * 添加多个short值。
   *
   * @param <T>
   *     类型参数（未使用）。
   * @param values
   *     要添加的short值数组，可为null。
   * @return
   *     此Joiner实例，用于链式调用。
   */
  public <T> Joiner addAll(final short ... values) {
    if (values == null) {
      return this;
    }
    ensureListExist();
    for (final short value : values) {
      addToList(ShortUtils.toString(value));
    }
    return this;
  }

  /**
   * 添加指定范围内的short值。
   *
   * @param <T>
   *     类型参数（未使用）。
   * @param values
   *     要添加的short值数组，可为null。
   * @param startIndex
   *     开始索引（包含）。
   * @param endIndex
   *     结束索引（不包含）。
   * @return
   *     此Joiner实例，用于链式调用。
   */
  public <T> Joiner addAll(final short[] values, final int startIndex,
      final int endIndex) {
    if (values == null) {
      return this;
    }
    ensureListExist();
    final int start = Math.max(0, startIndex);
    final int end = Math.min(values.length, endIndex);
    for (int i = start; i < end; ++i) {
      final short value = values[i];
      addToList(ShortUtils.toString(value));
    }
    return this;
  }

  /**
   * 添加多个int值。
   *
   * @param <T>
   *     类型参数（未使用）。
   * @param values
   *     要添加的int值数组，可为null。
   * @return
   *     此Joiner实例，用于链式调用。
   */
  public <T> Joiner addAll(final int ... values) {
    if (values == null) {
      return this;
    }
    ensureListExist();
    for (final int value : values) {
      addToList(IntUtils.toString(value));
    }
    return this;
  }

  /**
   * 添加指定范围内的int值。
   *
   * @param <T>
   *     类型参数（未使用）。
   * @param values
   *     要添加的int值数组，可为null。
   * @param startIndex
   *     开始索引（包含）。
   * @param endIndex
   *     结束索引（不包含）。
   * @return
   *     此Joiner实例，用于链式调用。
   */
  public <T> Joiner addAll(final int[] values, final int startIndex,
      final int endIndex) {
    if (values == null) {
      return this;
    }
    ensureListExist();
    final int start = Math.max(0, startIndex);
    final int end = Math.min(values.length, endIndex);
    for (int i = start; i < end; ++i) {
      final int value = values[i];
      addToList(IntUtils.toString(value));
    }
    return this;
  }

  /**
   * 添加多个long值。
   *
   * @param <T>
   *     类型参数（未使用）。
   * @param values
   *     要添加的long值数组，可为null。
   * @return
   *     此Joiner实例，用于链式调用。
   */
  public <T> Joiner addAll(final long ... values) {
    if (values == null) {
      return this;
    }
    ensureListExist();
    for (final long value : values) {
      addToList(LongUtils.toString(value));
    }
    return this;
  }

  /**
   * 添加指定范围内的long值。
   *
   * @param <T>
   *     类型参数（未使用）。
   * @param values
   *     要添加的long值数组，可为null。
   * @param startIndex
   *     开始索引（包含）。
   * @param endIndex
   *     结束索引（不包含）。
   * @return
   *     此Joiner实例，用于链式调用。
   */
  public <T> Joiner addAll(final long[] values, final int startIndex,
      final int endIndex) {
    if (values == null) {
      return this;
    }
    ensureListExist();
    final int start = Math.max(0, startIndex);
    final int end = Math.min(values.length, endIndex);
    for (int i = start; i < end; ++i) {
      final long value = values[i];
      addToList(LongUtils.toString(value));
    }
    return this;
  }

  /**
   * 添加多个float值。
   *
   * @param <T>
   *     类型参数（未使用）。
   * @param values
   *     要添加的float值数组，可为null。
   * @return
   *     此Joiner实例，用于链式调用。
   */
  public <T> Joiner addAll(final float ... values) {
    if (values == null) {
      return this;
    }
    ensureListExist();
    for (final float value : values) {
      addToList(FloatUtils.toString(value));
    }
    return this;
  }

  /**
   * 添加指定范围内的float值。
   *
   * @param <T>
   *     类型参数（未使用）。
   * @param values
   *     要添加的float值数组，可为null。
   * @param startIndex
   *     开始索引（包含）。
   * @param endIndex
   *     结束索引（不包含）。
   * @return
   *     此Joiner实例，用于链式调用。
   */
  public <T> Joiner addAll(final float[] values, final int startIndex,
      final int endIndex) {
    if (values == null) {
      return this;
    }
    ensureListExist();
    final int start = Math.max(0, startIndex);
    final int end = Math.min(values.length, endIndex);
    for (int i = start; i < end; ++i) {
      final float value = values[i];
      addToList(FloatUtils.toString(value));
    }
    return this;
  }

  /**
   * 添加多个double值。
   *
   * @param <T>
   *     类型参数（未使用）。
   * @param values
   *     要添加的double值数组，可为null。
   * @return
   *     此Joiner实例，用于链式调用。
   */
  public <T> Joiner addAll(final double ... values) {
    if (values == null) {
      return this;
    }
    ensureListExist();
    for (final double value : values) {
      addToList(DoubleUtils.toString(value));
    }
    return this;
  }

  /**
   * 添加指定范围内的double值。
   *
   * @param <T>
   *     类型参数（未使用）。
   * @param values
   *     要添加的double值数组，可为null。
   * @param startIndex
   *     开始索引（包含）。
   * @param endIndex
   *     结束索引（不包含）。
   * @return
   *     此Joiner实例，用于链式调用。
   */
  public <T> Joiner addAll(final double[] values, final int startIndex,
      final int endIndex) {
    if (values == null) {
      return this;
    }
    ensureListExist();
    final int start = Math.max(0, startIndex);
    final int end = Math.min(values.length, endIndex);
    for (int i = start; i < end; ++i) {
      final double value = values[i];
      addToList(DoubleUtils.toString(value));
    }
    return this;
  }

  /**
   * 添加集合中的所有对象值。
   *
   * @param <T>
   *     对象的类型。
   * @param values
   *     要添加的对象值集合，可为null。
   * @return
   *     此Joiner实例，用于链式调用。
   */
  public <T> Joiner addAll(@Nullable final Collection<T> values) {
    if (values == null) {
      return this;
    }
    ensureListExist();
    for (final T value : values) {
      addToList(ObjectUtils.toString(value));
    }
    return this;
  }

  /**
   * 添加可迭代对象中的所有值。
   *
   * @param <T>
   *     对象的类型。
   * @param values
   *     要添加的可迭代对象，可为null。
   * @return
   *     此Joiner实例，用于链式调用。
   */
  public <T> Joiner addAll(@Nullable final Iterable<T> values) {
    if (values == null) {
      return this;
    }
    ensureListExist();
    for (final T value : values) {
      addToList(ObjectUtils.toString(value));
    }
    return this;
  }

  /**
   * 添加迭代器中的所有值。
   *
   * @param <T>
   *     对象的类型。
   * @param iterator
   *     要添加值的迭代器，可为null。
   * @return
   *     此Joiner实例，用于链式调用。
   */
  public <T> Joiner addAll(@Nullable final Iterator<T> iterator) {
    if (iterator == null) {
      return this;
    }
    ensureListExist();
    while (iterator.hasNext()) {
      final T value = iterator.next();
      addToList(ObjectUtils.toString(value));
    }
    return this;
  }

  /**
   * 添加BooleanList中的所有值。
   *
   * @param <T>
   *     类型参数（未使用）。
   * @param values
   *     要添加的BooleanList，可为null。
   * @return
   *     此Joiner实例，用于链式调用。
   */
  public <T> Joiner addAll(final BooleanList values) {
    if (values == null) {
      return this;
    }
    ensureListExist();
    final BooleanListIterator iter = values.listIterator();
    while (iter.hasNext()) {
      addToList(BooleanUtils.toString(iter.next()));
    }
    return this;
  }

  /**
   * 添加CharList中的所有值。
   *
   * @param <T>
   *     类型参数（未使用）。
   * @param values
   *     要添加的CharList，可为null。
   * @return
   *     此Joiner实例，用于链式调用。
   */
  public <T> Joiner addAll(final CharList values) {
    if (values == null) {
      return this;
    }
    ensureListExist();
    final CharListIterator iter = values.listIterator();
    while (iter.hasNext()) {
      addToList(CharUtils.toString(iter.next()));
    }
    return this;
  }

  /**
   * 添加ByteList中的所有值。
   *
   * @param <T>
   *     类型参数（未使用）。
   * @param values
   *     要添加的ByteList，可为null。
   * @return
   *     此Joiner实例，用于链式调用。
   */
  public <T> Joiner addAll(final ByteList values) {
    if (values == null) {
      return this;
    }
    ensureListExist();
    final ByteListIterator iter = values.listIterator();
    while (iter.hasNext()) {
      addToList(ByteUtils.toString(iter.next()));
    }
    return this;
  }

  /**
   * 添加ShortList中的所有值。
   *
   * @param <T>
   *     类型参数（未使用）。
   * @param values
   *     要添加的ShortList，可为null。
   * @return
   *     此Joiner实例，用于链式调用。
   */
  public <T> Joiner addAll(final ShortList values) {
    if (values == null) {
      return this;
    }
    ensureListExist();
    final ShortListIterator iter = values.listIterator();
    while (iter.hasNext()) {
      addToList(ShortUtils.toString(iter.next()));
    }
    return this;
  }

  /**
   * 添加IntList中的所有值。
   *
   * @param <T>
   *     类型参数（未使用）。
   * @param values
   *     要添加的IntList，可为null。
   * @return
   *     此Joiner实例，用于链式调用。
   */
  public <T> Joiner addAll(final IntList values) {
    if (values == null) {
      return this;
    }
    ensureListExist();
    final IntListIterator iter = values.listIterator();
    while (iter.hasNext()) {
      addToList(IntUtils.toString(iter.next()));
    }
    return this;
  }

  /**
   * 添加LongList中的所有值。
   *
   * @param <T>
   *     类型参数（未使用）。
   * @param values
   *     要添加的LongList，可为null。
   * @return
   *     此Joiner实例，用于链式调用。
   */
  public <T> Joiner addAll(final LongList values) {
    if (values == null) {
      return this;
    }
    ensureListExist();
    final LongListIterator iter = values.listIterator();
    while (iter.hasNext()) {
      addToList(LongUtils.toString(iter.next()));
    }
    return this;
  }

  /**
   * 添加FloatList中的所有值。
   *
   * @param <T>
   *     类型参数（未使用）。
   * @param values
   *     要添加的FloatList，可为null。
   * @return
   *     此Joiner实例，用于链式调用。
   */
  public <T> Joiner addAll(final FloatList values) {
    if (values == null) {
      return this;
    }
    ensureListExist();
    final FloatListIterator iter = values.listIterator();
    while (iter.hasNext()) {
      addToList(FloatUtils.toString(iter.next()));
    }
    return this;
  }

  /**
   * 添加DoubleList中的所有值。
   *
   * @param <T>
   *     类型参数（未使用）。
   * @param values
   *     要添加的DoubleList，可为null。
   * @return
   *     此Joiner实例，用于链式调用。
   */
  public <T> Joiner addAll(final DoubleList values) {
    if (values == null) {
      return this;
    }
    ensureListExist();
    final DoubleListIterator iter = values.listIterator();
    while (iter.hasNext()) {
      addToList(DoubleUtils.toString(iter.next()));
    }
    return this;
  }

  /**
   * 将所有添加的值连接为字符串。
   *
   * @return
   *     连接后的字符串，如果没有添加任何值则返回null。
   */
  @Nullable
  public String toString() {
    if (values == null) {
      return null;
    }
    final StringBuilder builder = new StringBuilder();
    toString(builder);
    return builder.toString();
  }

  /**
   * 将所有添加的值连接到指定的StringBuilder中。
   *
   * @param builder
   *     目标StringBuilder。
   */
  public void toString(final StringBuilder builder) {
    if (values == null) {
      return;
    }
    try {
      toString((Appendable) builder);
    } catch (final IOException e) {
      throw new UncheckedIOException(e);
    }
  }

  /**
   * 将所有添加的值连接到指定的Appendable中。
   *
   * @param output
   *     目标Appendable。
   * @throws IOException
   *     如果写入时发生I/O错误。
   */
  public void toString(final Appendable output) throws IOException {
    if (values == null) {
      return;
    }
    output.append(prefix);
    if (values.size() > 0) {
      final ListIterator<CharSequence> iter = values.listIterator();
      assert (iter.hasNext());
      final CharSequence firstValue = iter.next();
      output.append(firstValue);
      while (iter.hasNext()) {
        output.append(separator);
        output.append(iter.next());
      }
    }
    output.append(suffix);
  }
}