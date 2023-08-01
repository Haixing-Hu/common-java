////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text;

import ltd.qubit.commons.util.buffer.CharBuffer;

import java.util.BitSet;

public class TokenParser {

  public static final char CR = '\r';
  public static final char LF = '\n';
  public static final char SP = ' ';
  public static final char HT = '\t';
  public static final char DOUBLE_QUOTE = '"';
  public static final char ESCAPE = '\\';

  public static BitSet initBitset(final int... bits) {
    final BitSet bitset = new BitSet();
    for (final int b : bits) {
      bitset.set(b);
    }
    return bitset;
  }

  public static boolean isWhitespace(final char ch) {
    return ch == ' ' || ch == '\t' || ch == '\r' || ch == '\n';
  }

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
