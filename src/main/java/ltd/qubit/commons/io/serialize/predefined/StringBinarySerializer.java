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

import static ltd.qubit.commons.io.InputUtils.readString;
import static ltd.qubit.commons.io.OutputUtils.writeString;

/**
 * The {@link BinarySerializer} for {@link String} class.
 *
 * @author Haixing Hu
 */
@Immutable
public final class StringBinarySerializer implements BinarySerializer {

  public static final StringBinarySerializer INSTANCE = new StringBinarySerializer();

  @Override
  public String deserialize(final InputStream in, final boolean allowNull)
      throws IOException {
    return readString(in, allowNull);
  }

  @Override
  public void serialize(final OutputStream out, @Nullable final Object obj)
      throws IOException {
    final String value;
    try {
      value = (String) obj;
    } catch (final ClassCastException e) {
      throw new SerializationException(e);
    }
    writeString(out, value);
  }

}