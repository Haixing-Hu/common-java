////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.reflect.testbed;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class CustomMap implements Map<String, Object> {

  private String name;

  public String getName() {
    return name;
  }

  public void setName(final String name) {
    this.name = name;
  }

  @Override
  public int size() {
    return 0;
  }

  @Override
  public boolean isEmpty() {
    return false;
  }

  @Override
  public boolean containsKey(final Object key) {
    return false;
  }

  @Override
  public boolean containsValue(final Object value) {
    return false;
  }

  @Override
  public Object get(final Object key) {
    return null;
  }

  @Override
  public Object put(final String key, final Object value) {
    return null;
  }

  @Override
  public Object remove(final Object key) {
    return null;
  }

  @Override
  public void putAll(final Map<? extends String, ?> m) {

  }

  @Override
  public void clear() {

  }

  @Override
  public Set<String> keySet() {
    return null;
  }

  @Override
  public Collection<Object> values() {
    return null;
  }

  @Override
  public Set<Entry<String, Object>> entrySet() {
    return null;
  }
}
