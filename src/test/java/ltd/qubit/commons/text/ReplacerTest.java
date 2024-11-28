////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
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
        .searchForSubstring(null)
        .replaceWithString(null)
        .limit(-1)
        .ignoreCase(false)
        .applyTo(null));
    assertNull(new Replacer()
        .searchForSubstring(null)
        .replaceWithString("any")
        .limit(-1)
        .ignoreCase(false)
        .applyTo(null));
    assertNull(new Replacer()
        .searchForSubstring("any")
        .replaceWithString(null)
        .limit(-1)
        .ignoreCase(false)
        .applyTo(null));
    assertNull(new Replacer()
        .searchForSubstring("any")
        .replaceWithString("any")
        .limit(-1)
        .ignoreCase(false)
        .applyTo(null));

    assertNull(new Replacer()
        .searchForSubstring(null)
        .replaceWithString(null)
        .limit(2)
        .ignoreCase(false)
        .applyTo(null));
    assertNull(new Replacer()
        .searchForSubstring(null)
        .replaceWithString("any")
        .limit(2)
        .ignoreCase(false)
        .applyTo(null));
    assertNull(new Replacer()
        .searchForSubstring("any")
        .replaceWithString(null)
        .limit(2)
        .ignoreCase(false)
        .applyTo(null));
    assertNull(new Replacer()
        .searchForSubstring("any")
        .replaceWithString("any")
        .limit(2)
        .ignoreCase(false)
        .applyTo(null));

    assertEquals("", new Replacer()
        .searchForSubstring(null)
        .replaceWithString(null)
        .limit(-1)
        .ignoreCase(false)
        .applyTo(""));
    assertEquals("", new Replacer()
        .searchForSubstring(null)
        .replaceWithString("any")
        .limit(-1)
        .ignoreCase(false)
        .applyTo(""));
    assertEquals("", new Replacer()
        .searchForSubstring("any")
        .replaceWithString(null)
        .limit(-1)
        .ignoreCase(false)
        .applyTo(""));
    assertEquals("", new Replacer()
        .searchForSubstring("any")
        .replaceWithString("any")
        .limit(-1)
        .ignoreCase(false)
        .applyTo(""));

    assertEquals("FOO", new Replacer()
        .searchForSubstring("")
        .replaceWithString("any")
        .limit(-1)
        .ignoreCase(false)
        .applyTo("FOO"));
    assertEquals("FOO", new Replacer()
        .searchForSubstring(null)
        .replaceWithString("any")
        .limit(-1)
        .ignoreCase(false)
        .applyTo("FOO"));
    assertEquals("OO", new Replacer()
        .searchForSubstring("F")
        .replaceWithString(null)
        .limit(-1)
        .ignoreCase(false)
        .applyTo("FOO"));
    assertEquals("FOO", new Replacer()
        .searchForSubstring(null)
        .replaceWithString(null)
        .limit(-1)
        .ignoreCase(false)
        .applyTo("FOO"));

    assertEquals("", new Replacer()
        .searchForSubstring("foo")
        .replaceWithString("")
        .limit(-1)
        .ignoreCase(false)
        .applyTo("foofoofoo"));
    assertEquals("barbarbar", new Replacer()
        .searchForSubstring("foo")
        .replaceWithString("bar")
        .limit(-1)
        .ignoreCase(false)
        .applyTo("foofoofoo"));
    assertEquals("farfarfar", new Replacer()
        .searchForSubstring("oo")
        .replaceWithString("ar")
        .limit(-1)
        .ignoreCase(false)
        .applyTo("foofoofoo"));

    assertEquals("", new Replacer()
        .searchForSubstring(null)
        .replaceWithString(null)
        .limit(2)
        .ignoreCase(false)
        .applyTo(""));
    assertEquals("", new Replacer()
        .searchForSubstring(null)
        .replaceWithString("any")
        .limit(2)
        .ignoreCase(false)
        .applyTo(""));
    assertEquals("", new Replacer()
        .searchForSubstring("any")
        .replaceWithString(null)
        .limit(2)
        .ignoreCase(false)
        .applyTo(""));
    assertEquals("", new Replacer()
        .searchForSubstring("any")
        .replaceWithString("any")
        .limit(2)
        .ignoreCase(false)
        .applyTo(""));

    final String str = new String(new char[]{'o', 'o', 'f', 'o', 'o'});
    assertEquals(str, new Replacer()
        .searchForSubstring("x")
        .replaceWithString("")
        .limit(-1)
        .ignoreCase(false)
        .applyTo(str));

    assertEquals("f", new Replacer()
        .searchForSubstring("o")
        .replaceWithString("")
        .limit(-1)
        .ignoreCase(false)
        .applyTo("oofoo"));
    assertEquals("oofoo", new Replacer()
        .searchForSubstring("o")
        .replaceWithString("")
        .limit(0)
        .ignoreCase(false)
        .applyTo("oofoo"));
    assertEquals("ofoo", new Replacer()
        .searchForSubstring("o")
        .replaceWithString("")
        .limit(1)
        .ignoreCase(false)
        .applyTo("oofoo"));
    assertEquals("foo", new Replacer()
        .searchForSubstring("o")
        .replaceWithString("")
        .limit(2)
        .ignoreCase(false)
        .applyTo("oofoo"));
    assertEquals("fo", new Replacer()
        .searchForSubstring("o")
        .replaceWithString("")
        .limit(3)
        .ignoreCase(false)
        .applyTo("oofoo"));
    assertEquals("f", new Replacer()
        .searchForSubstring("o")
        .replaceWithString("")
        .limit(4)
        .ignoreCase(false)
        .applyTo("oofoo"));

    assertEquals("f", new Replacer()
        .searchForSubstring("o")
        .replaceWithString("")
        .limit(-5)
        .ignoreCase(false)
        .applyTo("oofoo"));
    assertEquals("f", new Replacer()
        .searchForSubstring("o")
        .replaceWithString("")
        .limit(1000)
        .ignoreCase(false)
        .applyTo("oofoo"));

    assertNull(new Replacer()
        .searchForSubstring(null)
        .replaceWithString(null)
        .limit(1)
        .ignoreCase(false)
        .applyTo(null));
    assertNull(new Replacer()
        .searchForSubstring(null)
        .replaceWithString("any")
        .limit(1)
        .ignoreCase(false)
        .applyTo(null));
    assertNull(new Replacer()
        .searchForSubstring("any")
        .replaceWithString(null)
        .limit(1)
        .ignoreCase(false)
        .applyTo(null));
    assertNull(new Replacer()
        .searchForSubstring("any")
        .replaceWithString("any")
        .limit(1)
        .ignoreCase(false)
        .applyTo(null));

    assertEquals("", new Replacer()
        .searchForSubstring(null)
        .replaceWithString(null)
        .limit(1)
        .ignoreCase(false)
        .applyTo(""));
    assertEquals("", new Replacer()
        .searchForSubstring(null)
        .replaceWithString("any")
        .limit(1)
        .ignoreCase(false)
        .applyTo(""));
    assertEquals("", new Replacer()
        .searchForSubstring("any")
        .replaceWithString(null)
        .limit(1)
        .ignoreCase(false)
        .applyTo(""));
    assertEquals("", new Replacer()
        .searchForSubstring("any")
        .replaceWithString("any")
        .limit(1)
        .ignoreCase(false)
        .applyTo(""));

    assertEquals("FOO", new Replacer()
        .searchForSubstring("")
        .replaceWithString("any")
        .limit(1)
        .ignoreCase(false)
        .applyTo("FOO"));
    assertEquals("FOO", new Replacer()
        .searchForSubstring(null)
        .replaceWithString("any")
        .limit(1)
        .ignoreCase(false)
        .applyTo("FOO"));
    assertEquals("OO", new Replacer()
        .searchForSubstring("F")
        .replaceWithString(null)
        .limit(1)
        .ignoreCase(false)
        .applyTo("FOO"));
    assertEquals("FOO", new Replacer()
        .searchForSubstring(null)
        .replaceWithString(null)
        .limit(1)
        .ignoreCase(false)
        .applyTo("FOO"));

    assertEquals("foofoo", new Replacer()
        .searchForSubstring("foo")
        .replaceWithString("")
        .limit(1)
        .ignoreCase(false)
        .applyTo("foofoofoo"));
  }

  @Test
  public void testReplaceCharWithChar() {
    assertNull(new Replacer().searchForChar('b').replaceWithChar('z').applyTo(null));
    assertEquals("", new Replacer().searchForChar('b').replaceWithChar('z').applyTo(""));
    assertEquals("azcza",
        new Replacer().searchForChar('b').replaceWithChar('z').applyTo("abcba"));
    assertEquals("abcba",
        new Replacer().searchForChar('x').replaceWithChar('z').applyTo("abcba"));
  }

  @Test
  public void testReplaceCharWithString() {
    assertNull(new Replacer().searchForChar('b').replaceWithString("xyz").applyTo(null));
    assertEquals("", new Replacer().searchForChar('b').replaceWithString("xyz").applyTo(""));
    assertEquals("axyzcxyza",
        new Replacer().searchForChar('b').replaceWithString("xyz").applyTo("abcba"));
    assertEquals("abcba",
        new Replacer().searchForChar('x').replaceWithString("xyz").applyTo("abcba"));
    assertEquals("aca",
        new Replacer().searchForChar('b').replaceWithString("").applyTo("abcba"));
    assertEquals("aca",
        new Replacer().searchForChar('b').replaceWithString(null).applyTo("abcba"));
    // not replace repeatedly
    assertEquals("axbxcxbxa",
        new Replacer().searchForChar('b').replaceWithString("xbx").applyTo("abcba"));
  }

  @Test
  public void testReplaceCodePointWithCodePoint() {
    assertNull(new Replacer()
        .searchForCodePoint('⚽')
        .replaceWithCodePoint("\uD83C\uDFC6")
        .applyTo(null));
    assertEquals("",
        new Replacer()
        .searchForCodePoint('⚽')
        .replaceWithCodePoint("\uD83C\uDFC6")
        .applyTo(""));

    assertEquals("World Cup \uD83C\uDFC6 2022 \uD83C\uDFC6",
        new Replacer()
        .searchForCodePoint('⚽')
        .replaceWithCodePoint("\uD83C\uDFC6")
        .applyTo("World Cup ⚽ 2022 ⚽"));

    assertEquals("World Cup  2022 ",
        new Replacer()
        .searchForCodePoint('⚽')
        .replaceWithCodePoint("")
        .applyTo("World Cup ⚽ 2022 ⚽"));

    assertEquals("World Cup  2022 ",
        new Replacer()
        .searchForCodePoint('⚽')
        .replaceWithCodePoint(null)
        .applyTo("World Cup ⚽ 2022 ⚽"));

    assertEquals("World Cup ⚽ 2022 ⚽",
        new Replacer()
            .searchForCodePoint("\uD83C\uDFC6")
            .replaceWithCodePoint("⚽")
            .applyTo("World Cup \uD83C\uDFC6 2022 \uD83C\uDFC6"));

    assertEquals("World Cup \uD83C⚽ 2022 ⚽",
        new Replacer()
            .searchForCodePoint("\uD83C\uDFC6")
            .replaceWithCodePoint("⚽")
            .applyTo("World Cup \uD83C\uD83C\uDFC6 2022 \uD83C\uDFC6"));
  }

}
