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
 * A character filter which accepts only non-ASCII printable characters.
 *
 * @author Haixing Hu
 */
@Immutable
public class NonAsciiPrintableCharFilter implements CharFilter {

  public static final NonAsciiPrintableCharFilter INSTANCE = new NonAsciiPrintableCharFilter();

  private NonAsciiPrintableCharFilter() {}

  @Override
  public boolean accept(final Character ch) {
    return (ch != null) && (!Ascii.isPrintable(ch));
  }

  public String toString() {
    return this.getClass().getSimpleName();
  }
}
