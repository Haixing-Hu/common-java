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
 * A CharFilter that accept only the letter characters or a whitespace character.
 *
 * @author Haixing Hu
 */
@Immutable
public final class LetterSpaceCodePointFilter implements CodePointFilter {

  public static final LetterSpaceCodePointFilter INSTANCE = new LetterSpaceCodePointFilter();

  private LetterSpaceCodePointFilter() {
    //  empty
  }

  @Override
  public boolean accept(final Integer codePoint) {
    return (codePoint != null)
        && (Character.isLetter(codePoint) || Character.isWhitespace(codePoint));
  }

  public String toString() {
    return this.getClass().getSimpleName();
  }
}
