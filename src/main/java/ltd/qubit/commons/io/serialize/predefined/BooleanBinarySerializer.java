////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
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

import static ltd.qubit.commons.io.InputUtils.readBooleanObject;
import static ltd.qubit.commons.io.OutputUtils.writeBooleanObject;

/**
 * The {@link BinarySerializer} for {@link Boolean} class.
 *
 * @author Haixing Hu
 */
@Immutable
public final class BooleanBinarySerializer implements BinarySerializer {

  public static final BooleanBinarySerializer INSTANCE = new BooleanBinarySerializer();

  @Override
  public Boolean deserialize(final InputStream in, final boolean allowNull)
      throws IOException {
    return readBooleanObject(in, allowNull);
  }

  @Override
  public void serialize(final OutputStream out, @Nullable final Object obj)
      throws IOException {
    final Boolean value;
    try {
      value = (Boolean) obj;
    } catch (final ClassCastException e) {
      throw new SerializationException(e);
    }
    writeBooleanObject(out, value);
  }

}
