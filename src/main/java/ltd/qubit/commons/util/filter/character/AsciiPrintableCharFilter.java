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

import ltd.qubit.commons.text.Ascii;

/**
 * 只接受 ASCII 可打印字符的字符过滤器。
 *
 * @author 胡海星
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