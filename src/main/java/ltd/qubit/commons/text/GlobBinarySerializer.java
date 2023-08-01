////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text;

import ltd.qubit.commons.io.OutputUtils;
import ltd.qubit.commons.io.error.SerializationException;
import ltd.qubit.commons.io.error.UnexpectedNullValueException;
import ltd.qubit.commons.io.serialize.BinarySerializer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import static ltd.qubit.commons.io.InputUtils.readInt;
import static ltd.qubit.commons.io.InputUtils.readNullMark;
import static ltd.qubit.commons.io.InputUtils.readString;

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
