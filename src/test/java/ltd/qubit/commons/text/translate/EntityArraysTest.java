////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.translate;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Map;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

import static ltd.qubit.commons.text.translate.EntityArrays.APOS_ESCAPE;
import static ltd.qubit.commons.text.translate.EntityArrays.APOS_UNESCAPE;
import static ltd.qubit.commons.text.translate.EntityArrays.BASIC_ESCAPE;
import static ltd.qubit.commons.text.translate.EntityArrays.BASIC_UNESCAPE;
import static ltd.qubit.commons.text.translate.EntityArrays.HTML40_EXTENDED_ESCAPE;
import static ltd.qubit.commons.text.translate.EntityArrays.HTML40_EXTENDED_UNESCAPE;
import static ltd.qubit.commons.text.translate.EntityArrays.ISO8859_1_ESCAPE;
import static ltd.qubit.commons.text.translate.EntityArrays.ISO8859_1_UNESCAPE;
import static ltd.qubit.commons.text.translate.EntityArrays.JAVA_CTRL_CHARS_ESCAPE;
import static ltd.qubit.commons.text.translate.EntityArrays.JAVA_CTRL_CHARS_UNESCAPE;

public class EntityArraysTest {

  @Test
  public void testAposMap() {
    testEscapeVsUnescapeMaps(APOS_ESCAPE, APOS_UNESCAPE);
  }

  @Test
  public void testBasicMap() {
    testEscapeVsUnescapeMaps(BASIC_ESCAPE, BASIC_UNESCAPE);
  }

  private void testEscapeVsUnescapeMaps(
      final Map<CharSequence, CharSequence> escapeMap,
      final Map<CharSequence, CharSequence> unescapeMap) {
    for (final CharSequence escapeKey : escapeMap.keySet()) {
      for (final CharSequence unescapeKey : unescapeMap.keySet()) {
        if (escapeKey == unescapeMap.get(unescapeKey)) {
          assertThat(unescapeKey).isEqualTo(escapeMap.get(escapeKey));
        }
      }
    }
  }

  // LANG-659, LANG-658 - avoid duplicate entries
  @Test
  public void testForDuplicatedDeclaredMapKeys() throws Exception {
    final String packageDirectory = EntityArraysTest.class.getPackage().getName().replace(".", "/");
    final String filename = "src/main/java/" + packageDirectory + "/EntityArrays.java";
    try (final BufferedReader br = new BufferedReader(new FileReader(filename))) {
      String line;
      int mapDeclarationCounter = 0;
      while ((line = br.readLine()) != null) {
        //Start with map declaration and count put lines
        if (line.contains("new HashMap<>();")) {
          mapDeclarationCounter = 0;
        } else if (line.contains(".put(")) {
          mapDeclarationCounter++;
        } else if (line.contains("Collections.unmodifiableMap(initialMap);")) {
          final String mapVariableName = line.split("=")[0].trim();
          @SuppressWarnings("unchecked") // This is test code
          final
          Map<String, String> mapValue = (Map<String, String>)
              EntityArrays.class.getDeclaredField(mapVariableName).get(EntityArrays.class);
          // Validate that we are not inserting into the same key twice in the map declaration. If this,
          // indeed was the case the keySet().size() would be smaller than the number of put() statements
          assertThat(mapValue.size()).isEqualTo(mapDeclarationCounter);
        }
      }
    }
  }

  @Test
  public void testForDuplicateDeclaredMapValuesAposMap() {
    assertThat(APOS_ESCAPE.keySet())
        .hasSameSizeAs(APOS_UNESCAPE.keySet());
  }

  @Test
  public void testForDuplicateDeclaredMapValuesBasicMap() {
    assertThat(BASIC_ESCAPE.keySet())
        .hasSameSizeAs(BASIC_UNESCAPE.keySet());
  }

  @Test
  public void testForDuplicateDeclaredMapValuesHtml40ExtendedMap() {
    assertThat(HTML40_EXTENDED_ESCAPE.keySet())
        .hasSameSizeAs(HTML40_EXTENDED_UNESCAPE.keySet());
  }

  @Test
  public void testForDuplicateDeclaredMapValuesISO8859Map() {
    assertThat(ISO8859_1_ESCAPE.keySet()).hasSameSizeAs(
        ISO8859_1_UNESCAPE.keySet());
  }

  @Test
  public void testForDuplicateDeclaredMapValuesJavaCtrlCharsMap() {
    assertThat(JAVA_CTRL_CHARS_ESCAPE.keySet())
        .hasSameSizeAs(JAVA_CTRL_CHARS_UNESCAPE.keySet());
  }

  @Test
  public void testHtml40ExtendedMap() {
    testEscapeVsUnescapeMaps(HTML40_EXTENDED_ESCAPE, HTML40_EXTENDED_UNESCAPE);
  }

  @Test
  public void testISO8859Map() {
    testEscapeVsUnescapeMaps(ISO8859_1_ESCAPE, ISO8859_1_UNESCAPE);
  }

  @Test
  public void testJavaCtrlCharsMap() {
    testEscapeVsUnescapeMaps(JAVA_CTRL_CHARS_ESCAPE, JAVA_CTRL_CHARS_UNESCAPE);
  }
}