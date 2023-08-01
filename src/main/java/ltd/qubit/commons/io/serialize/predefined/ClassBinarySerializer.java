////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.io.serialize.predefined;

import ltd.qubit.commons.io.error.SerializationException;
import ltd.qubit.commons.io.serialize.BinarySerializer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import static ltd.qubit.commons.io.InputUtils.readClass;
import static ltd.qubit.commons.io.OutputUtils.writeClass;

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
