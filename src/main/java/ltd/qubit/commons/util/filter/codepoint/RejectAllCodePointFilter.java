////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.filter.codepoint;

/**
 * An empty code point filter that rejects all code points.
 *
 * @author Haixing Hu
 */
public class RejectAllCodePointFilter implements CodePointFilter {

  public static final RejectAllCodePointFilter INSTANCE = new RejectAllCodePointFilter();

  private RejectAllCodePointFilter() {}

  @Override
  public boolean accept(final Integer cp) {
    return false;
  }
}
