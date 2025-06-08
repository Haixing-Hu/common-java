////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
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
 * 一个简单的包装类，使数组可迭代。
 * <p>
 * 使用此类别可将数组传递给需要{@link Iterable}参数的函数。 此实现比
 * {@link Arrays#asList(Object[])}要高效得多。
 *
 * @param <E>
 *     数组中元素的类型。
 * @see ArrayIterator
 * @author 胡海星
 */
public class IterableArray<E> implements Iterable<E> {

  private final E[] array;

  /**
   * 构造一个{@link IterableArray}对象。
   *
   * @param array
   *     要包装的数组。
   */
  public IterableArray(final E[] array) {
    this.array = requireNonNull("array", array);
  }

  /**
   * {@inheritDoc}
   */
  @NotNull
  @Override
  public Iterator<E> iterator() {
    return new ArrayIterator<>(array);
  }
}