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
 * A CharFilter that accept only the letter or digit characters.
 *
 * @author Haixing Hu
 */
@Immutable
public final class LetterDigitCharFilter implements CharFilter {

  public static final LetterDigitCharFilter INSTANCE = new LetterDigitCharFilter();

  private LetterDigitCharFilter() {}

  @Override
  public boolean accept(final Character ch) {
    return (ch != null) && Character.isLetterOrDigit(ch);
  }

  public String toString() {
    return this.getClass().getSimpleName();
  }
}
