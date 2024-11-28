////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.filter.character;

import javax.annotation.concurrent.Immutable;

/**
 * A CharFilter that accept only the letter characters.
 *
 * @author Haixing Hu
 */
@Immutable
public final class LetterCharFilter implements CharFilter {

  public static final LetterCharFilter INSTANCE = new LetterCharFilter();

  private LetterCharFilter() {}

  @Override
  public boolean accept(final Character ch) {
    return (ch != null) && Character.isLetter(ch);
  }

  public String toString() {
    return this.getClass().getSimpleName();
  }
}
