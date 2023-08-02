////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.xml;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.io.error.SerializationException;
import ltd.qubit.commons.io.error.UnexpectedNullValueException;
import ltd.qubit.commons.io.serialize.BinarySerializer;

import static ltd.qubit.commons.io.InputUtils.readNullMark;
import static ltd.qubit.commons.io.InputUtils.readString;
import static ltd.qubit.commons.io.InputUtils.readVarInt;
import static ltd.qubit.commons.io.OutputUtils.writeNullMark;
import static ltd.qubit.commons.io.OutputUtils.writeString;
import static ltd.qubit.commons.io.OutputUtils.writeVarInt;

/**
 * The {@link BinarySerializer} for the {@link TagPattern} class.
 *
 * @author Haixing Hu
 */
@Immutable
public final class TagPatternBinarySerializer implements BinarySerializer {

  public static final TagPatternBinarySerializer INSTANCE = new TagPatternBinarySerializer();

  @Override
  public TagPattern deserialize(final InputStream in, final boolean allowNull)
      throws IOException {
    if (readNullMark(in)) {
      if (allowNull) {
        return null;
      } else {
        throw new UnexpectedNullValueException();
      }
    } else {
      final TagPattern result = new TagPattern();
      result.tagName = readString(in, true);
      result.attributeName = readString(in, true);
      result.attributeValue = readString(in, true);
      result.order = readVarInt(in);
      result.child = deserialize(in, true); // recursively deserialize child
      return result;
    }
  }

  @Override
  public void serialize(final OutputStream out, @Nullable final Object obj)
      throws IOException {
    if (! writeNullMark(out, obj)) {
      final TagPattern tp;
      try {
        tp = (TagPattern) obj;
      } catch (final ClassCastException e) {
        throw new SerializationException(e);
      }
      writeString(out, tp.tagName);
      writeString(out, tp.attributeName);
      writeString(out, tp.attributeValue);
      writeVarInt(out, tp.order);
      serialize(out, tp.child);
    }
  }

}
