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

import static ltd.qubit.commons.io.io.InputUtils.readBooleanArray;
import static ltd.qubit.commons.io.io.OutputUtils.writeBooleanArray;

/**
 * The {@link BinarySerializer} for {@code boolean[]} class.
 *
 * @author Haixing Hu
 */
@Immutable
public final class BooleanArrayBinarySerializer implements BinarySerializer {

  public static final BooleanArrayBinarySerializer INSTANCE = new BooleanArrayBinarySerializer();

  @Override
  public boolean[] deserialize(final InputStream in, final boolean allowNull)
      throws IOException {
    return readBooleanArray(in, allowNull, null);
  }

  @Override
  public void serialize(final OutputStream out, @Nullable final Object obj)
      throws IOException {
    final boolean[] value;
    try {
      value = (boolean[]) obj;
    } catch (final ClassCastException e) {
      throw new SerializationException(e);
    }
    writeBooleanArray(out, value);
  }

}
