////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.filter.codepoint;

import javax.annotation.concurrent.Immutable;

/**
 * A CharFilter that accept only the letter or digit characters.
 *
 * @author Haixing Hu
 */
@Immutable
public final class LetterDigitCodePointFilter implements CodePointFilter {

  public static final LetterDigitCodePointFilter INSTANCE = new LetterDigitCodePointFilter();

  private LetterDigitCodePointFilter() {}

  @Override
  public boolean accept(final Integer codePoint) {
    return (codePoint != null) && Character.isLetterOrDigit(codePoint);
  }

  public String toString() {
    return this.getClass().getSimpleName();
  }
}
