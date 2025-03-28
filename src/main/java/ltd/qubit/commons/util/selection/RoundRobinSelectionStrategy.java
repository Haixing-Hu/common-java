////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.selection;

import java.io.Serial;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.concurrent.ThreadSafe;

import ltd.qubit.commons.text.tostring.ToStringBuilder;

/**
 * A selection strategy that selects items in a round-robin fashion.
 * <p>
 * This strategy cycles through the list of items, returning each item in turn.
 * When it reaches the end of the list, it starts over from the beginning.
 *
 * @author Haixing Hu
 */
@ThreadSafe
public class RoundRobinSelectionStrategy implements SelectionStrategy {

  @Serial
  private static final long serialVersionUID = -1068513295753033674L;

  private final AtomicInteger counter = new AtomicInteger(0);

  @Override
  public <T> T select(final List<T> items) {
    if (items == null || items.isEmpty()) {
      throw new IllegalArgumentException("The items list cannot be null or empty");
    }
    final int size = items.size();
    final int index = Math.abs(counter.getAndIncrement() % size);
    return items.get(index);
  }

  /**
   * Resets the counter used for round-robin selection.
   *
   * <p>After calling this method, the next call to {@link #select(List)} will
   * select the first item in the list.
   */
  public void reset() {
    counter.set(0);
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("counter", counter.get())
        .toString();
  }
}