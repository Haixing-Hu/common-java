////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.pair;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import ltd.qubit.commons.lang.Assignable;
import ltd.qubit.commons.lang.Assignment;
import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.ObjectUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 此模型表示键值对列表。
 *
 * @author 胡海星
 */
public class KeyValuePairList extends ArrayList<KeyValuePair> implements
    Serializable, Assignable<KeyValuePairList> {

  private static final long serialVersionUID = 8257783205419834476L;

  public static KeyValuePairList of(final Object... params) {
    final KeyValuePairList result = new KeyValuePairList();
    for (int i = 0; i + 1 < params.length; i = i + 2) {
      final String key = ObjectUtils.toString(params[i]);
      final String value = ObjectUtils.toString(params[i + 1]);
      result.add(key, value);
    }
    return result;
  }

  public static KeyValuePairList of(final List<String> keys,
      final List<String> values) {
    if (keys.size() != values.size()) {
      final Logger logger = LoggerFactory.getLogger(KeyValuePairList.class);
      logger.error("The sizes of keys and values are different: keys = {}, values = {}",
          keys, values);
    }
    final KeyValuePairList result = new KeyValuePairList();
    final int n = Math.min(keys.size(), values.size());
    for (int i = 0; i < n; ++i) {
      final String key = keys.get(i);
      final String value = values.get(i);
      result.add(key, value);
    }
    return result;
  }

  public KeyValuePairList() {
    // empty
  }

  public KeyValuePairList(final KeyValuePairList other) {
    assign(other);
  }

  public KeyValuePairList(@Nullable final KeyValuePair... array) {
    if (array != null) {
      for (final KeyValuePair pair : array) {
        add(pair);
      }
    }
  }

  @Override
  public void assign(final KeyValuePairList other) {
    if (this != other) {
      clear();
      for (final KeyValuePair kv : other) {
        add(Assignment.clone(kv));
      }
    }
  }

  public KeyValuePairList clone() {
    return new KeyValuePairList(this);
  }

  public String getValue(final String key) {
    for (final KeyValuePair kv : this) {
      if (Equality.equals(kv.getKey(), key)) {
        return kv.getValue();
      }
    }
    return null;
  }

  public KeyValuePairList setValue(final String key,
      @Nullable final Object value) {
    for (final KeyValuePair kv : this) {
      if (Equality.equals(kv.getKey(), key)) {
        kv.setValue(value == null ? null : value.toString());
        return this;
      }
    }
    final KeyValuePair kv = new KeyValuePair();
    kv.setKey(key);
    kv.setValue(value == null ? null : value.toString());
    this.add(kv);
    return this;
  }

  public <E extends Enum<E>> KeyValuePairList add(final E key,
      @Nullable final Object value) {
    return add(key.name().toLowerCase(), value);
  }

  public KeyValuePairList add(final String key, @Nullable final Object value) {
    final KeyValuePair kv = new KeyValuePair();
    kv.setKey(key);
    kv.setValue(value == null ? null : value.toString());
    this.add(kv);
    return this;
  }

  public KeyValuePairList addNonNull(final String key, @Nullable final Object value) {
    if (value != null) {
      add(key, value);
    }
    return this;
  }

  public String find(final String key) {
    for (final KeyValuePair pair : this) {
      if (Equality.equals(key, pair.getKey())) {
        return pair.getValue();
      }
    }
    return null;
  }

  public KeyValuePair[] toArray() {
    return this.toArray(new KeyValuePair[0]);
  }

}
