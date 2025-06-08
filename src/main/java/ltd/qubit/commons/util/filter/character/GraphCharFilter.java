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
 * 一个字符过滤器，接受所有可见字符。
 *
 * @author 胡海星
 * @see CharUtils#isGraph(char)
 */
@Immutable
public final class GraphCharFilter implements CharFilter {

  /**
   * 此类的单例实例。
   */
  public static final GraphCharFilter INSTANCE = new GraphCharFilter();

  private GraphCharFilter() {}

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean accept(final Character ch) {
    return (ch != null) && CharUtils.isGraph(ch);
  }

  public String toString() {
    return this.getClass().getSimpleName();
  }
}