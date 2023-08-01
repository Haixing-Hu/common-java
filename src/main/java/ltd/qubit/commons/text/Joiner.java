////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text;

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

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import javax.annotation.Nullable;

import static ltd.qubit.commons.lang.Argument.requireNonNull;
import static ltd.qubit.commons.lang.StringUtils.EMPTY;
import static ltd.qubit.commons.lang.StringUtils.nullToEmpty;

/**
 * The class used to join values to a string.
 *
 * @author Haixing Hu
 */
public class Joiner {

  private final CharSequence separator;
  private final CharSequence prefix;
  private final CharSequence suffix;
  private boolean ignoreNull = false;
  private CharSequence nullText = EMPTY;
  private List<CharSequence> values;

  public Joiner(final char separator) {
    this.separator = CharUtils.toString(separator);
    this.prefix = EMPTY;
    this.suffix = EMPTY;
  }

  public Joiner(final char separator, @Nullable final CharSequence prefix,
      @Nullable final CharSequence suffix) {
    this.separator = CharUtils.toString(separator);
    this.prefix = nullToEmpty(prefix);
    this.suffix = nullToEmpty(suffix);
  }

  public Joiner(@Nullable final CharSequence separator) {
    this.separator = nullToEmpty(separator);
    this.prefix = EMPTY;
    this.suffix = EMPTY;
  }

  public Joiner(@Nullable final CharSequence separator,
      @Nullable final CharSequence prefix,
      @Nullable final CharSequence suffix) {
    this.separator = nullToEmpty(separator);
    this.prefix = nullToEmpty(prefix);
    this.suffix = nullToEmpty(suffix);
  }

  public Joiner ignoreNull(final boolean ignoreNull) {
    this.ignoreNull = ignoreNull;
    return this;
  }

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

  public Joiner add(@Nullable final CharSequence value) {
    addToList(value);
    return this;
  }

  public <T> Joiner add(@Nullable final T value) {
    addToList(ObjectUtils.toString(value));
    return this;
  }

  public Joiner add(final boolean value) {
    addToList(BooleanUtils.toString(value));
    return this;
  }

  public Joiner add(@Nullable final Boolean value) {
    addToList(BooleanUtils.toString(value));
    return this;
  }

  public Joiner add(final char value) {
    addToList(CharUtils.toString(value));
    return this;
  }

  public Joiner add(@Nullable final Character value) {
    addToList(CharUtils.toString(value));
    return this;
  }

  public Joiner add(final byte value) {
    addToList(ByteUtils.toString(value));
    return this;
  }

  public Joiner add(@Nullable final Byte value) {
    addToList(ByteUtils.toString(value));
    return this;
  }

  public Joiner add(final short value) {
    addToList(ShortUtils.toString(value));
    return this;
  }

  public Joiner add(@Nullable final Short value) {
    addToList(ShortUtils.toString(value));
    return this;
  }

  public Joiner add(final int value) {
    addToList(IntUtils.toString(value));
    return this;
  }

  public Joiner add(@Nullable final Integer value) {
    addToList(IntUtils.toString(value));
    return this;
  }

  public Joiner add(final long value) {
    addToList(LongUtils.toString(value));
    return this;
  }

  public Joiner add(@Nullable final Long value) {
    addToList(LongUtils.toString(value));
    return this;
  }

  public Joiner add(final float value) {
    addToList(FloatUtils.toString(value));
    return this;
  }

  public Joiner add(@Nullable final Float value) {
    addToList(FloatUtils.toString(value));
    return this;
  }

  public Joiner add(final double value) {
    addToList(DoubleUtils.toString(value));
    return this;
  }

  public Joiner add(@Nullable final Double value) {
    addToList(DoubleUtils.toString(value));
    return this;
  }

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

  @Nullable
  public String toString() {
    if (values == null) {
      return null;
    }
    final StringBuilder builder = new StringBuilder();
    toString(builder);
    return builder.toString();
  }

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
