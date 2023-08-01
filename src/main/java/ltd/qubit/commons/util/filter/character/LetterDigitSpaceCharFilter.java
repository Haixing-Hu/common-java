////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.filter.character;

import javax.annotation.concurrent.Immutable;

/**
 * A CharFilter that accept only the letter or digit characters or whitespace
 * characters.
 *
 * @author Haixing Hu
 */
@Immutable
public final class LetterDigitSpaceCharFilter implements CharFilter {

  public static final LetterDigitSpaceCharFilter INSTANCE = new LetterDigitSpaceCharFilter();

  private LetterDigitSpaceCharFilter() {}

  @Override
  public boolean accept(final Character ch) {
    return (ch != null)
        && (Character.isLetterOrDigit(ch) || Character.isWhitespace(ch));
  }

  public String toString() {
    return this.getClass().getSimpleName();
  }
}
