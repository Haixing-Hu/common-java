////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.datastructure;

import java.lang.reflect.InaccessibleObjectException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import ltd.qubit.commons.lang.Argument;
import ltd.qubit.commons.reflect.ReflectionException;
import ltd.qubit.commons.reflect.impl.GetterMethod;
import ltd.qubit.commons.util.filter.Filter;

import static ltd.qubit.commons.reflect.ConstructorUtils.newInstance;

public class CollectionUtils {

  /**
   * 判断集合是否为空。
   *
   * @param col
   *     集合，可以为{@code null}。
   * @return
   *     若集合为{@code null}或空，则返回{@code true}，否则返回{@code false}。
   * @author 胡海星
   */
  public static boolean isEmpty(@Nullable final Collection<?> col) {
    return col == null || col.isEmpty();
  }

  /**
   * 判断映射是否为空。
   *
   * @param map
   *     映射，可以为{@code null}。
   * @return
   *     若映射为{@code null}或空，则返回{@code true}，否则返回{@code false}。
   * @author 胡海星
   */
  public static boolean isEmpty(@Nullable final Map<?, ?> map) {
    return map == null || map.isEmpty();
  }

  /**
   * 对列表中元素去重，此操作不改变列表中元素的顺序。
   *
   * @param <T>
   *     列表中元素的类型。
   * @param list
   *     输入的列表，可以为{@code null}.
   * @return
   *     去重后的列表，若{@code list}是{@code null}则返回{@code null}。
   * @author 胡海星
   */
  public static <T> List<T> unique(@Nullable final List<T> list) {
    if (list == null) {
      return null;
    }
    final ArrayList<T> result = new ArrayList<>();
    final Set<T> exist = new HashSet<>();
    for (final T value : list) {
      if (!exist.contains(value)) {
        result.add(value);
        exist.add(value);
      }
    }
    return result;
  }

  /**
   * 查找集合中第一个满足条件的元素。
   *
   * @param <T>
   *     集合中元素的类型。
   * @param col
   *     集合，可以为{@code null}。
   * @param filter
   *     过滤器，用于过滤符合条件的元素，不能为{@code null}。
   * @return
   *     集合中第一个满足条件的元素，若集合为{@code null}或空，则返回{@code null}。
   *     若集合中没有满足条件的元素，则返回{@code null}。
   * @author 胡海星
   */
  @Nullable
  public static <T> T findFirst(@Nullable final Collection<T> col,
      @Nonnull final Filter<T> filter) {
    if (col == null || col.isEmpty()) {
      return null;
    }
    for (final T value : col) {
      if (filter.accept(value)) {
        return value;
      }
    }
    return null;
  }

  /**
   * 查找集合中所有满足条件的元素。
   *
   * @param <T>
   *     集合中元素的类型。
   * @param col
   *     集合，可以为{@code null}。
   * @param filter
   *     过滤器，用于过滤符合条件的元素，不能为{@code null}。
   * @return
   *     集合中第所有满足条件的元素，若集合为{@code null}或空，则返回{@code null}。
   *     若集合中没有满足条件的元素，则返回空列表。
   * @author 胡海星
   */
  public static <T> List<T> findAll(@Nullable final Collection<T> col,
      @Nonnull final Filter<T> filter) {
    if (col == null || col.isEmpty()) {
      return new ArrayList<>();
    }
    final List<T> result = new ArrayList<>();
    for (final T value : col) {
      if (filter.accept(value)) {
        result.add(value);
      }
    }
    return result;
  }

  public static <T> boolean containsIf(@Nullable final Collection<T> col,
      @Nonnull final Filter<T> filter) {
    if (col == null || col.isEmpty()) {
      return false;
    }
    for (final T value : col) {
      if (filter.accept(value)) {
        return true;
      }
    }
    return false;
  }

  public static <T> Queue<T> constructSameTypeOfCollection(final Queue<T> list) {
    return (Queue<T>) constructSameTypeOfCollection((Collection<T>) list);
  }

  public static <T> List<T> constructSameTypeOfCollection(final List<T> list) {
    return (List<T>) constructSameTypeOfCollection((Collection<T>) list);
  }

  public static <T> SortedSet<T> constructSameTypeOfCollection(final SortedSet<T> list) {
    return (SortedSet<T>) constructSameTypeOfCollection((Collection<T>) list);
  }

  public static <T> Set<T> constructSameTypeOfCollection(final Set<T> list) {
    return (Set<T>) constructSameTypeOfCollection((Collection<T>) list);
  }

  @SuppressWarnings({ "unchecked", "rawtypes" })
  public static <T> Collection<T> constructSameTypeOfCollection(final Collection<T> col) {
    Argument.requireNonNull("col", col);
    final Class<? extends Collection> cls = col.getClass();
    return (Collection<T>) constructSameTypeOfCollection(cls);
  }

  @SuppressWarnings("rawtypes")
  public static Collection constructSameTypeOfCollection(final Class<? extends Collection> colType) {
    Argument.requireNonNull("colType", colType);
    try {
      return newInstance(colType);
    } catch (final ReflectionException | InaccessibleObjectException e) {
      // ignore the errors
    }
    if (Queue.class.isAssignableFrom(colType)) {
      return new LinkedList<>();
    } else if (List.class.isAssignableFrom(colType)) {
      return new ArrayList<>();
    } else if (SortedSet.class.isAssignableFrom(colType)) {
      return new TreeSet<>();
    } else if (Set.class.isAssignableFrom(colType)) {
      return new HashSet<>();
    } else {
      throw new IllegalArgumentException("Unsupported collection type: " + colType
          + ". The supported collection types are: Queue, List, SortedSet, Set.");
    }
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public static <K, V> Map<K, V> constructSameTypeOfMap(final Map<K, V> map) {
    final Class<? extends Map> cls = map.getClass();
    return (Map<K, V>) constructSameTypeOfMap(cls);
  }

  @SuppressWarnings("rawtypes")
  public static Map constructSameTypeOfMap(final Class<? extends Map> mapType) {
    Argument.requireNonNull("mapType", mapType);
    if (!mapType.isInterface()) {
      try {
        return newInstance(mapType);
      } catch (final ReflectionException | InaccessibleObjectException e) {
        // ignore the errors
      }
    }
    if (SortedMap.class.isAssignableFrom(mapType)) {
      return new TreeMap<>();
    } else if (ConcurrentMap.class.isAssignableFrom(mapType)) {
      return new ConcurrentHashMap<>();
    } else {
      return new HashMap<>();
    }
  }

  /**
   * Converts a collections of objects into a hash map.
   *
   * @param <K>
   *     the type of the key.
   * @param <V>
   *     the type of the value.
   * @param col
   *     the collection of objects, which could be {@code null}.
   * @param keyGetter
   *     the getter method to get the key from the object.
   * @return
   *     a hash map, where the key is gotten from the object by the {@code keyGetter}.
   *     and the value is the object itself. A {@code null} collection will
   *     return an empty map.
   */
  public static <K, V>
  HashMap<K, V> toHashMap(@Nullable final Collection<V> col, final GetterMethod<V, K> keyGetter) {
    final HashMap<K, V> map = new HashMap<>();
    if (col != null) {
      for (final V value : col) {
        final K key = keyGetter.invoke(value);
        map.put(key, value);
      }
    }
    return map;
  }

  /**
   * Converts a collections of values into a hash map.
   *
   * @param <T>
   *     the type of the object.
   * @param <K>
   *     the type of the key.
   * @param <V>
   *     the type of the value.
   * @param col
   *     the collection of objects, which could be {@code null}.
   * @param keyGetter
   *     the getter method to get the key from the object.
   * @param valueGetter
   *     the getter method to get the value from the object.
   * @return
   *     a hash map, where the key is gotten from the object by the {@code keyGetter},
   *     and the value is gotten from the object by the {@code valueGetter}. A
   *     {@code null} collection will return an empty map.
   */
  public static <T, K, V>
  HashMap<K, V> toHashMap(@Nullable final Collection<T> col,
      final GetterMethod<T, K> keyGetter,
      final GetterMethod<T, V> valueGetter) {
    final HashMap<K, V> map = new HashMap<>();
    if (col != null) {
      for (final T o : col) {
        final K key = keyGetter.invoke(o);
        final V value = valueGetter.invoke(o);
        map.put(key, value);
      }
    }
    return map;
  }

  /**
   * Converts a list of objects to a map from its key to its position.
   *
   * @param <K>
   *     the type of the key.
   * @param <V>
   *     the type of values in the list.
   * @param list
   *     the list of objects, which could be {@code null}.
   * @param keyGetter
   *     the getter method to get the key from the object.
   * @return
   *     a hash map, where the key is gotten from the object by the {@code keyGetter}.
   *     and the value is position of the object with the key in the list,
   *     converted to the {@code long} type. The position is 0-based.
   *     A {@code null} list will return an empty map.
   */
  public static <K, V>
  HashMap<K, Long> getKeyToPositionMap(@Nullable final List<V> list,
      final GetterMethod<V, K> keyGetter) {
    final HashMap<K, Long> result = new HashMap<>();
    if (list != null) {
      final int n = list.size();
      for (int i = 0; i < n; ++i) {
        final V value = list.get(i);
        final K key = keyGetter.invoke(value);
        result.put(key, (long) i);
      }
    }
    return result;
  }

  /**
   * Splits the list into sublists of the specified size.
   *
   * @param <T>
   *     the type of the elements in the list.
   * @param list
   *     the list to be split, which could be {@code null}.
   * @param size
   *     the size of each sublist.
   * @return
   *     a list of sublists, where each sublist has the specified size.
   *     If the input list is {@code null}, then return an empty list.
   */
  public static <T> List<List<T>> splitList(@Nullable final List<T> list, final int size) {
    if (list == null || list.isEmpty()) {
      return new ArrayList<>();
    }
    final List<List<T>> result = new ArrayList<>();
    for (int i = 0; i < list.size(); i += size) {
      final int end = Math.min(i + size, list.size());
      result.add(list.subList(i, end));
    }
    return result;
  }

  /**
   * Maps the elements of the list to another list.
   *
   * @param <F>
   *     the type of the elements in the input list.
   * @param <T>
   *     the type of the elements in the output list.
   * @param list
   *     the list to be mapped, which could be {@code null}.
   * @param mapper
   *     the map function to apply to each element.
   * @return
   *     a list of the results of applying the function to the elements of the
   *     list; or {@code null} if the input list is {@code null}. The returned
   *     list is modifiable.
   */
  public static <F, T> List<T> map(@Nullable final List<F> list,
      final Function<? super F, ? extends T> mapper) {
    if (list == null) {
      return null;
    }
    return list.stream().map(mapper).collect(Collectors.toList());
  }

  /**
   * Groups a collections of objects by the specified key.
   *
   * @param <K>
   *     the type of the key.
   * @param <V>
   *     the type of the value.
   * @param col
   *     the collection of objects, which could be {@code null}.
   * @param keyGetter
   *     the getter method to get the key from the object.
   * @return
   *     a hash map, where the key is gotten from the object by the {@code keyGetter}.
   *     and the value is the list of objects with the same key. A {@code null}
   *     collection will return an empty map.
   */
  public static <K, V>
  Map<K, List<V>> groupData(@Nullable final Collection<V> col,
      final GetterMethod<V, K> keyGetter) {
    final Map<K, List<V>> map = new HashMap<>();
    if (col != null) {
      for (final V value : col) {
        final K key = keyGetter.invoke(value);
        final List<V> list = map.computeIfAbsent(key, k -> new ArrayList<>());
        list.add(value);
      }
    }
    return map;
  }

  /**
   * Groups a collections of objects by the specified key.
   *
   * @param <T>
   *     the type of the object.
   * @param <K>
   *     the type of the key.
   * @param <V>
   *     the type of the value.
   * @param col
   *     the collection of objects, which could be {@code null}.
   * @param keyGetter
   *     the getter method to get the key from the object.
   * @param valueGetter
   *     the getter method to get the value from the object.
   * @return
   *     a hash map, where the key is gotten from the object by the {@code keyGetter},
   *     and the value the list of properties gotten by the {@code valueGetter}
   *     from objects with the same key. A {@code null} collection will return
   *     an empty map.
   */
  public static <T, K, V>
  Map<K, List<V>> groupData(@Nullable final Collection<T> col,
      final GetterMethod<T, K> keyGetter,
      final GetterMethod<T, V> valueGetter) {
    final Map<K, List<V>> map = new HashMap<>();
    if (col != null) {
      for (final T o : col) {
        final K key = keyGetter.invoke(o);
        final V value = valueGetter.invoke(o);
        final List<V> list = map.computeIfAbsent(key, k -> new ArrayList<>());
        list.add(value);
      }
    }
    return map;
  }

  /**
   * Merges the source list into the target list.
   *
   * @param <T>
   *     the type of the elements in the list.
   * @param target
   *     the target list, which could be {@code null}.
   * @param source
   *     the source list, which could be {@code null}.
   */
  public static <T> void mergeList(final List<T> target, final List<T> source) {
    for (final T e : source) {
      if (!target.contains(e)) {
        target.add(e);
      }
    }
  }

  /**
   * Gets the stream of a nullable collection.
   *
   * @param <T>
   *     the type of the elements in the collection.
   * @param col
   *     the collection, which could be {@code null}.
   * @return
   *     the stream of the collection, or an empty stream if the collection is {@code null}.
   */
  public static <T> Stream<T> stream(@Nullable final Collection<T> col) {
    return (col == null ? Stream.empty() : col.stream());
  }

  /**
   * Performs the given action for each element of the {@code Iterable}
   * until all elements have been processed or the action throws an
   * exception.  Actions are performed in the order of iteration, if that
   * order is specified.  Exceptions thrown by the action are relayed to the
   * caller.
   * <p>
   * The behavior of this method is unspecified if the action performs
   * side effects that modify the underlying source of elements, unless an
   * overriding class has specified a concurrent modification policy.
   * <p>
   * The default implementation behaves as if:
   * <pre>{@code
   *    if (col != null) {
   *      for (T t : col) {
   *         action.accept(t);
   *      }
   *    }
   * }</pre>
   *
   * @param <T>
   *     The type of the element in the iterable.
   * @param col
   *     An {@code Iterable} to be iterated, which may be {@code null}.
   * @param action
   *     The action to be performed for each element
   * @throws NullPointerException
   *     if the specified action is null
   */
  public static <T> void forEach(@Nullable final Iterable<T> col,
      final Consumer<? super T> action) {
    if (col != null) {
      col.forEach(action);
    }
  }

  /**
   * Creates an array list of elements.
   * <p>
   * This function is similar to the {@link Arrays#asList} method, or the
   * {@link List#of} method introduced in Java 9, but this function returns a
   * modifiable array list instead of a fixed-size list or an unmodifiable list.
   *
   * @param <T>
   *     the type of the elements.
   * @param elements
   *     the elements to be added to the list.
   * @return
   *     an array list of the elements.
   */
  @SafeVarargs
  public static <T> ArrayList<T> listOf(final T... elements) {
    final ArrayList<T> result = new ArrayList<>();
    Collections.addAll(result, elements);
    return result;
  }

  /**
   * Process a collection of elements in batches.
   *
   * @param <T>
   *     the type of the elements.
   * @param col
   *     the collection of elements to be processed.
   * @param batchSize
   *     the size of each batch, which must be positive.
   * @param action
   *     the action to be applied to each batch of elements, which accepts a
   *     collection of elements and returns the number of elements successfully
   *     processed.
   * @return
   *     the total number of elements processed.
   */
  public static <T> int batchProcess(final Collection<T> col,
      final int batchSize, final Function<Collection<T>, Integer> action) {
    if (batchSize <= 0) {
      throw new IllegalArgumentException("The batch size must be positive.");
    }
    if (col == null || col.isEmpty()) {
      return 0;
    }
    if (col.size() <= batchSize) {
      return action.apply(col);
    }
    final List<T> list = new ArrayList<>();
    int result = 0;
    for (final T element : col) {
      if (list.size() == batchSize) {
        result += action.apply(list);
        list.clear();
      }
      list.add(element);
    }
    // note that the list is always non-empty here
    result += action.apply(list);
    return result;
  }
}