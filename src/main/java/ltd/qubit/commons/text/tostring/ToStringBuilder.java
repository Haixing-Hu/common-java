////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.tostring;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.annotation.Nullable;
import javax.annotation.concurrent.NotThreadSafe;

import jakarta.xml.bind.DatatypeConverter;

import ltd.qubit.commons.lang.Argument;
import ltd.qubit.commons.lang.DateUtils;

/**
 * This class assists in implementing {@link Object#toString()} methods.
 *
 * <p>This class enables a good and consistent {@code toString()} to be built
 * for any class or object. This class aims to simplify the process by:
 * <ul>
 * <li>allowing field names</li>
 * <li>handling all types consistently</li>
 * <li>handling nulls consistently</li>
 * <li>outputting arrays and multi-dimensional arrays</li>
 * <li>enabling the detail level to be controlled for Objects and Collections</li>
 * <li>handling class hierarchies</li>
 * </ul>
 *
 * <p>To use this class write code as follows:
 * <pre><code>
 * public class Person {
 *   String name;
 *   int age;
 *   boolean smoker;
 *
 *   ...
 *
 *   public String toString() {
 *     return new ToStringBuilder(this)
 *                .append("name", name);
 *                .append("age", age);
 *                .append("smoker", smoker)
 *                .toString();
 *   }
 * }
 * </code></pre>
 *
 * <p>This will produce a toString of the format:
 * <pre><code>
 *   Person@7f54[name=Stephen,age=29,smoker=false]
 * </code></pre>
 *
 * <p>To add the superclass {@code toString}, use {@link #appendSuper}. To
 * append the {@code toString} from an object that is delegated to (or any
 * other object), use {@link #appendToString}. The exact format of the
 * {@code toString} is determined by the {@link ToStringStyle} passed into
 * the constructor. This class has a static StringBuilder object for each
 * thread, and the program could safely call the static function of this class
 * to implement the toString() method.
 *
 * @author Haixing Hu
 */
@NotThreadSafe
public class ToStringBuilder {

  private static final int INITIAL_BUFFER_SIZE = 128;

  private final StringBuilder builder;
  private ToStringStyle style;
  private Object object;

  public ToStringBuilder() {
    builder = new StringBuilder(INITIAL_BUFFER_SIZE);
    style = ToStringStyle.getDefault();
    object = null;
  }

  public ToStringBuilder(final ToStringStyle style) {
    builder = new StringBuilder(INITIAL_BUFFER_SIZE);
    this.style = Argument.requireNonNull("style", style);
    object = null;
  }

  public ToStringBuilder(@Nullable final Object object) {
    builder = new StringBuilder(INITIAL_BUFFER_SIZE);
    style = ToStringStyle.getDefault();
    this.object = object;
    style.appendStart(builder, this.object);
  }

  public ToStringBuilder(final ToStringStyle style,
      @Nullable final Object object) {
    builder = new StringBuilder(INITIAL_BUFFER_SIZE);
    this.style = Argument.requireNonNull("style", style);
    this.object = object;
    this.style.appendStart(builder, this.object);
  }

  public ToStringStyle getStyle() {
    return style;
  }

  public void setStyle(final ToStringStyle style) {
    this.style = Argument.requireNonNull("style", style);
  }

  public int length() {
    return builder.length();
  }

  public void clear() {
    object = null;
    builder.setLength(0);
  }

  public ToStringBuilder reset(@Nullable final Object object) {
    this.object = object;
    builder.setLength(0);
    style.appendStart(builder, this.object);
    return this;
  }

  public ToStringBuilder append(final boolean value) {
    style.append(builder, null, value);
    return this;
  }

  public ToStringBuilder append(@Nullable final boolean[] value) {
    style.append(builder, null, value, null);
    return this;
  }

  public ToStringBuilder append(final char value) {
    style.append(builder, null, value);
    return this;
  }

  public ToStringBuilder append(@Nullable final char[] value) {
    style.append(builder, null, value, null);
    return this;
  }

  public ToStringBuilder append(final byte value) {
    style.append(builder, null, value);
    return this;
  }

  public ToStringBuilder append(@Nullable final byte[] value) {
    style.append(builder, null, value, null);
    return this;
  }

  public ToStringBuilder append(final short value) {
    style.append(builder, null, value);
    return this;
  }

  public ToStringBuilder append(@Nullable final short[] value) {
    style.append(builder, null, value, null);
    return this;
  }

  public ToStringBuilder append(final int value) {
    style.append(builder, null, value);
    return this;
  }

  public ToStringBuilder append(@Nullable final int[] value) {
    style.append(builder, null, value, null);
    return this;
  }

  public ToStringBuilder append(final long value) {
    style.append(builder, null, value);
    return this;
  }

  public ToStringBuilder append(@Nullable final long[] value) {
    style.append(builder, null, value, null);
    return this;
  }

  public ToStringBuilder append(final float value) {
    style.append(builder, null, value);
    return this;
  }

  public ToStringBuilder append(@Nullable final float[] value) {
    style.append(builder, null, value, null);
    return this;
  }

  public ToStringBuilder append(final double value) {
    style.append(builder, null, value);
    return this;
  }

  public ToStringBuilder append(@Nullable final double[] value) {
    style.append(builder, null, value, null);
    return this;
  }

  public ToStringBuilder append(@Nullable final Object value) {
    style.append(builder, null, value, null);
    return this;
  }

  public ToStringBuilder append(@Nullable final Object[] value) {
    style.append(builder, null, value, null);
    return this;
  }

  public ToStringBuilder append(final String name, final boolean value) {
    style.append(builder, name, value);
    return this;
  }

  public ToStringBuilder append(final String name,
      @Nullable final boolean[] value) {
    style.append(builder, name, value, null);
    return this;
  }

  public ToStringBuilder append(final String name,
      @Nullable final boolean[] value, final boolean fullDetail) {
    style.append(builder, name, value, (fullDetail ? Boolean.TRUE
                                                   : Boolean.FALSE));
    return this;
  }

  public ToStringBuilder append(final String name, final char value) {
    style.append(builder, name, value);
    return this;
  }

  public ToStringBuilder append(final String name,
      @Nullable final char[] value) {
    style.append(builder, name, value, null);
    return this;
  }

  public ToStringBuilder append(final String name,
      @Nullable final char[] value, final boolean fullDetail) {
    style.append(builder, name, value, (fullDetail ? Boolean.TRUE
                                                   : Boolean.FALSE));
    return this;
  }

  public ToStringBuilder append(final String name, final byte value) {
    style.append(builder, name, value);
    return this;
  }

  public ToStringBuilder append(final String name,
      @Nullable final byte[] value) {
    style.append(builder, name, value, null);
    return this;
  }

  public ToStringBuilder append(final String name,
      @Nullable final byte[] value, final boolean fullDetail) {
    style.append(builder, name, value, (fullDetail ? Boolean.TRUE
                                                   : Boolean.FALSE));
    return this;
  }

  public ToStringBuilder append(final String name, final short value) {
    style.append(builder, name, value);
    return this;
  }

  public ToStringBuilder append(final String name,
      @Nullable final short[] value) {
    style.append(builder, name, value, null);
    return this;
  }

  public ToStringBuilder append(final String name,
      @Nullable final short[] value, final boolean fullDetail) {
    style.append(builder, name, value, (fullDetail ? Boolean.TRUE
                                                   : Boolean.FALSE));
    return this;
  }

  public ToStringBuilder append(final String name, final int value) {
    style.append(builder, name, value);
    return this;
  }

  public ToStringBuilder append(final String name,
      @Nullable final int[] value) {
    style.append(builder, name, value, null);
    return this;
  }

  public ToStringBuilder append(final String name, @Nullable final int[] value,
      final boolean fullDetail) {
    style.append(builder, name, value, (fullDetail ? Boolean.TRUE
                                                   : Boolean.FALSE));
    return this;
  }

  public ToStringBuilder append(final String name, final long value) {
    style.append(builder, name, value);
    return this;
  }

  public ToStringBuilder append(final String name,
      @Nullable final long[] value) {
    style.append(builder, name, value, null);
    return this;
  }

  public ToStringBuilder append(final String name,
      @Nullable final long[] value, final boolean fullDetail) {
    style.append(builder, name, value, (fullDetail ? Boolean.TRUE
                                                   : Boolean.FALSE));
    return this;
  }

  public ToStringBuilder append(final String name, final float value) {
    style.append(builder, name, value);
    return this;
  }

  public ToStringBuilder append(final String name,
      @Nullable final float[] value) {
    style.append(builder, name, value, null);
    return this;
  }

  public ToStringBuilder append(final String name,
      @Nullable final float[] value, final boolean fullDetail) {
    style.append(builder, name, value, (fullDetail ? Boolean.TRUE
                                                   : Boolean.FALSE));
    return this;
  }

  public ToStringBuilder append(final String name, final double value) {
    style.append(builder, name, value);
    return this;
  }

  public ToStringBuilder append(final String name,
      @Nullable final double[] value) {
    style.append(builder, name, value, null);
    return this;
  }

  public ToStringBuilder append(final String name,
      @Nullable final double[] value, final boolean fullDetail) {
    style.append(builder, name, value, (fullDetail ? Boolean.TRUE
                                                   : Boolean.FALSE));
    return this;
  }

  public ToStringBuilder append(final String name, @Nullable final Date value) {
    if (value == null) {
      style.append(builder, name, (String) null, null);
    } else {
      final Calendar cal = new GregorianCalendar(DateUtils.UTC);
      cal.setTime(value);
      final String str = DatatypeConverter.printDateTime(cal);
      style.append(builder, name, str, null);
    }
    return this;
  }

  public ToStringBuilder append(final String name,
      @Nullable final String value) {
    style.append(builder, name, value, null);
    return this;
  }

  public ToStringBuilder append(final String name,
      @Nullable final Object value) {
    style.append(builder, name, value, null);
    return this;
  }

  public ToStringBuilder append(final String name,
      @Nullable final Object value, final boolean fullDetail) {
    style.append(builder, name, value, (fullDetail ? Boolean.TRUE
                                                   : Boolean.FALSE));
    return this;
  }

  public ToStringBuilder append(final String name,
      @Nullable final Object[] value) {
    style.append(builder, name, value, null);
    return this;
  }

  public ToStringBuilder append(final String name,
      @Nullable final Object[] value, final boolean fullDetail) {
    style.append(builder, name, value, (fullDetail ? Boolean.TRUE
                                                   : Boolean.FALSE));
    return this;
  }

  /**
   * Append the {@code toString} from the superclass.
   *
   * <p>This method assumes that the superclass uses the same
   * {@code ToStringStyle} as this one. If {@code superToString} is {@code
   * null}, no change is made.
   *
   * @param superToString
   *     the result of {@code super.toString()}.
   * @return this builder.
   */
  public ToStringBuilder appendSuper(@Nullable final String superToString) {
    if (superToString != null) {
      style.appendSuper(builder, superToString);
    }
    return this;
  }

  /**
   * Append the {@code toString} from another object.
   *
   * <p>This method is useful where a class delegates most of the implementation
   * of its properties to another class. You can then call {@code toString()} on
   * the other class and pass the result into this method.
   *
   * <pre><code>
   *   private AnotherObject delegate;
   *   private int age;
   *   private boolean smoker;
   *
   *   public String toString() {
   *     final ToStringBuilderPool pool = ToStringBuilderPool.getInstance();
   *     final ToStringStyle style = ToStringStyle.getDefault();
   *     final ToStringBuilder builder = pool.borrow(style);
   *     try {
   *       return builder.reset(this)
   *                     .appendToString(delegate.toString());
   *                     .append("age", age);
   *                     .append("smoker", smoker)
   *                     .toString();
   *     } finally {
   *       pool.restore(style, builder);
   *     }
   *   }
   * </code></pre>
   *
   * <p>This method assumes that the other object uses the same
   * {@code ToStringStyle} as this one. If the {@code toString} is {@code null},
   * no change is made.
   *
   * @param toString
   *     the result of {@code toString()} on another object.
   * @return this builder.
   */
  public ToStringBuilder appendToString(@Nullable final String toString) {
    if (toString != null) {
      style.appendToString(builder, null, toString);
    }
    return this;
  }

  /**
   * Append the {@code toString} from another object.
   *
   * <p>This method is useful where a class delegates most of the implementation
   * of its properties to another class. You can then call {@code toString()} on
   * the other class and pass the result into this method.
   *
   * <pre><code>
   *   private AnotherObject delegate;
   *   private int age;
   *   private boolean smoker;
   *
   *   public String toString() {
   *     final ToStringBuilderPool pool = ToStringBuilderPool.getInstance();
   *     final ToStringStyle style = ToStringStyle.getDefault();
   *     final ToStringBuilder builder = pool.borrow(style);
   *     try {
   *       return builder.reset(this)
   *                     .appendToString("delegate", delegate.toString());
   *                     .append("age", age);
   *                     .append("smoker", smoker)
   *                     .toString();
   *     } finally {
   *       pool.restore(style, builder);
   *     }
   *   }
   * </code></pre>
   *
   * <p>This method assumes that the other object uses the same
   * {@code ToStringStyle} as this one. If the {@code toString} is {@code null},
   * no change is made.
   *
   * @param fieldName
   *     the field name.
   * @param toString
   *     the result of {@code toString()} on the field.
   * @return this builder.
   */
  public ToStringBuilder appendToString(final String fieldName,
      @Nullable final String toString) {
    if (toString != null) {
      style.appendToString(builder, fieldName, toString);
    }
    return this;
  }

  @Override
  public String toString() {
    if ((object == null) && (builder.length() == 0)) {
      builder.append(style.getNullText());
    } else {
      style.appendEnd(builder, object);
    }
    return builder.toString();
  }
}
