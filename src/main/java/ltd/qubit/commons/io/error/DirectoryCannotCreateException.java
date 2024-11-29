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
 * Thrown to indicate that a directory can not be create.
 *
 * @author Haixing Hu
 */
public class DirectoryCannotCreateException extends IOException
    implements ErrorInfoConvertable {

  @Serial
  private static final long serialVersionUID = 2148829371944261196L;

  private final String path;

  public DirectoryCannotCreateException() {
    super("The directory can't be created. ");
    this.path = "<unknown>";
  }

  public DirectoryCannotCreateException(final String path) {
    super("The directory can't be created: " + path);
    this.path = path;
  }

  public DirectoryCannotCreateException(final String path, final Throwable cause) {
    super("The directory can't be created: " + path, cause);
    this.path = path;
  }

  public DirectoryCannotCreateException(final File file) {
    this(file.getAbsolutePath());
  }

  public DirectoryCannotCreateException(final Path path) {
    this(path.toAbsolutePath().toString());
  }

  public String getPath() {
    return path;
  }

  @Override
  public ErrorInfo toErrorInfo() {
    return new ErrorInfo("IO_ERROR", "DIRECTORY_CANNOT_CREATE",
        KeyValuePairList.of("path", path, "reason", getMessage()));
  }
}
