////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text;

import ltd.qubit.commons.lang.Argument;
import ltd.qubit.commons.lang.Assignable;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

/**
 * 用于格式化和解析数字的选项。
 *
 * @author 胡海星
 */
public class NumberFormatOptions extends FormatFlags implements Assignable<NumberFormatOptions> {

  /**
   * 默认基数。
   */
  public static final int DEFAULT_RADIX = 10;

  /**
   * 默认最大数字位数。
   */
  public static final int DEFAULT_MAX_DIGITS = Integer.MAX_VALUE;

  /**
   * 默认整数精度。
   */
  public static final int DEFAULT_INT_PRECISION = 1;

  /**
   * 默认实数精度。
   */
  public static final int DEFAULT_REAL_PRECISION = 6;

  /**
   * 默认宽度。
   */
  public static final int DEFAULT_WIDTH = 0;

  /**
   * 默认填充字符。
   */
  public static final int DEFAULT_FILL = ' ';

  private int defaultRadix;
  private int maxDigits;
  private int intPrecision;
  private int realPrecision;
  private int width;
  private int fill;

  /**
   * 构造一个使用默认设置的NumberFormatOptions实例。
   */
  public NumberFormatOptions() {
    flags = FormatFlag.DEFAULT;
    defaultRadix = DEFAULT_RADIX;
    maxDigits = DEFAULT_MAX_DIGITS;
    intPrecision = DEFAULT_INT_PRECISION;
    realPrecision = DEFAULT_REAL_PRECISION;
    width = DEFAULT_WIDTH;
    fill = DEFAULT_FILL;
  }

  /**
   * 构造一个NumberFormatOptions实例，复制另一个实例的设置。
   *
   * @param other
   *     要复制的NumberFormatOptions实例。
   */
  public NumberFormatOptions(final NumberFormatOptions other) {
    assign(other);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void assign(final NumberFormatOptions other) {
    Argument.requireNonNull("other", other);
    defaultRadix = other.defaultRadix;
    maxDigits = other.maxDigits;
    intPrecision = other.intPrecision;
    realPrecision = other.realPrecision;
    width = other.width;
    fill = other.fill;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public NumberFormatOptions cloneEx() {
    return new NumberFormatOptions(this);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void reset() {
    super.reset();
    defaultRadix = DEFAULT_RADIX;
    maxDigits = DEFAULT_MAX_DIGITS;
    intPrecision = DEFAULT_INT_PRECISION;
    realPrecision = DEFAULT_REAL_PRECISION;
    width = DEFAULT_WIDTH;
    fill = DEFAULT_FILL;
  }

  /**
   * 获取默认基数。
   *
   * @return
   *     默认基数。
   */
  public int getDefaultRadix() {
    return defaultRadix;
  }

  /**
   * 设置默认基数。
   *
   * @param defaultRadix
   *     默认基数，必须是允许的基数值之一。
   */
  public void setDefaultRadix(final int defaultRadix) {
    this.defaultRadix = Argument
            .requireInEnum("defaultRadix", defaultRadix, ALLOWED_RADIX);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getRadix() {
    final int radix = super.getRadix();
    return (radix > 0 ? radix : defaultRadix);
  }

  /**
   * 获取最大数字位数。
   *
   * @return
   *     最大数字位数。
   */
  public int getMaxDigits() {
    return maxDigits;
  }

  /**
   * 设置最大数字位数。
   *
   * @param maxDigits
   *     最大数字位数，必须大于0。
   */
  public void setMaxDigits(final int maxDigits) {
    Argument.requireGreater("maxDigits", maxDigits, "zero", 0);
    this.maxDigits = maxDigits;
  }

  /**
   * 获取整数精度。
   *
   * @return
   *     整数精度。
   */
  public int getIntPrecision() {
    return intPrecision;
  }

  /**
   * 设置整数精度。
   *
   * @param intPrecision
   *     整数精度，必须大于0。
   */
  public void setIntPrecision(final int intPrecision) {
    Argument.requireGreater("intPrecision", intPrecision, "zero", 0);
    this.intPrecision = intPrecision;
  }

  /**
   * 获取实数精度。
   *
   * @return
   *     实数精度。
   */
  public int getRealPrecision() {
    return realPrecision;
  }

  /**
   * 设置实数精度。
   *
   * @param realPrecision
   *     实数精度，必须大于0。
   */
  public void setRealPrecision(final int realPrecision) {
    Argument.requireGreater("realPrecision", realPrecision, "zero", 0);
    this.realPrecision = realPrecision;
  }

  /**
   * 获取宽度。
   *
   * @return
   *     宽度。
   */
  public int getWidth() {
    return width;
  }

  /**
   * 设置宽度。
   *
   * @param width
   *     宽度，必须大于或等于0。
   */
  public void setWidth(final int width) {
    Argument.requireGreaterEqual("width", width, "zero", 0);
    this.width = width;
  }

  /**
   * 获取填充字符。
   *
   * @return
   *     填充字符。
   */
  public int getFill() {
    return fill;
  }

  /**
   * 设置填充字符。
   *
   * @param fill
   *     填充字符，必须是有效的Unicode字符。
   */
  public void setFill(final int fill) {
    this.fill = Argument.requireValidUnicode("fill", fill);
  }

  @Override
  public int hashCode() {
    final int multiplier = 31;
    int code = 3;
    code = Hash.combine(code, multiplier, flags);
    code = Hash.combine(code, multiplier, defaultRadix);
    code = Hash.combine(code, multiplier, maxDigits);
    code = Hash.combine(code, multiplier, intPrecision);
    code = Hash.combine(code, multiplier, realPrecision);
    code = Hash.combine(code, multiplier, width);
    code = Hash.combine(code, multiplier, fill);
    return code;
  }

  @Override
  public boolean equals(final Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final NumberFormatOptions other = (NumberFormatOptions) obj;
    return (flags == other.flags)
        && (defaultRadix == other.defaultRadix)
        && (maxDigits == other.maxDigits)
        && (intPrecision == other.intPrecision)
        && (realPrecision == other.realPrecision)
        && (width == other.width)
        && (fill == other.fill);
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
               .append("flags", flags)
               .append("defaultRadix", defaultRadix)
               .append("maxDigits", maxDigits)
               .append("intPrecision", intPrecision)
               .append("realPrecision", realPrecision)
               .append("width", width)
               .append("fill", fill)
               .toString();
  }

}