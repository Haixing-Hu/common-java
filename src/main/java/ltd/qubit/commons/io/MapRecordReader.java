////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.io;

import java.util.Iterator;
import java.util.Map;

import javax.annotation.concurrent.NotThreadSafe;

import ltd.qubit.commons.reflect.ConstructorUtils;
import ltd.qubit.commons.reflect.ReflectionException;

import static ltd.qubit.commons.lang.Argument.requireNonNull;

/**
 * 一个从映射中读取记录的记录读取器。
 *
 * @param <KEY> 键的类型
 * @param <VALUE> 值的类型
 * @author 胡海星
 */
@NotThreadSafe
public final class MapRecordReader<KEY, VALUE> implements RecordReader<KEY, VALUE> {

  private final Map<KEY, VALUE> map;
  private final Class<KEY> keyClass;
  private final Class<VALUE> valueClass;
  private final long size;
  private long position;
  private Iterator<Map.Entry<KEY, VALUE>> iterator;

  /**
   * 构造一个映射记录读取器。
   *
   * @param map 要读取的映射
   * @param keyClass 键的类
   * @param valueClass 值的类
   */
  public MapRecordReader(final Map<KEY, VALUE> map, final Class<KEY> keyClass,
      final Class<VALUE> valueClass) {
    this.map = requireNonNull("map", map);
    this.keyClass = requireNonNull("keyClass", keyClass);
    this.valueClass = requireNonNull("valueClass", valueClass);
    size = map.size();
    position = 0;
    iterator = map.entrySet().iterator();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public KEY createKey() throws ReflectionException {
    return ConstructorUtils.newInstance(keyClass);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public VALUE createValue() throws ReflectionException {
    return ConstructorUtils.newInstance(valueClass);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public long getPosition() {
    return position;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public float getProgress() {
    return (float) position / (float) size;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean hasNext() {
    return iterator.hasNext();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Map.Entry<KEY, VALUE> next() {
    final Map.Entry<KEY, VALUE> result = iterator.next();
    ++position;
    return result;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void close() {
    position = 0;
    iterator = map.entrySet().iterator();
  }

}