////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.filter.string;

import javax.annotation.concurrent.NotThreadSafe;

/**
 * A string filter with a black list.
 *
 * <p>Given a string, if it is in the black list of a
 * {@link BlackListStringFilter} object, it is rejected by the
 * {@link BlackListStringFilter} object; otherwise,
 * it is accepted by the {@link BlackListStringFilter} object.
 *
 * @author Haixing Hu
 */
@NotThreadSafe
public class BlackListStringFilter extends ListStringFilter {

  public BlackListStringFilter() {
    super(false, false);
  }

  public BlackListStringFilter(final boolean caseInsensitive) {
    super(false, caseInsensitive);
  }

}