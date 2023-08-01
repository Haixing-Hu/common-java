////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.datastructure.list.primitive;

import ltd.qubit.commons.lang.Comparison;
import ltd.qubit.commons.lang.Hash;

import javax.annotation.Nullable;

/**
 * Abstract base class for {@link BooleanCollection}s.
 *
 * <p>Read-only subclasses must override {@link #iterator} and {@link #size}.
 * Mutable subclasses should also override {@link #add} and
 * {@link BooleanIterator#remove BooleanIterator.remove}. All other methods have
 * at least some base implementation derived from these. Subclasses may choose
 * to override these methods to provide a more efficient implementation.
 *
 * @author Haixing Hu
 */
public abstract class AbstractBooleanCollection implements BooleanCollection {

  private static final long serialVersionUID = 5843747887303529054L;

  @Override
  public abstract BooleanIterator iterator();

  @Override
  public abstract int size();

  @Override
  public abstract boolean add(boolean element);

  @Override
  public boolean addAll(final BooleanCollection c) {
    boolean modified = false;
    for (final BooleanIterator iter = c.iterator(); iter.hasNext();) {
      modified |= add(iter.next());
    }
    return modified;
  }

  @Override
  public void clear() {
    for (final BooleanIterator iter = iterator(); iter.hasNext();) {
      iter.next();
      iter.remove();
    }
  }

  @Override
  public boolean contains(final boolean element) {
    for (final BooleanIterator iter = iterator(); iter.hasNext();) {
      if (iter.next() == element) {
        return true;
      }
    }
    return false;
  }

  @Override
  public boolean containsAll(final BooleanCollection c) {
    for (final BooleanIterator iter = c.iterator(); iter.hasNext();) {
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
  public boolean remove(final boolean element) {
    for (final BooleanIterator iter = iterator(); iter.hasNext();) {
      if (iter.next() == element) {
        iter.remove();
        return true;
      }
    }
    return false;
  }

  @Override
  public boolean removeAll(final BooleanCollection c) {
    boolean modified = false;
    for (final BooleanIterator iter = c.iterator(); iter.hasNext();) {
      modified |= remove(iter.next());
    }
    return modified;
  }

  @Override
  public boolean retainAll(final BooleanCollection c) {
    boolean modified = false;
    for (final BooleanIterator iter = iterator(); iter.hasNext();) {
      if (! c.contains(iter.next())) {
        iter.remove();
        modified = true;
      }
    }
    return modified;
  }

  @Override
  public boolean[] toArray() {
    final boolean[] array = new boolean[size()];
    int i = 0;
    for (final BooleanIterator iter = iterator(); iter.hasNext();) {
      array[i++] = iter.next();
    }
    return array;
  }

  @Override
  public boolean[] toArray(final boolean[] a) {
    if (a.length < size()) {
      return toArray();
    } else {
      int i = 0;
      for (final BooleanIterator iter = iterator(); iter.hasNext();) {
        a[i++] = iter.next();
      }
      return a;
    }
  }

  @Override
  public int hashCode() {
    final int multiplier = 131;
    int code = 11;
    final BooleanIterator iter = iterator();
    while (iter.hasNext()) {
      final boolean value = iter.next();
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
    final BooleanCollection other = (BooleanCollection) obj;
    if (size() != other.size()) {
      return false;
    }
    final BooleanIterator lhsIter = iterator();
    final BooleanIterator rhsIter = other.iterator();
    while (lhsIter.hasNext() && rhsIter.hasNext()) {
      final boolean lhsValue = lhsIter.next();
      final boolean rhsValue = rhsIter.next();
      if (lhsValue != rhsValue) {
        return false;
      }
    }
    return true;
  }

  @Override
  public int compareTo(@Nullable final BooleanCollection other) {
    if (other == null) {
      return +1;
    }
    final BooleanIterator lhsIter = iterator();
    final BooleanIterator rhsIter = other.iterator();
    while (lhsIter.hasNext() && rhsIter.hasNext()) {
      final boolean lhsValue = lhsIter.next();
      final boolean rhsValue = rhsIter.next();
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
}
