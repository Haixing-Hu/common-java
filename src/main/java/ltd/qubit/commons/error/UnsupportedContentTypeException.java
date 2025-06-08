////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.error;

import java.io.Serial;

import ltd.qubit.commons.util.pair.KeyValuePair;

/**
 * 抛出此异常以指示内容类型不受支持。
 *
 * @author 胡海星
 */
public class UnsupportedContentTypeException extends RuntimeException
    implements ErrorInfoConvertable {

  @Serial
  private static final long serialVersionUID = 1526366180287500174L;

  private final String contentType;

  /**
   * 构造一个新的不支持内容类型异常。
   *
   * @param contentType
   *     不支持的内容类型。
   */
  public UnsupportedContentTypeException(final String contentType) {
    super("Unsupported content type: " + contentType);
    this.contentType = contentType;
  }

  /**
   * 获取不支持的内容类型。
   *
   * @return
   *     不支持的内容类型。
   */
  public final String getContentType() {
    return contentType;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ErrorInfo toErrorInfo() {
    return new ErrorInfo("REQUEST_ERROR", "UNSUPPORTED_CONTENT_TYPE",
        new KeyValuePair("content_type", contentType),
        new KeyValuePair("message", getMessage()));
  }
}