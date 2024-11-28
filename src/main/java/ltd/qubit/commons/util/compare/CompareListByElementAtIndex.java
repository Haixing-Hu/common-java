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
import java.util.List;

/**
 * A comparator used to compare two lists according to the element at the
 * specified index.
 *
 * @param <T>
 *     the type of elements of the list.
 * @author Haixing Hu
 */
public class CompareListByElementAtIndex<T extends Comparable<T>> implements
        Comparator<List<T>> {

  private final int index;

  public CompareListByElementAtIndex(final int index) {
    this.index = index;
  }

  @Override
  public int compare(final List<T> l1, final List<T> l2) {
    final T e1 = l1.get(index);
    final T e2 = l2.get(index);
    return e1.compareTo(e2);
  }
}
