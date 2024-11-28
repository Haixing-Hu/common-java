////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.filter.character;

import javax.annotation.concurrent.Immutable;

/**
 * A character filter which accepts only non-uppercase characters.
 *
 * @author Haixing Hu
 */
@Immutable
public class NonUpperCaseCharFilter implements CharFilter {

  public static final NonUpperCaseCharFilter INSTANCE = new NonUpperCaseCharFilter();

  private NonUpperCaseCharFilter() {}

  @Override
  public boolean accept(final Character ch) {
    return (ch != null) && (!Character.isUpperCase(ch));
  }

  public String toString() {
    return this.getClass().getSimpleName();
  }
}
