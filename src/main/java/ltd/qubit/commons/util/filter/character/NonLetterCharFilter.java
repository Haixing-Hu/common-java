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
 * A CharFilter that accept only the non-letter characters.
 *
 * @author Haixing Hu
 */
@Immutable
public final class NonLetterCharFilter implements CharFilter {

  public static final NonLetterCharFilter INSTANCE = new NonLetterCharFilter();

  private NonLetterCharFilter() {}

  @Override
  public boolean accept(final Character ch) {
    return (ch != null) && (!Character.isLetter(ch));
  }

  public String toString() {
    return this.getClass().getSimpleName();
  }
}