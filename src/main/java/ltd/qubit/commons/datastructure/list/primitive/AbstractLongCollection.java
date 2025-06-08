////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.datastructure.list.primitive;

import java.io.Serial;

import javax.annotation.Nullable;

import ltd.qubit.commons.lang.Comparison;
import ltd.qubit.commons.lang.Hash;

/**
 * {@link LongCollection} 的抽象基类。
 *
 * <p>只读子类必须覆盖 {@link #iterator()} 和 {@link #size()}。
 * 可变子类还应覆盖 {@link #add(long)} 和 {@link LongIterator#remove()}。
 * 所有其他方法都至少有一些派生自这些方法的基本实现。子类可以选择覆盖这些方法以提供更有效的实现。
 *
 * @author 胡海星
 */
public abstract class AbstractLongCollection implements LongCollection {

  @Serial
  private static final long serialVersionUID = -7800640379547955378L;

  @Override
  public abstract LongIterator iterator();

  @Override
  public abstract int size();

  @Override
  public abstract boolean add(final long element);

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean addAll(final LongCollection c) {
    boolean modified = false;
    for (final LongIterator iter = c.iterator(); iter.hasNext();) {
      modified |= add(iter.next());
    }
    return modified;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void clear() {
    for (final LongIterator iter = iterator(); iter.hasNext();) {
      iter.next();
      iter.remove();
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean contains(final long element) {
    for (final LongIterator iter = iterator(); iter.hasNext();) {
      if (iter.next() == element) {
        return true;
      }
    }
    return false;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean containsAll(final LongCollection c) {
    for (final LongIterator iter = c.iterator(); iter.hasNext();) {
      if (! contains(iter.next())) {
        return false;
      }
    }
    return true;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isEmpty() {
    return (0 == size());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean remove(final long element) {
    for (final LongIterator iter = iterator(); iter.hasNext();) {
      if (iter.next() == element) {
        iter.remove();
        return true;
      }
    }
    return false;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean removeAll(final LongCollection c) {
    boolean modified = false;
    for (final LongIterator iter = c.iterator(); iter.hasNext();) {
      modified |= remove(iter.next());
    }
    return modified;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean retainAll(final LongCollection c) {
    boolean modified = false;
    for (final LongIterator iter = iterator(); iter.hasNext();) {
      if (! c.contains(iter.next())) {
        iter.remove();
        modified = true;
      }
    }
    return modified;
  }

  @Override
  public long[] toArray() {
    final long[] array = new long[size()];
    int i = 0;
    for (final LongIterator iter = iterator(); iter.hasNext();) {
      array[i] = iter.next();
      i++;
    }
    return array;
  }

  @Override
  public long[] toArray(final long[] a) {
    if (a.length < size()) {
      return toArray();
    } else {
      int i = 0;
      for (final LongIterator iter = iterator(); iter.hasNext();) {
        a[i] = iter.next();
        i++;
      }
      return a;
    }
  }

  @Override
  public int compareTo(@Nullable final LongCollection other) {
    if (other == null) {
      return +1;
    }
    final LongIterator lhsIter = this.iterator();
    final LongIterator rhsIter = other.iterator();
    while (lhsIter.hasNext() && rhsIter.hasNext()) {
      final long lhsValue = lhsIter.next();
      final long rhsValue = rhsIter.next();
      final int rc = Comparison.compare(lhsValue, rhsValue);
      if (rc != 0) {
        return rc;
      }
    }
    if (lhsIter.hasNext()) {
      return +1;
    } else if (rhsIter.hasNext()) {
      return -1;
    } else {
      return 0;
    }
  }

  @Override
  public int hashCode() {
    final int multiplier = 131;
    int code = 11;
    final LongIterator iter = this.iterator();
    while (iter.hasNext()) {
      final long value = iter.next();
      code = Hash.combine(code, multiplier, value);
    }
    return code;
  }

  @Override
  public boolean equals(@Nullable final Object obj) {
    if (obj == null) {
      return false;
    }
    if (this.getClass() != obj.getClass()) {
      return false;
    }
    final LongCollection other = (LongCollection) obj;
    if (this.size() != other.size()) {
      return false;
    }
    final LongIterator lhsIter = this.iterator();
    final LongIterator rhsIter = other.iterator();
    while (lhsIter.hasNext() && rhsIter.hasNext()) {
      final long lhsValue = lhsIter.next();
      final long rhsValue = rhsIter.next();
      if (lhsValue != rhsValue) {
        return false;
      }
    }
    return true;
  }
}
