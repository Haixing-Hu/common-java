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
 * 抛出此异常表示文件的版本签名无效。
 *
 * @author 胡海星
 */
public class InvalidSignatureException extends InvalidFormatException {

  private static final long serialVersionUID = 3066517065925815105L;

  /**
   * 构造一个 {@link InvalidSignatureException}。
   */
  public InvalidSignatureException() {
    super("The version signature of the file is invalid.");
  }

  /**
   * 构造一个 {@link InvalidSignatureException}。
   *
   * @param message
   *     详细信息。
   */
  public InvalidSignatureException(final String message) {
    super(message);
  }
}