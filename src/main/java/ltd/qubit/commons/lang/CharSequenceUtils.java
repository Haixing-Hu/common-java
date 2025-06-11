////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.lang;

/**
 * 提供字符序列的实用工具函数。
 *
 * @author 胡海星
 */
public final class CharSequenceUtils {

  /**
   * 检查字符序列是否以指定的前缀开头。
   *
   * @param str 要检查的字符序列
   * @param prefix 前缀
   * @return 如果字符序列以指定前缀开头则返回 {@code true}，否则返回 {@code false}
   */
  public static boolean startsWith(final CharSequence str,
      final CharSequence prefix) {
    return startsWith(str, 0, str.length(), prefix);
  }

  /**
   * 检查字符序列从指定位置开始是否以指定的前缀开头。
   *
   * @param str 要检查的字符序列
   * @param startIndex 开始检查的位置
   * @param prefix 前缀
   * @return 如果字符序列从指定位置开始以指定前缀开头则返回 {@code true}，否则返回 {@code false}
   */
  public static boolean startsWith(final CharSequence str,
      final int startIndex, final CharSequence prefix) {
    return startsWith(str, startIndex, str.length(), prefix);
  }

  /**
   * 检查字符序列在指定范围内是否以指定的前缀开头。
   *
   * @param str 要检查的字符序列
   * @param startIndex 开始检查的位置
   * @param endIndex 结束检查的位置（不包含）
   * @param prefix 前缀
   * @return 如果字符序列在指定范围内以指定前缀开头则返回 {@code true}，否则返回 {@code false}
   */
  public static boolean startsWith(final CharSequence str,
      final int startIndex, final int endIndex, final CharSequence prefix) {
    final int prefixLen = prefix.length();
    if (endIndex - startIndex < prefixLen) {
      return false;
    }
    for (int i = 0; i < prefixLen; ++i) {
      if (str.charAt(startIndex + i) != prefix.charAt(i)) {
        return false;
      }
    }
    return true;
  }

  /**
   * 检查字符序列是否以指定的前缀开头（忽略大小写）。
   *
   * @param str 要检查的字符序列
   * @param prefix 前缀
   * @return 如果字符序列以指定前缀开头（忽略大小写）则返回 {@code true}，否则返回 {@code false}
   */
  public static boolean startsWithIgnoreCase(final CharSequence str,
      final CharSequence prefix) {
    return startsWithIgnoreCase(str, 0, str.length(), prefix);
  }

  /**
   * 检查字符序列从指定位置开始是否以指定的前缀开头（忽略大小写）。
   *
   * @param str 要检查的字符序列
   * @param startIndex 开始检查的位置
   * @param prefix 前缀
   * @return 如果字符序列从指定位置开始以指定前缀开头（忽略大小写）则返回 {@code true}，否则返回 {@code false}
   */
  public static boolean startsWithIgnoreCase(final CharSequence str,
      final int startIndex, final CharSequence prefix) {
    return startsWithIgnoreCase(str, startIndex, str.length(), prefix);
  }

  /**
   * 检查字符序列在指定范围内是否以指定的前缀开头（忽略大小写）。
   *
   * @param str 要检查的字符序列
   * @param startIndex 开始检查的位置
   * @param endIndex 结束检查的位置（不包含）
   * @param prefix 前缀
   * @return 如果字符序列在指定范围内以指定前缀开头（忽略大小写）则返回 {@code true}，否则返回 {@code false}
   */
  public static boolean startsWithIgnoreCase(final CharSequence str,
      final int startIndex, final int endIndex, final CharSequence prefix) {
    final int prefixLen = prefix.length();
    if (endIndex - startIndex < prefixLen) {
      return false;
    }
    for (int i = 0; i < prefixLen; ++i) {
      final char ch1 = str.charAt(startIndex + i);
      final char ch2 = prefix.charAt(i);
      if (Character.toLowerCase(ch1) != Character.toLowerCase(ch2)) {
        return false;
      }
    }
    return true;
  }

  /**
   * 检查字符序列是否以指定的后缀结尾。
   *
   * @param str 要检查的字符序列
   * @param suffix 后缀
   * @return 如果字符序列以指定后缀结尾则返回 {@code true}，否则返回 {@code false}
   */
  public static boolean endsWith(final CharSequence str,
      final CharSequence suffix) {
    return endsWith(str, 0, str.length(), suffix);
  }

  /**
   * 检查字符序列到指定位置是否以指定的后缀结尾。
   *
   * @param str 要检查的字符序列
   * @param endIndex 结束检查的位置（不包含）
   * @param suffix 后缀
   * @return 如果字符序列到指定位置以指定后缀结尾则返回 {@code true}，否则返回 {@code false}
   */
  public static boolean endsWith(final CharSequence str, final int endIndex,
      final CharSequence suffix) {
    return endsWith(str, 0, endIndex, suffix);
  }

  /**
   * 检查字符序列在指定范围内是否以指定的后缀结尾。
   *
   * @param str 要检查的字符序列
   * @param startIndex 开始检查的位置
   * @param endIndex 结束检查的位置（不包含）
   * @param suffix 后缀
   * @return 如果字符序列在指定范围内以指定后缀结尾则返回 {@code true}，否则返回 {@code false}
   */
  public static boolean endsWith(final CharSequence str, final int startIndex,
      final int endIndex, final CharSequence suffix) {
    final int suffixLen = suffix.length();
    if (endIndex - startIndex < suffixLen) {
      return false;
    }
    for (int i = 1; i <= suffixLen; ++i) {
      if (str.charAt(endIndex - i) != suffix.charAt(suffixLen - i)) {
        return false;
      }
    }
    return true;
  }

  /**
   * 检查字符序列是否以指定的后缀结尾（忽略大小写）。
   *
   * @param str 要检查的字符序列
   * @param suffix 后缀
   * @return 如果字符序列以指定后缀结尾（忽略大小写）则返回 {@code true}，否则返回 {@code false}
   */
  public static boolean endsWithIgnoreCase(final CharSequence str,
      final CharSequence suffix) {
    return endsWithIgnoreCase(str, 0, str.length(), suffix);
  }

  /**
   * 检查字符序列到指定位置是否以指定的后缀结尾（忽略大小写）。
   *
   * @param str 要检查的字符序列
   * @param endIndex 结束检查的位置（不包含）
   * @param suffix 后缀
   * @return 如果字符序列到指定位置以指定后缀结尾（忽略大小写）则返回 {@code true}，否则返回 {@code false}
   */
  public static boolean endsWithIgnoreCase(final CharSequence str,
      final int endIndex, final CharSequence suffix) {
    return endsWithIgnoreCase(str, 0, endIndex, suffix);
  }

  /**
   * 检查字符序列在指定范围内是否以指定的后缀结尾（忽略大小写）。
   *
   * @param str 要检查的字符序列
   * @param startIndex 开始检查的位置
   * @param endIndex 结束检查的位置（不包含）
   * @param suffix 后缀
   * @return 如果字符序列在指定范围内以指定后缀结尾（忽略大小写）则返回 {@code true}，否则返回 {@code false}
   */
  public static boolean endsWithIgnoreCase(final CharSequence str,
      final int startIndex, final int endIndex, final CharSequence suffix) {
    final int suffixLen = suffix.length();
    if (endIndex - startIndex < suffixLen) {
      return false;
    }
    for (int i = 1; i <= suffixLen; ++i) {
      final char ch1 = str.charAt(endIndex - i);
      final char ch2 = suffix.charAt(suffixLen - i);
      if (Character.toLowerCase(ch1) != Character.toLowerCase(ch2)) {
        return false;
      }
    }
    return true;
  }
}