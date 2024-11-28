////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.error;

import java.io.Serial;

import ltd.qubit.commons.util.pair.KeyValuePair;

/**
 * Thrown to indicate that a content type is not supported.
 *
 * @author Haixing Hu
 */
public class UnsupportedContentTypeException extends RuntimeException
    implements ErrorInfoConvertable {

  @Serial
  private static final long serialVersionUID = 1526366180287500174L;

  private final String contentType;

  public UnsupportedContentTypeException(final String contentType) {
    super("Unsupported content type: " + contentType);
    this.contentType = contentType;
  }

  public final String getContentType() {
    return contentType;
  }

  @Override
  public ErrorInfo toErrorInfo() {
    return new ErrorInfo("REQUEST_ERROR", "UNSUPPORTED_CONTENT_TYPE",
        new KeyValuePair("content_type", contentType),
        new KeyValuePair("message", getMessage()));
  }
}
