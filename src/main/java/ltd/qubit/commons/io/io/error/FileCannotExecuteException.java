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
 * Thrown to indicate that a file or directory or device can not be read.
 *
 * @author Haixing Hu
 */
public class FileCannotExecuteException extends IOException
    implements ErrorInfoConvertable {

  @Serial
  private static final long serialVersionUID = - 752442371625554633L;

  private final String path;

  public FileCannotExecuteException() {
    super("The file can't be executed. ");
    this.path = "<unknown>";
  }

  public FileCannotExecuteException(final String path) {
    super("The file can't be executed: " + path);
    this.path = path;
  }

  public FileCannotExecuteException(final File file) {
    this(file.getAbsolutePath());
  }

  public FileCannotExecuteException(final Path path) {
    this(path.toAbsolutePath().toString());
  }

  public final String getPath() {
    return path;
  }

  @Override
  public ErrorInfo toErrorInfo() {
    return new ErrorInfo("IO_ERROR", "FILE_CANNOT_EXECUTE",
        KeyValuePairList.of("path", path, "reason", getMessage()));
  }
}
