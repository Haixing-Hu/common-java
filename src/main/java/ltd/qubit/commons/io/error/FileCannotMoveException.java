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
 * Thrown to indicate that a file or directory or device can not be moved.
 *
 * @author Haixing Hu
 */
public class FileCannotMoveException extends IOException
    implements ErrorInfoConvertable {

  @Serial
  private static final long serialVersionUID = 8721109996158945749L;

  private final String path;

  public FileCannotMoveException() {
    super("The file can't be moved. ");
    this.path = "<unknown>";
  }

  public FileCannotMoveException(final String path) {
    super("The file can't be moved: " + path);
    this.path = path;
  }

  public FileCannotMoveException(final File file) {
    this(file.getAbsolutePath());
  }

  public FileCannotMoveException(final Path path) {
    this(path.toAbsolutePath().toString());
  }

  public final String getPath() {
    return path;
  }

  @Override
  public ErrorInfo toErrorInfo() {
    return new ErrorInfo("IO_ERROR", "FILE_CANNOT_MOVE",
        KeyValuePairList.of("path", path, "reason", getMessage()));
  }
}