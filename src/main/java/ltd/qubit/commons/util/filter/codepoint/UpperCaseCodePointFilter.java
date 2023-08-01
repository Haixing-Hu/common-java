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
 * A character filter which accepts only uppercase characters.
 *
 * @author Haixing Hu
 */
@Immutable
public class UpperCaseCodePointFilter implements CodePointFilter {

  public static final UpperCaseCodePointFilter INSTANCE = new UpperCaseCodePointFilter();

  private UpperCaseCodePointFilter() {}

  @Override
  public boolean accept(final Integer codePoint) {
    return (codePoint != null) && Character.isUpperCase(codePoint);
  }

  public String toString() {
    return this.getClass().getSimpleName();
  }
}
