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
 * Provides utility functions for character sequences.
 *
 * @author Haixing Hu
 */
public final class CharSequenceUtils {

  public static boolean startsWith(final CharSequence str,
      final CharSequence prefix) {
    return startsWith(str, 0, str.length(), prefix);
  }

  public static boolean startsWith(final CharSequence str,
      final int startIndex, final CharSequence prefix) {
    return startsWith(str, startIndex, str.length(), prefix);
  }

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

  public static boolean startsWithIgnoreCase(final CharSequence str,
      final CharSequence prefix) {
    return startsWithIgnoreCase(str, 0, str.length(), prefix);
  }

  public static boolean startsWithIgnoreCase(final CharSequence str,
      final int startIndex, final CharSequence prefix) {
    return startsWithIgnoreCase(str, startIndex, str.length(), prefix);
  }

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

  public static boolean endsWith(final CharSequence str,
      final CharSequence suffix) {
    return endsWith(str, 0, str.length(), suffix);
  }

  public static boolean endsWith(final CharSequence str, final int endIndex,
      final CharSequence suffix) {
    return endsWith(str, 0, endIndex, suffix);
  }

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

  public static boolean endsWithIgnoreCase(final CharSequence str,
      final CharSequence suffix) {
    return endsWithIgnoreCase(str, 0, str.length(), suffix);
  }

  public static boolean endsWithIgnoreCase(final CharSequence str,
      final int endIndex, final CharSequence suffix) {
    return endsWithIgnoreCase(str, 0, endIndex, suffix);
  }

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