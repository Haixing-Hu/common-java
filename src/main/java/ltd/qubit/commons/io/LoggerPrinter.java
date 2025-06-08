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
 * A simple printer that logs all printed messages to a logger.
 *
 * @author Haixing Hu
 */
public class LoggerPrinter implements Printer {

  /**
   * The default logging level.
   */
  public static final Level DEFAULT_LEVEL = Level.INFO;

  private final Consumer<String> log;

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

  public LoggerPrinter(final Logger logger) {
    this(logger, DEFAULT_LEVEL);
  }

  public LoggerPrinter(final String loggerName, final Level level) {
    this(LoggerFactory.getLogger(loggerName), level);
  }

  public LoggerPrinter(final String loggerName) {
    this(LoggerFactory.getLogger(loggerName), DEFAULT_LEVEL);
  }

  public LoggerPrinter(final Class<?> loggerClass, final Level level) {
    this(LoggerFactory.getLogger(loggerClass), level);
  }

  public LoggerPrinter(final Class<?> loggerClass) {
    this(LoggerFactory.getLogger(loggerClass), DEFAULT_LEVEL);
  }

  @Override
  public LoggerPrinter println(@Nonnull final String message) {
    log.accept(message);
    return this;
  }
}