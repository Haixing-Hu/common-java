////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text;

import java.io.IOException;

import static ltd.qubit.commons.lang.CharUtils.UPPERCASE_DIGITS;

/**
 * 关于Unicode的实用工具函数。
 *
 * @author 胡海星
 */
public final class Unicode {
  /**
   * 有效ASCII码点的最大值。
   */
  public static final int ASCII_MAX                  = 0x7F;

  /**
   * 有效Latin1码点的最大值。
   */
  public static final int LATIN1_MAX                 = 0xFF;

  /**
   * 有效Unicode码点的最大值。
   */
  public static final int UNICODE_MAX                = 0x10FFFF;

  /**
   * 补充码点的最小值。
   */
  public static final int SUPPLEMENTARY_MIN          = 0x10000;

  /**
   * 高代理码点的最小值。
   */
  public static final int HIGH_SURROGATE_MIN         = 0xD800;

  /**
   * 高代理码点的最大值。
   */
  public static final int HIGH_SURROGATE_MAX         = 0xDBFF;

  /**
   * 用于测试32位无符号整数是否为高代理码点值的掩码值。
   *
   * <p>更精确地说，32位无符号整数ch是高代理码点值当且仅当
   * <pre>
   *    (ch &amp; HIGH_SURROGATE_MASK) == HIGH_SURROGATE_MIN
   * </pre>
   */
  public static final int HIGH_SURROGATE_MASK        = 0xFFFFFC00;

  /**
   * 低代理码点的最小值。
   */
  public static final int LOW_SURROGATE_MIN          = 0xDC00;

  /**
   * 低代理码点的最大值。
   */
  public static final int LOW_SURROGATE_MAX          = 0xDFFF;

  /**
   * 用于测试32位无符号整数是否为低代理码点值的掩码值。
   *
   * <p>更精确地说，32位无符号整数ch是低代理码点值当且仅当
   * <pre>
   *   (ch &amp; LOW_SURROGATE_MASK) == LOW_SURROGATE_MIN
   * </pre>
   */
  public static final int LOW_SURROGATE_MASK         = 0xFFFFFC00;

  /**
   * 代理码点的最小值。
   */
  public static final int SURROGATE_MIN              = HIGH_SURROGATE_MIN;

  /**
   * 用于测试32位无符号整数是否为代理码点值的掩码值。
   *
   * <p>更精确地说，32位无符号整数ch是代理码点值当且仅当
   * <pre>
   *   (ch &amp; SURROGATE_MASK) == SURROGATE_MIN
   * </pre>
   */
  public static final int SURROGATE_MASK             = 0xFFFFF800;

  /**
   * 在组合代理对为单个Unicode码点时高代理需要移位的位数。
   */
  public static final int HIGH_SURROGATE_SHIFT       = 10;

  /**
   * 组合代理对时使用的偏移量。
   *
   * <p>更精确地说，组合代理对的公式是：
   * <pre>
   *  (high &lt;&lt; HIGH_SURROGATE_SHIFT) + low - SURROGATE_COMPOSE_OFFSET
   * </pre>
   */
  public static final int SURROGATE_COMPOSE_OFFSET =
      ((HIGH_SURROGATE_MIN << HIGH_SURROGATE_SHIFT)
          + LOW_SURROGATE_MIN - SUPPLEMENTARY_MIN);

  /**
   * 将补充码点分解为代理对时用于计算高代理的偏移量。
   *
   * <p>更精确地说，分解补充码点时计算高代理的公式是：
   * <pre>
   *   high = (codePoint &gt;&gt; HIGH_SURROGATE_SHIFT) + SURROGATE_DECOMPOSE_OFFSET;
   * </pre>
   * 它等价于
   * <pre>
   *  high = ((codePoint - SUPPLEMENTARY_MIN) &gt;&gt; HIGH_SURROGATE_SHIFT) + HIGH_SURROGATE_MIN;
   * </pre>
   *
   */
  public static final int SURROGATE_DECOMPOSE_OFFSET =
      (HIGH_SURROGATE_MIN - (SUPPLEMENTARY_MIN >> HIGH_SURROGATE_SHIFT));

  /**
   * 将补充码点分解为代理对时用于计算低代理的偏移量。
   *
   * <p>更精确地说，分解补充码点时计算低代理的公式是：
   * <pre>
   *  low = (codePoint &amp; SURROGATE_DECOMPOSE_MASK) | LOW_SURROGATE_MIN;
   * </pre>
   * 它等价于
   * <pre>
   *  low = ((codePoint - SUPPLEMENTARY_MIN) &amp; SURROGATE_DECOMPOSE_MASK) + LOW_SURROGATE_MIN;
   * </pre>
   */
  public static final int SURROGATE_DECOMPOSE_MASK =
      ((1 << HIGH_SURROGATE_SHIFT) - 1);

  /**
   * 为了获取Unicode码点的平面而需要向右移位的位数。
   */
  public static final int PlaneShift                 = 16;

  /**
   * 检查指定的码点是否为有效的ASCII码点。
   *
   * @param codePoint 要检查的码点
   * @return 如果码点是有效的ASCII码点则返回{@code true}，否则返回{@code false}
   */
  public static boolean isValidAscii(final int codePoint) {
    return (codePoint >= 0) && (codePoint <= ASCII_MAX);
  }

  /**
   * 检查指定的码点是否为有效的Latin1码点。
   *
   * @param codePoint 要检查的码点
   * @return 如果码点是有效的Latin1码点则返回{@code true}，否则返回{@code false}
   */
  public static boolean isValidLatin1(final int codePoint) {
    return (codePoint >= 0) && (codePoint <= LATIN1_MAX);
  }

  /**
   * 检查指定的码点是否为有效的Unicode码点。
   *
   * @param codePoint 要检查的码点
   * @return 如果码点是有效的Unicode码点则返回{@code true}，否则返回{@code false}
   */
  public static boolean isValidUnicode(final int codePoint) {
    return (codePoint >= 0) && (codePoint <= UNICODE_MAX);
  }

  /**
   * 检查指定的码点是否属于基本多文种平面（BMP）。
   *
   * @param codePoint 要检查的码点
   * @return 如果码点属于基本多文种平面则返回{@code true}，否则返回{@code false}
   */
  public static boolean isBmp(final int codePoint) {
    return (codePoint >= 0) && (codePoint < SUPPLEMENTARY_MIN);
  }

  /**
   * 检查指定的码点是否为补充码点。
   *
   * @param codePoint 要检查的码点
   * @return 如果码点是补充码点则返回{@code true}，否则返回{@code false}
   */
  public static boolean isSupplementary(final int codePoint) {
    return (codePoint >= SUPPLEMENTARY_MIN) && (codePoint <= UNICODE_MAX);
  }

  /**
   * 检查指定的码点是否为高代理码点。
   *
   * @param codePoint 要检查的码点
   * @return 如果码点是高代理码点则返回{@code true}，否则返回{@code false}
   */
  public static boolean isHighSurrogate(final int codePoint) {
    return (codePoint & HIGH_SURROGATE_MASK) == HIGH_SURROGATE_MIN;
  }

  /**
   * 检查指定的码点是否为低代理码点。
   *
   * @param codePoint 要检查的码点
   * @return 如果码点是低代理码点则返回{@code true}，否则返回{@code false}
   */
  public static boolean isLowSurrogate(final int codePoint) {
    return (codePoint & LOW_SURROGATE_MASK) == LOW_SURROGATE_MIN;
  }

  /**
   * 检查指定的码点是否为代理码点。
   *
   * @param codePoint 要检查的码点
   * @return 如果码点是代理码点则返回{@code true}，否则返回{@code false}
   */
  public static boolean isSurrogate(final int codePoint) {
    return (codePoint & SURROGATE_MASK) == SURROGATE_MIN;
  }

  /**
   * 检查两个码点是否组成有效的代理对。
   *
   * @param high 高代理码点
   * @param low 低代理码点
   * @return 如果两个码点组成有效的代理对则返回{@code true}，否则返回{@code false}
   */
  public static boolean isSurrogatePair(final int high, final int low) {
    return isHighSurrogate(high) && isLowSurrogate(low);
  }

  /**
   * 将代理对组合为单个Unicode码点。
   *
   * @param high 高代理码点
   * @param low 低代理码点
   * @return 组合后的Unicode码点
   */
  public static int composeSurrogatePair(final int high, final int low) {
    assert (isHighSurrogate(high) && isLowSurrogate(low));
    return (high << HIGH_SURROGATE_SHIFT) + low - SURROGATE_COMPOSE_OFFSET;
  }

  /**
   * 分解补充码点以获取高代理码点。
   *
   * @param codePoint 要分解的补充码点
   * @return 高代理码点
   */
  public static int decomposeHighSurrogate(final int codePoint) {
    assert (isSupplementary(codePoint));
    return (codePoint >> HIGH_SURROGATE_SHIFT) + SURROGATE_DECOMPOSE_OFFSET;
  }

  /**
   * 分解补充码点以获取低代理码点。
   *
   * @param codePoint 要分解的补充码点
   * @return 低代理码点
   */
  public static int decomposeLowSurrogate(final int codePoint) {
    assert (isSupplementary(codePoint));
    return (codePoint & SURROGATE_DECOMPOSE_MASK) | LOW_SURROGATE_MIN;
  }

  /**
   * 将码点转义为Unicode转义序列并追加到指定的可追加对象中。
   *
   * @param codePoint 要转义的码点
   * @param appendable 要追加到的可追加对象
   * @throws IOException 如果发生I/O错误
   */
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