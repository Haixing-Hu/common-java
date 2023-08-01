////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.io;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Method;

import ltd.qubit.commons.error.InitializationError;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Provides utilities about the logging operations.
 *
 * @author Haixing Hu
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

  public static PrintStream getTraceStream(final Logger logger) {
    return getLogStream(logger, TRACE);
  }

  public static PrintStream getDebugStream(final Logger logger) {
    return getLogStream(logger, DEBUG);
  }

  public static PrintStream getInfoStream(final Logger logger) {
    return getLogStream(logger, INFO);
  }

  public static PrintStream getWarnStream(final Logger logger) {
    return getLogStream(logger, WARN);
  }

  public static PrintStream getErrorStream(final Logger logger) {
    return getLogStream(logger, ERROR);
  }

  /**
   * Returns a stream that, when written to, adds log lines.
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
