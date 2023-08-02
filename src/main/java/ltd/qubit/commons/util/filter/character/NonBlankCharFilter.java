////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.filter.character;

import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.lang.CharUtils;


/**
 * A CharFilter that accept only the non-blank characters.
 *
 * @author Haixing Hu
 */
@Immutable
public final class NonBlankCharFilter implements CharFilter {

  public static final NonBlankCharFilter INSTANCE = new NonBlankCharFilter();

  private NonBlankCharFilter() {}

  @Override
  public boolean accept(final Character ch) {
    return (ch != null) && (!CharUtils.isBlank(ch));
  }

  public String toString() {
    return this.getClass().getSimpleName();
  }
}
