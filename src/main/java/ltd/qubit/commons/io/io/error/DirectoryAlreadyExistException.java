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
 * Thrown to indicate that a directory already exists.
 *
 * @author Haixing Hu
 */
public class DirectoryAlreadyExistException extends IOException
    implements ErrorInfoConvertable {

  @Serial
  private static final long serialVersionUID = 5699851702224917371L;

  private final String path;

  public DirectoryAlreadyExistException(final String path) {
    super("The directory already exists: " + path);
    this.path = path;
  }

  public DirectoryAlreadyExistException(final File file) {
    this(file.getAbsolutePath());
  }

  public DirectoryAlreadyExistException(final Path path) {
    this(path.toAbsolutePath().toString());
  }

  public String getPath() {
    return path;
  }

  @Override
  public ErrorInfo toErrorInfo() {
    return new ErrorInfo("IO_ERROR", "DIRECTORY_ALREADY_EXIST",
        KeyValuePairList.of("path", path, "reason", getMessage()));
  }
}
