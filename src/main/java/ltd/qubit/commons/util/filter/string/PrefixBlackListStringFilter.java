////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.filter.string;

import javax.annotation.concurrent.NotThreadSafe;

/**
 * A string filter with a prefix black list.
 *
 * <p>Given a string, if it has a prefix in the black list of a
 * {@link PrefixBlackListStringFilter} object, it is rejected by the
 * {@link PrefixBlackListStringFilter} object; otherwise, it is accepted by
 * the {@link PrefixBlackListStringFilter} object.
 *
 * @author Haixing Hu
 */
@NotThreadSafe
public class PrefixBlackListStringFilter extends PrefixListStringFilter {

  public PrefixBlackListStringFilter() {
    super(false, false);
  }

  public PrefixBlackListStringFilter(final boolean caseInsensitive) {
    super(false, caseInsensitive);
  }
}
