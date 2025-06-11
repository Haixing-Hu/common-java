////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.lang;

/**
 * 分割操作的默认常量。
 *
 * @author 胡海星
 */
public interface SplitOption {

  /**
   * 既不修剪也不忽略空字符串。
   */
  int NONE = 0;

  /**
   * 修剪分割后的子字符串。
   */
  int TRIM = 0x0001;

  /**
   * 忽略空的分割子字符串。
   */
  int IGNORE_EMPTY = 0x0002;

  /**
   * 对字母类型使用所谓的"驼峰命名法"。
   */
  int CAMEL_CASE = 0x0004;

  /**
   * 比较子字符串和分隔符时忽略大小写。
   */
  int IGNORE_CASE = 0x0008;

  /**
   * 默认选项，将修剪分割后的子字符串并忽略空字符串。
   */
  int DEFAULT = TRIM | IGNORE_EMPTY;
}