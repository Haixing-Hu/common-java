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
import java.io.UncheckedIOException;

import static ltd.qubit.commons.lang.CharUtils.UPPERCASE_DIGITS;
import static ltd.qubit.commons.text.ErrorCode.BUFFER_OVERFLOW;
import static ltd.qubit.commons.text.ErrorCode.INCOMPLETE_UNICODE;
import static ltd.qubit.commons.text.ErrorCode.MALFORMED_UNICODE;
import static ltd.qubit.commons.text.Unicode.HIGH_SURROGATE_MASK;
import static ltd.qubit.commons.text.Unicode.HIGH_SURROGATE_MIN;
import static ltd.qubit.commons.text.Unicode.HIGH_SURROGATE_SHIFT;
import static ltd.qubit.commons.text.Unicode.LOW_SURROGATE_MASK;
import static ltd.qubit.commons.text.Unicode.LOW_SURROGATE_MIN;
import static ltd.qubit.commons.text.Unicode.SUPPLEMENTARY_MIN;
import static ltd.qubit.commons.text.Unicode.SURROGATE_COMPOSE_OFFSET;
import static ltd.qubit.commons.text.Unicode.SURROGATE_DECOMPOSE_MASK;
import static ltd.qubit.commons.text.Unicode.SURROGATE_DECOMPOSE_OFFSET;
import static ltd.qubit.commons.text.Unicode.SURROGATE_MASK;
import static ltd.qubit.commons.text.Unicode.SURROGATE_MIN;
import static ltd.qubit.commons.text.Unicode.UNICODE_MAX;
import static ltd.qubit.commons.text.Unicode.composeSurrogatePair;
import static ltd.qubit.commons.text.Unicode.decomposeHighSurrogate;
import static ltd.qubit.commons.text.Unicode.decomposeLowSurrogate;
import static ltd.qubit.commons.text.Unicode.isSupplementary;

/**
 * Provides the UTF-16 coding scheme utilities.
 *
 * @author Haixing Hu
 */
public final class Utf16 {

  /**
   * The maximum number of code units in order to encode a single Unicode code
   * point.
   */
  public static final int MAX_CODE_UNIT_COUNT = 2;

  /**
   * Determines whether the specified UTF-16 code unit is a single code unit
   * encoding a valid code point.
   *
   * <p>A valid Unicode code point could be encoded as 1 to 2 UTF-16 code unit, and
   * a UTF-16 code unit is a single code unit encoding a Unicode code point iff
   * it is not a surrogate.
   *
   * @param ch
   *          a UTF-16 code unit.
   * @return true if the specified UTF-16 code unit is a single code unit
   *         encoding a valid Unicode code point; false otherwise.
   */
  public static boolean isSingle(final char ch) {
    return (ch & SURROGATE_MASK) != SURROGATE_MIN;
  }

  /**
   * Determines whether the specified UTF-16 code unit is a leading code unit of
   * a valid Unicode code point.
   *
   * <p>A valid Unicode code point could be encoded as 1 to 2 UTF-16 code unit, and
   * a UTF-16 code unit is a leading code unit of a Unicode code point iff it is
   * a high surrogate.
   *
   * @param ch
   *          a UTF-16 code unit.
   * @return true if the specified UTF-16 code unit is a leading code unit of a
   *         valid Unicode code point; false otherwise.
   */
  public static boolean isLeading(final char ch) {
    return (ch & HIGH_SURROGATE_MASK) == HIGH_SURROGATE_MIN;
  }

  /**
   * Determines whether the specified UTF-16 code unit is a trailing code unit
   * of a valid Unicode code point.
   *
   * <p>A valid Unicode code point could be encoded as 1 to 2 UTF-16 code unit, and
   * a UTF-16 code unit is a trailing code unit of a Unicode code point iff it
   * is a low surrogate.
   *
   * @param ch
   *          a UTF-16 code unit.
   * @return true if the specified UTF-16 code unit is a trailing code unit of a
   *         valid Unicode code point; false otherwise.
   */
  public static boolean isTrailing(final char ch) {
    return (ch & LOW_SURROGATE_MASK) == LOW_SURROGATE_MIN;
  }

  /**
   * Tests whether a UTF-16 code unit is a surrogate character.
   *
   * @param ch
   *     the UTF-16 code unit to be tested.
   * @return
   *     {@code true} if the UTF-16 code unit is a surrogate character;
   *     {@code false} otherwise.
   */
  public static boolean isSurrogate(final char ch) {
    return (ch & SURROGATE_MASK) == SURROGATE_MIN;
  }

  /**
   * Tests whether two UTF-16 code units compose a surrogate pair.
   *
   * @param high
   *     the UTF-16 code unit to be treated as the high surrogate.
   * @param low
   *     the UTF-16 code unit to be treated as the low surrogate.
   * @return
   *     {@code true} if the two UTF-16 code units compose a surrogate pair;
   *     {@code false} otherwise.
   */
  public static boolean isSurrogatePair(final char high, final char low) {
    return isLeading(high) && isTrailing(low);
  }

  /**
   * Compose a surrogate pair of UTF-16 code units.
   *
   * @param high
   *     the UTF-16 code unit representing a high surrogate.
   * @param low
   *     the UTF-16 code unit representing a low surrogate.
   * @return
   *     the code point of the Unicode character composed by the specified
   *     surrogate pair.
   */
  public static int compose(final char high, final char low) {
    assert (isLeading(high) && isTrailing(low));
    return (high << HIGH_SURROGATE_SHIFT) + low - SURROGATE_COMPOSE_OFFSET;
  }

  /**
   * Decompose the high surrogate UTF-16 code unit of supplementary Unicode
   * code point.
   *
   * @param codePoint
   *     the supplementary Unicode code point to be decomposed.
   * @return
   *     the UTF-16 code unit representing the high surrogate of the
   *     supplementary Unicode code point.
   */
  public static char decomposeHigh(final int codePoint) {
    assert (isSupplementary(codePoint));
    return (char) ((codePoint >> HIGH_SURROGATE_SHIFT) + SURROGATE_DECOMPOSE_OFFSET);
  }

  /**
   * Decompose the low surrogate UTF-16 code unit of supplementary Unicode
   * code point.
   *
   * @param codePoint
   *     the supplementary Unicode code point to be decomposed.
   * @return
   *     the UTF-16 code unit representing the low surrogate of the
   *     supplementary Unicode code point.
   */
  public static int decomposeLow(final int codePoint) {
    assert (isSupplementary(codePoint));
    return (char) ((codePoint & SURROGATE_DECOMPOSE_MASK) | LOW_SURROGATE_MIN);
  }

  /**
   * Counts the number of trailing UTF-16 code units need to compose a valid
   * Unicode code point according to the leading code unit.
   *
   * @param ch
   *          a UTF-16 code unit, which must be a leading code unit of a valid
   *          code point.
   * @return The number of trailing code units (does NOT include the leading
   *         code unit itself) need to compose a valid Unicode code point
   *         according to the leading code unit.
   */
  public static int getTrailingCount(final char ch) {
    return ((ch & HIGH_SURROGATE_MASK) == HIGH_SURROGATE_MIN ? 1 : 0);
  }

  /**
   * Gets the number of UTF-16 code units need to encode the specified Unicode
   * code point.
   *
   * @param codePoint
   *          a code point, which must be a valid Unicode code point and must
   *          NOT be a surrogate code point.
   * @return The number of UTF-16 code units need to encode the specified
   *         Unicode code point, which is in the range of [1, MaxCount].
   */
  public static int getCodeUnitCount(final int codePoint) {
    return (codePoint >= SUPPLEMENTARY_MIN ? 2 : 1);
  }

  /**
   * Adjust a random-access offset in a UTF-16 code unit sequence to the start
   * position of the current code point.
   *
   * <p>More precisely, if the offset points to a trailing code unit, then the
   * offset is moved backward to the corresponding leading code unit; otherwise,
   * it is not modified.
   *
   * @param pos
   *          the index parsing position; after calling this function, it will
   *          be set to the new position.
   * @param buffer
   *          an array of chars.
   * @param startIndex
   *          the start current of the char array.
   * @return the amount of decreasing of the parsing position, or 0 if the
   *         parsing position does not modified. After successfully calling this
   *         function, the position of the buffer will be decreased to the
   *         amount of value returned by this function. If the current position
   *         of the buffer points an illegal code unit sequence, or an
   *         incomplete code unit sequence, the function returns a negative
   *         integer indicating the error: {@link ErrorCode#MALFORMED_UNICODE}
   *         indicates an illegal code unit sequence;
   *         {@link ErrorCode#INCOMPLETE_UNICODE} indicates an incomplete code
   *         unit sequence, and the position of the buffer does not changed.
   * @throws IndexOutOfBoundsException
   *           if startIndex &lt; 0 or pos.getIndex() &lt; startIndex.
   */
  public static int setToStart(final ParsingPosition pos, final char[] buffer,
      final int startIndex) {
    int index = pos.getIndex();
    if ((startIndex < 0) || (index < startIndex)) {
      throw new IndexOutOfBoundsException();
    }
    if (! isTrailing(buffer[index])) {
      // buffer[current] is not a lower surrogate.
      return 0;
    } else if (index == startIndex) {
      pos.setErrorIndex(index);
      pos.setErrorCode(INCOMPLETE_UNICODE);
      return INCOMPLETE_UNICODE;
    } else {
      --index;
      if (isLeading(buffer[index])) {
        // buffer[current] is a high surrogate
        pos.setIndex(index);
        return 1;
      } else {
        pos.setErrorIndex(index);
        pos.setErrorCode(MALFORMED_UNICODE);
        return MALFORMED_UNICODE;
      }
    }
  }

  /**
   * Adjust a random-access offset in a UTF-16 code unit sequence to the start
   * position of the current code point.
   *
   * <p>More precisely, if the offset points to a trailing code unit, then the
   * offset is moved backward to the corresponding leading code unit; otherwise,
   * it is not modified.
   *
   * @param pos
   *          the index parsing position; after calling this function, it will
   *          be set to the new position.
   * @param str
   *          a character sequence.
   * @param startIndex
   *          the start current of the character sequence.
   * @return the amount of decreasing of the parsing position, or 0 if the
   *         parsing position does not modified. After successfully calling this
   *         function, the position of the buffer will be decreased to the
   *         amount of value returned by this function. If the current position
   *         of the buffer points an illegal code unit sequence, or an
   *         incomplete code unit sequence, the function returns a negative
   *         integer indicating the error: {@link ErrorCode#MALFORMED_UNICODE}
   *         indicates an illegal code unit sequence;
   *         {@link ErrorCode#INCOMPLETE_UNICODE} indicates an incomplete code
   *         unit sequence, and the position of the buffer does not changed.
   * @throws IndexOutOfBoundsException
   *           if startIndex &lt; 0 or pos.getIndex() &lt; startIndex.
   */
  public static int setToStart(final ParsingPosition pos, final CharSequence str,
      final int startIndex) {
    int index = pos.getIndex();
    if ((startIndex < 0) || (index < startIndex)) {
      throw new IndexOutOfBoundsException();
    }
    if (! isTrailing(str.charAt(index))) {
      // buffer[current] is not a lower surrogate.
      return 0;
    } else if (index == startIndex) {
      pos.setErrorIndex(index);
      pos.setErrorCode(INCOMPLETE_UNICODE);
      return INCOMPLETE_UNICODE;
    } else {
      --index;
      if (isLeading(str.charAt(index))) {
        // buffer[current] is a high surrogate
        pos.setIndex(index);
        return 1;
      } else {
        pos.setErrorIndex(index);
        pos.setErrorCode(MALFORMED_UNICODE);
        return MALFORMED_UNICODE;
      }
    }
  }

  /**
   * Adjust a random-access offset in a UTF-16 code unit sequence to the
   * position of the end of current code point.
   *
   * <p>More precisely, if the offset points to a trailing code unit of a code
   * point, then the offset is increased to behind the whole code unit sequence
   * of the code point; otherwise, it is not modified.
   *
   * @param pos
   *          the index parsing position; after calling this function, it will
   *          be set to the new position.
   * @param buffer
   *          an array of bytes.
   * @param endIndex
   *          the end current of the byte array.
   * @return the amount of increasing of the parsing position, or 0 if the
   *         parsing position does not modified. After successfully calling this
   *         function, the position of the buffer will be increased to the
   *         amount of value returned by this function. If the current position
   *         of the buffer points to an illegal code unit sequence, or an
   *         incomplete code unit sequence, the function returns a negative
   *         integer indicating the error: {@link ErrorCode#MALFORMED_UNICODE}
   *         indicates an illegal code unit sequence;
   *         {@link ErrorCode#INCOMPLETE_UNICODE} indicates an incomplete code
   *         unit sequence.
   * @throws IndexOutOfBoundsException
   *           if endIndex &gt; buffer.length or pos.getIndex() &gt; endIndex. Note that
   *           if pos.getIndex() == endIndex, the function does nothing and returns
   *           0.
   */
  public static int setToTerminal(final ParsingPosition pos, final char[] buffer,
      final int endIndex) {
    int index = pos.getIndex();
    if ((endIndex > buffer.length) || (index > endIndex)) {
      throw new IndexOutOfBoundsException();
    }
    if (index == endIndex) {
      return 0;
    } else if (! isLeading(buffer[index])) {
      // buffer[current] is not a high surrogate
      return 0;
    } else {
      ++index;
      if (index >= endIndex) {
        pos.setErrorIndex(index);
        pos.setErrorCode(INCOMPLETE_UNICODE);
        return INCOMPLETE_UNICODE;
      } else if (isTrailing(buffer[index])) {
        // buffer[current] is a low surrogate
        pos.setIndex(index);
        return 1;
      } else {
        pos.setErrorIndex(index);
        pos.setErrorCode(MALFORMED_UNICODE);
        return MALFORMED_UNICODE;
      }
    }
  }

  /**
   * Adjust a random-access offset in a UTF-16 code unit sequence to the
   * position of the end of current code point.
   *
   * <p>More precisely, if the offset points to a trailing code unit of a code
   * point, then the offset is increased to behind the whole code unit sequence
   * of the code point; otherwise, it is not modified.
   *
   * @param pos
   *          the index parsing position; after calling this function, it will
   *          be set to the new position.
   * @param str
   *          a character sequence.
   * @param endIndex
   *          the end current of the character sequence.
   * @return the amount of increasing of the parsing position, or 0 if the
   *         parsing position does not modified. After successfully calling this
   *         function, the position of the buffer will be increased to the
   *         amount of value returned by this function. If the current position
   *         of the buffer points to an illegal code unit sequence, or an
   *         incomplete code unit sequence, the function returns a negative
   *         integer indicating the error: {@link ErrorCode#MALFORMED_UNICODE}
   *         indicates an illegal code unit sequence;
   *         {@link ErrorCode#INCOMPLETE_UNICODE} indicates an incomplete code
   *         unit sequence.
   * @throws IndexOutOfBoundsException
   *           if endIndex &gt; buffer.length or pos.getIndex() &gt; endIndex. Note that
   *           if pos.getIndex() == endIndex, the function does nothing and returns
   *           0.
   */
  public static int setToTerminal(final ParsingPosition pos,
      final CharSequence str, final int endIndex) {
    int index = pos.getIndex();
    if ((endIndex > str.length()) || (index > endIndex)) {
      throw new IndexOutOfBoundsException();
    }
    if (index == endIndex) {
      return 0;
    } else if (! isLeading(str.charAt(index))) {
      // buffer[current] is not a high surrogate
      return 0;
    } else {
      ++index;
      if (index >= endIndex) {
        pos.setErrorIndex(index);
        pos.setErrorCode(INCOMPLETE_UNICODE);
        return INCOMPLETE_UNICODE;
      } else if (isTrailing(str.charAt(index))) {
        // buffer[current] is a low surrogate
        pos.setIndex(index);
        return 1;
      } else {
        pos.setErrorIndex(index);
        pos.setErrorCode(MALFORMED_UNICODE);
        return MALFORMED_UNICODE;
      }
    }
  }

  /**
   * Advance the offset of a UTF-16 code unit sequence from one code point
   * boundary to the next one.
   *
   * <p>The offset must point to a code unit starting a valid code point (i.e.,
   * it's either points to a single code unit, or points to a leading code
   * unit), and the function advance the offset to the starting of the next code
   * unit, returns the number of code unit it skipped. If the offset does not
   * points to a code unit starting a valid code point, or it points to a
   * illegal code unit sequence, the function does nothing but returns a
   * negative integer indicating the error.
   *
   * @param pos
   *          the index parsing position; after calling this function, it will
   *          be set to the new position.
   * @param buffer
   *          an array of bytes.
   * @param endIndex
   *          the end current of the byte array.
   * @return if the function successfully skip a code point from the code unit
   *         sequence at the specified offset, returns the number of code units
   *         consisting the skipped code point, and forward the position of the
   *         buffer to the new position; otherwise, if the specified offset does
   *         not point to a leading code unit of a valid code point, do nothing
   *         and returns a negative integer indicating the error:
   *         {@link ErrorCode#MALFORMED_UNICODE} indicates the specified offset
   *         does not point to the starting of a legal code unit sequence
   *         consisting a valid code point; {@link ErrorCode#INCOMPLETE_UNICODE}
   *         indicates the input code unit sequence is not complete to form a
   *         valid code point.
   * @throws IndexOutOfBoundsException
   *           if endIndex &gt; buffer.length or pos.getIndex() &gt; endIndex. Note that
   *           if pos.getIndex() == endIndex, the function does nothing and returns
   *           0.
   */
  public static int forward(final ParsingPosition pos, final char[] buffer,
      final int endIndex) {
    int index = pos.getIndex();
    if ((endIndex > buffer.length) || (index > endIndex)) {
      throw new IndexOutOfBoundsException();
    }
    if (index == endIndex) {
      return 0;
    }
    final char ch = buffer[index];
    if (isSingle(ch)) {
      pos.setIndex(index + 1);
      return 1;
    } else if (! isLeading(ch)) {
      pos.setErrorIndex(index);
      pos.setErrorCode(MALFORMED_UNICODE);
      return MALFORMED_UNICODE;
    } else {
      ++index;
      if (index == endIndex) {
        pos.setErrorIndex(index);
        pos.setErrorCode(INCOMPLETE_UNICODE);
        return INCOMPLETE_UNICODE;
      } else if (isTrailing(buffer[index])) {
        pos.setIndex(index + 1);
        return 2;
      } else {
        pos.setErrorIndex(index);
        pos.setErrorCode(MALFORMED_UNICODE);
        return MALFORMED_UNICODE;
      }
    }
  }

  /**
   * Advance the offset of a UTF-16 code unit sequence from one code point
   * boundary to the next one.
   *
   * <p>The offset must point to a code unit starting a valid code point (i.e.,
   * it's either points to a single code unit, or points to a leading code
   * unit), and the function advance the offset to the starting of the next code
   * unit, returns the number of code unit it skipped. If the offset does not
   * points to a code unit starting a valid code point, or it points to a
   * illegal code unit sequence, the function does nothing but returns a
   * negative integer indicating the error.
   *
   * @param pos
   *          the index parsing position; after calling this function, it will
   *          be set to the new position.
   * @param str
   *          a character sequence.
   * @param endIndex
   *          the end current of the character sequence.
   * @return if the function successfully skip a code point from the code unit
   *         sequence at the specified offset, returns the number of code units
   *         consisting the skipped code point, and forward the position of the
   *         buffer to the new position; otherwise, if the specified offset does
   *         not point to a leading code unit of a valid code point, do nothing
   *         and returns a negative integer indicating the error:
   *         {@link ErrorCode#MALFORMED_UNICODE} indicates the specified offset
   *         does not point to the starting of a legal code unit sequence
   *         consisting a valid code point; {@link ErrorCode#INCOMPLETE_UNICODE}
   *         indicates the input code unit sequence is not complete to form a
   *         valid code point.
   * @throws IndexOutOfBoundsException
   *           if endIndex &gt; buffer.length or pos.getIndex() &gt; endIndex. Note that
   *           if pos.getIndex() == endIndex, the function does nothing and returns
   *           0.
   */
  public static int forward(final ParsingPosition pos, final CharSequence str,
      final int endIndex) {
    int index = pos.getIndex();
    if ((endIndex > str.length()) || (index > endIndex)) {
      throw new IndexOutOfBoundsException();
    }
    if (index == endIndex) {
      return 0;
    }
    final char ch = str.charAt(index);
    if (isSingle(ch)) {
      pos.setIndex(index + 1);
      return 1;
    } else if (! isLeading(ch)) {
      pos.setErrorIndex(index);
      pos.setErrorCode(MALFORMED_UNICODE);
      return MALFORMED_UNICODE;
    } else {
      ++index;
      if (index == endIndex) {
        pos.setErrorIndex(index);
        pos.setErrorCode(INCOMPLETE_UNICODE);
        return INCOMPLETE_UNICODE;
      } else if (isTrailing(str.charAt(index))) {
        pos.setIndex(index + 1);
        return 2;
      } else {
        pos.setErrorIndex(index);
        pos.setErrorCode(MALFORMED_UNICODE);
        return MALFORMED_UNICODE;
      }
    }
  }

  /**
   * Decreases the offset of a UTF-16 code unit sequence from one code point
   * boundary to the previous one.
   *
   * <p>The offset must point to a position next to the code unit ending a valid
   * code point (i.e., it's either points to a single code unit, or points to a
   * leading code unit, or points to the position right after the last code unit
   * of the whole code unit sequence), and the function decreases the offset to
   * the starting of the previous code unit, returns the number of code unit it
   * passed. If the offset does not points to a position next to the code unit
   * ending a valid code point, or if the previous code unit sequence is
   * illegal, the function does nothing but returns a negative integer
   * indicating the error.
   *
   * @param pos
   *          the index parsing position; after calling this function, it will
   *          be set to the new position.
   * @param buffer
   *          an array of chars.
   * @param startIndex
   *          the start current of the char array.
   * @return if the function successfully passed the previous code point from
   *         the code unit sequence at the specified offset, returns the number
   *         of code units consisting the passed code point, and backward the
   *         position of the buffer to the new position; otherwise, if the
   *         specified offset does not point to a leading code unit of a valid
   *         code point, do nothing and returns a negative integer indicating
   *         the error: {@link ErrorCode#MALFORMED_UNICODE} indicates the
   *         specified offset does not point to the next position of the code
   *         unit ending a valid code point;
   *         {@link ErrorCode#INCOMPLETE_UNICODE} indicates the input code unit
   *         sequence is not complete to form a valid code point.
   * @throws IndexOutOfBoundsException
   *           if startIndex &lt; 0 or pos.getIndex() &lt; startIndex. Note that if
   *           pos.getIndex() == startIndex, the function does nothing and returns
   *           0.
   */
  public static int backward(final ParsingPosition pos, final char[] buffer,
      final int startIndex) {
    int index = pos.getIndex();
    if ((startIndex < 0) || (index < startIndex)) {
      throw new IndexOutOfBoundsException();
    }
    if (index == startIndex) {
      return 0;
    }
    --index;
    final char ch = buffer[index];
    if (isSingle(ch)) {
      pos.setIndex(index);
      return 1;
    } else if (! isTrailing(ch)) {
      pos.setErrorIndex(index);
      pos.setErrorCode(MALFORMED_UNICODE);
      return MALFORMED_UNICODE;
    }
    if (index == startIndex) {
      pos.setErrorIndex(index);
      pos.setErrorCode(INCOMPLETE_UNICODE);
      return INCOMPLETE_UNICODE;
    }
    --index;
    if (isLeading(buffer[index])) {
      pos.setIndex(index);
      return 2;
    } else {
      pos.setErrorIndex(index);
      pos.setErrorCode(MALFORMED_UNICODE);
      return MALFORMED_UNICODE;
    }
  }

  /**
   * Decreases the offset of a UTF-16 code unit sequence from one code point
   * boundary to the previous one.
   *
   * <p>The offset must point to a position next to the code unit ending a valid
   * code point (i.e., it's either points to a single code unit, or points to a
   * leading code unit, or points to the position right after the last code unit
   * of the whole code unit sequence), and the function decreases the offset to
   * the starting of the previous code unit, returns the number of code unit it
   * passed. If the offset does not points to a position next to the code unit
   * ending a valid code point, or if the previous code unit sequence is
   * illegal, the function does nothing but returns a negative integer
   * indicating the error.
   *
   * @param pos
   *          the index parsing position; after calling this function, it will
   *          be set to the new position.
   * @param str
   *          a character sequence.
   * @param startIndex
   *          the start current of the a character sequence.
   * @return if the function successfully passed the previous code point from
   *         the code unit sequence at the specified offset, returns the number
   *         of code units consisting the passed code point, and backward the
   *         position of the buffer to the new position; otherwise, if the
   *         specified offset does not point to a leading code unit of a valid
   *         code point, do nothing and returns a negative integer indicating
   *         the error: {@link ErrorCode#MALFORMED_UNICODE} indicates the
   *         specified offset does not point to the next position of the code
   *         unit ending a valid code point;
   *         {@link ErrorCode#INCOMPLETE_UNICODE} indicates the input code unit
   *         sequence is not complete to form a valid code point.
   * @throws IndexOutOfBoundsException
   *           if startIndex &lt; 0 or pos.getIndex() &lt; startIndex. Note that if
   *           pos.getIndex() == startIndex, the function does nothing and returns
   *           0.
   */
  public static int backward(final ParsingPosition pos, final CharSequence str,
      final int startIndex) {
    int index = pos.getIndex();
    if ((startIndex < 0) || (index < startIndex)) {
      throw new IndexOutOfBoundsException();
    }
    if (index == startIndex) {
      return 0;
    }
    --index;
    final char ch = str.charAt(index);
    if (isSingle(ch)) {
      pos.setIndex(index);
      return 1;
    } else if (! isTrailing(ch)) {
      pos.setErrorIndex(index);
      pos.setErrorCode(MALFORMED_UNICODE);
      return MALFORMED_UNICODE;
    }
    if (index == startIndex) {
      pos.setErrorIndex(index);
      pos.setErrorCode(INCOMPLETE_UNICODE);
      return INCOMPLETE_UNICODE;
    }
    --index;
    if (isLeading(str.charAt(index))) {
      pos.setIndex(index);
      return 2;
    } else {
      pos.setErrorIndex(index);
      pos.setErrorCode(MALFORMED_UNICODE);
      return MALFORMED_UNICODE;
    }
  }

  /**
   * Get a code point from a UTF-16 code unit sequence at a code point boundary
   * offset.
   *
   * <p>The offset must point to a code unit starting a valid code point (i.e.,
   * either a single code unit, or the leading code unit of a valid code point).
   * The function read all the code points consisting that code point, and
   * returns the number of code units have been read. If the offset does not
   * point to a starting code unit of a valid code point, or points to an
   * illegal code unit sequence, the function returns a negative integer
   * indicating the error.
   *
   * @param pos
   *          the index parsing position; after calling this function, it will
   *          be set to the new position.
   * @param buffer
   *          an array of chars.
   * @param endIndex
   *          the end current of the char array.
   * @return if the function successfully get a code point from the code unit
   *         sequence at the specified offset, returns the value of the code
   *         point (which is non-negative); otherwise, returns a negative
   *         integer indicating the error: {@link ErrorCode#MALFORMED_UNICODE}
   *         indicates the specified offset does not point to the starting of a
   *         legal code unit sequence consisting a valid code point;
   *         {@link ErrorCode#INCOMPLETE_UNICODE} indicates the input code unit
   *         sequence is not complete to form a valid code point.
   * @throws IndexOutOfBoundsException
   *           if endIndex &gt; buffer.length or pos.getIndex() &gt; endIndex. Note that
   *           if pos.getIndex() == endIndex, the function does nothing and returns
   *           0.
   */
  public static int getNext(final ParsingPosition pos, final char[] buffer,
      final int endIndex) {
    int index = pos.getIndex();
    if ((endIndex > buffer.length) || (index > endIndex)) {
      throw new IndexOutOfBoundsException();
    }
    if (index == endIndex) {
      return 0;
    }
    final char ch = buffer[index];
    if (isSingle(ch)) {
      pos.setIndex(index + 1);
      return ch;
    } else if (! isLeading(ch)) {
      pos.setErrorIndex(index);
      pos.setErrorCode(MALFORMED_UNICODE);
      return MALFORMED_UNICODE;
    } else {
      ++index;
      if (index >= endIndex) {
        pos.setErrorIndex(index);
        pos.setErrorCode(INCOMPLETE_UNICODE);
        return INCOMPLETE_UNICODE;
      }
      final char next_ch = buffer[index];
      if (isTrailing(next_ch)) {
        final int codePoint = composeSurrogatePair(ch, next_ch);
        pos.setIndex(index + 1);
        return codePoint;
      } else {
        pos.setErrorIndex(index);
        pos.setErrorCode(MALFORMED_UNICODE);
        return MALFORMED_UNICODE;
      }
    }
  }

  /**
   * Get a code point from a UTF-16 code unit sequence at a code point boundary
   * offset.
   *
   * <p>The offset must point to a code unit starting a valid code point (i.e.,
   * either a single code unit, or the leading code unit of a valid code point).
   * The function read all the code points consisting that code point, and
   * returns the number of code units have been read. If the offset does not
   * point to a starting code unit of a valid code point, or points to an
   * illegal code unit sequence, the function returns a negative integer
   * indicating the error.
   *
   * @param pos
   *          the index parsing position; after calling this function, it will
   *          be set to the new position.
   * @param str
   *          a character sequence.
   * @param endIndex
   *          the end current of the character sequence.
   * @return if the function successfully get a code point from the code unit
   *         sequence at the specified offset, returns the value of the code
   *         point (which is non-negative); otherwise, returns a negative
   *         integer indicating the error: {@link ErrorCode#MALFORMED_UNICODE}
   *         indicates the specified offset does not point to the starting of a
   *         legal code unit sequence consisting a valid code point;
   *         {@link ErrorCode#INCOMPLETE_UNICODE} indicates the input code unit
   *         sequence is not complete to form a valid code point.
   * @throws IndexOutOfBoundsException
   *           if endIndex &gt; buffer.length or pos.getIndex() &gt; endIndex. Note that
   *           if pos.getIndex() == endIndex, the function does nothing and returns
   *           0.
   */
  public static int getNext(final ParsingPosition pos, final CharSequence str,
      final int endIndex) {
    int index = pos.getIndex();
    if ((endIndex > str.length()) || (index > endIndex)) {
      throw new IndexOutOfBoundsException();
    }
    if (index == endIndex) {
      return 0;
    }
    final char ch = str.charAt(index);
    if (isSingle(ch)) {
      pos.setIndex(index + 1);
      return ch;
    } else if (! isLeading(ch)) {
      pos.setErrorIndex(index);
      pos.setErrorCode(MALFORMED_UNICODE);
      return MALFORMED_UNICODE;
    } else {
      ++index;
      if (index >= endIndex) {
        pos.setErrorIndex(index);
        pos.setErrorCode(INCOMPLETE_UNICODE);
        return INCOMPLETE_UNICODE;
      }
      final char next_ch = str.charAt(index);
      if (isTrailing(next_ch)) {
        final int codePoint = composeSurrogatePair(ch, next_ch);
        pos.setIndex(index + 1);
        return codePoint;
      } else {
        pos.setErrorIndex(index);
        pos.setErrorCode(MALFORMED_UNICODE);
        return MALFORMED_UNICODE;
      }
    }
  }

  /**
   * Move the offset of a UTF-16 code unit sequence from one code point boundary
   * to the previous one and get the code point between them.
   *
   * <p>The input offset must points to a position next to the ending code unit of
   * a valid code point (which is either a starting code unit of the next code
   * point, or the end of the whole code unit sequence). The function will move
   * the offset to the starting code unit of the previous code point, and reads
   * that code point, then returns the number of code units consisting that code
   * point (which is also the amount of decreasing of the offset). If the offset
   * does not point to a position next to the ending code unit of a valid code
   * point, or if the previous code unit sequence in illegal, the function
   * returns a negative integer indicating the error.
   *
   * @param pos
   *          the index parsing position; after calling this function, it will
   *          be set to the new position.
   * @param buffer
   *          an array of chars.
   * @param startIndex
   *          the start current of the char array.
   * @return if the function successfully get a code point from the code unit
   *         sequence at the specified index, returns the value of the code
   *         point (which is non-negative); otherwise, returns a negative
   *         integer indicating the error: {@link ErrorCode#MALFORMED_UNICODE}
   *         indicates the specified offset does not point to the next position
   *         of the ending of a legal code unit sequence consisting a valid code
   *         point; {@link ErrorCode#INCOMPLETE_UNICODE} indicates the input
   *         code unit sequence is not complete to form a valid Unicode code
   *         point.
   * @throws IndexOutOfBoundsException
   *           if startIndex &lt; 0 or pos.getIndex() &lt; startIndex. Note that if
   *           pos.getIndex() == startIndex, the function does nothing and returns
   *           0.
   */
  public static int getPrevious(final ParsingPosition pos, final char[] buffer,
      final int startIndex) {
    int index = pos.getIndex();
    if ((startIndex < 0) || (index < startIndex)) {
      throw new IndexOutOfBoundsException();
    }
    if (index == startIndex) {
      return 0;
    }
    --index;
    final char ch1 = buffer[index];
    if (isSingle(ch1)) {
      pos.setIndex(index);
      return ch1;
    } else if (! isTrailing(ch1)) {
      pos.setErrorIndex(index);
      pos.setErrorCode(MALFORMED_UNICODE);
      return MALFORMED_UNICODE;
    } else if (index == startIndex) {
      pos.setErrorIndex(index);
      pos.setErrorCode(INCOMPLETE_UNICODE);
      return INCOMPLETE_UNICODE;
    } else {
      --index;
      final char ch2 = buffer[index];
      if (isLeading(ch2)) {
        final int codePoint = composeSurrogatePair(ch2, ch1);
        pos.setIndex(index);
        return codePoint;
      } else {
        pos.setErrorIndex(index);
        pos.setErrorCode(MALFORMED_UNICODE);
        return MALFORMED_UNICODE;
      }
    }
  }

  /**
   * Move the offset of a UTF-16 code unit sequence from one code point boundary
   * to the previous one and get the code point between them.
   *
   * <p>The input offset must points to a position next to the ending code unit of
   * a valid code point (which is either a starting code unit of the next code
   * point, or the end of the whole code unit sequence). The function will move
   * the offset to the starting code unit of the previous code point, and reads
   * that code point, then returns the number of code units consisting that code
   * point (which is also the amount of decreasing of the offset). If the offset
   * does not point to a position next to the ending code unit of a valid code
   * point, or if the previous code unit sequence in illegal, the function
   * returns a negative integer indicating the error.
   *
   * @param pos
   *          the index parsing position; after calling this function, it will
   *          be set to the new position.
   * @param str
   *          a character sequence.
   * @param startIndex
   *          the start current of the character sequence.
   * @return if the function successfully get a code point from the code unit
   *         sequence at the specified index, returns the value of the code
   *         point (which is non-negative); otherwise, returns a negative
   *         integer indicating the error: ErrorCode::Malformed indicates the
   *         specified offset does not point to the next position of the ending
   *         of a legal code unit sequence consisting a valid code point;
   *         ErrorCode::Incomplete indicates the input code unit sequence is not
   *         complete to form a valid Unicode code point.
   * @throws IndexOutOfBoundsException
   *           if startIndex &lt; 0 or pos.getIndex() &lt; startIndex. Note that if
   *           pos.getIndex() == startIndex, the function does nothing and returns
   *           0.
   */
  public static int getPrevious(final ParsingPosition pos,
      final CharSequence str, final int startIndex) {
    int index = pos.getIndex();
    if ((startIndex < 0) || (index < startIndex)) {
      throw new IndexOutOfBoundsException();
    }
    if (index == startIndex) {
      return 0;
    }
    --index;
    final char ch1 = str.charAt(index);
    if (isSingle(ch1)) {
      pos.setIndex(index);
      return ch1;
    } else if (! isTrailing(ch1)) {
      pos.setErrorIndex(index);
      pos.setErrorCode(MALFORMED_UNICODE);
      return MALFORMED_UNICODE;
    } else if (index == startIndex) {
      pos.setErrorIndex(index);
      pos.setErrorCode(INCOMPLETE_UNICODE);
      return INCOMPLETE_UNICODE;
    } else {
      --index;
      final char ch2 = str.charAt(index);
      if (isLeading(ch2)) {
        final int codePoint = composeSurrogatePair(ch2, ch1);
        pos.setIndex(index);
        return codePoint;
      } else {
        pos.setErrorIndex(index);
        pos.setErrorCode(MALFORMED_UNICODE);
        return MALFORMED_UNICODE;
      }
    }
  }

  /**
   * Puts a code point to a UTF-16 code unit buffer, writes 1 to 4 code units.
   *
   * <p>The current points to the position where to put the code point and is
   * advanced by 1 to 2 after putting the code point. If the code point is not
   * valid or the buffer space is not sufficient, the function returns 0.
   *
   * @param codePoint
   *          the code point to be put.
   * @param index
   *          the index in the buffer where to put the code point. It must be
   *          within [0, buffer.length).
   * @param buffer
   *          a UTF-16 code unit buffer.
   * @param endIndex
   *          the end index of the byte array.
   * @return if the code point is successfully put into the buffer, returns the
   *         number of code units put into the buffer; otherwise, returns a
   *         negative integer indicating the error:
   *         {@link ErrorCode#MALFORMED_UNICODE} indicates the specified code
   *         point is not a valid Unicode code point, or is a surrogate;
   *         {@link ErrorCode#BUFFER_OVERFLOW} indicates the code unit buffer
   *         has no sufficient space to hold the result.
   * @throws IndexOutOfBoundsException
   *           if endIndex &gt; buffer.length or current &gt; endIndex. Note that if
   *           current == endIndex, the function will returns ErrorCode.OVERFLOW.
   */
  public static int put(final int codePoint, final int index,
      final char[] buffer, final int endIndex) {
    if ((endIndex > buffer.length) || (index > endIndex)) {
      throw new IndexOutOfBoundsException();
    }
    if (index == endIndex) {
      return BUFFER_OVERFLOW;
    }
    if (codePoint < SUPPLEMENTARY_MIN) {
      buffer[index] = (char) codePoint;
      return 1;
    } else if (codePoint > UNICODE_MAX) {
      return MALFORMED_UNICODE;
    } else {
      final int next_index = index + 1;
      if (next_index >= endIndex) {
        return BUFFER_OVERFLOW;
      } else {
        buffer[index] = (char) decomposeHighSurrogate(codePoint);
        buffer[next_index] = (char) decomposeLowSurrogate(codePoint);
        return 2;
      }
    }
  }

  /**
   * Puts a code point to a UTF-16 code unit buffer, writes 1 to 4 code units.
   *
   * <p>The current points to the position where to put the code point and is
   * advanced by 1 to 2 after putting the code point. If the code point is not
   * valid or the buffer space is not sufficient, the function returns 0.
   *
   * @param codePoint
   *          the code point to be put.
   * @param buffer
   *          a {@link Appendable} object used to store the UTF-16 code units.
   * @return if the code point is successfully put into the buffer, returns the
   *         number of code units put into the buffer; otherwise, returns a
   *         negative integer indicating the error:
   *         {@link ErrorCode#MALFORMED_UNICODE} indicates the specified code
   *         point is not a valid Unicode code point, or is a surrogate;
   *         {@link ErrorCode#BUFFER_OVERFLOW} indicates the code unit buffer
   *         has no sufficient space to hold the result.
   * @throws IOException
   *           if any I/O error occurs.
   */
  public static int put(final int codePoint, final Appendable buffer)
      throws IOException {
    if (codePoint < SUPPLEMENTARY_MIN) {
      buffer.append((char) codePoint);
      return 1;
    } else if (codePoint > UNICODE_MAX) {
      return MALFORMED_UNICODE;
    } else {
      buffer.append((char) decomposeHighSurrogate(codePoint));
      buffer.append((char) decomposeLowSurrogate(codePoint));
      return 2;
    }
  }

  public static void escape(final int codePoint, final Appendable appendable)
      throws IOException {
    if (codePoint < SUPPLEMENTARY_MIN) {
      appendable.append("\\u");
      toHex(codePoint, appendable);
    } else {
      appendable.append("\\u");
      toHex(decomposeHighSurrogate(codePoint), appendable);
      appendable.append("\\u");
      toHex(decomposeLowSurrogate(codePoint), appendable);
    }
  }

  public static String escape(final int codePoint) {
    final StringBuilder builder = new StringBuilder();
    try {
      escape(codePoint, builder);
    } catch (final IOException e) {
      // should never throw
      throw new UncheckedIOException(e);
    }
    return builder.toString();
  }

  private static void toHex(final int value, final Appendable appendable)
      throws IOException {
    // the last 4 HEX digits should always be output
    // stop checkstyle: MagicNumber
    appendable.append(UPPERCASE_DIGITS[(value >>> 12) & 0xF]);
    appendable.append(UPPERCASE_DIGITS[(value >>> 8) & 0xF]);
    appendable.append(UPPERCASE_DIGITS[(value >>> 4) & 0xF]);
    appendable.append(UPPERCASE_DIGITS[value & 0x0F]);
    // resume checkstyle: MagicNumber
  }
}