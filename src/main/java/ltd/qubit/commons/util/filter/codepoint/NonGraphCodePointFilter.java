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
 * 一个代码点过滤器，接受所有非可见字符。
 *
 * @author 胡海星
 * @see CharUtils#isGraph(int)
 */
@Immutable
public final class NonGraphCodePointFilter implements CodePointFilter {

  /**
   * 此类的单例实例。
   */
  public static final NonGraphCodePointFilter INSTANCE = new NonGraphCodePointFilter();

  private NonGraphCodePointFilter() {}

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean accept(final Integer codePoint) {
    return (codePoint != null) && (!CharUtils.isGraph(codePoint));
  }

  public String toString() {
    return this.getClass().getSimpleName();
  }
}