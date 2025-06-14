////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.filter.codepoint;

import javax.annotation.concurrent.Immutable;

/**
 * A CharFilter that accept only the letter characters.
 *
 * @author Haixing Hu
 */
@Immutable
public final class LetterCodePointFilter implements CodePointFilter {

  public static final LetterCodePointFilter INSTANCE = new LetterCodePointFilter();

  private LetterCodePointFilter() {}

  @Override
  public boolean accept(final Integer codePoint) {
    return (codePoint != null) && Character.isLetter(codePoint);
  }

  public String toString() {
    return this.getClass().getSimpleName();
  }
}