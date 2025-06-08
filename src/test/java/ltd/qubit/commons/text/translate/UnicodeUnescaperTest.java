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
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UnicodeUnescaperTest {

  @Test
  public void testLessThanFour() {
    final UnicodeUnescaper uu = new UnicodeUnescaper();
    final String input = "\\0047\\u006";
    assertThrows(IllegalArgumentException.class, () -> uu.transform(input));
  }

  // Requested in LANG-507
  @Test
  public void testUPlus() {
    final UnicodeUnescaper uu = new UnicodeUnescaper();
    final String input = "\\u+0047";
    assertThat(uu.transform(input))
        .as("Failed to unescape Unicode characters with 'u+' notation")
        .isEqualTo("G");
  }

  @Test
  public void testUuuuu() {
    final UnicodeUnescaper uu = new UnicodeUnescaper();
    final String input = "\\uuuuuuuu0047";
    final String result = uu.transform(input);
    assertThat(result)
        .as("Failed to unescape Unicode characters with many 'u' characters")
        .isEqualTo("G");
  }
}