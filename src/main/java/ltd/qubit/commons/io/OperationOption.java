////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.io;

/**
 * 定义文件操作选项的常量。
 *
 * @author 胡海星
 */
public final class OperationOption {

  /**
   * 如果存在此选项，文件操作将在必要时自动创建文件的目录。
   */
  public static final int MAKE_DIRS     = 0x0001;

  /**
   * 如果存在此选项，文件操作将覆盖现有文件。
   */
  public static final int OVERWRITE     = 0x0002;

  /**
   * 如果存在此选项，文件操作将保留源文件的最后修改日期。
   */
  public static final int PRESERVE_DATE = 0x0004;

}