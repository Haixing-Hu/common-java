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

import ltd.qubit.commons.lang.Hash;

/**
 * {@link BooleanList} 的抽象基类。
 *
 * @author 胡海星
 */
public abstract class AbstractBooleanList extends AbstractBooleanCollection
    implements BooleanList {

  @Serial
  private static final long serialVersionUID = 8720263254681296396L;

  protected byte modifyCount = 0;

  @Override
  public abstract boolean get(int index);

  @Override
  public abstract int size();

  @Override
  public abstract boolean removeAt(int index);

  @Override
  public abstract boolean set(int index, boolean element);

  @Override
  public abstract void add(int index, boolean element);

  @Override
  public boolean add(final boolean element) {
    add(size(), element);
    return true;
  }

  @Override
  public boolean addAll(final int index, final BooleanCollection collection) {
    boolean modified = false;
    final BooleanIterator iter = collection.iterator();
    int i = index;
    while (iter.hasNext()) {
      add(i++, iter.next());
      modified = true;
    }
    return modified;
  }

  @Override
  public int indexOf(final boolean element) {
    final BooleanIterator iter = iterator();
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
  public int lastIndexOf(final boolean element) {
    final BooleanListIterator iter = listIterator(size());
    while (iter.hasPrevious()) {
      if (iter.previous() == element) {
        return iter.nextIndex();
      }
    }
    return -1;
  }

  @Override
  public BooleanIterator iterator() {
    return listIterator();
  }

  @Override
  public BooleanListIterator listIterator() {
    return listIterator(0);
  }

  @Override
  public abstract BooleanListIterator listIterator(final int index);

  @Override
  public void unique() {
    final BooleanListIterator iter = listIterator();
    Boolean previous = null;
    while (iter.hasNext()) {
      final boolean value = iter.next();
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
    } else if (that instanceof BooleanList) {
      final BooleanList thatList = (BooleanList) that;
      if (size() != thatList.size()) {
        return false;
      }
      final BooleanIterator thisIter = iterator();
      final BooleanIterator thatIter = thatList.iterator();
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
    final BooleanIterator iter = iterator();
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
      final BooleanIterator iter = iterator();
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
