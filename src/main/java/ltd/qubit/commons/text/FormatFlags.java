////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text;

import ltd.qubit.commons.text.tostring.ToStringBuilder;

import static ltd.qubit.commons.text.NumberFormat.BINARY_RADIX;
import static ltd.qubit.commons.text.NumberFormat.DECIMAL_RADIX;
import static ltd.qubit.commons.text.NumberFormat.HEX_RADIX;
import static ltd.qubit.commons.text.NumberFormat.OCTAL_RADIX;

/**
 * {@link FormatFlags}是格式标志的按位或组合。
 *
 * @author 胡海星
 */
public class FormatFlags {

  /**
   * 允许的基数数组。
   */
  public static final int[] ALLOWED_RADIX = {
    2, 8, 10, 16,
  };

  /**
   * 默认格式标志。
   */
  public static final int DEFAULT_FLAGS = FormatFlag.DEFAULT;

  /**
   * 格式标志位域。
   */
  protected int flags;

  /**
   * 创建默认的格式标志对象。
   */
  public FormatFlags() {
    flags = FormatFlag.DEFAULT;
  }

  /**
   * 创建指定标志的格式标志对象。
   *
   * @param flags
   *     初始格式标志值。
   */
  public FormatFlags(final int flags) {
    this.flags = flags;
  }

  /**
   * 重置为默认格式标志。
   */
  public void reset() {
    flags = FormatFlag.DEFAULT;
  }

  /**
   * 获取当前格式标志。
   *
   * @return
   *     当前格式标志。
   */
  public int getFlags() {
    return flags;
  }

  /**
   * 设置格式标志。
   *
   * @param flags
   *     要设置的格式标志。
   */
  public void setFlags(final int flags) {
    this.flags = flags;
  }

  /**
   * 判断是否启用字母布尔格式。
   *
   * @return
   *     如果启用字母布尔格式则返回{@code true}，否则返回{@code false}。
   */
  public boolean isBoolAlpha() {
    return (flags & FormatFlag.BOOL_ALPHA) != 0;
  }

  /**
   * 设置是否启用字母布尔格式。
   *
   * @param value
   *     如果为{@code true}则启用字母布尔格式，否则禁用。
   */
  public void setBoolAlpha(final boolean value) {
    if (value) {
      flags |= FormatFlag.BOOL_ALPHA;
    } else {
      flags &= (~ FormatFlag.BOOL_ALPHA);
    }
  }

  /**
   * 判断是否启用分组。
   *
   * @return
   *     如果启用分组则返回{@code true}，否则返回{@code false}。
   */
  public boolean isGrouping() {
    return (flags & FormatFlag.GROUPING) != 0;
  }

  /**
   * 设置是否启用分组。
   *
   * @param value
   *     如果为{@code true}则启用分组，否则禁用。
   */
  public void setGrouping(final boolean value) {
    if (value) {
      flags |= FormatFlag.GROUPING;
    } else {
      flags &= (~ FormatFlag.GROUPING);
    }
  }

  /**
   * 判断是否保留空白字符。
   *
   * @return
   *     如果保留空白字符则返回{@code true}，否则返回{@code false}。
   */
  public boolean isKeepBlank() {
    return (flags & FormatFlag.KEEP_BLANKS) != 0;
  }

  /**
   * 设置是否保留空白字符。
   *
   * @param value
   *     如果为{@code true}则保留空白字符，否则不保留。
   */
  public void setKeepBlank(final boolean value) {
    if (value) {
      flags |= FormatFlag.KEEP_BLANKS;
    } else {
      flags &= (~ FormatFlag.KEEP_BLANKS);
    }
  }

  /**
   * 判断是否为二进制格式。
   *
   * @return
   *     如果为二进制格式则返回{@code true}，否则返回{@code false}。
   */
  public boolean isBinary() {
    return (flags & FormatFlag.BINARY) != 0;
  }

  /**
   * 设置是否为二进制格式。
   *
   * @param value
   *     如果为{@code true}则设置为二进制格式，否则取消二进制格式。
   */
  public void setBinary(final boolean value) {
    if (value) {
      // clear all other radix options
      flags &= (~ FormatFlag.RADIX_MASK);
      // set binary option
      flags |= FormatFlag.BINARY;
    } else {
      flags &= (~ FormatFlag.BINARY);
    }
  }

  /**
   * 判断是否为八进制格式。
   *
   * @return
   *     如果为八进制格式则返回{@code true}，否则返回{@code false}。
   */
  public boolean isOctal() {
    return (flags & FormatFlag.OCTAL) != 0;
  }

  /**
   * 设置是否为八进制格式。
   *
   * @param value
   *     如果为{@code true}则设置为八进制格式，否则取消八进制格式。
   */
  public void setOctal(final boolean value) {
    if (value) {
      // clear all other radix options
      flags &= (~ FormatFlag.RADIX_MASK);
      // set octal option
      flags |= FormatFlag.OCTAL;
    } else {
      flags &= (~ FormatFlag.OCTAL);
    }
  }

  /**
   * 判断是否为十进制格式。
   *
   * @return
   *     如果为十进制格式则返回{@code true}，否则返回{@code false}。
   */
  public boolean isDecimal() {
    return (flags & FormatFlag.DECIMAL) != 0;
  }

  /**
   * 设置是否为十进制格式。
   *
   * @param value
   *     如果为{@code true}则设置为十进制格式，否则取消十进制格式。
   */
  public void setDecimal(final boolean value) {
    if (value) {
      // clear all other radix options
      flags &= (~ FormatFlag.RADIX_MASK);
      // set decimal option
      flags |= FormatFlag.DECIMAL;
    } else {
      flags &= (~ FormatFlag.DECIMAL);
    }
  }

  /**
   * 判断是否为十六进制格式。
   *
   * @return
   *     如果为十六进制格式则返回{@code true}，否则返回{@code false}。
   */
  public boolean isHex() {
    return (flags & FormatFlag.HEX) != 0;
  }

  /**
   * 设置是否为十六进制格式。
   *
   * @param value
   *     如果为{@code true}则设置为十六进制格式，否则取消十六进制格式。
   */
  public void setHex(final boolean value) {
    if (value) {
      // clear all other radix options
      flags &= (~ FormatFlag.RADIX_MASK);
      // set hex option
      flags |= FormatFlag.HEX;
    } else {
      flags &= (~ FormatFlag.HEX);
    }
  }

  /**
   * 获取当前使用的进制基数。
   *
   * @return
   *     当前使用的进制基数，可能是2（二进制）、8（八进制）、10（十进制）或16（十六进制）；
   *     如果没有设置任何进制标志，则返回-1。
   */
  public int getRadix() {
    switch (flags & FormatFlag.RADIX_MASK) {
      case FormatFlag.BINARY:
        return BINARY_RADIX;
      case FormatFlag.OCTAL:
        return OCTAL_RADIX;
      case FormatFlag.DECIMAL:
        return DECIMAL_RADIX;
      case FormatFlag.HEX:
        return HEX_RADIX;
      default:
        return -1;
    }
  }

  /**
   * 设置进制基数。
   *
   * @param radix
   *     要设置的进制基数，必须是2、8、10或16之一。
   * @throws IllegalArgumentException
   *     如果提供的进制基数不被支持。
   */
  public void setRadix(final int radix) {
    flags &= (~ FormatFlag.RADIX_MASK);
    switch (radix) {
      case BINARY_RADIX:
        flags |= FormatFlag.BINARY;
        break;
      case OCTAL_RADIX:
        flags |= FormatFlag.OCTAL;
        break;
      case DECIMAL_RADIX:
        flags |= FormatFlag.DECIMAL;
        break;
      case HEX_RADIX:
        flags |= FormatFlag.HEX;
        break;
      default:
        throw new IllegalArgumentException("Unsupported radix: " + radix);
    }
  }

  /**
   * 清除所有进制选项。
   */
  public void clearRadixOptions() {
    // clear all other radix options
    flags &= (~ FormatFlag.RADIX_MASK);
  }

  /**
   * 判断是否为定点数格式。
   *
   * @return
   *     如果为定点数格式则返回{@code true}，否则返回{@code false}。
   */
  public boolean isFixPoint() {
    return (flags & FormatFlag.FIXED_POINT) != 0;
  }

  /**
   * 设置是否为定点数格式。
   *
   * @param value
   *     如果为{@code true}则设置为定点数格式，否则取消定点数格式。
   */
  public void setFixPoint(final boolean value) {
    if (value) {
      // clear all other real options
      flags &= (~ FormatFlag.REAL_MASK);
      // set fix point option
      flags |= FormatFlag.FIXED_POINT;
    } else {
      flags &= (~ FormatFlag.FIXED_POINT);
    }
  }

  /**
   * 判断是否为科学计数法格式。
   *
   * @return
   *     如果为科学计数法格式则返回{@code true}，否则返回{@code false}。
   */
  public boolean isScientific() {
    return (flags & FormatFlag.SCIENTIFIC) != 0;
  }

  /**
   * 设置是否为科学计数法格式。
   *
   * @param value
   *     如果为{@code true}则设置为科学计数法格式，否则取消科学计数法格式。
   */
  public void setScientific(final boolean value) {
    if (value) {
      // clear all other real options
      flags &= (~ FormatFlag.REAL_MASK);
      // set scientific option
      flags |= FormatFlag.SCIENTIFIC;
    } else {
      flags &= (~ FormatFlag.SCIENTIFIC);
    }
  }

  /**
   * 判断是否为紧凑实数格式。
   *
   * @return
   *     如果为紧凑实数格式则返回{@code true}，否则返回{@code false}。
   */
  public boolean isShortReal() {
    return (flags & FormatFlag.SHORT_REAL) != 0;
  }

  /**
   * 设置是否为紧凑实数格式。
   *
   * @param value
   *     如果为{@code true}则设置为紧凑实数格式，否则取消紧凑实数格式。
   */
  public void setShortReal(final boolean value) {
    if (value) {
      // clear all other real options
      flags &= (~ FormatFlag.REAL_MASK);
      // set scientific option
      flags |= FormatFlag.SHORT_REAL;
    } else {
      flags &= (~ FormatFlag.SHORT_REAL);
    }
  }

  /**
   * 清除所有实数格式选项。
   */
  public void clearRealOptions() {
    // clear all other real options
    flags &= (~ FormatFlag.REAL_MASK);
  }

  /**
   * 判断是否显示进制前缀。
   *
   * @return
   *     如果显示进制前缀则返回{@code true}，否则返回{@code false}。
   */
  public boolean isShowRadix() {
    return (flags & FormatFlag.SHOW_RADIX) != 0;
  }

  /**
   * 设置是否显示进制前缀。
   *
   * @param value
   *     如果为{@code true}则显示进制前缀，否则不显示。
   */
  public void setShowRadix(final boolean value) {
    if (value) {
      flags |= FormatFlag.SHOW_RADIX;
    } else {
      flags &= (~ FormatFlag.SHOW_RADIX);
    }
  }

  /**
   * 判断是否显示小数点。
   *
   * @return
   *     如果显示小数点则返回{@code true}，否则返回{@code false}。
   */
  public boolean isShowPoint() {
    return (flags & FormatFlag.SHOW_POINT) != 0;
  }

  /**
   * 设置是否显示小数点。
   *
   * @param value
   *     如果为{@code true}则显示小数点，否则不显示。
   */
  public void setShowPoint(final boolean value) {
    if (value) {
      flags |= FormatFlag.SHOW_POINT;
    } else {
      flags &= (~ FormatFlag.SHOW_POINT);
    }
  }

  /**
   * 判断是否显示正数符号。
   *
   * @return
   *     如果显示正数符号则返回{@code true}，否则返回{@code false}。
   */
  public boolean isShowPositive() {
    return (flags & FormatFlag.SHOW_POSITIVE) != 0;
  }

  /**
   * 设置是否显示正数符号。
   *
   * @param value
   *     如果为{@code true}则显示正数符号，否则不显示。
   */
  public void setShowPositive(final boolean value) {
    if (value) {
      // clear the show space option
      flags &= (~ FormatFlag.SHOW_SPACE);
      // set the show radix option
      flags |= FormatFlag.SHOW_POSITIVE;
    } else {
      flags &= (~ FormatFlag.SHOW_POSITIVE);
    }
  }

  /**
   * 判断是否显示正数前的空格。
   *
   * @return
   *     如果显示正数前的空格则返回{@code true}，否则返回{@code false}。
   */
  public boolean isShowSpace() {
    return (flags & FormatFlag.SHOW_SPACE) != 0;
  }

  /**
   * 设置是否显示正数前的空格。
   *
   * @param value
   *     如果为{@code true}则显示正数前的空格，否则不显示。
   */
  public void setShowSpace(final boolean value) {
    if (value) {
      // clear the show positive option
      flags &= (~ FormatFlag.SHOW_POSITIVE);
      // set the show space option
      flags |= FormatFlag.SHOW_SPACE;
    } else {
      flags &= (~ FormatFlag.SHOW_SPACE);
    }
  }

  /**
   * 判断是否使用大写的进制前缀。
   *
   * @return
   *     如果使用大写的进制前缀则返回{@code true}，否则返回{@code false}。
   */
  public boolean isUppercaseRadixPrefix() {
    return (flags & FormatFlag.UPPERCASE_RADIX_PREFIX) != 0;
  }

  /**
   * 设置是否使用大写的进制前缀。
   *
   * @param value
   *     如果为{@code true}则使用大写的进制前缀，否则使用小写。
   */
  public void setUppercaseRadixPrefix(final boolean value) {
    if (value) {
      flags |= FormatFlag.UPPERCASE_RADIX_PREFIX;
    } else {
      flags &= (~ FormatFlag.UPPERCASE_RADIX_PREFIX);
    }
  }

  /**
   * 判断是否使用大写的指数符号。
   *
   * @return
   *     如果使用大写的指数符号则返回{@code true}，否则返回{@code false}。
   */
  public boolean isUppercaseExponent() {
    return (flags & FormatFlag.UPPERCASE_EXPONENT) != 0;
  }

  /**
   * 设置是否使用大写的指数符号。
   *
   * @param value
   *     如果为{@code true}则使用大写的指数符号，否则使用小写。
   */
  public void setUppercaseExponent(final boolean value) {
    if (value) {
      flags |= FormatFlag.UPPERCASE_EXPONENT;
    } else {
      flags &= (~ FormatFlag.UPPERCASE_EXPONENT);
    }
  }

  /**
   * 判断是否左对齐。
   *
   * @return
   *     如果左对齐则返回{@code true}，否则返回{@code false}。
   */
  public boolean isAlignLeft() {
    return (flags & FormatFlag.ALIGN_LEFT) != 0;
  }

  /**
   * 设置是否左对齐。
   *
   * @param value
   *     如果为{@code true}则设置左对齐，否则取消左对齐。
   */
  public void setAlignLeft(final boolean value) {
    if (value) {
      // clear the alignment options
      flags &= (~ FormatFlag.ALIGN_MASK);
      // set the left option
      flags |= FormatFlag.ALIGN_LEFT;
    } else {
      flags &= (~ FormatFlag.ALIGN_LEFT);
    }
  }

  /**
   * 判断是否居中对齐。
   *
   * @return
   *     如果居中对齐则返回{@code true}，否则返回{@code false}。
   */
  public boolean isAlignCenter() {
    return (flags & FormatFlag.ALIGN_CENTER) != 0;
  }

  /**
   * 设置是否居中对齐。
   *
   * @param value
   *     如果为{@code true}则设置居中对齐，否则取消居中对齐。
   */
  public void setAlignCenter(final boolean value) {
    if (value) {
      // clear the alignment options
      flags &= (~ FormatFlag.ALIGN_MASK);
      // set the center option
      flags |= FormatFlag.ALIGN_CENTER;
    } else {
      flags &= (~ FormatFlag.ALIGN_CENTER);
    }
  }

  /**
   * 判断是否右对齐。
   *
   * @return
   *     如果右对齐则返回{@code true}，否则返回{@code false}。
   */
  public boolean isAlignRight() {
    return (flags & FormatFlag.ALIGN_RIGHT) != 0;
  }

  /**
   * 设置是否右对齐。
   *
   * @param value
   *     如果为{@code true}则设置右对齐，否则取消右对齐。
   */
  public void setAlignRight(final boolean value) {
    if (value) {
      // clear the alignment options
      flags &= (~ FormatFlag.ALIGN_MASK);
      // set the right option
      flags |= FormatFlag.ALIGN_RIGHT;
    } else {
      flags &= (~ FormatFlag.ALIGN_RIGHT);
    }
  }

  /**
   * 清除所有对齐选项。
   */
  public void clearAlignOptions() {
    flags &= (~ FormatFlag.ALIGN_MASK);
  }

  /**
   * 判断是否使用大写字母。
   *
   * @return
   *     如果使用大写字母则返回{@code true}，否则返回{@code false}。
   */
  public boolean isUppercase() {
    return (flags & FormatFlag.UPPERCASE) != 0;
  }

  /**
   * 设置是否使用大写字母。
   *
   * @param value
   *     如果为{@code true}则使用大写字母，否则不使用。
   */
  public void setUppercase(final boolean value) {
    if (value) {
      flags &= (~ FormatFlag.CASE_MASK);
      flags |= FormatFlag.UPPERCASE;
    } else {
      flags &= (~ FormatFlag.UPPERCASE);
    }
  }

  /**
   * 判断是否使用小写字母。
   *
   * @return
   *     如果使用小写字母则返回{@code true}，否则返回{@code false}。
   */
  public boolean isLowercase() {
    return (flags & FormatFlag.LOWERCASE) != 0;
  }

  /**
   * 设置是否使用小写字母。
   *
   * @param value
   *     如果为{@code true}则使用小写字母，否则不使用。
   */
  public void setLowercase(final boolean value) {
    if (value) {
      flags &= (~ FormatFlag.CASE_MASK);
      flags |= FormatFlag.LOWERCASE;
    } else {
      flags &= (~ FormatFlag.LOWERCASE);
    }
  }

  /**
   * 判断是否使用标题大小写。
   *
   * @return
   *     如果使用标题大小写则返回{@code true}，否则返回{@code false}。
   */
  public boolean isTitlecase() {
    return (flags & FormatFlag.TITLECASE) != 0;
  }

  /**
   * 设置是否使用标题大小写。
   *
   * @param value
   *     如果为{@code true}则使用标题大小写，否则不使用。
   */
  public void setTitlecase(final boolean value) {
    if (value) {
      flags &= (~ FormatFlag.CASE_MASK);
      flags |= FormatFlag.TITLECASE;
    } else {
      flags &= (~ FormatFlag.TITLECASE);
    }
  }

  /**
   * 清除所有大小写选项。
   */
  public void clearCaseOptions() {
    flags &= (~ FormatFlag.CASE_MASK);
  }

  @Override
  public int hashCode() {
    return flags;
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
    final FormatFlags other = (FormatFlags) obj;
    return (flags == other.flags);
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
               .append("flags", flags)
               .toString();
  }

}