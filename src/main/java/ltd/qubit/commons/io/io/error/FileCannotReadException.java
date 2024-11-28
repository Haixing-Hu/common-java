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
public class FileCannotReadException extends IOException
    implements ErrorInfoConvertable {

  @Serial
  private static final long serialVersionUID = - 4170967684441144089L;

  private final String path;

  public FileCannotReadException() {
    super("The file can't be read. ");
    this.path = "<unknown>";
  }

  public FileCannotReadException(final String path) {
    super("The file can't be read: " + path);
    this.path = path;
  }

  public FileCannotReadException(final File file) {
    this(file.getAbsolutePath());
  }

  public FileCannotReadException(final Path path) {
    this(path.toAbsolutePath().toString());
  }

  public String getPath() {
    return path;
  }

  @Override
  public ErrorInfo toErrorInfo() {
    return new ErrorInfo("IO_ERROR", "FILE_CANNOT_READ",
        KeyValuePairList.of("path", path, "reason", getMessage()));
  }
}
