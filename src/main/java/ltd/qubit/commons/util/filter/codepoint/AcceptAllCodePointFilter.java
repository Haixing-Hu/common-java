////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.filter.codepoint;

/**
 * An empty code point filter that accepts all code points.
 *
 * @author Haixing Hu
 */
public class AcceptAllCodePointFilter implements CodePointFilter {

  public static final AcceptAllCodePointFilter INSTANCE = new AcceptAllCodePointFilter();

  private AcceptAllCodePointFilter() {}

  @Override
  public boolean accept(final Integer cp) {
    return true;
  }
}