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

public abstract class AbstractByteList extends AbstractByteCollection
    implements ByteList {

  private static final long serialVersionUID = -8413030046337855832L;

  protected byte modifyCount = 0;

  @Override
  public abstract byte get(int index);

  @Override
  public abstract int size();

  @Override
  public abstract byte removeAt(int index);

  @Override
  public abstract byte set(int index, byte element);

  @Override
  public abstract void add(int index, byte element);

  @Override
  public boolean add(final byte element) {
    add(size(), element);
    return true;
  }

  @Override
  public boolean addAll(final int index, final ByteCollection collection) {
    boolean modified = false;
    final ByteIterator iter = collection.iterator();
    int i = index;
    while (iter.hasNext()) {
      add(i++, iter.next());
      modified = true;
    }
    return modified;
  }

  @Override
  public int indexOf(final byte element) {
    final ByteIterator iter = iterator();
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
  public int lastIndexOf(final byte element) {
    final ByteListIterator iter = listIterator(size());
    while (iter.hasPrevious()) {
      if (iter.previous() == element) {
        return iter.nextIndex();
      }
    }
    return -1;
  }

  @Override
  public ByteIterator iterator() {
    return listIterator();
  }

  @Override
  public ByteListIterator listIterator() {
    return listIterator(0);
  }

  @Override
  public abstract ByteListIterator listIterator(final int index);

  @Override
  public void unique() {
    final ByteListIterator iter = listIterator();
    Byte previous = null;
    while (iter.hasNext()) {
      final byte value = iter.next();
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
    } else if (that instanceof ByteList) {
      final ByteList thatList = (ByteList) that;
      if (size() != thatList.size()) {
        return false;
      }
      final ByteIterator thisIter = iterator();
      final ByteIterator thatIter = thatList.iterator();
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
    final ByteIterator iter = iterator();
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
      final ByteIterator iter = iterator();
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
