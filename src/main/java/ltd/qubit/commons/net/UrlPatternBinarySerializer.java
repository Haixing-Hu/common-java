////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.io.io.OutputUtils;
import ltd.qubit.commons.io.io.error.SerializationException;
import ltd.qubit.commons.io.io.error.UnexpectedNullValueException;
import ltd.qubit.commons.io.io.serialize.BinarySerializer;
import ltd.qubit.commons.text.Pattern;

import static ltd.qubit.commons.io.io.InputUtils.readEnum;
import static ltd.qubit.commons.io.io.InputUtils.readNullMark;
import static ltd.qubit.commons.io.io.InputUtils.readObject;

/**
 * The {@link BinarySerializer} for the {@link UrlPattern} class.
 *
 * @author Haixing Hu
 */
@Immutable
public final class UrlPatternBinarySerializer implements BinarySerializer {

  public static final UrlPatternBinarySerializer INSTANCE = new UrlPatternBinarySerializer();

  @Override
  public UrlPattern deserialize(final InputStream in, final boolean allowNull)
      throws IOException {
    if (readNullMark(in)) {
      if (allowNull) {
        return null;
      } else {
        throw new UnexpectedNullValueException();
      }
    } else {
      final UrlPart part = readEnum(UrlPart.class, in, true);
      final Pattern pattern = readObject(Pattern.class, in, false);
      return new UrlPattern(part, pattern);
    }
  }

  @Override
  public void serialize(final OutputStream out, @Nullable final Object obj)
      throws IOException {
    if (! OutputUtils.writeNullMark(out, obj)) {
      final UrlPattern up;
      try {
        up = (UrlPattern) obj;
      } catch (final ClassCastException e) {
        throw new SerializationException(e);
      }
      OutputUtils.writeEnum(out, up.part);
      OutputUtils.writeObject(Pattern.class, out, up.pattern);
    }
  }
}
