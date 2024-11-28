////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.io.io.error;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serial;
import java.nio.file.Path;

import ltd.qubit.commons.error.ErrorInfo;
import ltd.qubit.commons.error.ErrorInfoConvertable;
import ltd.qubit.commons.util.pair.KeyValuePairList;

/**
 * This exception is thrown when you try to list a non-existent directory.
 *
 * @author Haixing Hu
 */
public class DirectoryNotExistException extends FileNotFoundException
    implements ErrorInfoConvertable {

  @Serial
  private static final long serialVersionUID = - 8781993099750916914L;

  private final String path;

  public DirectoryNotExistException() {
    super("Can't find the directory. ");
    this.path = "<unknown>";
  }

  public DirectoryNotExistException(final String path) {
    super("Can't find the directory: " + path);
    this.path = path;
  }

  public DirectoryNotExistException(final File file) {
    this(file.getAbsolutePath());
  }

  public DirectoryNotExistException(final Path path) {
    this(path.toAbsolutePath().toString());
  }

  public String getPath() {
    return path;
  }

  @Override
  public ErrorInfo toErrorInfo() {
    return new ErrorInfo("IO_ERROR", "DIRECTORY_NOT_EXIST",
        KeyValuePairList.of("path", path, "reason", getMessage()));
  }
}
