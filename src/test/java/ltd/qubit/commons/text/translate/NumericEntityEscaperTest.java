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

import static org.assertj.core.api.Assertions.assertThat;

public class NumericEntityEscaperTest {

  @Test
  public void testAbove() {
    final NumericEntityEscaper nee = NumericEntityEscaper.above('F');
    final String input = "ADFGZ";
    final String result = nee.transform(input);
    assertThat(result)
        .as("Failed to escape numeric entities via the above method")
        .isEqualTo("ADF&#71;&#90;");
  }

  @Test
  public void testBelow() {
    final NumericEntityEscaper nee = NumericEntityEscaper.below('F');
    final String input = "ADFGZ";
    final String result = nee.transform(input);
    assertThat(result)
        .as("Failed to escape numeric entities via the below method")
        .isEqualTo("&#65;&#68;FGZ");
  }

  @Test
  public void testBetween() {
    final NumericEntityEscaper nee = NumericEntityEscaper.between('F', 'L');
    final String input = "ADFGZ";
    final String result = nee.transform(input);
    assertThat(result)
        .as("Failed to escape numeric entities via the between method")
        .isEqualTo("AD&#70;&#71;Z");
  }

  @Test
  public void testSupplementary() {
    final NumericEntityEscaper nee = new NumericEntityEscaper();
    final String input = "\uD803\uDC22";
    final String expected = "&#68642;";
    final String result = nee.transform(input);
    assertThat(result)
        .as("Failed to escape numeric entities supplementary characters")
        .isEqualTo(expected);
  }
}