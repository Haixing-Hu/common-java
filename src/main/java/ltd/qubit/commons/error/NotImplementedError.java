////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.error;

/**
 * Thrown to indicate that a function is not implemented.
 *
 * @author Haixing Hu
 */
public class NotImplementedError extends Error {

  private static final long serialVersionUID = -6961719822444213578L;

  public NotImplementedError() {
    super("The specified function is not implemented yet.");
  }

  public NotImplementedError(final String message) {
    super(message);
  }
}
