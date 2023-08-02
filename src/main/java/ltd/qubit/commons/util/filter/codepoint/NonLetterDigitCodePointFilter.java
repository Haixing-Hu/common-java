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
 * A CharFilter that accept only non-letter and non-digit characters.
 *
 * @author Haixing Hu
 */
@Immutable
public final class NonLetterDigitCodePointFilter implements CodePointFilter {

  public static final NonLetterDigitCodePointFilter INSTANCE = new NonLetterDigitCodePointFilter();

  private NonLetterDigitCodePointFilter() {}

  @Override
  public boolean accept(final Integer codePoint) {
    return (codePoint != null) && (!Character.isLetterOrDigit(codePoint));
  }

  public String toString() {
    return this.getClass().getSimpleName();
  }
}
