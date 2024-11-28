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
 * A CharFilter that accept only the digit characters or whitespace character.
 *
 * @author Haixing Hu
 */
@Immutable
public final class DigitSpaceCharFilter implements CharFilter {

  public static final DigitSpaceCharFilter INSTANCE = new DigitSpaceCharFilter();

  private DigitSpaceCharFilter() {}

  @Override
  public boolean accept(final Character ch) {
    return (ch != null)
        && (Character.isDigit(ch) || Character.isWhitespace(ch));
  }

  public String toString() {
    return this.getClass().getSimpleName();
  }
}
