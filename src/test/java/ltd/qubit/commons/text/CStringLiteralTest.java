////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text;

import java.text.ParseException;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

/**
 * Unit test of the {@link CStringLiteral} class.
 *
 * @author Haixing Hu
 */
public class CStringLiteralTest {

  /**
   * Test method for {@link CStringLiteral#decode(CharSequence, int, int)}.
   */
  @Test
  public void testDecode() throws ParseException {
    String str = null;
    byte[] expect = null;
    byte[] result = null;

    str = "hello, world.";
    expect = new byte[]{0x68, 0x65, 0x6c, 0x6c, 0x6f, 0x2c, 0x20, 0x77,
        0x6f, 0x72, 0x6c, 0x64, 0x2e};
    result = CStringLiteral.decode(str, 0, str.length());
    assertArrayEquals(expect, result);

    str = "hello, \\\"world\\\".";
    expect = new byte[]{0x68, 0x65, 0x6c, 0x6c, 0x6f, 0x2c, 0x20, 0x22,
        0x77, 0x6f, 0x72, 0x6c, 0x64, 0x22, 0x2e};
    result = CStringLiteral.decode(str, 0, str.length());
    assertArrayEquals(expect, result);

    str = "hello, \\x22world\\x22.";
    expect = new byte[]{0x68, 0x65, 0x6c, 0x6c, 0x6f, 0x2c, 0x20, 0x22,
        0x77, 0x6f, 0x72, 0x6c, 0x64, 0x22, 0x2e};
    result = CStringLiteral.decode(str, 0, str.length());
    assertArrayEquals(expect, result);

    str = "hello, \\42world\\42\\100123.";
    expect = new byte[]{0x68, 0x65, 0x6c, 0x6c, 0x6f, 0x2c, 0x20, 0x22,
        0x77, 0x6f, 0x72, 0x6c, 0x64, 0x22, 0x40, 0x31, 0x32, 0x33, 0x2e};
    result = CStringLiteral.decode(str, 0, str.length());
    assertArrayEquals(expect, result);

    str = "hello, \\u0022world\\u0022.";
    expect = new byte[]{0x68, 0x65, 0x6c, 0x6c, 0x6f, 0x2c, 0x20, 0x22,
        0x77, 0x6f, 0x72, 0x6c, 0x64, 0x22, 0x2e};
    result = CStringLiteral.decode(str, 0, str.length());
    assertArrayEquals(expect, result);

    str = "hello, \\U00000022world\\U00000022.";
    expect = new byte[]{0x68, 0x65, 0x6c, 0x6c, 0x6f, 0x2c, 0x20, 0x22,
        0x77, 0x6f, 0x72, 0x6c, 0x64, 0x22, 0x2e};
    result = CStringLiteral.decode(str, 0, str.length());
    assertArrayEquals(expect, result);
  }

  @Test
  public void testHexOverflowBug() throws TextParseException {
    final String str = "\\x1aDS";
    final byte[] expect = new byte[]{0x1A, 0x44, 0x53};
    final byte[] result = CStringLiteral.decode(str, 0, str.length());
    assertArrayEquals(expect, result);
  }
}
