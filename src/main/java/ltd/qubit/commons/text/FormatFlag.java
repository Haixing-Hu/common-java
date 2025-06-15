////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text;

import javax.annotation.concurrent.ThreadSafe;

/**
 * 此类定义格式化标志的常量。
 * <p>
 * 这些常量可以使用按位或进行组合。
 * </p>
 *
 * @author 胡海星
 */
@ThreadSafe
public final class FormatFlag {

  /**
   * 将布尔元素读取或写入为字母字符串（true和false）。
   */
  public static final int BOOL_ALPHA = 0x00000001;

  /**
   * 写入整数值时在前面加上相应的数字基数前缀。
   */
  public static final int SHOW_RADIX = 0x00000002;

  /**
   * 写入实数值时总是包含小数分隔符。
   */
  public static final int SHOW_POINT = 0x00000004;

  /**
   * 写入非负数值时在前面加上加号（+）。
   * 此选项与{@link #SHOW_SPACE}选项冲突。
   */
  public static final int SHOW_POSITIVE = 0x00000008;

  /**
   * 写入非负数值时在前面加上单个空格。
   *
   * <p>此选项与{@link #SHOW_POSITIVE}选项冲突。
   */
  public static final int SHOW_SPACE = 0x00000010;

  /**
   * 如果整数值与相应的数字基数前缀一起打印，则基数前缀应以其大写映射打印。
   */
  public static final int UPPERCASE_RADIX_PREFIX = 0x00000020;

  /**
   * 如果实数值以科学记数法打印，则指数符号应以其大写映射打印。
   */
  public static final int UPPERCASE_EXPONENT = 0x00000040;

  /**
   * 读取或写入十进制整数时使用分组。
   */
  public static final int GROUPING = 0x00000080;

  /**
   * 在某些输入操作中不跳过前导空白字符，并且在遇到另一个空白字符时不停止读取。
   *
   * <p>注意，默认情况下，所有解析例程都跳过前导空白字符，并在遇到另一个空白字符时停止读取。
   * 如果设置了此标志，解析例程将不跳过前导空白字符，并且在遇到另一个空白字符时不会停止读取。
   */
  public static final int KEEP_BLANKS = 0x00000100;

  /**
   * 在某些插入操作中写入大写字母。
   *
   * <p>如果设置了此选项，十六进制数的输出也将使用大写字母数字。
   */
  public static final int UPPERCASE = 0x00000200;

  /**
   * 在某些插入操作中写入小写字母。
   *
   * <p>如果设置了此选项，十六进制数的输出也将使用小写字母数字。
   */
  public static final int LOWERCASE = 0x00000400;

  /**
   * 在某些插入操作中写入标题大小写字母。
   */
  public static final int TITLECASE = 0x00000800;

  /**
   * 使用二进制基数格式读取或写入整数值。二进制整数字面量将具有"0b"前缀。
   * 例如，"0b101011101"表示十进制数349。
   *
   * <p>此选项与{@link #OCTAL}、{@link #DECIMAL}和{@link #HEX}选项冲突。
   */
  public static final int BINARY = 0x00001000;

  /**
   * 使用八进制基数格式读取或写入整数值。八进制整数字面量将具有"0"前缀。
   * 例如，"0127"表示十进制数87。
   *
   * <p>此选项与{@link #BINARY}、{@link #DECIMAL}和{@link #HEX}选项冲突。
   */
  public static final int OCTAL = 0x00002000;

  /**
   * 使用十进制基数格式读取或写入整数值。这是默认的基数设置。
   *
   * <p>此选项与{@link #BINARY}、{@link #OCTAL}和{@link #HEX}选项冲突。
   */
  public static final int DECIMAL = 0x00004000;

  /**
   * 使用十六进制基数格式读取或写入整数值。
   *
   * <p>十六进制整数字面量将具有"0x"前缀。例如，"0x3F2A"表示十进制数16170。
   *
   * <p>此选项与{@link #BINARY}、{@link #OCTAL}和{@link #DECIMAL}选项冲突。
   */
  public static final int HEX = 0x00008000;

  /**
   * 以定点记数法读取或写入实数值。
   *
   * <p>此选项与{@link #SCIENTIFIC}、{@link #SHORT_REAL}选项冲突。
   */
  public static final int FIXED_POINT = 0x00010000;

  /**
   * 以科学记数法读取或写入实数值。
   *
   * <p>此选项与{@link #FIXED_POINT}、{@link #SHORT_REAL}选项冲突。
   */
  public static final int SCIENTIFIC = 0x00020000;

  /**
   * 以定点记数法或科学记数法的较短形式写入实数值。这是默认的实数格式设置。
   *
   * <p>此选项与{@link #FIXED_POINT}、{@link #SCIENTIFIC}选项冲突。
   */
  public static final int SHORT_REAL = 0x00040000;

  /**
   * 格式化的对象左对齐，即输出在字段宽度上填充，在末尾追加填充字符。
   *
   * <p>此选项与{@link #ALIGN_RIGHT}、{@link #ALIGN_CENTER}选项冲突。
   */
  public static final int ALIGN_LEFT = 0x00080000;

  /**
   * 格式化的对象右对齐，即输出在字段宽度上填充，在开头追加填充字符。这是默认的对齐设置。
   *
   * <p>此选项与{@link #ALIGN_LEFT}、{@link #ALIGN_CENTER}选项冲突。
   */
  public static final int ALIGN_RIGHT = 0x00100000;

  /**
   * 格式化的对象居中对齐，即输出在字段宽度上填充，在两端追加填充字符。
   * 它使格式化对象的中心与指定字段宽度的中心对齐。
   *
   * <p>此选项与{@link #ALIGN_LEFT}、{@link #ALIGN_RIGHT}选项冲突。
   */
  public static final int ALIGN_CENTER = 0x00200000;

  /**
   * 所有大小写相关字段的掩码。
   */
  public static final int CASE_MASK = (UPPERCASE | LOWERCASE | TITLECASE);

  /**
   * 默认大小写格式。
   */
  public static final int DEFAULT_CASE = UPPERCASE;

  /**
   * 所有基数相关字段的掩码。
   */
  public static final int RADIX_MASK = (BINARY | OCTAL | DECIMAL | HEX);

  /**
   * 默认基数格式。
   */
  public static final int DEFAULT_RADIX = DECIMAL;

  /**
   * 所有实数格式相关字段的掩码。
   */
  public static final int REAL_MASK = (FIXED_POINT | SCIENTIFIC | SHORT_REAL);

  /**
   * 默认实数格式。
   */
  public static final int DEFAULT_REAL = SHORT_REAL;

  /**
   * 所有对齐相关字段的掩码。
   */
  public static final int ALIGN_MASK = (ALIGN_LEFT | ALIGN_RIGHT | ALIGN_CENTER);

  /**
   * 默认对齐格式。
   */
  public static final int DEFAULT_ALIGN = ALIGN_RIGHT;

  /**
   * 默认格式标志。
   */
  public static final int DEFAULT = (BOOL_ALPHA | SHOW_RADIX | SHOW_POINT
      | DEFAULT_CASE | DEFAULT_REAL | DEFAULT_ALIGN);
}