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

/**
 * 一个代码点过滤器，仅接受字母或数字代码点。
 *
 * @author 胡海星
 */
@Immutable
public final class LetterDigitCodePointFilter implements CodePointFilter {

  /**
   * 此类的单例实例。
   */
  public static final LetterDigitCodePointFilter INSTANCE = new LetterDigitCodePointFilter();

  private LetterDigitCodePointFilter() {}

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean accept(final Integer codePoint) {
    return (codePoint != null) && Character.isLetterOrDigit(codePoint);
  }

  public String toString() {
    return this.getClass().getSimpleName();
  }
}