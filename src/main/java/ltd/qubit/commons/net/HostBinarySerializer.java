////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.net;

import ltd.qubit.commons.io.OutputUtils;
import ltd.qubit.commons.io.error.SerializationException;
import ltd.qubit.commons.io.error.UnexpectedNullValueException;
import ltd.qubit.commons.io.serialize.BinarySerializer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.annotation.concurrent.Immutable;

import static ltd.qubit.commons.io.InputUtils.readInt;
import static ltd.qubit.commons.io.InputUtils.readNullMark;
import static ltd.qubit.commons.io.InputUtils.readString;

/**
 * The {@link BinarySerializer} for the {@link Host} class.
 *
 * @author Haixing Hu
 */
@Immutable
public final class HostBinarySerializer implements BinarySerializer {

  public static final HostBinarySerializer INSTANCE = new HostBinarySerializer();

  @Override
  public Host deserialize(final InputStream in, final boolean allowNull)
      throws IOException {
    if (readNullMark(in)) {
      if (allowNull) {
        return null;
      } else {
        throw new UnexpectedNullValueException();
      }
    } else {
      final String scheme = readString(in, false);
      final String hostname = readString(in, false);
      final int port = readInt(in);
      return new Host(scheme, hostname, port);
    }
  }

  @Override
  public void serialize(final OutputStream out, final Object obj)
      throws IOException {
    if (! OutputUtils.writeNullMark(out, obj)) {
      final Host host;
      try {
        host = (Host) obj;
      } catch (final ClassCastException e) {
        throw new SerializationException(e);
      }
      OutputUtils.writeString(out, host.scheme());
      OutputUtils.writeString(out, host.hostname());
      OutputUtils.writeInt(out, host.port());
    }
  }
}
