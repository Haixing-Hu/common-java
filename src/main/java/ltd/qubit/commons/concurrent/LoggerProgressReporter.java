////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.concurrent;

import org.slf4j.Logger;

import ltd.qubit.commons.io.LoggerPrinter;

/**
 * A {@link ProgressReporter} that reports the progress to a {@link Logger}.
 *
 * @author Haixing Hu
 */
public class LoggerProgressReporter extends PrinterProgressReporter {

  LoggerProgressReporter(final Logger logger) {
    super(new LoggerPrinter(logger));
  }
}
