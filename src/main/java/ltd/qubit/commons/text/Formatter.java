////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text;

/**
 * A generic interface for formatters.
 *
 * @param <INPUT>
 *          the type of the formatting input.
 * @param <OUTPUT>
 *          the type of the formatting output.
 * @author Haixing Hu
 */
public interface Formatter<INPUT, OUTPUT> {

  /**
   * Formats an input to an output.
   *
   * @param input
   *          the input object to be formatted.
   * @return the output object as the formatting result.
   */
  OUTPUT format(INPUT input);
}