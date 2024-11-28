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
 * A ASCII string filter with a prefix white list.
 *
 * <p>Given a string, if it is an ASCII string and has a prefix in the white
 * list of a {@link PrefixWhiteListAsciiStringFilter} object, it is accepted by
 * the {@link PrefixWhiteListAsciiStringFilter} object; otherwise, it is
 * rejected by the {@link PrefixWhiteListAsciiStringFilter} object.
 *
 * @author Haixing Hu
 */
public class PrefixWhiteListAsciiStringFilter extends PrefixListAsciiStringFilter {

  public PrefixWhiteListAsciiStringFilter() {
    super(true, false);
  }

  public PrefixWhiteListAsciiStringFilter(final boolean caseInsensitive) {
    super(true, caseInsensitive);
  }
}
