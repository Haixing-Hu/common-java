////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
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

import ltd.qubit.commons.io.io.OutputUtils;
import ltd.qubit.commons.io.io.error.InvalidFormatException;
import ltd.qubit.commons.io.io.error.SerializationException;
import ltd.qubit.commons.io.io.serialize.BinarySerializer;

import static ltd.qubit.commons.io.io.InputUtils.readString;

/**
 * The {@link BinarySerializer} for the {@link NodePattern} class.
 *
 * @author Haixing Hu
 */
@Immutable
public final class NodePatternBinarySerializer implements BinarySerializer {

  public static final NodePatternBinarySerializer INSTANCE = new NodePatternBinarySerializer();

  @Override
  public NodePattern deserialize(final InputStream in, final boolean allowNull)
      throws IOException {
    final String xpath = readString(in, allowNull);
    if (xpath == null) {
      return null;
    } else {
      try {
        return new NodePattern(xpath);
      } catch (final InvalidXPathExpressionException e) {
        throw new InvalidFormatException(e);
      }
    }
  }

  @Override
  public void serialize(final OutputStream out, @Nullable final Object obj)
      throws IOException {
    final NodePattern np;
    try {
      np = (NodePattern) obj;
    } catch (final ClassCastException e) {
      throw new SerializationException(e);
    }
    OutputUtils.writeString(out, (np == null ? null : np.xpath));
  }

}
