////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.io;

/**
 * Defines the constants of file operation options.
 *
 * @author Haixing Hu
 */
public final class OperationOption {

  /**
   * If this option is presented, the file operation will automatically create
   * the directories of the file if necessary.
   */
  public static final int MAKE_DIRS     = 0x0001;

  /**
   * If this option is presented, the file operation will overwrite the existing
   * file.
   */
  public static final int OVERWRITE     = 0x0002;

  /**
   * If this option is presented, the file operation will preserve the file last
   * modify date of the source file.
   */
  public static final int PRESERVE_DATE = 0x0004;

}
