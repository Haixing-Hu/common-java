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
 * A CharFilter that accept only the non-letter and non-whitespace character.
 *
 * @author Haixing Hu
 */
@Immutable
public final class NonLetterSpaceCodePointFilter implements CodePointFilter {

  public static final NonLetterSpaceCodePointFilter INSTANCE = new NonLetterSpaceCodePointFilter();

  private NonLetterSpaceCodePointFilter() {}

  @Override
  public boolean accept(final Integer codePoint) {
    return (codePoint != null)
        && (!Character.isLetter(codePoint))
        && (!Character.isWhitespace(codePoint));
  }

  public String toString() {
    return this.getClass().getSimpleName();
  }
}
