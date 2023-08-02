////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.filter.character;

/**
 * An empty character filter that accepts all characters.
 *
 * @author Haixing Hu
 */
public class AcceptAllCharFilter implements CharFilter {

  public static final AcceptAllCharFilter INSTANCE = new AcceptAllCharFilter();

  private AcceptAllCharFilter() {}

  @Override
  public boolean accept(final Character ch) {
    return true;
  }
}
