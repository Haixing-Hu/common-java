////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.filter.string;

/**
 * An ASCII string filter with a white list.
 *
 * <p>Given a string, if it is an ASCII string and is in the white list of a
 * {@link WhiteListAsciiStringFilter} object, it is accepted by the
 * {@link WhiteListAsciiStringFilter} object; otherwise, it is rejected by the
 * {@link WhiteListAsciiStringFilter} object.
 *
 * @author Haixing Hu
 */
public class WhiteListAsciiStringFilter extends ListAsciiStringFilter {

  public WhiteListAsciiStringFilter() {
    super(true, false);
  }

  public WhiteListAsciiStringFilter(final boolean caseInsensitive) {
    super(true, caseInsensitive);
  }
}
