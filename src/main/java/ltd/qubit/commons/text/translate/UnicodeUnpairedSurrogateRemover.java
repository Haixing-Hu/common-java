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
import javax.annotation.concurrent.Immutable;

/**
 * Helper subclass to CharSequenceTranslator to remove unpaired surrogates.
 *
 * @author Haixing Hu
 */
@Immutable
public class UnicodeUnpairedSurrogateRemover extends CodePointTranslator {
  /**
   * Implementation of translate that throws out unpaired surrogates.
   * {@inheritDoc}
   */
  @Override
  public boolean translate(final int codePoint, final Appendable appendable)
      throws IOException {
    // If true, it is a surrogate. Write nothing and say we've translated.
    // Otherwise return false, and don't translate it.
    return (codePoint >= Character.MIN_SURROGATE) && (codePoint <= Character.MAX_SURROGATE);
  }

  @Override
  public UnicodeUnpairedSurrogateRemover clone() {
    return new UnicodeUnpairedSurrogateRemover();
  }
}
