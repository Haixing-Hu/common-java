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

import static ltd.qubit.commons.io.InputUtils.readDoubleObject;
import static ltd.qubit.commons.io.OutputUtils.writeDoubleObject;

/**
 * {@link Double} 类的 {@link BinarySerializer}。
 *
 * @author 胡海星
 */
@Immutable
public final class DoubleBinarySerializer implements BinarySerializer {

  public static final DoubleBinarySerializer INSTANCE = new DoubleBinarySerializer();

  /**
   * {@inheritDoc}
   */
  @Override
  public Double deserialize(final InputStream in, final boolean allowNull)
      throws IOException {
    return readDoubleObject(in, allowNull);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void serialize(final OutputStream out, @Nullable final Object obj)
      throws IOException {
    final Double value;
    try {
      value = (Double) obj;
    } catch (final ClassCastException e) {
      throw new SerializationException(e);
    }
    writeDoubleObject(out, value);
  }

}