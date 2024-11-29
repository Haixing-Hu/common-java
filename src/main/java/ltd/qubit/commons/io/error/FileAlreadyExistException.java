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
 * Thrown to indicate that a file already exists.
 *
 * @author Haixing Hu
 */
public class FileAlreadyExistException extends IOException
    implements ErrorInfoConvertable {

  @Serial
  private static final long serialVersionUID = 8025754182997731427L;

  private final String path;

  public FileAlreadyExistException(final String path) {
    super("The file already exists: " + path);
    this.path = path;
  }

  public FileAlreadyExistException(final File file) {
    this(file.getAbsolutePath());
  }

  public FileAlreadyExistException(final Path path) {
    this(path.toAbsolutePath().toString());
  }

  public final String getPath() {
    return path;
  }

  @Override
  public ErrorInfo toErrorInfo() {
    return new ErrorInfo("IO_ERROR", "FILE_ALREADY_EXIST",
        KeyValuePairList.of("path", path, "reason", getMessage()));
  }
}
