////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text;

import org.junit.jupiter.api.Test;

import static ltd.qubit.commons.text.CaseFormat.LOWER_CAMEL;
import static ltd.qubit.commons.text.CaseFormat.LOWER_HYPHEN;
import static ltd.qubit.commons.text.CaseFormat.LOWER_UNDERSCORE;
import static ltd.qubit.commons.text.CaseFormat.UPPER_CAMEL;
import static ltd.qubit.commons.text.CaseFormat.UPPER_UNDERSCORE;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

/**
 * Unit test for the {@link CaseFormat} class.
 *
 * @author Haixing Hu
 */
public class CaseFormatTest {

  @Test
  public void testIdentity() {
    for (final CaseFormat from : CaseFormat.values()) {
      assertSame("foo", from.to(from, "foo"), from + " to " + from);
      for (final CaseFormat to : CaseFormat.values()) {
        assertEquals("", from.to(to, ""), from + " to " + to);
        assertEquals(" ", from.to(to, " "), from + " to " + to);
      }
    }
  }

  @Test
  public void testLowerHyphenToLowerHyphen() {
    assertNull(LOWER_HYPHEN.to(LOWER_HYPHEN, null));
    assertEquals("", LOWER_HYPHEN.to(LOWER_HYPHEN, ""));
    assertEquals("foo", LOWER_HYPHEN.to(LOWER_HYPHEN, "foo"));
    assertEquals("foo-bar", LOWER_HYPHEN.to(LOWER_HYPHEN, "foo-bar"));
  }

  @Test
  public void testLowerHyphenToLowerUnderscore() {
    assertNull(LOWER_HYPHEN.to(LOWER_UNDERSCORE, null));
    assertEquals("", LOWER_HYPHEN.to(LOWER_UNDERSCORE, ""));
    assertEquals("foo", LOWER_HYPHEN.to(LOWER_UNDERSCORE, "foo"));
    assertEquals("foo_bar", LOWER_HYPHEN.to(LOWER_UNDERSCORE, "foo-bar"));
  }

  @Test
  public void testLowerHyphenToLowerCamel() {
    assertNull(LOWER_HYPHEN.to(LOWER_CAMEL, null));
    assertEquals("", LOWER_HYPHEN.to(LOWER_CAMEL, ""));
    assertEquals("foo", LOWER_HYPHEN.to(LOWER_CAMEL, "foo"));
    assertEquals("fooBar", LOWER_HYPHEN.to(LOWER_CAMEL, "foo-bar"));
  }

  @Test
  public void testLowerHyphenToUpperCamel() {
    assertNull(LOWER_HYPHEN.to(UPPER_CAMEL, null));
    assertEquals("", LOWER_HYPHEN.to(UPPER_CAMEL, ""));
    assertEquals("Foo", LOWER_HYPHEN.to(UPPER_CAMEL, "foo"));
    assertEquals("FooBar", LOWER_HYPHEN.to(UPPER_CAMEL, "foo-bar"));
  }

  @Test
  public void testLowerHyphenToUpperUnderscore() {
    assertNull(LOWER_HYPHEN.to(UPPER_UNDERSCORE, null));
    assertEquals("", LOWER_HYPHEN.to(UPPER_UNDERSCORE, ""));
    assertEquals("FOO", LOWER_HYPHEN.to(UPPER_UNDERSCORE, "foo"));
    assertEquals("FOO_BAR", LOWER_HYPHEN.to(UPPER_UNDERSCORE, "foo-bar"));
  }

  @Test
  public void testLowerUnderscoreToLowerHyphen() {
    assertNull(LOWER_UNDERSCORE.to(LOWER_HYPHEN, null));
    assertEquals("", LOWER_UNDERSCORE.to(LOWER_HYPHEN, ""));
    assertEquals("foo", LOWER_UNDERSCORE.to(LOWER_HYPHEN, "foo"));
    assertEquals("foo-bar", LOWER_UNDERSCORE.to(LOWER_HYPHEN, "foo_bar"));
  }

  @Test
  public void testLowerUnderscoreToLowerUnderscore() {
    assertNull(LOWER_UNDERSCORE.to(LOWER_UNDERSCORE, null));
    assertEquals("", LOWER_UNDERSCORE.to(LOWER_UNDERSCORE, ""));
    assertEquals("foo", LOWER_UNDERSCORE.to(LOWER_UNDERSCORE, "foo"));
    assertEquals("foo_bar", LOWER_UNDERSCORE.to(LOWER_UNDERSCORE, "foo_bar"));
  }

  @Test
  public void testLowerUnderscoreToLowerCamel() {
    assertNull(LOWER_UNDERSCORE.to(LOWER_CAMEL, null));
    assertEquals("", LOWER_UNDERSCORE.to(LOWER_CAMEL, ""));
    assertEquals("foo", LOWER_UNDERSCORE.to(LOWER_CAMEL, "foo"));
    assertEquals("fooBar", LOWER_UNDERSCORE.to(LOWER_CAMEL, "foo_bar"));
  }

  @Test
  public void testLowerUnderscoreToUpperCamel() {
    assertNull(LOWER_UNDERSCORE.to(UPPER_CAMEL, null));
    assertEquals("", LOWER_UNDERSCORE.to(UPPER_CAMEL, ""));
    assertEquals("Foo", LOWER_UNDERSCORE.to(UPPER_CAMEL, "foo"));
    assertEquals("FooBar", LOWER_UNDERSCORE.to(UPPER_CAMEL, "foo_bar"));
  }

  @Test
  public void testLowerUnderscoreToUpperUnderscore() {
    assertNull(LOWER_UNDERSCORE.to(UPPER_UNDERSCORE, null));
    assertEquals("", LOWER_UNDERSCORE.to(UPPER_UNDERSCORE, ""));
    assertEquals("FOO", LOWER_UNDERSCORE.to(UPPER_UNDERSCORE, "foo"));
    assertEquals("FOO_BAR", LOWER_UNDERSCORE.to(UPPER_UNDERSCORE, "foo_bar"));
  }

  @Test
  public void testLowerCamelToLowerHyphen() {
    assertNull(LOWER_CAMEL.to(LOWER_HYPHEN, null));
    assertEquals("", LOWER_CAMEL.to(LOWER_HYPHEN, ""));
    assertEquals("foo", LOWER_CAMEL.to(LOWER_HYPHEN, "foo"));
    assertEquals("foo-bar", LOWER_CAMEL.to(LOWER_HYPHEN, "fooBar"));
    assertEquals("foo-bar", LOWER_CAMEL.to(LOWER_HYPHEN, "FooBar"));
    assertEquals("h-t-t-p", LOWER_CAMEL.to(LOWER_HYPHEN, "HTTP"));
  }

  @Test
  public void testLowerCamelToLowerUnderscore() {
    assertNull(LOWER_CAMEL.to(LOWER_UNDERSCORE, null));
    assertEquals("", LOWER_CAMEL.to(LOWER_UNDERSCORE, ""));
    assertEquals("foo", LOWER_CAMEL.to(LOWER_UNDERSCORE, "foo"));
    assertEquals("foo_bar", LOWER_CAMEL.to(LOWER_UNDERSCORE, "fooBar"));
    assertEquals("h_t_t_p", LOWER_CAMEL.to(LOWER_UNDERSCORE, "hTTP"));
    assertEquals("user_id", LOWER_CAMEL.to(LOWER_UNDERSCORE, "user_id"));
  }

  @Test
  public void testLowerCamelToLowerCamel() {
    assertNull(LOWER_CAMEL.to(LOWER_CAMEL, null));
    assertEquals("", LOWER_CAMEL.to(LOWER_CAMEL, ""));
    assertEquals("foo", LOWER_CAMEL.to(LOWER_CAMEL, "foo"));
    assertEquals("fooBar", LOWER_CAMEL.to(LOWER_CAMEL, "fooBar"));
  }

  @Test
  public void testLowerCamelToUpperCamel() {
    assertNull(LOWER_CAMEL.to(UPPER_CAMEL, null));
    assertEquals("", LOWER_CAMEL.to(UPPER_CAMEL, ""));
    assertEquals("Foo", LOWER_CAMEL.to(UPPER_CAMEL, "foo"));
    assertEquals("FooBar", LOWER_CAMEL.to(UPPER_CAMEL, "fooBar"));
    assertEquals("HTTP", LOWER_CAMEL.to(UPPER_CAMEL, "hTTP"));
  }

  @Test
  public void testLowerCamelToUpperUnderscore() {
    assertNull(LOWER_CAMEL.to(UPPER_UNDERSCORE, null));
    assertEquals("", LOWER_CAMEL.to(UPPER_UNDERSCORE, ""));
    assertEquals("FOO", LOWER_CAMEL.to(UPPER_UNDERSCORE, "foo"));
    assertEquals("FOO_BAR", LOWER_CAMEL.to(UPPER_UNDERSCORE, "fooBar"));
  }

  @Test
  public void testUpperCamelToLowerHyphen() {
    assertNull(UPPER_CAMEL.to(LOWER_HYPHEN, null));
    assertEquals("", UPPER_CAMEL.to(LOWER_HYPHEN, ""));
    assertEquals("foo", UPPER_CAMEL.to(LOWER_HYPHEN, "Foo"));
    assertEquals("foo-bar", UPPER_CAMEL.to(LOWER_HYPHEN, "FooBar"));
  }

  @Test
  public void testUpperCamelToLowerUnderscore() {
    assertNull(UPPER_CAMEL.to(LOWER_UNDERSCORE, null));
    assertEquals("", UPPER_CAMEL.to(LOWER_UNDERSCORE, ""));
    assertEquals("", UPPER_CAMEL.to(LOWER_UNDERSCORE, ""));
    assertEquals("foo", UPPER_CAMEL.to(LOWER_UNDERSCORE, "Foo"));
    assertEquals("foo_bar", UPPER_CAMEL.to(LOWER_UNDERSCORE, "FooBar"));
    assertEquals("a", UPPER_CAMEL.to(LOWER_UNDERSCORE, "a"));
    assertEquals("abc", UPPER_CAMEL.to(LOWER_UNDERSCORE, "Abc"));
    assertEquals("abc_def", UPPER_CAMEL.to(LOWER_UNDERSCORE, "AbcDef"));
    assertEquals("abc_def", UPPER_CAMEL.to(LOWER_UNDERSCORE, "abcDef"));
    assertEquals("a_a_b_b", UPPER_CAMEL.to(LOWER_UNDERSCORE, "AABB"));
    assertEquals("m__owner_id", UPPER_CAMEL.to(LOWER_UNDERSCORE, "m_OwnerId"));
  }

  @Test
  public void testUpperCamelToLowerCamel() {
    assertNull(UPPER_CAMEL.to(LOWER_CAMEL, null));
    assertEquals("", UPPER_CAMEL.to(LOWER_CAMEL, ""));
    assertEquals("foo", UPPER_CAMEL.to(LOWER_CAMEL, "Foo"));
    assertEquals("fooBar", UPPER_CAMEL.to(LOWER_CAMEL, "FooBar"));
    assertEquals("hTTP", UPPER_CAMEL.to(LOWER_CAMEL, "HTTP"));
  }

  @Test
  public void testUpperCamelToUpperCamel() {
    assertNull(UPPER_CAMEL.to(UPPER_CAMEL, null));
    assertEquals("", UPPER_CAMEL.to(UPPER_CAMEL, ""));
    assertEquals("Foo", UPPER_CAMEL.to(UPPER_CAMEL, "Foo"));
    assertEquals("FooBar", UPPER_CAMEL.to(UPPER_CAMEL, "FooBar"));
  }

  @Test
  public void testUpperCamelToUpperUnderscore() {
    assertNull(UPPER_CAMEL.to(UPPER_UNDERSCORE, null));
    assertEquals("", UPPER_CAMEL.to(UPPER_UNDERSCORE, ""));
    assertEquals("FOO", UPPER_CAMEL.to(UPPER_UNDERSCORE, "Foo"));
    assertEquals("FOO_BAR", UPPER_CAMEL.to(UPPER_UNDERSCORE, "FooBar"));
    assertEquals("H_T_T_P", UPPER_CAMEL.to(UPPER_UNDERSCORE, "HTTP"));
    assertEquals("H__T__T__P", UPPER_CAMEL.to(UPPER_UNDERSCORE, "H_T_T_P"));
    assertEquals("A", UPPER_CAMEL.to(UPPER_UNDERSCORE, "a"));
    assertEquals("ABC", UPPER_CAMEL.to(UPPER_UNDERSCORE, "Abc"));
    assertEquals("ABC_DEF", UPPER_CAMEL.to(UPPER_UNDERSCORE, "AbcDef"));
    assertEquals("ABC_DEF", UPPER_CAMEL.to(UPPER_UNDERSCORE, "abcDef"));
    assertEquals("A_A_B_B", UPPER_CAMEL.to(UPPER_UNDERSCORE, "AABB"));
    assertEquals("M__OWNER_ID", UPPER_CAMEL.to(UPPER_UNDERSCORE, "m_OwnerId"));
  }

  @Test
  public void testUpperUnderscoreToLowerHyphen() {
    assertNull(UPPER_UNDERSCORE.to(LOWER_HYPHEN, null));
    assertEquals("", UPPER_UNDERSCORE.to(LOWER_HYPHEN, ""));
    assertEquals("foo", UPPER_UNDERSCORE.to(LOWER_HYPHEN, "FOO"));
    assertEquals("foo-bar", UPPER_UNDERSCORE.to(LOWER_HYPHEN, "FOO_BAR"));
  }

  @Test
  public void testUpperUnderscoreToLowerUnderscore() {
    assertNull(UPPER_UNDERSCORE.to(LOWER_UNDERSCORE, null));
    assertEquals("", UPPER_UNDERSCORE.to(LOWER_UNDERSCORE, ""));
    assertEquals("foo", UPPER_UNDERSCORE.to(LOWER_UNDERSCORE, "FOO"));
    assertEquals("foo_bar", UPPER_UNDERSCORE.to(LOWER_UNDERSCORE, "FOO_BAR"));
  }

  @Test
  public void testUpperUnderscoreToLowerCamel() {
    assertNull(UPPER_UNDERSCORE.to(LOWER_CAMEL, null));
    assertEquals("", UPPER_UNDERSCORE.to(LOWER_CAMEL, ""));
    assertEquals("foo", UPPER_UNDERSCORE.to(LOWER_CAMEL, "FOO"));
    assertEquals("fooBar", UPPER_UNDERSCORE.to(LOWER_CAMEL, "FOO_BAR"));
  }

  @Test
  public void testUpperUnderscoreToUpperCamel() {
    assertNull(UPPER_UNDERSCORE.to(UPPER_CAMEL, null));
    assertEquals("", UPPER_UNDERSCORE.to(UPPER_CAMEL, ""));
    assertEquals("Foo", UPPER_UNDERSCORE.to(UPPER_CAMEL, "FOO"));
    assertEquals("FooBar", UPPER_UNDERSCORE.to(UPPER_CAMEL, "FOO_BAR"));
    assertEquals("HTTP", UPPER_UNDERSCORE.to(UPPER_CAMEL, "H_T_T_P"));
  }

  @Test
  public void testUpperUnderscoreToUpperUnderscore() {
    assertNull(UPPER_UNDERSCORE.to(UPPER_UNDERSCORE, null));
    assertEquals("", UPPER_UNDERSCORE.to(UPPER_UNDERSCORE, ""));
    assertEquals("FOO", UPPER_UNDERSCORE.to(UPPER_UNDERSCORE, "FOO"));
    assertEquals("FOO_BAR", UPPER_UNDERSCORE.to(UPPER_UNDERSCORE, "FOO_BAR"));
  }

//
//  @Test
//  public void testLowerCamelDotToLowerHyphen() {
//    assertNull(LOWER_CAMEL_DOT.to(LOWER_HYPHEN, null));
//    assertEquals("", LOWER_CAMEL_DOT.to(LOWER_HYPHEN, ""));
//    assertEquals("a", LOWER_CAMEL_DOT.to(LOWER_HYPHEN, "a"));
//    assertEquals("abc", LOWER_CAMEL_DOT.to(LOWER_HYPHEN, "Abc"));
//    assertEquals("abc_def", LOWER_CAMEL_DOT.to(LOWER_HYPHEN, "AbcDef"));
//    assertEquals("abc_def", LOWER_CAMEL_DOT.to(LOWER_HYPHEN, "abcDef"));
//    assertEquals("a_a_b_b", LOWER_CAMEL_DOT.to(LOWER_HYPHEN, "AABB"));
//    assertEquals("a_", LOWER_CAMEL_DOT.to(LOWER_HYPHEN, "a."));
//    assertEquals("ab_c", LOWER_CAMEL_DOT.to(LOWER_HYPHEN, "Ab.c"));
//  }
//
//  @Test
//  public void testLowerCamelDotToLowerUnderscore() {
//    assertNull(LOWER_CAMEL_DOT.to(LOWER_UNDERSCORE, null));
//    assertEquals("", LOWER_CAMEL_DOT.to(LOWER_UNDERSCORE, ""));
//    assertEquals("a", LOWER_CAMEL_DOT.to(LOWER_UNDERSCORE, "a"));
//    assertEquals("abc", LOWER_CAMEL_DOT.to(LOWER_UNDERSCORE, "Abc"));
//    assertEquals("abc_def", LOWER_CAMEL_DOT.to(LOWER_UNDERSCORE, "abcDef"));
//    assertEquals("abc_def", LOWER_CAMEL_DOT.to(LOWER_UNDERSCORE, "AbcDef"));
//    assertEquals("a_a_b_b", LOWER_CAMEL_DOT.to(LOWER_UNDERSCORE, "AABB"));
//    assertEquals("ab_c", LOWER_CAMEL_DOT.to(LOWER_UNDERSCORE, "ab.c"));
//    assertEquals("a_", LOWER_CAMEL_DOT.to(LOWER_UNDERSCORE, "a."));
//    assertEquals("ab_c", LOWER_CAMEL_DOT.to(LOWER_UNDERSCORE, "Ab.c"));
//    assertEquals("abc_de_f", LOWER_CAMEL_DOT.to(LOWER_UNDERSCORE, "Abc.De.f"));
//    assertEquals("abc_x_de_f", LOWER_CAMEL_DOT.to(LOWER_UNDERSCORE, "Abc.xDe.f"));
//    assertEquals("abc_d_ef", LOWER_CAMEL_DOT.to(LOWER_UNDERSCORE, "abcD.ef"));
//    assertEquals("a_a_b_b", LOWER_CAMEL_DOT.to(LOWER_UNDERSCORE, "AAB.B"));
//    assertEquals("a_ab_b", LOWER_CAMEL_DOT.to(LOWER_UNDERSCORE, "A..ab.B"));
//    assertEquals("app_code", LOWER_CAMEL_DOT.to(LOWER_UNDERSCORE, "app.code"));
//    assertEquals("attachment_owner_id", LOWER_CAMEL_DOT.to(LOWER_UNDERSCORE, "attachment.ownerId"));
//    assertEquals("attachment_m_owner_id", LOWER_CAMEL_DOT.to(LOWER_UNDERSCORE, "attachment.m_ownerId"));
//    assertEquals("m_owner_id", LOWER_CAMEL_DOT.to(LOWER_UNDERSCORE, "m_ownerId"));
//    assertEquals("m_owner_id", LOWER_CAMEL_DOT.to(LOWER_UNDERSCORE, "m_OwnerId"));
//  }
//
//  @Test
//  public void testLowerCamelDotToLowerCamel() {
//
//  }
//
//  @Test
//  public void testLowerCamelDotToUpperCamel() {
//
//  }
//
//  @Test
//  public void testLowerCamelDotToUpperUnderscore() {
//
//  }
}
