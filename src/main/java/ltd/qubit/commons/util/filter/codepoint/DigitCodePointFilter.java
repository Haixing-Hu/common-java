////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
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
