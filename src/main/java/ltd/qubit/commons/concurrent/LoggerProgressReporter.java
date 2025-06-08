////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.concurrent;

import org.slf4j.Logger;

import ltd.qubit.commons.io.LoggerPrinter;

/**
 * 将进度报告给{@link Logger}的{@link ProgressReporter}。
 *
 * @author 胡海星
 */
public class LoggerProgressReporter extends PrinterProgressReporter {

  /**
   * 构造一个{@link LoggerProgressReporter}。
   *
   * @param logger
   *     指定的{@link Logger}。
   */
  LoggerProgressReporter(final Logger logger) {
    super(new LoggerPrinter(logger));
  }
}