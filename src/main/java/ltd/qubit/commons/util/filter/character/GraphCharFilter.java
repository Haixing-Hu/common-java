////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.filter.character;

import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.lang.CharUtils;

/**
 * A CharFilter that accept any character that is a graph character.
 *
 * @author Haixing Hu
 */
@Immutable
public final class GraphCharFilter implements CharFilter {

  public static final GraphCharFilter INSTANCE = new GraphCharFilter();

  private GraphCharFilter() {}

  @Override
  public boolean accept(final Character ch) {
    return (ch != null) && CharUtils.isGraph(ch);
  }

  public String toString() {
    return this.getClass().getSimpleName();
  }
}
