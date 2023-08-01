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

import static ltd.qubit.commons.io.InputUtils.readCharArray;
import static ltd.qubit.commons.io.OutputUtils.writeCharArray;

/**
 * The {@link BinarySerializer} for {@code char[]} class.
 *
 * @author Haixing Hu
 */
@Immutable
public final class CharArrayBinarySerializer implements BinarySerializer {

  public static final CharArrayBinarySerializer INSTANCE = new CharArrayBinarySerializer();

  @Override
  public char[] deserialize(final InputStream in, final boolean allowNull)
      throws IOException {
    return readCharArray(in, allowNull, null);
  }

  @Override
  public void serialize(final OutputStream out, @Nullable final Object obj)
      throws IOException {
    final char[] value;
    try {
      value = (char[]) obj;
    } catch (final ClassCastException e) {
      throw new SerializationException(e);
    }
    writeCharArray(out, value);
  }

}
