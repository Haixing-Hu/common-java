////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.io;

import jakarta.validation.constraints.NotNull;

/**
 * A simple printer that prints messages to a destination.
 *
 * @author Haixing Hu
 */
public interface Printer {
  /**
   * Prints an empty line to the destination.
   *
   * @return
   *     this printer.
   */
  default Printer println() {
    return println("");
  }

  /**
   * Prints a line of message to the destination.
   * <p>
   * The message will be followed by a line separator.
   *
   * @param line
   *     the line of message to be printed.
   * @return
   *     this printer.
   */
  Printer println(String line);

  /**
   * Prints a line of formatted message to the destination.
   * <p>
   * The message will be followed by a line separator.
   *
   * @param format
   *     the format string.
   * @param args
   *     the arguments of the format string.
   * @return
   *     this printer.
   */
  default Printer printf(@NotNull final String format, final Object... args) {
    return println(String.format(format, args));
  }
}
