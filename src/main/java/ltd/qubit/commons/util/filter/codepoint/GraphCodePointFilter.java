////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.filter.codepoint;

import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.lang.CharUtils;

/**
 * A CharFilter that accept any character that is a graph character.
 *
 * @author Haixing Hu
 */
@Immutable
public final class GraphCodePointFilter implements CodePointFilter {

  public static final GraphCodePointFilter INSTANCE = new GraphCodePointFilter();

  private GraphCodePointFilter() {}

  @Override
  public boolean accept(final Integer codePoint) {
    return (codePoint != null) && CharUtils.isGraph(codePoint);
  }

  public String toString() {
    return this.getClass().getSimpleName();
  }
}
