////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.datastructure.list.primitive.impl;

import ltd.qubit.commons.datastructure.list.primitive.ByteCollection;
import ltd.qubit.commons.datastructure.list.primitive.ByteIterator;
import ltd.qubit.commons.datastructure.list.primitive.ByteList;
import ltd.qubit.commons.datastructure.list.primitive.RandomAccessByteList;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;

import static ltd.qubit.commons.lang.Argument.requireIndexInCloseRange;
import static ltd.qubit.commons.lang.Argument.requireIndexInRightOpenRange;

import static java.lang.System.arraycopy;

/**
 * An {@link ByteList} backed by an array of {@code byte}s. This implementation
 * supports all optional methods.
 *
 * @author Haixing Hu
 */
public class ByteArrayList extends RandomAccessByteList {

  private static final long serialVersionUID = -3623502616203201839L;

  private transient byte[] data;
  private int size;

  /**
   * Construct an empty list with the default initial capacity.
   */
  public ByteArrayList() {
    this(DEFAULT_INITIAL_CAPACITY);
  }

  /**
   * Construct an empty list with the given initial capacity.
   *
   * @param initialCapacity
   *     the initial capacity.
   * @throws IllegalArgumentException
   *     when <i>initialCapacity</i> is negative
   */
  public ByteArrayList(final int initialCapacity) {
    if (initialCapacity < 0) {
      throw new IllegalArgumentException("capacity " + initialCapacity);
    }
    data = new byte[initialCapacity];
    size = 0;
  }

  /**
   * Constructs a list containing the elements of the given collection, in the
   * order they are returned by that collection's iterator.
   *
   * @param that
   *     the non-{@code null} collection of {@code byte}s to add
   * @throws NullPointerException
   *     if <i>that</i> is {@code null}
   * @see ByteArrayList#addAll(ByteCollection)
   */
  public ByteArrayList(final ByteCollection that) {
    this(that.size());
    addAll(that);
  }

  /**
   * Constructs a list by copying the specified array.
   *
   * @param array
   *     the array to initialize the collection with
   * @throws NullPointerException
   *     if the array is {@code null}
   */
  public ByteArrayList(final byte[] array) {
    this(array.length);
    arraycopy(array, 0, data, 0, array.length);
    size = array.length;
  }

  // ByteList methods
  // -------------------------------------------------------------------------

  @Override
  public byte get(final int index) {
    requireIndexInRightOpenRange(index, 0, size);
    return data[index];
  }

  @Override
  public int size() {
    return size;
  }

  /**
   * Removes the element at the specified position in (optional operation). Any
   * subsequent elements are shifted to the left, subtracting one from their
   * indices. Returns the element that was removed.
   *
   * @param index
   *     the index of the element to remove
   * @return the value of the element that was removed
   * @throws UnsupportedOperationException
   *     when this operation is not supported
   * @throws IndexOutOfBoundsException
   *     if the specified index is out of range
   */
  @Override
  public byte removeAt(final int index) {
    requireIndexInRightOpenRange(index, 0, size);
    ++modifyCount;
    final byte oldval = data[index];
    final int numtomove = size - index - 1;
    if (numtomove > 0) {
      arraycopy(data, index + 1, data, index, numtomove);
    }
    size--;
    return oldval;
  }

  /**
   * Replaces the element at the specified position in me with the specified
   * element (optional operation).
   *
   * @param index
   *     the index of the element to change
   * @param element
   *     the value to be stored at the specified position
   * @return the value previously stored at the specified position
   * @throws UnsupportedOperationException
   *     when this operation is not supported
   * @throws IndexOutOfBoundsException
   *     if the specified index is out of range
   */
  @Override
  public byte set(final int index, final byte element) {
    requireIndexInRightOpenRange(index, 0, size);
    ++modifyCount;
    final byte oldval = data[index];
    data[index] = element;
    return oldval;
  }

  /**
   * Inserts the specified element at the specified position (optional
   * operation). Shifts the element currently at that position (if any) and any
   * subsequent elements to the right, increasing their indices.
   *
   * @param index
   *     the index at which to insert the element
   * @param element
   *     the value to insert
   * @throws UnsupportedOperationException
   *     when this operation is not supported
   * @throws IllegalArgumentException
   *     if some aspect of the specified element prevents it from being added to
   *     me
   * @throws IndexOutOfBoundsException
   *     if the specified index is out of range
   */
  @Override
  public void add(final int index, final byte element) {
    requireIndexInCloseRange(index, 0, size);
    ++modifyCount;
    ensureCapacity(size + 1);
    final int numtomove = size - index;
    arraycopy(data, index, data, index + 1, numtomove);
    data[index] = element;
    size++;
  }

  @Override
  public void clear() {
    ++modifyCount;
    size = 0;
  }

  @Override
  public boolean addAll(final ByteCollection collection) {
    return addAll(size(), collection);
  }

  @Override
  public boolean addAll(final int index, final ByteCollection collection) {
    if (collection.size() == 0) {
      return false;
    }
    requireIndexInCloseRange(index, 0, size);
    ++modifyCount;
    ensureCapacity(size + collection.size());
    if (index != size) {
      // Need to move some elements
      arraycopy(data, index, data, index + collection.size(), size - index);
    }
    int i = index;
    for (final ByteIterator it = collection.iterator(); it.hasNext(); ++i) {
      data[i] = it.next();
    }
    size += collection.size();
    return true;
  }

  // capacity methods
  // -------------------------------------------------------------------------

  /**
   * Increases my capacity, if necessary, to ensure that I can hold at least the
   * number of elements specified by the minimum capacity argument without
   * growing.
   *
   * @param mincap
   *     the minimal capacity.
   */
  public void ensureCapacity(final int mincap) {
    ++modifyCount;
    if (mincap > data.length) {
      final int newcap = ((data.length * 3) / 2) + 1;
      final byte[] olddata = data;
      data = new byte[newcap < mincap ? mincap : newcap];
      arraycopy(olddata, 0, data, 0, size);
    }
  }

  /**
   * Reduce my capacity, if necessary, to match my current {@link #size size}.
   */
  public void trimToSize() {
    ++modifyCount;
    if (size < data.length) {
      final byte[] olddata = data;
      data = new byte[size];
      arraycopy(olddata, 0, data, 0, size);
    }
  }

  private void writeObject(final ObjectOutputStream out) throws IOException {
    out.defaultWriteObject();
    out.writeInt(data.length);
    for (int i = 0; i < size; i++) {
      out.writeByte(data[i]);
    }
  }

  private void readObject(final ObjectInputStream in) throws IOException,
      ClassNotFoundException {
    in.defaultReadObject();
    data = new byte[in.readInt()];
    for (int i = 0; i < size; i++) {
      data[i] = in.readByte();
    }
  }

  @Override
  protected void sort(final int fromIndex, final int toIndex) {
    Arrays.sort(data, fromIndex, toIndex);
  }
}
