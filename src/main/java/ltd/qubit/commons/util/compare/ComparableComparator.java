////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.compare;

import java.util.Comparator;

/**
 * The default comparator for comparable objects.
 *
 * @author Haixing Hu
 */
public class ComparableComparator<T extends Comparable<T>> implements Comparator<T> {
  @Override
  public int compare(final T o1, final T o2) {
    return o1.compareTo(o2);
  }
}
