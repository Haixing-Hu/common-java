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
import java.util.concurrent.ThreadLocalRandom;

import javax.annotation.concurrent.ThreadSafe;

import ltd.qubit.commons.text.tostring.ToStringBuilder;

/**
 * A selection strategy that randomly selects items from a list.
 * <p>
 * This strategy uses a pseudo-random number generator to select an item from
 * the list at random.
 *
 * @author Haixing Hu
 */
@ThreadSafe
public class RandomSelectionStrategy implements SelectionStrategy {

  @Serial
  private static final long serialVersionUID = -2564702240040969857L;

  @Override
  public <T> T select(final List<T> items) {
    if (items == null || items.isEmpty()) {
      throw new IllegalArgumentException("The items list cannot be null or empty");
    }
    final int size = items.size();
    // ThreadLocalRandom is faster than Random for multi-threaded
    final int index = ThreadLocalRandom.current().nextInt(size);
    return items.get(index);
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this).toString();
  }
}