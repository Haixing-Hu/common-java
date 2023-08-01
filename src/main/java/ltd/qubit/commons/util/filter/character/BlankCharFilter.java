////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.filter.character;

import ltd.qubit.commons.lang.CharUtils;

import javax.annotation.concurrent.Immutable;


/**
 * A CharFilter that accept only the blank characters.
 *
 * @author Haixing Hu
 */
@Immutable
public final class BlankCharFilter implements CharFilter {

  public static final BlankCharFilter INSTANCE = new BlankCharFilter();

  private BlankCharFilter() {}

  @Override
  public boolean accept(final Character ch) {
    return (ch != null) && CharUtils.isBlank(ch);
  }

  public String toString() {
    return this.getClass().getSimpleName();
  }
}
