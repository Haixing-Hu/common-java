////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.io;

import java.io.PrintStream;

import javax.annotation.Nonnull;

/**
 * 一个将消息打印到 {@link PrintStream} 的 {@link Printer}。
 *
 * @author 胡海星
 */
public class PrintStreamPrinter implements Printer {

  private final PrintStream stream;

  /**
   * 构造一个使用指定打印流的打印器。
   *
   * @param stream 用于输出的打印流。
   */
  public PrintStreamPrinter(final PrintStream stream) {
    this.stream = stream;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public PrintStreamPrinter println(@Nonnull final String message) {
    stream.println(message);
    return this;
  }
}