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

import ltd.qubit.commons.text.Ascii;

/**
 * A code point filter which accepts only non-ASCII characters.
 *
 * @author Haixing Hu
 */
@Immutable
public class NonAsciiCodePointFilter implements CodePointFilter {

  public static final NonAsciiCodePointFilter INSTANCE = new NonAsciiCodePointFilter();

  private NonAsciiCodePointFilter() {}

  @Override
  public boolean accept(final Integer codePoint) {
    return (codePoint != null) && (!Ascii.isAscii(codePoint));
  }

  public String toString() {
    return this.getClass().getSimpleName();
  }
}
