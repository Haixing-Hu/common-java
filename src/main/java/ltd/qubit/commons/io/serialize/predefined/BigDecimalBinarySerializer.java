////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.io.serialize.predefined;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.io.error.SerializationException;
import ltd.qubit.commons.io.serialize.BinarySerializer;

import static ltd.qubit.commons.io.InputUtils.readBigDecimal;
import static ltd.qubit.commons.io.OutputUtils.writeBigDecimal;

/**
 * The {@link BinarySerializer} for {@link BigDecimal} class.
 *
 * @author Haixing Hu
 */
@Immutable
public final class BigDecimalBinarySerializer implements BinarySerializer {

  public static final BigDecimalBinarySerializer INSTANCE = new BigDecimalBinarySerializer();

  @Override
  public BigDecimal deserialize(final InputStream in, final boolean allowNull)
      throws IOException {
    return readBigDecimal(in, allowNull);
  }

  @Override
  public void serialize(final OutputStream out, @Nullable final Object obj)
      throws IOException {
    final BigDecimal value;
    try {
      value = (BigDecimal) obj;
    } catch (final ClassCastException e) {
      throw new SerializationException(e);
    }
    writeBigDecimal(out, value);
  }

}
