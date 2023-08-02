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

/**
 * A character filter which accepts only uppercase characters.
 *
 * @author Haixing Hu
 */
@Immutable
public class UpperCaseCharFilter implements CharFilter {

  public static final UpperCaseCharFilter INSTANCE = new UpperCaseCharFilter();

  private UpperCaseCharFilter() {}

  @Override
  public boolean accept(final Character ch) {
    return (ch != null) && Character.isUpperCase(ch);
  }

  public String toString() {
    return this.getClass().getSimpleName();
  }
}
