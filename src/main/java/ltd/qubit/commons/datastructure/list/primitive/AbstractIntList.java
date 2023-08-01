////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.datastructure.list.primitive;

import ltd.qubit.commons.lang.Hash;

public abstract class AbstractIntList extends AbstractIntCollection implements IntList {

  private static final long serialVersionUID = 2747013056428863977L;

  protected int modifyCount = 0;

  @Override
  public abstract int get(int index);

  @Override
  public abstract int size();

  @Override
  public abstract int removeAt(int index);

  @Override
  public abstract int set(int index, int element);

  @Override
  public abstract void add(int index, int element);

  @Override
  public boolean add(final int element) {
    add(size(), element);
    return true;
  }

  @Override
  public boolean addAll(final int index, final IntCollection collection) {
    boolean modified = false;
    final IntIterator iter = collection.iterator();
    int i = index;
    while (iter.hasNext()) {
      add(i++, iter.next());
      modified = true;
    }
    return modified;
  }

  @Override
  public int indexOf(final int element) {
    final IntIterator iter = iterator();
    int i = 0;
    while (iter.hasNext()) {
      if (iter.next() == element) {
        return i;
      } else {
        ++i;
      }
    }
    return -1;
  }

  @Override
  public int lastIndexOf(final int element) {
    final IntListIterator iter = listIterator(size());
    while (iter.hasPrevious()) {
      if (iter.previous() == element) {
        return iter.nextIndex();
      }
    }
    return -1;
  }

  @Override
  public IntIterator iterator() {
    return listIterator();
  }

  @Override
  public IntListIterator listIterator() {
    return listIterator(0);
  }

  @Override
  public abstract IntListIterator listIterator(final int index);

  @Override
  public void unique() {
    final IntListIterator iter = listIterator();
    Integer previous = null;
    while (iter.hasNext()) {
      final int value = iter.next();
      if (previous != null && previous == value) {
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
    } else if (that instanceof IntList) {
      final IntList thatList = (IntList) that;
      if (size() != thatList.size()) {
        return false;
      }
      final IntIterator thisIter = iterator();
      final IntIterator thatIter = thatList.iterator();
      while (thisIter.hasNext()) {
        if (thisIter.next() != thatIter.next()) {
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
    final IntIterator iter = iterator();
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
      final IntIterator iter = iterator();
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
