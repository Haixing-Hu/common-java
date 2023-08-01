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
 * A CharFilter that accept only the digit characters or whitespace character.
 *
 * @author Haixing Hu
 */
@Immutable
public final class DigitSpaceCodePointFilter implements CodePointFilter {

  public static final DigitSpaceCodePointFilter INSTANCE = new DigitSpaceCodePointFilter();

  private DigitSpaceCodePointFilter() {}

  @Override
  public boolean accept(final Integer codePoint) {
    return (codePoint != null)
        && (Character.isDigit(codePoint) || Character.isWhitespace(codePoint));
  }

  public String toString() {
    return this.getClass().getSimpleName();
  }
}
