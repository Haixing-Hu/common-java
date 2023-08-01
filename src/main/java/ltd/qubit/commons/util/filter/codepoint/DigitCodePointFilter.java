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
 * A CharFilter that accept only the digit characters.
 *
 * @author Haixing Hu
 */
@Immutable
public final class DigitCodePointFilter implements CodePointFilter {

  public static final DigitCodePointFilter INSTANCE = new DigitCodePointFilter();

  private DigitCodePointFilter() {}

  @Override
  public boolean accept(final Integer codePoint) {
    return (codePoint != null) && Character.isDigit(codePoint);
  }

  public String toString() {
    return this.getClass().getSimpleName();
  }
}
