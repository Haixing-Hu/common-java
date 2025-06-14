////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.filter.character;

import javax.annotation.concurrent.Immutable;


/**
 * A CharFilter that accept only non-whitespace characters.
 *
 * @author Haixing Hu
 */
@Immutable
public final class NonWhitespaceCharFilter implements CharFilter {

  public static final NonWhitespaceCharFilter INSTANCE = new NonWhitespaceCharFilter();

  private NonWhitespaceCharFilter() {}

  @Override
  public boolean accept(final Character ch) {
    return (ch != null) && (!Character.isWhitespace(ch));
  }

  public String toString() {
    return this.getClass().getSimpleName();
  }
}