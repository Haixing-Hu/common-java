////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.datastructure;

import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * 惰性迭代器。
 * <p>
 * 此类提供了一种创建可迭代对象的方法，该对象可延迟生成其元素。 元素由提供者生成。
 * 每次迭代器请求下一个元素时，都会调用提供者。
 *
 * @param <T>
 *     此可迭代对象的元素类型。
 * @author 胡海星
 */
public class LazyIterable<T> implements Iterable<T> {
  private final Supplier<T> supplier;
  private final int size;

  /**
   * 构造一个新的惰性迭代器。
   *
   * @param size
   *     要迭代的元素数。
   * @param supplier
   *     生成可迭代元素的提供者。
   */
  public LazyIterable(final int size, final Supplier<T> supplier) {
    this.size = size;
    this.supplier = supplier;
  }

  /**
   * 构造一个新的惰性迭代器。
   *
   * @param <E>
   *     源元素的类型。
   * @param col
   *     源元素的集合，这些源元素将被映射到要迭代的元素。
   * @param mapper
   *     将源元素映射到要迭代的元素的映射器函子。
   */
  public <E> LazyIterable(final Collection<E> col, final Function<E, T> mapper) {
    this.size = col.size();
    final Iterator<E> iter = col.iterator();
    this.supplier = () -> mapper.apply(iter.next());
  }

  /**
   * 构造一个新的惰性迭代器。
   *
   * @param <E>
   *     源元素的类型。
   * @param array
   *     源元素的数组，这些源元素将被映射到要迭代的元素。
   * @param mapper
   *     将源元素映射到要迭代的元素的映射器函子。
   */
  public <E> LazyIterable(final E[] array, final Function<E, T> mapper) {
    this.size = array.length;
    final Iterator<E> iter = new ArrayIterator<>(array);
    this.supplier = () -> mapper.apply(iter.next());
  }

  @Override
  public Iterator<T> iterator() {
    return new LazyIterator();
  }

  private class LazyIterator implements Iterator<T> {
    private int index = 0;

    @Override
    public boolean hasNext() {
      return index < size;
    }

    @Override
    public T next() {
      if (index >= size) {
        throw new NoSuchElementException();
      }
      ++index;
      return supplier.get();
    }
  }
}