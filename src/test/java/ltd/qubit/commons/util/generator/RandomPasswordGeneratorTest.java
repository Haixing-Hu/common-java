////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.generator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit test for {@link RandomPasswordGenerator}.
 *
 * @author Haixing Hu
 */
public class RandomPasswordGeneratorTest {

  @Test
  void generateWithAllTypesAllowed() {
    final RandomPasswordGenerator generator = new RandomPasswordGenerator();
    generator.setMinLength(8);
    generator.setMaxLength(12);
    generator.setAllowLowerCaseLetters(true);
    generator.setAllowUpperCaseLetters(true);
    generator.setAllowDigits(true);
    generator.setAllowSymbols(true);
    final String password = generator.generate("user", "1234567890");
    assertTrue(password.matches("[a-zA-Z0-9`~!@#$%^&*()\\-_=+\\{\\}\\[\\]|\\\\:;'\",./?<>]+"));
    assertTrue(password.length() >= 8 && password.length() <= 12);
  }

  @Test
  void generateWithNoTypesAllowed() {
    final RandomPasswordGenerator generator = new RandomPasswordGenerator();
    generator.setAllowLowerCaseLetters(false);
    generator.setAllowUpperCaseLetters(false);
    generator.setAllowDigits(false);
    generator.setAllowSymbols(false);
    final Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      generator.generate("user", "1234567890");
    });
    final String expectedMessage = "At least one character type should be allowed";
    final String actualMessage = exception.getMessage();
    assertTrue(actualMessage.contains(expectedMessage));
  }

  @Test
  void generateWithOnlyNumbersAllowed() {
    final RandomPasswordGenerator generator = new RandomPasswordGenerator();
    generator.setMinLength(6);
    generator.setMaxLength(6);
    generator.setAllowLowerCaseLetters(false);
    generator.setAllowUpperCaseLetters(false);
    generator.setAllowDigits(true);
    generator.setAllowSymbols(false);
    final String password = generator.generate("user", "1234567890");
    assertTrue(password.matches("[0-9]+"));
    assertEquals(6, password.length());
  }

  @Test
  void generateWithFixedLength() {
    final RandomPasswordGenerator generator = new RandomPasswordGenerator();
    final int fixedLength = 10;
    generator.setMinLength(fixedLength);
    generator.setMaxLength(fixedLength);
    generator.setAllowLowerCaseLetters(true);
    generator.setAllowUpperCaseLetters(true);
    generator.setAllowDigits(true);
    generator.setAllowSymbols(true);
    final String password = generator.generate("user", "1234567890");
    assertEquals(fixedLength, password.length());
  }

  @Test
  void generateWithOnlyLowerCaseLettersAllowed() {
    final RandomPasswordGenerator generator = new RandomPasswordGenerator();
    generator.setMinLength(5);
    generator.setMaxLength(10);
    generator.setAllowLowerCaseLetters(true);
    generator.setAllowUpperCaseLetters(false);
    generator.setAllowDigits(false);
    generator.setAllowSymbols(false);

    final String password = generator.generate("user", "1234567890");
    assertTrue(password.matches("[a-z]+"));
    assertTrue(password.length() >= 5 && password.length() <= 10);
  }

  @Test
  void generateWithOnlyUpperCaseLettersAllowed() {
    final RandomPasswordGenerator generator = new RandomPasswordGenerator();
    generator.setMinLength(5);
    generator.setMaxLength(10);
    generator.setAllowLowerCaseLetters(false);
    generator.setAllowUpperCaseLetters(true);
    generator.setAllowDigits(false);
    generator.setAllowSymbols(false);
    final String password = generator.generate("user", "1234567890");
    assertTrue(password.matches("[A-Z]+"));
    assertTrue(password.length() >= 5 && password.length() <= 10);
  }

  @Test
  void generateWithOnlySymbolsAllowed() {
    final RandomPasswordGenerator generator = new RandomPasswordGenerator();
    generator.setMinLength(5);
    generator.setMaxLength(10);
    generator.setAllowLowerCaseLetters(false);
    generator.setAllowUpperCaseLetters(false);
    generator.setAllowDigits(false);
    generator.setAllowSymbols(true);
    final String password = generator.generate("user", "1234567890");
    assertTrue(password.matches("[`~!@#$%^&*()\\-_=+\\{\\}\\[\\]|\\\\:;'\",./?<>]+"));
    assertTrue(password.length() >= 5 && password.length() <= 10);
  }

  @Test
  void generateWithMaxLengthLessThanMinLength() {
    final RandomPasswordGenerator generator = new RandomPasswordGenerator();
    generator.setMinLength(10);
    generator.setMaxLength(5); // 设置一个不合适的值
    final Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      generator.generate("user", "1234567890");
    });
    final String expectedMessage = "The minimum length should be less than or equal to the maximum length";
    final String actualMessage = exception.getMessage();
    assertTrue(actualMessage.contains(expectedMessage));
  }

  // 测试最小和最大长度边界值
  @Test
  void generateWithExtremeLengths() {
    final RandomPasswordGenerator generator = new RandomPasswordGenerator();
    generator.setMinLength(1);
    generator.setMaxLength(1);
    generator.setAllowDigits(true);
    generator.setAllowLowerCaseLetters(false);
    generator.setAllowUpperCaseLetters(false);
    generator.setAllowSymbols(false);
    final String password = generator.generate("user", "1234567890");
    assertEquals(1, password.length());
    assertTrue(password.matches("[0-9]"));
  }
}
