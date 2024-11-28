////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.io.io.serialize.predefined;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.io.io.error.SerializationException;
import ltd.qubit.commons.io.io.serialize.BinarySerializer;

import static ltd.qubit.commons.io.io.InputUtils.readClass;
import static ltd.qubit.commons.io.io.OutputUtils.writeClass;

/**
 * The {@link BinarySerializer} for {@link Class} class.
 *
 * @author Haixing Hu
 */
@Immutable
public final class ClassBinarySerializer implements BinarySerializer {

  public static final ClassBinarySerializer INSTANCE = new ClassBinarySerializer();

  @Override
  public Class<?> deserialize(final InputStream in, final boolean allowNull)
      throws IOException {
    return readClass(in, allowNull);
  }

  @Override
  public void serialize(final OutputStream out, @Nullable final Object obj)
      throws IOException {
    final Class<?> value;
    try {
      value = (Class<?>) obj;
    } catch (final ClassCastException e) {
      throw new SerializationException(e);
    }
    writeClass(out, value);
  }

}
