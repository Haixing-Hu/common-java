/// /////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.generator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TimestampCodeGeneratorTest {
  @Test
  void generateWithValidPrefix() {
    final TimestampCodeGenerator generator = new TimestampCodeGenerator();
    final String prefix = "INV";
    final String code = generator.generate(prefix);
    assertTrue(code.startsWith(prefix + "-"));
    assertTrue(code.length() > prefix.length() + 1);
  }

  @Test
  void generateWithEmptyPrefix() {
    final TimestampCodeGenerator generator = new TimestampCodeGenerator();
    final String prefix = "";
    final String code = generator.generate(prefix);
    assertTrue(code.startsWith("-"));
    assertTrue(code.length() > 1);
  }

  @Test
  void generateWithNullPrefix() {
    final TimestampCodeGenerator generator = new TimestampCodeGenerator();
    final String code = generator.generate(null);
    assertTrue(code.startsWith("null-"));
    assertTrue(code.length() > 5);
  }
}
