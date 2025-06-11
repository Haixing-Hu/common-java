////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.io;

import java.util.Map;

import javax.annotation.Nullable;
import javax.annotation.concurrent.NotThreadSafe;

import ltd.qubit.commons.lang.Argument;

/**
 * 一个将记录写入映射的记录写入器。
 *
 * @param <KEY> 键的类型
 * @param <VALUE> 值的类型
 * @author 胡海星
 */
@NotThreadSafe
public final class MapRecordWriter<KEY, VALUE> implements RecordWriter<KEY, VALUE> {

  private final Map<KEY, VALUE> map;

  /**
   * 构造一个映射记录写入器。
   *
   * @param map 要写入的映射
   */
  public MapRecordWriter(final Map<KEY, VALUE> map) {
    this.map = Argument.requireNonNull("map", map);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void write(@Nullable final KEY key, @Nullable final VALUE value) {
    map.put(key, value);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void close() {
    // do nothing
  }

}