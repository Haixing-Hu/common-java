////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.io;

import java.util.function.Consumer;

import javax.annotation.Nonnull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;

/**
 * 一个简单的打印器，将所有打印的消息记录到日志器中。
 *
 * @author 胡海星
 */
public class LoggerPrinter implements Printer {

  /**
   * 默认的日志级别。
   */
  public static final Level DEFAULT_LEVEL = Level.INFO;

  private final Consumer<String> log;

  /**
   * 构造一个指定日志器和日志级别的日志打印器。
   *
   * @param logger 要使用的日志器
   * @param level 日志级别
   */
  public LoggerPrinter(final Logger logger, final Level level) {
    switch (level) {
      case TRACE:
        log = logger::trace;
        break;
      case DEBUG:
        log = logger::debug;
        break;
      case INFO:
        log = logger::info;
        break;
      case WARN:
        log = logger::warn;
        break;
      case ERROR:
        log = logger::error;
        break;
      default:
        throw new IllegalArgumentException("Unknown log level: " + level);
    }
  }

  /**
   * 构造一个指定日志器的日志打印器，使用默认日志级别。
   *
   * @param logger 要使用的日志器
   */
  public LoggerPrinter(final Logger logger) {
    this(logger, DEFAULT_LEVEL);
  }

  /**
   * 构造一个指定日志器名称和日志级别的日志打印器。
   *
   * @param loggerName 日志器名称
   * @param level 日志级别
   */
  public LoggerPrinter(final String loggerName, final Level level) {
    this(LoggerFactory.getLogger(loggerName), level);
  }

  /**
   * 构造一个指定日志器名称的日志打印器，使用默认日志级别。
   *
   * @param loggerName 日志器名称
   */
  public LoggerPrinter(final String loggerName) {
    this(LoggerFactory.getLogger(loggerName), DEFAULT_LEVEL);
  }

  /**
   * 构造一个指定日志器类和日志级别的日志打印器。
   *
   * @param loggerClass 日志器类
   * @param level 日志级别
   */
  public LoggerPrinter(final Class<?> loggerClass, final Level level) {
    this(LoggerFactory.getLogger(loggerClass), level);
  }

  /**
   * 构造一个指定日志器类的日志打印器，使用默认日志级别。
   *
   * @param loggerClass 日志器类
   */
  public LoggerPrinter(final Class<?> loggerClass) {
    this(LoggerFactory.getLogger(loggerClass), DEFAULT_LEVEL);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public LoggerPrinter println(@Nonnull final String message) {
    log.accept(message);
    return this;
  }
}