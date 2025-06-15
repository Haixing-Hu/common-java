////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text;

import static ltd.qubit.commons.text.NumberFormat.BINARY_RADIX;
import static ltd.qubit.commons.text.NumberFormat.DECIMAL_RADIX;
import static ltd.qubit.commons.text.NumberFormat.HEX_RADIX;
import static ltd.qubit.commons.text.NumberFormat.OCTAL_RADIX;

/**
 * 提供格式化文本的工具函数。
 *
 * @author 胡海星
 */
public final class FormatUtils {

  /**
   * 根据格式标志获取数字字符数组。
   *
   * @param flags
   *          格式标志。
   * @param symbols
   *          数字格式符号。
   * @return 数字字符数组。
   */
  public static char[] getDigits(final FormatFlags flags,
      final NumberFormatSymbols symbols) {
    if (flags.isUppercase()) {
      return symbols.getUppercaseDigits();
    } else {
      return symbols.getLowercaseDigits();
    }
  }

  /**
   * 将特殊进制（2、8、16进制）的整数值从后向前放入缓冲区。
   *
   * @param value
   *          要格式化的整数值。
   * @param radix
   *          进制（必须是2、8或16）。
   * @param precision
   *          精度。
   * @param digits
   *          数字字符数组。
   * @param buffer
   *          字符缓冲区。
   * @param startIndex
   *          起始索引。
   * @return 实际的起始索引。
   */
  public static int putSpecialRadixIntBackward(final int value, final int radix,
      final int precision, final char[] digits, final char[] buffer,
      final int startIndex) {
    if ((radix != BINARY_RADIX)
        && (radix != OCTAL_RADIX)
        && (radix != HEX_RADIX)) {
      throw new IllegalArgumentException("The special radix should be 2, 8, or 16.");
    }
    // let shift = floor(log2(radix))
    final int shift = (Integer.SIZE - 1) - Integer.numberOfLeadingZeros(radix);
    final int mask = (1 << shift) - 1;
    // put the value as an unsigned long
    int start = startIndex;
    int pre = precision;
    int result = value;
    while (result != 0) {
      final int d = (result & mask);
      buffer[--start] = digits[d];
      result >>>= shift;
      --pre;
    }
    // put the leading zeros to satisfy the precision
    while (pre > 0) {
      assert (start > 0);
      buffer[--start] = digits[0];
      --pre;
    }
    return start;
  }

  /**
   * 将特殊进制（2、8、16进制）的长整数值从后向前放入缓冲区。
   *
   * @param value
   *          要格式化的长整数值。
   * @param radix
   *          进制（必须是2、8或16）。
   * @param precision
   *          精度。
   * @param digits
   *          数字字符数组。
   * @param buffer
   *          字符缓冲区。
   * @param startIndex
   *          起始索引。
   * @return 实际的起始索引。
   */
  public static int putSpecialRadixLongBackward(final long value, final int radix,
      final int precision, final char[] digits, final char[] buffer,
      final int startIndex) {
    if ((radix != BINARY_RADIX)
        && (radix != OCTAL_RADIX)
        && (radix != HEX_RADIX)) {
      throw new IllegalArgumentException("The special radix should be 2, 8, or 16.");
    }
    // let shift = floor(log2(radix))
    final int shift = (Long.SIZE - 1) - Long.numberOfLeadingZeros(radix);
    final long mask = (1L << shift) - 1L;
    // put the value as an unsigned long
    int start = startIndex;
    int pre = precision;
    long result = value;
    while (result != 0) {
      final int d = (int) (result & mask);
      assert (start > 0);
      buffer[--start] = digits[d];
      result >>>= shift;
      --pre;
    }
    // put the leading zeros to satisfy the precision
    while (pre > 0) {
      assert (start > 0);
      buffer[--start] = digits[0];
      --pre;
    }
    return start;
  }

  /**
   * Integer.MIN_VALUE 的绝对值各位数字。
   */
  private static final int[] INT_MIN_ABS = {
      2, 1, 4, 7, 4, 8, 3, 6, 4, 8,
  };

  /**
   * 将十进制整数的绝对值从后向前放入缓冲区。
   *
   * @param value
   *          要格式化的整数值。
   * @param precision
   *          精度。
   * @param digits
   *          数字字符数组。
   * @param buffer
   *          字符缓冲区。
   * @param startIndex
   *          起始索引。
   * @return 实际的起始索引。
   */
  public static int putDecimalIntAbsBackward(final int value, final int precision,
      final char[] digits, final char[] buffer, final int startIndex) {
    int start = startIndex;
    int pre = precision;
    if (value == Integer.MIN_VALUE) {
      // take special care for Integer.MIN_VALUE, since its absolute value
      // can NOT be represented as a positive int value.
      for (int i = INT_MIN_ABS.length - 1; i >= 0; --i) {
        assert (start > 0);
        final int d = INT_MIN_ABS[i];
        buffer[--start] = digits[d];
        --pre;
      }
    } else {
      // put the absolute value backward
      int absValue = (value >= 0 ? value : (- value));
      while (absValue != 0) {
        final int d = (absValue % DECIMAL_RADIX);
        assert (start > 0);
        buffer[--start] = digits[d];
        absValue /= DECIMAL_RADIX;
        --pre;
      }
    }
    // put the leading zeros to satisfy the precision
    while (pre > 0) {
      assert (start > 0);
      buffer[--start] = digits[0];
      --pre;
    }
    return start;
  }

  /**
   * Long.MIN_VALUE 的绝对值各位数字。
   */
  private static final int[] LONG_MIN_ABS = {
      9, 2, 2, 3, 3, 7, 2, 0, 3, 6, 8, 5, 4, 7, 7, 5, 8, 0, 8,
  };

  /**
   * 将十进制长整数的绝对值从后向前放入缓冲区。
   *
   * @param value
   *          要格式化的长整数值。
   * @param precision
   *          精度。
   * @param digits
   *          数字字符数组。
   * @param buffer
   *          字符缓冲区。
   * @param startIndex
   *          起始索引。
   * @return 实际的起始索引。
   */
  public static int putDecimalLongAbsBackward(final long value, final int precision,
      final char[] digits, final char[] buffer, final int startIndex) {
    int start = startIndex;
    int pre = precision;
    if (value == Long.MIN_VALUE) {
      // take special care for Long.MIN_VALUE, since its absolute value
      // can NOT be represented as a positive long value.
      for (int i = LONG_MIN_ABS.length - 1; i >= 0; --i) {
        assert (start > 0);
        final int d = LONG_MIN_ABS[i];
        buffer[--start] = digits[d];
        --pre;
      }
    } else {
      // put the absolute value backward
      long absValue = (value >= 0 ? value : (- value));
      while (absValue != 0) {
        final int d = (int) (absValue % DECIMAL_RADIX);
        assert (start > 0);
        buffer[--start] = digits[d];
        absValue /= DECIMAL_RADIX;
        --pre;
      }
    }
    // put the leading zeros to satisfy the precision
    while (pre > 0) {
      assert (start > 0);
      buffer[--start] = digits[0];
      --pre;
    }
    return start;
  }

  /**
   * 将进制前缀从后向前放入缓冲区。
   *
   * @param radix
   *          进制。
   * @param radixPrefixes
   *          进制前缀数组。
   * @param buffer
   *          字符缓冲区。
   * @param startIndex
   *          起始索引。
   * @return 实际的起始索引。
   */
  public static int putRadixPrefixBackward(final int radix,
      final String[] radixPrefixes, final char[] buffer, final int startIndex) {
    final String prefix = radixPrefixes[radix];
    if (prefix == null) {
      return startIndex;
    }
    final int n = prefix.length();
    int start = startIndex;
    for (int i = 0; i < n; ++i) {
      buffer[--start] = prefix.charAt(i);
    }
    return start;
  }

  /**
   * 将整数从后向前放入缓冲区。
   *
   * @param signedValue
   *          有符号整数值。
   * @param unsignedValue
   *          无符号整数值。
   * @param options
   *          数字格式选项。
   * @param symbols
   *          数字格式符号。
   * @param buffer
   *          字符缓冲区。
   * @param startIndex
   *          起始索引。
   * @return 实际的起始索引。
   */
  public static int putIntBackward(final int signedValue,
      final int unsignedValue, final NumberFormatOptions options,
      final NumberFormatSymbols symbols, final char[] buffer, final int startIndex) {
    final int radix = options.getRadix();
    final char[] digits = symbols.getDigits(options.isUppercase());
    final int precision = options.getIntPrecision();
    int start = startIndex;
    switch (radix) {
      case BINARY_RADIX:  //  drop down
      case OCTAL_RADIX:   //  drop down
      case HEX_RADIX:
        start = putSpecialRadixIntBackward(unsignedValue, radix,
            precision, digits, buffer, start);
        break;
      case DECIMAL_RADIX:
      default:
        // TODO(haixing): Need to implement the grouping of decimal numbers in
        // the future version.
        start = putDecimalIntAbsBackward(signedValue, precision, digits,
            buffer, start);
        // put the sign
        if (signedValue < 0) {
          buffer[--start] = symbols.getNegativeSign();
        } else if (options.isShowPositive()) {
          buffer[--start] = symbols.getPositiveSign();
        } else if (options.isShowSpace()) {
          buffer[--start] = Ascii.SPACE;
        }
        break;
    }
    if (options.isShowRadix()) {
      final boolean uppercasePrefix = options.isUppercaseRadixPrefix();
      final String[] radixPrefixes = symbols.getRadixPrefixes(uppercasePrefix);
      start = putRadixPrefixBackward(radix, radixPrefixes, buffer, start);
    }
    return start;
  }

  /**
   * 将长整数从后向前放入缓冲区。
   *
   * @param value
   *          长整数值。
   * @param options
   *          数字格式选项。
   * @param symbols
   *          数字格式符号。
   * @param buffer
   *          字符缓冲区。
   * @param startIndex
   *          起始索引。
   * @return 实际的起始索引。
   */
  public static int putLongBackward(final long value,
      final NumberFormatOptions options, final NumberFormatSymbols symbols,
      final char[] buffer, final int startIndex) {
    final int radix = options.getRadix();
    final char[] digits = symbols.getDigits(options.isUppercase());
    final int precision = options.getIntPrecision();
    int start = startIndex;
    switch (radix) {
      case BINARY_RADIX:  // drop down
      case OCTAL_RADIX:   // drop down
      case HEX_RADIX:
        start = putSpecialRadixLongBackward(value, radix, precision,
            digits, buffer, start);
        break;
      case DECIMAL_RADIX:
      default:
        // TODO(haixing): Need to implement the grouping of decimal numbers in
        // the future version.
        start = putDecimalLongAbsBackward(value, precision, digits,
            buffer, start);
        // put the sign
        if (value < 0) {
          buffer[--start] = symbols.getNegativeSign();
        } else if (options.isShowPositive()) {
          buffer[--start] = symbols.getPositiveSign();
        } else if (options.isShowSpace()) {
          buffer[--start] = Ascii.SPACE;
        }
        break;
    }
    if (options.isShowRadix()) {
      final boolean uppercasePrefix = options.isUppercaseRadixPrefix();
      final String[] radixPrefixes = symbols.getRadixPrefixes(uppercasePrefix);
      start = putRadixPrefixBackward(radix, radixPrefixes, buffer,
          start);
    }
    return start;
  }

  /**
   * 将格式化结果写入输出并处理对齐和填充。
   *
   * @param flags
   *          格式标志。
   * @param width
   *          输出宽度。
   * @param fill
   *          填充字符。
   * @param buffer
   *          字符缓冲区。
   * @param startIndex
   *          起始索引。
   * @param endIndex
   *          结束索引。
   * @param output
   *          输出字符串构建器。
   * @return 实际输出的字符数。
   */
  public static int putFormatResult(final int flags, final int width,
      final int fill, final char[] buffer, final int startIndex,
      final int endIndex, final StringBuilder output) {

    assert ((buffer != null) && (startIndex >= 0) && (startIndex <= endIndex)
        && (endIndex <= buffer.length) && (output != null));

    final int actualWidth = endIndex - startIndex;
    if (width <= actualWidth) {
      for (int i = startIndex; i < endIndex; ++i) {
        output.append(buffer[i]);
      }
      return actualWidth;
    } else {
      final int padding = width - actualWidth;
      final int leftPadding;
      final int rightPadding;
      switch (flags & FormatFlag.ALIGN_MASK) {
        case FormatFlag.ALIGN_LEFT:
          leftPadding = 0;
          rightPadding = padding;
          break;
        case FormatFlag.ALIGN_CENTER:
          // note that if the padding is odd, the leftPadding is
          // larger than the rightPadding by 1.
          leftPadding = (padding + 1) / 2;
          rightPadding = padding - leftPadding;
          break;
        case FormatFlag.ALIGN_RIGHT:
        default:
          leftPadding = padding;
          rightPadding = 0;
          break;
      }
      if (Unicode.isBmp(fill)) {
        final char ch = (char) fill;
        for (int i = 0; i < leftPadding; ++i) {
          output.append(ch);
        }
        for (int i = startIndex; i < endIndex; ++i) {
          output.append(buffer[i]);
        }
        for (int i = 0; i < rightPadding; ++i) {
          output.append(ch);
        }
      } else {
        final char high = (char) Unicode.decomposeHighSurrogate(fill);
        final char low = (char) Unicode.decomposeLowSurrogate(fill);
        for (int i = 0; i < leftPadding; ++i) {
          output.append(high);
          output.append(low);
        }
        for (int i = startIndex; i < endIndex; ++i) {
          output.append(buffer[i]);
        }
        for (int i = 0; i < rightPadding; ++i) {
          output.append(high);
          output.append(low);
        }
      }
      return width;
    }
  }

  //  public static void formatDate(final Date value, final Appendable output,
  //      final FormatOptions options, final FormatSymbols symbols,
  //      final String formatPattern) throws IOException {
  //    // FIXME: use a better format method, use the format options and symbols
  //    final SimpleDateFormat df = new SimpleDateFormat(formatPattern);
  //    final String str = df.format(value);
  //    output.append(str);
  //  }
  //
  //  public static void formatDate(final Date value, final StringBuilder builder,
  //      final FormatOptions options, final FormatSymbols symbols,
  //      final String formatPattern) {
  //    // FIXME: use a better format method, use the format options and symbols
  //    final SimpleDateFormat df = new SimpleDateFormat(formatPattern);
  //    final String str = df.format(value);
  //    builder.append(str);
  //  }
  //
  //  public static String formatDate(final Date value, final FormatOptions options) {
  //    // FIXME:use a better format method, use the format options
  //    final SimpleDateFormat df = new SimpleDateFormat(DateUtils.DEFAULT_LOCAL_DATETIME_PATTERN);
  //    final String str = df.format(value);
  //    return str;
  //  }
  //
  //  public static String formatDate(final Date value) {
  //    // FIXME: use a better format method
  //    final SimpleDateFormat df = new SimpleDateFormat(DateUtils.DEFAULT_LOCAL_DATETIME_PATTERN);
  //    final String str = df.format(value);
  //    return str;
  //  }
}