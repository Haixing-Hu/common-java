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
 * Thrown to indicate that a file or directory or device can not be write.
 *
 * @author Haixing Hu
 */
public class FileCannotWriteException extends IOException
    implements ErrorInfoConvertable {

  @Serial
  private static final long serialVersionUID = - 8520556806523403160L;

  private final String path;

  public FileCannotWriteException() {
    super("The file can't be written to. ");
    this.path = "<unknown>";
  }

  public FileCannotWriteException(final String path) {
    super("The file can't be written to: " + path);
    this.path = path;
  }

  public FileCannotWriteException(final File file) {
    this(file.getAbsolutePath());
  }

  public FileCannotWriteException(final Path path) {
    this(path.toAbsolutePath().toString());
  }

  public String getPath() {
    return path;
  }

  @Override
  public ErrorInfo toErrorInfo() {
    return new ErrorInfo("IO_ERROR", "FILE_CANNOT_WRITE",
        KeyValuePairList.of("path", path, "reason", getMessage()));
  }
}
