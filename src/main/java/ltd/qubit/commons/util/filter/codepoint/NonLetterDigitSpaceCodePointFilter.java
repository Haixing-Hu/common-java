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
 * A CharFilter that accept only the non-letter and non-digit characters and
 * whitespace characters.
 *
 * @author Haixing Hu
 */
@Immutable
public final class NonLetterDigitSpaceCodePointFilter implements CodePointFilter {

  public static final NonLetterDigitSpaceCodePointFilter INSTANCE = new NonLetterDigitSpaceCodePointFilter();

  private NonLetterDigitSpaceCodePointFilter() {}

  @Override
  public boolean accept(final Integer codePoint) {
    return (codePoint != null)
        && (!Character.isLetterOrDigit(codePoint))
        && (!Character.isWhitespace(codePoint));
  }

  public String toString() {
    return this.getClass().getSimpleName();
  }
}
