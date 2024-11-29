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
 * Thrown to indicate that a directory can not be deleted.
 *
 * @author Haixing Hu
 */
public class DirectoryCannotDeleteException extends IOException
    implements ErrorInfoConvertable {

  @Serial
  private static final long serialVersionUID = 568544508014410954L;

  private final String path;

  public DirectoryCannotDeleteException() {
    super("The directory can't be deleted. ");
    this.path = "<unknown>";
  }

  public DirectoryCannotDeleteException(final String path) {
    super("The directory can't be deleted: " + path);
    this.path = path;
  }

  public DirectoryCannotDeleteException(final File file) {
    this(file.getAbsolutePath());
  }

  public DirectoryCannotDeleteException(final Path path) {
    this(path.toAbsolutePath().toString());
  }

  public String getPath() {
    return path;
  }

  @Override
  public ErrorInfo toErrorInfo() {
    return new ErrorInfo("IO_ERROR", "DIRECTORY_CANNOT_DELETE",
        KeyValuePairList.of("path", path, "reason", getMessage()));
  }
}
