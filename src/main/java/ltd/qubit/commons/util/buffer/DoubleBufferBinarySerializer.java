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

import ltd.qubit.commons.io.io.error.SerializationException;
import ltd.qubit.commons.io.io.error.UnexpectedNullValueException;
import ltd.qubit.commons.io.io.serialize.BinarySerializer;

import static ltd.qubit.commons.io.io.InputUtils.readDouble;
import static ltd.qubit.commons.io.io.InputUtils.readNullMark;
import static ltd.qubit.commons.io.io.InputUtils.readVarInt;
import static ltd.qubit.commons.io.io.OutputUtils.writeDouble;
import static ltd.qubit.commons.io.io.OutputUtils.writeNullMark;
import static ltd.qubit.commons.io.io.OutputUtils.writeVarInt;

/**
 * The {@link BinarySerializer} for the {@link DoubleBuffer} class.
 *
 * @author Haixing Hu
 */
@Immutable
public final class DoubleBufferBinarySerializer implements BinarySerializer {

  public static final DoubleBufferBinarySerializer INSTANCE =
      new DoubleBufferBinarySerializer();

  @Override
  public DoubleBuffer deserialize(final InputStream in, final boolean allowNull)
      throws IOException {
    if (readNullMark(in)) {
      if (allowNull) {
        return null;
      } else {
        throw new UnexpectedNullValueException();
      }
    }
    final DoubleBuffer result = new DoubleBuffer();
    final int n = readVarInt(in);
    if (n > 0) {
      result.buffer = new double[n];
      for (int i = 0; i < n; ++i) {
        result.buffer[i] = readDouble(in);
      }
      result.length = n;
    }
    return result;
  }

  @Override
  public void serialize(final OutputStream out, @Nullable final Object obj)
      throws IOException {
    if (! writeNullMark(out, obj)) {
      final DoubleBuffer buffer;
      try {
        buffer = (DoubleBuffer) obj;
      } catch (final ClassCastException e) {
        throw new SerializationException(e);
      }
      writeVarInt(out, buffer.length);
      for (int i = 0; i < buffer.length; ++i) {
        writeDouble(out, buffer.buffer[i]);
      }
    }
  }

}
