////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.filter.character;

/**
 * An empty character filter that rejects all characters.
 *
 * @author Haixing Hu
 */
public class RejectAllCharFilter implements CharFilter {

  public static final RejectAllCharFilter INSTANCE = new RejectAllCharFilter();

  private RejectAllCharFilter() {}

  @Override
  public boolean accept(final Character ch) {
    return false;
  }
}
