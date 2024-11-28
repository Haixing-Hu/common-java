////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.config.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.io.io.error.SerializationException;
import ltd.qubit.commons.io.io.error.UnexpectedNullValueException;
import ltd.qubit.commons.io.io.serialize.BinarySerializer;
import ltd.qubit.commons.lang.Type;

import static ltd.qubit.commons.io.io.InputUtils.readBoolean;
import static ltd.qubit.commons.io.io.InputUtils.readEnum;
import static ltd.qubit.commons.io.io.InputUtils.readNullMark;
import static ltd.qubit.commons.io.io.InputUtils.readString;
import static ltd.qubit.commons.io.io.InputUtils.readVarInt;
import static ltd.qubit.commons.io.io.OutputUtils.writeBoolean;
import static ltd.qubit.commons.io.io.OutputUtils.writeEnum;
import static ltd.qubit.commons.io.io.OutputUtils.writeNullMark;
import static ltd.qubit.commons.io.io.OutputUtils.writeString;
import static ltd.qubit.commons.io.io.OutputUtils.writeVarInt;

/**
 * The {@link BinarySerializer} for the {@link DefaultProperty} class.
 *
 * @author Haixing Hu
 */
@Immutable
public final class DefaultPropertyBinarySerializer implements BinarySerializer {

  public static final DefaultPropertyBinarySerializer INSTANCE =
      new DefaultPropertyBinarySerializer();

  @Override
  public DefaultProperty deserialize(final InputStream in,
      final boolean allowNull) throws IOException {
    if (readNullMark(in)) {
      if (allowNull) {
        return null;
      } else {
        throw new UnexpectedNullValueException();
      }
    }
    final DefaultProperty result = new DefaultProperty();
    result.setName(readString(in, false));
    result.setType(readEnum(Type.class, in, false));
    result.setDescription(readString(in, true));
    result.setFinal(readBoolean(in));
    final int n = readVarInt(in);
    result.readValues(result.getType(), n, in);
    return result;
  }

  @Override
  public void serialize(final OutputStream out, @Nullable final Object obj)
      throws IOException {
    if (writeNullMark(out, obj)) {
      return;
    }
    final DefaultProperty prop;
    try {
      prop = (DefaultProperty) obj;
    } catch (final ClassCastException e) {
      throw new SerializationException(e);
    }
    writeString(out, prop.getName());
    writeEnum(out, prop.getType());
    writeString(out, prop.getDescription());
    writeBoolean(out, prop.isFinal());
    final int n = prop.getCount();
    writeVarInt(out, n);
    prop.writeValues(out);
  }

}
