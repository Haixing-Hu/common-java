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
 * A CharFilter that accept only the non-letter and non-digit and non-whitespace
 * characters.
 *
 * @author Haixing Hu
 */
@Immutable
public final class NonLetterDigitSpaceCharFilter implements CharFilter {

  public static final NonLetterDigitSpaceCharFilter INSTANCE = new NonLetterDigitSpaceCharFilter();

  private NonLetterDigitSpaceCharFilter() {}

  @Override
  public boolean accept(final Character ch) {
    return (ch != null)
        && (!Character.isLetterOrDigit(ch))
        && (!Character.isWhitespace(ch));
  }

  public String toString() {
    return this.getClass().getSimpleName();
  }
}
