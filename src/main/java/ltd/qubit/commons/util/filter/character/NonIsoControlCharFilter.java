////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.filter.character;

import javax.annotation.concurrent.Immutable;


/**
 * A CharFilter that accept only the non-ISO control characters.
 *
 * @author Haixing Hu
 */
@Immutable
public final class NonIsoControlCharFilter implements CharFilter {

  public static final NonIsoControlCharFilter INSTANCE = new NonIsoControlCharFilter();

  private NonIsoControlCharFilter() {}

  @Override
  public boolean accept(final Character ch) {
    return (ch != null) && (!Character.isISOControl(ch));
  }

  public String toString() {
    return this.getClass().getSimpleName();
  }
}
