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
 * A thread-safe hash set implementation based on {@link ConcurrentHashMap}.
 *
 * @param <E>
 *     the type of elements in this set.
 * @author Haixing Hu
 */
public class ConcurrentHashSet<E> implements Set<E> {

  private final ConcurrentHashMap<E, Boolean> map;

  // 默认构造器，初始化 ConcurrentHashMap
  public ConcurrentHashSet() {
    map = new ConcurrentHashMap<>();
  }

  // 使用 putIfAbsent 来实现线程安全的添加
  @Override
  public boolean add(final E element) {
    return map.putIfAbsent(element, Boolean.TRUE) == null;
  }

  // 使用 remove 来删除元素
  @Override
  public boolean remove(final Object element) {
    return map.remove(element) == Boolean.TRUE;
  }

  // 判断元素是否存在
  @Override
  public boolean contains(final Object element) {
    return map.containsKey(element);
  }

  // 返回集合的大小
  @Override
  public int size() {
    return map.size();
  }

  // 判断集合是否为空
  @Override
  public boolean isEmpty() {
    return map.isEmpty();
  }

  // 清空集合
  @Override
  public void clear() {
    map.clear();
  }

  @Nonnull
  @Override
  public Iterator<E> iterator() {
    return map.keySet().iterator();
  }

  // 以下方法不需要实现，因为我们没有使用 `Map` 中的 `value` 部分
  @Override
  public boolean containsAll(final Collection<?> c) {
    for (final Object o : c) {
      if (!contains(o)) {
        return false;
      }
    }
    return true;
  }

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

  @Nonnull
  @Override
  public Object[] toArray() {
    return map.keySet().toArray();
  }

  @Nonnull
  @Override
  public <T> T[] toArray(@Nonnull final T[] array) {
    return map.keySet().toArray(array);
  }
}
