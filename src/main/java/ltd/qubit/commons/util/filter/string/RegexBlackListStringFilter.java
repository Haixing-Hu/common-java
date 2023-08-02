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
 * A string filter with a black list of regular expressions.
 *
 * <p>Given a string, if it matches any regular expression pattern in the black
 * list of a {@link RegexBlackListStringFilter} object, it is rejected by
 * the {@link RegexBlackListStringFilter} object; otherwise, it is accepted
 * by the {@link RegexBlackListStringFilter} object.
 *
 * @author Haixing Hu
 */
public class RegexBlackListStringFilter extends RegexStringFilter {

  public RegexBlackListStringFilter() {
    super(false, false);
  }

  public RegexBlackListStringFilter(final boolean caseInsensitive) {
    super(false, caseInsensitive);
  }

}
