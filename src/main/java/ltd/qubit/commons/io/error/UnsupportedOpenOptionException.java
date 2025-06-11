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

import ltd.qubit.commons.io.OpenOption;

/**
 * 抛出此异常表示函数不支持某个打开选项。
 *
 * @author 胡海星
 */
public class UnsupportedOpenOptionException extends IOException {

  private static final long serialVersionUID = 2438085249149373956L;

  private final OpenOption option;

  /**
   * 构造一个 {@code UnsupportedOpenOptionException}。
   *
   * @param option
   *     不支持的打开选项。
   */
  public UnsupportedOpenOptionException(final OpenOption option) {
    super("The specified open option is not supported by this function: "
        + option.name());
    this.option = option;
  }

  /**
   * 获取此异常中不支持的打开选项。
   *
   * @return
   *     此异常中不支持的打开选项。
   */
  public OpenOption option() {
    return option;
  }

}