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

import static ltd.qubit.commons.io.InputUtils.readChar;
import static ltd.qubit.commons.io.InputUtils.readNullMark;
import static ltd.qubit.commons.io.InputUtils.readVarInt;
import static ltd.qubit.commons.io.OutputUtils.writeChar;
import static ltd.qubit.commons.io.OutputUtils.writeNullMark;
import static ltd.qubit.commons.io.OutputUtils.writeVarInt;

/**
 * The {@link BinarySerializer} for the {@link CharBuffer} class.
 *
 * @author Haixing Hu
 */
@Immutable
public final class CharBufferBinarySerializer implements BinarySerializer {

  public static final CharBufferBinarySerializer INSTANCE =
      new CharBufferBinarySerializer();

  @Override
  public CharBuffer deserialize(final InputStream in, final boolean allowNull)
      throws IOException {
    if (readNullMark(in)) {
      if (allowNull) {
        return null;
      } else {
        throw new UnexpectedNullValueException();
      }
    }
    final CharBuffer result = new CharBuffer();
    final int n = readVarInt(in);
    if (n > 0) {
      result.buffer = new char[n];
      for (int i = 0; i < n; ++i) {
        result.buffer[i] = readChar(in);
      }
      result.length = n;
    }
    return result;
  }

  @Override
  public void serialize(final OutputStream out, @Nullable final Object obj)
      throws IOException {
    if (! writeNullMark(out, obj)) {
      assert (obj != null);
      final CharBuffer buf;
      try {
        buf = (CharBuffer) obj;
      } catch (final ClassCastException e) {
        throw new SerializationException(e);
      }
      writeVarInt(out, buf.length);
      for (int i = 0; i < buf.length; ++i) {
        writeChar(out, buf.buffer[i]);
      }
    }
  }

}
