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
import java.util.Date;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.io.io.error.SerializationException;
import ltd.qubit.commons.io.io.serialize.BinarySerializer;

import static ltd.qubit.commons.io.io.InputUtils.readDate;
import static ltd.qubit.commons.io.io.OutputUtils.writeDate;

/**
 * The {@link BinarySerializer} for {@link Date} class.
 *
 * @author Haixing Hu
 */
@Immutable
public final class DateBinarySerializer implements BinarySerializer {

  public static final DateBinarySerializer INSTANCE = new DateBinarySerializer();

  @Override
  public Date deserialize(final InputStream in, final boolean allowNull)
      throws IOException {
    return readDate(in, allowNull);
  }

  @Override
  public void serialize(final OutputStream out, @Nullable final Object obj)
      throws IOException {
    final Date value;
    try {
      value = (Date) obj;
    } catch (final ClassCastException e) {
      throw new SerializationException(e);
    }
    writeDate(out, value);
  }

}
