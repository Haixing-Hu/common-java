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
 * A CharFilter that accept only the non-digit characters and non-whitespace
 * character.
 *
 * @author Haixing Hu
 */
@Immutable
public final class NonDigitSpaceCharFilter implements CharFilter {

  public static final NonDigitSpaceCharFilter INSTANCE = new NonDigitSpaceCharFilter();

  private NonDigitSpaceCharFilter() {}

  @Override
  public boolean accept(final Character ch) {
    return (ch != null)
        && (!Character.isDigit(ch))
        && (!Character.isWhitespace(ch));
  }

  public String toString() {
    return this.getClass().getSimpleName();
  }
}
