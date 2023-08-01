////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.io;

import java.io.Closeable;
import java.io.IOException;
import javax.annotation.Nullable;

/**
 * This interface provides functions to write key-value pair records input an
 * output destination.
 *
 * @author Haixing Hu
 */
public interface RecordWriter<KEY, VALUE> extends Closeable {

  /**
   * Writes a key/value pair to the output.
   *
   * @param key
   *     the key to be written.
   * @param value
   *     the value to be written.
   * @throws IOException
   *     if any I/O error occurred.
   */
  void write(@Nullable KEY key, @Nullable VALUE value) throws IOException;
}
