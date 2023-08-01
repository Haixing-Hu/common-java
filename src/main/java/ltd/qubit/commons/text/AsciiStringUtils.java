////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text;

import javax.annotation.concurrent.ThreadSafe;

import ltd.qubit.commons.lang.StringUtils;

/**
 * Provides functions for manipulating the ASCII strings (represented by a
 * byte[]).
 *
 * @author Haixing Hu
 */
@ThreadSafe
public final class AsciiStringUtils {

  /**
   * The current value when an element is not found in a list or array:
   * {@code -1}. This value is returned by methods in this class and can also be
   * used in comparisons with values returned by various method from
   * {@link java.util.List}.
   */
  public static final int INDEX_NOT_FOUND = -1;

  public static String toString(final byte[] str, final int startIndex,
      final int endIndex) {
    final int theStart = Math.max(startIndex, 0);
    final int theEnd = Math.min(endIndex, str.length);
    if (theStart >= theEnd) {
      return StringUtils.EMPTY;
    }
    final StringBuilder builder = new StringBuilder();
    for (int i = theStart; i < theEnd; ++i) {
      builder.append((char) (str[i] & 0xFF));
    }
    return builder.toString();
  }

  public static boolean equals(final byte[] str1, final int startIndex1,
      final int endIndex1, final byte[] str2, final int startIndex2,
      final int endIndex2) {
    final int theStart1 = Math.max(startIndex1, 0);
    final int theEnd1 = Math.min(endIndex1, str1.length);
    final int theStart2 = Math.max(startIndex2, 0);
    final int theEnd2 = Math.min(endIndex2, str2.length);
    if ((theEnd1 - theStart1) != (theEnd2 - theStart2)) {
      return false;
    }
    for (int i = theStart1, j = theStart2; i < theEnd1; ++i, ++j) {
      if (str1[i] != str2[j]) {
        return false;
      }
    }
    return true;
  }

  public static boolean equalsIgnoreCase(final byte[] str1, final int startIndex1,
      final int endIndex1, final byte[] str2, final int startIndex2,
      final int endIndex2) {
    final int theStart1 = Math.max(startIndex1, 0);
    final int theEnd1 = Math.min(endIndex1, str1.length);
    final int theStart2 = Math.max(startIndex2, 0);
    final int theEnd2 = Math.min(endIndex2, str2.length);
    if ((theEnd1 - theStart1) != (theEnd2 - theStart2)) {
      return false;
    }
    for (int i = theStart1, j = theStart2; i < theEnd1; ++i, ++j) {
      if (! Ascii.equalsIgnoreCase(str1[i], str2[j])) {
        return false;
      }
    }
    return true;
  }

  public static int indexOf(final byte[] str, final int startIndex,
      final int endIndex, final byte ch) {
    final int theStart = Math.max(startIndex, 0);
    final int theEnd = Math.min(endIndex, str.length);
    for (int i = theStart; i < theEnd; ++i) {
      if (str[i] == ch) {
        return i;
      }
    }
    return INDEX_NOT_FOUND;
  }

  public static int indexOf(final byte[] str1, final int startIndex1,
      final int endIndex1, final byte[] str2, final int startIndex2,
      final int endIndex2) {
    final int theStart1 = Math.max(startIndex1, 0);
    final int theEnd1 = Math.min(endIndex1, str1.length);
    final int theStart2 = Math.max(startIndex2, 0);
    final int theEnd2 = Math.min(endIndex2, str2.length);
    final int len1 = theEnd1 - theStart1;
    if (len1 <= 0) {
      return INDEX_NOT_FOUND;
    }
    final int len2 = theEnd2 - theStart2;
    if (len2 <= 0) {
      return theStart1;
    }
    if (len1 < len2) {
      return INDEX_NOT_FOUND;
    }
    final byte first2 = str2[theStart2];
    final int newEnd1 = theEnd1 - (len2 - 1);
    for (int i = theStart1; i < newEnd1; ++i) {
      if (str1[i] == first2) {
        boolean match = true;
        for (int j = i + 1, k = theStart2 + 1; k < theEnd2; ++j, ++k) {
          assert (j < theEnd1);
          if (str1[j] != str2[k]) {
            match = false;
            break;
          }
        }
        if (match) {
          return i;
        }
      }
    }
    return INDEX_NOT_FOUND;
  }

  public static int indexOfIgnoreCase(final byte[] str, final int startIndex,
      final int endIndex, final byte ch) {
    final int theStart = Math.max(startIndex, 0);
    final int theEnd = Math.min(endIndex, str.length);
    final int target = Ascii.toLowerCase(ch);
    for (int i = theStart; i < theEnd; ++i) {
      if (Ascii.toLowerCase(str[i]) ==  target) {
        return i;
      }
    }
    return INDEX_NOT_FOUND;
  }

  public static int indexOfIgnoreCase(final byte[] str1, final int startIndex1,
      final int endIndex1, final byte[] str2, final int startIndex2,
      final int endIndex2) {
    final int theStart1 = Math.max(startIndex1, 0);
    final int theEnd1 = Math.min(endIndex1, str1.length);
    final int theStart2 = Math.max(startIndex2, 0);
    final int theEnd2 = Math.min(endIndex2, str2.length);
    final int len1 = theEnd1 - theStart1;
    if (len1 <= 0) {
      return INDEX_NOT_FOUND;
    }
    final int len2 = theEnd2 - theStart2;
    if (len2 <= 0) {
      return theStart1;
    }
    if (len1 < len2) {
      return INDEX_NOT_FOUND;
    }
    final byte first2 = Ascii.toLowerCase(str2[theStart2]);
    final int newEnd1 = theEnd1 - (len2 - 1);
    for (int i = theStart1; i < newEnd1; ++i) {
      if (Ascii.toLowerCase(str1[i]) == first2) {
        boolean match = true;
        for (int j = i + 1, k = theStart2 + 1; k < theEnd2; ++j, ++k) {
          assert (j < theEnd1);
          if (! Ascii.equalsIgnoreCase(str1[j], str2[k])) {
            match = false;
            break;
          }
        }
        if (match) {
          return i;
        }
      }
    }
    return INDEX_NOT_FOUND;
  }

  public static int indexOfAny(final byte[] str, final int startIndex,
      final int endIndex, final byte ... chars) {
    final int theStart = Math.max(startIndex, 0);
    final int theEnd = Math.min(endIndex, str.length);
    for (int i = theStart; i < theEnd; ++i) {
      final byte ch = str[i];
      for (final byte aChar : chars) {
        if (ch == aChar) {
          return i;
        }
      }
    }
    return INDEX_NOT_FOUND;
  }

  public static int indexOfAnyIgnoreCase(final byte[] str, final int startIndex,
      final int endIndex, final byte ...  chars) {
    final int theStart = Math.max(startIndex, 0);
    final int theEnd = Math.min(endIndex, str.length);
    for (int i = theStart; i < theEnd; ++i) {
      final byte ch = Ascii.toLowerCase(str[i]);
      for (final byte theChar : chars) {
        if (ch == Ascii.toLowerCase(theChar)) {
          return i;
        }
      }
    }
    return INDEX_NOT_FOUND;
  }

  public static int indexOfAnyBut(final byte[] str, final int startIndex,
      final int endIndex, final byte ... chars) {
    final int theStart = Math.max(startIndex, 0);
    final int theEnd = Math.min(endIndex, str.length);
    for (int i = theStart; i < theEnd; ++i) {
      final byte ch = str[i];
      boolean found = false;
      for (final byte aChar : chars) {
        if (ch == aChar) {
          found = true;
          break;
        }
      }
      if (! found) {
        return i;
      }
    }
    return INDEX_NOT_FOUND;
  }

  public static int indexOfAnyButIgnoreCase(final byte[] str, final int startIndex,
      final int endIndex, final byte ...  chars) {
    final int theStart = Math.max(startIndex, 0);
    final int theEnd = Math.min(endIndex, str.length);
    for (int i = theStart; i < theEnd; ++i) {
      final byte ch = Ascii.toLowerCase(str[i]);
      boolean found = false;
      for (final byte theChar : chars) {
        if (ch == Ascii.toLowerCase(theChar)) {
          found = true;
          break;
        }
      }
      if (! found) {
        return i;
      }
    }
    return INDEX_NOT_FOUND;
  }

  public static int indexOfWhitespace(final byte[] str, final int startIndex,
      final int endIndex) {
    final int theStart = Math.max(startIndex, 0);
    final int theEnd = Math.min(endIndex, str.length);
    for (int i = theStart; i < theEnd; ++i) {
      if (Ascii.isWhitespace(str[i])) {
        return i;
      }
    }
    return INDEX_NOT_FOUND;
  }

  public static int indexOfNonWhitespace(final byte[] str, final int startIndex,
      final int endIndex) {
    final int theStart = Math.max(startIndex, 0);
    final int theEnd = Math.min(endIndex, str.length);
    for (int i = theStart; i < theEnd; ++i) {
      if (! Ascii.isWhitespace(str[i])) {
        return i;
      }
    }
    return INDEX_NOT_FOUND;
  }

  public static int indexOfLetter(final byte[] str, final int startIndex,
      final int endIndex) {
    final int theStart = Math.max(startIndex, 0);
    final int theEnd = Math.min(endIndex, str.length);
    for (int i = theStart; i < theEnd; ++i) {
      if (Ascii.isLetter(str[i])) {
        return i;
      }
    }
    return INDEX_NOT_FOUND;
  }

  public static int indexOfNonLetter(final byte[] str, final int startIndex,
      final int endIndex) {
    final int theStart = Math.max(startIndex, 0);
    final int theEnd = Math.min(endIndex, str.length);
    for (int i = theStart; i < theEnd; ++i) {
      if (! Ascii.isLetter(str[i])) {
        return i;
      }
    }
    return INDEX_NOT_FOUND;
  }

  public static int indexOfUppercaseLetter(final byte[] str, final int startIndex,
      final int endIndex) {
    final int theStart = Math.max(startIndex, 0);
    final int theEnd = Math.min(endIndex, str.length);
    for (int i = theStart; i < theEnd; ++i) {
      if (Ascii.isUpperCaseLetter(str[i])) {
        return i;
      }
    }
    return INDEX_NOT_FOUND;
  }

  public static int indexOfNonUppercaseLetter(final byte[] str, final int startIndex,
      final int endIndex) {
    final int theStart = Math.max(startIndex, 0);
    final int theEnd = Math.min(endIndex, str.length);
    for (int i = theStart; i < theEnd; ++i) {
      if (! Ascii.isUpperCaseLetter(str[i])) {
        return i;
      }
    }
    return INDEX_NOT_FOUND;
  }

  public static int indexOfLowercaseLetter(final byte[] str, final int startIndex,
      final int endIndex) {
    final int theStart = Math.max(startIndex, 0);
    final int theEnd = Math.min(endIndex, str.length);
    for (int i = theStart; i < theEnd; ++i) {
      if (Ascii.isLowerCaseLetter(str[i])) {
        return i;
      }
    }
    return INDEX_NOT_FOUND;
  }

  public static int indexOfNonLowercaseLetter(final byte[] str, final int startIndex,
      final int endIndex) {
    final int theStart = Math.max(startIndex, 0);
    final int theEnd = Math.min(endIndex, str.length);
    for (int i = theStart; i < theEnd; ++i) {
      if (! Ascii.isLowerCaseLetter(str[i])) {
        return i;
      }
    }
    return INDEX_NOT_FOUND;
  }

  public static int indexOfDigit(final byte[] str, final int startIndex,
      final int endIndex) {
    final int theStart = Math.max(startIndex, 0);
    final int theEnd = Math.min(endIndex, str.length);
    for (int i = theStart; i < theEnd; ++i) {
      if (Ascii.isDigit(str[i])) {
        return i;
      }
    }
    return INDEX_NOT_FOUND;
  }

  public static int indexOfNonDigit(final byte[] str, final int startIndex,
      final int endIndex) {
    final int theStart = Math.max(startIndex, 0);
    final int theEnd = Math.min(endIndex, str.length);
    for (int i = theStart; i < theEnd; ++i) {
      if (! Ascii.isDigit(str[i])) {
        return i;
      }
    }
    return INDEX_NOT_FOUND;
  }

  public static int lastIndexOf(final byte[] str, final int startIndex,
      final int endIndex, final byte ch) {
    final int theStart = Math.max(startIndex, 0);
    final int theEnd = Math.min(endIndex, str.length);
    for (int i = theEnd - 1; i >= theStart; --i) {
      if (str[i] == ch) {
        return i;
      }
    }
    return INDEX_NOT_FOUND;
  }

  public static int lastIndexOf(final byte[] str1, final int startIndex1,
      final int endIndex1, final byte[] str2, final int startIndex2,
      final int endIndex2) {
    final int theStart1 = Math.max(startIndex1, 0);
    final int theEnd1 = Math.min(endIndex1, str1.length);
    final int theStart2 = Math.max(startIndex2, 0);
    final int theEnd2 = Math.min(endIndex2, str2.length);
    final int len1 = theEnd1 - theStart1;
    if (len1 <= 0) {
      return INDEX_NOT_FOUND;
    }
    final int len2 = theEnd2 - theStart2;
    if (len2 <= 0) {
      return theEnd1 - 1;
    }
    if (len1 < len2) {
      return INDEX_NOT_FOUND;
    }
    final byte last2 = str2[theEnd2 - 1];
    final int newStart1 = theStart1 + (len2 - 1);
    for (int i = theEnd1 - 1; i >= newStart1; --i) {
      if (str1[i] == last2) {
        boolean match = true;
        for (int j = i - 1, k = theEnd2 - 2; k >= theStart2; --j, --k) {
          assert (j >= theStart1);
          if (str1[j] != str2[k]) {
            match = false;
            break;
          }
        }
        if (match) {
          return i;
        }
      }
    }
    return INDEX_NOT_FOUND;
  }

  public static int lastIndexOfIgnoreCase(final byte[] str, final int startIndex,
      final int endIndex, final byte ch) {
    final int theStart = Math.max(startIndex, 0);
    final int theEnd = Math.min(endIndex, str.length);
    final byte target = Ascii.toLowerCase(ch);
    for (int i = theEnd - 1; i >= theStart; --i) {
      if (Ascii.toLowerCase(str[i]) ==  target) {
        return i;
      }
    }
    return INDEX_NOT_FOUND;
  }

  public static int lastIndexOfIgnoreCase(final byte[] str1, final int startIndex1,
      final int endIndex1, final byte[] str2, final int startIndex2,
      final int endIndex2) {
    final int theStart1 = Math.max(startIndex1, 0);
    final int theEnd1 = Math.min(endIndex1, str1.length);
    final int theStart2 = Math.max(startIndex2, 0);
    final int theEnd2 = Math.min(endIndex2, str2.length);
    final int len1 = theEnd1 - theStart1;
    if (len1 <= 0) {
      return INDEX_NOT_FOUND;
    }
    final int len2 = theEnd2 - theStart2;
    if (len2 <= 0) {
      return theEnd1 - 1;
    }
    if (len1 < len2) {
      return INDEX_NOT_FOUND;
    }
    final byte last2 = Ascii.toLowerCase(str2[theEnd2 - 1]);
    final int newStart1 = theStart1 + (len2 - 1);
    for (int i = theEnd1 - 1; i >= newStart1; --i) {
      if (Ascii.toLowerCase(str1[i]) == last2) {
        boolean match = true;
        for (int j = i - 1, k = theEnd2 - 2; k >= theStart2; --j, --k) {
          assert (j >= theStart1);
          if (! Ascii.equalsIgnoreCase(str1[j], str2[k])) {
            match = false;
            break;
          }
        }
        if (match) {
          return i;
        }
      }
    }
    return INDEX_NOT_FOUND;
  }

  public static boolean startsWith(final byte[] str, final int startIndex,
      final int endIndex, final byte ch) {
    final int theStart = Math.max(startIndex, 0);
    final int theEnd = Math.min(endIndex, str.length);
    return (theStart < theEnd) && (str[theStart] == ch);
  }

  public static boolean startsWith(final byte[] str1, final int startIndex1,
      final int endIndex1, final byte[] str2, final int startIndex2,
      final int endIndex2) {
    final int theStart1 = Math.max(startIndex1, 0);
    final int theEnd1 = Math.min(endIndex1, str1.length);
    final int theStart2 = Math.max(startIndex2, 0);
    final int theEnd2 = Math.min(endIndex2, str2.length);
    if ((theEnd1 - theStart1) < (theEnd2 - theStart2)) {
      return false;
    }
    for (int i1 = theStart1, i2 = theStart2; i2 < theEnd2; ++i1, ++i2) {
      if (str1[i1] != str2[i2]) {
        return false;
      }
    }
    return true;
  }

  public static boolean startsWithIgnoreCase(final byte[] str, final int startIndex,
      final int endIndex, final byte ch) {
    final int theStart = Math.max(startIndex, 0);
    final int theEnd = Math.min(endIndex, str.length);
    return (theStart < theEnd) && Ascii.equalsIgnoreCase(str[theStart], ch);
  }

  public static boolean startsWithIgnoreCase(final byte[] str1,
      final int startIndex1, final int endIndex1, final byte[] str2,
      final int startIndex2, final int endIndex2) {
    final int theStart1 = Math.max(startIndex1, 0);
    final int theEnd1 = Math.min(endIndex1, str1.length);
    final int theStart2 = Math.max(startIndex2, 0);
    final int theEnd2 = Math.min(endIndex2, str2.length);
    if ((theEnd1 - theStart1) < (theEnd2 - theStart2)) {
      return false;
    }
    for (int i1 = theStart1, i2 = theStart2; i2 < theEnd2; ++i1, ++i2) {
      if (! Ascii.equalsIgnoreCase(str1[i1], str2[i2])) {
        return false;
      }
    }
    return true;
  }

  public static boolean startsWithAny(final byte[] str, final int startIndex,
      final int endIndex, final byte ...  chars) {
    final int theStart = Math.max(startIndex, 0);
    final int theEnd = Math.min(endIndex, str.length);
    if (theStart < theEnd) {
      final byte first = str[startIndex];
      for (final byte aChar : chars) {
        if (first == aChar) {
          return true;
        }
      }
    }
    return false;
  }

  public static boolean startsWithAnyIgnoreCase(final byte[] str,
      final int startIndex, final int endIndex, final byte ...  chars) {
    final int theStart = Math.max(startIndex, 0);
    final int theEnd = Math.min(endIndex, str.length);
    if (theStart < theEnd) {
      final byte first = Ascii.toLowerCase(str[theStart]);
      for (final byte target : chars) {
        if (first == Ascii.toLowerCase(target)) {
          return true;
        }
      }
    }
    return false;
  }

  public static boolean endsWith(final byte[] str, final int startIndex,
      final int endIndex, final byte ch) {
    final int theStart = Math.max(startIndex, 0);
    final int theEnd = Math.min(endIndex, str.length);
    return (theStart < theEnd) && (str[theEnd - 1] == ch);
  }

  public static boolean endsWith(final byte[] str1, final int startIndex1,
      final int endIndex1, final byte[] str2, final int startIndex2,
      final int endIndex2) {
    final int theStart1 = Math.max(startIndex1, 0);
    final int theEnd1 = Math.min(endIndex1, str1.length);
    final int theStart2 = Math.max(startIndex2, 0);
    final int theEnd2 = Math.min(endIndex2, str2.length);
    if ((theEnd1 - theStart1) < (theEnd2 - theStart2)) {
      return false;
    }
    for (int i1 = theEnd1 - 1, i2 = theEnd2 - 1; i2 >= theStart2; --i1, --i2) {
      if (str1[i1] != str2[i2]) {
        return false;
      }
    }
    return true;
  }

  public static boolean endsWithIgnoreCase(final byte[] str, final int startIndex,
      final int endIndex, final byte ch) {
    final int theStart = Math.max(startIndex, 0);
    final int theEnd = Math.min(endIndex, str.length);
    return (theStart < theEnd) && Ascii.equalsIgnoreCase(str[theEnd - 1], ch);
  }

  public static boolean endsWithIgnoreCase(final byte[] str1, final int startIndex1,
      final int endIndex1, final byte[] str2, final int startIndex2,
      final int endIndex2) {
    final int theStart1 = Math.max(startIndex1, 0);
    final int theEnd1 = Math.min(endIndex1, str1.length);
    final int theStart2 = Math.max(startIndex2, 0);
    final int theEnd2 = Math.min(endIndex2, str2.length);
    if ((theEnd1 - theStart1) < (theEnd2 - theStart2)) {
      return false;
    }
    for (int i1 = theEnd1 - 1, i2 = theEnd2 - 1; i2 >= theStart2; --i1, --i2) {
      if (! Ascii.equalsIgnoreCase(str1[i1], str2[i2])) {
        return false;
      }
    }
    return true;
  }

  public static boolean endsWithAny(final byte[] str, final int startIndex,
      final int endIndex, final byte ...  chars) {
    final int theStart = Math.max(startIndex, 0);
    final int theEnd = Math.min(endIndex, str.length);
    if (theStart < theEnd) {
      final byte last = str[theEnd - 1];
      for (final byte target : chars) {
        if (last == target) {
          return true;
        }
      }
    }
    return false;
  }

  public static boolean endsWithAnyIgnoreCase(final byte[] str, final int startIndex,
      final int endIndex, final byte ...  chars) {
    final int theStart = Math.max(startIndex, 0);
    final int theEnd = Math.min(endIndex, str.length);
    if (theStart < theEnd) {
      final byte last = Ascii.toLowerCase(str[theEnd - 1]);
      for (final byte target : chars) {
        if (last == Ascii.toLowerCase(target)) {
          return true;
        }
      }
    }
    return false;
  }
}
