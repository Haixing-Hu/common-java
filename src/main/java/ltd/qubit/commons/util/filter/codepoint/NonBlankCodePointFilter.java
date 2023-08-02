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
 * A code point filter that accept only the non-blank characters.
 *
 * @author Haixing Hu
 */
@Immutable
public final class NonBlankCodePointFilter implements CodePointFilter {

  public static final NonBlankCodePointFilter INSTANCE = new NonBlankCodePointFilter();

  private NonBlankCodePointFilter() {}

  @Override
  public boolean accept(final Integer codePoint) {
    return (codePoint != null) && (!CharUtils.isBlank(codePoint));
  }

  public String toString() {
    return this.getClass().getSimpleName();
  }
}
