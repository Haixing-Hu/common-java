////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.generator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * The unit test of the {@link RandomNumberTokenGenerator} class.
 *
 * @author starfish
 */
public class RandomNumberTokenGeneratorTest {

  /**
   * Test method for {@link RandomNumberTokenGenerator#RandomNumberTokenGenerator()}.
   */
  @Test
  public void testRandomNumberTokenGenerator() {
    final RandomNumberTokenGenerator generator = new RandomNumberTokenGenerator();
    assertNotNull(generator);
    assertEquals(RandomNumberTokenGenerator.DEFAULT_DIGITS, generator.getDigits());
  }

  /**
   * Test method for {@link RandomNumberTokenGenerator#setDigits(int)}.
   */
  @Test
  public void testSetDigits() {
    final RandomNumberTokenGenerator generator = new RandomNumberTokenGenerator();
    assertEquals(RandomNumberTokenGenerator.DEFAULT_DIGITS, generator.getDigits());

    generator.setDigits(1);
    assertEquals(1, generator.getDigits());

    generator.setDigits(RandomNumberTokenGenerator.MAX_DIGITS);
    assertEquals(RandomNumberTokenGenerator.MAX_DIGITS, generator.getDigits());

    generator.setDigits(6);
    assertEquals(6, generator.getDigits());

    try {
      generator.setDigits(0);
      fail("shoudl throw IllegalArgumentException");
    } catch (final IllegalArgumentException e) {
      assertEquals(6, generator.getDigits());
    }

    try {
      generator.setDigits(-1);
      fail("shoudl throw IllegalArgumentException");
    } catch (final IllegalArgumentException e) {
      assertEquals(6, generator.getDigits());
    }

    try {
      generator.setDigits(RandomNumberTokenGenerator.MAX_DIGITS + 1);
      fail("shoudl throw IllegalArgumentException");
    } catch (final IllegalArgumentException e) {
      assertEquals(6, generator.getDigits());
    }
  }

  /**
   * Test method for {@link RandomNumberTokenGenerator#generate(String)}.
   */
  @Test
  public void testGenerate() {
    final RandomNumberTokenGenerator generator = new RandomNumberTokenGenerator();
    final String phone = "86-12345678";
    final int REPEAT_COUNT = 100;
    String code;

    for (int i = 0; i < REPEAT_COUNT; ++i) {
      code = generator.generate(null);
      assertEquals(RandomNumberTokenGenerator.DEFAULT_DIGITS, code.length());
      assertAllDigits(code);
    }

    generator.setDigits(6);
    for (int i = 0; i < REPEAT_COUNT; ++i) {
      code = generator.generate(phone);
      assertEquals(6, code.length());
      assertAllDigits(code);
    }

    generator.setDigits(RandomNumberTokenGenerator.MAX_DIGITS);
    for (int i = 0; i < REPEAT_COUNT; ++i) {
      code = generator.generate(phone);
      assertEquals(RandomNumberTokenGenerator.MAX_DIGITS, code.length());
      assertAllDigits(code);
    }
  }

  private void assertAllDigits(final String code) {
    System.out.println("code = " + code);
    for (int i = 0; i < code.length(); ++i) {
      final char ch = code.charAt(i);
      assertTrue((ch >= '0') && (ch <= '9'));
    }
  }

}
