////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.error;

import java.io.IOException;
import java.io.Serial;

import ltd.qubit.commons.util.pair.KeyValuePair;

/**
 * Thrown to indicate that the JSON format is invalid.
 *
 * @author Haixing Hu
 */
public class InvalidJsonFormatException extends IOException
    implements ErrorInfoConvertable {

  @Serial
  private static final long serialVersionUID = -6779062496130135614L;

  private String json = "<unknown>";

  public InvalidJsonFormatException() {
    super("The JSON format is invalid.");
  }

  public InvalidJsonFormatException(final String json) {
    super("The JSON format is invalid: " + json);
    this.json = json;
  }

  public InvalidJsonFormatException(final String json, final Throwable cause) {
    super(cause.getMessage(), cause);
    this.json = json;
  }

  @Override
  public ErrorInfo toErrorInfo() {
    return new ErrorInfo("IO_ERROR", "INVALID_JSON_FORMAT",
        new KeyValuePair("json", json),
        new KeyValuePair("message", getMessage()));
  }

  public String getJson() {
    return json;
  }
}
