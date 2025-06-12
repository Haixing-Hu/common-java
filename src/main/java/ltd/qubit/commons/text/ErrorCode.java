////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text;

/**
 * 定义与文本编码、解析和格式化相关的错误代码常量。
 *
 * <p>注意，除了{@link #NONE}之外，所有错误代码都是负整数。
 * {@link #NONE}表示没有错误，其值为0。
 *
 * @author 胡海星
 */
public final class ErrorCode {

  /**
   * 表示没有错误。
   */
  public static final int NONE = 0;

  /**
   * 表示未知错误。
   */
  public static final int UNKNOWN_ERROR = -1;

  /**
   * 表示缓冲区溢出。
   */
  public static final int BUFFER_OVERFLOW = -2;

  /**
   * 表示Unicode字符在指定字符集中无法映射。
   */
  public static final int UNMAPPABLE_CHAR = -3;

  /**
   * 表示Unicode代码单元序列格式错误。
   */
  public static final int MALFORMED_UNICODE = -4;

  /**
   * 表示Unicode代码单元序列不完整。
   */
  public static final int INCOMPLETE_UNICODE = -5;

  /**
   * 表示要解析的文本段不包含所需值的文本表示。
   */
  public static final int EMPTY_VALUE = -6;

  /**
   * 表示解析的数值溢出。
   */
  public static final int NUMBER_OVERFLOW = -7;

  /**
   * 表示解析的数值下溢。
   */
  public static final int NUMBER_UNDERFLOW = -8;

  /**
   * 表示要解析的数值具有无效的数字分组。
   */
  public static final int INVALID_GROUPING = -9;

  /**
   * 表示要解析的文本具有无效的语法。
   */
  public static final int INVALID_SYNTAX = -10;

  private static final String[] ERROR_MESSAGE = {
      "No error.",
      "Unknown error.",
      "The buffer overflows.",
      "There is an Unicode character which is unmappable in the specified charset.",
      "The Unicode code unit sequence is malformed.",
      "The Unicode code unit sequence is incomplete.",
      "There is no text representation of the value to be parsed in the specified text segment.",
      "The numeric value to be parsed overflows.",
      "The numeric value to be parsed underflows.",
      "The numeric value to be parsed has an invalid digit grouping.",
      "The text to be parsed has an invalid syntax.",
  };

  /**
   * 获取指定错误代码的错误消息。
   *
   * @param errorCode 错误代码
   * @return 对应的错误消息
   */
  public static String getMessage(final int errorCode) {
    int index = (-errorCode);
    if ((index < 0) || (index >= ERROR_MESSAGE.length)) {
      index = (-UNKNOWN_ERROR);
    }
    return ERROR_MESSAGE[index];
  }
}