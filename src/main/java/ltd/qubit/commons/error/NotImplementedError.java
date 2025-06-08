////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.error;

/**
 * 抛出此错误以指示某个功能尚未实现。
 *
 * @author 胡海星
 */
public class NotImplementedError extends Error implements ErrorInfoConvertable {

  private static final long serialVersionUID = -6961719822444213578L;

  /**
   * 构造一个新的未实现错误。
   */
  public NotImplementedError() {
    super("The specified function is not implemented yet.");
  }

  /**
   * 构造一个带有指定消息的未实现错误。
   *
   * @param message
   *     错误消息。
   */
  public NotImplementedError(final String message) {
    super(message);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ErrorInfo toErrorInfo() {
    return new ErrorInfo("SERVER_ERROR", "NOT_IMPLEMENTED", this);
  }
}