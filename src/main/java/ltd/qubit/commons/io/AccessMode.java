////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.io;

/**
 * 文件访问模式的枚举。
 *
 * @author 胡海星
 */
public enum AccessMode {
  /**
   * 以只读访问方式打开设备。
   */
  READ_ONLY,

  /**
   * 以只写访问方式打开设备。
   */
  WRITE_ONLY,

  /**
   * 以读写访问方式打开设备。
   */
  READ_WRITE;

  /**
   * 测试此访问模式是否与指定的打开选项兼容。
   *
   * @param openOption
   *     指定的打开选项。
   * @return 如果此访问模式与指定的打开选项兼容，则返回 true；否则返回 false。
   */
  public boolean compatibleWith(final OpenOption openOption) {
    switch (this) {
      case READ_ONLY:
        return (openOption == OpenOption.OPEN_EXISTING);
      case WRITE_ONLY:
      case READ_WRITE:
        return true;
      default:
        return false;
    }
  }
}