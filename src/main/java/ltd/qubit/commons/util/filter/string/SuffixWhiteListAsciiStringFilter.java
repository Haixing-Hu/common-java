////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.filter.string;

/**
 * A ASCII string filter with a suffix white list.
 *
 * <p>Given a string, if it is an ASCII string and has a suffix in the white list
 * of a {@link SuffixBlackListAsciiStringFilter} object, it is accepted by
 * the {@link SuffixBlackListAsciiStringFilter} object; otherwise, it is
 * rejected by the {@link SuffixBlackListAsciiStringFilter} object.
 *
 * @author Haixing Hu
 */
public class SuffixWhiteListAsciiStringFilter extends SuffixListAsciiStringFilter {

  public SuffixWhiteListAsciiStringFilter() {
    super(true, false);
  }

  public SuffixWhiteListAsciiStringFilter(final boolean caseInsensitive) {
    super(true, caseInsensitive);
  }

}
