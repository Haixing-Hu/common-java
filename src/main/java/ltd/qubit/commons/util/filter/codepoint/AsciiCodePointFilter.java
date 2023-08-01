////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.filter.codepoint;

import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.text.Ascii;

/**
 * A code point filter which accepts only ASCII characters.
 *
 * @author Haixing Hu
 */
@Immutable
public class AsciiCodePointFilter implements CodePointFilter {

  public static final AsciiCodePointFilter INSTANCE = new AsciiCodePointFilter();

  private AsciiCodePointFilter() {}

  @Override
  public boolean accept(final Integer codePoint) {
    return (codePoint != null) && Ascii.isAscii(codePoint);
  }

  public String toString() {
    return this.getClass().getSimpleName();
  }
}
