////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.io;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ltd.qubit.commons.error.InitializationError;

/**
 * 提供有关日志操作的实用工具。
 *
 * @author 胡海星
 */
public final class LogUtils {

  private static final Logger LOGGER = LoggerFactory.getLogger(LogUtils.class);

  private static final Method TRACE;
  private static final Method DEBUG;
  private static final Method INFO;
  private static final Method WARN;
  private static final Method ERROR;

  static {
    try {
      TRACE = Logger.class.getMethod("trace", String.class);
      DEBUG = Logger.class.getMethod("debug", String.class);
      INFO = Logger.class.getMethod("info", String.class);
      WARN = Logger.class.getMethod("warn", String.class);
      ERROR = Logger.class.getMethod("error", String.class);
    } catch (final Throwable e) {
      LOGGER.error("Cannot init the log methods.", e);
      throw new InitializationError(e);
    }
  }

  /**
   * 获取一个 TRACE 级别的日志输出流。
   *
   * @param logger 要使用的日志器
   * @return 一个 PrintStream，写入时会以 TRACE 级别记录日志行
   */
  public static PrintStream getTraceStream(final Logger logger) {
    return getLogStream(logger, TRACE);
  }

  /**
   * 获取一个 DEBUG 级别的日志输出流。
   *
   * @param logger 要使用的日志器
   * @return 一个 PrintStream，写入时会以 DEBUG 级别记录日志行
   */
  public static PrintStream getDebugStream(final Logger logger) {
    return getLogStream(logger, DEBUG);
  }

  /**
   * 获取一个 INFO 级别的日志输出流。
   *
   * @param logger 要使用的日志器
   * @return 一个 PrintStream，写入时会以 INFO 级别记录日志行
   */
  public static PrintStream getInfoStream(final Logger logger) {
    return getLogStream(logger, INFO);
  }

  /**
   * 获取一个 WARN 级别的日志输出流。
   *
   * @param logger 要使用的日志器
   * @return 一个 PrintStream，写入时会以 WARN 级别记录日志行
   */
  public static PrintStream getWarnStream(final Logger logger) {
    return getLogStream(logger, WARN);
  }

  /**
   * 获取一个 ERROR 级别的日志输出流。
   *
   * @param logger 要使用的日志器
   * @return 一个 PrintStream，写入时会以 ERROR 级别记录日志行
   */
  public static PrintStream getErrorStream(final Logger logger) {
    return getLogStream(logger, ERROR);
  }

  /**
   * 返回一个流，当写入时会添加日志行。
   *
   * @param logger 要使用的日志器
   * @param method 要调用的日志方法
   * @return 一个 PrintStream，写入时会记录日志
   */
  private static PrintStream getLogStream(final Logger logger, final Method method) {
    return new PrintStream(new ByteArrayOutputStream() {
      private int scan = 0;

      private boolean hasNewline() {
        for (; scan < count; ++scan) {
          if (buf[scan] == '\n') {
            return true;
          }
        }
        return false;
      }

      @Override
      public void flush() {
        if (!hasNewline()) {
          return;
        }
        try {
          method.invoke(logger, toString());
        } catch (final Exception e) {
          LOGGER.error("Cannot log with method [{}].", method, e);
        }
        reset();
        scan = 0;
      }
    }, true);
  }

}