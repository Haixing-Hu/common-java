////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.io.error;

import java.io.File;
import java.io.IOException;
import java.io.Serial;
import java.nio.file.Path;

import ltd.qubit.commons.error.ErrorInfo;
import ltd.qubit.commons.error.ErrorInfoConvertable;
import ltd.qubit.commons.util.pair.KeyValuePairList;

/**
 * 抛出此异常表示无法写入文件、目录或设备。
 *
 * @author 胡海星
 */
public class FileCannotWriteException extends IOException
    implements ErrorInfoConvertable {

  @Serial
  private static final long serialVersionUID = - 8520556806523403160L;

  private final String path;

  /**
   * 构造一个 {@link FileCannotWriteException}。
   */
  public FileCannotWriteException() {
    super("The file can't be written to. ");
    this.path = "<unknown>";
  }

  /**
   * 使用指定的路径构造一个新的 {@link FileCannotWriteException}。
   *
   * @param path
   *     无法写入的文件的路径。
   */
  public FileCannotWriteException(final String path) {
    super("The file can't be written to: " + path);
    this.path = path;
  }

  /**
   * 使用指定的文件构造一个新的 {@link FileCannotWriteException}。
   *
   * @param file
   *     无法写入的文件。
   */
  public FileCannotWriteException(final File file) {
    this(file.getAbsolutePath());
  }

  /**
   * 使用指定的路径构造一个新的 {@link FileCannotWriteException}。
   *
   * @param path
   *     无法写入的文件的路径。
   */
  public FileCannotWriteException(final Path path) {
    this(path.toAbsolutePath().toString());
  }

  /**
   * 获取此异常相关联的路径。
   *
   * @return 此异常相关联的路径。
   */
  public String getPath() {
    return path;
  }

  @Override
  public ErrorInfo toErrorInfo() {
    return new ErrorInfo("IO_ERROR", "FILE_CANNOT_WRITE",
        KeyValuePairList.of("path", path, "reason", getMessage()));
  }
}