////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.filter.codepoint;

import javax.annotation.concurrent.Immutable;


/**
 * A CharFilter that accept only the non-digit and non-whitespace character.
 *
 * @author Haixing Hu
 */
@Immutable
public final class NonDigitSpaceCodePointFilter implements CodePointFilter {

  public static final NonDigitSpaceCodePointFilter INSTANCE = new NonDigitSpaceCodePointFilter();

  private NonDigitSpaceCodePointFilter() {}

  @Override
  public boolean accept(final Integer codePoint) {
    return (codePoint != null)
        && (!Character.isDigit(codePoint))
        && (!Character.isWhitespace(codePoint));
  }

  public String toString() {
    return this.getClass().getSimpleName();
  }
}
