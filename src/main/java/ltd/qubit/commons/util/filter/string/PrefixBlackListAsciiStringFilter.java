////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.filter.string;

/**
 * A ASCII string filter with a prefix black list.
 *
 * <p>Given a string, if it is an ASCII string and has a prefix in the black list
 * of a {@link  PrefixBlackListAsciiStringFilter} object, it is rejected by
 * the {@link PrefixBlackListAsciiStringFilter} object; otherwise, it is
 * accepted by the {@link PrefixBlackListAsciiStringFilter} object.
 *
 * @author Haixing Hu
 */
public class PrefixBlackListAsciiStringFilter extends PrefixListAsciiStringFilter {

  public PrefixBlackListAsciiStringFilter() {
    super(false, false);
  }

  public PrefixBlackListAsciiStringFilter(final boolean caseInsensitive) {
    super(false, caseInsensitive);
  }
}
