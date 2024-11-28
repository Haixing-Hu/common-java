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
 * A string filter with a suffix white list.
 *
 * <p>Given a string, if it has a suffix in the white list of a
 * {@link SuffixBlackListStringFilter} object, it is accepted by the
 * {@link SuffixBlackListStringFilter} object; otherwise, it is rejected by the
 * {@link SuffixBlackListStringFilter} object.
 *
 * @author Haixing Hu
 */
public class SuffixWhiteListStringFilter extends SuffixListStringFilter {

  public SuffixWhiteListStringFilter() {
    super(true, false);
  }

  public SuffixWhiteListStringFilter(final boolean caseInsensitive) {
    super(true, caseInsensitive);
  }
}
