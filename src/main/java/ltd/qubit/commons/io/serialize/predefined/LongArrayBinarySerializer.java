////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.io.serialize.predefined;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.io.error.SerializationException;
import ltd.qubit.commons.io.serialize.BinarySerializer;

import static ltd.qubit.commons.io.InputUtils.readLongArray;
import static ltd.qubit.commons.io.OutputUtils.writeLongArray;

/**
 * The {@link BinarySerializer} for {@code long[]} class.
 *
 * @author Haixing Hu
 */
@Immutable
public final class LongArrayBinarySerializer implements BinarySerializer {

  public static final LongArrayBinarySerializer INSTANCE = new LongArrayBinarySerializer();

  @Override
  public long[] deserialize(final InputStream in, final boolean allowNull)
      throws IOException {
    return readLongArray(in, allowNull, null);
  }

  @Override
  public void serialize(final OutputStream out, @Nullable final Object obj)
      throws IOException {
    final long[] value;
    try {
      value = (long[]) obj;
    } catch (final ClassCastException e) {
      throw new SerializationException(e);
    }
    writeLongArray(out, value);
  }

}
