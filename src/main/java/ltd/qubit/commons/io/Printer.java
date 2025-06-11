////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.io;

import javax.annotation.Nonnull;

/**
 * 一个简单的打印器，用于将消息打印到目标位置。
 *
 * @author 胡海星
 */
public interface Printer {
  /**
   * 向目标位置打印一个空行。
   *
   * @return
   *     此打印器。
   */
  default Printer println() {
    return println("");
  }

  /**
   * 向目标位置打印一行消息。
   * <p>
   * 消息后面会跟上一个行分隔符。
   *
   * @param line
   *     要打印的消息行。
   * @return
   *     此打印器。
   */
  Printer println(@Nonnull String line);

  /**
   * 向目标位置打印一行格式化的消息。
   * <p>
   * 消息后面会跟上一个行分隔符。
   *
   * @param format
   *     格式字符串。
   * @param args
   *     格式字符串的参数。
   * @return
   *     此打印器。
   */
  default Printer println(@Nonnull final String format, final Object... args) {
    return println(String.format(format, args));
  }
}