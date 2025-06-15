////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text;

import java.io.Serializable;

import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

/**
 * {@link ParseOptions} 对象存储解析文本的选项。
 *
 * @author 胡海星
 */
public class ParseOptions implements Serializable {

  /** @serial */
  private static final long serialVersionUID = 1340148038313182446L;

  /**
   * 默认的格式标志。
   */
  public static final int DEFAULT_FLAGS = FormatFlag.DEFAULT;

  /**
   * 默认的进制。
   */
  public static final int DEFAULT_RADIX = 10;

  /**
   * 默认的最大数字位数。
   */
  public static final int DEFAULT_MAX_DIGITS = Integer.MAX_VALUE;

  /**
   * 默认的解析选项。
   */
  public static final ParseOptions DEFAULT = new UnmodifiableParseOptions();

  /**
   * 保留空白字符的默认解析选项。
   */
  public static final ParseOptions DEFAULT_KEEP_BLANKS = new UnmodifiableParseOptions(
      FormatFlag.DEFAULT | FormatFlag.KEEP_BLANKS);

  /**
   * 十六进制字节的解析选项。
   */
  public static final ParseOptions HEX_BYTE = new UnmodifiableParseOptions(
      ((FormatFlag.DEFAULT & (~FormatFlag.RADIX_MASK)) | FormatFlag.HEX), 16, 2);

  protected int flags;
  protected int defaultRadix;
  protected int maxDigits;

  /**
   * 构造一个使用默认值的解析选项对象。
   */
  public ParseOptions() {
    flags = DEFAULT_FLAGS;
    defaultRadix = DEFAULT_RADIX;
    maxDigits = DEFAULT_MAX_DIGITS;
  }

  /**
   * 构造一个指定格式标志的解析选项对象。
   *
   * @param flags
   *     格式标志。
   */
  public ParseOptions(final int flags) {
    this.flags = flags;
    defaultRadix = DEFAULT_RADIX;
    maxDigits = DEFAULT_MAX_DIGITS;
  }

  /**
   * 构造一个指定格式标志和默认进制的解析选项对象。
   *
   * @param flags
   *     格式标志。
   * @param defaultRadix
   *     默认进制。
   */
  public ParseOptions(final int flags, final int defaultRadix) {
    this.flags = flags;
    this.defaultRadix = defaultRadix;
    maxDigits = DEFAULT_MAX_DIGITS;
  }

  /**
   * 构造一个指定格式标志、默认进制和最大数字位数的解析选项对象。
   *
   * @param flags
   *     格式标志。
   * @param defaultRadix
   *     默认进制。
   * @param maxDigits
   *     最大数字位数。
   */
  public ParseOptions(final int flags, final int defaultRadix, final int maxDigits) {
    this.flags = flags;
    this.defaultRadix = defaultRadix;
    this.maxDigits = maxDigits;
  }

  /**
   * 构造一个复制指定解析选项的对象。
   *
   * @param other
   *     要复制的解析选项。
   */
  public ParseOptions(final ParseOptions other) {
    flags = other.flags;
    defaultRadix = other.defaultRadix;
    maxDigits = other.maxDigits;
  }

  /**
   * 获取格式标志。
   *
   * @return
   *     格式标志。
   */
  public final int getFlags() {
    return flags;
  }

  /**
   * 设置格式标志。
   *
   * @param flags
   *     新的格式标志。
   */
  public void setFlags(final int flags) {
    this.flags = flags;
  }

  /**
   * 添加格式标志。
   *
   * @param flags
   *     要添加的格式标志。
   */
  public void addFlags(final int flags) {
    this.flags |= flags;
  }

  /**
   * 在指定掩码下添加格式标志。
   *
   * @param flags
   *     要添加的格式标志。
   * @param mask
   *     掩码。
   */
  public void addFlags(final int flags, final int mask) {
    this.flags &= (~mask);
    this.flags |= flags;
  }

  /**
   * 获取默认进制。
   *
   * @return
   *     默认进制。
   */
  public final int getDefaultRadix() {
    return defaultRadix;
  }

  /**
   * 设置默认进制。
   *
   * @param defaultRadix
   *     新的默认进制。
   */
  public void setDefaultRadix(final int defaultRadix) {
    this.defaultRadix = defaultRadix;
  }

  /**
   * 获取最大数字位数。
   *
   * @return
   *     最大数字位数。
   */
  public final int getMaxDigits() {
    return maxDigits;
  }

  /**
   * 设置最大数字位数。
   *
   * @param maxDigits
   *     新的最大数字位数。
   */
  public void setMaxDigits(final int maxDigits) {
    this.maxDigits = maxDigits;
  }

  /**
   * 重置最大数字位数为默认值。
   */
  public void resetMaxDigits() {
    maxDigits = DEFAULT_MAX_DIGITS;
  }

  /**
   * 判断是否启用布尔值字母表示。
   *
   * @return
   *     如果启用布尔值字母表示则返回 {@code true}，否则返回 {@code false}。
   */
  public final boolean isBoolAlpha() {
    return (flags & FormatFlag.BOOL_ALPHA) != 0;
  }

  /**
   * 设置是否启用布尔值字母表示。
   *
   * @param value
   *     如果为 {@code true} 则启用布尔值字母表示，否则禁用。
   */
  public void setBoolAlpha(final boolean value) {
    if (value) {
      flags |= FormatFlag.BOOL_ALPHA;
    } else {
      flags &= (~FormatFlag.BOOL_ALPHA);
    }
  }

  /**
   * 判断是否启用数字分组。
   *
   * @return
   *     如果启用数字分组则返回 {@code true}，否则返回 {@code false}。
   */
  public final boolean isGrouping() {
    return (flags & FormatFlag.GROUPING) != 0;
  }

  /**
   * 设置是否启用数字分组。
   *
   * @param value
   *     如果为 {@code true} 则启用数字分组，否则禁用。
   */
  public void setGrouping(final boolean value) {
    if (value) {
      flags |= FormatFlag.GROUPING;
    } else {
      flags &= (~FormatFlag.GROUPING);
    }
  }

  /**
   * 判断是否保留空白字符。
   *
   * @return
   *     如果保留空白字符则返回 {@code true}，否则返回 {@code false}。
   */
  public final boolean isKeepBlank() {
    return (flags & FormatFlag.KEEP_BLANKS) != 0;
  }

  /**
   * 设置是否保留空白字符。
   *
   * @param value
   *     如果为 {@code true} 则保留空白字符，否则不保留。
   */
  public void setKeepBlank(final boolean value) {
    if (value) {
      flags |= FormatFlag.KEEP_BLANKS;
    } else {
      flags &= (~FormatFlag.KEEP_BLANKS);
    }
  }

  /**
   * 判断是否使用二进制格式。
   *
   * @return
   *     如果使用二进制格式则返回 {@code true}，否则返回 {@code false}。
   */
  public final boolean isBinary() {
    return (flags & FormatFlag.BINARY) != 0;
  }

  /**
   * 设置是否使用二进制格式。
   *
   * @param value
   *     如果为 {@code true} 则使用二进制格式，否则不使用。
   */
  public void setBinary(final boolean value) {
    if (value) {
      // clear all other radix options
      flags &= (~FormatFlag.RADIX_MASK);
      // set binary option
      flags |= FormatFlag.BINARY;
    } else {
      flags &= (~FormatFlag.BINARY);
    }
  }

  /**
   * 判断是否使用八进制格式。
   *
   * @return
   *     如果使用八进制格式则返回 {@code true}，否则返回 {@code false}。
   */
  public final boolean isOctal() {
    return (flags & FormatFlag.OCTAL) != 0;
  }

  /**
   * 设置是否使用八进制格式。
   *
   * @param value
   *     如果为 {@code true} 则使用八进制格式，否则不使用。
   */
  public void setOctal(final boolean value) {
    if (value) {
      // clear all other radix options
      flags &= (~FormatFlag.RADIX_MASK);
      // set octal option
      flags |= FormatFlag.OCTAL;
    } else {
      flags &= (~FormatFlag.OCTAL);
    }
  }

  /**
   * 判断是否使用十进制格式。
   *
   * @return
   *     如果使用十进制格式则返回 {@code true}，否则返回 {@code false}。
   */
  public final boolean isDecimal() {
    return (flags & FormatFlag.DECIMAL) != 0;
  }

  /**
   * 设置是否使用十进制格式。
   *
   * @param value
   *     如果为 {@code true} 则使用十进制格式，否则不使用。
   */
  public void setDecimal(final boolean value) {
    if (value) {
      // clear all other radix options
      flags &= (~FormatFlag.RADIX_MASK);
      // set decimal option
      flags |= FormatFlag.DECIMAL;
    } else {
      flags &= (~FormatFlag.DECIMAL);
    }
  }

  /**
   * 判断是否使用十六进制格式。
   *
   * @return
   *     如果使用十六进制格式则返回 {@code true}，否则返回 {@code false}。
   */
  public final boolean isHex() {
    return (flags & FormatFlag.HEX) != 0;
  }

  /**
   * 设置是否使用十六进制格式。
   *
   * @param value
   *     如果为 {@code true} 则使用十六进制格式，否则不使用。
   */
  public void setHex(final boolean value) {
    if (value) {
      // clear all other radix options
      flags &= (~FormatFlag.RADIX_MASK);
      // set hex option
      flags |= FormatFlag.HEX;
    } else {
      flags &= (~FormatFlag.HEX);
    }
  }

  /**
   * 清除所有进制选项。
   */
  public void clearRadixOptions() {
    // clear all other radix options
    flags &= (~FormatFlag.RADIX_MASK);
  }

  /**
   * 判断是否使用定点数格式。
   *
   * @return
   *     如果使用定点数格式则返回 {@code true}，否则返回 {@code false}。
   */
  public final boolean isFixPoint() {
    return (flags & FormatFlag.FIXED_POINT) != 0;
  }

  /**
   * 设置是否使用定点数格式。
   *
   * @param value
   *     如果为 {@code true} 则使用定点数格式，否则不使用。
   */
  public void setFixPoint(final boolean value) {
    if (value) {
      // clear all other real options
      flags &= (~FormatFlag.REAL_MASK);
      // set fix point option
      flags |= FormatFlag.FIXED_POINT;
    } else {
      flags &= (~FormatFlag.FIXED_POINT);
    }
  }

  /**
   * 判断是否使用科学计数法格式。
   *
   * @return
   *     如果使用科学计数法格式则返回 {@code true}，否则返回 {@code false}。
   */
  public final boolean isScientific() {
    return (flags & FormatFlag.SCIENTIFIC) != 0;
  }

  /**
   * 设置是否使用科学计数法格式。
   *
   * @param value
   *     如果为 {@code true} 则使用科学计数法格式，否则不使用。
   */
  public void setScientific(final boolean value) {
    if (value) {
      // clear all other real options
      flags &= (~FormatFlag.REAL_MASK);
      // set scientific option
      flags |= FormatFlag.SCIENTIFIC;
    } else {
      flags &= (~FormatFlag.SCIENTIFIC);
    }
  }

  /**
   * 判断是否使用通用格式。
   *
   * @return
   *     如果使用通用格式则返回 {@code true}，否则返回 {@code false}。
   */
  public final boolean isGeneral() {
    return (flags & FormatFlag.SHORT_REAL) != 0;
  }

  /**
   * 设置是否使用通用格式。
   *
   * @param value
   *     如果为 {@code true} 则使用通用格式，否则不使用。
   */
  public void setGeneral(final boolean value) {
    if (value) {
      // clear all other real options
      flags &= (~FormatFlag.REAL_MASK);
      // set scientific option
      flags |= FormatFlag.SHORT_REAL;
    } else {
      flags &= (~FormatFlag.SHORT_REAL);
    }
  }

  /**
   * 清除所有实数格式选项。
   */
  public void clearRealOptions() {
    // clear all other real options
    flags &= (~FormatFlag.REAL_MASK);
  }

  /**
   * 重置此对象为默认值。
   */
  public void reset() {
    flags = DEFAULT_FLAGS;
    defaultRadix = DEFAULT_RADIX;
    maxDigits = DEFAULT_MAX_DIGITS;
  }

  @Override
  public int hashCode() {
    final int multiplier = 13;
    int code = 17;
    code = Hash.combine(code, multiplier, flags);
    code = Hash.combine(code, multiplier, defaultRadix);
    code = Hash.combine(code, multiplier, maxDigits);
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
    final ParseOptions other = (ParseOptions) obj;
    return (flags == other.flags)
        && (defaultRadix == other.defaultRadix)
        && (maxDigits == other.maxDigits);
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("flags", flags)
        .append("defaultRadix", defaultRadix)
        .append("maxDigits", maxDigits)
        .toString();
  }
}