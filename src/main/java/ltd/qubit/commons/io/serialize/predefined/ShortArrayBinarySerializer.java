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

import ltd.qubit.commons.io.error.SerializationException;
import ltd.qubit.commons.io.serialize.BinarySerializer;

import static ltd.qubit.commons.io.InputUtils.readShortArray;
import static ltd.qubit.commons.io.OutputUtils.writeShortArray;

/**
 * The {@link BinarySerializer} for {@code short[]} class.
 *
 * @author Haixing Hu
 */
@Immutable
public final class ShortArrayBinarySerializer implements BinarySerializer {

  public static final ShortArrayBinarySerializer INSTANCE = new ShortArrayBinarySerializer();

  @Override
  public short[] deserialize(final InputStream in, final boolean allowNull)
      throws IOException {
    return readShortArray(in, allowNull, null);
  }

  @Override
  public void serialize(final OutputStream out, @Nullable final Object obj)
      throws IOException {
    final short[] value;
    try {
      value = (short[]) obj;
    } catch (final ClassCastException e) {
      throw new SerializationException(e);
    }
    writeShortArray(out, value);
  }

}
