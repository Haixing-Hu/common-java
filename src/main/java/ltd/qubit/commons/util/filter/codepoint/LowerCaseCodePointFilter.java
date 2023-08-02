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

/**
 * A character filter which accepts only lowercase characters.
 *
 * @author Haixing Hu
 */
@Immutable
public class LowerCaseCodePointFilter implements CodePointFilter {

  public static final LowerCaseCodePointFilter INSTANCE = new LowerCaseCodePointFilter();

  private LowerCaseCodePointFilter() {}

  @Override
  public boolean accept(final Integer codePoint) {
    return (codePoint != null) && Character.isLowerCase(codePoint);
  }

  public String toString() {
    return this.getClass().getSimpleName();
  }
}
