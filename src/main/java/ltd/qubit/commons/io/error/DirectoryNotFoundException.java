////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.io.error;

/**
 * This exception is thrown when you try to list a non-existent directory.
 *
 * @author Haixing Hu
 */
public class DirectoryNotFoundException extends java.io.FileNotFoundException {

  private static final long serialVersionUID = - 8781993099750916914L;

  public DirectoryNotFoundException() {
    super("Can't find the directory. ");
  }

  public DirectoryNotFoundException(final String dirpath) {
    super("Can't find the directory: " + dirpath);
  }
}
