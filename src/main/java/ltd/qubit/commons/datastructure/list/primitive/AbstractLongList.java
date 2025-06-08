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
 * {@link LongList}接口的骨架抽象实现。
 *
 * @author 胡海星
 */
public abstract class AbstractLongList extends AbstractLongCollection implements LongList {

  @Serial
  private static final long serialVersionUID = -486872511884606247L;

  /**
   * 此列表已被修改的次数。
   */
  protected int modifyCount = 0;

  /**
   * {@inheritDoc}
   */
  @Override
  public abstract long get(int index);

  /**
   * {@inheritDoc}
   */
  @Override
  public abstract int size();

  /**
   * {@inheritDoc}
   */
  @Override
  public abstract long removeAt(int index);

  /**
   * {@inheritDoc}
   */
  @Override
  public abstract long set(int index, long element);

  /**
   * {@inheritDoc}
   */
  @Override
  public abstract void add(int index, long element);

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean add(final long element) {
    add(size(), element);
    return true;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean addAll(final int index, final LongCollection collection) {
    boolean modified = false;
    final LongIterator iter = collection.iterator();
    int i = index;
    while (iter.hasNext()) {
      add(i++, iter.next());
      modified = true;
    }
    return modified;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int indexOf(final long element) {
    final LongIterator iter = iterator();
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

  /**
   * {@inheritDoc}
   */
  @Override
  public int lastIndexOf(final long element) {
    final LongListIterator iter = listIterator(size());
    while (iter.hasPrevious()) {
      if (iter.previous() == element) {
        return iter.nextIndex();
      }
    }
    return -1;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public LongIterator iterator() {
    return listIterator();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public LongListIterator listIterator() {
    return listIterator(0);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public abstract LongListIterator listIterator(final int index);

  /**
   * {@inheritDoc}
   */
  @Override
  public void unique() {
    final LongListIterator iter = listIterator();
    Long previous = null;
    while (iter.hasNext()) {
      final long value = iter.next();
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
    } else if (that instanceof LongList) {
      final LongList thatList = (LongList) that;
      if (size() != thatList.size()) {
        return false;
      }
      final LongIterator thisIter = iterator();
      final LongIterator thatIter = thatList.iterator();
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
    final LongIterator iter = iterator();
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
      final LongIterator iter = iterator();
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
