////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.datastructure;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * An iterator for an array.
 *
 * @param <E>
 *       the type of the elements of the array.
 * @see IterableArray
 * @author Haixing Hu
 */
public class ArrayIterator<E> implements Iterator<E> {

  private final E[] array;
  private int index;

  public ArrayIterator(final E[] array) {
    this.array = array;
    this.index = 0;
  }

  @Override
  public boolean hasNext() {
    return (index < array.length);
  }

  @Override
  public E next() {
    if (!hasNext()) {
      throw new NoSuchElementException();
    }
    return array[index++];
  }
}
