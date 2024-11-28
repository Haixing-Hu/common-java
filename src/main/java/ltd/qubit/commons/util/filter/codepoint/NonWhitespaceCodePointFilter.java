////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.filter.codepoint;

import javax.annotation.concurrent.Immutable;

/**
 * A CharFilter that accept only the non-whitespace characters.
 *
 * @author Haixing Hu
 */
@Immutable
public final class NonWhitespaceCodePointFilter implements CodePointFilter {

  public static final NonWhitespaceCodePointFilter INSTANCE = new NonWhitespaceCodePointFilter();

  private NonWhitespaceCodePointFilter() {}

  @Override
  public boolean accept(final Integer codePoint) {
    return (codePoint != null) && Character.isWhitespace(codePoint);
  }

  public String toString() {
    return this.getClass().getSimpleName();
  }
}
