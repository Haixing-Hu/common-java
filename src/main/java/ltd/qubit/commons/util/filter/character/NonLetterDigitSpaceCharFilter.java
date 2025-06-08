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

/**
 * 一个字符过滤器，仅接受既不是字母、也不是数字、也不是空白的字符。
 *
 * @author 胡海星
 */
@Immutable
public final class NonLetterDigitSpaceCharFilter implements CharFilter {

  /**
   * 此类的单例实例。
   */
  public static final NonLetterDigitSpaceCharFilter INSTANCE = new NonLetterDigitSpaceCharFilter();

  private NonLetterDigitSpaceCharFilter() {}

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean accept(final Character ch) {
    return (ch != null)
        && (!Character.isLetterOrDigit(ch))
        && (!Character.isWhitespace(ch));
  }

  public String toString() {
    return this.getClass().getSimpleName();
  }
}