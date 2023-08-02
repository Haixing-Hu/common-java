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
 * Unit test of the {@link CharUtils#isQuoted(String, char, char, char)}
 * function.
 *
 * @author Haixing Hu
 */
public class CharUtilsIsQuotedTest {

  @Test
  public void testIsQuoted() {
    assertEquals(false, CharUtils.isQuoted(null, '\\', '\'', '\''));
    assertEquals(false, CharUtils.isQuoted("", '\\', '\'', '\''));
    assertEquals(false, CharUtils.isQuoted("'", '\\', '\'', '\''));
    assertEquals(false, CharUtils.isQuoted("''", '\\', '\'', '\''));
    assertEquals(false, CharUtils.isQuoted("'xx'", '\\', '\'', '\''));
    assertEquals(true, CharUtils.isQuoted("'''", '\\', '\'', '\''));
    assertEquals(true, CharUtils.isQuoted("'x'", '\\', '\'', '\''));
    assertEquals(true, CharUtils.isQuoted("'\\\\'", '\\', '\'', '\''));
    assertEquals(false, CharUtils.isQuoted("'\\'", '\\', '\'', '\''));
    assertEquals(false, CharUtils.isQuoted("[x)", '\\', '[', ']'));
    assertEquals(true, CharUtils.isQuoted("[x]", '\\', '[', ']'));
  }
}
