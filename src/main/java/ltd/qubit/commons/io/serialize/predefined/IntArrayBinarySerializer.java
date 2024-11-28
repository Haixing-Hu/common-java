////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
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

import ltd.qubit.commons.io.io.error.SerializationException;
import ltd.qubit.commons.io.io.serialize.BinarySerializer;

import static ltd.qubit.commons.io.io.InputUtils.readIntArray;
import static ltd.qubit.commons.io.io.OutputUtils.writeIntArray;

/**
 * The {@link BinarySerializer} for {@code int[]} class.
 *
 * @author Haixing Hu
 */
@Immutable
public final class IntArrayBinarySerializer implements BinarySerializer {

  public static final IntArrayBinarySerializer INSTANCE = new IntArrayBinarySerializer();

  @Override
  public int[] deserialize(final InputStream in, final boolean allowNull)
      throws IOException {
    return readIntArray(in, allowNull, null);
  }

  @Override
  public void serialize(final OutputStream out, @Nullable final Object obj)
      throws IOException {
    final int[] value;
    try {
      value = (int[]) obj;
    } catch (final ClassCastException e) {
      throw new SerializationException(e);
    }
    writeIntArray(out, value);
  }

}
