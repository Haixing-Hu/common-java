////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.io;

import java.io.IOException;

/**
 * 文件打开选项的枚举。
 *
 * @author 胡海星
 */
public enum OpenOption {

  /**
   * 总是创建新文件或设备。
   *
   * <p>如果指定的文件或设备存在且可写，打开函数会创建新文件或设备并覆盖旧的；
   * 如果指定的文件或设备不存在且路径名是可写位置的有效路径，打开函数会创建新文件或设备并成功。
   */
  CREATE_ALWAYS,

  /**
   * 仅当文件或设备不存在时才创建新文件或设备。
   *
   * <p>如果指定的文件或设备存在，打开函数失败并抛出 {@link IOException}；
   * 如果指定的文件或设备不存在且路径名是可写位置的有效路径，打开函数会创建新文件或设备并成功。
   */
  CREATE_NEW,

  /**
   * 总是打开文件或设备。
   *
   * <p>如果指定的文件或设备存在，打开函数成功。如果指定的文件或设备不存在且是可写位置的有效路径，
   * 打开函数会创建新文件或设备并成功。
   */
  OPEN_ALWAYS,

  /**
   * 仅当文件或设备存在时才打开。
   *
   * <p>如果指定的文件或设备不存在，打开函数失败并抛出 {@link IOException}。
   */
  OPEN_EXISTING,

  /**
   * 总是打开文件或设备进行追加。
   *
   * <p>如果指定的文件或设备存在，打开函数会打开它进行写入，将写入指针设置到末尾进行追加，并成功。
   * 如果指定的文件或设备不存在且是可写位置的有效路径，打开函数会创建新文件或设备，
   * 打开它进行写入，将写入指针设置到开头，并成功。
   */
  APPEND_ALWAYS,

  /**
   * 仅当文件或设备存在时才打开进行追加。
   *
   * <p>如果指定的文件或设备存在，打开函数会打开它进行写入，将写入指针设置到末尾进行追加，并成功。
   * 如果指定的文件或设备不存在，打开函数失败并抛出 {@link IOException}。
   */
  APPEND_EXISTING,

  /**
   * 仅当文件或设备存在时才打开，并将其截断为零字节。
   *
   * <p>如果指定的文件或设备不存在，打开函数失败并抛出 {@link IOException}；
   * 否则，打开函数会打开它并将其截断为零字节。
   */
  TRUNCATE_EXISTING;

  /**
   * 测试此打开选项是否与指定的访问模式兼容。
   *
   * @param accessMode
   *     指定的访问模式。
   * @return 如果此打开选项与指定的访问模式兼容则返回 true；否则返回 false。
   */
  public boolean isCompatibleWith(final AccessMode accessMode) {
    switch (this) {
      case OPEN_EXISTING:
        return true;
      case CREATE_ALWAYS:
      case CREATE_NEW:
      case APPEND_ALWAYS:
      case APPEND_EXISTING:
      case TRUNCATE_EXISTING:
        return (accessMode != AccessMode.READ_ONLY);
      default:
        return false;
    }
  }

}