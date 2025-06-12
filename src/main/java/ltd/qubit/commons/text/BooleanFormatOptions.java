////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
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
 * 格式化和解析布尔值的选项。
 *
 * @author 胡海星
 */
public class BooleanFormatOptions extends FormatFlags {

  /**
   * 默认的宽度。
   */
  public static final int DEFAULT_WIDTH = 0;

  /**
   * 默认的填充字符。
   */
  public static final int DEFAULT_FILL = ' ';

  /**
   * 宽度。
   */
  private int width;

  /**
   * 填充字符。
   */
  private int fill;

  /**
   * 构造一个新的 {@link BooleanFormatOptions} 对象。
   */
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

  /**
   * 获取宽度。
   *
   * @return 宽度。
   */
  public int getWidth() {
    return width;
  }

  /**
   * 设置宽度。
   *
   * @param width
   *          宽度，必须大于等于0。
   */
  public void setWidth(final int width) {
    requireGreaterEqual("width", width, "zero", 0);
    this.width = width;
  }

  /**
   * 获取填充字符。
   *
   * @return 填充字符。
   */
  public int getFill() {
    return fill;
  }

  /**
   * 设置填充字符。
   *
   * @param fill
   *          填充字符，必须是有效的Unicode字符。
   */
  public void setFill(final int fill) {
    this.fill = requireValidUnicode("fill", fill);
  }

  /**
   * 将另一个对象的值复制给此对象。
   *
   * @param that
   *          另一个 {@link BooleanFormatOptions} 对象。
   */
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