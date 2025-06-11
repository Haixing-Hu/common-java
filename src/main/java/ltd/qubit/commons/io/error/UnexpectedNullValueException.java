////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.io.error;

import java.io.Serial;

/**
 * 抛出此异常表示出现意外的空值。
 *
 * @author 胡海星
 */
public class UnexpectedNullValueException extends InvalidFormatException {

  @Serial
  private static final long serialVersionUID = -995883752209016372L;

  /**
   * 构造一个 {@code UnexpectedNullValueException}.
   */
  public UnexpectedNullValueException() {
    super("Unexpected null value.");
  }
}