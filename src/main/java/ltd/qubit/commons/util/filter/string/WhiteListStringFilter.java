////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.filter.string;

import javax.annotation.concurrent.NotThreadSafe;

/**
 * A string filter with a white list.
 *
 * <p>Given a string, if it is in the white list of a
 * {@link WhiteListStringFilter} object, it is accepted by the
 * {@link WhiteListStringFilter} object; otherwise, it is rejected by the
 * {@link WhiteListStringFilter} object.
 *
 * @author Haixing Hu
 */
@NotThreadSafe
public class WhiteListStringFilter extends ListStringFilter {

  public WhiteListStringFilter() {
    super(true, false);
  }

  public WhiteListStringFilter(final boolean caseInsensitive) {
    super(true, caseInsensitive);
  }
}
