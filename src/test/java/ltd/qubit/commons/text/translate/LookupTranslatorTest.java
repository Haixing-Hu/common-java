////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.translate;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class LookupTranslatorTest {

  @Test
  public void testBasicLookup() throws IOException {
    final Map<CharSequence, CharSequence> translatorMap = new HashMap<>();
    translatorMap.put("one", "two");
    final LookupTranslator lt = new LookupTranslator(translatorMap);
    final StringWriter out = new StringWriter();
    final int result = lt.translate("one", 0, out);
    assertThat(result).as("Incorrect code point consumption").isEqualTo(3);
    assertThat(out.toString()).as("Incorrect value").isEqualTo("two");
  }

  @Test
  public void testFailsToCreateLookupTranslatorThrowsInvalidParameterException() {
    assertThatExceptionOfType(NullPointerException.class)
        .isThrownBy(() -> new LookupTranslator((Map<CharSequence, CharSequence>) null));
  }

  // Tests: https://issues.apache.org/jira/browse/LANG-882
  @Test
  public void testLang882() throws IOException {
    final Map<CharSequence, CharSequence> translatorMap = new HashMap<>();
    translatorMap.put(new StringBuffer("one"), new StringBuffer("two"));
    final LookupTranslator lt = new LookupTranslator(translatorMap);
    final StringWriter out = new StringWriter();
    final int result = lt.translate(new StringBuffer("one"), 0, out);
    assertThat(result).as("Incorrect code point consumption").isEqualTo(3);
    assertThat(out.toString()).as("Incorrect value").isEqualTo("two");
  }

  @Test
  public void testTranslateSupplementaryCharacter() {
    /* Key: string with Mathematical double-struck capital A (U+1D538) */
    final String symbol = new StringBuilder().appendCodePoint(0x1D538).toString();
    /* Map U+1D538 to "A" */
    final Map<CharSequence, CharSequence> map = new HashMap<>();
    map.put(symbol, "A");
    final LookupTranslator translator = new LookupTranslator(map);
    final String translated = translator.transform(symbol + "=A");
    /* we should get "A=A". */
    assertThat(translated).as("Incorrect value").isEqualTo("A=A");
  }
}
