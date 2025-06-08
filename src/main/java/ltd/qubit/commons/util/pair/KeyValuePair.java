////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.pair;

import java.io.Serial;
import java.io.Serializable;

import javax.annotation.Nullable;

import ltd.qubit.commons.lang.Assignable;
import ltd.qubit.commons.lang.CloneableEx;
import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.lang.ObjectUtils;
import ltd.qubit.commons.reflect.impl.GetterMethod;

import static ltd.qubit.commons.reflect.FieldUtils.getFieldName;

/**
 * 此模型表示简单的字符串键值对。
 *
 * @author 胡海星
 */
public class KeyValuePair implements Serializable, CloneableEx<KeyValuePair>,
        Assignable<KeyValuePair> {

  @Serial
  private static final long serialVersionUID = -509702675803272160L;

  /**
   * 主键。
   */
  private String key;

  /**
   * 取值。
   */
  @Nullable
  private String value;

  public KeyValuePair() {}

  public KeyValuePair(final KeyValuePair other) {
    assign(other);
  }

  public KeyValuePair(final String key) {
    this.key = key;
    value = null;
  }

  public KeyValuePair(final String key, final Object value) {
    this.key = key;
    this.value = ObjectUtils.toString(value, null);
  }

  public <E extends Enum<E>> KeyValuePair(final E key, final Object value) {
    this.key = key.name().toLowerCase();
    this.value = ObjectUtils.toString(value, null);
  }

  public <T, K> KeyValuePair(final Class<T> cls, final GetterMethod<T, K> keyGetter, final K value) {
    this.key = getFieldName(cls, keyGetter);
    this.value = ObjectUtils.toString(value, null);
  }

  @Override
  public void assign(final KeyValuePair other) {
    key = other.key;
    value = other.value;
  }

  @Override
  public KeyValuePair cloneEx() {
    return new KeyValuePair(this);
  }

  public final String getKey() {
    return key;
  }

  public final void setKey(final String key) {
    this.key = key;
  }

  @Nullable
  public final String getValue() {
    return value;
  }

  public final void setValue(@Nullable final String value) {
    this.value = value;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if ((o == null) || (getClass() != o.getClass())) {
      return false;
    }
    final KeyValuePair other = (KeyValuePair) o;
    return Equality.equals(key, other.key)
        && Equality.equals(value, other.value);
  }

  @Override
  public int hashCode() {
    final int multiplier = 7;
    int result = 3;
    result = Hash.combine(result, multiplier, key);
    result = Hash.combine(result, multiplier, value);
    return result;
  }

  @Override
  public String toString() {
    return  key + " = " + value;
  }
}