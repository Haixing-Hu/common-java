////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.datastructure;

import java.util.Arrays;
import java.util.Iterator;

import jakarta.validation.constraints.NotNull;

import static ltd.qubit.commons.lang.Argument.requireNonNull;

/**
 * A simple wrapper class to make an array iterable.
 * <p>
 * Use this class to pass an array to a function that requires an {@link Iterable}
 * argument. This implementation is much efficient than the {@link Arrays#asList(Object[])}.
 *
 * @param <E>
 *     the type of the elements in the array.
 * @see ArrayIterator
 * @author Haixing Hu
 */
public class IterableArray<E> implements Iterable<E> {

  private final E[] array;

  /**
   * Constructs an {@link IterableArray} object.
   *
   * @param array
   *     the array to be wrapped.
   */
  public IterableArray(final E[] array) {
    this.array = requireNonNull("array", array);
  }

  @NotNull
  @Override
  public Iterator<E> iterator() {
    return new ArrayIterator<>(array);
  }
}
