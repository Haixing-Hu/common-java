////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text;

import java.util.Map;

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
 * Escapes and unescapes {@code String}s for Java, Java Script, HTML and XML.
 *
 * @author Haixing Hu
 */
@ThreadSafe
public class EscapeUtils {

  private EscapeUtils() {}

  /**
   * Convenience wrapper for {@link java.lang.StringBuilder} providing escaping
   * methods.
   *
   * <p>Example:</p>
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
     * StringBuilder to be used in the Builder class.
     */
    private final StringBuilder builder;

    /**
     * CharSequenceTranslator to be used in the Builder class.
     */
    private final CharSequenceTranslator translator;

    /**
     * Builder constructor.
     *
     * @param translator a CharSequenceTranslator.
     */
    private Builder(final CharSequenceTranslator translator) {
      this.builder = new StringBuilder();
      this.translator = translator;
    }

    /**
     * Literal append, no escaping being done.
     *
     * @param input the String to append
     * @return {@code this}, to enable chaining
     */
    public Builder append(final String input) {
      builder.append(input);
      return this;
    }

    /**
     * Escape {@code input} according to the given {@link CharSequenceTranslator}.
     *
     * @param input the String to escape
     * @return {@code this}, to enable chaining
     */
    public Builder escape(final String input) {
      translator.transform(input, builder);
      return this;
    }

    /**
     * Return the escaped string.
     *
     * @return The escaped string
     */
    @Override
    public String toString() {
      return builder.toString();
    }
  }

  /**
   * Translator object for escaping Java.
   *
   * While {@link #escapeJava(String)} is the expected method of use, this
   * object allows the Java escaping functionality to be used
   * as the foundation for a custom translator.
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
   * Translator object for escaping EcmaScript/JavaScript.
   *
   * While {@link #escapeEcmaScript(String)} is the expected method of use, this
   * object allows the EcmaScript escaping functionality to be used
   * as the foundation for a custom translator.
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
   * Translator object for escaping Json.
   *
   * While {@link #escapeJson(String)} is the expected method of use, this
   * object allows the Json escaping functionality to be used
   * as the foundation for a custom translator.
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
   * Translator object for escaping XML 1.0.
   *
   * While {@link #escapeXml10(String)} is the expected method of use, this
   * object allows the XML escaping functionality to be used
   * as the foundation for a custom translator.
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
   * Translator object for escaping XML 1.1.
   *
   * While {@link #escapeXml11(String)} is the expected method of use, this
   * object allows the XML escaping functionality to be used
   * as the foundation for a custom translator.
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
   * Translator object for escaping HTML version 3.0.
   *
   * While {@link #escapeHtml3(String)} is the expected method of use, this
   * object allows the HTML escaping functionality to be used
   * as the foundation for a custom translator.
   */
  public static final CharSequenceTranslator ESCAPE_HTML3 =
      new AggregateTranslator(
          new LookupTranslator(EntityArrays.BASIC_ESCAPE),
          new LookupTranslator(EntityArrays.ISO8859_1_ESCAPE)
      );

  /**
   * Translator object for escaping HTML version 4.0.
   *
   * While {@link #escapeHtml4(String)} is the expected method of use, this
   * object allows the HTML escaping functionality to be used
   * as the foundation for a custom translator.
   */
  public static final CharSequenceTranslator ESCAPE_HTML4 =
      new AggregateTranslator(
          new LookupTranslator(EntityArrays.BASIC_ESCAPE),
          new LookupTranslator(EntityArrays.ISO8859_1_ESCAPE),
          new LookupTranslator(EntityArrays.HTML40_EXTENDED_ESCAPE)
      );
  /**
   * Translator object for escaping individual Comma Separated Values.
   *
   * While {@link #escapeCsv(String)} is the expected method of use, this
   * object allows the CSV escaping functionality to be used
   * as the foundation for a custom translator.
   */
  public static final CharSequenceTranslator ESCAPE_CSV = new CsvEscaper();

  /* UNESCAPE TRANSLATORS */

  /**
   * Translator object for escaping Shell command language.
   *
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
   * Translator object for unescaping escaped Java.
   *
   * While {@link #unescapeJava(String)} is the expected method of use, this
   * object allows the Java unescaping functionality to be used
   * as the foundation for a custom translator.
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
   * Translator object for unescaping escaped EcmaScript.
   *
   * While {@link #unescapeEcmaScript(String)} is the expected method of use, this
   * object allows the EcmaScript unescaping functionality to be used
   * as the foundation for a custom translator.
   */
  public static final CharSequenceTranslator UNESCAPE_ECMASCRIPT = UNESCAPE_JAVA;

  /**
   * Translator object for unescaping escaped Json.
   *
   * While {@link #unescapeJson(String)} is the expected method of use, this
   * object allows the Json unescaping functionality to be used
   * as the foundation for a custom translator.
   */
  public static final CharSequenceTranslator UNESCAPE_JSON = UNESCAPE_JAVA;

  /**
   * Translator object for unescaping escaped HTML 3.0.
   *
   * While {@link #unescapeHtml3(String)} is the expected method of use, this
   * object allows the HTML unescaping functionality to be used
   * as the foundation for a custom translator.
   */
  public static final CharSequenceTranslator UNESCAPE_HTML3 =
      new AggregateTranslator(
          new LookupTranslator(EntityArrays.BASIC_UNESCAPE),
          new LookupTranslator(EntityArrays.ISO8859_1_UNESCAPE),
          new NumericEntityUnescaper()
      );

  /**
   * Translator object for unescaping escaped HTML 4.0.
   *
   * While {@link #unescapeHtml4(String)} is the expected method of use, this
   * object allows the HTML unescaping functionality to be used
   * as the foundation for a custom translator.
   */
  public static final CharSequenceTranslator UNESCAPE_HTML4 =
      new AggregateTranslator(
          new LookupTranslator(EntityArrays.BASIC_UNESCAPE),
          new LookupTranslator(EntityArrays.ISO8859_1_UNESCAPE),
          new LookupTranslator(EntityArrays.HTML40_EXTENDED_UNESCAPE),
          new NumericEntityUnescaper()
      );

  /**
   * Translator object for unescaping escaped XML.
   *
   * While {@link #unescapeXml(String)} is the expected method of use, this
   * object allows the XML unescaping functionality to be used
   * as the foundation for a custom translator.
   */
  public static final CharSequenceTranslator UNESCAPE_XML =
      new AggregateTranslator(
          new LookupTranslator(EntityArrays.BASIC_UNESCAPE),
          new LookupTranslator(EntityArrays.APOS_UNESCAPE),
          new NumericEntityUnescaper()
      );

  /**
   * Translator object for unescaping escaped Comma Separated Value entries.
   *
   * While {@link #unescapeCsv(String)} is the expected method of use, this
   * object allows the CSV unescaping functionality to be used
   * as the foundation for a custom translator.
   */
  public static final CharSequenceTranslator UNESCAPE_CSV = new CsvUnescaper();

  /* Helper functions */

  /**
   * Translator object for unescaping escaped XSI Value entries.
   *
   * While {@link #unescapeXSI(String)}  is the expected method of use, this
   * object allows the XSI unescaping functionality to be used
   * as the foundation for a custom translator.
   */
  public static final CharSequenceTranslator UNESCAPE_XSI = new XsiUnescaper();

  /**
   * Get a {@link Builder}.
   * @param translator the text translator
   * @return {@link Builder}
   */
  public static EscapeUtils.Builder builder(final CharSequenceTranslator translator) {
    return new Builder(translator);
  }

  /**
   * Returns a {@code String} value for a CSV column enclosed in double quotes,
   * if required.
   *
   * <p>If the value contains a comma, newline or double quote, then the
   *    String value is returned enclosed in double quotes.</p>
   *
   * <p>Any double quote characters in the value are escaped with another double quote.</p>
   *
   * <p>If the value does not contain a comma, newline or double quote, then the
   *    String value is returned unchanged.</p>
   *
   * see <a href="http://en.wikipedia.org/wiki/Comma-separated_values">Wikipedia</a> and
   * <a href="http://tools.ietf.org/html/rfc4180">RFC 4180</a>.
   *
   * @param input the input CSV column String, may be null
   * @return The input String, enclosed in double quotes if the value contains a comma,
   * newline or double quote, {@code null} if null string input
   */
  public static String escapeCsv(final String input) {
    return ESCAPE_CSV.transform(input);
  }

  /**
   * Escapes the characters in a {@code String} using EcmaScript String rules.
   *
   * <p>Escapes any values it finds into their EcmaScript String form.
   * Deals correctly with quotes and control-chars (tab, backslash, cr, ff, etc.) </p>
   *
   * <p>So a tab becomes the characters {@code '\\'} and
   * {@code 't'}.</p>
   *
   * <p>The only difference between Java strings and EcmaScript strings
   * is that in EcmaScript, a single quote and forward-slash (/) are escaped.</p>
   *
   * <p>Note that EcmaScript is best known by the JavaScript and ActionScript dialects.</p>
   *
   * <p>Example:</p>
   * <pre>
   * input string: He didn't say, "Stop!"
   * output string: He didn\'t say, \"Stop!\"
   * </pre>
   *
   * <b>Security Note.</b> We only provide backslash escaping in this method. For example, {@code '\"'} has the output
   * {@code '\\\"'} which could result in potential issues in the case where the string being escaped is being used
   * in an HTML tag like {@code <select onmouseover="..." />}. If you wish to have more rigorous string escaping, you
   * may consider the
   * <a href="https://www.owasp.org/index.php/Category:OWASP_Enterprise_Security_API_JAVA">ESAPI Libraries</a>.
   * Further, you can view the <a href="https://github.com/esapi">ESAPI GitHub Org</a>.
   *
   * @param input  String to escape values in, may be null
   * @return String with escaped values, {@code null} if null string input
   */
  public static String escapeEcmaScript(final String input) {
    return ESCAPE_ECMASCRIPT.transform(input);
  }

  /**
   * Escapes the characters in a {@code String} using HTML entities.
   *
   * <p>Supports only the HTML 3.0 entities.</p>
   *
   * @param input  the {@code String} to escape, may be null
   * @return a new escaped {@code String}, {@code null} if null string input
   */
  public static String escapeHtml3(final String input) {
    return ESCAPE_HTML3.transform(input);
  }

  // HTML and XML
  /**
   * Escapes the characters in a {@code String} using HTML entities.
   *
   * <p>
   * For example:
   * </p>
   * <p>{@code "bread" &amp; "butter"}</p>
   * becomes:
   * <p>
   * {@code &quot;bread&quot; &amp;amp; &quot;butter&quot;}.
   * </p>
   *
   * <p>Supports all known HTML 4.0 entities, including funky accents.
   * Note that the commonly used apostrophe escape character (&amp;apos;)
   * is not a legal entity and so is not supported).</p>
   *
   * @param input  the {@code String} to escape, may be null
   * @return a new escaped {@code String}, {@code null} if null string input
   *
   * @see <a href="http://hotwired.lycos.com/webmonkey/reference/special_characters/">ISO Entities</a>
   * @see <a href="http://www.w3.org/TR/REC-html32#latin1">HTML 3.2 Character Entities for ISO Latin-1</a>
   * @see <a href="http://www.w3.org/TR/REC-html40/sgml/entities.html">HTML 4.0 Character entity references</a>
   * @see <a href="http://www.w3.org/TR/html401/charset.html#h-5.3">HTML 4.01 Character References</a>
   * @see <a href="http://www.w3.org/TR/html401/charset.html#code-position">HTML 4.01 Code positions</a>
   */
  public static String escapeHtml4(final String input) {
    return ESCAPE_HTML4.transform(input);
  }

  // Java and JavaScript
  /**
   * Escapes the characters in a {@code String} using Java String rules.
   *
   * <p>Deals correctly with quotes and control-chars (tab, backslash, cr, ff, etc.) </p>
   *
   * <p>So a tab becomes the characters {@code '\\'} and
   * {@code 't'}.</p>
   *
   * <p>The only difference between Java strings and JavaScript strings
   * is that in JavaScript, a single quote and forward-slash (/) are escaped.</p>
   *
   * <p>Example:</p>
   * <pre>
   * input string: He didn't say, "Stop!"
   * output string: He didn't say, \"Stop!\"
   * </pre>
   *
   * @param input  String to escape values in, may be null
   * @return String with escaped values, {@code null} if null string input
   */
  public static String escapeJava(final String input) {
    return ESCAPE_JAVA.transform(input);
  }

  /**
   * Escapes the characters in a {@code String} using Json String rules.
   *
   * <p>Escapes any values it finds into their Json String form.
   * Deals correctly with quotes and control-chars (tab, backslash, cr, ff, etc.) </p>
   *
   * <p>So a tab becomes the characters {@code '\\'} and
   * {@code 't'}.</p>
   *
   * <p>The only difference between Java strings and Json strings
   * is that in Json, forward-slash (/) is escaped.</p>
   *
   * <p>See http://www.ietf.org/rfc/rfc4627.txt for further details.</p>
   *
   * <p>Example:</p>
   * <pre>
   * input string: He didn't say, "Stop!"
   * output string: He didn't say, \"Stop!\"
   * </pre>
   *
   * @param input  String to escape values in, may be null
   * @return String with escaped values, {@code null} if null string input
   */
  public static String escapeJson(final String input) {
    return ESCAPE_JSON.transform(input);
  }

  /**
   * Escapes the characters in a {@code String} using XML entities.
   *
   * <p>For example: {@code "bread" & "butter"} =&gt;
   * {@code &quot;bread&quot; &amp; &quot;butter&quot;}.
   * </p>
   *
   * <p>Note that XML 1.0 is a text-only format: it cannot represent control
   * characters or unpaired Unicode surrogate code points, even after escaping.
   * {@code escapeXml10} will remove characters that do not fit in the
   * following ranges:</p>
   *
   * <p>{@code #x9 | #xA | #xD | [#x20-#xD7FF] | [#xE000-#xFFFD] | [#x10000-#x10FFFF]}</p>
   *
   * <p>Though not strictly necessary, {@code escapeXml10} will escape
   * characters in the following ranges:</p>
   *
   * <p>{@code [#x7F-#x84] | [#x86-#x9F]}</p>
   *
   * <p>The returned string can be inserted into a valid XML 1.0 or XML 1.1
   * document. If you want to allow more non-text characters in an XML 1.1
   * document, use {@link #escapeXml11(String)}.</p>
   *
   * @param input  the {@code String} to escape, may be null
   * @return a new escaped {@code String}, {@code null} if null string input
   * @see #unescapeXml(String)
   */
  public static String escapeXml10(final String input) {
    return ESCAPE_XML10.transform(input);
  }

  /**
   * Escapes the characters in a {@code String} using XML entities.
   *
   * <p>For example: {@code "bread" & "butter"} =&gt;
   * {@code &quot;bread&quot; &amp; &quot;butter&quot;}.
   * </p>
   *
   * <p>XML 1.1 can represent certain control characters, but it cannot represent
   * the null byte or unpaired Unicode surrogate code points, even after escaping.
   * {@code escapeXml11} will remove characters that do not fit in the following
   * ranges:</p>
   *
   * <p>{@code [#x1-#xD7FF] | [#xE000-#xFFFD] | [#x10000-#x10FFFF]}</p>
   *
   * <p>{@code escapeXml11} will escape characters in the following ranges:</p>
   *
   * <p>{@code [#x1-#x8] | [#xB-#xC] | [#xE-#x1F] | [#x7F-#x84] | [#x86-#x9F]}</p>
   *
   * <p>The returned string can be inserted into a valid XML 1.1 document. Do not
   * use it for XML 1.0 documents.</p>
   *
   * @param input  the {@code String} to escape, may be null
   * @return a new escaped {@code String}, {@code null} if null string input
   * @see #unescapeXml(String)
   */
  public static String escapeXml11(final String input) {
    return ESCAPE_XML11.transform(input);
  }

  /**
   * Escapes the characters in a {@code String} using XSI rules.
   *
   * <p><b>Beware!</b> In most cases you don't want to escape shell commands but use multi-argument
   * methods provided by {@link java.lang.ProcessBuilder} or {@link java.lang.Runtime#exec(String[])}
   * instead.</p>
   *
   * <p>Example:</p>
   * <pre>
   * input string: He didn't say, "Stop!"
   * output string: He\ didn\'t\ say,\ \"Stop!\"
   * </pre>
   *
   * @see <a href="http://pubs.opengroup.org/onlinepubs/7908799/xcu/chap2.html">Shell Command Language</a>
   * @param input  String to escape values in, may be null
   * @return String with escaped values, {@code null} if null string input
   */
  public static String escapeXSI(final String input) {
    return ESCAPE_XSI.transform(input);
  }

  /**
   * Returns a {@code String} value for an unescaped CSV column.
   *
   * <p>If the value is enclosed in double quotes, and contains a comma, newline
   *    or double quote, then quotes are removed.
   * </p>
   *
   * <p>Any double quote escaped characters (a pair of double quotes) are unescaped
   *    to just one double quote.</p>
   *
   * <p>If the value is not enclosed in double quotes, or is and does not contain a
   *    comma, newline or double quote, then the String value is returned unchanged.</p>
   *
   * see <a href="http://en.wikipedia.org/wiki/Comma-separated_values">Wikipedia</a> and
   * <a href="http://tools.ietf.org/html/rfc4180">RFC 4180</a>.
   *
   * @param input the input CSV column String, may be null
   * @return The input String, with enclosing double quotes removed and embedded double
   * quotes unescaped, {@code null} if null string input
   */
  public static String unescapeCsv(final String input) {
    return UNESCAPE_CSV.transform(input);
  }

  /**
   * Unescapes any EcmaScript literals found in the {@code String}.
   *
   * <p>For example, it will turn a sequence of {@code '\'} and {@code 'n'}
   * into a newline character, unless the {@code '\'} is preceded by another
   * {@code '\'}.</p>
   *
   * @see #unescapeJava(String)
   * @param input  the {@code String} to unescape, may be null
   * @return A new unescaped {@code String}, {@code null} if null string input
   */
  public static String unescapeEcmaScript(final String input) {
    return UNESCAPE_ECMASCRIPT.transform(input);
  }

  /**
   * Unescapes a string containing entity escapes to a string
   * containing the actual Unicode characters corresponding to the
   * escapes. Supports only HTML 3.0 entities.
   *
   * @param input  the {@code String} to unescape, may be null
   * @return a new unescaped {@code String}, {@code null} if null string input
   */
  public static String unescapeHtml3(final String input) {
    return UNESCAPE_HTML3.transform(input);
  }

  /**
   * Unescapes a string containing entity escapes to a string
   * containing the actual Unicode characters corresponding to the
   * escapes. Supports HTML 4.0 entities.
   *
   * <p>For example, the string {@code "&lt;Fran&ccedil;ais&gt;"}
   * will become {@code "<Fran�ais>"}</p>
   *
   * <p>If an entity is unrecognized, it is left alone, and inserted
   * verbatim into the result string. e.g. {@code "&gt;&zzzz;x"} will
   * become {@code ">&zzzz;x"}.</p>
   *
   * @param input  the {@code String} to unescape, may be null
   * @return a new unescaped {@code String}, {@code null} if null string input
   */
  public static String unescapeHtml4(final String input) {
    return UNESCAPE_HTML4.transform(input);
  }

  /**
   * Unescapes any Java literals found in the {@code String}.
   * For example, it will turn a sequence of {@code '\'} and
   * {@code 'n'} into a newline character, unless the {@code '\'}
   * is preceded by another {@code '\'}.
   *
   * @param input  the {@code String} to unescape, may be null
   * @return a new unescaped {@code String}, {@code null} if null string input
   */
  public static String unescapeJava(final String input) {
    return UNESCAPE_JAVA.transform(input);
  }

  /**
   * Unescapes any Json literals found in the {@code String}.
   *
   * <p>For example, it will turn a sequence of {@code '\'} and {@code 'n'}
   * into a newline character, unless the {@code '\'} is preceded by another
   * {@code '\'}.</p>
   *
   * @see #unescapeJava(String)
   * @param input  the {@code String} to unescape, may be null
   * @return A new unescaped {@code String}, {@code null} if null string input
   */
  public static String unescapeJson(final String input) {
    return UNESCAPE_JSON.transform(input);
  }

  /**
   * Unescapes a string containing XML entity escapes to a string
   * containing the actual Unicode characters corresponding to the
   * escapes.
   *
   * <p>Supports only the five basic XML entities (gt, lt, quot, amp, apos).
   * Does not support DTDs or external entities.</p>
   *
   * <p>Note that numerical \\u Unicode codes are unescaped to their respective
   *    Unicode characters. This may change in future releases.</p>
   *
   * @param input  the {@code String} to unescape, may be null
   * @return a new unescaped {@code String}, {@code null} if null string input
   * @see #escapeXml10(String)
   * @see #escapeXml11(String)
   */
  public static String unescapeXml(final String input) {
    return UNESCAPE_XML.transform(input);
  }

  /**
   * Unescapes the characters in a {@code String} using XSI rules.
   *
   * @see EscapeUtils#escapeXSI(String)
   * @param input  the {@code String} to unescape, may be null
   * @return a new unescaped {@code String}, {@code null} if null string input
   */
  public static String unescapeXSI(final String input) {
    return UNESCAPE_XSI.transform(input);
  }
}
