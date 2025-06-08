////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
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
 * 抛出以指示 JSON 格式无效。
 *
 * @author 胡海星
 */
public class InvalidJsonFormatException extends IOException
    implements ErrorInfoConvertable {

  @Serial
  private static final long serialVersionUID = -6779062496130135614L;

  private String json = "<unknown>";

  /**
   * 构造一个新的 {@link InvalidJsonFormatException}。
   */
  public InvalidJsonFormatException() {
    super("The JSON format is invalid.");
  }

  /**
   * 使用指定的 JSON 字符串构造一个新的 {@link InvalidJsonFormatException}。
   *
   * @param json
   *     无效的 JSON 字符串。
   */
  public InvalidJsonFormatException(final String json) {
    super("The JSON format is invalid: " + json);
    this.json = json;
  }

  /**
   * 使用指定的 JSON 字符串和原因构造一个新的 {@link InvalidJsonFormatException}。
   *
   * @param json
   *     无效的 JSON 字符串。
   * @param cause
   *     原因。
   */
  public InvalidJsonFormatException(final String json, final Throwable cause) {
    super(cause.getMessage(), cause);
    this.json = json;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ErrorInfo toErrorInfo() {
    return new ErrorInfo("IO_ERROR", "INVALID_JSON_FORMAT",
        new KeyValuePair("json", json),
        new KeyValuePair("message", getMessage()));
  }

  /**
   * 获取无效的 JSON 字符串。
   *
   * @return 无效的 JSON 字符串。
   */
  public String getJson() {
    return json;
  }
}