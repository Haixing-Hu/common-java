////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.concurrent;

import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.io.PrintStreamPrinter;

/**
 * 将进度报告给{@link System#out}的{@link ProgressReporter}。
 *
 * @author 胡海星
 */
@Immutable
public class ConsoleProgressReporter extends PrinterProgressReporter {

  public static final ConsoleProgressReporter INSTANCE = new ConsoleProgressReporter();

  /**
   * 构造一个 {@link ConsoleProgressReporter}。
   */
  public ConsoleProgressReporter() {
    super(new PrintStreamPrinter(System.out));
  }
}