////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.translate;

import ltd.qubit.commons.text.Unicode;
import ltd.qubit.commons.text.Utf16;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

import java.io.IOException;

/**
 * Translates code points to their Unicode escaped value suitable for Java source.
 *
 * @author Haixing Hu
 */
public class JavaUnicodeEscaper extends UnicodeEscaper {

  /**
   * Constructs a {@code JavaUnicodeEscaper} above the specified value (exclusive).
   *
   * @param codePoint
   *     above which to escape.
   * @return
   *     The newly created {@code UnicodeEscaper} instance.
   */
  public static JavaUnicodeEscaper above(final int codePoint) {
    return outsideOf(0, codePoint);
  }

  /**
   * Constructs a {@code JavaUnicodeEscaper} below the specified value (exclusive).
   *
   * @param codePoint
   *     below which to escape.
   * @return
   *     The newly created {@code UnicodeEscaper} instance.
   */
  public static JavaUnicodeEscaper below(final int codePoint) {
    return outsideOf(codePoint, Unicode.UNICODE_MAX);
  }

  /**
   * Constructs a {@code JavaUnicodeEscaper} between the specified values (inclusive).
   *
   * @param codePointLow
   *      above which to escape.
   * @param codePointHigh
   *      below which to escape.
   * @return
   *     The newly created {@code UnicodeEscaper} instance.
   */
  public static JavaUnicodeEscaper between(final int codePointLow, final int codePointHigh) {
    return new JavaUnicodeEscaper(codePointLow, codePointHigh, true);
  }

  /**
   * Constructs a {@code JavaUnicodeEscaper} outside of the specified values (exclusive).
   *
   * @param codePointLow
   *     below which to escape.
   * @param codePointHigh
   *     above which to escape.
   * @return
   *     The newly created {@code UnicodeEscaper} instance.
   */
  public static JavaUnicodeEscaper outsideOf(final int codePointLow, final int codePointHigh) {
    return new JavaUnicodeEscaper(codePointLow, codePointHigh, false);
  }

  /**
   * Constructs a {@code JavaUnicodeEscaper} for the specified range.
   *
   * <p>This is the underlying method for the other constructors/builders.
   * The {@code below} and {@code above} boundaries are inclusive when
   * {@code between} is {@code true} and exclusive when it is {@code false}.</p>
   *
   * @param below
   *     the lowest code point boundary.
   * @param above
   *     the highest code point boundary.
   * @param between
   *     whether to escape between the boundaries or outside them.
   */
  public JavaUnicodeEscaper(final int below, final int above, final boolean between) {
    super(below, above, between);
  }

  public JavaUnicodeEscaper(final JavaUnicodeEscaper other) {
    super(other);
  }

  @Override
  public boolean translate(final int codePoint, final Appendable appendable)
      throws IOException {
    if (between) {
      if (codePoint < below || codePoint > above) {
        return false;
      }
    } else if (codePoint >= below && codePoint <= above) {
      return false;
    }
    // Use the UTF-16 coding
    Utf16.escape(codePoint, appendable);
    return true;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("below", below)
        .append("above", above)
        .append("between", between)
        .toString();
  }

  public JavaUnicodeEscaper clone() {
    return new JavaUnicodeEscaper(this);
  }
}
