////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.filter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static ltd.qubit.commons.lang.Argument.requireNonNull;

/**
 * A {@link ChainedFilter} is an implementation of {@link Filter} consists of a
 * list of {@link Filter} objects.
 *
 * <p>An object is accepted by a {@link ChainedFilter} object if and only if it
 * is accepted by all filters in the internal chain of that {@link ChainedFilter}
 * object.
 *
 * <p>Note that the order of the filters in the chain is crucial.
 *
 * @author Haixing Hu
 */
public class ChainedFilter<T> extends AbstractFilter<T> {

  protected List<Filter<T>> filterChain;

  public ChainedFilter() {
    filterChain = new ArrayList<>();
  }

  public final List<Filter<T>> getFilterChain() {
    return filterChain;
  }

  public final ChainedFilter<T> setFilterChain(final List<Filter<T>> filterChain) {
    this.filterChain =  requireNonNull("filterChain", filterChain);
    return this;
  }

  public final int size() {
    return filterChain.size();
  }

  public final boolean isEmpty() {
    return filterChain.isEmpty();
  }

  public final void clear() {
    filterChain.clear();
  }

  public final ChainedFilter<T> addFilter(final Filter<T> filter) {
    filterChain.add(requireNonNull("filter", filter));
    return this;
  }

  public final ChainedFilter<T> addFilters(final Collection<Filter<T>> filters) {
    requireNonNull("filters", filters);
    for (final Filter<T> filter : filters) {
      filterChain.add(requireNonNull("filter", filter));
    }
    return this;
  }

  @SafeVarargs
  public final ChainedFilter<T> addFilters(final Filter<T>... filters) {
    requireNonNull("filters", filters);
    for (final Filter<T> filter : filters) {
      filterChain.add(requireNonNull("filter", filter));
    }
    return this;
  }

  @Override
  public final boolean accept(final T t) {
    for (final Filter<T> filter : filterChain) {
      if ((filter != null) && (! filter.accept(t))) {
        return false;
      }
    }
    return true;
  }

}
