////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.datastructure;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * 数组的迭代器。
 *
 * @param <E>
 *     数组元素的类型
 * @author 胡海星。
 * @see IterableArray
 */
public class ArrayIterator<E> implements Iterator<E> {

  /**
   * 数组。
   */
  private final E[] array;

  /**
   * 索引。
   */
  private int index;

  /**
   * 构造一个数组的迭代器。
   *
   * @param array
   *     数组。
   */
  public ArrayIterator(final E[] array) {
    this.array = array;
    this.index = 0;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean hasNext() {
    return (index < array.length);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public E next() {
    if (!hasNext()) {
      throw new NoSuchElementException();
    }
    return array[index++];
  }
}