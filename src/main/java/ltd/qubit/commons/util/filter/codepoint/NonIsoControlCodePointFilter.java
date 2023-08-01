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
 * A CharFilter that accept only the non-ISO control characters.
 *
 * @author Haixing Hu
 */
@Immutable
public final class NonIsoControlCodePointFilter implements CodePointFilter {

  public static final NonIsoControlCodePointFilter INSTANCE = new NonIsoControlCodePointFilter();

  private NonIsoControlCodePointFilter() {}

  @Override
  public boolean accept(final Integer codePoint) {
    return (codePoint != null) && (!Character.isISOControl(codePoint));
  }

  public String toString() {
    return this.getClass().getSimpleName();
  }
}
