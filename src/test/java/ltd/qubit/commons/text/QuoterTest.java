////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test for the {@link Quoter} class.
 *
 * @author Haixing Hu
 */
public class QuoterTest {

  @Test
  public void testConstructor() {
    final Quoter quoter = new Quoter();
    assertEquals('"', quoter.getLeftQuote());
    assertEquals('"', quoter.getRightQuote());
    assertEquals('\\', quoter.getEscapeChar());
    assertTrue(quoter.isUseEscape());
  }

  @Test
  public void testWithQuotes() {
    final Quoter quoter = new Quoter().withQuotes('[', ']');
    assertEquals('[', quoter.getLeftQuote());
    assertEquals(']', quoter.getRightQuote());
  }

  @Test
  public void testWithQuote() {
    final Quoter quoter = new Quoter().withQuote('\'');
    assertEquals('\'', quoter.getLeftQuote());
    assertEquals('\'', quoter.getRightQuote());
  }

  @Test
  public void testWithSingleQuotes() {
    final Quoter quoter = new Quoter().withSingleQuotes();
    assertEquals('\'', quoter.getLeftQuote());
    assertEquals('\'', quoter.getRightQuote());
  }

  @Test
  public void testWithDoubleQuotes() {
    final Quoter quoter = new Quoter().withDoubleQuotes();
    assertEquals('"', quoter.getLeftQuote());
    assertEquals('"', quoter.getRightQuote());
  }

  @Test
  public void testWithBackQuotes() {
    final Quoter quoter = new Quoter().withBackQuotes();
    assertEquals('`', quoter.getLeftQuote());
    assertEquals('`', quoter.getRightQuote());
  }

  @Test
  public void testWithEscape() {
    final Quoter quoter = new Quoter().withEscape('#');
    assertEquals('#', quoter.getEscapeChar());
    assertTrue(quoter.isUseEscape());
  }

  @Test
  public void testWithoutEscape() {
    final Quoter quoter = new Quoter().withoutEscape();
    assertFalse(quoter.isUseEscape());
  }

  @Test
  public void testUseEscape() {
    final Quoter quoter = new Quoter().withoutEscape().useEscape();
    assertTrue(quoter.isUseEscape());
  }

  @Test
  public void testIsQuoted() {
    final Quoter quoter = new Quoter();

    // null case
    assertFalse(quoter.isQuoted(null));

    // empty string
    assertFalse(quoter.isQuoted(""));

    // single character
    assertFalse(quoter.isQuoted("a"));

    // properly quoted
    assertTrue(quoter.isQuoted("\"hello\""));

    // not quoted
    assertFalse(quoter.isQuoted("hello"));

    // only left quote
    assertFalse(quoter.isQuoted("\"hello"));

    // only right quote
    assertFalse(quoter.isQuoted("hello\""));

    // mismatched quotes
    assertFalse(quoter.isQuoted("'hello\""));
  }

  @Test
  public void testIsQuotedWithCustomQuotes() {
    final Quoter quoter = new Quoter().withQuotes('[', ']');

    assertTrue(quoter.isQuoted("[hello]"));
    assertFalse(quoter.isQuoted("\"hello\""));
    assertFalse(quoter.isQuoted("[hello"));
    assertFalse(quoter.isQuoted("hello]"));
  }

  @Test
  public void testQuoteString() {
    final Quoter quoter = new Quoter();

    // basic quoting
    assertEquals("\"hello\"", quoter.quote("hello"));

    // empty string
    assertEquals("\"\"", quoter.quote(""));

    // string with quotes that need escaping
    assertEquals("\"hello \\\"world\\\"\"", quoter.quote("hello \"world\""));

    // string with escape character
    assertEquals("\"hello \\\\world\"", quoter.quote("hello \\world"));
  }

  @Test
  public void testQuoteStringBuilder() {
    final Quoter quoter = new Quoter();
    final StringBuilder builder = new StringBuilder("prefix:");

    quoter.quote("hello", builder);
    assertEquals("prefix:\"hello\"", builder.toString());
  }

  @Test
  public void testQuoteWithCustomQuotes() {
    final Quoter quoter = new Quoter().withQuotes('[', ']');

    assertEquals("[hello]", quoter.quote("hello"));

    // with escape
    assertEquals("[hello \\[world\\]]", quoter.quote("hello [world]"));
  }

  @Test
  public void testQuoteWithoutEscape() {
    final Quoter quoter = new Quoter().withoutEscape();

    assertEquals("\"hello\"", quoter.quote("hello"));

    // characters that would normally be escaped are not escaped
    assertEquals("\"hello \"world\"\"", quoter.quote("hello \"world\""));
  }

  @Test
  public void testQuoteNullString() {
    final Quoter quoter = new Quoter();

    assertThrows(NullPointerException.class, () -> quoter.quote((String) null));
  }

  @Test
  public void testQuoteNullStringBuilder() {
    final Quoter quoter = new Quoter();

    assertThrows(NullPointerException.class, () -> quoter.quote("hello", null));
  }

  @Test
  public void testUnquote() {
    final Quoter quoter = new Quoter();

    // basic unquoting
    assertEquals("hello", quoter.unquote("\"hello\""));

    // empty string
    assertEquals("", quoter.unquote("\"\""));

    // string with escaped quotes
    assertEquals("hello \"world\"", quoter.unquote("\"hello \\\"world\\\"\""));

    // string with escaped escape character
    assertEquals("hello \\world", quoter.unquote("\"hello \\\\world\""));
  }

  @Test
  public void testUnquoteStringBuilder() {
    final Quoter quoter = new Quoter();
    final StringBuilder builder = new StringBuilder("prefix:");

    quoter.unquote("\"hello\"", builder);
    assertEquals("prefix:hello", builder.toString());
  }

  @Test
  public void testUnquoteWithCustomQuotes() {
    final Quoter quoter = new Quoter().withQuotes('[', ']');

    assertEquals("hello", quoter.unquote("[hello]"));

    // with escape
    assertEquals("hello [world]", quoter.unquote("[hello \\[world\\]]"));
  }

  @Test
  public void testUnquoteWithoutEscape() {
    final Quoter quoter = new Quoter().withoutEscape();

    assertEquals("hello", quoter.unquote("\"hello\""));

    // escape characters are not processed
    assertEquals("hello \\\"world\\\"", quoter.unquote("\"hello \\\"world\\\"\""));
  }

  @Test
  public void testUnquoteNotQuoted() {
    final Quoter quoter = new Quoter();

    assertThrows(IllegalArgumentException.class, () -> quoter.unquote("hello"));
    assertThrows(IllegalArgumentException.class, () -> quoter.unquote("\"hello"));
    assertThrows(IllegalArgumentException.class, () -> quoter.unquote("hello\""));
    assertThrows(IllegalArgumentException.class, () -> quoter.unquote("'hello\""));
  }

  @Test
  public void testUnquoteNullString() {
    final Quoter quoter = new Quoter();

    assertThrows(NullPointerException.class, () -> quoter.unquote((String) null));
  }

  @Test
  public void testUnquoteNullStringBuilder() {
    final Quoter quoter = new Quoter();

    assertThrows(NullPointerException.class, () -> quoter.unquote("\"hello\"", null));
  }

  @Test
  public void testUnquoteIfNecessary() {
    final Quoter quoter = new Quoter();

    // null case
    assertNull(quoter.unquoteIfNecessary(null));

    // quoted string
    assertEquals("hello", quoter.unquoteIfNecessary("\"hello\""));

    // unquoted string
    assertEquals("hello", quoter.unquoteIfNecessary("hello"));

    // empty string
    assertEquals("", quoter.unquoteIfNecessary(""));

    // single character
    assertEquals("a", quoter.unquoteIfNecessary("a"));
  }

  @Test
  public void testUnquoteIfNecessaryStringBuilder() {
    final Quoter quoter = new Quoter();
    StringBuilder builder = new StringBuilder();

    // null case
    quoter.unquoteIfNecessary(null, builder);
    assertEquals("", builder.toString());

    builder.setLength(0);

    // quoted string
    quoter.unquoteIfNecessary("\"hello\"", builder);
    assertEquals("hello", builder.toString());

    builder.setLength(0);

    // unquoted string
    quoter.unquoteIfNecessary("hello", builder);
    assertEquals("hello", builder.toString());
  }

  @Test
  public void testUnquoteIfNecessaryNullStringBuilder() {
    final Quoter quoter = new Quoter();

    assertThrows(NullPointerException.class, () -> quoter.unquoteIfNecessary("hello", null));
  }

  @Test
  public void testChainedCalls() {
    final String result = new Quoter()
        .withQuotes('<', '>')
        .withEscape('#')
        .quote("hello <world>");

    assertEquals("<hello #<world#>>", result);

    final String unquoted = new Quoter()
        .withQuotes('<', '>')
        .withEscape('#')
        .unquote(result);

    assertEquals("hello <world>", unquoted);
  }

  @Test
  public void testComplexEscaping() {
    final Quoter quoter = new Quoter().withEscape('#').withQuotes('[', ']');

    // Test multiple characters that need escaping
    final String input = "hello #[world]# test";
    final String quoted = quoter.quote(input);
    assertEquals("[hello ###[world#]## test]", quoted);

    final String unquoted = quoter.unquote(quoted);
    assertEquals(input, unquoted);
  }

  @Test
  public void testSpecialCharacters() {
    final Quoter quoter = new Quoter();

    // Test with newlines, tabs, etc.
    final String input = "hello\nworld\ttab";
    final String quoted = quoter.quote(input);
    final String unquoted = quoter.unquote(quoted);
    assertEquals(input, unquoted);
  }

  @Test
  public void testEquals() {
    final Quoter quoter1 = new Quoter();
    final Quoter quoter2 = new Quoter();
    final Quoter quoter3 = new Quoter().withSingleQuotes();

    assertEquals(quoter1, quoter2);
    assertNotEquals(quoter1, quoter3);
    assertNotEquals(quoter1, null);
    assertNotEquals(quoter1, "not a quoter");

    // Test different escape characters
    final Quoter quoter4 = new Quoter().withEscape('#');
    assertNotEquals(quoter1, quoter4);

    // Test different useEscape setting
    final Quoter quoter5 = new Quoter().withoutEscape();
    assertNotEquals(quoter1, quoter5);
  }

  @Test
  public void testHashCode() {
    final Quoter quoter1 = new Quoter();
    final Quoter quoter2 = new Quoter();
    final Quoter quoter3 = new Quoter().withSingleQuotes();

    assertEquals(quoter1.hashCode(), quoter2.hashCode());
    assertNotEquals(quoter1.hashCode(), quoter3.hashCode());
  }

  @Test
  public void testToString() {
    final Quoter quoter = new Quoter();
    final String str = quoter.toString();

    assertNotNull(str);
    assertTrue(str.contains("Quoter"));
    assertTrue(str.contains("leftQuote"));
    assertTrue(str.contains("rightQuote"));
    assertTrue(str.contains("escapeChar"));
    assertTrue(str.contains("useEscape"));
  }

  @Test
  public void testConstants() {
    assertEquals('"', Quoter.DEFAULT_LEFT_QUOTE);
    assertEquals('"', Quoter.DEFAULT_RIGHT_QUOTE);
    assertEquals('\\', Quoter.DEFAULT_ESCAPE_CHAR);
    assertTrue(Quoter.DEFAULT_USE_ESCAPE);
  }

  @Test
  public void testQuoteCharSequence() {
    final Quoter quoter = new Quoter();
    final StringBuilder input = new StringBuilder("hello");

    assertEquals("\"hello\"", quoter.quote(input));
  }

  @Test
  public void testUnquoteCharSequence() {
    final Quoter quoter = new Quoter();
    final StringBuilder input = new StringBuilder("\"hello\"");

    assertEquals("hello", quoter.unquote(input));
  }

  @Test
  public void testUnquoteIfNecessaryCharSequence() {
    final Quoter quoter = new Quoter();
    final StringBuilder quotedInput = new StringBuilder("\"hello\"");
    final StringBuilder unquotedInput = new StringBuilder("hello");

    assertEquals("hello", quoter.unquoteIfNecessary(quotedInput));
    assertEquals("hello", quoter.unquoteIfNecessary(unquotedInput));
  }

  @Test
  public void testEmptyQuotes() {
    final Quoter quoter = new Quoter();

    assertTrue(quoter.isQuoted("\"\""));
    assertEquals("", quoter.unquote("\"\""));
    assertEquals("\"\"", quoter.quote(""));
  }

  @Test
  public void testSingleCharacterQuotes() {
    final Quoter quoter = new Quoter();

    assertTrue(quoter.isQuoted("\"a\""));
    assertEquals("a", quoter.unquote("\"a\""));
    assertEquals("\"a\"", quoter.quote("a"));
  }

  @Test
  public void testMixedQuotesInContent() {
    final Quoter quoter = new Quoter();

    final String input = "he said 'hello' to me";
    final String quoted = quoter.quote(input);
    final String unquoted = quoter.unquote(quoted);
    assertEquals(input, unquoted);
  }

  @Test
  public void testEdgeCaseEscaping() {
    final Quoter quoter = new Quoter();

    // String ending with escape character
    final String input = "hello\\";
    final String quoted = quoter.quote(input);
    final String unquoted = quoter.unquote(quoted);
    assertEquals(input, unquoted);

    // String starting with escape character
    final String input2 = "\\hello";
    final String quoted2 = quoter.quote(input2);
    final String unquoted2 = quoter.unquote(quoted2);
    assertEquals(input2, unquoted2);
  }

  @Test
  public void testRoundTripQuoteUnquote() {
    final Quoter quoter = new Quoter();
    final String[] testStrings = {
        "",
        "hello",
        "hello world",
        "hello \"world\"",
        "hello \\world",
        "hello\nworld",
        "hello\tworld",
        "'single quotes'",
        "[brackets]",
        "{braces}",
        "<angles>",
        "special chars: !@#$%^&*()",
        "unicode: 你好世界",
        "emoji: 😀😃😄😁",
    };

    for (final String input : testStrings) {
      final String quoted = quoter.quote(input);
      final String unquoted = quoter.unquote(quoted);
      assertEquals(input, unquoted, "Failed for input: " + input);
    }
  }

  @Test
  public void testRoundTripWithDifferentQuotes() {
    final Quoter[] quoters = {
        new Quoter().withSingleQuotes(),
        new Quoter().withBackQuotes(),
        new Quoter().withQuotes('[', ']'),
        new Quoter().withQuotes('{', '}'),
        new Quoter().withQuotes('<', '>'),
    };

    final String input = "hello world";

    for (final Quoter quoter : quoters) {
      final String quoted = quoter.quote(input);
      final String unquoted = quoter.unquote(quoted);
      assertEquals(input, unquoted);
      assertTrue(quoter.isQuoted(quoted));
    }
  }
}