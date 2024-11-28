////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.datastructure.list.primitive;

import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

import static ltd.qubit.commons.lang.Argument.requireInCloseRange;
import static ltd.qubit.commons.lang.Argument.requireInRightOpenRange;
import static ltd.qubit.commons.lang.Argument.requireIndexInCloseRange;
import static ltd.qubit.commons.lang.Argument.requireLessEqual;

/**
 * Abstract base class for {@link CharList}s backed by random access structures
 * like arrays.
 *
 * <p>Read-only subclasses must override {@link #get} and {@link #size}. Mutable
 * subclasses should also override {@link #set}. Variably-sized subclasses
 * should also override {@link #add(char)} and {@link #removeAt}. All
 * other methods have at least some base implementation derived from these.
 * Subclasses may choose to override these methods to provide a more efficient
 * implementation.
 *
 * @author Haixing Hu
 */
public abstract class RandomAccessCharList extends AbstractCharList {

  private static final long serialVersionUID = -8135626327664674112L;

  public static final int DEFAULT_INITIAL_CAPACITY = 8;

  @Override
  public CharListIterator listIterator(final int index) {
    requireIndexInCloseRange(index, 0, size());
    return new RandomAccessCharListIterator(this, index);
  }

  @Override
  public CharList subList(final int fromIndex, final int toIndex) {
    final int n = size();
    requireInCloseRange("fromIndex", fromIndex, 0, n);
    requireInCloseRange("toIndex", toIndex, fromIndex, n);
    return new RandomAccessCharSubList(this, fromIndex, toIndex);
  }

  @Override
  public void sort() {
    sort(0, size());
  }

  protected abstract void sort(final int fromIndex, final int toIndex);


  protected static class RandomAccessCharListIterator implements CharListIterator {

    protected RandomAccessCharList source;
    protected int nextIndex;
    protected int lastReturnedIndex;
    protected int expectedModifyCount;

    RandomAccessCharListIterator(final RandomAccessCharList list, final int index) {
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
    public char next() {
      if (expectedModifyCount != source.modifyCount) {
        throw new ConcurrentModificationException();
      }
      if (! hasNext()) {
        throw new NoSuchElementException();
      } else {
        final char val = source.get(nextIndex);
        lastReturnedIndex = nextIndex;
        ++nextIndex;
        return val;
      }
    }

    @Override
    public char previous() {
      if (expectedModifyCount != source.modifyCount) {
        throw new ConcurrentModificationException();
      }
      if (! hasPrevious()) {
        throw new NoSuchElementException();
      } else {
        final char val = source.get(nextIndex - 1);
        lastReturnedIndex = nextIndex - 1;
        --nextIndex;
        return val;
      }
    }

    @Override
    public void add(final char value) {
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
    public void set(final char value) {
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

  protected static class RandomAccessCharSubList extends RandomAccessCharList {

    private static final long serialVersionUID = -2300466981832206104L;

    protected final int offset;
    protected int limit;
    protected final RandomAccessCharList source;
    protected int expectedModifyCount;

    RandomAccessCharSubList(final RandomAccessCharList list, final int fromIndex,
        final int toIndex) {
      source = list;
      offset = fromIndex;
      limit = toIndex - fromIndex;
      expectedModifyCount = list.modifyCount;
    }

    @Override
    public char get(final int index) {
      requireInRightOpenRange("index", index, 0, limit);
      if (expectedModifyCount != source.modifyCount) {
        throw new ConcurrentModificationException();
      }
      return source.get(index + offset);
    }

    @Override
    public char removeAt(final int index) {
      requireInRightOpenRange("index", index, 0, limit);
      if (expectedModifyCount != source.modifyCount) {
        throw new ConcurrentModificationException();
      }
      final char val = source.removeAt(index + offset);
      --limit;
      expectedModifyCount = source.modifyCount;
      ++modifyCount;
      return val;
    }

    @Override
    public char set(final int index, final char element) {
      requireInRightOpenRange("index", index, 0, limit);
      if (expectedModifyCount != source.modifyCount) {
        throw new ConcurrentModificationException();
      }
      final char val = source.set(index + offset, element);
      ++modifyCount;
      expectedModifyCount = source.modifyCount;
      return val;
    }

    @Override
    public void add(final int index, final char element) {
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
