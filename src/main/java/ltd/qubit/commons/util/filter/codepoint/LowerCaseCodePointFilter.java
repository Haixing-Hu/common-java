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
 * 一个代码点过滤器，仅接受小写字母代码点。
 *
 * @author 胡海星
 */
@Immutable
public class LowerCaseCodePointFilter implements CodePointFilter {

  /**
   * 此类的单例实例。
   */
  public static final LowerCaseCodePointFilter INSTANCE = new LowerCaseCodePointFilter();

  private LowerCaseCodePointFilter() {}

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean accept(final Integer codePoint) {
    return (codePoint != null) && Character.isLowerCase(codePoint);
  }

  public String toString() {
    return this.getClass().getSimpleName();
  }
}