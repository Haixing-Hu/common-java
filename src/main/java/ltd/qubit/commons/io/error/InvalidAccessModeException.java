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

import ltd.qubit.commons.io.AccessMode;
import ltd.qubit.commons.io.OpenOption;

/**
 * 抛出此异常表示访问模式无效。
 *
 * @author 胡海星
 */
public class InvalidAccessModeException extends IOException {

  private static final long serialVersionUID = 5327741114744838035L;

  private final AccessMode accessMode;

  /**
   * 构造一个 {@link InvalidAccessModeException}。
   *
   * @param accessMode
   *     无效的访问模式。
   */
  public InvalidAccessModeException(final AccessMode accessMode) {
    super("Invalid access mode for the operation: " + accessMode);
    this.accessMode = accessMode;
  }

  /**
   * 构造一个 {@link InvalidAccessModeException}。
   *
   * @param accessMode
   *     无效的访问模式。
   * @param openOption
   *     导致此异常的打开选项。
   */
  public InvalidAccessModeException(final AccessMode accessMode,
      final OpenOption openOption) {
    super("Invalid access mode for the open option "
        + openOption + ": " + accessMode);
    this.accessMode = accessMode;
  }

  /**
   * 获取无效的访问模式。
   *
   * @return 无效的访问模式。
   */
  public AccessMode getAccessMode() {
    return accessMode;
  }
}