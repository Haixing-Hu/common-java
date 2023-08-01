////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.datastructure.map;

import ltd.qubit.commons.lang.ClassKey;

import com.fasterxml.jackson.databind.util.ClassUtil;

import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

/**
 * Provides the utility functions about the {@link Map}.
 *
 * @author Haixing Hu
 */
public class MapUtils {

  private MapUtils() {}

  /**
   * Inverts the map, i.e., swap the keys and values of the map.
   *
   * @param <K>
   *     the type of the key.
   * @param <V>
   *     the type of the value.
   * @param map
   *     the map to be inverted.
   * @return
   *     the inverted map, i.e., maps the value to the key.
   */
  public static <K, V> Map<V, K> invert(final Map<K, V> map) {
    return map.entrySet()
        .stream()
        .collect(Collectors.toMap(Entry::getValue, Entry::getKey));
  }

  /**
   * Inverts the map, i.e., swap the keys and values of the map.
   *
   * @param <K>
   *     the type of the key.
   * @param <V>
   *     the type of the value.
   * @param map
   *     the map to be inverted.
   * @return
   *     the inverted map, i.e., maps the value to the key, as an unmodifiable
   *     map.
   */
  public static <K, V> Map<V, K> invertAsUnmodifiable(final Map<K, V> map) {
    return map.entrySet()
        .stream()
        .collect(Collectors.toUnmodifiableMap(Entry::getValue, Entry::getKey));
  }

  public static <T> T findTypeMapping(final Class<?> type,
      final Map<ClassKey, T> classMap,
      final Map<ClassKey, T> interfaceMap) {
    T result;
    if (type.isInterface()) {
      result = interfaceMap.get(new ClassKey(type));
      if (result != null) {
        return result;
      }
    } else {
      result = classMap.get(new ClassKey(type));
      if (result != null) {
        return result;
      }
      //  Handle registration of plain `Enum` serializer
      if (ClassUtil.isEnumType(type)) {
        result = classMap.get(new ClassKey(Enum.class));
        if (result != null) {
          return result;
        }
      }
      // If not direct match, maybe super-class match?
      Class<?> cls = type.getSuperclass();
      for ( ; cls != null; cls = cls.getSuperclass()) {
        result = classMap.get(new ClassKey(cls));
        if (result != null) {
          return result;
        }
      }
    }
    // No direct match? How about super-interfaces?
    result = findInterfaceMapping(type, interfaceMap);
    if (result != null) {
      return null;
    }
    if (!type.isInterface()) {
      Class<?> cls = type;
      while ((cls = cls.getSuperclass()) != null) {
        result = findInterfaceMapping(type, interfaceMap);
        if (result != null) {
          return null;
        }
      }
    }
    return null;
  }

  private static <T> T findInterfaceMapping(final Class<?> type,
      final Map<ClassKey, T> interfaceMap) {
    for (final Class<?> iface : type.getInterfaces()) {
      T result = interfaceMap.get(new ClassKey(iface));
      if (result != null) {
        return result;
      }
      result = findInterfaceMapping(iface, interfaceMap);
      if (result != null) {
        return result;
      }
    }
    return null;
  }
}
