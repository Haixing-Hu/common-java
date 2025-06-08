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

import ltd.qubit.commons.lang.CharUtils;


/**
 * 一个字符过滤器，仅接受非空白字符。
 *
 * @author 胡海星
 * @see CharUtils#isBlank(char)
 */
@Immutable
public final class NonBlankCharFilter implements CharFilter {

  /**
   * 此类的单例实例。
   */
  public static final NonBlankCharFilter INSTANCE = new NonBlankCharFilter();

  private NonBlankCharFilter() {}

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean accept(final Character ch) {
    return (ch != null) && (!CharUtils.isBlank(ch));
  }

  public String toString() {
    return this.getClass().getSimpleName();
  }
}