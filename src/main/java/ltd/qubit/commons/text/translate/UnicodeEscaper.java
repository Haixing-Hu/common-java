////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.translate;

import java.io.IOException;

import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.text.Unicode;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

import static ltd.qubit.commons.lang.Argument.requireGreaterEqual;
import static ltd.qubit.commons.lang.Argument.requireValidUnicode;

/**
 * Translates code points to their Unicode escaped value.
 *
 * @author Haixing Hu
 */
public class UnicodeEscaper extends CodePointTranslator {

  /**
   * Constructs a {@code UnicodeEscaper} above the specified value (exclusive).
   *
   * @param codePoint
   *     the code point above which to escape.
   * @return
   *     The newly created {@code UnicodeEscaper} instance.
   */
  public static UnicodeEscaper above(final int codePoint) {
    return outsideOf(0, codePoint);
  }
  /**
   * Constructs a {@code UnicodeEscaper} below the specified value (exclusive).
   *
   * @param codePoint
   *     the code point below which to escape.
   * @return
   *     The newly created {@code UnicodeEscaper} instance.
   */
  public static UnicodeEscaper below(final int codePoint) {
    return outsideOf(codePoint, Unicode.UNICODE_MAX);
  }
  /**
   * Constructs a {@code UnicodeEscaper} between the specified values (inclusive).
   *
   * @param codePointLow
   *     the code point above which to escape.
   * @param codePointHigh
   *     the code point below which to escape.
   * @return
   *     The newly created {@code UnicodeEscaper} instance.
   */
  public static UnicodeEscaper between(final int codePointLow, final int codePointHigh) {
    return new UnicodeEscaper(codePointLow, codePointHigh, true);
  }

  /**
   * Constructs a {@code UnicodeEscaper} outside of the specified values (exclusive).
   *
   * @param codePointLow
   *     the code point below which to escape.
   * @param codePointHigh
   *     the code point above which to escape.
   * @return
   *     The newly created {@code UnicodeEscaper} instance.
   */
  public static UnicodeEscaper outsideOf(final int codePointLow, final int codePointHigh) {
    return new UnicodeEscaper(codePointLow, codePointHigh, false);
  }

  /**
   * The lowest code point boundary.
   */
  protected final int below;

  /**
   * The highest code point boundary.
   */
  protected final int above;

  /**
   * Whether to escape between the boundaries or outside them.
   */
  protected final boolean between;

  /**
   * Constructs a {@code UnicodeEscaper} for all characters.
   */
  public UnicodeEscaper() {
    this(0, Unicode.UNICODE_MAX, true);
  }

  /**
   * Constructs a {@code UnicodeEscaper} for the specified range.
   *
   * <p>This is the underlying method for the other constructors/builders.
   * The {@code below} and {@code above} boundaries are inclusive when
   * {@code between} is {@code true} and exclusive when it is {@code false}.
   *
   * @param below
   *     the lowest code point boundary.
   * @param above
   *     the highest code point boundary.
   * @param between
   *     whether to escape between the boundaries or outside them.
   */
  public UnicodeEscaper(final int below, final int above, final boolean between) {
    requireValidUnicode("below", below);
    requireValidUnicode("above", above);
    requireGreaterEqual("above", above, "below", below);
    this.below = below;
    this.above = above;
    this.between = between;
  }

  public UnicodeEscaper(final UnicodeEscaper other) {
    this.below = other.below;
    this.above = other.above;
    this.between = other.between;
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
    Unicode.escape(codePoint, appendable);
    return true;
  }

  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if ((o == null) || (getClass() != o.getClass())) {
      return false;
    }
    final UnicodeEscaper other = (UnicodeEscaper) o;
    return Equality.equals(below, other.below)
        && Equality.equals(above, other.above)
        && Equality.equals(between, other.between);
  }

  public int hashCode() {
    final int multiplier = 7;
    int result = 3;
    result = Hash.combine(result, multiplier, below);
    result = Hash.combine(result, multiplier, above);
    result = Hash.combine(result, multiplier, between);
    return result;
  }

  public String toString() {
    return new ToStringBuilder(this)
        .append("below", below)
        .append("above", above)
        .append("between", between)
        .toString();
  }

  @Override
  public UnicodeEscaper cloneEx() {
    return new UnicodeEscaper(this);
  }
}