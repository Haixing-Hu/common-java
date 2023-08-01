////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.translate;

import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CodePointTranslatorTest {

  @Test
  public void testAboveReturningNonNull() throws IOException {
    final NumericEntityEscaper numericEntityEscaper = NumericEntityEscaper.above(0);
    final UnicodeEscaper unicodeEscaper = new UnicodeEscaper();
    final String string = "\\u0";
    try (final PipedReader pipedReader = new PipedReader();
        final PipedWriter pipedWriter = new PipedWriter(pipedReader)) {
      assertThat(numericEntityEscaper.translate(string, 0, pipedWriter)).isEqualTo(1);
    }
  }
}
