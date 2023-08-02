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

/**
 * Unit test of the {@link Ascii} class.
 *
 * @author Haixing Hu
 */
public class AsciiTest {

  /**
   * Test method for {@link Ascii#isAscii(byte)}.
   */
  @Test
  public void testIsAsciiByte() {
    byte ch;

    ch = 0x00;
    assertEquals(true, Ascii.isAscii(ch));

    ch = 0x01;
    assertEquals(true, Ascii.isAscii(ch));

    ch = 0x0F;
    assertEquals(true, Ascii.isAscii(ch));

    ch = 0x7F;
    assertEquals(true, Ascii.isAscii(ch));

    ch = (byte) 0x80;
    assertEquals(false, Ascii.isAscii(ch));

    ch = (byte) 0xFF;
    assertEquals(false, Ascii.isAscii(ch));

    ch = (byte) 0xEF;
    assertEquals(false, Ascii.isAscii(ch));

    ch = Byte.MAX_VALUE;
    assertEquals(true, Ascii.isAscii(ch));

    ch = Byte.MIN_VALUE;
    assertEquals(false, Ascii.isAscii(ch));

    for (ch = 0x00; ch >= 0; ++ch) {
      assertEquals(true, Ascii.isAscii(ch), "ch = " + ch);
    }

    for (ch = 0x00 - 1; ch < 0; --ch) {
      assertEquals(false, Ascii.isAscii(ch), "ch = " + ch);
    }

  }

  /**
   * Test method for {@link Ascii#isAscii(int)}.
   */
  @Test
  public void testIsAsciiInt() {
    int ch;

    ch = 0x00;
    assertEquals(true, Ascii.isAscii(ch));

    ch = 0x01;
    assertEquals(true, Ascii.isAscii(ch));

    ch = 0x0F;
    assertEquals(true, Ascii.isAscii(ch));

    ch = 0x7F;
    assertEquals(true, Ascii.isAscii(ch));

    ch = 0x80;
    assertEquals(false, Ascii.isAscii(ch));

    ch = 0xFF;
    assertEquals(false, Ascii.isAscii(ch));

    ch = 0xEF;
    assertEquals(false, Ascii.isAscii(ch));

    ch = 0xFFFFF;
    assertEquals(false, Ascii.isAscii(ch));

    ch = Integer.MAX_VALUE;
    assertEquals(false, Ascii.isAscii(ch));

    ch = Integer.MIN_VALUE;
    assertEquals(false, Ascii.isAscii(ch));
  }

  /**
   * Test method for {@link Ascii#isWhitespace(int)}.
   */
  @Test
  public void testIsWhitespace() {
    assertEquals(true, Ascii.isWhitespace('\t'));
    assertEquals(true, Ascii.isWhitespace('\n'));
    assertEquals(true, Ascii.isWhitespace('\f'));
    assertEquals(true, Ascii.isWhitespace('\r'));
    assertEquals(true, Ascii.isWhitespace(' '));

    for (int ch = 0; ch < '\t'; ++ch) {
      assertEquals(false, Ascii.isWhitespace(ch), "ch = " + ch);
    }
    for (int ch = '\t' + 1; ch < '\n'; ++ch) {
      assertEquals(false, Ascii.isWhitespace(ch), "ch = " + ch);
    }
    for (int ch = '\n' + 1; ch < '\f'; ++ch) {
      assertEquals(false, Ascii.isWhitespace(ch), "ch = " + ch);
    }
    for (int ch = '\f' + 1; ch < '\r'; ++ch) {
      assertEquals(false, Ascii.isWhitespace(ch), "ch = " + ch);
    }
    for (int ch = '\r' + 1; ch < ' '; ++ch) {
      assertEquals(false, Ascii.isWhitespace(ch), "ch = " + ch);
    }
    for (int ch = ' ' + 1; ch <= 0xFF; ++ch) {
      assertEquals(false, Ascii.isWhitespace(ch), "ch = " + ch);
    }

    assertEquals(false, Ascii.isWhitespace(Integer.MIN_VALUE));
    assertEquals(false, Ascii.isWhitespace(Integer.MAX_VALUE));
  }

  /**
   * Test method for {@link Ascii#isLetter(int)}.
   */
  @Test
  public void testIsLetter() {
    for (int ch = 'A'; ch <= 'Z'; ++ch) {
      assertEquals(true, Ascii.isLetter(ch), "ch = " + (char) ch);
    }
    for (int ch = 'a'; ch <= 'z'; ++ch) {
      assertEquals(true, Ascii.isLetter(ch), "ch = " + (char) ch);
    }

    for (int ch = 0; ch < 'A'; ++ch) {
      assertEquals(false, Ascii.isLetter(ch), "ch = " + (char) ch);
    }
    for (int ch = 'Z' + 1; ch < 'a'; ++ch) {
      assertEquals(false, Ascii.isLetter(ch), "ch = " + (char) ch);
    }
    for (int ch = 'z' + 1; ch <= 0xFF; ++ch) {
      assertEquals(false, Ascii.isLetter(ch), "ch = " + (char) ch);
    }

    assertEquals(false, Ascii.isLetter(0), "ch = " + 0);
    assertEquals(false, Ascii.isLetter(Integer.MIN_VALUE),
        "ch = " + Integer.MIN_VALUE);
    assertEquals(false, Ascii.isLetter(Integer.MAX_VALUE),
        "ch = " + Integer.MAX_VALUE);
  }

  /**
   * Test method for {@link Ascii#isUpperCaseLetter(int)}.
   */
  @Test
  public void testIsUppercaseLetter() {
    for (int ch = 'A'; ch <= 'Z'; ++ch) {
      assertEquals(true, Ascii.isUpperCaseLetter(ch), "ch = " + (char) ch);
    }
    for (int ch = 'a'; ch <= 'z'; ++ch) {
      assertEquals(false, Ascii.isUpperCaseLetter(ch), "ch = " + (char) ch);
    }

    for (int ch = 0; ch < 'A'; ++ch) {
      assertEquals(false, Ascii.isUpperCaseLetter(ch), "ch = " + ch);
    }
    for (int ch = 'Z' + 1; ch < 'a'; ++ch) {
      assertEquals(false, Ascii.isUpperCaseLetter(ch), "ch = " + ch);
    }
    for (int ch = 'z' + 1; ch <= 0xFF; ++ch) {
      assertEquals(false, Ascii.isUpperCaseLetter(ch), "ch = " + ch);
    }

    assertEquals(false, Ascii.isLetter(0), "ch = " + 0);
    assertEquals(false, Ascii.isUpperCaseLetter(Integer.MIN_VALUE),
        "ch = " + Integer.MIN_VALUE);
    assertEquals(false, Ascii.isUpperCaseLetter(Integer.MAX_VALUE),
        "ch = " + Integer.MAX_VALUE);
  }

  /**
   * Test method for {@link Ascii#isLowerCaseLetter(int)}.
   */
  @Test
  public void testIsLowercaseLetter() {
    for (int ch = 'A'; ch <= 'Z'; ++ch) {
      assertEquals(false, Ascii.isLowerCaseLetter(ch), "ch = " + (char) ch);
    }
    for (int ch = 'a'; ch <= 'z'; ++ch) {
      assertEquals(true, Ascii.isLowerCaseLetter(ch), "ch = " + (char) ch);
    }

    for (int ch = 0; ch < 'A'; ++ch) {
      assertEquals(false, Ascii.isLowerCaseLetter(ch), "ch = " + ch);
    }
    for (int ch = 'Z' + 1; ch < 'a'; ++ch) {
      assertEquals(false, Ascii.isLowerCaseLetter(ch), "ch = " + ch);
    }
    for (int ch = 'z' + 1; ch <= 0xFF; ++ch) {
      assertEquals(false, Ascii.isLowerCaseLetter(ch), "ch = " + ch);
    }

    assertEquals(false, Ascii.isLetter(0), "ch = " + 0);
    assertEquals(false, Ascii.isLowerCaseLetter(Integer.MIN_VALUE),
        "ch = " + Integer.MIN_VALUE);
    assertEquals(false, Ascii.isLowerCaseLetter(Integer.MAX_VALUE),
        "ch = " + Integer.MAX_VALUE);
  }

  /**
   * Test method for {@link Ascii#isDigit(int)}.
   */
  @Test
  public void testIsDigit() {
    for (int ch = '0'; ch <= '9'; ++ch) {
      assertEquals(true, Ascii.isDigit(ch), "ch = " + (char) ch);
    }
    for (int ch = 0; ch < '0'; ++ch) {
      assertEquals(false, Ascii.isDigit(ch), "ch = " + ch);
    }
    for (int ch = '9' + 1; ch <= 0xFF; ++ch) {
      assertEquals(false, Ascii.isDigit(ch), "ch = " + ch);
    }
    assertEquals(false, Ascii.isDigit(0), "ch = " + 0);
    assertEquals(false, Ascii.isDigit(Integer.MIN_VALUE),
        "ch = " + Integer.MIN_VALUE);
    assertEquals(false, Ascii.isDigit(Integer.MAX_VALUE),
        "ch = " + Integer.MAX_VALUE);

  }

  /**
   * Test method for {@link Ascii#isLetterOrDigit(int)}.
   */
  @Test
  public void testIsLetterOrDigit() {
    for (int ch = '0'; ch <= '9'; ++ch) {
      assertEquals(true, Ascii.isLetterOrDigit(ch), "ch = " + (char) ch);
    }
    for (int ch = 'A'; ch <= 'Z'; ++ch) {
      assertEquals(true, Ascii.isLetterOrDigit(ch), "ch = " + (char) ch);
    }
    for (int ch = 'a'; ch <= 'z'; ++ch) {
      assertEquals(true, Ascii.isLetterOrDigit(ch), "ch = " + (char) ch);
    }

    for (int ch = 0; ch < '0'; ++ch) {
      assertEquals(false, Ascii.isLetterOrDigit(ch), "ch = " + ch);
    }
    for (int ch = '9' + 1; ch < 'A'; ++ch) {
      assertEquals(false, Ascii.isLetterOrDigit(ch), "ch = " + ch);
    }
    for (int ch = 'Z' + 1; ch < 'a'; ++ch) {
      assertEquals(false, Ascii.isLetterOrDigit(ch), "ch = " + ch);
    }
    for (int ch = 'z' + 1; ch <= 0xFF; ++ch) {
      assertEquals(false, Ascii.isLetterOrDigit(ch), "ch = " + ch);
    }

    assertEquals(false, Ascii.isLetterOrDigit(0), "ch = " + 0);
    assertEquals(false, Ascii.isLetterOrDigit(Integer.MIN_VALUE),
        "ch = " + Integer.MIN_VALUE);
    assertEquals(false, Ascii.isLetterOrDigit(Integer.MAX_VALUE),
        "ch = " + Integer.MAX_VALUE);
  }

  /**
   * Test method for {@link Ascii#isPrintable(int)}.
   */
  @Test
  public void testIsPrintable() {
    for (int ch = 0x20; ch < 0x7F; ++ch) {
      assertEquals(true, Ascii.isPrintable(ch), "ch = " + ch);
    }
    for (int ch = 0; ch < 0x20; ++ch) {
      assertEquals(false, Ascii.isPrintable(ch), "ch = " + ch);
    }
    for (int ch = 0x7F; ch <= 0xFF; ++ch) {
      assertEquals(false, Ascii.isPrintable(ch), "ch = " + ch);
    }

    assertEquals(false, Ascii.isPrintable(0), "ch = " + 0);
    assertEquals(false, Ascii.isPrintable(Integer.MIN_VALUE),
        "ch = " + Integer.MIN_VALUE);
    assertEquals(false, Ascii.isPrintable(Integer.MAX_VALUE),
        "ch = " + Integer.MAX_VALUE);

  }

  /**
   * Test method for {@link Ascii#isControl(int)}.
   */
  @Test
  public void testIsControl() {
    for (int ch = 0; ch < 0x20; ++ch) {
      assertEquals(true, Ascii.isControl(ch), "ch = " + ch);
    }
    for (int ch = 0x20; ch < 0x7F; ++ch) {
      assertEquals(false, Ascii.isControl(ch), "ch = " + ch);
    }
    assertEquals(true, Ascii.isControl(0x7F), "ch = " + 0x7F);
    for (int ch = 0; ch < 0; --ch) {
      assertEquals(false, Ascii.isControl(ch), "ch = " + ch);
    }

    assertEquals(true, Ascii.isControl(0), "ch = " + 0);
    assertEquals(false, Ascii.isControl(Integer.MIN_VALUE),
        "ch = " + Integer.MIN_VALUE);
    assertEquals(false, Ascii.isControl(Integer.MAX_VALUE),
        "ch = " + Integer.MAX_VALUE);
    assertEquals(true, Ascii.isControl(Byte.MAX_VALUE),
        "ch = " + Byte.MAX_VALUE);
  }

  /**
   * Test method for {@link Ascii#equalsIgnoreCase(int, int)}.
   */
  @Test
  public void testEqualsIgnoreCase() {
    for (int ch = 0; ch <= Ascii.MAX; ++ch) {
      assertEquals(true, Ascii.equalsIgnoreCase(ch, ch), "ch = " + ch);
    }

    for (int ch = 'A'; ch <= 'Z'; ++ch) {
      assertEquals(true, Ascii.equalsIgnoreCase(ch, ch + 0x20), "ch = " + ch);
      assertEquals(true, Ascii.equalsIgnoreCase(ch + 0x20, ch),
          "ch = " + (ch + 0x20));
    }

    for (int ch1 = 0; ch1 <= Ascii.MAX; ++ch1) {
      for (int ch2 = 0; ch2 <= Ascii.MAX; ++ch2) {
        if (ch1 == ch2) {
          assertEquals(
              true, Ascii.equalsIgnoreCase(ch1, ch2),
              "ch1 = " + ch1 + " ch2 = " + ch2);
        } else if ((ch1 + 0x20 == ch2)
            && Ascii.isUpperCaseLetter(ch1)
            && Ascii.isLowerCaseLetter(ch2)) {
          assertEquals(
              true, Ascii.equalsIgnoreCase(ch1, ch2),
              "ch1 = " + ch1 + " ch2 = " + ch2);
        } else if ((ch1 - 0x20 == ch2)
            && Ascii.isLowerCaseLetter(ch1)
            && Ascii.isUpperCaseLetter(ch2)) {
          assertEquals(
              true, Ascii.equalsIgnoreCase(ch1, ch2),
              "ch1 = " + ch1 + " ch2 = " + ch2);
        } else {
          assertEquals(
              false, Ascii.equalsIgnoreCase(ch1, ch2),
              "ch1 = " + ch1 + " ch2 = " + ch2);
        }
      }
    }

    int ch1 = Integer.MIN_VALUE;
    int ch2 = Integer.MIN_VALUE;
    assertEquals(
        true, Ascii.equalsIgnoreCase(ch1, ch2),
        "ch1 = " + ch1 + " ch2 = " + ch2);

    ch1 = Integer.MAX_VALUE;
    ch2 = Integer.MAX_VALUE;
    assertEquals(
        true, Ascii.equalsIgnoreCase(ch1, ch2),
        "ch1 = " + ch1 + " ch2 = " + ch2);

    ch1 = Integer.MIN_VALUE;
    ch2 = Integer.MAX_VALUE;
    assertEquals(
        false, Ascii.equalsIgnoreCase(ch1, ch2),
        "ch1 = " + ch1 + " ch2 = " + ch2);
  }

  /**
   * Test method for {@link Ascii#toUpperCase(byte)}.
   */
  @Test
  public void testToUppercaseByte() {
    for (byte ch = 0; ch >= 0; ++ch) {
      if (Ascii.isLowerCaseLetter(ch)) {
        assertEquals(ch - 0x20, Ascii.toUpperCase(ch), "ch = " + ch);
      } else {
        assertEquals(ch, Ascii.toUpperCase(ch), "ch = " + ch);
      }
    }
    for (byte ch = 0; ch < 0; --ch) {
      assertEquals(ch, Ascii.toUpperCase(ch), "ch = " + ch);
    }
  }

  /**
   * Test method for {@link Ascii#toUpperCase(char)}.
   */
  @Test
  public void testToUppercaseChar() {
    for (char ch = 0; ch <= Ascii.MAX; ++ch) {
      if (Ascii.isLowerCaseLetter(ch)) {
        assertEquals(ch - 0x20, Ascii.toUpperCase(ch), "ch = " + ch);
      } else {
        assertEquals(ch, Ascii.toUpperCase(ch), "ch = " + ch);
      }
    }

    char ch = Character.MIN_VALUE;
    assertEquals(ch, Ascii.toUpperCase(ch), "ch = " + ch);

    ch = Character.MAX_VALUE;
    assertEquals(ch, Ascii.toUpperCase(ch), "ch = " + ch);

    ch = 0;
    assertEquals(ch, Ascii.toUpperCase(ch), "ch = " + ch);
  }

  /**
   * Test method for {@link Ascii#toUpperCase(int)}.
   */
  @Test
  public void testToUppercaseInt() {
    for (int ch = 0; ch <= Ascii.MAX; ++ch) {
      if (Ascii.isLowerCaseLetter(ch)) {
        assertEquals(ch - 0x20, Ascii.toUpperCase(ch), "ch = " + ch);
      } else {
        assertEquals(ch, Ascii.toUpperCase(ch), "ch = " + ch);
      }
    }

    int ch = Integer.MIN_VALUE;
    assertEquals(ch, Ascii.toUpperCase(ch), "ch = " + ch);

    ch = Integer.MAX_VALUE;
    assertEquals(ch, Ascii.toUpperCase(ch), "ch = " + ch);

    ch = 0;
    assertEquals(ch, Ascii.toUpperCase(ch), "ch = " + ch);
  }

  /**
   * Test method for {@link Ascii#toLowerCase(byte)}.
   */
  @Test
  public void testToLowercaseByte() {
    for (byte ch = 0; ch >= 0; ++ch) {
      if (Ascii.isUpperCaseLetter(ch)) {
        assertEquals(ch + 0x20, Ascii.toLowerCase(ch), "ch = " + ch);
      } else {
        assertEquals(ch, Ascii.toLowerCase(ch), "ch = " + ch);
      }
    }
    for (byte ch = 0; ch < 0; --ch) {
      assertEquals(ch, Ascii.toLowerCase(ch), "ch = " + ch);
    }
  }

  /**
   * Test method for {@link Ascii#toLowerCase(char)}.
   */
  @Test
  public void testToLowercaseChar() {
    for (char ch = 0; ch <= Ascii.MAX; ++ch) {
      if (Ascii.isUpperCaseLetter(ch)) {
        assertEquals(ch + 0x20, Ascii.toLowerCase(ch), "ch = " + ch);
      } else {
        assertEquals(ch, Ascii.toLowerCase(ch), "ch = " + ch);
      }
    }

    char ch = Character.MIN_VALUE;
    assertEquals(ch, Ascii.toLowerCase(ch), "ch = " + ch);

    ch = Character.MAX_VALUE;
    assertEquals(ch, Ascii.toLowerCase(ch), "ch = " + ch);

    ch = 0;
    assertEquals(ch, Ascii.toLowerCase(ch), "ch = " + ch);
  }

  /**
   * Test method for {@link Ascii#toLowerCase(int)}.
   */
  @Test
  public void testToLowercaseInt() {
    for (int ch = 0; ch <= Ascii.MAX; ++ch) {
      if (Ascii.isUpperCaseLetter(ch)) {
        assertEquals(ch + 0x20, Ascii.toLowerCase(ch), "ch = " + ch);
      } else {
        assertEquals(ch, Ascii.toLowerCase(ch), "ch = " + ch);
      }
    }

    int ch = Integer.MIN_VALUE;
    assertEquals(ch, Ascii.toLowerCase(ch), "ch = " + ch);

    ch = Integer.MAX_VALUE;
    assertEquals(ch, Ascii.toLowerCase(ch), "ch = " + ch);

    ch = 0;
    assertEquals(ch, Ascii.toLowerCase(ch), "ch = " + ch);
  }

  /**
   * Test method for {@link Ascii#toDigit(int)}.
   */
  @Test
  public void testToDigit() {
    for (int ch = '0'; ch <= '9'; ++ch) {
      assertEquals(ch - '0', Ascii.toDigit(ch), "ch = " + ch);
    }
    for (int ch = 0; ch < '0'; ++ch) {
      assertEquals(-1, Ascii.toDigit(ch), "ch = " + ch);
    }
    for (int ch = '9' + 1; ch <= 0xFF; ++ch) {
      assertEquals(-1, Ascii.toDigit(ch), "ch = " + ch);
    }

    assertEquals(-1, Ascii.toDigit(0), "ch = " + 0);
    assertEquals(-1, Ascii.toDigit(Integer.MAX_VALUE),
        "ch = " + Integer.MAX_VALUE);
    assertEquals(-1, Ascii.toDigit(Integer.MIN_VALUE),
        "ch = " + Integer.MIN_VALUE);
  }

  /**
   * Test method for {@link Ascii#toHexDigit(int)}.
   */
  @Test
  public void testToHexDigit() {
    for (int ch = '0'; ch <= '9'; ++ch) {
      assertEquals(ch - '0', Ascii.toHexDigit(ch), "ch = " + ch);
    }
    for (int ch = 'A'; ch <= 'F'; ++ch) {
      assertEquals(ch - 'A' + 10, Ascii.toHexDigit(ch), "ch = " + ch);
    }
    for (int ch = 'a'; ch <= 'f'; ++ch) {
      assertEquals(ch - 'a' + 10, Ascii.toHexDigit(ch), "ch = " + ch);
    }
    for (int ch = 0; ch < '0'; ++ch) {
      assertEquals(-1, Ascii.toHexDigit(ch), "ch = " + ch);
    }
    for (int ch = '9' + 1; ch < 'A'; ++ch) {
      assertEquals(-1, Ascii.toHexDigit(ch), "ch = " + ch);
    }
    for (int ch = 'F' + 1; ch < 'a'; ++ch) {
      assertEquals(-1, Ascii.toHexDigit(ch), "ch = " + ch);
    }
    for (int ch = 'f' + 1; ch <= 0xFF; ++ch) {
      assertEquals(-1, Ascii.toHexDigit(ch), "ch = " + ch);
    }

    assertEquals(-1, Ascii.toHexDigit(0), "ch = " + 0);
    assertEquals(-1, Ascii.toHexDigit(Integer.MAX_VALUE),
        "ch = " + Integer.MAX_VALUE);
    assertEquals(-1, Ascii.toHexDigit(Integer.MIN_VALUE),
        "ch = " + Integer.MIN_VALUE);
  }

}
