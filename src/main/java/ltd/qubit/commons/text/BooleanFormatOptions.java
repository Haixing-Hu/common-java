////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text;

import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

import static ltd.qubit.commons.lang.Argument.requireGreaterEqual;
import static ltd.qubit.commons.lang.Argument.requireValidUnicode;

/**
 * The options for formatting and parsing numbers.
 *
 * @author Haixing Hu
 */
public class BooleanFormatOptions extends FormatFlags {

  public static final int DEFAULT_WIDTH = 0;

  public static final int DEFAULT_FILL = ' ';

  private int width;
  private int fill;

  public BooleanFormatOptions() {
    width = DEFAULT_WIDTH;
    fill = DEFAULT_FILL;
  }

  @Override
  public void reset() {
    super.reset();
    width = DEFAULT_WIDTH;
    fill = DEFAULT_FILL;
  }

  public int getWidth() {
    return width;
  }

  public void setWidth(final int width) {
    requireGreaterEqual("width", width, "zero", 0);
    this.width = width;
  }

  public int getFill() {
    return fill;
  }

  public void setFill(final int fill) {
    this.fill = requireValidUnicode("fill", fill);
  }

  public void assign(final BooleanFormatOptions that) {
    if (this == that) {
      return;
    }
    flags = that.flags;
    width = that.width;
    fill = that.fill;
  }

  @Override
  public int hashCode() {
    final int multiplier = 31;
    int code = 3;
    code = Hash.combine(code, multiplier, flags);
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
    final BooleanFormatOptions other = (BooleanFormatOptions) obj;
    return (flags == other.flags)
        && (width == other.width)
        && (fill == other.fill);
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
               .append("flags", flags)
               .append("width", width)
               .append("fill", fill)
               .toString();
  }

}
