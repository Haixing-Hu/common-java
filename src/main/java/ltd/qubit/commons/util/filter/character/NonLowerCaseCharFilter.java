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

/**
 * A character filter which accepts only non-lowercase characters.
 *
 * @author Haixing Hu
 */
@Immutable
public class NonLowerCaseCharFilter implements CharFilter {

  public static final NonLowerCaseCharFilter INSTANCE = new NonLowerCaseCharFilter();

  private NonLowerCaseCharFilter() {}

  @Override
  public boolean accept(final Character ch) {
    return (ch != null) && (!Character.isLowerCase(ch));
  }

  public String toString() {
    return this.getClass().getSimpleName();
  }
}
