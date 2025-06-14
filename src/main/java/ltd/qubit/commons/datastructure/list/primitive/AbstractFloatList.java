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

import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;

/**
 * {@link FloatList} 的抽象基类。
 *
 * @author 胡海星
 */
public abstract class AbstractFloatList extends AbstractFloatCollection implements FloatList {

  @Serial
  private static final long serialVersionUID = 4768615621442643457L;

  protected int modifyCount = 0;

  /**
   * {@inheritDoc}
   */
  @Override
  public abstract float get(int index);

  /**
   * {@inheritDoc}
   */
  @Override
  public abstract int size();

  /**
   * {@inheritDoc}
   */
  @Override
  public abstract float removeAt(int index);

  /**
   * {@inheritDoc}
   */
  @Override
  public abstract float set(int index, float element);

  /**
   * {@inheritDoc}
   */
  @Override
  public abstract void add(int index, float element);

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean add(final float element) {
    add(size(), element);
    return true;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean addAll(final int index, final FloatCollection collection) {
    boolean modified = false;
    final FloatIterator iter = collection.iterator();
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
  public int indexOf(final float element) {
    final FloatIterator iter = iterator();
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

  /**
   * {@inheritDoc}
   */
  @Override
  public int lastIndexOf(final float element) {
    final FloatListIterator iter = listIterator(size());
    while (iter.hasPrevious()) {
      if (Equality.equals(iter.previous(), element)) {
        return iter.nextIndex();
      }
    }
    return -1;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public FloatIterator iterator() {
    return listIterator();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public FloatListIterator listIterator() {
    return listIterator(0);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public abstract FloatListIterator listIterator(final int index);

  /**
   * {@inheritDoc}
   */
  @Override
  public void unique() {
    final FloatListIterator iter = listIterator();
    Float previous = null;
    while (iter.hasNext()) {
      final float value = iter.next();
      if ((previous != null) && Equality.equals((float) previous, value)) {
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
    } else if (that instanceof FloatList) {
      final FloatList thatList = (FloatList) that;
      if (size() != thatList.size()) {
        return false;
      }
      final FloatIterator thisIter = iterator();
      final FloatIterator thatIter = thatList.iterator();
      while (thisIter.hasNext()) {
        final float lhs = thisIter.next();
        final float rhs = thatIter.next();
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
    final FloatIterator iter = iterator();
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
      final FloatIterator iter = iterator();
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
