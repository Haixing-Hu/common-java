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
 * {@link ShortCollection} 的抽象基类。
 *
 * <p>只读子类必须覆盖 {@link #iterator}和{@link #size}。
 * 可变子类还应覆盖{@link #add}和{@link ShortIterator#remove ShortIterator.remove}。
 * 所有其他方法都至少具有从这些方法派生的一些基本实现。子类可以选择覆盖这些方法以提供更有效的实现。
 *
 * @author 胡海星
 */
public abstract class AbstractShortCollection implements ShortCollection {

  @Serial
  private static final long serialVersionUID = -415181402693273004L;

  /**
   * {@inheritDoc}
   */
  @Override
  public abstract ShortIterator iterator();

  /**
   * {@inheritDoc}
   */
  @Override
  public abstract int size();

  /**
   * {@inheritDoc}
   */
  @Override
  public abstract boolean add(final short element);

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean addAll(final ShortCollection c) {
    boolean modified = false;
    for (final ShortIterator iter = c.iterator(); iter.hasNext();) {
      modified |= add(iter.next());
    }
    return modified;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void clear() {
    for (final ShortIterator iter = iterator(); iter.hasNext();) {
      iter.next();
      iter.remove();
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean contains(final short element) {
    for (final ShortIterator iter = iterator(); iter.hasNext();) {
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
  public boolean containsAll(final ShortCollection c) {
    for (final ShortIterator iter = c.iterator(); iter.hasNext();) {
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
  public boolean remove(final short element) {
    for (final ShortIterator iter = iterator(); iter.hasNext();) {
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
  public boolean removeAll(final ShortCollection c) {
    boolean modified = false;
    for (final ShortIterator iter = c.iterator(); iter.hasNext();) {
      modified |= remove(iter.next());
    }
    return modified;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean retainAll(final ShortCollection c) {
    boolean modified = false;
    for (final ShortIterator iter = iterator(); iter.hasNext();) {
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
  public short[] toArray() {
    final short[] array = new short[size()];
    int i = 0;
    for (final ShortIterator iter = iterator(); iter.hasNext();) {
      array[i] = iter.next();
      i++;
    }
    return array;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public short[] toArray(final short[] a) {
    if (a.length < size()) {
      return toArray();
    } else {
      int i = 0;
      for (final ShortIterator iter = iterator(); iter.hasNext();) {
        a[i] = iter.next();
        i++;
      }
      return a;
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int compareTo(@Nullable final ShortCollection other) {
    if (other == null) {
      return +1;
    }
    final ShortIterator lhsIter = this.iterator();
    final ShortIterator rhsIter = other.iterator();
    while (lhsIter.hasNext() && rhsIter.hasNext()) {
      final short lhsValue = lhsIter.next();
      final short rhsValue = rhsIter.next();
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

  /**
   * {@inheritDoc}
   */
  @Override
  public int hashCode() {
    final int multiplier = 131;
    int code = 11;
    final ShortIterator iter = this.iterator();
    while (iter.hasNext()) {
      final short value = iter.next();
      code = Hash.combine(code, multiplier, value);
    }
    return code;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean equals(@Nullable final Object obj) {
    if (obj == null) {
      return false;
    }
    if (this.getClass() != obj.getClass()) {
      return false;
    }
    final ShortCollection other = (ShortCollection) obj;
    if (this.size() != other.size()) {
      return false;
    }
    final ShortIterator lhsIter = this.iterator();
    final ShortIterator rhsIter = other.iterator();
    while (lhsIter.hasNext() && rhsIter.hasNext()) {
      final short lhsValue = lhsIter.next();
      final short rhsValue = rhsIter.next();
      if (lhsValue != rhsValue) {
        return false;
      }
    }
    return true;
  }
}
