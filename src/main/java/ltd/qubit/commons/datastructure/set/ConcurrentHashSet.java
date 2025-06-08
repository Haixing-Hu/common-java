////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.datastructure.set;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Nonnull;

/**
 * 基于 {@link ConcurrentHashMap} 实现的线程安全的哈希集合。
 *
 * @param <E>
 *     集合中元素的类型。
 * @author 胡海星
 */
public class ConcurrentHashSet<E> implements Set<E> {

  private final ConcurrentHashMap<E, Boolean> map;

  /**
   * 构造一个新的 {@link ConcurrentHashSet}。
   */
  public ConcurrentHashSet() {
    map = new ConcurrentHashMap<>();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean add(final E element) {
    return map.putIfAbsent(element, Boolean.TRUE) == null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean remove(final Object element) {
    return map.remove(element) == Boolean.TRUE;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean contains(final Object element) {
    return map.containsKey(element);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int size() {
    return map.size();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isEmpty() {
    return map.isEmpty();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void clear() {
    map.clear();
  }

  /**
   * {@inheritDoc}
   */
  @Nonnull
  @Override
  public Iterator<E> iterator() {
    return map.keySet().iterator();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean containsAll(final Collection<?> c) {
    for (final Object o : c) {
      if (!contains(o)) {
        return false;
      }
    }
    return true;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean addAll(final Collection<? extends E> c) {
    boolean modified = false;
    for (final E e : c) {
      if (add(e)) {
        modified = true;
      }
    }
    return modified;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean retainAll(final Collection<?> c) {
    boolean modified = false;
    for (final Object o : map.keySet()) {
      if (!c.contains(o)) {
        remove(o);
        modified = true;
      }
    }
    return modified;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean removeAll(final Collection<?> c) {
    boolean modified = false;
    for (final Object o : c) {
      if (remove(o)) {
        modified = true;
      }
    }
    return modified;
  }

  /**
   * {@inheritDoc}
   */
  @Nonnull
  @Override
  public Object[] toArray() {
    return map.keySet().toArray();
  }

  /**
   * {@inheritDoc}
   */
  @Nonnull
  @Override
  public <T> T[] toArray(final T[] array) {
    return map.keySet().toArray(array);
  }
}