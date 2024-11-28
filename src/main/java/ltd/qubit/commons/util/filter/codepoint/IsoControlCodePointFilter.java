////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.filter.codepoint;

import javax.annotation.concurrent.Immutable;


/**
 * A CharFilter that accept only the ISO control characters.
 *
 * @author Haixing Hu
 */
@Immutable
public final class IsoControlCodePointFilter implements CodePointFilter {

  public static final IsoControlCodePointFilter INSTANCE = new IsoControlCodePointFilter();

  private IsoControlCodePointFilter() {}

  @Override
  public boolean accept(final Integer codePoint) {
    return (codePoint != null) && Character.isISOControl(codePoint);
  }

  public String toString() {
    return this.getClass().getSimpleName();
  }
}
