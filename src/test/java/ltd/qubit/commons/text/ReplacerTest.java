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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class ReplacerTest {

  @Test
  public void testReplaceStringWithString() {
    assertNull(new Replacer()
        .forSubstring(null)
        .withString(null)
        .limit(-1)
        .ignoreCase(false)
        .replace(null));
    assertNull(new Replacer()
        .forSubstring(null)
        .withString("any")
        .limit(-1)
        .ignoreCase(false)
        .replace(null));
    assertNull(new Replacer()
        .forSubstring("any")
        .withString(null)
        .limit(-1)
        .ignoreCase(false)
        .replace(null));
    assertNull(new Replacer()
        .forSubstring("any")
        .withString("any")
        .limit(-1)
        .ignoreCase(false)
        .replace(null));

    assertNull(new Replacer()
        .forSubstring(null)
        .withString(null)
        .limit(2)
        .ignoreCase(false)
        .replace(null));
    assertNull(new Replacer()
        .forSubstring(null)
        .withString("any")
        .limit(2)
        .ignoreCase(false)
        .replace(null));
    assertNull(new Replacer()
        .forSubstring("any")
        .withString(null)
        .limit(2)
        .ignoreCase(false)
        .replace(null));
    assertNull(new Replacer()
        .forSubstring("any")
        .withString("any")
        .limit(2)
        .ignoreCase(false)
        .replace(null));

    assertEquals("", new Replacer()
        .forSubstring(null)
        .withString(null)
        .limit(-1)
        .ignoreCase(false)
        .replace(""));
    assertEquals("", new Replacer()
        .forSubstring(null)
        .withString("any")
        .limit(-1)
        .ignoreCase(false)
        .replace(""));
    assertEquals("", new Replacer()
        .forSubstring("any")
        .withString(null)
        .limit(-1)
        .ignoreCase(false)
        .replace(""));
    assertEquals("", new Replacer()
        .forSubstring("any")
        .withString("any")
        .limit(-1)
        .ignoreCase(false)
        .replace(""));

    assertEquals("FOO", new Replacer()
        .forSubstring("")
        .withString("any")
        .limit(-1)
        .ignoreCase(false)
        .replace("FOO"));
    assertEquals("FOO", new Replacer()
        .forSubstring(null)
        .withString("any")
        .limit(-1)
        .ignoreCase(false)
        .replace("FOO"));
    assertEquals("OO", new Replacer()
        .forSubstring("F")
        .withString(null)
        .limit(-1)
        .ignoreCase(false)
        .replace("FOO"));
    assertEquals("FOO", new Replacer()
        .forSubstring(null)
        .withString(null)
        .limit(-1)
        .ignoreCase(false)
        .replace("FOO"));

    assertEquals("", new Replacer()
        .forSubstring("foo")
        .withString("")
        .limit(-1)
        .ignoreCase(false)
        .replace("foofoofoo"));
    assertEquals("barbarbar", new Replacer()
        .forSubstring("foo")
        .withString("bar")
        .limit(-1)
        .ignoreCase(false)
        .replace("foofoofoo"));
    assertEquals("farfarfar", new Replacer()
        .forSubstring("oo")
        .withString("ar")
        .limit(-1)
        .ignoreCase(false)
        .replace("foofoofoo"));

    assertEquals("", new Replacer()
        .forSubstring(null)
        .withString(null)
        .limit(2)
        .ignoreCase(false)
        .replace(""));
    assertEquals("", new Replacer()
        .forSubstring(null)
        .withString("any")
        .limit(2)
        .ignoreCase(false)
        .replace(""));
    assertEquals("", new Replacer()
        .forSubstring("any")
        .withString(null)
        .limit(2)
        .ignoreCase(false)
        .replace(""));
    assertEquals("", new Replacer()
        .forSubstring("any")
        .withString("any")
        .limit(2)
        .ignoreCase(false)
        .replace(""));

    final String str = new String(new char[]{'o', 'o', 'f', 'o', 'o'});
    assertEquals(str, new Replacer()
        .forSubstring("x")
        .withString("")
        .limit(-1)
        .ignoreCase(false)
        .replace(str));

    assertEquals("f", new Replacer()
        .forSubstring("o")
        .withString("")
        .limit(-1)
        .ignoreCase(false)
        .replace("oofoo"));
    assertEquals("oofoo", new Replacer()
        .forSubstring("o")
        .withString("")
        .limit(0)
        .ignoreCase(false)
        .replace("oofoo"));
    assertEquals("ofoo", new Replacer()
        .forSubstring("o")
        .withString("")
        .limit(1)
        .ignoreCase(false)
        .replace("oofoo"));
    assertEquals("foo", new Replacer()
        .forSubstring("o")
        .withString("")
        .limit(2)
        .ignoreCase(false)
        .replace("oofoo"));
    assertEquals("fo", new Replacer()
        .forSubstring("o")
        .withString("")
        .limit(3)
        .ignoreCase(false)
        .replace("oofoo"));
    assertEquals("f", new Replacer()
        .forSubstring("o")
        .withString("")
        .limit(4)
        .ignoreCase(false)
        .replace("oofoo"));

    assertEquals("f", new Replacer()
        .forSubstring("o")
        .withString("")
        .limit(-5)
        .ignoreCase(false)
        .replace("oofoo"));
    assertEquals("f", new Replacer()
        .forSubstring("o")
        .withString("")
        .limit(1000)
        .ignoreCase(false)
        .replace("oofoo"));

    assertNull(new Replacer()
        .forSubstring(null)
        .withString(null)
        .limit(1)
        .ignoreCase(false)
        .replace(null));
    assertNull(new Replacer()
        .forSubstring(null)
        .withString("any")
        .limit(1)
        .ignoreCase(false)
        .replace(null));
    assertNull(new Replacer()
        .forSubstring("any")
        .withString(null)
        .limit(1)
        .ignoreCase(false)
        .replace(null));
    assertNull(new Replacer()
        .forSubstring("any")
        .withString("any")
        .limit(1)
        .ignoreCase(false)
        .replace(null));

    assertEquals("", new Replacer()
        .forSubstring(null)
        .withString(null)
        .limit(1)
        .ignoreCase(false)
        .replace(""));
    assertEquals("", new Replacer()
        .forSubstring(null)
        .withString("any")
        .limit(1)
        .ignoreCase(false)
        .replace(""));
    assertEquals("", new Replacer()
        .forSubstring("any")
        .withString(null)
        .limit(1)
        .ignoreCase(false)
        .replace(""));
    assertEquals("", new Replacer()
        .forSubstring("any")
        .withString("any")
        .limit(1)
        .ignoreCase(false)
        .replace(""));

    assertEquals("FOO", new Replacer()
        .forSubstring("")
        .withString("any")
        .limit(1)
        .ignoreCase(false)
        .replace("FOO"));
    assertEquals("FOO", new Replacer()
        .forSubstring(null)
        .withString("any")
        .limit(1)
        .ignoreCase(false)
        .replace("FOO"));
    assertEquals("OO", new Replacer()
        .forSubstring("F")
        .withString(null)
        .limit(1)
        .ignoreCase(false)
        .replace("FOO"));
    assertEquals("FOO", new Replacer()
        .forSubstring(null)
        .withString(null)
        .limit(1)
        .ignoreCase(false)
        .replace("FOO"));

    assertEquals("foofoo", new Replacer()
        .forSubstring("foo")
        .withString("")
        .limit(1)
        .ignoreCase(false)
        .replace("foofoofoo"));
  }

  @Test
  public void testReplaceCharWithChar() {
    assertNull(new Replacer().forChar('b').withChar('z').replace(null));
    assertEquals("", new Replacer().forChar('b').withChar('z').replace(""));
    assertEquals("azcza",
        new Replacer().forChar('b').withChar('z').replace("abcba"));
    assertEquals("abcba",
        new Replacer().forChar('x').withChar('z').replace("abcba"));
  }

  @Test
  public void testReplaceCharWithString() {
    assertNull(new Replacer().forChar('b').withString("xyz").replace(null));
    assertEquals("", new Replacer().forChar('b').withString("xyz").replace(""));
    assertEquals("axyzcxyza",
        new Replacer().forChar('b').withString("xyz").replace("abcba"));
    assertEquals("abcba",
        new Replacer().forChar('x').withString("xyz").replace("abcba"));
    assertEquals("aca",
        new Replacer().forChar('b').withString("").replace("abcba"));
    assertEquals("aca",
        new Replacer().forChar('b').withString(null).replace("abcba"));
    // not replace repeatedly
    assertEquals("axbxcxbxa",
        new Replacer().forChar('b').withString("xbx").replace("abcba"));
  }

  @Test
  public void testReplaceCodePointWithCodePoint() {
    assertNull(new Replacer()
        .forCodePoint('⚽')
        .withCodePoint("\uD83C\uDFC6")
        .replace(null));
    assertEquals("",
        new Replacer()
        .forCodePoint('⚽')
        .withCodePoint("\uD83C\uDFC6")
        .replace(""));

    assertEquals("World Cup \uD83C\uDFC6 2022 \uD83C\uDFC6",
        new Replacer()
        .forCodePoint('⚽')
        .withCodePoint("\uD83C\uDFC6")
        .replace("World Cup ⚽ 2022 ⚽"));

    assertEquals("World Cup  2022 ",
        new Replacer()
        .forCodePoint('⚽')
        .withCodePoint("")
        .replace("World Cup ⚽ 2022 ⚽"));

    assertEquals("World Cup  2022 ",
        new Replacer()
        .forCodePoint('⚽')
        .withCodePoint(null)
        .replace("World Cup ⚽ 2022 ⚽"));

    assertEquals("World Cup ⚽ 2022 ⚽",
        new Replacer()
            .forCodePoint("\uD83C\uDFC6")
            .withCodePoint("⚽")
            .replace("World Cup \uD83C\uDFC6 2022 \uD83C\uDFC6"));

    assertEquals("World Cup \uD83C⚽ 2022 ⚽",
        new Replacer()
            .forCodePoint("\uD83C\uDFC6")
            .withCodePoint("⚽")
            .replace("World Cup \uD83C\uD83C\uDFC6 2022 \uD83C\uDFC6"));
  }

}
