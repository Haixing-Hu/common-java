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
import ltd.qubit.commons.io.error.InvalidFormatException;
import ltd.qubit.commons.io.error.SerializationException;
import ltd.qubit.commons.io.serialize.BinarySerializer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import javax.annotation.concurrent.Immutable;

import static ltd.qubit.commons.io.InputUtils.readString;

/**
 * The {@link BinarySerializer} for the {@link Url} class.
 *
 * @author Haixing Hu
 */
@Immutable
public final class UrlBinarySerializer implements BinarySerializer {

  public static final UrlBinarySerializer INSTANCE = new UrlBinarySerializer();

  @Override
  public Url deserialize(final InputStream in, final boolean allowNull)
      throws IOException {
    final String str = readString(in, allowNull);
    if (str == null) {
      return null;
    }
    try {
      return new Url(str);
    } catch (final MalformedURLException e) {
      throw new InvalidFormatException(e);
    }
  }

  @Override
  public void serialize(final OutputStream out, final Object obj)
      throws IOException {
    final Url url;
    try {
      url = (Url) obj;
    } catch (final ClassCastException e) {
      throw new SerializationException(e);
    }
    OutputUtils.writeString(out, (url == null ? null : url.toString()));
  }
}
