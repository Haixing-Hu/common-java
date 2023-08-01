////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text;

/**
 * A generic interface for parsers.
 *
 * @param <INPUT>
 *     the type of the parsing input.
 * @param <OUTPUT>
 *     the type of the parsing output.
 * @author Haixing Hu
 */
public interface Parser<INPUT, OUTPUT> {

  /**
   * Parses an input to an output.
   *
   * @param input
   *     the input object to be parsed.
   * @return the output object as the parsing result.
   * @throws ParsingException
   *     if any error occurs.
   */
  OUTPUT parse(INPUT input) throws ParsingException;
}
