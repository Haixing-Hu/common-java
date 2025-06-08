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

import ltd.qubit.commons.lang.CharUtils;


/**
 * 一个代码点过滤器，仅接受非空白代码点。
 *
 * @author 胡海星
 * @see CharUtils#isBlank(int)
 */
@Immutable
public final class NonBlankCodePointFilter implements CodePointFilter {

  /**
   * 此类的单例实例。
   */
  public static final NonBlankCodePointFilter INSTANCE = new NonBlankCodePointFilter();

  private NonBlankCodePointFilter() {}

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean accept(final Integer codePoint) {
    return (codePoint != null) && (!CharUtils.isBlank(codePoint));
  }

  public String toString() {
    return this.getClass().getSimpleName();
  }
}