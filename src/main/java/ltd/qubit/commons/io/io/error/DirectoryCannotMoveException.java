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
 * Thrown to indicate that a directory can not be moved.
 *
 * @author Haixing Hu
 */
public class DirectoryCannotMoveException extends IOException
    implements ErrorInfoConvertable {

  @Serial
  private static final long serialVersionUID = 5344978076308643042L;

  private final String path;

  public DirectoryCannotMoveException() {
    super("The directory can't be moved. ");
    this.path = "<unknown>";
  }

  public DirectoryCannotMoveException(final String path) {
    super("The directory can't be moved: " + path);
    this.path = path;
  }

  public DirectoryCannotMoveException(final File file) {
    this(file.getAbsolutePath());
  }

  public DirectoryCannotMoveException(final Path path) {
    this(path.toAbsolutePath().toString());
  }

  public String getPath() {
    return path;
  }

  @Override
  public ErrorInfo toErrorInfo() {
    return new ErrorInfo("IO_ERROR", "DIRECTORY_CANNOT_MOVE",
        KeyValuePairList.of("path", path, "reason", getMessage()));
  }
}
