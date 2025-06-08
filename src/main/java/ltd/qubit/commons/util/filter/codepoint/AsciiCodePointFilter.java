////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.filter.codepoint;

import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.text.Ascii;

/**
 * 一个代码点过滤器，只接受 ASCII 字符。
 *
 * @author 胡海星
 * @see Ascii#isAscii(int)
 */
@Immutable
public class AsciiCodePointFilter implements CodePointFilter {

  /**
   * 此类的单例实例。
   */
  public static final AsciiCodePointFilter INSTANCE = new AsciiCodePointFilter();

  private AsciiCodePointFilter() {}

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean accept(final Integer codePoint) {
    return (codePoint != null) && Ascii.isAscii(codePoint);
  }

  public String toString() {
    return this.getClass().getSimpleName();
  }
}