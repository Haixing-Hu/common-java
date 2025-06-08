////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;

import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.io.OutputUtils;
import ltd.qubit.commons.io.error.InvalidFormatException;
import ltd.qubit.commons.io.error.SerializationException;
import ltd.qubit.commons.io.serialize.BinarySerializer;

import static ltd.qubit.commons.io.InputUtils.readString;

/**
 * {@link Url} 类的 {@link BinarySerializer}。
 *
 * @author 胡海星
 */
@Immutable
public final class UrlBinarySerializer implements BinarySerializer {

  /**
   * 本类的唯一实例。
   */
  public static final UrlBinarySerializer INSTANCE = new UrlBinarySerializer();

  /**
   * {@inheritDoc}
   */
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

  /**
   * {@inheritDoc}
   */
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