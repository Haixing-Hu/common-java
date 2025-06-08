////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.filter.string;

import ltd.qubit.commons.util.filter.ChainedFilter;

/**
 * A {@link ChainedStringFilter} object is a instance of {@link StringFilter}
 * consists of a list of {@link StringFilter} objects.
 *
 * <p>A string is accepted by a ChainedStringFilter object if and only if it is
 * accepted by all filters in the chain of the ChainedStringFilter object.
 *
 * <p>Note that the order of the filters in the chain is crucial.
 *
 * @author Haixing Hu
 */
public class ChainedStringFilter extends ChainedFilter<String> implements StringFilter {

  // empty

}