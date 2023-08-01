////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.compare;

import ltd.qubit.commons.lang.Comparison;
import ltd.qubit.commons.util.pair.Pair;

import java.util.Comparator;

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
