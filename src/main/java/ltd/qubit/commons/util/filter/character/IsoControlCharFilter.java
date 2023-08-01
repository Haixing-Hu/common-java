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
 * A CharFilter that accept only the ISO control characters.
 *
 * @author Haixing Hu
 */
@Immutable
public final class IsoControlCharFilter implements CharFilter {

  public static final IsoControlCharFilter INSTANCE = new IsoControlCharFilter();

  private IsoControlCharFilter() {}

  @Override
  public boolean accept(final Character ch) {
    return (ch != null) && Character.isISOControl(ch);
  }

  public String toString() {
    return this.getClass().getSimpleName();
  }
}
