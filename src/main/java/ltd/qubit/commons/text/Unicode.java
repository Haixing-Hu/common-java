////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text;

import java.io.IOException;

import static ltd.qubit.commons.lang.CharUtils.UPPERCASE_DIGITS;

/**
 * Utility functions about the Unicode.
 *
 * @author Haixing Hu
 */
public final class Unicode {
  /**
   * The maximum value of a valid ASCII code point.
   */
  public static final int ASCII_MAX                  = 0x7F;

  /**
   * The maximum value of a valid Latin1 code point.
   */
  public static final int LATIN1_MAX                 = 0xFF;

  /**
   * The maximum value of a valid Unicode code point.
   */
  public static final int UNICODE_MAX                = 0x10FFFF;

  /**
   * Minimum value of a supplementary code point.
   */
  public static final int SUPPLEMENTARY_MIN          = 0x10000;

  /**
   * Minimum value of a high surrogate code point.
   */
  public static final int HIGH_SURROGATE_MIN         = 0xD800;

  /**
   * Maximum value of a high surrogate code point.
   */
  public static final int HIGH_SURROGATE_MAX         = 0xDBFF;

  /**
   * The mask value used to test whether a 32-bit unsigned integer is a high
   * surrogate code point value.
   *
   * <p>More precisely, a 32-bit unsigned integer ch is a high surrogate code point
   * value if and only if
   * <pre>
   *    (ch &amp; HIGH_SURROGATE_MASK) == HIGH_SURROGATE_MIN
   * </pre>
   */
  public static final int HIGH_SURROGATE_MASK        = 0xFFFFFC00;

  /**
   * Minimum value of a low surrogate code point.
   */
  public static final int LOW_SURROGATE_MIN          = 0xDC00;

  /**
   * Maximum value of a low surrogate code point.
   */
  public static final int LOW_SURROGATE_MAX          = 0xDFFF;

  /**
   * The mask value used to test whether a 32-bit unsigned integer is a low
   * surrogate code point value.
   *
   * <p>More precisely, a 32-bit unsigned integer ch is a low surrogate code point
   * value if and only if
   * <pre>
   *   (ch &amp; LOW_SURROGATE_MASK) == LOW_SURROGATE_MIN
   * </pre>
   */
  public static final int LOW_SURROGATE_MASK         = 0xFFFFFC00;

  /**
   * Minimum value of a surrogate code point.
   */
  public static final int SURROGATE_MIN              = HIGH_SURROGATE_MIN;

  /**
   * The mask value used to test whether a 32-bit unsigned integer is a
   * surrogate code point value.
   *
   * <p>More precisely, a 32-bit unsigned integer ch is a surrogate code point
   * value if and only if
   * <pre>
   *   (ch &amp; SURROGATE_MASK) == SURROGATE_MIN
   * </pre>
   */
  public static final int SURROGATE_MASK             = 0xFFFFF800;

  /**
   * The number of bits need to shift by the high surrogate when compose the
   * surrogate pair into a single Unicode code point.
   */
  public static final int HIGH_SURROGATE_SHIFT       = 10;

  /**
   * The offset used when composing surrogate pair.
   *
   * <p>More precisely, the formula to compose a surrogate pair is:
   * <pre>
   *  (high &lt;&lt; HIGH_SURROGATE_SHIFT) + low - SURROGATE_COMPOSE_OFFSET
   * </pre>
   */
  public static final int SURROGATE_COMPOSE_OFFSET =
      ((HIGH_SURROGATE_MIN << HIGH_SURROGATE_SHIFT)
          + LOW_SURROGATE_MIN - SUPPLEMENTARY_MIN);

  /**
   * The offset used to calculate the high surrogate when decomposing a
   * supplementary code point into surrogate pair.
   *
   * <p>More precisely, the formula to calculate the high surrogate when
   * decomposing a supplementary code point is:
   * <pre>
   *   high = (codePoint &gt;&gt; HIGH_SURROGATE_SHIFT) + SURROGATE_DECOMPOSE_OFFSET;
   * </pre>
   * which is equivalent to
   * <pre>
   *  high = ((codePoint - SUPPLEMENTARY_MIN) &gt;&gt; HIGH_SURROGATE_SHIFT) + HIGH_SURROGATE_MIN;
   * </pre>
   *
   */
  public static final int SURROGATE_DECOMPOSE_OFFSET =
      (HIGH_SURROGATE_MIN - (SUPPLEMENTARY_MIN >> HIGH_SURROGATE_SHIFT));

  /**
   * The offset used to calculate the low surrogate when decomposing a
   * supplementary code point into surrogate pair.
   *
   * <p>More precisely, the formula to calculate the low surrogate when decomposing
   * a supplementary code point is:
   * <pre>
   *  low = (codePoint &amp; SURROGATE_DECOMPOSE_MASK) | LOW_SURROGATE_MIN;
   * </pre>
   * which is equivalent to
   * <pre>
   *  low = ((codePoint - SUPPLEMENTARY_MIN) &amp; SURROGATE_DECOMPOSE_MASK) + LOW_SURROGATE_MIN;
   * </pre>
   */
  public static final int SURROGATE_DECOMPOSE_MASK =
      ((1 << HIGH_SURROGATE_SHIFT) - 1);

  /**
   * The number of bits need to be shifted to the right in order to get the
   * plane of a Unicode code point.
   */
  public static final int PlaneShift                 = 16;

  public static boolean isValidAscii(final int codePoint) {
    return (codePoint >= 0) && (codePoint <= ASCII_MAX);
  }

  public static boolean isValidLatin1(final int codePoint) {
    return (codePoint >= 0) && (codePoint <= LATIN1_MAX);
  }

  public static boolean isValidUnicode(final int codePoint) {
    return (codePoint >= 0) && (codePoint <= UNICODE_MAX);
  }

  public static boolean isBmp(final int codePoint) {
    return (codePoint >= 0) && (codePoint < SUPPLEMENTARY_MIN);
  }

  public static boolean isSupplementary(final int codePoint) {
    return (codePoint >= SUPPLEMENTARY_MIN) && (codePoint <= UNICODE_MAX);
  }

  public static boolean isHighSurrogate(final int codePoint) {
    return (codePoint & HIGH_SURROGATE_MASK) == HIGH_SURROGATE_MIN;
  }

  public static boolean isLowSurrogate(final int codePoint) {
    return (codePoint & LOW_SURROGATE_MASK) == LOW_SURROGATE_MIN;
  }

  public static boolean isSurrogate(final int codePoint) {
    return (codePoint & SURROGATE_MASK) == SURROGATE_MIN;
  }

  public static boolean isSurrogatePair(final int high, final int low) {
    return isHighSurrogate(high) && isLowSurrogate(low);
  }

  public static int composeSurrogatePair(final int high, final int low) {
    assert (isHighSurrogate(high) && isLowSurrogate(low));
    return (high << HIGH_SURROGATE_SHIFT) + low - SURROGATE_COMPOSE_OFFSET;
  }

  public static int decomposeHighSurrogate(final int codePoint) {
    assert (isSupplementary(codePoint));
    return (codePoint >> HIGH_SURROGATE_SHIFT) + SURROGATE_DECOMPOSE_OFFSET;
  }

  public static int decomposeLowSurrogate(final int codePoint) {
    assert (isSupplementary(codePoint));
    return (codePoint & SURROGATE_DECOMPOSE_MASK) | LOW_SURROGATE_MIN;
  }

  public static void escape(final int codePoint, final Appendable appendable)
      throws IOException {
    // stop checkstyle: MagicNumber
    appendable.append("\\u");
    if (codePoint > 0xFFFF) {
      final int[] v = new int[4];
      v[0] = (codePoint >>> 28) & 0xF;
      v[1] = (codePoint >>> 24) & 0xF;
      v[2] = (codePoint >>> 20) & 0xF;
      v[3] = (codePoint >>> 16) & 0xF;
      // skip all leading zeros
      int i = 0;
      while (i < 4 && v[i] == 0) {
        ++i;
      }
      // output the remained HEX digits
      for ( ; i < 4; ++i) {
        appendable.append(UPPERCASE_DIGITS[v[i]]);
      }
    }
    // the last 4 HEX digits should always be output
    appendable.append(UPPERCASE_DIGITS[(codePoint >>> 12) & 0xF]);
    appendable.append(UPPERCASE_DIGITS[(codePoint >>> 8) & 0xF]);
    appendable.append(UPPERCASE_DIGITS[(codePoint >>> 4) & 0xF]);
    appendable.append(UPPERCASE_DIGITS[codePoint & 0x0F]);
    // resume checkstyle: MagicNumber
  }
}