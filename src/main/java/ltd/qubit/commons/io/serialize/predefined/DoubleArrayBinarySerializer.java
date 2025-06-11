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

import static ltd.qubit.commons.io.InputUtils.readDoubleArray;
import static ltd.qubit.commons.io.OutputUtils.writeDoubleArray;

/**
 * {@code double[]} 的 {@link BinarySerializer}。
 *
 * @author 胡海星
 */
@Immutable
public final class DoubleArrayBinarySerializer implements BinarySerializer {

  public static final DoubleArrayBinarySerializer INSTANCE = new DoubleArrayBinarySerializer();

  /**
   * {@inheritDoc}
   */
  @Override
  public double[] deserialize(final InputStream in, final boolean allowNull)
      throws IOException {
    return readDoubleArray(in, allowNull, null);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void serialize(final OutputStream out, @Nullable final Object obj)
      throws IOException {
    final double[] value;
    try {
      value = (double[]) obj;
    } catch (final ClassCastException e) {
      throw new SerializationException(e);
    }
    writeDoubleArray(out, value);
  }

}