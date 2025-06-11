////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.io.error;

import java.io.IOException;

/**
 * 抛出此异常表示文件或设备正在关闭。
 *
 * @author 胡海星
 */
public class IsClosingException extends IOException {

  private static final long serialVersionUID = - 4707122216334525975L;

  /**
   * 构造一个 {@link IsClosingException}。
   */
  public IsClosingException() {
    super("The file, directory or device is being closed.");
  }

  /**
   * 构造一个 {@link IsClosingException}。
   *
   * @param message
   *     详细信息。
   */
  public IsClosingException(final String message) {
    super(message);
  }
}