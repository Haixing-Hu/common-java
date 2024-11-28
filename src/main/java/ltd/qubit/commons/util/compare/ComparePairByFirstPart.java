////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.compare;

import java.util.Comparator;

import ltd.qubit.commons.lang.Comparison;
import ltd.qubit.commons.util.pair.Pair;

/**
 * A comparator used to compare pairs according to their first part.
 *
 * @author Haixing Hu
 */
public class ComparePairByFirstPart<F, S> implements Comparator<Pair<F, S>> {

  @Override
  public int compare(final Pair<F, S> o1, final Pair<F, S> o2) {
    return Comparison.compare(o1.first, o2.first);
  }
}
