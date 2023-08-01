////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.codec;

import ltd.qubit.commons.datastructure.list.primitive.impl.ByteArrayList;
import ltd.qubit.commons.lang.ArrayUtils;
import ltd.qubit.commons.lang.ByteUtils;
import ltd.qubit.commons.lang.StringUtils;
import ltd.qubit.commons.lang.SystemUtils;
import ltd.qubit.commons.math.ByteBit;
import ltd.qubit.commons.text.ParsingPosition;

import javax.annotation.Nullable;

import static ltd.qubit.commons.lang.Argument.requireGreater;
import static ltd.qubit.commons.lang.Argument.requireIndexInCloseRange;
import static ltd.qubit.commons.lang.Argument.requireNonNull;
import static ltd.qubit.commons.lang.CharUtils.LOWERCASE_DIGITS;
import static ltd.qubit.commons.lang.CharUtils.UPPERCASE_DIGITS;
import static ltd.qubit.commons.text.NumberFormat.HEX_RADIX;
import static ltd.qubit.commons.text.NumberFormatSymbols.DEFAULT_LOWERCASE_RADIX_PREFIXES;
import static ltd.qubit.commons.text.NumberFormatSymbols.DEFAULT_UPPERCASE_RADIX_PREFIXES;
import static ltd.qubit.commons.text.ParseUtils.getSpecialRadixInt;
import static ltd.qubit.commons.text.ParseUtils.skipBlanks;
import static ltd.qubit.commons.text.ParseUtils.skipPrefix;

/**
 * The converter of hexadecimal strings and byte arrays.
 *
 * @author Haixing Hu
 */
public class HexCodec implements Codec<byte[], String> {

  public static final String DEFAULT_SEPARATOR = " ";

  public static final boolean DEFAULT_SHOW_RADIX = true;

  public static final boolean DEFAULT_UPPERCASE_RADIX = false;

  public static final boolean DEFAULT_UPPERCASE_DIGIT = true;

  public static final boolean DEFAULT_SKIP_BLANKS = true;

  public static final int DEFAULT_MAX_PER_LINE = Integer.MAX_VALUE;

  private String separator;
  private boolean showRadix;
  private boolean uppercaseRadix;
  private boolean uppercaseDigit;
  private boolean skipBlanks;
  private int maxPerLine;
  private String prefix;
  private final transient ParsingPosition position;
  private final transient ByteArrayList byteList;
  private final transient StringBuilder builder;

  public HexCodec() {
    separator = DEFAULT_SEPARATOR;
    showRadix = DEFAULT_SHOW_RADIX;
    uppercaseRadix = DEFAULT_UPPERCASE_RADIX;
    uppercaseDigit = DEFAULT_UPPERCASE_DIGIT;
    skipBlanks = DEFAULT_SKIP_BLANKS;
    maxPerLine = DEFAULT_MAX_PER_LINE;
    prefix = null;
    position = new ParsingPosition();
    byteList = new ByteArrayList();
    builder = new StringBuilder();
  }

  public void reset() {
    separator = DEFAULT_SEPARATOR;
    showRadix = DEFAULT_SHOW_RADIX;
    uppercaseRadix = DEFAULT_UPPERCASE_RADIX;
    uppercaseDigit = DEFAULT_UPPERCASE_DIGIT;
    skipBlanks = DEFAULT_SKIP_BLANKS;
    maxPerLine = DEFAULT_MAX_PER_LINE;
    prefix = null;
  }

  /**
   * Gets the separator between hex digits.
   *
   * <p>While parsing hex digits, the separator is optional, and all blanks
   * around
   * the separator will be skipped; while formatting the hex digits, the
   * separator is inserted between two hex digits.
   *
   * @return the separator between hex digits.
   */
  public String getSeparator() {
    return separator;
  }

  /**
   * Sets the separator between hex digits.
   *
   * @param separator
   *     the new separator between hex digits. A null string is treated as an
   *     empty string.
   * @return this object.
   */
  public HexCodec setSeparator(@Nullable final String separator) {
    if (separator == null) {
      this.separator = StringUtils.EMPTY;
    } else {
      this.separator = separator;
    }
    return this;
  }

  /**
   * Tests whether the formatting of hex digits should show the hex radix
   * prefix.
   *
   * @return whether the formatting of hex digits should show the hex radix
   *     prefix.
   */
  public boolean isShowRadix() {
    return showRadix;
  }

  /**
   * Sets whether the formatting of hex digits should show the hex radix
   * prefix.
   *
   * @param showRadix
   *     determinate whether the formatting of hex digits should show the hex
   *     radix prefix.
   * @return this object.
   */
  public HexCodec setShowRadix(final boolean showRadix) {
    this.showRadix = showRadix;
    return this;
  }

  /**
   * Tests whether the formatting of hex digits should use the uppercase radix
   * prefix.
   *
   * @return whether the formatting of hex digits should use the uppercase radix
   *     prefix.
   */
  public boolean isUppercaseRadix() {
    return uppercaseRadix;
  }

  /**
   * Sets whether the formatting of hex digits should use the uppercase radix
   * prefix.
   *
   * @param uppercaseRadix
   *     determinate whether the formatting of hex digits should use the
   *     uppercase radix prefix.
   * @return this object.
   */
  public HexCodec setUppercaseRadix(final boolean uppercaseRadix) {
    this.uppercaseRadix = uppercaseRadix;
    return this;
  }

  /**
   * Tests whether the formatting of hex digits should use the uppercase hex
   * digits.
   *
   * @return whether the formatting of hex digits should use the uppercase hex
   *     digits.
   */
  public boolean isUppercaseDigit() {
    return uppercaseDigit;
  }

  /**
   * Sets whether the formatting of hex digits should use the uppercase hex
   * digits.
   *
   * @param uppercaseDigit
   *     determinate whether the formatting of hex digits should use the
   *     uppercase hex digits.
   * @return this object.
   */
  public HexCodec setUppercaseDigit(final boolean uppercaseDigit) {
    this.uppercaseDigit = uppercaseDigit;
    return this;
  }

  /**
   * Tests whether to skip the blanks while parsing hex string.
   *
   * @return whether to skip the blanks while parsing hex string.
   */
  public boolean isSkipBlanks() {
    return skipBlanks;
  }

  /**
   * Sets whether to skip the blanks while parsing hex string.
   *
   * @param skipBlanks
   *     determinate whether to skip the blanks while parsing hex string.
   * @return this object.
   */
  public HexCodec setSkipBlanks(final boolean skipBlanks) {
    this.skipBlanks = skipBlanks;
    return this;
  }

  /**
   * Gets the maximum number of hex digits per line in the formatted string.
   *
   * @return the maximum number of hex digits per line in the formatted string.
   */
  public int getMaxPerLine() {
    return maxPerLine;
  }

  /**
   * Sets the maximum number of hex digits per line in the formatted string.
   *
   * @param maxPerLine
   *     the maximum number of hex digits per line in the formatted string.
   * @return this object.
   */
  public HexCodec setMaxPerLine(final int maxPerLine) {
    this.maxPerLine = requireGreater("maxPerLine", maxPerLine, "zero", 0);
    return this;
  }

  /**
   * Gets the prefix of the hex numbers.
   *
   * @return the prefix of the hex numbers, or {@code null} to use the default
   *     prefix.
   */
  @Nullable
  public String getPrefix() {
    return prefix;
  }

  /**
   * Sets the prefix of the hex numbers.
   *
   * @param prefix
   *     the prefix of the hex numbers, or {@code null} to use the default
   *     prefix.
   */
  public void setPrefix(@Nullable final String prefix) {
    this.prefix = prefix;
  }

  public ParsingPosition getParsePosition() {
    return position;
  }

  public int getParseIndex() {
    return position.getIndex();
  }

  public int getErrorIndex() {
    return position.getErrorIndex();
  }

  public int getErrorCode() {
    return position.getErrorCode();
  }

  public boolean success() {
    return position.success();
  }

  public boolean fail() {
    return position.fail();
  }

  @Override
  public byte[] decode(final String str) {
    return decode(str, 0, str.length());
  }

  public byte[] decode(final String str, final int startIndex) {
    return decode(str, startIndex, str.length());
  }

  public byte[] decode(final String str, final int startIndex,
      final int endIndex) {
    // check the index bounds
    requireIndexInCloseRange(startIndex, 0, str.length());
    requireIndexInCloseRange(endIndex, 0, str.length());
    requireNonNull("separator", separator);
    // deal with the special case
    if (startIndex >= endIndex) {
      return ArrayUtils.EMPTY_BYTE_ARRAY;
    }
    // reset the parse position
    position.reset(startIndex);
    // clear the byte list
    byteList.clear();
    // set the prefix
    final String lowercaseRadixPrefix;
    final String uppercaseRadixPrefix;
    if (prefix == null) {
      lowercaseRadixPrefix = DEFAULT_LOWERCASE_RADIX_PREFIXES[HEX_RADIX];
      uppercaseRadixPrefix = DEFAULT_UPPERCASE_RADIX_PREFIXES[HEX_RADIX];
    } else {
      lowercaseRadixPrefix = prefix.toLowerCase();
      uppercaseRadixPrefix = prefix.toUpperCase();
    }
    // parsing the byte array
    while (position.getIndex() < endIndex) {
      // skip the possible blanks
      if (skipBlanks) {
        skipBlanks(position, str, endIndex);
        if (position.fail()) {
          return null;
        }
      }
      // skip the optional separator
      if (separator.length() > 0) {
        skipPrefix(position, str, endIndex, separator);
      }
      // check whether this is the end of the text segment
      if (position.getIndex() >= endIndex) {
        break;
      }
      // skip the optional hex radix prefix
      if (!skipPrefix(position, str, endIndex, lowercaseRadixPrefix)) {
        skipPrefix(position, str, endIndex, uppercaseRadixPrefix);
      }
      // parse the hex byte
      final byte value = (byte) getSpecialRadixInt(position, str, endIndex, +1,
          16, ByteUtils.UNSIGNED_MAX, 2, false, ',');
      if (position.fail()) {
        return null;
      }
      byteList.add(value);
    }
    return byteList.toArray();
  }

  @Override
  public String encode(final byte[] source) {
    builder.setLength(0);
    encode(source, 0, source.length, builder);
    return builder.toString();
  }

  public void encode(final byte[] source, final StringBuilder builder) {
    encode(source, 0, source.length, builder);
  }

  public String encode(final byte[] source, final int startIndex) {
    builder.setLength(0);
    encode(source, startIndex, source.length, builder);
    return builder.toString();
  }

  public void encode(final byte[] source, final int startIndex,
      final StringBuilder builder) {
    encode(source, startIndex, source.length, builder);
  }

  public String encode(final byte[] source, final int startIndex,
      final int endIndex) {
    builder.setLength(0);
    encode(source, startIndex, endIndex, builder);
    return builder.toString();
  }

  public void encode(final byte[] values, final int startIndex,
      final int endIndex, final StringBuilder builder) {
    // check the index bounds
    requireIndexInCloseRange(startIndex, 0, values.length);
    requireIndexInCloseRange(endIndex, 0, values.length);
    // deal with the special case
    if (startIndex >= endIndex) {
      return;
    }
    // select the digits
    final char[] digits = (uppercaseDigit ? UPPERCASE_DIGITS :
                           LOWERCASE_DIGITS);
    // select the radix prefix
    String radixPrefix = StringUtils.EMPTY;
    if (prefix != null) {
      radixPrefix = prefix;
    } else if (showRadix) {
      if (uppercaseRadix) {
        radixPrefix = DEFAULT_UPPERCASE_RADIX_PREFIXES[HEX_RADIX];
      } else {
        radixPrefix = DEFAULT_LOWERCASE_RADIX_PREFIXES[HEX_RADIX];
      }
    }
    // now perform the formatting
    final int n = endIndex - startIndex;
    for (int i = 0; i < n; ++i) {
      if (i > 0) {
        if ((i % maxPerLine) == 0) {
          builder.append(SystemUtils.LINE_SEPARATOR);
        } else {
          builder.append(separator);
        }
      }
      final byte value = values[startIndex + i];
      builder.append(radixPrefix)
             .append(
                 digits[(value >>> ByteBit.HALF_BITS) & ByteBit.HALF_BITS_MASK])
             .append(digits[value & ByteBit.HALF_BITS_MASK]);
    }
  }

}
