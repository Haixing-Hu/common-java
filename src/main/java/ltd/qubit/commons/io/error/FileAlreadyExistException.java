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
 * 表示文件已存在的异常。
 *
 * @author 胡海星
 */
public class FileAlreadyExistException extends IOException
    implements ErrorInfoConvertable {

  @Serial
  private static final long serialVersionUID = 8025754182997731427L;

  private final String path;

  /**
   * 使用指定的路径构造一个新的 {@link FileAlreadyExistException}。
   *
   * @param path
   *     导致此异常的文件路径。
   */
  public FileAlreadyExistException(final String path) {
    super("The file already exists: " + path);
    this.path = path;
  }

  /**
   * 使用指定的 {@link File} 对象构造一个新的 {@link FileAlreadyExistException}。
   *
   * @param file
   *     导致此异常的文件。
   */
  public FileAlreadyExistException(final File file) {
    this(file.getAbsolutePath());
  }

  /**
   * 使用指定的 {@link Path} 对象构造一个新的 {@link FileAlreadyExistException}。
   *
   * @param path
   *     导致此异常的文件路径。
   */
  public FileAlreadyExistException(final Path path) {
    this(path.toAbsolutePath().toString());
  }

  /**
   * 获取导致此异常的文件路径。
   *
   * @return 导致此异常的文件路径。
   */
  public final String getPath() {
    return path;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ErrorInfo toErrorInfo() {
    return new ErrorInfo("IO_ERROR", "FILE_ALREADY_EXIST",
        KeyValuePairList.of("path", path, "reason", getMessage()));
  }
}