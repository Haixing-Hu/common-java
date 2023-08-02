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

import ltd.qubit.commons.text.Ascii;

/**
 * A character filter which accepts only non-ASCII characters.
 *
 * @author Haixing Hu
 */
@Immutable
public class NonAsciiCharFilter implements CharFilter {

  public static final NonAsciiCharFilter INSTANCE = new NonAsciiCharFilter();

  private NonAsciiCharFilter() {}

  @Override
  public boolean accept(final Character ch) {
    return (ch != null) && (!Ascii.isAscii(ch));
  }

  public String toString() {
    return this.getClass().getSimpleName();
  }
}
