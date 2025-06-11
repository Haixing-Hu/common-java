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
 * 抛出此异常表示文件或设备尚未打开。
 *
 * @author 胡海星
 */
public class NotOpenedException extends IOException {

  private static final long serialVersionUID = - 7810592920579567878L;

  /**
   * 构造一个 {@link NotOpenedException}。
   */
  public NotOpenedException() {
    super("The object has not been opened.");
  }

  /**
   * 构造一个 {@link NotOpenedException}。
   *
   * @param message
   *     详细信息。
   */
  public NotOpenedException(final String message) {
    super(message);
  }
}