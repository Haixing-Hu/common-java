////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.datastructure.list.primitive;

import ltd.qubit.commons.lang.Hash;

public abstract class AbstractShortList extends AbstractShortCollection implements ShortList {

  private static final long serialVersionUID = -1392082730597946038L;

  protected int modifyCount = 0;

  @Override
  public abstract short get(int index);

  @Override
  public abstract int size();

  @Override
  public abstract short removeAt(int index);

  @Override
  public abstract short set(int index, short element);

  @Override
  public abstract void add(int index, short element);

  @Override
  public boolean add(final short element) {
    add(size(), element);
    return true;
  }

  @Override
  public boolean addAll(final int index, final ShortCollection collection) {
    boolean modified = false;
    final ShortIterator iter = collection.iterator();
    int i = index;
    while (iter.hasNext()) {
      add(i++, iter.next());
      modified = true;
    }
    return modified;
  }

  @Override
  public int indexOf(final short element) {
    final ShortIterator iter = iterator();
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
  public int lastIndexOf(final short element) {
    final ShortListIterator iter = listIterator(size());
    while (iter.hasPrevious()) {
      if (iter.previous() == element) {
        return iter.nextIndex();
      }
    }
    return -1;
  }

  @Override
  public ShortIterator iterator() {
    return listIterator();
  }

  @Override
  public ShortListIterator listIterator() {
    return listIterator(0);
  }

  @Override
  public abstract ShortListIterator listIterator(final int index);

  @Override
  public void unique() {
    final ShortListIterator iter = listIterator();
    Short previous = null;
    while (iter.hasNext()) {
      final short value = iter.next();
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
    } else if (that instanceof ShortList) {
      final ShortList thatList = (ShortList) that;
      if (size() != thatList.size()) {
        return false;
      }
      final ShortIterator thisIter = iterator();
      final ShortIterator thatIter = thatList.iterator();
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
    final ShortIterator iter = iterator();
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
      final ShortIterator iter = iterator();
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
