////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.translate;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;

import ltd.qubit.commons.lang.ArrayUtils;
import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.text.Utf16;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

/**
 * Translates XML numeric entities of the form &amp;#[xX]?\d+;? to
 * the specific code point.
 *
 * <p><b>Note that the semicolon is optional.</b></p>
 *
 * @author Haixing Hu
 */
public class NumericEntityUnescaper extends CharSequenceTranslator {

  /**
   * Enumerates {@link NumericEntityUnescaper} options for unescaping.
   */
  public enum SemiColonOption {
    /**
     * Requires a semicolon.
     */
    REQUIRED,

    /**
     * Does not require a semicolon.
     */
    OPTIONAL,

    /**
     * Throws an exception if a semicolon is missing.
     */
    ERROR_IF_MISSING,
  }

  /**
   * Default options.
   */
  private static final EnumSet<SemiColonOption> DEFAULT_OPTIONS = EnumSet
      .copyOf(Collections.singletonList(SemiColonOption.REQUIRED));

  /**
   * EnumSet of OPTIONS, given from the constructor, read-only.
   */
  private final EnumSet<SemiColonOption> options;

  /**
   * Creates a UnicodeUnescaper.
   *
   * <p>The constructor takes a list of options, only one type of which is currently
   * available (whether to allow, error or ignore the semicolon on the end of a
   * numeric entity to being missing).</p>
   *
   * <p>For example, to support numeric entities without a ';':</p>
   * <pre><code>
   *    new NumericEntityUnescaper(NumericEntityUnescaper.OPTION.semiColonOptional)
   * </code></pre>
   * <p>and to throw an IllegalArgumentException when they're missing:</p>
   * <pre><code>
   *    new NumericEntityUnescaper(NumericEntityUnescaper.OPTION.errorIfNoSemiColon)
   * </code></pre>
   *
   * Note that the default behavior is to ignore them.
   *
   * @param options
   *     the options to apply to this unescaper.
   */
  public NumericEntityUnescaper(final SemiColonOption... options) {
    if (ArrayUtils.isEmpty(options)) {
      this.options = DEFAULT_OPTIONS;
    } else {
      this.options = EnumSet.copyOf(Arrays.asList(options));
    }
  }

  public NumericEntityUnescaper(final NumericEntityUnescaper other) {
    this.options = EnumSet.copyOf(other.options);
  }

  /**
   * Tests whether the passed in option is currently set.
   *
   * @param option to check state of
   * @return whether the option is set
   */
  public boolean isSet(final SemiColonOption option) {
    return options.contains(option);
  }

  @Override
  public int translate(final CharSequence input, final int index,
      final Appendable appendable) throws IOException {
    final int seqEnd = input.length();
    // Uses -2 to ensure there is something after the &#
    if ((input.charAt(index) == '&')
        && (index < seqEnd - 2)
        && (input.charAt(index + 1) == '#')) {
      int start = index + 2;
      boolean isHex = false;
      final char firstChar = input.charAt(start);
      if (firstChar == 'x' || firstChar == 'X') {
        start++;
        isHex = true;
        // Check there's more than just an x after the &#
        if (start == seqEnd) {
          return 0;
        }
      }
      int end = start;
      // Note that this supports character codes without a ; on the end
      while ((end < seqEnd)
          && ((input.charAt(end) >= '0' && input.charAt(end) <= '9')
           || (input.charAt(end) >= 'a' && input.charAt(end) <= 'f')
           || (input.charAt(end) >= 'A' && input.charAt(end) <= 'F'))) {
        end++;
      }
      final boolean semiNext = end != seqEnd && input.charAt(end) == ';';
      if (!semiNext) {
        if (isSet(SemiColonOption.REQUIRED)) {
          return 0;
        }
        if (isSet(SemiColonOption.ERROR_IF_MISSING)) {
          throw new IllegalArgumentException("Semi-colon required at end of numeric entity");
        }
      }
      final int entityValue;
      try {
        // FIXME: use more efficient method
        if (isHex) {
          entityValue = Integer.parseInt(input.subSequence(start, end).toString(), 16);
        } else {
          entityValue = Integer.parseInt(input.subSequence(start, end).toString(), 10);
        }
      } catch (final NumberFormatException nfe) {
        return 0;
      }
      Utf16.put(entityValue, appendable);
      return 2 + end - start + (isHex ? 1 : 0) + (semiNext ? 1 : 0);
    }
    return 0;
  }

  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if ((o == null) || (getClass() != o.getClass())) {
      return false;
    }
    final NumericEntityUnescaper other = (NumericEntityUnescaper) o;
    return Equality.equals(options, other.options);
  }

  public int hashCode() {
    final int multiplier = 7;
    int result = 3;
    result = Hash.combine(result, multiplier, options);
    return result;
  }

  public String toString() {
    return new ToStringBuilder(this)
        .append("options", options)
        .toString();
  }

  public NumericEntityUnescaper clone() {
    return new NumericEntityUnescaper(this);
  }
}
