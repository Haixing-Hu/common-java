////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.datastructure.map;


import java.util.function.Function;

import ltd.qubit.commons.text.tostring.ToStringBuilder;

/**
 * Wrapper class for keys in a map.
 * <p>
 * This class wraps a key object and provides a custom hash function for the key.
 * <p>
 * The JDK's native HashMap has a problem: if the key is an native array, the
 * default implementation of the {@link Object#hashCode()} method will return the
 * hash code of the array object, not the hash code of the array's content. This
 * will cause the HashMap to fail to find the key in the map. This class can be
 * used to solve this problem.
 *
 * @param <K>
 *     the type of the key.
 * @author Haixing Hu
 */
public class KeyWrapper<K> {
  private final K key;
  private final Function<K, Integer> hashFunction;

  /**
   * Constructs a new {@link KeyWrapper} object.
   *
   * @param key
   *     the key object to be wrapped.
   * @param hashFunction
   *     the custom hash function for the key.
   */
  public KeyWrapper(final K key, final Function<K, Integer> hashFunction) {
    this.key = key;
    this.hashFunction = hashFunction;
  }

  public K getKey() {
    return key;
  }

  @Override
  public boolean equals(final Object obj) {
    if (this == obj) return true;
    if (!(obj instanceof final KeyWrapper<?> other)) return false;
    return key.equals(other.key); // 仍然用原对象的 equals 方法
  }

  @Override
  public int hashCode() {
    return hashFunction.apply(key); // 使用自定义哈希函数
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("key", key)
        .toString();
  }
}