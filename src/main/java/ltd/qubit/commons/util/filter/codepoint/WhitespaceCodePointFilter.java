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
 * A CharFilter that accept only the whitespace characters.
 *
 * @author Haixing Hu
 */
@Immutable
public final class WhitespaceCodePointFilter implements CodePointFilter {

  public static final WhitespaceCodePointFilter INSTANCE = new WhitespaceCodePointFilter();

  private WhitespaceCodePointFilter() {}

  @Override
  public boolean accept(final Integer codePoint) {
    return (codePoint != null) && Character.isWhitespace(codePoint);
  }

  public String toString() {
    return this.getClass().getSimpleName();
  }
}
