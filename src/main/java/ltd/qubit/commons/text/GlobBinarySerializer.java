////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.io.io.OutputUtils;
import ltd.qubit.commons.io.io.error.SerializationException;
import ltd.qubit.commons.io.io.error.UnexpectedNullValueException;
import ltd.qubit.commons.io.io.serialize.BinarySerializer;

import static ltd.qubit.commons.io.io.InputUtils.readInt;
import static ltd.qubit.commons.io.io.InputUtils.readNullMark;
import static ltd.qubit.commons.io.io.InputUtils.readString;

/**
 * The {@link BinarySerializer} for the {@link Glob} class.
 *
 * @author Haixing Hu
 */
@Immutable
public final class GlobBinarySerializer implements BinarySerializer {

  public static final GlobBinarySerializer INSTANCE = new GlobBinarySerializer();

  @Override
  public Glob deserialize(final InputStream in, final boolean allowNull)
      throws IOException {
    if (readNullMark(in)) {
      if (allowNull) {
        return null;
      } else {
        throw new UnexpectedNullValueException();
      }
    } else {
      final String pattern = readString(in, false);
      final int flags = readInt(in);
      return new Glob(pattern, flags);
    }
  }

  @Override
  public void serialize(final OutputStream out, @Nullable final Object obj)
      throws IOException {
    if (! OutputUtils.writeNullMark(out, obj)) {
      final Glob glob;
      try {
        glob = (Glob) obj;
      } catch (final ClassCastException e) {
        throw new SerializationException(e);
      }
      OutputUtils.writeString(out, glob.pattern);
      OutputUtils.writeInt(out, glob.flags);
    }
  }
}
