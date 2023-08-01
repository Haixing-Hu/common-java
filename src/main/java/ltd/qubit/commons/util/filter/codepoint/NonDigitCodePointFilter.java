////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.filter.codepoint;

import javax.annotation.concurrent.Immutable;


/**
 * A CharFilter that accept only the non-digit characters.
 *
 * @author Haixing Hu
 */
@Immutable
public final class NonDigitCodePointFilter implements CodePointFilter {

  public static final NonDigitCodePointFilter INSTANCE = new NonDigitCodePointFilter();

  private NonDigitCodePointFilter() {}

  @Override
  public boolean accept(final Integer codePoint) {
    return (codePoint != null) && (!Character.isDigit(codePoint));
  }

  public String toString() {
    return this.getClass().getSimpleName();
  }
}
