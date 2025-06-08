////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
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

import static ltd.qubit.commons.io.InputUtils.readByteArray;
import static ltd.qubit.commons.io.OutputUtils.writeByteArray;

/**
 * The {@link BinarySerializer} for {@code byte[]} class.
 *
 * @author Haixing Hu
 */
@Immutable
public final class ByteArrayBinarySerializer implements BinarySerializer {

  public static final ByteArrayBinarySerializer INSTANCE = new ByteArrayBinarySerializer();

  @Override
  public byte[] deserialize(final InputStream in, final boolean allowNull)
      throws IOException {
    return readByteArray(in, allowNull, null);
  }

  @Override
  public void serialize(final OutputStream out, @Nullable final Object obj)
      throws IOException {
    final byte[] value;
    try {
      value = (byte[]) obj;
    } catch (final ClassCastException e) {
      throw new SerializationException(e);
    }
    writeByteArray(out, value);
  }

}