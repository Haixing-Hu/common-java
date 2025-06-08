////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
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

/**
 * 提供集合操作的静态函数。
 *
 * @author 胡海星
 */
public class CollectionUtils {

  /**
   * 判断集合是否为空。
   *
   * @param col
   *     集合，可以为{@code null}。
   * @return
   *     若集合为{@code null}或空，则返回{@code true}，否则返回{@code false}。
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

  /**
   * 判断集合中是否存在满足条件的元素。
   *
   * @param col
   *     要判断的集合。
   * @param filter
   *     判断条件。
   * @param <T>
   *     集合中元素的类型。
   * @return
   *     如果集合中存在满足条件的元素，返回{@code true}；否则返回{@code false}。
   */
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

  /**
   * 构造一个和指定队列相同类型的空队列。
   *
   * @param queue
   *     指定的队列。
   * @param <T>
   *     队列中元素的类型。
   * @return
   *     和指定队列相同类型的空队列。
   */
  public static <T> Queue<T> constructSameTypeOfCollection(final Queue<T> queue) {
    return (Queue<T>) constructSameTypeOfCollection((Collection<T>) queue);
  }

  /**
   * 构造一个和指定列表相同类型的空列表。
   *
   * @param list
   *     指定的列表。
   * @param <T>
   *     列表中元素的类型。
   * @return
   *     和指定列表相同类型的空列表。
   */
  public static <T> List<T> constructSameTypeOfCollection(final List<T> list) {
    return (List<T>) constructSameTypeOfCollection((Collection<T>) list);
  }

  /**
   * 构造一个和指定有序集合相同类型的空有序集合。
   *
   * @param set
   *     指定的有序集合。
   * @param <T>
   *     集合中元素的类型。
   * @return
   *     和指定有序集合相同类型的空有序集合。
   */
  public static <T> SortedSet<T> constructSameTypeOfCollection(final SortedSet<T> set) {
    return (SortedSet<T>) constructSameTypeOfCollection((Collection<T>) set);
  }

  /**
   * 构造一个和指定集合相同类型的空集合。
   *
   * @param set
   *     指定的集合。
   * @param <T>
   *     集合中元素的类型。
   * @return
   *     和指定集合相同类型的空集合。
   */
  public static <T> Set<T> constructSameTypeOfCollection(final Set<T> set) {
    return (Set<T>) constructSameTypeOfCollection((Collection<T>) set);
  }

  /**
   * 构造一个和指定集合相同类型的空集合。
   *
   * @param col
   *     指定的集合。
   * @param <T>
   *     集合中元素的类型。
   * @return
   *     和指定集合相同类型的空集合。
   */
  @SuppressWarnings({"unchecked", "rawtypes"})
  public static <T> Collection<T> constructSameTypeOfCollection(final Collection<T> col) {
    Argument.requireNonNull("col", col);
    final Class<? extends Collection> cls = col.getClass();
    return (Collection<T>) constructSameTypeOfCollection(cls);
  }

  /**
   * 根据指定的集合类型构造一个该类型的空集合。
   *
   * @param colType
   *     指定的集合类型。
   * @return
   *     指定类型的空集合。
   */
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

  /**
   * 构造一个和指定映射相同类型的空映射。
   *
   * @param map
   *     指定的映射。
   * @param <K>
   *     映射中键的类型。
   * @param <V>
   *     映射中值的类型。
   * @return
   *     和指定映射相同类型的空映射。
   */
  @SuppressWarnings({"unchecked", "rawtypes"})
  public static <K, V> Map<K, V> constructSameTypeOfMap(final Map<K, V> map) {
    final Class<? extends Map> cls = map.getClass();
    return (Map<K, V>) constructSameTypeOfMap(cls);
  }

  /**
   * 根据指定的映射类型构造一个该类型的空映射。
   *
   * @param mapType
   *     指定的映射类型。
   * @return
   *     指定类型的空映射。
   */
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
   * 将对象集合转换为哈希图。
   *
   * @param <K>
   *     键的类型。
   * @param <V>
   *     值的类型。
   * @param col
   *     对象集合，可以为{@code null}。
   * @param keyGetter
   *     从对象中获取键的 getter 方法。
   * @return
   *     一个哈希图，其中键是通过{@code keyGetter}从对象中获取的，值是对象本身。
   *     一个{@code null}集合将导致一个空的哈希图。
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
   * 将对象集合转换为哈希图。
   *
   * @param <T>
   *     对象类型。
   * @param <K>
   *     键的类型。
   * @param <V>
   *     值的类型。
   * @param col
   *     对象集合，可以为{@code null}。
   * @param keyGetter
   *     用于从对象获取键的 getter 方法。
   * @param valueGetter
   *     用于从对象获取值的 getter 方法。
   * @return
   *     一个哈希图，其中键和值分别通过{@code keyGetter}和{@code valueGetter}
   *     从对象中获取。{@code null}集合将导致一个空的哈希图。
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
   * 获取一个将列表中的键映射到其在列表中位置的映射。
   *
   * @param <K>
   *     键的类型。
   * @param <V>
   *     值的类型。
   * @param list
   *     对象列表，可以为{@code null}。
   * @param keyGetter
   *     用于从对象获取键的 getter 方法。
   * @return
   *     一个哈希图，其中键是通过{@code keyGetter}从对象中获取的，值是对象在
   *     列表中的位置索引。{@code null}列表将导致一个空的哈希图。
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
   * 将列表分割成指定大小的子列表。
   *
   * @param list
   *     要分割的列表。
   * @param size
   *     每个子列表的大小。
   * @param <T>
   *     列表中元素的类型。
   * @return
   *     一个包含子列表的列表。
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
   * 对列表中的每个元素进行映射，返回一个新的列表。
   *
   * @param list
   *     要映射的列表。
   * @param mapper
   *     映射函数。
   * @param <F>
   *     原列表中元素的类型。
   * @param <T>
   *     新列表中元素的类型。
   * @return
   *     一个新的列表，其中包含了原列表中每个元素经过映射函数处理后的结果。
   */
  public static <F, T> List<T> map(@Nullable final List<F> list,
      final Function<? super F, ? extends T> mapper) {
    if (list == null) {
      return null;
    }
    return list.stream().map(mapper).collect(Collectors.toList());
  }

  /**
   * 对集合中的数据进行分组。
   *
   * @param col
   *     要去分组的数据集合。
   * @param keyGetter
   *     用于获取分组依据的键的 getter。
   * @param <K>
   *     分组依据的键的类型。
   * @param <V>
   *     集合中元素的类型。
   * @return
   *     一个Map，其键为分组依据的键，其值为具有该键的元素的列表。
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
   * 对集合中的数据进行分组。
   *
   * @param col
   *     要去分组的数据集合。
   * @param keyGetter
   *     用于获取分组依据的键的 getter。
   * @param valueGetter
   *     用于获取分组后列表中元素的 getter。
   * @param <T>
   *     输入集合中元素的类型。
   * @param <K>
   *     分组依据的键的类型。
   * @param <V>
   *     分组后列表中元素的类型。
   * @return
   *     一个Map，其键为分组依据的键，其值为具有该键的元素的列表。
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
   * 将源列表中的元素合并到目标列表中。
   *
   * @param target
   *     目标列表。
   * @param source
   *     源列表。
   * @param <T>
   *     列表中元素的类型。
   */
  public static <T> void mergeList(final List<T> target, final List<T> source) {
    for (final T e : source) {
      if (!target.contains(e)) {
        target.add(e);
      }
    }
  }

  /**
   * 获取集合的流。
   *
   * @param col
   *     集合。
   * @param <T>
   *     集合中元素的类型。
   * @return
   *     集合的流。如果集合为{@code null}，则返回一个空的流。
   */
  public static <T> Stream<T> stream(@Nullable final Collection<T> col) {
    return (col == null ? Stream.empty() : col.stream());
  }

  /**
   * 对 {@code Iterable} 的每个元素执行给定的操作，
   * 直到所有元素都被处理完毕或操作抛出异常为止。
   * 如果指定了遍历顺序，则按照遍历顺序执行操作。
   * 操作抛出的异常会被传递给调用者。
   * <p>
   * 如果该操作会产生副作用并修改底层元素源，则本方法的行为未指定，
   * 除非重写的类指定了并发修改策略。
   * <p>
   * 默认实现行为等同于：
   * <pre>{@code
   *    if (col != null) {
   *      for (T t : col) {
   *         action.accept(t);
   *      }
   *    }
   * }</pre>
   *
   * @param col
   *     要处理的集合，可以为{@code null}。
   * @param action
   *     要对每个元素执行的操作。
   * @param <T>
   *     集合中元素的类型。
   */

  public static <T> void forEach(@Nullable final Iterable<T> col,
      final Consumer<? super T> action) {
    if (col != null) {
      col.forEach(action);
    }
  }

  /**
   * 创建一个包含指定元素的数组列表。
   * <p>
   * 此函数类似于 {@link Arrays#asList} 方法，或 Java 9 引入的 {@link List#of} 方法，
   * 但本函数返回的是可修改的数组列表，而不是固定大小的列表或不可修改的列表。
   *
   * @param elements
   *     指定的元素。
   * @param <T>
   *     元素的类型。
   * @return
   *     包含指定元素的 {@link ArrayList}。
   */
  @SafeVarargs
  public static <T> ArrayList<T> listOf(final T... elements) {
    final ArrayList<T> result = new ArrayList<>();
    Collections.addAll(result, elements);
    return result;
  }

  /**
   * 对集合进行分批处理。
   *
   * @param col
   *     要处理的集合。
   * @param batchSize
   *     每批处理的数量。
   * @param action
   *     对每批数据进行处理的函数。该函数的返回值为成功处理的数量。
   * @param <T>
   *     集合中元素的类型。
   * @return
   *     成功处理的元素总数。
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