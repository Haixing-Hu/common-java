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
import java.io.IOException;
import java.io.Serial;
import java.nio.file.Path;

import ltd.qubit.commons.error.ErrorInfo;
import ltd.qubit.commons.error.ErrorInfoConvertable;
import ltd.qubit.commons.util.pair.KeyValuePairList;

/**
 * Thrown to indicate that the file exists but is a directory instead of a file.
 *
 * @author Haixing Hu
 */
public class FileIsDirectoryException extends IOException
    implements ErrorInfoConvertable {

  @Serial
  private static final long serialVersionUID = -5140786141865269895L;

  private final String path;

  public FileIsDirectoryException() {
    super("The file exists but is a directory. ");
    this.path = "<unknown>";
  }

  public FileIsDirectoryException(final String path) {
    super("The file exists but is a directory: " + path);
    this.path = path;
  }

  public FileIsDirectoryException(final File file) {
    this(file.getAbsolutePath());
  }

  public FileIsDirectoryException(final Path path) {
    this(path.toAbsolutePath().toString());
  }

  public String getPath() {
    return path;
  }

  @Override
  public ErrorInfo toErrorInfo() {
    return new ErrorInfo("IO_ERROR", "FILE_IS_DIRECTORY",
        KeyValuePairList.of("path", path, "reason", getMessage()));
  }
}
