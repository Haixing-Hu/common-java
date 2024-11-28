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
 * A extension of the {@link ParseException} which could be supplied with an
 * additional error message.
 *
 * @author Haixing Hu
 */
public class ParsingException extends ParseException {

  private static final long serialVersionUID = -3592758755666565544L;

  private final String text;
  private final String message;

  public ParsingException(final String text, final int errorOffset) {
    super(text, errorOffset);
    this.text = text;
    message = null;
  }

  public ParsingException(final String text, final int errorOffset,
      final String message) {
    super(text, errorOffset);
    this.text = text;
    this.message = message;
  }

  @Override
  public String getMessage() {
    String result = "An error occurs at the offset "
        + getErrorOffset()
        + " while parsing the text '" + text + "'";
    if (message != null) {
      result += ": " + message;
    }
    return result;
  }
}
