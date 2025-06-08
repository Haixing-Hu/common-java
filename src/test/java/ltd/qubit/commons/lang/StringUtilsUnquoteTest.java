////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.lang;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit test of the {@link StringUtils#unquote()} functions.
 *
 * @author Haixing Hu
 */
public class StringUtilsUnquoteTest {

  @Test
  public void testUnquoteWithEscape() {
    final String str1 = "hello\\, 'Tom'!";
    final String str1q = "'hello\\\\, \\'Tom\\'!'";
    assertEquals(str1, StringUtils.unquote(str1q, '\\', '\'', '\''));

    final String str2 = "hello\\, \\(tom)!";
    final String str2q = "(hello\\\\, \\\\\\(tom\\)!)";
    assertEquals(str2, StringUtils.unquote(str2q, '\\', '(', ')'));

    final String str3 = "hello\\, \\(tom)!";
    final String str3q = "\\hello\\\\, \\\\(tom\\)!)";
    assertEquals(str3, StringUtils.unquote(str3q, '\\', '\\', ')'));
  }

  @Test
  public void testUnquoteStringBuilderWithEscape() {
    final String str1 = "hello\\, 'Tom'!";
    final String str1q = "'hello\\\\, \\'Tom\\'!'";
    final StringBuilder b1 = new StringBuilder();
    StringUtils.unquote(str1q, '\\', '\'', '\'', b1);
    assertEquals(str1, b1.toString());

    final String str2 = "hello\\, \\(tom)!";
    final String str2q = "(hello\\\\, \\\\\\(tom\\)!)";
    final StringBuilder b2 = new StringBuilder("abc");
    StringUtils.unquote(str2q, '\\', '(', ')', b2);
    assertEquals("abc" + str2, b2.toString());

    final String str3 = "hello\\, \\(tom)!";
    final String str3q = "\\hello\\\\, \\\\(tom\\)!)";
    final StringBuilder b3 = new StringBuilder("xyz");
    StringUtils.unquote(str3q, '\\', '\\', ')', b3);
    assertEquals("xyz" + str3, b3.toString());
  }

  @Test
  public void testUnquoteWithoutEscape() {
    final String str1 = "hello\\\\, \\'Tom\\'!";
    final String str1q = "'hello\\\\, \\'Tom\\'!'";
    assertEquals(str1, StringUtils.unquote(str1q, '\'', '\''));

    final String str2 = "hello\\\\, \\\\\\(tom\\)!";
    final String str2q = "(hello\\\\, \\\\\\(tom\\)!)";
    assertEquals(str2, StringUtils.unquote(str2q, '(', ')'));

    final String str3 = "hello\\\\, \\\\(tom\\)!";
    final String str3q = "\\hello\\\\, \\\\(tom\\)!)";
    assertEquals(str3, StringUtils.unquote(str3q, '\\', ')'));
  }

  @Test
  public void testUnquoteStringBuilderWithoutEscape() {
    final String str1 = "hello\\\\, \\'Tom\\'!";
    final String str1q = "'hello\\\\, \\'Tom\\'!'";
    final StringBuilder b1 = new StringBuilder();
    StringUtils.unquote(str1q, '\'', '\'', b1);
    assertEquals(str1, b1.toString());

    final String str2 = "hello\\\\, \\\\\\(tom\\)!";
    final String str2q = "(hello\\\\, \\\\\\(tom\\)!)";
    final StringBuilder b2 = new StringBuilder("abc");
    StringUtils.unquote(str2q, '(', ')', b2);
    assertEquals("abc" + str2, b2.toString());

    final String str3 = "hello\\\\, \\\\(tom\\)!";
    final String str3q = "\\hello\\\\, \\\\(tom\\)!)";
    final StringBuilder b3 = new StringBuilder("xyz");
    StringUtils.unquote(str3q, '\\', ')', b3);
    assertEquals("xyz" + str3, b3.toString());
  }
}