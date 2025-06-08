////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.filter.character;

import javax.annotation.concurrent.Immutable;

/**
 * A CharFilter that accept only the non-letter and non-digit characters.
 *
 * @author Haixing Hu
 */
@Immutable
public final class NonLetterDigitCharFilter implements CharFilter {

  public static final NonLetterDigitCharFilter INSTANCE = new NonLetterDigitCharFilter();

  private NonLetterDigitCharFilter() {}

  @Override
  public boolean accept(final Character ch) {
    return (ch != null) && (!Character.isLetterOrDigit(ch));
  }

  public String toString() {
    return this.getClass().getSimpleName();
  }
}