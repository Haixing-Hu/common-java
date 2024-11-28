////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.datastructure.list.primitive;

import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;

public abstract class AbstractDoubleList extends AbstractDoubleCollection implements DoubleList {

  private static final long serialVersionUID = -8869106754958585067L;

  protected int modifyCount = 0;

  @Override
  public abstract double get(int index);

  @Override
  public abstract int size();

  @Override
  public abstract double removeAt(int index);

  @Override
  public abstract double set(int index, double element);

  @Override
  public abstract void add(int index, double element);

  @Override
  public boolean add(final double element) {
    add(size(), element);
    return true;
  }

  @Override
  public boolean addAll(final int index, final DoubleCollection collection) {
    boolean modified = false;
    final DoubleIterator iter = collection.iterator();
    int i = index;
    while (iter.hasNext()) {
      add(i++, iter.next());
      modified = true;
    }
    return modified;
  }

  @Override
  public int indexOf(final double element) {
    final DoubleIterator iter = iterator();
    int i = 0;
    while (iter.hasNext()) {
      if (Equality.equals(iter.next(), element)) {
        return i;
      } else {
        ++i;
      }
    }
    return -1;
  }

  @Override
  public int lastIndexOf(final double element) {
    final DoubleListIterator iter = listIterator(size());
    while (iter.hasPrevious()) {
      if (Equality.equals(iter.previous(), element)) {
        return iter.nextIndex();
      }
    }
    return -1;
  }

  @Override
  public DoubleIterator iterator() {
    return listIterator();
  }

  @Override
  public DoubleListIterator listIterator() {
    return listIterator(0);
  }

  @Override
  public abstract DoubleListIterator listIterator(final int index);

  @Override
  public void unique() {
    final DoubleListIterator iter = listIterator();
    Double previous = null;
    while (iter.hasNext()) {
      final double value = iter.next();
      if ((previous != null) && Equality.equals((double) previous, value)) {
        iter.remove();
      } else {
        previous = value;
      }
    }
  }

  @Override
  public boolean equals(final Object that) {
    if (this == that) {
      return true;
    } else if (that instanceof DoubleList) {
      final DoubleList thatList = (DoubleList) that;
      if (size() != thatList.size()) {
        return false;
      }
      final DoubleIterator thisIter = iterator();
      final DoubleIterator thatIter = thatList.iterator();
      while (thisIter.hasNext()) {
        final double lhs = thisIter.next();
        final double rhs = thatIter.next();
        if (!Equality.equals(lhs, rhs)) {
          return false;
        }
      }
      return true;
    } else {
      return false;
    }
  }

  @Override
  public int hashCode() {
    final DoubleIterator iter = iterator();
    final int multiplier = 31;
    int code = 3;
    while (iter.hasNext()) {
      code = Hash.combine(code, multiplier, iter.next());
    }
    return code;
  }

  @Override
  public String toString() {
    if (size() == 0) {
      return "[]";
    } else {
      final StringBuilder builder = new StringBuilder();
      builder.append('[');
      final DoubleIterator iter = iterator();
      while (iter.hasNext()) {
        builder.append(iter.next()).append(',');
      }
      // eat the last separator ','
      builder.setLength(builder.length() - 1);
      builder.append(']');
      return builder.toString();
    }
  }
}
