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
 * {@link ByteCollection} 的抽象基类。
 *
 * <p>只读子类必须覆盖{@link #iterator}和{@link #size}。 可变子类还应覆盖{@link #add}和
 * {@link ByteIterator#remove ByteIterator.remove}。
 * 所有其他方法都至少具有一些派生自这些方法的某些基本实现。 子类可以选择覆盖这些方法以提供更有效的实现。
 *
 * @author 胡海星
 */
public abstract class AbstractByteCollection implements ByteCollection {

  @Serial
  private static final long serialVersionUID = -8950858494309449583L;

  @Override
  public abstract ByteIterator iterator();

  @Override
  public abstract int size();

  @Override
  public abstract boolean add(byte element);

  @Override
  public boolean addAll(final ByteCollection c) {
    boolean modified = false;
    for (final ByteIterator iter = c.iterator(); iter.hasNext();) {
      modified |= add(iter.next());
    }
    return modified;
  }

  @Override
  public void clear() {
    for (final ByteIterator iter = iterator(); iter.hasNext();) {
      iter.next();
      iter.remove();
    }
  }

  @Override
  public boolean contains(final byte element) {
    for (final ByteIterator iter = iterator(); iter.hasNext();) {
      if (iter.next() == element) {
        return true;
      }
    }
    return false;
  }

  @Override
  public boolean containsAll(final ByteCollection c) {
    for (final ByteIterator iter = c.iterator(); iter.hasNext();) {
      if (! contains(iter.next())) {
        return false;
      }
    }
    return true;
  }

  @Override
  public boolean isEmpty() {
    return (0 == size());
  }

  @Override
  public boolean remove(final byte element) {
    for (final ByteIterator iter = iterator(); iter.hasNext();) {
      if (iter.next() == element) {
        iter.remove();
        return true;
      }
    }
    return false;
  }

  @Override
  public boolean removeAll(final ByteCollection c) {
    boolean modified = false;
    for (final ByteIterator iter = c.iterator(); iter.hasNext();) {
      modified |= remove(iter.next());
    }
    return modified;
  }

  @Override
  public boolean retainAll(final ByteCollection c) {
    boolean modified = false;
    for (final ByteIterator iter = iterator(); iter.hasNext();) {
      if (! c.contains(iter.next())) {
        iter.remove();
        modified = true;
      }
    }
    return modified;
  }

  @Override
  public byte[] toArray() {
    final byte[] array = new byte[size()];
    int i = 0;
    for (final ByteIterator iter = iterator(); iter.hasNext();) {
      array[i] = iter.next();
      i++;
    }
    return array;
  }

  @Override
  public byte[] toArray(final byte[] a) {
    if (a.length < size()) {
      return toArray();
    } else {
      int i = 0;
      for (final ByteIterator iter = iterator(); iter.hasNext();) {
        a[i] = iter.next();
        i++;
      }
      return a;
    }
  }

  @Override
  public int compareTo(@Nullable final ByteCollection other) {
    if (other == null) {
      return +1;
    }
    final ByteIterator lhsIter = iterator();
    final ByteIterator rhsIter = other.iterator();
    while (lhsIter.hasNext() && rhsIter.hasNext()) {
      final byte lhsValue = lhsIter.next();
      final byte rhsValue = rhsIter.next();
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
    final ByteIterator iter = iterator();
    while (iter.hasNext()) {
      final byte value = iter.next();
      code = Hash.combine(code, multiplier, value);
    }
    return code;
  }

  @Override
  public boolean equals(@Nullable final Object obj) {
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final ByteCollection other = (ByteCollection) obj;
    if (size() != other.size()) {
      return false;
    }
    final ByteIterator lhsIter = iterator();
    final ByteIterator rhsIter = other.iterator();
    while (lhsIter.hasNext() && rhsIter.hasNext()) {
      final byte lhsValue = lhsIter.next();
      final byte rhsValue = rhsIter.next();
      if (lhsValue != rhsValue) {
        return false;
      }
    }
    return true;
  }
}
