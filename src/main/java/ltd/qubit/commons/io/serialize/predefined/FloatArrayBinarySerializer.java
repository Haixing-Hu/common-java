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

import static ltd.qubit.commons.io.InputUtils.readFloatArray;
import static ltd.qubit.commons.io.OutputUtils.writeFloatArray;

/**
 * The {@link BinarySerializer} for {@code float[]} class.
 *
 * @author Haixing Hu
 */
@Immutable
public final class FloatArrayBinarySerializer implements BinarySerializer {

  public static final FloatArrayBinarySerializer INSTANCE = new FloatArrayBinarySerializer();

  @Override
  public float[] deserialize(final InputStream in, final boolean allowNull)
      throws IOException {
    return readFloatArray(in, allowNull, null);
  }

  @Override
  public void serialize(final OutputStream out, @Nullable final Object obj)
      throws IOException {
    final float[] value;
    try {
      value = (float[]) obj;
    } catch (final ClassCastException e) {
      throw new SerializationException(e);
    }
    writeFloatArray(out, value);
  }

}