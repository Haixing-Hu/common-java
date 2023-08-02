////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
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
 * The options for formatting and parsing numbers.
 *
 * @author Haixing Hu
 */
public class NumberFormatOptions extends FormatFlags implements Assignable<NumberFormatOptions> {

  public static final int DEFAULT_RADIX = 10;

  public static final int DEFAULT_MAX_DIGITS = Integer.MAX_VALUE;

  public static final int DEFAULT_INT_PRECISION = 1;

  public static final int DEFAULT_REAL_PRECISION = 6;

  public static final int DEFAULT_WIDTH = 0;

  public static final int DEFAULT_FILL = ' ';

  private int defaultRadix;
  private int maxDigits;
  private int intPrecision;
  private int realPrecision;
  private int width;
  private int fill;

  public NumberFormatOptions() {
    flags = FormatFlag.DEFAULT;
    defaultRadix = DEFAULT_RADIX;
    maxDigits = DEFAULT_MAX_DIGITS;
    intPrecision = DEFAULT_INT_PRECISION;
    realPrecision = DEFAULT_REAL_PRECISION;
    width = DEFAULT_WIDTH;
    fill = DEFAULT_FILL;
  }

  public NumberFormatOptions(final NumberFormatOptions other) {
    assign(other);
  }

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

  @Override
  public NumberFormatOptions clone() {
    return new NumberFormatOptions(this);
  }

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

  public int getDefaultRadix() {
    return defaultRadix;
  }

  public void setDefaultRadix(final int defaultRadix) {
    this.defaultRadix = Argument
            .requireInEnum("defaultRadix", defaultRadix, ALLOWED_RADIX);
  }

  @Override
  public int getRadix() {
    final int radix = super.getRadix();
    return (radix > 0 ? radix : defaultRadix);
  }

  public int getMaxDigits() {
    return maxDigits;
  }

  public void setMaxDigits(final int maxDigits) {
    Argument.requireGreater("maxDigits", maxDigits, "zero", 0);
    this.maxDigits = maxDigits;
  }

  public int getIntPrecision() {
    return intPrecision;
  }

  public void setIntPrecision(final int intPrecision) {
    Argument.requireGreater("intPrecision", intPrecision, "zero", 0);
    this.intPrecision = intPrecision;
  }

  public int getRealPrecision() {
    return realPrecision;
  }

  public void setRealPrecision(final int realPrecision) {
    Argument.requireGreater("realPrecision", realPrecision, "zero", 0);
    this.realPrecision = realPrecision;
  }

  public int getWidth() {
    return width;
  }

  public void setWidth(final int width) {
    Argument.requireGreaterEqual("width", width, "zero", 0);
    this.width = width;
  }

  public int getFill() {
    return fill;
  }

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
