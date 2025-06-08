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
import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

import static ltd.qubit.commons.lang.Argument.requireInCloseRange;
import static ltd.qubit.commons.lang.Argument.requireInRightOpenRange;
import static ltd.qubit.commons.lang.Argument.requireIndexInCloseRange;
import static ltd.qubit.commons.lang.Argument.requireLessEqual;

/**
 * 一个抽象基类，用于由数组等随机访问结构支持的 {@link ByteList}。
 *
 * <p>只读子类必须重写 {@link #get} 和 {@link #size}。可变子类还应重写 {@link #set}。
 * 可变大小的子类还应重写 {@link #add(byte)} 和 {@link #removeAt}。
 * 所有其他方法都至少有一些从这些派生的基本实现。子类可以选择重写这些方法以提供更高效的实现。
 *
 * @author 胡海星
 */
public abstract class RandomAccessByteList extends AbstractByteList {

  @Serial
  private static final long serialVersionUID = 5583256332233364308L;

  public static final int DEFAULT_INITIAL_CAPACITY = 8;

  @Override
  public ByteListIterator listIterator(final int index) {
    requireIndexInCloseRange(index, 0, size());
    return new RandomAccessByteListIterator(this, index);
  }

  @Override
  public ByteList subList(final int fromIndex, final int toIndex) {
    final int n = size();
    requireInCloseRange("fromIndex", fromIndex, 0, n);
    requireInCloseRange("toIndex", toIndex, fromIndex, n);
    return new RandomAccessByteSubList(this, fromIndex, toIndex);
  }

  @Override
  public void sort() {
    sort(0, size());
  }

  protected abstract void sort(final int fromIndex, final int toIndex);

  /**
   * {@link RandomAccessByteList} 的 {@link ByteListIterator} 实现。
   */
  protected static class RandomAccessByteListIterator implements
      ByteListIterator {

    protected RandomAccessByteList source;
    protected int nextIndex;
    protected int lastReturnedIndex;
    protected int expectedModifyCount;

    RandomAccessByteListIterator(final RandomAccessByteList list,
        final int index) {
      requireIndexInCloseRange(index, 0, list.size());
      source = list;
      nextIndex = index;
      lastReturnedIndex = - 1;
      expectedModifyCount = source.modifyCount;
    }

    @Override
    public boolean hasNext() {
      if (expectedModifyCount != source.modifyCount) {
        throw new ConcurrentModificationException();
      }
      return nextIndex < source.size();
    }

    @Override
    public boolean hasPrevious() {
      if (expectedModifyCount != source.modifyCount) {
        throw new ConcurrentModificationException();
      }
      return nextIndex > 0;
    }

    @Override
    public int nextIndex() {
      if (expectedModifyCount != source.modifyCount) {
        throw new ConcurrentModificationException();
      }
      return nextIndex;
    }

    @Override
    public int previousIndex() {
      if (expectedModifyCount != source.modifyCount) {
        throw new ConcurrentModificationException();
      }
      return nextIndex - 1;
    }

    @Override
    public byte next() {
      if (expectedModifyCount != source.modifyCount) {
        throw new ConcurrentModificationException();
      }
      if (! hasNext()) {
        throw new NoSuchElementException();
      } else {
        final byte val = source.get(nextIndex);
        lastReturnedIndex = nextIndex;
        ++nextIndex;
        return val;
      }
    }

    @Override
    public byte previous() {
      if (expectedModifyCount != source.modifyCount) {
        throw new ConcurrentModificationException();
      }
      if (! hasPrevious()) {
        throw new NoSuchElementException();
      } else {
        final byte val = source.get(nextIndex - 1);
        lastReturnedIndex = nextIndex - 1;
        --nextIndex;
        return val;
      }
    }

    @Override
    public void add(final byte value) {
      if (expectedModifyCount != source.modifyCount) {
        throw new ConcurrentModificationException();
      }
      source.add(nextIndex, value);
      ++nextIndex;
      lastReturnedIndex = - 1;
      expectedModifyCount = source.modifyCount;
    }

    @Override
    public void remove() {
      if (expectedModifyCount != source.modifyCount) {
        throw new ConcurrentModificationException();
      }
      if (lastReturnedIndex == - 1) {
        throw new IllegalStateException();
      }
      if (lastReturnedIndex == nextIndex) {
        // remove() following previous()
        source.removeAt(lastReturnedIndex);
      } else {
        // remove() following next()
        source.removeAt(lastReturnedIndex);
        --nextIndex;
      }
      lastReturnedIndex = - 1;
      expectedModifyCount = source.modifyCount;
    }

    @Override
    public void set(final byte value) {
      if (expectedModifyCount != source.modifyCount) {
        throw new ConcurrentModificationException();
      }
      if (lastReturnedIndex == - 1) {
        throw new IllegalStateException();
      }
      source.set(lastReturnedIndex, value);
      expectedModifyCount = source.modifyCount;
    }
  }

  /**
   * {@link RandomAccessByteList} 的子列表视图。
   */
  protected static class RandomAccessByteSubList extends RandomAccessByteList {

    private static final long serialVersionUID = -4910140338506781305L;

    protected final int offset;
    protected int limit;
    protected final RandomAccessByteList source;
    protected int expectedModifyCount;

    RandomAccessByteSubList(final RandomAccessByteList list,
        final int fromIndex, final int toIndex) {
      source = list;
      offset = fromIndex;
      limit = toIndex - fromIndex;
      expectedModifyCount = list.modifyCount;
    }

    @Override
    public byte get(final int index) {
      requireInRightOpenRange("index", index, 0, limit);
      if (expectedModifyCount != source.modifyCount) {
        throw new ConcurrentModificationException();
      }
      return source.get(index + offset);
    }

    @Override
    public byte removeAt(final int index) {
      requireInRightOpenRange("index", index, 0, limit);
      if (expectedModifyCount != source.modifyCount) {
        throw new ConcurrentModificationException();
      }
      final byte val = source.removeAt(index + offset);
      --limit;
      expectedModifyCount = source.modifyCount;
      ++modifyCount;
      return val;
    }

    @Override
    public byte set(final int index, final byte element) {
      requireInRightOpenRange("index", index, 0, limit);
      if (expectedModifyCount != source.modifyCount) {
        throw new ConcurrentModificationException();
      }
      final byte val = source.set(index + offset, element);
      ++modifyCount;
      expectedModifyCount = source.modifyCount;
      return val;
    }

    @Override
    public void add(final int index, final byte element) {
      requireInCloseRange("index", index, 0, limit);
      if (expectedModifyCount != source.modifyCount) {
        throw new ConcurrentModificationException();
      }
      source.add(index + offset, element);
      ++limit;
      ++modifyCount;
      expectedModifyCount = source.modifyCount;
    }

    @Override
    public int size() {
      if (expectedModifyCount != source.modifyCount) {
        throw new ConcurrentModificationException();
      }
      return limit;
    }

    @Override
    public void sort() {
      if (expectedModifyCount != source.modifyCount) {
        throw new ConcurrentModificationException();
      }
      source.sort(offset, offset + limit);
      ++modifyCount;
      expectedModifyCount = source.modifyCount;
    }

    @Override
    protected void sort(final int fromIndex, final int toIndex) {
      requireLessEqual("fromIndex", fromIndex, "toIndex", toIndex);
      requireInRightOpenRange("fromIndex", fromIndex, 0, limit);
      if (expectedModifyCount != source.modifyCount) {
        throw new ConcurrentModificationException();
      }
      source.sort(fromIndex + offset, toIndex + offset);
      ++modifyCount;
      expectedModifyCount = source.modifyCount;
    }
  }
}
