/// /////////////////////////////////////////////////////////////////////////////
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
 * A printer which prints messages to multiple destinations.
 *
 * @author Haixing Hu
 */
public class MultiplePrinter implements Printer {

  private final List<Printer> printers = new ArrayList<>();

  public MultiplePrinter() {
    // do nothing
  }

  public MultiplePrinter(final Printer ... printers) {
    Collections.addAll(this.printers, printers);
  }

  public MultiplePrinter add(final Printer printer) {
    printers.add(printer);
    return this;
  }

  public MultiplePrinter add(final PrintStream stream) {
    printers.add(new PrintStreamPrinter(stream));
    return this;
  }

  public MultiplePrinter add(final Logger logger, final Level level) {
    printers.add(new LoggerPrinter(logger, level));
    return this;
  }

  @Override
  public MultiplePrinter println(@Nonnull final String line) {
    for (final Printer printer : printers) {
      printer.println(line);
    }
    return this;
  }
}