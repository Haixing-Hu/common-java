////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.buffer;

import ltd.qubit.commons.io.error.SerializationException;
import ltd.qubit.commons.io.error.UnexpectedNullValueException;
import ltd.qubit.commons.io.serialize.BinarySerializer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import static ltd.qubit.commons.io.InputUtils.readByte;
import static ltd.qubit.commons.io.InputUtils.readNullMark;
import static ltd.qubit.commons.io.InputUtils.readVarInt;
import static ltd.qubit.commons.io.OutputUtils.writeByte;
import static ltd.qubit.commons.io.OutputUtils.writeNullMark;
import static ltd.qubit.commons.io.OutputUtils.writeVarInt;

/**
 * The {@link BinarySerializer} for the {@link ByteBuffer} class.
 *
 * @author Haixing Hu
 */
@Immutable
public final class ByteBufferBinarySerializer implements BinarySerializer {

  public static final ByteBufferBinarySerializer INSTANCE =
      new ByteBufferBinarySerializer();

  @Override
  public ByteBuffer deserialize(final InputStream in, final boolean allowNull)
      throws IOException {
    if (readNullMark(in)) {
      if (allowNull) {
        return null;
      } else {
        throw new UnexpectedNullValueException();
      }
    }
    final ByteBuffer result = new ByteBuffer();
    final int n = readVarInt(in);
    if (n > 0) {
      result.buffer = new byte[n];
      for (int i = 0; i < n; ++i) {
        result.buffer[i] = readByte(in);
      }
      result.length = n;
    }
    return result;
  }

  @Override
  public void serialize(final OutputStream out, @Nullable final Object obj)
      throws IOException {
    if (! writeNullMark(out, obj)) {
      final ByteBuffer buffer;
      try {
        buffer = (ByteBuffer) obj;
      } catch (final ClassCastException e) {
        throw new SerializationException(e);
      }
      writeVarInt(out, buffer.length);
      for (int i = 0; i < buffer.length; ++i) {
        writeByte(out, buffer.buffer[i]);
      }
    }
  }

}
