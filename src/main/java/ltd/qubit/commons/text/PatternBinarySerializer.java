////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.io.error.SerializationException;
import ltd.qubit.commons.io.error.UnexpectedNullValueException;
import ltd.qubit.commons.io.serialize.BinarySerializer;

import static ltd.qubit.commons.io.InputUtils.readBooleanObject;
import static ltd.qubit.commons.io.InputUtils.readEnum;
import static ltd.qubit.commons.io.InputUtils.readNullMark;
import static ltd.qubit.commons.io.InputUtils.readString;
import static ltd.qubit.commons.io.OutputUtils.writeBooleanObject;
import static ltd.qubit.commons.io.OutputUtils.writeEnum;
import static ltd.qubit.commons.io.OutputUtils.writeNullMark;
import static ltd.qubit.commons.io.OutputUtils.writeString;

/**
 * The {@link BinarySerializer} for the {@link Pattern} class.
 *
 * @author Haixing Hu
 */
@Immutable
public final class PatternBinarySerializer implements BinarySerializer {

  public static final PatternBinarySerializer INSTANCE = new PatternBinarySerializer();

  @Override
  public Pattern deserialize(final InputStream in, final boolean allowNull)
      throws IOException {
    if (readNullMark(in)) {
      if (allowNull) {
        return null;
      } else {
        throw new UnexpectedNullValueException();
      }
    } else {
      final PatternType type = readEnum(PatternType.class, in, true);
      final Boolean caseInsensitive = readBooleanObject(in, true);
      final String expression = readString(in, false);
      return new Pattern(type, caseInsensitive, expression);
    }
  }

  @Override
  public void serialize(final OutputStream out, @Nullable final Object obj)
      throws IOException {
    if (!writeNullMark(out, obj)) {
      final Pattern pattern;
      try {
        pattern = (Pattern) obj;
      } catch (final ClassCastException e) {
        throw new SerializationException(e);
      }
      writeEnum(out, pattern.getType());
      writeBooleanObject(out, pattern.getIgnoreCase());
      writeString(out, pattern.getExpression());
    }
  }
}
