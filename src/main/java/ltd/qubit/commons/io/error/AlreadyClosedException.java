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
 * 抛出此异常表示文件或设备已经被关闭。
 *
 * @author 胡海星
 */
public class AlreadyClosedException extends IOException {

  private static final long serialVersionUID = - 6574812183392346958L;

  /**
   * 构造一个表示文件、目录或设备已经关闭的异常。
   */
  public AlreadyClosedException() {
    super("The file, directory or device has already been closed.");
  }

  /**
   * 构造一个表示文件、目录或设备已经关闭的异常。
   *
   * @param message
   *     异常消息。
   */
  public AlreadyClosedException(final String message) {
    super(message);
  }
}
