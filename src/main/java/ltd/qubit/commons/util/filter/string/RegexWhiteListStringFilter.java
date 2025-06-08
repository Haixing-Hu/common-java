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
 * A string filter with a white list of regular expressions.
 *
 * <p>Given a string, if it matches any regular expression pattern in the white
 * list of a {@link RegexWhiteListStringFilter} object, it is accepted by
 * the {@link RegexWhiteListStringFilter} object; otherwise, it is rejected
 * by the {@link RegexWhiteListStringFilter} object.
 *
 * @author Haixing Hu
 */
public class RegexWhiteListStringFilter extends RegexStringFilter {

  public RegexWhiteListStringFilter() {
    super(true, false);
  }

  public RegexWhiteListStringFilter(final boolean caseInsensitive) {
    super(true, caseInsensitive);
  }

}