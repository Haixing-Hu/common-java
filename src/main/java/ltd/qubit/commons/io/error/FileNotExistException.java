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
import java.io.FileNotFoundException;
import java.io.Serial;
import java.nio.file.Path;

import ltd.qubit.commons.error.ErrorInfo;
import ltd.qubit.commons.error.ErrorInfoConvertable;
import ltd.qubit.commons.util.pair.KeyValuePairList;

/**
 * 抛出此异常表示找不到文件。
 *
 * <p>这个类扩展了{@code java.io.FileNotFoundException}，但可以用一个可选的文件名构造。
 *
 * @author 胡海星
 */
public class FileNotExistException extends FileNotFoundException
    implements ErrorInfoConvertable {

  @Serial
  private static final long serialVersionUID = -4901933132912456682L;

  private final String path;

  /**
   * 构造一个 {@link FileNotExistException}。
   */
  public FileNotExistException() {
    super("Can't find the file. ");
    this.path = "<unknown>";
  }

  /**
   * 构造一个 {@link FileNotExistException}。
   *
   * @param path
   *     不存在的文件的路径。
   */
  public FileNotExistException(final String path) {
    super("Can't find the file: " + path);
    this.path = path;
  }

  /**
   * 构造一个 {@link FileNotExistException}。
   *
   * @param file
   *     不存在的文件。
   */
  public FileNotExistException(final File file) {
    this(file.getAbsolutePath());
  }

  /**
   * 构造一个 {@link FileNotExistException}。
   *
   * @param path
   *     不存在的文件的路径。
   */
  public FileNotExistException(final Path path) {
    this(path.toAbsolutePath().toString());
  }

  /**
   * 获取不存在的文件的路径。
   *
   * @return 不存在的文件的路径。
   */
  public final String getPath() {
    return path;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ErrorInfo toErrorInfo() {
    return new ErrorInfo("IO_ERROR", "FILE_NOT_EXIST",
        KeyValuePairList.of("path", path, "reason", getMessage()));
  }
}