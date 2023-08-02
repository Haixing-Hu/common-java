////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.error;

import ltd.qubit.commons.util.pair.KeyValuePair;

/**
 * Thrown to indicate that a content type is not supported.
 *
 * @author Haixing Hu
 */
public class UnsupportedContentTypeException extends BusinessLogicException {

  private static final long serialVersionUID = 1526366180287500174L;

  private final String contentType;

  public UnsupportedContentTypeException(final String contentType) {
    super(ErrorType.REQUEST_ERROR, ErrorCode.UNSUPPORTED_CONTENT_TYPE,
        new KeyValuePair("content_type", contentType));
    this.contentType = contentType;
  }

  public final String getContentType() {
    return contentType;
  }
}
