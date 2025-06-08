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
import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;

/**
 * {@link FloatCollection} 的抽象基类。
 *
 * <p>只读子类必须覆盖 {@link #iterator} 和 {@link #size}。
 * 可变子类还应覆盖 {@link #add} 和 {@link FloatIterator#remove FloatIterator.remove}。
 * 所有其他方法都至少具有从这些方法派生的一些基本实现。子类可以选择覆盖这些方法以提供更有效的实现。
 *
 * @author 胡海星
 */
public abstract class AbstractFloatCollection implements FloatCollection {

  @Serial
  private static final long serialVersionUID = -1104376036648527273L;

  @Override
  public abstract FloatIterator iterator();

  @Override
  public abstract int size();

  @Override
  public abstract boolean add(final float element);

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean addAll(final FloatCollection c) {
    boolean modified = false;
    for (final FloatIterator iter = c.iterator(); iter.hasNext();) {
      modified |= add(iter.next());
    }
    return modified;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void clear() {
    for (final FloatIterator iter = iterator(); iter.hasNext();) {
      iter.next();
      iter.remove();
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean contains(final float element) {
    for (final FloatIterator iter = iterator(); iter.hasNext();) {
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
  public boolean containsAll(final FloatCollection c) {
    for (final FloatIterator iter = c.iterator(); iter.hasNext();) {
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
  public boolean remove(final float element) {
    for (final FloatIterator iter = iterator(); iter.hasNext();) {
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
  public boolean removeAll(final FloatCollection c) {
    boolean modified = false;
    for (final FloatIterator iter = c.iterator(); iter.hasNext();) {
      modified |= remove(iter.next());
    }
    return modified;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean retainAll(final FloatCollection c) {
    boolean modified = false;
    for (final FloatIterator iter = iterator(); iter.hasNext();) {
      if (! c.contains(iter.next())) {
        iter.remove();
        modified = true;
      }
    }
    return modified;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public float[] toArray() {
    final float[] array = new float[size()];
    int i = 0;
    for (final FloatIterator iter = iterator(); iter.hasNext();) {
      array[i] = iter.next();
      i++;
    }
    return array;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public float[] toArray(final float[] a) {
    if (a.length < size()) {
      return toArray();
    } else {
      int i = 0;
      for (final FloatIterator iter = iterator(); iter.hasNext();) {
        a[i] = iter.next();
        i++;
      }
      return a;
    }
  }

  @Override
  public int compareTo(@Nullable final FloatCollection other) {
    if (other == null) {
      return +1;
    }
    final FloatIterator lhsIter = this.iterator();
    final FloatIterator rhsIter = other.iterator();
    while (lhsIter.hasNext() && rhsIter.hasNext()) {
      final float lhsValue = lhsIter.next();
      final float rhsValue = rhsIter.next();
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
    final FloatIterator iter = this.iterator();
    while (iter.hasNext()) {
      final float value = iter.next();
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
    final FloatCollection other = (FloatCollection) obj;
    if (this.size() != other.size()) {
      return false;
    }
    final FloatIterator lhsIter = this.iterator();
    final FloatIterator rhsIter = other.iterator();
    while (lhsIter.hasNext() && rhsIter.hasNext()) {
      final float lhsValue = lhsIter.next();
      final float rhsValue = rhsIter.next();
      if (! Equality.equals(lhsValue, rhsValue)) {
        return false;
      }
    }
    return true;
  }
}
