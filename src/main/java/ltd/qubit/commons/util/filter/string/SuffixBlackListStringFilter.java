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
 * A string filter with a suffix black list.
 *
 * <p>Given a string, if it has a suffix in the black list of a
 * {@link SuffixBlackListStringFilter} object, it is rejected by the
 * {@link SuffixBlackListStringFilter} object; otherwise, it is accepted by the
 * {@link SuffixBlackListStringFilter} object.
 *
 * @author Haixing Hu
 */
public class SuffixBlackListStringFilter extends SuffixListStringFilter {

  public SuffixBlackListStringFilter() {
    super(false, false);
  }

  public SuffixBlackListStringFilter(final boolean caseInsensitive) {
    super(false, caseInsensitive);
  }
}
