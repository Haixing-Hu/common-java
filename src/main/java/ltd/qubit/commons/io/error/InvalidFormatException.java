////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.io.error;

/**
 * 当格式无效时抛出此异常。
 *
 * @author 胡海星
 */
public class InvalidFormatException extends SerializationException {

  private static final long serialVersionUID = 5893399775330552816L;

  /**
   * 构造一个 {@link InvalidFormatException}。
   */
  public InvalidFormatException() {
    super("Invalid format. ");
  }

  /**
   * 构造一个 {@link InvalidFormatException}。
   *
   * @param cause
   *     导致此异常的原因。
   */
  public InvalidFormatException(final Throwable cause) {
    super(cause);
  }

  /**
   * 构造一个 {@link InvalidFormatException}。
   *
   * @param message
   *     详细信息。
   */
  public InvalidFormatException(final String message) {
    super(message);
  }
}