////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.translate;

import ltd.qubit.commons.text.translate.NumericEntityUnescaper.SemiColonOption;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

public class NumericEntityUnescaperTest {

  @Test
  public void testCreatesNumericEntityUnescaperOne() {
    final SemiColonOption[] numericEntityUnescaperOPTIONArray = {};
    final NumericEntityUnescaper numericEntityUnescaper =
        new NumericEntityUnescaper(numericEntityUnescaperOPTIONArray);

    assertThat(numericEntityUnescaper.transform("2|y|O7y`&#uVWj"))
        .isEqualTo("2|y|O7y`&#uVWj");
  }

  @Test
  public void testCreatesNumericEntityUnescaperTwo() {
    final SemiColonOption[] numericEntityUnescaperOPTIONArray = {};
    final NumericEntityUnescaper numericEntityUnescaper =
        new NumericEntityUnescaper(numericEntityUnescaperOPTIONArray);

    assertThat(numericEntityUnescaper.transform("Ws2v8|O=7NR&#cB"))
        .isEqualTo("Ws2v8|O=7NR&#cB");
  }

  @Test
  public void testOutOfBounds() {
    final NumericEntityUnescaper neu = new NumericEntityUnescaper();
    assertThat(neu.transform("Test &"))
        .as("Failed to ignore when last character is &")
        .isEqualTo("Test &");
    assertThat(neu.transform("Test &#"))
        .as("Failed to ignore when last character is &")
        .isEqualTo("Test &#");
    assertThat(neu.transform("Test &#x"))
        .as("Failed to ignore when last character is &")
        .isEqualTo("Test &#x");
    assertThat(neu.transform("Test &#X"))
        .as("Failed to ignore when last character is &")
        .isEqualTo("Test &#X");
  }

  @Test
  public void testSupplementaryUnescaping() {
    final NumericEntityUnescaper neu = new NumericEntityUnescaper();
    final String input = "&#68642;";
    final String expected = "\uD803\uDC22";

    final String result = neu.transform(input);
    assertThat(result)
        .as("Failed to unescape numeric entities supplementary characters")
        .isEqualTo(expected);
  }

  @Test
  public void testUnfinishedEntity() {
    // parse it
    NumericEntityUnescaper neu =
        new NumericEntityUnescaper(SemiColonOption.OPTIONAL);
    String input = "Test &#x30 not test";
    String expected = "Test \u0030 not test";

    String result = neu.transform(input);
    assertThat(result)
        .as("Failed to support unfinished entities (i.e. missing semicolon)")
        .isEqualTo(expected);

    // ignore it
    neu = new NumericEntityUnescaper();
    input = "Test &#x30 not test";
    expected = input;

    result = neu.transform(input);
    assertThat(result)
        .as("Failed to ignore unfinished entities (i.e. missing semicolon)")
        .isEqualTo(expected);

    // fail it
    neu = new NumericEntityUnescaper(SemiColonOption.ERROR_IF_MISSING);
    input = "Test &#x30 not test";

    try {
      result = neu.transform(input);
      fail("IllegalArgumentException expected");
    } catch (final IllegalArgumentException iae) {
      // expected
    }
  }
}
