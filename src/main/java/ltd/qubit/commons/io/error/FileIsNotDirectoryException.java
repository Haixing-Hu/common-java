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
 * 抛出此异常表示文件存在但不是目录。
 *
 * @author 胡海星
 */
public class FileIsNotDirectoryException extends IOException
    implements ErrorInfoConvertable {

  @Serial
  private static final long serialVersionUID = 375647745159564317L;

  private final String path;

  /**
   * 构造一个 {@link FileIsNotDirectoryException}。
   */
  public FileIsNotDirectoryException() {
    super("The file exists but is not a directory. ");
    this.path = "<unknown>";
  }

  /**
   * 构造一个 {@link FileIsNotDirectoryException}。
   *
   * @param path
   *     不是目录的文件的路径。
   */
  public FileIsNotDirectoryException(final String path) {
    super("The file exists but is not a directory: " + path);
    this.path = path;
  }

  /**
   * 构造一个 {@link FileIsNotDirectoryException}。
   *
   * @param file
   *     不是目录的文件。
   */
  public FileIsNotDirectoryException(final File file) {
    this(file.getAbsolutePath());
  }

  /**
   * 构造一个 {@link FileIsNotDirectoryException}。
   *
   * @param path
   *     不是目录的文件的路径。
   */
  public FileIsNotDirectoryException(final Path path) {
    this(path.toAbsolutePath().toString());
  }

  /**
   * 获取不是目录的文件的路径。
   *
   * @return 不是目录的文件的路径。
   */
  public String getPath() {
    return path;
  }

  @Override
  public ErrorInfo toErrorInfo() {
    return new ErrorInfo("IO_ERROR", "FILE_IS_NOT_DIRECTORY",
        KeyValuePairList.of("path", path, "reason", getMessage()));
  }
}