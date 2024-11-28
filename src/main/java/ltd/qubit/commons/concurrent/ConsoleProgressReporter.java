////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.concurrent;

import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.io.io.PrintStreamPrinter;

/**
 * A {@link ProgressReporter} that reports the progress to the {@link System#out}.
 *
 * @author Haixing Hu
 */
@Immutable
public class ConsoleProgressReporter extends PrinterProgressReporter {

  public static final ConsoleProgressReporter INSTANCE = new ConsoleProgressReporter();

  public ConsoleProgressReporter() {
    super(new PrintStreamPrinter(System.out));
  }
}
