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
import java.io.FileNotFoundException;
import java.io.Serial;
import java.nio.file.Path;

import ltd.qubit.commons.error.ErrorInfo;
import ltd.qubit.commons.error.ErrorInfoConvertable;
import ltd.qubit.commons.util.pair.KeyValuePairList;

/**
 * Thrown to indicate that a file is not found.
 *
 * <p>This class extends the {@code java.io.FileNotFoundException}, except
 * that it could be constructed with a optional filename.
 *
 * @author Haixing Hu
 */
public class FileNotExistException extends FileNotFoundException
    implements ErrorInfoConvertable {

  @Serial
  private static final long serialVersionUID = -4901933132912456682L;

  private final String path;

  public FileNotExistException() {
    super("Can't find the file. ");
    this.path = "<unknown>";
  }

  public FileNotExistException(final String path) {
    super("Can't find the file: " + path);
    this.path = path;
  }

  public FileNotExistException(final File file) {
    this(file.getAbsolutePath());
  }

  public FileNotExistException(final Path path) {
    this(path.toAbsolutePath().toString());
  }

  public final String getPath() {
    return path;
  }

  @Override
  public ErrorInfo toErrorInfo() {
    return new ErrorInfo("IO_ERROR", "FILE_NOT_EXIST",
        KeyValuePairList.of("path", path, "reason", getMessage()));
  }
}
