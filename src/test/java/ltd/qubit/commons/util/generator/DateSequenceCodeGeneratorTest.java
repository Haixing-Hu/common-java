////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.generator;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;

import ltd.qubit.commons.util.clock.MockClock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DateSequenceCodeGeneratorTest {
  @Test
  void generateWithValidPrefix() {
    final DateSequenceCodeGenerator generator = new DateSequenceCodeGenerator();
    final String prefix = "INV";
    final String code = generator.generate(prefix);
    assertTrue(code.startsWith(prefix + "-"));
    assertTrue(code.matches("INV-\\d{4}-\\d{2}-\\d{2}-\\d+"));
  }

  @Test
  void generateWithEmptyPrefix() {
    final DateSequenceCodeGenerator generator = new DateSequenceCodeGenerator();
    final String prefix = "";
    final String code = generator.generate(prefix);
    assertTrue(code.startsWith("-"));
    assertTrue(code.matches("-\\d{4}-\\d{2}-\\d{2}-\\d+"));
  }

  @Test
  void generateWithNullPrefix() {
    final DateSequenceCodeGenerator generator = new DateSequenceCodeGenerator();
    final String code = generator.generate(null);
    assertTrue(code.startsWith("null-"));
    assertTrue(code.matches("null-\\d{4}-\\d{2}-\\d{2}-\\d+"));
  }

  @Test
  void generateSequentialCodes() {
    final DateSequenceCodeGenerator generator = new DateSequenceCodeGenerator();
    final String prefix = "SEQ";
    final String code1 = generator.generate(prefix);
    final String code2 = generator.generate(prefix);
    assertTrue(code1.endsWith("-1"));
    assertTrue(code2.endsWith("-2"));
    assertEquals(code1.substring(0, code1.length() - 2), code2.substring(0, code2.length() - 2));
  }

  @Test
  void generateWithDifferentDates() {
    final MockClock clock = new MockClock();
    final DateSequenceCodeGenerator generator = new DateSequenceCodeGenerator(clock);
    final String prefix = "DATE";
    final String code1 = generator.generate(prefix);
    // Simulate a different date by manipulating the internal state
    clock.add(1, TimeUnit.DAYS);
    final String code2 = generator.generate(prefix);
    System.out.println(code1);
    System.out.println(code2);
    final String[] part1 = code1.split("-");
    final String[] part2 = code2.split("-");
    assertEquals(5, part1.length);
    assertEquals(5, part2.length);
    assertEquals(part1[0], part2[0]);
    assertEquals(part1[1], part2[1]);
    assertEquals(part1[2], part2[2]);
    assertNotEquals(part1[3], part2[3]);
    assertEquals(part1[4], part2[4]);
  }
}