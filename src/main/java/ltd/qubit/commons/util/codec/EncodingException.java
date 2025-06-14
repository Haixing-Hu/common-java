////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.codec;

import static ltd.qubit.commons.lang.ThrowableUtils.getRootCauseMessage;

/**
 * Thrown when there is a failure condition during the encoding process.
 * <p>
 * This exception is thrown when an {@link Encoder} encounters a encoding
 * specific exception such as invalid data, inability to calculate a checksum,
 * characters outside of the expected range.
 * </p>
 *
 * @author Haixing Hu
 */
public class EncodingException extends Exception {

  private static final long serialVersionUID = - 4826911300810073359L;

  /**
   * Constructs a new exception with {@code null} as its detail message.
   * The cause is not initialized, and may subsequently be initialized by a call
   * to {@link #initCause}.
   */
  public EncodingException() {
  }

  /**
   * Constructs a new exception with the specified detail message. The cause is
   * not initialized, and may subsequently be initialized by a call to
   * {@link #initCause}.
   *
   * @param message
   *          a useful message relating to the encoder specific error.
   */
  public EncodingException(final String message) {
    super(message);
  }

  /**
   * Constructs a new exception with the specified detail message and cause.
   *
   * <p>Note that the detail message associated with {@code cause} is not
   * automatically incorporated into this exception's detail message.
   *
   * @param message
   *          The detail message which is saved for later retrieval by the
   *          {@link #getMessage()} method.
   * @param cause
   *          The cause which is saved for later retrieval by the
   *          {@link #getCause()} method. A {@code null} value is
   *          permitted, and indicates that the cause is nonexistent or unknown.
   */
  public EncodingException(final String message, final Throwable cause) {
    super(message, cause);
  }

  /**
   * Constructs a new exception with the specified cause and a detail message of
   * {@code (cause==null ?
   * null : cause.toString())} (which typically contains the class and
   * detail message of {@code cause}). This constructor is useful for
   * exceptions that are little more than wrappers for other throwables.
   *
   * @param cause
   *          The cause which is saved for later retrieval by the
   *          {@link #getCause()} method. A {@code null} value is
   *          permitted, and indicates that the cause is nonexistent or unknown.
   */
  public EncodingException(final Throwable cause) {
    super(getRootCauseMessage(cause), cause);
  }
}