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
 * Thrown to indicate that a directory can not be copied.
 *
 * @author Haixing Hu
 */
public class DirectoryCannotCopyException extends IOException
    implements ErrorInfoConvertable {

  @Serial
  private static final long serialVersionUID = 1869539087762563069L;

  private final String path;

  public DirectoryCannotCopyException() {
    super("The directory can't be copied. ");
    this.path = "<unknown>";
  }

  public DirectoryCannotCopyException(final String path) {
    super("The directory can't be copied: " + path);
    this.path = path;
  }

  public DirectoryCannotCopyException(final File file) {
    this(file.getAbsolutePath());
  }

  public DirectoryCannotCopyException(final Path path) {
    this(path.toAbsolutePath().toString());
  }

  public String getPath() {
    return path;
  }

  @Override
  public ErrorInfo toErrorInfo() {
    return new ErrorInfo("IO_ERROR", "DIRECTORY_CANNOT_COPY",
        KeyValuePairList.of("path", path, "reason", getMessage()));
  }
}
