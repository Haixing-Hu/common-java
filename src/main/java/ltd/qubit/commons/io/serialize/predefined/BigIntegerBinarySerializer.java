////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.io.serialize.predefined;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.io.error.SerializationException;
import ltd.qubit.commons.io.serialize.BinarySerializer;

import static ltd.qubit.commons.io.InputUtils.readBigInteger;
import static ltd.qubit.commons.io.OutputUtils.writeBigInteger;

/**
 * The {@link BinarySerializer} for {@link BigInteger} class.
 *
 * @author Haixing Hu
 */
@Immutable
public final class BigIntegerBinarySerializer implements BinarySerializer {

  public static final BigIntegerBinarySerializer INSTANCE = new BigIntegerBinarySerializer();

  @Override
  public BigInteger deserialize(final InputStream in, final boolean allowNull)
      throws IOException {
    return readBigInteger(in, allowNull);
  }

  @Override
  public void serialize(final OutputStream out, @Nullable final Object obj)
      throws IOException {
    final BigInteger value;
    try {
      value = (BigInteger) obj;
    } catch (final ClassCastException e) {
      throw new SerializationException(e);
    }
    writeBigInteger(out, value);
  }

}
