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

import static ltd.qubit.commons.io.InputUtils.readCharObject;
import static ltd.qubit.commons.io.OutputUtils.writeCharObject;

/**
 * The {@link BinarySerializer} for {@link Character} class.
 *
 * @author Haixing Hu
 */
@Immutable
public final class CharacterBinarySerializer implements BinarySerializer {

  public static final CharacterBinarySerializer INSTANCE = new CharacterBinarySerializer();

  @Override
  public Character deserialize(final InputStream in, final boolean allowNull)
      throws IOException {
    return readCharObject(in, allowNull);
  }

  @Override
  public void serialize(final OutputStream out, @Nullable final Object obj)
      throws IOException {
    final Character value;
    try {
      value = (Character) obj;
    } catch (final ClassCastException e) {
      throw new SerializationException(e);
    }
    writeCharObject(out, value);
  }

}