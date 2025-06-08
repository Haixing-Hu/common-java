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
 * 一个字符过滤器，仅接受既不是数字也不是空白的字符。
 *
 * @author 胡海星
 */
@Immutable
public final class NonDigitSpaceCharFilter implements CharFilter {

  /**
   * 此类的单例实例。
   */
  public static final NonDigitSpaceCharFilter INSTANCE = new NonDigitSpaceCharFilter();

  private NonDigitSpaceCharFilter() {}

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean accept(final Character ch) {
    return (ch != null)
        && (!Character.isDigit(ch))
        && (!Character.isWhitespace(ch));
  }

  public String toString() {
    return this.getClass().getSimpleName();
  }
}