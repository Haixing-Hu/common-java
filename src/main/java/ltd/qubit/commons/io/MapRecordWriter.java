////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.io;

import ltd.qubit.commons.lang.Argument;

import java.util.Map;
import javax.annotation.Nullable;
import javax.annotation.concurrent.NotThreadSafe;

/**
 * A RecordWriter which writes records into a map.
 *
 * @author Haixing Hu
 */
@NotThreadSafe
public final class MapRecordWriter<KEY, VALUE> implements RecordWriter<KEY, VALUE> {

  private final Map<KEY, VALUE> map;

  public MapRecordWriter(final Map<KEY, VALUE> map) {
    this.map = Argument.requireNonNull("map", map);
  }

  @Override
  public void write(@Nullable final KEY key, @Nullable final VALUE value) {
    map.put(key, value);
  }

  @Override
  public void close() {
    // do nothing
  }

}
