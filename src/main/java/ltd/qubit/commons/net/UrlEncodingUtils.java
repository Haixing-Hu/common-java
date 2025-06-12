////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.net;

import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nullable;

import jakarta.validation.constraints.NotNull;

import ltd.qubit.commons.lang.Argument;
import ltd.qubit.commons.lang.CharUtils;
import ltd.qubit.commons.text.ParserCursor;
import ltd.qubit.commons.text.TokenParser;
import ltd.qubit.commons.util.buffer.CharBuffer;
import ltd.qubit.commons.util.pair.NameValuePair;

import static java.nio.charset.StandardCharsets.UTF_8;

import static ltd.qubit.commons.lang.ObjectUtils.defaultIfNull;

/**
 * URL编码/解码实用程序类。
 *
 * <p>该类的实现参考了
 * <a href="http://hc.apache.org/httpcomponents-client-ga/httpclient/apidocs/org/apache/http/client/utils/URLEncodedUtils.html">
 *   org.apache.http.client.utils.URLEncodedUtils
 * </a>
 * </p>
 *
 * @author 胡海星
 */
public class UrlEncodingUtils {

  public static final String CONTENT_TYPE = "application/x-www-form-urlencoded";
  private static final char QP_SEP_A = '&';
  private static final char QP_SEP_S = ';';
  private static final String NAME_VALUE_SEPARATOR = "=";
  private static final char PATH_SEPARATOR = '/';
  private static final BitSet PATH_SEPARATORS = new BitSet(256);
  private static final BitSet UNRESERVED;
  private static final BitSet PUNCT;
  private static final BitSet USERINFO;
  private static final BitSet PATHSAFE;
  private static final BitSet URIC;
  private static final BitSet RESERVED;
  private static final BitSet URLENCODER;
  private static final BitSet PATH_SPECIAL;
  private static final int RADIX = 16;

  // stop checkstyle: MagicNumber
  static {
    PATH_SEPARATORS.set(47);
    UNRESERVED = new BitSet(256);
    PUNCT = new BitSet(256);
    USERINFO = new BitSet(256);
    PATHSAFE = new BitSet(256);
    URIC = new BitSet(256);
    RESERVED = new BitSet(256);
    URLENCODER = new BitSet(256);
    PATH_SPECIAL = new BitSet(256);
    for (int i = 97; i <= 122; ++i) {
      UNRESERVED.set(i);
    }
    for (int i = 65; i <= 90; ++i) {
      UNRESERVED.set(i);
    }
    for (int i = 48; i <= 57; ++i) {
      UNRESERVED.set(i);
    }
    UNRESERVED.set(95);
    UNRESERVED.set(45);
    UNRESERVED.set(46);
    UNRESERVED.set(42);
    URLENCODER.or(UNRESERVED);
    UNRESERVED.set(33);
    UNRESERVED.set(126);
    UNRESERVED.set(39);
    UNRESERVED.set(40);
    UNRESERVED.set(41);
    PUNCT.set(44);
    PUNCT.set(59);
    PUNCT.set(58);
    PUNCT.set(36);
    PUNCT.set(38);
    PUNCT.set(43);
    PUNCT.set(61);
    USERINFO.or(UNRESERVED);
    USERINFO.or(PUNCT);
    PATHSAFE.or(UNRESERVED);
    PATHSAFE.set(59);
    PATHSAFE.set(58);
    PATHSAFE.set(64);
    PATHSAFE.set(38);
    PATHSAFE.set(61);
    PATHSAFE.set(43);
    PATHSAFE.set(36);
    PATHSAFE.set(44);
    PATH_SPECIAL.or(PATHSAFE);
    PATH_SPECIAL.set(47);
    RESERVED.set(59);
    RESERVED.set(47);
    RESERVED.set(63);
    RESERVED.set(58);
    RESERVED.set(64);
    RESERVED.set(38);
    RESERVED.set(61);
    RESERVED.set(43);
    RESERVED.set(36);
    RESERVED.set(44);
    RESERVED.set(91);
    RESERVED.set(93);
    URIC.or(RESERVED);
    URIC.or(UNRESERVED);
  }
  // resume checkstyle: MagicNumber

  private UrlEncodingUtils() {
  }

  /**
   * 返回一个 {@link NameValuePair} 列表，表示从URI查询字符串中解析出的键值对。
   *
   * @param uri
   *     要解析的URI。
   * @param charset
   *     用于解码的字符集，如果为 {@code null} 则使用UTF-8。
   * @return
   *     从URI查询字符串中解析出的 {@link NameValuePair} 列表。
   */
  public static List<NameValuePair> parse(final URI uri, @Nullable final Charset charset) {
    Argument.requireNonNull("URI", uri);
    final String query = uri.getRawQuery();
    if (query != null && !query.isEmpty()) {
      return parse(query, charset);
    } else {
      return Collections.emptyList();
    }
  }

  /**
   * 返回一个 {@link NameValuePair} 列表，表示从字符串中解析出的键值对。
   *
   * @param str
   *     要解析的字符串。
   * @param charset
   *     用于解码的字符集，如果为 {@code null} 则使用UTF-8。
   * @return
   *     从字符串中解析出的 {@link NameValuePair} 列表。
   */
  public static List<NameValuePair> parse(final String str, final Charset charset) {
    if (str == null) {
      return Collections.emptyList();
    } else {
      final CharBuffer buffer = new CharBuffer(str);
      return parse(buffer, charset, '&', ';');
    }
  }

  /**
   * 返回一个 {@link NameValuePair} 列表，表示从字符串中解析出的键值对。
   *
   * @param str
   *     要解析的字符串。
   * @param charset
   *     用于解码的字符集，如果为 {@code null} 则使用UTF-8。
   * @param separators
   *     用于分隔键值对的字符。
   * @return
   *     从字符串中解析出的 {@link NameValuePair} 列表。
   */
  public static List<NameValuePair> parse(final String str, final Charset charset,
      final char... separators) {
    if (str == null) {
      return Collections.emptyList();
    } else {
      final CharBuffer buffer = new CharBuffer(str);
      return parse(buffer, charset, separators);
    }
  }

  /**
   * 返回一个 {@link NameValuePair} 列表，表示从 {@link CharBuffer} 中解析出的键值对。
   *
   * @param buffer
   *     要解析的 {@link CharBuffer}。
   * @param charset
   *     用于解码的字符集，如果为 {@code null} 则使用UTF-8。
   * @param separators
   *     用于分隔键值对的字符。
   * @return
   *     从 {@link CharBuffer} 中解析出的 {@link NameValuePair} 列表。
   */
  public static List<NameValuePair> parse(final CharBuffer buffer,
      @Nullable final Charset charset, final char... separators) {
    Argument.requireNonNull("Char buffer", buffer);
    final BitSet delimSet = new BitSet();
    for (final char separator : separators) {
      delimSet.set(separator);
    }
    final Charset actualCharset = defaultIfNull(charset, UTF_8);
    final ParserCursor cursor = new ParserCursor(0, buffer.length());
    final ArrayList<NameValuePair> list = new ArrayList<>();
    while (!cursor.atEnd()) {
      delimSet.set(61);
      final String name = TokenParser.parseToken(buffer, cursor, delimSet);
      String value = null;
      if (!cursor.atEnd()) {
        final int delim = buffer.at(cursor.getPos());
        cursor.updatePos(cursor.getPos() + 1);
        if (delim == '=') {
          delimSet.clear('=');
          value = TokenParser.parseToken(buffer, cursor, delimSet);
          if (!cursor.atEnd()) {
            cursor.updatePos(cursor.getPos() + 1);
          }
        }
      }
      if (!name.isEmpty()) {
        final String decodedName = decodeFormFields(name, actualCharset);
        final String decodedValue = decodeFormFields(value, actualCharset);
        list.add(new NameValuePair(decodedName, decodedValue));
      }
    }
    return list;
  }

  static List<String> splitSegments(final CharSequence str, final BitSet separators) {
    final ParserCursor cursor = new ParserCursor(0, str.length());
    if (cursor.atEnd()) {
      return Collections.emptyList();
    } else {
      if (separators.get(str.charAt(cursor.getPos()))) {
        cursor.updatePos(cursor.getPos() + 1);
      }
      final List<String> list = new ArrayList<>();
      final StringBuilder builder = new StringBuilder();
      for (; !cursor.atEnd(); cursor.updatePos(cursor.getPos() + 1)) {
        final char current = str.charAt(cursor.getPos());
        if (separators.get(current)) {
          list.add(builder.toString());
          builder.setLength(0);
        } else {
          builder.append(current);
        }
      }
      list.add(builder.toString());
      return list;
    }
  }

  static List<String> splitPathSegments(final CharSequence str) {
    return splitSegments(str, PATH_SEPARATORS);
  }

  /**
   * 将路径字符串解析为路径段列表。
   *
   * @param str
   *     要解析的路径字符串。
   * @param charset
   *     用于解码的字符集，如果为 {@code null} 则使用UTF-8。
   * @return
   *     路径段列表。
   */
  public static List<String> parsePathSegments(final CharSequence str,
      @Nullable final Charset charset) {
    Argument.requireNonNull("Char sequence", str);
    final List<String> list = splitPathSegments(str);
    final Charset actualCharset = defaultIfNull(charset, UTF_8);
    for (int i = 0; i < list.size(); ++i) {
      list.set(i, urlDecode(list.get(i), actualCharset, false));
    }
    return list;
  }

  /**
   * 将路径字符串解析为路径段列表，使用UTF-8字符集。
   *
   * @param str
   *     要解析的路径字符串。
   * @return
   *     路径段列表。
   */
  public static List<String> parsePathSegments(final CharSequence str) {
    return parsePathSegments(str, UTF_8);
  }

  /**
   * 将路径段列表格式化为路径字符串。
   *
   * @param segments
   *     要格式化的路径段列表。
   * @param charset
   *     用于编码的字符集，如果为 {@code null} 则使用UTF-8。
   * @return
   *     格式化后的路径字符串。
   */
  public static String formatSegments(final Iterable<String> segments,
      @Nullable final Charset charset) {
    Argument.requireNonNull("Segments", segments);
    final StringBuilder result = new StringBuilder();
    for (final String segment : segments) {
      result.append('/')
          .append(urlEncode(segment, charset, PATHSAFE, false));
    }
    return result.toString();
  }

  /**
   * 将路径段列表格式化为路径字符串，使用UTF-8字符集。
   *
   * @param segments
   *     要格式化的路径段数组。
   * @return
   *     格式化后的路径字符串。
   */
  public static String formatSegments(final String... segments) {
    return formatSegments(Arrays.asList(segments), UTF_8);
  }

  /**
   * 将名值对列表格式化为查询字符串。
   *
   * @param parameters
   *     要格式化的名值对列表。
   * @param charset
   *     用于编码的字符集名称，如果为 {@code null} 则使用UTF-8。
   * @return
   *     格式化后的查询字符串。
   */
  public static String format(final List<? extends NameValuePair> parameters,
      @Nullable final String charset) {
    return format(parameters, '&', charset);
  }

  /**
   * 将名值对列表格式化为查询字符串。
   *
   * @param parameters
   *     要格式化的名值对列表。
   * @param parameterSeparator
   *     用于分隔参数的字符。
   * @param charset
   *     用于编码的字符集名称，如果为 {@code null} 则使用UTF-8。
   * @return
   *     格式化后的查询字符串。
   */
  public static String format(final List<? extends NameValuePair> parameters,
      final char parameterSeparator, @Nullable final String charset) {
    final Charset actualCharset = (charset != null ? Charset.forName(charset) : UTF_8);
    final StringBuilder result = new StringBuilder();
    for (final NameValuePair parameter : parameters) {
      final String encodedName = encodeFormFields(parameter.getName(), actualCharset);
      final String encodedValue = encodeFormFields(parameter.getValue(), actualCharset);
      if (result.length() > 0) {
        result.append(parameterSeparator);
      }
      result.append(encodedName);
      if (encodedValue != null) {
        result.append("=");
        result.append(encodedValue);
      }
    }
    return result.toString();
  }

  /**
   * 将名值对集合格式化为查询字符串。
   *
   * @param parameters
   *     要格式化的名值对集合。
   * @param charset
   *     用于编码的字符集。
   * @return
   *     格式化后的查询字符串。
   */
  public static String format(final Iterable<? extends NameValuePair> parameters,
      final Charset charset) {
    return format(parameters, '&', charset);
  }

  /**
   * 将名值对集合格式化为查询字符串。
   *
   * @param parameters
   *     要格式化的名值对集合。
   * @param parameterSeparator
   *     用于分隔参数的字符。
   * @param charset
   *     用于编码的字符集，如果为 {@code null} 则使用UTF-8。
   * @return
   *     格式化后的查询字符串。
   */
  public static String format(final Iterable<? extends NameValuePair> parameters,
      final char parameterSeparator, @Nullable final Charset charset) {
    Argument.requireNonNull("Parameters", parameters);
    final Charset actualCharset = defaultIfNull(charset, UTF_8);
    final StringBuilder result = new StringBuilder();
    for (final NameValuePair parameter : parameters) {
      final String encodedName = encodeFormFields(parameter.getName(), actualCharset);
      final String encodedValue = encodeFormFields(parameter.getValue(), actualCharset);
      if (result.length() > 0) {
        result.append(parameterSeparator);
      }
      result.append(encodedName);
      if (encodedValue != null) {
        result.append("=").append(encodedValue);
      }
    }
    return result.toString();
  }

  private static String urlEncode(@NotNull final String content,
      @NotNull final Charset charset, @NotNull final BitSet safeChars,
      final boolean blankAsPlus) {
    if (content == null) {
      return null;
    } else {
      final StringBuilder buffer = new StringBuilder();
      final ByteBuffer bb = charset.encode(content);
      while (bb.hasRemaining()) {
        final int b = bb.get() & 0xFF;
        if (safeChars.get(b)) {
          buffer.append((char) b);
        } else if (blankAsPlus && b == ' ') {
          buffer.append('+');
        } else {
          buffer.append("%");
          final char hex1 = CharUtils.UPPERCASE_DIGITS[(b >> 4) & 0xF];
          final char hex2 = CharUtils.UPPERCASE_DIGITS[b & 0xF];
          buffer.append(hex1);
          buffer.append(hex2);
        }
      }
      return buffer.toString();
    }
  }

  private static String urlDecode(@NotNull final String content,
      @NotNull final Charset charset, final boolean plusAsBlank) {
    if (content == null) {
      return null;
    } else {
      final java.nio.ByteBuffer bb = java.nio.ByteBuffer.allocate(content.length());
      final java.nio.CharBuffer cb = java.nio.CharBuffer.wrap(content);
      while (cb.hasRemaining()) {
        final char c = cb.get();
        if (c == '%' && cb.remaining() >= 2) {
          final char uc = cb.get();
          final char lc = cb.get();
          final int u = Character.digit(uc, 16);
          final int l = Character.digit(lc, 16);
          if (u != -1 && l != -1) {
            bb.put((byte) ((u << 4) + l));
          } else {
            bb.put((byte) '%');
            bb.put((byte) uc);
            bb.put((byte) lc);
          }
        } else if (plusAsBlank && c == '+') {
          bb.put((byte) ' ');
        } else {
          bb.put((byte) c);
        }
      }
      bb.flip();
      return charset.decode(bb).toString();
    }
  }

  private static String decodeFormFields(@Nullable final String content,
      @NotNull final Charset charset) {
    if (content == null) {
      return null;
    } else {
      return urlDecode(content, charset, true);
    }
  }

  private static String encodeFormFields(@Nullable final String content,
      @NotNull final Charset charset) {
    if (content == null) {
      return null;
    } else {
      return urlEncode(content, charset, URLENCODER, true);
    }
  }

  static String encUserInfo(@NotNull final String content, @NotNull final Charset charset) {
    return urlEncode(content, charset, USERINFO, false);
  }

  static String encUric(@NotNull final String content, @NotNull final Charset charset) {
    return urlEncode(content, charset, URIC, false);
  }

  static String encPath(@NotNull final String content, @NotNull final Charset charset) {
    return urlEncode(content, charset, PATH_SPECIAL, false);
  }
}