////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.translate;

import java.io.CharArrayWriter;
import java.io.IOException;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UnicodeUnpairedSurrogateRemoverTest {
  final UnicodeUnpairedSurrogateRemover subject = new UnicodeUnpairedSurrogateRemover();
  final CharArrayWriter writer = new CharArrayWriter(); // nothing is ever written to it

  @Test
  public void testInvalidCharacters() throws IOException {
    assertThat(subject.translate(0xd800, writer)).isTrue();
    assertThat(subject.translate(0xdfff, writer)).isTrue();
    assertThat(writer.size()).isZero();
  }

  @Test
  public void testValidCharacters() throws IOException {
    assertThat(subject.translate(0xd7ff, writer)).isFalse();
    assertThat(subject.translate(0xe000, writer)).isFalse();
    assertThat(writer.size()).isZero();
  }
}
