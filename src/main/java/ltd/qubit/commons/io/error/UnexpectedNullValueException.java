////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.io.error;

/**
 * Thrown to indicate an unexpected null value.
 *
 * @author Haixing Hu
 */
public class UnexpectedNullValueException extends InvalidFormatException {

  private static final long serialVersionUID = -995883752209016372L;

  public UnexpectedNullValueException() {
    super("Unexpected null value.");
  }
}
