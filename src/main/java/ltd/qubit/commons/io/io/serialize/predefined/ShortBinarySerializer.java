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

import static ltd.qubit.commons.io.io.InputUtils.readShortObject;
import static ltd.qubit.commons.io.io.OutputUtils.writeShortObject;

/**
 * The {@link BinarySerializer} for {@link Short} class.
 *
 * @author Haixing Hu
 */
@Immutable
public final class ShortBinarySerializer implements BinarySerializer {

  public static final ShortBinarySerializer INSTANCE = new ShortBinarySerializer();

  @Override
  public Short deserialize(final InputStream in, final boolean allowNull)
      throws IOException {
    return readShortObject(in, allowNull);
  }

  @Override
  public void serialize(final OutputStream out, @Nullable final Object obj)
      throws IOException {
    final Short value;
    try {
      value = (Short) obj;
    } catch (final ClassCastException e) {
      throw new SerializationException(e);
    }
    writeShortObject(out, value);
  }

}
