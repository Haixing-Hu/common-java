////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.lang;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static ltd.qubit.commons.lang.StringUtils.addPrefixToEachLine;

public class StringUtilsAddPrefixToEachLineTest {

  @Test
  public void testAddPrefixToEachLineWithMultipleLines() {
    final String input = "Line 1\nLine 2\rLine 3\r\nLine 4";
    final String prefix = "Prefix: ";
    final String expected = "Prefix: Line 1\nPrefix: Line 2\rPrefix: Line 3\r\nPrefix: Line 4";
    assertEquals(expected, addPrefixToEachLine(input, prefix));
  }

  @Test
  public void testAddPrefixToEmptyString() {
    final String input = "";
    final String prefix = "Prefix: ";
    final String expected = "";
    assertEquals(expected, addPrefixToEachLine(input, prefix));
  }

  @Test
  public void testAddPrefixToSingleLine() {
    final String input = "Line 1";
    final String prefix = "Prefix: ";
    final String expected = "Prefix: Line 1";
    assertEquals(expected, addPrefixToEachLine(input, prefix));
  }

  @Test
  public void testAddPrefixToLinesWithOnlyLineBreaks() {
    final String input = "\n\r\n\r";
    final String prefix = "Prefix: ";
    final String expected = "Prefix: \nPrefix: \r\nPrefix: \r";
    assertEquals(expected, addPrefixToEachLine(input, prefix));
  }

  @Test
  public void testAddPrefixWithLineBreaksAtStartAndEnd() {
    final String input = "\nLine 1\n";
    final String prefix = "Prefix: ";
    final String expected = "Prefix: \nPrefix: Line 1\n";
    assertEquals(expected, addPrefixToEachLine(input, prefix));
  }

  @Test
  public void testAddPrefixWithLineBreaksAtStartAndEndButWithLastSpace() {
    final String input = "\nLine 1\n ";
    final String prefix = "Prefix: ";
    final String expected = "Prefix: \nPrefix: Line 1\nPrefix:  ";
    assertEquals(expected, addPrefixToEachLine(input, prefix));
  }
}
