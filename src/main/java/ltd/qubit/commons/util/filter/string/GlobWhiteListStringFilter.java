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
 * A string filter with a white list of globs.
 *
 * <p>Given a string, if it matches any glob pattern in the white list of a
 * {@link GlobBlackListStringFilter} object, it is accepted by the
 * {@link GlobBlackListStringFilter} object; otherwise, it is rejected
 * by the {@link GlobBlackListStringFilter} object.
 *
 * @author Haixing Hu
 */
public class GlobWhiteListStringFilter extends GlobStringFilter {

  public GlobWhiteListStringFilter() {
    super(true, false);
  }

  public GlobWhiteListStringFilter(final boolean caseInsensitive) {
    super(true, caseInsensitive);
  }

}
