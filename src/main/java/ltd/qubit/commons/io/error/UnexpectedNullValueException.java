////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
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
