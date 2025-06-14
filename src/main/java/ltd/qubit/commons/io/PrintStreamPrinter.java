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
 * A {@link Printer} that prints the messages to a {@link PrintStream}.
 *
 * @author Haixing Hu
 */
public class PrintStreamPrinter implements Printer {

  private final PrintStream stream;

  public PrintStreamPrinter(final PrintStream stream) {
    this.stream = stream;
  }

  @Override
  public PrintStreamPrinter println(@Nonnull final String message) {
    stream.println(message);
    return this;
  }
}