////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.datastructure.map;

import ltd.qubit.commons.lang.Equality;

import java.util.AbstractCollection;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import javax.annotation.concurrent.NotThreadSafe;

/**
 * An implementation of map using an abstract list to store the keys and
 * values.
 *
 * @author Haixing Hu
 */
@NotThreadSafe
public abstract class AbstractListMap<KEY, VALUE> implements Map<KEY, VALUE> {

  protected static class Entry<KEY, VALUE> implements Map.Entry<KEY, VALUE> {

    KEY key;
    VALUE value;

    public Entry(final KEY key, final VALUE value) {
      this.key = key;
      this.value = value;
    }

    @Override
    public KEY getKey() {
      return key;
    }

    @Override
    public VALUE getValue() {
      return value;
    }

    @Override
    public VALUE setValue(final VALUE value) {
      final VALUE oldValue = this.value;
      this.value = value;
      return oldValue;
    }

    @Override
    public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((key == null) ? 0 : key.hashCode());
      result = prime * result + ((value == null) ? 0 : value.hashCode());
      return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean equals(final Object obj) {
      if (this == obj) {
        return true;
      }
      if (obj == null) {
        return false;
      }
      if (getClass() != obj.getClass()) {
        return false;
      }
      final Entry<KEY, VALUE> other = (Entry<KEY, VALUE>) obj;
      if (key == null) {
        if (other.key != null) {
          return false;
        }
      } else if (!key.equals(other.key)) {
        return false;
      }
      if (value == null) {
        return other.value == null;
      } else {
        return value.equals(other.value);
      }
    }
  }

  protected List<Entry<KEY, VALUE>> list;

  protected abstract List<Entry<KEY, VALUE>> makeList();

  protected AbstractListMap() {
    list = null;
  }

  @Override
  public boolean isEmpty() {
    return (list == null) || list.isEmpty();
  }

  @Override
  public int size() {
    return (list == null ? 0 : list.size());
  }

  @Override
  public void clear() {
    if (list != null) {
      list.clear();
    }
  }

  @Override
  public Set<Map.Entry<KEY, VALUE>> entrySet() {
    if (list == null) {
      return Collections.emptySet();
    } else {
      return new EntrySet();
    }
  }

  @Override
  public Set<KEY> keySet() {
    if (list == null) {
      return Collections.emptySet();
    } else {
      return new KeySet();
    }
  }

  @Override
  public Collection<VALUE> values() {
    if (list == null) {
      return Collections.emptySet();
    } else {
      return new ValueCollection();
    }
  }

  private ListIterator<Entry<KEY, VALUE>> getEntryIterator(final Object key) {
    if (list == null) {
      return null;
    }
    if (key == null) {
      for (final ListIterator<Entry<KEY, VALUE>> it = list.listIterator();
          it.hasNext(); ) {
        final Entry<KEY, VALUE> entry = it.next();
        if (entry.key == null) {
          return it;
        }
      }
    } else {
      for (final ListIterator<Entry<KEY, VALUE>> it = list.listIterator();
          it.hasNext(); ) {
        final Entry<KEY, VALUE> entry = it.next();
        if (key.equals(entry.key)) {
          return it;
        }
      }
    }
    return null;
  }

  @Override
  public boolean containsKey(final Object key) {
    return getEntryIterator(key) != null;
  }

  @Override
  public boolean containsValue(final Object value) {
    if (list == null) {
      return false;
    }
    if (value == null) {
      for (final Entry<KEY, VALUE> entry : list) {
        if (entry.value == null) {
          return true;
        }
      }
    } else {
      for (final Entry<KEY, VALUE> entry : list) {
        if (value.equals(entry.value)) {
          return true;
        }
      }
    }
    return false;
  }

  @Override
  public VALUE get(final Object key) {
    final ListIterator<Entry<KEY, VALUE>> it = getEntryIterator(key);
    return (it == null ? null : it.previous().value);
  }

  @Override
  public VALUE put(final KEY key, final VALUE value) {
    if (list == null) {
      list = makeList();
      list.add(new Entry<>(key, value));
      return null;
    } else {
      final ListIterator<Entry<KEY, VALUE>> it = getEntryIterator(key);
      if (it == null) {
        list.add(new Entry<>(key, value));
        return null;
      } else {
        final Entry<KEY, VALUE> entry = it.previous();
        final VALUE oldValue = entry.value;
        entry.value = value;
        return oldValue;
      }
    }
  }

  @Override
  public void putAll(final Map<? extends KEY, ? extends VALUE> map) {
    if ((map == this) || map.isEmpty()) {
      return;
    }
    if (list == null) {
      list = makeList();
      for (final Map.Entry<? extends KEY, ? extends VALUE> entry : map
          .entrySet()) {
        list.add(new Entry<>(entry.getKey(), entry.getValue()));
      }
    } else {
      for (final Map.Entry<? extends KEY, ? extends VALUE> entry : map
          .entrySet()) {
        final KEY key = entry.getKey();
        final VALUE value = entry.getValue();
        final ListIterator<Entry<KEY, VALUE>> it = getEntryIterator(key);
        if (it == null) {
          list.add(new Entry<>(key, value));
        } else {
          it.previous().value = value;
        }
      }
    }
  }

  @Override
  public VALUE remove(final Object key) {
    if (list == null) {
      return null;
    }
    final ListIterator<Entry<KEY, VALUE>> it = getEntryIterator(key);
    if (it == null) {
      return null;
    }
    final Entry<KEY, VALUE> entry = it.previous();
    final VALUE oldValue = entry.value;
    it.remove();
    return oldValue;
  }

  private final class EntrySet extends AbstractSet<Map.Entry<KEY, VALUE>> {

    @Override
    public Iterator<Map.Entry<KEY, VALUE>> iterator() {
      return new EntryIterator();
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean contains(final Object o) {
      if (!(o instanceof Map.Entry)) {
        return false;
      }
      final Map.Entry<KEY, VALUE> e = (Map.Entry<KEY, VALUE>) o;
      final ListIterator<Entry<KEY, VALUE>> it = getEntryIterator(e.getKey());
      if (it == null) {
        return false;
      } else {
        final Entry<KEY, VALUE> candidate = it.previous();
        if (candidate.value == null) {
          return e.getValue() == null;
        } else {
          return candidate.value.equals(e.getValue());
        }
      }
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean remove(final Object o) {
      if (list == null) {
        return false;
      }
      if (!(o instanceof Map.Entry)) {
        return false;
      }
      final Map.Entry<KEY, VALUE> e = (Map.Entry<KEY, VALUE>) o;
      final ListIterator<Entry<KEY, VALUE>> it = getEntryIterator(e.getKey());
      if (it == null) {
        return false;
      } else {
        final Entry<KEY, VALUE> candidate = it.previous();
        if (Equality.equals(candidate.value, e.getValue())) {
          it.remove();
          return true;
        } else {
          return false;
        }
      }
    }

    @Override
    public int size() {
      return (list == null ? 0 : list.size());
    }

    @Override
    public void clear() {
      if (list != null) {
        list.clear();
      }
    }
  }

  private final class EntryIterator implements Iterator<Map.Entry<KEY, VALUE>> {

    private final ListIterator<Entry<KEY, VALUE>> iter;

    public EntryIterator() {
      if (list == null) {
        final List<Entry<KEY, VALUE>> emptyList = Collections.emptyList();
        iter = emptyList.listIterator();
      } else {
        iter = list.listIterator();
      }
    }

    @Override
    public boolean hasNext() {
      return iter.hasNext();
    }

    @Override
    public Map.Entry<KEY, VALUE> next() {
      return iter.next();
    }

    @Override
    public void remove() {
      iter.remove();
    }
  }

  private final class KeySet extends AbstractSet<KEY> {

    @Override
    public Iterator<KEY> iterator() {
      return new KeyIterator();
    }

    @Override
    public boolean contains(final Object key) {
      return containsKey(key);
    }

    @Override
    public boolean remove(final Object key) {
      return AbstractListMap.this.remove(key) != null;
    }

    @Override
    public int size() {
      return (list == null ? 0 : list.size());
    }

    @Override
    public void clear() {
      if (list != null) {
        list.clear();
      }
    }
  }

  private final class KeyIterator implements Iterator<KEY> {

    private final ListIterator<Entry<KEY, VALUE>> iter;

    public KeyIterator() {
      if (list == null) {
        final List<Entry<KEY, VALUE>> emptyList = Collections.emptyList();
        iter = emptyList.listIterator();
      } else {
        iter = list.listIterator();
      }
    }

    @Override
    public boolean hasNext() {
      return iter.hasNext();
    }

    @Override
    public KEY next() {
      return iter.next().key;
    }

    @Override
    public void remove() {
      iter.remove();
    }
  }

  private final class ValueCollection extends AbstractCollection<VALUE> {

    @Override
    public Iterator<VALUE> iterator() {
      return new ValueIterator();
    }

    @Override
    public boolean isEmpty() {
      return (list == null) || list.isEmpty();
    }

    @Override
    public int size() {
      return (list == null ? 0 : list.size());
    }

    @Override
    public void clear() {
      if (list != null) {
        list.clear();
      }
    }

    @Override
    public boolean contains(final Object o) {
      return containsValue(o);
    }

  }

  private final class ValueIterator implements Iterator<VALUE> {

    private final ListIterator<Entry<KEY, VALUE>> iter;

    public ValueIterator() {
      if (list == null) {
        final List<Entry<KEY, VALUE>> emptyList = Collections.emptyList();
        iter = emptyList.listIterator();
      } else {
        iter = list.listIterator();
      }
    }

    @Override
    public boolean hasNext() {
      return iter.hasNext();
    }

    @Override
    public VALUE next() {
      return iter.next().value;
    }

    @Override
    public void remove() {
      iter.remove();
    }
  }

}
