////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.filter.character;

import javax.annotation.concurrent.Immutable;

/**
 * A CharFilter that accept only the letter characters or a whitespace character.
 *
 * @author Haixing Hu
 */
@Immutable
public final class LetterSpaceCharFilter implements CharFilter {

  public static final LetterSpaceCharFilter INSTANCE = new LetterSpaceCharFilter();

  private LetterSpaceCharFilter() {}

  @Override
  public boolean accept(final Character ch) {
    return (ch != null)
        && (Character.isLetter(ch) || Character.isWhitespace(ch));
  }

  public String toString() {
    return this.getClass().getSimpleName();
  }
}
