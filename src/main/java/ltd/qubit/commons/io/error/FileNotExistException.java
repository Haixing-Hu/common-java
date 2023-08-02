////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.io.error;

import java.io.FileNotFoundException;

/**
 * Thrown to indicate that a file is not found.
 *
 * <p>This class extends the {@code java.io.FileNotFoundException}, except
 * that it could be constructed with a optional filename.
 *
 * @author Haixing Hu
 */
public class FileNotExistException extends FileNotFoundException {

  private static final long serialVersionUID = -4901933132912456682L;

  private String path;

  public FileNotExistException() {
    super("Can't find the file. ");
  }

  public FileNotExistException(final String path) {
    super("Can't find the file: " + path);
    this.path = path;
  }

  public final String getPath() {
    return path;
  }
}
