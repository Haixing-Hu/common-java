////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text;

import java.util.BitSet;

import ltd.qubit.commons.util.buffer.CharBuffer;

/**
 * 用于解析字符串中的标记（token）的工具类。
 *
 * <p>该类提供了一系列静态方法，用于从字符缓冲区中解析标记和值，支持引号内容处理和空白字符处理。
 *
 * @author 胡海星
 */
public class TokenParser {

  /**
   * 回车符常量。
   */
  public static final char CR = '\r';

  /**
   * 换行符常量。
   */
  public static final char LF = '\n';

  /**
   * 空格符常量。
   */
  public static final char SP = ' ';

  /**
   * 制表符常量。
   */
  public static final char HT = '\t';

  /**
   * 双引号常量。
   */
  public static final char DOUBLE_QUOTE = '"';

  /**
   * 转义符常量。
   */
  public static final char ESCAPE = '\\';

  /**
   * 初始化位集合。
   *
   * @param bits
   *     要设置的位的索引数组。
   * @return
   *     初始化后的位集合。
   */
  public static BitSet initBitset(final int... bits) {
    final BitSet bitset = new BitSet();
    for (final int b : bits) {
      bitset.set(b);
    }
    return bitset;
  }

  /**
   * 判断指定字符是否为空白字符。
   *
   * @param ch
   *     要判断的字符。
   * @return
   *     如果指定字符是空白字符（空格、制表符、回车符或换行符），返回 {@code true}；否则返回 {@code false}。
   */
  public static boolean isWhitespace(final char ch) {
    return ch == ' ' || ch == '\t' || ch == '\r' || ch == '\n';
  }

  /**
   * 从字符缓冲区中解析一个标记。
   *
   * <p>该方法会跳过空白字符，并将连续的非空白、非分隔符字符组合成一个标记。
   * 如果遇到空白字符，会用单个空格替换连续的空白字符。
   *
   * @param buffer
   *     字符缓冲区。
   * @param cursor
   *     解析游标，用于跟踪当前解析位置。
   * @param delimiters
   *     分隔符位集合，如果为 {@code null} 则不使用分隔符。
   * @return
   *     解析得到的标记字符串。
   */
  public static String parseToken(final CharBuffer buffer, final ParserCursor cursor,
      final BitSet delimiters) {
    final StringBuilder builder = new StringBuilder();
    boolean whitespace = false;
    while (!cursor.atEnd()) {
      final char current = buffer.at(cursor.getPos());
      if (delimiters != null && delimiters.get(current)) {
        break;
      }
      if (isWhitespace(current)) {
        skipWhiteSpace(buffer, cursor);
        whitespace = true;
      } else {
        if (whitespace && builder.length() > 0) {
          builder.append(' ');
        }
        copyContent(buffer, cursor, delimiters, builder);
        whitespace = false;
      }
    }
    return builder.toString();
  }

  /**
   * 从字符缓冲区中解析一个值。
   *
   * <p>该方法支持引号内容的处理，可以正确解析被双引号包围的内容。
   * 对于非引号内容，处理方式与 {@link #parseToken} 类似。
   *
   * @param buffer
   *     字符缓冲区。
   * @param cursor
   *     解析游标，用于跟踪当前解析位置。
   * @param delimiters
   *     分隔符位集合，如果为 {@code null} 则不使用分隔符。
   * @return
   *     解析得到的值字符串。
   */
  public static String parseValue(final CharBuffer buffer, final ParserCursor cursor,
      final BitSet delimiters) {
    final StringBuilder builder = new StringBuilder();
    boolean whitespace = false;
    while (!cursor.atEnd()) {
      final char current = buffer.at(cursor.getPos());
      if (delimiters != null && delimiters.get(current)) {
        break;
      }
      if (isWhitespace(current)) {
        skipWhiteSpace(buffer, cursor);
        whitespace = true;
      } else if (current == '"') {
        if (whitespace && builder.length() > 0) {
          builder.append(' ');
        }
        copyQuotedContent(buffer, cursor, builder);
        whitespace = false;
      } else {
        if (whitespace && builder.length() > 0) {
          builder.append(' ');
        }
        copyUnquotedContent(buffer, cursor, delimiters, builder);
        whitespace = false;
      }
    }
    return builder.toString();
  }

  /**
   * 跳过字符缓冲区中的空白字符。
   *
   * <p>该方法会将游标移动到下一个非空白字符的位置。
   *
   * @param buffer
   *     字符缓冲区。
   * @param cursor
   *     解析游标，会被更新到下一个非空白字符的位置。
   */
  public static void skipWhiteSpace(final CharBuffer buffer, final ParserCursor cursor) {
    int pos = cursor.getPos();
    final int indexFrom = cursor.getPos();
    final int indexTo = cursor.getUpperBound();
    for (int i = indexFrom; i < indexTo; ++i) {
      final char current = buffer.at(i);
      if (!isWhitespace(current)) {
        break;
      }
      ++pos;
    }
    cursor.updatePos(pos);
  }

  /**
   * 从字符缓冲区中复制内容到字符串构建器中。
   *
   * <p>该方法会复制连续的非空白、非分隔符字符，直到遇到空白字符、分隔符或缓冲区结束。
   *
   * @param buffer
   *     字符缓冲区。
   * @param cursor
   *     解析游标，会被更新到复制结束的位置。
   * @param delimiters
   *     分隔符位集合，如果为 {@code null} 则不使用分隔符。
   * @param builder
   *     用于接收复制内容的字符串构建器。
   */
  public static void copyContent(final CharBuffer buffer, final ParserCursor cursor,
      final BitSet delimiters, final StringBuilder builder) {
    int pos = cursor.getPos();
    final int indexFrom = cursor.getPos();
    final int indexTo = cursor.getUpperBound();
    for (int i = indexFrom; i < indexTo; ++i) {
      final char current = buffer.at(i);
      if (delimiters != null && delimiters.get(current) || isWhitespace(current)) {
        break;
      }
      ++pos;
      builder.append(current);
    }
    cursor.updatePos(pos);
  }

  /**
   * 从字符缓冲区中复制非引号内容到字符串构建器中。
   *
   * <p>该方法会复制连续的非空白、非分隔符、非引号字符，直到遇到空白字符、分隔符、引号或缓冲区结束。
   *
   * @param buffer
   *     字符缓冲区。
   * @param cursor
   *     解析游标，会被更新到复制结束的位置。
   * @param delimiters
   *     分隔符位集合，如果为 {@code null} 则不使用分隔符。
   * @param builder
   *     用于接收复制内容的字符串构建器。
   */
  public static void copyUnquotedContent(final CharBuffer buffer, final ParserCursor cursor,
      final BitSet delimiters, final StringBuilder builder) {
    int pos = cursor.getPos();
    final int indexFrom = cursor.getPos();
    final int indexTo = cursor.getUpperBound();
    for (int i = indexFrom; i < indexTo; ++i) {
      final char current = buffer.at(i);
      if (delimiters != null && delimiters.get(current)
          || isWhitespace(current)
          || current == '"') {
        break;
      }
      ++pos;
      builder.append(current);
    }
    cursor.updatePos(pos);
  }

  /**
   * 从字符缓冲区中复制引号内容到字符串构建器中。
   *
   * <p>该方法会正确处理双引号包围的内容，包括转义字符的处理。
   * 支持 \" 和 \\ 的转义序列，会过滤掉回车符和换行符。
   *
   * @param buffer
   *     字符缓冲区。
   * @param cursor
   *     解析游标，会被更新到引号内容结束的位置。
   * @param builder
   *     用于接收复制内容的字符串构建器。
   */
  public static void copyQuotedContent(final CharBuffer buffer, final ParserCursor cursor,
      final StringBuilder builder) {
    if (!cursor.atEnd()) {
      int pos = cursor.getPos();
      int indexFrom = cursor.getPos();
      final int indexTo = cursor.getUpperBound();
      char current = buffer.at(pos);
      if (current == '"') {
        ++pos;
        ++indexFrom;
        boolean escaped = false;
        for (int i = indexFrom; i < indexTo; ++pos) {
          current = buffer.at(i);
          if (escaped) {
            if (current != '"' && current != '\\') {
              builder.append('\\');
            }
            builder.append(current);
            escaped = false;
          } else {
            if (current == '"') {
              ++pos;
              break;
            }
            if (current == '\\') {
              escaped = true;
            } else if (current != '\r' && current != '\n') {
              builder.append(current);
            }
          }
          ++i;
        }
        cursor.updatePos(pos);
      }
    }
  }
}