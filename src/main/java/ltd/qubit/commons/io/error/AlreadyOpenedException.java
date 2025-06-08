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
 * 抛出此异常表示文件或设备已经被打开。
 *
 * @author 胡海星
 */
public class AlreadyOpenedException extends IOException {

  private static final long serialVersionUID = 429834574478758972L;

  /**
   * 构造一个表示文件或设备已经被打开的异常。
   */ 
  public AlreadyOpenedException() {
    super("The object has already been opened.");
  }

  /**
   * 构造一个表示文件或设备已经被打开的异常。
   *
   * @param message
   *     异常消息。
   */
  public AlreadyOpenedException(final String message) {
    super(message);
  }
}
