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
 * A string filter with a black list of globs.
 *
 * <p>Given a string, if it matches any glob pattern in the black list of a
 * {@link GlobBlackListStringFilter} object, it is rejected by the
 * {@link GlobBlackListStringFilter} object; otherwise, it is accepted
 * by the {@link GlobBlackListStringFilter} object.
 *
 * @author Haixing Hu
 */
public class GlobBlackListStringFilter extends GlobStringFilter {

  public GlobBlackListStringFilter() {
    super(false, false);
  }

  public GlobBlackListStringFilter(final boolean caseInsensitive) {
    super(false, caseInsensitive);
  }

}