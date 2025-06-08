////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.filter.string;

/**
 * A ASCII string filter with a suffix black list.
 *
 * <p>Given a string, if it is an ASCII string and has a suffix in the black
 * list of a {@link SuffixBlackListAsciiStringFilter} object, it is rejected by
 * the {@link SuffixBlackListAsciiStringFilter} object; otherwise, it is
 * accepted by the {@link SuffixBlackListAsciiStringFilter} object.
 *
 * @author Haixing Hu
 */
public class SuffixBlackListAsciiStringFilter extends SuffixListAsciiStringFilter {

  public SuffixBlackListAsciiStringFilter() {
    super(false, false);
  }

  public SuffixBlackListAsciiStringFilter(final boolean caseInsensitive) {
    super(false, caseInsensitive);
  }

}