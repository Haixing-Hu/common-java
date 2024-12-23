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
 * A CharFilter that accept only the non-digit characters.
 *
 * @author Haixing Hu
 */
@Immutable
public final class NonDigitCharFilter implements CharFilter {

  public static final NonDigitCharFilter INSTANCE = new NonDigitCharFilter();

  private NonDigitCharFilter() {}

  @Override
  public boolean accept(final Character ch) {
    return (ch != null) && (!Character.isDigit(ch));
  }

  public String toString() {
    return this.getClass().getSimpleName();
  }
}
