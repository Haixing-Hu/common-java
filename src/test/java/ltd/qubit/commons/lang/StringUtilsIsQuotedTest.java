////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.lang;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit test for the {@link StringUtils#isQuoted()} functions.
 *
 * @author Haixing Hu
 */
public class StringUtilsIsQuotedTest {

  @Test
  public void testIsQuoted() {
    assertEquals(false, StringUtils.isQuoted(null));
    assertEquals(false, StringUtils.isQuoted(""));
    assertEquals(false, StringUtils.isQuoted("'"));
    assertEquals(false, StringUtils.isQuoted("\""));
    assertEquals(false, StringUtils.isQuoted("abc"));
    assertEquals(true, StringUtils.isQuoted("''"));
    assertEquals(true, StringUtils.isQuoted("\"\""));
    assertEquals(false, StringUtils.isQuoted("'\""));
    assertEquals(false, StringUtils.isQuoted("\"'"));
    assertEquals(true, StringUtils.isQuoted("'abc'"));
    assertEquals(true, StringUtils.isQuoted("\"def\""));
    assertEquals(true, StringUtils.isQuoted("'abc''"));
    assertEquals(true, StringUtils.isQuoted("\"def\"\""));
  }

  @Test
  public void testIsQuotedChar() {
    assertEquals(false, StringUtils.isQuoted(null, '\'', '\''));
    assertEquals(false, StringUtils.isQuoted("", '\'', '\''));
    assertEquals(false, StringUtils.isQuoted("'", '\'', '\''));
    assertEquals(false, StringUtils.isQuoted("\"", '\'', '\''));
    assertEquals(false, StringUtils.isQuoted("abc", '\'', '\''));
    assertEquals(true, StringUtils.isQuoted("''", '\'', '\''));
    assertEquals(false, StringUtils.isQuoted("\"\", ''', '''"));
    assertEquals(false, StringUtils.isQuoted("'\"", '\'', '\''));
    assertEquals(false, StringUtils.isQuoted("\"'", '\'', '\''));
    assertEquals(true, StringUtils.isQuoted("'abc'", '\'', '\''));
    assertEquals(false, StringUtils.isQuoted("\"def\"", '\'', '\''));
    assertEquals(true, StringUtils.isQuoted("'abc''", '\'', '\''));
    assertEquals(false, StringUtils.isQuoted("\"def\"\"", '\'', '\''));

    assertEquals(false, StringUtils.isQuoted("'a[b]c'", '[', ']'));
    assertEquals(true, StringUtils.isQuoted("[abc]", '[', ']'));
  }
}
