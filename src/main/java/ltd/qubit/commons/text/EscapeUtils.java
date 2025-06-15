////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text;

import java.util.Map;

import javax.annotation.Nullable;
import javax.annotation.concurrent.ThreadSafe;

import ltd.qubit.commons.lang.StringUtils;
import ltd.qubit.commons.text.translate.AggregateTranslator;
import ltd.qubit.commons.text.translate.CharSequenceTranslator;
import ltd.qubit.commons.text.translate.CsvEscaper;
import ltd.qubit.commons.text.translate.CsvUnescaper;
import ltd.qubit.commons.text.translate.EntityArrays;
import ltd.qubit.commons.text.translate.JavaUnicodeEscaper;
import ltd.qubit.commons.text.translate.LookupTranslator;
import ltd.qubit.commons.text.translate.NumericEntityEscaper;
import ltd.qubit.commons.text.translate.NumericEntityUnescaper;
import ltd.qubit.commons.text.translate.OctalUnescaper;
import ltd.qubit.commons.text.translate.UnicodeUnescaper;
import ltd.qubit.commons.text.translate.UnicodeUnpairedSurrogateRemover;
import ltd.qubit.commons.text.translate.XsiUnescaper;

/**
 * 为Java、JavaScript、HTML和XML转义和反转义{@code String}。
 *
 * @author 胡海星
 */
@ThreadSafe
public class EscapeUtils {

  private EscapeUtils() {}

  /**
   * {@link java.lang.StringBuilder}的便利包装器，提供转义方法。
   *
   * <p>示例：</p>
   * <pre>
   * new Builder(ESCAPE_HTML4)
   *      .append("&lt;p&gt;")
   *      .escape("This is paragraph 1 and special chars like &amp; get escaped.")
   *      .append("&lt;/p&gt;&lt;p&gt;")
   *      .escape("This is paragraph 2 &amp; more...")
   *      .append("&lt;/p&gt;")
   *      .toString()
   * </pre>
   *
   */
  public static class Builder {

    /**
     * 在Builder类中使用的StringBuilder。
     */
    private final StringBuilder builder;

    /**
     * 在Builder类中使用的CharSequenceTranslator。
     */
    private final CharSequenceTranslator translator;

    /**
     * Builder构造函数。
     *
     * @param translator
     *     CharSequenceTranslator。
     */
    private Builder(final CharSequenceTranslator translator) {
      this.builder = new StringBuilder();
      this.translator = translator;
    }

    /**
     * 字面量追加，不进行转义。
     *
     * @param input
     *     要追加的字符串。
     * @return
     *     {@code this}，以支持链式调用。
     */
    public Builder append(@Nullable final String input) {
      builder.append(input);
      return this;
    }

    /**
     * 根据给定的{@link CharSequenceTranslator}转义{@code input}。
     *
     * @param input
     *     要转义的字符串。
     * @return
     *     {@code this}，以支持链式调用。
     */
    public Builder escape(@Nullable final String input) {
      translator.transform(input, builder);
      return this;
    }

    /**
     * 返回转义后的字符串。
     *
     * @return
     *     转义后的字符串。
     */
    @Override
    public String toString() {
      return builder.toString();
    }
  }

  /**
   * 用于转义Java的转换器对象。
   * <p>
   * 虽然{@link #escapeJava(String)}是预期的使用方法，但此对象允许Java转义
   * 功能用作自定义转换器的基础。
   */
  public static final CharSequenceTranslator ESCAPE_JAVA =
      new AggregateTranslator(
        new LookupTranslator(Map.of(
            "\"", "\\\"",
            "\\", "\\\\"
        )),
        new LookupTranslator(EntityArrays.JAVA_CTRL_CHARS_ESCAPE),
        JavaUnicodeEscaper.outsideOf(32, 0x7f)
      );

  /**
   * 用于转义EcmaScript/JavaScript的转换器对象。
   * <p>
   * 虽然{@link #escapeEcmaScript(String)}是预期的使用方法，但此对象允许EcmaScript转义
   * 功能用作自定义转换器的基础。
   */
  public static final CharSequenceTranslator ESCAPE_ECMASCRIPT =
      new AggregateTranslator(
        new LookupTranslator(Map.of(
            "'", "\\'",
            "\"", "\\\"",
            "\\", "\\\\",
            "/", "\\/"
        )),
        new LookupTranslator(EntityArrays.JAVA_CTRL_CHARS_ESCAPE),
        JavaUnicodeEscaper.outsideOf(32, 0x7f)
    );

  /**
   * 用于转义Json的转换器对象。
   * <p>
   * 虽然{@link #escapeJson(String)}是预期的使用方法，但此对象允许Json转义
   * 功能用作自定义转换器的基础。
   */
  public static final CharSequenceTranslator ESCAPE_JSON =
      new AggregateTranslator(
        new LookupTranslator(Map.of(
            "\"", "\\\"",
            "\\", "\\\\",
            "/", "\\/")
        ),
        new LookupTranslator(EntityArrays.JAVA_CTRL_CHARS_ESCAPE),
        JavaUnicodeEscaper.outsideOf(32, 0x7e)
      );

  /**
   * 用于转义XML 1.0的转换器对象。
   * <p>
   * 虽然{@link #escapeXml10(String)}是预期的使用方法，但此对象允许XML转义
   * 功能用作自定义转换器的基础。
   */
  public static final CharSequenceTranslator ESCAPE_XML10 =
      new AggregateTranslator(
        new LookupTranslator(EntityArrays.BASIC_ESCAPE),
        new LookupTranslator(EntityArrays.APOS_ESCAPE),
        new LookupTranslator(Map.ofEntries(
            Map.entry("\u0000", StringUtils.EMPTY),
            Map.entry("\u0001", StringUtils.EMPTY),
            Map.entry("\u0002", StringUtils.EMPTY),
            Map.entry("\u0003", StringUtils.EMPTY),
            Map.entry("\u0004", StringUtils.EMPTY),
            Map.entry("\u0005", StringUtils.EMPTY),
            Map.entry("\u0006", StringUtils.EMPTY),
            Map.entry("\u0007", StringUtils.EMPTY),
            Map.entry("\u0008", StringUtils.EMPTY),
            Map.entry("\u000b", StringUtils.EMPTY),
            Map.entry("\u000c", StringUtils.EMPTY),
            Map.entry("\u000e", StringUtils.EMPTY),
            Map.entry("\u000f", StringUtils.EMPTY),
            Map.entry("\u0010", StringUtils.EMPTY),
            Map.entry("\u0011", StringUtils.EMPTY),
            Map.entry("\u0012", StringUtils.EMPTY),
            Map.entry("\u0013", StringUtils.EMPTY),
            Map.entry("\u0014", StringUtils.EMPTY),
            Map.entry("\u0015", StringUtils.EMPTY),
            Map.entry("\u0016", StringUtils.EMPTY),
            Map.entry("\u0017", StringUtils.EMPTY),
            Map.entry("\u0018", StringUtils.EMPTY),
            Map.entry("\u0019", StringUtils.EMPTY),
            Map.entry("\u001a", StringUtils.EMPTY),
            Map.entry("\u001b", StringUtils.EMPTY),
            Map.entry("\u001c", StringUtils.EMPTY),
            Map.entry("\u001d", StringUtils.EMPTY),
            Map.entry("\u001e", StringUtils.EMPTY),
            Map.entry("\u001f", StringUtils.EMPTY),
            Map.entry("\ufffe", StringUtils.EMPTY),
            Map.entry("\uffff", StringUtils.EMPTY)
        )),
        NumericEntityEscaper.between(0x7f, 0x84),
        NumericEntityEscaper.between(0x86, 0x9f),
        new UnicodeUnpairedSurrogateRemover()
          );

  /**
   * 用于转义XML 1.1的转换器对象。
   * <p>
   * 虽然{@link #escapeXml11(String)}是预期的使用方法，但此对象允许XML转义
   * 功能用作自定义转换器的基础。
   */
  public static final CharSequenceTranslator ESCAPE_XML11 =
      new AggregateTranslator(
        new LookupTranslator(EntityArrays.BASIC_ESCAPE),
        new LookupTranslator(EntityArrays.APOS_ESCAPE),
        new LookupTranslator(Map.of(
            "\u0000", StringUtils.EMPTY,
            "\u000b", "&#11;",
            "\u000c", "&#12;",
            "\ufffe", StringUtils.EMPTY,
            "\uffff", StringUtils.EMPTY
        )),
        NumericEntityEscaper.between(0x1, 0x8),
        NumericEntityEscaper.between(0xe, 0x1f),
        NumericEntityEscaper.between(0x7f, 0x84),
        NumericEntityEscaper.between(0x86, 0x9f),
        new UnicodeUnpairedSurrogateRemover()
    );

  /**
   * 用于转义HTML 3.0版本的转换器对象。
   * <p>
   * 虽然{@link #escapeHtml3(String)}是预期的使用方法，但此对象允许HTML转义
   * 功能用作自定义转换器的基础。
   */
  public static final CharSequenceTranslator ESCAPE_HTML3 =
      new AggregateTranslator(
          new LookupTranslator(EntityArrays.BASIC_ESCAPE),
          new LookupTranslator(EntityArrays.ISO8859_1_ESCAPE)
      );

  /**
   * 用于转义HTML 4.0版本的转换器对象。
   * <p>
   * 虽然{@link #escapeHtml4(String)}是预期的使用方法，但此对象允许HTML转义
   * 功能用作自定义转换器的基础。
   */
  public static final CharSequenceTranslator ESCAPE_HTML4 =
      new AggregateTranslator(
          new LookupTranslator(EntityArrays.BASIC_ESCAPE),
          new LookupTranslator(EntityArrays.ISO8859_1_ESCAPE),
          new LookupTranslator(EntityArrays.HTML40_EXTENDED_ESCAPE)
      );

  /**
   * 用于转义各个逗号分隔值的转换器对象。
   * <p>
   * 虽然{@link #escapeCsv(String)}是预期的使用方法，但此对象允许CSV转义
   * 功能用作自定义转换器的基础。
   */
  public static final CharSequenceTranslator ESCAPE_CSV = new CsvEscaper();

  /* UNESCAPE TRANSLATORS */

  /**
   * 用于转义Shell命令语言的转换器对象。
   * <p>
   * @see <a href="http://pubs.opengroup.org/onlinepubs/7908799/xcu/chap2.html">Shell Command Language</a>
   */
  public static final CharSequenceTranslator ESCAPE_XSI =
      new LookupTranslator(Map.ofEntries(
          Map.entry("|", "\\|"),
          Map.entry("&", "\\&"),
          Map.entry(";", "\\;"),
          Map.entry("<", "\\<"),
          Map.entry(">", "\\>"),
          Map.entry("(", "\\("),
          Map.entry(")", "\\)"),
          Map.entry("$", "\\$"),
          Map.entry("`", "\\`"),
          Map.entry("\\", "\\\\"),
          Map.entry("\"", "\\\""),
          Map.entry("'", "\\'"),
          Map.entry(" ", "\\ "),
          Map.entry("\t", "\\\t"),
          Map.entry("\r\n", StringUtils.EMPTY),
          Map.entry("\n", StringUtils.EMPTY),
          Map.entry("*", "\\*"),
          Map.entry("?", "\\?"),
          Map.entry("[", "\\["),
          Map.entry("#", "\\#"),
          Map.entry("~", "\\~"),
          Map.entry("=", "\\="),
          Map.entry("%", "\\%"))
    );

  /**
   * 用于反转义Java的转换器对象。
   * <p>
   * 虽然{@link #unescapeJava(String)}是预期的使用方法，但此对象允许Java反转义
   * 功能用作自定义转换器的基础。
   */
  public static final CharSequenceTranslator UNESCAPE_JAVA =
      new AggregateTranslator(
        new OctalUnescaper(),     // .between('\1', '\377'),
        new UnicodeUnescaper(),
        new LookupTranslator(EntityArrays.JAVA_CTRL_CHARS_UNESCAPE),
        new LookupTranslator(Map.of(
            "\\\\", "\\",
            "\\\"", "\"",
            "\\'", "'",
            "\\", StringUtils.EMPTY
        ))
    );

  /**
   * 用于反转义EcmaScript的转换器对象。
   * <p>
   * 虽然{@link #unescapeEcmaScript(String)}是预期的使用方法，但此对象允许EcmaScript反转义
   * 功能用作自定义转换器的基础。
   */
  public static final CharSequenceTranslator UNESCAPE_ECMASCRIPT = UNESCAPE_JAVA;

  /**
   * 用于反转义Json的转换器对象。
   * <p>
   * 虽然{@link #unescapeJson(String)}是预期的使用方法，但此对象允许Json反转义
   * 功能用作自定义转换器的基础。
   */
  public static final CharSequenceTranslator UNESCAPE_JSON = UNESCAPE_JAVA;

  /**
   * 用于反转义HTML 3.0的转换器对象。
   * <p>
   * 虽然{@link #unescapeHtml3(String)}是预期的使用方法，但此对象允许HTML反转义
   * 功能用作自定义转换器的基础。
   */
  public static final CharSequenceTranslator UNESCAPE_HTML3 =
      new AggregateTranslator(
          new LookupTranslator(EntityArrays.BASIC_UNESCAPE),
          new LookupTranslator(EntityArrays.ISO8859_1_UNESCAPE),
          new NumericEntityUnescaper()
      );

  /**
   * 用于反转义HTML 4.0的转换器对象。
   * <p>
   * 虽然{@link #unescapeHtml4(String)}是预期的使用方法，但此对象允许HTML反转义
   * 功能用作自定义转换器的基础。
   */
  public static final CharSequenceTranslator UNESCAPE_HTML4 =
      new AggregateTranslator(
          new LookupTranslator(EntityArrays.BASIC_UNESCAPE),
          new LookupTranslator(EntityArrays.ISO8859_1_UNESCAPE),
          new LookupTranslator(EntityArrays.HTML40_EXTENDED_UNESCAPE),
          new NumericEntityUnescaper()
      );

  /**
   * 用于反转义XML的转换器对象。
   * <p>
   * 虽然{@link #unescapeXml(String)}是预期的使用方法，但此对象允许XML反转义
   * 功能用作自定义转换器的基础。
   */
  public static final CharSequenceTranslator UNESCAPE_XML =
      new AggregateTranslator(
          new LookupTranslator(EntityArrays.BASIC_UNESCAPE),
          new LookupTranslator(EntityArrays.APOS_UNESCAPE),
          new NumericEntityUnescaper()
      );

  /**
   * 用于转义Java正则表达式的转换器对象。
   */
  public static final CharSequenceTranslator ESCAPE_JAVA_REGEX =
      new LookupTranslator(EntityArrays.JAVA_REGEX_ESCAPE);

  /**
   * 用于反转义Java正则表达式的转换器对象。
   */
  public static final CharSequenceTranslator UNESCAPE_JAVA_REGEX =
      new LookupTranslator(EntityArrays.JAVA_REGEX_UNESCAPE);

  /**
   * 用于反转义逗号分隔值条目的转换器对象。
   * <p>
   * 虽然{@link #unescapeCsv(String)}是预期的使用方法，但此对象允许CSV反转义
   * 功能用作自定义转换器的基础。
   */
  public static final CharSequenceTranslator UNESCAPE_CSV = new CsvUnescaper();

  /* Helper functions */

  /**
   * 用于反转义XSI值条目的转换器对象。
   * <p>
   * 虽然{@link #unescapeXSI(String)}是预期的使用方法，但此对象允许XSI反转义
   * 功能用作自定义转换器的基础。
   */
  public static final CharSequenceTranslator UNESCAPE_XSI = new XsiUnescaper();

  /**
   * 获取一个{@link Builder}。
   * @param translator 文本转换器
   * @return {@link Builder}
   */
  public static EscapeUtils.Builder builder(final CharSequenceTranslator translator) {
    return new Builder(translator);
  }

  /**
   * 返回一个用双引号括起来的CSV列的{@code String}值（如果需要）。
   * <p>
   * 如果值包含逗号、换行符或双引号，则返回用双引号括起来的字符串值。
   * <p>
   * 值中的任何双引号字符都会被另一个双引号转义。
   * <p>
   * 如果值不包含逗号、换行符或双引号，则返回的字符串值保持不变。
   * <p>
   * 参见<a href="http://en.wikipedia.org/wiki/Comma-separated_values">Wikipedia</a>和
   * <a href="http://tools.ietf.org/html/rfc4180">RFC 4180</a>。
   *
   * @param input 输入的CSV列字符串，可能为null
   * @return 输入字符串，如果值包含逗号、换行符或双引号则用双引号括起来，
   * 如果输入字符串为null则返回{@code null}
   */
  @Nullable
  public static String escapeCsv(@Nullable final String input) {
    return ESCAPE_CSV.transform(input);
  }

  /**
   * 使用EcmaScript字符串规则转义{@code String}中的字符。
   *
   * <p>将找到的任何值转义成它们的EcmaScript字符串形式。
   * 正确处理引号和控制字符（制表符、反斜线、回车、换页等）。</p>
   *
   * <p>所以制表符变成字符{@code '\\'}和{@code 't'}。</p>
   *
   * <p>Java字符串和EcmaScript字符串之间唯一的区别是，
   * 在EcmaScript中，单引号和正斜线（/）会被转义。</p>
   *
   * <p>请注意，EcmaScript最著名的是JavaScript和ActionScript方言。</p>
   *
   * <p>示例：</p>
   * <pre>
   * input string: He didn't say, "Stop!"
   * output string: He didn\'t say, \"Stop!\"
   * </pre>
   *
   * <b>安全注意事项。</b>我们只在此方法中提供反斜线转义。例如，{@code '\"'}的输出是
   * {@code '\\\"'}，这在被转义的字符串在HTML标签中使用时（如{@code <select onmouseover="..." />}）
   * 可能会导致潜在问题。如果您希望进行更严格的字符串转义，可以考虑使用
   * <a href="https://www.owasp.org/index.php/Category:OWASP_Enterprise_Security_API_JAVA">ESAPI Libraries</a>。
   * 此外，您可以查看<a href="https://github.com/esapi">ESAPI GitHub Org</a>。
   *
   * @param input 要转义值的字符串，可能为null
   * @return 包含转义值的字符串，如果输入字符串为null则返回{@code null}
   */
  @Nullable
  public static String escapeEcmaScript(@Nullable final String input) {
    return ESCAPE_ECMASCRIPT.transform(input);
  }

  /**
   * 使用HTML实体转义{@code String}中的字符。
   *
   * <p>仅支持HTML 3.0实体。</p>
   *
   * @param input 要转义的{@code String}，可能为null
   * @return 新的转义后的{@code String}，如果输入字符串为null则返回{@code null}
   */
  @Nullable
  public static String escapeHtml3(@Nullable final String input) {
    return ESCAPE_HTML3.transform(input);
  }

  // HTML and XML
  /**
   * 使用HTML实体转义{@code String}中的字符。
   *
   * <p>
   * 例如：
   * </p>
   * <p>{@code "bread" &amp; "butter"}</p>
   * 变成：
   * <p>
   * {@code &quot;bread&quot; &amp;amp; &quot;butter&quot;}。
   * </p>
   *
   * <p>支持所有已知的HTML 4.0实体，包括奇特的重音符号。
   * 请注意，常用的撇号转义字符（&amp;apos;）
   * 不是合法实体，因此不受支持。</p>
   *
   * @param input 要转义的{@code String}，可能为null
   * @return 新的转义后的{@code String}，如果输入字符串为null则返回{@code null}
   *
   * @see <a href="http://hotwired.lycos.com/webmonkey/reference/special_characters/">ISO Entities</a>
   * @see <a href="http://www.w3.org/TR/REC-html32#latin1">HTML 3.2 Character Entities for ISO Latin-1</a>
   * @see <a href="http://www.w3.org/TR/REC-html40/sgml/entities.html">HTML 4.0 Character entity references</a>
   * @see <a href="http://www.w3.org/TR/html401/charset.html#h-5.3">HTML 4.01 Character References</a>
   * @see <a href="http://www.w3.org/TR/html401/charset.html#code-position">HTML 4.01 Code positions</a>
   */
  @Nullable
  public static String escapeHtml4(@Nullable final String input) {
    return ESCAPE_HTML4.transform(input);
  }

  // Java and JavaScript
  /**
   * 使用Java字符串规则转义{@code String}中的字符。
   *
   * <p>正确处理引号和控制字符（制表符、反斜线、回车、换页等）。</p>
   *
   * <p>所以制表符变成字符{@code '\\'}和{@code 't'}。</p>
   *
   * <p>Java字符串和JavaScript字符串之间唯一的区别是，
   * 在JavaScript中，单引号和正斜线（/）会被转义。</p>
   *
   * <p>示例：</p>
   * <pre>
   * input string: He didn't say, "Stop!"
   * output string: He didn't say, \"Stop!\"
   * </pre>
   *
   * @param input 要转义值的字符串，可能为null
   * @return 包含转义值的字符串，如果输入字符串为null则返回{@code null}
   */
  @Nullable
  public static String escapeJava(@Nullable final String input) {
    return ESCAPE_JAVA.transform(input);
  }

  /**
   * 使用Json字符串规则转义{@code String}中的字符。
   *
   * <p>将找到的任何值转义成它们的Json字符串形式。
   * 正确处理引号和控制字符（制表符、反斜线、回车、换页等）。</p>
   *
   * <p>所以制表符变成字符{@code '\\'}和{@code 't'}。</p>
   *
   * <p>Java字符串和Json字符串之间唯一的区别是，
   * 在Json中，正斜线（/）会被转义。</p>
   *
   * <p>有关更多详细信息，请参见<a href="http://www.ietf.org/rfc/rfc4627.txt">RFC 4627</a>。</p>
   *
   * <p>示例：</p>
   * <pre>
   * input string: He didn't say, "Stop!"
   * output string: He didn't say, \"Stop!\"
   * </pre>
   *
   * @param input 要转义值的字符串，可能为null
   * @return 包含转义值的字符串，如果输入字符串为null则返回{@code null}
   */
  @Nullable
  public static String escapeJson(@Nullable final String input) {
    return ESCAPE_JSON.transform(input);
  }

  /**
   * 使用XML实体转义{@code String}中的字符。
   *
   * <p>例如：{@code "bread" & "butter"} =&gt;
   * {@code &quot;bread&quot; &amp; &quot;butter&quot;}。
   * </p>
   *
   * <p>请注意，XML 1.0是纯文本格式：即使在转义后，它也无法表示控制字符
   * 或不成对的Unicode代理码点。{@code escapeXml10}将删除不符合
   * 以下范围的字符：</p>
   *
   * <p>{@code #x9 | #xA | #xD | [#x20-#xD7FF] | [#xE000-#xFFFD] | [#x10000-#x10FFFF]}</p>
   *
   * <p>虽然不是严格必要的，{@code escapeXml10}将转义
   * 以下范围内的字符：</p>
   *
   * <p>{@code [#x7F-#x84] | [#x86-#x9F]}</p>
   *
   * <p>返回的字符串可以插入到有效的XML 1.0或XML 1.1文档中。
   * 如果您想在XML 1.1文档中允许更多非文本字符，请使用{@link #escapeXml11(String)}。</p>
   *
   * @param input 要转义的{@code String}，可能为null
   * @return 新的转义后的{@code String}，如果输入字符串为null则返回{@code null}
   * @see #unescapeXml(String)
   */
  @Nullable
  public static String escapeXml10(@Nullable final String input) {
    return ESCAPE_XML10.transform(input);
  }

  /**
   * 使用XML实体转义{@code String}中的字符。
   *
   * <p>例如：{@code "bread" & "butter"} =&gt;
   * {@code &quot;bread&quot; &amp; &quot;butter&quot;}。
   * </p>
   *
   * <p>XML 1.1可以表示某些控制字符，但即使在转义后，它也无法表示
   * 空字节或不成对的Unicode代理码点。{@code escapeXml11}将删除不符合
   * 以下范围的字符：</p>
   *
   * <p>{@code [#x1-#xD7FF] | [#xE000-#xFFFD] | [#x10000-#x10FFFF]}</p>
   *
   * <p>{@code escapeXml11}将转义以下范围内的字符：</p>
   *
   * <p>{@code [#x1-#x8] | [#xB-#xC] | [#xE-#x1F] | [#x7F-#x84] | [#x86-#x9F]}</p>
   *
   * <p>返回的字符串可以插入到有效的XML 1.1文档中。
   * 不要将它用于XML 1.0文档。</p>
   *
   * @param input 要转义的{@code String}，可能为null
   * @return 新的转义后的{@code String}，如果输入字符串为null则返回{@code null}
   * @see #unescapeXml(String)
   */
  @Nullable
  public static String escapeXml11(@Nullable final String input) {
    return ESCAPE_XML11.transform(input);
  }

  /**
   * 使用XSI规则转义{@code String}中的字符。
   *
   * <p><b>当心！</b>在大多数情况下，您不想转义shell命令，而是使用
   * {@link java.lang.ProcessBuilder}或{@link java.lang.Runtime#exec(String[])}
   * 提供的多参数方法。</p>
   *
   * <p>示例：</p>
   * <pre>
   * input string: He didn't say, "Stop!"
   * output string: He\ didn\'t\ say,\ \"Stop!\"
   * </pre>
   *
   * @see <a href="http://pubs.opengroup.org/onlinepubs/7908799/xcu/chap2.html">Shell Command Language</a>
   * @param input 要转义值的字符串，可能为null
   * @return 包含转义值的字符串，如果输入字符串为null则返回{@code null}
   */
  @Nullable
  public static String escapeXSI(@Nullable final String input) {
    return ESCAPE_XSI.transform(input);
  }

  /**
   * 使用Java正则表达式规则转义{@code String}中的字符。
   *
   * @param input
   *     要转义的输入字符串，可能为{@code null}。
   * @return
   *     包含转义值的字符串，如果输入字符串为null则返回{@code null}。
   */
  @Nullable
  public static String escapeJavaRegex(@Nullable final String input) {
    return ESCAPE_JAVA_REGEX.transform(input);
  }

  /**
   * 返回反转义后的CSV列的{@code String}值。
   *
   * <p>如果值用双引号括起来，并且包含逗号、换行符或双引号，则删除引号。
   * </p>
   *
   * <p>任何双引号转义字符（一对双引号）都被反转义为一个双引号。</p>
   *
   * <p>如果值没有用双引号括起来，或者用了双引号但不包含逗号、换行符或双引号，
   * 则返回的字符串值保持不变。</p>
   *
   * 参见<a href="http://en.wikipedia.org/wiki/Comma-separated_values">Wikipedia</a>和
   * <a href="http://tools.ietf.org/html/rfc4180">RFC 4180</a>。
   *
   * @param input 输入的CSV列字符串，可能为null
   * @return 输入字符串，删除了括起来的双引号并反转义了嵌入的双引号，
   * 如果输入字符串为null则返回{@code null}
   */
  @Nullable
  public static String unescapeCsv(@Nullable final String input) {
    return UNESCAPE_CSV.transform(input);
  }

  /**
   * 反转义{@code String}中找到的任何EcmaScript字面量。
   *
   * <p>例如，它将把{@code '\'}和{@code 'n'}的序列转换为换行符，
   * 除非{@code '\'}前面有另一个{@code '\'}。</p>
   *
   * @see #unescapeJava(String)
   * @param input 要反转义的{@code String}，可能为null
   * @return 新的反转义后的{@code String}，如果输入字符串为null则返回{@code null}
   */
  @Nullable
  public static String unescapeEcmaScript(@Nullable final String input) {
    return UNESCAPE_ECMASCRIPT.transform(input);
  }

  /**
   * 将包含实体转义的字符串反转义为包含与转义对应的
   * 实际Unicode字符的字符串。仅支持HTML 3.0实体。
   *
   * @param input 要反转义的{@code String}，可能为null
   * @return 新的反转义后的{@code String}，如果输入字符串为null则返回{@code null}
   */
  @Nullable
  public static String unescapeHtml3(@Nullable final String input) {
    return UNESCAPE_HTML3.transform(input);
  }

  /**
   * 将包含实体转义的字符串反转义为包含与转义对应的
   * 实际Unicode字符的字符串。支持HTML 4.0实体。
   *
   * <p>例如，字符串{@code "&lt;Fran&ccedil;ais&gt;"}
   * 将变成{@code "<Français>"}。</p>
   *
   * <p>如果实体无法识别，它会保持原样，并逐字插入到结果字符串中。
   * 例如{@code "&gt;&zzzz;x"}将变成{@code ">&zzzz;x"}。</p>
   *
   * @param input 要反转义的{@code String}，可能为null
   * @return 新的反转义后的{@code String}，如果输入字符串为null则返回{@code null}
   */
  @Nullable
  public static String unescapeHtml4(@Nullable final String input) {
    return UNESCAPE_HTML4.transform(input);
  }

  /**
   * 反转义{@code String}中找到的任何Java字面量。
   * 例如，它将把{@code '\'}和{@code 'n'}的序列转换为换行符，
   * 除非{@code '\'}前面有另一个{@code '\'}。
   *
   * @param input 要反转义的{@code String}，可能为null
   * @return 新的反转义后的{@code String}，如果输入字符串为null则返回{@code null}
   */
  @Nullable
  public static String unescapeJava(@Nullable final String input) {
    return UNESCAPE_JAVA.transform(input);
  }

  /**
   * 反转义{@code String}中找到的任何Json字面量。
   *
   * <p>例如，它将把{@code '\'}和{@code 'n'}的序列转换为换行符，
   * 除非{@code '\'}前面有另一个{@code '\'}。</p>
   *
   * @see #unescapeJava(String)
   * @param input 要反转义的{@code String}，可能为null
   * @return 新的反转义后的{@code String}，如果输入字符串为null则返回{@code null}
   */
  @Nullable
  public static String unescapeJson(@Nullable final String input) {
    return UNESCAPE_JSON.transform(input);
  }

  /**
   * 将包含XML实体转义的字符串反转义为包含与转义对应的
   * 实际Unicode字符的字符串。
   *
   * <p>仅支持五个基本XML实体（gt、lt、quot、amp、apos）。
   * 不支持DTD或外部实体。</p>
   *
   * <p>请注意，数字\\u Unicode码会被反转义为其相应的
   * Unicode字符。这在将来的版本中可能会改变。</p>
   *
   * @param input 要反转义的{@code String}，可能为null
   * @return 新的反转义后的{@code String}，如果输入字符串为null则返回{@code null}
   * @see #escapeXml10(String)
   * @see #escapeXml11(String)
   */
  @Nullable
  public static String unescapeXml(@Nullable final String input) {
    return UNESCAPE_XML.transform(input);
  }

  /**
   * 使用XSI规则反转义{@code String}中的字符。
   *
   * @see EscapeUtils#escapeXSI(String)
   * @param input 要反转义的{@code String}，可能为null
   * @return 新的反转义后的{@code String}，如果输入字符串为null则返回{@code null}
   */
  @Nullable
  public static String unescapeXSI(@Nullable final String input) {
    return UNESCAPE_XSI.transform(input);
  }

  /**
   * 使用Java正则表达式规则反转义{@code String}中的字符。
   *
   * @param input
   *     要反转义的{@code String}，可能为null。
   * @return
   *     新的反转义后的{@code String}，如果输入字符串为null则返回{@code null}。
   */
  @Nullable
  public static String unescapeJavaRegex(@Nullable final String input) {
    return UNESCAPE_JAVA_REGEX.transform(input);
  }
}