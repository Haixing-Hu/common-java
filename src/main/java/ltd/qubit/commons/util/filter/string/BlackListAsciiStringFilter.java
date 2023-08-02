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
 * An ASCII string filter with a black list.
 *
 * <p>Given a string, if it is an ASCII string and is in the black list of a
 * {@link BlackListAsciiStringFilter} object, it is
 * rejected by the{@link BlackListAsciiStringFilter}  object; otherwise,
 * it is accepted by the {@link BlackListAsciiStringFilter} object.
 *
 * @author Haixing Hu
 */
public class BlackListAsciiStringFilter extends ListAsciiStringFilter {

  public BlackListAsciiStringFilter() {
    super(false, false);
  }

  public BlackListAsciiStringFilter(final boolean caseInsensitive) {
    super(false, caseInsensitive);
  }
}
