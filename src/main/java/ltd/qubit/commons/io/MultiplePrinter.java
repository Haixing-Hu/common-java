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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nonnull;

import org.slf4j.Logger;
import org.slf4j.event.Level;

/**
 * 一个将消息打印到多个目标的打印器。
 *
 * @author 胡海星
 */
public class MultiplePrinter implements Printer {

  private final List<Printer> printers = new ArrayList<>();

  /**
   * 构造一个空的多重打印器。
   */
  public MultiplePrinter() {
    // do nothing
  }

  /**
   * 构造一个包含指定打印器的多重打印器。
   *
   * @param printers 要添加的打印器
   */
  public MultiplePrinter(final Printer ... printers) {
    Collections.addAll(this.printers, printers);
  }

  /**
   * 添加一个打印器。
   *
   * @param printer 要添加的打印器
   * @return 此多重打印器实例，用于链式调用
   */
  public MultiplePrinter add(final Printer printer) {
    printers.add(printer);
    return this;
  }

  /**
   * 添加一个打印流作为打印器。
   *
   * @param stream 要添加的打印流
   * @return 此多重打印器实例，用于链式调用
   */
  public MultiplePrinter add(final PrintStream stream) {
    printers.add(new PrintStreamPrinter(stream));
    return this;
  }

  /**
   * 添加一个日志器作为打印器。
   *
   * @param logger 要添加的日志器
   * @param level 日志级别
   * @return 此多重打印器实例，用于链式调用
   */
  public MultiplePrinter add(final Logger logger, final Level level) {
    printers.add(new LoggerPrinter(logger, level));
    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public MultiplePrinter println(@Nonnull final String line) {
    for (final Printer printer : printers) {
      printer.println(line);
    }
    return this;
  }
}