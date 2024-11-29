////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.buffer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.io.error.SerializationException;
import ltd.qubit.commons.io.error.UnexpectedNullValueException;
import ltd.qubit.commons.io.serialize.BinarySerializer;

import static ltd.qubit.commons.io.InputUtils.readNullMark;
import static ltd.qubit.commons.io.InputUtils.readShort;
import static ltd.qubit.commons.io.InputUtils.readVarInt;
import static ltd.qubit.commons.io.OutputUtils.writeNullMark;
import static ltd.qubit.commons.io.OutputUtils.writeShort;
import static ltd.qubit.commons.io.OutputUtils.writeVarInt;

/**
 * The {@link BinarySerializer} for the {@link ShortBuffer} class.
 *
 * @author Haixing Hu
 */
@Immutable
public final class ShortBufferBinarySerializer implements BinarySerializer {

  public static final ShortBufferBinarySerializer INSTANCE =
      new ShortBufferBinarySerializer();

  @Override
  public ShortBuffer deserialize(final InputStream in, final boolean allowNull)
      throws IOException {
    if (readNullMark(in)) {
      if (allowNull) {
        return null;
      } else {
        throw new UnexpectedNullValueException();
      }
    }
    final ShortBuffer result = new ShortBuffer();
    final int n = readVarInt(in);
    if (n > 0) {
      result.buffer = new short[n];
      for (int i = 0; i < n; ++i) {
        result.buffer[i] = readShort(in);
      }
      result.length = n;
    }
    return result;
  }

  @Override
  public void serialize(final OutputStream out, @Nullable final Object obj)
      throws IOException {
    if (! writeNullMark(out, obj)) {
      final ShortBuffer buffer;
      try {
        buffer = (ShortBuffer) obj;
      } catch (final ClassCastException e) {
        throw new SerializationException(e);
      }
      writeVarInt(out, buffer.length);
      for (int i = 0; i < buffer.length; ++i) {
        writeShort(out, buffer.buffer[i]);
      }
    }
  }

}
