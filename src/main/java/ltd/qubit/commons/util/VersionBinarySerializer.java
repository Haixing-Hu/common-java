////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.io.error.SerializationException;
import ltd.qubit.commons.io.error.UnexpectedNullValueException;
import ltd.qubit.commons.io.serialize.BinarySerializer;

import static ltd.qubit.commons.io.InputUtils.readInt;
import static ltd.qubit.commons.io.InputUtils.readNullMark;
import static ltd.qubit.commons.io.OutputUtils.writeInt;
import static ltd.qubit.commons.io.OutputUtils.writeNullMark;

/**
 * The {@link BinarySerializer} for the {@link Version} class.
 *
 * @author Haixing Hu
 */
@Immutable
public final class VersionBinarySerializer implements BinarySerializer {

  public static final VersionBinarySerializer INSTANCE = new VersionBinarySerializer();

  @Override
  public Version deserialize(final InputStream in, final boolean allowNull)
      throws IOException {
    if (readNullMark(in)) {
      if (allowNull) {
        return null;
      } else {
        throw new UnexpectedNullValueException();
      }
    } else {
      final int number = readInt(in);
      return new Version(number, true);
    }
  }

  @Override
  public void serialize(final OutputStream out, @Nullable final Object obj)
      throws IOException {
    if (! writeNullMark(out, obj)) {
      final Version version;
      try {
        version = (Version) obj;
      } catch (final ClassCastException e) {
        throw new SerializationException(e);
      }
      writeInt(out, version.number);
    }
  }

}