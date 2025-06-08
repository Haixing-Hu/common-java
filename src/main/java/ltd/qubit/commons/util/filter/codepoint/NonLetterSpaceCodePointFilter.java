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
 * 一个代码点过滤器，仅接受既非字母也非空白的代码点。
 *
 * @author 胡海星
 */
@Immutable
public final class NonLetterSpaceCodePointFilter implements CodePointFilter {

  /**
   * 此类的单例实例。
   */
  public static final NonLetterSpaceCodePointFilter INSTANCE =
      new NonLetterSpaceCodePointFilter();

  private NonLetterSpaceCodePointFilter() {}

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean accept(final Integer codePoint) {
    return (codePoint != null)
        && (!Character.isLetter(codePoint))
        && (!Character.isWhitespace(codePoint));
  }

  public String toString() {
    return this.getClass().getSimpleName();
  }
}