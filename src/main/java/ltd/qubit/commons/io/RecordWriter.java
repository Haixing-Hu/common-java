////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.io;

import java.io.Closeable;
import java.io.IOException;

import javax.annotation.Nullable;

/**
 * 此接口提供将键值对记录写入输出目标的功能。
 *
 * @author 胡海星
 */
public interface RecordWriter<KEY, VALUE> extends Closeable {

  /**
   * 将键值对写入输出。
   *
   * @param key
   *     要写入的键。
   * @param value
   *     要写入的值。
   * @throws IOException
   *     如果发生任何 I/O 错误。
   */
  void write(@Nullable KEY key, @Nullable VALUE value) throws IOException;
}