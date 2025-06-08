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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

public class SinglePassTranslatorTest {

  private final SinglePassTranslator dummyTranslator = new SinglePassTranslator() {
    @Override
    void translateWhole(final CharSequence input, final Appendable appendable)
        throws IOException {
      // noop
    }

    @Override
    public SinglePassTranslator cloneEx() {
      return this;
    }
  };

  private StringWriter out;

  @BeforeEach
  public void before() {
    out = new StringWriter();
  }

  @Test
  public void codePointsAreReturned() throws Exception {
    assertThat(dummyTranslator.translate("", 0, out)).isEqualTo(0);
    assertThat(dummyTranslator.translate("abc", 0, out)).isEqualTo(3);
    assertThat(dummyTranslator.translate("abcdefg", 0, out)).isEqualTo(7);
  }

  @Test
  public void indexIsValidated() {
    assertThatIllegalArgumentException()
        .isThrownBy(() -> dummyTranslator.translate("abc", 1, out));
  }

  @Test
  public void testTranslateThrowsIllegalArgumentException() {
    assertThatIllegalArgumentException()
        .isThrownBy(() -> dummyTranslator.translate("(,Fk", 647, null));
  }
}