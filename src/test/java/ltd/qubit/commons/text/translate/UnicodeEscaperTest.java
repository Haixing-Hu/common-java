////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.translate;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UnicodeEscaperTest {

  @Test
  public void testAbove() {
    final UnicodeEscaper ue = UnicodeEscaper.above('F');

    final String input = "ADFGZ";
    final String result = ue.transform(input);
    assertThat(result)
        .as("Failed to escape Unicode characters via the above method")
        .isEqualTo("ADF\\u0047\\u005A");
  }

  @Test
  public void testBelow() {
    final UnicodeEscaper ue = UnicodeEscaper.below('F');

    final String input = "ADFGZ";
    final String result = ue.transform(input);
    assertThat(result)
        .as("Failed to escape Unicode characters via the below method")
        .isEqualTo("\\u0041\\u0044FGZ");
  }

  @Test
  public void testBetween() {
    final UnicodeEscaper ue = UnicodeEscaper.between('F', 'L');

    final String input = "ADFGZ";
    final String result = ue.transform(input);
    assertThat(result)
        .as("Failed to escape Unicode characters via the between method")
        .isEqualTo("AD\\u0046\\u0047Z");
  }
}
