////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.value;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.io.OutputUtils;
import ltd.qubit.commons.io.error.SerializationException;
import ltd.qubit.commons.io.error.UnexpectedNullValueException;
import ltd.qubit.commons.io.serialize.BinarySerializer;
import ltd.qubit.commons.lang.Type;
import ltd.qubit.commons.lang.TypeUtils;

import static ltd.qubit.commons.io.InputUtils.readEnum;
import static ltd.qubit.commons.io.InputUtils.readNullMark;

/**
 * The {@link BinarySerializer} for the {@link BasicValue} class.
 *
 * @author Haixing Hu
 */
@Immutable
public final class BasicValueBinarySerializer implements BinarySerializer {

  public static final BasicValueBinarySerializer INSTNACE = new BasicValueBinarySerializer();

  @Override
  public BasicValue deserialize(final InputStream in, final boolean allowNull)
      throws IOException {
    if (readNullMark(in)) {
      if (allowNull) {
        return null;
      } else {
        throw new UnexpectedNullValueException();
      }
    }
    final BasicValue result = new BasicValue();
    result.type = readEnum(Type.class, in, false);
    try {
      result.value = TypeUtils.readObject(result.type, in);
    } catch (final ClassNotFoundException e) {
      throw new IOException(e);
    } catch (final UnsupportedDataTypeException e) {
      throw new IOException(e);
    }
    return result;
  }

  @Override
  public void serialize(final OutputStream out, @Nullable final Object obj)
      throws IOException {
    if (! OutputUtils.writeNullMark(out, obj)) {
      final BasicValue var;
      try {
        var = (BasicValue) obj;
      } catch (final ClassCastException e) {
        throw new SerializationException(e);
      }
      OutputUtils.writeEnum(out, var.type);
      TypeUtils.writeObject(var.type, var.value, out);
    }
  }

}
