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
 * A CharFilter that accept only the non-letter characters.
 *
 * @author Haixing Hu
 */
@Immutable
public final class NonLetterCodePointFilter implements CodePointFilter {

  public static final NonLetterCodePointFilter INSTANCE = new NonLetterCodePointFilter();

  private NonLetterCodePointFilter() {}

  @Override
  public boolean accept(final Integer codePoint) {
    return (codePoint != null) && (!Character.isLetter(codePoint));
  }

  public String toString() {
    return this.getClass().getSimpleName();
  }
}
