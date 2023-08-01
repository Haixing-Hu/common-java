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
 * A character filter which accepts only non-uppercase characters.
 *
 * @author Haixing Hu
 */
@Immutable
public class NonUpperCaseCodePointFilter implements CodePointFilter {

  public static final NonUpperCaseCodePointFilter INSTANCE = new NonUpperCaseCodePointFilter();

  private NonUpperCaseCodePointFilter() {}

  @Override
  public boolean accept(final Integer codePoint) {
    return (codePoint != null) && (!Character.isUpperCase(codePoint));
  }

  public String toString() {
    return this.getClass().getSimpleName();
  }
}
