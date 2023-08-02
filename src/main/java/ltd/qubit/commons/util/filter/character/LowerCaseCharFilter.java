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
 * A character filter which accepts only lowercase characters.
 *
 * @author Haixing Hu
 */
@Immutable
public class LowerCaseCharFilter implements CharFilter {

  public static final LowerCaseCharFilter INSTANCE = new LowerCaseCharFilter();

  private LowerCaseCharFilter() {}

  @Override
  public boolean accept(final Character ch) {
    return (ch != null) && Character.isLowerCase(ch);
  }

  public String toString() {
    return this.getClass().getSimpleName();
  }
}
