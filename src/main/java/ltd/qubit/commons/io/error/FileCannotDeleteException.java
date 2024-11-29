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
 * Thrown to indicate that a file or directory or device can not be deleted.
 *
 * @author Haixing Hu
 */
public class FileCannotDeleteException extends IOException
    implements ErrorInfoConvertable {

  @Serial
  private static final long serialVersionUID = - 7495925551285474239L;

  private final String path;

  public FileCannotDeleteException() {
    super("The file can't be deleted. ");
    this.path = "<unknown>";
  }

  public FileCannotDeleteException(final String path) {
    super("The file can't be deleted: " + path);
    this.path = path;
  }

  public FileCannotDeleteException(final File file) {
    this(file.getAbsolutePath());
  }

  public FileCannotDeleteException(final Path path) {
    this(path.toAbsolutePath().toString());
  }

  public String getPath() {
    return path;
  }

  @Override
  public ErrorInfo toErrorInfo() {
    return new ErrorInfo("IO_ERROR", "FILE_CANNOT_DELETE",
        KeyValuePairList.of("path", path, "reason", getMessage()));
  }
}
