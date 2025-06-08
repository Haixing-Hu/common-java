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
 * Unit test for the {@link StringUtils#quote()} functions.
 *
 * @author Haixing Hu
 */
public class StringUtilsQuoteTest {

  @Test
  public void testQuote() {
    final String str1 = "hello\\, 'Tom'!";
    assertEquals("'hello\\\\, \\'Tom\\'!'",
        StringUtils.quote(str1, '\\', '\'', '\''));

    final String str2 = "hello\\, \\(tom)!";
    assertEquals("(hello\\\\, \\\\\\(tom\\)!)",
        StringUtils.quote(str2, '\\', '(', ')'));

    final String str3 = "hello\\, \\(tom)!";
    assertEquals("\\hello\\\\, \\\\(tom\\)!)",
        StringUtils.quote(str3, '\\', '\\', ')'));
  }

  @Test
  public void testQuoteStringBuilder() {
    final String str1 = "hello\\, 'Tom'!";
    final StringBuilder builder1 = new StringBuilder();
    StringUtils.quote(str1, '\\', '\'', '\'', builder1);
    assertEquals("'hello\\\\, \\'Tom\\'!'", builder1.toString());

    final String str2 = "hello\\, \\(tom)!";
    final StringBuilder builder2 = new StringBuilder("xyz");
    StringUtils.quote(str2, '\\', '(', ')', builder2);
    assertEquals("xyz(hello\\\\, \\\\\\(tom\\)!)", builder2.toString());

    final String str3 = "hello\\, \\(tom)!";
    final StringBuilder builder3 = new StringBuilder("abc");
    StringUtils.quote(str3, '\\', '\\', ')', builder3);
    assertEquals("abc\\hello\\\\, \\\\(tom\\)!)", builder3.toString());
  }
}