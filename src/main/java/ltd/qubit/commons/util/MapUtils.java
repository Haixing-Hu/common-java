////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Provides utility functions for {@link Map} objects.
 *
 * @author Haixing Hu
 */
public class MapUtils {

  private static final Object[][] EMPTY_ARRAY = new Object[0][0];

  public static <K, V> Object[][] toArray(final Map<K, V> map) {
    if (map.isEmpty()) {
      return EMPTY_ARRAY;
    }
    final Object[][] result = new Object[map.size()][2];
    int i = 0;
    for (final Map.Entry<K, V> entry : map.entrySet()) {
      result[i][0] = entry.getKey();
      result[i][1] = entry.getValue();
      ++i;
    }
    return result;
  }

  @SuppressWarnings("unchecked")
  public static <K, V> Map<K, V> fromArray(final Object[][] array) {
    final Map<K, V> result = new HashMap<K, V>();
    for (int i = 0; i < array.length; ++i) {
      final K key = (K) array[i][0];
      final V value = (V) array[i][1];
      result.put(key, value);
    }
    return result;
  }
}
