////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.lang;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CodingErrorAction;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.ThreadSafe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import ltd.qubit.commons.datastructure.list.primitive.IntList;
import ltd.qubit.commons.datastructure.list.primitive.impl.IntArrayList;
import ltd.qubit.commons.text.Ascii;
import ltd.qubit.commons.text.BooleanFormat;
import ltd.qubit.commons.text.CharSequenceCodePointIterator;
import ltd.qubit.commons.text.DateFormat;
import ltd.qubit.commons.text.Joiner;
import ltd.qubit.commons.text.NumberFormat;
import ltd.qubit.commons.text.Remover;
import ltd.qubit.commons.text.Replacer;
import ltd.qubit.commons.text.Searcher;
import ltd.qubit.commons.text.Splitter;
import ltd.qubit.commons.text.Stripper;
import ltd.qubit.commons.text.Unicode;
import ltd.qubit.commons.util.codec.DecodingException;
import ltd.qubit.commons.util.codec.HexCodec;
import ltd.qubit.commons.util.codec.IsoLocalDateCodec;
import ltd.qubit.commons.util.codec.IsoLocalDateTimeCodec;
import ltd.qubit.commons.util.codec.IsoLocalTimeCodec;
import ltd.qubit.commons.util.codec.LocalDateCodec;
import ltd.qubit.commons.util.codec.LocalDateTimeCodec;
import ltd.qubit.commons.util.codec.LocalTimeCodec;
import ltd.qubit.commons.util.filter.character.AcceptSpecifiedCharFilter;
import ltd.qubit.commons.util.filter.character.AsciiCharFilter;
import ltd.qubit.commons.util.filter.character.AsciiPrintableCharFilter;
import ltd.qubit.commons.util.filter.character.BlankCharFilter;
import ltd.qubit.commons.util.filter.character.CharFilter;
import ltd.qubit.commons.util.filter.codepoint.BlankCodePointFilter;
import ltd.qubit.commons.util.filter.codepoint.CodePointFilter;
import ltd.qubit.commons.util.filter.codepoint.DigitCodePointFilter;
import ltd.qubit.commons.util.filter.codepoint.DigitSpaceCodePointFilter;
import ltd.qubit.commons.util.filter.codepoint.LetterCodePointFilter;
import ltd.qubit.commons.util.filter.codepoint.LetterDigitCodePointFilter;
import ltd.qubit.commons.util.filter.codepoint.LetterDigitSpaceCodePointFilter;
import ltd.qubit.commons.util.filter.codepoint.LetterSpaceCodePointFilter;
import ltd.qubit.commons.util.filter.codepoint.WhitespaceCodePointFilter;

import static java.nio.charset.StandardCharsets.UTF_8;

import static ltd.qubit.commons.lang.Argument.requireEqual;
import static ltd.qubit.commons.lang.ObjectUtils.defaultIfNull;
import static ltd.qubit.commons.lang.SplitOption.CAMEL_CASE;
import static ltd.qubit.commons.lang.SplitOption.IGNORE_EMPTY;
import static ltd.qubit.commons.lang.SplitOption.TRIM;
import static ltd.qubit.commons.text.impl.SearcherImpl.endsWithSubstring;
import static ltd.qubit.commons.text.impl.SearcherImpl.firstIndexOf;

/**
 * 本类提供对 {@link String} 的操作，这些操作都是 {@code null} 安全的。
 *
 * <ul>
 * <li><b>IsEmpty/IsBlank</b> - 检查字符串是否包含文本</li>
 * <li><b>Trim/Strip</b> - 删除首尾空白字符</li>
 * <li><b>Equals</b> - null安全的字符串比较</li>
 * <li><b>startsWith</b> - null安全的检查字符串是否以前缀开始</li>
 * <li><b>endsWith</b> - null安全的检查字符串是否以后缀结束</li>
 * <li><b>IndexOf/LastIndexOf/Contains</b> - null安全的索引检查
 * <li><b>IndexOfAny/LastIndexOfAny/IndexOfAnyBut/LastIndexOfAnyBut</b> -
 * 搜索字符串集合中任意一个的索引</li>
 * <li><b>ContainsOnly/ContainsNone/ContainsAny</b> - 检查字符串是否只包含/不包含/包含任意
 * 这些字符</li>
 * <li><b>Substring/Left/Right/Mid</b> - null安全的子字符串提取</li>
 * <li><b>SubstringBefore/SubstringAfter/SubstringBetween</b> - 相对于其他字符串
 * 的子字符串提取</li>
 * <li><b>Split/Join</b> - 将字符串分割为子字符串数组，以及反向操作</li>
 * <li><b>Remove/Delete</b> - 删除字符串的一部分</li>
 * <li><b>Replace/Overlay</b> - 搜索字符串并用另一个字符串替换</li>
 * <li><b>Chomp/Chop</b> - 删除字符串的最后部分</li>
 * <li><b>LeftPad/RightPad/Center/Repeat</b> - 填充字符串</li>
 * <li><b>UpperCase/LowerCase/SwapCase/Capitalize/Uncapitalize</b> - 改变
 * 字符串的大小写</li>
 * <li><b>CountMatches</b> - 计算一个字符串在另一个字符串中出现的次数</li>
 * <li><b>IsAlpha/IsNumeric/IsWhitespace/IsAsciiPrintable</b> - 检查
 * 字符串中的字符</li>
 * <li><b>DefaultString</b> - 防止 {@code null} 输入字符串</li>
 * <li><b>Reverse/ReverseDelimited</b> - 反转字符串</li>
 * <li><b>Abbreviate</b> - 使用省略号缩写字符串</li>
 * <li><b>Difference</b> - 比较字符串并报告其差异</li>
 * <li><b>LevensteinDistance</b> - 将一个字符串更改为另一个字符串所需的更改次数</li>
 * </ul>
 *
 * <p>{@code Strings} 类定义了与字符串处理相关的某些词汇。
 * <ul>
 * <li>null - {@code null}</li>
 * <li>empty - 零长度字符串 ({@code ""})</li>
 * <li>space - 空格字符 ({@code ' '}, char 32)</li>
 * <li>whitespace - 由 {@link Character#isWhitespace(char)} 定义的字符</li>
 * <li>trim - 字符 &lt;= 32，如 {@link String#trim()} 中的定义</li>
 * </ul>
 *
 * <p>{@code Strings} 安静地处理 {@code null} 输入字符串。也就是说，
 * {@code null} 输入将返回 {@code null}。当返回 {@code boolean}
 * 或 {@code int} 时，详细信息因方法而异。
 *
 * <p>{@code null} 处理的副作用是，{@code NullPointerException} 应该被视为
 * {@code Strings} 中的错误（除了已弃用的方法）。
 *
 * <p>本类中的方法提供示例代码来解释其操作。符号 {@code *} 用于表示
 * 包括 {@code null} 在内的任何输入。
 *
 * @author 胡海星
 */
@SuppressWarnings("overloads")
@ThreadSafe
public class StringUtils {

  private static final Logger LOGGER = LoggerFactory.getLogger(StringUtils.class);

  /**
   * 表示空字符串常量。
   */
  public static final String EMPTY = "";

  /**
   * 布尔值 true 的字符串表示。
   */
  public static final String TRUE = "true";

  /**
   * 布尔值 false 的字符串表示。
   */
  public static final String FALSE = "false";

  /**
   * 肯定回应的字符串表示。
   */
  public static final String YES = "yes";

  /**
   * 否定回应的字符串表示。
   */
  public static final String NO = "no";

  /**
   * 启用状态的字符串表示。
   */
  public static final String ON = "on";

  /**
   * 禁用状态的字符串表示。
   */
  public static final String OFF = "off";

  /**
   * 表示省略号字符串常量。
   */
  public static final String ELLIPSES = "...";

  /**
   * 表示单个空格字符。
   */
  public static final String SPACE = " ";

  /**
   * 表示制表符字符。
   */
  public static final String TAB = "\t";

  /**
   * 最小字节值 (-128) 的字符串表示。
   */
  public static final String BYTE_MIN = "-128";

  /**
   * 最小字节绝对值 (128) 的字符串表示。
   */
  public static final String BYTE_MIN_ABS = "128";

  /**
   * 最大字节值 (127) 的字符串表示。
   */
  public static final String BYTE_MAX = "127";

  /**
   * 最小短整型值 (-32768) 的字符串表示。
   */
  public static final String SHORT_MIN = "-32768";

  /**
   * 最小短整型绝对值 (32768) 的字符串表示。
   */
  public static final String SHORT_MIN_ABS = "32768";

  /**
   * 最大短整型值 (32767) 的字符串表示。
   */
  public static final String SHORT_MAX = "32767";

  /**
   * 最小整型值 (-2147483648) 的字符串表示。
   */
  public static final String INT_MIN = "-2147483648";

  /**
   * 最小整型绝对值 (2147483648) 的字符串表示。
   */
  public static final String INT_MIN_ABS = "2147483648";

  /**
   * 最大整型值 (2147483647) 的字符串表示。
   */
  public static final String INT_MAX = "2147483647";

  /**
   * 最小长整型值 (-9223372036854775808) 的字符串表示。
   */
  public static final String LONG_MIN = "-9223372036854775808";

  /**
   * 最小长整型绝对值 (9223372036854775808) 的字符串表示。
   */
  public static final String LONG_MIN_ABS = "9223372036854775808";

  /**
   * 最大长整型值 (9223372036854775807) 的字符串表示。
   */
  public static final String LONG_MAX = "9223372036854775807";

  /**
   * 最小浮点型值 (1.4E-45) 的字符串表示。
   */
  public static final String FLOAT_MIN = "1.4E-45";

  /**
   * 最大浮点型值 (3.4028235E38) 的字符串表示。
   */
  public static final String FLOAT_MAX = "3.4028235E38";

  /**
   * 最小双精度浮点型值 (4.9E-324) 的字符串表示。
   */
  public static final String DOUBLE_MIN = "4.9E-324";

  /**
   * 最大双精度浮点型值 (1.7976931348623157E308) 的字符串表示。
   */
  public static final String DOUBLE_MAX = "1.7976931348623157E308";

  /**
   * 填充常量可以扩展到的最大大小。
   */
  public static final int PAD_LIMIT = 8192;

  /**
   * Object.toString() 结果长度的假设值。
   */
  private static final int TO_STRING_LENGTH_ASSUMPTION = 16;

  /**
   * 可迭代集合中对象数量的假设值。
   */
  private static final int OBJECT_COUNT_ASSUMPTION = 64;

  private StringUtils() {
  }

  /**
   * 测试字符串是否不超过指定长度。
   *
   * @param str
   *     要测试的字符串，可能为null。
   * @param len
   *     要测试的长度。
   * @return 如果字符串为null或其长度不超过{@code len}则返回{@code true}；
   *     否则返回{@code false}。
   */
  public static boolean isNotLongerThan(@Nullable final CharSequence str,
      final int len) {
    return (str == null) || (str.length() <= len);
  }

  /**
   * 测试字符串是否超过指定长度。
   *
   * @param str
   *     要测试的字符串，可能为null。
   * @param len
   *     要测试的长度。
   * @return 如果字符串不为{@code null}且其长度超过{@code len}则返回{@code true}；
   *     否则返回{@code false}。
   */
  public static boolean isLongerThan(@Nullable final CharSequence str,
      final int len) {
    return (str != null) && (str.length() > len);
  }

  /**
   * 检查字符串是否为空（""）或为null。
   *
   * <pre>
   * StringUtils.isEmpty(null)      = true
   * StringUtils.isEmpty("")        = true
   * StringUtils.isEmpty(" ")       = false
   * StringUtils.isEmpty("bob")     = false
   * StringUtils.isEmpty("  bob  ") = false
   * </pre>
   *
   * @param str
   *     要检查的字符串，可能为null
   * @return 如果字符串为空或为null则返回{@code true}；否则返回{@code false}。
   */
  public static boolean isEmpty(@Nullable final CharSequence str) {
    return (str == null) || (str.length() == 0);
  }

  /**
   * 检查字符串既不为空（""）也不为{@code null}。
   * <pre>
   * StringUtils.isNotEmpty(null)      = false
   * StringUtils.isNotEmpty("")        = false
   * StringUtils.isNotEmpty(" ")       = true
   * StringUtils.isNotEmpty("bob")     = true
   * StringUtils.isNotEmpty("  bob  ") = true
   * </pre>
   *
   * @param str
   *     要检查的字符串，可能为null
   * @return 如果字符串既不为空也不为{@code null}则返回{@code true}；
   *     否则返回{@code false}。
   */
  public static boolean isNotEmpty(@Nullable final CharSequence str) {
    return (str != null) && (str.length() > 0);
  }

  /**
   * 检查字符串是否只包含不可打印字符或空白字符，或者字符串为空（""）或为null。
   * <pre>
   * StringUtils.isBlank(null)      = true
   * StringUtils.isBlank("")        = true
   * StringUtils.isBlank(" ")       = true
   * StringUtils.isBlank("bob")     = false
   * StringUtils.isBlank("  bob  ") = false
   * StringUtils.isBlank("\n \r")   = true
   * </pre>
   *
   * @param str
   *     要检查的字符串，可能为null
   * @return 如果字符串为null、为空或全为空白字符则返回{@code true}。
   */
  public static boolean isBlank(@Nullable final CharSequence str) {
    return (str == null) || containsOnly(str, BlankCodePointFilter.INSTANCE);
  }

  /**
   * 检查字符串是否不只包含空白字符。
   * <pre>
   * StringUtils.isNotBlank(null)      = false
   * StringUtils.isNotBlank("")        = false
   * StringUtils.isNotBlank(" ")       = false
   * StringUtils.isNotBlank("bob")     = true
   * StringUtils.isNotBlank("  bob  ") = true
   * StringUtils.isNotBlank("\n \r")   = false
   * </pre>
   *
   * @param str
   *     要检查的字符串，可能为null
   * @return 如果字符串不为{@code null}、不为空且不只包含空白字符则返回{@code true}；
   *     否则返回{@code false}。
   * @see #isBlank(CharSequence)
   */
  public static boolean isNotBlank(@Nullable final CharSequence str) {
    return (str != null) && (!containsOnly(str, BlankCodePointFilter.INSTANCE));
  }

  /**
   * 检查字符串是否为空（""）、为null或只包含空白字符。
   *
   * <pre>
   * StringUtils.isEmptyOrBlank(null)      = true
   * StringUtils.isEmptyOrBlank("")        = true
   * StringUtils.isEmptyOrBlank(" ")       = true
   * StringUtils.isEmptyOrBlank("bob")     = false
   * StringUtils.isEmptyOrBlank("  bob  ") = false
   * </pre>
   *
   * @param str
   *     要检查的字符串，可以为null。
   * @return 如果字符串为空、为null或只包含空白字符则返回{@code true}；
   *     否则返回{@code false}。
   */
  public static boolean isEmptyOrBlank(@Nullable final CharSequence str) {
    return (str == null)
        || (str.length() == 0)
        || containsOnly(str, BlankCodePointFilter.INSTANCE);
  }

  /**
   * 检查字符串是否既不为空（""）又不为{@code null}且不只包含空白字符。
   * <pre>
   * StringUtils.isNotEmptyNorBlank(null)      = false
   * StringUtils.isNotEmptyNorBlank("")        = false
   * StringUtils.isNotEmptyNorBlank(" ")       = false
   * StringUtils.isNotEmptyNorBlank("bob")     = true
   * StringUtils.isNotEmptyNorBlank("  bob  ") = true
   * </pre>
   *
   * @param str
   *     要检查的字符串，可以为null
   * @return 如果字符串既不为空也不为null且不只包含空白字符则返回{@code true}；
   *     否则返回{@code false}。
   */
  public static boolean isNotEmptyNorBlank(@Nullable final CharSequence str) {
    return (str != null)
        && (str.length() > 0)
        && (!containsOnly(str, BlankCodePointFilter.INSTANCE));
  }

  /**
   * 检查字符串是否只包含空白字符，或者字符串为空（""）或为null。
   * <pre>
   * StringUtils.isWhitespace(null)   = true
   * StringUtils.isWhitespace("")     = true
   * StringUtils.isWhitespace("  ")   = true
   * StringUtils.isWhitespace("abc")  = false
   * StringUtils.isWhitespace("ab2c") = false
   * StringUtils.isWhitespace("ab-c") = false
   * </pre>
   *
   * @param str
   *     要检查的字符串，可以为null。
   * @return 如果只包含空白字符则返回{@code true}；否则返回{@code false}。
   */
  public static boolean isWhitespace(@Nullable final CharSequence str) {
    return (str == null) || containsOnly(str, WhitespaceCodePointFilter.INSTANCE);
  }

  /**
   * 检查字符串是否只包含Unicode字母。
   *
   * <p>{@code null}将返回{@code false}。空字符串（""）将返回{@code true}。
   * <pre>
   * StringUtils.isLetter(null)   = false
   * StringUtils.isLetter("")     = true
   * StringUtils.isLetter("  ")   = false
   * StringUtils.isLetter("abc")  = true
   * StringUtils.isLetter("ab2c") = false
   * StringUtils.isLetter("ab-c") = false
   * </pre>
   *
   * @param str
   *     要检查的字符串，可以为null。
   * @return 如果只包含字母且非null则返回{@code true}。
   */
  public static boolean isLetter(@Nullable final CharSequence str) {
    return containsOnly(str, LetterCodePointFilter.INSTANCE);
  }

  /**
   * 检查字符串是否只包含Unicode字母和空格（' '）。
   *
   * <p>{@code null}将返回{@code false}。空字符串（""）将返回{@code true}。
   * <pre>
   * StringUtils.isLetterSpace(null)   = false
   * StringUtils.isLetterSpace("")     = true
   * StringUtils.isLetterSpace("  ")   = true
   * StringUtils.isLetterSpace("abc")  = true
   * StringUtils.isLetterSpace("ab c") = true
   * StringUtils.isLetterSpace("ab \tc") = true
   * StringUtils.isLetterSpace("ab \f\nc") = true
   * StringUtils.isLetterSpace("ab2c") = false
   * StringUtils.isLetterSpace("ab-c") = false
   * </pre>
   *
   * @param str
   *     要检查的字符串，可以为null
   * @return 如果只包含字母和空格且非null则返回{@code true}
   */
  public static boolean isLetterSpace(@Nullable final CharSequence str) {
    return containsOnly(str, LetterSpaceCodePointFilter.INSTANCE);
  }

  /**
   * 检查字符串是否只包含Unicode字母或数字。
   *
   * <p>{@code null}将返回{@code false}。空字符串（""）将返回{@code true}。
   * <pre>
   * StringUtils.isLetterDigit(null)   = false
   * StringUtils.isLetterDigit("")     = true
   * StringUtils.isLetterDigit("  ")   = false
   * StringUtils.isLetterDigit("abc")  = true
   * StringUtils.isLetterDigit("ab c") = false
   * StringUtils.isLetterDigit("ab2c") = true
   * StringUtils.isLetterDigit("ab-c") = false
   * </pre>
   *
   * @param str
   *     要检查的字符串，可以为null
   * @return 如果只包含字母或数字且非null则返回{@code true}
   */
  public static boolean isLetterDigit(@Nullable final CharSequence str) {
    return containsOnly(str, LetterDigitCodePointFilter.INSTANCE);
  }

  /**
   * 检查字符串是否只包含Unicode字母、数字或空格（{@code ' '}）。
   *
   * <p>{@code null}将返回{@code false}。空字符串（""）将返回{@code true}。
   * <pre>
   * StringUtils.isLetterDigitSpace(null)   = false
   * StringUtils.isLetterDigitSpace("")     = true
   * StringUtils.isLetterDigitSpace("  ")   = true
   * StringUtils.isLetterDigitSpace("abc")  = true
   * StringUtils.isLetterDigitSpace("ab c") = true
   * StringUtils.isLetterDigitSpace("ab2c") = true
   * StringUtils.isLetterDigitSpace("ab-c") = false
   * </pre>
   *
   * @param str
   *     要检查的字符串，可以为null
   * @return 如果只包含字母、数字或空格且非null则返回{@code true}
   */
  public static boolean isLetterDigitSpace(@Nullable final CharSequence str) {
    return containsOnly(str, LetterDigitSpaceCodePointFilter.INSTANCE);
  }

  /**
   * 检查字符串是否只包含ASCII字符。
   *
   * <p>{@code null}将返回{@code false}。空字符串（""）将返回{@code true}。
   * <pre>
   * StringUtils.isAscii(null)   = false
   * StringUtils.isAscii("")     = true
   * StringUtils.isAscii("  ")   = true
   * StringUtils.isAscii("abc")  = true
   * StringUtils.isAscii("ab2c") = true
   * StringUtils.isAscii("ab-c") = true
   * StringUtils.isAscii("中文") = false
   * </pre>
   *
   * @param str
   *     要检查的字符串，可以为null
   * @return 如果只包含ASCII字符且非null则返回{@code true}
   */
  public static boolean isAscii(@Nullable final CharSequence str) {
    return containsOnly(str, AsciiCharFilter.INSTANCE);
  }

  /**
   * 检查字符串是否只包含ASCII可打印字符。
   *
   * <p>{@code null}将返回{@code false}。空字符串（""）将返回{@code true}。
   *
   * <pre>
   * StringUtils.isAsciiPrintable(null)     = false
   * StringUtils.isAsciiPrintable("")       = true
   * StringUtils.isAsciiPrintable(" ")      = true
   * StringUtils.isAsciiPrintable("Ceki")   = true
   * StringUtils.isAsciiPrintable("ab2c")   = true
   * StringUtils.isAsciiPrintable("!ab-c~") = true
   * StringUtils.isAsciiPrintable("\u0020") = true
   * StringUtils.isAsciiPrintable("\u0021") = true
   * StringUtils.isAsciiPrintable("\u007e") = true
   * StringUtils.isAsciiPrintable("\u007f") = false
   * StringUtils.isAsciiPrintable("Ceki Gülcü") = false
   * </pre>
   *
   * @param str
   *     要检查的字符串，可以为null
   * @return 如果每个字符都在32到126的范围内则返回{@code true}。如果字符串为null，则返回false。
   */
  public static boolean isAsciiPrintable(@Nullable final CharSequence str) {
    return containsOnly(str, AsciiPrintableCharFilter.INSTANCE);
  }

  /**
   * 检查字符串是否只包含Unicode数字。小数点不是Unicode数字，因此返回false。
   *
   * <p>{@code null}将返回{@code false}。空字符串（""）将返回{@code true}。
   *
   * <pre>
   * StringUtils.isDigit(null)   = false
   * StringUtils.isDigit("")     = true
   * StringUtils.isDigit("  ")   = false
   * StringUtils.isDigit("123")  = true
   * StringUtils.isDigit("12 3") = false
   * StringUtils.isDigit("ab2c") = false
   * StringUtils.isDigit("12-3") = false
   * StringUtils.isDigit("12.3") = false
   * </pre>
   *
   * @param str
   *     要检查的字符串，可以为null
   * @return 如果只包含数字且非null则返回{@code true}。如果字符串为null，则返回false。
   */
  public static boolean isDigit(@Nullable final CharSequence str) {
    return containsOnly(str, DigitCodePointFilter.INSTANCE);
  }

  /**
   * 检查字符串是否只包含Unicode数字或空格（{@code ' '}）。
   * 小数点不是Unicode数字，因此返回false。
   *
   * <p>{@code null}将返回{@code false}。空字符串（""）将返回{@code true}。
   *
   * <pre>
   * StringUtils.isDigitSpace(null)   = false
   * StringUtils.isDigitSpace("")     = true
   * StringUtils.isDigitSpace("  ")   = true
   * StringUtils.isDigitSpace("123")  = true
   * StringUtils.isDigitSpace("12 3") = true
   * StringUtils.isDigitSpace("ab2c") = false
   * StringUtils.isDigitSpace("12-3") = false
   * StringUtils.isDigitSpace("12.3") = false
   * </pre>
   *
   * @param str
   *     要检查的字符串，可以为null
   * @return 如果只包含数字或空格则返回{@code true}。如果字符串为null，返回false。
   */
  public static boolean isDigitSpace(@Nullable final CharSequence str) {
    return containsOnly(str, DigitSpaceCodePointFilter.INSTANCE);
  }

  /**
   * 检查字符串是否为有效的Java数字。
   *
   * <p>有效的数字包括带有类型限定符的十六进制、科学记数法和普通十进制数字。
   * 非有效数字包括{@code null}、空字符串、无穷大和NaN值。
   *
   * <p>{@code null}将返回{@code false}。空字符串（""）将返回{@code true}。
   *
   * <pre>
   * StringUtils.isNumber(null)   = false
   * StringUtils.isNumber("")     = true
   * StringUtils.isNumber("  ")   = false
   * StringUtils.isNumber("123")  = true
   * StringUtils.isNumber("12 3") = false
   * StringUtils.isNumber("ab2c") = false
   * StringUtils.isNumber("12-3") = false
   * StringUtils.isNumber("12.3") = true
   * StringUtils.isNumber(".123") = true
   * StringUtils.isNumber("1.23E3") = true
   * StringUtils.isNumber("1.23E-3") = true
   * StringUtils.isNumber("-123") = true
   * StringUtils.isNumber("+123") = true
   * StringUtils.isNumber("0x123") = true
   * StringUtils.isNumber("123f") = true
   * StringUtils.isNumber("123F") = true
   * StringUtils.isNumber("123d") = true
   * StringUtils.isNumber("123D") = true
   * StringUtils.isNumber("123l") = true
   * StringUtils.isNumber("123L") = true
   * </pre>
   *
   * @param str
   *     要检查的字符串，可以为null
   * @return 如果字符串是有效的Java数字则返回{@code true}；否则返回{@code false}
   */
  public static boolean isNumber(@Nullable final CharSequence str) {
    if (str == null) {
      return false;
    }
    final int n = str.length();
    if (n == 0) {
      return true;
    }
    int sz = n;
    boolean hasExp = false;
    boolean hasDecPoint = false;
    boolean allowSigns = false;
    boolean foundDigit = false;
    // deal with any possible sign up front
    char ch = str.charAt(0);
    final int start = ((ch == '-') || (ch == '+')) ? 1 : 0;
    if (sz > (start + 1)) {
      ch = str.charAt(start);
      final char next = str.charAt(start + 1);
      if ((ch == '0') && (next == 'x')) {
        int i = start + 2;
        if (i == sz) {
          return false; // str == "0x"
        }
        // checking hex (it can't be anything else)
        for (; i < n; i++) {
          ch = str.charAt(i);
          if (!CharUtils.isAsciiHexDigit(ch)) {
            return false;
          }
        }
        return true;
      }
    }
    --sz; // don't want to loop to the last char, check it afterwords
    // for type qualifiers
    int i = start;
    // loop to the next to last char or to the last char if we need another
    // digit to
    // make a valid number (e.g. chars[0..5] = "1234E")
    while ((i < sz) || (allowSigns && !foundDigit)) {
      ch = str.charAt(i);
      if ((ch >= '0') && (ch <= '9')) {
        foundDigit = true;
        allowSigns = false;
      } else if (ch == '.') {
        if (hasDecPoint || hasExp) {
          // two decimal points or dec in exponent
          return false;
        }
        hasDecPoint = true;
      } else if ((ch == 'e') || (ch == 'E')) {
        // we've already taken care of hex.
        if (hasExp) {
          // two E's
          return false;
        }
        if (!foundDigit) {
          return false;
        }
        hasExp = true;
        allowSigns = true;
      } else if ((ch == '+') || (ch == '-')) {
        if (!allowSigns) {
          return false;
        }
        allowSigns = false;
        foundDigit = false; // we need a digit after the E
      } else {
        return false;
      }
      ++i;
    }
    if (i < n) {
      ch = str.charAt(i);
      if ((ch >= '0') && (ch <= '9')) {
        // no type qualifier, OK
        return true;
      }
      if ((ch == 'e') || (ch == 'E')) {
        // can't have an E at the last byte
        return false;
      }
      if (!allowSigns
          && ((ch == 'd') || (ch == 'D') || (ch == 'f') || (ch == 'F'))) {
        return foundDigit;
      }
      if ((ch == 'l') || (ch == 'L')) {
        // not allowing L with an exponent
        return foundDigit && !hasExp;
      }
      // last character is illegal
      return false;
    }
    // allowSigns is {@code true} iff the val ends in 'E'
    // found digit it to make sure weird stuff like '.' and '1E-' doesn't pass
    return !allowSigns && foundDigit;
  }

  /**
   * 测试指定的字符串是否被单引号（'）或双引号（"）包围。
   *
   * @param str
   *     要测试的字符串，可能为{@code null}。
   * @return
   *    如果字符串不为{@code null}且被单引号（'）或双引号（"）包围则返回{@code true}；
   *    否则返回{@code false}。
   */
  public static boolean isQuoted(@Nullable final CharSequence str) {
    if (str == null) {
      return false;
    }
    final int len;
    if ((len = str.length()) < 2) {
      return false;
    }
    final char ch1 = str.charAt(0);
    if ((ch1 == Ascii.SINGLE_QUOTE) || (ch1 == Ascii.DOUBLE_QUOTE)) {
      final char ch2 = str.charAt(len - 1);
      return (ch1 == ch2);
    } else {
      return false;
    }
  }

  /**
   * 测试指定的字符串是否被指定的左引号和右引号包围。
   *
   * @param str
   *     要测试的字符串，可能为{@code null}。
   * @param leftQuote
   *     左引号。
   * @param rightQuote
   *     右引号。
   * @return
   *     如果字符串不为{@code null}且被左引号和右引号包围则返回{@code true}；
   *     否则返回{@code false}。
   */
  public static boolean isQuoted(@Nullable final CharSequence str,
      final char leftQuote, final char rightQuote) {
    if (str == null) {
      return false;
    }
    final int n;
    if ((n = str.length()) < 2) {
      return false;
    }
    final char ch1 = str.charAt(0);
    if (ch1 == leftQuote) {
      final char ch2 = str.charAt(n - 1);
      return (ch2 == rightQuote);
    } else {
      return false;
    }
  }

  /**
   * 比较两个字符串，如果相等则返回{@code true}。
   *
   * <p>{@code null}值会被正确处理而不抛出异常。两个{@code null}引用被认为是相等的。
   * 这个方法是区分大小写的。
   *
   * <p>示例：
   * <pre>
   * StringUtils.equals(null, null)      = true
   * StringUtils.equals(null, "abc")     = false
   * StringUtils.equals("abc", null)     = false
   * StringUtils.equals("abc", "abc")    = true
   * StringUtils.equals("abc", "ABC")    = false
   * </pre>
   *
   * @param str1
   *     第一个字符串，可以为null。
   * @param str2
   *     第二个字符串，可以为null。
   * @return 如果字符串相等（区分大小写）或都为{@code null}则返回{@code true}
   * @see String#equals(Object)
   * @see String#equalsIgnoreCase(String)
   */
  public static boolean equals(@Nullable final CharSequence str1,
      @Nullable final CharSequence str2) {
    return Equality.equals(str1, str2);
  }

  /**
   * 比较两个字符串，如果相等则返回{@code true}。
   *
   * <p>{@code null}值会被正确处理而不抛出异常。两个{@code null}引用被认为是相等的。
   * 比较可以是区分大小写或不区分大小写的，由参数控制。
   *
   * @param str1
   *     第一个字符串，可以为null。
   * @param str2
   *     第二个字符串，可以为null。
   * @param ignoreCase
   *     指示比较两个字符串时是否忽略大小写。
   * @return 如果字符串相等（区分大小写）或都为{@code null}则返回{@code true}
   * @see String#equals(Object)
   * @see String#equalsIgnoreCase(String)
   */
  public static boolean equals(@Nullable final CharSequence str1,
      @Nullable final CharSequence str2, final boolean ignoreCase) {
    if (ignoreCase) {
      return Equality.equalsIgnoreCase(str1, str2);
    } else {
      return Equality.equals(str1, str2);
    }
  }

  /**
   * 比较两个字符串，如果相等则返回{@code true}。
   *
   * <p>{@code null}值会被正确处理而不抛出异常。两个{@code null}引用被认为是相等的。
   * 比较是不区分大小写的。
   *
   * <p>示例：
   * <pre>
   * StringUtils.equals(null, null, *)       = true
   * StringUtils.equals(null, "abc", *)      = false
   * StringUtils.equals("abc", null, *)      = false
   * StringUtils.equals("abc", "abc", *)     = true
   * StringUtils.equals("abc", "ABC", true)  = true
   * StringUtils.equals("abc", "ABC", false) = false
   * </pre>
   *
   * @param str1
   *     第一个字符串，可以为null。
   * @param str2
   *     第二个字符串，可以为null。
   * @return 如果字符串相等（不区分大小写）或都为{@code null}则返回{@code true}
   * @see String#equals(Object)
   * @see String#equalsIgnoreCase(String)
   */
  public static boolean equalsIgnoreCase(@Nullable final CharSequence str1, @Nullable final CharSequence str2) {
    return Equality.equalsIgnoreCase(str1, str2);
  }

  /**
   * 检查字符序列是否以指定的代码点开始。
   *
   * <p>示例：
   * <pre>
   *   StringUtils.startsWithChar(null, 'h') = false
   *   StringUtils.startsWithChar("", 'h') = false
   *   StringUtils.startsWithChar("while", 'h') = false
   *   StringUtils.startsWithChar("hello", 'h') = true
   *   StringUtils.startsWithChar("h", 'h') = true
   *   StringUtils.startsWithChar("𠀇abc", 0x20007) = true
   * </pre>
   *
   * @param str
   *     字符序列，可以为null。
   * @param ch
   *     指定的字符（Unicode代码点）。
   * @return
   *     如果字符序列以指定的代码点开始则返回{@code true}；否则返回{@code false}。
   * @see Searcher#isAtStartOf(CharSequence)
   */
  public static boolean startsWithChar(@Nullable final CharSequence str, final int ch) {
    return new Searcher()
        .forCodePoint(ch)
        .isAtStartOf(str);
  }

  /**
   * 测试字符序列是否以被{@link CharFilter}接受的字符开始。
   *
   * @param str
   *     要测试的字符序列，可以为null。
   * @param filter
   *     用于测试字符的{@link CharFilter}，可以为null。
   * @return
   *     如果字符序列以被{@link CharFilter}接受的字符开始则返回{@code true}；否则返回{@code false}。
   *     如果字符序列或过滤器为null，返回{@code false}。
   * @see Searcher#isAtStartOf(CharSequence)
   */
  public static boolean startsWithChar(@Nullable final CharSequence str,
      @Nullable final CharFilter filter) {
    return new Searcher()
        .forCharsSatisfy(filter)
        .isAtStartOf(str);
  }

  /**
   * 测试字符序列是否以被{@link CodePointFilter}接受的代码点开始。
   *
   * @param str
   *     要测试的字符序列，可以为null。
   * @param filter
   *     用于测试代码点的{@link CodePointFilter}，可以为null。
   * @return
   *     如果字符序列以被{@link CodePointFilter}接受的代码点开始则返回{@code true}；否则返回{@code false}。
   *     如果字符序列或过滤器为null，返回{@code false}。
   * @see Searcher#isAtStartOf(CharSequence)
   */
  public static boolean startsWithChar(@Nullable final CharSequence str,
      @Nullable final CodePointFilter filter) {
    return new Searcher()
        .forCodePointsSatisfy(filter)
        .isAtStartOf(str);
  }

  /**
   * 检查字符串是否以指定的前缀开始。
   *
   * <p>{@code null}值会被正确处理而不抛出异常。两个{@code null}引用被认为是相等的。
   * 比较是区分大小写的。
   *
   * <p>示例：
   * <pre>
   * StringUtils.startsWith(null, null)          = true
   * StringUtils.startsWith(null, "abc")         = false
   * StringUtils.startsWith("", "")              = true
   * StringUtils.startsWith("", *)               = false
   * StringUtils.startsWith("abcdef", null)      = false
   * StringUtils.startsWith("abcdef", "")        = true
   * StringUtils.startsWith("abcdef", "abc")     = true
   * StringUtils.startsWith("abcdef", "abcdef")  = true
   * StringUtils.startsWith("ABCDEF", "abc")     = false
   * </pre>
   *
   * @param str
   *     要检查的字符串，可以为null。
   * @param prefix
   *     要查找的前缀，可以为null。
   * @return 如果{@code str}以{@code prefix}开始（区分大小写模式）或都为{@code null}则返回{@code true}
   * @see String#startsWith(String)
   * @see Searcher#isAtStartOf(CharSequence)
   */
  public static boolean startsWith(@Nullable final CharSequence str,
      @Nullable final CharSequence prefix) {
    return new Searcher()
        .forSubstring(prefix)
        .ignoreCase(false)
        .isAtStartOf(str);
  }

  /**
   * 检查字符串是否以指定的前缀开始（不区分大小写）。
   *
   * <p>{@code null}值会被正确处理而不抛出异常。两个{@code null}引用被认为是相等的。
   * 比较是不区分大小写的。
   *
   * <p>示例：
   * <pre>
   * StringUtils.startsWithIgnoreCase(null, null)          = true
   * StringUtils.startsWithIgnoreCase(null, "abc")         = false
   * StringUtils.startsWithIgnoreCase("", "")              = true
   * StringUtils.startsWithIgnoreCase("", *)               = false
   * StringUtils.startsWithIgnoreCase("abcdef", null)      = false
   * StringUtils.startsWithIgnoreCase("abcdef", "")        = true
   * StringUtils.startsWithIgnoreCase("abcdef", "abc")     = true
   * StringUtils.startsWithIgnoreCase("abcdef", "abcdef")  = true
   * StringUtils.startsWithIgnoreCase("ABCDEF", "abc")     = true
   * </pre>
   *
   * @param str
   *     要检查的字符串，可以为null
   * @param prefix
   *     要查找的前缀，可以为null
   * @return 如果字符串以前缀开始（不区分大小写模式）或都为{@code null}则返回{@code true}
   * @see String#startsWith(String)
   * @see Searcher#isAtStartOf(CharSequence)
   */
  public static boolean startsWithIgnoreCase(@Nullable final CharSequence str,
      @Nullable final CharSequence prefix) {
    return new Searcher()
        .forSubstring(prefix)
        .ignoreCase(true)
        .isAtStartOf(str);
  }

  /**
   * 检查字符序列是否以指定的代码点结束。
   *
   * <p>示例：
   * <pre>
   *   StringUtils.endsWithChar(null, 'o') = false
   *   StringUtils.endsWithChar("", 'o') = false
   *   StringUtils.endsWithChar("while", 'l') = false
   *   StringUtils.endsWithChar("hello", 'o') = true
   *   StringUtils.endsWithChar("o", 'o') = true
   *   StringUtils.endsWithChar("abc𠀇", 0x20007) = true
   * </pre>
   *
   * @param str
   *     字符序列，可以为null。
   * @param ch
   *     指定的字符（Unicode代码点）。
   * @return
   *     如果字符序列以指定的代码点结束则返回{@code true}；否则返回false。
   * @see Searcher#isAtEndOf(CharSequence)
   */
  public static boolean endsWithChar(@Nullable final CharSequence str, final int ch) {
    return new Searcher()
        .forCodePoint(ch)
        .isAtEndOf(str);
  }

  /**
   * 测试字符序列是否以被{@link CharFilter}接受的字符结束。
   *
   * @param str
   *     要测试的字符序列，可以为null。
   * @param filter
   *     用于测试字符的{@link CharFilter}，可以为null。
   * @return
   *     如果字符序列以被{@link CharFilter}接受的字符结束则返回{@code true}；否则返回{@code false}。
   *     如果字符序列或过滤器为null，返回{@code false}。
   * @see Searcher#isAtEndOf(CharSequence)
   */
  public static boolean endsWithChar(@Nullable final CharSequence str,
      @Nullable final CharFilter filter) {
    return new Searcher()
        .forCharsSatisfy(filter)
        .isAtEndOf(str);
  }

  /**
   * 测试字符序列是否以被{@link CodePointFilter}接受的代码点结束。
   *
   * @param str
   *     要测试的字符序列，可以为null。
   * @param filter
   *     用于测试代码点的{@link CodePointFilter}，可以为null。
   * @return
   *     如果字符序列以被{@link CodePointFilter}接受的代码点结束则返回{@code true}；否则返回{@code false}。
   *     如果字符序列或过滤器为null，返回{@code false}。
   * @see Searcher#isAtEndOf(CharSequence)
   */
  public static boolean endsWithChar(@Nullable final CharSequence str,
      @Nullable final CodePointFilter filter) {
    return new Searcher()
        .forCodePointsSatisfy(filter)
        .isAtEndOf(str);
  }

  /**
   * 检查字符串是否以指定的后缀结束（区分大小写）。
   *
   * <p>{@code null}值会被正确处理而不抛出异常。两个{@code null}引用被认为是相等的。
   * 比较是区分大小写的。
   *
   * <p>示例：
   * <pre>
   * StringUtils.endsWith(null, null)          = true
   * StringUtils.endsWith("abcdef", null)      = false
   * StringUtils.endsWith(null, "def")         = false
   * StringUtils.endsWith("", "")              = true
   * StringUtils.endsWith(*, "")               = true
   * StringUtils.endsWith("", "abc")           = false
   * StringUtils.endsWith("abcdef", "def")     = true
   * StringUtils.endsWith("abcdef", "abcdef")  = true
   * StringUtils.endsWith("ABCDEF", "def")     = false
   * </pre>
   *
   * @param str
   *     要检查的字符串，可以为null
   * @param suffix
   *     要查找的后缀，可以为null
   * @return 如果字符串以后缀结束（区分大小写模式）或都为{@code null}则返回{@code true}
   * @see String#endsWith(String)
   * @see Searcher#isAtEndOf(CharSequence)
   */
  public static boolean endsWith(@Nullable final CharSequence str,
      @Nullable final CharSequence suffix) {
    return new Searcher()
        .forSubstring(suffix)
        .ignoreCase(false)
        .isAtEndOf(str);
  }

  /**
   * 检查字符串是否以指定的后缀结束（不区分大小写）。
   *
   * <p>{@code null}值会被正确处理而不抛出异常。两个{@code null}引用被认为是相等的。
   * 比较是不区分大小写的。
   *
   * <p>示例：
   * <pre>
   * StringUtils.endsWithIgnoreCase(null, null)          = true
   * StringUtils.endsWithIgnoreCase("abcdef", null)      = false
   * StringUtils.endsWithIgnoreCase(null, "def")         = false
   * StringUtils.endsWithIgnoreCase("", "")              = true
   * StringUtils.endsWithIgnoreCase(*, "")               = true
   * StringUtils.endsWithIgnoreCase("", "abc")           = false
   * StringUtils.endsWithIgnoreCase("abcdef", "")        = true
   * StringUtils.endsWithIgnoreCase("abcdef", "def")     = true
   * StringUtils.endsWithIgnoreCase("abcdef", "abcdef")  = true
   * StringUtils.endsWithIgnoreCase("ABCDEF", "def")     = true
   * </pre>
   *
   * @param str
   *     要检查的字符串，可以为null
   * @param suffix
   *     要查找的后缀，可以为null
   * @return 如果字符串以后缀结束（不区分大小写模式）或都为{@code null}则返回{@code true}
   * @see String#endsWith(String)
   * @see Searcher#isAtEndOf(CharSequence)
   */
  public static boolean endsWithIgnoreCase(@Nullable final CharSequence str,
      @Nullable final CharSequence suffix) {
    return new Searcher()
        .forSubstring(suffix)
        .ignoreCase(true)
        .isAtEndOf(str);
  }

  /**
   * 测试字符串是否以指定的代码点开始或结束。
   *
   * @param str
   *     要测试的字符串，可以为null。
   * @param ch
   *     指定的字符（Unicode代码点）。
   * @return 如果字符串以指定的代码点开始或结束则返回{@code true}；否则返回{@code false}。
   *     如果字符串为null或空，返回false。
   * @see Searcher#isAtStartOrEndOf(CharSequence)
   */
  public static boolean startsOrEndsWithChar(@Nullable final CharSequence str,
      final int ch) {
    return new Searcher()
        .forCodePoint(ch)
        .isAtStartOrEndOf(str);
  }

  /**
   * 测试字符串是否以满足指定{@link CharFilter}的字符开始或结束。
   *
   * @param str
   *     要测试的字符串，可以为null。
   * @param filter
   *     指定的{@link CharFilter}。
   * @return 如果字符串以满足指定{@link CharFilter}的字符开始或结束则返回{@code true}；否则返回{@code false}。
   *     如果字符串为null或空，或者过滤器为null，返回false。
   * @see Searcher#isAtStartOrEndOf(CharSequence)
   */
  public static boolean startsOrEndsWithChar(@Nullable final CharSequence str,
      @Nullable final CharFilter filter) {
    return new Searcher()
        .forCharsSatisfy(filter)
        .isAtStartOrEndOf(str);
  }

  /**
   * 测试字符串是否以满足指定{@link CodePointFilter}的代码点开始或结束。
   *
   * @param str
   *     要测试的字符串，可以为null。
   * @param filter
   *     指定的{@link CodePointFilter}。
   * @return 如果字符串以满足指定{@link CodePointFilter}的代码点开始或结束则返回{@code true}；否则返回{@code false}。
   *     如果字符串为null或空，或者过滤器为null，返回false。
   * @see Searcher#isAtStartOrEndOf(CharSequence)
   */
  public static boolean startsOrEndsWithChar(@Nullable final CharSequence str,
      @Nullable final CodePointFilter filter) {
    return new Searcher()
        .forCodePointsSatisfy(filter)
        .isAtStartOrEndOf(str);
  }

  /**
   * 测试字符串是否以指定的代码点开始并结束。
   *
   * @param str
   *     要测试的字符串，可以为null。
   * @param ch
   *     指定的字符（Unicode代码点）。
   * @return 如果字符串以指定的代码点开始并结束则返回{@code true}；否则返回{@code false}。
   *     如果字符串为null或空，返回false。
   * @see Searcher#isAtStartAndEndOf(CharSequence)
   */
  public static boolean startsAndEndsWithChar(@Nullable final CharSequence str,
      final int ch) {
    return new Searcher()
        .forCodePoint(ch)
        .isAtStartAndEndOf(str);
  }

  /**
   * 测试字符串是否以满足指定{@link CharFilter}的字符开始并结束。
   *
   * @param str
   *     要测试的字符串，可以为null。
   * @param filter
   *     指定的{@link CharFilter}。
   * @return 如果字符串以满足指定{@link CharFilter}的字符开始并结束则返回{@code true}；否则返回{@code false}。
   *     如果字符串为null或空，或者过滤器为null，返回false。
   * @see Searcher#isAtStartAndEndOf(CharSequence)
   */
  public static boolean startsAndEndsWithChar(@Nullable final CharSequence str,
      @Nullable final CharFilter filter) {
    return new Searcher()
        .forCharsSatisfy(filter)
        .isAtStartAndEndOf(str);
  }

  /**
   * 测试字符串是否以满足指定{@link CodePointFilter}的代码点开始并结束。
   *
   * @param str
   *     要测试的字符串，可以为null。
   * @param filter
   *     指定的{@link CodePointFilter}。
   * @return
   *     如果字符串以满足指定{@link CodePointFilter}的代码点开始并结束则返回
   *     {@code true}；否则返回{@code false}。如果字符串为null或空，或者过滤器为null，返回false。
   * @see Searcher#isAtStartAndEndOf(CharSequence)
   */
  public static boolean startsAndEndsWithChar(@Nullable final CharSequence str,
      @Nullable final CodePointFilter filter) {
    return new Searcher()
        .forCodePointsSatisfy(filter)
        .isAtStartAndEndOf(str);
  }

  /**
   * 比较两个字符串，返回字符串开始不同的索引位置。
   *
   * <p>例如：
   * <pre>
   * StringUtils.indexOfDifference(null, null) = -1
   * StringUtils.indexOfDifference("", "") = -1
   * StringUtils.indexOfDifference("", "abc") = 0
   * StringUtils.indexOfDifference("abc", "") = 0
   * StringUtils.indexOfDifference("abc", "abc") = -1
   * StringUtils.indexOfDifference("ab", "abxyz") = 2
   * StringUtils.indexOfDifference("abcde", "abxyz") = 2
   * StringUtils.indexOfDifference("abcde", "xyz") = 0
   * StringUtils.indexOfDifference("i am a machine", "i am a robot")  = 7
   * </pre>
   *
   * @param str1
   *     第一个字符串，可以为null
   * @param str2
   *     第二个字符串，可以为null
   * @return str1和str2开始不同的索引位置；如果相等则返回-1。
   */
  public static int indexOfDifference(@Nullable final CharSequence str1,
      @Nullable final CharSequence str2) {
    if (str1 == null) {
      return (str2 == null ? -1 : 0);
    }
    if (str2 == null) {
      return 0;
    }
    int i = 0;
    for (; (i < str1.length()) && (i < str2.length()); ++i) {
      if (str1.charAt(i) != str2.charAt(i)) {
        break;
      }
    }
    if ((i < str2.length()) || (i < str1.length())) {
      return i;
    }
    return -1;
  }

  /**
   * 比较数组中的所有字符串，返回字符串开始不同的索引位置。
   *
   * <p>例如：
   * <pre>
   * StringUtils.indexOfDifference(null) = -1
   * StringUtils.indexOfDifference(new String[] {}) = -1
   * StringUtils.indexOfDifference(new String[] {"abc"}) = -1
   * StringUtils.indexOfDifference(new String[] {null, null}) = -1
   * StringUtils.indexOfDifference(new String[] {"", ""}) = -1
   * StringUtils.indexOfDifference(new String[] {"", null}) = 0
   * StringUtils.indexOfDifference(new String[] {"abc", null, null}) = 0
   * StringUtils.indexOfDifference(new String[] {null, null, "abc"}) = 0
   * StringUtils.indexOfDifference(new String[] {"", "abc"}) = 0
   * StringUtils.indexOfDifference(new String[] {"abc", ""}) = 0
   * StringUtils.indexOfDifference(new String[] {"abc", "abc"}) = -1
   * StringUtils.indexOfDifference(new String[] {"abc", "a"}) = 1
   * StringUtils.indexOfDifference(new String[] {"ab", "abxyz"}) = 2
   * StringUtils.indexOfDifference(new String[] {"abcde", "abxyz"}) = 2
   * StringUtils.indexOfDifference(new String[] {"abcde", "xyz"}) = 0
   * StringUtils.indexOfDifference(new String[] {"xyz", "abcde"}) = 0
   * StringUtils.indexOfDifference(new String[] {"i am a machine", "i am a robot"}) = 7
   * </pre>
   *
   * @param strs
   *     字符串数组，条目可能为null
   * @return 字符串开始不同的索引位置；如果所有字符串都相等则返回-1
   */
  public static int indexOfDifference(@Nullable final CharSequence... strs) {
    if ((strs == null) || (strs.length <= 1)) {
      return -1;
    }
    boolean anyStringNull = false;
    boolean allStringsNull = true;
    final int arrayLen = strs.length;
    int shortestStrLen = Integer.MAX_VALUE;
    int longestStrLen = 0;
    // find the min and max string lengths; this avoids checking to make
    // sure we are not exceeding the length of the string each time through
    // the bottom loop.
    for (final CharSequence str : strs) {
      if (str == null) {
        anyStringNull = true;
        shortestStrLen = 0;
      } else {
        allStringsNull = false;
        shortestStrLen = Math.min(str.length(), shortestStrLen);
        longestStrLen = Math.max(str.length(), longestStrLen);
      }
    }
    // handle lists containing all nulls or all empty strings
    if (allStringsNull || ((longestStrLen == 0) && !anyStringNull)) {
      return -1;
    }
    // handle lists containing some nulls or some empty strings
    if (shortestStrLen == 0) {
      return 0;
    }
    // find the position with the first difference across all strings
    int firstDiff = -1;
    for (int stringPos = 0; stringPos < shortestStrLen; stringPos++) {
      final char comparisonChar = strs[0].charAt(stringPos);
      for (int arrayPos = 1; arrayPos < arrayLen; arrayPos++) {
        if (strs[arrayPos].charAt(stringPos) != comparisonChar) {
          firstDiff = stringPos;
          break;
        }
      }
      if (firstDiff != -1) {
        break;
      }
    }
    if ((firstDiff == -1) && (shortestStrLen != longestStrLen)) {
      // we compared all of the characters up to the length of the
      // shortest string and didn't find a match, but the string lengths
      // vary, so return the length of the shortest string.
      return shortestStrLen;
    }
    return firstDiff;
  }

  /**
   * 比较两个字符串，返回它们不同的部分。（更准确地说，返回第二个字符串从与第一个字符串
   * 不同位置开始的剩余部分。）
   *
   * <p>例如：
   * <pre>
   * StringUtils.getDifference(null, null) = null
   * StringUtils.getDifference("", "") = ""
   * StringUtils.getDifference("", "abc") = "abc"
   * StringUtils.getDifference("abc", "") = ""
   * StringUtils.getDifference("abc", "abc") = ""
   * StringUtils.getDifference("ab", "abxyz") = "xyz"
   * StringUtils.getDifference("abcde", "abxyz") = "xyz"
   * StringUtils.getDifference("abcde", "xyz") = "xyz"
   * StringUtils.getDifference("i am a machine", "i am a robot") = "robot"
   * </pre>
   *
   * @param str1
   *     第一个字符串，可以为null
   * @param str2
   *     第二个字符串，可以为null
   * @return str2中与str1不同的部分；如果相等则返回空字符串
   */
  public static String getDifference(@Nullable final CharSequence str1,
      @Nullable final CharSequence str2) {
    if (str1 == null) {
      return (str2 == null ? null : str2.toString());
    }
    if (str2 == null) {
      return str1.toString();
    }
    final int at = indexOfDifference(str1, str2);
    if (at == -1) {
      return EMPTY;
    }
    return str2.subSequence(at, str2.length()).toString();
  }

  /**
   * 比较数组中的所有字符串，返回它们都共有的初始字符序列。
   *
   * <p>例如：
   * <pre>
   * StringUtils.getCommonPrefix(null) = ""
   * StringUtils.getCommonPrefix(new String[] {}) = ""
   * StringUtils.getCommonPrefix(new String[] {"abc"}) = "abc"
   * StringUtils.getCommonPrefix(new String[] {null, null}) = ""
   * StringUtils.getCommonPrefix(new String[] {"", ""}) = ""
   * StringUtils.getCommonPrefix(new String[] {"", null}) = ""
   * StringUtils.getCommonPrefix(new String[] {"abc", null, null}) = ""
   * StringUtils.getCommonPrefix(new String[] {null, null, "abc"}) = ""
   * StringUtils.getCommonPrefix(new String[] {"", "abc"}) = ""
   * StringUtils.getCommonPrefix(new String[] {"abc", ""}) = ""
   * StringUtils.getCommonPrefix(new String[] {"abc", "abc"}) = "abc"
   * StringUtils.getCommonPrefix(new String[] {"abc", "a"}) = "a"
   * StringUtils.getCommonPrefix(new String[] {"ab", "abxyz"}) = "ab"
   * StringUtils.getCommonPrefix(new String[] {"abcde", "abxyz"}) = "ab"
   * StringUtils.getCommonPrefix(new String[] {"abcde", "xyz"}) = ""
   * StringUtils.getCommonPrefix(new String[] {"xyz", "abcde"}) = ""
   * StringUtils.getCommonPrefix(new String[] {"i am a machine", "i am a robot"}) = "i am a "
   * </pre>
   *
   * @param strs
   *     字符串对象数组，条目可能为null
   * @return 数组中所有字符串共有的初始字符序列；如果数组为null、所有元素都为null或没有共同前缀则返回空字符串。
   */
  public static String getCommonPrefix(@Nullable final CharSequence... strs) {
    if ((strs == null) || (strs.length == 0)) {
      return EMPTY;
    }
    final int smallestIndexOfDiff = indexOfDifference(strs);
    if (smallestIndexOfDiff == -1) {
      // all strings were identical
      if (strs[0] == null) {
        return EMPTY;
      }
      return strs[0].toString();
    } else if (smallestIndexOfDiff == 0) {
      // there were no common initial characters
      return EMPTY;
    } else {
      // we found a common initial character sequence
      return strs[0].subSequence(0, smallestIndexOfDiff).toString();
    }
  }

  /**
   * 计算两个字符串之间的Levenshtein距离。
   *
   * <p>这是将一个字符串转换为另一个字符串所需的更改次数，其中每次更改都是单个字符的修改
   * （删除、插入或替换）。
   *
   * <p>之前的Levenshtein距离算法实现来自
   * <a href="http://www.merriampark.com/ld.htm">http://www.merriampark.com/ld.htm</a>
   *
   * <p>Chas Emerick编写了一个Java实现，避免了在处理非常大的字符串时可能出现的
   * OutOfMemoryError。<br> 这个Levenshtein距离算法的实现来自
   * <a href="http://www.merriampark.com/ldjava.htm">http://www.merriampark.com/ldjava.htm</a>。
   * <pre>
   * StringUtils.getLevenshteinDistance(null, *)             = IllegalArgumentException
   * StringUtils.getLevenshteinDistance(*, null)             = IllegalArgumentException
   * StringUtils.getLevenshteinDistance("","")               = 0
   * StringUtils.getLevenshteinDistance("","a")              = 1
   * StringUtils.getLevenshteinDistance("aaapppp", "")       = 7
   * StringUtils.getLevenshteinDistance("frog", "fog")       = 1
   * StringUtils.getLevenshteinDistance("fly", "ant")        = 3
   * StringUtils.getLevenshteinDistance("elephant", "hippo") = 7
   * StringUtils.getLevenshteinDistance("hippo", "elephant") = 7
   * StringUtils.getLevenshteinDistance("hippo", "zzzzzzzz") = 8
   * StringUtils.getLevenshteinDistance("hello", "hallo")    = 1
   * </pre>
   *
   * @param str1
   *     第一个字符串，不能为null
   * @param str2
   *     第二个字符串，不能为null
   * @return 结果距离
   * @throws IllegalArgumentException
   *     如果任一字符串输入为{@code null}
   */
  public static int getLevenshteinDistance(final CharSequence str1,
      final CharSequence str2) {
    if ((str1 == null) || (str2 == null)) {
      throw new IllegalArgumentException("Strings must not be null");
    }
    /*
     * The difference between this impl. and the previous is that, rather than
     * creating and retaining a matrix of size s.length()+1 by t.length()+1, we
     * maintain two single-dimensional arrays of length s.length()+1. The first,
     * d, is the 'current working' distance array that maintains the newest
     * distance cost counts as we iterate through the characters of String s.
     * Each time we increment the index of String t we are comparing, d is
     * copied to p, the second int[]. Doing so allows us to retain the previous
     * cost counts as required by the algorithm (taking the minimum of the cost
     * count to the left, up one, and diagonally up and to the left of the
     * current cost count being calculated). (Note that the arrays aren't really
     * copied anymore, just switched...this is clearly better than cloning an array or
     * doing a System.arraycopy() each time through the outer loop.)
     * Effectively, the difference between the two implementations is this one
     * does not cause an out of memory condition when calculating the LD over
     * two very large strings.
     */
    int n = str1.length(); // length of str1
    int m = str2.length(); // length of str2
    if (n == 0) {
      return m;
    } else if (m == 0) {
      return n;
    }
    CharSequence s = str1;
    CharSequence t = str2;
    if (n > m) {
      // swap the input strings to consume less memory
      final CharSequence tmp = s;
      s = t;
      t = tmp;
      n = m;
      m = t.length();
    }
    int[] p = new int[n + 1]; // 'previous' cost array, horizontally
    int[] d = new int[n + 1]; // cost array, horizontally
    int[] tmp; // placeholder to assist in swapping p and d
    // indexes into strings s and t
    int i; // iterates through s
    int j; // iterates through t
    char jth; // jth character of t
    int cost; // cost
    for (i = 0; i <= n; i++) {
      p[i] = i;
    }
    for (j = 1; j <= m; j++) {
      jth = t.charAt(j - 1);
      d[0] = j;
      for (i = 1; i <= n; i++) {
        cost = s.charAt(i - 1) == jth ? 0 : 1;
        // minimum of cell to the left+1, to the top+1, diagonally left and up
        // +cost
        d[i] = Math.min(Math.min(d[i - 1] + 1, p[i] + 1), p[i - 1] + cost);
      }
      // copy current distance counts to 'previous row' distance counts
      tmp = p;
      p = d;
      d = tmp;
    }
    // our last action in the above loop was to switch d and p, so p now
    // actually has the most recent cost counts
    return p[n];
  }

  /**
   * 在字符串中查找指定字符的第一次出现位置。
   *
   * <p>如果字符串为{@code null}或空("")，将返回{@code -1}。负数的起始位置被视为零。
   * 起始位置大于字符串长度时返回{@code -1}。
   * <pre>
   * StringUtils.indexOfChar(null, *, *)          = -1
   * StringUtils.indexOfChar("", *, *)            = -1
   * StringUtils.indexOfChar("aabaabaa", 'b', 0)  = 2
   * StringUtils.indexOfChar("aabaabaa", 'b', 3)  = 5
   * StringUtils.indexOfChar("aabaabaa", 'b', 9)  = -1
   * StringUtils.indexOfChar("aabaabaa", 'b', -1) = 2
   * </pre>
   *
   * @param str
   *     要检查的字符串，可能为null
   * @param ch
   *     要查找的字符
   * @param startIndex
   *     开始搜索的位置。如果为负数，则效果与零相同：整个字符串都可以被搜索。
   *     如果大于字符串的长度，则效果与等于字符串长度相同：返回-1。
   * @return 搜索字符的第一次出现位置，如果没有匹配或字符串输入为{@code null}则返回-1
   * @see Searcher
   */
  public static int indexOfChar(@Nullable final CharSequence str,
      final char ch, final int startIndex) {
    return new Searcher()
        .forChar(ch)
        .startFrom(startIndex)
        .findFirstIndexIn(str);
  }

  /**
   * 在字符串中查找指定字符的第一次出现位置。
   *
   * <p>如果字符串为{@code null}或空("")，将返回{@code -1}。负数的起始位置被视为零。
   * 起始位置大于字符串长度时返回{@code -1}。
   * <pre>
   * StringUtils.indexOfChar(null, *, *)          = -1
   * StringUtils.indexOfChar("", *, *)            = -1
   * StringUtils.indexOfChar("aabaabaa", 'b', 0)  = 2
   * StringUtils.indexOfChar("aabaabaa", 'b', 3)  = 5
   * StringUtils.indexOfChar("aabaabaa", 'b', 9)  = -1
   * StringUtils.indexOfChar("aabaabaa", 'b', -1) = 2
   * </pre>
   *
   * @param str
   *     要检查的字符串，可能为null。
   * @param ch
   *     要查找的字符。
   * @param startIndex
   *     开始搜索的位置。如果为负数，则效果与零相同。
   * @param endIndex
   *     结束搜索的位置。
   * @return 搜索字符的第一次出现位置，如果没有匹配或字符串输入为{@code null}则返回-1。
   * @see Searcher
   */
  public static int indexOfChar(@Nullable final CharSequence str,
      final char ch, final int startIndex, final int endIndex) {
    return new Searcher()
        .forChar(ch)
        .startFrom(startIndex)
        .endBefore(endIndex)
        .findFirstIndexIn(str);
  }

  /**
   * 在字符串中查找第一个满足给定过滤器条件的字符的出现位置。
   *
   * <p>如果字符串为{@code null}或过滤器为{@code null}，将返回{@code -1}。
   *
   * @param str
   *     要检查的字符串，可能为null
   * @param filter
   *     指定要搜索字符条件的{@link CharFilter}。
   * @param startIndex
   *     开始搜索的位置。如果为负数，则效果与零相同：整个字符串都可以被搜索。
   *     如果大于字符串的长度，则效果与等于字符串长度相同：返回-1。
   * @return 字符串{@code str}中从{@code startIndex}开始满足{@code filter}条件的
   *     第一个字符的位置；如果没有这样的字符则返回-1。
   * @see Searcher
   */
  public static int indexOfChar(@Nullable final CharSequence str,
      @Nullable final CharFilter filter, final int startIndex) {
    return new Searcher()
        .forCharsSatisfy(filter)
        .startFrom(startIndex)
        .findFirstIndexIn(str);
  }

  /**
   * 在字符串中查找第一个满足给定过滤器条件的字符的出现位置。
   *
   * <p>如果字符串为{@code null}或过滤器为{@code null}，将返回{@code -1}。
   *
   * @param str
   *     要检查的字符串，可能为null。
   * @param filter
   *     指定要搜索字符条件的{@link CodePointFilter}。
   * @param startIndex
   *     开始搜索的位置。如果为负数，则效果与零相同。
   * @param endIndex
   *     结束搜索的位置。如果大于{@code str}的长度，则效果与{@code str}的长度相同。
   * @return
   *     字符串{@code str}中从{@code startIndex}开始到{@code endIndex}之前满足
   *     {@code filter}条件的最后一个字符的索引；如果没有这样的字符则返回-1。
   *     如果{@code str}为{@code null}或空，返回-1。如果{@code [startIndex, endIndex)}
   *     不是字符串{@code str}的有效范围，返回-1。如果{@code filter}为{@code null}，返回-1。
   * @see Searcher
   */
  public static int indexOfChar(@Nullable final CharSequence str,
      @Nullable final CharFilter filter, final int startIndex,
      final int endIndex) {
    return new Searcher()
        .forCharsSatisfy(filter)
        .startFrom(startIndex)
        .endBefore(endIndex)
        .findFirstIndexIn(str);
  }

  /**
   * 在字符串中查找指定Unicode码点的第一次出现位置。
   *
   * <p>如果字符串为{@code null}或空("")，将返回{@code -1}。负数的起始位置被视为零。
   * 起始位置大于字符串长度时返回{@code -1}。
   * <pre>
   * StringUtils.indexOfChar(null, *, *)          = -1
   * StringUtils.indexOfChar("", *, *)            = -1
   * StringUtils.indexOfChar("aabaabaa", 'b', 0)  = 2
   * StringUtils.indexOfChar("aabaabaa", 'b', 3)  = 5
   * StringUtils.indexOfChar("aabaabaa", 'b', 9)  = -1
   * StringUtils.indexOfChar("aabaabaa", 'b', -1) = 2
   * </pre>
   *
   * @param str
   *     要检查的字符串，可能为null。
   * @param codePoint
   *     要查找的Unicode码点。
   * @param startIndex
   *     开始搜索的位置。如果为负数，则效果与零相同：整个字符串都可以被搜索。
   *     如果大于字符串的长度，则效果与等于字符串长度相同：返回-1。
   * @return 搜索Unicode码点的第一次出现位置，如果没有匹配或字符串输入为{@code null}则返回-1
   * @see Searcher
   */
  public static int indexOfCodePoint(@Nullable final CharSequence str,
      final int codePoint, final int startIndex) {
    return new Searcher()
        .forCodePoint(codePoint)
        .startFrom(startIndex)
        .findFirstIndexIn(str);
  }

  /**
   * 在字符串中查找指定Unicode码点的第一次出现位置。
   *
   * <p>如果字符串为{@code null}或空("")，将返回{@code -1}。负数的起始位置被视为零。
   * 起始位置大于字符串长度时返回{@code -1}。
   * <pre>
   * StringUtils.indexOfChar(null, *, *)          = -1
   * StringUtils.indexOfChar("", *, *)            = -1
   * StringUtils.indexOfChar("aabaabaa", 'b', 0)  = 2
   * StringUtils.indexOfChar("aabaabaa", 'b', 3)  = 5
   * StringUtils.indexOfChar("aabaabaa", 'b', 9)  = -1
   * StringUtils.indexOfChar("aabaabaa", 'b', -1) = 2
   * </pre>
   *
   * @param str
   *     要检查的字符串，可能为null。
   * @param codePoint
   *     要查找的Unicode码点。
   * @param startIndex
   *     开始搜索的位置。如果为负数，则效果与零相同。
   * @param endIndex
   *     结束搜索的位置。
   * @return 搜索Unicode码点的第一次出现位置，如果没有匹配或字符串输入为{@code null}则返回-1。
   * @see Searcher
   */
  public static int indexOfCodePoint(@Nullable final CharSequence str,
      final int codePoint, final int startIndex, final int endIndex) {
    return new Searcher()
        .forCodePoint(codePoint)
        .startFrom(startIndex)
        .endBefore(endIndex)
        .findFirstIndexIn(str);
  }

  /**
   * 在字符串中查找第一个满足给定过滤器条件的Unicode码点的出现位置。
   *
   * <p>如果字符串为{@code null}或过滤器为{@code null}，将返回{@code -1}。
   *
   * @param str
   *     要检查的字符串，可能为null。
   * @param filter
   *     指定要搜索字符条件的{@link CodePointFilter}。
   * @return
   *     字符串{@code str}中满足{@code filter}条件的第一个Unicode码点的索引；
   *     如果没有这样的字符则返回-1。如果{@code str}为{@code null}或空，返回-1。
   *     如果{@code filter}为{@code null}，返回-1。
   * @see Searcher
   */
  public static int indexOfCodePoint(@Nullable final CharSequence str,
      @Nullable final CodePointFilter filter) {
    return new Searcher()
        .forCodePointsSatisfy(filter)
        .findFirstIndexIn(str);
  }

  /**
   * 在字符串中查找第一个满足给定过滤器条件的Unicode码点的出现位置。
   *
   * <p>如果字符串为{@code null}或过滤器为{@code null}，将返回{@code -1}。
   *
   * @param str
   *     要检查的字符串，可能为null。
   * @param filter
   *     指定要搜索字符条件的{@link CodePointFilter}。
   * @param startIndex
   *     开始搜索的位置。如果为负数，则效果与零相同。
   * @return
   *     字符串{@code str}中从{@code startIndex}开始满足{@code filter}条件的
   *     第一个Unicode码点的索引；如果没有这样的字符则返回-1。
   *     如果{@code str}为{@code null}或空，返回-1。如果{@code [startIndex, str.length())}
   *     不是字符串{@code str}的有效范围，返回-1。如果{@code filter}为{@code null}，返回-1。
   * @see Searcher
   */
  public static int indexOfCodePoint(@Nullable final CharSequence str,
      @Nullable final CodePointFilter filter, final int startIndex) {
    return new Searcher()
        .forCodePointsSatisfy(filter)
        .startFrom(startIndex)
        .findFirstIndexIn(str);
  }

  /**
   * 在字符串中查找第一个满足给定过滤器条件的Unicode码点的出现位置。
   *
   * <p>如果字符串为{@code null}或过滤器为{@code null}，将返回{@code -1}。
   *
   * @param str
   *     要检查的字符串，可能为null。
   * @param filter
   *     指定要搜索字符条件的{@link CodePointFilter}。
   * @param startIndex
   *     开始搜索的位置。如果为负数，则效果与零相同。
   * @param endIndex
   *     结束搜索的位置。如果大于{@code str}的长度，则效果与{@code str}的长度相同。
   * @return
   *     字符串{@code str}中从{@code startIndex}开始到{@code endIndex}之前满足
   *     {@code filter}条件的第一个Unicode码点的索引；如果没有这样的字符则返回-1。
   *     如果{@code str}为{@code null}或空，返回-1。如果{@code [startIndex, endIndex)}
   *     不是字符串{@code str}的有效范围，返回-1。如果{@code filter}为{@code null}，返回-1。
   * @see Searcher
   */
  public static int indexOfCodePoint(@Nullable final CharSequence str,
      @Nullable final CodePointFilter filter, final int startIndex,
      final int endIndex) {
    return new Searcher()
        .forCodePointsSatisfy(filter)
        .startFrom(startIndex)
        .endBefore(endIndex)
        .findFirstIndexIn(str);
  }


  /**
   * 在字符串中查找给定字符集合中任意字符的第一次出现位置。
   *
   * <p>如果字符串为{@code null}，将返回{@code -1}。如果搜索数组为{@code null}或长度为零，
   * 将返回{@code -1}。
   * <pre>
   * StringUtils.indexOfAnyChar(null, *, *)                 = -1
   * StringUtils.indexOfAnyChar("", *, *)                   = -1
   * StringUtils.indexOfAnyChar(*, null, *)                 = -1
   * StringUtils.indexOfAnyChar(*, [], *)                   = -1
   * StringUtils.indexOfAnyChar("zzabyycdxxz",['z','a'], 0) = 0
   * StringUtils.indexOfAnyChar("zzabyycdxxz",['z','a'], 1) = 1
   * StringUtils.indexOfAnyChar("zzabyycdxxz",['z','a'], 2) = 2
   * StringUtils.indexOfAnyChar("zzabyycdxxz",['z','a'], 5) = 10
   * StringUtils.indexOfAnyChar("zzabyycdxxz",['b','y'], 1) = 3
   * StringUtils.indexOfAnyChar("aba", ['z'], *)            = -1
   * </pre>
   *
   * @param str
   *     要检查的字符串，可能为null
   * @param chars
   *     要搜索的字符，可能为null
   * @param startIndex
   *     开始搜索的位置。如果为负数，则效果与零相同：整个字符串都可以被搜索。
   *     如果大于字符串的长度，则效果与等于字符串长度相同：返回-1。
   * @return 任意字符的索引，如果没有匹配或输入为null则返回-1
   * @see Searcher
   */
  public static int indexOfAnyChar(@Nullable final CharSequence str,
      @Nullable final char[] chars, final int startIndex) {
    return new Searcher()
        .forCharsIn(chars)
        .startFrom(startIndex)
        .findFirstIndexIn(str);
  }

  /**
   * 在字符串中查找给定字符集合中任意字符的第一次出现位置。
   *
   * <p>如果字符串为{@code null}，将返回{@code -1}。如果搜索字符串为{@code null}，
   * 将返回{@code -1}。
   * <pre>
   * StringUtils.indexOfAnyChar(null, *, *)                 = -1
   * StringUtils.indexOfAnyChar("", *, *)                   = -1
   * StringUtils.indexOfAnyChar(*, null, *)                 = -1
   * StringUtils.indexOfAnyChar(*, "", *)                   = -1
   * StringUtils.indexOfAnyChar("zzabyycdxxz", "za", 0)     = 0
   * StringUtils.indexOfAnyChar("zzabyycdxxz", "za", 1)     = 1
   * StringUtils.indexOfAnyChar("zzabyycdxxz", "za", 2)     = 2
   * StringUtils.indexOfAnyChar("zzabyycdxxz", "za", 5)     = 10
   * StringUtils.indexOfAnyChar("zzabyycdxxz", "by", 1)     = 3
   * StringUtils.indexOfAnyChar("aba", "z", *)              = -1
   * </pre>
   *
   * @param str
   *     要检查的字符串，可能为null
   * @param chars
   *     要搜索的字符，可能为null
   * @param startIndex
   *     开始搜索的位置。如果为负数，则效果与零相同：整个字符串都可以被搜索。
   *     如果大于字符串的长度，则效果与等于字符串长度相同：返回-1。
   * @return 任意字符的索引，如果没有匹配或输入为null则返回-1
   * @see Searcher
   */
  public static int indexOfAnyChar(@Nullable final CharSequence str,
      @Nullable final CharSequence chars, final int startIndex) {
    return new Searcher()
        .forCharsIn(chars)
        .startFrom(startIndex)
        .findFirstIndexIn(str);
  }

  /**
   * 在字符串中查找第一个不在给定字符集合中的字符的出现位置。
   *
   * <p>如果字符串为{@code null}，将返回{@code -1}。如果搜索数组为{@code null}或长度为零，
   * 将返回{@code -1}。
   * <pre>
   * StringUtils.indexOfAnyCharBut(null, *, *)                 = -1
   * StringUtils.indexOfAnyCharBut("", *, *)                   = -1
   * StringUtils.indexOfAnyCharBut("abc", null, 0)             = 0
   * StringUtils.indexOfAnyCharBut("abc", null, 1)             = 1
   * StringUtils.indexOfAnyCharBut("abc", null, 100)           = -1
   * StringUtils.indexOfAnyCharBut("abc", [], 0)               = 0
   * StringUtils.indexOfAnyCharBut("abc", [], 1)               = 1
   * StringUtils.indexOfAnyCharBut("abc", [], 100)             = -1
   * StringUtils.indexOfAnyCharBut("zzabyycdxx", ['z','a'], 0) = 3
   * StringUtils.indexOfAnyCharBut("zzabyycdxx", ['a'], 0)     = 0
   * StringUtils.indexOfAnyCharBut("aba", ['a', 'b'], *)       = -1
   * </pre>
   *
   * @param str
   *     要检查的字符串，可能为null
   * @param searchChars
   *     要搜索的字符，可能为null
   * @param fromIndex
   *     开始搜索的位置。如果为负数，则效果与零相同：整个字符串都可以被搜索。
   *     如果大于字符串的长度，则效果与等于字符串长度相同：返回-1。
   * @return 任意字符的索引，如果没有匹配或输入为null则返回-1
   * @see Searcher
   */
  public static int indexOfAnyCharBut(@Nullable final CharSequence str,
      @Nullable final char[] searchChars, final int fromIndex) {
    return new Searcher()
        .forCharsNotIn(searchChars)
        .startFrom(fromIndex)
        .findFirstIndexIn(str);
  }

  /**
   * 在字符串中查找第一个不在给定字符集合中的字符的出现位置。
   *
   * <p>如果字符串为{@code null}，将返回{@code -1}。如果搜索字符串为{@code null}，
   * 将返回{@code -1}。
   * <pre>
   * StringUtils.indexOfAnyCharBut(null, *, *)            = -1
   * StringUtils.indexOfAnyCharBut("", *, *)              = -1
   * StringUtils.indexOfAnyCharBut("abc", null, 0)        = 0
   * StringUtils.indexOfAnyCharBut("abc", null, 1)        = 1
   * StringUtils.indexOfAnyCharBut("abc", null, 100)      = -1
   * StringUtils.indexOfAnyCharBut("abc", "", 0)          = 0
   * StringUtils.indexOfAnyCharBut("abc", "", 1)          = 1
   * StringUtils.indexOfAnyCharBut("abc", "", 100)        = -1
   * StringUtils.indexOfAnyCharBut("zzabyycdxx", "za", 0) = 3
   * StringUtils.indexOfAnyCharBut("zzabyycdxx", "", 0)   = 0
   * StringUtils.indexOfAnyCharBut("aba","ab", *)         = -1
   * </pre>
   *
   * @param str
   *     要检查的字符串，可能为null
   * @param searchChars
   *     要搜索的字符，可能为null
   * @param fromIndex
   *     开始搜索的位置。如果为负数，则效果与零相同：整个字符串都可以被搜索。
   *     如果大于字符串的长度，则效果与等于字符串长度相同：返回-1。
   * @return 任意字符的索引，如果没有匹配或输入为null则返回-1
   * @see Searcher
   */
  public static int indexOfAnyCharBut(@Nullable final CharSequence str,
      @Nullable final CharSequence searchChars, final int fromIndex) {
    return new Searcher()
        .forCharsNotIn(searchChars)
        .startFrom(fromIndex)
        .findFirstIndexIn(str);
  }

  /**
   * 在字符串中查找子字符串的第一次出现位置，处理{@code null}。
   * 此方法使用{@link String#indexOf(String, int)}。
   *
   * <p>如果字符串为{@code null}，将返回{@code -1}。负数的起始位置被视为零。
   * 空("")搜索字符串总是匹配。起始位置大于字符串长度时只匹配空搜索字符串。
   * <pre>
   * StringUtils.indexOf(null, *, *, *)          = -1
   * StringUtils.indexOf(*, null, *, *)          = -1
   * StringUtils.indexOf("", "", 0, *)           = -1
   * StringUtils.indexOf("aabaabaa", "a", 0, *)  = 0
   * StringUtils.indexOf("aabaabaa", "A", 0, true)  = 0
   * StringUtils.indexOf("aabaabaa", "A", 0, false)  = -1
   * StringUtils.indexOf("aabaabaa", "b", 0, *)  = 2
   * StringUtils.indexOf("aabaabaa", "ab", 0, *) = 1
   * StringUtils.indexOf("aabaabaa", "aB", 0, true) =  1
   * StringUtils.indexOf("aabaabaa", "aB", 0, false) = -1
   * StringUtils.indexOf("aabaabaa", "b", 3, *)  = 5
   * StringUtils.indexOf("aabaabaa", "b", 9, *)  = -1
   * StringUtils.indexOf("aabaabaa", "b", -1, *) = 2
   * StringUtils.indexOf("aabaabaa", "", 2, *)   = 2
   * StringUtils.indexOf("abc", "", 9, *)        = 3
   * </pre>
   *
   * @param str
   *     要检查的字符串，可能为null
   * @param search
   *     要查找的字符串，可能为null
   * @param fromIndex
   *     开始搜索的位置。如果为负数，则效果与零相同：整个字符串都可以被搜索。
   *     如果大于字符串的长度，则效果与等于字符串长度相同：返回-1。
   * @param ignoreCase
   *     比较字符串时是否忽略大小写。
   * @return 搜索字符串的第一次出现位置，如果没有匹配或{@code null}字符串输入则返回-1
   * @see Searcher
   */
  public static int indexOf(@Nullable final CharSequence str,
      @Nullable final CharSequence search, final int fromIndex,
      final boolean ignoreCase) {
    return new Searcher()
        .forSubstring(search)
        .startFrom(fromIndex)
        .ignoreCase(ignoreCase)
        .findFirstIndexIn(str);
  }

  /**
   * 查找一组潜在子字符串中任意一个的第一次出现位置。
   *
   * <p>如果字符串为{@code null}，将返回{@code -1}。如果搜索数组为{@code null}或长度为零，
   * 将返回{@code -1}。{@code null}的搜索数组项将被忽略，但包含""的搜索数组将在
   * {@code str}不为{@code null}时返回{@code 0}。此方法使用{@link String#indexOf(String)}。
   * <pre>
   * StringUtils.indexOfAny(null, *, *, *)                     = -1
   * StringUtils.indexOfAny(*, null, *, *)                     = -1
   * StringUtils.indexOfAny(*, [], *, *)                       = -1
   * StringUtils.indexOfAny("zzabyycdxx", ["ab","cd"], 0, *)   = 2
   * StringUtils.indexOfAny("zzabyycdxx", ["Ab","cd"], 0, true)  = 2
   * StringUtils.indexOfAny("zzabyycdxx", ["Ab","cd"], 0, false) = 6
   * StringUtils.indexOfAny("zzabyycdxx", ["cd","ab"], 0, *)   = 2
   * StringUtils.indexOfAny("zzabyycdxx", ["mn","op"], *, *)   = -1
   * StringUtils.indexOfAny("zzabyycdxx", ["zab","aby"], 0, *) = 1
   * StringUtils.indexOfAny("zzabyycdxx", [""], 0, *)          = 0
   * StringUtils.indexOfAny("", [""], 0, *)                    = -1
   * StringUtils.indexOfAny("", ["a"], *, *)                   = -1
   * </pre>
   *
   * @param str
   *     要检查的字符串，可能为null
   * @param searches
   *     要搜索的字符串，可能为null
   * @param fromIndex
   *     开始搜索的位置。如果为负数，则效果与零相同：整个字符串都可以被搜索。
   *     如果大于字符串的长度，则效果与等于字符串长度相同：返回-1。
   * @param ignoreCase
   *     比较字符串时是否忽略大小写。
   * @return str中任意searchStrs的第一次出现位置，如果没有匹配则返回-1
   * @see Searcher
   */
  public static int indexOfAny(@Nullable final CharSequence str,
      @Nullable final String[] searches, final int fromIndex,
      final boolean ignoreCase) {
    return new Searcher()
        .forSubstringsIn(searches)
        .startFrom(fromIndex)
        .ignoreCase(ignoreCase)
        .findFirstIndexIn(str);
  }

  /**
   * 从指定位置开始在字符串中查找字符的最后出现位置，处理{@code null}。
   * 此方法使用{@link String#lastIndexOf(int, int)}。
   *
   * <p>如果字符串为{@code null}或空("")，将返回{@code -1}。负数的起始位置返回{@code -1}。
   * 起始位置大于字符串长度时搜索整个字符串。
   * <pre>
   * StringUtils.lastIndexOfChar(null, *, *)          = -1
   * StringUtils.lastIndexOfChar("", *,  *)           = -1
   * StringUtils.lastIndexOfChar("aabaabaa", 'b', 8)  = 5
   * StringUtils.lastIndexOfChar("aabaabaa", 'b', 4)  = 2
   * StringUtils.lastIndexOfChar("aabaabaa", 'b', 0)  = -1
   * StringUtils.lastIndexOfChar("aabaabaa", 'b', 9)  = 5
   * StringUtils.lastIndexOfChar("aabaabaa", 'b', -1) = -1
   * StringUtils.lastIndexOfChar("aabaabaa", 'a', 0)  = 0
   * </pre>
   *
   * @param str
   *     要检查的字符串，可能为null
   * @param ch
   *     要查找的字符
   * @param fromIndex
   *     开始搜索的位置（反向搜索）。fromIndex的值没有限制。如果它大于或等于
   *     此字符串的长度，效果与它等于此字符串长度减一相同：整个字符串都可以被搜索。
   *     如果为负数，效果与-1相同：返回-1。
   * @return 搜索字符的最后出现位置，如果没有匹配或{@code null}字符串输入则返回-1
   * @see Searcher
   */
  public static int lastIndexOfChar(@Nullable final CharSequence str,
      final char ch, final int fromIndex) {
    return new Searcher()
        .forChar(ch)
        .endBefore(Math.min(Integer.MAX_VALUE - 1, fromIndex) + 1)
        .findLastIndexIn(str);
  }

  /**
   * 在字符串中搜索满足给定过滤器条件的字符的最后出现位置。
   *
   * <p>如果字符串为{@code null}，将返回{@code -1}。如果过滤器为{@code null}，
   * 将返回{@code -1}。
   *
   * @param str
   *     要检查的字符串，可能为null
   * @param filter
   *     一个{@link CharFilter}指定要搜索的字符条件。
   * @return 字符串{@code str}中被{@code filter}接受的字符的最后出现位置。
   * @see Searcher
   */
  public static int lastIndexOfChar(@Nullable final CharSequence str,
      @Nullable final CharFilter filter) {
    return new Searcher()
        .forCharsSatisfy(filter)
        .findLastIndexIn(str);
  }

  /**
   * 在字符串中搜索满足给定过滤器条件的字符的最后出现位置。
   *
   * <p>如果字符串为{@code null}，将返回{@code -1}。如果过滤器为{@code null}，
   * 将返回{@code -1}。
   *
   * @param str
   *     要检查的字符串，可能为null
   * @param filter
   *     一个{@link CharFilter}指定要搜索的字符条件。
   * @param fromIndex
   *     开始搜索的位置（反向搜索）。fromIndex的值没有限制。如果它大于或等于
   *     此字符串的长度，效果与它等于此字符串长度减一相同：整个字符串都可以被搜索。
   *     如果为负数，效果与-1相同：返回-1。
   * @return 字符串{@code str}中从{@code fromIndex}开始被{@code filter}接受的字符的最后出现位置。
   * @see Searcher
   */
  public static int lastIndexOfChar(@Nullable final CharSequence str,
      @Nullable final CharFilter filter, final int fromIndex) {
    return new Searcher()
        .forCharsSatisfy(filter)
        .endBefore(Math.min(Integer.MAX_VALUE - 1, fromIndex) + 1)
        .findLastIndexIn(str);
  }

  /**
   * 在字符串中搜索给定字符集合中任意字符的最后出现位置。
   *
   * <p>如果字符串为{@code null}，将返回{@code -1}。如果搜索数组为{@code null}或长度为零，
   * 将返回{@code -1}。
   * <pre>
   * StringUtils.lastIndexOfAnyChar(null, *, *)                 = -1
   * StringUtils.lastIndexOfAnyChar("", *, *)                   = -1
   * StringUtils.lastIndexOfAnyChar(*, null, *)                 = -1
   * StringUtils.lastIndexOfAnyChar(*, [], *)                   = -1
   * StringUtils.lastIndexOfAnyChar("zzabyycdxxz",['z','a'], 0) = 0
   * StringUtils.lastIndexOfAnyChar("zzabyycdxxz",['z','a'], 1) = 1
   * StringUtils.lastIndexOfAnyChar("zzabyycdxxz",['z','a'], 2) = 2
   * StringUtils.lastIndexOfAnyChar("zzabyycdxxz",['z','a'], 5) = 10
   * StringUtils.lastIndexOfAnyChar("zzabyycdxxz",['b','y'], 1) = 3
   * StringUtils.lastIndexOfAnyChar("aba", ['z'], *)            = -1
   * </pre>
   *
   * @param str
   *     要检查的字符串，可能为null
   * @param searchChars
   *     要搜索的字符，可能为null
   * @param fromIndex
   *     开始搜索的位置（反向搜索）。fromIndex的值没有限制。如果它大于或等于
   *     此字符串的长度，效果与它等于此字符串长度减一相同：整个字符串都可以被搜索。
   *     如果为负数，效果与-1相同：返回-1。
   * @return 任意字符的索引，如果没有匹配或输入为null则返回-1
   * @see Searcher
   */
  public static int lastIndexOfAnyChar(@Nullable final CharSequence str,
      @Nullable final char[] searchChars, final int fromIndex) {
    return new Searcher()
        .forCharsIn(searchChars)
        .endBefore(Math.min(Integer.MAX_VALUE - 1, fromIndex) + 1)
        .findLastIndexIn(str);
  }

  /**
   * 在字符串中查找给定字符集合中任意字符的最后出现位置。
   *
   * <p>如果字符串为{@code null}，将返回{@code -1}。如果搜索字符串为{@code null}，
   * 将返回{@code -1}。
   * <pre>
   * StringUtils.lastIndexOfAnyChar(null, *, *)                 = -1
   * StringUtils.lastIndexOfAnyChar("", *, *)                   = -1
   * StringUtils.lastIndexOfAnyChar(*, null, *)                 = -1
   * StringUtils.lastIndexOfAnyChar(*, "", *)                   = -1
   * StringUtils.lastIndexOfAnyChar("zzabyycdxxz", "za", 0)     = 0
   * StringUtils.lastIndexOfAnyChar("zzabyycdxxz", "za", 1)     = 1
   * StringUtils.lastIndexOfAnyChar("zzabyycdxxz", "za", 2)     = 2
   * StringUtils.lastIndexOfAnyChar("zzabyycdxxz", "za", 5)     = 10
   * StringUtils.lastIndexOfAnyChar("zzabyycdxx", "by", 1)     = 3
   * StringUtils.lastIndexOfAnyChar("aba", "z", *)              = -1
   * </pre>
   *
   * @param str
   *     要检查的字符串，可能为null
   * @param searchChars
   *     要搜索的字符，可能为null
   * @param fromIndex
   *     开始搜索的位置（反向搜索）。fromIndex的值没有限制。如果它大于或等于
   *     此字符串的长度，效果与它等于此字符串长度减一相同：整个字符串都可以被搜索。
   *     如果为负数，效果与-1相同：返回-1。
   * @return 任意字符的索引，如果没有匹配或输入为null则返回-1
   * @see Searcher
   */
  public static int lastIndexOfAnyChar(@Nullable final CharSequence str,
      @Nullable final String searchChars, final int fromIndex) {
    return new Searcher()
        .forCharsIn(searchChars)
        .endBefore(Math.min(Integer.MAX_VALUE - 1, fromIndex) + 1)
        .findLastIndexIn(str);
  }

  /**
   * 在字符串中查找第一个不在给定字符集合中的字符的出现位置。
   *
   * <p>如果字符串为{@code null}，将返回{@code -1}。如果搜索数组为{@code null}或长度为零，
   * 将返回{@code -1}。
   * <pre>
   * StringUtils.lastIndexOfAnyCharBut(null, *, *)                 = -1
   * StringUtils.lastIndexOfAnyCharBut("", *, *)                   = -1
   * StringUtils.lastIndexOfAnyCharBut("abc", null, 0)             = 0
   * StringUtils.lastIndexOfAnyCharBut("abc", null, 1)             = 1
   * StringUtils.lastIndexOfAnyCharBut("abc", null, 100)           = -1
   * StringUtils.lastIndexOfAnyCharBut("abc", [], 0)               = 0
   * StringUtils.lastIndexOfAnyCharBut("abc", [], 1)               = 1
   * StringUtils.lastIndexOfAnyCharBut("abc", [], 100)             = -1
   * StringUtils.lastIndexOfAnyCharBut("zzabyycdxx", ['z','a'], 0) = 3
   * StringUtils.lastIndexOfAnyCharBut("zzabyycdxx", ['a'], 0)     = 0
   * StringUtils.lastIndexOfAnyCharBut("aba", ['a', 'b'], *)       = -1
   * </pre>
   *
   * @param str
   *     要检查的字符串，可能为null
   * @param searchChars
   *     要搜索的字符，可能为null
   * @param fromIndex
   *     开始搜索的位置（反向搜索）。fromIndex的值没有限制。如果它大于或等于
   *     此字符串的长度，效果与它等于此字符串长度减一相同：整个字符串都可以被搜索。
   *     如果为负数，效果与-1相同：返回-1。
   * @return 任意字符的索引，如果没有匹配或输入为null则返回-1
   * @see Searcher
   */
  public static int lastIndexOfAnyCharBut(@Nullable final CharSequence str,
      @Nullable final char[] searchChars, final int fromIndex) {
    return new Searcher()
        .forCharsNotIn(searchChars)
        .endBefore(Math.min(Integer.MAX_VALUE - 1, fromIndex) + 1)
        .findLastIndexIn(str);
  }

  /**
   * 在字符串中查找第一个不在给定字符集合中的字符的出现位置。
   *
   * <p>如果字符串为{@code null}，将返回{@code -1}。如果搜索字符串为{@code null}，
   * 将返回{@code -1}。
   * <pre>
   * StringUtils.lastIndexOfAnyCharBut(null, *, *)            = -1
   * StringUtils.lastIndexOfAnyCharBut("", *, *)              = -1
   * StringUtils.lastIndexOfAnyCharBut("abc", null, 0)        = 0
   * StringUtils.lastIndexOfAnyCharBut("abc", null, 1)        = 1
   * StringUtils.lastIndexOfAnyCharBut("abc", null, 100)      = -1
   * StringUtils.lastIndexOfAnyCharBut("abc", "", 0)          = 0
   * StringUtils.lastIndexOfAnyCharBut("abc", "", 1)          = 1
   * StringUtils.lastIndexOfAnyCharBut("abc", "", 100)        = -1
   * StringUtils.lastIndexOfAnyCharBut("zzabyycdxx", "za", 0) = 3
   * StringUtils.lastIndexOfAnyCharBut("zzabyycdxx", "", 0)   = 0
   * StringUtils.lastIndexOfAnyCharBut("aba","ab", *)         = -1
   * </pre>
   *
   * @param str
   *     要检查的字符串，可能为null
   * @param searchChars
   *     要搜索的字符，可能为null
   * @param fromIndex
   *     开始搜索的位置（反向搜索）。fromIndex的值没有限制。如果它大于或等于
   *     此字符串的长度，效果与它等于此字符串长度减一相同：整个字符串都可以被搜索。
   *     如果为负数，效果与-1相同：返回-1。
   * @return 任意字符的索引，如果没有匹配或输入为null则返回-1
   * @see Searcher
   */
  public static int lastIndexOfAnyCharBut(@Nullable final CharSequence str,
      @Nullable final CharSequence searchChars, final int fromIndex) {
    return new Searcher()
        .forCharsNotIn(searchChars)
        .endBefore(Math.min(Integer.MAX_VALUE - 1, fromIndex) + 1)
        .findLastIndexIn(str);
  }

  /**
   * 在字符串中查找第一次出现位置，处理{@code null}。此方法使用{@link String#lastIndexOf(String, int)}。
   *
   * <p>如果字符串为{@code null}，将返回{@code -1}。负数的起始位置返回{@code -1}。
   * 空字符串("")总是匹配，除非起始位置为负数。起始位置大于字符串长度时搜索整个字符串。
   * <pre>
   * StringUtils.lastIndexOf(null, *, *)          = -1
   * StringUtils.lastIndexOf(*, null, *)          = -1
   * StringUtils.lastIndexOf("", *, *)            = -1
   * StringUtils.lastIndexOf("aabaabaa", "a", 8)  = 7
   * StringUtils.lastIndexOf("aabaabaa", "b", 8)  = 5
   * StringUtils.lastIndexOf("aabaabaa", "ab", 8) = 4
   * StringUtils.lastIndexOf("aabaabaa", "b", 9)  = 5
   * StringUtils.lastIndexOf("aabaabaa", "b", -1) = -1
   * StringUtils.lastIndexOf("aabaabaa", "a", 0)  = 0
   * StringUtils.lastIndexOf("aabaabaa", "b", 0)  = -1
   * </pre>
   *
   * @param str
   *     要检查的字符串，可能为null
   * @param search
   *     要查找的字符串，可能为null
   * @param fromIndex
   *     开始搜索的位置（反向搜索）。fromIndex的值没有限制。如果它大于或等于
   *     此字符串的长度，效果与它等于此字符串长度减一相同：整个字符串都可以被搜索。
   *     如果为负数，效果与-1相同：返回-1。
   * @param ignoreCase
   *     比较字符串时是否忽略大小写。
   * @return 搜索字符串的第一次出现位置，如果没有匹配或{@code null}字符串输入则返回-1
   * @see Searcher
   */
  public static int lastIndexOf(@Nullable final CharSequence str,
      @Nullable final CharSequence search, final int fromIndex,
      final boolean ignoreCase) {
    return new Searcher()
        .forSubstring(search)
        .endBefore(Math.min(Integer.MAX_VALUE - 1, fromIndex) + 1)
        .ignoreCase(ignoreCase)
        .findLastIndexIn(str);
  }

  /**
   * 查找一组潜在子字符串中任意一个的最后一次出现位置。
   *
   * <p>如果字符串为{@code null}，将返回{@code -1}。如果搜索数组为{@code null}，
   * 将返回{@code -1}。{@code null}或长度为零的搜索数组项将被忽略，但包含""的搜索数组
   * 将在{@code str}不为{@code null}时返回{@code str}的长度。此方法使用
   * {@link String#indexOf(String)}
   * <pre>
   * StringUtils.lastIndexOfAny(null, *, *)                     = -1
   * StringUtils.lastIndexOfAny("", *, *)                       =  -1
   * StringUtils.lastIndexOfAny(*, null, *)                     = -1
   * StringUtils.lastIndexOfAny(*, [], *)                       = -1
   * StringUtils.lastIndexOfAny(*, [null], *)                   = -1
   * StringUtils.lastIndexOfAny("zzabyycdxx", ["ab","cd"], 100) = 6
   * StringUtils.lastIndexOfAny("zzabyycdxx", ["cd","ab"], 100) = 6
   * StringUtils.lastIndexOfAny("zzabyycdxx", ["mn","op"], 100) = -1
   * StringUtils.lastIndexOfAny("zzabyycdxx", ["mn","op"], 100) = -1
   * StringUtils.lastIndexOfAny("zzabyycdxx", ["mn",""], 100)   = 10
   * </pre>
   *
   * @param str
   *     要检查的字符串，可能为null
   * @param searches
   *     要搜索的字符串，可能为null
   * @param fromIndex
   *     开始搜索的位置（反向搜索）。fromIndex的值没有限制。如果它大于或等于
   *     此字符串的长度，效果与它等于此字符串长度减一相同：整个字符串都可以被搜索。
   *     如果为负数，效果与-1相同：返回-1。
   * @param ignoreCase
   *     比较字符串时是否忽略大小写。
   * @return 任意字符串的最后一次出现位置，如果没有匹配则返回-1
   * @see Searcher
   */
  public static int lastIndexOfAny(@Nullable final String str,
      @Nullable final CharSequence[] searches, final int fromIndex,
      final boolean ignoreCase) {
    return new Searcher()
        .forSubstringsIn(searches)
        .endBefore(Math.min(Integer.MAX_VALUE - 1, fromIndex) + 1)
        .ignoreCase(ignoreCase)
        .findLastIndexIn(str);
  }

  /**
   * 测试字符串是否包含指定字符。
   *
   * @param str
   *     字符串，可能为{@code null}。
   * @param ch
   *     字符。
   * @return 如果{@code str}不为{@code null}且包含{@code ch}则返回{@code true}；
   *     否则返回{@code false}。
   * @see Searcher#isContainedIn(CharSequence)
   */
  public static boolean containsChar(@Nullable final CharSequence str, final char ch) {
    return new Searcher()
        .forChar(ch)
        .isContainedIn(str);
  }

  /**
   * 测试字符串是否包含指定字符。
   *
   * @param str
   *     字符串，可能为{@code null}。
   * @param ch
   *     字符。
   * @param startIndex
   *     开始搜索的索引位置。
   * @return 如果{@code str}不为{@code null}且包含{@code ch}则返回{@code true}；
   *     否则返回{@code false}。
   * @see Searcher#isContainedIn(CharSequence)
   */
  public static boolean containsChar(@Nullable final CharSequence str,
      final char ch, final int startIndex) {
    return new Searcher()
        .forChar(ch)
        .startFrom(startIndex)
        .isContainedIn(str);
  }

  /**
   * 测试字符串是否包含指定字符的码点。
   *
   * @param str
   *     字符串，可能为{@code null}。
   * @param codePoint
   *     字符的码点。
   * @return 如果{@code str}不为{@code null}且包含{@code codePoint}则返回{@code true}；
   *     否则返回{@code false}。
   * @see Searcher#isContainedIn(CharSequence)
   */
  public static boolean containsCodePoint(@Nullable final CharSequence str,
      final int codePoint) {
    return new Searcher()
        .forCodePoint(codePoint)
        .isContainedIn(str);
  }

  /**
   * 测试字符串是否包含指定字符的码点。
   *
   * @param str
   *     字符串，可能为{@code null}。
   * @param codePoint
   *     字符的码点。
   * @param startIndex
   *     开始搜索的索引位置。
   * @return 如果{@code str}不为{@code null}且包含{@code codePoint}则返回{@code true}；
   *     否则返回{@code false}。
   * @see Searcher#isContainedIn(CharSequence)
   */
  public static boolean containsCodePoint(@Nullable final CharSequence str,
      final int codePoint, final int startIndex) {
    return new Searcher()
        .forCodePoint(codePoint)
        .startFrom(startIndex)
        .isContainedIn(str);
  }

  /**
   * 检查指定字符串是否不包含除指定字符之外的任何字符。
   *
   * @param str
   *     字符串，可能为null或空。
   * @param ch
   *     指定的字符。
   * @return 如果指定字符串不包含除指定字符之外的任何字符，则返回{@code true}；
   *     否则返回{@code false}。如果指定字符串为null或空，返回false。
   * @see Searcher#forCharsNotEqual(char)
   * @see Searcher#isNotContainedIn(CharSequence)
   */
  public static boolean containsOnly(@Nullable final CharSequence str, final char ch) {
    if (str == null) {
      return false;
    } else if (str.length() == 0) {
      return true;
    } else {
      return new Searcher().forCharsNotEqual(ch).isNotContainedIn(str);
    }
  }

  /**
   * 检查指定字符串是否只包含指定的Unicode代码点。
   *
   * @param str
   *     字符串，可能为null或空。
   * @param codePoint
   *     指定的Unicode代码点。
   * @return 如果指定字符串只包含指定的Unicode代码点则返回{@code true}；
   *     否则返回{@code false}。如果指定字符串为null或空，返回false。
   */
  public static boolean containsOnly(@Nullable final CharSequence str,
      final int codePoint) {
    if (str == null) {
      return false;
    } else if (str.length() == 0) {
      return true;
    } else {
      return new Searcher().forCodePointsNotEqual(codePoint).isNotContainedIn(str);
    }
  }

  /**
   * 测试指定字符串是否只包含满足指定{@link CharFilter}的字符。
   *
   * @param str
   *     要测试的字符串，可能为null或空。
   * @param filter
   *     指定的{@link CharFilter}，不能为null。
   * @return 如果字符串只包含满足指定{@link CharFilter}的字符，则返回{@code true}；
   *     否则返回{@code false}。如果字符串为null，返回false。如果字符串为空，返回true。
   * @see Searcher#forCharsNotSatisfy(CharFilter)
   * @see Searcher#isNotContainedIn(CharSequence)
   */
  public static boolean containsOnly(@Nullable final CharSequence str,
      final CharFilter filter) {
    if (str == null) {
      return false;
    } else if (filter == null) {
      return false;
    } else if (str.length() == 0) {
      return true;
    } else {
      return new Searcher().forCharsNotSatisfy(filter).isNotContainedIn(str);
    }
  }

  /**
   * 测试指定字符串是否只包含满足指定{@link CodePointFilter}的代码点。
   *
   * @param str
   *     要测试的字符串，可能为null或空。
   * @param filter
   *     指定的{@link CodePointFilter}，不能为null。
   * @return 如果字符串只包含满足指定{@link CodePointFilter}的代码点，则返回{@code true}；
   *     否则返回{@code false}。如果字符串为null，返回false。如果字符串为空，返回true。
   * @see Searcher#forCharsNotSatisfy(CharFilter)
   * @see Searcher#isNotContainedIn(CharSequence)
   */
  public static boolean containsOnly(@Nullable final CharSequence str,
      final CodePointFilter filter) {
    if (str == null) {
      return false;
    } else if (filter == null) {
      return false;
    } else if (str.length() == 0) {
      return true;
    } else {
      return new Searcher().forCodePointsNotSatisfy(filter).isNotContainedIn(str);
    }
  }

  /**
   * 检查字符串是否只包含特定字符。
   *
   * <p>如果字符串为{@code null}，将返回{@code false}。如果有效字符数组为{@code null}，
   * 将返回{@code false}。空字符串("")总是返回{@code true}。
   * <pre>
   * StringUtils.containsOnly(null, *)       = false
   * StringUtils.containsOnly(*, null)       = false
   * StringUtils.containsOnly("", *)         = true
   * StringUtils.containsOnly("ab", '')      = false
   * StringUtils.containsOnly("abab", 'abc') = true
   * StringUtils.containsOnly("ab1", 'abc')  = false
   * StringUtils.containsOnly("abz", 'abc')  = false
   * </pre>
   *
   * @param str
   *     要检查的字符串，可能为null
   * @param validChars
   *     有效字符的数组，可能为null
   * @return 如果只包含有效字符且非null，则返回{@code true}
   * @see Searcher#forCharsNotIn(char[])}
   * @see Searcher#isNotContainedIn(CharSequence)
   */
  public static boolean containsOnly(@Nullable final CharSequence str,
      @Nullable final char[] validChars) {
    if (str == null) {
      return false;
    } else if (validChars == null) {
      return false;
    } else if (str.length() == 0) {
      return true;
    } else {
      return new Searcher().forCharsNotIn(validChars).isNotContainedIn(str);
    }
  }

  /**
   * 检查字符串是否只包含特定字符。
   *
   * <p>如果字符串为{@code null}，将返回{@code false}。如果有效字符字符串为{@code null}，
   * 将返回{@code false}。空字符串("")总是返回{@code true}。
   * <pre>
   * StringUtils.containsOnly(null, *)       = false
   * StringUtils.containsOnly(*, null)       = false
   * StringUtils.containsOnly("", *)         = true
   * StringUtils.containsOnly("ab", "")      = false
   * StringUtils.containsOnly("abab", "abc") = true
   * StringUtils.containsOnly("ab1", "abc")  = false
   * StringUtils.containsOnly("abz", "abc")  = false
   * </pre>
   *
   * @param str
   *     要检查的字符串，可能为null
   * @param validChars
   *     有效字符的字符串，可能为null
   * @return 如果只包含有效字符且非null，则返回{@code true}
   * @see Searcher#forCharsNotIn(CharSequence)
   * @see Searcher#isNotContainedIn(CharSequence)
   */
  public static boolean containsOnly(@Nullable final CharSequence str,
      @Nullable final CharSequence validChars) {
    if (str == null) {
      return false;
    } else if (validChars == null) {
      return false;
    } else if (str.length() == 0) {
      return true;
    } else {
      return new Searcher().forCharsNotIn(validChars).isNotContainedIn(str);
    }
  }

  /**
   * 测试指定字符串是否不包含任何满足指定{@link CharFilter}的字符。
   *
   * @param str
   *     要测试的字符串，可能为null或空。
   * @param filter
   *     指定的{@link CharFilter}，不能为null。
   * @return 如果字符串不包含任何满足指定{@link CharFilter}的字符，则返回{@code true}；
   *     否则返回{@code false}。如果字符串为null，返回true。如果字符串为空，返回true。
   * @see Searcher#forCharsSatisfy(CharFilter)
   * @see Searcher#isNotContainedIn(CharSequence)
   */
  public static boolean containsNone(@Nullable final CharSequence str,
      final CharFilter filter) {
    return new Searcher()
        .forCharsSatisfy(filter)
        .isNotContainedIn(str);
  }

  /**
   * 测试指定字符串是否不包含任何满足指定{@link CodePointFilter}的代码点。
   *
   * @param str
   *     要测试的字符串，可能为null或空。
   * @param filter
   *     指定的{@link CodePointFilter}，不能为null。
   * @return 如果字符串不包含任何满足指定{@link CodePointFilter}的代码点，则返回{@code true}；
   *     否则返回{@code false}。如果字符串为null，返回true。如果字符串为空，返回true。
   * @see Searcher#forCodePointsSatisfy(CodePointFilter)
   * @see Searcher#isNotContainedIn(CharSequence)
   */
  public static boolean containsNone(@Nullable final CharSequence str,
      final CodePointFilter filter) {
    return new Searcher()
        .forCodePointsSatisfy(filter)
        .isNotContainedIn(str);
  }

  /**
   * 测试指定字符串是否不包含指定的代码点。
   *
   * @param str
   *     要测试的字符串，可能为null或空。
   * @param codePoint
   *     指定的Unicode代码点。
   * @return 如果字符串不包含指定的代码点，则返回{@code true}；
   *     否则返回{@code false}。如果字符串为null，返回true。
   *     如果字符串为空，返回true。
   * @see Searcher#forCodePoint(int)
   * @see Searcher#isNotContainedIn(CharSequence)
   */
  public static boolean containsNone(@Nullable final CharSequence str, final int codePoint) {
    return new Searcher()
        .forCodePoint(codePoint)
        .isNotContainedIn(str);
  }

  /**
   * 检查字符串是否不包含任何无效字符。
   *
   * <p>如果字符串为{@code null}，将返回{@code true}。如果无效字符数组为{@code null}，
   * 将返回{@code true}。空字符串("")将返回{@code true}。空的无效字符数组将返回{@code true}。
   * <pre>
   * StringUtils.containsNone(null, *)                = true
   * StringUtils.containsNone(*, null)                = true
   * StringUtils.containsNone("", *)                  = true
   * StringUtils.containsNone("ab", [])               = true
   * StringUtils.containsNone("abab", ['x', 'b', y']) = false
   * StringUtils.containsNone("ab1", ['x','y','z'])   = true
   * StringUtils.containsNone("abz", ['z'])            = false
   * </pre>
   *
   * @param str
   *     要检查的字符串，可能为null。
   * @param invalidChars
   *     无效字符的数组，可能为null或空。
   * @return
   *     如果指定字符串不包含任何无效字符，则返回{@code true}。
   * @see Searcher#forCharsIn(char...)
   * @see Searcher#isNotContainedIn(CharSequence)
   */
  public static boolean containsNone(@Nullable final CharSequence str,
      @Nullable final char[] invalidChars) {
    return new Searcher()
        .forCharsIn(invalidChars)
        .isNotContainedIn(str);
  }

  /**
   * 检查字符串是否不包含任何无效字符。
   *
   * <p>如果字符串为{@code null}，将返回{@code true}。如果无效字符数组为{@code null}，
   * 将返回{@code true}。空字符串("")将返回{@code true}。空的无效字符字符串将返回{@code true}。
   * <pre>
   * StringUtils.containsNone(null, *)                = true
   * StringUtils.containsNone(*, null)                = true
   * StringUtils.containsNone("", *)                  = true
   * StringUtils.containsNone("ab", "")               = true
   * StringUtils.containsNone("abab", "xby")          = false
   * StringUtils.containsNone("ab1", "xyz")           = true
   * StringUtils.containsNone("abz", "z")             = false
   * </pre>
   *
   * @param str
   *     要检查的字符串，可能为null。
   * @param invalidChars
   *     无效字符的序列，可能为null。
   * @return
   *     如果指定字符串不包含任何无效字符，则返回{@code true}。
   * @see Searcher#forCharsIn(CharSequence)
   * @see Searcher#isNotContainedIn(CharSequence)
   */
  public static boolean containsNone(@Nullable final CharSequence str,
      @Nullable final CharSequence invalidChars) {
    return new Searcher()
        .forCharsIn(invalidChars)
        .isNotContainedIn(str);
  }

  /**
   * 测试指定字符串是否包含任何满足指定{@link CharFilter}的字符。
   *
   * @param str
   *     要测试的字符串，可能为null或空。
   * @param filter
   *     指定的{@link CharFilter}，不能为null。
   * @return
   *    如果字符串包含任何满足指定{@link CharFilter}的字符，则返回{@code true}；
   *    否则返回{@code false}。如果字符串为null或空，返回{@code false}。
   * @see Searcher#forCharsSatisfy(CharFilter)
   * @see Searcher#isContainedIn(CharSequence)
   */
  public static boolean containsAny(@Nullable final CharSequence str,
      final CharFilter filter) {
    return new Searcher()
        .forCharsSatisfy(filter)
        .isContainedIn(str);
  }

  /**
   * 测试指定字符串是否包含任何满足指定{@link CodePointFilter}的代码点。
   *
   * @param str
   *     要测试的字符串，可能为null或空。
   * @param filter
   *     指定的{@link CodePointFilter}，不能为null。
   * @return
   *    如果字符串包含任何满足指定{@link CodePointFilter}的代码点，则返回{@code true}；
   *     否则返回{@code false}。如果字符串为null或空，返回{@code false}。
   * @see Searcher#forCodePointsSatisfy(CodePointFilter)
   * @see Searcher#isContainedIn(CharSequence)
   */
  public static boolean containsAny(@Nullable final CharSequence str,
      final CodePointFilter filter) {
    return new Searcher()
        .forCodePointsSatisfy(filter)
        .isContainedIn(str);
  }

  /**
   * 测试指定字符串是否包含指定的代码点。
   *
   * @param str
   *     要测试的字符串，可能为null或空。
   * @param codePoint
   *     指定的代码点。
   * @return
   *    如果字符串包含指定的代码点，则返回{@code true}；
   *    否则返回{@code false}。如果字符串为null或空，返回{@code false}。
   * @see Searcher#forCodePoint(int)
   * @see Searcher#isContainedIn(CharSequence)
   */
  public static boolean containsAny(@Nullable final CharSequence str,
      final int codePoint) {
    return new Searcher()
        .forCodePoint(codePoint)
        .isContainedIn(str);
  }

  /**
   * 检查字符串是否包含任何有效字符。
   *
   * <p>如果字符串为{@code null}，将返回{@code false}。如果有效字符数组为{@code null}，
   * 将返回{@code false}。空字符串("")将返回{@code false}。空的无效字符数组将返回{@code false}。
   * <pre>
   * StringUtils.containsAny(null, *)                = false
   * StringUtils.containsAny(*, null)                = false
   * StringUtils.containsAny("", *)                  = false
   * StringUtils.containsAny("ab", [])               = false
   * StringUtils.containsAny("abab", ['x', 'b', y']) = true
   * StringUtils.containsAny("ab1", ['x','y','z'])   = false
   * StringUtils.containsAny("abz", ['z'])           = true
   * </pre>
   *
   * @param str
   *     要检查的字符串，可能为null。
   * @param validChars
   *     有效字符的数组，可能为null或空。
   * @return
   *     如果指定字符串包含任何有效字符，则返回{@code true}。
   *     如果字符串为null或空，返回{@code false}。
   * @see Searcher#forCharsIn(char...)
   * @see Searcher#isContainedIn(CharSequence)
   */
  public static boolean containsAny(@Nullable final CharSequence str,
      @Nullable final char[] validChars) {
    return new Searcher()
        .forCharsIn(validChars)
        .isContainedIn(str);
  }

  /**
   * 检查字符串是否包含任何有效字符。
   *
   * <p>如果字符串为{@code null}，将返回{@code false}。如果有效字符数组为{@code null}，
   * 将返回{@code false}。空字符串("")将返回{@code false}。空的无效字符字符串将返回{@code false}。
   * <pre>
   * StringUtils.containsAny(null, *)                = false
   * StringUtils.containsAny(*, null)                = false
   * StringUtils.containsAny("", *)                  = false
   * StringUtils.containsAny("ab", "")               = false
   * StringUtils.containsAny("abab", "xby")          = true
   * StringUtils.containsAny("ab1", "xyz")           = false
   * StringUtils.containsAny("abz", "z")             = true
   * </pre>
   *
   * @param str
   *     要检查的字符串，可能为null。
   * @param validChars
   *     有效字符的序列，可能为null。
   * @return
   *     如果指定字符串包含任何有效字符，则返回{@code true}。
   *     如果字符串为null或空，返回{@code false}。
   * @see Searcher#forCharsIn(CharSequence)
   * @see Searcher#isContainedIn(CharSequence)
   */
  public static boolean containsAny(@Nullable final CharSequence str,
      @Nullable final CharSequence validChars) {
    return new Searcher()
        .forCharsIn(validChars)
        .isContainedIn(str);
  }

  /**
   * 获取指定字符串中子字符串的所有出现位置。
   *
   * @param str
   *     查找子字符串的字符串。
   * @param substr
   *     要查找的子字符串。
   * @param ignoreCase
   *     比较字符串时是否忽略大小写。
   * @param list
   *     可选的{@link IntList}用于存放结果。如果为null，
   *     将创建一个新的{@link IntArrayList}来保存结果并返回。
   * @return 整数列表，包含子字符串在字符串中出现的所有索引。
   * @see Searcher#getOccurrencesIn(CharSequence)
   */
  public static IntList getOccurrences(@Nullable final CharSequence str,
      @Nullable final CharSequence substr, final boolean ignoreCase,
      @Nullable final IntList list) {
    return new Searcher()
        .forSubstring(substr)
        .ignoreCase(ignoreCase)
        .getOccurrencesIn(str, list);
  }

  /**
   * 计算字符在字符串中出现的次数。
   *
   * @param str
   *     字符串。
   * @param codePoint
   *     要计数的字符的代码点。
   * @return 字符ch在字符串str中的出现次数，如果str为null则返回0。
   * @see Searcher#countMatchesIn(CharSequence)
   */
  public static int countMatches(@Nullable final CharSequence str,
      final int codePoint) {
    return new Searcher()
        .forCodePoint(codePoint)
        .countMatchesIn(str);
  }

  /**
   * 计算子字符串在较大字符串中出现的次数。
   *
   * <p>如果输入的字符串为{@code null}或空("")，返回{@code 0}。
   * <pre>
   * StringUtils.countMatches(null, *)       = 0
   * StringUtils.countMatches("", *)         = 0
   * StringUtils.countMatches("abba", null)  = 0
   * StringUtils.countMatches("abba", "")    = 0
   * StringUtils.countMatches("abba", "a")   = 2
   * StringUtils.countMatches("abba", "ab")  = 1
   * StringUtils.countMatches("abba", "xxx") = 0
   * </pre>
   *
   * @param str
   *     要检查的字符串，可能为null
   * @param substr
   *     要计数的子字符串，可能为null
   * @return 出现次数，如果任一字符串为{@code null}则返回0
   * @see Searcher#countMatchesIn(CharSequence)
   */
  public static int countMatches(@Nullable final CharSequence str,
      @Nullable final CharSequence substr) {
    return new Searcher()
        .forSubstring(substr)
        .ignoreCase(false)
        .countMatchesIn(str);
  }

  /**
   * 计算子字符串在较大字符串中出现的次数。
   *
   * <p>如果输入的字符串为{@code null}或空("")，返回{@code 0}。
   * <pre>
   * StringUtils.countMatches(null, *)       = 0
   * StringUtils.countMatches("", *)         = 0
   * StringUtils.countMatches("abba", null)  = 0
   * StringUtils.countMatches("abba", "")    = 0
   * StringUtils.countMatches("abba", "a")   = 2
   * StringUtils.countMatches("abba", "ab")  = 1
   * StringUtils.countMatches("abba", "xxx") = 0
   * </pre>
   *
   * @param str
   *     要检查的字符串，可能为null
   * @param substr
   *     要计数的子字符串，可能为null
   * @param ignoreCase
   *     比较字符串时是否忽略大小写。
   * @return 出现次数，如果任一字符串为{@code null}则返回0
   * @see Searcher#countMatchesIn(CharSequence)
   */
  public static int countMatches(@Nullable final CharSequence str,
      @Nullable final CharSequence substr, final boolean ignoreCase) {
    return new Searcher()
        .forSubstring(substr)
        .ignoreCase(ignoreCase)
        .countMatchesIn(str);
  }

  /**
   * 测试字符序列是否可以从开头剥离。
   *
   * @param str
   *     指定的字符序列。
   * @return
   *     如果字符序列可以从开头剥离，则返回{@code true}，
   *     即字符序列的开头是非图形字符；否则返回{@code false}。
   * @see CharUtils#isGraph(int)
   * @see Stripper#isStrippable(CharSequence)
   */
  public static boolean isStartStrippable(final CharSequence str) {
    return new Stripper()
        .ofBlank()
        .fromStart()
        .isStrippable(str);
  }

  /**
   * 测试字符序列是否可以从末尾剥离。
   *
   * @param str
   *     指定的字符序列。
   * @return
   *     如果字符序列可以从末尾剥离，则返回{@code true}，
   *     即字符序列的末尾是非图形字符；否则返回{@code false}。
   * @see CharUtils#isGraph(int)
   * @see Stripper#isStrippable(CharSequence)
   */
  public static boolean isEndStrippable(final CharSequence str) {
    return new Stripper()
        .ofBlank()
        .fromEnd()
        .isStrippable(str);
  }

  /**
   * 测试字符序列是否可以剥离。
   *
   * @param str
   *     指定的字符序列。
   * @return
   *     如果字符序列可以剥离，则返回{@code true}，即字符序列的开头
   *     或末尾是非图形字符；否则返回{@code false}。
   * @see CharUtils#isGraph(int)
   * @see Stripper#isStrippable(CharSequence)
   */
  public static boolean isStrippable(final CharSequence str) {
    return new Stripper()
        .ofBlank()
        .fromAnySide()
        .isStrippable(str);
  }

  /**
   * 从字符串的开头剥离空白字符和不可打印字符。
   *
   * <p>该函数使用{@link CharUtils#isGraph(int)}来确定代码点
   * 是否为可打印的非空白字符。
   *
   * <p>如果输入字符串为{@code null}，返回{@code null}。空字符串("")
   * 输入返回空字符串。
   * <pre>
   * StringUtils.stripStart(null)       = null
   * StringUtils.stripStart("")         = ""
   * StringUtils.stripStart("abc")      = "abc"
   * StringUtils.stripStart(" abc ")    = "abc "
   * StringUtils.stripStart("  abc  ")  = "abc    "
   * StringUtils.stripStart("abc  ")    = "abc  "
   * StringUtils.stripStart(" abc ")    = "abc "
   * StringUtils.stripStart("       yxabc  ")  = "yxabc  "
   * </pre>
   *
   * @param str
   *     要移除字符的字符串，可能为null
   * @return 剥离后的字符串，如果输入字符串为null则返回{@code null}
   * @see Stripper#strip(CharSequence)
   */
  public static String stripStart(@Nullable final CharSequence str) {
    return new Stripper()
        .ofBlank()
        .fromStart()
        .strip(str);
  }

  /**
   * 从字符串的开头剥离空白字符和不可打印字符。
   *
   * <p>该函数使用{@link CharUtils#isGraph(int)}来确定代码点
   * 是否为可打印的非空白字符。
   *
   * <p>如果输入字符串为{@code null}，返回{@code null}。空字符串("")
   * 输入返回空字符串。
   * <pre>
   * StringUtils.stripStart(null)       = null
   * StringUtils.stripStart("")         = ""
   * StringUtils.stripStart("abc")      = "abc"
   * StringUtils.stripStart(" abc ")    = "abc "
   * StringUtils.stripStart("  abc  ")  = "abc    "
   * StringUtils.stripStart("abc  ")    = "abc  "
   * StringUtils.stripStart(" abc ")    = "abc "
   * StringUtils.stripStart("       yxabc  ")  = "yxabc  "
   * </pre>
   *
   * @param str
   *     要移除字符的字符串，可能为null
   * @param output
   *     输出结果字符串的目标。
   * @throws IOException
   *     如果发生任何I/O错误。
   * @see Stripper#strip(CharSequence, Appendable)
   */
  public static void stripStart(@Nullable final CharSequence str,
      final Appendable output) throws IOException {
    new Stripper()
        .ofBlank()
        .fromStart()
        .strip(str, output);
  }

  /**
   * 从字符串的开头剥离指定字符。
   *
   * <p>如果输入字符串为{@code null}，返回{@code null}。空字符串("")
   * 输入返回空字符串。
   *
   * <p>如果剥离字符为{@code null}，它被视为空字符串，
   * 返回原始字符串。
   * <pre>
   * StringUtils.stripStart(null, *)            = null
   * StringUtils.stripStart("", *)              = ""
   * StringUtils.stripStart("abc", 'a')         = "bc"
   * StringUtils.stripStart("abc", 'b')         = "abc"
   * StringUtils.stripStart("yyyxabc  ", 'y')   = "xabc  "
   * </pre>
   *
   * @param str
   *     要移除字符的字符串，可能为null
   * @param stripChar
   *     要移除的字符。
   * @return 剥离后的字符串，如果输入字符串为null则返回{@code null}
   * @see Stripper#strip(CharSequence)
   */
  public static String stripStart(@Nullable final CharSequence str,
      final char stripChar) {
    return new Stripper()
        .ofChar(stripChar)
        .fromStart()
        .strip(str);
  }

  /**
   * 从字符串开头剥离指定字符。
   *
   * <p>如果输入字符串为{@code null}，返回{@code null}。如果输入为空字符串("")，
   * 返回空字符串。
   *
   * <p>如果stripChar为{@code null}，将被视为空字符串，
   * 返回原字符串。
   * <pre>
   * StringUtils.stripStart(null, *)            = null
   * StringUtils.stripStart("", *)              = ""
   * StringUtils.stripStart("abc", 'a')         = "bc"
   * StringUtils.stripStart("abc", 'b')         = "abc"
   * StringUtils.stripStart("yyyxabc  ", 'y')   = "xabc  "
   * </pre>
   *
   * @param str
   *     要移除字符的字符串，可能为null
   * @param stripChar
   *     要移除的字符。
   * @param output
   *     用于追加剥离后字符串的输出。
   * @throws IOException
   *     如果发生任何I/O错误。
   * @see Stripper#strip(CharSequence, Appendable)
   */
  public static void stripStart(@Nullable final CharSequence str,
      final char stripChar, final Appendable output) throws IOException {
    new Stripper()
        .ofChar(stripChar)
        .fromStart()
        .strip(str, output);
  }

  /**
   * 从字符串开头剥离指定的一组字符。
   *
   * <p>如果输入字符串为{@code null}，返回{@code null}。如果输入为空字符串("")，
   * 返回空字符串。
   *
   * <p>如果stripChars字符串为{@code null}，将被视为空字符串，
   * 返回原字符串。
   * <pre>
   * StringUtils.stripStart(null, *)          = null
   * StringUtils.stripStart("", *)            = ""
   * StringUtils.stripStart(" abc ", null)    = " abc "
   * StringUtils.stripStart(" abc ", "")      = " abc "
   * StringUtils.stripStart("abc", "a")       = "bc"
   * StringUtils.stripStart("abc", "bc")      = "abc"
   * StringUtils.stripStart("  abc", " a")    = "bc"
   * StringUtils.stripStart("abc  ", "abc")   = "  "
   * StringUtils.stripStart(" abc ", "abc ")  = ""
   * StringUtils.stripStart("yxabc  ", "xyz") = "abc  "
   * </pre>
   *
   * @param str
   *     要移除字符的字符串，可能为null
   * @param stripChars
   *     要移除的字符，null被视为空字符串，返回原字符串。
   * @return 剥离后的字符串，如果输入字符串为null则返回{@code null}
   * @see Stripper#strip(CharSequence)
   */
  public static String stripStart(@Nullable final CharSequence str,
      @Nullable final CharSequence stripChars) {
    return new Stripper()
        .ofCharsIn(stripChars)
        .fromStart()
        .strip(str);
  }

  /**
   * 从字符串开头剥离指定的一组字符。
   *
   * <p>如果输入字符串为{@code null}，返回{@code null}。如果输入为空字符串("")，
   * 返回空字符串。
   *
   * <p>如果stripChars字符串为{@code null}，将被视为空字符串，
   * 返回原字符串。
   * <pre>
   * StringUtils.stripStart(null, *)          = null
   * StringUtils.stripStart("", *)            = ""
   * StringUtils.stripStart(" abc ", null)    = " abc "
   * StringUtils.stripStart(" abc ", "")      = " abc "
   * StringUtils.stripStart("abc", "a")       = "bc"
   * StringUtils.stripStart("abc", "bc")      = "abc"
   * StringUtils.stripStart("  abc", " a")    = "bc"
   * StringUtils.stripStart("abc  ", "abc")   = "  "
   * StringUtils.stripStart(" abc ", "abc ")  = ""
   * StringUtils.stripStart("yxabc  ", "xyz") = "abc  "
   * </pre>
   *
   * @param str
   *     要移除字符的字符串，可能为null
   * @param stripChars
   *     要移除的字符，null被视为空字符串，返回原字符串。
   * @param output
   *     用于追加剥离后字符串的输出。
   * @throws IOException
   *     如果发生任何I/O错误。
   * @see Stripper#strip(CharSequence, Appendable)
   */
  public static void stripStart(@Nullable final CharSequence str,
      @Nullable final CharSequence stripChars, final Appendable output)
      throws IOException {
    new Stripper()
        .ofCharsIn(stripChars)
        .fromStart()
        .strip(str, output);
  }

  /**
   * 从字符串开头剥离被{@link CharFilter}接受的字符。
   *
   * <p>如果输入字符串为{@code null}，返回{@code null}。如果输入为空字符串("")，
   * 返回空字符串。
   *
   * <p>如果过滤器为{@code null}，返回原字符串。
   *
   * @param str
   *     要移除字符的字符串，可能为null
   * @param filter
   *     {@link CharFilter}。被此过滤器接受的字符将从{@code str}的开头剥离。
   *     如果为null，返回原字符串。
   * @return
   *     剥离后的字符串，如果{@code str}为{@code null}则返回{@code null}。
   * @see Stripper#strip(CharSequence)
   */
  public static String stripStart(@Nullable final CharSequence str,
      @Nullable final CharFilter filter) {
    return new Stripper()
        .ofCharsSatisfy(filter)
        .fromStart()
        .strip(str);
  }

  /**
   * 从字符串开头剥离被{@link CharFilter}接受的字符。
   *
   * <p>如果输入字符串为{@code null}，返回{@code null}。如果输入为空字符串("")，
   * 返回空字符串。
   *
   * <p>如果过滤器为{@code null}，返回原字符串。
   *
   * @param str
   *     要移除字符的字符串，可能为null
   * @param filter
   *     {@link CharFilter}。被此过滤器接受的字符将从{@code str}的开头剥离。
   *     如果为null，返回原字符串。
   * @param output
   *     用于追加剥离后字符串的输出。
   * @throws IOException
   *     如果发生任何I/O错误。
   * @see Stripper#strip(CharSequence, Appendable)
   */
  public static void stripStart(@Nullable final CharSequence str,
      @Nullable final CharFilter filter, final Appendable output)
      throws IOException {
    new Stripper()
        .ofCharsSatisfy(filter)
        .fromStart()
        .strip(str, output);
  }

  /**
   * 从字符串开头剥离被{@link CodePointFilter}接受的代码点。
   *
   * <p>如果输入字符串为{@code null}，返回{@code null}。如果输入为空字符串("")，
   * 返回空字符串。
   *
   * <p>如果过滤器为{@code null}，返回原字符串。
   *
   * @param str
   *     要移除字符的字符串，可能为null
   * @param filter
   *     {@link CodePointFilter}。被此过滤器接受的代码点将从{@code str}的开头剥离。
   *     如果为null，返回原字符串。
   * @return
   *     剥离后的字符串，如果{@code str}为{@code null}则返回{@code null}。
   * @see Stripper#strip(CharSequence)
   */
  public static String stripStart(@Nullable final CharSequence str,
      @Nullable final CodePointFilter filter) {
    return new Stripper()
        .ofCodePointsSatisfy(filter)
        .fromStart()
        .strip(str);
  }

  /**
   * 从字符串开头剥离被{@link CodePointFilter}接受的代码点。
   *
   * <p>如果输入字符串为{@code null}，返回{@code null}。如果输入为空字符串("")，
   * 返回空字符串。
   *
   * <p>如果过滤器为{@code null}，返回原字符串。
   *
   * @param str
   *     要移除字符的字符串，可能为null
   * @param filter
   *     {@link CodePointFilter}。被此过滤器接受的代码点将从{@code str}的开头剥离。
   *     如果为null，返回原字符串。
   * @param output
   *     用于追加剥离后字符串的输出。
   * @throws IOException
   *     如果发生任何I/O错误。
   * @see Stripper#strip(CharSequence, Appendable)
   */
  public static void stripStart(@Nullable final CharSequence str,
      @Nullable final CodePointFilter filter, final Appendable output)
      throws IOException {
    new Stripper()
        .ofCodePointsSatisfy(filter)
        .fromStart()
        .strip(str, output);
  }

  /**
   * 从字符串末尾剥离空白字符和不可打印字符。
   *
   * <p>此函数使用{@link CharUtils#isGraph(int)}来确定代码点是否为可打印的非空白字符。
   *
   * <p>如果输入字符串为{@code null}，返回{@code null}。如果输入为空字符串("")，
   * 返回空字符串。
   * <pre>
   * StringUtils.stripEnd(null)           = null
   * StringUtils.stripEnd("")             = ""
   * StringUtils.stripEnd("abc")          = "abc"
   * StringUtils.stripEnd("abc  ")        = "abc"
   * StringUtils.stripEnd("  abc")        = "  abc"
   * StringUtils.stripEnd("  abc  ")      = "  abc"
   * StringUtils.stripEnd(" abc xy")      = " abc xy"
   * StringUtils.stripEnd("  abcyx xz  ") = "  abcyx xz"
   * </pre>
   *
   * @param str
   *     要移除字符的字符串，可能为null
   * @return 剥离后的字符串，如果输入字符串为null则返回{@code null}
   * @see Stripper#strip(CharSequence)
   */
  public static String stripEnd(@Nullable final CharSequence str) {
    return new Stripper()
        .ofBlank()
        .fromEnd()
        .strip(str);
  }

  /**
   * 从字符串末尾剥离空白字符和不可打印字符。
   *
   * <p>此函数使用{@link CharUtils#isGraph(int)}来确定代码点是否为可打印的非空白字符。
   *
   * <p>如果输入字符串为{@code null}，返回{@code null}。如果输入为空字符串("")，
   * 返回空字符串。
   * <pre>
   * StringUtils.stripEnd(null)           = null
   * StringUtils.stripEnd("")             = ""
   * StringUtils.stripEnd("abc")          = "abc"
   * StringUtils.stripEnd("abc  ")        = "abc"
   * StringUtils.stripEnd("  abc")        = "  abc"
   * StringUtils.stripEnd("  abc  ")      = "  abc"
   * StringUtils.stripEnd(" abc xy")      = " abc xy"
   * StringUtils.stripEnd("  abcyx xz  ") = "  abcyx xz"
   * </pre>
   *
   * @param str
   *     要移除字符的字符串，可能为null
   * @param output
   *     用于追加结果字符串的输出。
   * @throws IOException
   *     如果发生任何I/O错误。
   * @see Stripper#strip(CharSequence, Appendable)
   */
  public static void stripEnd(@Nullable final CharSequence str,
      final Appendable output) throws IOException {
    new Stripper()
        .ofBlank()
        .fromEnd()
        .strip(str, output);
  }

  /**
   * 从字符串末尾剥离指定字符。
   *
   * <p>如果输入字符串为{@code null}，返回{@code null}。如果输入为空字符串("")，
   * 返回空字符串。
   *
   * <p>如果stripChar为{@code null}，将被视为空字符串，
   * 返回原字符串。
   * <pre>
   * StringUtils.stripEnd(null, *)            = null
   * StringUtils.stripEnd("", *)              = ""
   * StringUtils.stripEnd("  abcyx", 'x')     = "  abccy"
   * StringUtils.stripEnd("  abcyxxx", 'x')   = "  abccy"
   * </pre>
   *
   * @param str
   *     要移除字符的字符串，可能为null
   * @param stripChar
   *     要移除的字符。
   * @return 剥离后的字符串，如果输入字符串为null则返回{@code null}。
   * @see Stripper#strip(CharSequence)
   */
  public static String stripEnd(@Nullable final CharSequence str,
      final char stripChar) {
    return new Stripper()
        .ofChar(stripChar)
        .fromEnd()
        .strip(str);
  }

  /**
   * 从字符串末尾剥离指定字符。
   *
   * <p>如果输入字符串为{@code null}，返回{@code null}。如果输入为空字符串("")，
   * 返回空字符串。
   *
   * <p>如果stripChar为{@code null}，将被视为空字符串，
   * 返回原字符串。
   * <pre>
   * StringUtils.stripEnd(null, *)            = null
   * StringUtils.stripEnd("", *)              = ""
   * StringUtils.stripEnd("  abcyx", 'x')     = "  abccy"
   * StringUtils.stripEnd("  abcyxxx", 'x')   = "  abccy"
   * </pre>
   *
   * @param str
   *     要移除字符的字符串，可能为null
   * @param stripChar
   *     要移除的字符。
   * @param output
   *     用于追加剥离后字符串的输出。
   * @throws IOException
   *     如果发生任何I/O错误。
   * @see Stripper#strip(CharSequence, Appendable)
   */
  public static void stripEnd(@Nullable final CharSequence str,
      final char stripChar, final Appendable output) throws IOException {
    new Stripper()
        .ofChar(stripChar)
        .fromEnd()
        .strip(str, output);
  }

  /**
   * 从字符串末尾剥离指定的一组字符。
   *
   * <p>如果输入字符串为{@code null}，返回{@code null}。如果输入为空字符串("")，
   * 返回空字符串。
   *
   * <p>如果stripChars字符串为{@code null}，将被视为空字符串，
   * 返回原字符串。
   * <pre>
   * StringUtils.stripEnd(null, *)          = null
   * StringUtils.stripEnd("", *)            = ""
   * StringUtils.stripEnd("abc", "")        = "abc"
   * StringUtils.stripEnd("abc", null)      = "abc"
   * StringUtils.stripEnd("  abc", null)    = "  abc"
   * StringUtils.stripEnd("abc  ", null)    = "abc"
   * StringUtils.stripEnd(" abc ", null)    = " abc"
   * StringUtils.stripEnd("  abcyx", "xyz") = "  abc"
   * </pre>
   *
   * @param str
   *     要移除字符的字符串，可能为null
   * @param stripChars
   *     要移除的字符，null被视为空字符串，返回原字符串。
   * @return 剥离后的字符串，如果输入字符串为null则返回{@code null}
   * @see Stripper#strip(CharSequence)
   */
  public static String stripEnd(@Nullable final CharSequence str,
      @Nullable final CharSequence stripChars) {
    return new Stripper()
        .ofCharsIn(stripChars)
        .fromEnd()
        .strip(str);
  }

  /**
   * 从字符串末尾剥离指定的一组字符。
   *
   * <p>如果输入字符串为{@code null}，返回{@code null}。如果输入为空字符串("")，
   * 返回空字符串。
   *
   * <p>如果stripChars字符串为{@code null}，将被视为空字符串，
   * 返回原字符串。
   * <pre>
   * StringUtils.stripEnd(null, *)          = null
   * StringUtils.stripEnd("", *)            = ""
   * StringUtils.stripEnd("abc", "")        = "abc"
   * StringUtils.stripEnd("abc", null)      = "abc"
   * StringUtils.stripEnd("  abc", null)    = "  abc"
   * StringUtils.stripEnd("abc  ", null)    = "abc"
   * StringUtils.stripEnd(" abc ", null)    = " abc"
   * StringUtils.stripEnd("  abcyx", "xyz") = "  abc"
   * </pre>
   *
   * @param str
   *     要移除字符的字符串，可能为null
   * @param stripChars
   *     要移除的字符，null被视为空字符串，返回原字符串。
   * @param output
   *     用于追加剥离后字符串的输出。
   * @throws IOException
   *     如果发生任何I/O错误。
   * @see Stripper#strip(CharSequence, Appendable)
   */
  public static void stripEnd(@Nullable final CharSequence str,
      @Nullable final CharSequence stripChars, final StringBuilder output) {
    new Stripper()
        .ofCharsIn(stripChars)
        .fromEnd()
        .strip(str, output);
  }

  /**
   * 从字符串末尾剥离被CharFilter接受的字符。
   *
   * <p>如果输入字符串为{@code null}，返回{@code null}。如果输入为空字符串("")，
   * 返回空字符串。
   *
   * <p>如果过滤器为null，返回原字符串。
   *
   * @param str
   *     要移除字符的字符串，可能为null。
   * @param filter
   *     CharFilter。被此过滤器接受的字符将从{@code str}的末尾剥离。
   *     如果为null，返回原字符串。
   * @return 剥离后的字符串，如果{@code str}为{@code null}则返回{@code null}
   * @see Stripper#strip(CharSequence)
   */
  public static String stripEnd(@Nullable final CharSequence str,
      @Nullable final CharFilter filter) {
    return new Stripper()
        .ofCharsSatisfy(filter)
        .fromEnd()
        .strip(str);
  }

  /**
   * 从字符串末尾剥离被CharFilter接受的字符。
   *
   * <p>如果输入字符串为{@code null}，返回{@code null}。如果输入为空字符串("")，
   * 返回空字符串。
   *
   * <p>如果过滤器为null，返回原字符串。
   *
   * @param str
   *     要移除字符的字符串，可能为null。
   * @param filter
   *     CharFilter。被此过滤器接受的字符将从{@code str}的末尾剥离。
   *     如果为null，返回原字符串。
   * @param output
   *     用于追加剥离后字符串的输出。
   * @throws IOException
   *     如果发生任何I/O错误。
   * @see Stripper#strip(CharSequence, Appendable)
   */
  public static void stripEnd(@Nullable final CharSequence str,
      @Nullable final CharFilter filter, final StringBuilder output) {
    new Stripper()
        .ofCharsSatisfy(filter)
        .fromEnd()
        .strip(str, output);
  }

  /**
   * 从字符串末尾剥离被CodePointFilter接受的代码点。
   *
   * <p>如果输入字符串为{@code null}，返回{@code null}。如果输入为空字符串("")，
   * 返回空字符串。
   *
   * <p>如果过滤器为null，返回原字符串。
   *
   * @param str
   *     要移除字符的字符串，可能为null。
   * @param filter
   *     CodePointFilter。被此过滤器接受的代码点将从{@code str}的末尾剥离。
   *     如果为null，返回原字符串。
   * @return 剥离后的字符串，如果{@code str}为{@code null}则返回{@code null}
   * @see Stripper#strip(CharSequence)
   */
  public static String stripEnd(@Nullable final CharSequence str,
      @Nullable final CodePointFilter filter) {
    return new Stripper()
        .ofCodePointsSatisfy(filter)
        .fromEnd()
        .strip(str);
  }

  /**
   * 从字符串末尾剥离被CodePointFilter接受的代码点。
   *
   * <p>如果输入字符串为{@code null}，返回{@code null}。如果输入为空字符串("")，
   * 返回空字符串。
   *
   * <p>如果过滤器为null，返回原字符串。
   *
   * @param str
   *     要移除字符的字符串，可能为null。
   * @param filter
   *     CodePointFilter。被此过滤器接受的代码点将从{@code str}的末尾剥离。
   *     如果为null，返回原字符串。
   * @param output
   *     用于追加剥离后字符串的输出。
   * @throws IOException
   *     如果发生任何I/O错误。
   * @see Stripper#strip(CharSequence, Appendable)
   */
  public static void stripEnd(@Nullable final CharSequence str,
      @Nullable final CodePointFilter filter, final StringBuilder output) {
    new Stripper()
        .ofCodePointsSatisfy(filter)
        .fromEnd()
        .strip(str, output);
  }


  /**
   * 从字符串的开头和末尾剥离空白字符和不可打印字符，如果字符串被剥离为空则返回{@code null}。
   *
   * <p>这类似于{@link String#trim()}，但使用{@link CharUtils#isGraph(int)}
   * 来确定代码点是否为可打印的非空白字符。
   *
   * <p>如果输入字符串为{@code null}，返回{@code null}。如果输入为空字符串("")，
   * 也返回{@code null}。
   *
   * <pre>
   * StringUtils.stripToNull(null)           = null
   * StringUtils.stripToNull("")             = null
   * StringUtils.stripToNull("abc")          = "abc"
   * StringUtils.stripToNull("  abc")        = "abc"
   * StringUtils.stripToNull("abc  ")        = "abc"
   * StringUtils.stripToNull(" abc        ") = "abc"
   * StringUtils.stripToNull("  abc yx ")    = "abc yx"
   * </pre>
   *
   * @param str
   *     要移除字符的字符串，可能为null
   * @return 剥离后的字符串，如果输入字符串被剥离为空字符串则返回{@code null}。
   * @see Stripper#stripToNull(CharSequence)
   */
  public static String stripToNull(@Nullable final CharSequence str) {
    return new Stripper()
        .ofBlank()
        .fromBothSide()
        .stripToNull(str);
  }

  /**
   * 从字符串的开头和末尾剥离空白字符和不可打印字符，如果字符串被剥离为空则返回空字符串。
   *
   * <p>这类似于{@link String#trim()}，但使用{@link CharUtils#isGraph(int)}
   * 来确定代码点是否为可打印的非空白字符。
   *
   * <p>如果输入字符串为{@code null}，返回""。如果输入为空字符串("")，
   * 也返回""。
   *
   * <pre>
   * StringUtils.stripToEmpty(null)           = ""
   * StringUtils.stripToEmpty("")             = ""
   * StringUtils.stripToEmpty("abc")          = "abc"
   * StringUtils.stripToEmpty("  abc")        = "abc"
   * StringUtils.stripToEmpty("abc  ")        = "abc"
   * StringUtils.stripToEmpty(" abc        ") = "abc"
   * StringUtils.stripToEmpty("  abc yx ")    = "abc yx"
   * </pre>
   *
   * @param str
   *     要移除字符的字符串，可能为null
   * @return 剥离后的字符串，如果输入字符串被剥离为空字符串则返回空字符串。
   * @see Stripper#stripToEmpty(CharSequence)
   */
  public static String stripToEmpty(@Nullable final CharSequence str) {
    return new Stripper()
        .ofBlank()
        .fromBothSide()
        .stripToEmpty(str);
  }

  /**
   * 从字符串的开头和末尾剥离空白字符和不可打印字符。
   *
   * <p>这类似于{@link String#trim()}，但使用{@link CharUtils#isGraph(int)}
   * 来确定代码点是否为可打印的非空白字符。
   *
   * <p>如果输入字符串为{@code null}，返回{@code null}。如果输入为空字符串("")，
   * 返回空字符串。
   *
   * <pre>
   * StringUtils.strip(null)           = null
   * StringUtils.strip("")             = ""
   * StringUtils.strip("abc")          = "abc"
   * StringUtils.strip("  abc")        = "abc"
   * StringUtils.strip("abc  ")        = "abc"
   * StringUtils.strip(" abc        ") = "abc"
   * StringUtils.strip("  abc yx ")    = "abc yx"
   * </pre>
   *
   * @param str
   *     要移除字符的字符串，可能为null
   * @return 剥离后的字符串，如果输入字符串为null则返回{@code null}
   * @see Stripper#strip(CharSequence)
   * @see String#strip()
   */
  public static String strip(@Nullable final CharSequence str) {
    return new Stripper()
        .ofBlank()
        .fromBothSide()
        .strip(str);
  }

  /**
   * 从字符串的开头和末尾剥离空白字符和不可打印字符。
   *
   * <p>这类似于{@link String#trim()}，但使用{@link CharUtils#isGraph(int)}
   * 来确定代码点是否为可打印的非空白字符。
   *
   * <p>如果输入字符串为{@code null}，返回{@code null}。如果输入为空字符串("")，
   * 返回空字符串。
   *
   * <pre>
   * StringUtils.strip(null)           = null
   * StringUtils.strip("")             = ""
   * StringUtils.strip("abc")          = "abc"
   * StringUtils.strip("  abc")        = "abc"
   * StringUtils.strip("abc  ")        = "abc"
   * StringUtils.strip(" abc        ") = "abc"
   * StringUtils.strip("  abc yx ")    = "abc yx"
   * </pre>
   *
   * @param str
   *     要移除字符的字符串，可能为null
   * @param output
   *     用于追加结果字符串的输出。
   * @throws IOException
   *     如果发生任何I/O错误。
   * @see Stripper#strip(CharSequence, Appendable)
   * @see String#strip()
   */
  public static void strip(@Nullable final CharSequence str,
      final Appendable output) throws IOException {
    new Stripper()
        .ofBlank()
        .fromBothSide()
        .strip(str, output);
  }

  /**
   * 从字符串的开头和末尾剥离指定的一组字符。这类似于{@link String#trim()}，
   * 但允许控制要剥离的字符。
   *
   * <p>如果输入字符串为{@code null}，返回{@code null}。如果输入为空字符串("")，
   * 返回空字符串。
   *
   * <p>如果剥离字符字符串为{@code null}或空，返回原字符串。
   * <pre>
   * StringUtils.strip(null, *)          = null
   * StringUtils.strip("", *)            = ""
   * StringUtils.strip("abc", null)      = "abc"
   * StringUtils.strip("  abc", null)    = "  abc"
   * StringUtils.strip("abc  ", null)    = "abc  "
   * StringUtils.strip(" abc ", null)    = "  abc "
   * StringUtils.strip("  abcyx", "xyz") = "  abc"
   * </pre>
   *
   * @param str
   *     要移除字符的字符串，可能为null。
   * @param stripChars
   *     要移除的字符；如果为null或空，返回原字符串。
   * @return 剥离后的字符串，如果输入字符串为null则返回{@code null}
   * @see Stripper#strip(CharSequence)
   */
  public static String strip(@Nullable final CharSequence str,
      @Nullable final CharSequence stripChars) {
    return new Stripper()
        .ofCharsIn(stripChars)
        .fromBothSide()
        .strip(str);
  }

  /**
   * 从字符串的开头和末尾剥离指定的一组字符。这类似于{@link String#trim()}，
   * 但允许控制要剥离的字符。
   *
   * <p>如果输入字符串为{@code null}，返回{@code null}。如果输入为空字符串("")，
   * 返回空字符串。
   *
   * <p>如果剥离字符字符串为{@code null}或空，返回原字符串。
   * <pre>
   * StringUtils.strip(null, *)          = null
   * StringUtils.strip("", *)            = ""
   * StringUtils.strip("abc", null)      = "abc"
   * StringUtils.strip("  abc", null)    = "  abc"
   * StringUtils.strip("abc  ", null)    = "abc  "
   * StringUtils.strip(" abc ", null)    = "  abc "
   * StringUtils.strip("  abcyx", "xyz") = "  abc"
   * </pre>
   *
   * @param str
   *     要移除字符的字符串，可能为null。
   * @param stripChars
   *     要移除的字符；如果为null或空，返回原字符串。
   * @param output
   *     用于追加结果字符串的输出。
   * @throws IOException
   *     如果发生任何I/O错误。
   * @see Stripper#strip(CharSequence, Appendable)
   */
  public static void strip(@Nullable final CharSequence str,
      @Nullable final CharSequence stripChars, final Appendable output)
      throws IOException {
    new Stripper()
        .ofCharsIn(stripChars)
        .fromBothSide()
        .strip(str, output);
  }

  /**
   * 从字符串的两端剥离被CharFilter接受的字符。
   *
   * <p>如果输入字符串为{@code null}，返回{@code null}。如果输入为空字符串("")，
   * 返回空字符串。
   *
   * <p>如果过滤器为{@code null}，返回原字符串。
   *
   * @param str
   *     要移除字符的字符串，可能为null。
   * @param filter
   *     CharFilter。被此过滤器接受的字符将从{@code str}的两端剥离。
   *     如果为null，返回原字符串。
   * @return 剥离后的字符串，如果输入字符串为null则返回{@code null}
   * @see Stripper#strip(CharSequence)
   */
  public static String strip(@Nullable final CharSequence str,
      @Nullable final CharFilter filter) {
    return new Stripper()
        .ofCharsSatisfy(filter)
        .fromBothSide()
        .strip(str);
  }

  /**
   * 从字符串的两端剥离被CharFilter接受的字符。
   *
   * <p>如果输入字符串为{@code null}，返回{@code null}。如果输入为空字符串("")，
   * 返回空字符串。
   *
   * <p>如果过滤器为{@code null}，返回原字符串。
   *
   * @param str
   *     要移除字符的字符串，可能为null。
   * @param filter
   *     CharFilter。被此过滤器接受的字符将从{@code str}的两端剥离。
   *     如果为null，返回原字符串。
   * @param output
   *     用于追加结果字符串的输出。
   * @throws IOException
   *     如果发生任何I/O错误。
   * @see Stripper#strip(CharSequence, Appendable)
   */
  public static void strip(@Nullable final CharSequence str,
      @Nullable final CharFilter filter, final Appendable output)
      throws IOException {
    new Stripper()
        .ofCharsSatisfy(filter)
        .fromBothSide()
        .strip(str, output);
  }

  /**
   * 从字符串的两端剥离被CodePointFilter接受的代码点。
   *
   * <p>如果输入字符串为{@code null}，返回{@code null}。如果输入为空字符串("")，
   * 返回空字符串。
   *
   * <p>如果过滤器为{@code null}，返回原字符串。
   *
   * @param str
   *     要移除字符的字符串，可能为null。
   * @param filter
   *     CodePointFilter。被此过滤器接受的代码点将从{@code str}的两端剥离。
   *     如果为null，返回原字符串。
   * @return 剥离后的字符串，如果输入字符串为null则返回{@code null}
   * @see Stripper#strip(CharSequence)
   */
  public static String strip(@Nullable final CharSequence str,
      @Nullable final CodePointFilter filter) {
    return new Stripper()
        .ofCodePointsSatisfy(filter)
        .fromBothSide()
        .strip(str);
  }

  /**
   * 从字符串的两端剥离被CodePointFilter接受的代码点。
   *
   * <p>如果输入字符串为{@code null}，返回{@code null}。如果输入为空字符串("")，
   * 返回空字符串。
   *
   * <p>如果过滤器为{@code null}，返回原字符串。
   *
   * @param str
   *     要移除字符的字符串，可能为null。
   * @param filter
   *     CodePointFilter。被此过滤器接受的代码点将从{@code str}的两端剥离。
   *     如果为null，返回原字符串。
   * @param output
   *     用于追加结果字符串的输出。
   * @throws IOException
   *     如果发生任何I/O错误。
   * @see Stripper#strip(CharSequence, Appendable)
   */
  public static void strip(@Nullable final CharSequence str,
      @Nullable final CodePointFilter filter, final Appendable output)
      throws IOException {
    new Stripper()
        .ofCodePointsSatisfy(filter)
        .fromBothSide()
        .strip(str, output);
  }

  /**
   * 从字符串中剥离所有空白字符和不可打印字符。
   *
   * <p>如果输入字符串为{@code null}，返回{@code null}。如果输入为空字符串("")，
   * 返回空字符串。
   *
   * <pre>
   * StringUtils.stripAll(null)           = null
   * StringUtils.stripAll("")             = ""
   * StringUtils.stripAll("abc")          = "abc"
   * StringUtils.stripAll("  abc")        = "abc"
   * StringUtils.stripAll("abc  ")        = "abc"
   * StringUtils.stripAll(" abc        ") = "abc"
   * StringUtils.stripAll("  abc yx ")    = "abcyx"
   * </pre>
   *
   * @param str
   *     要移除字符的字符串，可能为null
   * @return 剥离后的字符串，如果输入字符串为null则返回{@code null}
   * @see Remover#forCodePointsSatisfy(CodePointFilter)
   * @see Remover#removeFrom(CharSequence)
   */
  public static String stripAll(@Nullable final CharSequence str) {
    return new Remover()
        .forCodePointsSatisfy(BlankCodePointFilter.INSTANCE)
        .removeFrom(str);
  }

  /**
   * 从字符串中移除最后一个字符。
   *
   * <p>如果字符串以{@code \r\n}结尾，则移除这两个字符。
   * <pre>
   * StringUtils.chop(null)          = null
   * StringUtils.chop("")            = ""
   * StringUtils.chop("abc \r")      = "abc "
   * StringUtils.chop("abc\n")       = "abc"
   * StringUtils.chop("abc\r\n")     = "abc"
   * StringUtils.chop("abc")         = "ab"
   * StringUtils.chop("abc\nabc")    = "abc\nab"
   * StringUtils.chop("a")           = ""
   * StringUtils.chop("\r")          = ""
   * StringUtils.chop("\n")          = ""
   * StringUtils.chop("\r\n")        = ""
   * </pre>
   *
   * @param str
   *     要移除最后一个字符的字符串，可能为null
   * @return 移除最后一个字符后的字符串，如果输入字符串为null则返回{@code null}
   */
  public static String chop(@Nullable final CharSequence str) {
    if (str == null) {
      return null;
    }
    final int strLen = str.length();
    if (strLen < 2) {
      return EMPTY;
    }
    final int lastIndex = strLen - 1;
    if (str.charAt(lastIndex) == Ascii.LINE_FEED) {
      final int lastLastIndex = lastIndex - 1;
      if (str.charAt(lastLastIndex) == Ascii.CARRIAGE_RETURN) {
        return str.toString().substring(0, lastLastIndex);
      }
    }
    return str.toString().substring(0, lastIndex);
  }

  public static void chop(@Nullable final CharSequence str,
      final StringBuilder output) {
    final int len;
    if ((str == null) || ((len = str.length()) < 2)) {
      return;
    }
    final int lastIndex = len - 1;
    if (str.charAt(lastIndex) == Ascii.LINE_FEED) {
      final int lastLastIndex = lastIndex - 1;
      if (str.charAt(lastLastIndex) == Ascii.CARRIAGE_RETURN) {
        output.append(str, 0, lastLastIndex);
        return;
      }
    }
    output.append(str, 0, lastIndex);
  }

  /**
   * 如果字符串末尾有换行符则移除一个换行符，否则保持不变。换行符是
   * &quot;{@code \n}&quot;、&quot;{@code \r}&quot;或&quot;{@code \r\n}&quot;。
   * <pre>
   * StringUtils.chomp(null)          = null
   * StringUtils.chomp("")            = ""
   * StringUtils.chomp("abc \r")      = "abc "
   * StringUtils.chomp("abc\n")       = "abc"
   * StringUtils.chomp("abc\r\n")     = "abc"
   * StringUtils.chomp("abc\r\n\r\n") = "abc\r\n"
   * StringUtils.chomp("abc\n\r")     = "abc\n"
   * StringUtils.chomp("abc\n\rabc")  = "abc\n\rabc"
   * StringUtils.chomp("\r")          = ""
   * StringUtils.chomp("\n")          = ""
   * StringUtils.chomp("\r\n")        = ""
   * </pre>
   *
   * @param str
   *     要移除换行符的字符串，可能为null
   * @return 移除换行符后的字符串，如果输入字符串为null则返回{@code null}
   */
  public static String chomp(@Nullable final CharSequence str) {
    if (str == null) {
      return null;
    }
    final int len;
    if ((len = str.length()) == 0) {
      return EMPTY;
    }
    if (len == 1) {
      final char ch = str.charAt(0);
      if ((ch == Ascii.CARRIAGE_RETURN) || (ch == Ascii.LINE_FEED)) {
        return EMPTY;
      }
      return str.toString();
    }
    int lastIndex = len - 1;
    final char last = str.charAt(lastIndex);
    if (last == Ascii.LINE_FEED) {
      if (str.charAt(lastIndex - 1) == Ascii.CARRIAGE_RETURN) {
        --lastIndex;
      }
    } else if (last != Ascii.CARRIAGE_RETURN) {
      ++lastIndex;
    }
    if (lastIndex == 0) {
      return EMPTY;
    } else if (lastIndex == len) {
      return str.toString();
    } else {
      return str.toString().substring(0, lastIndex);
    }
  }

  public static void chomp(@Nullable final CharSequence str,
      final StringBuilder output) {
    final int len;
    if ((str == null) || ((len = str.length()) == 0)) {
      return;
    }
    if (len == 1) {
      final char ch = str.charAt(0);
      if ((ch != Ascii.CARRIAGE_RETURN) && (ch != Ascii.LINE_FEED)) {
        output.append(ch);
      }
      return;
    }
    int lastIndex = len - 1;
    final char last = str.charAt(lastIndex);
    if (last == Ascii.LINE_FEED) {
      if (str.charAt(lastIndex - 1) == Ascii.CARRIAGE_RETURN) {
        --lastIndex;
      }
    } else if (last != Ascii.CARRIAGE_RETURN) {
      ++lastIndex;
    }
    output.append(str, 0, lastIndex);
  }

  /**
   * 如果{@code str}末尾有{@code separator}则移除它，否则保持不变。
   *
   * <p>注意：此方法在2.0版本中有所更改。现在更接近Perl的chomp行为。
   * 如需之前的行为，请使用{@link #substringBeforeLast(String, String)}。
   * 此方法使用{@link String#endsWith(String)}。
   * <pre>
   * StringUtils.chomp(null, *)         = null
   * StringUtils.chomp("", *)           = ""
   * StringUtils.chomp("foobar", "bar") = "foo"
   * StringUtils.chomp("foobar", "baz") = "foobar"
   * StringUtils.chomp("foo", "foo")    = ""
   * StringUtils.chomp("foo ", "foo")   = "foo "
   * StringUtils.chomp(" foo", "foo")   = " "
   * StringUtils.chomp("foo", "foooo")  = "foo"
   * StringUtils.chomp("foo", "")       = "foo"
   * StringUtils.chomp("foo", null)     = "foo"
   * </pre>
   *
   * @param str
   *     要移除分隔符的字符串，可能为null
   * @param separator
   *     分隔符字符串，可能为null
   * @return 移除尾部分隔符后的字符串，如果输入字符串为null则返回{@code null}
   */
  public static String chomp(@Nullable final CharSequence str,
      @Nullable final CharSequence separator) {
    if (str == null) {
      return null;
    }
    final int strLen;
    if ((strLen = str.length()) == 0) {
      return EMPTY;
    }
    final int sepLen;
    if ((separator == null)
        || ((sepLen = separator.length()) == 0)) {
      return str.toString();
    }
    if (endsWithSubstring(str, 0, str.length(), separator, false)) {
      if (strLen == sepLen) {
        return EMPTY;
      } else {
        return str.subSequence(0, strLen - sepLen).toString();
      }
    } else {
      return str.toString();
    }
  }

  public static void chomp(@Nullable final CharSequence str,
      @Nullable final CharSequence separator, final StringBuilder output) {
    final int strLen;
    final int sepLen;
    if ((str == null) || ((strLen = str.length()) == 0)) {
      return;
    }
    if ((separator == null) || ((sepLen = separator.length()) == 0)) {
      output.append(str);
      return;
    }
    if (endsWithSubstring(str, 0, str.length(), separator, false)) {
      if (strLen > sepLen) {
        output.append(str, 0, strLen - sepLen);
      }
    } else {
      output.append(str);
    }
  }

  /**
   * 仅当子字符串位于源字符串的开头时才移除它，否则返回源字符串。
   *
   * <p>比较是大小写敏感的。</p>
   *
   * <p>{@code null} 源字符串将返回 {@code null}。空字符串 ("") 源字符串将返回空字符串。
   * {@code null} 搜索字符串将返回源字符串。</p>
   *
   * <pre><code>
   * StringUtils.removePrefix(null, *)      = null
   * StringUtils.removePrefix("", *)        = ""
   * StringUtils.removePrefix(str, null)    = str
   * StringUtils.removePrefix("www.domain.com", "www.")    = "domain.com"
   * StringUtils.removePrefix("www.domain.com", "WwW.")    = "www.domain.com"
   * StringUtils.removePrefix("domain.com", "www.")        = "domain.com"
   * StringUtils.removePrefix("www.domain.com", "domain")  = "www.domain.com"
   * StringUtils.removePrefix("abc", "")                   = "abc"
   * </code></pre>
   *
   * @param str
   *     要搜索的源字符串，可能为 null
   * @param prefix
   *     要搜索和移除的前缀字符串，可能为 null
   * @return 如果找到则返回移除字符串后的子字符串，如果输入为 null 字符串则返回 {@code null}
   * @see Remover#forPrefix(CharSequence)
   * @see Remover#removeFrom(CharSequence)
   */
  public static String removePrefix(@Nullable final CharSequence str,
      @Nullable final CharSequence prefix) {
    return new Remover()
        .forPrefix(prefix)
        .ignoreCase(false)
        .removeFrom(str);
  }

  /**
   * 仅当子字符串位于源字符串的开头时才移除它，否则返回源字符串。
   *
   * <p>比较可以是大小写敏感或大小写不敏感的，由参数控制。
   *
   * <p>{@code null} 源字符串将返回 {@code null}。空字符串 ("") 源字符串将返回空字符串。
   * {@code null} 搜索字符串将返回源字符串。
   * <pre>
   * StringUtils.removePrefix(null, *, *)      = null
   * StringUtils.removePrefix("", *, *)        = ""
   * StringUtils.removePrefix(str, null, *)    = str
   * StringUtils.removePrefix("www.domain.com", "www.", *)    = "domain.com"
   * StringUtils.removePrefix("www.domain.com", "WwW.", true) = "domain.com"
   * StringUtils.removePrefix("www.domain.com", "WwW.", false)= "www.domain.com"
   * StringUtils.removePrefix("domain.com", "www.", *)        = "domain.com"
   * StringUtils.removePrefix("www.domain.com", "domain", *)  = "www.domain.com"
   * StringUtils.removePrefix("abc", "", *)                   = "abc"
   * </pre>
   *
   * @param str
   *     要搜索的源字符串，可能为 null
   * @param prefix
   *     要搜索和移除的前缀字符串，可能为 null
   * @param ignoreCase
   *     指示比较是否应忽略大小写（大小写不敏感）
   * @return 如果找到则返回移除字符串后的子字符串，如果输入为 null 字符串则返回 {@code null}
   * @see Remover#forPrefix(CharSequence)
   * @see Remover#removeFrom(CharSequence)
   */
  public static String removePrefix(@Nullable final CharSequence str,
      @Nullable final CharSequence prefix, final boolean ignoreCase) {
    return new Remover()
        .forPrefix(prefix)
        .ignoreCase(ignoreCase)
        .removeFrom(str);
  }


  /**
   * 仅当子字符串位于源字符串的末尾时才移除它，否则返回源字符串。
   *
   * <p>比较是大小写敏感的。</p>
   *
   * <p>{@code null} 源字符串将返回 {@code null}。空字符串 ("") 源字符串将返回空字符串。
   * {@code null} 搜索字符串将返回源字符串。</p>
   *
   * <pre>
   * StringUtils.removeSuffix(null, *, *)                      = null
   * StringUtils.removeSuffix("", *, *)                        = ""
   * StringUtils.removeSuffix(*, null, *)                      = *
   * StringUtils.removeSuffix("www.domain.com", ".com.", *)    = "www.domain.com"
   * StringUtils.removeSuffix("www.domain.com", ".com", *)     = "www.domain"
   * StringUtils.removeSuffix("www.domain.com", ".Com", true)  = "www.domain"
   * StringUtils.removeSuffix("www.domain.com", ".Com", false) = "www.domain.com"
   * StringUtils.removeSuffix("www.domain.com", "domain", *)   = "www.domain.com"
   * StringUtils.removeSuffix("abc", "", *)                    = "abc"
   * </pre>
   *
   * @param str
   *     要搜索的源字符串，可能为 null
   * @param suffix
   *     要搜索和移除的后缀字符串，可能为 null
   * @return 如果找到则返回移除字符串后的子字符串，如果输入为 null 字符串则返回 {@code null}
   * @see Remover#forSuffix(CharSequence)
   * @see Remover#removeFrom(CharSequence)
   */
  public static String removeSuffix(@Nullable final CharSequence str,
      @Nullable final CharSequence suffix) {
    return new Remover()
        .forSuffix(suffix)
        .ignoreCase(false)
        .removeFrom(str);
  }

  /**
   * 仅当子字符串位于源字符串的末尾时才移除它，否则返回源字符串。
   *
   * <p>比较可以是大小写敏感或大小写不敏感的，由参数控制。
   *
   * <p>{@code null} 源字符串将返回 {@code null}。空字符串 ("") 源字符串将返回空字符串。
   * {@code null} 搜索字符串将返回源字符串。</p>
   *
   * <pre>
   * StringUtils.removeSuffix(null, *, *)                      = null
   * StringUtils.removeSuffix("", *, *)                        = ""
   * StringUtils.removeSuffix(*, null, *)                      = *
   * StringUtils.removeSuffix("www.domain.com", ".com.", *)    = "www.domain.com"
   * StringUtils.removeSuffix("www.domain.com", ".com", *)     = "www.domain"
   * StringUtils.removeSuffix("www.domain.com", ".Com", true)  = "www.domain"
   * StringUtils.removeSuffix("www.domain.com", ".Com", false) = "www.domain.com"
   * StringUtils.removeSuffix("www.domain.com", "domain", *)   = "www.domain.com"
   * StringUtils.removeSuffix("abc", "", *)                    = "abc"
   * </pre>
   *
   * @param str
   *     要搜索的源字符串，可能为 null
   * @param suffix
   *     要搜索和移除的后缀字符串，可能为 null
   * @param ignoreCase
   *     指示比较是否应忽略大小写（大小写不敏感）
   * @return 如果找到则返回移除字符串后的子字符串，如果输入为 null 字符串则返回 {@code null}
   * @see Remover#forSuffix(CharSequence)
   * @see Remover#removeFrom(CharSequence)
   */
  public static String removeSuffix(@Nullable final CharSequence str,
      @Nullable final CharSequence suffix, final boolean ignoreCase) {
    return new Remover()
        .forSuffix(suffix)
        .ignoreCase(ignoreCase)
        .removeFrom(str);
  }

  /**
   * 从字符串中同时移除前缀和后缀。
   *
   * <p>比较是大小写敏感的。</p>
   *
   * <p>{@code null} 源字符串将返回 {@code null}。空字符串 ("") 源字符串将返回空字符串。
   * {@code null} 前缀或后缀将被忽略。</p>
   *
   * @param str
   *     要搜索的源字符串，可能为 null
   * @param prefix
   *     要移除的前缀字符串，可能为 null
   * @param suffix
   *     要移除的后缀字符串，可能为 null
   * @return 如果找到则返回移除前缀和后缀后的字符串，如果输入为 null 字符串则返回 {@code null}
   * @see Remover#forPrefix(CharSequence)
   * @see Remover#forSuffix(CharSequence)
   * @see Remover#removeFrom(CharSequence)
   */
  public static String removePrefixAndSuffix(@Nullable final CharSequence str,
      final CharSequence prefix, final String suffix) {
    return new Remover()
        .forPrefix(prefix)
        .forSuffix(suffix)
        .ignoreCase(false)
        .removeFrom(str);
  }

  /**
   * 从字符串中同时移除前缀和后缀。
   *
   * <p>比较可以是大小写敏感或大小写不敏感的，由参数控制。</p>
   *
   * <p>{@code null} 源字符串将返回 {@code null}。空字符串 ("") 源字符串将返回空字符串。
   * {@code null} 前缀或后缀将被忽略。</p>
   *
   * @param str
   *     要搜索的源字符串，可能为 null
   * @param prefix
   *     要移除的前缀字符串，可能为 null
   * @param suffix
   *     要移除的后缀字符串，可能为 null
   * @param ignoreCase
   *     指示比较是否应忽略大小写（大小写不敏感）
   * @return 如果找到则返回移除前缀和后缀后的字符串，如果输入为 null 字符串则返回 {@code null}
   * @see Remover#forPrefix(CharSequence)
   * @see Remover#forSuffix(CharSequence)
   * @see Remover#removeFrom(CharSequence)
   */
  public static String removePrefixAndSuffix(@Nullable final CharSequence str,
      final CharSequence prefix, final CharSequence suffix, final boolean ignoreCase) {
    return new Remover()
        .forPrefix(prefix)
        .forSuffix(suffix)
        .ignoreCase(ignoreCase)
        .removeFrom(str);
  }

  /**
   * 从字符串中移除字符的所有出现。
   *
   * <p>{@code null} 源字符串将返回 {@code null}。空字符串 ("") 源字符串将返回空字符串。
   * <pre>
   * StringUtils.removeChar(null, *)       = null
   * StringUtils.removeChar("", *)         = ""
   * StringUtils.removeChar("queued", 'u') = "qeed"
   * StringUtils.removeChar("queued", 'z') = "queued"
   * </pre>
   *
   * @param str
   *     要搜索的源字符串，可能为 null
   * @param ch
   *     要搜索和移除的字符
   * @return 如果找到则返回移除字符后的子字符串，如果输入为 null 字符串则返回 {@code null}
   * @see Remover#removeFrom(CharSequence)
   */
  public static String removeChar(@Nullable final CharSequence str, final char ch) {
    return new Remover()
        .forChar(ch)
        .limit(-1)
        .removeFrom(str);
  }

  /**
   * 从字符串中移除字符的出现。
   *
   * <p>{@code null} 源字符串将返回 {@code null}。空字符串 ("") 源字符串将返回空字符串。
   * <pre>
   * StringUtils.removeChar(null, *, *)       = null
   * StringUtils.removeChar("", *, *)         = ""
   * StringUtils.removeChar("queued", 'u', -1) = "qeed"
   * StringUtils.removeChar("queued", 'u', 1) = "qeued"
   * StringUtils.removeChar("queued", 'z', -1) = "queued"
   * </pre>
   *
   * @param str
   *     要搜索的源字符串，可能为 null
   * @param ch
   *     要搜索和移除的字符
   * @param max
   *     可以移除的字符的最大数量；-1 表示无限制
   * @return 如果找到则返回移除字符后的子字符串，如果输入为 null 字符串则返回 {@code null}
   * @see Remover#removeFrom(CharSequence)
   */
  public static String removeChar(@Nullable final CharSequence str, final char ch,
      final int max) {
    return new Remover()
        .forChar(ch)
        .limit(max)
        .removeFrom(str);
  }

  /**
   * 从字符串中移除Unicode码点的所有出现。
   *
   * <p>{@code null} 源字符串将返回 {@code null}。空字符串 ("") 源字符串将返回空字符串。
   * <pre>
   * StringUtils.removeCodePoint(null, *)       = null
   * StringUtils.removeCodePoint("", *)         = ""
   * StringUtils.removeCodePoint("queued", 'u') = "qeed"
   * StringUtils.removeCodePoint("queued", 'z') = "queued"
   * </pre>
   *
   * @param str
   *     要搜索的源字符串，可能为 null
   * @param codePoint
   *     要搜索和移除的Unicode码点
   * @return 如果找到则返回移除字符后的子字符串，如果输入为 null 字符串则返回 {@code null}
   * @see Remover#removeFrom(CharSequence)
   */
  public static String removeCodePoint(@Nullable final CharSequence str, final int codePoint) {
    return new Remover()
        .forCodePoint(codePoint)
        .limit(-1)
        .removeFrom(str);
  }

  /**
   * 从字符串中移除Unicode码点的出现。
   *
   * <p>{@code null} 源字符串将返回 {@code null}。空字符串 ("") 源字符串将返回空字符串。
   * <pre>
   * StringUtils.removeCodePoint(null, *, *)       = null
   * StringUtils.removeCodePoint("", *, *)         = ""
   * StringUtils.removeCodePoint("queued", 'u', -1) = "qeed"
   * StringUtils.removeCodePoint("queued", 'u', 1) = "qeued"
   * StringUtils.removeCodePoint("queued", 'z', -1) = "queued"
   * </pre>
   *
   * @param str
   *     要搜索的源字符串，可能为 null
   * @param codePoint
   *     要搜索和移除的Unicode码点
   * @param max
   *     可以移除的字符的最大数量；-1 表示无限制
   * @return 如果找到则返回移除字符后的子字符串，如果输入为 null 字符串则返回 {@code null}
   * @see Remover#removeFrom(CharSequence)
   */
  public static String removeCodePoint(@Nullable final CharSequence str, final int codePoint,
      final int max) {
    return new Remover()
        .forCodePoint(codePoint)
        .limit(max)
        .removeFrom(str);
  }

  /**
   * 从字符串中移除满足指定条件的字符。
   *
   * <p>{@code null} 源字符串将返回 {@code null}。空字符串 ("") 源字符串将返回空字符串。
   * {@code null} 字符过滤器将返回原字符串。
   *
   * @param str
   *     要搜索的源字符串，可能为 null
   * @param filter
   *     用于确定要移除字符的过滤器，可能为 null
   * @return 如果找到则返回移除字符后的字符串，如果输入为 null 字符串则返回 {@code null}
   * @see Remover#removeFrom(CharSequence)
   */
  public static String removeChar(@Nullable final CharSequence str,
      @Nullable final CharFilter filter) {
    return new Remover()
        .forCharsSatisfy(filter)
        .limit(-1)
        .removeFrom(str);
  }

  /**
   * 从字符串中移除满足指定条件的字符，限制最大移除数量。
   *
   * <p>{@code null} 源字符串将返回 {@code null}。空字符串 ("") 源字符串将返回空字符串。
   * {@code null} 字符过滤器将返回原字符串。
   *
   * @param str
   *     要搜索的源字符串，可能为 null
   * @param filter
   *     用于确定要移除字符的过滤器，可能为 null
   * @param max
   *     可以移除的字符的最大数量；-1 表示无限制
   * @return 如果找到则返回移除字符后的字符串，如果输入为 null 字符串则返回 {@code null}
   * @see Remover#removeFrom(CharSequence)
   */
  public static String removeChar(@Nullable final CharSequence str,
      @Nullable final CharFilter filter, final int max) {
    return new Remover()
        .forCharsSatisfy(filter)
        .limit(max)
        .removeFrom(str);
  }

  /**
   * 从字符串中移除子字符串。
   *
   * <p>{@code null} 字符串输入返回 {@code null}。空字符串 ("") 输入返回空字符串。
   * {@code null} 移除字符串返回输入字符串。{@code null} 输出 {@link StringBuilder}
   * 抛出 {@link NullPointerException}。
   *
   * <pre>
   * StringUtils.removeSubstring(null, *, *)        = null
   * StringUtils.removeSubstring("", *, *)          = ""
   * StringUtils.removeSubstring("queued", null, *) = "queued"
   * StringUtils.removeSubstring("queued", "", *)   = "queued"
   * StringUtils.removeSubstring("queued", "ue", 1) = "qd"
   * StringUtils.removeSubstring("queued", "ue", 2) = "q"
   * StringUtils.removeSubstring("queued", "ue", 3) = ""
   * </pre>
   *
   * @param str
   *     要从中移除的字符串，可能为 null
   * @param remove
   *     要移除的字符串，可能为 null
   * @param max
   *     要移除的最大出现次数，或 {@code -1} 表示无限制
   * @param ignoreCase
   *     指示比较是否应忽略大小写（大小写不敏感）
   * @return 如果找到则返回移除字符串后的子字符串，如果输入为 null 字符串则返回 {@code null}
   * @see Remover#removeFrom(CharSequence)
   */
  public static String removeSubstring(@Nullable final CharSequence str,
      @Nullable final CharSequence remove, final int max, final boolean ignoreCase) {
    return new Remover()
        .forSubstring(remove)
        .ignoreCase(ignoreCase)
        .limit(max)
        .removeFrom(str);
  }

  /**
   * 用另一个字符替换字符串中某个字符的所有出现。这是 {@link String#replace(char, char)} 的空安全版本。
   *
   * <p>{@code null} 字符串输入返回 {@code null}。空字符串 ("") 输入返回空字符串。
   * <pre>
   * StringUtils.replaceChars(null, *, *)        = null
   * StringUtils.replaceChars("", *, *)          = ""
   * StringUtils.replaceChars("abcba", 'b', 'y') = "aycya"
   * StringUtils.replaceChars("abcba", 'z', 'y') = "abcba"
   * </pre>
   *
   * @param str
   *     要替换字符的字符串，可能为 null
   * @param search
   *     要搜索的字符，可能为 null
   * @param replacement
   *     要替换的字符，可能为 null
   * @return 修改后的字符串，如果输入为 null 字符串则返回 {@code null}
   * @see Replacer#searchForChar(char)
   * @see Replacer#replaceWithChar(char)
   * @see Replacer#applyTo(CharSequence)
   */
  public static String replaceChar(@Nullable final CharSequence str,
      final char search, final char replacement) {
    return new Replacer()
        .searchForChar(search)
        .replaceWithChar(replacement)
        .applyTo(str);
  }

  /**
   * 用另一个字符替换字符串中某个字符的所有出现，将结果输出到StringBuilder。
   *
   * <p>{@code null} 字符串输入将不进行任何操作。空字符串 ("") 输入将不进行任何操作。
   *
   * @param str
   *     要替换字符的字符串，可能为 null
   * @param search
   *     要搜索的字符
   * @param replacement
   *     要替换的字符
   * @param builder
   *     用于存储结果的StringBuilder，不能为 null
   * @see Replacer#searchForChar(char)
   * @see Replacer#replaceWithChar(char)
   * @see Replacer#applyTo(CharSequence, StringBuilder)
   */
  public static void replaceChar(@Nullable final CharSequence str, final char search,
      final char replacement, final StringBuilder builder) {
    new Replacer()
        .searchForChar(search)
        .replaceWithChar(replacement)
        .applyTo(str,builder);
  }

  /**
   * 一次性替换字符串中的多个字符。此方法也可用于删除字符。
   *
   * <p>{@code null} 字符串输入返回 {@code null}。空字符串 ("") 输入返回空字符串。
   * null 或空的搜索字符集返回输入字符串。
   *
   * <p>搜索字符的长度通常应等于替换字符的长度。如果搜索字符更长，则额外的搜索字符将被删除。
   * 如果搜索字符更短，则额外的替换字符将被忽略。
   * <pre>
   * StringUtils.replaceChars(null, *, *)           = null
   * StringUtils.replaceChars("", *, *)             = ""
   * StringUtils.replaceChars("abc", null, *)       = "abc"
   * StringUtils.replaceChars("abc", "", *)         = "abc"
   * StringUtils.replaceChars("abc", "b", null)     = "ac"
   * StringUtils.replaceChars("abc", "b", "")       = "ac"
   * StringUtils.replaceChars("abcba", "bc", "yz")  = "ayzya"
   * StringUtils.replaceChars("abcba", "bc", "y")   = "ayya"
   * StringUtils.replaceChars("abcba", "bc", "yzx") = "ayzya"
   * </pre>
   *
   * @param str
   *     要替换字符的字符串，可能为 null
   * @param searchChars
   *     要搜索的字符集，可能为 null
   * @param replaceChars
   *     要替换的字符集，可能为 null
   * @return 修改后的字符串，如果输入为 null 字符串则返回 {@code null}
   * @see Replacer#searchForChar(char)
   * @see Replacer#replaceWithChar(char)
   * @see Replacer#applyTo(CharSequence)
   */
  @Nullable
  public static String replaceChars(@Nullable final CharSequence str,
      @Nullable final CharSequence searchChars, @Nullable final String replaceChars) {
    if (str == null) {
      return null;
    }
    if (str.isEmpty() || (searchChars == null) || searchChars.isEmpty()) {
      return str.toString();
    }
    final StringBuilder builder = new StringBuilder();
    final int count = replaceChars(str, searchChars, replaceChars, builder);
    if (count == 0) {
      return str.toString();
    } else if (builder.isEmpty()) {
      return EMPTY;
    } else {
      return builder.toString();
    }
  }

  /**
   * 一次性替换字符串中的多个字符，将结果输出到StringBuilder。
   *
   * <p>{@code null} 字符串输入返回0。空字符串 ("") 输入返回0。
   * null 或空的搜索字符集直接追加原字符串并返回0。
   *
   * <p>搜索字符的长度通常应等于替换字符的长度。如果搜索字符更长，则额外的搜索字符将被删除。
   * 如果搜索字符更短，则额外的替换字符将被忽略。
   *
   * @param str
   *     要替换字符的字符串，可能为 null
   * @param searchChars
   *     要搜索的字符集，可能为 null
   * @param replaceChars
   *     要替换的字符集，可能为 null
   * @param builder
   *     用于存储结果的StringBuilder，不能为 null
   * @return 替换的字符数量
   * @see Replacer#searchForChar(char)
   * @see Replacer#replaceWithChar(char)
   * @see Replacer#applyTo(CharSequence, StringBuilder)
   */
  public static int replaceChars(@Nullable final CharSequence str,
      @Nullable final CharSequence searchChars, @Nullable final String replaceChars,
      final StringBuilder builder) {
    final int len;
    if ((str == null) || ((len = str.length()) == 0)) {
      return 0;
    }
    if ((searchChars == null) || (searchChars.isEmpty())) {
      builder.append(str);
      return 0;
    }
    final String replace = defaultIfNull(replaceChars, EMPTY);
    final int replaceLen = replace.length();
    final int searchCharsLen = searchChars.length();
    int count = 0;
    for (int i = 0; i < len; i++) {
      final char ch = str.charAt(i);
      final CharFilter filter = new AcceptSpecifiedCharFilter(ch);
      final int index = firstIndexOf(searchChars, 0, searchCharsLen, filter);
      if (index < searchCharsLen) {
        ++count;
        if (index < replaceLen) {
          builder.append(replace.charAt(index));
        }
      } else {
        builder.append(ch);
      }
    }
    return count;
  }

  /**
   * 在较大的字符串内用另一个字符串替换字符串，替换搜索字符串的所有出现。
   * 传递给此方法的 {@code null} 引用是无操作的。
   * <pre>
   * StringUtils.replace(null, *, *, *)         = null
   * StringUtils.replace("", *, *, *)           = ""
   * StringUtils.replace("any", null, *, *)     = "any"
   * StringUtils.replace("any", *, null, *)     = "any"
   * StringUtils.replace("any", "", *, *)       = "any"
   * StringUtils.replace("any", *, *, 0)        = "any"
   * StringUtils.replace("abaa", "a", null, -1) = "abaa"
   * StringUtils.replace("abaa", "a", "", -1)   = "b"
   * StringUtils.replace("abaa", "a", "z", 0)   = "abaa"
   * StringUtils.replace("abaa", "a", "z", 1)   = "zbaa"
   * StringUtils.replace("abaa", "a", "z", 2)   = "zbza"
   * StringUtils.replace("abaa", "a", "z", -1)  = "zbzz"
   * </pre>
   *
   * @param str
   *     要搜索和替换的文本，可能为 null
   * @param search
   *     要搜索的字符串，可能为 null
   * @param replacement
   *     要替换的字符串，可能为 null
   * @return 处理任何替换后的文本，如果输入为 null 字符串则返回 {@code null}
   * @see Replacer#searchForSubstring(CharSequence)
   * @see Replacer#replaceWithString(CharSequence)
   * @see Replacer#applyTo(CharSequence)
   */
  public static String replace(@Nullable final CharSequence str,
      @Nullable final CharSequence search, @Nullable final CharSequence replacement) {
    return new Replacer()
        .searchForSubstring(search)
        .ignoreCase(false)
        .replaceWithString(replacement)
        .applyTo(str);
  }

  /**
   * 在较大的字符串中用另一个字符串替换字符串，最多替换搜索字符串的前 {@code max} 次出现。
   * 传递给此方法的 {@code null} 引用是无操作的。
   * <pre>
   * StringUtils.replace(null, *, *, *)         = null
   * StringUtils.replace("", *, *, *)           = ""
   * StringUtils.replace("any", null, *, *)     = "any"
   * StringUtils.replace("any", "", *, *)       = "any"
   * StringUtils.replace("any", *, *, 0)        = "any"
   * StringUtils.replace("abaa", "a", null, -1) = "b"
   * StringUtils.replace("abaa", "a", "", -1)   = "b"
   * StringUtils.replace("abaa", "a", "z", 0)   = "abaa"
   * StringUtils.replace("abaa", "a", "z", 1)   = "zbaa"
   * StringUtils.replace("abaa", "a", "z", 2)   = "zbza"
   * StringUtils.replace("abaa", "a", "z", -1)  = "zbzz"
   * </pre>
   *
   * @param str
   *     要搜索和替换的文本，可能为 null
   * @param search
   *     要搜索的字符串，可能为 null
   * @param replacement
   *     要替换的字符串，可能为 null
   * @param max
   *     要替换的最大出现次数，或 {@code -1} 表示无限制
   * @param ignoreCase
   *     指示比较是否应忽略大小写（大小写不敏感）
   * @return 处理任何替换后的文本，如果输入为 null 字符串则返回 {@code null}
   * @see Replacer#searchForSubstring(CharSequence)
   * @see Replacer#replaceWithString(CharSequence)
   * @see Replacer#applyTo(CharSequence)
   */
  public static String replace(@Nullable final CharSequence str,
      @Nullable final CharSequence search, @Nullable final CharSequence replacement,
      final int max, final boolean ignoreCase) {
    return new Replacer()
        .searchForSubstring(search)
        .replaceWithString(replacement)
        .limit(max)
        .ignoreCase(ignoreCase)
        .applyTo(str);
  }

  /**
   * 在较大的字符串中用另一个字符串替换字符串，将结果输出到StringBuilder。
   *
   * <p>传递给此方法的 {@code null} 引用将返回0。
   *
   * @param str
   *     要搜索和替换的文本，可能为 null
   * @param search
   *     要搜索的字符串，可能为 null
   * @param replacement
   *     要替换的字符串，可能为 null
   * @param max
   *     要替换的最大出现次数，或 {@code -1} 表示无限制
   * @param ignoreCase
   *     指示比较是否应忽略大小写（大小写不敏感）
   * @param output
   *     用于存储结果的StringBuilder，不能为 null
   * @return 替换的次数
   * @see Replacer#searchForSubstring(CharSequence)
   * @see Replacer#replaceWithString(CharSequence)
   * @see Replacer#applyTo(CharSequence, StringBuilder)
   */
  public static int replace(@Nullable final CharSequence str,
      @Nullable final CharSequence search, @Nullable final CharSequence replacement,
      final int max, final boolean ignoreCase, final StringBuilder output) {
    return new Replacer()
        .searchForSubstring(search)
        .replaceWithString(replacement)
        .limit(max)
        .ignoreCase(ignoreCase)
        .applyTo(str, output);
  }

  /**
   * 替换另一个字符串中所有出现的字符串。
   *
   * <p>传递给此方法的 {@code null} 引用是无操作的，或者如果任何"搜索字符串"或"要替换的字符串"为 null，
   * 该替换将被忽略。这不会重复。对于重复替换，请调用函数 {@link #replaceEachRepeatedly}。
   * <pre>
   *  StringUtils.replaceEach(null, *, *)        = null
   *  StringUtils.replaceEach("", *, *)          = ""
   *  StringUtils.replaceEach("aba", null, null) = "aba"
   *  StringUtils.replaceEach("aba", new String[0], null) = "aba"
   *  StringUtils.replaceEach("aba", null, new String[0]) = "aba"
   *  StringUtils.replaceEach("aba", new String[]{"a"}, null)  = "aba"
   *  StringUtils.replaceEach("aba", new String[]{"a"}, new String[]{""})  = "b"
   *  StringUtils.replaceEach("aba", new String[]{null}, new String[]{"a"})  = "aba"
   *  StringUtils.replaceEach("abcde", new String[]{"ab", "d"}, new String[]{"w", "t"})  = "wcte"
   *  (example of how it does not repeat)
   *  StringUtils.replaceEach("abcde", new String[]{"ab", "d"}, new String[]{"d", "t"})  = "dcte"
   * </pre>
   *
   * @param str
   *     要搜索和替换的字符串，如果为 null 则无操作
   * @param searches
   *     要搜索的字符串，如果为 null 则无操作
   * @param replaces
   *     要替换的字符串，如果为 null 则无操作
   * @param ignoreCase
   *     比较字符串时是否忽略大小写
   * @return 处理任何替换后的文本，如果输入为 null 字符串则返回 {@code null}
   * @throws IndexOutOfBoundsException
   *     如果数组的长度不相同（null 是可以的，和/或大小为 0）
   */
  @Nullable
  public static String replaceEach(@Nullable final CharSequence str,
      @Nullable final CharSequence[] searches, @Nullable final String[] replaces,
      final boolean ignoreCase) {
    if (str == null) {
      return null;
    }
    if ((str.isEmpty()) || (searches == null) || (searches.length == 0)
        || (replaces == null) || (replaces.length == 0)) {
      return str.toString();
    }
    requireEqual("searches.length", searches.length,
        "replaces.length", replaces.length);
    final boolean[] noMoreMatches = new boolean[searches.length];
    final StringBuilder builder = new StringBuilder();
    final int modified = replaceEachImpl(str, searches, replaces, ignoreCase, builder, noMoreMatches);
    if (modified == 0) {
      return str.toString();
    }
    if (builder.isEmpty()) {
      return EMPTY;
    } else {
      return builder.toString();
    }
  }

  /**
   * 替换另一个字符串中所有出现的字符串，将结果输出到StringBuilder。
   *
   * <p>传递给此方法的 {@code null} 引用将返回0，或者如果任何"搜索字符串"或"要替换的字符串"为 null，
   * 该替换将被忽略。这不会重复。
   *
   * @param str
   *     要搜索和替换的字符串，如果为 null 则返回0
   * @param searches
   *     要搜索的字符串，如果为 null 则直接追加原字符串
   * @param replaces
   *     要替换的字符串，如果为 null 则直接追加原字符串
   * @param ignoreCase
   *     比较字符串时是否忽略大小写
   * @param builder
   *     用于存储结果的StringBuilder，不能为 null
   * @return 替换的次数
   * @throws IndexOutOfBoundsException
   *     如果数组的长度不相同（null 是可以的，和/或大小为 0）
   */
  public static int replaceEach(@Nullable final String str,
      @Nullable final String[] searches, @Nullable final String[] replaces,
      final boolean ignoreCase, final StringBuilder builder) {
    if ((str == null) || str.isEmpty()) {
      return 0;
    }
    if ((searches == null) || (searches.length == 0) || (replaces == null)
        || (replaces.length == 0)) {
      builder.append(str);
      return 0;
    }
    requireEqual("searches.length", searches.length,
        "replaces.length", replaces.length);
    final boolean[] noMoreMatches = new boolean[searches.length];
    return replaceEachImpl(str, searches, replaces, ignoreCase, builder,
        noMoreMatches);
  }

  /**
   * 重复替换另一个字符串中所有出现的字符串。
   *
   * <p>传递给此方法的 {@code null} 引用是无操作的，或者如果任何"搜索字符串"或"要替换的字符串"为 null，
   * 该替换将被忽略。这将重复直到无法进行进一步替换。
   * <pre>
   *  replaceEach(null, *, *, *) = null
   *  replaceEach("", *, *, *) = ""
   *  replaceEach("aba", null, null, *) = "aba"
   *  replaceEach("aba", new String[0], null, *) = "aba"
   *  replaceEach("aba", null, new String[0], *) = "aba"
   *  replaceEach("aba", new String[]{"a"}, null, *) = "aba"
   *  replaceEach("aba", new String[]{"a"}, new String[]{""}, *) = "b"
   *  replaceEach("aba", new String[]{null}, new String[]{"a"}, *) = "aba"
   *  replaceEach("abcde", new String[]{"ab", "d"}, new String[]{"w", "t"}, *) = "wcte"
   *  (example of how it repeats)
   *  replaceEach("abcde", new String[]{"ab", "d"}, new String[]{"d", "t"}, false) = "dcte"
   *  replaceEach("abcde", new String[]{"ab", "d"}, new String[]{"d", "t"}, true) = "tcte"
   *  replaceEach("abcde", new String[]{"ab", "d"}, new String[]{"d", "ab"}, false) = "dcabe"
   *  (example of endless loop and throw IllegalArgumentException)
   *  replaceEach("abcde", new String[]{"ab", "d"}, new String[]{"d", "ab"}, true)
   * </pre>
   *
   * @param str
   *     要搜索和替换的字符串，如果为 null 则无操作
   * @param searches
   *     要搜索的字符串，如果为 null 则无操作
   * @param replaces
   *     要替换的字符串，如果为 null 则无操作
   * @param ignoreCase
   *     比较字符串时是否忽略大小写
   * @return 处理任何替换后的文本，如果输入为 null 字符串则返回 {@code null}
   * @throws IllegalStateException
   *     如果搜索是重复的并且由于一个的输出是另一个的输入而存在无限循环
   * @throws IndexOutOfBoundsException
   *     如果数组的长度不相同（null 是可以的，和/或大小为 0）
   */
  public static String replaceEachRepeatedly(@Nullable final String str,
      @Nullable final String[] searches, @Nullable final String[] replaces,
      final boolean ignoreCase) {
    if ((str == null)
        || str.isEmpty()
        || (searches == null)
        || (searches.length == 0)
        || (replaces == null)
        || (replaces.length == 0)) {
      return str;
    }
    requireEqual("searches.length", searches.length, "replaces.length", replaces.length);
    final boolean[] noMoreMatches = new boolean[searches.length];
    final StringBuilder builder = new StringBuilder();
    String result = str;
    for (int i = 0; i < searches.length; ++i) {
      final int modifies = replaceEachImpl(result, searches, replaces,
          ignoreCase, builder, noMoreMatches);
      if (modifies == 0) {
        return result;
      } else if (builder.length() == 0) {
        return EMPTY;
      }
      result = builder.toString();
      builder.setLength(0);
    }
    // check for the loop
    if (replaceEachImpl(result, searches, replaces,
        ignoreCase, builder, noMoreMatches) > 0) {
      throw new IllegalStateException("An loop of replacement was detected.");
    }
    return result;
  }

  /**
   * 重复替换另一个字符串中所有出现的字符串，将结果输出到StringBuilder。
   *
   * <p>传递给此方法的 {@code null} 引用将返回0，或者如果任何"搜索字符串"或"要替换的字符串"为 null，
   * 该替换将被忽略。这将重复直到无法进行进一步替换。
   *
   * <p>注意：这可能导致无限循环，如果替换会创建新的匹配项。
   *
   * @param str
   *     要搜索和替换的字符串，如果为 null 则返回0
   * @param searches
   *     要搜索的字符串，如果为 null 则直接追加原字符串
   * @param replaces
   *     要替换的字符串，如果为 null 则直接追加原字符串
   * @param ignoreCase
   *     比较字符串时是否忽略大小写
   * @param builder
   *     用于存储结果的StringBuilder，不能为 null
   * @return 替换的次数
   * @throws IndexOutOfBoundsException
   *     如果数组的长度不相同（null 是可以的，和/或大小为 0）
   */
  public static int replaceEachRepeatedly(@Nullable final String str,
      @Nullable final String[] searches, @Nullable final String[] replaces,
      final boolean ignoreCase, final StringBuilder builder) {
    if ((str == null) || (str.length() == 0)) {
      return 0;
    }
    if ((searches == null)
        || (searches.length == 0)
        || (replaces == null)
        || (replaces.length == 0)) {
      builder.append(str);
      return 0;
    }
    requireEqual("searches.length", searches.length, "replaces.length", replaces.length);
    final boolean[] noMoreMatches = new boolean[searches.length];

    final StringBuilder tempBuilder = new StringBuilder();
    int totalModifies = 0;
    String text = str;
    for (int i = 0; i < searches.length; ++i) {
      final int modifies = replaceEachImpl(text, searches, replaces, ignoreCase,
          tempBuilder, noMoreMatches);
      if (modifies == 0) {
        break;
      }
      totalModifies += modifies;
      if (tempBuilder.length() == 0) {
        return totalModifies;
      }
      text = tempBuilder.toString();
      tempBuilder.setLength(0);
    }
    builder.append(text);
    // check for the loop
    if (replaceEachImpl(text, searches, replaces, ignoreCase, tempBuilder,
        noMoreMatches) > 0) {
      throw new IllegalStateException("An loop of replacement was detected.");
    }
    return totalModifies;
  }

  /**
   * 替换字符串中另一个字符串的所有出现。
   *
   * <p>传递给此方法的{@code null}引用是无操作的，或者如果任何
   * "搜索字符串"或"要替换的字符串"为null，该替换将被忽略。
   *
   * @param str
   *     要搜索和替换的文本；不能为null或空。
   * @param searches
   *     要搜索的字符串；不能为null或空。
   * @param replaces
   *     要替换的字符串；不能为null或空，并且
   *     必须与{@code searches}数组长度相同。
   * @param ignoreCase
   *     比较字符串时是否忽略大小写。
   * @param builder
   *     此函数使用的临时{@link StringBuilder}。
   * @param noMoreMatches
   *     此函数使用的临时{@code boolean}数组。它必须与
   *     {@code searches}数组长度相同。
   * @return 处理任何替换后的文本。
   */
  private static int replaceEachImpl(final CharSequence str, final CharSequence[] searches,
      final CharSequence[] replaces, final boolean ignoreCase,
      final StringBuilder builder, final boolean[] noMoreMatches) {
    // assume searches.length == replaces.length == noMoreMathes.length
    final int searchLen = searches.length;
    // initialize the noMoreMatches array
    for (int i = 0; i < searchLen; ++i) {
      noMoreMatches[i] = false;
    }
    // start searching
    final int strLen = str.length();
    int modifies = 0;
    int start = 0;
    while (start < strLen) {
      // find the next earliest match
      int replaceIndex = -1;
      int replacePos = Integer.MAX_VALUE;
      for (int i = 0; i < searchLen; ++i) {
        if (noMoreMatches[i] || (searches[i] == null) || searches[i].isEmpty() || (replaces[i] == null)) {
          continue;
        }
        final int pos = firstIndexOf(str, start, strLen, searches[i], ignoreCase);
        // see if we need to keep searching for this
        if (pos == strLen) {
          noMoreMatches[i] = true;
        } else if (pos < replacePos) {
          replacePos = pos;
          replaceIndex = i;
        }
      }
      if (replaceIndex < 0) {
        // no more match found
        builder.append(str, start, strLen);
        break;
      }
      // otherwise, make the replacement
      if (start < replacePos) {
        builder.append(str, start, replacePos);
      }
      if (replaces[replaceIndex] != null) {
        builder.append(replaces[replaceIndex]);
      }
      start = replacePos + searches[replaceIndex].length();
      ++modifies;
    }
    return modifies;
  }

  /**
   * 获取字符串最左边的{@code len}个字符。
   *
   * <p>如果{@code len}个字符不可用，或字符串为{@code null}，
   * 将不抛出异常直接返回字符串。如果len为负数则抛出异常。
   * <pre>
   * StringUtils.left(null, *)    = null
   * StringUtils.left(*, -ve)     = ""
   * StringUtils.left("", *)      = ""
   * StringUtils.left("abc", 0)   = ""
   * StringUtils.left("abc", 2)   = "ab"
   * StringUtils.left("abc", 4)   = "abc"
   * </pre>
   *
   * @param str
   *     要获取最左边字符的字符串，可能为null
   * @param len
   *     所需字符串的长度，必须为零或正数
   * @return 最左边的字符，如果输入字符串为null则返回{@code null}
   */
  public static String left(@Nullable final String str, final int len) {
    if (str == null) {
      return null;
    }
    if (len < 0) {
      return EMPTY;
    }
    if (str.length() <= len) {
      return str;
    }
    return str.substring(0, len);
  }

  /**
   * 获取字符串最右边的{@code len}个字符。
   *
   * <p>如果{@code len}个字符不可用，或字符串为{@code null}，
   * 将不抛出异常直接返回字符串。如果len为负数则抛出异常。
   * <pre>
   * StringUtils.right(null, *)    = null
   * StringUtils.right(*, -ve)     = ""
   * StringUtils.right("", *)      = ""
   * StringUtils.right("abc", 0)   = ""
   * StringUtils.right("abc", 2)   = "bc"
   * StringUtils.right("abc", 4)   = "abc"
   * </pre>
   *
   * @param str
   *     要获取最右边字符的字符串，可能为null
   * @param len
   *     所需字符串的长度，必须为零或正数
   * @return 最右边的字符，如果输入字符串为null则返回{@code null}
   */
  public static String right(@Nullable final String str, final int len) {
    if (str == null) {
      return null;
    }
    if (len < 0) {
      return EMPTY;
    }
    if (str.length() <= len) {
      return str;
    }
    return str.substring(str.length() - len);
  }

  /**
   * 从字符串中间获取{@code len}个字符。
   *
   * <p>如果{@code len}个字符不可用，将不抛出异常直接返回
   * 字符串的剩余部分。如果字符串为{@code null}，
   * 将返回{@code null}。如果len为负数则抛出异常。
   * <pre>
   * StringUtils.mid(null, *, *)    = null
   * StringUtils.mid(*, *, -ve)     = ""
   * StringUtils.mid("", 0, *)      = ""
   * StringUtils.mid("abc", 0, 2)   = "ab"
   * StringUtils.mid("abc", 0, 4)   = "abc"
   * StringUtils.mid("abc", 2, 4)   = "c"
   * StringUtils.mid("abc", 4, 2)   = ""
   * StringUtils.mid("abc", -2, 2)  = "ab"
   * </pre>
   *
   * @param str
   *     要获取字符的字符串，可能为 null
   * @param pos
   *     开始位置，负数视为零
   * @param len
   *     所需字符串的长度，必须为零或正数
   * @return 中间字符，如果输入字符串为 null 则返回 {@code null}
   */
  public static String mid(@Nullable final String str, final int pos,
      final int len) {
    if (str == null) {
      return null;
    }
    if ((len < 0) || (pos > str.length())) {
      return EMPTY;
    }
    final int start = Math.max(0, pos);
    if (str.length() <= (start + len)) {
      return str.substring(start);
    }
    return str.substring(start, start + len);
  }

  /**
   * 从指定字符串获取子字符串，避免异常。
   *
   * <p>可以使用负的起始位置从字符串末尾开始 {@code n} 个字符。
   *
   * <p>{@code null} 字符串将返回 {@code null}。空字符串 ("") 将返回 ""。
   * <pre>
   * StringUtils.substring(null, *)   = null
   * StringUtils.substring("", *)     = ""
   * StringUtils.substring("abc", 0)  = "abc"
   * StringUtils.substring("abc", 2)  = "c"
   * StringUtils.substring("abc", 4)  = ""
   * StringUtils.substring("abc", -2) = "bc"
   * StringUtils.substring("abc", -4) = "abc"
   * </pre>
   *
   * @param str
   *     要获取子字符串的字符串，可能为 null
   * @param pos
   *     开始位置，负数表示从字符串末尾向后计数此数量的字符
   * @return 从开始位置的子字符串，如果输入字符串为 null 则返回 {@code null}
   */
  public static String substring(@Nullable final String str, final int pos) {
    if (str == null) {
      return null;
    }
    // handle negatives, which means last n characters
    int start = pos;
    if (start < 0) {
      start += str.length(); // remember start is negative
    }
    if (start < 0) {
      start = 0;
    }
    if (start > str.length()) {
      return EMPTY;
    }
    return str.substring(start);
  }

  /**
   * 从指定字符串获取子字符串，避免异常。
   *
   * <p>可以使用负的起始位置从字符串末尾开始/结束{@code n}个字符。
   *
   * <p>返回的子字符串从{@code start}位置的字符开始，在{@code end}
   * 位置之前结束。所有位置计数都是从零开始的——即，要从字符串开头开始，
   * 使用{@code start = 0}。负的开始和结束位置可用于指定
   * 相对于字符串末尾的偏移量。
   *
   * <p>如果{@code start}不严格位于{@code end}的左边，则返回""。
   * <pre>
   * StringUtils.substring(null, *, *)    = null
   * StringUtils.substring("", * ,  *)    = "";
   * StringUtils.substring("abc", 0, 2)   = "ab"
   * StringUtils.substring("abc", 2, 0)   = ""
   * StringUtils.substring("abc", 2, 4)   = "c"
   * StringUtils.substring("abc", 4, 6)   = ""
   * StringUtils.substring("abc", 2, 2)   = ""
   * StringUtils.substring("abc", -2, -1) = "b"
   * StringUtils.substring("abc", -4, 2)  = "ab"
   * </pre>
   *
   * @param str
   *     要获取子字符串的字符串，可能为null
   * @param startIndex
   *     开始位置，负数表示从字符串末尾向后计数此数量的字符
   * @param endIndex
   *     结束位置（不包含），负数表示从字符串末尾向后计数此数量的字符
   * @return 从开始位置到结束位置的子字符串，如果输入字符串为null则返回{@code null}
   */
  public static String substring(@Nullable final String str, final int startIndex,
      final int endIndex) {
    if (str == null) {
      return null;
    }
    // handle negatives
    int end = endIndex;
    if (end < 0) {
      end = str.length() + end; // remember end is negative
    }
    int start = startIndex;
    if (start < 0) {
      start = str.length() + start; // remember start is negative
    }
    // check length next
    if (end > str.length()) {
      end = str.length();
    }
    // if start is greater than end, return ""
    if (start > end) {
      return EMPTY;
    }
    if (start < 0) {
      start = 0;
    }
    if (end < 0) {
      end = 0;
    }
    return str.substring(start, end);
  }

  /**
   * 获取分隔符第一次出现之前的子字符串。不返回分隔符。
   *
   * <p>如果输入字符串为{@code null}，将返回{@code null}。空字符串("")
   * 输入将返回空字符串。{@code null}分隔符将返回输入字符串。
   * <pre>
   * StringUtils.substringBefore(null, *)      = null
   * StringUtils.substringBefore("", *)        = ""
   * StringUtils.substringBefore("abc", "a")   = ""
   * StringUtils.substringBefore("abcba", "b") = "a"
   * StringUtils.substringBefore("abc", "c")   = "ab"
   * StringUtils.substringBefore("abc", "d")   = "abc"
   * StringUtils.substringBefore("abc", "")    = ""
   * StringUtils.substringBefore("abc", null)  = "abc"
   * </pre>
   *
   * @param str
   *     要获取子字符串的字符串，可能为null
   * @param separator
   *     要搜索的字符串，可能为null
   * @return 分隔符第一次出现之前的子字符串，如果输入字符串为null则返回{@code null}
   */
  public static String substringBefore(@Nullable final String str,
      @Nullable final String separator) {
    if ((str == null) || (str.length() == 0) || (separator == null)) {
      return str;
    }
    if (separator.length() == 0) {
      return EMPTY;
    }
    final int pos = str.indexOf(separator);
    if (pos == -1) {
      return str;
    }
    return str.substring(0, pos);
  }

  /**
   * 获取分隔符第一次出现之后的子字符串。不返回分隔符。
   *
   * <p>如果输入字符串为{@code null}，将返回{@code null}。空字符串("")
   * 输入将返回空字符串。如果输入字符串不为{@code null}，
   * {@code null}分隔符将返回空字符串。
   * <pre>
   * StringUtils.substringAfter(null, *)      = null
   * StringUtils.substringAfter("", *)        = ""
   * StringUtils.substringAfter(*, null)      = ""
   * StringUtils.substringAfter("abc", "a")   = "bc"
   * StringUtils.substringAfter("abcba", "b") = "cba"
   * StringUtils.substringAfter("abc", "c")   = ""
   * StringUtils.substringAfter("abc", "d")   = ""
   * StringUtils.substringAfter("abc", "")    = "abc"
   * </pre>
   *
   * @param str
   *     要获取子字符串的字符串，可能为null
   * @param separator
   *     要搜索的字符串，可能为null
   * @return 分隔符第一次出现之后的子字符串，如果输入字符串为null则返回{@code null}
   */
  public static String substringAfter(@Nullable final String str,
      @Nullable final String separator) {
    if ((str == null) || (str.length() == 0)) {
      return str;
    }
    if (separator == null) {
      return EMPTY;
    }
    final int pos = str.indexOf(separator);
    if (pos == -1) {
      return EMPTY;
    }
    return str.substring(pos + separator.length());
  }

  /**
   * 获取分隔符最后一次出现之前的子字符串。不返回分隔符。
   *
   * <p>如果输入字符串为{@code null}，将返回{@code null}。空字符串("")
   * 输入将返回空字符串。空的或{@code null}分隔符将返回输入字符串。
   * <pre>
   * StringUtils.substringBeforeLast(null, *)      = null
   * StringUtils.substringBeforeLast("", *)        = ""
   * StringUtils.substringBeforeLast("abcba", "b") = "abc"
   * StringUtils.substringBeforeLast("abc", "c")   = "ab"
   * StringUtils.substringBeforeLast("a", "a")     = ""
   * StringUtils.substringBeforeLast("a", "z")     = "a"
   * StringUtils.substringBeforeLast("a", null)    = "a"
   * StringUtils.substringBeforeLast("a", "")      = "a"
   * </pre>
   *
   * @param str
   *     要获取子字符串的字符串，可能为null
   * @param separator
   *     要搜索的字符串，可能为null
   * @return 分隔符最后一次出现之前的子字符串，如果输入字符串为null则返回{@code null}
   */
  public static String substringBeforeLast(@Nullable final String str,
      @Nullable final String separator) {
    if ((str == null) || (str.length() == 0) || (separator == null)
        || (separator.length() == 0)) {
      return str;
    }
    final int pos = str.lastIndexOf(separator);
    if (pos == -1) {
      return str;
    }
    return str.substring(0, pos);
  }

  /**
   * 获取分隔符最后一次出现之后的子字符串。不返回分隔符。
   *
   * <p>如果字符串输入为{@code null}，返回{@code null}。空的("")
   * 字符串输入返回空字符串。如果输入字符串不为{@code null}，
   * 空的或{@code null}分隔符将返回空字符串。
   * <pre>
   * StringUtils.substringAfterLast(null, *)      = null
   * StringUtils.substringAfterLast("", *)        = ""
   * StringUtils.substringAfterLast("", "")        = ""
   * StringUtils.substringAfterLast(*, "")        = ""
   * StringUtils.substringAfterLast(*, null)      = ""
   * StringUtils.substringAfterLast("abc", "a")   = "bc"
   * StringUtils.substringAfterLast("abcba", "b") = "a"
   * StringUtils.substringAfterLast("abc", "c")   = ""
   * StringUtils.substringAfterLast("a", "a")     = ""
   * StringUtils.substringAfterLast("a", "z")     = ""
   * </pre>
   *
   * @param str
   *     要从中获取子字符串的字符串，可能为null
   * @param separator
   *     要搜索的字符串，可能为null
   * @return 分隔符最后一次出现之后的子字符串，如果字符串输入为null则返回{@code null}
   */
  public static String substringAfterLast(@Nullable final String str,
      @Nullable final String separator) {
    if ((str == null) || (str.length() == 0)) {
      return str;
    }
    if ((separator == null) || (separator.length() == 0)) {
      return EMPTY;
    }
    final int pos = str.lastIndexOf(separator);
    if ((pos == -1) || (pos == (str.length() - separator.length()))) {
      return EMPTY;
    }
    return str.substring(pos + separator.length());
  }

  /**
   * 获取嵌套在同一个字符串的两个实例之间的字符串。
   *
   * <p>如果输入字符串为{@code null}，返回{@code null}。如果标签为{@code null}，
   * 返回{@code null}。
   * <pre>
   * StringUtils.substringBetween(null, *)            = null
   * StringUtils.substringBetween("", "")             = ""
   * StringUtils.substringBetween("", "tag")          = null
   * StringUtils.substringBetween("tagabctag", null)  = null
   * StringUtils.substringBetween("tagabctag", "")    = ""
   * StringUtils.substringBetween("tagabctag", "tag") = "abc"
   * </pre>
   *
   * @param str
   *     包含子字符串的字符串，可能为null
   * @param tag
   *     子字符串前后的字符串，可能为null
   * @return 子字符串，如果没有匹配则返回{@code null}
   */
  public static String substringBetween(@Nullable final String str,
      @Nullable final String tag) {
    return substringBetween(str, tag, tag);
  }

  /**
   * 获取嵌套在两个字符串之间的字符串。仅返回第一个匹配。
   *
   * <p>如果输入字符串为{@code null}，返回{@code null}。如果开启/关闭
   * 字符串为{@code null}，返回{@code null}（没有匹配）。空的("")开启和关闭
   * 字符串返回空字符串。
   * <pre>
   * StringUtils.substringBetween("[a][b][c]", "[", "]") = ["a","b","c"]
   * StringUtils.substringBetween(null, *, *)            = null
   * StringUtils.substringBetween(*, null, *)            = null
   * StringUtils.substringBetween(*, *, null)            = null
   * StringUtils.substringBetween("", "[", "]")          = []
   * </pre>
   *
   * @param str
   *     包含子字符串的字符串，可能为null
   * @param open
   *     子字符串之前的字符串，可能为null
   * @param close
   *     子字符串之后的字符串，可能为null
   * @return 子字符串，如果没有匹配则返回{@code null}
   */
  public static String substringBetween(@Nullable final String str,
      @Nullable final String open, @Nullable final String close) {
    if ((str == null) || (open == null) || (close == null)) {
      return null;
    }
    final int start = str.indexOf(open);
    if (start != -1) {
      final int end = str.indexOf(close, start + open.length());
      if (end != -1) {
        return str.substring(start + open.length(), end);
      }
    }
    return null;
  }

  /**
   * 在字符串中搜索由开始和结束标签分隔的子字符串，
   * 返回数组中所有匹配的子字符串。
   *
   * <p>如果输入字符串为{@code null}，返回{@code null}。如果开启/关闭
   * 字符串为{@code null}，返回{@code null}（没有匹配）。空的("")开启/关闭
   * 字符串返回{@code null}（没有匹配）。
   * <pre>
   * StringUtils.substringsBetween("[a][b][c]", "[", "]") = ["a","b","c"]
   * StringUtils.substringsBetween(null, *, *)            = null
   * StringUtils.substringsBetween(*, null, *)            = null
   * StringUtils.substringsBetween(*, *, null)            = null
   * StringUtils.substringsBetween("", "[", "]")          = []
   * </pre>
   *
   * @param str
   *     包含子字符串的字符串，null返回null，空的返回空的
   * @param open
   *     标识子字符串开始的字符串，空的返回null
   * @param close
   *     标识子字符串结束的字符串，空的返回null
   * @return 子字符串的字符串缓冲区，如果没有匹配则返回{@code null}
   */
  public static String[] substringsBetween(@Nullable final String str,
      @Nullable final String open, @Nullable final String close) {
    if ((str == null) || (open == null) || (open.length() == 0)
        || (close == null) || (close.length() == 0)) {
      return null;
    }
    final int strLen = str.length();
    if (strLen == 0) {
      return ArrayUtils.EMPTY_STRING_ARRAY;
    }
    final int closeLen = close.length();
    final int openLen = open.length();
    final List<String> list = new ArrayList<>();
    int pos = 0;
    while (pos < (strLen - closeLen)) {
      int start = str.indexOf(open, pos);
      if (start < 0) {
        break;
      }
      start += openLen;
      final int end = str.indexOf(close, start);
      if (end < 0) {
        break;
      }
      list.add(str.substring(start, end));
      pos = end + closeLen;
    }
    if (list.isEmpty()) {
      return null;
    }
    return list.toArray(new String[0]);
  }

  /**
   * 将提供的数组元素连接成包含提供的元素列表的单个字符串。
   *
   * <p>列表前后不添加分隔符。数组中的null对象或空字符串
   * 用空字符串表示。
   * <pre>
   * StringUtils.join(*, null)                   = null
   * StringUtils.join(*, [])                     = ""
   * StringUtils.join(';', [true, false, true])  = "true;false;true"
   * StringUtils.join(' ', [true, false, true])  = "true false true"
   * </pre>
   *
   * @param separator
   *     要使用的分隔符字符
   * @param array
   *     要连接在一起的值的数组，可能为null
   * @return 连接的字符串，如果数组输入为null则返回{@code null}
   * @see Joiner
   */
  public static String join(final char separator,
      @Nullable final boolean[] array) {
    return new Joiner(separator)
        .addAll(array)
        .toString();
  }

  /**
   * 将提供的数组元素连接成包含提供的元素列表的单个字符串。
   *
   * <p>列表前后不添加分隔符。数组中的null对象或空字符串
   * 用空字符串表示。
   * <pre>
   * StringUtils.join(*, null, *, *)                   = null
   * StringUtils.join(*, [], *, *)                     = ""
   * StringUtils.join(';', [true, false, true], 0, 3)  = "true;false;true"
   * StringUtils.join(' ', [true, false, true], 0, 2)  = "true false"
   * </pre>
   *
   * @param separator
   *     要使用的分隔符字符
   * @param array
   *     要连接在一起的值的数组，可能为null
   * @param startIndex
   *     开始连接的第一个出现索引。传入超过数组末尾的结束位置是错误的
   * @param endIndex
   *     停止连接的索引（不包含）。传入超过数组末尾的结束位置是错误的
   * @return 连接的字符串，如果数组输入为null则返回{@code null}
   * @see Joiner
   */
  public static String join(final char separator, @Nullable final boolean[] array,
      final int startIndex, final int endIndex) {
    return new Joiner(separator)
        .addAll(array, startIndex, endIndex)
        .toString();
  }

  /**
   * 将提供的数组元素连接成包含提供的元素列表的单个字符串。
   *
   * <p>列表前后不添加分隔符。数组中的null对象或空字符串
   * 用空字符串表示。
   * <pre>
   * StringUtils.join(*, null)                   = null
   * StringUtils.join(*, [])                     = ""
   * StringUtils.join(';', ['a', 'b', 'c'])      = "a;b;c"
   * StringUtils.join(' ', ['a', 'b', 'c'])      = "a b c"
   * </pre>
   *
   * @param separator
   *     要使用的分隔符字符
   * @param array
   *     要连接在一起的值的数组，可能为null
   * @return 连接的字符串，如果数组输入为null则返回{@code null}
   * @see Joiner
   */
  public static String join(final char separator,
      @Nullable final char[] array) {
    return new Joiner(separator)
        .addAll(array)
        .toString();
  }

  /**
   * 将提供的数组元素连接成包含提供的元素列表的单个字符串。
   *
   * <p>列表前后不添加分隔符。数组中的null对象或空字符串
   * 用空字符串表示。
   * <pre>
   * StringUtils.join(*, null, *, *)              = null
   * StringUtils.join(*, [], *, *)                = ""
   * StringUtils.join(';', ['a', 'b', 'c'], 0, 3) = "a;b;c"
   * StringUtils.join(' ', ['a', 'b', 'c'], 0, 2) = "a b"
   * </pre>
   *
   * @param separator
   *     要使用的分隔符字符
   * @param array
   *     要连接在一起的值的数组，可能为null
   * @param startIndex
   *     开始连接的第一个出现索引。传入超过数组末尾的结束位置是错误的
   * @param endIndex
   *     停止连接的索引（不包含）。传入超过数组末尾的结束位置是错误的
   * @return 连接的字符串，如果数组输入为null则返回{@code null}
   * @see Joiner
   */
  public static String join(final char separator, @Nullable final char[] array,
      final int startIndex, final int endIndex) {
    return new Joiner(separator)
        .addAll(array, startIndex, endIndex)
        .toString();
  }

  /**
   * 将提供的数组元素连接成包含提供的元素列表的单个字符串。
   *
   * <p>列表前后不添加分隔符。数组中的null对象或空字符串
   * 用空字符串表示。
   * <pre>
   * StringUtils.join(*, null)             = null
   * StringUtils.join(*, [])               = ""
   * StringUtils.join(';', [1, 2, 3])      = "1;2;3"
   * StringUtils.join(' ', [1, 2, 3])      = "1 2 3"
   * </pre>
   *
   * @param array
   *     要连接在一起的值的数组，可能为null
   * @param separator
   *     要使用的分隔符字符
   * @return 连接的字符串，如果数组输入为null则返回{@code null}
   * @see Joiner
   */
  public static String join(final char separator,
      @Nullable final byte[] array) {
    return new Joiner(separator)
        .addAll(array)
        .toString();
  }

  /**
   * 将提供的数组元素连接成包含提供的元素列表的单个字符串。
   *
   * <p>列表前后不添加分隔符。数组中的null对象或空字符串
   * 用空字符串表示。
   * <pre>
   * StringUtils.join(*, null, *, *)         = null
   * StringUtils.join(*, [], *, *)           = ""
   * StringUtils.join(';', [1, 2, 3], 0, 3)  = "1;2;3"
   * StringUtils.join(' ', [1, 2, 3], 0, 2)  = "1 2"
   * </pre>
   *
   * @param separator
   *     要使用的分隔符字符
   * @param array
   *     要连接在一起的值的数组，可能为null
   * @param startIndex
   *     开始连接的第一个出现索引。传入超过数组末尾的结束位置是错误的
   * @param endIndex
   *     停止连接的索引（不包含）。传入超过数组末尾的结束位置是错误的
   * @return 连接的字符串，如果数组输入为null则返回{@code null}
   * @see Joiner
   */
  public static String join(final char separator, @Nullable final byte[] array,
      final int startIndex, final int endIndex) {
    return new Joiner(separator)
        .addAll(array, startIndex, endIndex)
        .toString();
  }

  /**
   * 将提供的数组元素连接成包含提供的元素列表的单个字符串。
   *
   * <p>列表前后不添加分隔符。数组中的null对象或空字符串
   * 用空字符串表示。
   * <pre>
   * StringUtils.join(*, null)             = null
   * StringUtils.join(*, [])               = ""
   * StringUtils.join(';', [1, 2, 3])      = "1;2;3"
   * StringUtils.join(' ', [1, 2, 3])      = "1 2 3"
   * </pre>
   *
   * @param separator
   *     要使用的分隔符字符
   * @param array
   *     要连接在一起的值的数组，可能为null
   * @return 连接的字符串，如果数组输入为null则返回{@code null}
   * @see Joiner
   */
  public static String join(final char separator,
      @Nullable final short[] array) {
    return new Joiner(separator)
        .addAll(array)
        .toString();
  }

  /**
   * 将提供的数组元素连接成包含提供的元素列表的单个字符串。
   *
   * <p>列表前后不添加分隔符。数组中的null对象或空字符串
   * 用空字符串表示。
   * <pre>
   * StringUtils.join(*, null, *, *)         = null
   * StringUtils.join(*, [], *, *)           = ""
   * StringUtils.join(';', [1, 2, 3], 0, 3)  = "1;2;3"
   * StringUtils.join(' ', [1, 2, 3], 0, 2)  = "1 2"
   * </pre>
   *
   * @param separator
   *     要使用的分隔符字符
   * @param array
   *     要连接在一起的值的数组，可能为null
   * @param startIndex
   *     开始连接的第一个出现索引。传入超过数组末尾的结束位置是错误的
   * @param endIndex
   *     停止连接的索引（不包含）。传入超过数组末尾的结束位置是错误的
   * @return 连接的字符串，如果数组输入为null则返回{@code null}
   * @see Joiner
   */
  public static String join(final char separator,
      @Nullable final short[] array, final int startIndex, final int endIndex) {
    return new Joiner(separator)
        .addAll(array, startIndex, endIndex)
        .toString();
  }

  /**
   * 将提供的数组元素连接成包含提供的元素列表的单个字符串。
   *
   * <p>列表前后不添加分隔符。数组中的null对象或空字符串
   * 用空字符串表示。
   * <pre>
   * StringUtils.join(*, null)             = null
   * StringUtils.join(*, [])               = ""
   * StringUtils.join(';', [1, 2, 3])      = "1;2;3"
   * StringUtils.join(' ', [1, 2, 3])      = "1 2 3"
   * </pre>
   *
   * @param separator
   *     要使用的分隔符字符
   * @param array
   *     要连接在一起的值的数组，可能为null
   * @return 连接的字符串，如果数组输入为null则返回{@code null}
   * @see Joiner
   */
  public static String join(final char separator, @Nullable final int[] array) {
    return new Joiner(separator)
        .addAll(array)
        .toString();
  }

  /**
   * 将提供的数组元素连接成包含提供的元素列表的单个字符串。
   *
   * <p>列表前后不添加分隔符。数组中的null对象或空字符串
   * 用空字符串表示。
   * <pre>
   * StringUtils.join(*, null, *, *)         = null
   * StringUtils.join(*, [], *, *)           = ""
   * StringUtils.join(';', [1.1, 2.2, 3.3], 0, 3)  = "1.1;2.2;3.3"
   * StringUtils.join(' ', [1.1, 2.2, 3.3], 0, 2)  = "1.1 2.2"
   * </pre>
   *
   * @param separator
   *     要使用的分隔符字符
   * @param array
   *     要连接在一起的值的数组，可能为null
   * @param startIndex
   *     开始连接的第一个出现索引。传入超过数组末尾的结束位置是错误的
   * @param endIndex
   *     停止连接的索引（不包含）。传入超过数组末尾的结束位置是错误的
   * @return 连接的字符串，如果数组输入为null则返回{@code null}
   * @see Joiner
   */
  public static String join(final char separator, @Nullable final int[] array,
      final int startIndex, final int endIndex) {
    return new Joiner(separator)
        .addAll(array, startIndex, endIndex)
        .toString();
  }

  /**
   * 将提供的数组元素连接成包含提供的元素列表的单个字符串。
   *
   * <p>列表前后不添加分隔符。数组中的null对象或空字符串
   * 用空字符串表示。
   * <pre>
   * StringUtils.join(*, null)             = null
   * StringUtils.join(*, [])               = ""
   * StringUtils.join(';', [1, 2, 3])      = "1;2;3"
   * StringUtils.join(' ', [1, 2, 3])      = "1 2 3"
   * </pre>
   *
   * @param separator
   *     要使用的分隔符字符
   * @param array
   *     要连接在一起的值的数组，可能为null
   * @return 连接的字符串，如果数组输入为null则返回{@code null}
   * @see Joiner
   */
  public static String join(final char separator,
      @Nullable final long[] array) {
    return new Joiner(separator)
        .addAll(array)
        .toString();
  }

  /**
   * 将提供的数组元素连接成包含提供的元素列表的单个字符串。
   *
   * <p>列表前后不添加分隔符。数组中的null对象或空字符串
   * 用空字符串表示。
   * <pre>
   * StringUtils.join(*, null, *, *)         = null
   * StringUtils.join(*, [], *, *)           = ""
   * StringUtils.join(';', [1, 2, 3], 0, 3)  = "1;2;3"
   * StringUtils.join(' ', [1, 2, 3], 0, 2)  = "1 2"
   * </pre>
   *
   * @param separator
   *     要使用的分隔符字符
   * @param array
   *     要连接在一起的值的数组，可能为null
   * @param startIndex
   *     开始连接的第一个出现索引。传入超过数组末尾的结束位置是错误的
   * @param endIndex
   *     停止连接的索引（不包含）。传入超过数组末尾的结束位置是错误的
   * @return 连接的字符串，如果数组输入为null则返回{@code null}
   * @see Joiner
   */
  public static String join(final char separator, @Nullable final long[] array,
      final int startIndex, final int endIndex) {
    return new Joiner(separator)
        .addAll(array, startIndex, endIndex)
        .toString();
  }

  /**
   * 将提供的数组元素连接成包含提供的元素列表的单个字符串。
   *
   * <p>列表前后不添加分隔符。数组中的null对象或空字符串
   * 用空字符串表示。
   * <pre>
   * StringUtils.join(*, null)                = null
   * StringUtils.join(*, [])                 = ""
   * StringUtils.join(';', [1.1, 2.2, 3.3])  = "1.1;2.2;3.3"
   * StringUtils.join(' ', [1.1, 2.2, 3.3])  = "1.1 2.2 3.3"
   * </pre>
   *
   * @param separator
   *     要使用的分隔符字符
   * @param array
   *     要连接在一起的值的数组，可能为null
   * @return 连接的字符串，如果数组输入为null则返回{@code null}
   * @see Joiner
   */
  public static String join(final char separator,
      @Nullable final float[] array) {
    return new Joiner(separator)
        .addAll(array)
        .toString();
  }

  /**
   * 将提供的数组元素连接成包含提供的元素列表的单个字符串。
   *
   * <p>列表前后不添加分隔符。数组中的null对象或空字符串
   * 用空字符串表示。
   * <pre>
   * StringUtils.join(*, null, *, *)         = null
   * StringUtils.join(*, [], *, *)           = ""
   * StringUtils.join(';', [1.1, 2.2, 3.3], 0, 3)  = "1.1;2.2;3.3"
   * StringUtils.join(' ', [1.1, 2.2, 3.3], 0, 2)  = "1.1 2.2"
   * </pre>
   *
   * @param separator
   *     要使用的分隔符字符
   * @param array
   *     要连接在一起的值的数组，可能为null
   * @param startIndex
   *     开始连接的第一个出现索引。传入超过数组末尾的结束位置是错误的
   * @param endIndex
   *     停止连接的索引（不包含）。传入超过数组末尾的结束位置是错误的
   * @return 连接的字符串，如果数组输入为null则返回{@code null}
   * @see Joiner
   */
  public static String join(final char separator,
      @Nullable final float[] array, final int startIndex, final int endIndex) {
    return new Joiner(separator)
        .addAll(array, startIndex, endIndex)
        .toString();
  }

  /**
   * 将提供的数组元素连接成包含提供的元素列表的单个字符串。
   *
   * <p>列表前后不添加分隔符。数组中的null对象或空字符串
   * 用空字符串表示。
   * <pre>
   * StringUtils.join(*, null)                = null
   * StringUtils.join(*, [])                 = ""
   * StringUtils.join(';', [1.1, 2.2, 3.3])  = "1.1;2.2;3.3"
   * StringUtils.join(' ', [1.1, 2.2, 3.3])  = "1.1 2.2 3.3"
   * </pre>
   *
   * @param separator
   *     要使用的分隔符字符
   * @param array
   *     要连接在一起的值的数组，可能为null
   * @return 连接的字符串，如果数组输入为null则返回{@code null}
   * @see Joiner
   */
  public static String join(final char separator,
      @Nullable final double[] array) {
    return new Joiner(separator)
        .addAll(array)
        .toString();
  }

  /**
   * 将提供的数组元素连接成包含提供的元素列表的单个字符串。
   *
   * <p>列表前后不添加分隔符。数组中的null对象或空字符串
   * 用空字符串表示。
   * <pre>
   * StringUtils.join(*, null, *, *)         = null
   * StringUtils.join(*, [], *, *)           = ""
   * StringUtils.join(';', [1.1, 2.2, 3.3], 0, 3)  = "1.1;2.2;3.3"
   * StringUtils.join(' ', [1.1, 2.2, 3.3], 0, 2)  = "1.1 2.2"
   * </pre>
   *
   * @param separator
   *     要使用的分隔符字符
   * @param array
   *     要连接在一起的值的数组，可能为null
   * @param startIndex
   *     开始连接的第一个出现索引。传入超过数组末尾的结束位置是错误的
   * @param endIndex
   *     停止连接的索引（不包含）。传入超过数组末尾的结束位置是错误的
   * @return 连接的字符串，如果数组输入为null则返回{@code null}
   * @see Joiner
   */
  public static String join(final char separator,
      @Nullable final double[] array, final int startIndex, final int endIndex) {
    return new Joiner(separator)
        .addAll(array, startIndex, endIndex)
        .toString();
  }

  /**
   * 将提供的数组元素连接成包含提供的元素列表的单个字符串。
   *
   * <p>列表前后不添加分隔符。数组中的null对象或空字符串
   * 用空字符串表示。
   * <pre>
   * StringUtils.join(*, null)               = null
   * StringUtils.join(*, [])                 = ""
   * StringUtils.join(*, [null])             = ""
   * StringUtils.join(';', ["a", "b", "c"])  = "a;b;c"
   * StringUtils.join(' ', ["a", "b", "c"])  = "a b c"
   * StringUtils.join(';', [null, "", "a"])  = ";;a"
   * </pre>
   *
   * @param <T>
   *     要连接的元素类型
   * @param separator
   *     要使用的分隔符字符
   * @param array
   *     要连接在一起的值的数组，可能为null
   * @return 连接的字符串，如果数组输入为null则返回{@code null}
   * @see Joiner
   */
  public static <T> String join(final char separator,
      @Nullable final T[] array) {
    return new Joiner(separator)
        .addAll(array)
        .toString();
  }

  /**
   * 将提供的数组元素连接成包含提供的元素列表的单个字符串。
   *
   * <p>列表前后不添加分隔符。数组中的null对象或空字符串
   * 用空字符串表示。
   *
   * <p>示例请参见：{@link #join(char, Object[])}。
   *
   * @param <T>
   *     要连接的元素类型
   * @param separator
   *     要使用的分隔符字符
   * @param array
   *     要连接在一起的值的数组，可能为null
   * @param startIndex
   *     开始连接的第一个出现索引。如果小于零，则视为零
   * @param endIndex
   *     停止连接的索引（不包含）。如果大于{@code array}的长度，则视为{@code array}的长度
   * @return 连接的字符串，如果数组输入为null则返回{@code null}
   * @see Joiner
   */
  public static <T> String join(final char separator,
      @Nullable final T[] array, final int startIndex, final int endIndex) {
    return new Joiner(separator)
        .addAll(array, startIndex, endIndex)
        .toString();
  }

  /**
   * 将提供的{@code Iterator}元素连接成包含提供的元素的单个字符串。
   *
   * <p>列表前后不添加分隔符。迭代中的null对象或空字符串
   * 用空字符串表示。
   *
   * <p>示例请参见：{@link #join(char, Object[])}。
   *
   * @param <T>
   *     要连接的元素类型
   * @param separator
   *     要使用的分隔符字符
   * @param iterator
   *     要连接在一起的值的{@code Iterator}，可能为null
   * @return 连接的字符串，如果迭代器输入为null则返回{@code null}
   * @see Joiner
   */
  public static <T> String join(final char separator,
      @Nullable final Iterator<T> iterator) {
    return new Joiner(separator)
        .addAll(iterator)
        .toString();
  }

  /**
   * 将提供的{@code Iterable}元素连接成包含提供的元素的单个字符串。
   *
   * <p>列表前后不添加分隔符。迭代中的null对象或空字符串
   * 用空字符串表示。
   *
   * <p>示例请参见：{@link #join(char, Object[])}。
   *
   * @param <T>
   *     要连接的元素类型
   * @param separator
   *     要使用的分隔符字符
   * @param iterable
   *     要连接在一起的值的{@code Iterable}，可能为null
   * @return 连接的字符串，如果迭代器输入为null则返回{@code null}
   * @see Joiner
   */
  public static <T> String join(final char separator,
      @Nullable final Iterable<T> iterable) {
    return new Joiner(separator)
        .addAll(iterable)
        .toString();
  }

  /**
   * 将提供的数组元素连接成包含提供的元素列表的单个字符串。
   *
   * <p>列表前后不添加分隔符。{@code null}分隔符与空字符串("")相同。
   * 数组中的null对象或空字符串用空字符串表示。
   * <pre>
   * StringUtils.join(*, null)                = null
   * StringUtils.join(*, [])                  = ""
   * StringUtils.join(*, [null])              = ""
   * StringUtils.join("--", ["a", "b", "c"])  = "a--b--c"
   * StringUtils.join(null, ["a", "b", "c"])  = "abc"
   * StringUtils.join("", ["a", "b", "c"])    = "abc"
   * StringUtils.join(',', [null, "", "a"])   = ",,a"
   * </pre>
   *
   * @param separator
   *     要使用的分隔符字符，null被视为空字符串
   * @param array
   *     要连接在一起的值的数组，可能为null
   * @return 连接的字符串，如果数组输入为null则返回{@code null}
   * @see Joiner
   */
  public static String join(@Nullable final String separator,
      @Nullable final boolean[] array) {
    return new Joiner(separator)
        .addAll(array)
        .toString();
  }

  /**
   * 将提供的数组元素连接成包含提供的元素列表的单个字符串。
   *
   * <p>列表前后不添加分隔符。{@code null} 分隔符与空字符串 ("") 相同。
   * 数组中的null对象或空字符串用空字符串表示。
   * <pre>
   * StringUtils.join(*, null, *, *)                = null
   * StringUtils.join(*, [], *, *)                  = ""
   * StringUtils.join(*, [null], *, *)              = ""
   * StringUtils.join("--", ["a", "b", "c", 0, 3])  = "a--b--c"
   * StringUtils.join(null, ["a", "b", "c", 0, 2])  = "ab"
   * StringUtils.join("", ["a", "b", "c", 1, 3])    = "bc"
   * StringUtils.join(',', [null, "", "a", 0, 3])   = ",,a"
   * </pre>
   *
   * @param separator
   *     要使用的分隔符字符，null被视为空字符串
   * @param array
   *     要连接在一起的值的数组，可能为null
   * @param startIndex
   *     开始连接的第一个出现索引。如果小于零，则视为零
   * @param endIndex
   *     停止连接的索引（不包含）。如果大于{@code array}的长度，则视为{@code array}的长度
   * @return 连接的字符串，如果数组输入为null则返回{@code null}
   * @see Joiner
   */
  public static String join(@Nullable final String separator,
      @Nullable final boolean[] array, final int startIndex, final int endIndex) {
    return new Joiner(separator)
        .addAll(array, startIndex, endIndex)
        .toString();
  }

  /**
   * 将提供的数组元素连接成包含提供的元素列表的单个字符串。
   *
   * <p>列表前后不添加分隔符。{@code null} 分隔符与空字符串 ("") 相同。
   * 数组中的null对象或空字符串用空字符串表示。
   * <pre>
   * StringUtils.join(*, null)                = null
   * StringUtils.join(*, [])                  = ""
   * StringUtils.join(*, [null])              = ""
   * StringUtils.join("--", ["a", "b", "c"])  = "a--b--c"
   * StringUtils.join(null, ["a", "b", "c"])  = "abc"
   * StringUtils.join("", ["a", "b", "c"])    = "abc"
   * StringUtils.join(',', [null, "", "a"])   = ",,a"
   * </pre>
   *
   * @param separator
   *     要使用的分隔符字符，null被视为空字符串
   * @param array
   *     要连接在一起的值的数组，可能为null
   * @return 连接的字符串，如果数组输入为null则返回{@code null}
   * @see Joiner
   */
  public static String join(@Nullable final String separator,
      @Nullable final char[] array) {
    return new Joiner(separator)
        .addAll(array)
        .toString();
  }

  /**
   * 将提供的数组元素连接成包含提供元素列表的单个字符串。
   *
   * <p>列表前后不添加分隔符。{@code null} 分隔符与空字符串("")相同。
   * 数组中的null对象或空字符串用空字符串表示。
   * <pre>
   * StringUtils.join(*, null, *, *)                = null
   * StringUtils.join(*, [], *, *)                  = ""
   * StringUtils.join(*, [null], *, *)              = ""
   * StringUtils.join("--", ["a", "b", "c", 0, 3])  = "a--b--c"
   * StringUtils.join(null, ["a", "b", "c", 0, 2])  = "ab"
   * StringUtils.join("", ["a", "b", "c", 1, 3])    = "bc"
   * StringUtils.join(',', [null, "", "a", 0, 3])   = ",,a"
   * </pre>
   *
   * @param separator
   *     要使用的分隔符字符，null被视为空字符串
   * @param array
   *     要连接的值数组，可能为null
   * @param startIndex
   *     开始连接的第一个索引。如果小于零，则视为零
   * @param endIndex
   *     停止连接的索引（不包含）。如果大于{@code array}的长度，
   *     则视为{@code array}的长度
   * @return 连接的字符串，如果数组输入为null则返回{@code null}
   * @see Joiner
   */
  public static String join(@Nullable final String separator,
      @Nullable final char[] array, final int startIndex, final int endIndex) {
    return new Joiner(separator)
        .addAll(array, startIndex, endIndex)
        .toString();
  }

  /**
   * 将提供的数组元素连接成包含提供元素列表的单个字符串。
   *
   * <p>列表前后不添加分隔符。{@code null} 分隔符与空字符串("")相同。
   * 数组中的null对象或空字符串用空字符串表示。
   * <pre>
   * StringUtils.join(*, null)                = null
   * StringUtils.join(*, [])                  = ""
   * StringUtils.join(*, [null])              = ""
   * StringUtils.join("--", ["a", "b", "c"])  = "a--b--c"
   * StringUtils.join(null, ["a", "b", "c"])  = "abc"
   * StringUtils.join("", ["a", "b", "c"])    = "abc"
   * StringUtils.join(',', [null, "", "a"])   = ",,a"
   * </pre>
   *
   * @param separator
   *     要使用的分隔符字符，null被视为空字符串
   * @param array
   *     要连接的值数组，可能为null
   * @return 连接的字符串，如果数组输入为null则返回{@code null}
   * @see Joiner
   */
  public static String join(@Nullable final String separator,
      @Nullable final byte[] array) {
    return new Joiner(separator)
        .addAll(array)
        .toString();
  }

  /**
   * 将提供的数组元素连接成包含提供元素列表的单个字符串。
   *
   * <p>列表前后不添加分隔符。{@code null} 分隔符与空字符串("")相同。
   * 数组中的null对象或空字符串用空字符串表示。
   * <pre>
   * StringUtils.join(*, null, *, *)                = null
   * StringUtils.join(*, [], *, *)                  = ""
   * StringUtils.join(*, [null], *, *)              = ""
   * StringUtils.join("--", ["a", "b", "c", 0, 3])  = "a--b--c"
   * StringUtils.join(null, ["a", "b", "c", 0, 2])  = "ab"
   * StringUtils.join("", ["a", "b", "c", 1, 3])    = "bc"
   * StringUtils.join(',', [null, "", "a", 0, 3])   = ",,a"
   * </pre>
   *
   * @param separator
   *     要使用的分隔符，null被视为空字符串
   * @param array
   *     要连接的值数组，可能为null
   * @param startIndex
   *     开始连接的第一个位置。如果小于零，则视为零
   * @param endIndex
   *     停止连接的索引（不包括）。如果大于{@code array}的长度，
   *     则视为{@code array}的长度
   * @return 连接后的字符串，如果输入数组为null则返回{@code null}
   * @see Joiner
   */
  public static String join(@Nullable final String separator,
      @Nullable final byte[] array, final int startIndex, final int endIndex) {
    return new Joiner(separator)
        .addAll(array, startIndex, endIndex)
        .toString();
  }

  /**
   * 将提供的数组元素连接成包含提供元素列表的单个字符串。
   *
   * <p>列表前后不添加分隔符。{@code null} 分隔符与空字符串("")相同。
   * 数组中的null对象或空字符串用空字符串表示。
   * <pre>
   * StringUtils.join(*, null)                = null
   * StringUtils.join(*, [])                  = ""
   * StringUtils.join(*, [null])              = ""
   * StringUtils.join("--", ["a", "b", "c"])  = "a--b--c"
   * StringUtils.join(null, ["a", "b", "c"])  = "abc"
   * StringUtils.join("", ["a", "b", "c"])    = "abc"
   * StringUtils.join(',', [null, "", "a"])   = ",,a"
   * </pre>
   *
   * @param separator
   *     要使用的分隔符，null被视为空字符串
   * @param array
   *     要连接的值数组，可能为null
   * @return 连接后的字符串，如果输入数组为null则返回{@code null}
   * @see Joiner
   */
  public static String join(@Nullable final String separator,
      @Nullable final short[] array) {
    return new Joiner(separator)
        .addAll(array)
        .toString();
  }

  /**
   * 将提供的数组元素连接成包含提供元素列表的单个字符串。
   *
   * <p>列表前后不添加分隔符。{@code null} 分隔符与空字符串("")相同。
   * 数组中的null对象或空字符串用空字符串表示。
   * <pre>
   * StringUtils.join(*, null, *, *)                = null
   * StringUtils.join(*, [], *, *)                  = ""
   * StringUtils.join(*, [null], *, *)              = ""
   * StringUtils.join("--", ["a", "b", "c", 0, 3])  = "a--b--c"
   * StringUtils.join(null, ["a", "b", "c", 0, 2])  = "ab"
   * StringUtils.join("", ["a", "b", "c", 1, 3])    = "bc"
   * StringUtils.join(',', [null, "", "a", 0, 3])   = ",,a"
   * </pre>
   *
   * @param separator
   *     要使用的分隔符，null被视为空字符串
   * @param array
   *     要连接的值数组，可能为null
   * @param startIndex
   *     开始连接的第一个位置。如果小于零，则视为零
   * @param endIndex
   *     停止连接的索引（不包括）。如果大于{@code array}的长度，
   *     则视为{@code array}的长度
   * @return 连接后的字符串，如果输入数组为null则返回{@code null}
   * @see Joiner
   */
  public static String join(@Nullable final String separator,
      @Nullable final short[] array, final int startIndex, final int endIndex) {
    return new Joiner(separator)
        .addAll(array, startIndex, endIndex)
        .toString();
  }

  /**
   * 将提供的数组元素连接成包含提供元素列表的单个字符串。
   *
   * <p>列表前后不添加分隔符。{@code null} 分隔符与空字符串("")相同。
   * 数组中的null对象或空字符串用空字符串表示。
   * <pre>
   * StringUtils.join(*, null)                = null
   * StringUtils.join(*, [])                  = ""
   * StringUtils.join(*, [null])              = ""
   * StringUtils.join("--", ["a", "b", "c"])  = "a--b--c"
   * StringUtils.join(null, ["a", "b", "c"])  = "abc"
   * StringUtils.join("", ["a", "b", "c"])    = "abc"
   * StringUtils.join(',', [null, "", "a"])   = ",,a"
   * </pre>
   *
   * @param separator
   *     要使用的分隔符，null被视为空字符串
   * @param array
   *     要连接的值数组，可能为null
   * @return 连接后的字符串，如果输入数组为null则返回{@code null}
   * @see Joiner
   */
  public static String join(@Nullable final String separator,
      @Nullable final int[] array) {
    return new Joiner(separator)
        .addAll(array)
        .toString();
  }

  /**
   * 将提供的数组元素连接成包含提供元素列表的单个字符串。
   *
   * <p>列表前后不添加分隔符。{@code null} 分隔符与空字符串("")相同。
   * 数组中的null对象或空字符串用空字符串表示。
   * <pre>
   * StringUtils.join(*, null, *, *)                = null
   * StringUtils.join(*, [], *, *)                  = ""
   * StringUtils.join(*, [null], *, *)              = ""
   * StringUtils.join("--", ["a", "b", "c", 0, 3])  = "a--b--c"
   * StringUtils.join(null, ["a", "b", "c", 0, 2])  = "ab"
   * StringUtils.join("", ["a", "b", "c", 1, 3])    = "bc"
   * StringUtils.join(',', [null, "", "a", 0, 3])   = ",,a"
   * </pre>
   *
   * @param separator
   *     要使用的分隔符，null被视为空字符串
   * @param array
   *     要连接的值数组，可能为null
   * @param startIndex
   *     开始连接的第一个位置。如果小于零，则视为零
   * @param endIndex
   *     停止连接的索引（不包括）。如果大于{@code array}的长度，
   *     则视为{@code array}的长度
   * @return 连接后的字符串，如果输入数组为null则返回{@code null}
   * @see Joiner
   */
  public static String join(@Nullable final String separator,
      @Nullable final int[] array, final int startIndex, final int endIndex) {
    return new Joiner(separator)
        .addAll(array, startIndex, endIndex)
        .toString();
  }

  /**
   * 将提供的数组元素连接成包含提供元素列表的单个字符串。
   *
   * <p>列表前后不添加分隔符。{@code null} 分隔符与空字符串("")相同。
   * 数组中的null对象或空字符串用空字符串表示。
   * <pre>
   * StringUtils.join(*, null)                = null
   * StringUtils.join(*, [])                  = ""
   * StringUtils.join(*, [null])              = ""
   * StringUtils.join("--", ["a", "b", "c"])  = "a--b--c"
   * StringUtils.join(null, ["a", "b", "c"])  = "abc"
   * StringUtils.join("", ["a", "b", "c"])    = "abc"
   * StringUtils.join(',', [null, "", "a"])   = ",,a"
   * </pre>
   *
   * @param separator
   *     要使用的分隔符，null被视为空字符串
   * @param array
   *     要连接的值数组，可能为null
   * @return 连接后的字符串，如果输入数组为null则返回{@code null}
   * @see Joiner
   */
  public static String join(@Nullable final String separator,
      @Nullable final long[] array) {
    return new Joiner(separator)
        .addAll(array)
        .toString();
  }

  /**
   * 将提供的数组元素连接成包含提供元素列表的单个字符串。
   *
   * <p>列表前后不添加分隔符。{@code null} 分隔符与空字符串("")相同。
   * 数组中的null对象或空字符串用空字符串表示。
   * <pre>
   * StringUtils.join(*, null, *, *)                = null
   * StringUtils.join(*, [], *, *)                  = ""
   * StringUtils.join(*, [null], *, *)              = ""
   * StringUtils.join("--", ["a", "b", "c", 0, 3])  = "a--b--c"
   * StringUtils.join(null, ["a", "b", "c", 0, 2])  = "ab"
   * StringUtils.join("", ["a", "b", "c", 1, 3])    = "bc"
   * StringUtils.join(',', [null, "", "a", 0, 3])   = ",,a"
   * </pre>
   *
   * @param separator
   *     要使用的分隔符，null被视为空字符串
   * @param array
   *     要连接的值数组，可能为null
   * @param startIndex
   *     开始连接的第一个位置。如果小于零，则视为零
   * @param endIndex
   *     停止连接的索引（不包括）。如果大于{@code array}的长度，
   *     则视为{@code array}的长度
   * @return 连接后的字符串，如果输入数组为null则返回{@code null}
   * @see Joiner
   */
  public static String join(@Nullable final String separator,
      @Nullable final long[] array, final int startIndex, final int endIndex) {
    return new Joiner(separator)
        .addAll(array, startIndex, endIndex)
        .toString();
  }

  /**
   * 将提供的数组元素连接成包含提供元素列表的单个字符串。
   *
   * <p>列表前后不添加分隔符。{@code null} 分隔符与空字符串("")相同。
   * 数组中的null对象或空字符串用空字符串表示。
   * <pre>
   * StringUtils.join(*, null)                = null
   * StringUtils.join(*, [])                  = ""
   * StringUtils.join(*, [null])              = ""
   * StringUtils.join("--", ["a", "b", "c"])  = "a--b--c"
   * StringUtils.join(null, ["a", "b", "c"])  = "abc"
   * StringUtils.join("", ["a", "b", "c"])    = "abc"
   * StringUtils.join(',', [null, "", "a"])   = ",,a"
   * </pre>
   *
   * @param separator
   *     要使用的分隔符，null被视为空字符串
   * @param array
   *     要连接的值数组，可能为null
   * @return 连接后的字符串，如果输入数组为null则返回{@code null}
   * @see Joiner
   */
  public static String join(@Nullable final String separator,
      @Nullable final float[] array) {
    return new Joiner(separator)
        .addAll(array)
        .toString();
  }

  /**
   * 将提供的数组元素连接成包含提供元素列表的单个字符串。
   *
   * <p>列表前后不添加分隔符。{@code null} 分隔符与空字符串("")相同。
   * 数组中的null对象或空字符串用空字符串表示。
   * <pre>
   * StringUtils.join(*, null, *, *)                = null
   * StringUtils.join(*, [], *, *)                  = ""
   * StringUtils.join(*, [null], *, *)              = ""
   * StringUtils.join("--", ["a", "b", "c", 0, 3])  = "a--b--c"
   * StringUtils.join(null, ["a", "b", "c", 0, 2])  = "ab"
   * StringUtils.join("", ["a", "b", "c", 1, 3])    = "bc"
   * StringUtils.join(',', [null, "", "a", 0, 3])   = ",,a"
   * </pre>
   *
   * @param separator
   *     要使用的分隔符，null被视为空字符串
   * @param array
   *     要连接的值数组，可能为null
   * @param startIndex
   *     开始连接的第一个位置。如果小于零，则视为零
   * @param endIndex
   *     停止连接的索引（不包括）。如果大于{@code array}的长度，
   *     则视为{@code array}的长度
   * @return 连接后的字符串，如果输入数组为null则返回{@code null}
   * @see Joiner
   */
  public static String join(@Nullable final String separator,
      @Nullable final float[] array, final int startIndex, final int endIndex) {
    return new Joiner(separator)
        .addAll(array, startIndex, endIndex)
        .toString();
  }

  /**
   * 将提供的数组元素连接成包含提供元素列表的单个字符串。
   *
   * <p>列表前后不添加分隔符。{@code null} 分隔符与空字符串("")相同。
   * 数组中的null对象或空字符串用空字符串表示。
   * <pre>
   * StringUtils.join(*, null)                = null
   * StringUtils.join(*, [])                  = ""
   * StringUtils.join(*, [null])              = ""
   * StringUtils.join("--", ["a", "b", "c"])  = "a--b--c"
   * StringUtils.join(null, ["a", "b", "c"])  = "abc"
   * StringUtils.join("", ["a", "b", "c"])    = "abc"
   * StringUtils.join(',', [null, "", "a"])   = ",,a"
   * </pre>
   *
   * @param separator
   *     要使用的分隔符，null被视为空字符串
   * @param array
   *     要连接的值数组，可能为null
   * @return 连接后的字符串，如果输入数组为null则返回{@code null}
   * @see Joiner
   */
  public static String join(@Nullable final String separator,
      @Nullable final double[] array) {
    return new Joiner(separator)
        .addAll(array)
        .toString();
  }

  /**
   * 将提供的数组元素连接成包含提供元素列表的单个字符串。
   *
   * <p>列表前后不添加分隔符。{@code null} 分隔符与空字符串("")相同。
   * 数组中的null对象或空字符串用空字符串表示。
   * <pre>
   * StringUtils.join(*, null, *, *)                = null
   * StringUtils.join(*, [], *, *)                  = ""
   * StringUtils.join(*, [null], *, *)              = ""
   * StringUtils.join("--", ["a", "b", "c", 0, 3])  = "a--b--c"
   * StringUtils.join(null, ["a", "b", "c", 0, 2])  = "ab"
   * StringUtils.join("", ["a", "b", "c", 1, 3])    = "bc"
   * StringUtils.join(',', [null, "", "a", 0, 3])   = ",,a"
   * </pre>
   *
   * @param separator
   *     要使用的分隔符，null被视为空字符串
   * @param array
   *     要连接的值数组，可能为null
   * @param startIndex
   *     开始连接的第一个位置。如果小于零，则视为零
   * @param endIndex
   *     停止连接的索引（不包括）。如果大于{@code array}的长度，
   *     则视为{@code array}的长度
   * @return 连接后的字符串，如果输入数组为null则返回{@code null}
   * @see Joiner
   */
  public static String join(@Nullable final String separator,
      @Nullable final double[] array, final int startIndex, final int endIndex) {
    return new Joiner(separator)
        .addAll(array, startIndex, endIndex)
        .toString();
  }

  /**
   * 将提供的数组元素连接成包含提供元素列表的单个字符串。
   *
   * <p>列表前后不添加分隔符。{@code null} 分隔符与空字符串("")相同。
   * 数组中的null对象或空字符串用空字符串表示。
   * <pre>
   * StringUtils.join(*, null)                = null
   * StringUtils.join(*, [])                  = ""
   * StringUtils.join(*, [null])              = ""
   * StringUtils.join("--", ["a", "b", "c"])  = "a--b--c"
   * StringUtils.join(null, ["a", "b", "c"])  = "abc"
   * StringUtils.join("", ["a", "b", "c"])    = "abc"
   * StringUtils.join(',', [null, "", "a"])   = ",,a"
   * </pre>
   *
   * @param <T>
   *     要连接的元素类型
   * @param separator
   *     要使用的分隔符，null被视为空字符串
   * @param array
   *     要连接的值数组，可能为null
   * @return 连接后的字符串，如果输入数组为null则返回{@code null}
   * @see Joiner
   */
  public static <T> String join(@Nullable final String separator,
      @Nullable final T[] array) {
    return new Joiner(separator)
        .addAll(array)
        .toString();
  }

  /**
   * 将提供的数组元素连接成包含提供元素列表的单个字符串。
   *
   * <p>列表前后不添加分隔符。{@code null} 分隔符与空字符串("")相同。
   * 数组中的null对象或空字符串用空字符串表示。
   * <pre>
   * StringUtils.join(*, null, *, *)                = null
   * StringUtils.join(*, [], *, *)                  = ""
   * StringUtils.join(*, [null], *, *)              = ""
   * StringUtils.join("--", ["a", "b", "c", 0, 3])  = "a--b--c"
   * StringUtils.join(null, ["a", "b", "c", 0, 2])  = "ab"
   * StringUtils.join("", ["a", "b", "c", 1, 3])    = "bc"
   * StringUtils.join(',', [null, "", "a", 0, 3])   = ",,a"
   * </pre>
   *
   * @param <T>
   *     要连接的元素类型
   * @param separator
   *     要使用的分隔符，null被视为空字符串
   * @param array
   *     要连接的值数组，可能为null
   * @param startIndex
   *     开始连接的第一个索引。如果小于零，则视为零
   * @param endIndex
   *     停止连接的索引（不包括）。如果大于{@code array}的长度，
   *     则视为{@code array}的长度
   * @return 连接后的字符串，如果输入数组为null则返回{@code null}
   * @see Joiner
   */
  public static <T> String join(@Nullable final String separator,
      @Nullable final T[] array, final int startIndex, final int endIndex) {
    return new Joiner(separator)
        .addAll(array, startIndex, endIndex)
        .toString();
  }

  /**
   * 将提供的{@code Iterator}元素连接成包含提供元素的单个字符串。
   *
   * <p>列表前后不添加分隔符。{@code null} 分隔符与空字符串("")相同。
   *
   * <p>示例请参考：{@link #join(String, Object[])}。
   *
   * @param <T>
   *     要连接的元素类型
   * @param separator
   *     要使用的分隔符字符，null被视为""
   * @param iterator
   *     要连接的值{@code Iterator}，可能为null
   * @return 连接后的字符串，如果输入iterator为null则返回{@code null}
   * @see Joiner
   */
  public static <T> String join(@Nullable final String separator,
      @Nullable final Iterator<T> iterator) {
    return new Joiner(separator)
        .addAll(iterator)
        .toString();
  }

  /**
   * 将提供的{@code Iterable}元素连接成包含提供元素的单个字符串。
   *
   * <p>列表前后不添加分隔符。{@code null} 分隔符与空字符串("")相同。
   *
   * <p>示例请参考：{@link #join(String, Object[])}。
   *
   * @param <T>
   *     要连接的元素类型
   * @param separator
   *     要使用的分隔符字符，null被视为""
   * @param iterable
   *     要连接的值{@code Iterable}，可能为null
   * @return 连接后的字符串，如果输入iterator为null则返回{@code null}
   * @see Joiner
   */
  public static <T> String join(@Nullable final String separator,
      @Nullable final Iterable<T> iterable) {
    return new Joiner(separator)
        .addAll(iterable)
        .toString();
  }

  /**
   * 将提供的字符串分割为子字符串列表，由指定字符分隔。这是使用StringTokenizer的替代方案。
   *
   * <p>分隔符不包含在返回的字符串数组中。相邻的分隔符被视为一个分隔符。
   * 要对分割有更多控制，请使用StrTokenizer类。
   *
   * <p>{@code null} 输入字符串返回 {@code null}。
   *
   * <p>示例：
   * <pre>
   * StringUtils.split(null, *)         = null
   * StringUtils.split("", *)           = null
   * StringUtils.split("a.b.c", '.')    = {"a", "b", "c"}
   * StringUtils.split("a..b.c", '.')   = {"a", "b", "c"}
   * StringUtils.split("a:b:c", '.')    = {"a:b:c"}
   * StringUtils.split("a b c", ' ')    = {"a", "b", "c"}
   * </pre>
   *
   * @param str
   *     要分割的字符串。如果为null，清空结果（如果不为{@code null}）并返回结果
   * @param separator
   *     用作分隔符的字符
   * @return 存储分割子字符串的新链表。注意如果分割结果没有子字符串，返回的列表可能为空。
   *     返回的列表永远不会为{@code null}
   * @see SplitOption
   * @see Splitter
   */
  public static @Nonnull List<String> split(@Nullable final CharSequence str,
      final char separator) {
    return new Splitter()
        .byChar(separator)
        .strip(true)
        .ignoreEmpty(true)
        .split(str);
  }

  public static @Nonnull String[] splitToArray(@Nullable final CharSequence str,
      final char separator) {
    return split(str, separator).toArray(new String[0]);
  }

  /**
   * 将提供的字符串根据指定字符分割成子字符串列表。这是使用StringTokenizer的替代方案。
   *
   * <p>分隔符不包含在返回的字符串数组中。相邻的分隔符被视为一个分隔符。
   * 如需对分割进行更多控制，请使用StrTokenizer类。
   *
   * <p>如果输入字符串为{@code null}，则返回{@code null}。
   *
   * <p>示例：
   * <pre>
   * StringUtils.split(null, *, null)         = null
   * StringUtils.split("", *, null)           = null
   * StringUtils.split("a.b.c", '.', null)    = {"a", "b", "c"}
   * StringUtils.split("a..b.c", '.', null)   = {"a", "b", "c"}
   * StringUtils.split("a:b:c", '.', null)    = {"a:b:c"}
   * StringUtils.split("a b c", ' ', null)    = {"a", "b", "c"}
   * </pre>
   *
   * @param str
   *     要分割的字符串。如果为null，则清空结果（如果结果不为{@code null}）并返回结果。
   * @param separator
   *     用作分隔符的字符
   * @param options
   *     {@link SplitOption}组合的位掩码。
   * @return 存储分割后子字符串的新链表。注意如果分割结果没有子字符串，
   *     返回的列表可能为空。返回的列表永远不会为{@code null}。
   * @see SplitOption
   * @see Splitter
   */
  public static @Nonnull List<String> split(@Nullable final CharSequence str,
      final char separator, final int options) {
    return new Splitter()
        .byChar(separator)
        .strip((options & TRIM) != 0)
        .ignoreEmpty((options & IGNORE_EMPTY) != 0)
        .split(str);
  }

  /**
   * 将提供的字符串根据指定字符分割成子字符串列表。这是使用StringTokenizer的替代方案。
   *
   * <p>分隔符不包含在返回的字符串数组中。相邻的分隔符被视为一个分隔符。
   * 如需对分割进行更多控制，请使用StrTokenizer类。
   *
   * <p>如果输入字符串为{@code null}，则返回{@code null}。
   *
   * <p>示例：
   * <pre>
   * StringUtils.split(null, *, null, null)         = null
   * StringUtils.split("", *, null, null)           = null
   * StringUtils.split("a.b.c", '.', null, null)    = {"a", "b", "c"}
   * StringUtils.split("a..b.c", '.', null, null)   = {"a", "b", "c"}
   * StringUtils.split("a:b:c", '.', null, null)    = {"a:b:c"}
   * StringUtils.split("a b c", ' ', null, null)    = {"a", "b", "c"}
   * </pre>
   *
   * @param str
   *     要分割的字符串。如果为null，则清空结果（如果结果不为{@code null}）并返回结果。
   * @param separator
   *     用作分隔符的字符
   * @param options
   *     {@link SplitOption}组合的位掩码。
   * @param list
   *     用于追加分割后子字符串的字符串列表。如果为null，
   *     函数将创建一个新的字符串列表。注意如果此参数不为{@code null}，
   *     此函数将<b>不会</b>清空它。相反，分割结果将追加到此列表中。
   * @return 分割后的字符串列表。如果参数result不为{@code null}，
   *     函数将在该参数中追加分割后的子字符串并返回该参数；否则，
   *     将创建一个新的字符串列表并存储分割后的子字符串并返回。
   *     注意如果分割结果没有子字符串，返回的列表可能为空。
   *     返回的列表永远不会为{@code null}。
   * @see SplitOption
   */
  public static @Nonnull List<String> split(@Nullable final CharSequence str,
      final char separator, final int options,
      @Nullable final List<String> list) {
    return new Splitter()
        .byChar(separator)
        .strip((options & TRIM) != 0)
        .ignoreEmpty((options & IGNORE_EMPTY) != 0)
        .split(str, list);
  }

  /**
   * 将提供的字符串根据指定字符串的字符分割成子字符串列表。这是使用StringTokenizer的替代方案。
   *
   * <p>分隔符不包含在返回的字符串数组中。相邻的分隔符被视为一个分隔符。
   * 如需对分割进行更多控制，请使用StrTokenizer类。
   *
   * <p>如果输入字符串为{@code null}，则返回{@code null}。
   *
   * <p>示例：
   * <pre>
   * StringUtils.split(null, *)            = null
   * StringUtils.split("", *)              = null
   * StringUtils.split("a.b.c", ".")       = {"a", "b", "c"}
   * StringUtils.split("a..b.c:d", ".:")   = {"a", "b", "c", "d"}
   * StringUtils.split("a:b,c", ",.")      = {"a:b:c"}
   * StringUtils.split("a b c.d", " .")    = {"a", "b", "c", "d"}
   * </pre>
   *
   * @param str
   *     要分割的字符串。如果为null，则清空结果（如果结果不为{@code null}）并返回结果。
   * @param separatorChars
   *     用作分隔符的字符。如果为null或空字符串，
   *     则不分割字符串，函数将返回包含原始字符串的列表。
   * @param options
   *     {@link SplitOption}组合的位掩码。
   * @return 存储分割后子字符串的新链表。注意如果分割结果没有子字符串，
   *     返回的列表可能为空。返回的列表永远不会为{@code null}。
   * @see SplitOption
   */
  public static @Nonnull List<String> split(@Nullable final CharSequence str,
      @Nullable final CharSequence separatorChars, final int options) {
    return new Splitter()
        .byCodePointsIn(separatorChars)
        .strip((options & TRIM) != 0)
        .ignoreEmpty((options & IGNORE_EMPTY) != 0)
        .split(str);
  }

  /**
   * 将提供的字符串根据指定字符串的字符分割成子字符串列表。这是使用StringTokenizer的替代方案。
   *
   * <p>分隔符不包含在返回的字符串数组中。相邻的分隔符被视为一个分隔符。
   * 如需对分割进行更多控制，请使用StrTokenizer类。
   *
   * <p>如果输入字符串为{@code null}，则返回{@code null}。
   *
   * <p>示例：
   * <pre>
   * StringUtils.split(null, *, null, null)            = null
   * StringUtils.split("", *, null, null)              = null
   * StringUtils.split("a.b.c", ".", null, null)       = {"a", "b", "c"}
   * StringUtils.split("a..b.c:d", ".:", null, null)   = {"a", "b", "c", "d"}
   * StringUtils.split("a:b,c", ",.", null, null)      = {"a:b:c"}
   * StringUtils.split("a b c.d", " .", null, null)    = {"a", "b", "c", "d"}
   * </pre>
   *
   * @param str
   *     要分割的字符串。如果为null，则清空结果（如果结果不为{@code null}）并返回结果。
   * @param separatorChars
   *     the characters used as the delimiters. If it is null or empty string,
   *     the string is not splited and the function will return a list
   *     containing the original string.
   * @param options
   *     a bitwise mask of combination of {@link SplitOption}.
   * @param result
   *     存储分割子字符串的字符串列表。如果为null，函数将创建新的字符串列表。
   * @return 分割后的字符串列表。如果参数result不为{@code null}，
   *     函数将在该参数中存储分割的子字符串并返回该参数；否则，
   *     创建新的链表存储分割的子字符串并返回。注意返回的列表可能为空，
   *     如果分割结果没有子字符串。返回的列表永远不会为{@code null}。
   * @see SplitOption
   */
  public static @Nonnull List<String> split(@Nullable final CharSequence str,
      @Nullable final CharSequence separatorChars, final int options,
      @Nullable final List<String> result) {
    return new Splitter()
        .byCodePointsIn(separatorChars)
        .strip((options & TRIM) != 0)
        .ignoreEmpty((options & IGNORE_EMPTY) != 0)
        .split(str, result);
  }

  /**
   * 将提供的字符串分割成子字符串列表，通过指定数组的字符分隔。
   * 这是使用StringTokenizer的替代方案。
   *
   * <p>分隔符不包含在返回的字符串数组中。相邻的分隔符被视为一个分隔符。
   * 要更好地控制分割，请使用StrTokenizer类。
   *
   * <p>{@code null} 输入字符串返回 {@code null}。
   *
   * <p>示例：
   * <pre>
   * StringUtils.split(null, *)                  = null
   * StringUtils.split("", *)                    = null
   * StringUtils.split("a.b.c", ['.'])           = {"a", "b", "c"}
   * StringUtils.split("a..b.c:d", ['.', ':'])   = {"a", "b", "c", "d"}
   * StringUtils.split("a:b,c", [',', '.'])      = {"a:b:c"}
   * StringUtils.split("a b c.d", [' ', '.'])    = {"a", "b", "c", "d"}
   * </pre>
   *
   * @param str
   *     要分割的字符串。如果为null，则清除结果（如果不为{@code null}）并返回结果。
   * @param separatorChars
   *     用作分隔符的字符数组。如果为null或空字符串，则不分割字符串，
   *     函数将返回包含原始字符串的列表。
   * @param options
   *     {@link SplitOption} 组合的位掩码。
   * @return 存储分割子字符串的新链表。注意返回的列表可能为空，
   *     如果分割结果没有子字符串。返回的列表永远不会为{@code null}。
   * @see SplitOption
   */
  public static @Nonnull List<String> split(@Nullable final CharSequence str,
      @Nullable final char[] separatorChars, final int options) {
    return new Splitter()
        .byCharsIn(separatorChars)
        .strip((options & TRIM) != 0)
        .ignoreEmpty((options & IGNORE_EMPTY) != 0)
        .split(str);
  }

  /**
   * 将提供的字符串分割成子字符串列表，通过指定数组的字符分隔。
   * 这是使用StringTokenizer的替代方案。
   *
   * <p>分隔符不包含在返回的字符串数组中。相邻的分隔符被视为一个分隔符。
   * 要更好地控制分割，请使用StrTokenizer类。
   *
   * <p>{@code null} 输入字符串返回 {@code null}。
   *
   * <p>示例：
   * <pre>
   * StringUtils.split(null, *, null)                  = null
   * StringUtils.split("", *, null)                    = null
   * StringUtils.split("a.b.c", ['.'], null)           = {"a", "b", "c"}
   * StringUtils.split("a..b.c:d", ['.', ':'], null)   = {"a", "b", "c", "d"}
   * StringUtils.split("a:b,c", [',', '.'], null)      = {"a:b:c"}
   * StringUtils.split("a b c.d", [' ', '.'], null)    = {"a", "b", "c", "d"}
   * </pre>
   *
   * @param str
   *     要分割的字符串。如果为null，则清除结果（如果不为 {@code null}）并返回结果
   * @param separatorChars
   *     用作分隔符的字符数组。如果为null或空字符串，则不分割字符串，
   *     函数将返回包含原始字符串的列表
   * @param options
   *     {@link SplitOption} 组合的位掩码
   * @param result
   *     存储分割子字符串的字符串列表。如果为null，函数将创建新的字符串列表
   * @return 分割后的字符串列表。如果参数result不为 {@code null}，
   *     函数将在该参数中存储分割的子字符串并返回该参数；否则，
   *     创建新的链表存储分割的子字符串并返回。注意返回的列表可能为空，
   *     如果分割结果没有子字符串。返回的列表永远不会为 {@code null}
   * @see SplitOption
   */
  public static @Nonnull List<String> split(@Nullable final CharSequence str,
      @Nullable final char[] separatorChars, final int options,
      @Nullable final List<String> result) {
    return new Splitter()
        .byCharsIn(separatorChars)
        .strip((options & TRIM) != 0)
        .ignoreEmpty((options & IGNORE_EMPTY) != 0)
        .split(str, result);
  }

  /**
   * 将提供的字符串分割成子字符串列表，通过指定CharFilter接受的字符分隔。
   * 这是使用StringTokenizer的替代方案。
   *
   * <p>分隔符不包含在返回的字符串数组中。相邻的分隔符被视为一个分隔符。
   * 要更好地控制分割，请使用StrTokenizer类。
   *
   * <p>{@code null} 输入字符串返回 {@code null}。
   *
   * <p>示例：
   * <pre>
   * StringUtils.split(null, *)         = null
   * StringUtils.split("", *)           = null
   * StringUtils.split("a.b.c", '.')    = {"a", "b", "c"}
   * StringUtils.split("a..b.c", '.')   = {"a", "b", "c"}
   * StringUtils.split("a:b:c", '.')    = {"a:b:c"}
   * StringUtils.split("a b c", ' ')    = {"a", "b", "c"}
   * </pre>
   *
   * @param str
   *     要分割的字符串。如果为null，则清除结果（如果不为 {@code null}）并返回结果
   * @param charFilter
   *     用于过滤字符的过滤器。函数将通过此过滤器接受的所有字符分割字符串。
   *     此参数不能为null
   * @param options
   *     {@link SplitOption} 组合的位掩码
   * @return 存储分割子字符串的新链表。注意如果分割结果没有子字符串，
   *     返回的列表可能为空。返回的列表永远不会为 {@code null}
   * @see SplitOption
   */
  public static @Nonnull List<String> split(@Nullable final CharSequence str,
      final CharFilter charFilter, final int options) {
    return new Splitter()
        .byCharsSatisfy(charFilter)
        .strip((options & TRIM) != 0)
        .ignoreEmpty((options & IGNORE_EMPTY) != 0)
        .split(str);
  }

  /**
   * 将提供的字符串分割成子字符串列表，通过指定CharFilter接受的字符分隔。
   * 这是使用StringTokenizer的替代方案。
   *
   * <p>分隔符不包含在返回的字符串数组中。相邻的分隔符被视为一个分隔符。
   * 要更好地控制分割，请使用StrTokenizer类。
   *
   * <p>{@code null} 输入字符串返回 {@code null}。
   *
   * <p>示例：
   * <pre>
   * StringUtils.split(null, *, null)         = null
   * StringUtils.split("", *, null)           = null
   * StringUtils.split("a.b.c", '.', null)    = {"a", "b", "c"}
   * StringUtils.split("a..b.c", '.', null)   = {"a", "b", "c"}
   * StringUtils.split("a:b:c", '.', null)    = {"a:b:c"}
   * StringUtils.split("a b c", ' ', null)    = {"a", "b", "c"}
   * </pre>
   *
   * @param str
   *     要分割的字符串。如果为null，则清除结果（如果不为 {@code null}）并返回结果
   * @param charFilter
   *     用于过滤字符的过滤器。函数将通过此过滤器接受的所有字符分割字符串。
   *     此参数不能为null
   * @param options
   *     {@link SplitOption} 组合的位掩码
   * @param list
   *     存储分割子字符串的字符串列表。如果为null，函数将创建新的字符串列表
   * @return 分割后的字符串列表。如果参数result不为 {@code null}，
   *     函数将在该参数中存储分割的子字符串并返回该参数；否则，
   *     创建新的链表存储分割的子字符串并返回。注意返回的列表可能为空，
   *     如果分割结果没有子字符串。返回的列表永远不会为 {@code null}
   * @see SplitOption
   */
  public static @Nonnull List<String> split(@Nullable final CharSequence str,
      final CharFilter charFilter, final int options,
      @Nullable final List<String> list) {
    return new Splitter()
        .byCharsSatisfy(charFilter)
        .strip((options & TRIM) != 0)
        .ignoreEmpty((options & IGNORE_EMPTY) != 0)
        .split(str, list);
  }

  /**
   * 将提供的字符串分割成子字符串列表，通过空白字符分隔。
   * 这是使用StringTokenizer的替代方案。
   *
   * <p>分隔符不包含在返回的字符串数组中。相邻的分隔符被视为一个分隔符。
   * 要更好地控制分割，请使用StrTokenizer类。
   *
   * <p>{@code null} 输入字符串返回 {@code null}。
   *
   * <p>示例：
   * <pre>
   * StringUtils.split(null)       = null
   * StringUtils.split("")         = null
   * StringUtils.split("abc def")  = {"abc", "def"}
   * StringUtils.split("abc  def") = {"abc", "def"}
   * StringUtils.split(" abc ")    = {"abc"}
   * </pre>
   *
   * @param str
   *     要分割的字符串。如果为null，则清除结果（如果不为 {@code null}）并返回结果
   * @return 存储分割子字符串的新链表。注意如果分割结果没有子字符串，
   *     返回的列表可能为空。返回的列表永远不会为 {@code null}
   * @see Splitter
   */
  public static @Nonnull List<String> split(@Nullable final CharSequence str) {
    return new Splitter()
        .byWhitespaces()
        .split(str);
  }

  /**
   * 将提供的字符串分割成字符串数组，通过空白字符分隔。
   *
   * <p>这是 {@link #split(CharSequence)} 的数组版本。
   *
   * @param str
   *     要分割的字符串，可能为 null
   * @return 分割后的字符串数组。永远不会为 {@code null}
   * @see #split(CharSequence)
   */
  public static @Nonnull String[] splitToArray(@Nullable final CharSequence str) {
    return split(str).toArray(new String[0]);
  }

  /**
   * 将提供的字符串分割成子字符串列表，通过空白字符分隔。
   * 这是使用StringTokenizer的替代方案。
   *
   * <p>分隔符不包含在返回的字符串数组中。相邻的分隔符被视为一个分隔符。
   * 要更好地控制分割，请使用StrTokenizer类。
   *
   * <p>{@code null} 输入字符串返回 {@code null}。
   *
   * <p>示例：
   * <pre>
   * StringUtils.split(null, null)       = null
   * StringUtils.split("", null)         = null
   * StringUtils.split("abc def", null)  = {"abc", "def"}
   * StringUtils.split("abc  def", null) = {"abc", "def"}
   * StringUtils.split(" abc ", null)    = {"abc"}
   * </pre>
   *
   * @param str
   *     要分割的字符串。如果为null，则清除结果（如果不为 {@code null}）并返回结果
   * @param list
   *     存储分割子字符串的字符串列表。如果为null，函数将创建新的字符串列表
   * @return 分割后的字符串列表。如果参数result不为 {@code null}，
   *     函数将在该参数中存储分割的子字符串并返回该参数；否则，
   *     创建新的链表存储分割的子字符串并返回。注意返回的列表可能为空，
   *     如果分割结果没有子字符串。返回的列表永远不会为 {@code null}
   */
  public static @Nonnull List<String> split(@Nullable final CharSequence str,
      @Nullable final List<String> list) {
    return new Splitter()
        .byWhitespaces()
        .split(str, list);
  }

  /**
   * Splits a string by Character type as returned by {@code
   * java.lang.Character.getType(char)}. Groups of contiguous characters of the
   * same type are returned as complete tokens, with the following exception:
   *
   * <p>If {@code camelCase} is {@code true}, the character of type
   * {@code Character.UPPERCASE_LETTER}, if any, immediately preceding a token
   * of type {@code Character.LOWERCASE_LETTER} will belong to the following
   * token rather than to the preceding, if any, {@code Character.UPPERCASE_LETTER}
   * token.
   *
   * <p>Examples:
   * <pre>
   * StringUtils.splitByCharType(null, *)             = null
   * StringUtils.splitByCharType("", *)               = null
   * StringUtils.splitByCharType("ab   de fg", *)     = {"ab", "   ", "de", " ", "fg"}
   * StringUtils.splitByCharType("ab   de fg", *)     = {"ab", "   ", "de", " ", "fg"}
   * StringUtils.splitByCharType("ab:cd:ef", *)       = {"ab", ":", "cd", ":", "ef"}
   * StringUtils.splitByCharType("number5", *)        = {"number", "5"}
   * StringUtils.splitByCharType("fooBar", false)     = {"foo", "B", "ar"}
   * StringUtils.splitByCharType("fooBar", true)      = {"foo", "Bar"}
   * StringUtils.splitByCharType("foo200Bar", false)  = {"foo", "200", "B", "ar"}
   * StringUtils.splitByCharType("foo200Bar", true)   = {"foo", "200", "Bar"}
   * StringUtils.splitByCharType("ASFRules", false)   = {"ASFR", "ules"}
   * StringUtils.splitByCharType("ASFRules", true)    = {"ASF", "Rules"}
   * </pre>
   *
   * @param str
   *     the string to split, may be {@code null}
   * @param options
   *     a bitwise mask of combination of {@link SplitOption}。
   * @return 分割后的字符串列表。如果参数result不为 {@code
   *     null}，函数将在该参数中存储分割的子字符串并返回该参数；否则，
   *     创建新的字符串列表存储分割的子字符串并返回。注意返回的列表可能为空，
   *     如果分割结果没有子字符串。返回的列表永远不会为 {@code null}。
   * @see SplitOption
   * @see Splitter
   */
  public static @Nonnull List<String> splitByCharType(@Nullable final CharSequence str,
      final int options) {
    return new Splitter()
        .byCharTypes()
        .strip((options & TRIM) != 0)
        .ignoreEmpty((options & IGNORE_EMPTY) != 0)
        .camelCase((options & CAMEL_CASE) != 0)
        .split(str);
  }

  /**
   * 按照 {@code java.lang.Character.getType(char)} 返回的字符类型分割字符串。
   * 相同类型的连续字符组作为完整的标记返回，但有以下例外：
   *
   * <p>如果启用驼峰模式，紧邻 {@code Character.LOWERCASE_LETTER} 类型标记
   * 前面的 {@code Character.UPPERCASE_LETTER} 类型字符（如果有）
   * 将归属于后面的标记，而不是前面的 {@code Character.UPPERCASE_LETTER} 标记。
   *
   * <p>示例：
   * <pre>
   * StringUtils.splitByCharType(null, *, null)             = null
   * StringUtils.splitByCharType("", *, null)               = null
   * StringUtils.splitByCharType("ab   de fg", *, null)     = {"ab", "   ", "de", " ", "fg"}
   * StringUtils.splitByCharType("ab   de fg", *, null)     = {"ab", "   ", "de", " ", "fg"}
   * StringUtils.splitByCharType("ab:cd:ef", *, null)       = {"ab", ":", "cd", ":", "ef"}
   * StringUtils.splitByCharType("number5", *, null)        = {"number", "5"}
   * StringUtils.splitByCharType("fooBar", false, null)     = {"foo", "B", "ar"}
   * StringUtils.splitByCharType("fooBar", true, null)      = {"foo", "Bar"}
   * StringUtils.splitByCharType("foo200Bar", false, null)  = {"foo", "200", "B", "ar"}
   * StringUtils.splitByCharType("foo200Bar", true, null)   = {"foo", "200", "Bar"}
   * StringUtils.splitByCharType("ASFRules", false, null)   = {"ASFR", "ules"}
   * StringUtils.splitByCharType("ASFRules", true, null)    = {"ASF", "Rules"}
   * </pre>
   *
   * @param str
   *     要分割的字符串，可能为 {@code null}
   * @param options
   *     {@link SplitOption} 组合的位掩码
   * @param list
   *     存储分割子字符串的字符串列表。如果为null，函数将创建新的字符串列表
   * @return 分割后的字符串列表。如果参数result不为 {@code null}，
   *     函数将在该参数中存储分割的子字符串并返回该参数；否则，
   *     创建新的链表存储分割的子字符串并返回。注意返回的列表可能为空，
   *     如果分割结果没有子字符串。返回的列表永远不会为 {@code null}
   * @see SplitOption
   */
  public static @Nonnull List<String> splitByCharType(@Nullable final CharSequence str,
      final int options, @Nullable final List<String> list) {
    return new Splitter()
        .byCharTypes()
        .strip((options & TRIM) != 0)
        .ignoreEmpty((options & IGNORE_EMPTY) != 0)
        .camelCase((options & CAMEL_CASE) != 0)
        .split(str, list);
  }

  /**
   * 将提供的字符串分割成子字符串列表，通过指定的子字符串分隔。
   * 这是使用StringTokenizer的替代方案。
   *
   * <p>分隔符不会包含在返回的字符串数组中。
   *
   * <p>{@code null} 输入字符串返回 {@code null}。
   * <pre>
   * splitByString(null, *)                             = null
   * splitByString("", *)                               = null
   * splitByString("ab de fg", " ")                     = {"ab", "de", "fg"}
   * splitByString("ab-!-  cd-!-  -!-ef", "-!-")        = {"ab", "cd", "ef"}
   * </pre>
   *
   * @param str
   *     要分割的字符串。如果为null，则清除结果（如果不为 {@code null}）并返回结果
   * @param separator
   *     用作分隔符的字符串。如果为null或空，则字符串被分割为每个字符都成为一个子字符串
   * @return 存储分割子字符串的新链表。每个分割的子字符串都会被修剪，空字符串会被忽略。
   *     注意如果分割结果没有非空白子字符串，返回的列表可能为空。
   *     返回的列表永远不会为 {@code null}
   * @see Splitter
   */
  public static @Nonnull List<String> splitByString(@Nullable final CharSequence str,
      @Nullable final CharSequence separator) {
    return new Splitter()
        .bySubstring(separator)
        .strip(true)
        .ignoreEmpty(true)
        .split(str);
  }

  /**
   * 将提供的字符串分割成字符串数组，通过指定的子字符串分隔。
   *
   * <p>这是 {@link #splitByString(CharSequence, CharSequence)} 的数组版本。
   *
   * @param str
   *     要分割的字符串，可能为 null
   * @param separator
   *     用作分隔符的字符串，可能为 null
   * @return 分割后的字符串数组。永远不会为 {@code null}
   * @see #splitByString(CharSequence, CharSequence)
   */
  public static @Nonnull String[] splitByStringToArray(@Nullable final CharSequence str,
      @Nullable final CharSequence separator) {
    return splitByString(str, separator).toArray(new String[0]);
  }

  /**
   * 将提供的字符串分割成子字符串列表，通过指定的子字符串分隔。
   * 这是使用StringTokenizer的替代方案。
   *
   * <p>分隔符不会包含在返回的字符串数组中。
   *
   * <p>{@code null} 输入字符串返回 {@code null}。
   * <pre>
   * splitByString(null, *, *, *)                             = null
   * splitByString("", *, *, *)                               = null
   * splitByString("ab de fg", " ", *, *)                     = {"ab", "de", "fg"}
   * splitByString("ab   de fg", " ", *, false)               = {"ab", "", "", "de", "fg"}
   * splitByString("ab;;  ;de ;fg", ";", true, false)         = {"ab", "", "", "de", "fg"}
   * splitByString("ab;;  ;de ;fg", ";", false, false)        = {"ab", "", "  ", "de ", "fg"}
   * splitByString("ab-!-  cd-!-  -!-ef", "-!-", true, true)  = {"ab", "cd", "ef"}
   * </pre>
   *
   * @param str
   *     要分割的字符串。如果为null，则清除结果（如果不为 {@code null}）并返回结果
   * @param separator
   *     用作分隔符的字符串。如果为null或空，则字符串被分割为每个字符都成为一个子字符串
   * @param options
   *     {@link SplitOption} 组合的位掩码
   * @return 存储分割子字符串的新链表。注意如果分割结果没有子字符串，
   *     返回的列表可能为空。返回的列表永远不会为 {@code null}
   * @see SplitOption
   * @see Splitter
   */
  public static @Nonnull List<String> splitByString(@Nullable final CharSequence str,
      @Nullable final CharSequence separator, final int options) {
    return new Splitter()
        .bySubstring(separator)
        .strip((options & TRIM) != 0)
        .ignoreEmpty((options & IGNORE_EMPTY) != 0)
        .split(str);
  }

  /**
   * 将提供的字符串根据指定的子字符串分割成子字符串列表。这是使用StringTokenizer的替代方案。
   *
   * <p>分隔符不会包含在返回的字符串数组中。
   *
   * <p>{@code null} 输入字符串返回 {@code null}。
   * <pre>
   * splitByString(null, *, *, *, *)                  = null
   * splitByString("", *, *, *, null)                 = null
   * splitByString("ab de fg", " ", *, *, null)       = {"ab", "de", "fg"}
   * splitByString("ab   de fg", " ", *, false, null) = {"ab", "", "", "de", "fg"}
   * splitByString("ab;;  ;de ;fg", ";", true, false, null) = {"ab", "", "", "de", "fg"}
   * splitByString("ab;;  ;de ;fg", ";", false, false, null) = {"ab", "", "  ", "de ", "fg"}
   * splitByString("ab-!-  cd-!-  -!-ef", "-!-", true, true, null) = {"ab", "cd", "ef"}
   * </pre>
   *
   * @param str
   *     要分割的字符串。如果为null，清空结果（如果不为{@code null}）并返回结果
   * @param separator
   *     用作分隔符的字符串。如果为null或空，字符串被分割为每个字符成为一个子字符串
   * @param options
   *     {@link SplitOption}组合的位掩码
   * @param list
   *     存储分割子字符串的字符串列表。如果为null，函数将创建新的字符串列表
   * @return 分割后的字符串列表。如果参数result不为{@code null}，
   *     函数将在该参数中存储分割的子字符串并返回该参数；否则，
   *     创建新的链表存储分割的子字符串并返回。注意返回的列表可能为空，
   *     如果分割结果没有子字符串。返回的列表永远不会为{@code null}。
   * @see SplitOption
   */
  public static @Nonnull List<String> splitByString(@Nullable final CharSequence str,
      @Nullable final CharSequence separator, final int options,
      @Nullable final List<String> list) {
    return new Splitter()
        .bySubstring(separator)
        .strip((options & TRIM) != 0)
        .ignoreEmpty((options & IGNORE_EMPTY) != 0)
        .split(str, list);
  }

  /**
   * 将提供的字符串根据行分割成子字符串列表。
   *
   * <p>换行符可能是"\r"、"\r\n"或"\n"。
   *
   * <p>{@code null} 输入字符串返回 {@code null}。
   *
   * @param str
   *     要分割的字符串。如果为null，清空结果（如果不为{@code null}）并返回结果
   * @return 分割后的字符串列表。列表中的每个字符串都会被去除空白，
   *     空字符串将被忽略。如果分割结果没有非空白子字符串，返回的列表可能为空。
   *     返回的列表永远不会为{@code null}。
   * @see Splitter
   */
  public static @Nonnull List<String> splitLines(@Nullable final CharSequence str) {
    return new Splitter()
        .toLines()
        .strip(true)
        .ignoreEmpty(true)
        .split(str);
  }

  public static @Nonnull String[] splitLinesToArray(@Nullable final CharSequence str) {
    return splitLines(str).toArray(new String[0]);
  }

  /**
   * 将提供的字符串根据行分割成子字符串列表。
   *
   * <p>换行符可能是"\r"、"\r\n"或"\n"。
   *
   * <p>{@code null} 输入字符串返回 {@code null}。
   *
   * @param str
   *     要分割的字符串。如果为null，清空结果（如果不为{@code null}）并返回结果
   * @param trim
   *     是否去除分割子字符串的空白
   * @param ignoreEmpty
   *     是否在返回的列表中忽略空子字符串
   * @return 分割后的字符串列表。如果分割结果没有子字符串，返回的列表可能为空。
   *     返回的列表永远不会为{@code null}。
   */
  public static @Nonnull List<String> splitLines(@Nullable final CharSequence str,
      final boolean trim, final boolean ignoreEmpty) {
    return new Splitter()
        .toLines()
        .strip(trim)
        .ignoreEmpty(ignoreEmpty)
        .split(str);
  }

  /**
   * 用另一个字符串覆盖字符串的一部分。
   *
   * <p>{@code null} 字符串输入返回 {@code null}。负索引被视为零。
   * 大于字符串长度的索引被视为字符串长度。起始索引总是两个索引中较小的那个。
   * <pre>
   * StringUtils.overlay(null, *, *, *)            = null
   * StringUtils.overlay("", "abc", 0, 0)          = "abc"
   * StringUtils.overlay("abcdef", null, 2, 4)     = "abef"
   * StringUtils.overlay("abcdef", "", 2, 4)       = "abef"
   * StringUtils.overlay("abcdef", "", 4, 2)       = "abef"
   * StringUtils.overlay("abcdef", "zzzz", 2, 4)   = "abzzzzef"
   * StringUtils.overlay("abcdef", "zzzz", 4, 2)   = "abzzzzef"
   * StringUtils.overlay("abcdef", "zzzz", -1, 4)  = "zzzzef"
   * StringUtils.overlay("abcdef", "zzzz", 2, 8)   = "abzzzz"
   * StringUtils.overlay("abcdef", "zzzz", -2, -3) = "zzzzabcdef"
   * StringUtils.overlay("abcdef", "zzzz", 8, 10)  = "abcdefzzzz"
   * </pre>
   *
   * @param str
   *     要进行覆盖操作的字符串，可能为 null
   * @param overlay
   *     用于覆盖的字符串，可能为 null
   * @param startIndex
   *     开始覆盖的位置
   * @param endIndex
   *     停止覆盖的位置（不包含）
   * @return 覆盖后的字符串，如果输入字符串为 null 则返回 {@code null}
   */
  public static String overlay(@Nullable final String str,
      @Nullable final String overlay, final int startIndex, final int endIndex) {
    if (str == null) {
      return null;
    }
    final String theOverlay = defaultIfNull(overlay, EMPTY);
    final int len = str.length();
    int start = startIndex;
    if (start < 0) {
      start = 0;
    }
    if (start > len) {
      start = len;
    }
    int end = endIndex;
    if (end < 0) {
      end = 0;
    }
    if (end > len) {
      end = len;
    }
    if (start > end) {
      final int temp = start;
      start = end;
      end = temp;
    }
    final int n = ((len + start) - end) + theOverlay.length() + 1;
    final StringBuilder builder = new StringBuilder();
    builder.ensureCapacity(n);
    builder.append(str, 0, start)
        .append(theOverlay)
        .append(str, end, str.length());
    return builder.toString();
  }

  /**
   * 重复字符串 {@code repeat} 次以形成新字符串。
   * <pre>
   * StringUtils.repeat(null, 2) = null
   * StringUtils.repeat("", 0)   = ""
   * StringUtils.repeat("", 2)   = ""
   * StringUtils.repeat("a", 3)  = "aaa"
   * StringUtils.repeat("ab", 2) = "abab"
   * StringUtils.repeat("a", -2) = ""
   * </pre>
   *
   * @param str
   *     要重复的字符串，可能为 null
   * @param repeat
   *     重复 str 的次数，负数视为零
   * @return 由原始字符串重复组成的新字符串，如果输入字符串为 null 则返回 {@code null}
   */
  public static String repeat(@Nullable final String str, final int repeat) {
    if (str == null) {
      return null;
    }
    if (repeat <= 0) {
      return EMPTY;
    }
    final int inputLength = str.length();
    if ((repeat == 1) || (inputLength == 0)) {
      return str;
    }
    if ((inputLength == 1) && (repeat <= PAD_LIMIT)) {
      return padding(repeat, str.charAt(0));
    }

    final int outputLength = inputLength * repeat;
    switch (inputLength) {
      case 1:
        final char ch = str.charAt(0);
        final char[] output1 = new char[outputLength];
        for (int i = repeat - 1; i >= 0; --i) {
          output1[i] = ch;
        }
        return new String(output1);
      case 2:
        final char ch0 = str.charAt(0);
        final char ch1 = str.charAt(1);
        final char[] output2 = new char[outputLength];
        for (int i = (repeat * 2) - 2; i >= 0; i -= 2) {
          output2[i] = ch0;
          output2[i + 1] = ch1;
        }
        return new String(output2);
      default:
        final StringBuilder builder = new StringBuilder();
        builder.ensureCapacity(outputLength);
        for (int i = 0; i < repeat; i++) {
          builder.append(str);
        }
        return builder.toString();
    }
  }

  /**
   * 使用指定的分隔符重复到给定长度返回填充。
   * <pre>
   * StringUtils.padding(0, 'e')  = ""
   * StringUtils.padding(3, 'e')  = "eee"
   * StringUtils.padding(-2, 'e') = IndexOutOfBoundsException
   * </pre>
   *
   * <p>注意：此方法不支持使用 <a
   * href="http://www.unicode.org/glossary/#supplementary_character">Unicode
   * 补充字符</a> 进行填充，因为它们需要一对 {@code char} 来表示。如果您需要支持应用程序的完整 I18N，
   * 请考虑使用 {@link #repeat(String, int)} 代替。
   *
   * @param repeat
   *     重复分隔符的次数
   * @param padChar
   *     要重复的字符
   * @return 带有重复字符的字符串
   * @throws IndexOutOfBoundsException
   *     如果 {@code repeat &lt; 0}
   * @see #repeat(String, int)
   */
  private static String padding(final int repeat, final char padChar)
      throws IndexOutOfBoundsException {
    if (repeat < 0) {
      throw new IndexOutOfBoundsException("Cannot pad a negative amount: "
          + repeat);
    }
    final char[] buffer = new char[repeat];
    Arrays.fill(buffer, padChar);
    return new String(buffer);
  }

  /**
   * 用指定字符右填充字符串。
   *
   * <p>字符串被填充到 {@code size} 的大小。
   * <pre>
   * StringUtils.rightPad(null, *, *)     = null
   * StringUtils.rightPad("", 3, 'z')     = "zzz"
   * StringUtils.rightPad("bat", 3, 'z')  = "bat"
   * StringUtils.rightPad("bat", 5, 'z')  = "batzz"
   * StringUtils.rightPad("bat", 1, 'z')  = "bat"
   * StringUtils.rightPad("bat", -1, 'z') = "bat"
   * </pre>
   *
   * @param str
   *     要填充的字符串，可能为 null
   * @param size
   *     要填充到的大小
   * @param padChar
   *     用于填充的字符
   * @return 右填充的字符串，如果不需要填充则返回原始字符串，如果输入字符串为 null 则返回 {@code null}
   */
  public static String rightPad(@Nullable final String str, final int size,
      final char padChar) {
    if (str == null) {
      return null;
    }
    final int pads = size - str.length();
    if (pads <= 0) {
      return str; // returns original String when possible
    }
    if (pads > PAD_LIMIT) {
      return rightPad(str, size, String.valueOf(padChar));
    }
    return str.concat(padding(pads, padChar));
  }

  /**
   * 用指定字符串右填充字符串。
   *
   * <p>字符串被填充到 {@code size} 的大小。
   * <pre>
   * StringUtils.rightPad(null, *, *, *)      = null
   * StringUtils.rightPad("", 3, "z", *)      = "zzz"
   * StringUtils.rightPad("bat", 3, "yz", *)  = "bat"
   * StringUtils.rightPad("bat", 5, "yz", *)  = "batyz"
   * StringUtils.rightPad("bat", 8, "yz", *)  = "batyzyzy"
   * StringUtils.rightPad("bat", 1, "yz", *)  = "bat"
   * StringUtils.rightPad("bat", -1, "yz", *) = "bat"
   * StringUtils.rightPad("bat", 5, null, *)  = "bat  "
   * StringUtils.rightPad("bat", 5, "", *)    = "bat  "
   * </pre>
   *
   * @param str
   *     要填充的字符串，可能为 null
   * @param size
   *     要填充到的大小
   * @param padStr
   *     用于填充的字符串，null 或空字符串视为单个空格
   * @return 右填充的字符串，如果不需要填充则返回原始字符串，如果输入字符串为 null 则返回 {@code null}
   */
  public static String rightPad(@Nullable final String str, final int size,
      @Nullable final String padStr) {
    if (str == null) {
      return null;
    }
    final String thePadStr;
    if ((padStr == null) || (padStr.length() == 0)) {
      thePadStr = SPACE;
    } else {
      thePadStr = padStr;
    }
    final int padLen = thePadStr.length();
    final int strLen = str.length();
    final int pads = size - strLen;
    if (pads <= 0) {
      return str; // returns original String when possible
    }
    if ((padLen == 1) && (pads <= PAD_LIMIT)) {
      return rightPad(str, size, thePadStr.charAt(0));
    }

    if (pads == padLen) {
      return str.concat(thePadStr);
    } else if (pads < padLen) {
      return str.concat(thePadStr.substring(0, pads));
    } else {
      final char[] padding = new char[pads];
      final char[] padChars = thePadStr.toCharArray();
      for (int i = 0; i < pads; i++) {
        padding[i] = padChars[i % padLen];
      }
      return str.concat(new String(padding));
    }
  }

  /**
   * 用指定字符左填充字符串。
   *
   * <p>填充到 {@code size} 的大小。
   * <pre>
   * StringUtils.leftPad(null, *, *)     = null
   * StringUtils.leftPad("", 3, 'z')     = "zzz"
   * StringUtils.leftPad("bat", 3, 'z')  = "bat"
   * StringUtils.leftPad("bat", 5, 'z')  = "zzbat"
   * StringUtils.leftPad("bat", 1, 'z')  = "bat"
   * StringUtils.leftPad("bat", -1, 'z') = "bat"
   * </pre>
   *
   * @param str
   *     要填充的字符串，可能为 null
   * @param size
   *     要填充到的大小
   * @param padChar
   *     用于填充的字符
   * @return 左填充的字符串，如果不需要填充则返回原始字符串，如果输入字符串为 null 则返回 {@code null}
   */
  public static String leftPad(@Nullable final String str, final int size,
      final char padChar) {
    if (str == null) {
      return null;
    }
    final int pads = size - str.length();
    if (pads <= 0) {
      return str; // returns original String when possible
    }
    if (pads > PAD_LIMIT) {
      return leftPad(str, size, String.valueOf(padChar));
    }
    return padding(pads, padChar).concat(str);
  }

  /**
   * 用指定字符串左填充字符串。
   *
   * <p>填充到 {@code size} 的大小。
   * <pre>
   * StringUtils.leftPad(null, *, *)      = null
   * StringUtils.leftPad("", 3, "z")      = "zzz"
   * StringUtils.leftPad("bat", 3, "yz")  = "bat"
   * StringUtils.leftPad("bat", 5, "yz")  = "yzbat"
   * StringUtils.leftPad("bat", 8, "yz")  = "yzyzybat"
   * StringUtils.leftPad("bat", 1, "yz")  = "bat"
   * StringUtils.leftPad("bat", -1, "yz") = "bat"
   * StringUtils.leftPad("bat", 5, null)  = "  bat"
   * StringUtils.leftPad("bat", 5, "")    = "  bat"
   * </pre>
   *
   * @param str
   *     要填充的字符串，可能为 null
   * @param size
   *     要填充到的大小
   * @param padStr
   *     用于填充的字符串，null 或空字符串视为单个空格
   * @return 左填充的字符串，如果不需要填充则返回原始字符串，如果输入字符串为 null 则返回 {@code null}
   */
  public static String leftPad(@Nullable final String str, final int size,
      @Nullable final String padStr) {
    if (str == null) {
      return null;
    }
    final String thePadStr;
    if ((padStr == null) || (padStr.length() == 0)) {
      thePadStr = SPACE;
    } else {
      thePadStr = padStr;
    }
    final int padLen = thePadStr.length();
    final int strLen = str.length();
    final int pads = size - strLen;
    if (pads <= 0) {
      return str; // returns original String when possible
    }
    if ((padLen == 1) && (pads <= PAD_LIMIT)) {
      return leftPad(str, size, thePadStr.charAt(0));
    }
    if (pads == padLen) {
      return thePadStr.concat(str);
    } else if (pads < padLen) {
      return thePadStr.substring(0, pads).concat(str);
    } else {
      final char[] padding = new char[pads];
      final char[] padChars = thePadStr.toCharArray();
      for (int i = 0; i < pads; i++) {
        padding[i] = padChars[i % padLen];
      }
      return new String(padding).concat(str);
    }
  }

  /**
   * 在大小为 {@code size} 的更大字符串中居中字符串。使用提供的字符作为填充字符串的值。
   *
   * <p>如果大小小于字符串长度，则返回字符串。{@code null} 字符串返回 {@code null}。
   * 负大小视为零。
   * <pre>
   * StringUtils.center(null, *, *)     = null
   * StringUtils.center("", 4, ' ')     = "    "
   * StringUtils.center("ab", -1, ' ')  = "ab"
   * StringUtils.center("ab", 4, ' ')   = " ab"
   * StringUtils.center("abcd", 2, ' ') = "abcd"
   * StringUtils.center("a", 4, ' ')    = " a  "
   * StringUtils.center("a", 4, 'y')    = "yayy"
   * </pre>
   *
   * @param str
   *     要居中的字符串，可能为 null
   * @param size
   *     新字符串的整数大小，负数视为零
   * @param padChar
   *     用于填充新字符串的字符
   * @return 居中的字符串，如果输入字符串为 null 则返回 {@code null}
   */
  public static String center(@Nullable final String str, final int size,
      final char padChar) {
    if ((str == null) || (size <= 0)) {
      return str;
    }
    final int len = str.length();
    final int pads = size - len;
    if (pads <= 0) {
      return str;
    }
    String result = str;
    result = leftPad(result, len + (pads / 2), padChar);
    result = rightPad(result, size, padChar);
    return result;
  }

  /**
   * 在大小为 {@code size} 的更大字符串中居中字符串。使用提供的字符串作为填充字符串的值。
   *
   * <p>如果大小小于字符串长度，则返回字符串。{@code null} 字符串返回 {@code null}。
   * 负大小视为零。
   * <pre>
   * StringUtils.center(null, *, *)     = null
   * StringUtils.center("", 4, " ")     = "    "
   * StringUtils.center("ab", -1, " ")  = "ab"
   * StringUtils.center("ab", 4, " ")   = " ab"
   * StringUtils.center("abcd", 2, " ") = "abcd"
   * StringUtils.center("a", 4, " ")    = " a  "
   * StringUtils.center("a", 4, "yz")   = "yayz"
   * StringUtils.center("abc", 7, null) = "  abc  "
   * StringUtils.center("abc", 7, "")   = "  abc  "
   * </pre>
   *
   * @param str
   *     要居中的字符串，可能为 null
   * @param size
   *     新字符串的整数大小，负数视为零
   * @param padding
   *     用于填充新字符串的字符串，不能为 null 或空
   * @return 居中的字符串，如果输入字符串为 null 则返回 {@code null}
   * @throws IllegalArgumentException
   *     如果 padStr 为 {@code null} 或空
   */
  public static String center(@Nullable final String str, final int size,
      @Nullable final String padding) {
    if ((str == null) || (size <= 0)) {
      return str;
    }
    final String padStr;
    if ((padding == null) || (padding.length() == 0)) {
      padStr = " ";
    } else {
      padStr = padding;
    }
    final int strLen = str.length();
    final int pads = size - strLen;
    if (pads <= 0) {
      return str;
    }
    String result = leftPad(str, strLen + (pads / 2), padStr);
    result = rightPad(result, size, padStr);
    return result;
  }

  /**
   * 根据 {@link String#toUpperCase()} 将字符串转换为大写。
   *
   * <p>{@code null} 输入字符串返回 {@code null}。
   * <pre>
   * StringUtils.upperCase(null)  = null
   * StringUtils.upperCase("")    = ""
   * StringUtils.upperCase("aBc") = "ABC"
   * </pre>
   *
   * <p>FIXME: 该实现对所有语言都不完美。
   *
   * @param str
   *     要转换为大写的字符串，可能为 null
   * @return 大写字符串，如果输入字符串为 null 则返回 {@code null}
   */
  public static String toUpperCase(@Nullable final String str) {
    if (str == null) {
      return null;
    }
    return str.toUpperCase();
  }

  /**
   * 根据 {@link String#toLowerCase()} 将字符串转换为小写。
   *
   * <p>{@code null} 输入字符串返回 {@code null}。
   * <pre>
   * StringUtils.lowerCase(null)  = null
   * StringUtils.lowerCase("")    = ""
   * StringUtils.lowerCase("aBc") = "abc"
   * </pre>
   *
   * <p>FIXME: 该实现对所有语言都不完美。需要更完美的实现。
   *
   * @param str
   *     要转换为小写的字符串，可能为 null
   * @return 小写字符串，如果输入字符串为 null 则返回 {@code null}
   */
  public static String toLowerCase(@Nullable final String str) {
    if (str == null) {
      return null;
    }
    return str.toLowerCase();
  }

  /**
   * 根据 {@link Character#toTitleCase(char)} 将字符串的首字母转换为标题大小写。其他字母不变。
   *
   * <p>{@code null} 输入字符串返回 {@code null}。
   * <pre>
   * StringUtils.capitalize(null)  = null
   * StringUtils.capitalize("")    = ""
   * StringUtils.capitalize("cat") = "Cat"
   * StringUtils.capitalize("cAt") = "CAt"
   * </pre>
   *
   * <p>FIXME: 该实现对所有语言都不完美。需要更完善的实现。
   *
   * @param str
   *     要首字母大写的字符串，可能为 null
   * @return 首字母大写的字符串，如果输入字符串为 null 则返回 {@code null}
   * @see #uncapitalize(String)
   */
  public static String capitalize(@Nullable final String str) {
    final int strLen;
    if ((str == null) || ((strLen = str.length()) == 0)) {
      return str;
    }
    final char ch = Character.toTitleCase(str.charAt(0));
    if (strLen == 1) {
      return CharUtils.toString(ch);
    }
    final StringBuilder builder = new StringBuilder();
    builder.ensureCapacity(strLen);
    builder.append(ch).append(str, 1, strLen); // recall that strLen > 1
    return builder.toString();
  }

  /**
   * 根据 {@link Character#toLowerCase(char)} 将字符串的首字母转换为小写。其他字母不变。
   *
   * <p>{@code null} 输入字符串返回 {@code null}。
   * <pre>
   * StringUtils.uncapitalize(null)  = null
   * StringUtils.uncapitalize("")    = ""
   * StringUtils.uncapitalize("Cat") = "cat"
   * StringUtils.uncapitalize("CAT") = "cAT"
   * </pre>
   *
   * <p>FIXME: 该实现对所有语言都不完美。
   *
   * @param str
   *     要首字母小写的字符串，可能为 null
   * @return 首字母小写的字符串，如果输入字符串为 null 则返回 {@code null}
   * @see #capitalize(String)
   */
  public static String uncapitalize(@Nullable final String str) {
    final int strLen;
    if ((str == null) || ((strLen = str.length()) == 0)) {
      return str;
    }
    final char ch = Character.toLowerCase(str.charAt(0));
    if (strLen == 1) {
      return CharUtils.toString(ch);
    }
    final StringBuilder builder = new StringBuilder();
    builder.ensureCapacity(strLen);
    builder.append(ch).append(str, 1, strLen); // recall that strLen > 1
    return builder.toString();
  }

  /**
   * 交换字符串的大小写，将大写和标题大小写转换为小写，将小写转换为大写。
   * <ul>
   * <li>大写字符转换为小写</li>
   * <li>标题大小写字符转换为小写</li>
   * <li>小写字符转换为大写</li>
   * </ul>
   * {@code null} 输入字符串返回 {@code null}。
   * <pre>
   * StringUtils.swapCase(null)                 = null
   * StringUtils.swapCase("")                   = ""
   * StringUtils.swapCase("The dog has a BONE") = "tHE DOG HAS A bone"
   * </pre>
   *
   * @param str
   *     要交换大小写的字符串，可能为 null
   * @return 更改后的字符串，如果输入字符串为 null 则返回 {@code null}
   */
  public static String swapCase(@Nullable final String str) {
    final int strLen;
    if ((str == null) || ((strLen = str.length()) == 0)) {
      return str;
    }
    final StringBuilder builder = new StringBuilder();
    builder.ensureCapacity(strLen);
    for (int i = 0; i < strLen; i++) {
      char ch = str.charAt(i);
      if (Character.isUpperCase(ch)) {
        ch = Character.toLowerCase(ch);
      } else if (Character.isTitleCase(ch)) {
        ch = Character.toLowerCase(ch);
      } else if (Character.isLowerCase(ch)) {
        ch = Character.toUpperCase(ch);
      }
      builder.append(ch);
    }
    return builder.toString();
  }

  /**
   * 根据 {@link StringBuilder#reverse()} 反转字符串。
   *
   * <p>{@code null} 字符串返回 {@code null}。
   * <pre>
   * StringUtils.reverse(null)  = null
   * StringUtils.reverse("")    = ""
   * StringUtils.reverse("bat") = "tab"
   * </pre>
   *
   * @param str
   *     要反转的字符串，可能为 null
   * @return 反转后的字符串，如果输入字符串为 null 则返回 {@code null}
   */
  public static String reverse(@Nullable final String str) {
    if (str == null) {
      return null;
    }
    final int strLen;
    if ((strLen = str.length()) < 2) {
      return str;
    }
    final StringBuilder builder = new StringBuilder();
    builder.ensureCapacity(strLen);
    return builder.append(str).reverse().toString();
  }

  /**
   * 使用省略号缩写字符串。这将把 "Now is the time for all good men" 转换为 "...is the time for..."
   *
   * <p>此函数允许您指定"左边缘"偏移量。请注意，此左边缘不一定是结果中最左边的字符，
   * 或省略号后的第一个字符，但它会出现在结果中的某个位置。
   *
   * <p>具体来说：
   * <ul>
   * <li>如果 {@code str} 的长度小于 {@code maxWidth} 个字符，则返回它。</li>
   * <li>否则将其缩写为 {@code (substring(str, 0, max-3) + "...")}。</li>
   * <li>如果 {@code maxWidth} 小于 {@code 4}，则抛出 {@code IllegalArgumentException}。</li>
   * <li>在任何情况下都不会返回长度大于 {@code maxWidth} 的字符串。</li>
   * </ul>
   * 在任何情况下都不会返回长度大于 {@code maxWidth} 的字符串。
   * <pre>
   * StringUtils.abbreviate(null, *, *)                = null
   * StringUtils.abbreviate("", 0, 4)                  = ""
   * StringUtils.abbreviate("abcdefghijklmno", -1, 10) = "abcdefg..."
   * StringUtils.abbreviate("abcdefghijklmno", 0, 10)  = "abcdefg..."
   * StringUtils.abbreviate("abcdefghijklmno", 1, 10)  = "abcdefg..."
   * StringUtils.abbreviate("abcdefghijklmno", 4, 10)  = "abcdefg..."
   * StringUtils.abbreviate("abcdefghijklmno", 5, 10)  = "...fghi..."
   * StringUtils.abbreviate("abcdefghijklmno", 6, 10)  = "...ghij..."
   * StringUtils.abbreviate("abcdefghijklmno", 8, 10)  = "...ijklmno"
   * StringUtils.abbreviate("abcdefghijklmno", 10, 10) = "...ijklmno"
   * StringUtils.abbreviate("abcdefghijklmno", 12, 10) = "...ijklmno"
   * StringUtils.abbreviate("abcdefghij", 0, 3)        = IllegalArgumentException
   * StringUtils.abbreviate("abcdefghij", 5, 6)        = IllegalArgumentException
   * </pre>
   *
   * <p>FIXME: 此函数设计得很糟糕。
   *
   * @param str
   *     要检查的字符串，可能为 null
   * @param offset
   *     源字符串的左边缘
   * @param maxWidth
   *     结果字符串的最大长度，必须至少为 4
   * @return 缩写的字符串，如果输入字符串为 null 则返回 {@code null}
   * @throws IllegalArgumentException
   *     如果宽度太小
   */
  public static String abbreviate(@Nullable final String str, final int offset,
      final int maxWidth) {
    if (str == null) {
      return null;
    }
    if (maxWidth < MIN_ABBREV_WIDTH) {
      throw new IllegalArgumentException("Minimum abbreviation width is "
          + MIN_ABBREV_WIDTH);
    }
    if (str.length() <= maxWidth) {
      return str;
    }
    int off = offset;
    if (off > str.length()) {
      off = str.length();
    }
    if ((str.length() - off) < (maxWidth - ELLIPSES_LENGTH)) {
      off = str.length() - (maxWidth - ELLIPSES_LENGTH);
    }
    if (off <= MIN_ABBREV_WIDTH) {
      return str.substring(0, maxWidth - ELLIPSES_LENGTH) + ELLIPSES;
    }
    if (maxWidth < MIN_ABBREV_WIDTH_WITH_OFFSET) {
      throw new IllegalArgumentException("Minimum abbreviation width with "
          + "offset is " + MIN_ABBREV_WIDTH_WITH_OFFSET);
    }
    final int newMaxWidth = (maxWidth - ELLIPSES_LENGTH);
    if ((off + newMaxWidth) < str.length()) {
      return ELLIPSES + abbreviate(str.substring(off), 0, newMaxWidth);
    } else {
      return ELLIPSES + str.substring(str.length() - newMaxWidth);
    }
  }

  private static final int ELLIPSES_LENGTH = ELLIPSES.length();

  private static final int MIN_ABBREV_WIDTH = ELLIPSES_LENGTH + 1;

  private static final int MIN_ABBREV_WIDTH_WITH_OFFSET = ELLIPSES_LENGTH + MIN_ABBREV_WIDTH;

  /**
   * 截断字符串以获取指定长度的前缀字符串。
   *
   * <p>此函数类似于 String.substring(0, length) 函数，
   * 但该函数确保结果字符串始终是有效的 Unicode 字符串，
   * 即结果字符串末尾没有不完整的代码单元序列。
   *
   * @param str
   *     要截断的字符串，可能为{@code null}
   * @param length
   *     结果字符串的长度
   * @return str的前缀，短于length，由有效的Unicode字符串组成。
   *     null字符串将返回{@code null}
   * @throws IllegalArgumentException
   *     如果<code>length &lt; 0</code>
   */
  public static String truncateUtf16(@Nullable final String str,
      final int length) {
    if (str == null) {
      return null;
    }
    if (length <= 0) {
      throw new IllegalArgumentException();
    }
    if (str.length() <= length) {
      return str;
    } else {
      int len = length;
      final char ch = str.charAt(len - 1);
      if (Unicode.isHighSurrogate(ch)) {
        --len;
      }
      return str.substring(0, len);
    }
  }

  /**
   * 截断字符串以获取 UTF-8 编码中指定长度的前缀字符串。
   *
   * @param str
   *     要截断的字符串。如果为 {@code null}，则返回 {@code null}。
   * @param length
   *     结果的 UTF-8 编码中的字节长度。
   * @return str 的前缀，它由有效的 Unicode 字符串组成，并且在 UTF-8 编码时短于 length。
   */
  public static String truncateUtf8(@Nullable final String str,
      final int length) {
    if (str == null) {
      return null;
    }
    byte[] bytes = null;
    bytes = str.getBytes(UTF_8);
    if (bytes.length <= length) {
      return str;
    }
    final ByteBuffer in = ByteBuffer.wrap(bytes, 0, length);
    final CharsetDecoder decoder = UTF_8.newDecoder();
    decoder.onMalformedInput(CodingErrorAction.IGNORE);
    decoder.onUnmappableCharacter(CodingErrorAction.IGNORE);
    CharBuffer out = null;
    try {
      out = decoder.decode(in);
    } catch (final CharacterCodingException e) {
      // should not throw
      throw new RuntimeException(e);
    }
    return new String(out.array(), 0, out.limit());
  }

  public static void toCharArrayString(final String str,
      final StringBuilder builder) {
    final int n = str.length();
    builder.append('[');
    for (int i = 0; i < n; ++i) {
      final char ch = str.charAt(i);
      if (i > 0) {
        builder.append(',');
      }
      CharUtils.toUnicodeHexString(ch, builder);
    }
    builder.append(']');
  }

  public static String toCharArrayString(final String str) {
    final StringBuilder builder = new StringBuilder();
    toCharArrayString(str, builder);
    return builder.toString();
  }

  /**
   * 规范化字符串中的空格。
   *
   * <p>此函数将删除重复的换行符和重复的空格，以及行开头或结尾的空格。
   *
   * @param str
   *     要压缩的字符串。
   * @param lineFolding
   *     如果为 true，多行文本将被折叠为一行，即换行符被空格替换；否则，保留换行符。
   * @return 压缩后的字符串。
   */
  public static String normalizeSpace(final String str,
      final boolean lineFolding) {
    final StringBuilder builder = new StringBuilder();
    normalizeSpace(str, lineFolding, false, builder);
    return builder.toString();
  }

  /**
   * 规范化字符串中的空格。
   *
   * <p>此函数将删除重复的换行符和重复的空格，以及行开头或结尾的空格。
   *
   * @param str
   *     要压缩的字符串。
   * @param lineFolding
   *     如果为 true，多行文本将被折叠为一行，即换行符被空格替换；否则，保留换行符。
   * @param keepTrailingSpace
   *     true 表示保留尾随（最后）空格字符；false 表示删除尾随空格。
   * @param builder
   *     用于追加压缩字符串的 {@link StringBuilder}。
   * @return 追加到构建器的字符数。
   */
  public static int normalizeSpace(@Nullable final String str,
      final boolean lineFolding, final boolean keepTrailingSpace,
      final StringBuilder builder) {
    if (str == null) {
      return 0;
    }
    final int len = str.length();
    if (len == 0) {
      return 0;
    }
    int last = 0;
    int builderLen = builder.length();
    if (builderLen > 0) {
      final int lastCp = builder.codePointBefore(builderLen);
      last = CharUtils.getVisibility(lastCp);
    }
    int appended = 0;
    final CharSequenceCodePointIterator iter = new CharSequenceCodePointIterator(str);
    for (; !iter.atEnd(); iter.forward()) {
      final int codePoint = iter.current();
      int visibility = CharUtils.getVisibility(codePoint);
      // if line should be folded, the line break character is treated as a
      // inline blank
      // character
      if (lineFolding && (visibility == CharUtils.VISIBILITY_LINE_BREAK)) {
        visibility = CharUtils.VISIBILITY_INLINE_BLANK;
      }
      switch (visibility) {
        case CharUtils.VISIBILITY_GRAPH:
          // append the graphic code point
          builder.appendCodePoint(codePoint);
          ++appended;
          last = CharUtils.VISIBILITY_GRAPH;
          break;
        case CharUtils.VISIBILITY_INLINE_BLANK:
          // the current code point is a blank character except for line breaks.
          if (last == CharUtils.VISIBILITY_GRAPH) {
            // append a space only if the last code point appended is a graphic
            builder.append(' ');
            ++appended;
            last = CharUtils.VISIBILITY_INLINE_BLANK;
          }
          break;
        case CharUtils.VISIBILITY_LINE_BREAK:
          // the current code point is a line breaks.
          if (last == CharUtils.VISIBILITY_GRAPH) {
            // append a line break if the last code point appended is a graphic
            builder.append('\n');
            ++appended;
            last = CharUtils.VISIBILITY_LINE_BREAK;
          } else if (last == CharUtils.VISIBILITY_INLINE_BLANK) {
            if (appended > 0) {
              // if the last appended character is a space, change it to a line
              // break
              builderLen = builder.length();
              builder.setCharAt(builderLen - 1, '\n');
            } else { // otherwise, this is the first time to append, simply
              // append a line break
              builder.append('\n');
              ++appended;
            }
            last = CharUtils.VISIBILITY_LINE_BREAK;
          }
          break;
        default:
          throw new IllegalArgumentException("Unsupported visibility: " + visibility);
      }
    }
    // eat the last appended space or line break
    if ((!keepTrailingSpace) && (appended > 0)
        && ((last & CharUtils.VISIBILITY_BLANK) != 0)) {
      final int n = builder.length();
      assert (n > 0);
      builder.setLength(n - 1);
      --appended;
    }
    return appended;
  }

  /**
   * Quotes a string with the given quotation marks, escaping the characters in
   * the string if necessary.
   *
   * @param str
   *     the string to be quoted.
   * @param escapeChar
   *     the character used to escape itself and other characters.
   * @param leftQuote
   *     the left quotation mark.
   * @param rightQuote
   *     右引号
   * @return 引用的字符串，其原始内容已转义
   */
  public static String quote(final String str, final char escapeChar,
      final char leftQuote, final char rightQuote) {
    final StringBuilder builder = new StringBuilder();
    quote(str, escapeChar, leftQuote, rightQuote, builder);
    return builder.toString();
  }

  /**
   * 用给定的引号引用字符串，必要时转义字符串中的字符。
   *
   * @param str
   *     要引用的字符串。
   * @param escapeChar
   *     用于转义自身和其他字符的字符。
   * @param leftQuote
   *     左引号。
   * @param rightQuote
   *     右引号。
   * @param builder
   *     用于追加引用字符串的字符串构建器，其原始内容已转义。
   */
  public static void quote(final String str, final char escapeChar,
      final char leftQuote, final char rightQuote,
      final StringBuilder builder) {
    final String result = escape(str, escapeChar, leftQuote, rightQuote);
    builder.append(leftQuote)
           .append(result)
           .append(rightQuote);
  }

  /**
   * 用单引号引用字符串，必要时转义字符串中的字符。
   *
   * @param str
   *     要引用的字符串。
   * @return 单引号引用的字符串，其原始内容已转义。
   */
  public static String singleQuote(final String str) {
    final StringBuilder builder = new StringBuilder();
    quote(str, '\\', '\'', '\'', builder);
    return builder.toString();
  }

  /**
   * 用双引号引用字符串，必要时转义字符串中的字符。
   *
   * @param str
   *     要引用的字符串。
   * @return 双引号引用的字符串，其原始内容已转义。
   */
  public static String doubleQuote(final String str) {
    final StringBuilder builder = new StringBuilder();
    quote(str, '\\', '"', '"', builder);
    return builder.toString();
  }

  /**
   * 用给定的引号取消引用字符串，**不**反转义字符串中的字符。
   *
   * @param str
   *     要取消引用的字符串。
   * @param leftQuote
   *     左引号。
   * @param rightQuote
   *     右引号。
   * @return 取消引用的字符串，不转义字符串中的字符。
   * @throws IllegalArgumentException
   *     如果字符串没有正确引用。
   */
  public static String unquote(final String str, final char leftQuote,
      final char rightQuote) {
    final int n = str.length();
    if ((n < 2) || (str.charAt(0) != leftQuote)
        || (str.charAt(n - 1) != rightQuote)) {
      throw new IllegalArgumentException("String is not quoted: " + str);
    }
    return str.substring(1, n - 1);
  }

  /**
   * 用给定的引号取消引用字符串，**不**反转义字符串中的字符。
   *
   * @param str
   *     要取消引用的字符串。
   * @param leftQuote
   *     左引号。
   * @param rightQuote
   *     右引号。
   * @param builder
   *     用于追加取消引用字符串的字符串构建器，**不**转义字符串中的字符。
   * @throws IllegalArgumentException
   *     如果字符串没有正确引用。
   */
  public static void unquote(final String str, final char leftQuote,
      final char rightQuote, final StringBuilder builder) {
    final int n = str.length();
    if ((n < 2) || (str.charAt(0) != leftQuote) || (str.charAt(n - 1) != rightQuote)) {
      throw new IllegalArgumentException("String is not quoted: " + str);
    }
    builder.append(str, 1, n - 1);
  }

  /**
   * 用给定的引号取消引用字符串，必要时反转义字符串中的字符。
   *
   * @param str
   *     要取消引用的字符串。
   * @param escapeChar
   *     用于转义自身和其他字符的字符。
   * @param leftQuote
   *     左引号。
   * @param rightQuote
   *     右引号。
   * @return 取消引用的字符串，其原始内容已反转义。
   * @throws IllegalArgumentException
   *     如果字符串没有正确引用。
   */
  public static String unquote(final String str, final char escapeChar,
      final char leftQuote, final char rightQuote) {
    final StringBuilder builder = new StringBuilder(str.length());
    unquote(str, escapeChar, leftQuote, rightQuote, builder);
    return builder.toString();
  }

  /**
   * 用给定的引号取消引用字符串，必要时反转义字符串中的字符。
   *
   * @param str
   *     要取消引用的字符串。
   * @param escapeChar
   *     用于转义自身和其他字符的字符。
   * @param leftQuote
   *     左引号。
   * @param rightQuote
   *     右引号。
   * @param builder
   *     用于追加取消引用字符串的字符串构建器，其原始内容已反转义。
   * @throws IllegalArgumentException
   *     如果字符串没有正确引用。
   */
  public static void unquote(final String str, final char escapeChar,
      final char leftQuote, final char rightQuote,
      final StringBuilder builder) {
    final int n = str.length();
    if ((n < 2) || (str.charAt(0) != leftQuote) || (str.charAt(n - 1) != rightQuote)) {
      throw new IllegalArgumentException("String is not quoted: " + str);
    }
    unescape(str.substring(1, n - 1), escapeChar, builder);
  }

  /**
   * 用给定的引号取消引用字符串，不反转义字符串中的字符。
   *
   * @param str
   *     要取消引用的字符串。
   * @param leftQuote
   *     左引号。
   * @param rightQuote
   *     右引号。
   * @return 取消引用的字符串，不转义字符串中的字符，如果输入字符串为 {@code null} 则返回 {@code null}。
   * @throws IllegalArgumentException
   *     如果字符串没有正确引用。
   */
  public static String unquoteIfNecessary(@Nullable final String str,
      final char leftQuote, final char rightQuote) {
    if (str == null) {
      return null;
    }
    final int n = str.length();
    if ((n >= 2) && (str.charAt(0) == leftQuote) && (str.charAt(n - 1) == rightQuote)) {
      return str.substring(1, n - 1);
    } else {
      return str;
    }
  }

  /**
   * 用给定的引号取消引用字符串，不反转义字符串中的字符。
   *
   * @param str
   *     要取消引用的字符串。
   * @param leftQuote
   *     左引号。
   * @param rightQuote
   *     右引号。
   * @param builder
   *     用于追加取消引用字符串的字符串构建器，不转义字符串中的字符。
   * @throws IllegalArgumentException
   *     如果字符串没有正确引用。
   */
  public static void unquoteIfNecessary(@Nullable final String str,
      final char leftQuote, final char rightQuote, final StringBuilder builder) {
    if (str != null) {
      final int n = str.length();
      if ((n >= 2) && (str.charAt(0) == leftQuote) && (str.charAt(n - 1) == rightQuote)) {
        builder.append(str, 1, n - 1);
      } else {
        builder.append(str);
      }
    }
  }

  /**
   * 测试转义字符串中的字符是否被转义。
   *
   * @param str
   *     转义字符串。
   * @param escapeChar
   *     用于转义自身和其他字符的字符。
   * @param index
   *     转义字符串中字符的索引。
   * @return 如果转义字符串中指定索引处的字符被转义字符转义，则返回 {@code true}；否则返回 {@code false}。
   * @throws IndexOutOfBoundsException
   *     如果 {@code index} 超出 {@code str} 的边界。
   */
  public static boolean isEscaped(final String str, final char escapeChar,
      final int index) {
    final int n = str.length();
    if ((index < 0) || (index >= n)) {
      throw new IndexOutOfBoundsException();
    }
    boolean escaped = false;
    for (int i = 0; i < n; ++i) {
      final char ch = str.charAt(i);
      if (index == i) {
        return escaped;
      }
      if (escaped) {
        escaped = false;
      } else if (ch == escapeChar) {
        escaped = true;
      }
    }
    return false;
  }

  /**
   * 使用指定的转义字符对字符串中的字符进行转义。
   *
   * @param str
   *     要转义的字符串。
   * @param escapeChar
   *     用于转义自身和其他字符的字符。
   * @param characters
   *     需要被转义的指定字符。
   * @return 转义后的字符串。
   */
  public static String escape(final String str, final char escapeChar,
      final char... characters) {
    final int n = str.length();
    final StringBuilder builder = new StringBuilder(n * 2);
    for (int i = 0; i < n; ++i) {
      final char ch = str.charAt(i);
      if ((ch == escapeChar) || ArrayUtils.contains(characters, ch)) {
        builder.append(escapeChar);
      }
      builder.append(ch);
    }
    return builder.toString();
  }

  /**
   * 对转义字符串中的字符进行反转义。
   *
   * <p>转义字符串中所有被指定转义字符转义的字符都将被反转义。
   *
   * @param str
   *     要反转义的转义字符串。
   * @param escapeChar
   *     用于转义自身和其他字符的字符。
   * @return 反转义后的字符串。
   */
  public static String unescape(final String str, final char escapeChar) {
    final StringBuilder builder = new StringBuilder(str.length());
    unescape(str, escapeChar, builder);
    return builder.toString();
  }

  /**
   * 对转义字符串中的字符进行反转义。
   *
   * <p>转义字符串中所有被指定转义字符转义的字符都将被反转义。
   *
   * @param str
   *     要反转义的转义字符串。
   * @param escapeChar
   *     用于转义自身和其他字符的字符。
   * @param builder
   *     用于追加反转义字符串的字符串构建器。
   */
  public static void unescape(final String str, final char escapeChar,
      final StringBuilder builder) {
    final int n = str.length();
    boolean escaped = false;
    for (int i = 0; i < n; ++i) {
      final char ch = str.charAt(i);
      if (escaped) {
        builder.append(ch);
        escaped = false;
      } else if (ch == escapeChar) {
        escaped = true;
      } else {
        builder.append(ch);
      }
    }
  }

  /**
   * 将字符串转换为布尔值。
   *
   * <pre>
   * toBoolean(null)    = false
   * toBoolean("true")  = true
   * toBoolean("false") = false
   * toBoolean("abc")   = false
   * toBoolean("  abc") = false
   * </pre>
   *
   * @param str
   *     要转换为布尔值的字符串，可以为 null。
   * @return 布尔值输出，如果输入字符串为 null 则返回 {@code false}。
   */
  public static boolean toBoolean(@Nullable final String str) {
    return toBoolean(str, BooleanUtils.DEFAULT);
  }

  /**
   * 将字符串转换为布尔值。
   *
   * <pre>
   * toBoolean(null, "true")    = true
   * toBoolean(null, "false")   = false
   * toBoolean("true", "true")  = true
   * toBoolean("false", "true") = false
   * toBoolean("abc", "false")  = false
   * toBoolean("  abc", "true") = true
   * </pre>
   *
   * @param str
   *     要转换为布尔值的字符串，可以为 null。
   * @param defaultValue
   *     当字符串为 null 或不是布尔值时的默认值。
   * @return 布尔值输出，如果输入字符串为 null 或不是布尔值则返回 {@code defaultValue}。
   */
  public static boolean toBoolean(@Nullable final String str,
      final boolean defaultValue) {
    if (str == null) {
      return defaultValue;
    }
    final BooleanFormat bf = new BooleanFormat();
    final boolean value = bf.parse(str);
    if (bf.success()) {
      return value;
    } else {
      return defaultValue;
    }
  }

  /**
   * 将字符串转换为 Boolean 对象。
   *
   * <pre>
   * toBooleanObject(null)    = null
   * toBooleanObject("true")  = true
   * toBooleanObject("false") = false
   * toBooleanObject("abc")   = null
   * </pre>
   *
   * @param str
   *     要转换为布尔值的字符串，可以为 null。
   * @return Boolean 对象输出，如果输入字符串为 null 则返回 {@code null}。
   */
  public static Boolean toBooleanObject(@Nullable final String str) {
    return toBooleanObject(str, null);
  }

  /**
   * 将字符串转换为 Boolean 对象。
   *
   * <pre>
   * toBooleanObject(null, "true")    = true
   * toBooleanObject(null, "false")   = false
   * toBooleanObject("true", "true")  = true
   * toBooleanObject("false", "true") = false
   * toBooleanObject("abc", "false")  = false
   * toBooleanObject("  abc", "true") = true
   * </pre>
   *
   * @param str
   *     要转换为布尔值的字符串，可以为 null。
   * @param defaultValue
   *     当字符串为 null 或不是布尔值时的默认值。
   * @return Boolean 对象输出，如果输入字符串为 null 则返回 {@code defaultValue}。
   */
  public static Boolean toBooleanObject(@Nullable final String str,
      @Nullable final Boolean defaultValue) {
    if (str == null) {
      return defaultValue;
    }
    final BooleanFormat bf = new BooleanFormat();
    final boolean value = bf.parse(str);
    if (bf.success()) {
      return value;
    } else {
      return defaultValue;
    }
  }

  /**
   * 将字符串转换为字符。
   *
   * @param str
   *     要转换的字符串，可以为 null。
   * @return 如果字符串为 null 或空，则返回默认字符值；否则返回字符串的第一个字符。
   */
  public static char toChar(@Nullable final String str) {
    return toChar(str, CharUtils.DEFAULT);
  }

  /**
   * 将字符串转换为字符。
   *
   * @param str
   *     要转换的字符串，可以为 null。
   * @param defaultValue
   *     当字符串为 null 或空时的默认值。
   * @return 如果字符串为 null 或空，则返回 {@code defaultValue}；否则返回字符串的第一个字符。
   */
  public static char toChar(@Nullable final String str,
      final char defaultValue) {
    if ((str == null) || (str.length() == 0)) {
      return defaultValue;
    } else {
      return str.charAt(0);
    }
  }

  /**
   * 将字符串转换为 Character 对象。
   *
   * @param str
   *     要转换的字符串，可以为 null。
   * @return 如果字符串为 null 或空，则返回 null；否则返回字符串的第一个字符的 Character 对象。
   */
  public static Character toCharObject(@Nullable final String str) {
    return toCharObject(str, null);
  }

  /**
   * 将字符串转换为 Character 对象。
   *
   * @param str
   *     要转换的字符串，可以为 null。
   * @param defaultValue
   *     当字符串为 null 或空时的默认值。
   * @return 如果字符串为 null 或空，则返回 {@code defaultValue}；否则返回字符串的第一个字符的 Character 对象。
   */
  public static Character toCharObject(@Nullable final String str,
      @Nullable final Character defaultValue) {
    if ((str == null) || (str.length() == 0)) {
      return defaultValue;
    } else {
      return str.charAt(0);
    }
  }

  /**
   * 将字符串转换为字节值。
   *
   * @param str
   *     要转换的字符串，可以为 null。
   * @return 如果字符串为 null 或无法解析，则返回默认字节值；否则返回解析后的字节值。
   */
  public static byte toByte(@Nullable final String str) {
    return toByte(str, ByteUtils.DEFAULT);
  }

  /**
   * 将字符串转换为字节值。
   *
   * @param str
   *     要转换的字符串，可以为 null。
   * @param defaultValue
   *     当字符串为 null 或无法解析时的默认值。
   * @return 如果字符串为 null 或无法解析，则返回 {@code defaultValue}；否则返回解析后的字节值。
   */
  public static byte toByte(@Nullable final String str,
      final byte defaultValue) {
    if (str == null) {
      return defaultValue;
    }
    final NumberFormat nf = new NumberFormat();
    final byte value = nf.parseByte(str);
    if (nf.success()) {
      return value;
    } else {
      return defaultValue;
    }
  }

  /**
   * 将字符串转换为 Byte 对象。
   *
   * @param str
   *     要转换的字符串，可以为 null。
   * @return 如果字符串为 null 或无法解析，则返回 null；否则返回解析后的 Byte 对象。
   */
  public static Byte toByteObject(@Nullable final String str) {
    return toByteObject(str, null);
  }

  /**
   * 将字符串转换为 Byte 对象。
   *
   * @param str
   *     要转换的字符串，可以为 null。
   * @param defaultValue
   *     当字符串为 null 或无法解析时的默认值。
   * @return 如果字符串为 null 或无法解析，则返回 {@code defaultValue}；否则返回解析后的 Byte 对象。
   */
  public static Byte toByteObject(@Nullable final String str,
      @Nullable final Byte defaultValue) {
    if (str == null) {
      return defaultValue;
    }
    final NumberFormat nf = new NumberFormat();
    final byte value = nf.parseByte(str);
    if (nf.success()) {
      return value;
    } else {
      return defaultValue;
    }
  }

  /**
   * 将字符串转换为短整型值。
   *
   * @param str
   *     要转换的字符串，可以为 null。
   * @return 如果字符串为 null 或无法解析，则返回默认短整型值；否则返回解析后的短整型值。
   */
  public static short toShort(@Nullable final String str) {
    return toShort(str, ShortUtils.DEFAULT);
  }

  /**
   * 将字符串转换为短整型值。
   *
   * @param str
   *     要转换的字符串，可以为 null。
   * @param defaultValue
   *     当字符串为 null 或无法解析时的默认值。
   * @return 如果字符串为 null 或无法解析，则返回 {@code defaultValue}；否则返回解析后的短整型值。
   */
  public static short toShort(@Nullable final String str,
      final short defaultValue) {
    if (str == null) {
      return defaultValue;
    }
    final NumberFormat nf = new NumberFormat();
    final short value = nf.parseShort(str);
    if (nf.success()) {
      return value;
    } else {
      return defaultValue;
    }
  }

  /**
   * 将字符串转换为 Short 对象。
   *
   * @param str
   *     要转换的字符串，可以为 null。
   * @return 如果字符串为 null 或无法解析，则返回 null；否则返回解析后的 Short 对象。
   */
  public static Short toShortObject(@Nullable final String str) {
    return toShortObject(str, null);
  }

  /**
   * 将字符串转换为 Short 对象。
   *
   * @param str
   *     要转换的字符串，可以为 null。
   * @param defaultValue
   *     当字符串为 null 或无法解析时的默认值。
   * @return 如果字符串为 null 或无法解析，则返回 {@code defaultValue}；否则返回解析后的 Short 对象。
   */
  public static Short toShortObject(@Nullable final String str,
      @Nullable final Short defaultValue) {
    if (str == null) {
      return defaultValue;
    }
    final NumberFormat nf = new NumberFormat();
    final short value = nf.parseShort(str);
    if (nf.success()) {
      return value;
    } else {
      return defaultValue;
    }
  }

  /**
   * 将字符串转换为整型值。
   *
   * @param str
   *     要转换的字符串，可以为 null。
   * @return 如果字符串为 null 或无法解析，则返回默认整型值；否则返回解析后的整型值。
   */
  public static int toInt(@Nullable final String str) {
    return toInt(str, IntUtils.DEFAULT);
  }

  /**
   * 将字符串转换为整型值。
   *
   * @param str
   *     要转换的字符串，可以为 null。
   * @param defaultValue
   *     当字符串为 null 或无法解析时的默认值。
   * @return 如果字符串为 null 或无法解析，则返回 {@code defaultValue}；否则返回解析后的整型值。
   */
  public static int toInt(@Nullable final String str, final int defaultValue) {
    if (str == null) {
      return defaultValue;
    }
    final NumberFormat nf = new NumberFormat();
    final int value = nf.parseInt(str);
    if (nf.success()) {
      return value;
    } else {
      return defaultValue;
    }
  }

  /**
   * 将字符串转换为 Integer 对象。
   *
   * @param str
   *     要转换的字符串，可以为 null。
   * @return 如果字符串为 null 或无法解析，则返回 null；否则返回解析后的 Integer 对象。
   */
  public static Integer toIntObject(@Nullable final String str) {
    return toIntObject(str, null);
  }

  /**
   * 将字符串转换为 Integer 对象。
   *
   * @param str
   *     要转换的字符串，可以为 null。
   * @param defaultValue
   *     当字符串为 null 或无法解析时的默认值。
   * @return 如果字符串为 null 或无法解析，则返回 {@code defaultValue}；否则返回解析后的 Integer 对象。
   */
  public static Integer toIntObject(@Nullable final String str,
      @Nullable final Integer defaultValue) {
    if (str == null) {
      return defaultValue;
    }
    final NumberFormat nf = new NumberFormat();
    final int value = nf.parseInt(str);
    if (nf.success()) {
      return value;
    } else {
      return defaultValue;
    }
  }

  /**
   * 将字符串转换为长整型值。
   *
   * @param str
   *     要转换的字符串，可以为 null。
   * @return 如果字符串为 null 或无法解析，则返回默认长整型值；否则返回解析后的长整型值。
   */
  public static long toLong(@Nullable final String str) {
    return toLong(str, LongUtils.DEFAULT);
  }

  /**
   * 将字符串转换为长整型值。
   *
   * @param str
   *     要转换的字符串，可以为 null。
   * @param defaultValue
   *     当字符串为 null 或无法解析时的默认值。
   * @return 如果字符串为 null 或无法解析，则返回 {@code defaultValue}；否则返回解析后的长整型值。
   */
  public static long toLong(@Nullable final String str,
      final long defaultValue) {
    if (str == null) {
      return defaultValue;
    }
    final NumberFormat nf = new NumberFormat();
    final long value = nf.parseLong(str);
    if (nf.success()) {
      return value;
    } else {
      return defaultValue;
    }
  }

  /**
   * 将字符串转换为 Long 对象。
   *
   * @param str
   *     要转换的字符串，可以为 null。
   * @return 如果字符串为 null 或无法解析，则返回 null；否则返回解析后的 Long 对象。
   */
  public static Long toLongObject(@Nullable final String str) {
    return toLongObject(str, null);
  }

  /**
   * 将字符串转换为 Long 对象。
   *
   * @param str
   *     要转换的字符串，可以为 null。
   * @param defaultValue
   *     当字符串为 null 或无法解析时的默认值。
   * @return 如果字符串为 null 或无法解析，则返回 {@code defaultValue}；否则返回解析后的 Long 对象。
   */
  public static Long toLongObject(@Nullable final String str,
      @Nullable final Long defaultValue) {
    if (str == null) {
      return defaultValue;
    }
    final NumberFormat nf = new NumberFormat();
    final long value = nf.parseLong(str);
    if (nf.success()) {
      return value;
    } else {
      return defaultValue;
    }
  }

  /**
   * 将字符串转换为浮点型值。
   *
   * @param str
   *     要转换的字符串，可以为 null。
   * @return 如果字符串为 null 或无法解析，则返回默认浮点型值；否则返回解析后的浮点型值。
   */
  public static float toFloat(@Nullable final String str) {
    return toFloat(str, FloatUtils.DEFAULT);
  }

  /**
   * 将字符串转换为浮点型值。
   *
   * @param str
   *     要转换的字符串，可以为 null。
   * @param defaultValue
   *     当字符串为 null 或无法解析时的默认值。
   * @return 如果字符串为 null 或无法解析，则返回 {@code defaultValue}；否则返回解析后的浮点型值。
   */
  public static float toFloat(@Nullable final String str,
      final float defaultValue) {
    if (str == null) {
      return defaultValue;
    }
    final NumberFormat nf = new NumberFormat();
    final float value = nf.parseFloat(str);
    if (nf.success()) {
      return value;
    } else {
      return defaultValue;
    }
  }

  /**
   * 将字符串转换为 Float 对象。
   *
   * @param str
   *     要转换的字符串，可以为 null。
   * @return 如果字符串为 null 或无法解析，则返回 null；否则返回解析后的 Float 对象。
   */
  public static Float toFloatObject(@Nullable final String str) {
    return toFloatObject(str, null);
  }

  /**
   * 将字符串转换为 Float 对象。
   *
   * @param str
   *     要转换的字符串，可以为 null。
   * @param defaultValue
   *     当字符串为 null 或无法解析时的默认值。
   * @return 如果字符串为 null 或无法解析，则返回 {@code defaultValue}；否则返回解析后的 Float 对象。
   */
  public static Float toFloatObject(@Nullable final String str,
      @Nullable final Float defaultValue) {
    if (str == null) {
      return defaultValue;
    }
    final NumberFormat nf = new NumberFormat();
    final float value = nf.parseFloat(str);
    if (nf.success()) {
      return value;
    } else {
      return defaultValue;
    }
  }

  /**
   * 将字符串转换为双精度浮点型值。
   *
   * @param str
   *     要转换的字符串，可以为 null。
   * @return 如果字符串为 null 或无法解析，则返回默认双精度浮点型值；否则返回解析后的双精度浮点型值。
   */
  public static double toDouble(@Nullable final String str) {
    return toDouble(str, DoubleUtils.DEFAULT);
  }

  /**
   * 将字符串转换为双精度浮点型值。
   *
   * @param str
   *     要转换的字符串，可以为 null。
   * @param defaultValue
   *     当字符串为 null 或无法解析时的默认值。
   * @return 如果字符串为 null 或无法解析，则返回 {@code defaultValue}；否则返回解析后的双精度浮点型值。
   */
  public static double toDouble(@Nullable final String str,
      final double defaultValue) {
    if (str == null) {
      return defaultValue;
    }
    final NumberFormat nf = new NumberFormat();
    final double value = nf.parseDouble(str);
    if (nf.success()) {
      return value;
    } else {
      return defaultValue;
    }
  }

  /**
   * 将字符串转换为 Double 对象。
   *
   * @param str
   *     要转换的字符串，可以为 null。
   * @return 如果字符串为 null 或无法解析，则返回 null；否则返回解析后的 Double 对象。
   */
  public static Double toDoubleObject(@Nullable final String str) {
    return toDoubleObject(str, null);
  }

  /**
   * 将字符串转换为 Double 对象。
   *
   * @param str
   *     要转换的字符串，可以为 null。
   * @param defaultValue
   *     当字符串为 null 或无法解析时的默认值。
   * @return 如果字符串为 null 或无法解析，则返回 {@code defaultValue}；否则返回解析后的 Double 对象。
   */
  public static Double toDoubleObject(@Nullable final String str,
      @Nullable final Double defaultValue) {
    if (str == null) {
      return defaultValue;
    }
    final NumberFormat nf = new NumberFormat();
    final double value = nf.parseDouble(str);
    if (nf.success()) {
      return value;
    } else {
      return defaultValue;
    }
  }

  /**
   * 将字符串转换为 Date 对象。
   *
   * @param str
   *     要转换的字符串，可以为 null。
   * @return 如果字符串为 null 或无法解析，则返回 null；否则返回解析后的 Date 对象。
   */
  public static Date toDate(@Nullable final String str) {
    return toDate(str, null);
  }

  /**
   * 将字符串转换为 Date 对象。
   *
   * @param str
   *     要转换的字符串，可以为 null。
   * @param defaultValue
   *     当字符串为 null 或无法解析时的默认值。
   * @return 如果字符串为 null 或无法解析，则返回 {@code defaultValue}；否则返回解析后的 Date 对象。
   */
  public static Date toDate(@Nullable final String str,
      @Nullable final Date defaultValue) {
    if (str == null) {
      return defaultValue;
    }
    final DateFormat df = new DateFormat();
    Date value = df.parse(str);
    if (df.success()) {
      return value;
    } else {
      // try to parse without timezone
      df.setPattern(DateUtils.DEFAULT_LOCAL_DATETIME_PATTERN);
      value = df.parse(str);
      if (df.success()) {
        return value;
      }
      return defaultValue;
    }
  }

  /**
   * 将字符串转换为 LocalDate 对象。
   *
   * @param str
   *     要转换的字符串，可以为 null。
   * @return 如果字符串为 null 或无法解析，则返回 null；否则返回解析后的 LocalDate 对象。
   */
  public static LocalDate toLocalDate(@Nullable final String str) {
    return toLocalDate(str, null);
  }

  /**
   * 将字符串转换为 LocalDate 对象。
   *
   * @param str
   *     要转换的字符串，可以为 null。
   * @param defaultValue
   *     当字符串为 null 或无法解析时的默认值。
   * @return 如果字符串为 null 或无法解析，则返回 {@code defaultValue}；否则返回解析后的 LocalDate 对象。
   */
  public static LocalDate toLocalDate(@Nullable final String str,
      @Nullable final LocalDate defaultValue) {
    if (str == null) {
      return defaultValue;
    }
    final LocalDateCodec codec = IsoLocalDateCodec.INSTANCE;
    try {
      return codec.decode(str);
    } catch (final DecodingException e) {
      LOGGER.error("Failed to decode string to LocalDate: '{}', use the default value: {}", str, defaultValue, e);
      return defaultValue;
    }
  }

  /**
   * 将字符串转换为 LocalTime 对象。
   *
   * @param str
   *     要转换的字符串，可以为 null。
   * @return 如果字符串为 null 或无法解析，则返回 null；否则返回解析后的 LocalTime 对象。
   */
  public static LocalTime toLocalTime(@Nullable final String str) {
    return toLocalTime(str, null);
  }

  /**
   * 将字符串转换为 LocalTime 对象。
   *
   * @param str
   *     要转换的字符串，可以为 null。
   * @param defaultValue
   *     当字符串为 null 或无法解析时的默认值。
   * @return 如果字符串为 null 或无法解析，则返回 {@code defaultValue}；否则返回解析后的 LocalTime 对象。
   */
  public static LocalTime toLocalTime(@Nullable final String str,
      @Nullable final LocalTime defaultValue) {
    if (str == null) {
      return defaultValue;
    }
    final LocalTimeCodec codec = IsoLocalTimeCodec.INSTANCE;
    try {
      return codec.decode(str);
    } catch (final DecodingException e) {
      LOGGER.error("Failed to decode string to LocalTime: '{}', use the default value: {}", str, defaultValue, e);
      return defaultValue;
    }
  }

  /**
   * 将字符串转换为 LocalDateTime 对象。
   *
   * @param str
   *     要转换的字符串，可以为 null。
   * @return 如果字符串为 null 或无法解析，则返回 null；否则返回解析后的 LocalDateTime 对象。
   */
  public static LocalDateTime toLocalDateTime(@Nullable final String str) {
    return toLocalDateTime(str, null);
  }

  /**
   * 将字符串转换为 LocalDateTime 对象。
   *
   * @param str
   *     要转换的字符串，可以为 null。
   * @param defaultValue
   *     当字符串为 null 或无法解析时的默认值。
   * @return 如果字符串为 null 或无法解析，则返回 {@code defaultValue}；否则返回解析后的 LocalDateTime 对象。
   */
  public static LocalDateTime toLocalDateTime(@Nullable final String str,
      @Nullable final LocalDateTime defaultValue) {
    if (str == null) {
      return defaultValue;
    }
    final LocalDateTimeCodec codec = IsoLocalDateTimeCodec.INSTANCE;
    try {
      return codec.decode(str);
    } catch (final DecodingException e) {
      LOGGER.error("Failed to decode string to LocalDateTime: '{}', use the default value: {}", str, defaultValue, e);
      return defaultValue;
    }
  }

  /**
   * 将字符串转换为 Class 对象。
   *
   * @param str
   *     要转换的字符串，可以为 null。
   * @return 如果字符串为 null 或无法解析，则返回 null；否则返回解析后的 Class 对象。
   */
  public static Class<?> toClass(@Nullable final String str) {
    return toClass(str, null);
  }

  /**
   * 将字符串转换为 Class 对象。
   *
   * @param str
   *     要转换的字符串，可以为 null。
   * @param defaultValue
   *     当字符串为 null 或无法解析时的默认值。
   * @return 如果字符串为 null 或无法解析，则返回 {@code defaultValue}；否则返回解析后的 Class 对象。
   */
  public static Class<?> toClass(@Nullable final String str,
      @Nullable final Class<?> defaultValue) {
    if (str == null) {
      return defaultValue;
    }
    final String className = strip(str);
    try {
      return ClassUtils.getClass(className);
    } catch (final ClassNotFoundException e) {
      return defaultValue;
    }
  }

  /**
   * 将字符串转换为字节数组。
   *
   * @param str
   *     要转换的字符串，可以为 null。
   * @return 如果字符串为 null 或无法解析，则返回 null；否则返回解析后的字节数组。
   */
  public static byte[] toByteArray(@Nullable final String str) {
    return toByteArray(str, null);
  }

  /**
   * 将字符串转换为字节数组。
   *
   * @param str
   *     要转换的字符串，可以为 null。
   * @param defaultValue
   *     当字符串为 null 或无法解析时的默认值。
   * @return 如果字符串为 null 或无法解析，则返回 {@code defaultValue}；否则返回解析后的字节数组。
   */
  public static byte[] toByteArray(@Nullable final String str,
      @Nullable final byte[] defaultValue) {
    if (str == null) {
      return defaultValue;
    }
    if (str.length() == 0) {
      return defaultValue;
    }
    final HexCodec codec = new HexCodec();
    final byte[] value = codec.decode(str);
    if (codec.success()) {
      return value;
    } else {
      return defaultValue;
    }
  }

  /**
   * 将字符串转换为 {@link BigInteger} 值。
   *
   * @param str
   *     要转换的字符串，可以为 null。
   * @return
   *     字符串的 {@link BigInteger} 值，如果字符串为 null 或无法转换为 {@link BigInteger} 则返回 {@code null}。
   */
  public static BigInteger toBigInteger(@Nullable final String str) {
    return toBigInteger(str, null);
  }

  /**
   * 将字符串转换为 {@link BigInteger} 值，如果字符串为 null 或无法转换则使用默认值。
   *
   * @param str
   *     要转换的字符串，可以为 null。
   * @param defaultValue
   *     当字符串为 null 或无法转换为 {@link BigInteger} 时返回的默认值，可以为 null。
   * @return
   *     字符串的 {@link BigInteger} 值，如果字符串为 null 或无法转换为 {@link BigInteger} 则返回默认值。
   */
  public static BigInteger toBigInteger(@Nullable final String str,
      @Nullable final BigInteger defaultValue) {
    if (str == null) {
      return defaultValue;
    }
    final NumberFormat nf = new NumberFormat();
    final BigInteger value = nf.parseBigInteger(str);
    if (nf.success()) {
      return value;
    } else {
      return defaultValue;
    }
  }

  /**
   * 将字符串转换为 {@link BigDecimal} 值。
   *
   * @param str
   *     要转换的字符串，可以为 null。
   * @return
   *     字符串的 {@link BigDecimal} 值，如果字符串为 null 或无法转换为 {@link BigDecimal} 则返回 {@code null}。
   */
  public static BigDecimal toBigDecimal(@Nullable final String str) {
    return toBigDecimal(str, null);
  }

  /**
   * 将字符串转换为 {@link BigDecimal} 值，如果字符串为 null 或无法转换则使用默认值。
   *
   * @param str
   *     要转换的字符串，可以为 null。
   * @param defaultValue
   *     当字符串为 null 或无法转换为 {@link BigDecimal} 时返回的默认值，可以为 null。
   * @return
   *     字符串的 {@link BigDecimal} 值，如果字符串为 null 或无法转换为 {@link BigDecimal} 则返回默认值。
   */
  public static BigDecimal toBigDecimal(@Nullable final String str,
      @Nullable final BigDecimal defaultValue) {
    if (str == null) {
      return defaultValue;
    }
    final NumberFormat nf = new NumberFormat();
    final BigDecimal value = nf.parseBigDecimal(str);
    if (nf.success()) {
      return value;
    } else {
      return defaultValue;
    }
  }

  /**
   * 将字符串转换为指定枚举类的枚举值。
   *
   * @param <E>
   *     枚举类的类型。
   * @param cls
   *     枚举类。
   * @param str
   *     要转换的字符串，可以为 null。
   * @param defaultValue
   *     当字符串为 null 或无法转换为枚举值时返回的默认值，可以为 null。
   * @return
   *     字符串的枚举值，如果字符串为 null 或无法转换为枚举值则返回默认值。
   */
  public static <E extends Enum<E>> E toEnum(final Class<E> cls,
      @Nullable final String str,
      @Nullable final E defaultValue) {
    for (final E value : cls.getEnumConstants()) {
      if (value.name().equals(str)) {
        return value;
      }
    }
    return defaultValue;
  }

  /**
   * 将字符串转换为具有指定标签名和值的 XML 节点。
   *
   * @param doc
   *     XML 文档。
   * @param tagName
   *     XML 节点的标签名。
   * @param prevSpaceAttr
   *     前置空格的属性名，可以为 null。
   * @param value
   *     XML 节点的值，可以为 null。
   * @return
   *     XML 节点。
   */
  public static Element toXmlNode(final Document doc, final String tagName,
      @Nullable final String prevSpaceAttr, @Nullable final String value) {
    final Element node = doc.createElement(tagName);
    if (value != null) {
      if ((prevSpaceAttr != null) && (prevSpaceAttr.length() > 0)
          && startsOrEndsWithChar(value, BlankCharFilter.INSTANCE)) {
        node.setAttribute(prevSpaceAttr, TRUE);
      }
      node.setTextContent(value);
    }
    return node;
  }

  /**
   * 规范化字符串中的换行符。
   *
   * @param str
   *     要规范化的字符串，可以为 null。
   * @return
   *     规范化后的字符串，如果为 null 则返回原字符串。
   */
  public static String normalizeLines(final String str) {
    return normalizeLines(str, true, true);
  }

  /**
   * 规范化字符串中的换行符，可选择是否修剪和忽略空行。
   *
   * @param str
   *     要规范化的字符串，可以为 null。
   * @param trim
   *     是否修剪行。
   * @param ignoreEmpty
   *     是否忽略空行。
   * @return
   *     规范化后的字符串，如果为 null 则返回原字符串。
   */
  public static String normalizeLines(final String str, final boolean trim,
      final boolean ignoreEmpty) {
    if (isEmpty(str)) {
      return str;
    }
    final List<String> lines = new Splitter()
        .toLines()
        .strip(trim)
        .ignoreEmpty(ignoreEmpty)
        .split(str);
    return join('\n', lines);
  }

  /**
   * 将字符串中的行连接成单行，可选择是否修剪和忽略空行。
   *
   * @param str
   *     要连接的字符串，可以为 null。
   * @param trim
   *     是否修剪行。
   * @param ignoreEmpty
   *     是否忽略空行。
   * @return
   *     连接后的字符串，如果为 null 则返回原字符串。
   */
  public static String concatLines(final String str, final boolean trim,
      final boolean ignoreEmpty) {
    if (isEmpty(str)) {
      return str;
    }
    final List<String> lines = new Splitter()
        .toLines()
        .strip(trim)
        .ignoreEmpty(ignoreEmpty)
        .split(str);
    return join(' ', lines);
  }

  /**
   * 在指定子字符串第一次出现之前截断字符串。
   *
   * @param str
   *     要截断的字符串，可以为 null。
   * @param substr
   *     在其之前截断的子字符串，可以为 null。
   * @return
   *     截断后的字符串，如果未找到子字符串或输入字符串为 null 则返回原字符串。
   */
  public static String truncateBefore(final String str, final String substr) {
    final int pos = str.indexOf(substr);
    if (pos >= 0) {
      return str.substring(0, pos);
    } else {
      return str;
    }
  }

  /**
   * 在指定子字符串第一次出现之后截断字符串。
   *
   * @param str
   *     要截断的字符串，可以为 null。
   * @param substr
   *     在其之后截断的子字符串，可以为 null。
   * @return
   *     截断后的字符串，如果未找到子字符串或输入字符串为 null 则返回原字符串。
   */
  public static String truncateAfter(final String str, final String substr) {
    final int pos = str.indexOf(substr);
    if (pos >= 0) {
      return str.substring(0, pos + substr.length());
    } else {
      return str;
    }
  }

  /**
   * 将多个字符串连接成单个字符串。
   *
   * @param strings
   *     要连接的字符串。
   * @return
   *     连接后的字符串。
   */
  public static String concat(final String... strings) {
    final StringBuilder builder = new StringBuilder();
    for (final String str : strings) {
      builder.append(str);
    }
    return builder.toString();
  }

  /**
   * 将对象转换为字符串表示。
   *
   * @param obj
   *     要转换的对象，可以为 null。
   * @return
   *     对象的字符串表示，如果对象为 null 则返回 {@code null}。
   */
  public static String valueOf(@Nullable final Object obj) {
    return (obj == null ? null : obj.toString());
  }

  /**
   * 将字符串的第一个字符转换为大写。
   *
   * @param str
   *     要转换的字符串，可以为 null。
   * @return
   *     转换后的字符串，如果为 null 或空则返回原字符串。
   */
  public static String uppercaseFirstChar(final String str) {
    if (str == null || str.length() == 0) {
      return str;
    } else {
      return Character.toUpperCase(str.charAt(0)) + str.substring(1);
    }
  }

  /**
   * 将字符串的第一个字符转换为小写。
   *
   * @param str
   *     要转换的字符串，可以为 null。
   * @return
   *     转换后的字符串，如果为 null 或空则返回原字符串。
   */
  public static String lowercaseFirstChar(final String str) {
    if (str == null || str.length() == 0) {
      return str;
    } else {
      return Character.toLowerCase(str.charAt(0)) + str.substring(1);
    }
  }

  /**
   * 将值转换为字符串表示。
   *
   * @param value
   *     要转换的值，可以为 null。
   * @return
   *     值的字符串表示，如果值为 {@code null} 则返回 {@code null}。
   */
  public static String toString(@Nullable final Object value) {
    return (value == null ? null : toStringImpl(value));
  }

  /**
   * 将值转换为字符串表示，如果值为 {@code null} 则使用默认值。
   *
   * @param value
   *     要转换的值，可以为 null。
   * @param defaultValue
   *     当值为 null 时返回的默认值。
   * @return
   *     值的字符串表示，如果值为 {@code null} 则返回默认值。
   */
  public static String toString(@Nullable final Object value,
      final String defaultValue) {
    return (value == null ? defaultValue : toStringImpl(value));
  }

  /**
   * Converts a value to a string representation.
   *
   * @param value
   *     the value to be converted.
   * @return
   *     the string representation of the value.
   */
  private static String toStringImpl(final Object value) {
    if (value instanceof String) {
      return (String) value;
    } else if (value instanceof Character) {
      return String.valueOf(value);
    } else if (value instanceof Enum) {
      return ((Enum<?>) value).name();
    } else if (ArrayUtils.isArray(value)) {
      return ArrayUtils.toString(value);
    } else {
      return value.toString();
    }
  }

  /**
   * 检查指定类型的值是否可以与 {@code String} 类型的值进行比较。
   *
   * @param type
   *     指定的类型。
   * @return
   *     如果指定类型的值可以与 {@code String} 类型的值进行比较则返回 {@code true}；否则返回 {@code false}。
   */
  public static boolean isComparable(final Class<?> type) {
    return (type == String.class)
        || (type == char.class)
        || (type == Character.class)
        || Enum.class.isAssignableFrom(type);
  }

  /**
   * 将字符串的第一个字符转换为小写。
   *
   * @param str
   *     指定的字符串。
   * @return
   *     一个新字符串，其第一个字符为小写，其余内容与指定字符串相同。
   */
  public static String lowerCaseFirstChar(final String str) {
    if (str == null || str.length() == 0) {
      return str;
    } else {
      return Character.toLowerCase(str.charAt(0)) + str.substring(1);
    }
  }

  /**
   * 将空字符串转换为 {@code null} 值。
   *
   * @param str
   *     要转换的字符串，可以为 null。
   * @return
   *     如果指定字符串为 null 或空则返回 {@code null}；否则返回原字符串。
   */
  @Nullable
  public static String emptyToNull(@Nullable final String str) {
    if (str != null && str.isEmpty()) {
      return null;
    } else {
      return str;
    }
  }

  /**
   * 将与指定默认值相等的字符串转换为 {@code null}。
   *
   * @param defaultValue
   *     指定的默认值。
   * @param str
   *     要转换的字符串，可以为 null。
   * @return
   *     如果字符串与默认值相等则返回 {@code null}；否则返回原字符串。
   */
  @Nullable
  public static String defaultToNull(final String defaultValue, @Nullable final String str) {
    if (str != null && str.equals(defaultValue)) {
      return null;
    } else {
      return str;
    }
  }

  /**
   * 将null字符串转换为空字符串。
   *
   * @param str
   *     要转换的字符串，可能为null。
   * @return
   *     如果指定的字符串为null则返回空字符串；否则返回原字符串。
   */
  @Nonnull
  public static String nullToEmpty(@Nullable final String str) {
    return (str == null ? EMPTY : str);
  }

  /**
   * 将null字符序列转换为空字符串。
   *
   * @param str
   *     要转换的字符序列，可能为null。
   * @return
   *     如果指定的字符序列为null则返回空字符串；否则返回原字符序列。
   */
  @Nonnull
  public static CharSequence nullToEmpty(@Nullable final CharSequence str) {
    return (str == null ? EMPTY : str);
  }

  /**
   * 将指定的字符序列拆分为Unicode代码点。
   *
   * @param str
   *     指定的字符序列，可能为null
   * @return
   *     指定字符序列中Unicode代码点的列表。如果指定的字符序列为null或空，
   *     则返回空列表
   */
  public static IntList splitCodePoints(@Nullable final CharSequence str) {
    final IntList result = new IntArrayList();
    if (str == null || str.length() == 0) {
      return result;
    }
    final int n = str.length();
    int count;
    for (int i = 0; i < n; i += count) {
      final int codePoint = Character.codePointAt(str, i);
      count = Character.charCount(codePoint);
      result.add(codePoint);
    }
    return result;
  }

  /**
   * 将指定的字符序列拆分为Unicode代码点。
   *
   * @param str
   *     指定的字符序列，可能为null
   * @return
   *     指定字符序列中Unicode代码点的数组。如果指定的字符序列为null或空，
   *     则返回空数组
   */
  public static int[] splitCodePointsToArray(@Nullable final CharSequence str) {
    return splitCodePoints(str).toArray();
  }

  /**
   * 将double值格式化为百分比字符串。
   *
   * @param value
   *     要格式化的double值
   * @return
   *     格式化的百分比字符串
   */
  public static String formatPercent(final double value) {
    return formatPercent(value, 2, Locale.getDefault());
  }

  /**
   * 将double值格式化为具有指定小数位数的百分比字符串。
   *
   * @param value
   *     要格式化的double值
   * @param fractionDigits
   *     要显示的小数位数
   * @return
   *     格式化的百分比字符串
   */
  public static String formatPercent(final double value, final int fractionDigits) {
    return formatPercent(value, fractionDigits, Locale.getDefault());
  }

  /**
   * 将double值格式化为具有指定小数位数和语言环境的百分比字符串。
   *
   * @param value
   *     要格式化的double值
   * @param fractionDigits
   *     要显示的小数位数
   * @param locale
   *     用于格式化的语言环境
   * @return
   *     格式化的百分比字符串
   */
  public static String formatPercent(final double value, final int fractionDigits,
      final Locale locale) {
    final java.text.NumberFormat nf = java.text.NumberFormat.getPercentInstance(locale);
    nf.setMaximumFractionDigits(fractionDigits);
    nf.setMinimumFractionDigits(fractionDigits);
    return nf.format(value);
  }

  /**
   * 将百分比字符串解析为double值。
   *
   * @param str
   *     要解析的百分比字符串
   * @return
   *     解析后的double值
   * @throws ParseException
   *     如果字符串无法解析为百分比
   */
  public static double parsePercent(@Nullable final String str) throws ParseException {
    return parsePercent(str, 2, Locale.getDefault());
  }

  /**
   * 将百分比字符串解析为具有指定小数位数的double值。
   *
   * @param str
   *     要解析的百分比字符串
   * @param fractionDigits
   *     百分比字符串中期望的小数位数
   * @return
   *     解析后的double值
   * @throws ParseException
   *     如果字符串无法解析为百分比
   */
  public static double parsePercent(@Nullable final String str, final int fractionDigits)
      throws ParseException {
    return parsePercent(str, fractionDigits, Locale.getDefault());
  }

  /**
   * 将百分比字符串解析为具有指定小数位数和语言环境的double值。
   *
   * @param str
   *     要解析的百分比字符串
   * @param fractionDigits
   *     百分比字符串中期望的小数位数
   * @param locale
   *     用于解析的语言环境
   * @return
   *     解析后的double值
   * @throws ParseException
   *     如果字符串无法解析为百分比
   */
  public static double parsePercent(@Nullable final String str, final int fractionDigits,
      final Locale locale) throws ParseException {
    if (str == null || str.isEmpty()) {
      throw new ParseException("Cannot parse null or empty string as percentage", 0);
    }
    final java.text.NumberFormat nf = java.text.NumberFormat.getPercentInstance(locale);
    nf.setMaximumFractionDigits(fractionDigits);
    return nf.parse(str).doubleValue();
  }

  /**
   * 为输入字符串的每一行添加前缀。
   * <p>
   * 换行符包括{@code '\n'}、{@code '\r'}和{@code '\r\n'}。
   *
   * @param input
   *     输入字符串，可能为null
   * @param prefix
   *     要添加到每行的前缀，可能为null或空
   * @return
   *     每行都添加了指定前缀的字符串，如果输入或前缀为null或空则返回原字符串
   */
  public static String addPrefixToEachLine(final String input, final String prefix) {
    if (input == null || prefix == null || prefix.isEmpty()) {
      return input; // If input or prefix is null or empty, return the original string
    }
    // Use regular expression to match the beginning of each line and add the prefix
    // Regular expression explanation:
    // ^ represents the beginning of a line
    // (?m) is the multiline mode, which makes ^ and $ match the beginning and end of each line
    return input.replaceAll("(?m)^", prefix);
  }

  /**
   * 从输入字符串的每一行中移除前缀。
   * <p>
   * 换行符包括{@code '\n'}、{@code '\r'}和{@code '\r\n'}。
   *
   * @param input
   *     输入字符串，可能为null
   * @param prefix
   *     要从每行中移除的前缀，可能为null或空
   * @return
   *     每行都移除了指定前缀的字符串，如果输入或前缀为null或空则返回原字符串
   */
  public static String removePrefixFromEachLine(final String input, final String prefix) {
    if (input == null || prefix == null || prefix.isEmpty()) {
      return input; // If input or prefix is null or empty, return the original string
    }
    // Use regular expression to replace the prefix at the beginning of each line, ensuring line-by-line matching
    // ^ represents the beginning of a line
    // (?m) is the multiline mode, which makes ^ and $ match the beginning and end of each line
    final String regex = "(?m)^" + Pattern.quote(prefix);
    return input.replaceAll(regex, "");
  }
}