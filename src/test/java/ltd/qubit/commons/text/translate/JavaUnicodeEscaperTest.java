////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.translate;

import org.junit.jupiter.api.Test;

import ltd.qubit.commons.text.Utf16;

import static org.assertj.core.api.Assertions.assertThat;

public class JavaUnicodeEscaperTest {

  @Test
  public void testAbove() {
    final JavaUnicodeEscaper jue = JavaUnicodeEscaper.above('F');
    final String input = "ADFGZ";
    final String result = jue.transform(input);
    assertThat(result)
        .as("Failed to escape Unicode characters via the above method")
        .isEqualTo("ADF\\u0047\\u005A");
  }

  @Test
  public void testBelow() {
    final JavaUnicodeEscaper jue = JavaUnicodeEscaper.below('F');
    final String input = "ADFGZ";
    final String result = jue.transform(input);
    assertThat(result)
        .as("Failed to escape Unicode characters via the below method")
        .isEqualTo("\\u0041\\u0044FGZ");
  }

  @Test
  public void testBetween() {
    final JavaUnicodeEscaper jue = JavaUnicodeEscaper.between('F', 'L');
    final String input = "ADFGZ";
    final String result = jue.transform(input);
    assertThat(result)
        .as("Failed to escape Unicode characters via the between method")
        .isEqualTo("AD\\u0046\\u0047Z");
  }

  @Test
  public void testToUtf16Escape() {
    final JavaUnicodeEscaper jue = JavaUnicodeEscaper.below('F');
    // According to https://en.wikipedia.org/wiki/UTF-16#Code_points_U.2B10000..U.2B10FFFF,
    // Character ?, U+24B62, Binary Code Point 0010 0100 1011 0110 0010,
    // Binary UTF-167 1101 1000 0101 0010 1101 1111 0110 0010, UTF-16 Hex Code Units D852 DF62
    final int codePoint = Integer.parseInt("024B62", 16);
    final String encoding = Utf16.escape(codePoint);
    assertThat(encoding).isEqualTo("\\uD852\\uDF62");
  }
}