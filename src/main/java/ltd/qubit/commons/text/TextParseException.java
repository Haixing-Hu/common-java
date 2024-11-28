////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text;

import java.text.ParseException;

/**
 * Thrown to indicate a error occurs during parsing the text.
 *
 * <p>This class extends {@link ParsingException}, except it provides more
 * detailed information about the parsing and it will automatically build the
 * error message from those information.
 *
 * @author Haixing Hu
 */
public class TextParseException extends ParseException {

  private static final long serialVersionUID = 1897366428017592956L;

  private final String text;
  private final String reason;
  private final int startIndex;
  private final int endIndex;

  public TextParseException(final CharSequence text, final int errorCode) {
    this(text, 0, text.length(), ErrorCode.getMessage(errorCode), 0);
  }

  public TextParseException(final CharSequence text, final int errorCode,
      final int errorIndex) {
    this(text, 0, text.length(), ErrorCode.getMessage(errorCode), errorIndex);
  }

  public TextParseException(final CharSequence text, final int startIndex,
      final int endIndex, final int errorCode, final int errorIndex) {
    this(text, startIndex, endIndex, ErrorCode.getMessage(errorCode),
        errorIndex);
  }

  public TextParseException(final CharSequence text, final String reason) {
    this(text, 0, text.length(), reason, 0);
  }

  public TextParseException(final CharSequence text, final String reason,
      final int errorIndex) {
    this(text, 0, text.length(), reason, errorIndex);
  }

  public TextParseException(final CharSequence text, final int startIndex,
      final int endIndex, final String reason, final int errorIndex) {
    super(buildErrorMessage(text, startIndex, endIndex, reason), errorIndex);
    this.text = text.toString();
    this.reason = reason;
    this.startIndex = startIndex;
    this.endIndex = endIndex;
  }

  public TextParseException(final CharSequence text, final ParsingPosition pos) {
    this(text, 0, text.length(), pos);
  }

  public TextParseException(final CharSequence text, final int startIndex,
      final ParsingPosition pos) {
    this(text, startIndex, text.length(), pos);
  }

  public TextParseException(final CharSequence text, final int startIndex,
      final int endIndex, final ParsingPosition pos) {
    super(buildErrorMessage(text, startIndex, endIndex,
        ErrorCode.getMessage(pos.getErrorCode())), pos.getErrorIndex());
    this.text = text.toString();
    this.reason = ErrorCode.getMessage(pos.getErrorCode());
    this.startIndex = startIndex;
    this.endIndex = endIndex;
  }

  public String getText() {
    return text;
  }

  public String getReason() {
    return reason;
  }

  public int getStartIndex() {
    return startIndex;
  }

  public int getEndIndex() {
    return endIndex;
  }

  private static String buildErrorMessage(final CharSequence text,
      final int startIndex, final int endIndex, final String reason) {
    final StringBuilder builder = new StringBuilder();
    if ((startIndex == 0) && (endIndex == text.length())) {
      builder.append("Error while parsing the string '")
             .append(text)
             .append("': ")
             .append(reason);
    } else {
      builder.append("Error while parsing the sub-string from ")
             .append(startIndex)
             .append(" to ")
             .append(endIndex)
             .append(" of '")
             .append(text)
             .append("': ")
             .append(reason);
    }
    return builder.toString();
  }
}
