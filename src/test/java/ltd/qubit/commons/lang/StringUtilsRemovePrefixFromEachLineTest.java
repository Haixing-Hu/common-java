////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.lang;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class StringUtilsRemovePrefixFromEachLineTest {
  @Test
  void removePrefixFromEachLineWithValidInput() {
    final String input = "prefixLine1\nprefixLine2\nprefixLine3";
    final String prefix = "prefix";
    final String expected = "Line1\nLine2\nLine3";
    assertEquals(expected, StringUtils.removePrefixFromEachLine(input, prefix));
  }

  @Test
  void removePrefixFromEachLineWithValidInput_2() {
    final String input = "\n    Line1\n    Line2\n    Line3\n";
    final String prefix = "  ";
    final String expected = "\n  Line1\n  Line2\n  Line3\n";
    assertEquals(expected, StringUtils.removePrefixFromEachLine(input, prefix));
  }

  @Test
  void removePrefixFromEachLineWithEmptyPrefix() {
    final String input = "Line1\nLine2\nLine3";
    final String prefix = "";
    final String expected = "Line1\nLine2\nLine3";
    assertEquals(expected, StringUtils.removePrefixFromEachLine(input, prefix));
  }

  @Test
  void removePrefixFromEachLineWithNullPrefix() {
    final String input = "Line1\nLine2\nLine3";
    final String prefix = null;
    final String expected = "Line1\nLine2\nLine3";
    assertEquals(expected, StringUtils.removePrefixFromEachLine(input, prefix));
  }

  @Test
  void removePrefixFromEachLineWithNullInput() {
    final String input = null;
    final String prefix = "prefix";
    assertNull(StringUtils.removePrefixFromEachLine(input, prefix));
  }

  @Test
  void removePrefixFromEachLineWithNoMatchingPrefix() {
    final String input = "Line1\nLine2\nLine3";
    final String prefix = "prefix";
    final String expected = "Line1\nLine2\nLine3";
    assertEquals(expected, StringUtils.removePrefixFromEachLine(input, prefix));
  }
}