////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text;

/**
 * Defines the constants of error code related to the text coding, parsing, and
 * formatting.
 *
 * <p>Note that all error code, except for {@link #NONE}, are negative
 * integers.
 * The {@link #NONE} indicating no error, whose value is 0.
 *
 * @author Haixing Hu
 */
public final class ErrorCode {

  /**
   * Indicating no error.
   */
  public static final int NONE = 0;

  /**
   * Indicating an unknown error.
   */
  public static final int UNKNOWN_ERROR = -1;

  /**
   * Indicating the buffer is overflow.
   */
  public static final int BUFFER_OVERFLOW = -2;

  /**
   * Indicating the Unicode character is unmappable in the specified charset.
   */
  public static final int UNMAPPABLE_CHAR = -3;

  /**
   * Indicating the Unicode code unit sequence is malformed.
   */
  public static final int MALFORMED_UNICODE = -4;

  /**
   * Indicating the Unicode code unit sequence is incomplete.
   */
  public static final int INCOMPLETE_UNICODE = -5;

  /**
   * Indicating the text segment to be parsed contains no text representation of
   * the desired value.
   */
  public static final int EMPTY_VALUE = -6;

  /**
   * Indicating the parsed numeric value overflows.
   */
  public static final int NUMBER_OVERFLOW = -7;

  /**
   * Indicating the parsed numeric value underflows.
   */
  public static final int NUMBER_UNDERFLOW = -8;

  /**
   * Indicating that the numeric value to be parsed has an invalid digit
   * grouping.
   */
  public static final int INVALID_GROUPING = -9;

  /**
   * Indicating that the text to be parsed has an invalid syntax.
   */
  public static final int INVALID_SYNTAX = -10;

  private static final String[] ERROR_MESSAGE = {
      "No error.",
      "Unknown error.",
      "The buffer overflows.",
      "There is an Unicode character which is unmappable in the specified charset.",
      "The Unicode code unit sequence is malformed.",
      "The Unicode code unit sequence is incomplete.",
      "There is no text representation of the value to be parsed in the specified text segment.",
      "The numeric value to be parsed overflows.",
      "The numeric value to be parsed underflows.",
      "The numeric value to be parsed has an invalid digit grouping.",
      "The text to be parsed has an invalid syntax.",
  };

  public static String getMessage(final int errorCode) {
    int index = (-errorCode);
    if ((index < 0) || (index >= ERROR_MESSAGE.length)) {
      index = (-UNKNOWN_ERROR);
    }
    return ERROR_MESSAGE[index];
  }
}
