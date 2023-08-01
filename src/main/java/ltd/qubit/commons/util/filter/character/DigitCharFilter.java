////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.filter.character;

import javax.annotation.concurrent.Immutable;


/**
 * A CharFilter that accept only the digit characters.
 *
 * @author Haixing Hu
 */
@Immutable
public final class DigitCharFilter implements CharFilter {

  public static final DigitCharFilter INSTANCE = new DigitCharFilter();

  private DigitCharFilter() {}

  @Override
  public boolean accept(final Character ch) {
    return (ch != null) && Character.isDigit(ch);
  }

  public String toString() {
    return this.getClass().getSimpleName();
  }
}
