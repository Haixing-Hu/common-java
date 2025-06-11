////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.io.error;

import java.io.IOException;

/**
 * 抛出此异常以指示寻道位置无效。
 *
 * @author 胡海星
 */
public class InvalidSeekPositionException extends IOException {

  private static final long serialVersionUID = 4459304856821158328L;

  public static final String MESSAGE = "Invalid seek position: ";

  private final long pos;

  /**
   * 构造一个 {@link InvalidSeekPositionException}。
   *
   * @param pos
   *     无效的寻道位置。
   */
  public InvalidSeekPositionException(final long pos) {
    super(MESSAGE + pos);
    this.pos = pos;
  }

  /**
   * 获取无效的寻道位置。
   *
   * @return 无效的寻道位置。
   */
  public long position() {
    return pos;
  }

}