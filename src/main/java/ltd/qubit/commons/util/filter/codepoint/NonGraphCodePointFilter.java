////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.filter.codepoint;

import ltd.qubit.commons.lang.CharUtils;

import javax.annotation.concurrent.Immutable;

/**
 * A CharFilter that accept any character that is a non-graph character.
 *
 * @author Haixing Hu
 */
@Immutable
public final class NonGraphCodePointFilter implements CodePointFilter {

  public static final NonGraphCodePointFilter INSTANCE = new NonGraphCodePointFilter();

  private NonGraphCodePointFilter() {}

  @Override
  public boolean accept(final Integer codePoint) {
    return (codePoint != null) && (!CharUtils.isGraph(codePoint));
  }

  public String toString() {
    return this.getClass().getSimpleName();
  }
}
