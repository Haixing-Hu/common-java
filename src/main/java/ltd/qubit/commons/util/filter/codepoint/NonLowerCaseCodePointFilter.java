////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.filter.codepoint;

import javax.annotation.concurrent.Immutable;

/**
 * A character filter which accepts only non-lowercase characters.
 *
 * @author Haixing Hu
 */
@Immutable
public class NonLowerCaseCodePointFilter implements CodePointFilter {

  public static final NonLowerCaseCodePointFilter INSTANCE = new NonLowerCaseCodePointFilter();

  private NonLowerCaseCodePointFilter() {}

  @Override
  public boolean accept(final Integer codePoint) {
    return (codePoint != null) && (!Character.isLowerCase(codePoint));
  }

  public String toString() {
    return this.getClass().getSimpleName();
  }
}
