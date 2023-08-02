////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.filter.codepoint;

import javax.annotation.concurrent.Immutable;

/**
 * A CharFilter that accept only the letter or digit characters or whitespace
 * characters.
 *
 * @author Haixing Hu
 */
@Immutable
public final class LetterDigitSpaceCodePointFilter implements CodePointFilter {

  public static final LetterDigitSpaceCodePointFilter INSTANCE = new LetterDigitSpaceCodePointFilter();

  private LetterDigitSpaceCodePointFilter() {}

  @Override
  public boolean accept(final Integer codePoint) {
    return (codePoint != null)
        && (Character.isLetterOrDigit(codePoint) || Character.isWhitespace(codePoint));
  }

  public String toString() {
    return this.getClass().getSimpleName();
  }
}
