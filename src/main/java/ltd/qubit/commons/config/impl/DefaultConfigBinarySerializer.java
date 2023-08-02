////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
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

import ltd.qubit.commons.io.error.SerializationException;
import ltd.qubit.commons.io.error.UnexpectedNullValueException;
import ltd.qubit.commons.io.serialize.BinarySerializer;

import static ltd.qubit.commons.io.InputUtils.readNullMark;
import static ltd.qubit.commons.io.InputUtils.readVarInt;
import static ltd.qubit.commons.io.OutputUtils.writeNullMark;
import static ltd.qubit.commons.io.OutputUtils.writeVarInt;

/**
 * The {@link BinarySerializer} for the {@link DefaultConfig} class.
 *
 * @author Haixing Hu
 */
@Immutable
public final class DefaultConfigBinarySerializer implements BinarySerializer {

  public static final DefaultConfigBinarySerializer INSTANCE = new DefaultConfigBinarySerializer();

  @Override
  public DefaultConfig deserialize(final InputStream in, final boolean allowNull)
      throws IOException {
    if (readNullMark(in)) {
      if (allowNull) {
        return null;
      } else {
        throw new UnexpectedNullValueException();
      }
    }
    final DefaultPropertyBinarySerializer propSerializer = DefaultPropertyBinarySerializer.INSTANCE;
    final DefaultConfig result = new DefaultConfig();
    final int n = readVarInt(in);
    for (int i = 0; i < n; ++i) {
      final DefaultProperty prop = propSerializer.deserialize(in, false);
      result.properties.put(prop.getName(), prop);
    }
    return result;
  }

  @Override
  public void serialize(final OutputStream out, @Nullable final Object obj)
      throws IOException {
    if (writeNullMark(out, obj)) {
      return;
    }
    final DefaultConfig config;
    try {
      config = (DefaultConfig) obj;
    } catch (final ClassCastException e) {
      throw new SerializationException(e);
    }
    final DefaultPropertyBinarySerializer propSerializer = DefaultPropertyBinarySerializer.INSTANCE;
    writeVarInt(out, config.properties.size());
    for (final DefaultProperty prop : config.properties.values()) {
      propSerializer.serialize(out, prop);
    }
  }

}
