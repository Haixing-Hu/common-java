////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.filter.codepoint;

import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.lang.CharUtils;


/**
 * A code point filter that accept only the blank characters.
 *
 * @author Haixing Hu
 */
@Immutable
public final class BlankCodePointFilter implements CodePointFilter {

  public static final BlankCodePointFilter INSTANCE = new BlankCodePointFilter();

  private BlankCodePointFilter() {}

  @Override
  public boolean accept(final Integer codePoint) {
    return (codePoint != null) && CharUtils.isBlank(codePoint);
  }

  public String toString() {
    return this.getClass().getSimpleName();
  }
}
