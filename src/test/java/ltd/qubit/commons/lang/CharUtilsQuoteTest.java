////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.lang;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit test of the {@link CharUtils#quote()} functions.
 *
 * @author Haixing Hu
 */
public class CharUtilsQuoteTest {

  @Test
  public void testQuote() {
    assertEquals("'x'", CharUtils.quote('x', '\\', '\'', '\''));
    assertEquals("'\\\\'", CharUtils.quote('\\', '\\', '\'', '\''));
    assertEquals("'\\''", CharUtils.quote('\'', '\\', '\'', '\''));

    assertEquals("[x]", CharUtils.quote('x', '\\', '[', ']'));
    assertEquals("[\\\\]", CharUtils.quote('\\', '\\', '[', ']'));
    assertEquals("[\\[]", CharUtils.quote('[', '\\', '[', ']'));
    assertEquals("[\\]]", CharUtils.quote(']', '\\', '[', ']'));
  }

  @Test
  public void testQuoteStringBuilder() {
    final StringBuilder b1 = new StringBuilder();
    CharUtils.quote('x', '\\', '\'', '\'', b1);
    assertEquals("'x'", b1.toString());

    final StringBuilder b2 = new StringBuilder();
    CharUtils.quote('\\', '\\', '\'', '\'', b2);
    assertEquals("'\\\\'", b2.toString());

    final StringBuilder b3 = new StringBuilder("abc");
    CharUtils.quote('\'', '\\', '\'', '\'', b3);
    assertEquals("abc'\\''", b3.toString());

    final StringBuilder b4 = new StringBuilder();
    CharUtils.quote('x', '\\', '[', ']', b4);
    assertEquals("[x]", b4.toString());

    final StringBuilder b5 = new StringBuilder("xyz");
    CharUtils.quote('\\', '\\', '[', ']', b5);
    assertEquals("xyz[\\\\]", b5.toString());

    final StringBuilder b6 = new StringBuilder();
    CharUtils.quote('[', '\\', '[', ']', b6);
    assertEquals("[\\[]", b6.toString());

    final StringBuilder b7 = new StringBuilder("www");
    CharUtils.quote(']', '\\', '[', ']', b7);
    assertEquals("www[\\]]", b7.toString());
  }
}
