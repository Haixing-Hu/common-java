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
 * A RecordReader which reads records form a map.
 *
 * @author Haixing Hu
 */
@NotThreadSafe
public final class MapRecordReader<KEY, VALUE> implements RecordReader<KEY, VALUE> {

  private final Map<KEY, VALUE> map;
  private final Class<KEY> keyClass;
  private final Class<VALUE> valueClass;
  private final long size;
  private long position;
  private Iterator<Map.Entry<KEY, VALUE>> iterator;

  public MapRecordReader(final Map<KEY, VALUE> map, final Class<KEY> keyClass,
      final Class<VALUE> valueClass) {
    this.map = requireNonNull("map", map);
    this.keyClass = requireNonNull("keyClass", keyClass);
    this.valueClass = requireNonNull("valueClass", valueClass);
    size = map.size();
    position = 0;
    iterator = map.entrySet().iterator();
  }

  @Override
  public KEY createKey() throws ReflectionException {
    return ConstructorUtils.newInstance(keyClass);
  }

  @Override
  public VALUE createValue() throws ReflectionException {
    return ConstructorUtils.newInstance(valueClass);
  }

  @Override
  public long getPosition() {
    return position;
  }

  @Override
  public float getProgress() {
    return (float) position / (float) size;
  }

  @Override
  public boolean hasNext() {
    return iterator.hasNext();
  }

  @Override
  public Map.Entry<KEY, VALUE> next() {
    final Map.Entry<KEY, VALUE> result = iterator.next();
    ++position;
    return result;
  }

  @Override
  public void close() {
    position = 0;
    iterator = map.entrySet().iterator();
  }

}