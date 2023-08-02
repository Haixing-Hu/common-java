////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
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
