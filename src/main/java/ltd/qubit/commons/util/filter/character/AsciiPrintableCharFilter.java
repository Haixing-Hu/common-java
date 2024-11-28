////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.filter.character;

import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.text.Ascii;

/**
 * A character filter which accepts only ASCII printable characters.
 *
 * @author Haixing Hu
 */
@Immutable
public class AsciiPrintableCharFilter implements CharFilter {

  public static final AsciiPrintableCharFilter INSTANCE = new AsciiPrintableCharFilter();

  private AsciiPrintableCharFilter() {}

  @Override
  public boolean accept(final Character ch) {
    return (ch != null) && Ascii.isPrintable(ch);
  }

  public String toString() {
    return this.getClass().getSimpleName();
  }
}
