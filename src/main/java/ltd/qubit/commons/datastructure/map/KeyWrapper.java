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

import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

/**
 * 映射中键的包装类。
 *
 * <p>这个类包装了一个键对象，并为该键提供了一个自定义的哈希函数。
 *
 * <p>JDK 原生的 HashMap 有一个问题：如果键是一个原生数组，
 * {@link Object#hashCode()} 方法的默认实现将返回数组对象的哈希码，
 * 而不是数组内容的哈希码。这将导致 HashMap 在映射中找不到键。这个类可以用来解决这个问题。
 *
 * @param <K>
 *     键的类型。
 * @author 胡海星
 */
public class KeyWrapper<K> {
  private final K key;
  private final Function<K, Integer> hashFunction;

  /**
   * 构造一个新的 {@link KeyWrapper} 对象。
   *
   * @param key
   *     要包装的键对象。
   * @param hashFunction
   *     键的哈希函数。
   */
  public KeyWrapper(final K key, final Function<K, Integer> hashFunction) {
    this.key = key;
    this.hashFunction = hashFunction;
  }

  /**
   * 获取包装的键。
   *
   * @return 包装的键。
   */
  public K getKey() {
    return key;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if ((o == null) || (getClass() != o.getClass())) {
      return false;
    }
    @SuppressWarnings("unchecked")
    final KeyWrapper<K> other = (KeyWrapper<K>) o;
    return Equality.equals(key, other.key)
        && Equality.equals(hashFunction, other.hashFunction);
  }

  @Override
  public int hashCode() {
    final int multiplier = 7;
    int result = 3;
    result = Hash.combine(result, multiplier, key);
    result = Hash.combine(result, multiplier, hashFunction);
    return result;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("key", key)
        .append("hashFunction", hashFunction)
        .toString();
  }
}