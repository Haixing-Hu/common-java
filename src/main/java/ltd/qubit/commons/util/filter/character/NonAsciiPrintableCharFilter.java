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
 * 一个字符过滤器，只接受非 ASCII 可打印字符。
 *
 * @author 胡海星
 * @see Ascii#isPrintable(char)
 */
@Immutable
public class NonAsciiPrintableCharFilter implements CharFilter {

  /**
   * 此类的单例实例。
   */
  public static final NonAsciiPrintableCharFilter INSTANCE = new NonAsciiPrintableCharFilter();

  private NonAsciiPrintableCharFilter() {}

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean accept(final Character ch) {
    return (ch != null) && (!Ascii.isPrintable(ch));
  }

  public String toString() {
    return this.getClass().getSimpleName();
  }
}