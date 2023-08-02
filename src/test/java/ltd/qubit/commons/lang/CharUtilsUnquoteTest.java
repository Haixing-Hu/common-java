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
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Unit test of the {@link CharUtils#unquote(String, char, char, char)}
 * function.
 *
 * @author Haixing Hu
 */
public class CharUtilsUnquoteTest {

  @Test
  public void testUnquote() {
    assertEquals('x', CharUtils.unquote("'x'", '\\', '\'', '\''));
    assertEquals('x', CharUtils.unquote("\"x\"", '\\', '"', '"'));
    assertEquals('x', CharUtils.unquote("[x]", '\\', '[', ']'));
    assertEquals('\\', CharUtils.unquote("'\\\\'", '\\', '\'', '\''));
    assertEquals('\'', CharUtils.unquote("'\\''", '\\', '\'', '\''));
  }

  @Test
  public void testUnquoteError() {
    try {
      CharUtils.unquote(null, '\\', '\'', '\'');
      fail("should throw");
    } catch (final NullPointerException e) {
      //  pass
    }

    try {
      CharUtils.unquote("", '\\', '\'', '\'');
      fail("should throw");
    } catch (final IllegalArgumentException e) {
      //  pass
    }

    try {
      CharUtils.unquote("'", '\\', '\'', '\'');
      fail("should throw");
    } catch (final IllegalArgumentException e) {
      //  pass
    }

    try {
      CharUtils.unquote("''", '\\', '\'', '\'');
      fail("should throw");
    } catch (final IllegalArgumentException e) {
      //  pass
    }

    try {
      CharUtils.unquote("'xx'", '\\', '\'', '\'');
      fail("should throw");
    } catch (final IllegalArgumentException e) {
      //  pass
    }

    try {
      CharUtils.unquote("'xxx'", '\\', '\'', '\'');
      fail("should throw");
    } catch (final IllegalArgumentException e) {
      //  pass
    }

    try {
      CharUtils.unquote("''\\'", '\\', '\'', '\'');
      fail("should throw");
    } catch (final IllegalArgumentException e) {
      //  pass
    }

    try {
      CharUtils.unquote("[x)", '\\', '[', ']');
      fail("should throw");
    } catch (final IllegalArgumentException e) {
      //  pass
    }

    try {
      CharUtils.unquote("[\\]", '\\', '[', ']');
      fail("should throw");
    } catch (final IllegalArgumentException e) {
      //  pass
    }
  }
}
