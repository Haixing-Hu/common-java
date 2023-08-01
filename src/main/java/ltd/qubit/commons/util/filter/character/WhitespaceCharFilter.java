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
 * A CharFilter that accept only the whitespace characters.
 *
 * @author Haixing Hu
 */
@Immutable
public final class WhitespaceCharFilter implements CharFilter {

  public static final WhitespaceCharFilter INSTANCE = new WhitespaceCharFilter();

  private WhitespaceCharFilter() {}

  @Override
  public boolean accept(final Character ch) {
    return (ch != null) && Character.isWhitespace(ch);
  }

  public String toString() {
    return this.getClass().getSimpleName();
  }
}
