////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
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

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * Tests {@link AggregateTranslator}.
 */
public class AggregateTranslatorTest {

  @Test
  public void testNonNull() throws IOException {
    final Map<CharSequence, CharSequence> oneTwoMap = new HashMap<>();
    oneTwoMap.put("one", "two");
    final Map<CharSequence, CharSequence> threeFourMap = new HashMap<>();
    threeFourMap.put("three", "four");
    final CharSequenceTranslator translator1 = new LookupTranslator(oneTwoMap);
    final CharSequenceTranslator translator2 = new LookupTranslator(threeFourMap);
    final AggregateTranslator subject = new AggregateTranslator(translator1, translator2);
    final StringWriter out1 = new StringWriter();
    final int result1 = subject.translate(new StringBuffer("one"), 0, out1);
    assertThat(result1).as("Incorrect code point consumption").isEqualTo(3);
    assertThat(out1.toString()).as("Incorrect value").isEqualTo("two");
    final StringWriter out2 = new StringWriter();
    final int result2 = subject.translate(new StringBuffer("three"), 0, out2);
    assertThat(result2).as("Incorrect code point consumption").isEqualTo(5);
    assertThat(out2.toString()).as("Incorrect value").isEqualTo("four");
  }

  @Test
  public void testNullConstructor() {
    final String testString = "foo";
    final AggregateTranslator subject = new AggregateTranslator((CharSequenceTranslator[]) null);
    assertThat(subject.transform(testString)).isEqualTo(testString);
  }

  @Test
  public void testNullVarargConstructor() {
    final String testString = "foo";
    final AggregateTranslator subject = new AggregateTranslator((CharSequenceTranslator) null);
    assertThat(subject.transform(testString)).isEqualTo(testString);
  }

}