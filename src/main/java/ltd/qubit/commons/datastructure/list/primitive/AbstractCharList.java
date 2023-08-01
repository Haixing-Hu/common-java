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

public abstract class AbstractCharList extends AbstractCharCollection implements CharList {

  private static final long serialVersionUID = -8293434831269543674L;

  protected byte modifyCount = 0;

  @Override
  public abstract char get(int index);

  @Override
  public abstract int size();

  @Override
  public abstract char removeAt(int index);

  @Override
  public abstract char set(int index, char element);

  @Override
  public abstract void add(int index, char element);

  @Override
  public boolean add(final char element) {
    add(size(), element);
    return true;
  }

  @Override
  public boolean addAll(final int index, final CharCollection collection) {
    boolean modified = false;
    final CharIterator iter = collection.iterator();
    int i = index;
    while (iter.hasNext()) {
      add(i++, iter.next());
      modified = true;
    }
    return modified;
  }

  @Override
  public int indexOf(final char element) {
    final CharIterator iter = iterator();
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
  public int lastIndexOf(final char element) {
    final CharListIterator iter = listIterator(size());
    while (iter.hasPrevious()) {
      if (iter.previous() == element) {
        return iter.nextIndex();
      }
    }
    return -1;
  }

  @Override
  public CharIterator iterator() {
    return listIterator();
  }

  @Override
  public CharListIterator listIterator() {
    return listIterator(0);
  }

  @Override
  public abstract CharListIterator listIterator(final int index);

  @Override
  public void unique() {
    final CharListIterator iter = listIterator();
    Character previous = null;
    while (iter.hasNext()) {
      final char value = iter.next();
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
    } else if (that instanceof CharList) {
      final CharList thatList = (CharList) that;
      if (size() != thatList.size()) {
        return false;
      }
      final CharIterator thisIter = iterator();
      final CharIterator thatIter = thatList.iterator();
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
    final CharIterator iter = iterator();
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
      final CharIterator iter = iterator();
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
