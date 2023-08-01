////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text;

import ltd.qubit.commons.lang.StringUtilsTestBase;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class StripperTest extends StringUtilsTestBase {

  @Test
  public void testStrip_String() {
    // null strip
    assertNull(new Stripper().strip(null));
    assertEquals("", new Stripper().strip(""));
    assertEquals("", new Stripper().strip("        "));
    assertEquals("abc", new Stripper().strip("  abc  "));
    assertEquals(NON_BLANK, new Stripper().strip(BLANK + NON_BLANK + BLANK));

    // "" strip
    assertNull(new Stripper().ofCharsIn(" ").strip(null));
    assertEquals("", new Stripper().ofCharsIn("").strip(""));
    assertEquals("        ", new Stripper().ofCharsIn("").strip("        "));
    assertEquals("  abc  ", new Stripper().ofCharsIn("").strip("  abc  "));
    assertEquals(BLANK, new Stripper().ofCharsIn("").strip(BLANK));

    // " " strip
    assertNull(new Stripper().ofCharsIn(" ").strip(null));
    assertEquals("", new Stripper().ofCharsIn(" ").strip(""));
    assertEquals("", new Stripper().ofCharsIn(" ").strip("        "));
    assertEquals("abc", new Stripper().ofCharsIn(" ").strip("  abc  "));

    // "ab" strip
    assertNull(new Stripper().ofCharsIn("ab").strip(null));
    assertEquals("", new Stripper().ofCharsIn("ab").strip(""));
    assertEquals("        ", new Stripper().ofCharsIn("ab").strip("        "));
    assertEquals("  abc  ", new Stripper().ofCharsIn("ab").strip("  abc  "));
    assertEquals("c", new Stripper().ofCharsIn("ab").strip("abcabab"));
    assertEquals(BLANK, new Stripper().ofCharsIn("").strip(BLANK));
  }

  @Test
  public void testStripStart_String_String() {
    // null stripStart
    assertNull(new Stripper().fromStart().strip(null));
    assertEquals("", new Stripper().fromStart().strip(""));
    assertEquals("", new Stripper().fromStart().strip("        "));
    assertEquals("abc  ", new Stripper().fromStart().strip("  abc  "));
    assertEquals(NON_BLANK + BLANK,
        new Stripper().fromStart().strip(BLANK + NON_BLANK + BLANK));

    // "" stripStart
    assertNull(new Stripper().ofCharsIn("").fromStart().strip(null));
    assertEquals("", new Stripper().ofCharsIn("").fromStart().strip(""));
    assertEquals("        ", new Stripper().ofCharsIn("").fromStart().strip("        "));
    assertEquals("  abc  ", new Stripper().ofCharsIn("").fromStart().strip("  abc  "));
    assertEquals(BLANK, new Stripper().ofCharsIn("").fromStart().strip(BLANK));

    // " " stripStart
    assertNull(new Stripper().ofCharsIn(" ").fromStart().strip(null));
    assertEquals("", new Stripper().ofCharsIn(" ").fromStart().strip(""));
    assertEquals("", new Stripper().ofCharsIn(" ").fromStart().strip("        "));
    assertEquals("abc  ", new Stripper().ofCharsIn(" ").fromStart().strip("  abc  "));

    // "ab" stripStart
    assertNull(new Stripper().ofCharsIn("ab").fromStart().strip(null));
    assertEquals("", new Stripper().ofCharsIn("ab").fromStart().strip(""));
    assertEquals("        ", new Stripper().ofCharsIn("ab").fromStart().strip("        "));
    assertEquals("  abc  ", new Stripper().ofCharsIn("ab").fromStart().strip("  abc  "));
    assertEquals("cabab", new Stripper().ofCharsIn("ab").fromStart().strip("abcabab"));
    assertEquals(BLANK, new Stripper().ofCharsIn("").fromStart().strip(BLANK));
  }

  @Test
  public void testStripEnd_String_String() {
    // null stripEnd
    assertNull(new Stripper().fromEnd().strip(null));
    assertEquals("", new Stripper().fromEnd().strip(""));
    assertEquals("", new Stripper().fromEnd().strip("        "));
    assertEquals("  abc", new Stripper().fromEnd().strip("  abc  "));
    assertEquals(BLANK + NON_BLANK, new Stripper().fromEnd().strip(BLANK + NON_BLANK + BLANK));

    // "" stripEnd
    assertNull(new Stripper().ofCharsIn("").fromEnd().strip(null));
    assertEquals("", new Stripper().ofCharsIn("").fromEnd().strip(""));
    assertEquals("        ", new Stripper().ofCharsIn("").fromEnd().strip("        "));
    assertEquals("  abc  ", new Stripper().ofCharsIn("").fromEnd().strip("  abc  "));
    assertEquals(BLANK, new Stripper().ofCharsIn("").fromEnd().strip(BLANK));

    // " " stripEnd
    assertNull(new Stripper().ofCharsIn(" ").fromEnd().strip(null));
    assertEquals("", new Stripper().ofCharsIn(" ").fromEnd().strip(""));
    assertEquals("", new Stripper().ofCharsIn(" ").fromEnd().strip("        "));
    assertEquals("  abc", new Stripper().ofCharsIn(" ").fromEnd().strip("  abc  "));

    // "ab" stripEnd
    assertNull(new Stripper().ofCharsIn("ab").fromEnd().strip(null));
    assertEquals("", new Stripper().ofCharsIn("ab").fromEnd().strip(""));
    assertEquals("        ", new Stripper().ofCharsIn("ab").fromEnd().strip("        "));
    assertEquals("  abc  ", new Stripper().ofCharsIn("ab").fromEnd().strip("  abc  "));
    assertEquals("abc", new Stripper().ofCharsIn("ab").fromEnd().strip("abcabab"));
    assertEquals(BLANK, new Stripper().ofCharsIn("").fromEnd().strip(BLANK));
  }
}
