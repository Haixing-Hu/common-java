////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.filter.character;

import ltd.qubit.commons.text.Ascii;

import javax.annotation.concurrent.Immutable;

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
