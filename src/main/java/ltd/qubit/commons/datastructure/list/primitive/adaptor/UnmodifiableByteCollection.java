////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.datastructure.list.primitive.adaptor;
import java.io.Serial;

import ltd.qubit.commons.datastructure.list.primitive.AbstractByteCollection;
import ltd.qubit.commons.datastructure.list.primitive.ByteCollection;
import ltd.qubit.commons.datastructure.list.primitive.ByteIterator;

import static ltd.qubit.commons.lang.Argument.requireNonNull;

/**
 * {@link ByteCollection} 的一个不可修改版本。
 *
 * @author 胡海星
 */
public class UnmodifiableByteCollection extends AbstractByteCollection {

  @Serial
  private static final long serialVersionUID = -4825833429742656519L;

  /**
   * 将一个 {@link ByteCollection} 包装成一个
   * {@link UnmodifiableByteCollection}。
   *
   * @param collection
   *     要包装的 {@link ByteCollection}。
   * @return 包装了指定集合的 {@link UnmodifiableByteCollection}。
   */
  public static UnmodifiableByteCollection wrap(final ByteCollection collection) {
    if (collection instanceof UnmodifiableByteCollection) {
      return (UnmodifiableByteCollection) collection;
    } else {
      return new UnmodifiableByteCollection(collection);
    }
  }

  /**
   * 被包装的{@link ByteCollection}。
   */
  private final ByteCollection collection;

  /**
   * 构造一个{@link UnmodifiableByteCollection}。
   *
   * @param collection
   *     要包装的{@link ByteCollection}。
   */
  protected UnmodifiableByteCollection(final ByteCollection collection) {
    this.collection = requireNonNull("collection", collection);
  }

  /**
   * 不支持此操作。
   *
   * @param element
   *     要添加的元素。
   * @return 此调用总会抛出 {@link UnsupportedOperationException}。
   * @throws UnsupportedOperationException
   *     总是抛出。
   */
  @Override
  public boolean add(final byte element) {
    throw new UnsupportedOperationException();
  }

  /**
   * 不支持此操作。
   *
   * @param c
   *     要添加的元素集合。
   * @return 此调用总会抛出 {@link UnsupportedOperationException}。
   * @throws UnsupportedOperationException
   *     总是抛出。
   */
  @Override
  public boolean addAll(final ByteCollection c) {
    throw new UnsupportedOperationException();
  }

  /**
   * 不支持此操作。
   *
   * @throws UnsupportedOperationException
   *     总是抛出。
   */
  @Override
  public void clear() {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean contains(final byte element) {
    return collection.contains(element);
  }

  @Override
  public boolean containsAll(final ByteCollection c) {
    return collection.containsAll(c);
  }

  @Override
  public boolean isEmpty() {
    return collection.isEmpty();
  }

  @Override
  public ByteIterator iterator() {
    return new UnmodifiableByteIterator(collection.iterator());
  }

  /**
   * 不支持此操作。
   *
   * @param c
   *     要移除的元素集合。
   * @return 此调用总会抛出 {@link UnsupportedOperationException}。
   * @throws UnsupportedOperationException
   *     总是抛出。
   */
  @Override
  public boolean removeAll(final ByteCollection c) {
    throw new UnsupportedOperationException();
  }

  /**
   * 不支持此操作。
   *
   * @param element
   *     要移除的元素。
   * @return 此调用总会抛出 {@link UnsupportedOperationException}。
   * @throws UnsupportedOperationException
   *     总是抛出。
   */
  @Override
  public boolean remove(final byte element) {
    throw new UnsupportedOperationException();
  }

  /**
   * 不支持此操作。
   *
   * @param c
   *     要保留的元素集合。
   * @return 此调用总会抛出 {@link UnsupportedOperationException}。
   * @throws UnsupportedOperationException
   *     总是抛出。
   */
  @Override
  public boolean retainAll(final ByteCollection c) {
    throw new UnsupportedOperationException();
  }

  @Override
  public int size() {
    return collection.size();
  }

  @Override
  public byte[] toArray() {
    return collection.toArray();
  }

  @Override
  public byte[] toArray(final byte[] a) {
    return collection.toArray(a);
  }
}
