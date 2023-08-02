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
 * A string filter with a prefix white list.
 *
 * <p>Given a string, if it has a prefix in the white list of a
 * {@link PrefixWhiteListStringFilter} object, it is accepted by the
 * {@link PrefixWhiteListStringFilter} object; otherwise, it is rejected by
 * the {@link PrefixWhiteListStringFilter} object.
 *
 * @author Haixing Hu
 */
@NotThreadSafe
public class PrefixWhiteListStringFilter extends PrefixListStringFilter {

  public PrefixWhiteListStringFilter() {
    super(true, false);
  }

  public PrefixWhiteListStringFilter(final boolean caseInsensitive) {
    super(true, caseInsensitive);
  }
}
