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
public class DirectoryCannotListException extends IOException
    implements ErrorInfoConvertable {

  @Serial
  private static final long serialVersionUID = 4992858511247895491L;

  private final String path;

  public DirectoryCannotListException() {
    super("Can't list the directory. ");
    this.path = "<unknown>";
  }

  public DirectoryCannotListException(final String path) {
    super("Can't list the directory: " + path);
    this.path = path;
  }

  public DirectoryCannotListException(final File file) {
    this(file.getAbsolutePath());
  }

  public DirectoryCannotListException(final Path path) {
    this(path.toAbsolutePath().toString());
  }

  public String getPath() {
    return path;
  }

  @Override
  public ErrorInfo toErrorInfo() {
    return new ErrorInfo("IO_ERROR", "DIRECTORY_CANNOT_LIST",
        KeyValuePairList.of("path", path, "reason", getMessage()));
  }
}
