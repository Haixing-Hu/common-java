////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
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
 * Thrown to indicate that a directory can not be list.
 *
 * @author Haixing Hu
 */
public class DirectoryCannotWriteException extends IOException
    implements ErrorInfoConvertable {

  @Serial
  private static final long serialVersionUID = 1028954470907555494L;

  private final String path;

  public DirectoryCannotWriteException() {
    super("The directory can't be written to. ");
    this.path = "<unknown>";
  }

  public DirectoryCannotWriteException(final String path) {
    super("The directory can't be written to: " + path);
    this.path = path;
  }

  public DirectoryCannotWriteException(final File file) {
    this(file.getAbsolutePath());
  }

  public DirectoryCannotWriteException(final Path path) {
    this(path.toAbsolutePath().toString());
  }

  public String getPath() {
    return path;
  }

  @Override
  public ErrorInfo toErrorInfo() {
    return new ErrorInfo("IO_ERROR", "DIRECTORY_CANNOT_WRITE",
        KeyValuePairList.of("path", path, "reason", getMessage()));
  }
}
