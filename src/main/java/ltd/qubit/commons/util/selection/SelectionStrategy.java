////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.selection;

import java.io.Serializable;
import java.util.List;

/**
 * An interface for strategies that select an item from a list.
 *
 * @author Haixing Hu
 */
public interface SelectionStrategy extends Serializable {

  /**
   * Selects an item from a list.
   *
   * @param <T>
   *     the type of items in the list.
   * @param items
   *     the list of items to select from. Guaranteed to be non-null and non-empty.
   * @return
   *     the selected item.
   */
  <T> T select(List<T> items);
}